package com.thor.app.bus.events;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: MaxCountReInitCryptoEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000b\u001a\u00020\fHÖ\u0001J\t\u0010\r\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/thor/app/bus/events/MaxCountReInitCryptoEvent;", "", "maxCount", "", "(Z)V", "getMaxCount", "()Z", "component1", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class MaxCountReInitCryptoEvent {
    private final boolean maxCount;

    public MaxCountReInitCryptoEvent() {
        this(false, 1, null);
    }

    public static /* synthetic */ MaxCountReInitCryptoEvent copy$default(MaxCountReInitCryptoEvent maxCountReInitCryptoEvent, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = maxCountReInitCryptoEvent.maxCount;
        }
        return maxCountReInitCryptoEvent.copy(z);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getMaxCount() {
        return this.maxCount;
    }

    public final MaxCountReInitCryptoEvent copy(boolean maxCount) {
        return new MaxCountReInitCryptoEvent(maxCount);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof MaxCountReInitCryptoEvent) && this.maxCount == ((MaxCountReInitCryptoEvent) other).maxCount;
    }

    public int hashCode() {
        boolean z = this.maxCount;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public String toString() {
        return "MaxCountReInitCryptoEvent(maxCount=" + this.maxCount + ")";
    }

    public MaxCountReInitCryptoEvent(boolean z) {
        this.maxCount = z;
    }

    public /* synthetic */ MaxCountReInitCryptoEvent(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    public final boolean getMaxCount() {
        return this.maxCount;
    }
}
