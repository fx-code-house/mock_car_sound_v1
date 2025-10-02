# MyApplication - Thor BLE 逆向工程项目

## 项目概述
这是一个针对Thor汽车声学设备的Android逆向工程项目，实现了完整的BLE通信协议分析和Thor设备控制功能。

## 项目结构

### 核心文件
- `app/` - Android应用主目录
- `pc_thor/` - PC端Thor协议分析工具
- `pc_thor/jadx_thor_code/` - jadx反编译的Thor官方应用代码
- `pc_thor/mock_thor_by_zzj/` - Python实现的Thor协议客户端

### 关键技术文档
- `pc_thor/thor_bl_cmd.md` - Thor BLE协议完整分析文档
- `README.md` - 项目说明文档

## 技术栈

### Android应用
- **Target SDK**: 34 (Android 14)
- **Min SDK**: 21 (Android 5.0) - 支持更多设备
- **Java版本**: 1.8 - 提高兼容性
- **编译工具**: Gradle 8.x with Kotlin DSL

### BLE通信
- **协议**: 自定义Thor BLE协议
- **加密**: AES加密，自定义密钥派生算法
- **服务UUID**: 6e400001-b5a3-f393-e0a9-e50e24dcca9e

### 核心功能实现
- Thor设备BLE连接和通信
- AES加密/解密数据传输
- 完整的39个BLE命令支持
- 设备信息查询和音效管理

## 编译配置

### 最新优化（兼容性改进）
```kotlin
android {
    compileSdk = 34        // 降低API级别提高兼容性
    defaultConfig {
        minSdk = 21        // 支持Android 5.0+设备
        targetSdk = 34     // 稳定的API级别
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8  // Java 8兼容性
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        release {
            isMinifyEnabled = false  // 禁用混淆避免逆向分析问题
        }
    }
}
```

### BLE权限配置
```xml
<!-- 支持Android 5.0 到 Android 14 的完整BLE权限 -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" 
    android:usesPermissionFlags="neverForLocation" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## 开发环境要求
- Android Studio 2023.x+
- JDK 8+ (推荐JDK 11)
- Android SDK 34
- Gradle 8.x
- Python 3.8+ (用于PC端工具)

## 项目重点

### 已实现功能
- ✅ Thor BLE设备发现和连接
- ✅ AES加密通信协议
- ✅ 设备硬件信息查询
- ✅ 音效列表读取和管理
- ✅ 心跳保持和状态监控
- ✅ 完整的39个BLE命令映射

### 研究成果
- ✅ 完全逆向了Thor BLE通信协议
- ✅ 破解了AES密钥派生算法
- ✅ 实现了完整的协议栈
- ✅ 通过Frida分析了官方应用行为

### 技术限制
- ❌ 文件传输功能需要特殊权限
- ❌ 高级配置修改需要官方授权
- ❌ 音频文件导出功能受限

## 编译说明

### 推荐编译命令
```bash
# 清理项目
./gradlew clean

# 编译Debug版本（推荐开发使用）
./gradlew assembleDebug

# 编译Release版本（无混淆）
./gradlew assembleRelease
```

### 兼容性注意事项
- 配置针对广泛的Android设备优化
- 禁用了ProGuard混淆确保功能正常
- 使用Java 8保证老设备兼容性
- 完整的BLE权限声明支持Android 5.0-14

## 测试设备支持
- Android 5.0+ (API 21+)
- 支持BLE 4.0+的设备
- 已测试小米、华为、三星等主流品牌

## 维护日志
- 2025-01-27: 完成协议分析和基础功能实现
- 2025-01-27: 优化兼容性配置，支持更多机型

## 注意事项
- 本项目仅用于技术研究和学习
- 请遵守相关法律法规使用
- 不得用于商业用途或恶意攻击