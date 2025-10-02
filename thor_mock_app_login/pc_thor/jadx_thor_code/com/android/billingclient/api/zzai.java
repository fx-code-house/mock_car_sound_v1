package com.android.billingclient.api;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
final class zzai {
    private final List zza;
    private final BillingResult zzb;

    zzai(BillingResult billingResult, List list) {
        this.zza = list;
        this.zzb = billingResult;
    }

    final BillingResult zza() {
        return this.zzb;
    }

    final List zzb() {
        return this.zza;
    }
}
