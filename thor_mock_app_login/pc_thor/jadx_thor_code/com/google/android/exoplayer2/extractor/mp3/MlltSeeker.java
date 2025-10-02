package com.google.android.exoplayer2.extractor.mp3;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class MlltSeeker implements Seeker {
    private final long durationUs;
    private final long[] referencePositions;
    private final long[] referenceTimesMs;

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public static MlltSeeker create(long firstFramePosition, MlltFrame mlltFrame, long durationUs) {
        int length = mlltFrame.bytesDeviations.length;
        int i = length + 1;
        long[] jArr = new long[i];
        long[] jArr2 = new long[i];
        jArr[0] = firstFramePosition;
        long j = 0;
        jArr2[0] = 0;
        for (int i2 = 1; i2 <= length; i2++) {
            int i3 = i2 - 1;
            firstFramePosition += mlltFrame.bytesBetweenReference + mlltFrame.bytesDeviations[i3];
            j += mlltFrame.millisecondsBetweenReference + mlltFrame.millisecondsDeviations[i3];
            jArr[i2] = firstFramePosition;
            jArr2[i2] = j;
        }
        return new MlltSeeker(jArr, jArr2, durationUs);
    }

    private MlltSeeker(long[] referencePositions, long[] referenceTimesMs, long durationUs) {
        this.referencePositions = referencePositions;
        this.referenceTimesMs = referenceTimesMs;
        this.durationUs = durationUs == C.TIME_UNSET ? C.msToUs(referenceTimesMs[referenceTimesMs.length - 1]) : durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        Pair<Long, Long> pairLinearlyInterpolate = linearlyInterpolate(C.usToMs(Util.constrainValue(timeUs, 0L, this.durationUs)), this.referenceTimesMs, this.referencePositions);
        return new SeekMap.SeekPoints(new SeekPoint(C.msToUs(((Long) pairLinearlyInterpolate.first).longValue()), ((Long) pairLinearlyInterpolate.second).longValue()));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long position) {
        return C.msToUs(((Long) linearlyInterpolate(position, this.referencePositions, this.referenceTimesMs).second).longValue());
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    private static Pair<Long, Long> linearlyInterpolate(long x, long[] xReferences, long[] yReferences) {
        int iBinarySearchFloor = Util.binarySearchFloor(xReferences, x, true, true);
        long j = xReferences[iBinarySearchFloor];
        long j2 = yReferences[iBinarySearchFloor];
        int i = iBinarySearchFloor + 1;
        if (i == xReferences.length) {
            return Pair.create(Long.valueOf(j), Long.valueOf(j2));
        }
        return Pair.create(Long.valueOf(x), Long.valueOf(((long) ((xReferences[i] == j ? 0.0d : (x - j) / (r6 - j)) * (yReferences[i] - j2))) + j2));
    }
}
