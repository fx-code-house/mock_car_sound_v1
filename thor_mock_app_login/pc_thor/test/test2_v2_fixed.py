import asyncio
import struct
import secrets
import time

from bleak import BleakClient, BleakScanner
from Crypto.Cipher import AES  # 确保您已安装 pycryptodome (pip install pycryptodome)
from loguru import logger

# Thor BLE UUIDs
SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"

# Constants
PREAMBLE = -23206


def take_short(first_byte, second_byte):
    """
    完全匹配Java BleHelper.takeShort()的实现
    Java: ByteBuffer.allocate(2).put(first).put(second).getShort(0)
    Java ByteBuffer默认使用BIG ENDIAN
    """
    return struct.unpack('>h', bytes([first_byte, second_byte]))[0]


# ==================== vvv 最终修正：完全匹配Java代码逻辑 vvv ====================
def parse_sgu_sound_list_java_exact(decrypted_data):
    """
    完全按照Java ReadSguSoundsResponse.doHandleResponse()实现

    Java代码逻辑:
    1. short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
    2. this.soundsAmount = sTakeShort;
    3. if (sTakeShort == 0) return;
    4. if (1 <= sTakeShort) {
    5.     int i = 4; int i2 = 1;
    6.     while (true) {
    7.         this.soundIds.add(Short.valueOf(BleHelper.takeShort(bytes[i], bytes[i + 1])));
    8.         i += 2;
    9.         if (i2 == sTakeShort) break; else i2++;
    10.    }
    11. }
    """
    logger.info("\n" + "=" * 80)
    logger.info("🎵 (Java精确匹配) 解析SGU音效列表...")
    logger.info("=" * 80)

    if not decrypted_data or len(decrypted_data) < 4:
        logger.error("❌ 音效列表数据长度不足或为空")
        return {'total_sounds': 0, 'sound_ids': []}

    # 🔥 Java: short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
    sounds_amount = take_short(decrypted_data[2], decrypted_data[3])
    logger.info(f"🔢 Java: sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]) = {sounds_amount}")

    # 🔥 Java: if (sTakeShort == 0) return;
    if sounds_amount == 0:
        logger.info("📊 Java: sTakeShort == 0, return (有效响应)")
        return {'total_sounds': 0, 'sound_ids': []}

    sound_ids = []

    # 🔥 Java: if (1 <= sTakeShort) {
    if 1 <= sounds_amount:
        # 🔥 Java: int i = 4; int i2 = 1;
        i = 4
        i2 = 1

        logger.info(f"\n🎵 Java: 开始while循环 (i={i}, i2={i2}):")
        logger.info("-" * 60)

        # 🔥 Java: while (true) {
        while True:
            # 检查数据边界
            if i + 1 >= len(decrypted_data):
                logger.warning(f"⚠️  数据边界检查: i+1={i+1} >= len={len(decrypted_data)}, 停止")
                break

            # 🔥 Java: this.soundIds.add(Short.valueOf(BleHelper.takeShort(bytes[i], bytes[i + 1])));
            sound_id = take_short(decrypted_data[i], decrypted_data[i + 1])
            sound_ids.append(sound_id)

            logger.info(f"🎵 Java: soundIds.add(BleHelper.takeShort(bytes[{i}], bytes[{i+1}])) = {sound_id}")
            logger.info(f"      bytes[{i}]={decrypted_data[i]}, bytes[{i+1}]={decrypted_data[i+1]} -> {sound_id}")

            # 🔥 Java: i += 2;
            i += 2

            # 🔥 Java: if (i2 == sTakeShort) { break; } else { i2++; }
            if i2 == sounds_amount:
                logger.info(f"🔄 Java: i2 ({i2}) == sTakeShort ({sounds_amount}), break")
                break
            else:
                i2 += 1
                logger.info(f"🔄 Java: i2++ -> {i2}, 继续循环")

    logger.info("-" * 60)
    logger.info(f"📊 Java逻辑完成: 解析了 {len(sound_ids)} 个音效ID")
    logger.info(f"🎵 最终soundIds列表: {sound_ids}")

    if sound_ids:
        logger.info(f"🎯 音效ID范围: {min(sound_ids)} 到 {max(sound_ids)}")

        # 分析数据模式
        positive_ids = [id for id in sound_ids if id >= 0]
        negative_ids = [id for id in sound_ids if id < 0]
        logger.info(f"📈 正数ID: {len(positive_ids)}个 {positive_ids[:10]}{'...' if len(positive_ids) > 10 else ''}")
        logger.info(f"📉 负数ID: {len(negative_ids)}个 {negative_ids[:10]}{'...' if len(negative_ids) > 10 else ''}")

    logger.info("=" * 80)

    return {
        'total_sounds': len(sound_ids),
        'sound_ids': sound_ids,
    }


# ==================== ^^^ 最终修正：完全匹配Java代码逻辑 ^^^ ====================

def parse_upload_start_response(decrypted_data):
    """解析上传开始响应 (命令7)"""
    logger.info("\n" + "=" * 80)
    logger.info("🔺 解析上传开始响应...")
    logger.info("=" * 80)

    if not decrypted_data or len(decrypted_data) < 2:
        logger.error("❌ 上传开始响应数据不足")
        return {'success': False}

    command_echo = decrypted_data[1]
    logger.info(f"📋 命令回显: {command_echo} (期望: 7)")

    if command_echo == 7:
        logger.info("✅ 上传开始响应确认 - 设备准备接收文件")
        return {'success': True, 'command': command_echo}
    else:
        logger.warning(f"⚠️ 命令码不匹配: {command_echo} != 7")
        return {'success': False}


def parse_configuration_response(decrypted_data):
    """解析配置响应 (命令13等)"""
    logger.info("\n" + "=" * 80)
    logger.info("⚙️ 解析配置响应...")
    logger.info("=" * 80)

    if not decrypted_data or len(decrypted_data) < 2:
        logger.error("❌ 配置响应数据不足")
        return {'success': False}

    command_echo = decrypted_data[1]
    logger.info(f"📋 命令回显: {command_echo}")

    if command_echo in [13, 11]:  # 配置命令或音频控制命令
        logger.info(f"✅ 配置响应确认 - 命令{command_echo}执行成功")
        return {'success': True, 'command': command_echo}
    else:
        logger.info(f"ℹ️ 其他响应: 命令{command_echo}")
        return {'success': True, 'command': command_echo}


def parse_audio_control_response(decrypted_data):
    """解析音频控制响应"""
    logger.info("\n" + "=" * 80)
    logger.info("🎵 解析音频控制响应...")
    logger.info("=" * 80)

    if not decrypted_data or len(decrypted_data) < 2:
        logger.error("❌ 音频控制响应数据不足")
        return {'success': False}

    command_echo = decrypted_data[1]
    logger.info(f"📋 命令回显: {command_echo} (期望: 11)")

    # 显示原始数据用于调试
    logger.info(f"🔍 响应数据长度: {len(decrypted_data)} 字节")
    logger.info(f"🔍 响应数据 (hex): {decrypted_data.hex().upper()}")

    if command_echo == 11:
        logger.info("✅ 音频控制响应确认")
        return {'success': True, 'command': command_echo, 'data': decrypted_data}
    else:
        logger.info(f"ℹ️ 其他音频响应: 命令{command_echo}")
        return {'success': True, 'command': command_echo, 'data': decrypted_data}

def parse_device_parameters_response(decrypted_data):
    """
    完全按照Java ReadDeviceParametersBleResponse.doHandleResponse()实现

    Java代码逻辑:
    1. short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
    2. if (1 <= sTakeShort) {
    3.     while (true) {
    4.         int i2 = i * 4;
    5.         arrayList.add(new DeviceParameters.Parameter(
    6.             BleHelper.takeShort(bytes[i2], bytes[i2 + 1]),
    7.             BleHelper.takeShort(bytes[i2 + 2], bytes[i2 + 3])));
    8.         if (i == sTakeShort) break; else i++;
    9.     }
    10. }
    """
    logger.info("\n" + "=" * 80)
    logger.info("⚙️ (Java精确匹配) 解析设备参数响应...")
    logger.info("=" * 80)

    if not decrypted_data or len(decrypted_data) < 4:
        logger.error("❌ 设备参数数据长度不足或为空")
        return {'success': False}

    # 显示原始数据
    logger.info(f"📊 设备参数数据长度: {len(decrypted_data)} 字节")
    logger.info(f"📊 原始数据 (hex): {decrypted_data.hex().upper()}")
    logger.info(f"📊 原始数据 (list): {list(decrypted_data)}")

    # 🔥 Java: short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
    parameters_count = take_short(decrypted_data[2], decrypted_data[3])
    logger.info(f"🔢 Java: sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]) = {parameters_count}")

    parameters = []

    # 🔥 Java: if (1 <= sTakeShort) {
    if 1 <= parameters_count:
        # 🔥 Java: int i = 1;
        i = 1

        logger.info(f"\n⚙️ Java: 开始while循环 (i={i}):")
        logger.info("-" * 60)

        # 🔥 Java: while (true) {
        while True:
            # 🔥 Java: int i2 = i * 4;
            i2 = i * 4

            # 检查数据边界
            if i2 + 3 >= len(decrypted_data):
                logger.warning(f"⚠️  数据边界检查: i2+3={i2+3} >= len={len(decrypted_data)}, 停止")
                break

            # 🔥 Java: BleHelper.takeShort(bytes[i2], bytes[i2 + 1])
            param_id = take_short(decrypted_data[i2], decrypted_data[i2 + 1])

            # 🔥 Java: BleHelper.takeShort(bytes[i2 + 2], bytes[i2 + 3])
            param_value = take_short(decrypted_data[i2 + 2], decrypted_data[i2 + 3])

            parameters.append({'id': param_id, 'value': param_value})

            logger.info(f"⚙️ Java: 参数[{i}] ID={param_id}, Value={param_value}")
            logger.info(f"      bytes[{i2}]={decrypted_data[i2]}, bytes[{i2+1}]={decrypted_data[i2+1]} -> ID={param_id}")
            logger.info(f"      bytes[{i2+2}]={decrypted_data[i2+2]}, bytes[{i2+3}]={decrypted_data[i2+3]} -> Value={param_value}")

            # 🔥 Java: if (i == sTakeShort) { break; } else { i++; }
            if i == parameters_count:
                logger.info(f"🔄 Java: i ({i}) == sTakeShort ({parameters_count}), break")
                break
            else:
                i += 1
                logger.info(f"🔄 Java: i++ -> {i}, 继续循环")

    logger.info("-" * 60)
    logger.info(f"📊 Java逻辑完成: 解析了 {len(parameters)} 个设备参数")
    logger.info(f"⚙️ 最终参数列表: {parameters}")
    logger.info("=" * 80)

    return {
        'success': True,
        'command': 9,
        'parameters_count': len(parameters),
        'parameters': parameters,
        'data': decrypted_data
    }


class ThorCRC16:
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


class ThorCrypto:
    def __init__(self, key, iv):
        self.key = key
        self.initial_iv = iv
        self.counter_value = int.from_bytes(self.initial_iv, byteorder='big')
        logger.info(f"🔑 ThorCrypto 初始化。密钥: {self.key.hex().upper()}, 初始IV: {self.initial_iv.hex().upper()}")

    def _get_current_iv(self):
        return self.counter_value.to_bytes(16, byteorder='big')

    def encrypt(self, plaintext):
        current_iv = self._get_current_iv()
        cipher = AES.new(self.key, AES.MODE_CTR, initial_value=current_iv, nonce=b'')
        result = cipher.encrypt(plaintext)
        self.counter_value += 1
        return result

    def decrypt(self, ciphertext):
        current_iv = self._get_current_iv()
        cipher = AES.new(self.key, AES.MODE_CTR, initial_value=current_iv, nonce=b'')
        decrypted_raw = cipher.decrypt(ciphertext)
        self.counter_value += 1

        if len(decrypted_raw) > 0:
            padding_length = decrypted_raw[0]
            start_index = 1
            end_index = len(decrypted_raw) - padding_length
            if end_index < start_index:
                logger.error(f"❌ 解密后切片范围无效: start={start_index}, end={end_index}")
                return b''
            return decrypted_raw[start_index:end_index]
        return b''


class ThorClient:
    def __init__(self):
        self.client = None
        self.crc_calculator = ThorCRC16()
        self.response_data = bytearray()
        self.hardware_info = None
        self.client_iv = None
        self.device_iv = None
        self.combined_iv = None
        self.handshake_complete = False
        self.crypto = None
        self.sgu_sounds = None
        self.sgu_sounds_complete = False
        self.device_params = None
        self.device_params_complete = False
        self.upload_start_complete = False
        self.configuration_complete = False
        self.audio_control_complete = False
        self.sound_config_complete = False
        self.bulk_config_complete = False

    def _derive_aes_key(self, hardware_version, firmware_version, serial_number):
        logger.info(f"\n🔑 开始派生 Thor AES 密钥...")
        logger.info(f"   (输入参数) HW Ver: {hardware_version}, FW Ver: {firmware_version}, SN: {serial_number}")

        v12 = (53 * hardware_version + 241 * firmware_version + 11 * serial_number) & 0xFFFFFFFF
        v13 = v12 ^ (v12 >> 8) ^ (v12 >> 16) ^ (v12 >> 24)
        v13 &= 0xFFFFFFFF

        key = bytearray(16)
        key[8] = ((v13 + 39) ^ 0xE4) & 0xFF
        key[9] = ((v13 + 65) ^ 0x22) & 0xFF
        key[10] = ((v13 - 87) ^ 0x6A) & 0xFF
        key[11] = ((v13 + 117) ^ 0x40) & 0xFF
        key[12] = ((v13 + 78) ^ 0x4C) & 0xFF
        key[13] = (v13 ^ 0x35) & 0xFF
        key[14] = ((v13 - 100) ^ 0xF4) & 0xFF
        key[15] = ((v13 + 104) ^ 0xE5) & 0xFF

        const_A = bytes([0x1A, 0xB6, 0x8F, 0x0D, 0xC3, 0x5B, 0x34, 0x82])
        const_B = bytes([0xF1, 0x11, 0x82, 0x30, 0x5B, 0xED, 0x4A, 0x58])
        v13_byte = v13 & 0xFF

        for i in range(8):
            add_result = (v13_byte + const_A[i]) & 0xFF
            xor_result = (add_result ^ const_B[i]) & 0xFF
            key[i] = xor_result

        derived_key = bytes(key)
        logger.info(f"✅ 成功派生密钥: {derived_key.hex().upper()}")
        return derived_key

    async def scan_and_connect(self, device_name="Thor"):
        logger.info(f"🔍 扫描 {device_name} 设备...")
        devices = await BleakScanner.discover(timeout=10.0)
        thor_device = next((d for d in devices if d.name and device_name.lower() in d.name.lower()), None)

        if not thor_device:
            logger.error(f"❌ 未找到 {device_name} 设备")
            return False

        logger.info(f"✅ 找到设备: {thor_device.name} ({thor_device.address})")
        self.client = BleakClient(thor_device.address)
        try:
            await self.client.connect()
            logger.info("🔗 连接成功!")
            await self.client.start_notify(NOTIFY_UUID, self.notification_handler)
            logger.info("🔔 通知已启用")
            return True
        except Exception as e:
            logger.error(f"❌ 连接失败: {e}")
            return False

    def notification_handler(self, sender: int, data: bytearray):
        logger.info(f"📨 收到原始数据: {data.hex().upper()}")
        self.response_data.extend(data)
        self.try_parse_complete_response()

    def try_parse_complete_response(self):
        while len(self.response_data) >= 6:
            try:
                preamble_start_index = self.response_data.find(b'\xA5\x5A')
                if preamble_start_index == -1:
                    return

                self.response_data = self.response_data[preamble_start_index:]

                encryption_header = struct.unpack('>H', self.response_data[2:4])[0]
                data_length = encryption_header & 0x1FFF
                total_packet_length = 4 + data_length + 2

                if len(self.response_data) < total_packet_length:
                    return

                packet = self.response_data[:total_packet_length]
                self.response_data = self.response_data[total_packet_length:]

                if self.parse_response_packet(packet):
                    logger.info("✅ 包解析成功。")
                else:
                    logger.warning("❌ 包解析失败。")
            except Exception as e:
                logger.error(f"❌ 解析循环中出现错误: {e}, 清理一个字节后重试。")
                self.response_data = self.response_data[1:]

    def parse_response_packet(self, data):
        crc_data = data[:-2]
        crc_received = struct.unpack('<H', data[-2:])[0]
        crc_calculated = self.crc_calculator.calculate(crc_data)

        if crc_received != crc_calculated:
            logger.error(f"❌ CRC验证失败! 收到: 0x{crc_received:04X}, 计算: 0x{crc_calculated:04X}")
            return False

        logger.info("✅ CRC验证成功!")
        encryption_header = struct.unpack('>H', data[2:4])[0]
        encryption_type = (encryption_header >> 13) & 0x7
        data_part = data[4:-2]

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
        logger.info("🎉 硬件信息解析成功!")
        logger.info(
            f"   HW: {self.hardware_info['hardware_version']}, FW: {self.hardware_info['firmware_version']}, SN: {self.hardware_info['serial_number']}")

    def parse_handshake_response(self, data):
        if len(data) < 8 or not self.client_iv or not self.hardware_info:
            logger.error("❌ 握手响应前置条件不足!")
            return

        self.device_iv = bytes(data[:8])
        self.combined_iv = self.client_iv + self.device_iv

        logger.warning("⚠️  根据日志分析，尝试调换 HW version 和 SN 的顺序来派生密钥...")
        derived_key = self._derive_aes_key(
            self.hardware_info['serial_number'],
            self.hardware_info['firmware_version'],
            self.hardware_info['hardware_version']
        )

        self.crypto = ThorCrypto(derived_key, self.combined_iv)
        self.handshake_complete = True
        logger.info("🎊 握手成功完成!")

    def parse_encrypted_response(self, encrypted_data):
        logger.info("🔓 正在解析收到的加密响应...")
        if not self.crypto:
            logger.error("❌ 加密器未初始化!")
            return

        decrypted_data = self.crypto.decrypt(encrypted_data)
        if not decrypted_data:
            logger.error("❌ 解密数据为空!")
            return

        # 🔍 调试信息：显示解密后的原始数据
        logger.info(f"🔍 解密数据长度: {len(decrypted_data)} 字节")
        logger.info(f"🔍 解密数据 (hex): {decrypted_data.hex().upper()}")
        logger.info(f"🔍 解密数据 (list): {list(decrypted_data)}")
        if len(decrypted_data) >= 4:
            logger.info(f"🔍 前4字节: [{decrypted_data[0]}, {decrypted_data[1]}, {decrypted_data[2]}, {decrypted_data[3]}]")

        # 🔥 根据命令码决定使用哪个解析函数
        # 加密响应结构: [状态, 命令回显, 数据...]
        if len(decrypted_data) >= 2:
            command_echo = decrypted_data[1]  # 命令回显在bytes[1]
            logger.info(f"🔍 检测到命令回显: {command_echo}")

            if command_echo == 36:  # SGU音效列表响应
                logger.info("🎵 识别为SGU音效列表响应")
                result = parse_sgu_sound_list_java_exact(decrypted_data)
                if isinstance(result, dict):
                    logger.info("✅ SGU音效列表解析流程完成!")
                    self.sgu_sounds = result
                    self.sgu_sounds_complete = True
                else:
                    logger.warning("⚠️  SGU音效列表解析失败")

            elif command_echo == 9:  # 设备参数响应
                logger.info("⚙️ 识别为设备参数响应")
                result = parse_device_parameters_response(decrypted_data)
                if result.get('success'):
                    logger.info("✅ 设备参数解析流程完成!")
                    self.device_params = result
                    self.device_params_complete = True
                else:
                    logger.warning("⚠️  设备参数解析失败")

            elif command_echo == 7:  # 上传开始响应
                logger.info("📤 识别为上传开始响应")
                if len(decrypted_data) >= 2 and decrypted_data[0] == 0:
                    logger.info("✅ 上传开始响应成功!")
                    self.upload_start_complete = True
                else:
                    logger.warning("⚠️  上传开始响应失败")

            elif command_echo == 11:  # 音频控制响应
                logger.info("🎵 识别为音频控制响应")
                if len(decrypted_data) >= 2 and decrypted_data[0] == 0:
                    logger.info("✅ 音频控制响应成功!")
                    self.audio_control_complete = True
                else:
                    logger.warning("⚠️  音频控制响应失败")

            elif command_echo == 13:  # 设备配置响应
                logger.info("⚙️ 识别为设备配置响应")
                if len(decrypted_data) >= 2 and decrypted_data[0] == 0:
                    logger.info("✅ 设备配置响应成功!")
                    self.configuration_complete = True
                else:
                    logger.warning("⚠️  设备配置响应失败")

            else:
                logger.warning(f"⚠️  未知命令响应: {command_echo}")
        else:
            logger.warning("⚠️  加密响应数据太短，无法识别命令")

    async def send_request(self, request_name, creation_func, *args):
        if not self.client or not self.client.is_connected: return False
        logger.info(f"\n🚀 开始流程: {request_name}...")
        request = creation_func(*args)
        if not request: return False

        logger.info(f"📤 发送数据: {request.hex().upper()}")
        await self.client.write_gatt_char(WRITE_UUID, request)
        logger.info("✅ 请求已发送。")
        return True

    def _create_packet(self, data_body, crypto_type):
        if crypto_type == 1:  # Encrypted
            if not self.crypto:
                logger.error("❌ 加密器未初始化，无法创建加密包!")
                return None

            padding_needed = (16 - (1 + len(data_body)) % 16) % 16
            padding = bytes([0xA5] * padding_needed)
            pre_encrypt_data = bytes([padding_needed]) + data_body + padding
            data_part = self.crypto.encrypt(pre_encrypt_data)
        else:  # Unencrypted or Handshake
            data_part = data_body

        header = struct.pack('>H', ((crypto_type & 0x7) << 13) | (len(data_part) & 0x1FFF))
        preamble = struct.pack('>h', PREAMBLE)

        packet_without_crc = preamble + header + data_part
        crc_bytes = self.crc_calculator.create_checksum_part(self.crc_calculator.calculate(packet_without_crc))

        return packet_without_crc + crc_bytes

    def create_hardware_request(self):
        return self._create_packet(b'\x00\x01', 0)

    def create_handshake_request(self, client_iv):
        self.client_iv = client_iv
        return self._create_packet(client_iv, 2)

    def create_sgu_sounds_request(self):
        return self._create_packet(struct.pack('>H', 36), 1)

    def create_device_parameters_request(self):
        """创建读取设备参数请求 (命令9)"""
        return self._create_packet(struct.pack('>H', 9), 1)

    def create_upload_start_request(self, file_size=200):
        """创建上传开始请求 (命令7) - 模拟log中的文件上传"""
        # 基于log: 7,0,-128,8,0,0,0,0,-56
        request_body = struct.pack('>H', 7) + struct.pack('>H', 0x8008) + struct.pack('>I', file_size)
        logger.info(f"🔺 创建上传开始请求: 文件大小={file_size}字节")
        return self._create_packet(request_body, 1)

    def create_upload_data_block_request(self, block_data):
        """创建上传数据块请求 - 模拟文件数据传输"""
        # 填充到16字节对齐
        padding_needed = (16 - len(block_data) % 16) % 16
        if padding_needed > 0:
            block_data += bytes([0xA5] * padding_needed)
        logger.info(f"📤 创建数据块上传: {len(block_data)}字节")
        return self._create_packet(block_data, 1)

    def create_configuration_request(self, config_type=13, sub_type=0):
        """创建配置请求 (命令13) - 基于log中的配置命令"""
        request_body = struct.pack('>H', config_type) + struct.pack('>H', sub_type) + bytes([0xA5] * 12)
        logger.info(f"⚙️ 创建配置请求: type={config_type}, sub_type={sub_type}")
        return self._create_packet(request_body, 1)

    def create_audio_control_request(self, control_cmd=11, sub_cmd=12, param=0x4F78):
        """创建音频控制请求 (命令11) - 基于log中的播放控制"""
        request_body = struct.pack('>H', control_cmd) + struct.pack('>H', sub_cmd) + struct.pack('>H', param) + bytes([0xA5] * 10)
        logger.info(f"🎵 创建音频控制请求: cmd={control_cmd}, sub={sub_cmd}, param=0x{param:X}")
        return self._create_packet(request_body, 1)

    def create_sound_config_request(self, config_id=56, sound_count=1):
        """创建音效配置请求 - 基于log中的音效配置"""
        # 基于log: 11,0,56,0,1 (命令11子功能56, 音效数量1)
        request_body = struct.pack('>H', 11) + struct.pack('>H', config_id) + struct.pack('>H', sound_count) + bytes([0xA5] * 10)
        logger.info(f"🎼 创建音效配置请求: config_id={config_id}, count={sound_count}")
        return self._create_packet(request_body, 1)

    def create_bulk_sound_config_request(self):
        """创建批量音效配置请求 - 模拟log中的大量音效配置"""
        # 构建音效配置数据 (基于log中解密后的音效ID列表)
        sound_configs = [
            (27, 1), (107, 2), (3, 4), (4, 1), (5, 588), (12, 100), (17, 38), (18, 28), (19, 1),
            (20, 1642), (21, 0xFFFF), (22, 0xFFFF), (23, 0xFFFF), (24, 0xFFFF), (25, 44), (26, 1), (27, 69)
        ]

        config_data = struct.pack('>H', 11) + struct.pack('>H', 56) + struct.pack('>H', len(sound_configs))
        for sound_id, value in sound_configs[:8]:  # 限制数据长度避免过长
            config_data += struct.pack('>H', sound_id) + struct.pack('>H', value)

        # 填充到16字节对齐
        padding_needed = (16 - len(config_data) % 16) % 16
        if padding_needed > 0:
            config_data += bytes([0xA5] * padding_needed)

        logger.info(f"🎼 创建批量音效配置: {len(sound_configs)}个配置项")
        return self._create_packet(config_data, 1)

    async def disconnect(self):
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            logger.info("🔌 已断开连接")


async def main():
    client = ThorClient()
    try:
        if not await client.scan_and_connect(): return

        await asyncio.sleep(2)

        # 1. Hardware Request
        client.hardware_info = None
        await client.send_request("获取硬件信息", client.create_hardware_request)
        for _ in range(5):
            if client.hardware_info: break
            await asyncio.sleep(1)
        if not client.hardware_info:
            logger.error("\n❌ 硬件信息获取失败!")
            return

        # 2. Handshake
        client.handshake_complete = False
        await client.send_request("执行握手", client.create_handshake_request, secrets.token_bytes(8))
        for _ in range(5):
            if client.handshake_complete: break
            await asyncio.sleep(1)
        if not client.handshake_complete:
            logger.error("\n❌ 握手失败!")
            return

        # 3. Get SGU Sounds
        client.sgu_sounds_complete = False
        await client.send_request("读取SGU音效列表", client.create_sgu_sounds_request)
        for _ in range(10):
            if client.sgu_sounds_complete: break
            await asyncio.sleep(1)

        if not client.sgu_sounds_complete:
            logger.error("\n❌ SGU音效列表读取失败!")
            return

        # 4. Get Device Parameters (第二个命令)
        client.device_params_complete = False
        await client.send_request("读取设备参数", client.create_device_parameters_request)
        for _ in range(10):
            if client.device_params_complete: break
            await asyncio.sleep(1)

        # === 扩展通信流程 - 基于CryptoManager日志分析 ===
        logger.info("\n🔄 开始扩展通信流程测试长期连接稳定性...")

        # 5. Upload Start Request (Command 7) - 模拟音频包上传开始
        client.upload_start_complete = False
        await client.send_request("上传开始请求", client.create_upload_start_request, 1, 1, b'\x04\x00\x00\x00\x00\x00\x20\x00', 4)
        for _ in range(10):
            if client.upload_start_complete: break
            await asyncio.sleep(1)

        # 6. Audio Control Request (Command 11) - 音频控制
        client.audio_control_complete = False
        await client.send_request("音频控制请求", client.create_audio_control_request, 1, 100)
        for _ in range(10):
            if client.audio_control_complete: break
            await asyncio.sleep(1)

        # 7. Configuration Request (Command 13) - 设备配置
        client.configuration_complete = False
        await client.send_request("设备配置请求", client.create_configuration_request, 1)
        for _ in range(10):
            if client.configuration_complete: break
            await asyncio.sleep(1)

        # 显示最终结果
        logger.info("\n" + "🎊" * 30)
        logger.info("🎊   扩展Thor通信流程完成! 长期连接连贯性测试   🎊")
        logger.info("🎊" * 30)
        logger.info(f"🔊 SGU音效: 发现 {client.sgu_sounds['total_sounds']} 个音效")
        logger.info(f"🎵 音效ID列表: {client.sgu_sounds['sound_ids']}")

        # 统计各阶段成功情况
        stages = [
            ("设备参数", client.device_params_complete),
            ("上传开始", client.upload_start_complete),
            ("音频控制", client.audio_control_complete),
            ("设备配置", client.configuration_complete)
        ]

        for stage_name, success in stages:
            status = "✅ 成功" if success else "❌ 失败"
            logger.info(f"⚙️ {stage_name}: {status}")

        success_count = sum(1 for _, success in stages if success)
        logger.info(f"\n📊 扩展通信成功率: {success_count}/{len(stages)} ({success_count/len(stages)*100:.1f}%)")

    except Exception as e:
        logger.error(f"❌ 程序顶层错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await client.disconnect()


if __name__ == "__main__":
    logger.add("thor_test_v2_fixed.log", rotation="5 MB")
    asyncio.run(main())