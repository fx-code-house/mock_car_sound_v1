package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public interface ChunkSource {
    long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters);

    void getNextChunk(long playbackPositionUs, long loadPositionUs, List<? extends MediaChunk> queue, ChunkHolder out);

    int getPreferredQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue);

    void maybeThrowError() throws IOException;

    void onChunkLoadCompleted(Chunk chunk);

    boolean onChunkLoadError(Chunk chunk, boolean cancelable, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy);

    void release();

    boolean shouldCancelLoad(long playbackPositionUs, Chunk loadingChunk, List<? extends MediaChunk> queue);
}
