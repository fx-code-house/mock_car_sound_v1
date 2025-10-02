package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class IndexSeeker implements Seeker {
    static final long MIN_TIME_BETWEEN_POINTS_US = 100000;
    private final long dataEndPosition;
    private long durationUs;
    private final LongArray positions;
    private final LongArray timesUs;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public IndexSeeker(long durationUs, long dataStartPosition, long dataEndPosition) {
        this.durationUs = durationUs;
        this.dataEndPosition = dataEndPosition;
        LongArray longArray = new LongArray();
        this.timesUs = longArray;
        LongArray longArray2 = new LongArray();
        this.positions = longArray2;
        longArray.add(0L);
        longArray2.add(dataStartPosition);
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long position) {
        return this.timesUs.get(Util.binarySearchFloor(this.positions, position, true, true));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.dataEndPosition;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, timeUs, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs.get(iBinarySearchFloor), this.positions.get(iBinarySearchFloor));
        if (seekPoint.timeUs == timeUs || iBinarySearchFloor == this.timesUs.size() - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs.get(i), this.positions.get(i)));
    }

    public void maybeAddSeekPoint(long timeUs, long position) {
        if (isTimeUsInIndex(timeUs)) {
            return;
        }
        this.timesUs.add(timeUs);
        this.positions.add(position);
    }

    public boolean isTimeUsInIndex(long timeUs) {
        LongArray longArray = this.timesUs;
        return timeUs - longArray.get(longArray.size() - 1) < MIN_TIME_BETWEEN_POINTS_US;
    }

    void setDurationUs(long durationUs) {
        this.durationUs = durationUs;
    }
}
