#!/usr/bin/env python3
"""
SMP2文件分析和转换工具
分析Thor SMP2音频文件格式并尝试转换为可播放格式
"""
import struct
import wave
import numpy as np
from datetime import datetime


class SMP2Analyzer:
    def __init__(self, file_path):
        self.file_path = file_path
        self.file_data = None
        self.header_info = {}
        self.audio_data = None
        
    def load_file(self):
        """加载SMP2文件"""
        try:
            with open(self.file_path, 'rb') as f:
                self.file_data = f.read()
            print(f"📂 已加载文件: {self.file_path} ({len(self.file_data)} 字节)")
            return True
        except Exception as e:
            print(f"❌ 文件加载失败: {e}")
            return False
    
    def analyze_header(self):
        """分析SMP2文件头"""
        if not self.file_data or len(self.file_data) < 20:
            print("❌ 文件数据不足")
            return False
        
        print("\n📋 SMP2文件头分析:")
        print("=" * 50)
        
        # 检查魔数
        magic = self.file_data[:4]
        if magic == b'SMP2':
            print("✅ 文件类型: SMP2")
            self.header_info['magic'] = magic
        else:
            print(f"⚠️  未知文件类型: {magic}")
            return False
        
        # 分析版本信息
        if len(self.file_data) >= 6:
            version_bytes = self.file_data[4:6]
            version = struct.unpack('<H', version_bytes)[0] if len(version_bytes) == 2 else 0
            print(f"📌 版本信息: {version}")
            self.header_info['version'] = version
        
        # 分析可能的音频参数
        self._analyze_audio_params()
        
        return True
    
    def _analyze_audio_params(self):
        """分析音频参数"""
        print("\n🎚️ 音频参数分析:")
        
        # 查找可能的采样率、声道数等参数
        if len(self.file_data) >= 20:
            # 尝试不同的参数解析
            for offset in range(6, min(50, len(self.file_data) - 4), 2):
                try:
                    value = struct.unpack('<H', self.file_data[offset:offset+2])[0]
                    if value in [8000, 11025, 16000, 22050, 44100, 48000]:  # 常见采样率
                        print(f"   可能的采样率 @{offset}: {value} Hz")
                    elif value in [1, 2, 4, 6, 8]:  # 可能的声道数或位深
                        print(f"   可能的参数 @{offset}: {value}")
                except:
                    continue
    
    def extract_audio_data(self):
        """提取音频数据"""
        if not self.file_data:
            return False
        
        print("\n🎵 提取音频数据:")
        
        # 跳过文件头，查找音频数据开始位置
        data_start = 20  # 假设前20字节是头部
        
        # 寻找实际音频数据（非0xA5填充）
        for i in range(data_start, len(self.file_data)):
            if self.file_data[i] != 0xA5:  # 找到非填充数据
                data_start = i
                break
        
        raw_audio_data = self.file_data[data_start:]
        print(f"📊 原始音频数据长度: {len(raw_audio_data)} 字节")
        
        # 尝试不同的数据格式解析
        self._try_parse_audio_formats(raw_audio_data)
        
        return True
    
    def _try_parse_audio_formats(self, raw_data):
        """尝试不同的音频格式解析"""
        print("🔍 尝试不同音频格式:")
        
        # 1. 尝试16位有符号整数
        if len(raw_data) >= 2:
            try:
                samples_16bit = []
                for i in range(0, len(raw_data) - 1, 2):
                    sample = struct.unpack('<h', raw_data[i:i+2])[0]
                    samples_16bit.append(sample)
                
                if samples_16bit:
                    self._analyze_samples(samples_16bit, "16位有符号整数")
                    self.audio_data = np.array(samples_16bit, dtype=np.int16)
            except:
                pass
        
        # 2. 尝试8位无符号整数
        try:
            samples_8bit = list(raw_data)
            if samples_8bit:
                self._analyze_samples(samples_8bit, "8位无符号整数")
        except:
            pass
    
    def _analyze_samples(self, samples, format_name):
        """分析音频采样数据"""
        if not samples:
            return
        
        print(f"\n📈 {format_name} 分析:")
        print(f"   采样点数: {len(samples)}")
        print(f"   最小值: {min(samples)}")
        print(f"   最大值: {max(samples)}")
        print(f"   平均值: {sum(samples) / len(samples):.2f}")
        
        # 检查是否包含有意义的音频信号
        non_zero_count = sum(1 for s in samples if s != 0)
        print(f"   非零采样: {non_zero_count} ({non_zero_count/len(samples)*100:.1f}%)")
    
    def convert_to_wav(self, output_path=None, sample_rate=44100):
        """转换为WAV文件"""
        if self.audio_data is None:
            print("❌ 没有可用的音频数据")
            return False
        
        if output_path is None:
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            output_path = f"converted_smp2_{timestamp}.wav"
        
        try:
            # 创建WAV文件
            with wave.open(output_path, 'wb') as wav_file:
                wav_file.setnchannels(1)  # 单声道
                wav_file.setsampwidth(2)  # 16位
                wav_file.setframerate(sample_rate)  # 采样率
                
                # 写入音频数据
                wav_file.writeframes(self.audio_data.tobytes())
            
            print(f"🎵 WAV文件已保存: {output_path}")
            print(f"   格式: 16位单声道 {sample_rate}Hz")
            print(f"   时长: {len(self.audio_data) / sample_rate:.2f} 秒")
            return output_path
            
        except Exception as e:
            print(f"❌ WAV转换失败: {e}")
            return False
    
    def export_raw_data(self, output_path=None):
        """导出原始音频数据"""
        if not self.file_data:
            return False
        
        if output_path is None:
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            output_path = f"smp2_raw_{timestamp}.bin"
        
        try:
            # 跳过文件头，保存纯音频数据
            data_start = 20
            raw_audio = self.file_data[data_start:]
            
            with open(output_path, 'wb') as f:
                f.write(raw_audio)
            
            print(f"💾 原始数据已保存: {output_path} ({len(raw_audio)} 字节)")
            return output_path
            
        except Exception as e:
            print(f"❌ 原始数据导出失败: {e}")
            return False
    
    def generate_analysis_report(self):
        """生成分析报告"""
        print("\n📑 SMP2文件分析报告:")
        print("=" * 60)
        print(f"文件路径: {self.file_path}")
        print(f"文件大小: {len(self.file_data) if self.file_data else 0} 字节")
        
        if self.header_info:
            print(f"文件类型: {self.header_info.get('magic', 'Unknown')}")
            print(f"版本信息: {self.header_info.get('version', 'Unknown')}")
        
        if self.audio_data is not None:
            print(f"音频采样点: {len(self.audio_data)}")
            print(f"推荐播放参数: 16位单声道, 8000-44100Hz")
        
        print("\n💡 播放建议:")
        print("1. 使用转换后的WAV文件播放")
        print("2. 尝试不同采样率 (8000, 16000, 22050, 44100 Hz)")
        print("3. 可能需要调整音量或应用滤波器")


def analyze_smp2_file(file_path):
    """分析SMP2文件的主函数"""
    print(f"🔍 开始分析SMP2文件: {file_path}")
    
    analyzer = SMP2Analyzer(file_path)
    
    # 加载文件
    if not analyzer.load_file():
        return False
    
    # 分析文件头
    if not analyzer.analyze_header():
        print("⚠️  继续尝试作为原始音频数据处理...")
    
    # 提取音频数据
    analyzer.extract_audio_data()
    
    # 转换为WAV
    wav_file = analyzer.convert_to_wav()
    
    # 导出原始数据
    raw_file = analyzer.export_raw_data()
    
    # 生成报告
    analyzer.generate_analysis_report()
    
    return wav_file


if __name__ == "__main__":
    import sys
    
    print("🎵 SMP2音频文件分析工具")
    print("=" * 50)
    
    # 检查命令行参数
    if len(sys.argv) > 1:
        file_path = sys.argv[1]
    else:
        # 查找当前目录下的smp文件
        import glob
        smp_files = glob.glob("*.smp")
        if smp_files:
            file_path = smp_files[0]
            print(f"📂 找到SMP文件: {file_path}")
        else:
            print("❌ 请提供SMP2文件路径")
            print("用法: python smp2_analyzer.py <file.smp>")
            sys.exit(1)
    
    # 分析文件
    result = analyze_smp2_file(file_path)
    
    if result:
        print(f"\n🎉 分析完成! 请尝试播放: {result}")
        print("\n🎧 播放建议:")
        print("1. 使用Audacity打开WAV文件")
        print("2. 尝试调整播放速度和音量")
        print("3. 如果声音异常,尝试不同采样率重新转换")
    else:
        print("\n❌ 分析失败")