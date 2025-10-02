package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzis implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzku zzb;
    private final /* synthetic */ zzn zzc;
    private final /* synthetic */ zzir zzd;

    zzis(zzir zzirVar, boolean z, zzku zzkuVar, zzn zznVar) {
        this.zzd = zzirVar;
        this.zza = z;
        this.zzb = zzkuVar;
        this.zzc = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzei zzeiVar = this.zzd.zzb;
        if (zzeiVar == null) {
            this.zzd.zzq().zze().zza("Discarding data. Failed to set user property");
        } else {
            this.zzd.zza(zzeiVar, this.zza ? null : this.zzb, this.zzc);
            this.zzd.zzaj();
        }
    }
}
