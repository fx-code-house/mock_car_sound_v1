#!/usr/bin/env python3
"""
Thor并发音效上传器 - 基于Java项目的优化实现
采用预处理+队列+并发发送的架构，大幅提升上传速度
"""

import asyncio
import struct
import secrets
import frida
from bleak import BleakClient, BleakScanner
from loguru import logger
from datetime import datetime
from typing import Dict, List, Optional, Tuple
from enum import Enum
import time
from concurrent.futures import ThreadPoolExecutor
import threading

# --- 配置 ---
APP_PACKAGE_NAME = "com.carsystems.thor.app"
FRIDA_SCRIPT_PATH = "thor_rpc.js"

# --- 并发优化配置 ---
MAX_CONCURRENT_BLOCKS = 5  # 最大并发块数量
BLOCK_SEND_DELAY = 0.05    # 块发送间隔(秒)
RESPONSE_TIMEOUT = 3       # 响应超时(秒)
MAX_RETRIES = 3            # 最大重试次数

class SoundFileType(Enum):
    SGU = (3, 0)
    SMP2 = (4, 0)
    
    def __init__(self, type_id, version):
        self.type_id = type_id
        self.version = version

class BlockStatus(Enum):
    PENDING = "pending"
    SENDING = "sending"
    SUCCESS = "success"
    FAILED = "failed"
    RETRY = "retry"

class DataBlock:
    """数据块类"""
    def __init__(self, index: int, data: bytes, size: int):
        self.index = index
        self.data = data
        self.size = size
        self.status = BlockStatus.PENDING
        self.retry_count = 0
        self.send_time = None
        self.response_time = None

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


class ThorConcurrentUploader:
    """Thor并发音效上传器"""
    
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
        
        # 并发上传相关
        self.data_blocks: List[DataBlock] = []
        self.response_map: Dict[int, dict] = {}  # block_index -> response
        self.upload_stats = {
            'total_blocks': 0,
            'sent_blocks': 0,
            'success_blocks': 0,
            'failed_blocks': 0,
            'start_time': None,
            'end_time': None
        }
        self.upload_lock = threading.Lock()
        self.response_events: Dict[int, asyncio.Event] = {}  # block_index -> event

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
            logger.warning(f"❌ CRC校验失败!")
            return
        
        header = struct.unpack('>H', data[2:4])[0]
        encryption_type = (header >> 13) & 0x7
        data_part = data[4:-2]

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
            logger.error(f"❌ Frida RPC init失败")
            return
        logger.success("✅ Frida RPC init成功!")
        self.handshake_complete = True

    def parse_encrypted_response(self, encrypted_data):
        """解析加密响应"""
        if not self.frida_rpc: 
            return

        try:
            rpc_result = self.frida_rpc.jiemi(encrypted_data.hex())
        except Exception as e:
            logger.error(f"❌ RPC调用异常: {e}")
            return
            
        if not rpc_result.get('success'):
            return

        decrypted_hex = rpc_result.get('decrypted', '')
        if not decrypted_hex: 
            return

        try:
            decrypted_data = bytes.fromhex(decrypted_hex)
        except ValueError:
            return
            
        if len(decrypted_data) >= 3:
            padding_length = decrypted_data[0]
            separator = decrypted_data[1] 
            cmd = decrypted_data[2]
            
            # 处理块上传响应(命令114)
            if cmd == 114:
                self.handle_block_response(decrypted_data)

    def handle_block_response(self, decrypted_data):
        """处理块上传响应"""
        try:
            # 从响应数据中提取块索引(如果有的话)
            # 这里需要根据实际协议调整
            if len(decrypted_data) >= 5:
                # 假设响应包含块索引信息
                block_info = decrypted_data[3:5]
                # 解析并更新对应块的状态
                # 这里简化处理，实际需要根据协议格式解析
                logger.debug(f"📦 收到块响应: {block_info.hex()}")
                
                # 触发等待的协程
                for event in self.response_events.values():
                    if not event.is_set():
                        event.set()
                        break
        except Exception as e:
            logger.error(f"❌ 处理块响应失败: {e}")

    def prepare_data_blocks(self, file_data: bytes, block_size: int = 400) -> List[DataBlock]:
        """预处理数据块 - 模仿Java的takeDataBlock逻辑"""
        logger.info(f"📦 预处理数据块: 文件大小={len(file_data)}字节, 块大小={block_size}字节")
        
        blocks = []
        total_blocks = (len(file_data) + block_size - 1) // block_size
        
        for i in range(total_blocks):
            start_offset = i * block_size
            end_offset = min(start_offset + block_size, len(file_data))
            block_data = file_data[start_offset:end_offset]
            
            # 构造块参数 - 基于Frida数据格式
            block_params = [
                0,                          # 固定0
                i,                          # 块索引
                0,                          # 固定0
                (len(block_data) - 256) & 0xFF  # 数据长度标记
            ] + list(block_data)            # 实际数据
            
            block = DataBlock(i, bytes(block_params), len(block_data))
            blocks.append(block)
        
        logger.info(f"✅ 预处理完成: 共{len(blocks)}个数据块")
        return blocks

    def _create_packet(self, data_body, crypto_type):
        """创建BLE数据包"""
        data_part = data_body
        if crypto_type == 1:
            if not self.frida_rpc: 
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

            try:
                rpc_result = self.frida_rpc.jiami(pre_encrypt_data.hex())
            except Exception:
                return None
                
            if not rpc_result.get('success'):
                return None

            try:
                data_part = bytes.fromhex(rpc_result['encrypted'])
            except ValueError:
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
                elif isinstance(param, list):
                    for p in param:
                        if isinstance(p, int):
                            data += struct.pack('B', p & 0xFF)
        
        return data

    async def send_block_concurrent(self, block: DataBlock) -> bool:
        """并发发送单个数据块"""
        if not self.client or not self.client.is_connected:
            return False

        try:
            with self.upload_lock:
                block.status = BlockStatus.SENDING
                block.send_time = time.time()

            # 创建事件等待响应
            event = asyncio.Event()
            self.response_events[block.index] = event

            # 创建命令数据
            command_data = self._create_command_data(114, *block.data)
            packet = self._create_packet(command_data, 1)
            
            if not packet:
                block.status = BlockStatus.FAILED
                return False

            # 发送数据包
            await self.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
            logger.debug(f"📤 发送块 {block.index + 1} (大小: {block.size})")

            # 等待响应
            try:
                await asyncio.wait_for(event.wait(), timeout=RESPONSE_TIMEOUT)
                with self.upload_lock:
                    block.status = BlockStatus.SUCCESS
                    block.response_time = time.time()
                    self.upload_stats['success_blocks'] += 1
                logger.debug(f"✅ 块 {block.index + 1} 上传成功")
                return True
            except asyncio.TimeoutError:
                with self.upload_lock:
                    block.status = BlockStatus.FAILED
                    self.upload_stats['failed_blocks'] += 1
                logger.warning(f"⏰ 块 {block.index + 1} 响应超时")
                return False

        except Exception as e:
            with self.upload_lock:
                block.status = BlockStatus.FAILED
                self.upload_stats['failed_blocks'] += 1
            logger.error(f"❌ 块 {block.index + 1} 发送失败: {e}")
            return False
        finally:
            # 清理事件
            if block.index in self.response_events:
                del self.response_events[block.index]

    async def upload_blocks_concurrent(self, blocks: List[DataBlock]) -> bool:
        """并发上传所有数据块"""
        logger.info(f"🚀 开始并发上传 {len(blocks)} 个数据块...")
        
        self.upload_stats['total_blocks'] = len(blocks)
        self.upload_stats['start_time'] = time.time()
        
        # 使用信号量控制并发数量
        semaphore = asyncio.Semaphore(MAX_CONCURRENT_BLOCKS)
        
        async def upload_with_semaphore(block):
            async with semaphore:
                # 添加小延迟避免设备过载
                await asyncio.sleep(BLOCK_SEND_DELAY)
                return await self.send_block_concurrent(block)
        
        # 创建所有上传任务
        tasks = [upload_with_semaphore(block) for block in blocks]
        
        # 执行并发上传
        results = await asyncio.gather(*tasks, return_exceptions=True)
        
        self.upload_stats['end_time'] = time.time()
        
        # 统计结果
        success_count = sum(1 for r in results if r is True)
        failed_count = len(results) - success_count
        
        total_time = self.upload_stats['end_time'] - self.upload_stats['start_time']
        speed = len(blocks) / total_time if total_time > 0 else 0
        
        logger.info(f"📊 并发上传完成统计:")
        logger.info(f"   ✅ 成功: {success_count} 块")
        logger.info(f"   ❌ 失败: {failed_count} 块")
        logger.info(f"   ⏱️  总时间: {total_time:.2f}秒")
        logger.info(f"   🚀 平均速度: {speed:.1f} 块/秒")
        
        return success_count == len(blocks)

    async def upload_sound_file_concurrent(self, file_data: bytes, sound_id: int = 15) -> bool:
        """并发模式音效上传主函数"""
        try:
            logger.info("\n" + "🎵" + "="*50)
            logger.info("Thor并发音效上传器")
            logger.info("="*52)
            
            # 1. 读取音效列表
            logger.info("📋 读取现有音效列表...")
            response = await self.send_simple_command("READ_SGU_SOUNDS", 36)
            if not response:
                logger.error("❌ 无法读取音效列表")
                return False
            
            # 2. 开始组下载
            logger.info("🔗 开始组下载...")
            response = await self.send_simple_command("DOWNLOAD_START_GROUP", 112, [0, 3])
            if not response:
                return False
            
            # 3. 开始文件下载
            logger.info("📁 开始文件下载...")
            file_size = len(file_data)
            file_params = [
                4,                    # 文件类型
                0,                    # 固定0
                (file_size - 141) & 0xFF, 
                2,                    # 固定2
                0,                    # 固定0
                sound_id,             # 音效ID
                0xFE,                 # 固定-2
                0xFC                  # 固定-4
            ]
            response = await self.send_simple_command("DOWNLOAD_START_FILE", 113, file_params)
            if not response:
                return False
            
            # 4. 预处理数据块
            blocks = self.prepare_data_blocks(file_data, block_size=400)  # 使用400字节块
            
            # 5. 并发上传数据块
            success = await self.upload_blocks_concurrent(blocks)
            if not success:
                logger.error("❌ 数据块上传失败")
                return False
            
            # 6. 提交文件
            logger.info("📋 提交文件...")
            response = await self.send_simple_command("DOWNLOAD_COMMIT_FILE", 115)
            if not response:
                return False
            
            # 7. 提交组
            logger.info("🎯 提交组...")
            response = await self.send_simple_command("DOWNLOAD_COMMIT_GROUP", 116)
            if not response:
                return False
            
            # 8. 获取状态
            logger.info("📊 获取上传状态...")
            await self.send_simple_command("DOWNLOAD_GET_STATUS", 117)
            
            logger.success(f"🎉 并发音效上传成功! 音效ID: {sound_id}")
            return True
            
        except Exception as e:
            logger.error(f"❌ 并发音效上传失败: {e}")
            return False

    async def send_simple_command(self, command_name, command_id, params=None, timeout=5):
        """发送简单命令(非数据块命令)"""
        if not self.client or not self.client.is_connected:
            return None

        # 创建命令数据
        if params is None:
            command_data = self._create_command_data(command_id)
        else:
            command_data = self._create_command_data(command_id, *params)

        packet = self._create_packet(command_data, 1)
        if not packet:
            return None

        logger.debug(f"📤 发送{command_name}命令")
        
        try:
            await self.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
            
            # 简单等待响应
            await asyncio.sleep(1)  # 基本等待时间
            
            return {'success': True}
            
        except Exception as e:
            logger.error(f"❌ 发送{command_name}命令失败: {e}")
            return None

    async def disconnect(self):
        """断开连接"""
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            logger.info("🔌 已断开连接")


async def main():
    """主函数"""
    uploader = ThorConcurrentUploader()
    
    # 1. 初始化Frida
    if not uploader.setup_frida():
        return

    try:
        # 2. 连接蓝牙设备
        if not await uploader.scan_and_connect():
            return

        await asyncio.sleep(2)

        # 3. 获取硬件信息
        logger.info("\n🔧 获取硬件信息...")
        packet = uploader._create_packet(uploader._create_command_data(1), 0)
        await uploader.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
        for _ in range(5):
            if uploader.hardware_info: 
                break
            await asyncio.sleep(1)
        if not uploader.hardware_info:
            logger.error("❌ 获取硬件信息失败")
            return

        # 4. 执行握手
        logger.info("\n🤝 执行握手...")
        uploader.client_iv = secrets.token_bytes(8)
        packet = uploader._create_packet(uploader.client_iv, 2)
        await uploader.client.write_gatt_char("6e400002-b5a3-f393-e0a9-e50e24dcca9e", packet)
        for _ in range(5):
            if uploader.handshake_complete: 
                break
            await asyncio.sleep(1)
        if not uploader.handshake_complete:
            logger.error("❌ 握手失败")
            return

        # 5. 测试并发上传
        logger.info("\n🎵 开始并发音效上传测试...")
        
        # 创建测试SMP2数据 (1MB)
        test_smp2_data = b'SMP2' + b'\x00' * 4 + bytes([i % 256 for i in range(1024*1024)])
        
        # 并发上传
        success = await uploader.upload_sound_file_concurrent(test_smp2_data, sound_id=25)
        
        if success:
            logger.success("🎉 并发上传测试成功!")
        else:
            logger.error("❌ 并发上传测试失败")

    except Exception as e:
        logger.error(f"❌ 程序执行期间发生错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await uploader.disconnect()
        logger.info("🏁 任务完成")


if __name__ == "__main__":
    # 设置日志
    timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
    logger.add(f"thor_concurrent_uploader_{timestamp}.log")
    
    asyncio.run(main())