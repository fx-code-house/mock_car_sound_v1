# Thor Android 蓝牙通信实现

## 项目概述

这是一个将 Python Thor 蓝牙通信逻辑移植到 Android 的项目。目标是替换原有的 Python RPC 调用，改为 Android 应用直接调用 `libbridge.so` 库，实现完整的Thor设备通信和CAN配置管理。

## 项目状态：✅ 完整协议实现，基于真实业务流程分析

### 已完成核心功能

#### 1. **蓝牙连接和加密握手** ✅
- 成功连接 Thor 设备 (UUIDs: 写入 `6e400002-b5a3-f393-e0a9-e50e24dcca9e`, 通知 `6e400003-b5a3-f393-e0a9-e50e24dcca9e`)
- 完成 AES-CTR 加密握手流程
- 修复了关键的参数顺序问题：`coreAesInit(IV, SN, FW, HW)`

#### 2. **完整命令系统** ✅
基于Frida日志分析和jadx源码验证实现的完整命令系统：

**基础通信命令**：
- **命令8 (心跳测试)**: `[0,8]` - 基础通信验证 ✅
- **命令12 (解锁设备)**: `[0,12,79,120]` - 解锁高级功能访问 ✅

**设备状态管理**：
- **命令58 (读取设备设置)**: 获取设备配置状态 ✅
- **命令59 (写入设备设置)**: 修改设备配置 ✅
- **命令74 (读取CAN配置信息)**: 获取当前CAN配置ID和版本 ✅

**文件传输系统**：
- **命令112-116序列**: CAN配置文件完整烧录流程 ✅
  - 112: 开始下载组
  - 113: 开始下载文件
  - 114: 写入数据块（已优化ANR问题）
  - 115: 提交文件下载
  - 116: 提交文件组下载

**设备信息查询**：
- **命令127**: 获取设备配置（返回108字节"DAT2"格式数据）✅
- **命令128-129**: 文件上传和状态查询系统 ✅

#### 3. **CAN配置文件烧录系统** ✅
基于真实业务流程分析，实现完整的**7阶段CAN配置烧录流程**：

1. **加密握手阶段** 🔐: `128握手开始 → 127获取设备配置 → 126握手完成`
2. **状态查询阶段** 📋: `58读取设备设置 → (可选)59写入设置调整`
3. **文件传输初始化** 🚀: `112开始下载组 → 113开始下载文件`
4. **数据块传输阶段** 📦: `114写入数据块`（重复执行，已优化ANR）
5. **传输完成阶段** ✅: `115提交文件下载 → 116提交文件组下载`
6. **验证烧录阶段** 🔍: `74读取CAN配置信息 → 58读取设备设置`
7. **状态同步阶段** 🔄: `59写入最终设置 → 69激活预设(可选)`

#### 4. **ANR性能优化** ✅
针对114命令大文件传输的ANR问题实现优化：
- **问题原因**: 688KB文件传输产生约24,000条UI日志导致主线程阻塞
- **解决方案**: 每10个数据块输出一次日志，减少93%的UI更新操作
- **优化效果**: 保持调试能力的同时避免UI卡顿

#### 5. **业务数据解析系统** ✅
基于真实Frida日志和jadx源码分析实现的数据解析：

**74号命令 - CAN配置信息解析**：
```java
// 响应格式: [长度,分隔符,命令ID,CAN_ID(2字节),CAN版本(2字节),填充]
// 示例: [9,0,74,0,1,0,5,...] = CAN_ID=1, 版本=5
```

**58号命令 - 设备设置解析**：
```java
// 响应格式: [长度,分隔符,命令ID,状态,设置项数量,设置项列表]
// 示例: [15,0,58,0,3,0,1,0,0,0,2,0,0,0,3,0,1] = 3个设置项
```

**127号命令 - 设备配置文件**：
```java
// 返回108字节"DAT2"格式的完整设备配置信息
// 格式: [长度,命令ID,108字节,"DAT2",版本,配置数据]
```

### 核心架构设计分析

#### **Thor设备 vs Android APP 的数据分工** 🏛️
基于业务流程分析确认的系统架构：

**Thor设备端**：
- ✅ 只存储当前激活的.canbin文件二进制数据
- ✅ 存储简单的ID和版本号用于识别（ID永远为1）
- ✅ 同时只能存储一个CAN配置（覆盖式存储）

**Android APP端**：
- ✅ 缓存完整车型信息（品牌、车型、车系、系列）
- ✅ 管理用户选择和API数据同步
- ✅ 负责从服务器下载对应的.canbin文件
- ✅ 实现车型切换时的配置文件覆盖烧录

#### **CAN配置ID=1的业务含义** 🎯
**重要发现**: 通过多次切换车型的Frida日志分析确认：
- CAN ID=1是设备内部的**固定存储槽位标识**，不是业务层面的车型ID
- 设备设计为**单CAN配置存储**，每次烧录都覆盖同一个槽位
- 真实的车型信息（如奔驰S63 AMG）存储在APP的`CarInfoPreference`中

#### **82号命令的真相** ❌
**经过验证发现**：
- 82号命令(`COMMAND_WRITE_CAN_CONFIGURATIONS_FILE`)在`BleCommands.java`中存在
- **但实际业务中从未使用**，所有CAN配置烧录都通过112-116序列完成
- 可能是早期设计的遗留代码

### 核心文件结构

```
app/src/main/java/com/example/myapplication/
├── MainActivity.java          # 主界面，包含所有测试按钮
├── ThorBluetooth.java         # 蓝牙通信核心类，包含完整命令系统和业务解析
├── ThorCRC16.java            # CRC16 校验算法 (多项式 0xA001)
└── com/thor/businessmodule/crypto/
    └── CryptoManager.java    # JNI 接口，调用 libbridge.so

app/src/main/assets/music/107/  # 音效文件存储
├── 107.sample               # 音效数据文件 (688KB)
├── 107.smprl               # 采样规则文件 (2.9KB) 
└── 107.pkgrl               # 包规则文件 (1.1KB)
```

### 关键技术实现

#### 1. **设备解锁机制** 🔑
Thor设备采用分层访问控制：
- **基础命令** (无需解锁): 命令1(硬件信息), 命令8(心跳)
- **高级命令** (需要解锁): 命令74, 58, 112-116等
- **解锁流程**: 发送命令12且收到确认码20344，设置`deviceUnlocked=true`

#### 2. **加密参数顺序（重要修复）**
```java
// ❌ 错误的参数顺序（导致通信失败）
cryptoManager.coreAesInit(IV, HW, FW, SN);

// ✅ 正确的参数顺序（基于 Python RPC 日志分析）
cryptoManager.coreAesInit(IV, SN, FW, HW);
```

#### 3. **Thor 数据包格式**
```java
// 数据包结构：[A5 5A][header][encrypted_data][CRC16]
// 加密数据：[填充长度][0x00][命令ID][数据...][填充0xA5]
// Header格式：[加密类型3位<<13 | 数据长度13位]
```

#### 4. **双向加密理解** ⚠️
```java
// 关键理解: coreAesEncrypt()同时用于加密和解密
// 发送命令 - 加密明文
byte[] encryptedCmd = cryptoManager.coreAesEncrypt(commandData);

// 接收响应 - 解密密文  
byte[] decryptedResponse = cryptoManager.coreAesEncrypt(encryptedResponse);
```

### 关键Bug修复记录 🔧

#### **Bug #1: 命令12解锁参数字节顺序错误** 🔴
```java
// ❌ 错误实现（字节顺序反了）
byte[] cmd = createCommandData(12, new byte[]{120, 79}); // [0x78, 0x4F]

// ✅ 正确实现（基于jadx反编译验证）
byte[] cmd = createCommandData(12, new byte[]{79, 120}); // [0x4F, 0x78] = 20344
```

#### **Bug #2: 114命令ANR性能问题** 🔴
```java
// ❌ 原问题: 每个数据块输出5-7条日志
// 688KB文件 ≈ 3,440块 × 7条日志 = ~24,000条UI更新 → ANR

// ✅ 优化方案: 每10个数据块输出一次
if (chunkIndex % 10 == 0 || chunkIndex == 0) {
    callback.printlog(String.format("📤 发送第%d块数据", chunkIndex));
    // ... 其他关键信息
}
```

#### **Bug #3: 解锁状态判断错误** 🔴
```java
// ❌ 错误判断
if (commandData[0] == 0x78 && commandData[1] == 0x4F) {
    message = "解锁设备响应 - ⚠️ 收到回显数据";
}

// ✅ 正确判断（基于jadx分析）
if (commandData[0] == 0x4F && commandData[1] == 0x78) {
    message = "解锁设备响应 - ✅ 解锁成功！收到确认码 20344";
    deviceUnlocked = true; // 关键：正确设置解锁状态
}
```

### 验证方法论 🔬

建立了**三重验证体系**确保准确性：
1. **Frida动态分析** → 捕获真实应用的明文通信数据
2. **jadx静态分析** → 反编译验证参数格式和响应逻辑
3. **实际设备测试** → 验证修复后的命令响应效果

### 命令系统验证状态表

| 命令ID | 功能 | 明文格式 | Frida验证 | jadx验证 | 实测状态 | 访问级别 |
|--------|------|----------|-----------|----------|----------|----------|
| 8 | 心跳测试 | `[0,8]` | ✅ | ✅ | ✅ 正常响应 | 基础 |
| 12 | 设备解锁 | `[0,12,79,120]` | ✅ | ✅ | ✅ 已修复 | 基础 |
| 58 | 读取设备设置 | `[0,58]` | ✅ | ✅ | ✅ 解析完成 | 高级 |
| 74 | 读取CAN配置 | `[0,74]` | ✅ | ✅ | ✅ 解析完成 | 高级 |
| 112 | 开始下载组 | `[0,112,文件类型,数量]` | ✅ | ✅ | ✅ 完整流程 | 高级 |
| 113 | 开始下载文件 | `[0,113,类型,ID,版本,大小]` | ✅ | ✅ | ✅ 完整流程 | 高级 |
| 114 | 写入数据块 | `[0,114,块数据]` | ✅ | ✅ | ✅ 已优化ANR | 高级 |
| 115 | 提交文件下载 | `[0,115]` | ✅ | ✅ | ✅ 完整流程 | 高级 |
| 116 | 提交文件组 | `[0,116]` | ✅ | ✅ | ✅ 完整流程 | 高级 |
| 127 | 获取设备配置 | `[0,127]` | ✅ | 🔧 | ✅ DAT2格式 | 高级 |

## 开发环境和依赖

### Android 项目配置
- **Android Studio**: 最新版本
- **Target SDK**: Android API level配置
- **JNI库**: `libbridge.so` (公司提供的加密库)
- **蓝牙权限**: BLUETOOTH, BLUETOOTH_ADMIN, BLUETOOTH_SCAN, BLUETOOTH_CONNECT

### 外部依赖
```java
// JNI调用libbridge.so
public final native byte[] coreAesEncrypt(byte[] data);
public final native void coreAesInit(byte[] iv, int serial_number, int firmware_version, int hardware_version);
```

### 参考项目
- **Python实现**: `pc_thor/` - 原始Python通信实现
- **jadx反编译**: `pc_thor/jadx_thor_code/` - 原始Thor应用源码分析
- **真实日志**: `log/can.log`, `log/can2.log`, `log/can3.log` - Frida捕获的业务流程

## 技术难点和解决方案

### 1. **CAN配置烧录流程复杂性** (已解决)
**问题**: 需要理解完整的7阶段烧录流程
**解决**: 通过Frida日志分析真实业务流程，发现112-116序列是实际使用的烧录方法

### 2. **ANR性能问题** (已解决)  
**问题**: 大文件传输时UI日志过多导致主线程阻塞
**解决**: 实现每10个数据块输出一次日志的优化方案，减少93%的UI更新

### 3. **业务数据解析复杂性** (已解决)
**问题**: 不同命令返回格式差异大，需要准确解析业务含义
**解决**: 基于jadx源码和真实数据建立完整的解析系统

### 4. **设备架构理解错误** (已解决)
**问题**: 误认为Thor设备存储多个CAN配置
**解决**: 通过业务流程分析确认设备只存储单个配置，APP负责车型信息管理

## 项目成果总结

### 技术突破 ✅
1. **完全替代Python实现**: Android应用独立完成Thor设备通信
2. **掌握完整协议**: 握手、AES-CTR加密、命令系统、响应解析
3. **建立验证方法论**: Frida+jadx+实测三重验证确保准确性
4. **解决关键技术难题**: 字节序、访问控制、双向加密、ANR优化等

### 业务价值理解 💼
1. **系统架构清晰**: 理解Thor设备与APP的数据分工机制
2. **CAN配置管理**: 实现完整的车型切换和配置烧录流程
3. **性能优化**: 解决大文件传输的ANR问题，提升用户体验
4. **技术自主可控**: 完整掌握Thor通信协议和业务逻辑

### 商业价值 💼
1. **移除外部依赖**: 不再需要Python RPC服务
2. **提升安全性**: 本地加密处理，减少网络传输风险
3. **改善用户体验**: Android原生界面和流畅的文件传输
4. **降低维护成本**: 统一的Android技术栈和清晰的架构设计

## 开发经验总结和最佳实践 ⚠️

### 主要技术陷阱
1. **字节序问题** 🔴
   - Thor设备使用大端序，必须严格按照jadx代码验证
   - 验证方法: 查看jadx中的`BleHelper.convertShortToByteArray()`

2. **访问控制机制** 🔴  
   - Thor设备有分层访问控制，高级命令需要先解锁
   - 解锁状态必须正确设置: `deviceUnlocked = true`

3. **业务逻辑理解偏差** 🟡
   - CAN ID=1不是车型ID，而是设备存储槽位
   - 82号命令存在但不使用，实际通过112-116序列烧录

### 推荐开发方法
1. **三重验证**: 所有功能都要通过Frida+jadx+实测验证
2. **性能优化**: 注意UI线程阻塞，合理控制日志输出频率
3. **业务理解**: 优先理解真实业务流程，再实现技术细节
4. **文档维护**: 及时记录验证结果和关键发现

### 后续功能建议
1. **音效管理**: 基于107音效包实现完整音效系统
2. **设备监控**: 实现设备状态实时监控和异常处理
3. **配置备份**: 实现CAN配置的本地备份和恢复功能
4. **多设备支持**: 扩展支持多个Thor设备的并发管理

## Thor 服务器 API 接口文档

### 概述
Thor 系统通过 REST API 与服务器通信，获取车型信息和 CAN 配置文件。服务器有两个环境：
- **开发环境**: `https://dev.thor-tuning.com`
- **生产环境**: `https://sec2.thor-tuning.com`

### 认证机制
所有 API 请求都需要包含有效的 `apikey` 参数进行身份验证。

### 车型层级结构
Thor 系统使用四级车型分类：**品牌 → 车型 → 车系 → 系列**

### API 接口列表

#### 1. 获取车辆品牌列表
```bash
GET /api/cars/get-marks
```
**参数**: 无需参数
**响应示例**:
```json
{
    "result": true,
    "items": [
        {
            "id_car_mark": 99,
            "name": "Lexus"
        },
        {
            "id_car_mark": 116,
            "name": "Mercedes-Benz"
        }
    ]
}
```

#### 2. 获取车型列表
```bash
POST /api/cars/get-model-list
Content-Type: application/x-www-form-urlencoded
```
**参数**:
- `id_car_mark` - 品牌ID
- `language` - 语言代码 (如: en)

#### 3. 获取车系列表
```bash
POST /api/cars/get-generation-list
Content-Type: application/x-www-form-urlencoded
```
**参数**:
- `id_car_mark` - 品牌ID
- `id_car_model` - 车型ID
- `language` - 语言代码

#### 4. 获取系列列表
```bash
POST /api/cars/get-serie-list
Content-Type: application/x-www-form-urlencoded
```
**参数**:
- `id_car_mark` - 品牌ID
- `id_car_model` - 车型ID
- `id_car_generation` - 车系ID
- `language` - 语言代码

**响应示例**:
```json
{
    "result": true,
    "items": [
        {
            "id_car_serie": 301939,
            "id_car_model": 20364,
            "name": "AMG S63 Sedan",
            "id_car_generation": 10435
        }
    ]
}
```

#### 5. 获取 CAN 配置文件 ⭐
```bash
POST /api/cars/get-can-file
Content-Type: application/x-www-form-urlencoded
```
**参数**:
- `user_id` - 用户ID
- `device_id` - 设备ID
- `apikey` - API密钥
- `id_car_mark` - 品牌ID
- `id_car_model` - 车型ID
- `id_car_generation` - 车系ID
- `id_car_serie` - 系列ID
- `language` - 语言代码
- `device_sn` - 设备序列号

**成功响应示例**:
```json
{
    "status": true,
    "can": {
        "file": "/uploads/can/301939/MB_W222_AMG-S63_Lepestki_Mute_DriveSelect_v3.canbin",
        "version": "5",
        "comment": "",
        "comment_CanConnectionPoint": "Front electric panel in trunk, gasoline pump control module:\nBlue/white - can high\nBlue - can low",
        "comment_NativeControl": "OFF/ON (MUTE): Paddle shifter DOWN (-)\nSound switch: Paddle shifter UP (+)",
        "comment_DriveSelect": "Drive modes: S mode, E mode"
    }
}
```

#### 6. 获取音效包详情
```bash
POST /api/sounds/get-shop-sound-pack
Content-Type: application/x-www-form-urlencoded
```
**响应示例**:
```json
{
    "pkg_id": 144,
    "pkg_name": "MERCEDES-BENZ E63S",
    "files": [
        {"type": 1, "file": "/uploads/sounds/144/spfile_xxx.sample", "ver": 2},
        {"type": 2, "file": "/uploads/sounds/144/spfile_xxx.smprl", "ver": 2},
        {"type": 3, "file": "/uploads/sounds/144/spfile_xxx.pkgrl", "ver": 2}
    ]
}
```

### 实际测试案例
**测试车型**: 奔驰 AMG S63 Sedan (W222)
- `id_car_mark=116` (Mercedes-Benz)
- `id_car_model=20364`
- `id_car_generation=10435` 
- `id_car_serie=301939` (AMG S63 Sedan)

**获取的CAN文件**: `MB_W222_AMG-S63_Lepestki_Mute_DriveSelect_v3.canbin`

---

**项目状态**: 🎯 **完整协议实现完成，基于真实业务分析**  
**最后更新**: 2025-01-26  
**当前版本**: v2.0 - 完整业务分析版本  
**主要更新**: 
- 实现完整的CAN配置烧录7阶段流程
- 解决114命令ANR性能问题
- 完成74/58号命令业务数据解析
- 确认Thor设备与APP的架构分工
- 建立三重验证方法论体系

**项目价值**: ✅ 完全替代Python实现，掌握完整Thor通信协议