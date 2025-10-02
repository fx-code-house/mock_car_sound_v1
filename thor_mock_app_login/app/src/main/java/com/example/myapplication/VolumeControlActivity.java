package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.manager.SoundPackageManager;
import com.example.myapplication.model.PresetRule;
import com.example.myapplication.model.SoundPackage;

import java.util.ArrayList;
import java.util.List;


/**
 * 音效包音量控制界面
 * 基于Frida抓包分析结果实现的音量调整界面
 */
public class VolumeControlActivity extends AppCompatActivity
    implements SoundPackageManager.SoundPackageListener {

    private SoundPackageManager soundPackageManager;
    private ThorBluetooth thorBluetooth;
    
    // UI控件
    private Spinner spinnerSoundPackages;
    private TextView tvPackageInfo;
    private SeekBar seekbarMainVolume, seekbarNoloadVolume, seekbarIdleVolume, seekbarWorkingVolume;
    private TextView tvMainVolumeValue, tvNoloadVolumeValue, tvIdleVolumeValue, tvWorkingVolumeValue;
    
    // 当前选中的音效包
    private SoundPackage currentSoundPackage;
    private List<SoundPackage> soundPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_control);
        
        initializeComponents();
        setupUI();
        loadSoundPackages();
    }

    private void initializeComponents() {
        soundPackageManager = SoundPackageManager.getInstance(this);
        thorBluetooth = ThorBluetoothHolder.getInstance();
        
        // 添加监听器
        soundPackageManager.addListener(this);
    }

    private void setupUI() {
        // 获取UI控件
        Button btnBack = findViewById(R.id.btn_back);
        Button btnApplyAll = findViewById(R.id.btn_apply_all);
        Button btnResetDefault = findViewById(R.id.btn_reset_default);
        Button btnReadCurrent = findViewById(R.id.btn_read_current);
        
        // 独立保存按钮
        Button btnSaveMainVolume = findViewById(R.id.btn_save_main_volume);
        Button btnSaveNoloadVolume = findViewById(R.id.btn_save_noload_volume);
        Button btnSaveIdleVolume = findViewById(R.id.btn_save_idle_volume);
        Button btnSaveWorkingVolume = findViewById(R.id.btn_save_working_volume);
        
        spinnerSoundPackages = findViewById(R.id.spinner_sound_packages);
        tvPackageInfo = findViewById(R.id.tv_package_info);
        
        // 音量控制控件
        seekbarMainVolume = findViewById(R.id.seekbar_main_volume);
        seekbarNoloadVolume = findViewById(R.id.seekbar_noload_volume);
        seekbarIdleVolume = findViewById(R.id.seekbar_idle_volume);
        seekbarWorkingVolume = findViewById(R.id.seekbar_working_volume);
        
        tvMainVolumeValue = findViewById(R.id.tv_main_volume_value);
        tvNoloadVolumeValue = findViewById(R.id.tv_noload_volume_value);
        tvIdleVolumeValue = findViewById(R.id.tv_idle_volume_value);
        tvWorkingVolumeValue = findViewById(R.id.tv_working_volume_value);
        
        // 设置按钮点击事件
        btnBack.setOnClickListener(v -> finish());
        btnApplyAll.setOnClickListener(v -> applyAllVolumes());
        btnResetDefault.setOnClickListener(v -> resetToDefaults());
        btnReadCurrent.setOnClickListener(v -> readCurrentVolumes());
        
        // 独立保存按钮事件
        btnSaveMainVolume.setOnClickListener(v -> saveIndividualVolume(0, seekbarMainVolume.getProgress(), "主音量"));
        btnSaveNoloadVolume.setOnClickListener(v -> saveIndividualVolume(48, seekbarNoloadVolume.getProgress(), "空载量"));
        btnSaveIdleVolume.setOnClickListener(v -> saveIndividualVolume(24, seekbarIdleVolume.getProgress(), "空闲音"));
        btnSaveWorkingVolume.setOnClickListener(v -> saveIndividualVolume(25, seekbarWorkingVolume.getProgress(), "旋转工作音"));
        
        // 设置SeekBar监听
        setupSeekBarListeners();
        
        // 设置音效包选择监听
        spinnerSoundPackages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < soundPackages.size()) {
                    selectSoundPackage(soundPackages.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSeekBarListeners() {
        // 主音量
        seekbarMainVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    tvMainVolumeValue.setText(progress + "%");
                    updateVolumeRule(0, progress);
                }
            }
            
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 空载量
        seekbarNoloadVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    tvNoloadVolumeValue.setText(progress + "%");
                    updateVolumeRule(48, progress);
                }
            }
            
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 空闲音
        seekbarIdleVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    tvIdleVolumeValue.setText(progress + "%");
                    updateVolumeRule(24, progress);
                }
            }
            
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 旋转工作音
        seekbarWorkingVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    tvWorkingVolumeValue.setText(progress + "%");
                    updateVolumeRule(25, progress);
                }
            }
            
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void loadSoundPackages() {
        soundPackages = soundPackageManager.getInstalledSoundPackages();
        
        List<String> packageNames = new ArrayList<>();
        for (SoundPackage pkg : soundPackages) {
            packageNames.add(pkg.getName() + " (ID:" + pkg.getPackageId() + ")");
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            packageNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSoundPackages.setAdapter(adapter);
        
        // 默认选择第一个
        if (!soundPackages.isEmpty()) {
            selectSoundPackage(soundPackages.get(0));
        }
    }

    private void selectSoundPackage(SoundPackage soundPackage) {
        currentSoundPackage = soundPackage;
        
        // 更新包信息显示
        String info = String.format("音效包: %s\n描述: %s\n文件大小: %d KB",
            soundPackage.getName(),
            soundPackage.getDescription() != null ? soundPackage.getDescription() : "无描述",
            soundPackage.getFileSize());
        tvPackageInfo.setText(info);
        
        // 加载音量设置
        loadVolumeSettings(soundPackage.getPackageId());
    }

    private void loadVolumeSettings(int packageId) {
        List<PresetRule> rules = soundPackageManager.getPackageVolumeRules(packageId);
        
        // 重置为默认值
        seekbarMainVolume.setProgress(80);
        seekbarNoloadVolume.setProgress(2);
        seekbarIdleVolume.setProgress(50);
        seekbarWorkingVolume.setProgress(60);
        
        // 应用实际的规则值
        for (PresetRule rule : rules) {
            switch (rule.getId()) {
                case 0: // 主音量
                    seekbarMainVolume.setProgress(rule.getValue());
                    tvMainVolumeValue.setText(rule.getValue() + "%");
                    break;
                case 48: // 空载量
                    seekbarNoloadVolume.setProgress(rule.getValue());
                    tvNoloadVolumeValue.setText(rule.getValue() + "%");
                    break;
                case 24: // 空闲音
                    seekbarIdleVolume.setProgress(rule.getValue());
                    tvIdleVolumeValue.setText(rule.getValue() + "%");
                    break;
                case 25: // 旋转工作音
                    seekbarWorkingVolume.setProgress(rule.getValue());
                    tvWorkingVolumeValue.setText(rule.getValue() + "%");
                    break;
            }
        }
    }

    /**
     * 更新音量规则（本地）
     */
    private void updateVolumeRule(int ruleId, int value) {
        if (currentSoundPackage != null) {
            soundPackageManager.updatePackageVolume(
                currentSoundPackage.getPackageId(), 
                ruleId, 
                value
            );
        }
    }

    /**
     * 应用所有音量设置到设备
     */
    private void applyAllVolumes() {
        if (currentSoundPackage == null) {
            Toast.makeText(this, "请先选择音效包", Toast.LENGTH_SHORT).show();
            return;
        }

        if (thorBluetooth == null) {
            Toast.makeText(this, "蓝牙服务未初始化", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!thorBluetooth.isConnected()) {
            Toast.makeText(this, "设备未连接", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int packageId = currentSoundPackage.getPackageId();
            
            // 发送音量命令到设备
            thorBluetooth.sendVolumeCommand(packageId, 0, seekbarMainVolume.getProgress());
            thorBluetooth.sendVolumeCommand(packageId, 48, seekbarNoloadVolume.getProgress());
            thorBluetooth.sendVolumeCommand(packageId, 24, seekbarIdleVolume.getProgress());
            thorBluetooth.sendVolumeCommand(packageId, 25, seekbarWorkingVolume.getProgress());
            
            Toast.makeText(this, "音量设置已应用到设备", Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            Toast.makeText(this, "应用音量设置失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 重置为默认值
     */
    private void resetToDefaults() {
        if (currentSoundPackage == null) {
            Toast.makeText(this, "请先选择音效包", Toast.LENGTH_SHORT).show();
            return;
        }

        // 重置UI控件
        seekbarMainVolume.setProgress(80);
        seekbarNoloadVolume.setProgress(2);
        seekbarIdleVolume.setProgress(50);
        seekbarWorkingVolume.setProgress(60);
        
        tvMainVolumeValue.setText("80%");
        tvNoloadVolumeValue.setText("2%");
        tvIdleVolumeValue.setText("50%");
        tvWorkingVolumeValue.setText("60%");
        
        // 重置管理器中的数据
        soundPackageManager.resetPackageVolumes(currentSoundPackage.getPackageId());
        
        Toast.makeText(this, "已重置为默认音量", Toast.LENGTH_SHORT).show();
    }

    /**
     * 从设备读取当前音量
     */
    private void readCurrentVolumes() {
        if (currentSoundPackage == null) {
            Toast.makeText(this, "请先选择音效包", Toast.LENGTH_SHORT).show();
            return;
        }

        if (thorBluetooth == null) {
            Toast.makeText(this, "蓝牙服务未初始化", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!thorBluetooth.isConnected()) {
            Toast.makeText(this, "设备未连接", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // 从设备读取音量设置
            thorBluetooth.readVolumeSettings(currentSoundPackage.getPackageId());
            Toast.makeText(this, "正在读取设备音量设置...", Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            Toast.makeText(this, "读取音量设置失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // SoundPackageListener 接口实现
    @Override
    public void onPackageVolumeChanged(int packageId, int ruleId, int newValue) {
        runOnUiThread(() -> {
            if (currentSoundPackage != null && currentSoundPackage.getPackageId() == packageId) {
                // 更新UI显示
                switch (ruleId) {
                    case 0: // 主音量
                        seekbarMainVolume.setProgress(newValue);
                        tvMainVolumeValue.setText(newValue + "%");
                        break;
                    case 48: // 空载量
                        seekbarNoloadVolume.setProgress(newValue);
                        tvNoloadVolumeValue.setText(newValue + "%");
                        break;
                    case 24: // 空闲音
                        seekbarIdleVolume.setProgress(newValue);
                        tvIdleVolumeValue.setText(newValue + "%");
                        break;
                    case 25: // 旋转工作音
                        seekbarWorkingVolume.setProgress(newValue);
                        tvWorkingVolumeValue.setText(newValue + "%");
                        break;
                }
            }
        });
    }

    @Override
    public void onPackageInstalled(SoundPackage soundPackage) {
        runOnUiThread(() -> {
            loadSoundPackages(); // 重新加载音效包列表
        });
    }

    @Override
    public void onPackageUninstalled(int packageId) {
        runOnUiThread(() -> {
            loadSoundPackages(); // 重新加载音效包列表
        });
    }

    /**
     * 独立保存单个音效类型的音量
     * @param ruleId 规则ID (0=主音量, 48=空载量, 24=空闲音, 25=旋转工作音)
     * @param volume 音量值 (0-100)
     * @param typeName 音效类型名称
     */
    private void saveIndividualVolume(int ruleId, int volume, String typeName) {
        if (currentSoundPackage == null) {
            Toast.makeText(this, "请先选择音效包", Toast.LENGTH_SHORT).show();
            return;
        }

        if (thorBluetooth == null) {
            Toast.makeText(this, "蓝牙服务未初始化", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!thorBluetooth.isConnected()) {
            Toast.makeText(this, "设备未连接", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int packageId = currentSoundPackage.getPackageId();
            
            // 更新本地数据
            updateVolumeRule(ruleId, volume);
            
            // 发送单个音量命令到设备
            thorBluetooth.sendVolumeCommand(packageId, ruleId, volume);
            
            Toast.makeText(this, String.format("%s已保存并同步到设备 (音量: %d%%)", typeName, volume), Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            Toast.makeText(this, String.format("保存%s失败: %s", typeName, e.getMessage()), Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPackageManager != null) {
            soundPackageManager.removeListener(this);
        }
    }
}