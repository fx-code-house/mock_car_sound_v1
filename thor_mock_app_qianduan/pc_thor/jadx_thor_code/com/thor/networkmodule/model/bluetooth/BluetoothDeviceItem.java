package com.thor.networkmodule.model.bluetooth;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothDeviceItem.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "macAddress", "(Ljava/lang/String;Ljava/lang/String;)V", "getMacAddress", "()Ljava/lang/String;", "getName", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class BluetoothDeviceItem {
    private final String macAddress;
    private final String name;

    public static /* synthetic */ BluetoothDeviceItem copy$default(BluetoothDeviceItem bluetoothDeviceItem, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = bluetoothDeviceItem.name;
        }
        if ((i & 2) != 0) {
            str2 = bluetoothDeviceItem.macAddress;
        }
        return bluetoothDeviceItem.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final String getMacAddress() {
        return this.macAddress;
    }

    public final BluetoothDeviceItem copy(String name, String macAddress) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(macAddress, "macAddress");
        return new BluetoothDeviceItem(name, macAddress);
    }

    public String toString() {
        return "BluetoothDeviceItem(name=" + this.name + ", macAddress=" + this.macAddress + ")";
    }

    public BluetoothDeviceItem(String name, String macAddress) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(macAddress, "macAddress");
        this.name = name;
        this.macAddress = macAddress;
    }

    public final String getMacAddress() {
        return this.macAddress;
    }

    public final String getName() {
        return this.name;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem");
        return Intrinsics.areEqual(this.name, ((BluetoothDeviceItem) other).name);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.macAddress.hashCode();
    }
}
