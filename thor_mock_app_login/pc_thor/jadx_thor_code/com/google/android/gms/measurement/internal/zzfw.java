package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfw implements Runnable {
    private final /* synthetic */ zzgy zza;
    private final /* synthetic */ zzfu zzb;

    zzfw(zzfu zzfuVar, zzgy zzgyVar) {
        this.zzb = zzfuVar;
        this.zza = zzgyVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zza(this.zza);
        this.zzb.zza(this.zza.zzg);
    }
}
