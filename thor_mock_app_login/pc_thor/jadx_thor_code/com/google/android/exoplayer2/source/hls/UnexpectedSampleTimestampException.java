package com.google.android.exoplayer2.source.hls;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import java.io.IOException;

/* loaded from: classes.dex */
final class UnexpectedSampleTimestampException extends IOException {
    public final long lastAcceptedSampleTimeUs;
    public final MediaChunk mediaChunk;
    public final long rejectedSampleTimeUs;

    /* JADX WARN: Illegal instructions before constructor call */
    public UnexpectedSampleTimestampException(MediaChunk mediaChunk, long lastAcceptedSampleTimeUs, long rejectedSampleTimeUs) {
        long jUsToMs = C.usToMs(rejectedSampleTimeUs);
        long j = mediaChunk.startTimeUs;
        super(new StringBuilder(103).append("Unexpected sample timestamp: ").append(jUsToMs).append(" in chunk [").append(j).append(", ").append(mediaChunk.endTimeUs).append("]").toString());
        this.mediaChunk = mediaChunk;
        this.lastAcceptedSampleTimeUs = lastAcceptedSampleTimeUs;
        this.rejectedSampleTimeUs = rejectedSampleTimeUs;
    }
}
