package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: HWInfo.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B%\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\f¨\u0006\u001b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/HWInfo;", "", "data", "", "([B)V", "hwVersion", "", "bootVersion", "fmVersion", "serialNumber", "(IIII)V", "getBootVersion", "()I", "getFmVersion", "getHwVersion", "getSerialNumber", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class HWInfo {
    private final int bootVersion;
    private final int fmVersion;
    private final int hwVersion;
    private final int serialNumber;

    public static /* synthetic */ HWInfo copy$default(HWInfo hWInfo, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = hWInfo.hwVersion;
        }
        if ((i5 & 2) != 0) {
            i2 = hWInfo.bootVersion;
        }
        if ((i5 & 4) != 0) {
            i3 = hWInfo.fmVersion;
        }
        if ((i5 & 8) != 0) {
            i4 = hWInfo.serialNumber;
        }
        return hWInfo.copy(i, i2, i3, i4);
    }

    /* renamed from: component1, reason: from getter */
    public final int getHwVersion() {
        return this.hwVersion;
    }

    /* renamed from: component2, reason: from getter */
    public final int getBootVersion() {
        return this.bootVersion;
    }

    /* renamed from: component3, reason: from getter */
    public final int getFmVersion() {
        return this.fmVersion;
    }

    /* renamed from: component4, reason: from getter */
    public final int getSerialNumber() {
        return this.serialNumber;
    }

    public final HWInfo copy(int hwVersion, int bootVersion, int fmVersion, int serialNumber) {
        return new HWInfo(hwVersion, bootVersion, fmVersion, serialNumber);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HWInfo)) {
            return false;
        }
        HWInfo hWInfo = (HWInfo) other;
        return this.hwVersion == hWInfo.hwVersion && this.bootVersion == hWInfo.bootVersion && this.fmVersion == hWInfo.fmVersion && this.serialNumber == hWInfo.serialNumber;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.hwVersion) * 31) + Integer.hashCode(this.bootVersion)) * 31) + Integer.hashCode(this.fmVersion)) * 31) + Integer.hashCode(this.serialNumber);
    }

    public String toString() {
        return "HWInfo(hwVersion=" + this.hwVersion + ", bootVersion=" + this.bootVersion + ", fmVersion=" + this.fmVersion + ", serialNumber=" + this.serialNumber + ")";
    }

    public HWInfo(int i, int i2, int i3, int i4) {
        this.hwVersion = i;
        this.bootVersion = i2;
        this.fmVersion = i3;
        this.serialNumber = i4;
    }

    public final int getHwVersion() {
        return this.hwVersion;
    }

    public final int getBootVersion() {
        return this.bootVersion;
    }

    public final int getFmVersion() {
        return this.fmVersion;
    }

    public final int getSerialNumber() {
        return this.serialNumber;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public HWInfo(byte[] data) {
        this(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(0, 2))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(2, 4))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(4, 6))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(6, 8))));
        Intrinsics.checkNotNullParameter(data, "data");
    }
}
