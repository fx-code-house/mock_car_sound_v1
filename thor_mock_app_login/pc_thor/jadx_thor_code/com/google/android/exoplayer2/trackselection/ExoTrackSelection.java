package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import java.util.List;

/* loaded from: classes.dex */
public interface ExoTrackSelection extends TrackSelection {

    public interface Factory {
        ExoTrackSelection[] createTrackSelections(Definition[] definitions, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline);
    }

    boolean blacklist(int index, long exclusionDurationMs);

    void disable();

    void enable();

    int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue);

    Format getSelectedFormat();

    int getSelectedIndex();

    int getSelectedIndexInTrackGroup();

    Object getSelectionData();

    int getSelectionReason();

    boolean isBlacklisted(int index, long nowMs);

    default void onDiscontinuity() {
    }

    default void onPlayWhenReadyChanged(boolean playWhenReady) {
    }

    void onPlaybackSpeed(float playbackSpeed);

    default void onRebuffer() {
    }

    default boolean shouldCancelChunkLoad(long playbackPositionUs, Chunk loadingChunk, List<? extends MediaChunk> queue) {
        return false;
    }

    void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> queue, MediaChunkIterator[] mediaChunkIterators);

    public static final class Definition {
        public final TrackGroup group;
        public final int[] tracks;
        public final int type;

        public Definition(TrackGroup group, int... tracks) {
            this(group, tracks, 0);
        }

        public Definition(TrackGroup group, int[] tracks, int type) {
            this.group = group;
            this.tracks = tracks;
            this.type = type;
        }
    }
}
