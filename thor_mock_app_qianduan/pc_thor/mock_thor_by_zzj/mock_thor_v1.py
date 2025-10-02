import asyncio
import struct
import secrets
import frida  # 引入frida
from bleak import BleakClient, BleakScanner
from loguru import logger
import os
from enum import Enum
from typing import Union, List, Optional

# --- App 和 Frida 配置 ---
APP_PACKAGE_NAME = "com.carsystems.thor.app"
FRIDA_SCRIPT_PATH = "thor_rpc.js"

# --- 音效文件类型和配置 ---

class SoundFileType(Enum):
    """音效文件类型枚举 (对应Java中的FileType)"""
    SGU = (3, 0)  # SGU音效类型
    SMP2 = (4, 0)  # SMP2音效包类型
    
    def __init__(self, type_id, version):
        self.type_id = type_id
        self.version = version

class SoundUploadMode(Enum):
    """音效上传模式"""
    MODE_A_SGU = "mode_a_sgu"      # 模式A: 直接SGU音效上传
    MODE_B_SMP2 = "mode_b_smp2"    # 模式B: 音效包文件上传

# --- 音效上传配置 ---
SOUND_FILE_SIZE_THRESHOLD = 1024  # 1KB，超过此大小使用模式B
SMP2_BLOCK_SIZE = 200             # SMP2文件分块大小
MAX_SGU_DATA_SIZE = 512           # SGU直接上传最大数据大小

# --- 辅助函数和类 ---

def take_short(first_byte, second_byte):
    """将两个字节组合成16位有符号整数（小端序）"""
    return struct.unpack('<h', bytes([first_byte, second_byte]))[0]


class ThorCRC16:
    """
    设备的特定CRC16算法 (核心协议部分，与加解密无关，必须保留)
    """

    def __init__(self):
        self.crc_table = self._generate_crc_table()

    def _generate_crc_table(self):
        table = []
        for i in range(256):
            crc = i
            for _ in range(8):
                if crc & 1:
                    crc = (crc >> 1) ^ 0xA001
                else:
                    crc >>= 1
            table.append(crc & 0xFFFF)
        return table

    def calculate(self, data):
        crc = 0xFFFF
        for byte in data:
            tbl_idx = (crc ^ byte) & 0xFF
            crc = ((crc >> 8) ^ self.crc_table[tbl_idx]) & 0xFFFF
        return crc & 0xFFFF

    def create_checksum_part(self, crc_value):
        return struct.pack('<H', crc_value)


class ThorAudioExtractor:
    """
    一个完全依赖 Frida RPC 进行加解密的 BLE 通信器。
    此类不包含任何本地加解密逻辑。
    """

    def __init__(self):
        self.client = None
        self.crc_calculator = ThorCRC16()
        self.response_data = bytearray()
        self.hardware_info = None
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False
        self.frida_rpc = None  # 只保留 Frida RPC 实例
        self.current_command_response = None
        self.current_response_complete = False

    def setup_frida(self):
        """连接 Frida 并加载 RPC 脚本"""
        logger.info(f"👨‍💻 正在初始化 Frida 并附加到 App '{APP_PACKAGE_NAME}'...")
        try:
            device = frida.get_usb_device()
            # 使用 attach 如果 App 已经在运行，或 spawn 启动新进程
            logger.info("   正在启动 App 进程...")
            pid = device.spawn([APP_PACKAGE_NAME])
            session = device.attach(pid)

            with open(FRIDA_SCRIPT_PATH, 'r', encoding='utf-8') as f:
                script_code = f.read()

            script = session.create_script(script_code)
            script.load()
            self.frida_rpc = script.exports
            logger.success("✅ Frida RPC 脚本加载成功!")

            logger.info("   正在恢复 App 运行...")
            device.resume(pid)
            return True
        except Exception as e:
            logger.error(f"❌ Frida 初始化失败: {e}")
            return False

    async def scan_and_connect(self, device_name="Thor"):
        logger.info(f"🔍 扫描 {device_name} 设备...")
        devices = await BleakScanner.discover(timeout=10.0)
        thor_device = next((d for d in devices if d.name and device_name.lower() in d.name.lower()), None)
        if not thor_device:
            logger.error("❌ 未找到设备。")
            return False
        logger.info(f"✅ 找到设备: {thor_device.name} ({thor_device.address})")
        self.client = BleakClient(thor_device.address)
        await self.client.connect()
        logger.info("🔗 连接成功!")
        await self.client.start_notify("6e400003-b5a3-f393-e0a9-e50e24dcca9e", self.notification_handler)
        logger.info("🔔 通知已启用。")
        return True

    def notification_handler(self, _: int, data: bytearray):
        logger.info(f"📨 收到原始数据: {data.hex().upper()}")
        self.response_data.extend(data)
        self.try_parse_complete_response()

    def try_parse_complete_response(self):
        while len(self.response_data) >= 6:
            start_index = self.response_data.find(b'\xA5\x5A')
            if start_index == -1: return
            self.response_data = self.response_data[start_index:]
            if len(self.response_data) < 4: return

            header = struct.unpack('>H', self.response_data[2:4])[0]
            total_len = 4 + (header & 0x1FFF) + 2
            if len(self.response_data) < total_len: return

            packet = self.response_data[:total_len]
            self.response_data = self.response_data[total_len:]
            self.parse_response_packet(packet)

    def parse_response_packet(self, data):
        crc_data, crc_received_bytes = data[:-2], data[-2:]
        crc_received = struct.unpack('<H', crc_received_bytes)[0]
        crc_calculated = self.crc_calculator.calculate(crc_data)
        
        if crc_calculated != crc_received:
            logger.warning(f"❌ CRC 校验失败! 收到: 0x{crc_received:04X}, 计算: 0x{crc_calculated:04X}")
            return
        
        logger.info("✅ CRC验证成功!")
        header = struct.unpack('>H', data[2:4])[0]
        encryption_type = (header >> 13) & 0x7
        data_part = data[4:-2]
        
        logger.info(f"🔍 包类型: {encryption_type}, 数据长度: {len(data_part)}")

        if encryption_type == 0:
            self.parse_hardware_response(data_part)
        elif encryption_type == 2:
            self.parse_handshake_response(data_part)
        elif encryption_type == 1:
            self.parse_encrypted_response(data_part)
        return True

    def parse_hardware_response(self, data):
        if len(data) < 8: return
        self.hardware_info = {
            'command': struct.unpack('>H', data[0:2])[0],
            'hardware_version': struct.unpack('>H', data[2:4])[0],
            'firmware_version': struct.unpack('>H', data[4:6])[0],
            'serial_number': struct.unpack('>H', data[6:8])[0]
        }
        logger.info(
            f"🎉 硬件信息解析成功: HW={self.hardware_info['hardware_version']}, FW={self.hardware_info['firmware_version']}, SN={self.hardware_info['serial_number']}")

    def parse_handshake_response(self, data):
        if len(data) < 8 or not self.client_iv or not self.hardware_info: return

        self.device_iv = bytes(data[:8])
        self.combined_iv = self.client_iv + self.device_iv

        logger.info("🤝 (Frida) 正在调用 RPC init 进行远程初始化...")
        logger.info(f"   组合IV: {self.combined_iv.hex().upper()}")
        
        try:
            rpc_result = self.frida_rpc.init(
                self.combined_iv.hex(),
                self.hardware_info['hardware_version'],
                self.hardware_info['firmware_version'],
                self.hardware_info['serial_number']
            )
        except Exception as e:
            logger.error(f"❌ RPC 调用异常: {e}")
            return
            
        if not rpc_result.get('success'):
            logger.error(f"❌ Frida RPC init 失败: {rpc_result.get('error', '未知错误')}")
            return
        logger.success("✅ Frida RPC init 成功!")
        self.handshake_complete = True
        logger.info("🎊 握手成功完成!")

    def parse_encrypted_response(self, encrypted_data):
        if not self.frida_rpc: 
            logger.error("❌ Frida RPC 未初始化，无法解密!")
            return

        logger.info(f"🔓 (Frida) 正在调用 RPC jiemi 进行远程解密...")
        logger.info(f"   加密数据: {encrypted_data.hex().upper()}")
        
        try:
            rpc_result = self.frida_rpc.jiemi(encrypted_data.hex())
        except Exception as e:
            logger.error(f"❌ RPC 调用异常: {e}")
            return
            
        if not rpc_result.get('success'):
            logger.error(f"❌ Frida RPC jiemi 失败: {rpc_result.get('error', '未知错误')}")
            return

        decrypted_hex = rpc_result.get('decrypted', '')
        if not decrypted_hex: 
            logger.error("❌ Frida RPC jiemi 未返回有效数据。")
            return

        try:
            decrypted_data = bytes.fromhex(decrypted_hex)
        except ValueError as e:
            logger.error(f"❌ 解密数据格式错误: {e}")
            return
            
        logger.success(f"✅ (Frida) 解密成功, 数据: {decrypted_data.hex().upper()}")

        if len(decrypted_data) >= 3:
            # Thor加密数据结构: [填充长度][0x00][命令][数据...][填充]
            padding_length = decrypted_data[0]
            separator = decrypted_data[1] 
            cmd = decrypted_data[2]  # 真正的命令ID在第3字节
            logger.info(f"📝 Thor数据结构: 填充长度={padding_length}, 分隔符={separator}, 命令ID={cmd}")
            
            if cmd == 8:  # 心跳响应
                logger.info("💓 收到心跳响应")
                if len(decrypted_data) >= 5:
                    # 心跳状态从第4字节开始
                    status = struct.unpack('>H', decrypted_data[3:5])[0]
                    logger.info(f"   心跳状态: {status}")
                    self.current_command_response = {'command': cmd, 'success': status == 0, 'status': status}
                else:
                    self.current_command_response = {'command': cmd, 'success': True}
                self.current_response_complete = True
            
            elif cmd == 56:  # COMMAND_READ_UNIVERSAL_DATA_PARAMETERS响应
                logger.info("📊 收到读取通用数据参数响应")
                self._parse_universal_data_parameters(decrypted_data, padding_length)
                
            elif cmd == 55:  # 可能的替代命令ID  
                logger.info("📊 收到通用数据响应 (命令55)")
                self._parse_universal_data_parameters(decrypted_data, padding_length)
                
            elif cmd == 57:  # 可能的替代命令ID
                logger.info("📊 收到通用数据响应 (命令57)")
                self._parse_universal_data_parameters(decrypted_data, padding_length)
                
            elif cmd == 58:  # COMMAND_READ_SETTINGS响应
                logger.info("⚙️ 收到读取设备设置响应")
                self._parse_device_settings(decrypted_data, padding_length)
            else:
                logger.info(f"💬 收到其他命令响应: {cmd}")
                # 提取实际命令数据部分（去除填充）
                data_end_index = len(decrypted_data) - padding_length
                command_data = decrypted_data[3:data_end_index] if data_end_index > 3 else decrypted_data[3:]
                logger.info(f"   命令数据: {command_data.hex().upper()}")
                self.current_command_response = {'command': cmd, 'success': True, 'raw_data': decrypted_data, 'command_data': command_data}
                self.current_response_complete = True
        else:
            logger.warning("⚠️  解密数据长度不足，无法识别响应类型。")

    def _parse_universal_data_parameters(self, decrypted_data, padding_length):
        """解析通用数据参数响应 (命令56)"""
        try:
            logger.info("\\n" + "="*60)
            logger.info("📊 解析通用数据参数响应 (命令56)")
            logger.info("="*60)
            
            # 数据结构: [填充长度][0x00][cmd(56)][count(2)][length(2)][params...]
            if len(decrypted_data) < 7:
                logger.error("❌ 数据长度不足，无法解析通用数据参数")
                return
                
            # 提取参数计数 (第4-5字节，大端序)
            param_count = struct.unpack('>H', decrypted_data[3:5])[0]
            logger.info(f"📊 参数数量: {param_count}")
            
            # 提取数据长度 (第6-7字节，大端序)  
            data_length = struct.unpack('>H', decrypted_data[5:7])[0]
            logger.info(f"📏 数据长度: {data_length}")
            
            # 提取参数数据部分 (去除末尾填充)
            data_end_index = len(decrypted_data) - padding_length
            params_data = decrypted_data[7:data_end_index]
            logger.info(f"📋 参数数据: {params_data.hex().upper()} (长度: {len(params_data)}字节)")
            
            # 解析具体参数
            if len(params_data) >= param_count * 4:  # 假设每个参数4字节
                logger.info("📋 解析参数列表:")
                parameters = []
                for i in range(param_count):
                    param_start = i * 4
                    if param_start + 3 < len(params_data):
                        param_value = struct.unpack('>I', params_data[param_start:param_start + 4])[0]
                        parameters.append(param_value)
                        logger.info(f"   参数{i+1}: {param_value} (0x{param_value:08X})")
                        
                self.current_command_response = {
                    'command': 56,
                    'success': True,
                    'param_count': param_count,
                    'data_length': data_length,
                    'parameters': parameters,
                    'raw_params_data': params_data.hex().upper()
                }
            else:
                logger.warning("⚠️  参数数据长度不足，无法完整解析")
                self.current_command_response = {
                    'command': 56,
                    'success': False,
                    'error': '数据长度不足'
                }
                
            self.current_response_complete = True
            logger.info("="*60)
            
        except Exception as e:
            logger.error(f"❌ 解析通用数据参数失败: {e}")
            self.current_command_response = {
                'command': 56,
                'success': False,
                'error': str(e)
            }
            self.current_response_complete = True

    def _parse_device_settings(self, decrypted_data, padding_length):
        """解析设备设置响应 (命令58)"""
        try:
            logger.info("\\n" + "="*60)
            logger.info("⚙️ 解析设备设置响应 (命令58)")
            logger.info("="*60)
            
            # 数据结构: [填充长度][0x00][cmd(58)][settings_data...]
            if len(decrypted_data) < 4:
                logger.error("❌ 数据长度不足，无法解析设备设置")
                return
                
            # 提取设置数据部分 (去除末尾填充)
            data_end_index = len(decrypted_data) - padding_length
            settings_data = decrypted_data[3:data_end_index]
            logger.info(f"⚙️ 设置数据: {settings_data.hex().upper()} (长度: {len(settings_data)}字节)")
            
            # 解析设置数据的具体结构
            settings_parsed = {}
            if len(settings_data) >= 2:
                # 尝试解析为键值对结构
                logger.info("📋 尝试解析为设置项:")
                offset = 0
                setting_index = 1
                
                while offset + 1 < len(settings_data):
                    try:
                        # 假设每个设置项为2字节值
                        setting_value = struct.unpack('>H', settings_data[offset:offset+2])[0]
                        settings_parsed[f'setting_{setting_index}'] = setting_value
                        logger.info(f"   设置项{setting_index}: {setting_value} (0x{setting_value:04X})")
                        offset += 2
                        setting_index += 1
                        
                        if setting_index > 20:  # 防止无限循环
                            break
                    except:
                        break
                        
            self.current_command_response = {
                'command': 58,
                'success': True,
                'settings_data_hex': settings_data.hex().upper(),
                'settings_parsed': settings_parsed,
                'data_length': len(settings_data)
            }
            self.current_response_complete = True
            logger.info("="*60)
            
        except Exception as e:
            logger.error(f"❌ 解析设备设置失败: {e}")
            self.current_command_response = {
                'command': 58,
                'success': False,
                'error': str(e)
            }
            self.current_response_complete = True

    def detect_sound_file_type(self, file_data: bytes, file_name: str = "") -> SoundFileType:
        """检测音效文件类型"""
        if file_name.lower().endswith('.smp') or file_data.startswith(b'SMP2'):
            return SoundFileType.SMP2
        else:
            return SoundFileType.SGU

    def choose_upload_mode(self, file_data: bytes, file_type: SoundFileType, file_count: int = 1) -> SoundUploadMode:
        """
        自动选择上传模式 (与Java项目逻辑一致)
        
        选择规则:
        1. 文件数量 > 1 → 模式B (批量上传)
        2. SMP2文件类型 → 模式B (SMP2包上传)
        3. 文件大小 > 阈值 → 模式B (分块传输)
        4. 其他情况 → 模式A (直接SGU上传)
        """
        file_size = len(file_data)
        
        # 规则1: 多文件必须使用模式B
        if file_count > 1:
            logger.info(f"🎯 检测到多文件上传 ({file_count}个)，选择模式B")
            return SoundUploadMode.MODE_B_SMP2
            
        # 规则2: SMP2文件类型使用模式B
        if file_type == SoundFileType.SMP2:
            logger.info(f"🎯 检测到SMP2文件类型，选择模式B")
            return SoundUploadMode.MODE_B_SMP2
            
        # 规则3: 大文件使用模式B进行分块传输
        if file_size > SOUND_FILE_SIZE_THRESHOLD:
            logger.info(f"🎯 检测到大文件 ({file_size}字节 > {SOUND_FILE_SIZE_THRESHOLD}字节)，选择模式B")
            return SoundUploadMode.MODE_B_SMP2
            
        # 规则4: SGU小文件使用模式A
        if file_size > MAX_SGU_DATA_SIZE:
            logger.info(f"🎯 SGU文件过大 ({file_size}字节 > {MAX_SGU_DATA_SIZE}字节)，选择模式B")
            return SoundUploadMode.MODE_B_SMP2
            
        logger.info(f"🎯 检测到小SGU文件 ({file_size}字节)，选择模式A")
        return SoundUploadMode.MODE_A_SGU

    async def upload_sound_file(self, file_data: bytes, sound_id: Optional[int] = None, 
                               file_name: str = "", force_mode: Optional[SoundUploadMode] = None) -> bool:
        """
        统一的音效上传接口 (与Java项目UploadFilesService一致)
        
        Args:
            file_data: 音效文件数据
            sound_id: 指定音效ID，None表示自动分配
            file_name: 文件名，用于类型检测
            force_mode: 强制使用指定模式，None表示自动选择
            
        Returns:
            bool: 上传是否成功
        """
        try:
            logger.info("\n" + "="*60)
            logger.info("🎵 Thor音效统一上传接口")
            logger.info("="*60)
            
            # 1. 检测文件类型
            file_type = self.detect_sound_file_type(file_data, file_name)
            logger.info(f"📄 文件类型: {file_type.name}")
            logger.info(f"📏 文件大小: {len(file_data)}字节")
            logger.info(f"📝 文件名: {file_name or 'unnamed'}")
            
            # 2. 选择上传模式
            if force_mode:
                upload_mode = force_mode
                logger.info(f"🔧 强制使用模式: {upload_mode.value}")
            else:
                upload_mode = self.choose_upload_mode(file_data, file_type)
                logger.info(f"🤖 自动选择模式: {upload_mode.value}")
            
            # 3. 获取现有音效列表
            logger.info("📋 获取现有音效列表...")
            response = await self.send_command_and_wait("READ_SGU_SOUNDS", 36, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 无法读取现有音效列表")
                return False
                
            # 4. 确定音效ID
            if sound_id is None:
                sound_id = self._find_next_available_sound_id(response)
                logger.info(f"🎯 自动分配音效ID: {sound_id}")
            else:
                if self._is_sound_id_used(response, sound_id):
                    logger.warning(f"⚠️  音效ID {sound_id} 已被占用，自动寻找新ID")
                    sound_id = self._find_next_available_sound_id(response)
                    logger.info(f"🎯 重新分配音效ID: {sound_id}")
                else:
                    logger.info(f"🎯 使用指定音效ID: {sound_id}")
            
            # 5. 根据模式执行上传
            if upload_mode == SoundUploadMode.MODE_A_SGU:
                success = await self._upload_mode_a(file_data, sound_id, file_type)
            else:  # MODE_B_SMP2
                success = await self._upload_mode_b(file_data, sound_id, file_type, file_name)
                
            if success:
                logger.success(f"🎉 音效上传成功! ID: {sound_id}, 模式: {upload_mode.value}")
                return True
            else:
                logger.error(f"❌ 音效上传失败! 模式: {upload_mode.value}")
                return False
                
        except Exception as e:
            logger.error(f"❌ 音效上传异常: {e}")
            return False

    def _find_next_available_sound_id(self, sound_list_response) -> int:
        """找到下一个可用的音效ID (模拟Java项目逻辑)"""
        # 这里简化实现，实际应该解析sound_list_response
        # 返回10-50范围内的测试ID
        for sound_id in range(10, 51):
            if not self._is_sound_id_used(sound_list_response, sound_id):
                return sound_id
        return 10  # 默认返回10

    def _is_sound_id_used(self, sound_list_response, sound_id: int) -> bool:
        """检查音效ID是否已被使用"""
        # 这里简化实现，实际应该解析sound_list_response中的音效列表
        return False  # 简化为总是返回未使用

    async def _upload_mode_a(self, file_data: bytes, sound_id: int, file_type: SoundFileType) -> bool:
        """模式A: 直接SGU音效上传的内部实现"""
        logger.info(f"🎵 执行模式A上传: 音效ID={sound_id}")
        
        # 检查数据大小限制
        if len(file_data) > MAX_SGU_DATA_SIZE:
            logger.error(f"❌ SGU数据过大: {len(file_data)} > {MAX_SGU_DATA_SIZE}")
            return False
            
        # 执行模式A的4步流程
        try:
            # 2. START_WRITE_SGU_SOUND (38) - 基于Frida数据格式
            response = await self.send_command_and_wait("START_WRITE_SGU_SOUND", 38, [sound_id], crypto_type=1, timeout=8)
            if not response:
                return False
                
            # 3. WRITE_SGU_SOUND (39) - 需要发送音效ID和数据
            # 根据Java项目，这个命令需要特殊处理
            write_params = [sound_id] + list(file_data)
            response = await self.send_command_and_wait("WRITE_SGU_SOUND", 39, write_params, crypto_type=1, timeout=8)
            if not response:
                return False
                
            # 4. APPLY_SGU_SOUND (40)
            response = await self.send_command_and_wait("APPLY_SGU_SOUND", 40, [sound_id], crypto_type=1, timeout=8)
            if not response:
                return False
                
            return True
            
        except Exception as e:
            logger.error(f"❌ 模式A上传失败: {e}")
            return False

    async def _upload_mode_b(self, file_data: bytes, sound_id: int, file_type: SoundFileType, file_name: str) -> bool:
        """模式B: 音效包文件上传的内部实现"""
        logger.info(f"📦 执行模式B上传: 音效ID={sound_id}, 文件大小={len(file_data)}字节")
        
        try:
            # 2. DOWNLOAD_START_GROUP (112) - 基于Frida明文: data=11,0,112,0,3
            response = await self.send_command_and_wait("DOWNLOAD_START_GROUP", 112, [0, 3], crypto_type=1, timeout=8)
            if not response:
                return False
                
            # 3. DOWNLOAD_START_FILE (113) - 基于真实Frida明文
            # data=5,0,113,4,0,-115,2,0,10,-2,-4 → 精确复制这个格式
            file_size = len(file_data)
            file_params = [
                4,                    # 文件类型 (固定4)
                0,                    # 固定0
                (file_size - 141) & 0xFF,  # -115 = file_size - 141 的低字节
                2,                    # 固定2
                0,                    # 固定0
                sound_id,             # 音效ID
                0xFE,                 # 固定-2
                0xFC                  # 固定-4
            ]
            response = await self.send_command_and_wait("DOWNLOAD_START_FILE", 113, file_params, crypto_type=1, timeout=8)
            if not response:
                return False
                
            # 4. DOWNLOAD_WRITE_BLOCK (114) - 分块传输
            # 基于Frida明文: data=1,0,114,0,0,0,-56,83,77,80,50,0,48,0,0,0,-58,0,2...
            block_size = 200  # 固定200字节如Frida数据显示
            total_blocks = (file_size + block_size - 1) // block_size
            
            for block_idx in range(total_blocks):
                start_offset = block_idx * block_size
                end_offset = min(start_offset + block_size, file_size)
                block_data = file_data[start_offset:end_offset]
                
                # 基于Frida数据构造: [0][块索引][0][-56(数据长度)] + 数据
                block_params = [
                    0,                          # 固定0
                    block_idx,                  # 块索引
                    0,                          # 固定0
                    (len(block_data) - 256) & 0xFF  # -56 等于 len-256 的字节表示
                ] + list(block_data)            # 实际数据
                
                logger.info(f"   📤 传输块 {block_idx + 1}/{total_blocks} (大小: {len(block_data)})")
                response = await self.send_command_and_wait(f"DOWNLOAD_WRITE_BLOCK_{block_idx}", 114, block_params, crypto_type=1, timeout=8)
                if not response:
                    return False
            
            # 5. DOWNLOAD_COMMIT_FILE (115)
            response = await self.send_command_and_wait("DOWNLOAD_COMMIT_FILE", 115, crypto_type=1, timeout=8)
            if not response:
                return False
                
            # 6. DOWNLOAD_COMMIT_GROUP (116)
            response = await self.send_command_and_wait("DOWNLOAD_COMMIT_GROUP", 116, crypto_type=1, timeout=8)
            if not response:
                return False
                
            # 7. DOWNLOAD_GET_STATUS (117) - 可选
            response = await self.send_command_and_wait("DOWNLOAD_GET_STATUS", 117, crypto_type=1, timeout=5)
            
            return True
            
        except Exception as e:
            logger.error(f"❌ 模式B上传失败: {e}")
            return False

    def _create_packet(self, data_body, crypto_type):
        data_part = data_body
        if crypto_type == 1:
            if not self.frida_rpc: 
                logger.error("❌ Frida RPC 未初始化，无法加密!")
                return None

            # 计算填充
            total_data_length_for_padding = 1 + len(data_body)
            padding_needed = (16 - (total_data_length_for_padding % 16)) % 16
            
            if padding_needed == 0 and total_data_length_for_padding > 0:
                padding_needed = 16
            elif total_data_length_for_padding == 0:
                padding_needed = 16
                
            padding = bytes([0xA5] * padding_needed)
            pre_encrypt_data = bytes([padding_needed]) + data_body + padding

            logger.info(f"🔒 (Frida) 正在调用 RPC jiami 加密数据")
            logger.info(f"   加密前数据: {pre_encrypt_data.hex().upper()}")
            
            try:
                rpc_result = self.frida_rpc.jiami(pre_encrypt_data.hex())
            except Exception as e:
                logger.error(f"❌ RPC 调用异常: {e}")
                return None
                
            if not rpc_result.get('success'):
                logger.error(f"❌ Frida RPC jiami 失败: {rpc_result.get('error', '未知错误')}")
                return None

            try:
                data_part = bytes.fromhex(rpc_result['encrypted'])
                logger.success(f"✅ (Frida) 加密成功! 输出长度: {len(data_part)} 字节")
            except ValueError as e:
                logger.error(f"❌ 加密结果格式错误: {e}")
                return None

        preamble = struct.pack('>h', -23206)
        header = struct.pack('>H', ((crypto_type & 0x7) << 13) | (len(data_part) & 0x1FFF))
        packet_without_crc = preamble + header + data_part
        crc_bytes = self.crc_calculator.create_checksum_part(self.crc_calculator.calculate(packet_without_crc))
        return packet_without_crc + crc_bytes

    def _create_command_data(self, command_id: int, *params) -> bytes:
        """
        创建正确的Thor命令数据格式 - 基于Frida明文数据完全重写
        
        格式: [分隔符0x00][命令ID][参数...]
        注意: 填充长度和填充数据由_create_packet处理
        """
        # 构造核心命令数据: [分隔符][命令ID][参数...]
        data = struct.pack('BB', 0, command_id)
        
        if params:
            for param in params:
                if isinstance(param, int):
                    # 所有整数参数都作为字节处理（基于Frida数据观察）
                    data += struct.pack('B', param & 0xFF)
                elif isinstance(param, bytes):
                    data += param
                elif isinstance(param, str):
                    data += param.encode('utf-8')
                elif isinstance(param, list):
                    # 处理参数列表
                    for p in param:
                        if isinstance(p, int):
                            data += struct.pack('B', p & 0xFF)
                        else:
                            data += bytes([p]) if isinstance(p, int) else p
        
        return data

    async def send_command_and_wait(self, command_name, command_id, params=None, crypto_type=1, timeout=10):
        if not self.client or not self.client.is_connected: 
            logger.error("❌ 设备未连接")
            return None

        self.current_command_response = None
        self.current_response_complete = False

        # 创建命令数据
        if params is None:
            command_data = self._create_command_data(command_id)
        else:
            command_data = self._create_command_data(command_id, *params)

        packet = self._create_packet(command_data, crypto_type)
        if not packet: 
            logger.error(f"❌ 无法创建 {command_name} 命令包")
            return None

        logger.info(f"📤 发送 {command_name} 命令: {packet.hex().upper()}")
        logger.info(f"   命令数据: {command_data.hex().upper()}")
        
        try:
            await self.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
            logger.info(f"✅ {command_name}命令已发送")
            
            # 等待响应
            for i in range(timeout):
                await asyncio.sleep(1)
                if self.current_response_complete:
                    logger.success(f"✅ 收到 {command_name} 响应!")
                    return self.current_command_response
                if i % 2 == 0:
                    logger.info(f"⏳ 等待{command_name}响应... ({i + 1}/{timeout})")
                    
            logger.error(f"⏰ {command_name} 响应超时。")
            return None
            
        except Exception as e:
            logger.error(f"❌ 发送{command_name}命令失败: {e}")
            return None

    async def test_mode_a_sgu_upload(self):
        """模式A: 直接SGU音效上传 (简单音效)"""
        try:
            logger.info("\n" + "="*60)
            logger.info("🎵 模式A: 直接SGU音效上传")
            logger.info("="*60)
            
            # 1. READ_SGU_SOUNDS (36) → 获取现有音效列表
            logger.info("📋 步骤1: 读取现有音效列表...")
            response = await self.send_command_and_wait("READ_SGU_SOUNDS", 36, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 无法读取现有音效列表")
                return False
                
            logger.info(f"✅ 现有音效列表获取成功: {response}")
            
            # 2. START_WRITE_SGU_SOUND (38) → 分配音效ID，开始写入
            logger.info("🎯 步骤2: 开始SGU音效写入...")
            # 使用音效ID=10作为测试
            sound_id = 10
            response = await self.send_command_and_wait("START_WRITE_SGU_SOUND", 38, [sound_id], crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 开始SGU音效写入失败")
                return False
                
            logger.info(f"✅ SGU音效写入开始成功: {response}")
            
            # 3. WRITE_SGU_SOUND (39) → 直接写入音效数据
            logger.info("📝 步骤3: 写入音效数据...")
            # 创建简单的测试音效数据 (PCM格式示例)
            test_audio_data = bytes([0x00, 0x01, 0x02, 0x03] * 50)  # 200字节测试数据
            write_params = [sound_id] + list(test_audio_data)
            response = await self.send_command_and_wait("WRITE_SGU_SOUND", 39, write_params, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 写入音效数据失败")
                return False
                
            logger.info(f"✅ 音效数据写入成功: {response}")
            
            # 4. APPLY_SGU_SOUND (40) → 应用到设备
            logger.info("🚀 步骤4: 应用SGU音效到设备...")
            response = await self.send_command_and_wait("APPLY_SGU_SOUND", 40, [sound_id], crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 应用SGU音效失败")
                return False
                
            logger.info(f"✅ SGU音效应用成功: {response}")
            
            # 5. 验证结果 - 再次读取音效列表
            logger.info("🔍 步骤5: 验证音效上传结果...")
            response = await self.send_command_and_wait("VERIFY_SGU_SOUNDS", 36, crypto_type=1, timeout=8)
            if response:
                logger.success("🎉 模式A: SGU音效上传完成!")
                logger.info(f"   最终音效列表: {response}")
            else:
                logger.warning("⚠️  无法验证上传结果")
                
            logger.info("="*60)
            return True
            
        except Exception as e:
            logger.error(f"❌ 模式A测试失败: {e}")
            return False

    async def test_mode_b_smp2_upload(self):
        """模式B: 音效包文件上传 (复杂音效/SMP2文件)"""
        try:
            logger.info("\n" + "="*60)
            logger.info("📦 模式B: 音效包文件上传")
            logger.info("="*60)
            
            # 1. READ_SGU_SOUNDS (36) → 获取现有音效列表
            logger.info("📋 步骤1: 读取现有音效列表...")
            response = await self.send_command_and_wait("READ_SGU_SOUNDS", 36, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 无法读取现有音效列表")
                return False
                
            logger.info(f"✅ 现有音效列表获取成功: {response}")
            
            # 2. DOWNLOAD_START_GROUP (112) → 开始组下载
            logger.info("🔗 步骤2: 开始组下载...")
            response = await self.send_command_and_wait("DOWNLOAD_START_GROUP", 112, [0, 3], crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 开始组下载失败")
                return False
                
            logger.info(f"✅ 组下载开始成功: {response}")
            
            # 3. DOWNLOAD_START_FILE (113) → 开始文件下载，设置音效ID
            logger.info("📁 步骤3: 开始文件下载...")
            sound_id = 15  # 使用音效ID=15作为测试
            file_type = 0x01  # SMP2文件类型
            test_smp2_data = b'SMP2' + b'\x00' * 4 + bytes([0x01, 0x02, 0x03, 0x04] * 60)  # 252字节SMP2测试数据
            file_size = len(test_smp2_data)
            
            # 基于真实Frida明文格式
            file_params = [
                4,                    # 文件类型 (固定4)
                0,                    # 固定0
                (file_size - 141) & 0xFF,  # -115 = file_size - 141 的低字节
                2,                    # 固定2
                0,                    # 固定0
                sound_id,             # 音效ID
                0xFE,                 # 固定-2
                0xFC                  # 固定-4
            ]
            response = await self.send_command_and_wait("DOWNLOAD_START_FILE", 113, file_params, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 开始文件下载失败")
                return False
                
            logger.info(f"✅ 文件下载开始成功: {response}")
            logger.info(f"   音效ID: {sound_id}, 文件大小: {file_size}字节")
            
            # 4. DOWNLOAD_WRITE_BLOCK (114) → 分块传输SMP2文件
            logger.info("📤 步骤4: 分块传输SMP2文件...")
            block_size = 200  # 每块200字节
            total_blocks = (file_size + block_size - 1) // block_size  # 向上取整
            
            for block_idx in range(total_blocks):
                start_offset = block_idx * block_size
                end_offset = min(start_offset + block_size, file_size)
                block_data = test_smp2_data[start_offset:end_offset]
                actual_block_size = len(block_data)
                
                # 基于Frida数据构造块参数
                block_params = [
                    0,                          # 固定0
                    block_idx,                  # 块索引
                    0,                          # 固定0
                    (actual_block_size - 256) & 0xFF  # -56 等于 len-256 的字节表示
                ] + list(block_data)            # 实际数据
                
                logger.info(f"   传输块 {block_idx + 1}/{total_blocks} (大小: {actual_block_size}字节)")
                response = await self.send_command_and_wait(f"DOWNLOAD_WRITE_BLOCK_{block_idx}", 114, block_params, crypto_type=1, timeout=8)
                if not response:
                    logger.error(f"❌ 块 {block_idx + 1} 传输失败")
                    return False
                    
                logger.info(f"   ✅ 块 {block_idx + 1} 传输成功")
            
            logger.info(f"✅ 所有 {total_blocks} 个数据块传输完成")
            
            # 5. DOWNLOAD_COMMIT_FILE (115) → 提交文件
            logger.info("📋 步骤5: 提交文件...")
            response = await self.send_command_and_wait("DOWNLOAD_COMMIT_FILE", 115, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 提交文件失败")
                return False
                
            logger.info(f"✅ 文件提交成功: {response}")
            
            # 6. DOWNLOAD_COMMIT_GROUP (116) → 提交组
            logger.info("🎯 步骤6: 提交组...")
            response = await self.send_command_and_wait("DOWNLOAD_COMMIT_GROUP", 116, crypto_type=1, timeout=8)
            if not response:
                logger.error("❌ 提交组失败")
                return False
                
            logger.info(f"✅ 组提交成功: {response}")
            
            # 7. DOWNLOAD_GET_STATUS (117) → 获取上传状态
            logger.info("📊 步骤7: 获取上传状态...")
            response = await self.send_command_and_wait("DOWNLOAD_GET_STATUS", 117, crypto_type=1, timeout=8)
            if response:
                logger.info(f"📈 上传状态: {response}")
            else:
                logger.warning("⚠️  无法获取上传状态")
            
            # 8. 验证结果 - 再次读取音效列表
            logger.info("🔍 步骤8: 验证音效上传结果...")
            response = await self.send_command_and_wait("VERIFY_SMP2_SOUNDS", 36, crypto_type=1, timeout=8)
            if response:
                logger.success("🎉 模式B: SMP2音效包上传完成!")
                logger.info(f"   最终音效列表: {response}")
            else:
                logger.warning("⚠️  无法验证上传结果")
                
            logger.info("="*60)
            return True
            
        except Exception as e:
            logger.error(f"❌ 模式B测试失败: {e}")
            return False

    async def disconnect(self):
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            logger.info("🔌 已断开连接。")


async def main():
    """主执行流程"""
    extractor = ThorAudioExtractor()
    # --- 1. 初始化 Frida ---
    if not extractor.setup_frida():
        return

    try:
        # --- 2. 连接蓝牙设备 ---
        if not await extractor.scan_and_connect():
            return

        await asyncio.sleep(2)

        # --- 3. 获取硬件信息 (未加密) ---
        logger.info("\n🔧 获取硬件信息...")
        packet = extractor._create_packet(extractor._create_command_data(1), 0)  # crypto_type=0
        await extractor.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
        for _ in range(5):
            if extractor.hardware_info: break
            await asyncio.sleep(1)
        if not extractor.hardware_info:
            return logger.error("❌ 获取硬件信息失败。")

        # --- 4. 执行握手 (加密) ---
        logger.info("\n🤝 执行握手...")
        extractor.client_iv = secrets.token_bytes(8)
        packet = extractor._create_packet(extractor.client_iv, 2)  # crypto_type=2
        await extractor.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
        for _ in range(5):
            if extractor.handshake_complete: break
            await asyncio.sleep(1)
        if not extractor.handshake_complete:
            return logger.error("❌ 握手失败。")

        # --- 5. 发送标准心跳命令 ---
        logger.info("\n💓 发送标准心跳命令...")
        response = await extractor.send_command_and_wait("HEARTBEAT", 8, crypto_type=1)
        if response:
            logger.success("🎉 心跳测试成功!")
        else:
            logger.error("❌ 心跳测试失败。")
            
        # --- 6. 测试读取通用数据参数 (命令56) ---
        logger.info("\n📊 测试读取通用数据参数 (命令56)...")
        
        # 尝试不同的命令格式
        logger.info("   尝试基本格式 (无参数)...")
        response = await extractor.send_command_and_wait("READ_UNIVERSAL_DATA_BASIC", 56, crypto_type=1, timeout=5)
        
        if not response:
            logger.info("   尝试带参数格式...")
            response = await extractor.send_command_and_wait("READ_UNIVERSAL_DATA_PARAM", 56, [0], crypto_type=1, timeout=5)
            
        if not response:
            logger.info("   尝试带更多参数格式...")
            response = await extractor.send_command_and_wait("READ_UNIVERSAL_DATA_EXT", 56, [1, 0], crypto_type=1, timeout=5)
            
        if response:
            logger.success("🎉 通用数据参数读取成功!")
        else:
            logger.warning("⚠️  命令56可能不被此设备支持或需要特定参数")
            
        # --- 8. 测试统一音效上传接口 (自动模式选择) ---
        logger.info("\n🚀 测试统一音效上传接口...")
        
        # 测试1: 小SGU文件 → 自动选择模式A
        logger.info("\n📝 测试1: 小SGU文件 (应自动选择模式A)")
        small_sgu_data = bytes([0x00, 0x01, 0x02, 0x03] * 50)  # 200字节
        await extractor.upload_sound_file(small_sgu_data, file_name="test_small.wav")
        
        # 测试2: 大SGU文件 → 自动选择模式B
        logger.info("\n📝 测试2: 大SGU文件 (应自动选择模式B)")
        large_sgu_data = bytes([0x04, 0x05, 0x06, 0x07] * 300)  # 1200字节
        await extractor.upload_sound_file(large_sgu_data, file_name="test_large.wav")
        
        # 测试3: SMP2文件 → 自动选择模式B (读取真实SMP文件)
        logger.info("\n📝 测试3: SMP2文件 (应自动选择模式B)")
        smp_files = [f for f in os.listdir('.') if f.endswith('.smp')]
        if smp_files:
            smp_file = smp_files[0]
            logger.info(f"   发现SMP文件: {smp_file}")
            try:
                with open(smp_file, 'rb') as f:
                    smp2_data = f.read()
                logger.info(f"   读取SMP文件成功: {len(smp2_data)}字节")
                await extractor.upload_sound_file(smp2_data, file_name=smp_file)
            except Exception as e:
                logger.error(f"   读取SMP文件失败: {e}")
                # 回退到测试数据
                smp2_data = b'SMP2' + b'\x00' * 4 + bytes([0x08, 0x09, 0x0A, 0x0B] * 60)
                await extractor.upload_sound_file(smp2_data, file_name="test_sound.smp")
        else:
            logger.info("   未找到SMP文件，使用测试数据")
            smp2_data = b'SMP2' + b'\x00' * 4 + bytes([0x08, 0x09, 0x0A, 0x0B] * 60)
            await extractor.upload_sound_file(smp2_data, file_name="test_sound.smp")
        
        # 测试4: 强制使用模式A
        logger.info("\n📝 测试4: 强制使用模式A")
        test_data = bytes([0x0C, 0x0D, 0x0E, 0x0F] * 30)  # 120字节
        await extractor.upload_sound_file(test_data, sound_id=20, 
                                         force_mode=SoundUploadMode.MODE_A_SGU)
        
        # --- 9. 测试原有接口 (兼容性测试) ---
        logger.info("\n🔧 兼容性测试: 原有模式A和模式B接口...")
        await extractor.test_mode_a_sgu_upload()
        await extractor.test_mode_b_smp2_upload()

    except Exception as e:
        logger.error(f"❌ 程序执行期间发生未知错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await extractor.disconnect()
        logger.info("🏁 任务完成。")


from datetime import datetime

if __name__ == "__main__":
    logger.add(f"thor_rpc_test_{datetime.now().strftime('%Y%m%d_%H%M%S')}.log")
    asyncio.run(main())
