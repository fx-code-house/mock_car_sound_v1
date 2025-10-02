package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjd implements Runnable {
    private final /* synthetic */ zzaq zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzw zzc;
    private final /* synthetic */ zzir zzd;

    zzjd(zzir zzirVar, zzaq zzaqVar, String str, com.google.android.gms.internal.measurement.zzw zzwVar) {
        this.zzd = zzirVar;
        this.zza = zzaqVar;
        this.zzb = str;
        this.zzc = zzwVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        try {
            zzei zzeiVar = this.zzd.zzb;
            if (zzeiVar == null) {
                this.zzd.zzq().zze().zza("Discarding data. Failed to send event to service to bundle");
                return;
            }
            byte[] bArrZza = zzeiVar.zza(this.zza, this.zzb);
            this.zzd.zzaj();
            this.zzd.zzo().zza(this.zzc, bArrZza);
        } catch (RemoteException e) {
            this.zzd.zzq().zze().zza("Failed to send event to the service to bundle", e);
        } finally {
            this.zzd.zzo().zza(this.zzc, (byte[]) null);
        }
    }
}
