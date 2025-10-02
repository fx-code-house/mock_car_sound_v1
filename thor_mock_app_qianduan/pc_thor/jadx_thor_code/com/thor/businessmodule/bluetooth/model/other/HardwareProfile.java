package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HardwareProfile.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\u0006\u0010\u001f\u001a\u00020\u001bJ\t\u0010 \u001a\u00020\u0007HÖ\u0001R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010¨\u0006!"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "", "serialNumber", "", "versionFirmware", "versionHardware", "deviceId", "", "(SSSLjava/lang/String;)V", "getDeviceId", "()Ljava/lang/String;", "setDeviceId", "(Ljava/lang/String;)V", "getSerialNumber", "()S", "setSerialNumber", "(S)V", "getVersionFirmware", "setVersionFirmware", "getVersionHardware", "setVersionHardware", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "isDefaultValuesSet", "toString", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class HardwareProfile {

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("serial_number")
    private short serialNumber;

    @SerializedName("version_firmware")
    private short versionFirmware;

    @SerializedName("version_hardware")
    private short versionHardware;

    public HardwareProfile() {
        this((short) 0, (short) 0, (short) 0, null, 15, null);
    }

    public static /* synthetic */ HardwareProfile copy$default(HardwareProfile hardwareProfile, short s, short s2, short s3, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            s = hardwareProfile.serialNumber;
        }
        if ((i & 2) != 0) {
            s2 = hardwareProfile.versionFirmware;
        }
        if ((i & 4) != 0) {
            s3 = hardwareProfile.versionHardware;
        }
        if ((i & 8) != 0) {
            str = hardwareProfile.deviceId;
        }
        return hardwareProfile.copy(s, s2, s3, str);
    }

    /* renamed from: component1, reason: from getter */
    public final short getSerialNumber() {
        return this.serialNumber;
    }

    /* renamed from: component2, reason: from getter */
    public final short getVersionFirmware() {
        return this.versionFirmware;
    }

    /* renamed from: component3, reason: from getter */
    public final short getVersionHardware() {
        return this.versionHardware;
    }

    /* renamed from: component4, reason: from getter */
    public final String getDeviceId() {
        return this.deviceId;
    }

    public final HardwareProfile copy(short serialNumber, short versionFirmware, short versionHardware, String deviceId) {
        Intrinsics.checkNotNullParameter(deviceId, "deviceId");
        return new HardwareProfile(serialNumber, versionFirmware, versionHardware, deviceId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HardwareProfile)) {
            return false;
        }
        HardwareProfile hardwareProfile = (HardwareProfile) other;
        return this.serialNumber == hardwareProfile.serialNumber && this.versionFirmware == hardwareProfile.versionFirmware && this.versionHardware == hardwareProfile.versionHardware && Intrinsics.areEqual(this.deviceId, hardwareProfile.deviceId);
    }

    public int hashCode() {
        return (((((Short.hashCode(this.serialNumber) * 31) + Short.hashCode(this.versionFirmware)) * 31) + Short.hashCode(this.versionHardware)) * 31) + this.deviceId.hashCode();
    }

    public String toString() {
        return "HardwareProfile(serialNumber=" + ((int) this.serialNumber) + ", versionFirmware=" + ((int) this.versionFirmware) + ", versionHardware=" + ((int) this.versionHardware) + ", deviceId=" + this.deviceId + ")";
    }

    public HardwareProfile(short s, short s2, short s3, String deviceId) {
        Intrinsics.checkNotNullParameter(deviceId, "deviceId");
        this.serialNumber = s;
        this.versionFirmware = s2;
        this.versionHardware = s3;
        this.deviceId = deviceId;
    }

    public /* synthetic */ HardwareProfile(short s, short s2, short s3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (short) 0 : s, (i & 2) != 0 ? (short) 0 : s2, (i & 4) != 0 ? (short) 0 : s3, (i & 8) != 0 ? "" : str);
    }

    public final short getSerialNumber() {
        return this.serialNumber;
    }

    public final void setSerialNumber(short s) {
        this.serialNumber = s;
    }

    public final short getVersionFirmware() {
        return this.versionFirmware;
    }

    public final void setVersionFirmware(short s) {
        this.versionFirmware = s;
    }

    public final short getVersionHardware() {
        return this.versionHardware;
    }

    public final void setVersionHardware(short s) {
        this.versionHardware = s;
    }

    public final String getDeviceId() {
        return this.deviceId;
    }

    public final void setDeviceId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.deviceId = str;
    }

    public final boolean isDefaultValuesSet() {
        return this.serialNumber == 0 && this.versionFirmware == 0 && this.versionHardware == 0;
    }
}
