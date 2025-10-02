package com.thor.app.bus.events.bluetooth;

import kotlin.Metadata;

/* compiled from: StopScanBluetoothDevicesEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\n\u001a\u00020\u000bHÖ\u0001J\t\u0010\f\u001a\u00020\rHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u000e"}, d2 = {"Lcom/thor/app/bus/events/bluetooth/StopScanBluetoothDevicesEvent;", "", "isStop", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class StopScanBluetoothDevicesEvent {
    private final boolean isStop;

    public static /* synthetic */ StopScanBluetoothDevicesEvent copy$default(StopScanBluetoothDevicesEvent stopScanBluetoothDevicesEvent, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = stopScanBluetoothDevicesEvent.isStop;
        }
        return stopScanBluetoothDevicesEvent.copy(z);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsStop() {
        return this.isStop;
    }

    public final StopScanBluetoothDevicesEvent copy(boolean isStop) {
        return new StopScanBluetoothDevicesEvent(isStop);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof StopScanBluetoothDevicesEvent) && this.isStop == ((StopScanBluetoothDevicesEvent) other).isStop;
    }

    public int hashCode() {
        boolean z = this.isStop;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public String toString() {
        return "StopScanBluetoothDevicesEvent(isStop=" + this.isStop + ")";
    }

    public StopScanBluetoothDevicesEvent(boolean z) {
        this.isStop = z;
    }

    public final boolean isStop() {
        return this.isStop;
    }
}
