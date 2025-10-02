# Thor应用音效调整Frida抓包脚本使用指南

## 项目概述

本项目为Thor汽车声学设备的Android应用提供了专门的Frida抓包脚本，用于监听和分析音效调整、音量控制相关的操作。

## 脚本文件说明

### 1. `thor_sound_volume_frida.js` - 完整监听版本
**功能特点：**
- 全面监听所有音效相关API调用
- 详细记录BLE通信数据包
- 包含UI层面的用户交互监听
- 提供完整的调用栈跟踪
- 适合深度分析和调试

**使用场景：**
- 需要完整了解Thor应用音效系统的工作原理
- 进行深度逆向分析
- 调试BLE通信问题

### 2. `thor_volume_focused_frida.js` - 音量专用精简版
**功能特点：**
- 专注音量调整功能
- 过滤无关信息，输出简洁
- 提供音量统计和可视化显示
- 实时监控音量变化
- 适合专门分析音量控制

**使用场景：**
- 只关心音量调整功能
- 需要清晰的输出日志
- 进行音量相关的功能测试

## 使用方法

### 前置条件
1. **设备准备**
   - Android设备已root
   - 已安装frida-server
   - Thor官方应用已安装
   - 确保设备与PC在同一网络

2. **环境检查**
   ```bash
   # 检查设备连接
   adb devices
   
   # 检查frida-server状态
   adb shell "ps | grep frida"
   
   # 获取Thor应用包名
   adb shell pm list packages | grep thor
   ```

### 基本使用步骤

#### 方式1: 使用精简版脚本（推荐）
```bash
# 启动音量专用监听脚本
frida -U -f com.carsystems.thor.app -l thor_volume_focused_frida.js --no-pause

# 或者附加到正在运行的应用
frida -U com.carsystems.thor.app -l thor_volume_focused_frida.js
```

#### 方式2: 使用完整版脚本
```bash
# 启动完整监听脚本
frida -U -f com.carsystems.thor.app -l thor_sound_volume_frida.js --no-pause

# 或者附加到正在运行的应用
frida -U com.carsystems.thor.app -l thor_sound_volume_frida.js
```

### 操作流程

1. **启动脚本**
   ```bash
   frida -U -f com.carsystems.thor.app -l thor_volume_focused_frida.js --no-pause
   ```

2. **等待脚本加载完成**
   ```
   🎯 [THOR VOLUME MONITOR] 音量调整监听脚本已启动
   📍 专注功能: 音效音量调整抓包
   🔍 过滤模式: 仅显示音量相关操作
   ✅ 已Hook PlaySguSoundBleRequest (音量调整核心)
   ✅ 已Hook SguSoundManager
   ✅ 已Hook BLE写入监听
   ✅ 已Hook UI进度条监听
   🎯 [就绪] 音量调整监听脚本已就绪，开始操作Thor应用!
   ```

3. **在Thor应用中进行操作**
   - 打开音效设置界面
   - 调整音效音量滑块
   - 播放测试音效
   - 切换不同音效

4. **观察抓包输出**
   ```
   🔄 [音量调整] 14:23:45
      🎵 音效ID: 12
      📢 音量值: 75 [███████░░░] 75%
      📍 触发位置: PlaySguSoundBleRequest构造
      📊 统计: 总调用15次, 音量变化8次

   📦 [BLE数据包]
      十六进制: 00 0C 00 02 00 1A 00 4B 00 00
      解析结果: 音效12 音量75

   📡 [BLE发送] 音量75 到设备 6e400002...
   ```

## 输出信息解读

### 关键符号说明
- 🎯 脚本状态信息
- 🔄 音量变化事件
- ▶️ 首次音量设置
- 🎵 音效ID信息
- 📢 音量数值
- 📦 BLE数据包内容
- 📡 BLE发送事件
- 🎚️ UI滑块操作
- 🎶 音效管理器调用
- 📊 统计信息

### 重要数据字段
- **音效ID**: Thor设备中的音效编号
- **音量值**: 0-100的音量百分比
- **重复次数**: 音效播放重复次数
- **引擎静音量**: 引擎声音的静音程度
- **BLE数据包**: 发送给设备的原始数据

## 数据分析技巧

### 1. 音量调整分析
- 关注📢标识的音量值变化
- 观察音量条显示 `[███████░░░] 75%`
- 统计音量变化频率和范围

### 2. BLE协议分析
- 重点查看十六进制数据包格式
- 分析数据包结构：`[soundId(2), repeatCount(2), muteAmount(2), volume(2), reserved(2)]`
- 对比不同音量值对应的数据包差异

### 3. UI交互分析
- 监听🎚️标识的滑块操作
- 分析UI操作与BLE命令的对应关系
- 识别音量调整的触发时机

## 常见问题排查

### 1. 脚本无法附加
```bash
# 检查应用进程
adb shell "ps | grep thor"

# 重新启动frida-server
adb shell "su -c 'killall frida-server'"
adb shell "su -c 'frida-server &'"

# 使用包名启动
frida -U -f com.carsystems.thor.app -l 脚本文件名 --no-pause
```

### 2. Hook失败
- 确认应用版本与反编译代码匹配
- 检查类名和方法名是否正确
- 尝试使用`frida-ps -U`查看进程列表

### 3. 无输出信息
- 确认在应用中进行了音效相关操作
- 检查脚本配置中的过滤条件
- 尝试调整`CONFIG.MIN_VOLUME_TO_LOG`参数

## 进阶用法

### 1. 自定义过滤条件
编辑脚本中的CONFIG部分：
```javascript
const CONFIG = {
    SHOW_STACK_TRACE: true,     // 显示调用栈
    SHOW_RAW_BYTES: true,       // 显示原始字节
    MIN_VOLUME_TO_LOG: 10,      // 只记录音量>10的操作
    ENABLE_HEARTBEAT: true      // 启用心跳日志
};
```

### 2. 保存日志到文件
```bash
frida -U -f com.carsystems.thor.app -l thor_volume_focused_frida.js --no-pause > thor_volume_log.txt
```

### 3. 结合其他工具
```bash
# 同时监听网络流量
tcpdump -i any -w thor_network.pcap &
frida -U -f com.carsystems.thor.app -l thor_volume_focused_frida.js --no-pause
```

## 脚本原理说明

### Hook关键类
1. **PlaySguSoundBleRequest**: 音效播放BLE请求类，包含音量参数
2. **SguSoundManager**: 音效管理器，跟踪当前播放状态
3. **BluetoothGattCharacteristic**: Android BLE通信接口
4. **SeekBar**: Android UI滑块组件

### BLE协议分析
基于项目研究文档 `thor_bl_cmd.md` 中的协议定义：
- 命令ID 34: COMMAND_PLAY_SGU_SOUND
- 数据格式: `[soundId(2), repeatCount(2), muteAmount(2), volume(2), reserved(2)]`
- 加密方式: AES加密
- 传输方式: BLE特征值写入

## 相关文档
- `pc_thor/thor_bl_cmd.md`: Thor BLE协议完整分析
- `pc_thor/jadx_thor_code/`: 反编译的官方应用源码
- `CLAUDE.md`: 项目总体说明

## 版本信息
- 脚本版本: v1.0
- 适用Thor应用: 基于jadx反编译分析的版本
- 更新时间: 2025-09-16

## 注意事项
⚠️ **重要提醒**:
- 本脚本仅用于技术研究和学习目的
- 请遵守相关法律法规
- 不得用于商业用途或恶意攻击
- 使用前请备份重要数据