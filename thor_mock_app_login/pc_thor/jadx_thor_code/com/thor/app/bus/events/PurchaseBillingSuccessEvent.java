package com.thor.app.bus.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PurchaseBillingSuccessEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\bJ$\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/thor/app/bus/events/PurchaseBillingSuccessEvent;", "", "isSuccessful", "", "errorCode", "", "(ZLjava/lang/Integer;)V", "getErrorCode", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "()Z", "component1", "component2", "copy", "(ZLjava/lang/Integer;)Lcom/thor/app/bus/events/PurchaseBillingSuccessEvent;", "equals", "other", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class PurchaseBillingSuccessEvent {
    private final Integer errorCode;
    private final boolean isSuccessful;

    public static /* synthetic */ PurchaseBillingSuccessEvent copy$default(PurchaseBillingSuccessEvent purchaseBillingSuccessEvent, boolean z, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            z = purchaseBillingSuccessEvent.isSuccessful;
        }
        if ((i & 2) != 0) {
            num = purchaseBillingSuccessEvent.errorCode;
        }
        return purchaseBillingSuccessEvent.copy(z, num);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsSuccessful() {
        return this.isSuccessful;
    }

    /* renamed from: component2, reason: from getter */
    public final Integer getErrorCode() {
        return this.errorCode;
    }

    public final PurchaseBillingSuccessEvent copy(boolean isSuccessful, Integer errorCode) {
        return new PurchaseBillingSuccessEvent(isSuccessful, errorCode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PurchaseBillingSuccessEvent)) {
            return false;
        }
        PurchaseBillingSuccessEvent purchaseBillingSuccessEvent = (PurchaseBillingSuccessEvent) other;
        return this.isSuccessful == purchaseBillingSuccessEvent.isSuccessful && Intrinsics.areEqual(this.errorCode, purchaseBillingSuccessEvent.errorCode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.isSuccessful;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        Integer num = this.errorCode;
        return i + (num == null ? 0 : num.hashCode());
    }

    public String toString() {
        return "PurchaseBillingSuccessEvent(isSuccessful=" + this.isSuccessful + ", errorCode=" + this.errorCode + ")";
    }

    public PurchaseBillingSuccessEvent(boolean z, Integer num) {
        this.isSuccessful = z;
        this.errorCode = num;
    }

    public final Integer getErrorCode() {
        return this.errorCode;
    }

    public final boolean isSuccessful() {
        return this.isSuccessful;
    }
}
