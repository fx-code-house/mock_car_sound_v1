package com.google.android.exoplayer2;

import com.google.android.exoplayer2.MediaItem;

/* loaded from: classes.dex */
public interface LivePlaybackSpeedControl {
    float getAdjustedPlaybackSpeed(long liveOffsetUs, long bufferedDurationUs);

    long getTargetLiveOffsetUs();

    void notifyRebuffer();

    void setLiveConfiguration(MediaItem.LiveConfiguration liveConfiguration);

    void setTargetLiveOffsetOverrideUs(long liveOffsetUs);
}
