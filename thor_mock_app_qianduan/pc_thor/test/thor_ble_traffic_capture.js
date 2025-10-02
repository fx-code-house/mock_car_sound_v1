/**
 * Thor BLE Traffic Capture Script
 * 捕获Thor应用的所有蓝牙通信流量
 */

Java.perform(function() {
    console.log("[+] Thor BLE Traffic Capture Started");
    
    // 1. Hook BluetoothGatt.writeCharacteristic - 捕获发送的数据
    var BluetoothGatt = Java.use("android.bluetooth.BluetoothGatt");
    BluetoothGatt.writeCharacteristic.overload("android.bluetooth.BluetoothGattCharacteristic").implementation = function(characteristic) {
        console.log("\n========== BLE WRITE ==========");
        console.log("[WRITE] Characteristic UUID: " + characteristic.getUuid().toString());
        
        var value = characteristic.getValue();
        if (value != null) {
            var hexStr = "";
            var byteArray = [];
            for (var i = 0; i < value.length; i++) {
                var byte = value[i] & 0xFF;
                byteArray.push(byte);
                hexStr += ("0" + byte.toString(16)).slice(-2).toUpperCase() + " ";
            }
            console.log("[WRITE] Data Length: " + value.length);
            console.log("[WRITE] Data Hex: " + hexStr);
            console.log("[WRITE] Data Array: [" + byteArray.join(", ") + "]");
        }
        
        var result = this.writeCharacteristic(characteristic);
        console.log("[WRITE] Result: " + result);
        console.log("==============================\n");
        
        return result;
    };
    
    // 2. Hook Thor的BaseBleRequest.getBytes - 捕获Thor协议层数据
    try {
        var BaseBleRequest = Java.use("com.thor.businessmodule.bluetooth.request.other.BaseBleRequest");
        BaseBleRequest.getBytes.overload("boolean").implementation = function(useEncryption) {
            console.log("\n========== THOR REQUEST ==========");
            console.log("[THOR] Request Class: " + this.getClass().getName());
            console.log("[THOR] Use Encryption: " + useEncryption);
            
            var result = this.getBytes(useEncryption);
            if (result != null) {
                var hexStr = "";
                var byteArray = [];
                for (var i = 0; i < result.length; i++) {
                    var byte = result[i] & 0xFF;
                    byteArray.push(byte);
                    hexStr += ("0" + byte.toString(16)).slice(-2).toUpperCase() + " ";
                }
                console.log("[THOR] Request Length: " + result.length);
                console.log("[THOR] Request Hex: " + hexStr);
                console.log("[THOR] Request Array: [" + byteArray.join(", ") + "]");
            }
            console.log("=================================\n");
            
            return result;
        };
    } catch (e) {
        console.log("[!] Could not hook BaseBleRequest: " + e);
    }
    
    // 3. Hook CryptoManager.coreAesEncrypt - 捕获加密前数据
    try {
        var CryptoManager = Java.use("com.thor.businessmodule.crypto.CryptoManager");
        CryptoManager.coreAesEncrypt.implementation = function(data) {
            console.log("\n========== CRYPTO ENCRYPT ==========");
            
            if (data != null) {
                var hexStr = "";
                var byteArray = [];
                var signedArray = [];
                for (var i = 0; i < data.length; i++) {
                    var byte = data[i] & 0xFF;
                    var signed = data[i];
                    byteArray.push(byte);
                    signedArray.push(signed);
                    hexStr += ("0" + byte.toString(16)).slice(-2).toUpperCase() + " ";
                }
                console.log("[CRYPTO] Pre-encrypt Length: " + data.length);
                console.log("[CRYPTO] Pre-encrypt Hex: " + hexStr);
                console.log("[CRYPTO] Pre-encrypt Unsigned: [" + byteArray.join(", ") + "]");
                console.log("[CRYPTO] Pre-encrypt Signed: [" + signedArray.join(", ") + "]");
            }
            
            var result = this.coreAesEncrypt(data);
            
            if (result != null) {
                var hexStr = "";
                for (var i = 0; i < result.length; i++) {
                    var byte = result[i] & 0xFF;
                    hexStr += ("0" + byte.toString(16)).slice(-2).toUpperCase() + " ";
                }
                console.log("[CRYPTO] Post-encrypt Length: " + result.length);
                console.log("[CRYPTO] Post-encrypt Hex: " + hexStr);
            }
            console.log("===================================\n");
            
            return result;
        };
    } catch (e) {
        console.log("[!] Could not hook CryptoManager: " + e);
    }
    
    // 4. Hook Thor特定的UPLOAD_START命令
    try {
        var UploadStartBleRequest = Java.use("com.thor.businessmodule.bluetooth.request.other.UploadStartBleRequest");
        
        // 尝试hook正确的构造函数签名
        try {
            UploadStartBleRequest.$init.overload("int", "short").implementation = function(fileId, deviceType) {
                console.log("\n========== UPLOAD START COMMAND ==========");
                console.log("[UPLOAD] File ID: " + fileId + " (0x" + fileId.toString(16).toUpperCase() + ")");
                console.log("[UPLOAD] Device Type: " + deviceType);
                
                // 检查是否是SETTINGS_DAT_FILE_ID
                if (fileId == 134217728) {
                    console.log("[UPLOAD] *** SETTINGS_DAT_FILE_ID detected! ***");
                }
                
                var result = this.$init(fileId, deviceType);
                console.log("=========================================\n");
                
                return result;
            };
        } catch (e2) {
            // 如果上面失败，尝试其他可能的签名
            console.log("[!] Could not hook UploadStartBleRequest with (int, short): " + e2);
            
            // 列出所有可用的构造函数
            var constructors = UploadStartBleRequest.class.getDeclaredConstructors();
            console.log("[INFO] Available UploadStartBleRequest constructors:");
            for (var i = 0; i < constructors.length; i++) {
                console.log("  " + constructors[i].toString());
            }
        }
    } catch (e) {
        console.log("[!] Could not hook UploadStartBleRequest class: " + e);
    }
    
    // 5. Hook READ_SETTINGS命令
    try {
        var ReadSettingsBleRequest = Java.use("com.thor.businessmodule.bluetooth.request.other.ReadSettingsBleRequest");
        ReadSettingsBleRequest.$init.implementation = function() {
            console.log("\n========== READ SETTINGS COMMAND ==========");
            console.log("[READ_SETTINGS] Command initialized");
            
            var result = this.$init();
            console.log("==========================================\n");
            
            return result;
        };
    } catch (e) {
        console.log("[!] Could not hook ReadSettingsBleRequest: " + e);
    }
    
    // 6. Hook 通知回调 - 捕获接收的数据
    try {
        var BluetoothGattCallback = Java.use("android.bluetooth.BluetoothGattCallback");
        
        // Hook标准的onCharacteristicChanged方法
        BluetoothGattCallback.onCharacteristicChanged.overload("android.bluetooth.BluetoothGatt", "android.bluetooth.BluetoothGattCharacteristic").implementation = function(gatt, characteristic) {
            console.log("\n========== BLE NOTIFY ==========");
            console.log("[NOTIFY] Characteristic UUID: " + characteristic.getUuid().toString());
            
            var value = characteristic.getValue();
            if (value != null) {
                var hexStr = "";
                var byteArray = [];
                for (var i = 0; i < value.length; i++) {
                    var byte = value[i] & 0xFF;
                    byteArray.push(byte);
                    hexStr += ("0" + byte.toString(16)).slice(-2).toUpperCase() + " ";
                }
                console.log("[NOTIFY] Data Length: " + value.length);
                console.log("[NOTIFY] Data Hex: " + hexStr);
                console.log("[NOTIFY] Data Array: [" + byteArray.join(", ") + "]");
            }
            console.log("==============================\n");
            
            return this.onCharacteristicChanged(gatt, characteristic);
        };
        
        // 也hook带字节数组参数的版本（如果存在）
        try {
            BluetoothGattCallback.onCharacteristicChanged.overload("android.bluetooth.BluetoothGatt", "android.bluetooth.BluetoothGattCharacteristic", "[B").implementation = function(gatt, characteristic, value) {
                console.log("\n========== BLE NOTIFY (with bytes) ==========");
                console.log("[NOTIFY] Characteristic UUID: " + characteristic.getUuid().toString());
                
                if (value != null) {
                    var hexStr = "";
                    var byteArray = [];
                    for (var i = 0; i < value.length; i++) {
                        var byte = value[i] & 0xFF;
                        byteArray.push(byte);
                        hexStr += ("0" + byte.toString(16)).slice(-2).toUpperCase() + " ";
                    }
                    console.log("[NOTIFY] Data Length: " + value.length);
                    console.log("[NOTIFY] Data Hex: " + hexStr);
                    console.log("[NOTIFY] Data Array: [" + byteArray.join(", ") + "]");
                }
                console.log("============================================\n");
                
                return this.onCharacteristicChanged(gatt, characteristic, value);
            };
        } catch (e2) {
            console.log("[INFO] No 3-parameter onCharacteristicChanged method found");
        }
    } catch (e) {
        console.log("[!] Could not hook BluetoothGattCallback: " + e);
    }
    
    console.log("[+] All hooks installed successfully");
    console.log("[+] Waiting for BLE traffic...");
});