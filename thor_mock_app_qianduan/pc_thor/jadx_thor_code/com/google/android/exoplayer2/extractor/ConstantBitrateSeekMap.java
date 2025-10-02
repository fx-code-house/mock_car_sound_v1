package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public class ConstantBitrateSeekMap implements SeekMap {
    private final int bitrate;
    private final long dataSize;
    private final long durationUs;
    private final long firstFrameBytePosition;
    private final int frameSize;
    private final long inputLength;

    public ConstantBitrateSeekMap(long inputLength, long firstFrameBytePosition, int bitrate, int frameSize) {
        this.inputLength = inputLength;
        this.firstFrameBytePosition = firstFrameBytePosition;
        this.frameSize = frameSize == -1 ? 1 : frameSize;
        this.bitrate = bitrate;
        if (inputLength == -1) {
            this.dataSize = -1L;
            this.durationUs = C.TIME_UNSET;
        } else {
            this.dataSize = inputLength - firstFrameBytePosition;
            this.durationUs = getTimeUsAtPosition(inputLength, firstFrameBytePosition, bitrate);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.dataSize != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        if (this.dataSize == -1) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.firstFrameBytePosition));
        }
        long framePositionForTimeUs = getFramePositionForTimeUs(timeUs);
        long timeUsAtPosition = getTimeUsAtPosition(framePositionForTimeUs);
        SeekPoint seekPoint = new SeekPoint(timeUsAtPosition, framePositionForTimeUs);
        if (timeUsAtPosition < timeUs) {
            int i = this.frameSize;
            if (i + framePositionForTimeUs < this.inputLength) {
                long j = framePositionForTimeUs + i;
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(getTimeUsAtPosition(j), j));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    public long getTimeUsAtPosition(long position) {
        return getTimeUsAtPosition(position, this.firstFrameBytePosition, this.bitrate);
    }

    private static long getTimeUsAtPosition(long position, long firstFrameBytePosition, int bitrate) {
        return ((Math.max(0L, position - firstFrameBytePosition) * 8) * 1000000) / bitrate;
    }

    private long getFramePositionForTimeUs(long timeUs) {
        long j = (timeUs * this.bitrate) / 8000000;
        int i = this.frameSize;
        return this.firstFrameBytePosition + Util.constrainValue((j / i) * i, 0L, this.dataSize - i);
    }
}
