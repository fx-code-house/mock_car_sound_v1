package com.android.billingclient.api;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
final class zzaa extends ResultReceiver {
    final /* synthetic */ InAppMessageResponseListener zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaa(BillingClientImpl billingClientImpl, Handler handler, InAppMessageResponseListener inAppMessageResponseListener) {
        super(handler);
        this.zza = inAppMessageResponseListener;
    }

    @Override // android.os.ResultReceiver
    public final void onReceiveResult(int i, Bundle bundle) {
        this.zza.onInAppMessageResponse(com.google.android.gms.internal.play_billing.zzb.zze(bundle, "BillingClient"));
    }
}
