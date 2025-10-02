package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
final class zzy implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchasesResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzy(BillingClientImpl billingClientImpl, String str, PurchasesResponseListener purchasesResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchasesResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzbj zzbjVarZzN = BillingClientImpl.zzN(this.zzc, this.zza, 9);
        if (zzbjVarZzN.zzb() != null) {
            this.zzb.onQueryPurchasesResponse(zzbjVarZzN.zza(), zzbjVarZzN.zzb());
            return null;
        }
        this.zzb.onQueryPurchasesResponse(zzbjVarZzN.zza(), com.google.android.gms.internal.play_billing.zzu.zzk());
        return null;
    }
}
