#!/usr/bin/env python3

import asyncio
import struct
from bleak import BleakClient, BleakScanner

# Thor BLE UUIDs
SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"

# Constants
PREAMBLE = 0xA55A  # 从Frida日志确认: -91,90 = 0xA55A (小端序存储)


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


class ThorClient:
    def __init__(self):
        self.client = None
        self.crc_calculator = ThorCRC16()
        self.response_data = bytearray()
        self.hardware_info = None

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

        # 查找PREAMBLE
        preamble_found = False
        start_index = 0

        for i in range(len(self.response_data) - 1):
            # 检查PREAMBLE: 0xA55A -> [0xA5, 0x5A] (收到的数据是这个顺序)
            if (self.response_data[i] == 0xA5 and
                    self.response_data[i + 1] == 0x5A):
                preamble_found = True
                start_index = i
                break

        if not preamble_found:
            print("❌ 未找到有效的PREAMBLE")
            print(f"🔍 查找PREAMBLE [0xA5, 0x5A] 在数据中: {list(self.response_data[:10])}")
            return

        # 从PREAMBLE开始解析
        packet_data = self.response_data[start_index:]

        if len(packet_data) < 6:
            print("⏳ 包数据不完整...")
            return

        print(f"🎯 找到PREAMBLE，开始解析包: {list(packet_data[:min(20, len(packet_data))])}")

        # 解析包
        success = self.parse_response_packet(packet_data)

        if success:
            # 清空已处理的数据
            self.response_data.clear()

    def parse_response_packet(self, data):
        """解析响应包"""
        try:
            if len(data) < 6:
                return False

            # 解析PREAMBLE (大端序存储)
            preamble = struct.unpack('>H', data[0:2])[0]  # 大端序读取
            print(f"📋 PREAMBLE: 0x{preamble:04X}")

            # 解析加密头 (大端序，与PREAMBLE一致)
            encryption_header = struct.unpack('>H', data[2:4])[0]  # 改为大端序
            encryption_type = (encryption_header >> 12) & 0xF
            data_length = encryption_header & 0xFFF

            print(f"🔐 加密头: 0x{encryption_header:04X}")
            print(f"🔐 加密类型: {encryption_type}")
            print(f"📏 数据长度: {data_length}")

            # 计算完整包长度
            total_length = 4 + data_length + 2  # PREAMBLE + 加密头 + 数据 + CRC

            if len(data) < total_length:
                print(f"⏳ 包不完整: 需要{total_length}字节，只有{len(data)}字节")
                return False

            # 提取数据部分
            data_part = data[4:4 + data_length]

            # 提取CRC
            crc_bytes = data[4 + data_length:4 + data_length + 2]
            crc_received = struct.unpack('<H', crc_bytes)[0]

            print(f"📦 数据部分: {list(data_part)}")
            print(f"🔍 收到CRC: 0x{crc_received:04X}")

            # 验证CRC (对除CRC外的所有数据)
            crc_data = data[:4 + data_length]
            crc_calculated = self.crc_calculator.calculate(crc_data)

            print(f"🔍 计算CRC: 0x{crc_calculated:04X}")

            if crc_received == crc_calculated:
                print("✅ CRC验证成功!")

                # 根据加密类型处理数据
                if encryption_type == 0:  # 无加密
                    self.parse_hardware_response(data_part)
                else:
                    print(f"🔒 加密数据，类型: {encryption_type}")

                return True
            else:
                print("❌ CRC验证失败!")
                print("🔧 尝试继续解析数据...")
                # 即使CRC不匹配也尝试解析，可能是CRC算法差异
                if encryption_type == 0:
                    self.parse_hardware_response(data_part)
                return True

        except Exception as e:
            print(f"❌ 解析响应包失败: {e}")
            return False

    def parse_hardware_response(self, data):
        """解析硬件响应数据"""
        try:
            if len(data) < 8:
                print("❌ 硬件响应数据太短")
                return

            print(f"🔍 原始硬件响应数据: {list(data)}")
            print(f"🔍 十六进制: {data.hex().upper()}")

            # 根据实际数据分析，字段顺序是：[命令] [序列号] [固件版本] [硬件版本]
            # 使用大端序解析（与加密头一致）
            command = struct.unpack('>H', data[0:2])[0]
            serial_number = struct.unpack('>H', data[2:4])[0]
            firmware_version = struct.unpack('>H', data[4:6])[0]
            hardware_version = struct.unpack('>H', data[6:8])[0]

            print(f"\n🔍 大端序解析结果:")
            print(f"   命令: {command}")
            print(f"   序列号: {serial_number}")
            print(f"   固件版本: {firmware_version}")
            print(f"   硬件版本: {hardware_version}")

            # 验证是否与Frida日志匹配
            # Frida日志: 硬件版本=1538, 固件版本=520, 序列号=17102
            if (hardware_version == 1538 and firmware_version == 520 and serial_number == 17102):
                print("\n✅ 解析结果与Frida日志完全匹配!")
            else:
                print(f"\n⚠️  解析结果与Frida日志不匹配")
                print(f"   期望: 硬件版本=1538, 固件版本=520, 序列号=17102")
                print(f"   实际: 硬件版本={hardware_version}, 固件版本={firmware_version}, 序列号={serial_number}")

                # 如果不匹配，尝试小端序
                print(f"\n🔍 尝试小端序解析:")
                command_le = struct.unpack('<H', data[0:2])[0]
                serial_number_le = struct.unpack('<H', data[2:4])[0]
                firmware_version_le = struct.unpack('<H', data[4:6])[0]
                hardware_version_le = struct.unpack('<H', data[6:8])[0]

                print(f"   命令: {command_le}")
                print(f"   序列号: {serial_number_le}")
                print(f"   固件版本: {firmware_version_le}")
                print(f"   硬件版本: {hardware_version_le}")

                if (hardware_version_le == 1538 and firmware_version_le == 520 and serial_number_le == 17102):
                    print("✅ 小端序解析与Frida日志匹配!")
                    command, serial_number, firmware_version, hardware_version = command_le, serial_number_le, firmware_version_le, hardware_version_le

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
            print(f"🔢 序列号: {serial_number}")
            print(f"💾 固件版本: {firmware_version}")
            print(f"🔧 硬件版本: {hardware_version}")
            print("=" * 60)

            # 最终验证
            if (hardware_version == 1538 and
                    firmware_version == 520 and
                    serial_number == 17102):
                print("✅ 硬件信息与Frida日志完全匹配!")
            else:
                print("⚠️  硬件信息与Frida日志不匹配")

        except Exception as e:
            print(f"❌ 解析硬件响应失败: {e}")
            import traceback
            traceback.print_exc()

    def create_hardware_request(self):
        """创建未加密的硬件请求 - 基于Frida日志分析"""

        # 从Frida日志确认的硬件请求格式:
        # -91,90,0,2,0,1,32,-29
        # 转换有符号字节到无符号字节:
        # -91 = 256 - 91 = 165 = 0xA5
        # 90 = 90 = 0x5A
        # -29 = 256 - 29 = 227 = 0xE3
        # 所以实际字节序列是: [165, 90, 0, 2, 0, 1, 32, 227] = [0xA5, 0x5A, 0x00, 0x02, 0x00, 0x01, 0x20, 0xE3]

        print("\n🔨 创建硬件请求包...")

        # 正确转换Frida日志中的有符号字节
        frida_bytes = [-91, 90, 0, 2, 0, 1, 32, -29]
        unsigned_bytes = [(b & 0xFF) for b in frida_bytes]

        print(f"🔍 Frida日志字节 (有符号): {frida_bytes}")
        print(f"🔍 转换为无符号字节: {unsigned_bytes}")
        print(f"🔍 十六进制: {bytes(unsigned_bytes).hex().upper()}")

        # 验证我们的CRC计算
        data_without_crc = unsigned_bytes[:-2]  # 前6字节
        calculated_crc = self.crc_calculator.calculate(data_without_crc)
        expected_crc_bytes = unsigned_bytes[-2:]  # 最后2字节
        expected_crc = (expected_crc_bytes[1] << 8) | expected_crc_bytes[0]  # 小端序

        print(f"🔍 数据部分: {data_without_crc}")
        print(f"🔍 期望CRC: 0x{expected_crc:04X} (字节: {expected_crc_bytes})")
        print(f"🔍 计算CRC: 0x{calculated_crc:04X}")

        if calculated_crc == expected_crc:
            print("✅ CRC计算正确!")
        else:
            print("⚠️  CRC计算不匹配，但使用已验证的数据包")

        return bytes(unsigned_bytes)

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

            # 等待响应 (增加等待时间)
            print("⏳ 等待设备响应...")

            for i in range(10):  # 等待最多10秒
                await asyncio.sleep(1)
                if self.hardware_info:
                    print("🎉 成功获取硬件信息!")
                    return True
                print(f"⏳ 等待中... ({i + 1}/10)")

            if not self.hardware_info:
                print("⏰ 等待超时，未收到有效响应")
                if self.response_data:
                    print(f"📨 收到的原始数据: {list(self.response_data)}")
                return False

        except Exception as e:
            print(f"❌ 发送请求失败: {e}")
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

        # 发送硬件请求
        success = await client.send_hardware_request()

        if success and client.hardware_info:
            print("\n🎊 硬件信息读取成功!")
            print("📋 可以继续进行握手和加密通信...")
        else:
            print("\n❌ 硬件信息读取失败")
            print("💡 建议检查:")
            print("   1. 设备是否正确连接")
            print("   2. 数据包格式是否正确")
            print("   3. CRC计算是否匹配")

    except Exception as e:
        print(f"❌ 程序错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await client.disconnect()


if __name__ == "__main__":
    asyncio.run(main())