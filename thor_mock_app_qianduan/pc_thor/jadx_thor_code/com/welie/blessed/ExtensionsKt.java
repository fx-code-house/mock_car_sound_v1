package com.welie.blessed;

import android.bluetooth.BluetoothGattCharacteristic;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Extensions.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\u000f\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0002ø\u0001\u0000\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\b\u001a\n\u0010\t\u001a\u00020\u0007*\u00020\b\u001a\u0012\u0010\n\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0007*\u00020\b\u001a\n\u0010\u000e\u001a\u00020\u0007*\u00020\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"asHexString", "", "", "asString", "asUInt8", "Lkotlin/UInt;", "supportsNotifying", "", "Landroid/bluetooth/BluetoothGattCharacteristic;", "supportsReading", "supportsWriteType", "writeType", "Lcom/welie/blessed/WriteType;", "supportsWritingWithResponse", "supportsWritingWithoutResponse", "blessed_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ExtensionsKt {

    /* compiled from: Extensions.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WriteType.values().length];
            try {
                iArr[WriteType.WITH_RESPONSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WriteType.WITHOUT_RESPONSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String asString(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return bArr.length == 0 ? "" : new BluetoothBytesParser(bArr, 0, null, 6, null).getStringValue();
    }

    public static final String asHexString(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return BluetoothBytesParser.INSTANCE.bytes2String(bArr);
    }

    public static final UInt asUInt8(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        if (bArr.length == 0) {
            return null;
        }
        return UInt.m713boximpl(UInt.m719constructorimpl(new BluetoothBytesParser(bArr, 0, null, 6, null).getIntValue(17)));
    }

    public static final boolean supportsReading(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intrinsics.checkNotNullParameter(bluetoothGattCharacteristic, "<this>");
        return (bluetoothGattCharacteristic.getProperties() & 2) > 0;
    }

    public static final boolean supportsWritingWithResponse(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intrinsics.checkNotNullParameter(bluetoothGattCharacteristic, "<this>");
        return (bluetoothGattCharacteristic.getProperties() & 8) > 0;
    }

    public static final boolean supportsWritingWithoutResponse(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intrinsics.checkNotNullParameter(bluetoothGattCharacteristic, "<this>");
        return (bluetoothGattCharacteristic.getProperties() & 4) > 0;
    }

    public static final boolean supportsNotifying(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intrinsics.checkNotNullParameter(bluetoothGattCharacteristic, "<this>");
        return (bluetoothGattCharacteristic.getProperties() & 16) > 0 || (bluetoothGattCharacteristic.getProperties() & 32) > 0;
    }

    public static final boolean supportsWriteType(BluetoothGattCharacteristic bluetoothGattCharacteristic, WriteType writeType) {
        Intrinsics.checkNotNullParameter(bluetoothGattCharacteristic, "<this>");
        Intrinsics.checkNotNullParameter(writeType, "writeType");
        int i = WhenMappings.$EnumSwitchMapping$0[writeType.ordinal()];
        return (bluetoothGattCharacteristic.getProperties() & (i != 1 ? i != 2 ? 0 : 4 : 8)) > 0;
    }
}
