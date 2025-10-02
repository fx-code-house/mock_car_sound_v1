// 🔥 修复：全局保存CryptoManager实例，避免状态丢失
var globalCryptoInstance = null;

rpc.exports = {
    init: function (ivHex, hardwareVersion, firmwareVersion, serialNumber) {
        var result = '';
        Java.perform(function () {
            try {
                console.log("[HOOK] 🔄 开始初始化 AES");
                console.log("[HOOK] 📥 参数: IV长度=" + ivHex.length + ", HW=" + hardwareVersion + ", FW=" + firmwareVersion + ", SN=" + serialNumber);
                
                // 获取当前应用的classloader
                var currentApplication = Java.use("android.app.ActivityThread").currentApplication();
                var context = currentApplication.getApplicationContext();
                var classLoader = context.getClassLoader();

                // 使用正确的classloader加载类
                Java.classFactory.loader = classLoader;

                var CryptoManager = Java.use("com.thor.businessmodule.crypto.CryptoManager");
                
                // 🔥 关键修复：保存实例到全局变量
                globalCryptoInstance = CryptoManager.$new();
                if (!globalCryptoInstance) {
                    result = {success: false, error: "CryptoManager实例未创建"};
                    console.log("[HOOK] ❌ CryptoManager实例创建失败");
                    return;
                }

                var ivBytes = hexToByteArray(ivHex);
                console.log("[HOOK] 🔐 IV字节数组长度: " + ivBytes.length);
                
                globalCryptoInstance.coreAesInit(ivBytes, hardwareVersion, firmwareVersion, serialNumber);
                console.log("[HOOK] ✅ coreAesInit调用完成");

                result = {
                    success: true,
                    message: "AES初始化成功，实例已保存"
                };
                console.log("[HOOK] ✅ 初始化成功完成");
            } catch (e) {
                console.log("[HOOK] ❌ 初始化失败: " + e.message);
                result = {success: false, error: e.message};
            }
        });
        return result;
    },

    jiami: function (dataHex) {
        var result = '';
        Java.perform(function () {
            try {
                console.log("[HOOK] 🔐 开始加密数据");
                console.log("[HOOK] 📥 输入数据长度: " + dataHex.length + " 字符 (" + (dataHex.length/2) + " 字节)");
                console.log("[HOOK] 📥 输入数据: " + dataHex.substring(0, Math.min(32, dataHex.length)) + (dataHex.length > 32 ? "..." : ""));
                
                // 🔥 关键修复：使用已初始化的全局实例
                if (!globalCryptoInstance) {
                    result = {success: false, error: "CryptoManager实例未初始化，请先调用init"};
                    console.log("[HOOK] ❌ CryptoManager实例未初始化");
                    return;
                }

                console.log("[HOOK] ✅ 使用已保存的CryptoManager实例");
                
                var dataBytes = hexToByteArray(dataHex);
                console.log("[HOOK] 📊 转换后字节数组长度: " + dataBytes.length);
                
                var encryptedBytes = globalCryptoInstance.coreAesEncrypt(dataBytes);
                console.log("[HOOK] ✅ coreAesEncrypt调用完成");
                console.log("[HOOK] 📊 加密后字节数组长度: " + encryptedBytes.length);
                
                var encryptedHex = byteArrayToHex(encryptedBytes);
                console.log("[HOOK] 📤 输出数据长度: " + encryptedHex.length + " 字符 (" + (encryptedHex.length/2) + " 字节)");
                console.log("[HOOK] 📤 输出数据: " + encryptedHex.substring(0, Math.min(32, encryptedHex.length)) + (encryptedHex.length > 32 ? "..." : ""));

                result = {
                    success: true,
                    encrypted: encryptedHex
                };
                console.log("[HOOK] ✅ 加密成功完成");
            } catch (e) {
                console.log("[HOOK] ❌ 加密失败: " + e.message);
                console.log("[HOOK] ❌ 错误堆栈: " + e.stack);
                result = {success: false, error: e.message};
            }
        });
        return result;
    },
}

function hexToByteArray(hex) {
    hex = hex.replace(/[^0-9A-Fa-f]/g, '');
    if (hex.length % 2 !== 0) hex = '0' + hex;

    var bytes = [];
    for (var i = 0; i < hex.length; i += 2) {
        var byte = parseInt(hex.substr(i, 2), 16);
        if (byte > 127) byte = byte - 256;
        bytes.push(byte);
    }
    return Java.array('byte', bytes);
}

function byteArrayToHex(byteArray) {
    var hex = '';
    for (var i = 0; i < byteArray.length; i++) {
        var byte = byteArray[i] & 0xFF;
        var hexByte = byte.toString(16).toUpperCase();
        if (hexByte.length === 1) hexByte = '0' + hexByte;
        hex += hexByte;
    }
    return hex;
}