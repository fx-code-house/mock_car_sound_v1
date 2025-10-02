package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgn implements Runnable {
    private final /* synthetic */ zzku zza;
    private final /* synthetic */ zzn zzb;
    private final /* synthetic */ zzfz zzc;

    zzgn(zzfz zzfzVar, zzku zzkuVar, zzn zznVar) {
        this.zzc = zzfzVar;
        this.zza = zzkuVar;
        this.zzb = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzc.zza.zzr();
        if (this.zza.zza() == null) {
            this.zzc.zza.zzb(this.zza, this.zzb);
        } else {
            this.zzc.zza.zza(this.zza, this.zzb);
        }
    }
}
