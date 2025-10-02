#!/usr/bin/env python3
"""
快速SMP转WAV播放工具
简单快速地将SMP文件转换为可播放的WAV格式
"""
import wave
import struct
import os
import sys


def smp_to_wav(smp_file, wav_file=None, sample_rate=16000, skip_bytes=20):
    """
    将SMP文件转换为WAV文件
    
    Args:
        smp_file: SMP文件路径
        wav_file: 输出WAV文件路径（可选）
        sample_rate: 采样率（默认16000Hz）
        skip_bytes: 跳过的头部字节数（默认20）
    """
    if wav_file is None:
        wav_file = smp_file.replace('.smp', '.wav')
        if wav_file == smp_file:  # 如果没有.smp扩展名
            wav_file = smp_file + '.wav'
    
    try:
        # 读取SMP文件
        with open(smp_file, 'rb') as f:
            data = f.read()
        
        print(f"📂 读取文件: {smp_file} ({len(data)} 字节)")
        
        # 跳过文件头
        if len(data) > skip_bytes:
            audio_data = data[skip_bytes:]
        else:
            audio_data = data
        
        print(f"🎵 音频数据: {len(audio_data)} 字节")
        
        # 确保数据长度为偶数（16位需要）
        if len(audio_data) % 2 != 0:
            audio_data = audio_data[:-1]
        
        # 创建WAV文件
        with wave.open(wav_file, 'wb') as wav:
            wav.setnchannels(1)      # 单声道
            wav.setsampwidth(2)      # 16位
            wav.setframerate(sample_rate)  # 采样率
            wav.writeframes(audio_data)
        
        duration = len(audio_data) // 2 / sample_rate
        print(f"✅ 转换完成: {wav_file}")
        print(f"📊 格式: 16位单声道 {sample_rate}Hz")
        print(f"⏱️  时长: {duration:.2f}秒")
        
        return wav_file
        
    except Exception as e:
        print(f"❌ 转换失败: {e}")
        return None


def try_multiple_formats(smp_file):
    """尝试多种格式转换"""
    print(f"🔄 尝试多种格式转换: {smp_file}")
    
    # 不同的参数组合
    configs = [
        (8000, 20),   # 8kHz, 跳过20字节
        (16000, 20),  # 16kHz, 跳过20字节  
        (22050, 20),  # 22kHz, 跳过20字节
        (44100, 20),  # 44kHz, 跳过20字节
        (16000, 0),   # 16kHz, 不跳过头部
        (16000, 40),  # 16kHz, 跳过40字节
    ]
    
    results = []
    base_name = smp_file.replace('.smp', '')
    
    for i, (rate, skip) in enumerate(configs):
        output_file = f"{base_name}_{rate}Hz_skip{skip}.wav"
        print(f"\n🔧 尝试配置 {i+1}: {rate}Hz, 跳过{skip}字节")
        
        result = smp_to_wav(smp_file, output_file, rate, skip)
        if result:
            results.append(result)
    
    print(f"\n🎉 成功转换 {len(results)} 个文件:")
    for result in results:
        print(f"   📁 {result}")
    
    return results


def play_with_system(wav_file):
    """尝试用系统默认播放器播放"""
    try:
        if sys.platform.startswith('darwin'):  # macOS
            os.system(f'open "{wav_file}"')
        elif sys.platform.startswith('win'):   # Windows
            os.system(f'start "{wav_file}"')
        elif sys.platform.startswith('linux'): # Linux
            os.system(f'xdg-open "{wav_file}"')
        else:
            print("❌ 不支持的操作系统，请手动播放WAV文件")
    except:
        print("❌ 无法自动播放，请手动播放WAV文件")


if __name__ == "__main__":
    print("🎵 SMP快速播放工具")
    print("=" * 40)
    
    # 查找SMP文件
    smp_files = []
    for ext in ['*.smp', '*.SMP']:
        import glob
        smp_files.extend(glob.glob(ext))
    
    if len(sys.argv) > 1:
        # 命令行指定文件
        smp_file = sys.argv[1]
    elif smp_files:
        # 自动找到的文件
        smp_file = smp_files[0]
        print(f"📂 找到SMP文件: {smp_file}")
    else:
        print("❌ 未找到SMP文件")
        print("使用方法:")
        print("1. python quick_smp_player.py file.smp")
        print("2. 将SMP文件放在同目录下直接运行")
        sys.exit(1)
    
    if not os.path.exists(smp_file):
        print(f"❌ 文件不存在: {smp_file}")
        sys.exit(1)
    
    # 选择处理方式
    print(f"\n处理文件: {smp_file}")
    print("选择处理方式:")
    print("1. 快速转换 (推荐)")
    print("2. 尝试多种格式")
    
    try:
        choice = input("请选择 (1/2): ").strip()
    except:
        choice = "1"
    
    if choice == "2":
        # 尝试多种格式
        results = try_multiple_formats(smp_file)
        if results:
            print(f"\n🎧 请尝试播放这些WAV文件，找出音质最好的:")
            for result in results:
                print(f"   {result}")
    else:
        # 快速转换
        result = smp_to_wav(smp_file)
        if result:
            print(f"\n🎧 尝试播放: {result}")
            play_with_system(result)
    
    print("\n💡 播放提示:")
    print("- 如果声音异常，尝试其他采样率的版本")
    print("- 可以用VLC、Windows Media Player等播放器打开")
    print("- 也可以导入Audacity进行详细分析")