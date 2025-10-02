package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhw implements Runnable {
    private final /* synthetic */ zzac zza;
    private final /* synthetic */ int zzb;
    private final /* synthetic */ long zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ zzhb zze;

    zzhw(zzhb zzhbVar, zzac zzacVar, int i, long j, boolean z) {
        this.zze = zzhbVar;
        this.zza = zzacVar;
        this.zzb = i;
        this.zzc = j;
        this.zzd = z;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zze.zza(this.zza);
        this.zze.zza(this.zza, this.zzb, this.zzc, false, this.zzd);
    }
}
