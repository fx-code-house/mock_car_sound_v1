package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: DeviceStatus.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u001d\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 +2\u00020\u0001:\u0001+BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\fHÆ\u0003Jm\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u0003HÆ\u0001J\u0013\u0010&\u001a\u00020\u00032\b\u0010'\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010(\u001a\u00020\fHÖ\u0001J\t\u0010)\u001a\u00020*HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010¨\u0006,"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;", "", "presetError", "", "canFileError", "fileSystemError", "deviceBlocked", "mute", "driveSelect", "nativeControl", "twoSpeaker", "presetIndex", "", "engineConnected", "(ZZZZZZZZIZ)V", "getCanFileError", "()Z", "getDeviceBlocked", "getDriveSelect", "getEngineConnected", "getFileSystemError", "getMute", "getNativeControl", "getPresetError", "getPresetIndex", "()I", "getTwoSpeaker", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DeviceStatus {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean canFileError;
    private final boolean deviceBlocked;
    private final boolean driveSelect;
    private final boolean engineConnected;
    private final boolean fileSystemError;
    private final boolean mute;
    private final boolean nativeControl;
    private final boolean presetError;
    private final int presetIndex;
    private final boolean twoSpeaker;

    /* renamed from: component1, reason: from getter */
    public final boolean getPresetError() {
        return this.presetError;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getEngineConnected() {
        return this.engineConnected;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getCanFileError() {
        return this.canFileError;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getFileSystemError() {
        return this.fileSystemError;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getDeviceBlocked() {
        return this.deviceBlocked;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getMute() {
        return this.mute;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getDriveSelect() {
        return this.driveSelect;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getNativeControl() {
        return this.nativeControl;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getTwoSpeaker() {
        return this.twoSpeaker;
    }

    /* renamed from: component9, reason: from getter */
    public final int getPresetIndex() {
        return this.presetIndex;
    }

    public final DeviceStatus copy(boolean presetError, boolean canFileError, boolean fileSystemError, boolean deviceBlocked, boolean mute, boolean driveSelect, boolean nativeControl, boolean twoSpeaker, int presetIndex, boolean engineConnected) {
        return new DeviceStatus(presetError, canFileError, fileSystemError, deviceBlocked, mute, driveSelect, nativeControl, twoSpeaker, presetIndex, engineConnected);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeviceStatus)) {
            return false;
        }
        DeviceStatus deviceStatus = (DeviceStatus) other;
        return this.presetError == deviceStatus.presetError && this.canFileError == deviceStatus.canFileError && this.fileSystemError == deviceStatus.fileSystemError && this.deviceBlocked == deviceStatus.deviceBlocked && this.mute == deviceStatus.mute && this.driveSelect == deviceStatus.driveSelect && this.nativeControl == deviceStatus.nativeControl && this.twoSpeaker == deviceStatus.twoSpeaker && this.presetIndex == deviceStatus.presetIndex && this.engineConnected == deviceStatus.engineConnected;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v12, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v8, types: [boolean] */
    public int hashCode() {
        boolean z = this.presetError;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.canFileError;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        ?? r22 = this.fileSystemError;
        int i4 = r22;
        if (r22 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        ?? r23 = this.deviceBlocked;
        int i6 = r23;
        if (r23 != 0) {
            i6 = 1;
        }
        int i7 = (i5 + i6) * 31;
        ?? r24 = this.mute;
        int i8 = r24;
        if (r24 != 0) {
            i8 = 1;
        }
        int i9 = (i7 + i8) * 31;
        ?? r25 = this.driveSelect;
        int i10 = r25;
        if (r25 != 0) {
            i10 = 1;
        }
        int i11 = (i9 + i10) * 31;
        ?? r26 = this.nativeControl;
        int i12 = r26;
        if (r26 != 0) {
            i12 = 1;
        }
        int i13 = (i11 + i12) * 31;
        ?? r27 = this.twoSpeaker;
        int i14 = r27;
        if (r27 != 0) {
            i14 = 1;
        }
        int iHashCode = (((i13 + i14) * 31) + Integer.hashCode(this.presetIndex)) * 31;
        boolean z2 = this.engineConnected;
        return iHashCode + (z2 ? 1 : z2 ? 1 : 0);
    }

    public String toString() {
        return "DeviceStatus(presetError=" + this.presetError + ", canFileError=" + this.canFileError + ", fileSystemError=" + this.fileSystemError + ", deviceBlocked=" + this.deviceBlocked + ", mute=" + this.mute + ", driveSelect=" + this.driveSelect + ", nativeControl=" + this.nativeControl + ", twoSpeaker=" + this.twoSpeaker + ", presetIndex=" + this.presetIndex + ", engineConnected=" + this.engineConnected + ")";
    }

    public DeviceStatus(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, boolean z9) {
        this.presetError = z;
        this.canFileError = z2;
        this.fileSystemError = z3;
        this.deviceBlocked = z4;
        this.mute = z5;
        this.driveSelect = z6;
        this.nativeControl = z7;
        this.twoSpeaker = z8;
        this.presetIndex = i;
        this.engineConnected = z9;
    }

    public final boolean getPresetError() {
        return this.presetError;
    }

    public final boolean getCanFileError() {
        return this.canFileError;
    }

    public final boolean getFileSystemError() {
        return this.fileSystemError;
    }

    public final boolean getDeviceBlocked() {
        return this.deviceBlocked;
    }

    public final boolean getMute() {
        return this.mute;
    }

    public final boolean getDriveSelect() {
        return this.driveSelect;
    }

    public final boolean getNativeControl() {
        return this.nativeControl;
    }

    public final boolean getTwoSpeaker() {
        return this.twoSpeaker;
    }

    public final int getPresetIndex() {
        return this.presetIndex;
    }

    public final boolean getEngineConnected() {
        return this.engineConnected;
    }

    /* compiled from: DeviceStatus.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus$Companion;", "", "()V", "invoke", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;", "data", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DeviceStatus invoke(byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            boolean[] bitArray = BleHelperKt.toBitArray(ArraysKt.first(data));
            return new DeviceStatus(bitArray[7], bitArray[6], bitArray[5], bitArray[4], bitArray[3], bitArray[2], bitArray[1], bitArray[0], BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(2, data.length))), data[1] == 1);
        }
    }
}
