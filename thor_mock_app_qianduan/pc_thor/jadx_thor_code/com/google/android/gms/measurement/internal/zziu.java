package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zziu implements Runnable {
    private final /* synthetic */ zzn zza;
    private final /* synthetic */ zzir zzb;

    zziu(zzir zzirVar, zzn zznVar) {
        this.zzb = zzirVar;
        this.zza = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        zzei zzeiVar = this.zzb.zzb;
        if (zzeiVar == null) {
            this.zzb.zzq().zze().zza("Failed to reset data on the service: not connected to service");
            return;
        }
        try {
            zzeiVar.zzd(this.zza);
        } catch (RemoteException e) {
            this.zzb.zzq().zze().zza("Failed to reset data on the service: remote exception", e);
        }
        this.zzb.zzaj();
    }
}
