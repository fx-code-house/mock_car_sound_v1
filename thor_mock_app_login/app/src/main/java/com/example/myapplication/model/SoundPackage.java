package com.example.myapplication.model;

/**
 * 音效包数据模型
 * 基于Frida抓包分析结果创建
 */
public class SoundPackage {
    private int packageId;          // 音效包ID
    private int versionId;          // 版本ID
    private String name;            // 音效包名称
    private String description;     // 描述
    private boolean isInstalled;    // 是否已安装
    private int fileSize;           // 文件大小

    public SoundPackage(int packageId, int versionId, String name) {
        this.packageId = packageId;
        this.versionId = versionId;
        this.name = name;
        this.isInstalled = false;
    }

    // Getters and Setters
    public int getPackageId() { return packageId; }
    public void setPackageId(int packageId) { this.packageId = packageId; }

    public int getVersionId() { return versionId; }
    public void setVersionId(int versionId) { this.versionId = versionId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isInstalled() { return isInstalled; }
    public void setInstalled(boolean installed) { isInstalled = installed; }

    public int getFileSize() { return fileSize; }
    public void setFileSize(int fileSize) { this.fileSize = fileSize; }

    @Override
    public String toString() {
        return String.format("SoundPackage{id=%d, version=%d, name='%s', installed=%s}", 
                            packageId, versionId, name, isInstalled);
    }
}