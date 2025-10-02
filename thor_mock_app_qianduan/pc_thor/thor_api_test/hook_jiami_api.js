rpc.exports = {
    init: function (ivHex, hardwareVersion, firmwareVersion, serialNumber) {
        var result = '';
        Java.perform(function () {
            try {
                // 获取当前应用的classloader
                var currentApplication = Java.use("android.app.ActivityThread").currentApplication();
                var context = currentApplication.getApplicationContext();
                var classLoader = context.getClassLoader();

                // 使用正确的classloader加载类
                Java.classFactory.loader = classLoader;

                var CryptoManager = Java.use("com.thor.businessmodule.crypto.CryptoManager");
                var cryptoInstance = CryptoManager.$new();
                if (!cryptoInstance) {
                    result = {success: false, error: "CryptoManager实例未创建"};
                }

                var ivBytes = hexToByteArray(ivHex);
                cryptoInstance.coreAesInit(ivBytes, hardwareVersion, firmwareVersion, serialNumber);

                result = {
                    success: true,
                    message: "AES初始化成功"
                };
            } catch (e) {
                result = {success: false, error: e.message};
            }
        });
        return result;
    },

    jiami: function (dataHex) {
        var result = '';
        Java.perform(function () {
            try {
                // 使用相同的classloader
                var currentApplication = Java.use("android.app.ActivityThread").currentApplication();
                var context = currentApplication.getApplicationContext();
                var classLoader = context.getClassLoader();
                Java.classFactory.loader = classLoader;

                var CryptoManager = Java.use("com.thor.businessmodule.crypto.CryptoManager");
                var cryptoInstance = CryptoManager.$new();

                if (!cryptoInstance) {
                    result = {success: false, error: "CryptoManager实例未创建"};
                    return;
                }

                var dataBytes = hexToByteArray(dataHex);
                var encryptedBytes = cryptoInstance.coreAesEncrypt(dataBytes);
                var encryptedHex = byteArrayToHex(encryptedBytes);

                result = {
                    success: true,
                    encrypted: encryptedHex
                };
            } catch (e) {
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