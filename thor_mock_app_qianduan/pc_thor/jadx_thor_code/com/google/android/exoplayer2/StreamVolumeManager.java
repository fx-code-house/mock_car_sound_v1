package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class StreamVolumeManager {
    private static final String TAG = "StreamVolumeManager";
    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static final int VOLUME_FLAGS = 1;
    private final Context applicationContext;
    private final AudioManager audioManager;
    private final Handler eventHandler;
    private final Listener listener;
    private boolean muted;
    private VolumeChangeReceiver receiver;
    private int streamType;
    private int volume;

    public interface Listener {
        void onStreamTypeChanged(int streamType);

        void onStreamVolumeChanged(int streamVolume, boolean streamMuted);
    }

    public StreamVolumeManager(Context context, Handler eventHandler, Listener listener) {
        Context applicationContext = context.getApplicationContext();
        this.applicationContext = applicationContext;
        this.eventHandler = eventHandler;
        this.listener = listener;
        AudioManager audioManager = (AudioManager) Assertions.checkStateNotNull((AudioManager) applicationContext.getSystemService("audio"));
        this.audioManager = audioManager;
        this.streamType = 3;
        this.volume = getVolumeFromManager(audioManager, 3);
        this.muted = getMutedFromManager(audioManager, this.streamType);
        VolumeChangeReceiver volumeChangeReceiver = new VolumeChangeReceiver();
        try {
            applicationContext.registerReceiver(volumeChangeReceiver, new IntentFilter(VOLUME_CHANGED_ACTION));
            this.receiver = volumeChangeReceiver;
        } catch (RuntimeException e) {
            Log.w(TAG, "Error registering stream volume receiver", e);
        }
    }

    public void setStreamType(int streamType) {
        if (this.streamType == streamType) {
            return;
        }
        this.streamType = streamType;
        updateVolumeAndNotifyIfChanged();
        this.listener.onStreamTypeChanged(streamType);
    }

    public int getMinVolume() {
        if (Util.SDK_INT >= 28) {
            return this.audioManager.getStreamMinVolume(this.streamType);
        }
        return 0;
    }

    public int getMaxVolume() {
        return this.audioManager.getStreamMaxVolume(this.streamType);
    }

    public int getVolume() {
        return this.volume;
    }

    public boolean isMuted() {
        return this.muted;
    }

    public void setVolume(int volume) {
        if (volume < getMinVolume() || volume > getMaxVolume()) {
            return;
        }
        this.audioManager.setStreamVolume(this.streamType, volume, 1);
        updateVolumeAndNotifyIfChanged();
    }

    public void increaseVolume() {
        if (this.volume >= getMaxVolume()) {
            return;
        }
        this.audioManager.adjustStreamVolume(this.streamType, 1, 1);
        updateVolumeAndNotifyIfChanged();
    }

    public void decreaseVolume() {
        if (this.volume <= getMinVolume()) {
            return;
        }
        this.audioManager.adjustStreamVolume(this.streamType, -1, 1);
        updateVolumeAndNotifyIfChanged();
    }

    public void setMuted(boolean muted) {
        if (Util.SDK_INT >= 23) {
            this.audioManager.adjustStreamVolume(this.streamType, muted ? -100 : 100, 1);
        } else {
            this.audioManager.setStreamMute(this.streamType, muted);
        }
        updateVolumeAndNotifyIfChanged();
    }

    public void release() {
        VolumeChangeReceiver volumeChangeReceiver = this.receiver;
        if (volumeChangeReceiver != null) {
            try {
                this.applicationContext.unregisterReceiver(volumeChangeReceiver);
            } catch (RuntimeException e) {
                Log.w(TAG, "Error unregistering stream volume receiver", e);
            }
            this.receiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolumeAndNotifyIfChanged() {
        int volumeFromManager = getVolumeFromManager(this.audioManager, this.streamType);
        boolean mutedFromManager = getMutedFromManager(this.audioManager, this.streamType);
        if (this.volume == volumeFromManager && this.muted == mutedFromManager) {
            return;
        }
        this.volume = volumeFromManager;
        this.muted = mutedFromManager;
        this.listener.onStreamVolumeChanged(volumeFromManager, mutedFromManager);
    }

    private static int getVolumeFromManager(AudioManager audioManager, int streamType) {
        try {
            return audioManager.getStreamVolume(streamType);
        } catch (RuntimeException e) {
            Log.w(TAG, new StringBuilder(60).append("Could not retrieve stream volume for stream type ").append(streamType).toString(), e);
            return audioManager.getStreamMaxVolume(streamType);
        }
    }

    private static boolean getMutedFromManager(AudioManager audioManager, int streamType) {
        if (Util.SDK_INT >= 23) {
            return audioManager.isStreamMute(streamType);
        }
        return getVolumeFromManager(audioManager, streamType) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class VolumeChangeReceiver extends BroadcastReceiver {
        private VolumeChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Handler handler = StreamVolumeManager.this.eventHandler;
            final StreamVolumeManager streamVolumeManager = StreamVolumeManager.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.StreamVolumeManager$VolumeChangeReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    streamVolumeManager.updateVolumeAndNotifyIfChanged();
                }
            });
        }
    }
}
