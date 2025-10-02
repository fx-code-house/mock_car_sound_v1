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
    return struct.unpack('>h', bytes([first_byte, second_byte]))[0]


# ==================== vvv 关键修正：修复音效列表解析 vvv ====================
def parse_sgu_sound_list_final(decrypted_data):
    """
    最终修正版的SGU音效列表解析函数。
    """
    logger.info("\n" + "=" * 80)
    logger.info("🎵 (最终修正版) 解析SGU音效列表...")
    logger.info("=" * 80)

    if not decrypted_data or len(decrypted_data) < 4:
        logger.error("❌ 音效列表数据长度不足或为空")
        return {'total_sounds': 0, 'enabled_sounds': [], 'disabled_sounds': []}

    sounds_amount = take_short(decrypted_data[2], decrypted_data[3])
    logger.info(f"🔢 设备报告的音效数量: {sounds_amount}")

    estimated_sounds = (len(decrypted_data) - 4) // 2
    actual_sounds_to_parse = min(sounds_amount, estimated_sounds, 150)

    if actual_sounds_to_parse <= 0:
        logger.info("📊 设备报告了0个音效，这是一个有效的成功响应。")
        return {'total_sounds': 0, 'enabled_sounds': [], 'disabled_sounds': []}

    enabled_ids, disabled_ids = [], []
    i = 4  # 数据从第4个字节开始
    parsed_count = 0
    while parsed_count < actual_sounds_to_parse and i < len(decrypted_data) - 1:
        # ### 最终修正：正确的字节顺序 ###
        # 根据日志解密数据 `...01130114...` 推断，正确的顺序是：
        # 第一个字节 (i) 是状态，第二个字节 (i+1) 是ID。
        sound_status = decrypted_data[i]
        sound_id_value = decrypted_data[i + 1]

        # 填充字节 0xA5 不是一个有效的状态
        if sound_status == 0xA5:
            logger.info(f"📍 到达填充区域，位置: {i}，停止解析。")
            break

        status_text = "✅启用" if sound_status == 1 else "❌禁用"
        logger.info(f"   解析到音效 #{parsed_count + 1}: ID={sound_id_value:3d} (Status={sound_status}, {status_text})")

        if sound_status == 1:
            enabled_ids.append(sound_id_value)
        else:
            disabled_ids.append(sound_id_value)

        i += 2
        parsed_count += 1

    logger.info(f"📊 成功解析音效数量: {parsed_count}")
    logger.info(f"✅ 启用的音效ID ({len(enabled_ids)}个): {sorted(enabled_ids)}")
    logger.info(f"❌ 禁用的音效ID ({len(disabled_ids)}个): {sorted(disabled_ids)}")
    logger.info("=" * 80)

    return {
        'total_sounds': parsed_count,
        'enabled_sounds': sorted(enabled_ids),
        'disabled_sounds': sorted(disabled_ids),
    }


# ==================== ^^^ 关键修正：修复音效列表解析 ^^^ ====================


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

        # 收到加密数据后，我们假定它就是SGU音效列表的响应
        result = parse_sgu_sound_list_final(decrypted_data)

        if isinstance(result, dict):
            logger.info("✅ SGU音效列表解析流程完成!")
            self.sgu_sounds = result
            self.sgu_sounds_complete = True  # 标记成功
        else:
            logger.warning("⚠️  SGU音效列表解析失败，可能不是预期的响应包。")

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

        if client.sgu_sounds_complete:
            logger.info("\n" + "🎊" * 20)
            logger.info("🎊   完整的Thor通信流程成功!   🎊")
            logger.info("🎊" * 20)
            logger.info(
                f"🔊 发现 {client.sgu_sounds['total_sounds']} 个音效, {len(client.sgu_sounds['enabled_sounds'])} 个已启用。")
        else:
            logger.error("\n❌ SGU音效列表读取失败!")

    except Exception as e:
        logger.error(f"❌ 程序顶层错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await client.disconnect()


if __name__ == "__main__":
    logger.add("thor_test.log", rotation="5 MB")
    asyncio.run(main())