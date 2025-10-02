package com.google.android.exoplayer2.util;

import android.os.Looper;

/* loaded from: classes.dex */
public interface HandlerWrapper {

    public interface Message {
        HandlerWrapper getTarget();

        void sendToTarget();
    }

    Looper getLooper();

    boolean hasMessages(int what);

    Message obtainMessage(int what);

    Message obtainMessage(int what, int arg1, int arg2);

    Message obtainMessage(int what, int arg1, int arg2, Object obj);

    Message obtainMessage(int what, Object obj);

    boolean post(Runnable runnable);

    boolean postAtFrontOfQueue(Runnable runnable);

    boolean postDelayed(Runnable runnable, long delayMs);

    void removeCallbacksAndMessages(Object token);

    void removeMessages(int what);

    boolean sendEmptyMessage(int what);

    boolean sendEmptyMessageAtTime(int what, long uptimeMs);

    boolean sendEmptyMessageDelayed(int what, int delayMs);

    boolean sendMessageAtFrontOfQueue(Message message);
}
