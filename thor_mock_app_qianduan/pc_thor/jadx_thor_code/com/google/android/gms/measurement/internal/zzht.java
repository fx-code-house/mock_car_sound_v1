package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzht implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzhb zzb;

    zzht(zzhb zzhbVar, AtomicReference atomicReference) {
        this.zzb = zzhbVar;
        this.zza = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zza) {
            try {
                this.zza.set(Long.valueOf(this.zzb.zzs().zza(this.zzb.zzf().zzaa(), zzas.zzal)));
            } finally {
                this.zza.notify();
            }
        }
    }
}
