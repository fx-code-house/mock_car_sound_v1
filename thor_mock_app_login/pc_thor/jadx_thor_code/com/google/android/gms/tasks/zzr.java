package com.google.android.gms.tasks;

import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* loaded from: classes2.dex */
final class zzr<TResult> {
    private final Object zza = new Object();
    private Queue<zzq<TResult>> zzb;
    private boolean zzc;

    zzr() {
    }

    public final void zza(zzq<TResult> zzqVar) {
        synchronized (this.zza) {
            if (this.zzb == null) {
                this.zzb = new ArrayDeque();
            }
            this.zzb.add(zzqVar);
        }
    }

    public final void zzb(Task<TResult> task) {
        zzq<TResult> zzqVarPoll;
        synchronized (this.zza) {
            if (this.zzb != null && !this.zzc) {
                this.zzc = true;
                while (true) {
                    synchronized (this.zza) {
                        zzqVarPoll = this.zzb.poll();
                        if (zzqVarPoll == null) {
                            this.zzc = false;
                            return;
                        }
                    }
                    zzqVarPoll.zzd(task);
                }
            }
        }
    }
}
