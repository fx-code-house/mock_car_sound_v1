#!/usr/bin/env python3
"""
最简化Thor音效列表测试 - 修复版
目标：握手 + 读取音效列表
"""

import asyncio
import struct
import secrets
import requests
from bleak import BleakClient, BleakScanner

# 配置
SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"
WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"
PREAMBLE = -23206
API_BASE_URL = "http://localhost:8000"


class ThorCRC16:
    def __init__(self):
        self.crc_table = [0] * 256
        for i in range(256):
            crc = i
            for _ in range(8):
                if crc & 1:
                    crc = (crc >> 1) ^ 0xA001
                else:
                    crc >>= 1
            self.crc_table[i] = crc & 0xFFFF

    def calculate(self, data):
        crc = 0xFFFF
        for byte in data:
            tbl_idx = (crc ^ (byte & 0xFF)) & 0xFF
            crc = ((crc >> 8) ^ self.crc_table[tbl_idx]) & 0xFFFF
        return crc

    def create_checksum_part(self, crc_value):
        return bytes([crc_value & 0xFF, (crc_value >> 8) & 0xFF])


class MinimalThorClient:
    def __init__(self):
        self.client = None
        self.crc = ThorCRC16()
        self.response_data = bytearray()
        
        # 状态
        self.hardware_info = None
        self.client_iv = None
        self.device_iv = None
        self.handshake_complete = False
        self.sound_list_complete = False
        self.sound_list = None

    async def connect(self):
        """连接设备"""
        print("🔍 扫描设备...")
        devices = await BleakScanner.discover(timeout=10.0)
        
        for device in devices:
            if device.name and "thor" in device.name.lower():
                print(f"✅ 找到设备: {device.name}")
                self.client = BleakClient(device.address)
                await self.client.connect()
                await self.client.start_notify(NOTIFY_UUID, self.on_notify)
                print("🔗 已连接并启用通知")
                return True
        
        print("❌ 未找到Thor设备")
        return False

    def on_notify(self, sender, data):
        """处理通知"""
        print(f"📨 收到: {data.hex().upper()}")
        self.response_data.extend(data)
        self.parse_response()

    def parse_response(self):
        """解析响应"""
        if len(self.response_data) < 6:
            return

        # 查找前导码
        for i in range(len(self.response_data) - 1):
            if self.response_data[i] == 0xA5 and self.response_data[i + 1] == 0x5A:
                packet = self.response_data[i:]
                if self.process_packet(packet):
                    self.response_data.clear()
                break

    def process_packet(self, data):
        """处理数据包"""
        if len(data) < 6:
            return False

        try:
            # 解析包头
            preamble = struct.unpack('>H', data[0:2])[0]
            if preamble != 0xA55A:
                return False

            header = struct.unpack('>H', data[2:4])[0]
            enc_type = (header >> 13) & 0x7
            data_len = header & 0x1FFF

            if len(data) < 4 + data_len + 2:
                return False

            payload = data[4:4 + data_len]
            crc_bytes = data[4 + data_len:4 + data_len + 2]

            # 验证CRC
            crc_recv = struct.unpack('<H', crc_bytes)[0]
            crc_calc = self.crc.calculate(data[:4 + data_len])
            
            if crc_recv != crc_calc:
                crc_recv = struct.unpack('>H', crc_bytes)[0]
                if crc_recv != crc_calc:
                    print("❌ CRC失败")
                    return False

            print(f"✅ CRC成功, 类型:{enc_type}")

            # 处理不同类型
            if enc_type == 0:      # 硬件响应
                self.handle_hardware(payload)
            elif enc_type == 2:    # 握手响应
                self.handle_handshake(payload)
            elif enc_type == 1:    # 加密数据
                self.handle_encrypted(payload)

            return True

        except Exception as e:
            print(f"❌ 包处理失败: {e}")
            return False

    def handle_hardware(self, data):
        """处理硬件响应"""
        if len(data) >= 8:
            hw = struct.unpack('>H', data[2:4])[0]
            fw = struct.unpack('>H', data[4:6])[0]
            sn = struct.unpack('>H', data[6:8])[0]
            
            self.hardware_info = {'hw': hw, 'fw': fw, 'sn': sn}
            print(f"🔧 硬件: HW={hw}, FW={fw}, SN={sn}")

    def handle_handshake(self, data):
        """处理握手响应"""
        if len(data) >= 8:
            self.device_iv = bytes(data[:8])
            print(f"🔑 设备IV: {self.device_iv.hex().upper()}")
            
            if self.client_iv and self.hardware_info:
                combined_iv = self.client_iv + self.device_iv
                print(f"🔐 合并IV: {combined_iv.hex().upper()}")
                
                # 调用API初始化
                try:
                    print(f"🔄 调用API初始化...")
                    response = requests.post(f"{API_BASE_URL}/init", json={
                        'iv': combined_iv.hex().upper(),
                        'hw': self.hardware_info['hw'],
                        'fw': self.hardware_info['fw'],
                        'sn': self.hardware_info['sn']
                    }, timeout=5)
                    
                    print(f"📡 API响应状态: {response.status_code}")
                    result = response.json()
                    print(f"📡 API响应内容: {result}")
                    
                    if result.get('success'):
                        self.handshake_complete = True
                        print("✅ 握手完成!")
                    else:
                        print(f"❌ API初始化失败: {result.get('error')}")
                        
                except Exception as e:
                    print(f"❌ API调用失败: {e}")
                    import traceback
                    traceback.print_exc()

    def handle_encrypted(self, encrypted_data):
        """处理加密数据"""
        print(f"🔓 解密数据长度: {len(encrypted_data)}")
        
        try:
            # 调用API解密
            print(f"🔄 调用API解密，数据长度: {len(encrypted_data)}字节")
            response = requests.post(f"{API_BASE_URL}/encrypt", json={
                'hex': encrypted_data.hex().upper()
            }, timeout=5)
            
            print(f"📡 解密API响应状态: {response.status_code}")
            result = response.json()
            print(f"📡 解密API响应: {result}")
            
            if result.get('success'):
                decrypted_hex = result['encrypted']
                decrypted_raw = bytes.fromhex(decrypted_hex)
                print(f"🔓 解密成功: {decrypted_raw.hex().upper()}")
                
                # 处理解密数据
                if len(decrypted_raw) > 0:
                    padding_len = decrypted_raw[0]
                    start = 1
                    end = len(decrypted_raw) - padding_len
                    
                    print(f"📊 解密数据处理: 填充长度={padding_len}, 范围={start}-{end}")
                    
                    if end > start:
                        final_data = decrypted_raw[start:end]
                        print(f"✅ 最终数据: {final_data.hex().upper()}")
                        
                        # 解析音效列表
                        if len(final_data) >= 4:
                            cmd = struct.unpack('>H', final_data[0:2])[0]
                            print(f"📋 命令: {cmd}")
                            
                            if cmd == 36:  # 音效列表响应
                                self.parse_sound_list(final_data)
                            else:
                                print(f"❓ 未知命令响应: {cmd}")
                        else:
                            print(f"❌ 最终数据太短: {len(final_data)}字节")
                    else:
                        print(f"❌ 数据范围无效: start={start}, end={end}")
                else:
                    print("❌ 解密后数据为空")
                                
            else:
                print(f"❌ 解密失败: {result.get('error')}")
                
        except Exception as e:
            print(f"❌ 解密异常: {e}")
            import traceback
            traceback.print_exc()

    def parse_sound_list(self, data):
        """解析音效列表"""
        print(f"🎵 开始解析音效列表，数据长度: {len(data)}")
        
        if len(data) < 4:
            print("❌ 数据太短，无法解析")
            return
            
        sounds_count = struct.unpack('>h', data[2:4])[0]
        print(f"🎵 音效数量: {sounds_count}")
        
        sound_list = []
        i = 4
        
        for idx in range(sounds_count):
            if i + 1 < len(data):
                sound_id = data[i]
                status = data[i + 1]
                sound_list.append({'id': sound_id, 'enabled': status == 1})
                
                status_text = "启用" if status == 1 else "禁用"
                print(f"🎵 #{idx+1}: ID={sound_id} {status_text}")
                
                i += 2
            else:
                print(f"❌ 数据不足，停止解析在第{idx+1}个音效")
                break
        
        self.sound_list = sound_list
        self.sound_list_complete = True
        
        enabled_count = sum(1 for s in sound_list if s['enabled'])
        print(f"✅ 解析完成: 总数{len(sound_list)}, 启用{enabled_count}")

    async def send_hardware_request(self):
        """发送硬件请求"""
        print("🚀 发送硬件请求...")
        packet = bytes([165, 90, 0, 2, 0, 1, 32, 227])  # 固定硬件请求包
        await self.client.write_gatt_char(WRITE_UUID, packet)
        
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
        
        # 构建握手包
        preamble = struct.pack('>h', PREAMBLE)
        header = struct.pack('>H', (2 << 13) | 8)  # 类型2，长度8
        packet_no_crc = preamble + header + self.client_iv
        crc = self.crc.calculate(packet_no_crc)
        packet = packet_no_crc + self.crc.create_checksum_part(crc)
        
        await self.client.write_gatt_char(WRITE_UUID, packet)
        
        # 等待响应
        for i in range(10):
            await asyncio.sleep(1)
            if self.handshake_complete:
                return True
        return False

    async def send_sound_list_request(self):
        """发送音效列表请求"""
        print("🚀 发送音效列表请求...")
        
        # 构建加密数据
        command = struct.pack('>H', 36)  # 命令36
        padding_needed = 13  # 16 - ((2 + 1) % 16) = 13
        pre_encrypt = bytes([padding_needed]) + command + bytes([0xA5] * padding_needed)
        
        print(f"🔧 加密前数据: {pre_encrypt.hex().upper()}")
        
        try:
            # 调用API加密
            print(f"🔄 调用API加密...")
            response = requests.post(f"{API_BASE_URL}/encrypt", json={
                'hex': pre_encrypt.hex().upper()
            }, timeout=5)
            
            print(f"📡 加密API响应状态: {response.status_code}")
            result = response.json()
            print(f"📡 加密API响应: {result}")
            
            if result.get('success'):
                encrypted_data = bytes.fromhex(result['encrypted'])
                print(f"🔐 加密成功，长度: {len(encrypted_data)}字节")
                
                # 构建BLE包
                preamble = struct.pack('>h', PREAMBLE)
                header = struct.pack('>H', (1 << 13) | len(encrypted_data))  # 类型1
                packet_no_crc = preamble + header + encrypted_data
                crc = self.crc.calculate(packet_no_crc)
                packet = packet_no_crc + self.crc.create_checksum_part(crc)
                
                await self.client.write_gatt_char(WRITE_UUID, packet)
                print(f"📤 请求发送: {packet.hex().upper()}")
                
                # 等待响应
                print("⏳ 等待设备响应...")
                for i in range(15):
                    await asyncio.sleep(1)
                    if self.sound_list_complete:
                        return True
                    if i % 5 == 0:
                        print(f"⏳ 等待中... ({i+1}/15)")
                
                print("⏰ 15秒超时，未收到响应")
                return False
            else:
                print(f"❌ 加密请求失败: {result.get('error')}")
                return False
                
        except Exception as e:
            print(f"❌ 发送请求失败: {e}")
            import traceback
            traceback.print_exc()
            return False

    async def disconnect(self):
        """断开连接"""
        if self.client and self.client.is_connected:
            await self.client.disconnect()
            print("🔌 已断开")


async def main():
    """主程序"""
    client = MinimalThorClient()
    
    try:
        # 1. 连接
        if not await client.connect():
            return
        
        await asyncio.sleep(2)
        
        # 2. 硬件请求
        if not await client.send_hardware_request():
            print("❌ 硬件请求失败")
            return
        
        # 3. 握手
        if not await client.send_handshake_request():
            print("❌ 握手失败")
            return
        
        # 4. 音效列表
        if not await client.send_sound_list_request():
            print("❌ 音效列表请求失败")
            return
        
        # 5. 结果
        print("\n" + "=" * 50)
        print("🎊 成功!")
        print("=" * 50)
        print(f"🎵 音效总数: {len(client.sound_list)}")
        enabled = [s for s in client.sound_list if s['enabled']]
        print(f"✅ 启用音效: {len(enabled)}")
        print(f"🎵 启用ID: {[s['id'] for s in enabled]}")
        print("=" * 50)
        
    except Exception as e:
        print(f"❌ 程序错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        await client.disconnect()


if __name__ == "__main__":
    asyncio.run(main()) 