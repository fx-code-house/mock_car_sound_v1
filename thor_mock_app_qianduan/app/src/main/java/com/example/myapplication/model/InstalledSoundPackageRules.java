package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 已安装音效包规则模型
 * 对应Thor BLE协议中的InstalledPresetRules结构
 */
public class InstalledSoundPackageRules {
    private SoundPackage soundPackage;      // 关联的音效包
    private List<PresetRule> rules;         // 预设规则列表

    public InstalledSoundPackageRules(SoundPackage soundPackage) {
        this.soundPackage = soundPackage;
        this.rules = new ArrayList<>();
        initializeDefaultRules();
    }

    /**
     * 初始化默认规则
     * 基于Frida抓包分析的常见参数
     */
    private void initializeDefaultRules() {
        // 主音量参数 (id=0)
        rules.add(new PresetRule(0, 80, "主音量"));
        
        // 空闲音参数 (id=24)
        rules.add(new PresetRule(24, 50, "空闲音"));
        
        // 旋转工作音参数 (id=25)
        rules.add(new PresetRule(25, 60, "旋转工作音"));
        
        // 空载量参数 (id=48) - 基于Thor官方应用Frida抓包分析
        rules.add(new PresetRule(48, 2, "空载量"));
    }

    /**
     * 根据ID获取规则
     */
    public PresetRule getRuleById(int id) {
        for (PresetRule rule : rules) {
            if (rule.getId() == id) {
                return rule;
            }
        }
        return null;
    }

    /**
     * 更新规则值
     */
    public void updateRuleValue(int id, int value) {
        PresetRule rule = getRuleById(id);
        if (rule != null) {
            rule.setValue(value);
        } else {
            // 如果规则不存在，创建新规则
            rules.add(new PresetRule(id, value));
        }
    }

    /**
     * 获取所有音量相关规则
     */
    public List<PresetRule> getVolumeRules() {
        List<PresetRule> volumeRules = new ArrayList<>();
        for (PresetRule rule : rules) {
            if (rule.isVolumeParameter()) {
                volumeRules.add(rule);
            }
        }
        return volumeRules;
    }

    /**
     * 重置所有音量为默认值
     */
    public void resetToDefaults() {
        for (PresetRule rule : rules) {
            switch (rule.getId()) {
                case 0: rule.setValue(80); break;   // 主音量
                case 24: rule.setValue(50); break;  // 空闲音
                case 25: rule.setValue(60); break;  // 旋转工作音
                case 48: rule.setValue(2); break;   // 空载量
                default: rule.setValue(50); break;
            }
        }
    }

    // Getters and Setters
    public SoundPackage getSoundPackage() { return soundPackage; }
    public void setSoundPackage(SoundPackage soundPackage) { this.soundPackage = soundPackage; }

    public List<PresetRule> getRules() { return rules; }
    public void setRules(List<PresetRule> rules) { this.rules = rules; }

    @Override
    public String toString() {
        return String.format("InstalledSoundPackageRules{package=%s, rules=%d}", 
                           soundPackage != null ? soundPackage.getName() : "null", rules.size());
    }
}