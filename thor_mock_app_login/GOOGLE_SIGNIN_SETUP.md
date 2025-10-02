# Google Sign-In 配置指南

## 功能概述
已成功为Thor BLE控制应用添加Google登录功能。用户现在需要先通过Google账号登录才能访问主应用功能。

## 已完成的实现
1. ✅ 添加Google Sign-In SDK依赖
2. ✅ 创建LoginActivity登录页面
3. ✅ 实现Google OAuth 2.0认证流程  
4. ✅ 添加登出功能
5. ✅ 用户信息显示和传递

## 配置Google Cloud Console

### 当前配置信息
- **项目ID**: tool-472814
- **OAuth 2.0 客户端ID**: 455029952822-jgf383mhjucm53gcgks6r74sijc47r1c.apps.googleusercontent.com
- **Debug SHA-1**: 21:15:37:23:A4:64:3C:ED:6D:36:5D:D0:9F:A7:F7:61:F1:EE:C3:45

### 步骤1：创建项目
1. 访问 [Google Cloud Console](https://console.cloud.google.com)
2. 创建新项目或选择现有项目
3. 记录项目ID

### 步骤2：配置OAuth同意屏幕
1. 在左侧导航栏选择 "APIs & Services" > "OAuth consent screen"
2. 选择用户类型：
   - External（面向所有Google用户）
   - Internal（仅限组织内部用户）
3. 填写应用信息：
   - 应用名称：Thor BLE Control
   - 用户支持邮箱
   - 应用Logo（可选）
4. 添加作用域：
   - email
   - profile
   - openid
5. 添加测试用户（如果是测试阶段）

### 步骤3：创建Android OAuth客户端 ⚠️ **重要配置**
1. 导航到 "APIs & Services" > "Credentials"
2. 点击 "Create Credentials" > "OAuth client ID"
3. 选择应用类型：**Android** (必须是Android类型，不是Web类型)
4. 填写信息：
   - 名称：Thor BLE Android Client
   - 包名：com.example.myapplication
   - SHA-1证书指纹：21:15:37:23:A4:64:3C:ED:6D:36:5D:D0:9F:A7:F7:61:F1:EE:C3:45

⚠️ **注意**: 目前代码中暂时移除了requestIdToken，因为这需要Android类型的OAuth客户端。如果您已经创建了Android客户端，可以取消注释相关代码。

### 步骤4：获取SHA-1证书指纹

#### Debug证书SHA-1：
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

您的Debug SHA-1指纹：
```
21:15:37:23:A4:64:3C:ED:6D:36:5D:D0:9F:A7:F7:61:F1:EE:C3:45
```

#### Release证书SHA-1：
```bash
keytool -list -v -keystore your-release-key.keystore -alias your-key-alias
```

### 步骤5：下载配置文件（可选）
虽然当前实现不需要google-services.json文件，但如果需要更高级的功能：
1. 在Firebase Console创建项目
2. 添加Android应用
3. 下载google-services.json
4. 放置到app/目录下

## 测试登录功能

### 运行应用：
```bash
# 构建并安装Debug版本
./gradlew installDebug

# 或者生成APK
./gradlew assembleDebug
# APK位置：app/build/outputs/apk/debug/app-debug.apk
```

### 登录流程：
1. 启动应用，显示登录页面
2. 点击"Sign in with Google"按钮
3. 选择Google账号
4. 授权应用访问基本信息
5. 成功后跳转到主界面
6. 主界面显示用户名
7. 菜单中可选择"Sign Out"登出

## 故障排查

### 常见问题：

1. **错误10 - 开发者错误 (当前遇到的问题)**
   - ❌ 原因：缺少Android类型的OAuth客户端ID
   - ✅ 解决：在Google Cloud Console创建Android类型（非Web类型）的OAuth客户端
   - ✅ 配置：使用您的包名和SHA-1指纹
   - ⚠️ 状态：当前使用基础配置，移除了requestIdToken以避免错误

2. **错误12500 - 登录失败**
   - 确认SHA-1指纹正确
   - 确认包名匹配
   - 等待几分钟让配置生效

3. **错误16 - 配置错误**
   - 确认已在Google Cloud Console启用Google Sign-In API
   - 检查OAuth同意屏幕配置

4. **NETWORK_ERROR / TimeoutException**
   - 检查网络连接
   - 确认Google Play Services已更新
   - 尝试重启设备或清除Google Play Services缓存

### 调试建议：
1. 使用Logcat查看详细错误：
   ```bash
   adb logcat | grep LoginActivity
   ```

2. 验证客户端配置：
   - 包名必须完全匹配：com.example.myapplication
   - SHA-1必须与当前签名证书匹配
   - 如果使用Google Play签名，需要添加Google Play的SHA-1

## 生产环境配置

### 发布前准备：
1. 创建Release签名证书
2. 在Google Cloud Console添加Release证书的SHA-1
3. 如果使用Google Play App Signing，添加Google Play的SHA-1
4. 完成OAuth同意屏幕验证（如果需要敏感范围）
5. 更新隐私政策和服务条款链接

### 安全建议：
1. 不要在代码中硬编码客户端ID
2. 使用ProGuard/R8混淆（当前已禁用，生产环境建议启用）
3. 实现后端验证ID Token
4. 设置适当的会话超时

## 高级功能扩展

### 可选功能：
1. **获取ID Token用于后端验证**：
   ```java
   .requestIdToken(getString(R.string.server_client_id))
   ```

2. **静默登录**：
   ```java
   GoogleSignIn.silentSignIn()
   ```

3. **撤销访问权限**：
   ```java
   mGoogleSignInClient.revokeAccess()
   ```

4. **获取更多用户信息**：
   - 需要请求额外的范围
   - 使用Google People API

## 相关文档
- [Google Sign-In Android文档](https://developers.google.com/identity/sign-in/android)
- [OAuth 2.0 for Mobile Apps](https://developers.google.com/identity/protocols/oauth2/native-app)
- [Google Cloud Console](https://console.cloud.google.com)

## 注意事项
- 当前实现使用基础的Google Sign-In，不包含Firebase
- 用户信息仅在客户端使用，未与后端同步
- 登录状态在应用重启后保持（通过GoogleSignIn.getLastSignedInAccount）
- 支持Android 5.0（API 21）及以上版本