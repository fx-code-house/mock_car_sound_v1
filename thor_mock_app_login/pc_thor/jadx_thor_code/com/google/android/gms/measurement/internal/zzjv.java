package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjv implements Runnable {
    private final /* synthetic */ zzkl zza;
    private final /* synthetic */ Runnable zzb;

    zzjv(zzjq zzjqVar, zzkl zzklVar, Runnable runnable) {
        this.zza = zzklVar;
        this.zzb = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zzr();
        this.zza.zza(this.zzb);
        this.zza.zzo();
    }
}
