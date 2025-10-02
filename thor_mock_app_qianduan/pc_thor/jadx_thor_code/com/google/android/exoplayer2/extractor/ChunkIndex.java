package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class ChunkIndex implements SeekMap {
    private final long durationUs;
    public final long[] durationsUs;
    public final int length;
    public final long[] offsets;
    public final int[] sizes;
    public final long[] timesUs;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public ChunkIndex(int[] sizes, long[] offsets, long[] durationsUs, long[] timesUs) {
        this.sizes = sizes;
        this.offsets = offsets;
        this.durationsUs = durationsUs;
        this.timesUs = timesUs;
        int length = sizes.length;
        this.length = length;
        if (length > 0) {
            this.durationUs = durationsUs[length - 1] + timesUs[length - 1];
        } else {
            this.durationUs = 0L;
        }
    }

    public int getChunkIndex(long timeUs) {
        return Util.binarySearchFloor(this.timesUs, timeUs, true, true);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        int chunkIndex = getChunkIndex(timeUs);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[chunkIndex], this.offsets[chunkIndex]);
        if (seekPoint.timeUs >= timeUs || chunkIndex == this.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = chunkIndex + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i], this.offsets[i]));
    }

    public String toString() {
        int i = this.length;
        String string = Arrays.toString(this.sizes);
        String string2 = Arrays.toString(this.offsets);
        String string3 = Arrays.toString(this.timesUs);
        String string4 = Arrays.toString(this.durationsUs);
        return new StringBuilder(String.valueOf(string).length() + 71 + String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length()).append("ChunkIndex(length=").append(i).append(", sizes=").append(string).append(", offsets=").append(string2).append(", timeUs=").append(string3).append(", durationsUs=").append(string4).append(")").toString();
    }
}
