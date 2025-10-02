package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgi implements Runnable {
    private final /* synthetic */ zzaq zza;
    private final /* synthetic */ zzn zzb;
    private final /* synthetic */ zzfz zzc;

    zzgi(zzfz zzfzVar, zzaq zzaqVar, zzn zznVar) {
        this.zzc = zzfzVar;
        this.zza = zzaqVar;
        this.zzb = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        zzaq zzaqVarZzb = this.zzc.zzb(this.zza, this.zzb);
        this.zzc.zza.zzr();
        this.zzc.zza.zza(zzaqVarZzb, this.zzb);
    }
}
