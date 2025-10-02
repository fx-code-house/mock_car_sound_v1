package com.thor.app.bus.events.device;

import kotlin.Metadata;

/* compiled from: DeviceLockedStateEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\n\u001a\u00020\u000bHÖ\u0001J\t\u0010\f\u001a\u00020\rHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u000e"}, d2 = {"Lcom/thor/app/bus/events/device/DeviceLockedStateEvent;", "", "isLocked", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class DeviceLockedStateEvent {
    private final boolean isLocked;

    public static /* synthetic */ DeviceLockedStateEvent copy$default(DeviceLockedStateEvent deviceLockedStateEvent, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = deviceLockedStateEvent.isLocked;
        }
        return deviceLockedStateEvent.copy(z);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsLocked() {
        return this.isLocked;
    }

    public final DeviceLockedStateEvent copy(boolean isLocked) {
        return new DeviceLockedStateEvent(isLocked);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DeviceLockedStateEvent) && this.isLocked == ((DeviceLockedStateEvent) other).isLocked;
    }

    public int hashCode() {
        boolean z = this.isLocked;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public String toString() {
        return "DeviceLockedStateEvent(isLocked=" + this.isLocked + ")";
    }

    public DeviceLockedStateEvent(boolean z) {
        this.isLocked = z;
    }

    public final boolean isLocked() {
        return this.isLocked;
    }
}
