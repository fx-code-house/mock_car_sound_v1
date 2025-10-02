package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjw implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzjx zzb;

    zzjw(zzjx zzjxVar, long j) {
        this.zzb = zzjxVar;
        this.zza = j;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zzb(this.zza);
    }
}
