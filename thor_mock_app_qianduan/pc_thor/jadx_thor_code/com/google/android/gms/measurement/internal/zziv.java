package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zziv implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzn zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzir zzd;

    zziv(zzir zzirVar, AtomicReference atomicReference, zzn zznVar, boolean z) {
        this.zzd = zzirVar;
        this.zza = atomicReference;
        this.zzb = zznVar;
        this.zzc = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzei zzeiVar;
        synchronized (this.zza) {
            try {
                try {
                    zzeiVar = this.zzd.zzb;
                } catch (RemoteException e) {
                    this.zzd.zzq().zze().zza("Failed to get all user properties; remote exception", e);
                }
                if (zzeiVar == null) {
                    this.zzd.zzq().zze().zza("Failed to get all user properties; not connected to service");
                    return;
                }
                this.zza.set(zzeiVar.zza(this.zzb, this.zzc));
                this.zzd.zzaj();
                this.zza.notify();
            } finally {
                this.zza.notify();
            }
        }
    }
}
