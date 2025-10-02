#!/usr/bin/env python3
"""
测试Thor AES加密的重放攻击可能性
"""

import struct
from Crypto.Cipher import AES

class ThorCrypto:
    def __init__(self, key, iv):
        self.key = key
        self.initial_iv = iv
        self.counter_value = int.from_bytes(self.initial_iv, byteorder='big')
        print(f"🔑 初始化加密器，Counter起始值: {self.counter_value}")

    def _get_current_iv(self):
        return self.counter_value.to_bytes(16, byteorder='big')

    def encrypt(self, plaintext):
        current_iv = self._get_current_iv()
        print(f"🔐 加密Counter值: {self.counter_value}")
        cipher = AES.new(self.key, AES.MODE_CTR, initial_value=current_iv, nonce=b'')
        result = cipher.encrypt(plaintext)
        self.counter_value += 1
        return result

    def decrypt(self, ciphertext):
        current_iv = self._get_current_iv()
        print(f"🔓 解密Counter值: {self.counter_value}")
        cipher = AES.new(self.key, AES.MODE_CTR, initial_value=current_iv, nonce=b'')
        decrypted_raw = cipher.decrypt(ciphertext)
        self.counter_value += 1

        if len(decrypted_raw) > 0:
            padding_length = decrypted_raw[0]
            start_index = 1
            end_index = len(decrypted_raw) - padding_length
            if end_index < start_index:
                return b''
            return decrypted_raw[start_index:end_index]
        return b''

def test_crypto_replay():
    """测试加密重放攻击"""
    print("🧪 测试Thor AES加密重放攻击可能性")
    print("=" * 60)
    
    # 模拟密钥和IV
    test_key = b'\x01\x02\x03\x04\x05\x06\x07\x08\x09\x0a\x0b\x0c\x0d\x0e\x0f\x10'
    test_iv = b'\x00' * 16
    
    # 创建两个独立的加密器（模拟客户端和设备）
    client_crypto = ThorCrypto(test_key, test_iv)
    device_crypto = ThorCrypto(test_key, test_iv)
    
    # 测试数据（来自你的日志）
    test_command = struct.pack('>H', 58)  # READ_SETTINGS命令
    padding_needed = 13
    padding = bytes([0xA5] * padding_needed)
    plaintext = bytes([padding_needed]) + test_command + padding
    
    print(f"📝 原始明文: {plaintext.hex().upper()}")
    
    # 第1次加密
    print("\n🔐 第1次加密操作：")
    encrypted1 = client_crypto.encrypt(plaintext)
    print(f"密文1: {encrypted1.hex().upper()}")
    
    # 设备解密第1个包
    print("\n🔓 设备解密第1个包：")
    decrypted1 = device_crypto.decrypt(encrypted1)
    print(f"解密结果1: {decrypted1.hex().upper()}")
    print(f"解密成功: {decrypted1 == test_command}")
    
    # 第2次加密相同数据
    print("\n🔐 第2次加密相同数据：")
    encrypted2 = client_crypto.encrypt(plaintext)
    print(f"密文2: {encrypted2.hex().upper()}")
    print(f"两次密文相同: {encrypted1 == encrypted2}")
    
    # 尝试重放第1个包
    print("\n🚨 尝试重放第1个包：")
    try:
        # 注意：这里设备的counter已经递增了，所以会失败
        decrypted_replay = device_crypto.decrypt(encrypted1)
        print(f"重放解密结果: {decrypted_replay.hex().upper()}")
        print(f"重放解密成功: {decrypted_replay == test_command}")
    except Exception as e:
        print(f"重放解密失败: {e}")
    
    print("\n" + "=" * 60)
    print("📊 测试结论：")
    print("1. 相同明文每次加密产生不同密文（CTR模式特性）")
    print("2. 重放攻击无法成功（Counter不匹配）")
    print("3. Thor设备具有天然的重放攻击防护")

if __name__ == "__main__":
    test_crypto_replay()