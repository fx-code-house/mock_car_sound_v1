package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzit implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzn zzd;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzw zze;
    private final /* synthetic */ zzir zzf;

    zzit(zzir zzirVar, String str, String str2, boolean z, zzn zznVar, com.google.android.gms.internal.measurement.zzw zzwVar) {
        this.zzf = zzirVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = z;
        this.zzd = zznVar;
        this.zze = zzwVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        Bundle bundle = new Bundle();
        try {
            zzei zzeiVar = this.zzf.zzb;
            if (zzeiVar == null) {
                this.zzf.zzq().zze().zza("Failed to get user properties; not connected to service", this.zza, this.zzb);
                return;
            }
            Bundle bundleZza = zzkv.zza(zzeiVar.zza(this.zza, this.zzb, this.zzc, this.zzd));
            this.zzf.zzaj();
            this.zzf.zzo().zza(this.zze, bundleZza);
        } catch (RemoteException e) {
            this.zzf.zzq().zze().zza("Failed to get user properties; remote exception", this.zza, e);
        } finally {
            this.zzf.zzo().zza(this.zze, bundle);
        }
    }
}
