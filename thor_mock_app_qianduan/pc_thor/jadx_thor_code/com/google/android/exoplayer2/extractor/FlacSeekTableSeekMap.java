package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class FlacSeekTableSeekMap implements SeekMap {
    private final long firstFrameOffset;
    private final FlacStreamMetadata flacStreamMetadata;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public FlacSeekTableSeekMap(FlacStreamMetadata flacStreamMetadata, long firstFrameOffset) {
        this.flacStreamMetadata = flacStreamMetadata;
        this.firstFrameOffset = firstFrameOffset;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.flacStreamMetadata.getDurationUs();
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        Assertions.checkStateNotNull(this.flacStreamMetadata.seekTable);
        long[] jArr = this.flacStreamMetadata.seekTable.pointSampleNumbers;
        long[] jArr2 = this.flacStreamMetadata.seekTable.pointOffsets;
        int iBinarySearchFloor = Util.binarySearchFloor(jArr, this.flacStreamMetadata.getSampleNumber(timeUs), true, false);
        SeekPoint seekPoint = getSeekPoint(iBinarySearchFloor == -1 ? 0L : jArr[iBinarySearchFloor], iBinarySearchFloor != -1 ? jArr2[iBinarySearchFloor] : 0L);
        if (seekPoint.timeUs == timeUs || iBinarySearchFloor == jArr.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, getSeekPoint(jArr[i], jArr2[i]));
    }

    private SeekPoint getSeekPoint(long sampleNumber, long offsetFromFirstFrame) {
        return new SeekPoint((sampleNumber * 1000000) / this.flacStreamMetadata.sampleRate, this.firstFrameOffset + offsetFromFirstFrame);
    }
}
