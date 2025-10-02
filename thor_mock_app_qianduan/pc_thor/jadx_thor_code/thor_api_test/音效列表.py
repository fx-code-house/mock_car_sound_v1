import asyncio
import struct
import secrets
from bleak import BleakClient, BleakScanner
from Crypto.Cipher import AES  # 确保您已安装 pycryptodome (pip install pycryptodome)

# Thor BLE UUIDs
SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"

# Constants - 根据Java代码修正
PREAMBLE = -23206  # Java中定义的PREAMBLE值


def take_short(first_byte, second_byte):
    """
    模拟Java的BleHelper.takeShort方法
    Java: ByteBuffer.allocate(2).put(first).put(second).getShort(0)
    """
    # 将两个字节组合成一个16位short值 (大端序)
    return struct.unpack('>h', bytes([first_byte, second_byte]))[0]


def parse_sgu_sound_list_corrected(decrypted_data):
    """解析SGU音效列表数据，完全按照Java逻辑实现 - 最终修正版"""
    print("\n" + "=" * 80)
    print("🎵 解析SGU音效列表数据 (Java逻辑修正版)")
    print("=" * 80)

    # 打印原始数据
    print(f"📦 解密数据长度: {len(decrypted_data)} 字节")
    print(f"📦 解密数据前10字节: {decrypted_data[:10]}")

    # 按照Java逻辑解析
    if len(decrypted_data) < 4:
        print("❌ 数据长度不足，无法解析")
        return []

    # 重要理解：Java中的getBytes()返回的是解密后的数据
    # 所以decrypted_data就相当于Java中的bytes变量

    # Java: short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
    # 这里bytes[2], bytes[3]是解密数据的第3、4个字节
    sounds_amount = take_short(decrypted_data[2], decrypted_data[3])

    print(f"📋 解密数据结构分析:")
    print(f"   bytes[0]: {decrypted_data[0]} (可能是响应长度或其他)")
    print(f"   bytes[1]: {decrypted_data[1]} (可能是状态)")
    print(f"   bytes[2]: {decrypted_data[2]} (用于计算soundsAmount的低字节)")
    print(f"   bytes[3]: {decrypted_data[3]} (用于计算soundsAmount的高字节)")
    print(f"🔢 计算得到的soundsAmount: {sounds_amount}")

    # 从实际数据看，soundsAmount计算不准确，基于数据长度估算
    estimated_sounds = (len(decrypted_data) - 4) // 2  # 减去前4字节，每个音效2字节
    print(f"🔢 基于数据长度估算的音效数: {estimated_sounds}")

    # 使用合理的音效数量
    actual_sounds_to_parse = min(sounds_amount, estimated_sounds, 100)  # 限制最大100个
    print(f"🔢 实际解析音效数: {actual_sounds_to_parse}")

    if actual_sounds_to_parse <= 0:
        print("📊 无音效数据需要解析")
        return []

    # 解析音效数据 - 按照Java逻辑
    sound_ids = []

    print(f"\n🎵 开始解析音效:")
    print("-" * 60)

    # Java逻辑: 从index=4开始，每次读取2个字节组成一个short
    i = 4  # 从第5个字节开始
    sound_index = 1

    while sound_index <= actual_sounds_to_parse and i < len(decrypted_data) - 1:
        # 检查是否到达填充区域 (0xA5)
        if decrypted_data[i] == 0xA5:
            print(f"📍 到达填充区域，位置: {i}")
            break

        # Java: this.soundIds.add(Short.valueOf(BleHelper.takeShort(bytes[i], bytes[i + 1])));
        sound_id = take_short(decrypted_data[i], decrypted_data[i + 1])
        sound_ids.append(sound_id)

        # 分析音效ID的含义
        # 根据实际分析，第一个字节是音效ID，第二个字节是状态
        sound_id_value = decrypted_data[i]
        sound_status = decrypted_data[i + 1]
        status_text = "✅启用" if sound_status == 1 else "❌禁用" if sound_status == 0 else f"❓状态{sound_status}"

        print(
            f"🎵 #{sound_index:2d}: 组合ID={sound_id:6d} | 音效ID={sound_id_value:3d} {status_text} | bytes[{i}]={decrypted_data[i]}, bytes[{i + 1}]={decrypted_data[i + 1]}")

        i += 2
        sound_index += 1

    print("-" * 60)
    print(f"📊 成功解析音效数量: {len(sound_ids)}")

    # 分析结果
    if sound_ids:
        print(f"🎯 组合音效ID范围: {min(sound_ids)} - {max(sound_ids)}")

        # 提取真实的音效ID (低字节) 和状态 (高字节)
        real_sound_ids = []
        enabled_ids = []
        disabled_ids = []

        for idx, combined_id in enumerate(sound_ids):
            real_id = decrypted_data[4 + idx * 2]  # 音效ID
            status = decrypted_data[4 + idx * 2 + 1]  # 状态
            real_sound_ids.append(real_id)

            if status == 1:
                enabled_ids.append(real_id)
            elif status == 0:
                disabled_ids.append(real_id)

        print(f"🎵 真实音效ID范围: {min(real_sound_ids)} - {max(real_sound_ids)}")
        print(f"✅ 启用的音效ID ({len(enabled_ids)}个): {sorted(enabled_ids)}")
        print(f"❌ 禁用的音效ID ({len(disabled_ids)}个): {sorted(disabled_ids)}")

        # 按状态分组统计
        print(f"\n📈 音效状态统计:")
        print(f"   总音效数: {len(sound_ids)}")
        print(f"   启用音效: {len(enabled_ids)} ({len(enabled_ids) / len(sound_ids) * 100:.1f}%)")
        print(f"   禁用音效: {len(disabled_ids)} ({len(disabled_ids) / len(sound_ids) * 100:.1f}%)")

        # 按ID范围分组
        print(f"\n📈 音效ID分组统计:")
        ranges = [
            (0, 30, "基础音效"),
            (31, 100, "扩展音效"),
            (101, 200, "高级音效"),
            (201, 255, "特殊音效")
        ]

        for min_range, max_range, category in ranges:
            range_enabled = [id for id in enabled_ids if min_range <= id <= max_range]
            range_disabled = [id for id in disabled_ids if min_range <= id <= max_range]
            total_in_range = len(range_enabled) + len(range_disabled)

            if total_in_range > 0:
                print(
                    f"   {category:12s} ({min_range:3d}-{max_range:3d}): {total_in_range:2d}个 (启用{len(range_enabled)}, 禁用{len(range_disabled)})")

    print("=" * 80)

    return {
        'total_sounds': len(sound_ids),
        'enabled_sounds': enabled_ids,
        'disabled_sounds': disabled_ids,
        'sound_ids': sound_ids,
        'real_sound_ids': real_sound_ids
    }


class ThorCRC16:
    """Thor自定义CRC16实现 - 基于Java代码完全匹配"""

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
        """计算CRC16 - 完全匹配Thor的实现"""
        crc = 0xFFFF
        for byte in data:
            if isinstance(byte, int):
                byte_val = byte & 0xFF
            else:
                byte_val = ord(byte) & 0xFF

            tbl_idx = (crc ^ byte_val) & 0xFF
            crc = ((crc >> 8) ^ self.crc_table[tbl_idx]) & 0xFFFF

        return crc & 0xFFFF

    def create_checksum_part(self, crc_value):
        """创建CRC部分 - 匹配createCheckSumPart方法"""
        # 从Frida日志看到Thor使用字节反转
        low_byte = crc_value & 0xFF
        high_byte = (crc_value >> 8) & 0xFF
        return bytes([low_byte, high_byte])  # 小端序


class ThorCrypto:
    """Thor AES-CTR加密实现 - 基于Frida Hook分析"""

    def __init__(self, key, iv):
        self.key = bytes.fromhex(key) if isinstance(key, str) else key
        self.initial_iv = iv if isinstance(iv, bytes) else bytes.fromhex(iv)
        # 🔥 关键修复：维护一个递增的计数器
        self.counter_value = int.from_bytes(self.initial_iv, byteorder='big')

    def _get_current_iv(self):
        """获取当前计数器对应的IV"""
        return self.counter_value.to_bytes(16, byteorder='big')

    def encrypt(self, plaintext):
        """AES-CTR加密"""
        current_iv = self._get_current_iv()
        cipher = AES.new(self.key, AES.MODE_CTR, initial_value=current_iv, nonce=b'')
        result = cipher.encrypt(plaintext)
        # 🔥 关键：加密后递增计数器
        self.counter_value += 1
        return result

    def decrypt(self, ciphertext):
        """AES-CTR解密并处理Java AES/CTR的填充/长度逻辑"""
        current_iv = self._get_current_iv()
        cipher = AES.new(self.key, AES.MODE_CTR, initial_value=current_iv, nonce=b'')
        decrypted_raw = cipher.decrypt(ciphertext)
        self.counter_value += 1  # 递增计数器

        # 🔥 关键修复：模拟Java的解密后处理逻辑
        # Java: message = ArraysKt.copyOfRange(bArrCoreAesEncrypt, 1, bArrCoreAesEncrypt.length - bArrCoreAesEncrypt[0]);
        # decrypted_raw[0] 是填充长度 (padding_needed)
        if len(decrypted_raw) > 0:
            padding_length = decrypted_raw[0]
            # 实际数据从第二个字节开始，到总长度减去填充长度为止
            # 确保切片索引不越界
            start_index = 1
            end_index = len(decrypted_raw) - padding_length

            # Sanity check to prevent invalid slicing
            if end_index < start_index:
                # If padding_length is too large, it means no actual data after the first byte or invalid.
                # In such cases, return an empty bytes object or handle as an error.
                print(
                    f"❌ Decryption produced invalid slice range: start={start_index}, end={end_index}. Original length={len(decrypted_raw)}, padding={padding_length}")
                return b''

            decrypted_data = decrypted_raw[start_index:end_index]
            print(f"🔓 原始解密数据: {list(decrypted_raw)}")
            print(f"📐 填充长度 (从字节0读取): {padding_length}")
            print(f"🔓 截取后数据 (从字节1到 {end_index - 1}): {list(decrypted_data)}")
            return decrypted_data
        else:
            print("❌ 解密原始数据为空，无法处理")
            return b''


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
        self.crypto = None
        # SGU音效列表相关
        self.sgu_sounds = None
        self.sgu_sounds_complete = False
        # 播放音效响应状态
        self.play_sound_success = None
        self.play_sound_response_received = False

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

        # 启用通知
        await self.client.start_notify(NOTIFY_UUID, self.notification_handler)
        print("🔔 通知已启用")

        return True

    def notification_handler(self, sender, data):
        """处理BLE通知"""
        print(f"\n📨 收到原始数据: {data.hex().upper()}")
        print(f"📨 收到数据 (bytes): {list(data)}")

        self.response_data.extend(data)

        # 尝试解析完整的响应包
        self.try_parse_complete_response()

    def try_parse_complete_response(self):
        """尝试解析完整的响应包"""
        if len(self.response_data) < 6:  # 最小包长度
            print("⏳ 数据不完整，等待更多数据...")
            return

        # 🔥 关键修复：查找正确的PREAMBLE
        preamble_found = False
        start_index = 0

        for i in range(len(self.response_data) - 1):
            # 🔥 关键修正：Thor使用大端序！
            # PREAMBLE = 0xA55A，在大端序中就是 [0xA5, 0x5A]
            if (self.response_data[i] == 0xA5 and
                    self.response_data[i + 1] == 0x5A):
                preamble_found = True
                start_index = i
                print(f"🎯 找到PREAMBLE at index {i}: [0xA5, 0x5A]")
                break

        if not preamble_found:
            print("❌ 未找到有效的PREAMBLE")
            print(f"🔍 当前数据: {list(self.response_data[:min(20, len(self.response_data))])}")
            return

        # 从PREAMBLE开始解析
        packet_data = self.response_data[start_index:]

        if len(packet_data) < 6:
            print("⏳ 包数据不完整...")
            return

        print(f"🎯 开始解析包: {list(packet_data[:min(20, len(packet_data))])}")

        # 解析包
        success = self.parse_response_packet(packet_data)

        if success:
            # 清空已处理的数据
            self.response_data.clear()

    # ┌─────────┬─────────┬─────────┬─────────┬─────┐
    # │PREAMBLE │加密头   │数据长度 │实际数据 │ CRC │
    # │0xA5 0x5A│2字节    │包含在头中│N字节   │2字节│
    # └─────────┴─────────┴─────────┴─────────┴─────┘
    def parse_response_packet(self, data):
        """解析响应包 - 根据Java BaseBleResponse逻辑"""
        try:
            if len(data) < 6:
                return False

            # 🔥 关键修正：Thor使用大端序解析PREAMBLE
            preamble = struct.unpack('>H', data[0:2])[0]
            print(f"📋 PREAMBLE: 0x{preamble:04X}")

            # 验证PREAMBLE
            expected_preamble = 0xA55A  # -23206的无符号表示
            if preamble != expected_preamble:
                print(f"❌ PREAMBLE不匹配: 期望0x{expected_preamble:04X}, 实际0x{preamble:04X}")
                return False

            # 🔥 关键修复：解析加密头和数据长度 (大端序)
            encryption_header = struct.unpack('>H', data[2:4])[0]

            # 根据EncryptionWithSize.decode逻辑解析
            encryption_type = (encryption_header >> 13) & 0x7  # 高3位 (修正)
            data_length = encryption_header & 0x1FFF  # 低13位 (修正)

            print(f"🔐 加密头: 0x{encryption_header:04X}")
            print(f"🔐 加密类型: {encryption_type}")
            print(f"📏 数据长度: {data_length}")

            # 计算完整包长度: PREAMBLE(2) + 加密头(2) + 数据 + CRC(2)
            total_length = 4 + data_length + 2

            if len(data) < total_length:
                print(f"⏳ 包不完整: 需要{total_length}字节，只有{len(data)}字节")
                return False

            # 提取数据部分
            data_part = data[4:4 + data_length]

            # 提取CRC
            crc_bytes = data[4 + data_length:4 + data_length + 2]
            if len(crc_bytes) < 2:
                print("❌ CRC数据不完整")
                return False

            # 🔥 CRC字节序问题：尝试小端序解析CRC
            crc_received = struct.unpack('<H', crc_bytes)[0]

            print(f"📦 数据部分: {list(data_part)}")
            print(f"🔍 收到CRC (小端序): 0x{crc_received:04X}")

            # 验证CRC (对除CRC外的所有数据)
            crc_data = data[:4 + data_length]
            crc_calculated = self.crc_calculator.calculate(crc_data)

            print(f"🔍 计算CRC: 0x{crc_calculated:04X}")

            # 如果小端序不匹配，尝试大端序
            if crc_received != crc_calculated:
                crc_received_be = struct.unpack('>H', crc_bytes)[0]
                print(f"🔍 收到CRC (大端序): 0x{crc_received_be:04X}")
                if crc_received_be == crc_calculated:
                    crc_received = crc_received_be
                    print("✅ 使用大端序CRC匹配成功!")

            if crc_received == crc_calculated:
                print("✅ CRC验证成功!")

                # 根据加密类型处理数据
                if encryption_type == 0:  # 无加密 - 硬件响应
                    self.parse_hardware_response(data_part)
                elif encryption_type == 2:  # 握手响应
                    self.parse_handshake_response(data_part)
                elif encryption_type == 1:  # 加密数据 - SGU音效列表响应或播放响应
                    self.parse_encrypted_response(data_part)
                else:
                    print(f"🔒 其他加密数据，类型: {encryption_type}")

                return True
            else:
                print("❌ CRC验证失败!")
                return False

        except Exception as e:
            print(f"❌ 解析响应包失败: {e}")
            import traceback
            traceback.print_exc()
            return False

    def parse_hardware_response(self, data):
        """解析硬件响应数据"""
        try:
            if len(data) < 8:
                print("❌ 硬件响应数据太短")
                return

            # 根据HardwareBleResponse.doHandleResponse()方法:
            # 🔥 关键修正：Thor使用大端序解析所有数据
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

            print("\n" + "=" * 60)
            print("🎉 硬件信息解析成功!")
            print("=" * 60)
            print(f"📋 响应命令: {command}")
            print(f"🔧 硬件版本: {hardware_version}")
            print(f"💾 固件版本: {firmware_version}")
            print(f"🔢 序列号: {serial_number}")
            print("=" * 60)

            # 验证是否与Frida日志中的值匹配
            if (hardware_version == 1538 and
                    firmware_version == 520 and
                    serial_number == 17102):
                print("✅ 硬件信息与Frida日志完全匹配!")
            else:
                print("⚠️  硬件信息与Frida日志不匹配，但解析成功")

        except Exception as e:
            print(f"❌ 解析硬件响应失败: {e}")
            import traceback
            traceback.print_exc()

    def parse_handshake_response(self, data):
        """解析握手响应数据"""
        try:
            print("\n🤝 解析握手响应...")

            if len(data) < 8:
                print("❌ 握手响应数据太短")
                return

            # 握手响应应该包含设备的8字节IV
            self.device_iv = bytes(data[:8])

            print(f"🔑 设备IV: {self.device_iv.hex().upper()}")

            # 合并客户端IV和设备IV
            if self.client_iv:
                self.combined_iv = self.client_iv + self.device_iv
                print(f"🔐 合并IV: {self.combined_iv.hex().upper()}")
                print(f"🔐 合并IV长度: {len(self.combined_iv)} 字节")

                # 初始化加密器 - 使用固定密钥
                fixed_key = "1793d9e9d4ca4a16172f1f0156f99cd1"
                self.crypto = ThorCrypto(fixed_key, self.combined_iv)

                self.handshake_complete = True
                print("✅ 握手完成！")

                print("\n" + "=" * 60)
                print("🎊 握手成功完成!")
                print("=" * 60)
                print(f"🔑 客户端IV: {self.client_iv.hex().upper()}")
                print(f"🔑 设备IV: {self.device_iv.hex().upper()}")
                print(f"🔐 最终合并IV: {self.combined_iv.hex().upper()}")
                print("💡 现在可以进行加密通信了!")
                print("=" * 60)
            else:
                print("❌ 客户端IV丢失")

        except Exception as e:
            print(f"❌ 解析握手响应失败: {e}")
            import traceback
            traceback.print_exc()

    def parse_encrypted_response(self, encrypted_data):
        """解析加密响应数据 - 集成修正的解析逻辑"""
        try:
            print("\n🔓 解析加密响应...")

            if not self.crypto:
                print("❌ 加密器未初始化")
                return

            # 解密数据
            decrypted_data = self.crypto.decrypt(encrypted_data)
            # The decrypt method now handles the slicing based on padding_length
            if not decrypted_data:
                print("❌ 解密数据为空或无效，无法解析")
                return

            print(f"🔓 解密并截取后数据: {list(decrypted_data)}")
            print(f"🔓 解密并截取后数据 (hex): {decrypted_data.hex().upper()}")

            # 🔥 关键修复：使用修正的解析逻辑
            # 检查是否是SGU音效列表响应
            if len(decrypted_data) >= 4:
                # 尝试解析命令码 - 根据实际数据结构
                # 从你的分析看，解密数据的结构可能是：
                # [响应长度] [状态] [命令码低] [命令码高] [数据...]

                # 检查是否是播放音效响应 (命令34)
                if len(decrypted_data) >= 4:
                    # 尝试解析命令码
                    possible_command = take_short(decrypted_data[2], decrypted_data[3])

                    if possible_command == 34:  # PLAY_SGU_SOUND响应
                        self.parse_play_sgu_sound_response(decrypted_data)
                        return

                # 先尝试直接用修正的解析函数解析SGU音效列表
                result = parse_sgu_sound_list_corrected(decrypted_data)

                if result and result['total_sounds'] > 0:
                    # 解析成功，保存结果
                    self.sgu_sounds = {
                        'amount': result['total_sounds'],
                        'sound_ids': result['sound_ids'],
                        'enabled_sounds': result['enabled_sounds'],
                        'disabled_sounds': result['disabled_sounds'],
                        'real_sound_ids': result['real_sound_ids']
                    }
                    self.sgu_sounds_complete = True

                    print(f"\n🎊 SGU音效列表解析完成!")
                    print(f"📊 总音效数: {result['total_sounds']}")
                    print(f"✅ 启用音效数: {len(result['enabled_sounds'])}")
                    print(f"❌ 禁用音效数: {len(result['disabled_sounds'])}")
                else:
                    print("❌ SGU音效列表解析失败")
            else:
                print("❌ 解密数据太短，无法解析")

        except Exception as e:
            print(f"❌ 解析加密响应失败: {e}")
            import traceback
            traceback.print_exc()

    def parse_play_sgu_sound_response(self, decrypted_data):
        """解析播放SGU音效的响应 - 基于PlaySguSoundBleResponse.java"""
        try:
            print("\n🎵 解析播放音效响应...")
            print("=" * 50)
            print(f"🔍 完整解密数据: {list(decrypted_data)}")
            print(f"🔍 完整解密数据 (hex): {decrypted_data.hex().upper()}")

            if len(decrypted_data) < 4:
                print("❌ 响应数据太短")
                return

            # 🔥 重要：根据BaseBleResponse的checkStatusResponse逻辑分析数据结构
            # getBytes()可能返回的是去除前缀后的实际响应数据

            # 尝试多种解析方案
            print(f"\n🔍 尝试解析方案:")

            # 方案1：假设数据结构是 [填充长度][状态][命令码][数据]
            if len(decrypted_data) >= 4:
                padding_length = decrypted_data[0]
                status_or_command_1 = decrypted_data[1]
                command_candidate_1 = take_short(decrypted_data[2], decrypted_data[3])
                print(
                    f"   方案1: 填充长度={padding_length}, 状态/命令={status_or_command_1}, 命令码={command_candidate_1}")

            # 方案2：假设数据结构是 [状态][保留][命令码高][命令码低]
            if len(decrypted_data) >= 4:
                status_candidate = decrypted_data[0]
                reserved = decrypted_data[1]
                command_candidate_2 = take_short(decrypted_data[2], decrypted_data[3])
                print(f"   方案2: 状态={status_candidate}, 保留={reserved}, 命令码={command_candidate_2}")

            # 方案3：尝试小端序命令码
            if len(decrypted_data) >= 4:
                command_candidate_3 = take_short(decrypted_data[3], decrypted_data[2])  # 小端序
                print(f"   方案3: 小端序命令码={command_candidate_3}")

            # 🎯 根据Java代码，播放响应的命令码应该是34
            # 寻找命令码34的位置来确定数据结构
            command = None
            response_status = None
            sound_file_id = None

            if len(decrypted_data) >= 4:
                # 检查哪种方案的命令码是34
                if command_candidate_1 == 34:
                    print(f"✅ 使用方案1: 命令码在位置[2,3]")
                    command = command_candidate_1
                    response_status = status_or_command_1
                    # 根据Java代码：soundFileId从bytes[2], bytes[3]解析
                    # 但这与命令码位置冲突，可能Java的getBytes()返回的数据结构不同
                    if len(decrypted_data) >= 6:
                        sound_file_id = take_short(decrypted_data[4], decrypted_data[5])
                elif command_candidate_2 == 34:
                    print(f"✅ 使用方案2: 命令码在位置[2,3]")
                    command = command_candidate_2
                    response_status = status_candidate
                    if len(decrypted_data) >= 6:
                        sound_file_id = take_short(decrypted_data[4], decrypted_data[5])
                elif command_candidate_3 == 34:
                    print(f"✅ 使用方案3: 小端序命令码")
                    command = command_candidate_3
                    response_status = decrypted_data[0]
                    if len(decrypted_data) >= 6:
                        sound_file_id = take_short(decrypted_data[5], decrypted_data[4])

            if command == 34:
                print(f"\n🎯 确认为播放音效响应:")
                print(f"📋 命令码: {command} ✅")
                print(f"📋 响应状态: {response_status}")
                if sound_file_id is not None:
                    print(f"🎵 音效文件ID: {sound_file_id}")

                # 🔥 关键：根据Java代码的isSuccessful()逻辑判断成功
                # BaseBleResponse的checkStatusResponse()通常检查特定位置的状态码
                is_successful = (response_status == 0)  # 假设0表示成功

                if is_successful:
                    print("✅ 播放音效命令执行成功!")
                    print("🔊 Thor设备应该正在播放音效")
                    self.play_sound_success = True
                else:
                    print(f"❌ 播放音效命令执行失败，状态码: {response_status}")
                    self.play_sound_success = False

                # 标记已收到响应
                self.play_sound_response_received = True
            else:
                print(f"⚠️  未找到播放命令码34，可能不是播放响应")
                print(f"🔍 检测到的命令码: {[command_candidate_1, command_candidate_2, command_candidate_3]}")

            print("=" * 50)

        except Exception as e:
            print(f"❌ 解析播放音效响应失败: {e}")
            import traceback
            traceback.print_exc()

    def generate_client_iv(self):
        """生成8字节安全随机IV - 对应Java的EncryptionHelper.generateIVH()"""
        # Java中使用SecureRandom生成8字节随机数
        client_iv = secrets.token_bytes(8)
        print(f"🔑 生成客户端IV: {client_iv.hex().upper()}")
        return client_iv

    def create_hardware_request(self):
        """创建未加密的硬件请求 - 使用Frida验证的数据包"""
        print("\n🔨 创建硬件请求包...")

        # 直接使用Frida日志中验证过的硬件请求数据包
        verified_packet = [165, 90, 0, 2, 0, 1, 32, 227]

        print(f"🔍 使用验证过的数据包: {verified_packet}")
        print(f"🔍 十六进制: {bytes(verified_packet).hex().upper()}")

        return bytes(verified_packet)

    def create_handshake_request(self, client_iv):
        """创建握手请求包 - 根据Frida日志修正"""
        print("\n🔨 创建握手请求包...")
        print("=" * 50)
        print(f"🔑 客户端IV: {client_iv.hex().upper()}")

        # 前导码 (2字节，大端序) - 对应Java的PREAMBLE
        preamble_bytes = struct.pack('>h', PREAMBLE)  # -23206 -> A5 5A

        # 数据体：客户端生成的8字节IV
        data_body = client_iv

        # 加密头：EncryptionWithSize.encode(HANDSHAKE=2, size=8)
        # 正确公式：((2 & 255) << 13) | (8 & 8191) = 16392 = 0x4008
        crypto_type = 2  # HANDSHAKE
        data_length = len(data_body)  # 8
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)  # 大端序

        print(f"📋 前导码: {preamble_bytes.hex().upper()}")
        print(f"🔐 加密头: {encryption_header_bytes.hex().upper()} (期望: 4008)")
        print(f"📦 数据体: {data_body.hex().upper()}")

        # 组装数据包（不含CRC）
        packet_without_crc = preamble_bytes + encryption_header_bytes + data_body

        # 计算CRC
        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        # 完整数据包
        complete_packet = packet_without_crc + crc_bytes

        print(f"🔍 CRC: 0x{crc:04X} -> {crc_bytes.hex().upper()}")
        print(f"✅ 完整握手包: {complete_packet.hex().upper()}")
        print(f"✅ 握手包字节: {list(complete_packet)}")
        print(f"✅ 包长度: {len(complete_packet)} 字节")
        print("=" * 50)

        # 验证包结构
        print("🔍 包结构验证:")
        print(f"   前导码(2字节): {preamble_bytes.hex().upper()}")
        print(f"   加密头(2字节): {encryption_header_bytes.hex().upper()}")
        print(f"   数据体(8字节): {data_body.hex().upper()}")
        print(f"   CRC(2字节): {crc_bytes.hex().upper()}")
        print()

        return complete_packet

    def create_sgu_sounds_request(self):
        """创建读取SGU音效列表的加密请求 - 根据ReadSguSoundsBleRequest"""
        print("\n🔨 创建SGU音效列表请求包...")

        if not self.crypto:
            print("❌ 加密器未初始化")
            return None

        # 命令码36 - READ_SGU_SOUNDS
        command = 36
        command_bytes = struct.pack('>H', command)

        print(f"📋 命令码: {command}")
        print(f"📋 命令字节: {command_bytes.hex().upper()}")

        # 构建加密前的数据：[填充长度] + [命令] + [数据] + [填充]
        # ReadSguSoundsBleRequest没有额外数据，只有命令
        data_body = command_bytes  # 只有命令，无额外数据

        # 计算填充 - 数据需要16字节对齐
        total_data_length = 1 + len(data_body)  # 填充长度字节 + 数据
        padding_needed = (16 - (total_data_length % 16)) % 16
        if padding_needed == 0 and total_data_length < 16:
            padding_needed = 16 - total_data_length  # 确保至少16字节
        padding = bytes([0xA5] * padding_needed)  # Thor使用0xA5填充

        # 构建完整的加密前数据
        pre_encrypt_data = bytes([padding_needed]) + data_body + padding

        print(f"🔍 填充长度: {padding_needed}")
        print(f"🔍 加密前数据: {pre_encrypt_data.hex().upper()}")
        print(f"🔍 加密前数据长度: {len(pre_encrypt_data)}")

        # 加密数据
        encrypted_data = self.crypto.encrypt(pre_encrypt_data)
        print(f"🔐 加密后数据: {encrypted_data.hex().upper()}")

        # 构建完整的BLE包
        # 前导码
        preamble_bytes = struct.pack('>h', PREAMBLE)

        # 加密头：EncryptionWithSize.encode(ENCRYPTED=1, size=len(encrypted_data))
        crypto_type = 1  # ENCRYPTED
        data_length = len(encrypted_data)
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)

        print(f"📋 前导码: {preamble_bytes.hex().upper()}")
        print(f"🔐 加密头: {encryption_header_bytes.hex().upper()}")
        print(f"📦 加密数据长度: {data_length}")

        # 组装数据包（不含CRC）
        packet_without_crc = preamble_bytes + encryption_header_bytes + encrypted_data

        # 计算CRC
        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        # 完整数据包
        complete_packet = packet_without_crc + crc_bytes

        print(f"🔍 CRC: 0x{crc:04X} -> {crc_bytes.hex().upper()}")
        print(f"✅ 完整SGU请求包: {complete_packet.hex().upper()}")
        print(f"✅ 请求包长度: {len(complete_packet)} 字节")

        return complete_packet

    def create_play_sgu_sound_request(self, sound_id=50, repeat_count=1, engine_mute=50, volume=50):
        """创建播放指定SGU音效的加密请求 - 完整Java格式"""
        print(f"\n🔨 创建播放SGU音效请求包 (完整Java格式)...")
        print(f"🎯 使用Java PlaySguSoundBleRequest的完整参数格式")

        if not self.crypto:
            print("❌ 加密器未初始化")
            return None

        # 🎯 使用正确的播放命令码：34（十进制）
        command = 34
        command_bytes = struct.pack('>H', command)

        # 🎵 根据Java PlaySguSoundBleRequest.getBody()的完整参数
        sound_id_bytes = struct.pack('>H', sound_id)  # 音效ID
        repeat_count_bytes = struct.pack('>H', repeat_count)  # 重复次数
        engine_mute_bytes = struct.pack('>H', engine_mute)  # 引擎静音量
        volume_bytes = struct.pack('>H', volume)  # 音量
        extra_bytes = struct.pack('>H', 0)  # 额外的0

        # 🎯 按照Java代码：只有数据体，不包含命令码
        # Java的getBody()返回：soundId + repeatCount + engineSoundMuteAmount + volume + 0
        data_body = sound_id_bytes + repeat_count_bytes + engine_mute_bytes + volume_bytes + extra_bytes

        print(f"📋 命令码: {command} (0x{command:04X})")
        print(
            f"🎵 音效ID: {sound_id} (0x{sound_id:04X}) {'✅ 在启用列表中' if hasattr(self, 'sgu_sounds') and self.sgu_sounds and sound_id in self.sgu_sounds.get('enabled_sounds', []) else '⚠️  不在启用列表中'}")
        print(f"🔁 重复次数: {repeat_count} (0x{repeat_count:04X})")
        print(f"🔇 引擎静音量: {engine_mute} (0x{engine_mute:04X})")
        print(f"🔊 音量: {volume} (0x{volume:04X})")
        print(f"📦 额外参数: 0 (0x0000)")
        print(f"🔍 数据体: {data_body.hex().upper()}")

        # 🎯 构建加密前数据：[填充长度] + [命令码] + [数据体] + [填充]
        # 这应该匹配BaseBleRequest.generateData()的逻辑
        complete_data = command_bytes + data_body

        # 计算填充以达到16字节对齐
        total_data_length = 1 + len(complete_data)  # 填充长度字节 + 完整数据
        padding_needed = (16 - (total_data_length % 16)) % 16
        if padding_needed == 0 and total_data_length < 16:
            padding_needed = 16 - total_data_length  # 确保至少16字节
        padding = bytes([0xA5] * padding_needed)

        # 构建完整的加密前数据
        pre_encrypt_data = bytes([padding_needed]) + complete_data + padding

        print(f"🔍 完整数据: {complete_data.hex().upper()}")
        print(f"🔍 填充长度: {padding_needed}")
        print(f"🔍 填充数据: {padding.hex().upper()}")
        print(f"🔍 加密前数据: {pre_encrypt_data.hex().upper()}")
        print(f"🔍 加密前数据长度: {len(pre_encrypt_data)} 字节")

        # 加密数据
        encrypted_data = self.crypto.encrypt(pre_encrypt_data)
        print(f"🔐 加密后数据: {encrypted_data.hex().upper()}")

        # 构建完整的BLE包
        # 前导码
        preamble_bytes = struct.pack('>h', PREAMBLE)

        # 加密头：EncryptionWithSize.encode(ENCRYPTED=1, size=len(encrypted_data))
        crypto_type = 1  # ENCRYPTED
        data_length = len(encrypted_data)
        encryption_header = ((crypto_type & 255) << 13) | (data_length & 8191)
        encryption_header_bytes = struct.pack('>H', encryption_header)

        print(f"📋 前导码: {preamble_bytes.hex().upper()}")
        print(f"🔐 加密头: {encryption_header_bytes.hex().upper()}")
        print(f"📦 加密数据长度: {data_length}")

        # 组装数据包（不含CRC）
        packet_without_crc = preamble_bytes + encryption_header_bytes + encrypted_data

        # 计算CRC
        crc = self.crc_calculator.calculate(packet_without_crc)
        crc_bytes = self.crc_calculator.create_checksum_part(crc)

        # 完整数据包
        complete_packet = packet_without_crc + crc_bytes

        print(f"🔍 CRC: 0x{crc:04X} -> {crc_bytes.hex().upper()}")
        print(f"✅ 完整播放音效请求包: {complete_packet.hex().upper()}")
        print(f"✅ 请求包长度: {len(complete_packet)} 字节")

        return complete_packet

    async def play_sgu_sound(self, sound_id=50, repeat_count=1):
        """播放指定的SGU音效 - 使用启用列表中的音效"""
        if not self.client or not self.client.is_connected:
            print("❌ 设备未连接")
            return False

        if not self.handshake_complete or not self.crypto:
            print("❌ 握手未完成或加密器未初始化")
            return False

        print(f"\n🎵 开始播放SGU音效 (ID: {sound_id}, 重复: {repeat_count}次)...")

        # 清空响应数据和状态
        self.response_data.clear()
        self.play_sound_success = None
        self.play_sound_response_received = False

        # 创建播放音效请求
        request = self.create_play_sgu_sound_request(sound_id, repeat_count)
        if not request:
            return False

        print(f"\n📤 发送播放音效请求...")
        print(f"📤 数据: {request.hex().upper()}")
        print(f"📤 字节: {list(request)}")

        try:
            # 发送请求
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ 播放音效请求已发送")

            # 等待设备响应
            print("⏳ 等待Thor设备响应...")

            for i in range(5):  # 等待5秒即可，播放命令通常不返回响应
                await asyncio.sleep(1)
                if self.play_sound_response_received:
                    if self.play_sound_success:
                        print(f"🎉 音效 {sound_id} 播放成功!")
                        print("🔊 请注意听Thor设备的声音输出!")
                        return True
                    else:
                        print(f"❌ 音效 {sound_id} 播放失败")
                        return False
                print(f"⏳ 等待响应中... ({i + 1}/5)")

            # 播放命令通常不返回响应，这是正常的
            print("✅ 播放命令已发送 (设备通常不返回播放响应)")
            print(f"🎵 已发送播放音效 {sound_id} 的命令，请注意听设备声音")

            return True  # 命令发送成功即认为成功

        except Exception as e:
            print(f"❌ 发送播放音效请求失败: {e}")
            return False

    async def send_hardware_request(self):
        """发送硬件请求并等待响应"""
        if not self.client or not self.client.is_connected:
            print("❌ 设备未连接")
            return False

        print("\n🚀 开始硬件信息读取流程...")

        # 清空之前的响应数据
        self.response_data.clear()
        self.hardware_info = None

        # 创建硬件请求
        request = self.create_hardware_request()

        print(f"\n📤 发送硬件请求...")
        print(f"📤 数据: {request.hex().upper()}")
        print(f"📤 字节: {list(request)}")

        try:
            # 发送请求
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ 硬件请求已发送")

            # 等待响应
            print("⏳ 等待Thor设备响应...")

            for i in range(10):  # 等待最多10秒
                await asyncio.sleep(1)
                if self.hardware_info:
                    print("🎉 成功获取硬件信息!")
                    return True
                print(f"⏳ 等待中... ({i + 1}/10)")

            if not self.hardware_info:
                print("⏰ 设备响应超时，未收到有效响应")
                return False

        except Exception as e:
            print(f"❌ 发送请求失败: {e}")
            return False

        return True

    async def send_handshake_request(self):
        """发送握手请求并等待响应"""
        if not self.client or not self.client.is_connected:
            print("❌ 设备未连接")
            return False

        print("\n🚀 开始握手流程...")

        # 清空握手状态
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False
        self.response_data.clear()

        # 生成客户端IV
        self.client_iv = self.generate_client_iv()

        # 创建握手请求
        request = self.create_handshake_request(self.client_iv)

        print(f"📤 发送握手请求...")
        print(f"📤 数据: {request.hex().upper()}")
        print(f"📤 字节: {list(request)}")

        try:
            # 发送握手请求
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ 握手请求已发送")

            # 等待握手响应
            print("⏳ 等待Thor握手响应...")

            for i in range(10):  # 等待最多10秒
                await asyncio.sleep(1)
                if self.handshake_complete:
                    print("🎉 握手成功完成!")
                    return True
                print(f"⏳ 等待中... ({i + 1}/10)")

            if not self.handshake_complete:
                print("⏰ 握手超时，未收到有效响应")
                print("\n❌ 握手失败")
                print("💡 可能的问题:")
                print("   1. 握手包格式不正确")
                print("   2. Thor设备不响应握手请求")
                print("   3. 握手响应解析错误")
                return False

        except Exception as e:
            print(f"❌ 发送握手请求失败: {e}")
            return False

        return True

    async def send_sgu_sounds_request(self):
        """发送读取SGU音效列表请求并等待响应"""
        if not self.client or not self.client.is_connected:
            print("❌ 设备未连接")
            return False

        if not self.handshake_complete or not self.crypto:
            print("❌ 握手未完成或加密器未初始化")
            return False

        print("\n🚀 开始读取SGU音效列表...")

        # 清空SGU状态
        self.sgu_sounds = None
        self.sgu_sounds_complete = False
        self.response_data.clear()

        # 创建SGU音效列表请求
        request = self.create_sgu_sounds_request()
        if not request:
            return False

        print(f"\n📤 发送SGU音效列表请求...")
        print(f"📤 数据: {request.hex().upper()}")
        print(f"📤 字节: {list(request)}")

        try:
            # 发送请求
            await self.client.write_gatt_char(WRITE_UUID, request)
            print("✅ SGU音效列表请求已发送")

            # 等待响应
            print("⏳ 等待Thor设备响应...")

            for i in range(15):  # 等待最多15秒
                await asyncio.sleep(1)
                if self.sgu_sounds_complete:
                    print("🎉 成功获取SGU音效列表!")
                    return True
                print(f"⏳ 等待中... ({i + 1}/15)")

            if not self.sgu_sounds_complete:
                print("⏰ SGU音效列表响应超时")
                return False

        except Exception as e:
            print(f"❌ 发送SGU音效列表请求失败: {e}")
            return False

        return True

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

        # 等待连接稳定
        print("⏳ 等待连接稳定...")
        await asyncio.sleep(2)

        # 第一步：发送硬件请求
        success = await client.send_hardware_request()

        if not success or not client.hardware_info:
            print("\n❌ 硬件信息获取失败")
            return

        print(f"\n🎉 成功获取硬件信息!")

        # 第二步：执行握手流程
        handshake_success = await client.send_handshake_request()

        if not handshake_success or not client.handshake_complete:
            print("\n❌ 握手失败")
            return

        print("\n🎊 握手流程成功完成!")

        # 第三步：读取SGU音效列表
        sgu_success = await client.send_sgu_sounds_request()

        if sgu_success and client.sgu_sounds_complete:
            print("\n" + "=" * 80)
            print("🎊 完整的Thor通信流程成功!")
            print("=" * 80)
            print("📋 最终状态总结:")
            print(f"   🔧 硬件版本: {client.hardware_info['hardware_version']}")
            print(f"   💾 固件版本: {client.hardware_info['firmware_version']}")
            print(f"   🔢 序列号: {client.hardware_info['serial_number']}")
            print(f"   🔐 合并IV: {client.combined_iv.hex().upper()}")
            print(f"   🎵 音效总数: {client.sgu_sounds['amount']}")
            print(f"   ✅ 启用音效数: {len(client.sgu_sounds['enabled_sounds'])}")
            print(f"   ❌ 禁用音效数: {len(client.sgu_sounds['disabled_sounds'])}")
            print(f"   🎵 启用音效ID: {sorted(client.sgu_sounds['enabled_sounds'])}")
            print("💡 Thor设备通信协议完全破解成功!")
            print("=" * 80)

            # 🎵 新增：播放启用列表中的音效
            if client.sgu_sounds and client.sgu_sounds['enabled_sounds']:
                # 选择几个启用的音效进行测试
                test_sounds = client.sgu_sounds['enabled_sounds'][:3]  # 测试前3个启用的音效
                print(f"\n🎵 准备播放启用列表中的音效...")
                print(f"🎯 测试音效ID: {test_sounds}")

                for sound_id in test_sounds:
                    print(f"\n🎵 播放音效 ID: {sound_id}...")

                    # 播放音效 (重复1次)
                    play_success = await client.play_sgu_sound(sound_id, 1)

                    if play_success:
                        print(f"🎉 成功发送播放音效 {sound_id} 的命令!")
                        print("🔊 请注意听Thor设备的声音输出!")

                        # 等待一下再播放下一个音效
                        print("⏳ 等待2秒后播放下一个音效...")
                        await asyncio.sleep(2)
                    else:
                        print(f"❌ 播放音效 {sound_id} 失败")

                print("\n💡 注意：这只是发送播放命令，音效文件仍然存储在Thor设备中")
                print("💡 无法将音效文件下载到本地，因为Thor设备不支持音效文件下载功能")
            else:
                print("\n⚠️  没有找到启用的音效")

        else:
            print("\n❌ SGU音效列表读取失败")

    except Exception as e:
        print(f"❌ 程序错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await client.disconnect()


if __name__ == "__main__":
    asyncio.run(main())