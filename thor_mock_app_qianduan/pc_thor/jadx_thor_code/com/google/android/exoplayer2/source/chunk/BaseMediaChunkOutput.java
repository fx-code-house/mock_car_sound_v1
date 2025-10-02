package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes.dex */
public final class BaseMediaChunkOutput implements ChunkExtractor.TrackOutputProvider {
    private static final String TAG = "BaseMediaChunkOutput";
    private final SampleQueue[] sampleQueues;
    private final int[] trackTypes;

    public BaseMediaChunkOutput(int[] trackTypes, SampleQueue[] sampleQueues) {
        this.trackTypes = trackTypes;
        this.sampleQueues = sampleQueues;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.TrackOutputProvider
    public TrackOutput track(int id, int type) {
        int i = 0;
        while (true) {
            int[] iArr = this.trackTypes;
            if (i < iArr.length) {
                if (type == iArr[i]) {
                    return this.sampleQueues[i];
                }
                i++;
            } else {
                Log.e(TAG, new StringBuilder(36).append("Unmatched track of type: ").append(type).toString());
                return new DummyTrackOutput();
            }
        }
    }

    public int[] getWriteIndices() {
        int[] iArr = new int[this.sampleQueues.length];
        int i = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            if (i >= sampleQueueArr.length) {
                return iArr;
            }
            iArr[i] = sampleQueueArr[i].getWriteIndex();
            i++;
        }
    }

    public void setSampleOffsetUs(long sampleOffsetUs) {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.setSampleOffsetUs(sampleOffsetUs);
        }
    }
}
