package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhf implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzhb zzb;

    zzhf(zzhb zzhbVar, boolean z) {
        this.zzb = zzhbVar;
        this.zza = z;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        boolean zZzaa = this.zzb.zzy.zzaa();
        boolean zZzz = this.zzb.zzy.zzz();
        this.zzb.zzy.zza(this.zza);
        if (zZzz == this.zza) {
            this.zzb.zzy.zzq().zzw().zza("Default data collection state already set to", Boolean.valueOf(this.zza));
        }
        if (this.zzb.zzy.zzaa() == zZzaa || this.zzb.zzy.zzaa() != this.zzb.zzy.zzz()) {
            this.zzb.zzy.zzq().zzj().zza("Default data collection is different than actual status", Boolean.valueOf(this.zza), Boolean.valueOf(zZzaa));
        }
        this.zzb.zzal();
    }
}
