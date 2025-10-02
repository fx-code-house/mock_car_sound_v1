package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import java.util.List;

/* loaded from: classes.dex */
public final class FixedTrackSelection extends BaseTrackSelection {
    private final Object data;
    private final int reason;

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int getSelectedIndex() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> queue, MediaChunkIterator[] mediaChunkIterators) {
    }

    public FixedTrackSelection(TrackGroup group, int track) {
        this(group, track, 0);
    }

    public FixedTrackSelection(TrackGroup group, int track, int type) {
        this(group, track, type, 0, null);
    }

    public FixedTrackSelection(TrackGroup group, int track, int type, int reason, Object data) {
        super(group, new int[]{track}, type);
        this.reason = reason;
        this.data = data;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int getSelectionReason() {
        return this.reason;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public Object getSelectionData() {
        return this.data;
    }
}
