package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class WavSeekMap implements SeekMap {
    private final long blockCount;
    private final long durationUs;
    private final long firstBlockPosition;
    private final int framesPerBlock;
    private final WavHeader wavHeader;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public WavSeekMap(WavHeader wavHeader, int framesPerBlock, long dataStartPosition, long dataEndPosition) {
        this.wavHeader = wavHeader;
        this.framesPerBlock = framesPerBlock;
        this.firstBlockPosition = dataStartPosition;
        long j = (dataEndPosition - dataStartPosition) / wavHeader.blockSize;
        this.blockCount = j;
        this.durationUs = blockIndexToTimeUs(j);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        long jConstrainValue = Util.constrainValue((this.wavHeader.frameRateHz * timeUs) / (this.framesPerBlock * 1000000), 0L, this.blockCount - 1);
        long j = this.firstBlockPosition + (this.wavHeader.blockSize * jConstrainValue);
        long jBlockIndexToTimeUs = blockIndexToTimeUs(jConstrainValue);
        SeekPoint seekPoint = new SeekPoint(jBlockIndexToTimeUs, j);
        if (jBlockIndexToTimeUs >= timeUs || jConstrainValue == this.blockCount - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long j2 = jConstrainValue + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(blockIndexToTimeUs(j2), this.firstBlockPosition + (this.wavHeader.blockSize * j2)));
    }

    private long blockIndexToTimeUs(long blockIndex) {
        return Util.scaleLargeTimestamp(blockIndex * this.framesPerBlock, 1000000L, this.wavHeader.frameRateHz);
    }
}
