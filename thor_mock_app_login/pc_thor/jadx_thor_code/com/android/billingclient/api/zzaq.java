package com.android.billingclient.api;

import com.google.android.gms.internal.play_billing.zzfa;
import com.google.android.gms.internal.play_billing.zzfb;
import com.google.android.gms.internal.play_billing.zzfe;
import com.google.android.gms.internal.play_billing.zzff;
import com.google.android.gms.internal.play_billing.zzfh;
import com.google.android.gms.internal.play_billing.zzfj;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
public final /* synthetic */ class zzaq {
    public static zzfb zza(int i, int i2, BillingResult billingResult) {
        zzfa zzfaVarZzv = zzfb.zzv();
        zzfh zzfhVarZzv = zzfj.zzv();
        zzfhVarZzv.zzj(billingResult.getResponseCode());
        zzfhVarZzv.zzi(billingResult.getDebugMessage());
        zzfhVarZzv.zzk(i);
        zzfaVarZzv.zzi(zzfhVarZzv);
        zzfaVarZzv.zzk(i2);
        return (zzfb) zzfaVarZzv.zzc();
    }

    public static zzff zzb(int i) {
        zzfe zzfeVarZzv = zzff.zzv();
        zzfeVarZzv.zzj(i);
        return (zzff) zzfeVarZzv.zzc();
    }
}
