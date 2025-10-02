package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjb implements Runnable {
    private final /* synthetic */ Bundle zza;
    private final /* synthetic */ zzn zzb;
    private final /* synthetic */ zzir zzc;

    zzjb(zzir zzirVar, Bundle bundle, zzn zznVar) {
        this.zzc = zzirVar;
        this.zza = bundle;
        this.zzb = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        zzei zzeiVar = this.zzc.zzb;
        if (zzeiVar == null) {
            this.zzc.zzq().zze().zza("Failed to send default event parameters to service");
            return;
        }
        try {
            zzeiVar.zza(this.zza, this.zzb);
        } catch (RemoteException e) {
            this.zzc.zzq().zze().zza("Failed to send default event parameters to service", e);
        }
    }
}
