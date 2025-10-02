package com.android.billingclient.api;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: com.android.billingclient:billing-ktx@@6.0.1 */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/android/billingclient/api/ConsumeResult;", "", "billingResult", "Lcom/android/billingclient/api/BillingResult;", "purchaseToken", "", "(Lcom/android/billingclient/api/BillingResult;Ljava/lang/String;)V", "getBillingResult", "()Lcom/android/billingclient/api/BillingResult;", "getPurchaseToken", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "java.com.google.android.libraries.play.billing.public.ktbilling_granule"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class ConsumeResult {
    private final BillingResult zza;
    private final String zzb;

    public ConsumeResult(BillingResult billingResult, String str) {
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        this.zza = billingResult;
        this.zzb = str;
    }

    public static /* synthetic */ ConsumeResult copy$default(ConsumeResult consumeResult, BillingResult billingResult, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            billingResult = consumeResult.zza;
        }
        if ((i & 2) != 0) {
            str = consumeResult.zzb;
        }
        return consumeResult.copy(billingResult, str);
    }

    /* renamed from: component1, reason: from getter */
    public final BillingResult getZza() {
        return this.zza;
    }

    /* renamed from: component2, reason: from getter */
    public final String getZzb() {
        return this.zzb;
    }

    public final ConsumeResult copy(BillingResult billingResult, String purchaseToken) {
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        return new ConsumeResult(billingResult, purchaseToken);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConsumeResult)) {
            return false;
        }
        ConsumeResult consumeResult = (ConsumeResult) other;
        return Intrinsics.areEqual(this.zza, consumeResult.zza) && Intrinsics.areEqual(this.zzb, consumeResult.zzb);
    }

    public final BillingResult getBillingResult() {
        return this.zza;
    }

    public final String getPurchaseToken() {
        return this.zzb;
    }

    public int hashCode() {
        int iHashCode = this.zza.hashCode() * 31;
        String str = this.zzb;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "ConsumeResult(billingResult=" + this.zza + ", purchaseToken=" + this.zzb + ")";
    }
}
