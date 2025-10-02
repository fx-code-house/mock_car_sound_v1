package com.google.android.gms.libs.punchclock.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class TracingHandler extends Handler {
    private static zza zzbi;

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public interface zza {
    }

    public TracingHandler() {
    }

    public TracingHandler(Handler.Callback callback) {
        super(callback);
    }

    public TracingHandler(Looper looper) {
        super(looper);
    }

    public TracingHandler(Looper looper, Handler.Callback callback) {
        super(looper, callback);
    }

    @Override // android.os.Handler
    public boolean sendMessageAtTime(Message message, long j) {
        return super.sendMessageAtTime(message, j);
    }

    public boolean sendMessageAtFrontOfQueueTraced(Message message) {
        return sendMessageAtFrontOfQueue(message);
    }

    public boolean postAtFrontOfQueueTraced(Runnable runnable) {
        return postAtFrontOfQueue(runnable);
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        dispatchMessageTraced(message);
    }

    protected void dispatchMessageTraced(Message message) {
        super.dispatchMessage(message);
    }
}
