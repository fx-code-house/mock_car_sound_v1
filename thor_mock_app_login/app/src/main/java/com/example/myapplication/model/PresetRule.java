package com.example.myapplication.model;

/**
 * 预设规则模型
 * 对应Thor BLE协议中的PresetRule结构
 * 基于Frida抓包分析: PresetRule(id=XX, value=YY)
 */
public class PresetRule {
    private int id;        // 规则ID，对应不同的音效参数
    private int value;     // 参数值，通常是音量值 (0-100)
    private String name;   // 参数名称，用于显示

    public PresetRule(int id, int value) {
        this.id = id;
        this.value = value;
        this.name = getParameterName(id);
    }

    public PresetRule(int id, int value, String name) {
        this.id = id;
        this.value = value;
        this.name = name;
    }

    /**
     * 根据ID获取参数名称
     * 基于Frida抓包分析的ID映射关系
     */
    private String getParameterName(int id) {
        switch (id) {
            case 0: return "主音量";
            case 1: return "空载量";
            case 24: return "空闲音";
            case 25: return "旋转工作音";
            case 33: return "其他控制";
            default: return "参数" + id;
        }
    }

    /**
     * 检查是否是音量相关参数
     */
    public boolean isVolumeParameter() {
        return id == 0 || id == 1 || id == 24 || id == 25;
    }

    /**
     * 获取音量百分比显示
     */
    public String getVolumePercentage() {
        if (isVolumeParameter()) {
            return value + "%";
        }
        return String.valueOf(value);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { 
        this.id = id; 
        this.name = getParameterName(id);
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = Math.max(0, Math.min(100, value)); } // 限制0-100

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return String.format("PresetRule{id=%d, name='%s', value=%d}", id, name, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PresetRule that = (PresetRule) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}