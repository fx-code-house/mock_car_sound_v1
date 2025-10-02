package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@18.0.0 */
/* loaded from: classes2.dex */
final class zzi implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzw zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ AppMeasurementDynamiteService zze;

    zzi(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzw zzwVar, String str, String str2, boolean z) {
        this.zze = appMeasurementDynamiteService;
        this.zza = zzwVar;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = z;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zze.zza.zzv().zza(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
