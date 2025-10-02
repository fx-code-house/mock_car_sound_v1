package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhe implements Runnable {
    private final /* synthetic */ zzhb zza;

    zzhe(zzhb zzhbVar) {
        this.zza = zzhbVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zza.zzb.zza();
    }
}
