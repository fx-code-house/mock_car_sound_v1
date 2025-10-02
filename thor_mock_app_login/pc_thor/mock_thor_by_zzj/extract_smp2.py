#!/usr/bin/env python3
"""
Thor SMP2 文件提取脚本
从Frida日志中提取命令114的SMP2文件数据
"""
import re
import struct
from datetime import datetime


class SMP2Extractor:
    def __init__(self):
        self.blocks = {}  # 存储各个块 {block_id: data}
        self.file_header = None
        self.total_blocks = 0
        
    def parse_log_line(self, log_line):
        """解析单行Frida日志"""
        # 匹配发送给thor的加密前明文
        match = re.search(r'发送给thor的加密前明文:\s*data=(.+)', log_line)
        if not match:
            return None
            
        data_str = match.group(1).strip()
        # 转换为字节数组
        try:
            data_values = [int(x.strip()) for x in data_str.split(',')]
            # 转换负数为无符号字节
            data_bytes = [x if x >= 0 else (256 + x) for x in data_values]
            return data_bytes
        except:
            return None
    
    def parse_command_114(self, data_bytes):
        """解析命令114的数据"""
        if len(data_bytes) < 7:
            return None
            
        # 验证是否为命令114
        if data_bytes[2] != 114:  # 第3字节应该是命令114
            return None
            
        # 提取块信息
        padding_length = data_bytes[0]
        separator = data_bytes[1] 
        command = data_bytes[2]
        block_seq = (data_bytes[3] << 16) | (data_bytes[4] << 8) | data_bytes[5]  # 块序号
        data_length = data_bytes[6]  # 数据长度
        
        print(f"📦 命令114块: 序号={block_seq}, 数据长度={data_length}, 填充={padding_length}")
        
        # 提取实际数据部分 (跳过头部7字节，去除末尾填充)
        data_start = 7
        data_end = len(data_bytes) - padding_length if padding_length > 0 else len(data_bytes)
        
        if data_end <= data_start:
            print(f"⚠️  数据长度异常: start={data_start}, end={data_end}")
            return None
            
        payload = data_bytes[data_start:data_end]
        
        return {
            'block_seq': block_seq,
            'data_length': data_length,
            'payload': payload,
            'padding_length': padding_length
        }
    
    def extract_file_header(self, payload):
        """从第一个块提取文件头信息"""
        if len(payload) < 10:
            return None
            
        # 查找SMP文件头
        header_start = None
        for i in range(len(payload) - 4):
            if (payload[i] == 83 and payload[i+1] == 77 and 
                payload[i+2] == 80 and payload[i+3] == 50):  # "SMP2"
                header_start = i
                break
                
        if header_start is None:
            return None
            
        print(f"🎵 发现SMP2文件头在位置: {header_start}")
        
        # 提取文件头信息
        file_header = {
            'magic': payload[header_start:header_start+4],  # "SMP2"
            'version': payload[header_start+4:header_start+6] if len(payload) > header_start+5 else b'',
            'header_data': payload[header_start:header_start+20] if len(payload) > header_start+19 else payload[header_start:]
        }
        
        return file_header
    
    def add_block(self, block_info):
        """添加一个数据块"""
        block_seq = block_info['block_seq']
        payload = block_info['payload']
        
        # 第一个块可能包含文件头
        if block_seq == 0 and not self.file_header:
            self.file_header = self.extract_file_header(payload)
            if self.file_header:
                print(f"📋 文件头信息: {self.file_header}")
        
        # 存储块数据
        self.blocks[block_seq] = payload
        self.total_blocks = max(self.total_blocks, block_seq + 1)
        
        print(f"✅ 添加块 {block_seq}: {len(payload)} 字节")
    
    def reconstruct_file(self):
        """重组完整文件"""
        if not self.blocks:
            print("❌ 没有数据块可重组")
            return None
            
        print(f"🔧 开始重组文件，共 {len(self.blocks)} 个块...")
        
        # 按块序号排序重组
        file_data = bytearray()
        for block_seq in sorted(self.blocks.keys()):
            file_data.extend(self.blocks[block_seq])
            print(f"   添加块 {block_seq}: {len(self.blocks[block_seq])} 字节")
        
        print(f"✅ 文件重组完成，总大小: {len(file_data)} 字节")
        return bytes(file_data)
    
    def save_file(self, file_data, filename=None):
        """保存文件到磁盘"""
        if not filename:
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            filename = f"extracted_smp2_{timestamp}.smp"
        
        try:
            with open(filename, 'wb') as f:
                f.write(file_data)
            print(f"💾 文件已保存: {filename} ({len(file_data)} 字节)")
            return filename
        except Exception as e:
            print(f"❌ 保存文件失败: {e}")
            return None
    
    def analyze_data_structure(self, file_data):
        """分析文件数据结构"""
        print(f"\n📊 数据结构分析:")
        print(f"   总大小: {len(file_data)} 字节")
        
        if len(file_data) >= 4:
            magic = file_data[:4]
            if magic == b'SMP2':
                print(f"   文件类型: SMP2")
            else:
                print(f"   前4字节: {magic}")
        
        # 显示前32字节的十六进制
        if len(file_data) >= 32:
            hex_data = ' '.join(f'{b:02X}' for b in file_data[:32])
            print(f"   前32字节: {hex_data}")
        
        # 显示数据分布
        byte_counts = {}
        for byte in file_data[:1000]:  # 分析前1000字节
            byte_counts[byte] = byte_counts.get(byte, 0) + 1
        
        common_bytes = sorted(byte_counts.items(), key=lambda x: x[1], reverse=True)[:5]
        print(f"   常见字节: {common_bytes}")


def extract_from_log_file(log_file_path):
    """从日志文件中提取SMP2数据"""
    print(f"📂 读取日志文件: {log_file_path}")
    
    extractor = SMP2Extractor()
    
    try:
        with open(log_file_path, 'r', encoding='utf-8') as f:
            for line_num, line in enumerate(f, 1):
                # 解析日志行
                data_bytes = extractor.parse_log_line(line)
                if not data_bytes:
                    continue
                
                # 解析命令114
                block_info = extractor.parse_command_114(data_bytes)
                if block_info:
                    extractor.add_block(block_info)
                    
    except Exception as e:
        print(f"❌ 读取日志文件失败: {e}")
        return None
    
    # 重组文件
    file_data = extractor.reconstruct_file()
    if not file_data:
        return None
    
    # 分析数据结构
    extractor.analyze_data_structure(file_data)
    
    # 保存文件
    filename = extractor.save_file(file_data)
    return filename


def extract_from_log_text(log_text):
    """从日志文本中提取SMP2数据"""
    print(f"📝 从文本中提取数据...")
    
    extractor = SMP2Extractor()
    
    for line_num, line in enumerate(log_text.split('\n'), 1):
        # 解析日志行
        data_bytes = extractor.parse_log_line(line)
        if not data_bytes:
            continue
        
        # 解析命令114
        block_info = extractor.parse_command_114(data_bytes)
        if block_info:
            extractor.add_block(block_info)
    
    # 重组文件
    file_data = extractor.reconstruct_file()
    if not file_data:
        return None
    
    # 分析数据结构
    extractor.analyze_data_structure(file_data)
    
    # 保存文件
    filename = extractor.save_file(file_data)
    return filename


if __name__ == "__main__":
    # 示例用法
    print("🚀 Thor SMP2 文件提取工具")
    print("=" * 50)

    
    # 提取文件
    filename = extract_from_log_file('send_recv_.log')
    if filename:
        print(f"\n🎉 提取完成! 输出文件: {filename}")
    else:
        print(f"\n❌ 提取失败")
    
    print("\n💡 使用方法:")
    print("1. 从文件提取: extract_from_log_file('frida_log.txt')")
    print("2. 从文本提取: extract_from_log_text(log_content)")