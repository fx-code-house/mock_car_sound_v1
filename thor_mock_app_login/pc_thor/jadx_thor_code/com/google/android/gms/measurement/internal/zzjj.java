package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjj implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzn zzc;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzw zzd;
    private final /* synthetic */ zzir zze;

    zzjj(zzir zzirVar, String str, String str2, zzn zznVar, com.google.android.gms.internal.measurement.zzw zzwVar) {
        this.zze = zzirVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zznVar;
        this.zzd = zzwVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        ArrayList<Bundle> arrayList = new ArrayList<>();
        try {
            zzei zzeiVar = this.zze.zzb;
            if (zzeiVar == null) {
                this.zze.zzq().zze().zza("Failed to get conditional properties; not connected to service", this.zza, this.zzb);
                return;
            }
            ArrayList<Bundle> arrayListZzb = zzkv.zzb(zzeiVar.zza(this.zza, this.zzb, this.zzc));
            this.zze.zzaj();
            this.zze.zzo().zza(this.zzd, arrayListZzb);
        } catch (RemoteException e) {
            this.zze.zzq().zze().zza("Failed to get conditional properties; remote exception", this.zza, this.zzb, e);
        } finally {
            this.zze.zzo().zza(this.zzd, arrayList);
        }
    }
}
