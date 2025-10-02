package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgb implements Runnable {
    private final /* synthetic */ zzn zza;
    private final /* synthetic */ zzfz zzb;

    zzgb(zzfz zzfzVar, zzn zznVar) {
        this.zzb = zzfzVar;
        this.zza = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.zzr();
        zzkl zzklVar = this.zzb.zza;
        zzn zznVar = this.zza;
        zzklVar.zzp().zzc();
        zzklVar.zzn();
        Preconditions.checkNotEmpty(zznVar.zza);
        zzklVar.zzc(zznVar);
    }
}
