package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzml;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgj implements Runnable {
    private final /* synthetic */ zzn zza;
    private final /* synthetic */ zzfz zzb;

    zzgj(zzfz zzfzVar, zzn zznVar) {
        this.zzb = zzfzVar;
        this.zza = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zza.zzr();
        zzkl zzklVar = this.zzb.zza;
        zzn zznVar = this.zza;
        if (zzml.zzb() && zzklVar.zzb().zza(zzas.zzci)) {
            zzklVar.zzp().zzc();
            zzklVar.zzn();
            Preconditions.checkNotEmpty(zznVar.zza);
            zzac zzacVarZza = zzac.zza(zznVar.zzw);
            zzac zzacVarZza2 = zzklVar.zza(zznVar.zza);
            zzklVar.zzq().zzw().zza("Setting consent, package, consent", zznVar.zza, zzacVarZza);
            zzklVar.zza(zznVar.zza, zzacVarZza);
            if (zzacVarZza.zza(zzacVarZza2)) {
                zzklVar.zza(zznVar);
            }
        }
    }
}
