package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.manager.SoundPackageManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ThorBluetooth.ThorCallback {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_PERMISSIONS = 1001;

    private ActivityMainBinding binding;
    private ThorBluetooth thorBluetooth;
    private TextView statusText;
    private TextView logTextView;
    private ScrollView logScrollView;
    private Button connectButton;
    private Button cmd58Button;
    private Button cmd59Button;
    private Button cmd128Button;
    private Button cmd129Button;
    private Button cmd130Button;
    private Button cmd12Button;
    private Button cmd56Button;
    private Button cmd69Button;
    private Button cmd71Button;
    private Button cmd117Button;
    private Button cmd112Button;
    //    private Button cmd113Button;
    private Button cmd114Button_sample;
    private Button cmd114Button_smprl;
    private Button cmd114Button_pkgrl;
    private Button cmd114Button_all;

    private Button cmd114Button_canbin;

    private Button btn_cmd_74;
    private Button clearLogButton;
    
    // 音效包音量控制按钮
    private Button btnVolumeControl;
    private Button btnReadVolumes;
    private Button btnResetVolumes;

    private SimpleDateFormat timeFormat;
    private StringBuilder logBuffer;
    
    private GoogleSignInClient mGoogleSignInClient;
    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化日志系统
        timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        logBuffer = new StringBuilder();
        
        // Configure Google Sign-In for sign-out functionality
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        
        // Get user info from intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        userEmail = intent.getStringExtra("USER_EMAIL");
        
        // Update title with user info
        if (userName != null) {
            setTitle("Thor Control - " + userName);
        }

        initViews();
        requestPermissions();

        thorBluetooth = new ThorBluetooth(this, this);
        // 设置到持有者中供其他Activity使用
        ThorBluetoothHolder.setInstance(thorBluetooth);

        // 显示初始日志
        appendLog("系统", "Thor 调试工具已启动", "等待用户操作...");
        if (userName != null) {
            appendLog("系统", "用户已登录", "欢迎, " + userName);
        }
    }

    private void initViews() {
        statusText = binding.statusText;
        logTextView = binding.logTextView;
        logScrollView = binding.logScrollView;
        connectButton = binding.btnConnect;
        cmd58Button = binding.btnCmd58;
        cmd59Button = binding.btnCmd59;
        cmd128Button = binding.btnCmd128;
        cmd129Button = binding.btnCmd129;
        cmd130Button = binding.btnCmd130;
        cmd12Button = binding.btnCmd12;
        cmd56Button = binding.btnCmd56;
        cmd69Button = binding.btnCmd69;
        cmd71Button = binding.btnCmd71;
        cmd117Button = binding.btnCmd117;
//        cmd112Button = binding.btnCmd112;
//        cmd113Button = binding.btnCmd113;
        cmd114Button_sample = binding.btnCmd114Sample;
        cmd114Button_smprl = binding.btnCmd114Smprl;
        cmd114Button_pkgrl = binding.btnCmd114Pkgrl;
        cmd114Button_all = binding.btnCmd114All;
        cmd114Button_canbin = binding.btnCmd114Canbin;

        btn_cmd_74 = binding.btnCmd74;
        clearLogButton = binding.btnClearLog;
        
        // 音效包音量控制按钮初始化
        btnVolumeControl = binding.btnVolumeControl;
        btnReadVolumes = binding.btnReadVolumes;
        btnResetVolumes = binding.btnResetVolumes;

        connectButton.setOnClickListener(v -> connectToDevice());
        cmd58Button.setOnClickListener(v -> sendCommand58());
        cmd59Button.setOnClickListener(v -> sendCommand59());
        cmd128Button.setOnClickListener(v -> sendCommand128());
        cmd129Button.setOnClickListener(v -> sendCommand129());
        cmd130Button.setOnClickListener(v -> sendCommand130());
        cmd12Button.setOnClickListener(v -> sendCommand12());
        cmd56Button.setOnClickListener(v -> sendCommand56());
        cmd69Button.setOnClickListener(v -> sendCommand69());
        cmd71Button.setOnClickListener(v -> sendCommand71());
        cmd117Button.setOnClickListener(v -> sendCommand117());
//        cmd112Button.setOnClickListener(v -> sendCommand112());
//        cmd113Button.setOnClickListener(v -> sendCommand113());
        cmd114Button_sample.setOnClickListener(v -> sendCommand114("sample"));
        cmd114Button_smprl.setOnClickListener(v -> sendCommand114("smprl"));
        cmd114Button_pkgrl.setOnClickListener(v -> sendCommand114("pkgrl"));
        cmd114Button_all.setOnClickListener(v -> sendCommand114("all"));
        cmd114Button_canbin.setOnClickListener(v -> sendCommand114("canbin"));


        btn_cmd_74.setOnClickListener(v -> sendCommand74());
        clearLogButton.setOnClickListener(v -> clearLog());
        
        // 音效包音量控制按钮事件
        btnVolumeControl.setOnClickListener(v -> openVolumeControl());
        btnReadVolumes.setOnClickListener(v -> readAllVolumes());
        btnResetVolumes.setOnClickListener(v -> resetAllVolumes());

        updateStatus("未连接");
    }

    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE
        };

        boolean needRequest = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                needRequest = true;
                break;
            }
        }

        if (needRequest) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
    }

    private void connectToDevice() {
        updateStatus("正在连接...");
        appendLog("系统", "开始扫描 Thor 设备", "扫描中...");
        connectButton.setEnabled(false);
        thorBluetooth.scanAndConnect();
    }

    private void sendCommand8() {
        Log.d(TAG, "用户点击命令8");
        appendLog("用户", "命令8", "心跳测试");
        thorBluetooth.sendCommand8Heartbeat();
    }

    private void sendCommand128() {
        Log.d(TAG, "用户点击命令128");
        appendLog("用户", "命令128", "文件上传");
        thorBluetooth.sendCommand128FileUpload();

        /*
        发送明文数据格式
          | 字节位置 | 值         | 业务含义             | 计算过程     |
          |------|-----------|------------------|----------|
          | 0    | 7         | 填充长度             | 自动计算     |
          | 1    | 0         | 分隔符              | 固定值      |
          | 2    | -128      | 命令ID=128         | 文件上传     |
          | 3-6  | [8,0,0,0] | fileID=8         | 小端序int   |
          | 7-8  | [0,-56]   | maxBlockSize=200 | 大端序short |
          | 9-15 | [-91×7]   | 填充数据             | 标准填充     |

        响应解密数据格式
          | 字节位置 | 原始值         | 十六进制                  | jadx解析方法              | 业务字段名        | 计算过程         | 解析结果       |
          |------|-------------|-----------------------|-----------------------|--------------|--------------|------------|
          | 0    | 7           | 0x07                  | -                     | 填充长度         | 直接读取         | 7字节填充      |
          | 1    | 0           | 0x00                  | -                     | 分隔符          | 直接读取         | 标准分隔符      |
          | 2    | -128        | 0x80                  | -                     | 命令ID         | 直接读取         | 128 (文件上传) |
          | 3-6  | [0,0,0,108] | [0x00,0x00,0x00,0x6C] | BleHelperKt.toInt()   | fileSize     | 大端序4字节转int   | 108字节      |
          | 7-8  | [0,-56]     | [0x00,0xC8]           | BleHelper.takeShort() | maxBlockSize | 大端序2字节转short | 200字节      |
          | 9-15 | [-91×7]     | [0xA5×7]              | -                     | 填充数据         | 跳过处理         | 标准填充       |
         */

    }

    private void sendCommand129() {

//      CryptoManager.coreAesEncrypt is called: data=9,0,-127,0,0,0,0,-91,-91,-91,-91,-91,-91,-91,-91,-91
        /*
        9 填充长度
        0 分隔符
        -127 读取上传快
        0 0 0 0 上传块偏移量
        -91... 真实占位符

//15,0,-127,0,108,68,65,84,50,12,0,1,0,2,6,36,2,8,2,-50,66,32,0,2,0,54,0,34,0,5,81,51,48,54,53,48,55,54,-95,-111,24,-54,88,64,4,-1,0,-1,0,-1,0,-1,0,8,0,3,0,-112,0,0,0,18,0,4,0,2,0,101,0,1,0,7,0,107,0,1,0,7,0,30,0,5,0,4,0,101,0,3,0,1,0,107,0,3,0,1,0,101,0,2,0,1,0,101,0,1,0,1,0,-1,-1,116,47,-91,-91,-91,-91,-91,-91,-91,-91,-91,-91,-91,-91,-91,-91,-91
        15 填充长度
        0 分割符
        -127 命令 读取上传块
//      0 ？
      108 = 0x6C = 'l'
      68  = 0x44 = 'D'
      65  = 0x41 = 'A'
      84  = 0x54 = 'T'

  分析jadx代码发现的文件处理流程：

  1. 文件上传协议：
    - 命令128（UPLOAD_START）：初始化文件上传，获取fileSize和maxBlockSize
    - 命令129（UPLOAD_READ_BLOCK）：按块读取文件数据，使用文件偏移量参数
  2. 文件偏移量参数 [0,0,0,0]：
  // UploadReadBlockRequest.java:44
  arrayList.addAll(ArraysKt.toList(BleHelper.convertIntToByteArray(Integer.valueOf(i4 * i2))));
    - 确实是4字节int类型表示文件读取偏移量
    - currentBlock * maxBlockSize 计算当前读取位置
  3. 完整文件读取后的处理：

  根据jadx DeviceParameters.java 分析，文件内容通常是设备参数配置文件：

  | 文件用途    | 处理方式                         |
  |---------|------------------------------|
  | CAN参数文件 | 解析为车辆通信配置，更新设备CAN协议参数        |
  | 音频包规则   | 解析声音样本和音频包配置                 |
  | 固件更新    | 验证固件完整性后刷写到设备                |
  | 设备参数    | 解析为DeviceParameters对象，更新设备状态 |

  具体处理步骤：
  1. 数据完整性校验：CRC验证所有块数据
  2. 文件类型识别：根据文件头或扩展名判断用途
  3. 业务逻辑处理：
    - 参数文件 → 解析并应用到设备
    - 固件文件 → 启动固件更新流程
    - 配置文件 → 更新设备运行参数




         * */
        thorBluetooth.sendCommand129StatusQuery();
    }

    private void sendCommand130() {

        thorBluetooth.sendCommand130();
    }

    private void sendCommand12() {
        thorBluetooth.sendCommand12Unlock();
    }

    private void sendCommand56_1() {

        thorBluetooth.sendCommand56((byte) 1);
    }

    private void sendCommand56Setting3() {

        thorBluetooth.sendCommand56((byte) 3);
    }

    private void sendCommand69() {

        thorBluetooth.sendCommand69();
    }

    private void sendCommand71() {
        thorBluetooth.sendCommand71();
    }

    private void sendCommand117() {
        thorBluetooth.sendCommand117();
    }

    private void clearLog() {
        logBuffer.setLength(0);
        runOnUiThread(() -> {
            logTextView.setText("日志已清空\n");
        });
        appendLog("系统", "日志已清空", "准备记录新的通信日志...");
    }

    // 更新状态显示
    private void updateStatus(String message) {
        runOnUiThread(() -> {
            statusText.setText(message);
            Log.d(TAG, "状态更新: " + message);
        });
    }

    // 添加日志条目
    public void appendLog(String category, String title, String content) {
        String timestamp = timeFormat.format(new Date());
        String logEntry = String.format("[%s] %s: %s\n%s\n\n",
                timestamp, category, title, content);

        logBuffer.append(logEntry);

        runOnUiThread(() -> {
            logTextView.setText(logBuffer.toString());
            // 滚动到底部
            logScrollView.post(() -> logScrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    // 添加日志条目
    public void printlog(String text) {

        logBuffer.append(text).append("\n");

        // 限制日志缓冲区为1024行
        String[] lines = logBuffer.toString().split("\n");
        if (lines.length > 1024) {
            logBuffer.setLength(0);
            for (int i = lines.length - 1024; i < lines.length; i++) {
                logBuffer.append(lines[i]).append("\n");
            }
        }

        runOnUiThread(() -> {
            logTextView.setText(logBuffer.toString());
            // 滚动到底部
            logScrollView.post(() -> logScrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    // ThorCallback 实现
    @Override
    public void onConnected() {
        runOnUiThread(() -> {
            updateStatus("已连接，握手中...");
            appendLog("系统", "设备连接成功", "开始执行握手流程...");
            Toast.makeText(this, "设备已连接", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDisconnected() {
        runOnUiThread(() -> {
            updateStatus("未连接");
            appendLog("系统", "设备断开连接", "请重新连接设备");

            Toast.makeText(this, "设备已断开", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onHandshakeComplete() {
        runOnUiThread(() -> {
            updateStatus("已连接");
            appendLog("系统", "握手完成", "设备已准备就绪，可以开始测试通信");

            Toast.makeText(this, "握手完成！可以开始测试", Toast.LENGTH_SHORT).show();

            connectButton.setEnabled(true);
            cmd58Button.setEnabled(true);
            cmd59Button.setEnabled(true);
            cmd128Button.setEnabled(true);
            cmd129Button.setEnabled(true);
            cmd130Button.setEnabled(true);
            cmd12Button.setEnabled(true);
            cmd56Button.setEnabled(true);
            cmd69Button.setEnabled(true);
            cmd71Button.setEnabled(true);
            cmd117Button.setEnabled(true);
//            cmd112Button.setEnabled(true);
//            cmd113Button.setEnabled(true);
            cmd114Button_sample.setEnabled(true);
            cmd114Button_smprl.setEnabled(true);
            cmd114Button_pkgrl.setEnabled(true);
            cmd114Button_all.setEnabled(true);
            cmd114Button_canbin.setEnabled(true);
            
            btn_cmd_74.setEnabled(true);
            clearLogButton.setEnabled(true);
            
            // 启用音效包音量控制按钮
            btnVolumeControl.setEnabled(true);
            btnReadVolumes.setEnabled(true);
            btnResetVolumes.setEnabled(true);

        });
    }

    @Override
    public void onMessageReceived(String message) {
        // 这个方法将被重构，现在先保持简单的日志记录
        appendLog("接收", "收到响应", message);
    }

    @Override
    public void onError(String error) {
        runOnUiThread(() -> {
            updateStatus("错误");
            appendLog("系统", "发生错误", error);
            connectButton.setEnabled(true);
            Toast.makeText(this, "错误: " + error, Toast.LENGTH_LONG).show();
        });
    }


    @Override
    public void onResponseReceived(int commandId, String commandName, String parsedData, byte[] rawData) {
        String rawDataHex = bytesToHex(rawData);
        String content = parsedData + "\n原始数据: " + rawDataHex;
        appendLog("接收", String.format("命令%d响应 - %s", commandId, commandName), content);

    }

    @Override
    public void onDeviceUnlocked() {
        runOnUiThread(() -> {
            updateStatus("已连接");
            appendLog("系统", "命令128已发送", "等待设备响应...");
        });
    }

    // 字节数组转十六进制字符串辅助方法
    private String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";

        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X ", b));
        }
        return result.toString().trim();
    }

    // 命令方法
    private void sendCommand58() {
        if (thorBluetooth != null) {
            thorBluetooth.sendCommand58ReadSettings();
        }
    }

    private void sendCommand59() {
        if (thorBluetooth != null) {
            thorBluetooth.sendCommand59WriteSettings();
        }
    }

    private void sendCommand56() {
        if (thorBluetooth != null) {
            thorBluetooth.sendCommand56((byte) 1);
        }
    }

    private void sendCommand114(String flag) {
        if (thorBluetooth != null) {

            thorBluetooth.sendCommand114(flag);
        }
    }

    private void sendCommand74() {
        if (thorBluetooth != null) {
            thorBluetooth.sendCommand74();
        }
    }

    // 音效包音量控制方法
    private void openVolumeControl() {
        Intent intent = new Intent(this, VolumeControlActivity.class);
        startActivity(intent);
    }

    private void readAllVolumes() {
        if (thorBluetooth != null && thorBluetooth.isConnected()) {
            appendLog("音效", "开始读取所有音效包音量设置", "正在读取...");
            
            SoundPackageManager manager = SoundPackageManager.getInstance(this);
            manager.getInstalledSoundPackages().forEach(pkg -> {
                try {
                    thorBluetooth.readVolumeSettings(pkg.getPackageId());
                } catch (Exception e) {
                    appendLog("音效", "读取音量失败", "包ID: " + pkg.getPackageId() + ", 错误: " + e.getMessage());
                }
            });
            
            Toast.makeText(this, "正在读取所有音效包音量设置", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "设备未连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAllVolumes() {
        if (thorBluetooth != null && thorBluetooth.isConnected()) {
            appendLog("音效", "开始重置所有音效包音量", "重置为默认值...");
            
            SoundPackageManager manager = SoundPackageManager.getInstance(this);
            manager.getInstalledSoundPackages().forEach(pkg -> {
                manager.resetPackageVolumes(pkg.getPackageId());
                
                // 发送默认音量到设备
                try {
                    thorBluetooth.sendVolumeCommand(pkg.getPackageId(), 0, 80);  // 主音量
                    thorBluetooth.sendVolumeCommand(pkg.getPackageId(), 24, 50); // 空闲音
                    thorBluetooth.sendVolumeCommand(pkg.getPackageId(), 25, 60); // 旋转工作音
                    thorBluetooth.sendVolumeCommand(pkg.getPackageId(), 48, 40); // 引擎音效
                } catch (Exception e) {
                    appendLog("音效", "重置音量失败", "包ID: " + pkg.getPackageId() + ", 错误: " + e.getMessage());
                }
            });
            
            Toast.makeText(this, "所有音效包音量已重置为默认值", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "设备未连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (thorBluetooth != null) {
            thorBluetooth.disconnect();
        }
        super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    // Sign-out successful, navigate back to login
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
    }
}