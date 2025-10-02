package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* loaded from: classes.dex */
final class AudioBecomingNoisyManager {
    private final Context context;
    private final AudioBecomingNoisyReceiver receiver;
    private boolean receiverRegistered;

    public interface EventListener {
        void onAudioBecomingNoisy();
    }

    public AudioBecomingNoisyManager(Context context, Handler eventHandler, EventListener listener) {
        this.context = context.getApplicationContext();
        this.receiver = new AudioBecomingNoisyReceiver(eventHandler, listener);
    }

    public void setEnabled(boolean enabled) {
        if (enabled && !this.receiverRegistered) {
            this.context.registerReceiver(this.receiver, new IntentFilter("android.media.AUDIO_BECOMING_NOISY"));
            this.receiverRegistered = true;
        } else {
            if (enabled || !this.receiverRegistered) {
                return;
            }
            this.context.unregisterReceiver(this.receiver);
            this.receiverRegistered = false;
        }
    }

    private final class AudioBecomingNoisyReceiver extends BroadcastReceiver implements Runnable {
        private final Handler eventHandler;
        private final EventListener listener;

        public AudioBecomingNoisyReceiver(Handler eventHandler, EventListener listener) {
            this.eventHandler = eventHandler;
            this.listener = listener;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                this.eventHandler.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AudioBecomingNoisyManager.this.receiverRegistered) {
                this.listener.onAudioBecomingNoisy();
            }
        }
    }
}
