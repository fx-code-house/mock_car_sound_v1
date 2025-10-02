package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjm implements Runnable {
    private final /* synthetic */ zzei zza;
    private final /* synthetic */ zzjl zzb;

    zzjm(zzjl zzjlVar, zzei zzeiVar) {
        this.zzb = zzjlVar;
        this.zza = zzeiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb) {
            zzjl.zza(this.zzb, false);
            if (!this.zzb.zza.zzaa()) {
                this.zzb.zza.zzq().zzv().zza("Connected to remote service");
                this.zzb.zza.zza(this.zza);
            }
        }
    }
}
