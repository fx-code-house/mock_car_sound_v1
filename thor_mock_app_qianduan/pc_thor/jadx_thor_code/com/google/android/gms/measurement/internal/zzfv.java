package com.google.android.gms.measurement.internal;

import android.os.Process;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfv extends Thread {
    private final Object zza;
    private final BlockingQueue<zzfs<?>> zzb;
    private boolean zzc = false;
    private final /* synthetic */ zzfr zzd;

    public zzfv(zzfr zzfrVar, String str, BlockingQueue<zzfs<?>> blockingQueue) {
        this.zzd = zzfrVar;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(blockingQueue);
        this.zza = new Object();
        this.zzb = blockingQueue;
        setName(str);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() throws IllegalStateException, InterruptedException {
        boolean z = false;
        while (!z) {
            try {
                this.zzd.zzh.acquire();
                z = true;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzfs<?> zzfsVarPoll = this.zzb.poll();
                if (zzfsVarPoll != null) {
                    Process.setThreadPriority(zzfsVarPoll.zza ? threadPriority : 10);
                    zzfsVarPoll.run();
                } else {
                    synchronized (this.zza) {
                        if (this.zzb.peek() == null && !this.zzd.zzi) {
                            try {
                                this.zza.wait(30000L);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zzd.zzg) {
                        if (this.zzb.peek() == null) {
                            break;
                        }
                    }
                }
            }
            if (this.zzd.zzs().zza(zzas.zzbq)) {
                zzb();
            }
        } finally {
            zzb();
        }
    }

    private final void zzb() {
        synchronized (this.zzd.zzg) {
            if (!this.zzc) {
                this.zzd.zzh.release();
                this.zzd.zzg.notifyAll();
                if (this == this.zzd.zza) {
                    zzfr.zza(this.zzd, null);
                } else if (this == this.zzd.zzb) {
                    zzfr.zzb(this.zzd, null);
                } else {
                    this.zzd.zzq().zze().zza("Current scheduler thread is neither worker nor network");
                }
                this.zzc = true;
            }
        }
    }

    public final void zza() {
        synchronized (this.zza) {
            this.zza.notifyAll();
        }
    }

    private final void zza(InterruptedException interruptedException) throws IllegalStateException {
        this.zzd.zzq().zzh().zza(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }
}
