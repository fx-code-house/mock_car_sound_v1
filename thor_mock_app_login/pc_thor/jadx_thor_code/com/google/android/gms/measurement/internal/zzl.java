package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.internal.AppMeasurementDynamiteService;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@18.0.0 */
/* loaded from: classes2.dex */
final class zzl implements Runnable {
    private final /* synthetic */ AppMeasurementDynamiteService.zza zza;
    private final /* synthetic */ AppMeasurementDynamiteService zzb;

    zzl(AppMeasurementDynamiteService appMeasurementDynamiteService, AppMeasurementDynamiteService.zza zzaVar) {
        this.zzb = appMeasurementDynamiteService;
        this.zza = zzaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.zzg().zza(this.zza);
    }
}
