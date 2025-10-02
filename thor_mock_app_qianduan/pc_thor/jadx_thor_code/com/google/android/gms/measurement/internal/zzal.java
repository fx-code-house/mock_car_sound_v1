package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzal implements Runnable {
    private final /* synthetic */ zzgt zza;
    private final /* synthetic */ zzai zzb;

    zzal(zzai zzaiVar, zzgt zzgtVar) {
        this.zzb = zzaiVar;
        this.zza = zzgtVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zza.zzt();
        if (zzw.zza()) {
            this.zza.zzp().zza(this);
            return;
        }
        boolean zZzb = this.zzb.zzb();
        zzai.zza(this.zzb, 0L);
        if (zZzb) {
            this.zzb.zza();
        }
    }
}
