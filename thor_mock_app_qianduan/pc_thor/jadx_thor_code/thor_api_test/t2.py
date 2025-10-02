#!/usr/bin/env python3
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

# API配置
API_BASE_URL = "http://localhost:8000"


class ThorCRC16:
    """Thor自定义CRC16实现"""

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
    """使用API调用的Thor加密客户端"""

    def __init__(self):
        self.initialized = False

    def init_crypto(self, iv_hex, hardware_version, firmware_version, serial_number):
        """初始化加密器"""
        try:
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
                print(f"❌ API加密初始化失败: {result.get('error', 'Unknown error')}")
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
                print(f"❌ API加密失败: {result.get('error', 'Unknown error')}")
                return None
        except Exception as e:
            print(f"❌ API调用失败: {e}")
            return None

    def decrypt(self, ciphertext):
        """解密数据 (CTR模式下加密=解密)"""
        return self.encrypt(ciphertext.hex().upper())


class ThorClient:
    def __init__(self):
        self.client = None
        self.crc_calculator = ThorCRC16()
        self.response_data = bytearray()
        self.hardware_info = None

        # 握手相关状态
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False

        # 加密相关
        self.crypto = ThorAPIClient()
        
        # 音效列表相关
        self.sound_list = None
        self.sgu_sounds_complete = False

    async def scan_and_connect(self, device_name="Thor"):
        """扫描并连接到Thor设备"""
        print(f"🔍 扫描 {device_name} 设备...")

        devices = await BleakScanner.discover(timeout=10.0)
        thor_device = None

        for device in devices:
            if device.name and device_name.lower() in device.name.lower():
                thor_device = device
                break

        if not thor_device:
            print(f"❌ 未找到 {device_name} 设备")
            return False

        print(f"✅ 找到设备: {thor_device.name} ({thor_device.address})")

        self.client = BleakClient(thor_device.address)
        await self.client.connect()
        print("🔗 连接成功!")

        await self.client.start_notify(NOTIFY_UUID, self.notification_handler)
        print("🔔 通知已启用")
        return True

    def notification_handler(self, sender, data):
        """处理BLE通知"""
        print(f"📨 收到数据: {data.hex().upper()}")
        self.response_data.extend(data)
        self.try_parse_complete_response()

    def try_parse_complete_response(self):
        """尝试解析完整的响应包"""
        if len(self.response_data) < 6:
            return

        # 查找PREAMBLE
        preamble_found = False
        start_index = 0

        for i in range(len(self.response_data) - 1):
            if (self.response_data[i] == 0xA5 and self.response_data[i + 1] == 0x5A):
                preamble_found = True
                start_index = i
                break

        if not preamble_found:
            return

        packet_data = self.response_data[start_index:]
        if len(packet_data) < 6:
            return

        success = self.parse_response_packet(packet_data)
        if success:
            self.response_data.clear()

    def parse_response_packet(self, data):
        """解析响应包"""
        try:
            if len(data) < 6:
                return False

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

            if len(crc_bytes) < 2:
                return False

            # 验证CRC
            crc_received = struct.unpack('<H', crc_bytes)[0]
            crc_data = data[:4 + data_length]
            crc_calculated = self.crc_calculator.calculate(crc_data)

            if crc_received != crc_calculated:
                crc_received = struct.unpack('>H', crc_bytes)[0]
                if crc_received != crc_calculated:
                    print("❌ CRC验证失败!")
                    return False

            print("✅ CRC验证成功!")

            # 根据加密类型处理数据
            if encryption_type == 0:  # 无加密 - 硬件响应
                self.parse_hardware_response(data_part)
            elif encryption_type == 2:  # 握手响应
                self.parse_handshake_response(data_part)
            elif encryption_type == 1:  # 加密数据
                self.parse_encrypted_response(data_part)

            return True

        except Exception as e:
            print(f"❌ 解析响应包失败: {e}")
            return False

    def parse_hardware_response(self, data):
        """解析硬件响应数据"""
        try:
            if len(data) < 8:
                return

            command = struct.unpack('>H', data[0:2])[0]
            hardware_version = struct.unpack('>H', data[2:4])[0]
            firmware_version = struct.unpack('>H', data[4:6])[0]
            serial_number = struct.unpack('>H', data[6:8])[0]

            self.hardware_info = {
                'command': command,
                'hardware_version': hardware_version,
                'firmware_version': firmware_version,
                'serial_number': serial_number
            }

            print("🎉 硬件信息解析成功!")
            print(f"🔧 硬件版本: {hardware_version}")
            print(f"💾 固件版本: {firmware_version}")
            print(f"🔢 序列号: {serial_number}")

        except Exception as e:
            print(f"❌ 解析硬件响应失败: {e}")

    def parse_handshake_response(self, data):
        """解析握手响应数据"""
        try:
            if len(data) < 8:
                return

            self.device_iv = bytes(data[:8])
            print(f"🔑 设备IV: {self.device_iv.hex().upper()}")

            if self.client_iv:
                self.combined_iv = self.client_iv + self.device_iv
                print(f"🔐 合并IV: {self.combined_iv.hex().upper()}")

                # 🔥 关键：使用API初始化加密器
                if self.crypto.init_crypto(
                        self.combined_iv.hex().upper(),
                        self.hardware_info['hardware_version'],
                        self.hardware_info['firmware_version'],
                        self.hardware_info['serial_number']
                ):
                    self.handshake_complete = True
                    print("✅ 握手完成！")
                else:
                    print("❌ 加密器初始化失败")

        except Exception as e:
            print(f"❌ 解析握手响应失败: {e}")

    def parse_encrypted_response(self, encrypted_data):
        """解析加密响应数据"""
        try:
            print(f"\n🔓 正在解析加密响应数据...")
            print(f"📦 加密数据: {encrypted_data.hex().upper()}")

            if not self.crypto.initialized:
                print("❌ 加密器未初始化")
                return

            # 🔥 使用API解密数据
            decrypted_raw = self.crypto.decrypt(encrypted_data)
            if not decrypted_raw:
                print("❌ 解密失败")
                return

            print(f"🔓 原始解密数据: {decrypted_raw.hex().upper()}")

            if len(decrypted_raw) > 0:
                padding_length = decrypted_raw[0]
                start_index = 1
                end_index = len(decrypted_raw) - padding_length

                print(f"📐 填充长度: {padding_length}")
                print(f"📊 数据范围: 字节{start_index}到{end_index - 1}")

                if end_index > start_index:
                    decrypted_data = decrypted_raw[start_index:end_index]
                    print(f"✅ 最终解密数据: {decrypted_data.hex().upper()}")
                    print(f"📋 解密数据字节: {list(decrypted_data)}")

                    # 尝试解析命令响应
                    if len(decrypted_data) >= 4:
                        # 假设前两个字节是命令码
                        command = struct.unpack('>H', decrypted_data[0:2])[0]
                        print(f"📋 响应命令码: {command}")

                        # 识别常见命令
                        command_names = {
                            36: "READ_SGU_SOUNDS (读取音效列表)",
                            34: "PLAY_SGU_SOUND (播放音效)",
                            58: "READ_SETTINGS (读取设置)"
                        }

                        if command in command_names:
                            print(f"🎯 命令类型: {command_names[command]}")
                            
                            # 🔥 专门处理音效列表响应
                            if command == 36:  # READ_SGU_SOUNDS
                                self.parse_sound_list_response(decrypted_data)
                        else:
                            print(f"❓ 未知命令: {command}")

                        if len(decrypted_data) > 2:
                            print(f"📄 响应数据: {decrypted_data[2:].hex().upper()}")
                else:
                    print("❌ 解密数据格式错误")

        except Exception as e:
            print(f"❌ 解析加密响应失败: {e}")
            import traceback
            traceback.print_exc()

    def parse_sound_list_response(self, decrypted_data):
        """解析音效列表响应 - 按照Java ReadSguSoundsResponse逻辑"""
        try:
            print("\n🎵 解析音效列表响应...")
            
            if len(decrypted_data) < 4:
                print("❌ 响应数据太短")
                return
                
            # Java: short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
            sounds_amount = struct.unpack('>h', decrypted_data[2:4])[0]
            print(f"🔢 音效数量: {sounds_amount}")
            
            if sounds_amount <= 0:
                print("📊 设备中没有音效")
                self.sound_list = []
                self.sgu_sounds_complete = True
                return
                
            # 解析音效ID列表
            sound_ids = []
            i = 4  # 从第5个字节开始
            
            for sound_index in range(sounds_amount):
                if i + 1 < len(decrypted_data):
                    # Java: this.soundIds.add(Short.valueOf(BleHelper.takeShort(bytes[i], bytes[i + 1])));
                    sound_id = struct.unpack('>h', decrypted_data[i:i+2])[0]
                    sound_ids.append(sound_id)
                    
                    # 分析音效ID的含义（第一个字节是音效ID，第二个字节是状态）
                    actual_id = decrypted_data[i]
                    status = decrypted_data[i + 1]
                    status_text = "启用" if status == 1 else "禁用"
                    
                    print(f"🎵 #{sound_index+1}: 组合ID={sound_id} | 音效ID={actual_id} {status_text}")
                    
                    i += 2
                else:
                    break
            
            self.sound_list = sound_ids
            self.sgu_sounds_complete = True
            
            enabled_count = sum(1 for sid in sound_ids if (sid & 0xFF00) >> 8 == 1)
            print(f"✅ 解析完成: 总数{len(sound_ids)}, 启用{enabled_count}")
            print(f"🎵 音效ID列表: {sound_ids}")
            
        except Exception as e:
            print(f"❌ 解析音效列表失败: {e}")
            import traceback
            traceback.print_exc()

    def generate_client_iv(self):
        """生成8字节安全随机IV"""
        client_iv = secrets.token_bytes(8)
        print(f"🔑 生成客户端IV: {client_iv.hex().upper()}")
        return client_iv

    def create_hardware_request(self):
        """创建硬件请求"""
        verified_packet = [165, 90, 0, 2, 0, 1, 32, 227]
        return bytes(verified_packet)

    def create_handshake_request(self, client_iv):
        """创建握手请求包"""
        preamble_bytes = struct.pack('>h', PREAMBLE)
        data_body = client_iv

        crypto_type = 2  # HANDSHAKE
        data_length = len(data_body)
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)

        packet_without_crc = preamble_bytes + encryption_header_bytes + data_body

        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        complete_packet = packet_without_crc + crc_bytes
        return complete_packet

    def create_encrypted_request(self, command, data_body=b''):
        """创建加密请求"""
        if not self.crypto.initialized:
            print("❌ 加密器未初始化")
            return None

        # 🔥 修复：按照Java BaseBleRequest.generateData逻辑
        command_bytes = struct.pack('>H', command)
        command_and_body = command_bytes + data_body  # getCommandPart() + getBody()

        # Java: padByteCount = getPadByteCount(bArrPlus.length + 1)
        # getPadByteCount(messageLength) { return 16 - (messageLength % 16); }
        padding_needed = 16 - ((len(command_and_body) + 1) % 16)
        if padding_needed == 16:
            padding_needed = 0
            
        padding = bytes([0xA5] * padding_needed)
        
        # Java加密前数据格式：[padding_length] + command_and_body + padding
        pre_encrypt_data = bytes([padding_needed]) + command_and_body + padding

        print(f"🔧 创建加密请求 (命令{command}): 填充长度{padding_needed}")

        # 🔥 使用API加密数据
        encrypted_data = self.crypto.encrypt(pre_encrypt_data.hex().upper())
        if not encrypted_data:
            return None

        # 构建完整的BLE包
        preamble_bytes = struct.pack('>h', PREAMBLE)
        crypto_type = 1  # ENCRYPTED
        data_length = len(encrypted_data)
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)

        packet_without_crc = preamble_bytes + encryption_header_bytes + encrypted_data
        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        complete_packet = packet_without_crc + crc_bytes
        return complete_packet

    async def send_hardware_request(self):
        """发送硬件请求并等待响应"""
        if not self.client or not self.client.is_connected:
            return False

        print("🚀 开始硬件信息读取...")
        self.response_data.clear()
        self.hardware_info = None

        request = self.create_hardware_request()
        print(f"📤 发送硬件请求: {request.hex().upper()}")

        try:
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ 硬件请求已发送")

            # 等待响应
            for i in range(10):
                await asyncio.sleep(1)
                if self.hardware_info:
                    return True
                print(f"⏳ 等待中... ({i + 1}/10)")

            print("⏰ 硬件请求响应超时")
            return False

        except Exception as e:
            print(f"❌ 发送硬件请求失败: {e}")
            return False

    async def send_handshake_request(self):
        """发送握手请求并等待响应"""
        if not self.client or not self.client.is_connected:
            return False

        print("🚀 开始握手流程...")
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False
        self.response_data.clear()

        self.client_iv = self.generate_client_iv()
        request = self.create_handshake_request(self.client_iv)
        print(f"📤 发送握手请求: {request.hex().upper()}")

        try:
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ 握手请求已发送")

            # 等待握手响应
            for i in range(10):
                await asyncio.sleep(1)
                if self.handshake_complete:
                    return True
                print(f"⏳ 等待中... ({i + 1}/10)")

            print("⏰ 握手超时")
            return False

        except Exception as e:
            print(f"❌ 发送握手请求失败: {e}")
            return False

    async def send_command(self, command, data_body=b''):
        """发送加密命令"""
        if not self.handshake_complete:
            print("❌ 握手未完成")
            return False

        request = self.create_encrypted_request(command, data_body)
        if not request:
            return False

        print(f"📤 发送命令 {command}: {request.hex().upper()}")

        try:
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ 命令已发送")
            return True

        except Exception as e:
            print(f"❌ 发送命令失败: {e}")
            return False

    async def disconnect(self):
        """断开连接"""
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            print("🔌 已断开连接")


async def main():
    client = ThorClient()

    try:
        # 连接设备
        if not await client.scan_and_connect():
            return

        print("⏳ 等待连接稳定...")
        await asyncio.sleep(2)

        # 发送硬件请求
        if not await client.send_hardware_request():
            print("❌ 硬件信息获取失败")
            return

        print("🎉 成功获取硬件信息!")

        # 执行握手流程
        if not await client.send_handshake_request():
            print("❌ 握手失败")
            return

        print("🎊 握手流程成功完成!")



        # 发送读取SGU音效列表命令 (命令36)
        print("🚀 发送读取SGU音效列表命令...")
        
        # 清空响应缓冲区并发送命令
        client.response_data.clear()
        client.sgu_sounds_complete = False
        await client.send_command(36)  # READ_SGU_SOUNDS

        # 等待响应
        print("⏳ 等待SGU音效列表响应...")
        for i in range(15):  # 15秒
            await asyncio.sleep(1)
            
            if client.sgu_sounds_complete:
                print("🎉 音效列表响应解析成功!")
                break
                
            if i % 5 == 0:  # 每5秒打印一次
                print(f"⏳ 等待中... ({i+1}/15)")
        
        if not client.sgu_sounds_complete:
            print("⏰ 15秒超时，未收到音效列表响应")

        # 发送播放音效命令 (命令34)
        print("🚀 发送播放音效命令...")
        sound_id_bytes = struct.pack('>H', 50)  # 音效ID 50
        repeat_bytes = struct.pack('>H', 1)  # 重复1次
        engine_mute_bytes = struct.pack('>H', 50)  # 引擎静音
        volume_bytes = struct.pack('>H', 80)  # 音量
        extra_bytes = struct.pack('>H', 0)  # 额外参数

        play_data = sound_id_bytes + repeat_bytes + engine_mute_bytes + volume_bytes + extra_bytes
        await client.send_command(34, play_data)  # PLAY_SGU_SOUND

        # 等待播放响应
        print("⏳ 等待播放音效响应...")
        await asyncio.sleep(5)

        print("✅ 命令序列执行完成!")

    except Exception as e:
        print(f"❌ 程序错误: {e}")
    finally:
        await client.disconnect()


if __name__ == "__main__":
    asyncio.run(main())