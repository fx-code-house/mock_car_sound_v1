#!/usr/bin/env python3
"""
Thor音效列表读取器 - 验证音效上传成果
专门用于读取Thor设备中的音效列表，验证上传是否成功
"""

import asyncio
import struct
import secrets
import frida
from bleak import BleakClient, BleakScanner
from loguru import logger
from datetime import datetime
from typing import Dict, List, Optional

# --- App 和 Frida 配置 ---
APP_PACKAGE_NAME = "com.carsystems.thor.app"
FRIDA_SCRIPT_PATH = "thor_rpc.js"

class ThorCRC16:
    """Thor设备的CRC16算法"""
    
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


class ThorSoundListReader:
    """Thor音效列表读取器"""
    
    def __init__(self):
        self.client = None
        self.crc_calculator = ThorCRC16()
        self.response_data = bytearray()
        self.hardware_info = None
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False
        self.frida_rpc = None
        self.current_command_response = None
        self.current_response_complete = False
        self.sound_list = []

    def setup_frida(self):
        """初始化Frida RPC"""
        logger.info(f"🔧 初始化Frida并附加到App '{APP_PACKAGE_NAME}'...")
        try:
            device = frida.get_usb_device()
            logger.info("   启动App进程...")
            pid = device.spawn([APP_PACKAGE_NAME])
            session = device.attach(pid)

            with open(FRIDA_SCRIPT_PATH, 'r', encoding='utf-8') as f:
                script_code = f.read()

            script = session.create_script(script_code)
            script.load()
            self.frida_rpc = script.exports
            logger.success("✅ Frida RPC脚本加载成功!")

            logger.info("   恢复App运行...")
            device.resume(pid)
            return True
        except Exception as e:
            logger.error(f"❌ Frida初始化失败: {e}")
            return False

    async def scan_and_connect(self, device_name="Thor"):
        """扫描并连接Thor设备"""
        logger.info(f"🔍 扫描{device_name}设备...")
        devices = await BleakScanner.discover(timeout=10.0)
        thor_device = next((d for d in devices if d.name and device_name.lower() in d.name.lower()), None)
        if not thor_device:
            logger.error("❌ 未找到设备")
            return False
        logger.info(f"✅ 找到设备: {thor_device.name} ({thor_device.address})")
        self.client = BleakClient(thor_device.address)
        await self.client.connect()
        logger.info("🔗 连接成功!")
        await self.client.start_notify("6e400003-b5a3-f393-e0a9-e50e24dcca9e", self.notification_handler)
        logger.info("🔔 通知已启用")
        return True

    def notification_handler(self, _: int, data: bytearray):
        """处理BLE通知数据"""
        logger.debug(f"📨 收到数据: {data.hex().upper()}")
        self.response_data.extend(data)
        self.try_parse_complete_response()

    def try_parse_complete_response(self):
        """尝试解析完整响应"""
        while len(self.response_data) >= 6:
            start_index = self.response_data.find(b'\xA5\x5A')
            if start_index == -1: 
                return
            self.response_data = self.response_data[start_index:]
            if len(self.response_data) < 4: 
                return

            header = struct.unpack('>H', self.response_data[2:4])[0]
            total_len = 4 + (header & 0x1FFF) + 2
            if len(self.response_data) < total_len: 
                return

            packet = self.response_data[:total_len]
            self.response_data = self.response_data[total_len:]
            self.parse_response_packet(packet)

    def parse_response_packet(self, data):
        """解析响应数据包"""
        crc_data, crc_received_bytes = data[:-2], data[-2:]
        crc_received = struct.unpack('<H', crc_received_bytes)[0]
        crc_calculated = self.crc_calculator.calculate(crc_data)
        
        if crc_calculated != crc_received:
            logger.warning(f"❌ CRC校验失败! 收到: 0x{crc_received:04X}, 计算: 0x{crc_calculated:04X}")
            return
        
        logger.debug("✅ CRC验证成功")
        header = struct.unpack('>H', data[2:4])[0]
        encryption_type = (header >> 13) & 0x7
        data_part = data[4:-2]
        
        logger.debug(f"🔍 包类型: {encryption_type}, 数据长度: {len(data_part)}")

        if encryption_type == 0:
            self.parse_hardware_response(data_part)
        elif encryption_type == 2:
            self.parse_handshake_response(data_part)
        elif encryption_type == 1:
            self.parse_encrypted_response(data_part)

    def parse_hardware_response(self, data):
        """解析硬件信息响应"""
        if len(data) < 8: 
            return
        self.hardware_info = {
            'command': struct.unpack('>H', data[0:2])[0],
            'hardware_version': struct.unpack('>H', data[2:4])[0],
            'firmware_version': struct.unpack('>H', data[4:6])[0],
            'serial_number': struct.unpack('>H', data[6:8])[0]
        }
        logger.info(f"🎉 硬件信息: HW={self.hardware_info['hardware_version']}, "
                   f"FW={self.hardware_info['firmware_version']}, SN={self.hardware_info['serial_number']}")

    def parse_handshake_response(self, data):
        """解析握手响应"""
        if len(data) < 8 or not self.client_iv or not self.hardware_info: 
            return

        self.device_iv = bytes(data[:8])
        self.combined_iv = self.client_iv + self.device_iv

        logger.info("🤝 (Frida) 调用RPC init进行远程初始化...")
        
        try:
            rpc_result = self.frida_rpc.init(
                self.combined_iv.hex(),
                self.hardware_info['hardware_version'],
                self.hardware_info['firmware_version'], 
                self.hardware_info['serial_number']
            )
        except Exception as e:
            logger.error(f"❌ RPC调用异常: {e}")
            return
            
        if not rpc_result.get('success'):
            logger.error(f"❌ Frida RPC init失败: {rpc_result.get('error', '未知错误')}")
            return
        logger.success("✅ Frida RPC init成功!")
        self.handshake_complete = True
        logger.info("🎊 握手完成!")

    def parse_encrypted_response(self, encrypted_data):
        """解析加密响应"""
        if not self.frida_rpc: 
            logger.error("❌ Frida RPC未初始化，无法解密!")
            return

        logger.debug(f"🔓 (Frida) 调用RPC jiemi解密...")
        
        try:
            rpc_result = self.frida_rpc.jiemi(encrypted_data.hex())
        except Exception as e:
            logger.error(f"❌ RPC调用异常: {e}")
            return
            
        if not rpc_result.get('success'):
            logger.error(f"❌ Frida RPC jiemi失败: {rpc_result.get('error', '未知错误')}")
            return

        decrypted_hex = rpc_result.get('decrypted', '')
        if not decrypted_hex: 
            logger.error("❌ Frida RPC jiemi未返回有效数据")
            return

        try:
            decrypted_data = bytes.fromhex(decrypted_hex)
        except ValueError as e:
            logger.error(f"❌ 解密数据格式错误: {e}")
            return
            
        logger.debug(f"✅ (Frida) 解密成功: {decrypted_data.hex().upper()}")

        if len(decrypted_data) >= 3:
            padding_length = decrypted_data[0]
            separator = decrypted_data[1] 
            cmd = decrypted_data[2]
            
            logger.debug(f"📝 Thor数据: 填充={padding_length}, 分隔符={separator}, 命令={cmd}")
            
            if cmd == 36:  # READ_SGU_SOUNDS响应
                logger.info("🎵 收到音效列表响应")
                self.parse_sound_list_response(decrypted_data, padding_length)
            else:
                logger.debug(f"💬 收到其他命令响应: {cmd}")
                data_end_index = len(decrypted_data) - padding_length
                command_data = decrypted_data[3:data_end_index] if data_end_index > 3 else decrypted_data[3:]
                self.current_command_response = {'command': cmd, 'success': True, 'command_data': command_data}
                self.current_response_complete = True

    def parse_sound_list_response(self, decrypted_data, padding_length):
        """解析音效列表响应 (命令36)"""
        try:
            logger.info("\n" + "="*60)
            logger.info("🎵 解析音效列表响应 (命令36)")
            logger.info("="*60)
            
            # 提取音效列表数据 (去除填充)
            data_end_index = len(decrypted_data) - padding_length
            sound_data = decrypted_data[3:data_end_index]
            logger.info(f"🎵 音效原始数据: {sound_data.hex().upper()} (长度: {len(sound_data)}字节)")
            
            # 解析音效列表结构
            if len(sound_data) >= 4:
                # 前4字节通常是头部信息
                header = struct.unpack('>I', sound_data[0:4])[0]
                logger.info(f"📊 音效列表头部: 0x{header:08X}")
                
                # 解析音效条目
                self.sound_list = []
                offset = 4
                sound_index = 1
                
                logger.info("🎵 解析音效条目:")
                while offset + 1 < len(sound_data):
                    try:
                        # 每个音效条目通常是2字节
                        if offset + 1 < len(sound_data):
                            sound_entry = struct.unpack('>H', sound_data[offset:offset+2])[0]
                            
                            # 解析音效ID和状态
                            sound_id = sound_entry & 0xFF  # 低8位是ID
                            sound_status = (sound_entry >> 8) & 0xFF  # 高8位是状态
                            
                            if sound_id != 0 or sound_status != 0:  # 跳过空条目
                                sound_info = {
                                    'index': sound_index,
                                    'id': sound_id,
                                    'status': sound_status,
                                    'raw_value': sound_entry
                                }
                                self.sound_list.append(sound_info)
                                logger.info(f"   音效{sound_index}: ID={sound_id}, 状态=0x{sound_status:02X}, 原值=0x{sound_entry:04X}")
                                sound_index += 1
                        
                        offset += 2
                        
                        if sound_index > 100:  # 防止无限循环
                            break
                    except:
                        break
                        
                logger.info(f"📊 共发现 {len(self.sound_list)} 个音效条目")
                
            self.current_command_response = {
                'command': 36,
                'success': True,
                'sound_count': len(self.sound_list),
                'sound_list': self.sound_list,
                'raw_data_hex': sound_data.hex().upper()
            }
            self.current_response_complete = True
            logger.info("="*60)
            
        except Exception as e:
            logger.error(f"❌ 解析音效列表失败: {e}")
            self.current_command_response = {
                'command': 36,
                'success': False,
                'error': str(e)
            }
            self.current_response_complete = True

    def _create_packet(self, data_body, crypto_type):
        """创建BLE数据包"""
        data_part = data_body
        if crypto_type == 1:
            if not self.frida_rpc: 
                logger.error("❌ Frida RPC未初始化，无法加密!")
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

            logger.debug(f"🔒 (Frida) 调用RPC jiami加密数据")
            
            try:
                rpc_result = self.frida_rpc.jiami(pre_encrypt_data.hex())
            except Exception as e:
                logger.error(f"❌ RPC调用异常: {e}")
                return None
                
            if not rpc_result.get('success'):
                logger.error(f"❌ Frida RPC jiami失败: {rpc_result.get('error', '未知错误')}")
                return None

            try:
                data_part = bytes.fromhex(rpc_result['encrypted'])
                logger.debug(f"✅ (Frida) 加密成功! 输出长度: {len(data_part)}字节")
            except ValueError as e:
                logger.error(f"❌ 加密结果格式错误: {e}")
                return None

        preamble = struct.pack('>h', -23206)
        header = struct.pack('>H', ((crypto_type & 0x7) << 13) | (len(data_part) & 0x1FFF))
        packet_without_crc = preamble + header + data_part
        crc_bytes = self.crc_calculator.create_checksum_part(self.crc_calculator.calculate(packet_without_crc))
        return packet_without_crc + crc_bytes

    def _create_command_data(self, command_id: int, *params) -> bytes:
        """创建Thor命令数据"""
        data = struct.pack('BB', 0, command_id)
        
        if params:
            for param in params:
                if isinstance(param, int):
                    data += struct.pack('B', param & 0xFF)
                elif isinstance(param, bytes):
                    data += param
                elif isinstance(param, str):
                    data += param.encode('utf-8')
        
        return data

    async def send_command_and_wait(self, command_name, command_id, params=None, crypto_type=1, timeout=10):
        """发送命令并等待响应"""
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
            logger.error(f"❌ 无法创建{command_name}命令包")
            return None

        logger.info(f"📤 发送{command_name}命令")
        logger.debug(f"   命令数据: {command_data.hex().upper()}")
        
        try:
            await self.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
            logger.info(f"✅ {command_name}命令已发送")
            
            # 等待响应
            for i in range(timeout):
                await asyncio.sleep(1)
                if self.current_response_complete:
                    logger.success(f"✅ 收到{command_name}响应!")
                    return self.current_command_response
                if i % 2 == 0:
                    logger.debug(f"⏳ 等待{command_name}响应... ({i + 1}/{timeout})")
                    
            logger.error(f"⏰ {command_name}响应超时")
            return None
            
        except Exception as e:
            logger.error(f"❌ 发送{command_name}命令失败: {e}")
            return None

    async def read_sound_list(self):
        """读取Thor设备音效列表"""
        logger.info("\n" + "🎵" + "="*50)
        logger.info("Thor音效列表读取器")
        logger.info("="*52)
        
        response = await self.send_command_and_wait("READ_SGU_SOUNDS", 36, crypto_type=1, timeout=8)
        if not response:
            logger.error("❌ 无法读取音效列表")
            return None
            
        return response

    def print_sound_list_summary(self, response):
        """打印音效列表摘要"""
        if not response or not response.get('success'):
            logger.error("❌ 音效列表读取失败")
            return
            
        logger.info("\n" + "📊" + "="*50)
        logger.info("音效列表分析结果")
        logger.info("="*52)
        
        sound_count = response.get('sound_count', 0)
        sound_list = response.get('sound_list', [])
        
        logger.info(f"🎵 总音效数量: {sound_count}")
        
        if sound_list:
            logger.info("🎵 音效详细列表:")
            for sound in sound_list:
                status_desc = "已安装" if sound['status'] == 1 else f"状态{sound['status']}"
                logger.info(f"   ID {sound['id']:3d}: {status_desc} (原值: 0x{sound['raw_value']:04X})")
        
        # 检查特定的测试音效ID
        test_ids = [10, 15, 20]  # 我们在测试中使用的ID
        logger.info("\n🔍 测试音效验证:")
        for test_id in test_ids:
            found = any(sound['id'] == test_id for sound in sound_list)
            status = "✅ 已找到" if found else "❌ 未找到"
            logger.info(f"   音效ID {test_id}: {status}")
        
        logger.info("="*52)

    async def disconnect(self):
        """断开连接"""
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            logger.info("🔌 已断开连接")


async def main():
    """主函数"""
    reader = ThorSoundListReader()
    
    # 1. 初始化Frida
    if not reader.setup_frida():
        return

    try:
        # 2. 连接蓝牙设备
        if not await reader.scan_and_connect():
            return

        await asyncio.sleep(2)

        # 3. 获取硬件信息
        logger.info("\n🔧 获取硬件信息...")
        packet = reader._create_packet(reader._create_command_data(1), 0)
        await reader.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
        for _ in range(5):
            if reader.hardware_info: 
                break
            await asyncio.sleep(1)
        if not reader.hardware_info:
            logger.error("❌ 获取硬件信息失败")
            return

        # 4. 执行握手
        logger.info("\n🤝 执行握手...")
        reader.client_iv = secrets.token_bytes(8)
        packet = reader._create_packet(reader.client_iv, 2)
        await reader.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
        for _ in range(5):
            if reader.handshake_complete: 
                break
            await asyncio.sleep(1)
        if not reader.handshake_complete:
            logger.error("❌ 握手失败")
            return

        # 5. 读取音效列表
        logger.info("\n🎵 读取Thor设备音效列表...")
        response = await reader.read_sound_list()
        
        # 6. 打印结果
        reader.print_sound_list_summary(response)
        
        # 7. 保存详细结果到文件
        if response and response.get('success'):
            timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
            result_file = f"thor_sound_list_{timestamp}.txt"
            
            with open(result_file, 'w', encoding='utf-8') as f:
                f.write("Thor音效列表读取结果\n")
                f.write("="*50 + "\n")
                f.write(f"读取时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
                f.write(f"总音效数量: {response.get('sound_count', 0)}\n\n")
                
                f.write("音效详细列表:\n")
                for sound in response.get('sound_list', []):
                    f.write(f"ID {sound['id']:3d}: 状态=0x{sound['status']:02X}, 原值=0x{sound['raw_value']:04X}\n")
                
                f.write(f"\n原始数据: {response.get('raw_data_hex', '')}\n")
            
            logger.info(f"📄 详细结果已保存到: {result_file}")

    except Exception as e:
        logger.error(f"❌ 程序执行期间发生错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await reader.disconnect()
        logger.info("🏁 任务完成")


if __name__ == "__main__":
    # 设置日志
    timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
    logger.add(f"thor_sound_list_reader_{timestamp}.log")
    
    asyncio.run(main())