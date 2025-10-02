package com.thor.app.bus.events;

import kotlin.Metadata;

/* compiled from: FormatFlashExecuteEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/thor/app/bus/events/FormatFlashExecuteEvent;", "", "isSuccess", "", "showFormatDelay", "(ZZ)V", "()Z", "getShowFormatDelay", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class FormatFlashExecuteEvent {
    private final boolean isSuccess;
    private final boolean showFormatDelay;

    public static /* synthetic */ FormatFlashExecuteEvent copy$default(FormatFlashExecuteEvent formatFlashExecuteEvent, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = formatFlashExecuteEvent.isSuccess;
        }
        if ((i & 2) != 0) {
            z2 = formatFlashExecuteEvent.showFormatDelay;
        }
        return formatFlashExecuteEvent.copy(z, z2);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsSuccess() {
        return this.isSuccess;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getShowFormatDelay() {
        return this.showFormatDelay;
    }

    public final FormatFlashExecuteEvent copy(boolean isSuccess, boolean showFormatDelay) {
        return new FormatFlashExecuteEvent(isSuccess, showFormatDelay);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FormatFlashExecuteEvent)) {
            return false;
        }
        FormatFlashExecuteEvent formatFlashExecuteEvent = (FormatFlashExecuteEvent) other;
        return this.isSuccess == formatFlashExecuteEvent.isSuccess && this.showFormatDelay == formatFlashExecuteEvent.showFormatDelay;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.isSuccess;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        boolean z2 = this.showFormatDelay;
        return i + (z2 ? 1 : z2 ? 1 : 0);
    }

    public String toString() {
        return "FormatFlashExecuteEvent(isSuccess=" + this.isSuccess + ", showFormatDelay=" + this.showFormatDelay + ")";
    }

    public FormatFlashExecuteEvent(boolean z, boolean z2) {
        this.isSuccess = z;
        this.showFormatDelay = z2;
    }

    public final boolean getShowFormatDelay() {
        return this.showFormatDelay;
    }

    public final boolean isSuccess() {
        return this.isSuccess;
    }
}
