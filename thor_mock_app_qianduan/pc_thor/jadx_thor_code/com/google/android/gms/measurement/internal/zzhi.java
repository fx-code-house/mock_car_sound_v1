package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhi implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Object zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzhb zze;

    zzhi(zzhb zzhbVar, String str, String str2, Object obj, long j) {
        this.zze = zzhbVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = obj;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zze.zza(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
