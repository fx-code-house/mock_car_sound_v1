package com.thor.app.bus.events.mainpreset;

import kotlin.Metadata;

/* compiled from: UpdateDamagePckEvent.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\u0003HÖ\u0001J\t\u0010\r\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/thor/app/bus/events/mainpreset/UpdateDamagePckEvent;", "", "damagePck", "", "(I)V", "getDamagePck", "()I", "component1", "copy", "equals", "", "other", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UpdateDamagePckEvent {
    private final int damagePck;

    public static /* synthetic */ UpdateDamagePckEvent copy$default(UpdateDamagePckEvent updateDamagePckEvent, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = updateDamagePckEvent.damagePck;
        }
        return updateDamagePckEvent.copy(i);
    }

    /* renamed from: component1, reason: from getter */
    public final int getDamagePck() {
        return this.damagePck;
    }

    public final UpdateDamagePckEvent copy(int damagePck) {
        return new UpdateDamagePckEvent(damagePck);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof UpdateDamagePckEvent) && this.damagePck == ((UpdateDamagePckEvent) other).damagePck;
    }

    public int hashCode() {
        return Integer.hashCode(this.damagePck);
    }

    public String toString() {
        return "UpdateDamagePckEvent(damagePck=" + this.damagePck + ")";
    }

    public UpdateDamagePckEvent(int i) {
        this.damagePck = i;
    }

    public final int getDamagePck() {
        return this.damagePck;
    }
}
