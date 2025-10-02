package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public interface ChunkExtractor {

    public interface Factory {
        ChunkExtractor createProgressiveMediaExtractor(int primaryTrackType, Format representationFormat, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, TrackOutput playerEmsgTrackOutput);
    }

    public interface TrackOutputProvider {
        TrackOutput track(int id, int type);
    }

    ChunkIndex getChunkIndex();

    Format[] getSampleFormats();

    void init(TrackOutputProvider trackOutputProvider, long startTimeUs, long endTimeUs);

    boolean read(ExtractorInput input) throws IOException;

    void release();
}
