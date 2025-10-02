#!/usr/bin/env python3
"""
简化的Thor音效列表客户端
功能：握手 + 读取音效列表 + API加密调用
删除：播放音效、复杂解析、其他非必要逻辑
"""

import asyncio
import struct
import secrets
import requests
from bleak import BleakClient, BleakScanner

# Thor BLE UUIDs
SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"

# Constants
PREAMBLE = -23206
API_BASE_URL = "http://localhost:8000"


def take_short(first_byte, second_byte):
    """模拟Java的BleHelper.takeShort方法"""
    return struct.unpack('>h', bytes([first_byte, second_byte]))[0]


def parse_sound_list(decrypted_data):
    """简化的音效列表解析"""
    print(f"\n🎵 解析音效列表数据 (长度: {len(decrypted_data)})")
    
    if len(decrypted_data) < 4:
        return []

    # 解析音效数量
    sounds_amount = take_short(decrypted_data[2], decrypted_data[3])
    print(f"🔢 音效数量: {sounds_amount}")

    # 解析音效ID和状态
    sound_list = []
    i = 4  # 从第5个字节开始
    
    while i < len(decrypted_data) - 1 and len(sound_list) < sounds_amount:
        if decrypted_data[i] == 0xA5:  # 填充区域
            break
            
        sound_id = decrypted_data[i]
        sound_status = decrypted_data[i + 1]
        status_text = "启用" if sound_status == 1 else "禁用"
        
        sound_list.append({
            'id': sound_id,
            'status': sound_status,
            'enabled': sound_status == 1
        })
        
        print(f"🎵 音效ID: {sound_id} - {status_text}")
        i += 2

    enabled_count = sum(1 for s in sound_list if s['enabled'])
    print(f"✅ 解析完成: 总数{len(sound_list)}, 启用{enabled_count}")
    
    return sound_list


class ThorCRC16:
    """Thor CRC16计算"""
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
            byte_val = byte & 0xFF if isinstance(byte, int) else ord(byte) & 0xFF
            tbl_idx = (crc ^ byte_val) & 0xFF
            crc = ((crc >> 8) ^ self.crc_table[tbl_idx]) & 0xFFFF
        return crc & 0xFFFF

    def create_checksum_part(self, crc_value):
        low_byte = crc_value & 0xFF
        high_byte = (crc_value >> 8) & 0xFF
        return bytes([low_byte, high_byte])


class ThorAPIClient:
    """Thor API加密客户端"""
    def __init__(self):
        self.initialized = False

    def init_crypto(self, iv_hex, hardware_version, firmware_version, serial_number):
        """初始化加密器"""
        try:
            print(f"🔄 初始化API加密器...")
            response = requests.post(f"{API_BASE_URL}/init", json={
                'iv': iv_hex,
                'hw': hardware_version,
                'fw': firmware_version,
                'sn': serial_number
            }, timeout=5)

            result = response.json()
            if result.get('success'):
                self.initialized = True
                print("✅ API加密初始化成功")
                return True
            else:
                print(f"❌ API初始化失败: {result.get('error')}")
                return False
        except Exception as e:
            print(f"❌ API调用失败: {e}")
            return False

    def encrypt(self, data_hex):
        """加密数据"""
        if not self.initialized:
            print("❌ 加密器未初始化")
            return None

        try:
            response = requests.post(f"{API_BASE_URL}/encrypt", json={
                'hex': data_hex
            }, timeout=5)

            result = response.json()
            if result.get('success'):
                return bytes.fromhex(result['encrypted'])
            else:
                print(f"❌ API加密失败: {result.get('error')}")
                return None
        except Exception as e:
            print(f"❌ API调用失败: {e}")
            return None

    def decrypt(self, ciphertext):
        """解密数据 (CTR模式下加密=解密)"""
        return self.encrypt(ciphertext.hex().upper())


class SimpleThorClient:
    """简化的Thor客户端"""
    def __init__(self):
        self.client = None
        self.crc_calculator = ThorCRC16()
        self.response_data = bytearray()
        
        # 设备信息
        self.hardware_info = None
        
        # 握手状态
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False
        
        # 加密器
        self.crypto = ThorAPIClient()
        
        # 音效列表
        self.sound_list = None
        self.sound_list_complete = False

    async def scan_and_connect(self, device_name="Thor"):
        """扫描并连接设备"""
        print(f"🔍 扫描 {device_name} 设备...")

        devices = await BleakScanner.discover(timeout=10.0)
        for device in devices:
            if device.name and device_name.lower() in device.name.lower():
                print(f"✅ 找到设备: {device.name} ({device.address})")
                
                self.client = BleakClient(device.address)
                await self.client.connect()
                print("🔗 连接成功!")

                await self.client.start_notify(NOTIFY_UUID, self.notification_handler)
                print("🔔 通知已启用")
                return True

        print(f"❌ 未找到 {device_name} 设备")
        return False

    def notification_handler(self, sender, data):
        """处理BLE通知"""
        print(f"📨 收到数据: {data.hex().upper()}")
        self.response_data.extend(data)
        self.try_parse_response()

    def try_parse_response(self):
        """尝试解析响应"""
        if len(self.response_data) < 6:
            return

        # 查找PREAMBLE
        for i in range(len(self.response_data) - 1):
            if (self.response_data[i] == 0xA5 and self.response_data[i + 1] == 0x5A):
                packet_data = self.response_data[i:]
                if len(packet_data) >= 6:
                    success = self.parse_packet(packet_data)
                    if success:
                        self.response_data.clear()
                break

    def parse_packet(self, data):
        """解析数据包"""
        try:
            if len(data) < 6:
                return False

            # 解析包头
            preamble = struct.unpack('>H', data[0:2])[0]
            if preamble != 0xA55A:
                return False

            encryption_header = struct.unpack('>H', data[2:4])[0]
            encryption_type = (encryption_header >> 13) & 0x7
            data_length = encryption_header & 0x1FFF

            total_length = 4 + data_length + 2
            if len(data) < total_length:
                return False

            data_part = data[4:4 + data_length]
            crc_bytes = data[4 + data_length:4 + data_length + 2]

            # 验证CRC
            crc_received = struct.unpack('<H', crc_bytes)[0]
            crc_calculated = self.crc_calculator.calculate(data[:4 + data_length])

            if crc_received != crc_calculated:
                crc_received = struct.unpack('>H', crc_bytes)[0]
                if crc_received != crc_calculated:
                    print("❌ CRC验证失败!")
                    return False

            print("✅ CRC验证成功!")

            # 根据加密类型处理数据
            if encryption_type == 0:  # 硬件响应
                self.parse_hardware_response(data_part)
            elif encryption_type == 2:  # 握手响应
                self.parse_handshake_response(data_part)
            elif encryption_type == 1:  # 加密数据
                self.parse_encrypted_response(data_part)

            return True

        except Exception as e:
            print(f"❌ 解析包失败: {e}")
            return False

    def parse_hardware_response(self, data):
        """解析硬件响应"""
        if len(data) < 8:
            return

        command = struct.unpack('>H', data[0:2])[0]
        hardware_version = struct.unpack('>H', data[2:4])[0]
        firmware_version = struct.unpack('>H', data[4:6])[0]
        serial_number = struct.unpack('>H', data[6:8])[0]

        self.hardware_info = {
            'hardware_version': hardware_version,
            'firmware_version': firmware_version,
            'serial_number': serial_number
        }

        print("🎉 硬件信息解析成功!")
        print(f"🔧 硬件版本: {hardware_version}")
        print(f"💾 固件版本: {firmware_version}")
        print(f"🔢 序列号: {serial_number}")

    def parse_handshake_response(self, data):
        """解析握手响应"""
        if len(data) < 8:
            return

        self.device_iv = bytes(data[:8])
        print(f"🔑 设备IV: {self.device_iv.hex().upper()}")

        if self.client_iv:
            self.combined_iv = self.client_iv + self.device_iv
            print(f"🔐 合并IV: {self.combined_iv.hex().upper()}")

            # 🔥 使用API初始化加密器
            if self.crypto.init_crypto(
                self.combined_iv.hex().upper(),
                self.hardware_info['hardware_version'],
                self.hardware_info['firmware_version'],
                self.hardware_info['serial_number']
            ):
                self.handshake_complete = True
                print("✅ 握手完成！")

    def parse_encrypted_response(self, encrypted_data):
        """解析加密响应"""
        print(f"\n🔓 解析加密响应 (长度: {len(encrypted_data)})")
        
        if not self.crypto.initialized:
            print("❌ 加密器未初始化")
            return

        # 🔥 使用API解密
        decrypted_raw = self.crypto.decrypt(encrypted_data)
        if not decrypted_raw:
            print("❌ 解密失败")
            return

        print(f"🔓 原始解密数据: {decrypted_raw.hex().upper()}")

        # 处理解密数据格式
        if len(decrypted_raw) > 0:
            padding_length = decrypted_raw[0]
            start_index = 1
            end_index = len(decrypted_raw) - padding_length

            if end_index > start_index:
                decrypted_data = decrypted_raw[start_index:end_index]
                print(f"✅ 最终解密数据: {decrypted_data.hex().upper()}")

                # 解析音效列表
                self.sound_list = parse_sound_list(decrypted_data)
                if self.sound_list:
                    self.sound_list_complete = True
                    print(f"🎊 音效列表解析完成! 总数: {len(self.sound_list)}")

    def create_hardware_request(self):
        """创建硬件请求"""
        return bytes([165, 90, 0, 2, 0, 1, 32, 227])

    def create_handshake_request(self, client_iv):
        """创建握手请求"""
        preamble_bytes = struct.pack('>h', PREAMBLE)
        data_body = client_iv

        crypto_type = 2
        data_length = len(data_body)
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)

        packet_without_crc = preamble_bytes + encryption_header_bytes + data_body
        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        return packet_without_crc + crc_bytes

    def create_sound_list_request(self):
        """创建读取音效列表请求"""
        if not self.crypto.initialized:
            return None

        # 🔥 修复：ReadSguSoundsBleRequest只有命令，没有body
        # Java: getCommandPart() + getBody() 其中getBody()返回空数组
        command_bytes = struct.pack('>H', 36)  # 命令36
        # ReadSguSoundsBleRequest.getBody()返回空数组，所以只有命令字节
        
        # 构建加密前数据 - 按照Java BaseBleRequest.generateData逻辑
        # bArrPlus = getCommandPart() + getBody() = [0, 36] + [] = [0, 36]
        # padByteCount = getPadByteCount(2 + 1) = 16 - (3 % 16) = 13
        command_and_body = command_bytes  # 只有命令，没有body
        padding_needed = 16 - ((len(command_and_body) + 1) % 16)
        if padding_needed == 16:
            padding_needed = 0
            
        # Java加密前数据格式：[padding_length] + command_and_body + padding
        padding = bytes([0xA5] * padding_needed)
        pre_encrypt_data = bytes([padding_needed]) + command_and_body + padding

        print(f"🔧 修复请求构建:")
        print(f"   命令字节: {command_and_body.hex().upper()}")
        print(f"   填充长度: {padding_needed}")
        print(f"   加密前数据: {pre_encrypt_data.hex().upper()}")

        # 🔥 使用API加密
        encrypted_data = self.crypto.encrypt(pre_encrypt_data.hex().upper())
        if not encrypted_data:
            return None

        # 构建BLE包
        preamble_bytes = struct.pack('>h', PREAMBLE)
        crypto_type = 1
        data_length = len(encrypted_data)
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)

        packet_without_crc = preamble_bytes + encryption_header_bytes + encrypted_data
        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        return packet_without_crc + crc_bytes

    async def send_hardware_request(self):
        """发送硬件请求"""
        print("🚀 发送硬件请求...")
        request = self.create_hardware_request()
        
        await self.client.write_gatt_char(WRITE_UUID, request)
        print(f"📤 硬件请求: {request.hex().upper()}")

        # 等待响应
        for i in range(10):
            await asyncio.sleep(1)
            if self.hardware_info:
                return True
        return False

    async def send_handshake_request(self):
        """发送握手请求"""
        print("🚀 发送握手请求...")
        
        self.client_iv = secrets.token_bytes(8)
        print(f"🔑 客户端IV: {self.client_iv.hex().upper()}")
        
        request = self.create_handshake_request(self.client_iv)
        await self.client.write_gatt_char(WRITE_UUID, request)
        print(f"📤 握手请求: {request.hex().upper()}")

        # 等待响应
        for i in range(10):
            await asyncio.sleep(1)
            if self.handshake_complete:
                return True
        return False

    async def send_sound_list_request(self):
        """发送音效列表请求"""
        print("🚀 发送音效列表请求...")
        
        request = self.create_sound_list_request()
        if not request:
            return False

        await self.client.write_gatt_char(WRITE_UUID, request)
        print(f"📤 音效列表请求: {request.hex().upper()}")

        # 等待响应
        for i in range(15):
            await asyncio.sleep(1)
            if self.sound_list_complete:
                return True
            print(f"⏳ 等待响应... ({i+1}/15)")
        return False

    async def disconnect(self):
        """断开连接"""
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            print("🔌 已断开连接")


async def main():
    """主程序：握手 + 读取音效列表"""
    client = SimpleThorClient()

    try:
        # 1. 连接设备
        if not await client.scan_and_connect():
            return

        await asyncio.sleep(2)

        # 2. 获取硬件信息
        if not await client.send_hardware_request():
            print("❌ 硬件信息获取失败")
            return

        # 3. 执行握手
        if not await client.send_handshake_request():
            print("❌ 握手失败")
            return

        # 4. 读取音效列表
        if not await client.send_sound_list_request():
            print("❌ 音效列表读取失败")
            return

        # 5. 输出结果
        print("\n" + "=" * 60)
        print("🎊 Thor音效列表获取成功!")
        print("=" * 60)
        print(f"🔧 硬件版本: {client.hardware_info['hardware_version']}")
        print(f"💾 固件版本: {client.hardware_info['firmware_version']}")
        print(f"🔢 序列号: {client.hardware_info['serial_number']}")
        print(f"🎵 音效总数: {len(client.sound_list)}")
        
        enabled_sounds = [s for s in client.sound_list if s['enabled']]
        print(f"✅ 启用音效: {len(enabled_sounds)}")
        print(f"🎵 启用音效ID: {[s['id'] for s in enabled_sounds]}")
        print("=" * 60)

    except Exception as e:
        print(f"❌ 程序错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await client.disconnect()


if __name__ == "__main__":
    asyncio.run(main()) 