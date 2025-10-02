package com.android.billingclient.api;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.internal.play_billing.zzgd;
import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
final class zzaf implements ServiceConnection {
    final /* synthetic */ BillingClientImpl zza;
    private final Object zzb = new Object();
    private boolean zzc = false;
    private BillingClientStateListener zzd;

    /* synthetic */ zzaf(BillingClientImpl billingClientImpl, BillingClientStateListener billingClientStateListener, zzae zzaeVar) {
        this.zza = billingClientImpl;
        this.zzd = billingClientStateListener;
    }

    private final void zzd(BillingResult billingResult) {
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingSetupFinished(billingResult);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Billing service connected.");
        this.zza.zzg = com.google.android.gms.internal.play_billing.zzd.zzn(iBinder);
        BillingClientImpl billingClientImpl = this.zza;
        if (billingClientImpl.zzS(new Callable() { // from class: com.android.billingclient.api.zzac
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zza();
                return null;
            }
        }, 30000L, new Runnable() { // from class: com.android.billingclient.api.zzad
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzb();
            }
        }, billingClientImpl.zzO()) == null) {
            BillingResult billingResultZzQ = this.zza.zzQ();
            this.zza.zzf.zza(zzaq.zza(25, 6, billingResultZzQ));
            zzd(billingResultZzQ);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Billing service disconnected.");
        this.zza.zzf.zzc(zzgd.zzw());
        this.zza.zzg = null;
        this.zza.zza = 0;
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingServiceDisconnected();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final /* synthetic */ java.lang.Object zza() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 502
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.zzaf.zza():java.lang.Object");
    }

    final /* synthetic */ void zzb() {
        this.zza.zza = 0;
        this.zza.zzg = null;
        this.zza.zzf.zza(zzaq.zza(24, 6, zzat.zzn));
        zzd(zzat.zzn);
    }

    final void zzc() {
        synchronized (this.zzb) {
            this.zzd = null;
            this.zzc = true;
        }
    }
}
