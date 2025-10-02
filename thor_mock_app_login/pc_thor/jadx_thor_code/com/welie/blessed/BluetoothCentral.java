package com.welie.blessed;

import android.bluetooth.BluetoothDevice;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothCentral.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0017\u001a\u00020\u0018J\u0013\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u000e\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010\u001e\u001a\u00020\u000eH\u0016J\u000e\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0018R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\b¨\u0006!"}, d2 = {"Lcom/welie/blessed/BluetoothCentral;", "", "device", "Landroid/bluetooth/BluetoothDevice;", "(Landroid/bluetooth/BluetoothDevice;)V", "address", "", "getAddress", "()Ljava/lang/String;", "bondState", "Lcom/welie/blessed/BondState;", "getBondState", "()Lcom/welie/blessed/BondState;", "currentMtu", "", "getCurrentMtu", "()I", "setCurrentMtu", "(I)V", "getDevice", "()Landroid/bluetooth/BluetoothDevice;", AppMeasurementSdk.ConditionalUserProperty.NAME, "getName", "createBond", "", "equals", "other", "getMaximumWriteValueLength", "writeType", "Lcom/welie/blessed/WriteType;", "hashCode", "setPairingConfirmation", "confirm", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothCentral {
    private int currentMtu;
    private final BluetoothDevice device;

    /* compiled from: BluetoothCentral.kt */
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
                iArr[WriteType.SIGNED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BluetoothCentral(BluetoothDevice device) {
        Intrinsics.checkNotNullParameter(device, "device");
        this.device = device;
        this.currentMtu = 23;
    }

    public final BluetoothDevice getDevice() {
        return this.device;
    }

    public final int getCurrentMtu() {
        return this.currentMtu;
    }

    public final void setCurrentMtu(int i) {
        this.currentMtu = i;
    }

    public final String getAddress() {
        String address = this.device.getAddress();
        Intrinsics.checkNotNullExpressionValue(address, "device.address");
        return address;
    }

    public final String getName() {
        if (this.device.getName() == null) {
            return "";
        }
        String name = this.device.getName();
        Intrinsics.checkNotNullExpressionValue(name, "device.name");
        return name;
    }

    public final BondState getBondState() {
        return BondState.INSTANCE.fromValue(this.device.getBondState());
    }

    public final boolean createBond() {
        return this.device.createBond();
    }

    public final boolean setPairingConfirmation(boolean confirm) {
        return this.device.setPairingConfirmation(confirm);
    }

    public final int getMaximumWriteValueLength(WriteType writeType) {
        Intrinsics.checkNotNullParameter(writeType, "writeType");
        int i = WhenMappings.$EnumSwitchMapping$0[writeType.ordinal()];
        if (i == 1) {
            return 512;
        }
        if (i == 2) {
            return this.currentMtu - 15;
        }
        return this.currentMtu - 3;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        return Intrinsics.areEqual(this.device.getAddress(), ((BluetoothCentral) other).device.getAddress());
    }

    public int hashCode() {
        return Objects.hash(this.device);
    }
}
