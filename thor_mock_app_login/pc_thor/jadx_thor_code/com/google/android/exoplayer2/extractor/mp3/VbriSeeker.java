package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class VbriSeeker implements Seeker {
    private static final String TAG = "VbriSeeker";
    private final long dataEndPosition;
    private final long durationUs;
    private final long[] positions;
    private final long[] timesUs;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public static VbriSeeker create(long inputLength, long position, MpegAudioUtil.Header mpegAudioHeader, ParsableByteArray frame) {
        int unsignedByte;
        frame.skipBytes(10);
        int i = frame.readInt();
        if (i <= 0) {
            return null;
        }
        int i2 = mpegAudioHeader.sampleRate;
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(i, (i2 >= 32000 ? 1152 : 576) * 1000000, i2);
        int unsignedShort = frame.readUnsignedShort();
        int unsignedShort2 = frame.readUnsignedShort();
        int unsignedShort3 = frame.readUnsignedShort();
        frame.skipBytes(2);
        long j = position + mpegAudioHeader.frameSize;
        long[] jArr = new long[unsignedShort];
        long[] jArr2 = new long[unsignedShort];
        int i3 = 0;
        long j2 = position;
        while (i3 < unsignedShort) {
            int i4 = unsignedShort2;
            long j3 = j;
            jArr[i3] = (i3 * jScaleLargeTimestamp) / unsignedShort;
            jArr2[i3] = Math.max(j2, j3);
            if (unsignedShort3 == 1) {
                unsignedByte = frame.readUnsignedByte();
            } else if (unsignedShort3 == 2) {
                unsignedByte = frame.readUnsignedShort();
            } else if (unsignedShort3 == 3) {
                unsignedByte = frame.readUnsignedInt24();
            } else {
                if (unsignedShort3 != 4) {
                    return null;
                }
                unsignedByte = frame.readUnsignedIntToInt();
            }
            j2 += unsignedByte * i4;
            i3++;
            j = j3;
            unsignedShort2 = i4;
        }
        if (inputLength != -1 && inputLength != j2) {
            Log.w(TAG, new StringBuilder(67).append("VBRI data size mismatch: ").append(inputLength).append(", ").append(j2).toString());
        }
        return new VbriSeeker(jArr, jArr2, jScaleLargeTimestamp, j2);
    }

    private VbriSeeker(long[] timesUs, long[] positions, long durationUs, long dataEndPosition) {
        this.timesUs = timesUs;
        this.positions = positions;
        this.durationUs = durationUs;
        this.dataEndPosition = dataEndPosition;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, timeUs, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[iBinarySearchFloor], this.positions[iBinarySearchFloor]);
        if (seekPoint.timeUs >= timeUs || iBinarySearchFloor == this.timesUs.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i], this.positions[i]));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long position) {
        return this.timesUs[Util.binarySearchFloor(this.positions, position, true, true)];
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.dataEndPosition;
    }
}
