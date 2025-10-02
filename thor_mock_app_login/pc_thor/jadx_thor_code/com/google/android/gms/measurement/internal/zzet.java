package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzet implements Runnable {
    private final /* synthetic */ int zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Object zzc;
    private final /* synthetic */ Object zzd;
    private final /* synthetic */ Object zze;
    private final /* synthetic */ zzeq zzf;

    zzet(zzeq zzeqVar, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzf = zzeqVar;
        this.zza = i;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfc zzfcVarZzb = this.zzf.zzy.zzb();
        if (!zzfcVarZzb.zzaa()) {
            this.zzf.zza(6, "Persisted config not initialized. Not logging error/warn");
            return;
        }
        if (this.zzf.zza == 0) {
            if (this.zzf.zzs().zze()) {
                this.zzf.zza = 'C';
            } else {
                this.zzf.zza = 'c';
            }
        }
        if (this.zzf.zzb < 0) {
            this.zzf.zzb = 33025L;
        }
        char cCharAt = "01VDIWEA?".charAt(this.zza);
        char c = this.zzf.zza;
        long j = this.zzf.zzb;
        String strZza = zzeq.zza(true, this.zzb, this.zzc, this.zzd, this.zze);
        String string = new StringBuilder(String.valueOf(strZza).length() + 24).append("2").append(cCharAt).append(c).append(j).append(":").append(strZza).toString();
        if (string.length() > 1024) {
            string = this.zzb.substring(0, 1024);
        }
        zzfcVarZzb.zzb.zza(string, 1L);
    }
}
