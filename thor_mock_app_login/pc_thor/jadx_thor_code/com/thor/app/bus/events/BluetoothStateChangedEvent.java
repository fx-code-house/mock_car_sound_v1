package com.thor.app.bus.events;

import kotlin.Metadata;

/* compiled from: BluetoothStateChangedEvent.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\u0003HÖ\u0001J\t\u0010\r\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/thor/app/bus/events/BluetoothStateChangedEvent;", "", "state", "", "(I)V", "getState", "()I", "component1", "copy", "equals", "", "other", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class BluetoothStateChangedEvent {
    private final int state;

    public static /* synthetic */ BluetoothStateChangedEvent copy$default(BluetoothStateChangedEvent bluetoothStateChangedEvent, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = bluetoothStateChangedEvent.state;
        }
        return bluetoothStateChangedEvent.copy(i);
    }

    /* renamed from: component1, reason: from getter */
    public final int getState() {
        return this.state;
    }

    public final BluetoothStateChangedEvent copy(int state) {
        return new BluetoothStateChangedEvent(state);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof BluetoothStateChangedEvent) && this.state == ((BluetoothStateChangedEvent) other).state;
    }

    public int hashCode() {
        return Integer.hashCode(this.state);
    }

    public String toString() {
        return "BluetoothStateChangedEvent(state=" + this.state + ")";
    }

    public BluetoothStateChangedEvent(int i) {
        this.state = i;
    }

    public final int getState() {
        return this.state;
    }
}
