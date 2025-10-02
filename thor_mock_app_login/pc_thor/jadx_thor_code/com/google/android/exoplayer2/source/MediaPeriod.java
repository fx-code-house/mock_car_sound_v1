package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public interface MediaPeriod extends SequenceableLoader {

    public interface Callback extends SequenceableLoader.Callback<MediaPeriod> {
        void onPrepared(MediaPeriod mediaPeriod);
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    boolean continueLoading(long positionUs);

    void discardBuffer(long positionUs, boolean toKeyframe);

    long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters);

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    long getBufferedPositionUs();

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    long getNextLoadPositionUs();

    TrackGroupArray getTrackGroups();

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    boolean isLoading();

    void maybeThrowPrepareError() throws IOException;

    void prepare(Callback callback, long positionUs);

    long readDiscontinuity();

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    void reevaluateBuffer(long positionUs);

    long seekToUs(long positionUs);

    long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs);

    default List<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
        return Collections.emptyList();
    }
}
