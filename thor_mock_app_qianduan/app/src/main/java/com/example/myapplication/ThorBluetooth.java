package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.thor.businessmodule.crypto.CryptoManager;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ThorBluetooth {
    private static final String TAG = "ThorBluetooth";

    // UUID定义
    private static final UUID SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    private static final UUID CHARACTERISTIC_WRITE = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    private static final UUID CHARACTERISTIC_NOTIFY = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    private static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic writeCharacteristic;
    private BluetoothGattCharacteristic notifyCharacteristic;

    private ThorCRC16 crcCalculator;
    private CryptoManager cryptoManager;

    // 状态变量
    private List<Byte> responseData;
    private HardwareInfo hardwareInfo;
    private byte[] clientIV;
    private byte[] deviceIV;
    private byte[] combinedIV;
    private boolean handshakeComplete = false;
    private boolean connected = false;
    private boolean deviceUnlocked = false;
    private int unlockRetryCount = 0;
    private static final int MAX_UNLOCK_RETRIES = 3;

    // 同步响应处理
    private final Object responseLock = new Object();
    private byte[] lastResponse = null;
    private boolean responseReceived = false;

    // 回调接口
    public interface ThorCallback {
        void onConnected();

        void onDisconnected();

        void onHandshakeComplete();

        void onMessageReceived(String message);

        void onError(String error);

        // 新增详细日志回调
        void onResponseReceived(int commandId, String commandName, String parsedData, byte[] rawData);

        // 设备状态变化回调
        void onDeviceUnlocked();

        void appendLog(String category, String title, String content);

        void printlog(String text);
    }

    private ThorCallback callback;
    private Handler handler;

    public static class HardwareInfo {
        public int command;
        public int hardwareVersion;
        public int firmwareVersion;
        public int serialNumber;

        @Override
        public String toString() {
            return String.format("HW=%d, FW=%d, SN=%d", hardwareVersion, firmwareVersion, serialNumber);
        }
    }

    public ThorBluetooth(Context context, ThorCallback callback) {
        this.context = context;
        this.callback = callback;
        this.handler = new Handler(Looper.getMainLooper());
        this.responseData = new ArrayList<>();
        this.crcCalculator = new ThorCRC16();
        this.cryptoManager = new CryptoManager();

        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void scanAndConnect() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            callback.onError("蓝牙未启用");
            return;
        }

        Log.d(TAG, "开始扫描 Thor 设备...");
        BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanner.startScan(scanCallback);

        // 10秒后停止扫描
        handler.postDelayed(() -> {
            scanner.stopScan(scanCallback);
            if (!connected) {
                callback.onError("未找到 Thor 设备");
            }
        }, 10000);
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            String deviceName = device.getName();
            String deviceAddress = device.getAddress();

            // 记录所有发现的设备
            Log.d(TAG, String.format("发现设备: %s (%s) RSSI: %d", deviceName != null ? deviceName : "Unknown", deviceAddress, result.getRssi()));

            // 检查是否是Thor设备 (支持多种命名方式)
            boolean isThorDevice = false;
            if (deviceName != null) {
                String lowercaseName = deviceName.toLowerCase();
                isThorDevice = lowercaseName.contains("thor") || lowercaseName.startsWith("thor") || deviceAddress.equals("E0:21:11:46:8A:8F"); // 备用：直接匹配地址
            } else if (deviceAddress.equals("E0:21:11:46:8A:8F")) {
                // 即使没有设备名，也尝试匹配已知的Thor设备地址
                isThorDevice = true;
                Log.d(TAG, "通过地址匹配到Thor设备: " + deviceAddress);
            }

            if (isThorDevice) {
                Log.d(TAG, "找到 Thor 设备: " + (deviceName != null ? deviceName : "Unknown") + " (" + deviceAddress + ")");
                bluetoothAdapter.getBluetoothLeScanner().stopScan(this);
                connectToDevice(device);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e(TAG, "BLE扫描失败，错误码: " + errorCode);
            callback.onError("BLE扫描失败，错误码: " + errorCode);
        }
    };

    private void connectToDevice(BluetoothDevice device) {
        Log.d(TAG, "连接到设备: " + device.getAddress());
        bluetoothGatt = device.connectGatt(context, false, gattCallback);
    }

    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                Log.d(TAG, "蓝牙已连接，发现服务...");
                connected = true;
                gatt.discoverServices();
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                Log.d(TAG, "蓝牙已断开");
                connected = false;
                callback.onDisconnected();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "服务发现成功");
                setupCharacteristics();
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "数据发送成功: " + bytesToHex(characteristic.getValue()));
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
//            Log.d(TAG, "onCharacteristicChanged: 收到数据: " + bytesToHex(data));
//            Log.d(TAG, "特征值UUID: " + characteristic.getUuid().toString());
            handleReceivedData(data);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "描述符写入成功: " + descriptor.getUuid().toString());

                if (CLIENT_CHARACTERISTIC_CONFIG.equals(descriptor.getUuid())) {
                    if (!handshakeComplete) {
                        Log.d(TAG, "初始通知已启用，开始握手");
                        callback.onConnected();
                        startHandshake();
                    } else {
                        Log.d(TAG, "握手后通知重新启用成功");
                    }
                }
            } else {
                Log.e(TAG, "描述符写入失败，状态: " + status);
            }
        }
    };

    private void setupCharacteristics() {
        for (BluetoothGattService service : bluetoothGatt.getServices()) {
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                if (CHARACTERISTIC_WRITE.equals(characteristic.getUuid())) {
                    writeCharacteristic = characteristic;
                    Log.d(TAG, "找到写特征值");
                }
                if (CHARACTERISTIC_NOTIFY.equals(characteristic.getUuid())) {
                    notifyCharacteristic = characteristic;
                    Log.d(TAG, "找到通知特征值");

                    // 启用通知
                    bluetoothGatt.setCharacteristicNotification(characteristic, true);
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    bluetoothGatt.writeDescriptor(descriptor);
                }
            }
        }
    }

    private void startHandshake() {
        Log.d(TAG, "开始握手流程...");

        // 1. 获取硬件信息（未加密）
        byte[] hwInfoCmd = createCommandData(1);
        byte[] hwPacket = createPacket(hwInfoCmd, 0); // crypto_type = 0
        sendData(hwPacket);

        // 等待硬件信息响应，然后发送握手
        handler.postDelayed(this::sendHandshakeData, 2000);
    }

    private void sendHandshakeData() {
        if (hardwareInfo == null) {
            Log.e(TAG, "未收到硬件信息，重试...");
            handler.postDelayed(this::sendHandshakeData, 1000);
            return;
        }

        // 2. 发送握手数据（加密类型2）
        clientIV = generateRandomBytes(8);
        byte[] handshakePacket = createPacket(clientIV, 2); // crypto_type = 2
        Log.d(TAG, "发送握手包，客户端IV: " + bytesToHex(clientIV));
        sendData(handshakePacket);
    }

    private void handleReceivedData(byte[] data) {
        Log.d(TAG, "handleReceivedData: 收到 " + data.length + " 字节数据");

        // 添加到响应数据缓冲区
        for (byte b : data) {
            responseData.add(b);
        }

        Log.d(TAG, "responseData缓冲区大小: " + responseData.size() + " 字节");

        // 尝试解析完整响应
        tryParseCompleteResponse();
    }

    private void tryParseCompleteResponse() {
        while (responseData.size() >= 6) {
            // 查找数据包头 A5 5A
            int startIndex = -1;
            for (int i = 0; i < responseData.size() - 1; i++) {
                if (responseData.get(i) == (byte) 0xA5 && responseData.get(i + 1) == (byte) 0x5A) {
                    startIndex = i;
                    break;
                }
            }

            if (startIndex == -1) return;

            // 移除无效数据
            if (startIndex > 0) {
                responseData.subList(0, startIndex).clear();
            }

            if (responseData.size() < 4) return;

            // 解析包头获取数据长度
            int header = ((responseData.get(2) & 0xFF) << 8) | (responseData.get(3) & 0xFF);
            int totalLen = 4 + (header & 0x1FFF) + 2; // 头部4字节 + 数据 + CRC2字节

            if (responseData.size() < totalLen) return;

            // 提取完整数据包
            byte[] packet = new byte[totalLen];
            for (int i = 0; i < totalLen; i++) {
                packet[i] = responseData.get(i);
            }

            // 从缓冲区移除已处理的数据
            responseData.subList(0, totalLen).clear();

            // 解析数据包
            parseResponsePacket(packet);
        }
    }

    private void parseResponsePacket(byte[] data) {
        // CRC校验
        byte[] crcData = Arrays.copyOfRange(data, 0, data.length - 2);
        byte[] crcBytes = Arrays.copyOfRange(data, data.length - 2, data.length);
        int crcReceived = ByteBuffer.wrap(crcBytes).order(ByteOrder.LITTLE_ENDIAN).getShort() & 0xFFFF;
        int crcCalculated = crcCalculator.calculate(crcData);

        if (crcCalculated != crcReceived) {
            Log.w(TAG, String.format("CRC校验失败! 收到: 0x%04X, 计算: 0x%04X", crcReceived, crcCalculated));
            return;
        }

//        Log.d(TAG, "CRC验证成功");

        // 解析包头
        int header = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
        int encryptionType = (header >> 13) & 0x7;
        byte[] dataPart = Arrays.copyOfRange(data, 4, data.length - 2);

//        Log.d(TAG, String.format("包类型: %d, 数据长度: %d", encryptionType, dataPart.length));

        switch (encryptionType) {
            case 0:
                parseHardwareResponse(dataPart);
                break;
            case 2:
                parseHandshakeResponse(dataPart);
                break;
            case 1:
                parseEncryptedResponse(dataPart);
                break;
        }
    }

    private void parseHardwareResponse(byte[] data) {
        if (data.length < 8) return;

        hardwareInfo = new HardwareInfo();
        hardwareInfo.command = ByteBuffer.wrap(data, 0, 2).order(ByteOrder.BIG_ENDIAN).getShort() & 0xFFFF;
        hardwareInfo.hardwareVersion = ByteBuffer.wrap(data, 2, 2).order(ByteOrder.BIG_ENDIAN).getShort() & 0xFFFF;
        hardwareInfo.firmwareVersion = ByteBuffer.wrap(data, 4, 2).order(ByteOrder.BIG_ENDIAN).getShort() & 0xFFFF;
        hardwareInfo.serialNumber = ByteBuffer.wrap(data, 6, 2).order(ByteOrder.BIG_ENDIAN).getShort() & 0xFFFF;

        Log.d(TAG, "硬件信息: " + hardwareInfo.toString());
    }

    private void parseHandshakeResponse(byte[] data) {
        if (data.length < 8 || clientIV == null || hardwareInfo == null) return;

        deviceIV = Arrays.copyOfRange(data, 0, 8);
        combinedIV = new byte[16];
        System.arraycopy(clientIV, 0, combinedIV, 0, 8);
        System.arraycopy(deviceIV, 0, combinedIV, 8, 8);

        Log.d(TAG, "设备IV: " + bytesToHex(deviceIV));
        Log.d(TAG, "组合IV: " + bytesToHex(combinedIV));

        // 初始化加密 - 修复参数顺序：(IV, SN, FW, HW)
        try {
            Log.d(TAG, String.format("调用coreAesInit，参数顺序: IV, SN=%d, FW=%d, HW=%d", hardwareInfo.serialNumber, hardwareInfo.firmwareVersion, hardwareInfo.hardwareVersion));
            Log.d(TAG, "组合IV详情: " + bytesToHex(combinedIV));

            cryptoManager.coreAesInit(combinedIV, hardwareInfo.serialNumber,    // SN - 第2个参数
                    hardwareInfo.firmwareVersion, // FW - 第3个参数
                    hardwareInfo.hardwareVersion); // HW - 第4个参数

            Log.d(TAG, "✅ coreAesInit调用完成，无异常抛出");

            handshakeComplete = true;
            Log.d(TAG, "握手完成!");

            // 握手完成后重新启用通知 - 修复通知失效问题
            Log.d(TAG, "重新启用通知...");
            if (notifyCharacteristic != null) {
                bluetoothGatt.setCharacteristicNotification(notifyCharacteristic, true);
                BluetoothGattDescriptor descriptor = notifyCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
                if (descriptor != null) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    bluetoothGatt.writeDescriptor(descriptor);
                }
            }

            callback.onHandshakeComplete();

        } catch (Exception e) {
            Log.e(TAG, "加密初始化失败: " + e.getMessage());
            callback.onError("加密初始化失败");
        }
    }

    private void parseEncryptedResponse(byte[] encryptedData) {
        if (!handshakeComplete) {
            Log.e(TAG, "握手未完成，无法解密");
            return;
        }

        try {
//            Log.d(TAG, "解密数据: " + Arrays.toString(encryptedData));
            byte[] decryptedData = cryptoManager.coreAesEncrypt(encryptedData); // 解密也用encrypt函数
            Log.d(TAG, decryptedData[2] + "---------->接收: " + Arrays.toString(decryptedData));

//            // 检查解密是否真正工作（输入输出应该不同）
//            if (Arrays.equals(encryptedData, decryptedData)) {
//                Log.w(TAG, "⚠️ 警告：解密输出与输入相同！可能是回显数据或加密未工作！");
//            } else {
//                Log.d(TAG, "✅ 解密正常：输出与输入不同");
//            }

            if (decryptedData.length >= 3) {
                int paddingLength = decryptedData[0] & 0xFF;
                int separator = decryptedData[1] & 0xFF;
                int cmd = decryptedData[2] & 0xFF;

//                Log.d(TAG, String.format("Thor数据: 填充长度=%d, 分隔符=%d, 命令ID=%d", paddingLength, separator, cmd));

                // 提取实际数据
                int dataEndIndex = decryptedData.length - paddingLength;
                byte[] commandData = Arrays.copyOfRange(decryptedData, 3, Math.max(3, dataEndIndex));

                // 统一使用发送端解析逻辑
//                Log.d(TAG, String.format("收到命令%d响应", cmd));
                String message = parseCommandResponse(cmd, getCommandName(cmd), decryptedData);

                // 特殊处理需要状态更新的命令
                if (cmd == 12) { // 解锁命令需要更新设备状态
                    boolean unlockSuccess = false;
                    if (commandData.length >= 2 && commandData[0] == 0x4F && commandData[1] == 0x78) {
                        unlockSuccess = true;
                    } else if (commandData.length >= 2) {
                        int status = ((commandData[0] & 0xFF) << 8) | (commandData[1] & 0xFF);
                        unlockSuccess = (status == 0);
                    } else if (commandData.length == 0) {
                        unlockSuccess = true;
                    }

                    if (unlockSuccess && !deviceUnlocked) {
                        deviceUnlocked = true;
                        unlockRetryCount = 0;
                        Log.d(TAG, "✅ 设备解锁状态已更新");
                        callback.onDeviceUnlocked();
                    }
                }

                // 调用详细回调（如果有数据）
                if (commandData.length > 0) {
                    callback.onResponseReceived(cmd, getCommandName(cmd), message, commandData);
                }

                callback.onMessageReceived(message);

                // 通知等待响应的线程
                synchronized (responseLock) {
                    lastResponse = decryptedData;
                    responseReceived = true;
                    responseLock.notifyAll();
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "解密失败: " + e.getMessage());
            // 即使解密失败也要通知等待的线程
            synchronized (responseLock) {
                lastResponse = null;
                responseReceived = true;
                responseLock.notifyAll();
            }
        }
    }


    public void sendHeartbeat() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送心跳");
            return;
        }

        Log.d(TAG, "发送心跳命令 (8) - 测试设备通信");

        // 心跳命令通常不需要参数，但有些设备可能需要
        byte[] heartbeatCmd = createCommandData(8); // 心跳命令ID = 8，无参数

        // 打印发送前的明文数据
        Log.d(TAG, "发送明文数据: " + bytesToHex(heartbeatCmd));
        Log.d(TAG, "明文解析: 分隔符=" + (heartbeatCmd[0] & 0xFF) + ", 命令ID=" + (heartbeatCmd[1] & 0xFF));

        byte[] packet = createPacket(heartbeatCmd, 1); // 加密类型1

        // 详细日志回调
        String details = "命令: HEARTBEAT (心跳检测)\n" + "参数: 无\n" + "功能: 检测设备连接状态和通信是否正常\n" + "期望响应: 心跳状态确认";

//        callback.onCommandSent(8, getCommandName(8), details, packet);

        Log.d(TAG, "发送心跳命令，等待响应...");
        sendData(packet);

        // 设置10秒后的超时检查
        handler.postDelayed(() -> {
            Log.w(TAG, "⚠️ 心跳命令超时 - 未收到设备响应");
            Log.w(TAG, "可能原因: 1) 设备不支持心跳 2) 需要先解锁 3) 通信异常");
        }, 10000);
    }

    public void sendReadSguSounds() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        // 发送读取SGU音效列表命令 (Python中确认工作的命令36)
        byte[] cmd = createCommandData(36);
        byte[] packet = createPacket(cmd, 1);

        Log.d(TAG, "发送读取SGU音效列表命令");
        sendData(packet);
    }

    // 新增：完整的设备初始化序列，模拟日志中的完整流程
    public void performFullDeviceInitialization() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法执行完整初始化");
            return;
        }

        Log.d(TAG, "开始完整设备初始化序列...");

        // 按照日志顺序执行命令
        handler.post(() -> sendReadSettings());           // 命令58
        handler.postDelayed(() -> sendReadUniversalDataParams(), 500);  // 命令59 
        handler.postDelayed(() -> sendDeviceStatusQuery(), 1000);       // 命令128
        handler.postDelayed(() -> sendDeviceParametersQuery(), 1500);   // 命令127
        handler.postDelayed(() -> sendGetDeviceInfo(), 2000);           // 命令130
        handler.postDelayed(() -> sendHeartbeat(), 3000);               // 命令8
        handler.postDelayed(() -> sendCommand126(), 3500);              // 命令126
    }

    // 命令58：读取设备设置（公开方法）
    public void sendCommand58ReadSettings() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送读取设备设置命令 (58) - 无参数");
        byte[] cmd = createCommandData(58);
        byte[] packet = createPacket(cmd, 1);

        // 详细日志回调
        String details = "参数: 无\n功能: 获取当前设备配置项\n期望响应: 设置项列表";
//        callback.onCommandSent(58, getCommandName(58), details, packet);

        sendData(packet);
    }

    // 命令59：写入设备设置（公开方法）
    public void sendCommand59WriteSettings() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送写入设备设置命令 (59) - 带参数");

        byte[] settingsData = new byte[]{
                0, 3, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 1,
        };

        byte[] cmd = createCommandData(59, settingsData);
        byte[] packet = createPacket(cmd, 1);

        // 详细日志回调
        String details = "参数: 3个设置项\n" + "- DRIVE_SELECT = 0 (关闭)\n" + "- NATIVE_CONTROL = 0 (关闭)\n" + "- SPEAKER_COUNT = 1 (单扬声器)\n" + "期望响应: 写入状态确认";
//        callback.onCommandSent(59, getCommandName(59), details, packet);

        sendData(packet);
    }

    // 命令58：读取设备设置（内部方法，保持原有逻辑）
    private void sendReadSettings() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送读取设备设置命令 (58)");
        byte[] cmd = createCommandData(58);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    // 命令59：读取通用数据参数
    public void sendReadUniversalDataParams() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送读取通用数据参数命令 (59)");
        // 根据日志，这个命令带参数 [0, 3]
        byte[] params = new byte[]{0, 3};
        byte[] cmd = createCommandData(59, params);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    // 命令128：设备状态查询  
    public void sendDeviceStatusQuery() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送设备状态查询命令 (128)");
        byte[] cmd = createCommandData(128, new byte[]{8, 0, 0, 0, 0, -56}); // 基于日志的参数
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    // 命令127：设备参数读取
    public void sendDeviceParametersQuery() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送设备参数读取命令 (127)");
        byte[] cmd = createCommandData(127);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    // 命令130：获取设备信息
    public void sendGetDeviceInfo() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送获取设备信息命令 (130)");
        byte[] cmd = createCommandData(130);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }


    // 命令126：其他查询
    public void sendCommand126() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送命令126");
        byte[] cmd = createCommandData(126);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    // 命令71：读取已安装音效包
    public void sencCommand71() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        Log.d(TAG, "发送读取已安装音效包命令 (71)");
        byte[] cmd = createCommandData(71);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    // 文件上传命令 (命令128)
    public void sendFileUploadCommand() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送文件上传命令");
            return;
        }

        Log.d(TAG, "发送文件上传命令 (128) - 测试明文业务解析");

        // 根据Frida日志明文: 7,0,-128,8,0,0,0,0,-56,-91,-91,-91,-91,-91,-91,-91
        // 精确匹配Frida数据结构（16字节总长度）
        byte[] exactFridaData = new byte[]{7,    // 填充长度=7
                0,    // 分隔符=0
                (byte) 128, // 命令ID=128 (无符号字节形式0x80)
                8, 0, 0, 0, 0, // 参数=[8,0,0,0,0]
                (byte) 200, (byte) 165, (byte) 165, (byte) 165, (byte) 165, (byte) 165, (byte) 165 // 填充数据=7字节 (-56,-91,-91,-91,-91,-91,-91)
        };

        // 打印发送前的明文数据
//        Log.d(TAG, "精确Frida明文: " + bytesToHex(exactFridaData));
//        Log.d(TAG, "明文解析: 填充长度=" + (exactFridaData[0] & 0xFF) + ", 分隔符=" + (exactFridaData[1] & 0xFF) + ", 命令ID=" + (exactFridaData[2] & 0xFF));

        byte[] packet = createFridaPacket(exactFridaData);

        // 详细日志回调
//        String details = "命令: FILE_UPLOAD (文件上传)\n" + "参数: [8,0,0,0,0]\n" + "功能: 启动文件上传模式\n" + "明文格式: [填充长度][分隔符][命令ID][参数][填充数据]\n" + "期望响应: 上传状态确认";

//        callback.onCommandSent(128, getCommandName(128), details, packet);

        sendData(packet);
//        Log.d(TAG, "✅ 文件上传命令已发送，等待设备响应...");
//        Log.d(TAG, "期望响应解析: 填充=7, 分隔符=0, 命令=128, 状态=[0,0,0,84,0]");

        // 设置15秒超时检查
        handler.postDelayed(() -> {
            Log.w(TAG, "⚠️ 文件上传命令超时 - 未收到设备响应");
        }, 15000);
    }

    // 解析设备设置响应数据 (命令58) - 详细版本
    private String parseDeviceSettingsResponseDetailed(byte[] data) {
        if (data.length < 2) {
            return "状态: 数据不足，无法解析\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();
            result.append("状态: 成功接收\n");

            // 根据Thor协议，设备设置响应通常包含设置项数量和具体设置
            if (data.length >= 4) {
                int settingCount = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
                result.append("设置项数量: ").append(settingCount).append("\n");

                // 解析具体设置项
                int offset = 2;
                for (int i = 0; i < settingCount && offset + 3 < data.length; i++) {
                    int settingId = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
                    int settingValue = ((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF);

                    String settingName = getSettingName(settingId);
                    String settingDesc = getSettingDescription(settingId, settingValue);
                    result.append(String.format("- %s = %d %s\n", settingName, settingValue, settingDesc));
                    offset += 4;
                }
            }

            result.append("原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    // 解析写入设置响应数据 (命令59) - 详细版本
    private String parseWriteSettingsResponseDetailed(byte[] data) {
        if (data.length < 2) {
            return "状态: 数据不足，无法解析\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();

            // 检查写入状态
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            if (status == 0) {
                result.append("状态: ✅ 写入成功\n");
                result.append("所有设置项已成功更新到设备");
            } else {
                result.append("状态: ❌ 写入失败\n");
                result.append("错误代码: ").append(status);
            }

            result.append("\n原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    // 获取设置值描述
    private String getSettingDescription(int settingId, int value) {
        switch (settingId) {
            case 1: // DRIVE_SELECT
                return value == 0 ? "(关闭)" : "(开启)";
            case 2: // NATIVE_CONTROL
                return value == 0 ? "(关闭)" : "(开启)";
            case 3: // SPEAKER_COUNT
                return value == 1 ? "(单扬声器)" : "(多扬声器:" + value + ")";
            default:
                return "";
        }
    }

    // 解析设备设置响应数据 (命令58) - 原版本保持兼容
    private void parseDeviceSettingsResponse(byte[] data) {
        parseDeviceSettingsResponseDetailed(data);
    }

    // 获取设置项名称
    private String getSettingName(int settingId) {
        switch (settingId) {
            case 1:
                return "DRIVE_SELECT";
            case 2:
                return "NATIVE_CONTROL";
            case 3:
                return "SPEAKER_COUNT";
            default:
                return "UNKNOWN_SETTING";
        }
    }

    // 获取命令名称
    private String getCommandName(int commandId) {
        switch (commandId) {
            case 1:
                return "获取硬件信息";
            case 8:
                return "心跳检测";
            case 12:
                return "解锁设备";
            case 36:
                return "读取SGU音效列表";
            case 56:
                return "读取设置组";
            case 58:
                return "读取设备设置";
            case 59:
                return "写入设备设置";
            case 71:
                return "读取已安装音效包";
            case 74:
                return "读取CAN配置信息";
            case 114:
                return "文件数据传输";
            case 115:
                return "文件提交完成";
            case 116:
                return "文件组提交完成";
            case 126:
                return "系统信息";
            case 127:
                return "读取设备参数";
            case 128:
                return "文件上传";
            case 129:
                return "状态查询";
            case 130:
                return "获取设备信息";
            default:
                return "未知命令";
        }
    }

    // 解析设备参数响应数据
    private void parseDeviceParametersResponse(byte[] data) {
        if (data.length < 2) return;

        try {
            Log.d(TAG, "解析设备参数数据:");
            // 基于日志，设备参数通常以参数数量开始
            if (data.length >= 4) {
                int paramCount = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
                int dataLength = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
                Log.d(TAG, String.format("  参数数量: %d, 数据长度: %d", paramCount, dataLength));

                // 解析具体参数
                int offset = 4;
                for (int i = 0; i < paramCount && offset + 3 < data.length; i++) {
                    int paramId = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
                    int paramValue = ((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF);
                    Log.d(TAG, String.format("  参数%d: ID=%d, Value=%d (0x%04X)", i + 1, paramId, paramValue, paramValue));
                    offset += 4;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "解析设备参数失败: " + e.getMessage());
        }
    }

    // 解析设备信息响应数据
    private void parseDeviceInfoResponse(byte[] data) {
        if (data.length < 4) return;

        try {
            Log.d(TAG, "解析设备信息数据 (前32字节):");
            Log.d(TAG, "  原始数据: " + bytesToHex(Arrays.copyOfRange(data, 0, Math.min(32, data.length))));

            // 根据日志，这个命令返回大量结构化数据
            // 包含设备配置、参数等信息
            if (data.length >= 8) {
                // 尝试解析前几个字段
                int field1 = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
                int field2 = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
                int field3 = ((data[4] & 0xFF) << 8) | (data[5] & 0xFF);
                int field4 = ((data[6] & 0xFF) << 8) | (data[7] & 0xFF);

                Log.d(TAG, String.format("  字段1-4: %d, %d, %d, %d", field1, field2, field3, field4));
            }

            // 检查是否包含版本信息等
            if (data.length >= 16) {
                Log.d(TAG, "  包含详细设备信息，总长度: " + data.length + " 字节");
            }

        } catch (Exception e) {
            Log.e(TAG, "解析设备信息失败: " + e.getMessage());
        }
    }

    // 解析文件上传响应数据 (按业务逻辑)
    private String parseFileUploadResponse(byte[] data, int paddingLength, int separator, int cmd) {
        if (data.length < 5) {
            return "状态: 数据不足，期望至少5字节\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();
            result.append("业务解析结果:\n");
            result.append(String.format("明文结构: 填充=%d, 分隔符=%d, 命令=%d\n", paddingLength, separator, cmd));

            // 解析响应参数 [0,0,0,84,0]
            int[] responseParams = new int[5];
            for (int i = 0; i < 5 && i < data.length; i++) {
                responseParams[i] = data[i] & 0xFF;
            }

            result.append(String.format("响应参数: [%d,%d,%d,%d,%d]\n", responseParams[0], responseParams[1], responseParams[2], responseParams[3], responseParams[4]));

            // 业务状态判断
            if (responseParams[3] == 84) {
                result.append("✅ 文件上传模式已成功启动\n");
                result.append("设备已准备接收文件数据");
            } else {
                result.append("⚠️ 文件上传状态异常\n");
                result.append("期望第4个参数为84，实际为: ").append(responseParams[3]);
            }

            result.append("\n原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 业务解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    private String parseInstalledSoundPackagesResponse(byte[] data) {
        try {
            if (data.length < 2) {
                return "  已安装音效包: 数据长度不足";
            }

            // 根据实际响应数据分析: [0, 2, 0, 29, 0, 1, 0, 7, 0, 27, 0, 1, 0, 7, ...]
            // 第一个字节(0)可能是状态或头部，第二个字节(2)是音效包数量
            int packageCount = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);

            // 如果第一种解析不对，尝试第二种
            if (packageCount > 100) { // 数量过大不合理，可能格式不对
                packageCount = data[1] & 0xFF; // 只用第二个字节作为数量
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("  🔍 调试信息:\n"));
            result.append(String.format("    原始前8字节: [%s]\n",
                    java.util.Arrays.toString(java.util.Arrays.copyOfRange(data, 0, Math.min(8, data.length)))));
            result.append(String.format("    解析的音效包数量: %d\n", packageCount));

            if (packageCount == 0) {
                return result + "  已安装音效包: 无音效包";
            }

            result.append(String.format("  已安装音效包: %d个\n", packageCount));

            // 根据实际数据，音效包信息可能从索引2开始，每个包占6字节
            int currentIndex = 2;
            for (int i = 0; i < packageCount && currentIndex + 5 < data.length; i++) {
                // 检查是否到达填充区域
                if (data[currentIndex] == (byte) 0xA5) {
                    result.append(String.format("    包#%d: 到达填充区域\n", i + 1));
                    break;
                }

                // packageId (2字节，大端序)
                int packageId = ((data[currentIndex] & 0xFF) << 8) | (data[currentIndex + 1] & 0xFF);

                // versionId (2字节，大端序)  
                int versionId = ((data[currentIndex + 2] & 0xFF) << 8) | (data[currentIndex + 3] & 0xFF);

                // mode (2字节，大端序)
                int mode = ((data[currentIndex + 4] & 0xFF) << 8) | (data[currentIndex + 5] & 0xFF);

                result.append(String.format("    包#%d: packageId=%d, versionId=%d, mode=%d",
                        i + 1, packageId, versionId, mode));
                result.append(String.format(" [原始: %02X %02X %02X %02X %02X %02X]\n",
                        data[currentIndex] & 0xFF, data[currentIndex + 1] & 0xFF,
                        data[currentIndex + 2] & 0xFF, data[currentIndex + 3] & 0xFF,
                        data[currentIndex + 4] & 0xFF, data[currentIndex + 5] & 0xFF));

                currentIndex += 6; // 移动到下一个音效包 (6字节一组)
            }

            return result.toString().trim();

        } catch (Exception e) {
            return "  已安装音效包: 解析失败 - " + e.getMessage();
        }
    }

    private byte[] createPacket(byte[] dataBody, int cryptoType) {
        byte[] dataPart = dataBody;

        if (cryptoType == 1) {
            // 加密数据
            if (!handshakeComplete) {
                Log.e(TAG, "握手未完成，无法加密");
                return null;
            }

            // 检查是否为命令67（音量调整命令）
            boolean isVolumeCommand = (dataBody.length >= 3 && dataBody[2] == 67);
            
            byte[] preEncryptData;
            if (isVolumeCommand) {
                // 命令67特殊处理：直接加密，不添加填充长度前缀，使用-91填充到16字节
                int paddingNeeded = 16 - (dataBody.length % 16);
                if (paddingNeeded == 16) paddingNeeded = 0; // 如果已经是16的倍数，不需要填充
                
                preEncryptData = new byte[dataBody.length + paddingNeeded];
                System.arraycopy(dataBody, 0, preEncryptData, 0, dataBody.length);
                if (paddingNeeded > 0) {
                    Arrays.fill(preEncryptData, dataBody.length, preEncryptData.length, (byte) 0xA5); // -91填充
                }
            } else {
                // 其他命令使用原有格式：[填充长度1字节] + [原始数据] + [填充字节×N]
                int totalDataLength = 1 + dataBody.length; // +1 for padding length byte
                int paddingNeeded = 16 - (totalDataLength % 16);

                // 创建填充字节数组，填充值为0xA5（即-91）
                byte[] padding = new byte[paddingNeeded];
                Arrays.fill(padding, (byte) 0xA5); // 0xA5 = 165 = -91 (signed byte)

                // 构造最终的待加密数据：[填充长度] + [原始数据] + [填充字节]
                preEncryptData = new byte[1 + dataBody.length + paddingNeeded];
                preEncryptData[0] = (byte) paddingNeeded;  // 第1字节：填充长度
                System.arraycopy(dataBody, 0, preEncryptData, 1, dataBody.length);  // 拷贝原始数据
                System.arraycopy(padding, 0, preEncryptData, 1 + dataBody.length, paddingNeeded);  // 拷贝填充字节
            }


            Log.d(TAG, dataBody[1] + "---------->发送" + Arrays.toString(preEncryptData));

            try {
                dataPart = cryptoManager.coreAesEncrypt(preEncryptData);
                if (callback != null) {
                    callback.printlog(String.format("加密完成，输出长度: %d字节", dataPart.length));
                }

                // 检查加密是否真正工作（输入输出应该不同）
                if (Arrays.equals(preEncryptData, dataPart)) {
//                    Log.w(TAG, "⚠️ 警告：加密输出与输入相同！加密可能未正常工作！");
                } else {
//                    Log.d(TAG, "✅ 加密正常：输出与输入不同");
                }

            } catch (Exception e) {
                Log.e(TAG, "加密失败: " + e.getMessage());
                return null;
            }
        }

        //构造A55A
        byte[] preamble = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short) -23206).array(); // 0xA55A
//        构造header
        byte[] header = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short) (((cryptoType & 0x7) << 13) | (dataPart.length & 0x1FFF))).array();


//        申请一个全是0的数组
        byte[] packetWithoutCrc = new byte[4 + dataPart.length];
//        拷贝A55A
        System.arraycopy(preamble, 0, packetWithoutCrc, 0, 2);
//        拷贝header 3位加密头类型 13位数据长度
        System.arraycopy(header, 0, packetWithoutCrc, 2, 2);
//        数据部分
        System.arraycopy(dataPart, 0, packetWithoutCrc, 4, dataPart.length);

//        crc
        byte[] crcBytes = crcCalculator.createChecksumPart(crcCalculator.calculate(packetWithoutCrc));

//        0 0 0数组
        byte[] finalPacket = new byte[packetWithoutCrc.length + 2];
//        A55A+header+数据部分
        System.arraycopy(packetWithoutCrc, 0, finalPacket, 0, packetWithoutCrc.length);
//        crc
        System.arraycopy(crcBytes, 0, finalPacket, packetWithoutCrc.length, 2);

        return finalPacket;
        /*
          ✅ 正确理解:
          ┌─────────┬─────────────────────┬─────────┬─────┐
          │PREAMBLE │    HEADER (2字节)    │实际数据 │ CRC │
          │0xA5 0x5A│加密类型(3位)+长度(13位)│N字节   │2字节│
          └─────────┴─────────────────────┴─────────┴─────┘
        * */
    }

    // 创建精确匹配Frida数据的数据包
    private byte[] createFridaPacket(byte[] fridaPlaintext) {
        if (!handshakeComplete) {
            Log.e(TAG, "握手未完成，无法加密");
            return null;
        }

        try {
            // 直接加密Frida明文数据
            Log.d(TAG, "加密前Frida数据: " + bytesToHex(fridaPlaintext));
            byte[] encryptedData = cryptoManager.coreAesEncrypt(fridaPlaintext);
            Log.d(TAG, "加密后数据: " + bytesToHex(encryptedData));

            // 检查加密是否工作
            if (Arrays.equals(fridaPlaintext, encryptedData)) {
                Log.w(TAG, "⚠️ 警告：加密输出与输入相同！");
            } else {
                Log.d(TAG, "✅ 加密正常：输出与输入不同");
            }

            // 构造完整数据包
            byte[] preamble = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short) -23206).array(); // 0xA55A
            byte[] header = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short) (((1 & 0x7) << 13) | (encryptedData.length & 0x1FFF))).array(); // 加密类型1

            byte[] packetWithoutCrc = new byte[4 + encryptedData.length];
            System.arraycopy(preamble, 0, packetWithoutCrc, 0, 2);
            System.arraycopy(header, 0, packetWithoutCrc, 2, 2);
            System.arraycopy(encryptedData, 0, packetWithoutCrc, 4, encryptedData.length);

            byte[] crcBytes = crcCalculator.createChecksumPart(crcCalculator.calculate(packetWithoutCrc));

            byte[] finalPacket = new byte[packetWithoutCrc.length + 2];
            System.arraycopy(packetWithoutCrc, 0, finalPacket, 0, packetWithoutCrc.length);
            System.arraycopy(crcBytes, 0, finalPacket, packetWithoutCrc.length, 2);

            return finalPacket;
        } catch (Exception e) {
            Log.e(TAG, "创建Frida数据包失败: " + e.getMessage());
            return null;
        }
    }

    private byte[] createCommandData(int commandId, byte[]... params) {
        // 恢复原始格式，用于握手和其他命令
        int len = 2;
        if (params != null) for (byte[] p : params) if (p != null) len += p.length;

        byte[] out = new byte[len];
        int i = 0;
        out[i++] = 0x00;
        out[i++] = (byte) (commandId & 0xFF);

        if (params != null) {
            for (byte[] p : params) {
                if (p == null) continue;
                System.arraycopy(p, 0, out, i, p.length);
                i += p.length;
            }
        }

        if (callback != null) {
            callback.printlog(String.format("发送命令%d，参数长度: %d字节", commandId, out.length - 2));
        }
        return out;
    }
    
    /**
     * 创建带长度前缀的命令数据（专门用于音量等加密命令）
     * 格式: [长度] + [0] + [命令ID] + [载荷数据]
     */
    private byte[] createThorCommandData(int commandId, byte[]... params) {
        // 基于官方Thor应用实际格式: 1,0,67,0,27,0,0,0,3,0,1,0,0,0,100
        // 注意: 官方应用的长度字段是错误的(使用1而不是实际长度)
        // 但为了兼容性，我们也使用相同的格式
        
        byte[] out;
        if (commandId == 67) {
            // 命令67特殊处理，完全匹配官方格式 - 不添加长度前缀，直接传递15字节数据
            out = new byte[15]; // 固定15字节
            out[0] = 0x01; // 官方使用的长度值
            out[1] = 0x00; // 分隔符
            out[2] = 0x43; // 命令ID 67
            // 复制载荷数据
            if (params != null && params.length > 0 && params[0] != null) {
                System.arraycopy(params[0], 0, out, 3, Math.min(params[0].length, 12));
            }
        } else {
            // 其他命令使用正确的长度格式
            int payloadLen = 2; // 0 + commandId
            if (params != null) for (byte[] p : params) if (p != null) payloadLen += p.length;
            
            int totalLen = 1 + payloadLen; // +1 for length byte
            
            out = new byte[totalLen];
            int i = 0;
            
            // 1. 长度字段 (不包括长度字段本身)
            out[i++] = (byte) (payloadLen & 0xFF);
            
            // 2. 分隔符 (固定为0)
            out[i++] = 0x00;
            
            // 3. 命令ID
            out[i++] = (byte) (commandId & 0xFF);
            
            // 4. 载荷数据
            if (params != null) {
                for (byte[] p : params) {
                    if (p == null) continue;
                    System.arraycopy(p, 0, out, i, p.length);
                    i += p.length;
                }
            }
        }
        
        if (callback != null) {
            callback.printlog(String.format("发送Thor命令%d，总长度: %d字节", commandId, out.length));
        }
        return out;
    }


    private void sendData(byte[] data) {
        if (writeCharacteristic == null || bluetoothGatt == null) {
            Log.e(TAG, "蓝牙未准备好");
            return;
        }

//        Log.d(TAG, "发送数据: " + bytesToHex(data));
        writeCharacteristic.setValue(data);
        bluetoothGatt.writeCharacteristic(writeCharacteristic);
    }

    /**
     * 发送数据并等待响应，然后解析并打印结果
     *
     * @param data        要发送的数据包
     * @param commandId   命令ID，用于解析
     * @param commandName 命令名称，用于日志
     * @param timeoutMs   超时时间（毫秒）
     * @return 解析后的响应数据，如果超时或失败返回null
     */
    private String sendDataAndWaitResponse(byte[] data, int commandId, String commandName, long timeoutMs) {
        if (writeCharacteristic == null || bluetoothGatt == null) {
            Log.e(TAG, "蓝牙未准备好");
            return null;
        }

        synchronized (responseLock) {
            // 重置响应状态
            responseReceived = false;
            lastResponse = null;

            // 发送数据
//            Log.d(TAG, "发送命令" + commandId + " (" + commandName + "): " + bytesToHex(data));
            writeCharacteristic.setValue(data);
            bluetoothGatt.writeCharacteristic(writeCharacteristic);

            try {
                // 等待响应
                responseLock.wait(timeoutMs);

                if (!responseReceived) {
//                    Log.w(TAG, "命令" + commandId + "超时，未收到响应");
                    return null;
                }

                if (lastResponse == null) {
//                    Log.w(TAG, "命令" + commandId + "响应解密失败");
                    return null;
                }

                // 解析响应数据
                String result = parseCommandResponse(commandId, commandName, lastResponse);
//                Log.d(TAG, "命令" + commandId + "响应解析结果: " + result);
                return result;

            } catch (InterruptedException e) {
//                Log.e(TAG, "等待响应被中断: " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析命令响应数据并生成格式化输出
     *
     * @param commandId     命令ID
     * @param commandName   命令名称
     * @param decryptedData 解密后的完整响应数据
     * @return 格式化的解析结果
     */
    private String parseCommandResponse(int commandId, String commandName, byte[] decryptedData) {
        if (decryptedData.length < 3) {
            return "响应数据长度不足";
        }

        int paddingLength = decryptedData[0] & 0xFF;
        int separator = decryptedData[1] & 0xFF;
        int cmd = decryptedData[2] & 0xFF;

        // 提取实际数据
        int dataEndIndex = decryptedData.length - paddingLength;
        byte[] commandData = Arrays.copyOfRange(decryptedData, 3, Math.max(3, dataEndIndex));

        StringBuilder result = new StringBuilder();
        result.append(String.format("命令%d (%s) 响应解析:\n", commandId, commandName));
        result.append(String.format("  结构: 填充长度=%d, 分隔符=%d, 命令ID=%d\n", paddingLength, separator, cmd));
        result.append(String.format("  数据长度: %d字节\n", commandData.length));
        result.append(String.format("  原始数据: %s\n", bytesToHex(commandData)));

        // 根据命令ID进行专门的解析
        switch (commandId) {
            case 8: // 心跳
                result.append(parseHeartbeatResponse(commandData));
                break;
            case 12: // 解锁
                result.append(parseUnlockResponse(commandData));
                break;
            case 36: // SGU音效列表
                result.append(parseSguSoundsResponse(commandData));
                break;
            case 56: // 读取设置组
                result.append(parseSettingsGroupResponseDetailed(commandData));
                break;
            case 58: // 读取设备设置
                result.append(parseDeviceSettingsResponseDetailed(commandData));
                break;
            case 59: // 写入设备设置
                result.append(parseWriteSettingsResponseDetailed(commandData));
                break;
            case 71: // 已安装音效包
                result.append(parseInstalledSoundPackagesResponse(commandData));
                break;
            case 114: // 文件数据传输
                result.append(parseFileDataTransferResponseDetailed(commandData));
                break;
            case 115: // 文件提交
                result.append(parseFileCommitResponseDetailed(commandData));
                break;
            case 116: // 文件组提交
                result.append(parseFileGroupCommitResponseDetailed(commandData));
                break;
            case 126: // 系统信息
                result.append(parseSystemInfoResponseDetailed(commandData));
                break;
            case 127: // 设备参数读取
                result.append(parseDeviceParametersResponseDetailed(commandData));
                break;
            case 128: // 文件上传启动
                result.append(parseFileUploadStartResponse(commandData));
                break;
            case 129: // 读取文件块
                result.append(parseFileReadBlockResponse(commandData));
                break;
            case 130: // 获取设备信息
                result.append(parseDeviceInfoResponseDetailed(commandData));
                break;
            case 74: // 读取CAN配置信息
                result.append(parseCanInfoResponse(commandData));
                break;
            default:
                result.append(String.format("  通用解析: 收到%d字节响应数据", commandData.length));
                break;
        }

        return result.toString();
    }

    private String parseHeartbeatResponse(byte[] data) {
        if (data.length >= 2) {
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            return String.format("  心跳状态: %d %s", status, status == 0 ? "(正常)" : "(异常)");
        }
        return "  心跳响应: 正常";
    }

    private String parseUnlockResponse(byte[] data) {
        boolean unlockSuccess = false;
        String result;

        if (data.length >= 2 && data[0] == 0x4F && data[1] == 0x78) {
            result = "  解锁状态: ✅ 成功 (确认码: 20344)";
            unlockSuccess = true;
        } else if (data.length >= 2) {
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            unlockSuccess = (status == 0);
            result = String.format("  解锁状态: %s (状态码: %d)", unlockSuccess ? "✅ 成功" : "❌ 失败", status);
        } else {
            result = "  解锁状态: ✅ 成功 (空响应)";
            unlockSuccess = true;
        }

        // 更新解锁状态
        if (unlockSuccess && !deviceUnlocked) {
            deviceUnlocked = true;
            unlockRetryCount = 0;
            Log.d(TAG, "✅ 设备解锁状态已更新 (通过parseUnlockResponse)");
            new Handler(Looper.getMainLooper()).post(() -> callback.onDeviceUnlocked());
        }

        return result;
    }

    private String parseSguSoundsResponse(byte[] data) {
        return String.format("  SGU音效列表: 收到%d字节数据", data.length);
    }

    private String parseFileDataTransferResponseDetailed(byte[] data) {
        // 命令114: 如果commandData长度为0，说明传输成功
        if (data.length == 0) {
            return "  文件传输: ✅ 数据块传输成功";
        }

        if (data.length >= 2) {
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            String statusText = (status == 0) ? "✅ 数据块接收成功" : "❌ 数据块传输失败";
            if (data.length >= 6) {
                int offset = ByteBuffer.wrap(data, 2, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
                return String.format("  文件传输: %s, 偏移量=%d", statusText, offset);
            }
            return String.format("  文件传输: %s", statusText);
        }
        return "  文件传输: 响应格式异常";
    }

    private String parseFileCommitResponseDetailed(byte[] data) {
        // 命令115: 文件提交响应
        // 根据日志分析，响应通常为空数据(仅填充)，表示提交成功
        if (data.length == 0) {
            return "  文件提交: ✅ 文件提交成功，设备已确认";
        }

        // 如果有数据，可能包含状态信息
        if (data.length >= 2) {
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            String statusText = (status == 0) ? "✅ 提交成功" : "❌ 提交失败";
            return String.format("  文件提交: %s (状态码: %d)", statusText, status);
        }

        return "  文件提交: ✅ 提交确认 (标准响应)";
    }

    private String parseFileGroupCommitResponseDetailed(byte[] data) {
        // 命令116: 文件组提交响应
        // 根据日志分析，响应通常为空数据(仅填充)，表示组提交成功
        if (data.length == 0) {
            return "  文件组提交: ✅ 音效包提交成功，已应用到设备";
        }

        // 如果有数据，可能包含状态信息
        if (data.length >= 2) {
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            String statusText = (status == 0) ? "✅ 组提交成功" : "❌ 组提交失败";
            return String.format("  文件组提交: %s (状态码: %d)", statusText, status);
        }

        return "  文件组提交: ✅ 音效包安装完成";
    }

    private String parseDeviceParametersResponseDetailed(byte[] data) {
        return String.format("  设备参数: 收到%d字节参数数据", data.length);
    }

    private String parseDeviceInfoResponseDetailed(byte[] data) {
        return String.format("  设备信息: 收到%d字节信息数据 (前16字节: %s)",
                data.length, bytesToHex(Arrays.copyOfRange(data, 0, Math.min(16, data.length))));
    }

    private String parseCanInfoResponse(byte[] data) {
        if (data.length < 4) {
            return "  CAN配置信息: 数据不足，无法解析\n  原始数据: " + bytesToHex(data);
        }

        try {
            // 根据jadx ReadCanInfoBleResponse.doHandleResponse() 分析
            // CAN配置ID (bytes[0-1], 大端序)
            int canId = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);

            // CAN配置版本 (bytes[2-3], 大端序) 
            int canVersion = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);

            StringBuilder result = new StringBuilder();
            result.append("  CAN配置信息:\n");
            result.append(String.format("    ├─ CAN配置ID: %d (0x%04X)\n", canId, canId));
            result.append(String.format("    ├─ CAN配置版本: %d (0x%04X)\n", canVersion, canVersion));

            if (canId == 0 && canVersion == 0) {
                result.append("    └─ 状态: ⚠️ 设备未安装CAN配置文件（出厂状态）\n");
                result.append("           💡 需要选择车型并下载对应的CAN配置文件");
            } else {
                result.append("    └─ 状态: ✅ 设备已安装CAN配置文件");
            }

//            result.append(String.format("\n    原始数据: %s", bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return String.format("  CAN配置信息: 解析失败 - %s\n  原始数据: %s",
                    e.getMessage(), bytesToHex(data));
        }
    }

    private String parseFileUploadStartResponse(byte[] data) {
        if (data.length >= 5) {
            // 基于用户实测数据: [0,0,0,108,0] 和jadx分析
            // 实际fileSize在index 3-4, maxBlockSize在更后面
            int fileSize = ((data[4] & 0xFF) << 8) | (data[3] & 0xFF); // 小端序读取字节3-4
            if (data.length >= 6) {
                int maxBlockSize = data[5] & 0xFF;
                if (maxBlockSize < 0) maxBlockSize += 256; // 处理负数 (-56 -> 200)
                return String.format("  文件上传: 文件大小=%d字节, 最大块大小=%d字节", fileSize, maxBlockSize);
            }
            return String.format("  文件上传: 文件大小=%d字节", fileSize);
        } else if (data.length >= 3) {
            // 备用解析方案
            int fileSize = ((data[1] & 0xFF) << 8) | (data[0] & 0xFF);
            int maxBlockSize = data[2] & 0xFF;
            return String.format("  文件上传: 文件大小=%d字节, 最大块大小=%d字节 (备用解析)", fileSize, maxBlockSize);
        }
        return "  文件上传: 响应格式异常";
    }

    private String parseFileReadBlockResponse(byte[] data) {
        if (data.length >= 4) {
            // 基于jadx UploadReadBlockBleResponse.java分析
            // 前4字节是偏移量，后面是文件数据
            int offset = ByteBuffer.wrap(data, 0, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
            int fileDataLength = data.length - 4;
            String fileHeader = "";
            if (fileDataLength >= 4) {
                byte[] headerBytes = Arrays.copyOfRange(data, 4, 8);
                fileHeader = String.format(" (文件头: %s)", bytesToHex(headerBytes));
            }
            return String.format("  文件块读取: 偏移量=%d, 数据长度=%d字节%s", offset, fileDataLength, fileHeader);
        }
        return "  文件块读取: 响应格式异常";
    }

    private String parseSystemInfoResponseDetailed(byte[] data) {
        return String.format("  系统信息: 收到%d字节数据 (需要进一步解析)", data.length);
    }

    private String parseSettingsGroupResponseDetailed(byte[] data) {
        return String.format("  设置组: 收到%d字节配置数据", data.length);
    }

    private byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X ", b));
        }
        return result.toString().trim();
    }

    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
            bluetoothGatt = null;
        }
        connected = false;
        handshakeComplete = false;
        deviceUnlocked = false;
        unlockRetryCount = 0;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isHandshakeComplete() {
        return handshakeComplete;
    }

    public boolean isDeviceUnlocked() {
        return deviceUnlocked;
    }

    // ==== 新增响应解析方法 ====

    // 解析系统信息响应 (命令126)
    private String parseSystemInfoResponse(byte[] data) {
        if (data.length < 2) {
            return "状态: 数据不足，无法解析\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();
            result.append("状态: 成功接收系统信息\n");

            // 根据Thor协议解析系统信息
            if (data.length >= 4) {
                int field1 = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
                int field2 = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
                result.append("系统字段1: ").append(field1).append("\n");
                result.append("系统字段2: ").append(field2).append("\n");
            }

            result.append("原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    // 解析状态查询响应 (命令129)
    private String parseStatusQueryResponse(byte[] data) {
        if (data.length < 2) {
            return "状态: 数据不足，无法解析\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();
            result.append("状态: 成功接收设备状态\n");

            // 解析设备状态
            if (data.length >= 2) {
                int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
                result.append("设备状态码: ").append(status);

                if (status == 0) {
                    result.append(" (正常)");
                } else {
                    result.append(" (异常)");
                }
                result.append("\n");
            }

            result.append("原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    // 解析设置组响应 (命令56)
    private String parseSettingsGroupResponse(byte[] data) {
        if (data.length < 2) {
            return "状态: 数据不足，无法解析\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();
            result.append("状态: 成功接收设置组数据\n");

            // 解析设置组数据
            if (data.length >= 4) {
                int groupId = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
                int settingCount = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);

                result.append("设置组ID: ").append(groupId).append("\n");
                result.append("设置项数量: ").append(settingCount).append("\n");

                // 解析具体设置项
                int offset = 4;
                for (int i = 0; i < settingCount && offset + 3 < data.length; i++) {
                    int settingId = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
                    int settingValue = ((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF);

                    result.append(String.format("- 设置%d: ID=%d, 值=%d\n", i + 1, settingId, settingValue));
                    offset += 4;
                }
            }

            result.append("原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    // 解析文件数据传输响应 (命令114)
    private String parseFileDataTransferResponse(byte[] data, int paddingLength, int separator, int cmd) {
        if (data.length < 2) {
            return "状态: 数据不足，期望至少2字节\n原始数据: " + bytesToHex(data);
        }

        try {
            StringBuilder result = new StringBuilder();
            result.append("业务解析结果:\n");
            result.append(String.format("明文结构: 填充=%d, 分隔符=%d, 命令=%d\n", paddingLength, separator, cmd));

            // 解析状态码 (前2字节)
            int status = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
            result.append(String.format("状态码: 0x%04X (%d)", status, status));

            if (status == 0) {
                result.append(" ✅ 数据块传输成功\n");
                result.append("设备已确认接收文件数据块");
            } else {
                result.append(" ❌ 数据块传输失败\n");
                result.append("错误代码: ").append(String.format("0x%04X", status));
            }

            // 如果有偏移量信息 (字节2-5)
            if (data.length >= 6) {
                int offset = ByteBuffer.wrap(data, 2, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
                result.append(String.format("\n下一块偏移量: %d", offset));
            }

            // 如果有其他响应数据
            if (data.length > 6) {
                result.append("\n附加响应数据: ").append(bytesToHex(Arrays.copyOfRange(data, 6, data.length)));
            }

            result.append("\n原始数据: ").append(bytesToHex(data));
            return result.toString();

        } catch (Exception e) {
            return "状态: 业务解析失败 - " + e.getMessage() + "\n原始数据: " + bytesToHex(data);
        }
    }

    // 解析下载状态 (命令117)
    private void parseDownloadStatus(String result) {
        try {
            // 从117响应的原始数据中解析状态
            if (result.contains("原始数据:")) {
                String dataLine = result.substring(result.indexOf("原始数据:") + 5);
                String[] dataBytes = dataLine.trim().split("\\s+");

                if (dataBytes.length >= 2) {
                    // 状态码在第2个字节（索引1）
                    int status = Integer.parseInt(dataBytes[1], 16);

                    callback.printlog("📊 下载状态解析:");
                    callback.printlog(String.format("  ├─ 状态码: %d", status));

                    switch (status) {
                        case 0:
                            callback.printlog("  └─ READY(0) - 准备状态，需要先执行112启动组");
                            break;
                        case 1:
                            callback.printlog("  └─ GROUP_STARTED(1) - 组已启动，需要执行113启动文件");
                            break;
                        case 2:
                            callback.printlog("  └─ FILE_STARTED(2) - 文件已启动，但还未进入下载状态");
                            callback.printlog("  └─ ⚠️ 需要等待状态变为DOWNLOADING(3)才能发送114");
                            break;
                        case 3:
                            callback.printlog("  └─ DOWNLOADING(3) - ✅ 可以发送114数据块");
                            break;
                        case 4:
                            callback.printlog("  └─ FILE_COMMITTED(4) - 文件已提交");
                            break;
                        case 5:
                            callback.printlog("  └─ GROUP_COMMITTED(5) - 组已提交");
                            break;
                        default:
                            callback.printlog(String.format("  └─ 未知状态(%d)", status));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "解析下载状态失败: " + e.getMessage());
        }
    }

    // 解析已安装音效包 (命令71)
    private void parseInstalledSoundPackages(String result) {
        try {
            callback.printlog("🔍 开始解析命令71响应数据...");

            // 从71响应的原始数据中解析音效包列表
            if (result.contains("原始数据:")) {
                String dataLine = result.substring(result.indexOf("原始数据:") + 5);
                String[] dataBytes = dataLine.trim().split("\\s+");

                callback.printlog(String.format("📋 原始数据字节数: %d", dataBytes.length));
                callback.printlog(String.format("📋 原始数据内容: %s", dataLine.trim()));

                // 根据实际logcat数据: [15, 0, 71, 0, 2, 0, 29, 0, 1, 0, 7, 0, 27, 0, 1, 0, 7, -91, ...]
                // 协议格式: 填充长度(15) + 分隔符(0) + 命令ID(71) + 实际数据[0, 2, 0, 29, 0, 1, 0, 7, 0, 27, 0, 1, 0, 7, ...]

                if (dataBytes.length >= 6) {
                    try {
                        // 跳过协议头部: 填充长度[0] + 分隔符[1] + 命令ID[2]
                        // 从索引3开始是实际响应数据
                        int statusByte = Integer.parseInt(dataBytes[3], 16); // 状态字节
                        int amount = Integer.parseInt(dataBytes[4], 16); // 音效包数量

                        callback.printlog("📦 已安装音效包解析:");
                        callback.printlog(String.format("  ├─ 状态字节: 0x%02X (%d)", statusByte, statusByte));
                        callback.printlog(String.format("  ├─ 音效包数量: %d", amount));

                        if (amount == 0) {
                            callback.printlog("  └─ 无已安装音效包");
                            return;
                        }

                        // 检查数据是否足够解析所有音效包 (每个包占6字节)
                        int dataStartIndex = 5; // 从索引5开始是音效包数据
                        int requiredBytes = dataStartIndex + (amount * 6);

                        callback.printlog(String.format("  ├─ 数据起始索引: %d", dataStartIndex));
                        callback.printlog(String.format("  ├─ 需要字节数: %d, 实际字节数: %d", requiredBytes, dataBytes.length));

                        // 解析每个音效包信息 (每个包占6字节: packageId[2] + versionId[2] + mode[2])
                        int dataIndex = dataStartIndex;
                        for (int i = 0; i < amount && dataIndex + 5 < dataBytes.length; i++) {
                            try {
                                // packageId (2字节，大端序)
                                int packageIdHigh = Integer.parseInt(dataBytes[dataIndex], 16);
                                int packageIdLow = Integer.parseInt(dataBytes[dataIndex + 1], 16);
                                int packageId = (packageIdHigh << 8) | packageIdLow;

                                // versionId (2字节，大端序)
                                int versionIdHigh = Integer.parseInt(dataBytes[dataIndex + 2], 16);
                                int versionIdLow = Integer.parseInt(dataBytes[dataIndex + 3], 16);
                                int versionId = (versionIdHigh << 8) | versionIdLow;

                                // mode (2字节，大端序)
                                int modeHigh = Integer.parseInt(dataBytes[dataIndex + 4], 16);
                                int modeLow = Integer.parseInt(dataBytes[dataIndex + 5], 16);
                                int mode = (modeHigh << 8) | modeLow;

                                callback.printlog(String.format("  ├─ 音效包 %d:", i + 1));
                                callback.printlog(String.format("  │  ├─ 原始字节: %s %s %s %s %s %s",
                                        dataBytes[dataIndex], dataBytes[dataIndex + 1], dataBytes[dataIndex + 2],
                                        dataBytes[dataIndex + 3], dataBytes[dataIndex + 4], dataBytes[dataIndex + 5]));
                                callback.printlog(String.format("  │  ├─ packageId: %d (0x%04X)", packageId, packageId));
                                callback.printlog(String.format("  │  ├─ versionId: %d (0x%04X)", versionId, versionId));
                                callback.printlog(String.format("  │  └─ mode: %d (0x%04X)", mode, mode));

                                dataIndex += 6; // 移动到下一个音效包
                            } catch (Exception e) {
                                callback.printlog(String.format("  │  ✖ 解析音效包 %d 失败: %s", i + 1, e.getMessage()));
                                callback.printlog(String.format("  │  ✖ 当前索引: %d, 剩余字节: %d", dataIndex, dataBytes.length - dataIndex));
                                break;
                            }
                        }
                        callback.printlog("  └─ 音效包列表解析完成");
                    } catch (NumberFormatException e) {
                        callback.printlog(String.format("❌ 解析音效包数量失败: %s", e.getMessage()));
                        callback.printlog(String.format("❌ 尝试解析的字节: [3]=%s, [4]=%s", dataBytes[3], dataBytes[4]));
                    }
                } else {
                    callback.printlog(String.format("⚠️ 音效包响应数据不足，无法解析，实际字节数: %d", dataBytes.length));
                    for (int i = 0; i < Math.min(dataBytes.length, 15); i++) {
                        callback.printlog(String.format("  字节[%d]: %s", i, dataBytes[i]));
                    }
                }
            } else {
                callback.printlog("❌ 响应数据中未找到'原始数据:'标记");
            }
        } catch (Exception e) {
            Log.e(TAG, "解析已安装音效包失败: " + e.getMessage());
            callback.printlog("❌ 解析已安装音效包失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==== 根据Frida日志实现的7个命令方法 ====

    // 命令8: 心跳测试 - [0,8]
    public void sendCommand8Heartbeat() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        // Frida明文: [0, 8] -> 分隔符=0, 命令ID=8
        byte[] cmd = createCommandData(8);
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应，然后解析打印
        String result = sendDataAndWaitResponse(packet, 8, "心跳测试", 3000);
        if (result != null) {
            callback.printlog("=== 命令8响应 ===\n" + result);
        } else {
            callback.printlog("=== 命令8响应 ===\n❌ 超时或失败");
        }
    }

    // 命令128: 文件上传 - [7,0,128,8,0,0,0,0,200,165,165,165,165,165,165]
    public void sendCommand128FileUpload() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        // Frida明文: [0,128,8,0,0,0,0] -> 分隔符=0, 命令ID=128, 参数=[8,0,0,0,0]
        byte[] cmd = createCommandData(128, new byte[]{8, 0, 0, 0, 0, -56});
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应，然后解析打印
        String result = sendDataAndWaitResponse(packet, 128, "文件上传启动", 5000);
        if (result != null) {
            callback.printlog("=== 命令128响应 ===\n" + result);
        } else {
            callback.printlog("=== 命令128响应 ===\n❌ 超时或失败");
        }
    }

    // 命令129: 读取文件块 - [0,129,偏移量(4字节)]
    public void sendCommand129StatusQuery() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        // Frida明文: [0,129,0,0,0,0] -> 分隔符=0, 命令ID=129, 偏移量=[0,0,0,0]
        byte[] cmd = createCommandData(129, new byte[]{0, 0, 0, 0});
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应，然后解析打印
        String result = sendDataAndWaitResponse(packet, 129, "读取文件块", 5000);
        if (result != null) {
            callback.printlog("=== 命令129响应 ===\n" + result);
        } else {
            callback.printlog("=== 命令129响应 ===\n❌ 超时或失败");
        }
    }

    // 命令126: 系统信息 - [0,126]
    public void sendCommand130() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        byte[] cmd = createCommandData(130);
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应，然后解析打印
        String result = sendDataAndWaitResponse(packet, 130, "系统信息", 5000);
        if (result != null) {
            callback.printlog("=== 命令130响应 ===\n" + result);
        } else {
            callback.printlog("=== 命令130响应 ===\n❌ 超时或失败");
        }
    }

    // 命令12: 解锁设备 - [0,12,120,79]
    public void sendCommand12Unlock() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        // Frida明文: [0,12,79,120] -> 分隔符=0, 命令ID=12, 解锁码=[79,120] (20344大端序)
        byte[] cmd = createCommandData(12, new byte[]{79, 120});
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应，然后解析打印
        String result = sendDataAndWaitResponse(packet, 12, "设备解锁", 3000);
        if (result != null) {
            callback.printlog("=== 命令12响应 ===\n" + result);
            // 额外检查解锁状态
            if (deviceUnlocked) {
                callback.printlog("✅ 设备解锁成功，高级命令现在可用");
            }
        } else {
            callback.printlog("=== 命令12响应 ===\n❌ 超时或失败");
        }
    }

    // 命令56: 读取设置组1 - [0,56,1]
    public void sendCommand56(byte setting_group_id) {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        if (!deviceUnlocked) {
            Log.w(TAG, "⚠️ 警告：设备可能未解锁，命令56可能无响应");
            Log.w(TAG, "💡 建议：先发送命令12解锁设备");
        }

        byte[] cmd3 = createCommandData(56, new byte[]{0, setting_group_id});
        byte[] packet3 = createPacket(cmd3, 1);
        sendData(packet3);

    }

    public void sendCommand69() {

        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        byte[] cmd3 = createCommandData(69, new byte[]{0, 0});
        byte[] packet3 = createPacket(cmd3, 1);
        sendData(packet3);

//        recv_parse();
    }

    public void sendCommand71() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        byte[] cmd71 = createCommandData(71);
        byte[] packet71 = createPacket(cmd71, 1);

        // 发送并等待响应，解析已安装的音效包
        String result = sendDataAndWaitResponse(packet71, 71, "读取已安装音效包", 5000);
        if (result != null) {
            callback.printlog("=== 命令71读取已安装音效包 ===\n" + result);
            // 解析音效包信息
            if (result.contains("原始数据:")) {
                parseInstalledSoundPackages(result);
            }
        } else {
            callback.printlog("=== 命令71读取已安装音效包 ===\n❌ 超时或失败");
        }
    }

    public void sendCommand117() {

        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        byte[] cmd3 = createCommandData(117);
        byte[] packet3 = createPacket(cmd3, 1);

        // 发送并等待响应，解析状态
        String result = sendDataAndWaitResponse(packet3, 117, "状态查询", 5000);
        if (result != null) {
            callback.printlog("=== 命令117状态查询 ===\n" + result);
            // 解析状态信息
            if (result.contains("原始数据:")) {
                // 提取状态信息并解析
                parseDownloadStatus(result);
            }
        } else {
            callback.printlog("=== 命令117状态查询 ===\n❌ 超时或失败");
        }
    }

    public void sendCommand112(byte file_count) {

        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        byte[] cmd3 = createCommandData(112, new byte[]{0, file_count});
        byte[] packet3 = createPacket(cmd3, 1);
        sendData(packet3);

        // 发送并等待响应
        String result = sendDataAndWaitResponse(packet3, 112, "提交文件下载", 5000);

//        Log.d(TAG, "<----------" + result);
    }

    public void sendCommand113(String filename, byte fileType, byte[] package_id, byte version) {

        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }
        
        /*数据结构详解（根据jadx UploadStartFileBleRequest）：
        fileType = 4 (SoundSamplePackageV2)  - 1字节
        packageId = 107 (短整型，大端序) - 2字节  
        versionId = 1 - 1字节
        fileSize = 实际文件大小 (整型，大端序) - 4字节
        */

        // 使用与命令114相同的文件计算精确大小
        int actualFileSize;
        try {
            InputStream inputStream = context.getAssets().open(filename);
            actualFileSize = inputStream.available();
            inputStream.close();
        } catch (Exception e) {
            callback.onError("无法读取文件大小: " + e.getMessage());
            return;
        }
        callback.printlog(String.format("发送命令113 - 文件开始 (实际文件大小: %d字节)", actualFileSize));

        // 构造参数：[fileType, packageId_high, packageId_low, versionId, size_byte1, size_byte2, size_byte3, size_byte4]
        byte[] fileParams = new byte[8];
        fileParams[0] = fileType;  // fileType: SoundSamplePackageV2
        fileParams[1] = package_id[0];  // packageId high byte (107 = 0x006B)
        fileParams[2] = package_id[1]; // packageId low byte
        fileParams[3] = version;   // versionId

        // 文件大小 (大端序)
        fileParams[4] = (byte) ((actualFileSize >> 24) & 0xFF);
        fileParams[5] = (byte) ((actualFileSize >> 16) & 0xFF);
        fileParams[6] = (byte) ((actualFileSize >> 8) & 0xFF);
        fileParams[7] = (byte) (actualFileSize & 0xFF);

        byte[] cmd113 = createCommandData(113, fileParams);
        byte[] packet113 = createPacket(cmd113, 1);
        sendData(packet113);

        callback.printlog(String.format("命令113发送完成 - 等待设备状态转换为DOWNLOADING(3)"));

        // 发送并等待响应
        String result = sendDataAndWaitResponse(packet113, 113, "提交文件下载", 5000);

    }


    // 命令56: 读取设置组3 - [0,56,3]
    public void sendCommand56ReadSettings3() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        if (!deviceUnlocked) {
            Log.w(TAG, "⚠️ 警告：设备可能未解锁，命令56可能无响应");
            Log.w(TAG, "💡 建议：先发送命令12解锁设备");
        }

//        Log.d(TAG, "发送命令56 - 读取设置组3");

        // Frida明文: [0,56,3] -> 分隔符=0, 命令ID=56, 参数=3
        byte[] cmd = createCommandData(56, new byte[]{3});
        byte[] packet = createPacket(cmd, 1);

        String details = "命令: READ_SETTINGS_GROUP_3\n" + "参数: [3]\n" + "功能: 读取设置组3的配置项";

//        callback.onCommandSent(56, getCommandName(56), details, packet);
        sendData(packet);


    }

    public void sendCommand115() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        callback.printlog("发送命令115 - 提交文件下载完成");

        // 命令115: DOWNLOAD_COMMIT_FILE - 无参数，仅命令ID
        byte[] cmd = createCommandData(115);
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应
        String result = sendDataAndWaitResponse(packet, 115, "提交文件下载", 5000);
        if (result != null) {
            callback.printlog("=== 命令115响应 ===\n" + result);
            // 检查是否提交成功
            if (result.contains("115")) {
                callback.printlog("✅ 文件提交成功，设备已确认文件下载完成");
            }
        } else {
            callback.printlog("=== 命令115响应 ===\n❌ 超时或失败");
        }
    }

    public void sendCommand116() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        callback.printlog("发送命令116 - 提交文件组下载完成");

        // 命令116: DOWNLOAD_COMMIT_GROUP - 无参数，仅命令ID
        byte[] cmd = createCommandData(116);
        byte[] packet = createPacket(cmd, 1);

        // 发送并等待响应
        String result = sendDataAndWaitResponse(packet, 116, "提交文件组下载", 5000);
        if (result != null) {
            callback.printlog("=== 命令116响应 ===\n" + result);
            // 检查是否提交成功
            if (result.contains("116")) {
                callback.printlog("✅ 文件组提交成功，音效包已完整下载");
            }
        } else {
            callback.printlog("=== 命令116响应 ===\n❌ 超时或失败");
        }
    }

    public void sendCommand114(String flag) {

        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        if (flag == "canbin") {
            // 在后台线程执行文件读取和传输，避免ANR
            new Thread(new Runnable() {
                @Override
                public void run() {


                    byte file_count = 1;
                    sendCommand112(file_count);

                    byte real_id = (byte) 1;
                    String filename = "canbin/MB_W222_2014_Lepestki_Mute_DriveSelect_v3.canbin";
                    byte filetype = (byte) 1;
                    byte[] package_id = new byte[]{0, real_id};
                    byte versionid = (byte) 1;

                    if (flag == "canbin") {
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }


                    // 所有文件传输完成后，提交整个文件组
                    sendCommand116();

                }
            }).start();

        } else if (flag.equals("all")) {
            // 统一下载所有三个文件：smprl, pkgrl, sample
            new Thread(new Runnable() {
                @Override
                public void run() {
                    callback.printlog("=== 开始统一下载流程 ===");
                    callback.printlog("📦 准备下载 smprl, pkgrl, sample 三个文件");

                    byte file_count = 3;  // 设置文件数量为3
                    sendCommand112(file_count);

                    byte real_id = (byte) 27;
                    byte versionid = (byte) 1;

                    try {
                        // 1. 首先下载 smprl 文件
                        callback.printlog("📥 1/3 开始下载 smprl 文件...");
                        String filename = "music/" + real_id + "/" + real_id + ".smprl";
                        byte filetype = (byte) 5;
                        byte[] package_id = new byte[]{0, real_id};
                        
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();
                        
                        callback.printlog("✅ smprl 文件下载完成");
                        Thread.sleep(1000);

                        // 2. 然后下载 pkgrl 文件
                        callback.printlog("📥 2/3 开始下载 pkgrl 文件...");
                        filename = "music/" + real_id + "/" + real_id + ".pkgrl";
                        filetype = (byte) 6;
                        
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();
                        
                        callback.printlog("✅ pkgrl 文件下载完成");
                        Thread.sleep(1000);

                        // 3. 最后下载 sample 文件 (最大的文件)
                        callback.printlog("📥 3/3 开始下载 sample 文件 (最大文件)...");
                        filename = "music/" + real_id + "/" + real_id + ".sample";
                        filetype = (byte) 4;
                        
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();
                        
                        callback.printlog("✅ sample 文件下载完成");
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        callback.onError("下载过程中断: " + e.getMessage());
                        return;
                    }

                    // 所有文件传输完成后，提交整个文件组
                    callback.printlog("📋 提交所有文件到设备...");
                    sendCommand116();
                    callback.printlog("🎉 统一下载流程完成！");

                }
            }).start();

        } else {

            // 在后台线程执行文件读取和传输，避免ANR
            new Thread(new Runnable() {
                @Override
                public void run() {


                    byte file_count = 1;
                    sendCommand112(file_count);


                    byte real_id = (byte) 27;
                    String filename = "music/" + real_id + "/" + real_id + ".sample";
                    byte filetype = (byte) 4;
                    byte[] package_id = new byte[]{0, real_id};
                    byte versionid = (byte) 1;

                    if (flag == "sample") {
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    if (flag == "smprl") {

                        filename = "music/" + real_id + "/" + real_id + ".smprl";
                        filetype = (byte) 5;
                        package_id = new byte[]{0, real_id};
                        versionid = (byte) 1;
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    if (flag == "pkgrl") {
                        filename = "music/" + real_id + "/" + real_id + ".pkgrl";
                        filetype = (byte) 6;
                        package_id = new byte[]{0, real_id};
                        versionid = (byte) 1;
                        sendCommand113(filename, filetype, package_id, versionid);
                        sendCommand114InBackground(filename);
                        sendCommand115();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    // 所有文件传输完成后，提交整个文件组
                    sendCommand116();

                }
            }).start();

        }
    }

    public void sendCommand74() {
        if (!handshakeComplete) {
            callback.onError("握手未完成，无法发送命令");
            return;
        }

        byte[] cmd74 = createCommandData(74);
        byte[] packet74 = createPacket(cmd74, 1);

        // 发送并等待响应，然后解析CAN配置信息
        String result = sendDataAndWaitResponse(packet74, 74, "读取CAN配置信息", 5000);
        if (result != null) {
            callback.printlog("=== 命令74读取CAN配置信息 ===\n" + result);
        } else {
            callback.printlog("=== 命令74读取CAN配置信息 ===\n❌ 超时或失败");
        }
    }

    private void sendCommand114InBackground(String filename) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            byte[] buffer = new byte[200];
            int bytesRead;
            int totalBytesRead = 0;
            int chunkIndex = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                chunkIndex++;

                // 构造数据块参数：[块索引2字节] + [本块数据大小2字节]
                byte[] chunkData = new byte[4 + bytesRead];
                chunkData[0] = (byte) ((chunkIndex >> 8) & 0xFF);        // 块索引高字节
                chunkData[1] = (byte) (chunkIndex & 0xFF);               // 块索引低字节
                chunkData[2] = (byte) ((bytesRead >> 8) & 0xFF);         // 本块数据大小高字节 
                chunkData[3] = (byte) (bytesRead & 0xFF);                // 本块数据大小低字节
                System.arraycopy(buffer, 0, chunkData, 4, bytesRead);

                byte[] cmd3 = createCommandData(114, chunkData);
                byte[] packet3 = createPacket(cmd3, 1);

                // 发送前记录详细信息 (每10块输出一次，减少ANR风险)
                if (chunkIndex % 10 == 0 || chunkIndex == 0) {
                    callback.printlog(String.format("📤 发送第%d块数据", chunkIndex));
//                    callback.printlog(String.format("  ├─ 当前偏移: %d 字节", totalBytesRead));
//                    callback.printlog(String.format("  └─ 块大小: %d 字节", bytesRead));
                }

                // 更新总字节数（用于下一块的偏移量计算）
                totalBytesRead += bytesRead;

                String result = sendDataAndWaitResponse(packet3, 114, "文件数据传输块" + chunkIndex, 5000);

                if (result != null) {
                    Log.d(TAG, String.format("第%d块收到响应: %s", chunkIndex, result));

                    // 响应日志也只在每10块输出一次，减少ANR风险
                    if (chunkIndex % 10 == 0 || chunkIndex == 0) {
                        callback.printlog(String.format("📥 第%d块收到响应", chunkIndex));

                        if (result.contains("状态码:")) {
                            String statusPart = result.substring(result.indexOf("状态码:"));
                            String statusLine = statusPart.split("\n")[0];
                            callback.printlog(String.format("  └─ %s", statusLine));
                        }
                    }

                    // 检查响应是否为成功状态
                    boolean isSuccess = result.contains("✅ 数据块传输成功") || result.contains("✅ 数据块接收成功");

                    // 如果状态码不是成功，停止传输
                    if (!isSuccess) {
                        callback.printlog(String.format("❌ 第%d块传输失败，停止传输", chunkIndex));
                        inputStream.close();

                        final int failedChunk = chunkIndex;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(String.format("第%d块传输失败（错误响应），停止传输", failedChunk));
                            }
                        });
                        return;
                    }
                } else {
                    Log.e(TAG, String.format("第%d块传输失败或超时", chunkIndex));
                    // 失败时始终输出日志，便于调试
                    callback.printlog(String.format("❌ 第%d块传输失败", chunkIndex));
                    callback.printlog("  └─ 超时或无响应");
                    inputStream.close();

                    // 在主线程执行错误回调
                    final int failedChunk = chunkIndex;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(String.format("第%d块传输失败或超时，停止传输", failedChunk));
                        }
                    });
                    return;
                }
            }

            inputStream.close();
//            Log.d(TAG, String.format("文件发送完成! 总共发送: %d 字节，共 %d 块", totalBytesRead, chunkIndex));

            // 在主线程执行UI回调
            final int finalTotalBytes = totalBytesRead;
            final int finalChunkCount = chunkIndex;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.printlog(String.format("✅ 文件传输完成!\n总字节数: %d\n总块数: %d\n平均块大小: %.1f 字节",
                            finalTotalBytes, finalChunkCount, (double) finalTotalBytes / finalChunkCount));
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "读取文件失败，详细错误: ", e);
            Log.e(TAG, "错误类型: " + e.getClass().getSimpleName());

            // 在主线程执行错误回调
            final String errorMessage = e.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError("读取文件失败: " + errorMessage);
                }
            });
        }
    }

    // =====================================
    // 音效音量控制相关方法 (新增功能)
    // =====================================

    /**
     * 发送音效包音量设置命令 (基于Frida抓包分析)
     * 对应BleManager.executeWriteInstalledSoundPackageRules方法
     * @param packageId 音效包ID
     * @param ruleId 规则ID (0=主音量, 24=空闲音, 25=旋转工作音, 48=引擎音效)
     * @param volume 音量值 (0-100)
     */
    public void sendVolumeCommand(int packageId, int ruleId, int volume) {
        if (!isConnected() || !handshakeComplete) {
            callback.onError("设备未连接或握手未完成");
            return;
        }

        Log.d(TAG, String.format("发送音量调整命令: packageId=%d, ruleId=%d, volume=%d", packageId, ruleId, volume));
        callback.appendLog("音量控制", "发送音量调整", 
                          String.format("音效包ID: %d, 规则ID: %d, 音量: %d%%", packageId, ruleId, volume));

        // 基于官方Thor应用逆向分析，使用WriteInstalledSoundPackageRulesRequest格式
        // 命令67 (WRITE_INSTALLED_PRESET_RULES) - 严格按照官方应用格式构造
        byte[] volumeParams = createWriteInstalledPresetRulesData(packageId, ruleId, volume);
        byte[] cmd = createThorCommandData(67, volumeParams);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }

    /**
     * 创建WriteInstalledSoundPackageRulesRequest命令数据
     * 基于官方Thor应用逆向分析的精确格式
     * 格式: [packageId(2), versionId(2), mode(2), count(2), ruleId(2), ruleValue(2)]
     */
    private byte[] createWriteInstalledPresetRulesData(int packageId, int ruleId, int volume) {
        // 基于官方Thor应用实际Frida抓包分析
        // 官方命令格式: 1,0,67,0,27,0,0,0,3,0,1,0,0,0,100
        // 其中载荷部分: 0,27,0,0,0,3,0,1,0,0,0,100 (设置packageId=27, ruleId=0, volume=100)
        // 空载量格式: 1,0,67,0,27,0,0,0,3,0,1,0,48,0,XX (ruleId=48时需要特殊处理)
        
        byte[] data = new byte[12];
        int i = 0;
        
        // 1. 第一个字节固定为0
        data[i++] = 0x00;
        
        // 2. PackageId (只用一个字节)
        data[i++] = (byte) (packageId & 0xFF);
        
        // 3-4. 固定为 0,0
        data[i++] = 0x00;
        data[i++] = 0x00;
        
        // 5-6. 固定为 0,3 (可能是版本或模式标识)
        data[i++] = 0x00;
        data[i++] = 0x03;
        
        // 7-8. 固定为 0,1 (规则数量=1)
        data[i++] = 0x00;
        data[i++] = 0x01;
        
        // 9-10. 所有音效都使用统一格式 0,ruleId
        data[i++] = 0x00;
        data[i++] = (byte) (ruleId & 0xFF);
        
        // 11-12. 固定为 0,volume值
        data[i++] = 0x00;
        data[i++] = (byte) (volume & 0xFF);
        
        Log.d(TAG, String.format("WriteInstalledPresetRules数据(官方格式): packageId=%d, ruleId=%d, volume=%d, 载荷: %s", 
                                 packageId, ruleId, volume, bytesToHex(data)));
        
        return data;
    }

    /**
     * 创建播放音效音量控制数据
     * 基于命令34 PLAY_SGU_SOUND格式: [sound_id(2), repeat(2), mute(2), volume(2), reserved(2)]
     */
    private byte[] createPlaySoundVolumeData(int soundId, int volume) {
        List<Byte> data = new ArrayList<>();
        
        // sound_id - 2字节大端序 (使用传入的soundId，可能是packageId)
        data.add((byte) ((soundId >> 8) & 0xFF));
        data.add((byte) (soundId & 0xFF));
        
        // repeat - 2字节大端序 (0表示不重复)
        data.add((byte) 0);
        data.add((byte) 0);
        
        // mute - 2字节大端序 (0表示不静音)
        data.add((byte) 0);
        data.add((byte) 0);
        
        // volume - 2字节大端序 (实际音量值)
        data.add((byte) ((volume >> 8) & 0xFF));
        data.add((byte) (volume & 0xFF));
        
        // reserved - 2字节大端序 (保留字段)
        data.add((byte) 0);
        data.add((byte) 0);

        // 转换为字节数组
        byte[] result = new byte[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i);
        }
        
        Log.d(TAG, String.format("播放音效音量数据: sound_id=%d, volume=%d, 数据长度=%d", 
                                 soundId, volume, result.length));
        
        return result;
    }

    /**
     * 批量设置音效包的所有音量参数
     * @param packageId 音效包ID
     * @param mainVolume 主音量 (0-100)
     * @param idleVolume 空闲音音量 (0-100)
     * @param workingVolume 旋转工作音音量 (0-100)
     * @param engineVolume 引擎音效音量 (0-100)
     */
    public void setBatchVolume(int packageId, int mainVolume, int idleVolume, int workingVolume, int engineVolume) {
        if (!isConnected() || !handshakeComplete) {
            callback.onError("设备未连接或握手未完成");
            return;
        }

        callback.appendLog("批量音量设置", "开始批量设置音量", 
                          String.format("音效包%d: 主音量=%d, 空闲音=%d, 工作音=%d, 引擎音=%d", 
                                      packageId, mainVolume, idleVolume, workingVolume, engineVolume));

        // 按序列发送音量设置命令 (基于Frida抓包观察到的顺序)
        handler.post(() -> {
            sendVolumeCommand(packageId, 0, mainVolume);    // 主音量
            
            handler.postDelayed(() -> {
                sendVolumeCommand(packageId, 24, idleVolume);   // 空闲音
            }, 100);
            
            handler.postDelayed(() -> {
                sendVolumeCommand(packageId, 25, workingVolume); // 旋转工作音
            }, 200);
            
            handler.postDelayed(() -> {
                sendVolumeCommand(packageId, 48, engineVolume);  // 引擎音效
            }, 300);
            
            handler.postDelayed(() -> {
                // 发送停止命令 (基于Frida抓包分析)
                sendVolumeStopCommand(packageId);
            }, 400);
        });
    }

    /**
     * 发送音量设置停止命令
     * 基于Frida抓包分析的executeWriteInstalledSoundPackageRulesStop方法
     */
    private void sendVolumeStopCommand(int packageId) {
        Log.d(TAG, "发送音量设置停止命令: packageId=" + packageId);
        
        // 构造停止命令数据 (使用最后一个设置的规则)
        byte[] stopParams = createWriteInstalledPresetRulesData(packageId, 0, 100); // 使用主音量作为停止标记
        byte[] cmd = createCommandData(68, stopParams); // 假设68是停止命令
        byte[] packet = createPacket(cmd, 1); // 使用加密
        sendData(packet);
    }

    /**
     * 读取音效包当前音量设置
     * 基于Frida抓包分析的executeReadInstalledSoundPackageRulesCommand方法
     */
    public void readVolumeSettings(int packageId) {
        if (!isConnected() || !handshakeComplete) {
            callback.onError("设备未连接或握手未完成");
            return;
        }

        Log.d(TAG, "读取音效包音量设置: packageId=" + packageId);
        callback.appendLog("音量读取", "读取音量设置", "音效包ID: " + packageId);

        // 构造读取命令参数
        byte[] readParams = new byte[2];
        readParams[0] = (byte) ((packageId >> 8) & 0xFF);
        readParams[1] = (byte) (packageId & 0xFF);

        byte[] cmd = createCommandData(52, readParams); // 使用命令52读取
        byte[] packet = createPacket(cmd, 1); // 使用加密
        sendData(packet);
    }

    /**
     * 重置音效包音量为默认值
     */
    public void resetVolumeToDefaults(int packageId) {
        callback.appendLog("重置音量", "重置为默认值", "音效包ID: " + packageId);
        
        // 设置默认音量值 (基于Frida抓包分析的常见数值)
        setBatchVolume(packageId, 80, 50, 60, 40);
    }

    /**
     * 增强版音量设置命令 - 基于Frida抓包分析优化
     * 针对不同的音效类型使用更精确的协议参数
     * @param packageId 音效包ID
     * @param ruleId 规则ID (0=主音量, 48=空载量, 24=空闲音, 25=旋转工作音)
     * @param volume 音量值 (0-100)
     */
    public void sendVolumeCommandEnhanced(int packageId, int ruleId, int volume) {
        if (!isConnected() || !handshakeComplete) {
            callback.onError("设备未连接或握手未完成");
            return;
        }

        Log.d(TAG, String.format("发送增强版音量命令: packageId=%d, ruleId=%d, volume=%d", packageId, ruleId, volume));
        callback.appendLog("增强音量控制", "发送精确音量调整", 
                          String.format("音效包ID: %d, 规则ID: %d, 音量: %d%%", packageId, ruleId, volume));

        // 基于Frida抓包分析，不同的音效类型需要使用不同的协议参数
        byte[] volumeParams = createEnhancedVolumeRulesData(packageId, ruleId, volume);
        byte[] cmd = createThorCommandData(67, volumeParams);
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
        
        // 发送后延迟发送确认命令 (基于官方应用的行为模式)
        handler.postDelayed(() -> {
            sendVolumeConfirmCommand(packageId, ruleId);
        }, 50);
    }

    /**
     * 创建增强版音量规则数据
     * 基于Frida抓包分析，针对不同音效类型优化
     */
    private byte[] createEnhancedVolumeRulesData(int packageId, int ruleId, int volume) {
        byte[] data = new byte[12];
        int i = 0;
        
        // 1. 第一个字节固定为0
        data[i++] = 0x00;
        
        // 2. PackageId
        data[i++] = (byte) (packageId & 0xFF);
        
        // 3-4. 基于不同音效类型使用不同的标识
        data[i++] = 0x00;
        data[i++] = 0x00;
        
        // 5-6. 根据音效类型设置不同的模式值
        data[i++] = 0x00;
        data[i++] = getVolumeTypeMode(ruleId);  // 不同音效类型使用不同模式
        
        // 7-8. 规则数量=1
        data[i++] = 0x00;
        data[i++] = 0x01;
        
        // 9-10. 根据Frida抓包分析，某些类型需要特殊的子ID
        data[i++] = getVolumeSubId(ruleId);  
        data[i++] = (byte) (ruleId & 0xFF);
        
        // 11-12. 音量值，可能需要根据类型进行转换
        data[i++] = 0x00;
        data[i++] = (byte) (convertVolumeForType(ruleId, volume) & 0xFF);
        
        Log.d(TAG, String.format("增强版音量数据: packageId=%d, ruleId=%d, volume=%d, 载荷: %s", 
                                 packageId, ruleId, volume, bytesToHex(data)));
        
        return data;
    }

    /**
     * 根据音效类型获取对应的模式值
     */
    private byte getVolumeTypeMode(int ruleId) {
        switch (ruleId) {
            case 0:  return 0x03;  // 主音量
            case 48: return 0x03;  // 空载量
            case 24: return 0x03;  // 空闲音 
            case 25: return 0x03;  // 旋转工作音
            default: return 0x03;
        }
    }

    /**
     * 根据音效类型获取子ID
     */
    private byte getVolumeSubId(int ruleId) {
        switch (ruleId) {
            case 0:  return 0x00;  // 主音量
            case 48: return 0x30;  // 空载量
            case 24: return 0x00;  // 空闲音
            case 25: return 0x00;  // 旋转工作音  
            default: return 0x00;
        }
    }

    /**
     * 根据音效类型转换音量值
     */
    private int convertVolumeForType(int ruleId, int volume) {
        // 基于Frida抓包分析，某些音效类型可能需要不同的音量范围或转换
        switch (ruleId) {
            case 0:  return volume;           // 主音量 0-100
            case 48: return volume;           // 空载量 0-100
            case 24: return volume;           // 空闲音 0-100
            case 25: return volume;           // 旋转工作音 0-100
            default: return volume;
        }
    }

    /**
     * 发送音量设置确认命令
     */
    private void sendVolumeConfirmCommand(int packageId, int ruleId) {
        Log.d(TAG, String.format("发送音量确认命令: packageId=%d, ruleId=%d", packageId, ruleId));
        
        // 基于Frida抓包观察到的确认命令模式
        byte[] confirmParams = new byte[4];
        confirmParams[0] = 0x00;
        confirmParams[1] = (byte) (packageId & 0xFF);
        confirmParams[2] = 0x00;
        confirmParams[3] = (byte) (ruleId & 0xFF);
        
        byte[] cmd = createThorCommandData(68, confirmParams);  // 使用68作为确认命令
        byte[] packet = createPacket(cmd, 1);
        sendData(packet);
    }


}