#!/usr/bin/env python3
"""
分析Frida拦截的官方应用加密数据
"""

import struct

def analyze_frida_crypto_data():
    """分析官方应用的加密前数据"""
    print("🔍 分析官方应用Frida拦截的加密数据")
    print("=" * 70)
    
    # Frida拦截的原始数据（Java字节数组，有符号）
    frida_data = [7, 0, -128, 8, 0, 0, 0, 0, -56, -91, -91, -91, -91, -91, -91, -91]
    
    # 转换为无符号字节
    unsigned_bytes = []
    for b in frida_data:
        if b < 0:
            unsigned_bytes.append(256 + b)
        else:
            unsigned_bytes.append(b)
    
    data_bytes = bytes(unsigned_bytes)
    
    print(f"📝 原始Frida数据: {frida_data}")
    print(f"🔄 转换为无符号:   {unsigned_bytes}")
    print(f"📊 十六进制格式:   {data_bytes.hex().upper()}")
    print(f"📏 数据长度:       {len(data_bytes)} 字节")
    
    print("\n" + "🧩 数据结构分析:")
    print("-" * 50)
    
    # 按照Thor加密前数据格式分析
    if len(data_bytes) >= 1:
        padding_length = data_bytes[0]
        print(f"🔸 填充长度字节:   {padding_length}")
        
        if len(data_bytes) > padding_length:
            # 提取命令数据（去掉填充长度字节和尾部填充）
            command_data = data_bytes[1:len(data_bytes)-padding_length]
            padding_data = data_bytes[len(data_bytes)-padding_length:]
            
            print(f"🔸 命令数据部分:   {command_data.hex().upper()} ({len(command_data)} 字节)")
            print(f"🔸 填充数据部分:   {padding_data.hex().upper()} ({len(padding_data)} 字节)")
            
            # 分析命令
            if len(command_data) >= 2:
                command_id = struct.unpack('>H', command_data[0:2])[0]
                print(f"🔸 命令ID:         {command_id} (0x{command_id:04X})")
                
                # 查找对应的命令名称
                command_names = {
                    8: "COMMAND_POILING_REQUEST (心跳)",
                    9: "COMMAND_READ_DEVICE_PARAMETERS (读取设备参数)",
                    36: "COMMAND_READ_SGU_SOUNDS (读取SGU音效)",
                    58: "COMMAND_READ_SETTINGS (读取设备设置)",
                    128: "COMMAND_UPLOAD_START (开始上传)",
                    129: "COMMAND_UPLOAD_READ_BLOCK (读取上传块)"
                }
                
                command_name = command_names.get(command_id, f"未知命令 ({command_id})")
                print(f"🔸 命令名称:       {command_name}")
                
                # 分析参数
                if len(command_data) > 2:
                    params = command_data[2:]
                    print(f"🔸 命令参数:       {params.hex().upper()} ({len(params)} 字节)")
                    
                    # 特殊命令参数分析
                    if command_id == 128 and len(params) >= 6:  # UPLOAD_START
                        file_size = struct.unpack('>I', params[0:4])[0]
                        device_type = struct.unpack('>H', params[4:6])[0]
                        print(f"  📁 文件大小:     {file_size} 字节")
                        print(f"  🔧 设备类型:     {device_type}")
                    elif command_id == 129 and len(params) >= 2:  # UPLOAD_READ_BLOCK
                        block_id = struct.unpack('>H', params[0:2])[0]
                        print(f"  📦 块ID:         {block_id}")
            
            # 验证填充
            expected_padding = bytes([0xA5] * padding_length)
            if padding_data == expected_padding:
                print(f"✅ 填充验证:       正确 (0xA5 x {padding_length})")
            else:
                print(f"❌ 填充验证:       错误，期望0xA5，实际{padding_data.hex()}")
    
    print("\n" + "🎯 关键发现:")
    print("-" * 50)
    
    # 特别分析 -128 这个值
    if -128 in frida_data:
        pos = frida_data.index(-128)
        print(f"🔴 发现-128(0x80)在位置{pos}，这可能是:")
        print("   1. 某个参数的高位字节")
        print("   2. 文件大小或块ID的一部分")
        print("   3. 错误码或状态标识")
    
    # 查找特殊模式
    if frida_data.count(-91) >= 4:
        print(f"🟡 发现大量0xA5填充字节({frida_data.count(-91)}个)")
        print("   这符合Thor AES加密前的填充模式")

def compare_with_our_implementation():
    """与我们的实现进行对比"""
    print("\n" + "🔄 与我们实现的对比分析:")
    print("=" * 70)
    
    # 我们的READ_SETTINGS命令格式
    our_command = struct.pack('>H', 58)  # 0x003A
    padding_needed = 16 - ((1 + len(our_command)) % 16)
    if padding_needed == 0:
        padding_needed = 16
    
    our_data = bytes([padding_needed]) + our_command + bytes([0xA5] * padding_needed)
    
    print(f"📤 我们的实现:     {our_data.hex().upper()}")
    print(f"📥 官方应用:       070080080000000c8a5a5a5a5a5a5a5a5")  # 转换后的格式
    
    print("\n🔍 差异分析:")
    print("1. 官方应用可能使用了不同的命令参数")
    print("2. 官方应用可能有额外的协议字段")
    print("3. 版本差异或特殊的认证信息")

if __name__ == "__main__":
    analyze_frida_crypto_data()
    compare_with_our_implementation()