package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzip implements Runnable {
    private final /* synthetic */ zzij zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ zzii zzc;

    zzip(zzii zziiVar, zzij zzijVar, long j) {
        this.zzc = zziiVar;
        this.zza = zzijVar;
        this.zzb = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza(this.zza, false, this.zzb);
        this.zzc.zza = null;
        this.zzc.zzg().zza((zzij) null);
    }
}
