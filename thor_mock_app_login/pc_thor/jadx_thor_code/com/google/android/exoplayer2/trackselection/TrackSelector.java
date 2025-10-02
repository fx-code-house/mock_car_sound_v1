package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public abstract class TrackSelector {
    private BandwidthMeter bandwidthMeter;
    private InvalidationListener listener;

    public interface InvalidationListener {
        void onTrackSelectionsInvalidated();
    }

    public abstract void onSelectionActivated(Object info);

    public abstract TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray trackGroups, MediaSource.MediaPeriodId periodId, Timeline timeline) throws ExoPlaybackException;

    public final void init(InvalidationListener listener, BandwidthMeter bandwidthMeter) {
        this.listener = listener;
        this.bandwidthMeter = bandwidthMeter;
    }

    protected final void invalidate() {
        InvalidationListener invalidationListener = this.listener;
        if (invalidationListener != null) {
            invalidationListener.onTrackSelectionsInvalidated();
        }
    }

    protected final BandwidthMeter getBandwidthMeter() {
        return (BandwidthMeter) Assertions.checkNotNull(this.bandwidthMeter);
    }
}
