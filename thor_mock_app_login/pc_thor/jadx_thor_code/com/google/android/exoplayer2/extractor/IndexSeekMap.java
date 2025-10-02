package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class IndexSeekMap implements SeekMap {
    private final long durationUs;
    private final boolean isSeekable;
    private final long[] positions;
    private final long[] timesUs;

    public IndexSeekMap(long[] positions, long[] timesUs, long durationUs) {
        Assertions.checkArgument(positions.length == timesUs.length);
        int length = timesUs.length;
        boolean z = length > 0;
        this.isSeekable = z;
        if (z && timesUs[0] > 0) {
            int i = length + 1;
            long[] jArr = new long[i];
            this.positions = jArr;
            long[] jArr2 = new long[i];
            this.timesUs = jArr2;
            System.arraycopy(positions, 0, jArr, 1, length);
            System.arraycopy(timesUs, 0, jArr2, 1, length);
        } else {
            this.positions = positions;
            this.timesUs = timesUs;
        }
        this.durationUs = durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.isSeekable;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        if (!this.isSeekable) {
            return new SeekMap.SeekPoints(SeekPoint.START);
        }
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, timeUs, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[iBinarySearchFloor], this.positions[iBinarySearchFloor]);
        if (seekPoint.timeUs == timeUs || iBinarySearchFloor == this.timesUs.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i], this.positions[i]));
    }
}
