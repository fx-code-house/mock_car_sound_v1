package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class XingSeeker implements Seeker {
    private static final String TAG = "XingSeeker";
    private final long dataEndPosition;
    private final long dataSize;
    private final long dataStartPosition;
    private final long durationUs;
    private final long[] tableOfContents;
    private final int xingFrameSize;

    public static XingSeeker create(long inputLength, long position, MpegAudioUtil.Header mpegAudioHeader, ParsableByteArray frame) {
        int unsignedIntToInt;
        int i = mpegAudioHeader.samplesPerFrame;
        int i2 = mpegAudioHeader.sampleRate;
        int i3 = frame.readInt();
        if ((i3 & 1) != 1 || (unsignedIntToInt = frame.readUnsignedIntToInt()) == 0) {
            return null;
        }
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(unsignedIntToInt, i * 1000000, i2);
        if ((i3 & 6) != 6) {
            return new XingSeeker(position, mpegAudioHeader.frameSize, jScaleLargeTimestamp);
        }
        long unsignedInt = frame.readUnsignedInt();
        long[] jArr = new long[100];
        for (int i4 = 0; i4 < 100; i4++) {
            jArr[i4] = frame.readUnsignedByte();
        }
        if (inputLength != -1) {
            long j = position + unsignedInt;
            if (inputLength != j) {
                Log.w(TAG, new StringBuilder(67).append("XING data size mismatch: ").append(inputLength).append(", ").append(j).toString());
            }
        }
        return new XingSeeker(position, mpegAudioHeader.frameSize, jScaleLargeTimestamp, unsignedInt, jArr);
    }

    private XingSeeker(long dataStartPosition, int xingFrameSize, long durationUs) {
        this(dataStartPosition, xingFrameSize, durationUs, -1L, null);
    }

    private XingSeeker(long dataStartPosition, int xingFrameSize, long durationUs, long dataSize, long[] tableOfContents) {
        this.dataStartPosition = dataStartPosition;
        this.xingFrameSize = xingFrameSize;
        this.durationUs = durationUs;
        this.tableOfContents = tableOfContents;
        this.dataSize = dataSize;
        this.dataEndPosition = dataSize != -1 ? dataStartPosition + dataSize : -1L;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.tableOfContents != null;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        if (!isSeekable()) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.dataStartPosition + this.xingFrameSize));
        }
        long jConstrainValue = Util.constrainValue(timeUs, 0L, this.durationUs);
        double d = (jConstrainValue * 100.0d) / this.durationUs;
        double d2 = 0.0d;
        if (d > 0.0d) {
            if (d >= 100.0d) {
                d2 = 256.0d;
            } else {
                int i = (int) d;
                double d3 = ((long[]) Assertions.checkStateNotNull(this.tableOfContents))[i];
                d2 = d3 + ((d - i) * ((i == 99 ? 256.0d : r3[i + 1]) - d3));
            }
        }
        return new SeekMap.SeekPoints(new SeekPoint(jConstrainValue, this.dataStartPosition + Util.constrainValue(Math.round((d2 / 256.0d) * this.dataSize), this.xingFrameSize, this.dataSize - 1)));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long position) {
        long j = position - this.dataStartPosition;
        if (!isSeekable() || j <= this.xingFrameSize) {
            return 0L;
        }
        long[] jArr = (long[]) Assertions.checkStateNotNull(this.tableOfContents);
        double d = (j * 256.0d) / this.dataSize;
        int iBinarySearchFloor = Util.binarySearchFloor(jArr, (long) d, true, true);
        long timeUsForTableIndex = getTimeUsForTableIndex(iBinarySearchFloor);
        long j2 = jArr[iBinarySearchFloor];
        int i = iBinarySearchFloor + 1;
        long timeUsForTableIndex2 = getTimeUsForTableIndex(i);
        return timeUsForTableIndex + Math.round((j2 == (iBinarySearchFloor == 99 ? 256L : jArr[i]) ? 0.0d : (d - j2) / (r0 - j2)) * (timeUsForTableIndex2 - timeUsForTableIndex));
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.dataEndPosition;
    }

    private long getTimeUsForTableIndex(int tableIndex) {
        return (this.durationUs * tableIndex) / 100;
    }
}
