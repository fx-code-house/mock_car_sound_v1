package com.thor.businessmodule.bluetooth.model.other;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;

/* compiled from: ReloadUploadingFirmwareStatus.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u001dB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010\u000e\u001a\u0004\u0018\u00010\u0018J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001e"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingFirmwareStatus;", "", "deviceTypeId", "", "hardwareVersion", "firmwareVersion", NotificationCompat.CATEGORY_STATUS, "blockNumber", "(SSSSS)V", "getBlockNumber", "()S", "getDeviceTypeId", "getFirmwareVersion", "getHardwareVersion", "getStatus", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingFirmwareStatus$Status;", "hashCode", "", "toString", "", "Status", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ReloadUploadingFirmwareStatus {
    private final short blockNumber;
    private final short deviceTypeId;
    private final short firmwareVersion;
    private final short hardwareVersion;
    private final short status;

    public static /* synthetic */ ReloadUploadingFirmwareStatus copy$default(ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus, short s, short s2, short s3, short s4, short s5, int i, Object obj) {
        if ((i & 1) != 0) {
            s = reloadUploadingFirmwareStatus.deviceTypeId;
        }
        if ((i & 2) != 0) {
            s2 = reloadUploadingFirmwareStatus.hardwareVersion;
        }
        short s6 = s2;
        if ((i & 4) != 0) {
            s3 = reloadUploadingFirmwareStatus.firmwareVersion;
        }
        short s7 = s3;
        if ((i & 8) != 0) {
            s4 = reloadUploadingFirmwareStatus.status;
        }
        short s8 = s4;
        if ((i & 16) != 0) {
            s5 = reloadUploadingFirmwareStatus.blockNumber;
        }
        return reloadUploadingFirmwareStatus.copy(s, s6, s7, s8, s5);
    }

    /* renamed from: component1, reason: from getter */
    public final short getDeviceTypeId() {
        return this.deviceTypeId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getHardwareVersion() {
        return this.hardwareVersion;
    }

    /* renamed from: component3, reason: from getter */
    public final short getFirmwareVersion() {
        return this.firmwareVersion;
    }

    /* renamed from: component4, reason: from getter */
    public final short getStatus() {
        return this.status;
    }

    /* renamed from: component5, reason: from getter */
    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final ReloadUploadingFirmwareStatus copy(short deviceTypeId, short hardwareVersion, short firmwareVersion, short status, short blockNumber) {
        return new ReloadUploadingFirmwareStatus(deviceTypeId, hardwareVersion, firmwareVersion, status, blockNumber);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReloadUploadingFirmwareStatus)) {
            return false;
        }
        ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus = (ReloadUploadingFirmwareStatus) other;
        return this.deviceTypeId == reloadUploadingFirmwareStatus.deviceTypeId && this.hardwareVersion == reloadUploadingFirmwareStatus.hardwareVersion && this.firmwareVersion == reloadUploadingFirmwareStatus.firmwareVersion && this.status == reloadUploadingFirmwareStatus.status && this.blockNumber == reloadUploadingFirmwareStatus.blockNumber;
    }

    public int hashCode() {
        return (((((((Short.hashCode(this.deviceTypeId) * 31) + Short.hashCode(this.hardwareVersion)) * 31) + Short.hashCode(this.firmwareVersion)) * 31) + Short.hashCode(this.status)) * 31) + Short.hashCode(this.blockNumber);
    }

    public String toString() {
        return "ReloadUploadingFirmwareStatus(deviceTypeId=" + ((int) this.deviceTypeId) + ", hardwareVersion=" + ((int) this.hardwareVersion) + ", firmwareVersion=" + ((int) this.firmwareVersion) + ", status=" + ((int) this.status) + ", blockNumber=" + ((int) this.blockNumber) + ")";
    }

    public ReloadUploadingFirmwareStatus(short s, short s2, short s3, short s4, short s5) {
        this.deviceTypeId = s;
        this.hardwareVersion = s2;
        this.firmwareVersion = s3;
        this.status = s4;
        this.blockNumber = s5;
    }

    public final short getDeviceTypeId() {
        return this.deviceTypeId;
    }

    public final short getHardwareVersion() {
        return this.hardwareVersion;
    }

    public final short getFirmwareVersion() {
        return this.firmwareVersion;
    }

    /* renamed from: getStatus, reason: collision with other method in class */
    public final short m598getStatus() {
        return this.status;
    }

    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final Status getStatus() {
        short s = this.status;
        if (s == Status.NEED_FULL_RELOAD.getValue()) {
            return Status.NEED_FULL_RELOAD;
        }
        if (s == Status.NEED_RELOAD_UPLOADING_FIRMWARE.getValue()) {
            return Status.NEED_RELOAD_UPLOADING_FIRMWARE;
        }
        if (s == Status.NEED_ACCEPT_UPLOADING.getValue()) {
            return Status.NEED_ACCEPT_UPLOADING;
        }
        return null;
    }

    /* compiled from: ReloadUploadingFirmwareStatus.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingFirmwareStatus$Status;", "", "value", "", "(Ljava/lang/String;IS)V", "getValue", "()S", "NEED_FULL_RELOAD", "NEED_RELOAD_UPLOADING_FIRMWARE", "NEED_ACCEPT_UPLOADING", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum Status {
        NEED_FULL_RELOAD(0),
        NEED_RELOAD_UPLOADING_FIRMWARE(1),
        NEED_ACCEPT_UPLOADING(4);

        private final short value;

        Status(short s) {
            this.value = s;
        }

        public final short getValue() {
            return this.value;
        }
    }
}
