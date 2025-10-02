package com.thor.app.bus.events.bluetooth;

import kotlin.Metadata;

/* compiled from: UpdateCanConfigurationsErrorEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u00032\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsErrorEvent;", "", "isNetworkError", "", "isUploadingOnSchemaError", "(ZZ)V", "()Z", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UpdateCanConfigurationsErrorEvent {
    private final boolean isNetworkError;
    private final boolean isUploadingOnSchemaError;

    public static /* synthetic */ UpdateCanConfigurationsErrorEvent copy$default(UpdateCanConfigurationsErrorEvent updateCanConfigurationsErrorEvent, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = updateCanConfigurationsErrorEvent.isNetworkError;
        }
        if ((i & 2) != 0) {
            z2 = updateCanConfigurationsErrorEvent.isUploadingOnSchemaError;
        }
        return updateCanConfigurationsErrorEvent.copy(z, z2);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsNetworkError() {
        return this.isNetworkError;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getIsUploadingOnSchemaError() {
        return this.isUploadingOnSchemaError;
    }

    public final UpdateCanConfigurationsErrorEvent copy(boolean isNetworkError, boolean isUploadingOnSchemaError) {
        return new UpdateCanConfigurationsErrorEvent(isNetworkError, isUploadingOnSchemaError);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UpdateCanConfigurationsErrorEvent)) {
            return false;
        }
        UpdateCanConfigurationsErrorEvent updateCanConfigurationsErrorEvent = (UpdateCanConfigurationsErrorEvent) other;
        return this.isNetworkError == updateCanConfigurationsErrorEvent.isNetworkError && this.isUploadingOnSchemaError == updateCanConfigurationsErrorEvent.isUploadingOnSchemaError;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.isNetworkError;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        boolean z2 = this.isUploadingOnSchemaError;
        return i + (z2 ? 1 : z2 ? 1 : 0);
    }

    public String toString() {
        return "UpdateCanConfigurationsErrorEvent(isNetworkError=" + this.isNetworkError + ", isUploadingOnSchemaError=" + this.isUploadingOnSchemaError + ")";
    }

    public UpdateCanConfigurationsErrorEvent(boolean z, boolean z2) {
        this.isNetworkError = z;
        this.isUploadingOnSchemaError = z2;
    }

    public final boolean isNetworkError() {
        return this.isNetworkError;
    }

    public final boolean isUploadingOnSchemaError() {
        return this.isUploadingOnSchemaError;
    }
}
