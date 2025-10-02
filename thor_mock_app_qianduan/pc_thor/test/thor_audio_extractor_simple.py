import asyncio
import struct
import secrets
import time
import os
from datetime import datetime

from bleak import BleakClient, BleakScanner
from Crypto.Cipher import AES
from loguru import logger

# Thor BLE UUIDs
SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"

# Constants
PREAMBLE = -23206


def take_short(first_byte, second_byte):
    """将两个字节组合成16位有符号整数（大端序）"""
    return struct.unpack('>h', bytes([first_byte, second_byte]))[0]


def parse_read_sgu_sounds_response_data(decrypted_data):
    """解析SGU音效列表数据"""
    logger.info("\n" + "=" * 60)
    logger.info("🎵 解析SGU音效列表数据")
    logger.info("=" * 60)

    if len(decrypted_data) < 4:
        logger.error("❌ 数据长度不足，无法解析SGU音效列表")
        return None

    # 解析音效数量
    sounds_amount_from_header = take_short(decrypted_data[2], decrypted_data[3])
    logger.info(f"🔢 设备报告的音效数量: {sounds_amount_from_header}")

    # 解析音效列表
    sound_ids_list = []
    enabled_ids = []
    disabled_ids = []
    current_byte_index = 4
    parsed_sound_count = 0

    logger.info(f"🎵 开始解析音效:")
    while parsed_sound_count < sounds_amount_from_header and (current_byte_index + 1) < len(decrypted_data):
        # 检查填充区域
        if decrypted_data[current_byte_index] == 0xA5:
            logger.info(f"📍 到达填充区域，位置: {current_byte_index}")
            break

        # 解析音效ID和状态
        real_sound_id = decrypted_data[current_byte_index]
        sound_status = decrypted_data[current_byte_index + 1]
        sound_id_combined = take_short(decrypted_data[current_byte_index], decrypted_data[current_byte_index + 1])

        sound_ids_list.append(sound_id_combined)
        status_text = "✅启用" if sound_status == 1 else "❌禁用"

        logger.info(f"🎵 #{parsed_sound_count + 1:2d}: ID={real_sound_id:3d} {status_text}")

        if sound_status == 1:
            enabled_ids.append(real_sound_id)
        elif sound_status == 0:
            disabled_ids.append(real_sound_id)

        current_byte_index += 2
        parsed_sound_count += 1

    logger.info(f"📊 解析完成: 总数{len(sound_ids_list)}, 启用{len(enabled_ids)}, 禁用{len(disabled_ids)}")
    logger.info("=" * 60)

    return {
        'total_sounds': len(sound_ids_list),
        'sound_ids_combined': sound_ids_list,
        'enabled_sounds': enabled_ids,
        'disabled_sounds': disabled_ids,
        'real_sound_ids': [decrypted_data[4 + i * 2] for i in range(len(sound_ids_list))] if sound_ids_list else []
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


class ThorAudioExtractor:
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

        # 命令响应状态
        self.current_command_response = None
        self.current_response_complete = False

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

                if len(self.response_data) < 4:
                    return

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
                logger.error(f"❌ 解析循环中出现错误: {e}")
                if len(self.response_data) > 0:
                    self.response_data = self.response_data[1:]
                else:
                    break

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
        if len(data) < 8:
            return
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

        logger.info(f"🔍 解密后数据: {decrypted_data.hex().upper()}")

        if len(decrypted_data) >= 2:
            cmd = take_short(decrypted_data[0], decrypted_data[1])

            if cmd == 8:  # COMMAND_POILING_REQUEST响应 (心跳)
                logger.info("💓 收到心跳响应")
                if len(decrypted_data) >= 4:
                    status = struct.unpack('>H', decrypted_data[2:4])[0]
                    logger.info(f"   心跳状态: {status}")
                    self.current_command_response = {'command': cmd, 'success': status == 0, 'status': status}
                else:
                    self.current_command_response = {'command': cmd, 'success': True}
                self.current_response_complete = True

            elif cmd == 9:  # COMMAND_READ_DEVICE_PARAMETERS响应
                logger.info("📊 收到设备参数响应")
                logger.info(f"   完整解密数据: {decrypted_data.hex().upper()}")

                if len(decrypted_data) >= 4:
                    param_count = struct.unpack('>H', decrypted_data[2:4])[0]
                    logger.info(f"   参数数量: {param_count}")

                    data_part = decrypted_data[4:]
                    logger.info(f"   参数数据部分: {data_part.hex().upper()} (长度: {len(data_part)}字节)")

                    parameters = []

                    if len(data_part) >= param_count * 4:
                        logger.info("   尝试解析为 ID-Value 对格式:")
                        for i in range(param_count):
                            param_start = i * 4
                            if param_start + 3 < len(data_part):
                                param_id = struct.unpack('>H', data_part[param_start:param_start + 2])[0]
                                param_value = struct.unpack('>H', data_part[param_start + 2:param_start + 4])[0]
                                parameters.append({'id': param_id, 'value': param_value})
                                logger.info(f"     参数{i + 1}: ID={param_id}, Value={param_value}")

                    # 分析设备状态标志
                    if param_count == 1 and len(data_part) == 4:
                        param_value = struct.unpack('>I', data_part)[0]
                        logger.info(f"   设备状态值: {param_value} (0x{param_value:08X})")

                        device_flags = {
                            1: "NEED_RELOAD_SOUND_PACKAGE_RULES",
                            2: "NEED_RELOAD_CAN_PARAMETERS",
                            4: "NEED_RELOAD_PARAMETERS_OF_ALL_SAMPLES_AND_SOUND_PACKAGES",
                            5: "NEED_UPDATE_FIRMWARE",
                            8: "RECOMMENDED_TO_RETURN_TO_FACTORY_SETTINGS",
                            16: "CAN_HAS_NOT_ENGINE_SPEED_DATA",
                            32: "DEVICE_LOCKED"
                        }

                        for flag_val, flag_name in device_flags.items():
                            if param_value & flag_val:
                                logger.info(f"     🟢 {flag_name}")

                        if param_value == 16:
                            logger.info("     ✅ 设备状态正常，仅CAN无引擎转速数据")

                    self.current_command_response = {
                        'command': cmd,
                        'success': True,
                        'param_count': param_count,
                        'parameters': parameters,
                        'raw_data': data_part.hex().upper()
                    }
                else:
                    self.current_command_response = {'command': cmd, 'success': False}
                self.current_response_complete = True

            elif cmd == 36:  # SGU音效列表响应
                result = parse_read_sgu_sounds_response_data(decrypted_data)
                if result:
                    logger.info("✅ SGU音效列表响应解析完成!")
                    self.current_command_response = {
                        'command': cmd,
                        'success': True,
                        'sounds_data': result
                    }
                else:
                    self.current_command_response = {'command': cmd, 'success': False}
                self.current_response_complete = True

            else:
                logger.info(f"💬 收到其他命令响应: {cmd}")
                self.current_command_response = {
                    'command': cmd,
                    'success': True,
                    'raw_data': decrypted_data.hex().upper()
                }
                self.current_response_complete = True
        else:
            logger.warning("⚠️  解密数据长度不足，无法识别响应类型。")

    def _create_packet(self, data_body, crypto_type):
        if crypto_type == 1:  # 加密
            if not self.crypto:
                logger.error("❌ 加密器未初始化，无法创建加密包!")
                return None

            total_data_length_for_padding = 1 + len(data_body)
            padding_needed = (16 - (total_data_length_for_padding % 16)) % 16

            if padding_needed == 0 and total_data_length_for_padding > 0:
                padding_needed = 16
            elif total_data_length_for_padding == 0:
                padding_needed = 16

            padding = bytes([0xA5] * padding_needed)
            pre_encrypt_data = bytes([padding_needed]) + data_body + padding

            logger.info('加密之前.....{}'.format(pre_encrypt_data))

            data_part = self.crypto.encrypt(pre_encrypt_data)
        else:
            data_part = data_body

        preamble = struct.pack('>h', PREAMBLE)
        header = struct.pack('>H', ((crypto_type & 0x7) << 13) | (len(data_part) & 0x1FFF))
        packet_without_crc = preamble + header + data_part
        crc_bytes = self.crc_calculator.create_checksum_part(self.crc_calculator.calculate(packet_without_crc))

        return packet_without_crc + crc_bytes

    async def send_command_and_wait(self, command_name, command_data, crypto_type=1, timeout=10):
        """发送命令并等待响应"""
        if not self.client or not self.client.is_connected:
            logger.error("❌ 设备未连接")
            return None

        # 清空响应状态
        self.current_command_response = None
        self.current_response_complete = False

        # 创建并发送命令
        packet = self._create_packet(command_data, crypto_type)
        if not packet:
            logger.error(f"❌ 无法创建{command_name}命令包")
            return None

        logger.info(f"📤 发送{command_name}命令: {packet.hex().upper()}")

        try:
            await self.client.write_gatt_char(WRITE_UUID, packet)
            logger.info(f"✅ {command_name}命令已发送")

            # 等待响应
            for i in range(timeout):
                await asyncio.sleep(1)
                if self.current_response_complete:
                    return self.current_command_response
                if i % 2 == 0:
                    logger.info(f"⏳ 等待{command_name}响应... ({i + 1}/{timeout})")

            logger.error(f"⏰ {command_name}响应超时")
            return None

        except Exception as e:
            logger.error(f"❌ 发送{command_name}命令失败: {e}")
            return None

    async def test_working_commands(self):
        """测试已验证可用的命令"""
        logger.info("\n" + "🧪" * 60)
        logger.info("Thor设备可用命令测试")
        logger.info("🧪" * 60)

        test_results = {}

        # 测试1: 心跳命令
        logger.info("💓 测试心跳命令")
        heartbeat_data = struct.pack('>H', 8)
        response = await self.send_command_and_wait("HEARTBEAT", heartbeat_data, timeout=5)
        test_results['heartbeat'] = response is not None and response.get('success', False)
        logger.info(f"   心跳测试: {'✅成功' if test_results['heartbeat'] else '❌失败'}")

        # 测试2: 设备参数读取
        logger.info("📊 测试设备参数读取")
        device_params_data = struct.pack('>H', 9)
        response = await self.send_command_and_wait("DEVICE_PARAMETERS", device_params_data, timeout=5)
        test_results['device_params'] = response is not None and response.get('success', False)
        logger.info(f"   参数读取: {'✅成功' if test_results['device_params'] else '❌失败'}")

        # 测试3: SGU音效列表
        logger.info("🎵 测试SGU音效列表")
        sgu_data = struct.pack('>H', 36)
        response = await self.send_command_and_wait("SGU_SOUNDS", sgu_data, timeout=8)
        test_results['sgu_sounds'] = response is not None and response.get('success', False)
        logger.info(f"   音效列表: {'✅成功' if test_results['sgu_sounds'] else '❌失败'}")

        # 测试4：测试58 (READ_SETTINGS)
        logger.info('📋 测试读取设备设置')
        read_setting_data = struct.pack('>H', 58)
        response = await self.send_command_and_wait('READ_SETTINGS', read_setting_data, timeout=8)
        test_results['read_settings'] = response is not None and response.get('success', False)
        logger.info('   设置读取: {}'.format('✅成功' if test_results['read_settings'] else '❌失败'))

        if test_results['sgu_sounds'] and response:
            sounds_data = response.get('sounds_data', {})
            enabled_sounds = sounds_data.get('enabled_sounds', [])
            if enabled_sounds:
                logger.info(f"   🎵 发现可用音效ID: {enabled_sounds}")

        success_count = sum(test_results.values())
        logger.info(f"\n📊 测试结果: {success_count}/4 个命令成功")

        return test_results

    async def run_basic_test(self):
        """运行基础测试流程"""
        logger.info("\n🚀 启动Thor设备基础功能测试...")

        try:
            # 连接设备
            logger.info("🔗 第1步: 连接设备")
            if not await self.scan_and_connect():
                logger.error("❌ 设备连接失败")
                return False

            await asyncio.sleep(2)

            # 获取硬件信息
            logger.info("🔧 第2步: 获取硬件信息")
            hardware_cmd = struct.pack('>H', 1)
            packet = self._create_packet(hardware_cmd, 0)
            await self.client.write_gatt_char(WRITE_UUID, packet)

            for _ in range(5):
                if self.hardware_info:
                    break
                await asyncio.sleep(1)

            if not self.hardware_info:
                logger.error("❌ 硬件信息获取失败")
                return False

            # 执行握手
            logger.info("🤝 第3步: 执行握手")
            self.client_iv = secrets.token_bytes(8)
            handshake_packet = self._create_packet(self.client_iv, 2)
            await self.client.write_gatt_char(WRITE_UUID, handshake_packet)

            for _ in range(5):
                if self.handshake_complete:
                    break
                await asyncio.sleep(1)

            if not self.handshake_complete:
                logger.error("❌ 握手失败")
                return False

            # 测试可用命令
            logger.info("🧪 第4步: 测试可用命令")
            test_results = await self.test_working_commands()

            # 显示结果
            success_count = sum(test_results.values())
            logger.info(f"\n🎉 测试完成! 成功 {success_count}/4 个功能")
            logger.info("📋 可用功能:")
            if test_results.get('heartbeat'):
                logger.info("   ✅ 心跳监控")
            if test_results.get('device_params'):
                logger.info("   ✅ 设备参数读取")
            if test_results.get('sgu_sounds'):
                logger.info("   ✅ 音效系统访问")
            if test_results.get('read_settings'):
                logger.info("   ✅ 设备设置读取")

            return success_count > 0

        except Exception as e:
            logger.error(f"❌ 测试过程中发生错误: {e}")
            return False

    async def disconnect(self):
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            logger.info("🔌 已断开连接")


async def main():
    """主程序入口"""
    logger.info("🚀 启动Thor设备简化测试程序...")

    extractor = ThorAudioExtractor()

    try:
        success = await extractor.run_basic_test()

        if success:
            logger.info("🎉 Thor设备测试程序执行成功!")
        else:
            logger.info("⚠️  Thor设备测试遇到问题!")

    except KeyboardInterrupt:
        logger.info("⚠️  用户中断程序")
    except Exception as e:
        logger.error(f"❌ 程序执行异常: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await extractor.disconnect()


if __name__ == "__main__":
    # 配置日志
    log_file = f"thor_simple_test_{datetime.now().strftime('%Y%m%d_%H%M%S')}.log"
    logger.add(log_file, rotation="10 MB")

    # 运行程序
    asyncio.run(main())
