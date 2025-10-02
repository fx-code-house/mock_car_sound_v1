package com.thor.app.bus.events;

import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothDeviceAuthorizeEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/BluetoothDeviceAuthorizeEvent;", "", "device", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "(Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;)V", "getDevice", "()Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class BluetoothDeviceAuthorizeEvent {
    private final BluetoothDeviceItem device;

    public static /* synthetic */ BluetoothDeviceAuthorizeEvent copy$default(BluetoothDeviceAuthorizeEvent bluetoothDeviceAuthorizeEvent, BluetoothDeviceItem bluetoothDeviceItem, int i, Object obj) {
        if ((i & 1) != 0) {
            bluetoothDeviceItem = bluetoothDeviceAuthorizeEvent.device;
        }
        return bluetoothDeviceAuthorizeEvent.copy(bluetoothDeviceItem);
    }

    /* renamed from: component1, reason: from getter */
    public final BluetoothDeviceItem getDevice() {
        return this.device;
    }

    public final BluetoothDeviceAuthorizeEvent copy(BluetoothDeviceItem device) {
        Intrinsics.checkNotNullParameter(device, "device");
        return new BluetoothDeviceAuthorizeEvent(device);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof BluetoothDeviceAuthorizeEvent) && Intrinsics.areEqual(this.device, ((BluetoothDeviceAuthorizeEvent) other).device);
    }

    public int hashCode() {
        return this.device.hashCode();
    }

    public String toString() {
        return "BluetoothDeviceAuthorizeEvent(device=" + this.device + ")";
    }

    public BluetoothDeviceAuthorizeEvent(BluetoothDeviceItem device) {
        Intrinsics.checkNotNullParameter(device, "device");
        this.device = device;
    }

    public final BluetoothDeviceItem getDevice() {
        return this.device;
    }
}
