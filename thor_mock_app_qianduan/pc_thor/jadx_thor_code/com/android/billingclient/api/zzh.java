package com.android.billingclient.api;

import android.content.Context;
import android.content.IntentFilter;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
final class zzh {
    private final Context zza;
    private final zzg zzb;

    /* JADX WARN: Multi-variable type inference failed */
    zzh(Context context, zzaz zzazVar, zzar zzarVar) {
        this.zza = context;
        this.zzb = new zzg(this, null, zzarVar, 0 == true ? 1 : 0);
    }

    final zzaz zzb() {
        zzg.zza(this.zzb);
        return null;
    }

    final PurchasesUpdatedListener zzc() {
        return this.zzb.zzb;
    }

    final void zzd() {
        this.zzb.zzd(this.zza);
    }

    final void zze() {
        IntentFilter intentFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
        intentFilter.addAction("com.android.vending.billing.ALTERNATIVE_BILLING");
        this.zzb.zzc(this.zza, intentFilter);
    }

    zzh(Context context, PurchasesUpdatedListener purchasesUpdatedListener, AlternativeBillingListener alternativeBillingListener, zzar zzarVar) {
        this.zza = context;
        this.zzb = new zzg(this, purchasesUpdatedListener, alternativeBillingListener, zzarVar, null);
    }
}
