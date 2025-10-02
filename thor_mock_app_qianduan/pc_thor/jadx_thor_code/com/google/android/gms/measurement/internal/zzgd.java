package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgd implements Runnable {
    private final /* synthetic */ zzz zza;
    private final /* synthetic */ zzfz zzb;

    zzgd(zzfz zzfzVar, zzz zzzVar) {
        this.zzb = zzfzVar;
        this.zza = zzzVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zza.zzr();
        if (this.zza.zzc.zza() == null) {
            this.zzb.zza.zzb(this.zza);
        } else {
            this.zzb.zza.zza(this.zza);
        }
    }
}
