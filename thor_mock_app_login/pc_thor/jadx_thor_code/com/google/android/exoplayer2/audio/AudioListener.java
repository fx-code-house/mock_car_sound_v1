package com.google.android.exoplayer2.audio;

@Deprecated
/* loaded from: classes.dex */
public interface AudioListener {
    default void onAudioAttributesChanged(AudioAttributes audioAttributes) {
    }

    default void onAudioSessionIdChanged(int audioSessionId) {
    }

    default void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
    }

    default void onVolumeChanged(float volume) {
    }
}
