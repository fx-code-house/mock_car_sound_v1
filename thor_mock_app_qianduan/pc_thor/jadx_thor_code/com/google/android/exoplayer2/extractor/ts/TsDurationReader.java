package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
final class TsDurationReader {
    private boolean isDurationRead;
    private boolean isFirstPcrValueRead;
    private boolean isLastPcrValueRead;
    private final int timestampSearchBytes;
    private final TimestampAdjuster pcrTimestampAdjuster = new TimestampAdjuster(0);
    private long firstPcrValue = C.TIME_UNSET;
    private long lastPcrValue = C.TIME_UNSET;
    private long durationUs = C.TIME_UNSET;
    private final ParsableByteArray packetBuffer = new ParsableByteArray();

    TsDurationReader(int timestampSearchBytes) {
        this.timestampSearchBytes = timestampSearchBytes;
    }

    public boolean isDurationReadFinished() {
        return this.isDurationRead;
    }

    public int readDuration(ExtractorInput input, PositionHolder seekPositionHolder, int pcrPid) throws IOException {
        if (pcrPid <= 0) {
            return finishReadDuration(input);
        }
        if (!this.isLastPcrValueRead) {
            return readLastPcrValue(input, seekPositionHolder, pcrPid);
        }
        if (this.lastPcrValue == C.TIME_UNSET) {
            return finishReadDuration(input);
        }
        if (!this.isFirstPcrValueRead) {
            return readFirstPcrValue(input, seekPositionHolder, pcrPid);
        }
        long j = this.firstPcrValue;
        if (j == C.TIME_UNSET) {
            return finishReadDuration(input);
        }
        this.durationUs = this.pcrTimestampAdjuster.adjustTsTimestamp(this.lastPcrValue) - this.pcrTimestampAdjuster.adjustTsTimestamp(j);
        return finishReadDuration(input);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public TimestampAdjuster getPcrTimestampAdjuster() {
        return this.pcrTimestampAdjuster;
    }

    private int finishReadDuration(ExtractorInput input) {
        this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        this.isDurationRead = true;
        input.resetPeekPosition();
        return 0;
    }

    private int readFirstPcrValue(ExtractorInput input, PositionHolder seekPositionHolder, int pcrPid) throws IOException {
        int iMin = (int) Math.min(this.timestampSearchBytes, input.getLength());
        long j = 0;
        if (input.getPosition() != j) {
            seekPositionHolder.position = j;
            return 1;
        }
        this.packetBuffer.reset(iMin);
        input.resetPeekPosition();
        input.peekFully(this.packetBuffer.getData(), 0, iMin);
        this.firstPcrValue = readFirstPcrValueFromBuffer(this.packetBuffer, pcrPid);
        this.isFirstPcrValueRead = true;
        return 0;
    }

    private long readFirstPcrValueFromBuffer(ParsableByteArray packetBuffer, int pcrPid) {
        int iLimit = packetBuffer.limit();
        for (int position = packetBuffer.getPosition(); position < iLimit; position++) {
            if (packetBuffer.getData()[position] == 71) {
                long pcrFromPacket = TsUtil.readPcrFromPacket(packetBuffer, position, pcrPid);
                if (pcrFromPacket != C.TIME_UNSET) {
                    return pcrFromPacket;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int readLastPcrValue(ExtractorInput input, PositionHolder seekPositionHolder, int pcrPid) throws IOException {
        long length = input.getLength();
        int iMin = (int) Math.min(this.timestampSearchBytes, length);
        long j = length - iMin;
        if (input.getPosition() != j) {
            seekPositionHolder.position = j;
            return 1;
        }
        this.packetBuffer.reset(iMin);
        input.resetPeekPosition();
        input.peekFully(this.packetBuffer.getData(), 0, iMin);
        this.lastPcrValue = readLastPcrValueFromBuffer(this.packetBuffer, pcrPid);
        this.isLastPcrValueRead = true;
        return 0;
    }

    private long readLastPcrValueFromBuffer(ParsableByteArray packetBuffer, int pcrPid) {
        int position = packetBuffer.getPosition();
        int iLimit = packetBuffer.limit();
        for (int i = iLimit - 188; i >= position; i--) {
            if (TsUtil.isStartOfTsPacket(packetBuffer.getData(), position, iLimit, i)) {
                long pcrFromPacket = TsUtil.readPcrFromPacket(packetBuffer, i, pcrPid);
                if (pcrFromPacket != C.TIME_UNSET) {
                    return pcrFromPacket;
                }
            }
        }
        return C.TIME_UNSET;
    }
}
