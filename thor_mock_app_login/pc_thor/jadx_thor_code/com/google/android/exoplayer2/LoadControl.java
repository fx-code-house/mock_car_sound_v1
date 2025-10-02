package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;

/* loaded from: classes.dex */
public interface LoadControl {
    Allocator getAllocator();

    long getBackBufferDurationUs();

    void onPrepared();

    void onReleased();

    void onStopped();

    void onTracksSelected(Renderer[] renderers, TrackGroupArray trackGroups, ExoTrackSelection[] trackSelections);

    boolean retainBackBufferFromKeyframe();

    boolean shouldContinueLoading(long playbackPositionUs, long bufferedDurationUs, float playbackSpeed);

    boolean shouldStartPlayback(long bufferedDurationUs, float playbackSpeed, boolean rebuffering, long targetLiveOffsetUs);
}
