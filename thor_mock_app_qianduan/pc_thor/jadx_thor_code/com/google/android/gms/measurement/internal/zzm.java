package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@18.0.0 */
/* loaded from: classes2.dex */
final class zzm implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzw zza;
    private final /* synthetic */ AppMeasurementDynamiteService zzb;

    zzm(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzw zzwVar) {
        this.zzb = appMeasurementDynamiteService;
        this.zza = zzwVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zza.zzh().zza(this.zza, this.zzb.zza.zzz());
    }
}
