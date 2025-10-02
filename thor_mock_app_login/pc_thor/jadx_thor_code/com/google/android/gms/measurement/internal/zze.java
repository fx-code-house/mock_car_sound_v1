package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zze implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zza zzb;

    zze(zza zzaVar, long j) {
        this.zzb = zzaVar;
        this.zza = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
