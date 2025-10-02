package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: HwInfo2.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u001bB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B%\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\f¨\u0006\u001c"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2;", "", "data", "", "([B)V", "bleType", "", "nandManufactureCode", "nandDeviceCode", "nandFeatureSet", "(IIII)V", "getBleType", "()I", "getNandDeviceCode", "getNandFeatureSet", "getNandManufactureCode", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "BleType", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class HwInfo2 {
    private final int bleType;
    private final int nandDeviceCode;
    private final int nandFeatureSet;
    private final int nandManufactureCode;

    public static /* synthetic */ HwInfo2 copy$default(HwInfo2 hwInfo2, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = hwInfo2.bleType;
        }
        if ((i5 & 2) != 0) {
            i2 = hwInfo2.nandManufactureCode;
        }
        if ((i5 & 4) != 0) {
            i3 = hwInfo2.nandDeviceCode;
        }
        if ((i5 & 8) != 0) {
            i4 = hwInfo2.nandFeatureSet;
        }
        return hwInfo2.copy(i, i2, i3, i4);
    }

    /* renamed from: component1, reason: from getter */
    public final int getBleType() {
        return this.bleType;
    }

    /* renamed from: component2, reason: from getter */
    public final int getNandManufactureCode() {
        return this.nandManufactureCode;
    }

    /* renamed from: component3, reason: from getter */
    public final int getNandDeviceCode() {
        return this.nandDeviceCode;
    }

    /* renamed from: component4, reason: from getter */
    public final int getNandFeatureSet() {
        return this.nandFeatureSet;
    }

    public final HwInfo2 copy(int bleType, int nandManufactureCode, int nandDeviceCode, int nandFeatureSet) {
        return new HwInfo2(bleType, nandManufactureCode, nandDeviceCode, nandFeatureSet);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HwInfo2)) {
            return false;
        }
        HwInfo2 hwInfo2 = (HwInfo2) other;
        return this.bleType == hwInfo2.bleType && this.nandManufactureCode == hwInfo2.nandManufactureCode && this.nandDeviceCode == hwInfo2.nandDeviceCode && this.nandFeatureSet == hwInfo2.nandFeatureSet;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.bleType) * 31) + Integer.hashCode(this.nandManufactureCode)) * 31) + Integer.hashCode(this.nandDeviceCode)) * 31) + Integer.hashCode(this.nandFeatureSet);
    }

    public String toString() {
        return "HwInfo2(bleType=" + this.bleType + ", nandManufactureCode=" + this.nandManufactureCode + ", nandDeviceCode=" + this.nandDeviceCode + ", nandFeatureSet=" + this.nandFeatureSet + ")";
    }

    public HwInfo2(int i, int i2, int i3, int i4) {
        this.bleType = i;
        this.nandManufactureCode = i2;
        this.nandDeviceCode = i3;
        this.nandFeatureSet = i4;
    }

    public final int getBleType() {
        return this.bleType;
    }

    public final int getNandManufactureCode() {
        return this.nandManufactureCode;
    }

    public final int getNandDeviceCode() {
        return this.nandDeviceCode;
    }

    public final int getNandFeatureSet() {
        return this.nandFeatureSet;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public HwInfo2(byte[] data) {
        this(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(0, 2))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(2, 4))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(4, 6))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(6, 8))));
        Intrinsics.checkNotNullParameter(data, "data");
    }

    /* compiled from: HwInfo2.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2$BleType;", "", "rowValue", "", "(Ljava/lang/String;II)V", "getRowValue", "()I", "HM10", "HM12", "NORDIC", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum BleType {
        HM10(0),
        HM12(1),
        NORDIC(2);


        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final int rowValue;

        BleType(int i) {
            this.rowValue = i;
        }

        public final int getRowValue() {
            return this.rowValue;
        }

        /* compiled from: HwInfo2.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2$BleType$Companion;", "", "()V", "fromRawValue", "Lcom/thor/businessmodule/bluetooth/model/other/settings/HwInfo2$BleType;", "rawValue", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final BleType fromRawValue(int rawValue) {
                for (BleType bleType : BleType.values()) {
                    if (bleType.getRowValue() == rawValue) {
                        return bleType;
                    }
                }
                return null;
            }
        }
    }
}
