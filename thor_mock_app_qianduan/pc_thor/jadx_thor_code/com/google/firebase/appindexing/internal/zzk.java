package com.google.firebase.appindexing.internal;

import android.os.Handler;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.libs.punchclock.threads.TracingHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzk implements OnCompleteListener<Void>, Executor {
    private final Handler handler;
    private final GoogleApi<?> zzfa;
    private final Queue<zzj> zzff = new ArrayDeque();
    private int zzfg = 0;

    public zzk(GoogleApi<?> googleApi) {
        this.zzfa = googleApi;
        this.handler = new TracingHandler(googleApi.getLooper());
    }

    public final Task<Void> zzb(zzy zzyVar) {
        boolean zIsEmpty;
        zzj zzjVar = new zzj(this, zzyVar);
        Task<Void> task = zzjVar.getTask();
        task.addOnCompleteListener(this, this);
        synchronized (this.zzff) {
            zIsEmpty = this.zzff.isEmpty();
            this.zzff.add(zzjVar);
        }
        if (zIsEmpty) {
            zzjVar.execute();
        }
        return task;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(Task<Void> task) {
        zzj zzjVarPeek;
        synchronized (this.zzff) {
            if (this.zzfg == 2) {
                zzjVarPeek = this.zzff.peek();
                Preconditions.checkState(zzjVarPeek != null);
            } else {
                zzjVarPeek = null;
            }
            this.zzfg = 0;
        }
        if (zzjVarPeek != null) {
            zzjVarPeek.execute();
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.handler.post(runnable);
    }
}
