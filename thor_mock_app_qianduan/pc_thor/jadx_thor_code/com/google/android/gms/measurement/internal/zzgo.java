package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgo implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzfz zze;

    zzgo(zzfz zzfzVar, String str, String str2, String str3, long j) {
        this.zze = zzfzVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zza == null) {
            this.zze.zza.zzu().zzu().zza(this.zzb, (zzij) null);
        } else {
            this.zze.zza.zzu().zzu().zza(this.zzb, new zzij(this.zzc, this.zza, this.zzd));
        }
    }
}
