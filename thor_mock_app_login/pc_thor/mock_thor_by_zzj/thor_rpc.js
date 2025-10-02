// thor_rpc.js

// 全局加密实例，保持状态
var globalCryptoInstance = null;

rpc.exports = {
    /**
     * 初始化 CryptoManager，为加解密做准备
     * 注意：参数顺序必须与工作版本保持一致 (SN, FW, HW)
     */
    init: function (ivHex, hardwareVersion, firmwareVersion, serialNumber) {
        var result = {};
        Java.perform(function () {
            try {
                console.log("[RPC] 开始初始化加密器...");
                var CryptoManager = Java.use("com.thor.businessmodule.crypto.CryptoManager");
                globalCryptoInstance = CryptoManager.$new();
                
                var ivBytes = hexToByteArray(ivHex);
                console.log("[RPC] IV长度: " + ivBytes.length);
                console.log("[RPC] 硬件版本: " + hardwareVersion + ", 固件版本: " + firmwareVersion + ", 序列号: " + serialNumber);
                
                // 检查CryptoManager的可用方法
                console.log("[RPC] 检查CryptoManager可用方法...");
                var methods = [];
                var proto = CryptoManager.class.getDeclaredMethods();
                for (var i = 0; i < proto.length; i++) {
                    methods.push(proto[i].getName());
                }
                console.log("[RPC] CryptoManager方法: " + methods.join(', '));
                
                // 调用原生初始化函数 - 使用正确的参数顺序 (SN, FW, HW)
                console.log("[RPC] 调用coreAesInit，参数顺序: (IV, SN, FW, HW)");
                globalCryptoInstance.coreAesInit(ivBytes, serialNumber, firmwareVersion, hardwareVersion);
                console.log("[RPC] AES初始化成功");
                console.log("[RPC] 加密器实例已保存到全局变量");

                result = { success: true, message: "AES初始化成功" };
            } catch (e) {
                console.log("[RPC] 初始化失败: " + e.message);
                console.log("[RPC] 错误堆栈: " + e.stack);
                result = { success: false, error: e.message };
            }
        });
        return result;
    },

    /**
     * 使用原生代码进行加密
     */
    jiami: function (dataHex) {
        var result = {};
        Java.perform(function () {
            try {
                if (!globalCryptoInstance) {
                    console.log("[RPC] 错误: 加密器未初始化");
                    result = { success: false, error: "加密器未初始化，请先调用init" };
                    return;
                }
                
                console.log("[RPC] 开始加密，数据长度: " + dataHex.length / 2 + " 字节");
                var dataBytes = hexToByteArray(dataHex);
                
                // 调用原生加密
                var encryptedBytes = globalCryptoInstance.coreAesEncrypt(dataBytes);
                console.log("[RPC] 加密成功，输出长度: " + encryptedBytes.length + " 字节");
                
                result = { success: true, encrypted: byteArrayToHex(encryptedBytes) };
            } catch (e) {
                console.log("[RPC] 加密失败: " + e.message);
                console.log("[RPC] 错误堆栈: " + e.stack);
                result = { success: false, error: e.message };
            }
        });
        return result;
    },

    /**
     * 使用原生代码进行解密 - Thor设备加密解密是同一个函数
     */
    jiemi: function (dataHex) {
        var result = {};
        Java.perform(function () {
            try {
                if (!globalCryptoInstance) {
                    console.log("[RPC] 错误: 加密器未初始化");
                    result = { success: false, error: "加密器未初始化，请先调用init" };
                    return;
                }
                
                console.log("[RPC] 开始解密，数据长度: " + dataHex.length / 2 + " 字节");
                var dataBytes = hexToByteArray(dataHex);
                
                // Thor设备的加密解密是同一个函数 - 使用coreAesEncrypt
                var decryptedBytes = globalCryptoInstance.coreAesEncrypt(dataBytes);
                console.log("[RPC] 解密成功，输出长度: " + decryptedBytes.length + " 字节");
                
                result = { success: true, decrypted: byteArrayToHex(decryptedBytes) };
            } catch (e) {
                console.log("[RPC] 解密失败: " + e.message);
                console.log("[RPC] 错误堆栈: " + e.stack);
                result = { success: false, error: e.message };
            }
        });
        return result;
    }
};

// --- 辅助函数 ---

function hexToByteArray(hex) {
    // 这个函数需要确保返回的是 Java 的 byte[] 类型
    var bytes = [];
    for (var i = 0; i < hex.length; i += 2) {
        bytes.push(parseInt(hex.substr(i, 2), 16));
    }
    return Java.array('byte', bytes);
}

function byteArrayToHex(byteArray) {
    var hex = '';
    for (var i = 0; i < byteArray.length; i++) {
        // & 0xFF 是为了确保字节被当作无符号数处理
        var byte = byteArray[i] & 0xFF; 
        var hexByte = byte.toString(16).toUpperCase();
        if (hexByte.length === 1) {
            hexByte = '0' + hexByte;
        }
        hex += hexByte;
    }
    return hex;
}

// 在脚本加载时自动获取 ClassLoader，避免重复代码
Java.perform(function() {
    try {
        console.log("[RPC] 开始设置 ClassLoader...");
        
        // 等待应用完全启动
        setTimeout(function() {
            try {
                var ActivityThread = Java.use("android.app.ActivityThread");
                var currentApplication = ActivityThread.currentApplication();
                if (currentApplication) {
                    var context = currentApplication.getApplicationContext();
                    Java.classFactory.loader = context.getClassLoader();
                    console.log("[RPC] ClassLoader 设置成功");
                } else {
                    console.log("[RPC] 使用默认 ClassLoader");
                }
            } catch (e) {
                console.log("[RPC] 使用默认 ClassLoader (原因: " + e.message + ")");
            }
        }, 1000);
        
    } catch (e) {
        console.log("[RPC] ClassLoader 设置完全失败: " + e.message);
    }
});