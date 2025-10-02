package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zziy implements Runnable {
    private final /* synthetic */ zzij zza;
    private final /* synthetic */ zzir zzb;

    zziy(zzir zzirVar, zzij zzijVar) {
        this.zzb = zzirVar;
        this.zza = zzijVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        zzei zzeiVar = this.zzb.zzb;
        if (zzeiVar == null) {
            this.zzb.zzq().zze().zza("Failed to send current screen to service");
            return;
        }
        try {
            zzij zzijVar = this.zza;
            if (zzijVar == null) {
                zzeiVar.zza(0L, (String) null, (String) null, this.zzb.zzm().getPackageName());
            } else {
                zzeiVar.zza(zzijVar.zzc, this.zza.zza, this.zza.zzb, this.zzb.zzm().getPackageName());
            }
            this.zzb.zzaj();
        } catch (RemoteException e) {
            this.zzb.zzq().zze().zza("Failed to send current screen to the service", e);
        }
    }
}
