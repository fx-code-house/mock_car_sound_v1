package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class FixedSampleSizeRechunker {
    private static final int MAX_SAMPLE_SIZE = 8192;

    public static final class Results {
        public final long duration;
        public final int[] flags;
        public final int maximumSize;
        public final long[] offsets;
        public final int[] sizes;
        public final long[] timestamps;

        private Results(long[] offsets, int[] sizes, int maximumSize, long[] timestamps, int[] flags, long duration) {
            this.offsets = offsets;
            this.sizes = sizes;
            this.maximumSize = maximumSize;
            this.timestamps = timestamps;
            this.flags = flags;
            this.duration = duration;
        }
    }

    public static Results rechunk(int fixedSampleSize, long[] chunkOffsets, int[] chunkSampleCounts, long timestampDeltaInTimeUnits) {
        int i = 8192 / fixedSampleSize;
        int iCeilDivide = 0;
        for (int i2 : chunkSampleCounts) {
            iCeilDivide += Util.ceilDivide(i2, i);
        }
        long[] jArr = new long[iCeilDivide];
        int[] iArr = new int[iCeilDivide];
        long[] jArr2 = new long[iCeilDivide];
        int[] iArr2 = new int[iCeilDivide];
        int i3 = 0;
        int i4 = 0;
        int iMax = 0;
        for (int i5 = 0; i5 < chunkSampleCounts.length; i5++) {
            int i6 = chunkSampleCounts[i5];
            long j = chunkOffsets[i5];
            while (i6 > 0) {
                int iMin = Math.min(i, i6);
                jArr[i4] = j;
                int i7 = fixedSampleSize * iMin;
                iArr[i4] = i7;
                iMax = Math.max(iMax, i7);
                jArr2[i4] = i3 * timestampDeltaInTimeUnits;
                iArr2[i4] = 1;
                j += iArr[i4];
                i3 += iMin;
                i6 -= iMin;
                i4++;
            }
        }
        return new Results(jArr, iArr, iMax, jArr2, iArr2, timestampDeltaInTimeUnits * i3);
    }

    private FixedSampleSizeRechunker() {
    }
}
