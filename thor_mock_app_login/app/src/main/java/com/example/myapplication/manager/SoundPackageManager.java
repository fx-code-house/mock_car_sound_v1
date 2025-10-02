package com.example.myapplication.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.model.SoundPackage;
import com.example.myapplication.model.PresetRule;
import com.example.myapplication.model.InstalledSoundPackageRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 音效包管理器
 * 负责管理已安装的音效包和其音量设置
 */
public class SoundPackageManager {
    private static final String TAG = "SoundPackageManager";
    private static final String PREFS_NAME = "thor_volume_settings";
    
    private static SoundPackageManager instance;
    private Map<Integer, InstalledSoundPackageRules> installedPackages;
    private List<SoundPackageListener> listeners;
    private Context context;

    /**
     * 音效包状态监听接口
     */
    public interface SoundPackageListener {
        void onPackageVolumeChanged(int packageId, int ruleId, int newValue);
        void onPackageInstalled(SoundPackage soundPackage);
        void onPackageUninstalled(int packageId);
    }

    private SoundPackageManager(Context context) {
        this.context = context.getApplicationContext();
        installedPackages = new HashMap<>();
        listeners = new ArrayList<>();
        initializeMockData();
    }

    public static synchronized SoundPackageManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundPackageManager(context);
        }
        return instance;
    }
    
    public static synchronized SoundPackageManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SoundPackageManager not initialized. Call getInstance(Context) first.");
        }
        return instance;
    }

    /**
     * 初始化模拟数据
     * 基于Frida抓包分析中发现的音效包ID
     * 更新为使用Thor官方应用的PackageId=27
     */
    private void initializeMockData() {
        // 创建示例音效包 (基于Thor官方应用的packageId=27)
        SoundPackage package1 = new SoundPackage(27, 0, "ICE CREAM VAN");
        package1.setDescription("城市版 | 动力版 | 自我");
        package1.setInstalled(true);
        package1.setFileSize(2048);

        // 创建其他示例音效包
        SoundPackage package2 = new SoundPackage(28, 0, "SPORTS CAR");
        package2.setDescription("运动版音效包");
        package2.setInstalled(true);
        package2.setFileSize(1536);

        SoundPackage package3 = new SoundPackage(29, 0, "CLASSIC ENGINE");
        package3.setDescription("经典引擎音效");
        package3.setInstalled(false);
        package3.setFileSize(1024);

        // 添加到已安装列表
        installedPackages.put(package1.getPackageId(), new InstalledSoundPackageRules(package1));
        installedPackages.put(package2.getPackageId(), new InstalledSoundPackageRules(package2));

        // 迁移旧的音量设置数据（ruleId从1改为48）
        migrateOldVolumeSettings();
        
        // 加载保存的音量设置
        loadVolumeSettings();

        Log.d(TAG, "初始化完成，已安装音效包数量: " + installedPackages.size());
    }

    /**
     * 获取所有已安装的音效包
     */
    public List<SoundPackage> getInstalledSoundPackages() {
        List<SoundPackage> packages = new ArrayList<>();
        for (InstalledSoundPackageRules rules : installedPackages.values()) {
            packages.add(rules.getSoundPackage());
        }
        return packages;
    }

    /**
     * 根据ID获取音效包规则
     */
    public InstalledSoundPackageRules getPackageRules(int packageId) {
        return installedPackages.get(packageId);
    }

    /**
     * 更新音效包的音量设置
     * @param packageId 音效包ID
     * @param ruleId 规则ID
     * @param value 新的音量值 (0-100)
     */
    public void updatePackageVolume(int packageId, int ruleId, int value) {
        InstalledSoundPackageRules rules = installedPackages.get(packageId);
        if (rules != null) {
            int oldValue = rules.getRuleById(ruleId) != null ? rules.getRuleById(ruleId).getValue() : 0;
            rules.updateRuleValue(ruleId, value);
            
            // 保存音量设置到SharedPreferences
            saveVolumeSettings(packageId, ruleId, value);
            
            Log.d(TAG, String.format("更新音效包%d规则%d: %d -> %d", packageId, ruleId, oldValue, value));
            
            // 通知监听器
            for (SoundPackageListener listener : listeners) {
                listener.onPackageVolumeChanged(packageId, ruleId, value);
            }
        }
    }

    /**
     * 获取特定音效包的音量设置
     */
    public List<PresetRule> getPackageVolumeRules(int packageId) {
        InstalledSoundPackageRules rules = installedPackages.get(packageId);
        if (rules != null) {
            return rules.getVolumeRules();
        }
        return new ArrayList<>();
    }

    /**
     * 重置音效包音量为默认值
     */
    public void resetPackageVolumes(int packageId) {
        InstalledSoundPackageRules rules = installedPackages.get(packageId);
        if (rules != null) {
            rules.resetToDefaults();
            Log.d(TAG, "重置音效包" + packageId + "音量为默认值");
            
            // 通知每个规则的变化
            for (PresetRule rule : rules.getVolumeRules()) {
                for (SoundPackageListener listener : listeners) {
                    listener.onPackageVolumeChanged(packageId, rule.getId(), rule.getValue());
                }
            }
        }
    }

    /**
     * 安装新音效包
     */
    public void installSoundPackage(SoundPackage soundPackage) {
        soundPackage.setInstalled(true);
        InstalledSoundPackageRules rules = new InstalledSoundPackageRules(soundPackage);
        installedPackages.put(soundPackage.getPackageId(), rules);
        
        Log.d(TAG, "安装音效包: " + soundPackage);
        
        // 通知监听器
        for (SoundPackageListener listener : listeners) {
            listener.onPackageInstalled(soundPackage);
        }
    }

    /**
     * 卸载音效包
     */
    public void uninstallSoundPackage(int packageId) {
        InstalledSoundPackageRules removed = installedPackages.remove(packageId);
        if (removed != null) {
            Log.d(TAG, "卸载音效包: " + packageId);
            
            // 通知监听器
            for (SoundPackageListener listener : listeners) {
                listener.onPackageUninstalled(packageId);
            }
        }
    }

    /**
     * 添加监听器
     */
    public void addListener(SoundPackageListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * 移除监听器
     */
    public void removeListener(SoundPackageListener listener) {
        listeners.remove(listener);
    }

    /**
     * 获取音效包总数统计
     */
    public String getPackageStats() {
        int totalInstalled = installedPackages.size();
        int totalRules = 0;
        for (InstalledSoundPackageRules rules : installedPackages.values()) {
            totalRules += rules.getRules().size();
        }
        
        return String.format("已安装音效包: %d个, 总规则数: %d个", totalInstalled, totalRules);
    }

    /**
     * 保存音量设置到SharedPreferences
     */
    private void saveVolumeSettings(int packageId, int ruleId, int value) {
        if (context == null) return;
        
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String key = "volume_" + packageId + "_" + ruleId;
        prefs.edit().putInt(key, value).apply();
        
        Log.d(TAG, String.format("保存音量设置: %s = %d", key, value));
    }

    /**
     * 从SharedPreferences加载音量设置
     */
    private void loadVolumeSettings() {
        if (context == null) return;
        
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        
        for (InstalledSoundPackageRules rules : installedPackages.values()) {
            int packageId = rules.getSoundPackage().getPackageId();
            
            // 加载各种音量规则
            int[] ruleIds = {0, 48, 24, 25}; // 主音量, 空载量, 空闲音, 旋转工作音
            
            for (int ruleId : ruleIds) {
                String key = "volume_" + packageId + "_" + ruleId;
                if (prefs.contains(key)) {
                    int savedValue = prefs.getInt(key, -1);
                    if (savedValue >= 0) {
                        rules.updateRuleValue(ruleId, savedValue);
                        Log.d(TAG, String.format("加载音量设置: %s = %d", key, savedValue));
                    }
                }
            }
        }
    }

    /**
     * 清除所有保存的音量设置
     */
    public void clearVolumeSettings() {
        if (context == null) return;
        
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
        Log.d(TAG, "清除所有音量设置");
    }
    
    /**
     * 迁移旧的音量设置数据
     * 将ruleId=1的空载量数据迁移到ruleId=48
     */
    private void migrateOldVolumeSettings() {
        if (context == null) return;
        
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        // 检查是否已经迁移过
        if (prefs.getBoolean("volume_migrated_v2", false)) {
            return;
        }
        
        Log.d(TAG, "开始迁移音量设置数据：ruleId 1 -> 48");
        
        for (InstalledSoundPackageRules rules : installedPackages.values()) {
            int packageId = rules.getSoundPackage().getPackageId();
            
            // 检查是否存在旧的ruleId=1数据
            String oldKey = "volume_" + packageId + "_1";
            String newKey = "volume_" + packageId + "_48";
            
            if (prefs.contains(oldKey) && !prefs.contains(newKey)) {
                int oldValue = prefs.getInt(oldKey, 2);
                editor.putInt(newKey, oldValue);
                editor.remove(oldKey);  // 删除旧数据
                Log.d(TAG, String.format("迁移音量数据: %s (%d) -> %s", oldKey, oldValue, newKey));
            }
        }
        
        // 标记已迁移
        editor.putBoolean("volume_migrated_v2", true);
        editor.apply();
        
        Log.d(TAG, "音量设置数据迁移完成");
    }
}