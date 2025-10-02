package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
final class PsDurationReader {
    private static final int TIMESTAMP_SEARCH_BYTES = 20000;
    private boolean isDurationRead;
    private boolean isFirstScrValueRead;
    private boolean isLastScrValueRead;
    private final TimestampAdjuster scrTimestampAdjuster = new TimestampAdjuster(0);
    private long firstScrValue = C.TIME_UNSET;
    private long lastScrValue = C.TIME_UNSET;
    private long durationUs = C.TIME_UNSET;
    private final ParsableByteArray packetBuffer = new ParsableByteArray();

    PsDurationReader() {
    }

    public boolean isDurationReadFinished() {
        return this.isDurationRead;
    }

    public TimestampAdjuster getScrTimestampAdjuster() {
        return this.scrTimestampAdjuster;
    }

    public int readDuration(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException {
        if (!this.isLastScrValueRead) {
            return readLastScrValue(input, seekPositionHolder);
        }
        if (this.lastScrValue == C.TIME_UNSET) {
            return finishReadDuration(input);
        }
        if (!this.isFirstScrValueRead) {
            return readFirstScrValue(input, seekPositionHolder);
        }
        long j = this.firstScrValue;
        if (j == C.TIME_UNSET) {
            return finishReadDuration(input);
        }
        this.durationUs = this.scrTimestampAdjuster.adjustTsTimestamp(this.lastScrValue) - this.scrTimestampAdjuster.adjustTsTimestamp(j);
        return finishReadDuration(input);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public static long readScrValueFromPack(ParsableByteArray packetBuffer) {
        int position = packetBuffer.getPosition();
        if (packetBuffer.bytesLeft() < 9) {
            return C.TIME_UNSET;
        }
        byte[] bArr = new byte[9];
        packetBuffer.readBytes(bArr, 0, 9);
        packetBuffer.setPosition(position);
        return !checkMarkerBits(bArr) ? C.TIME_UNSET : readScrValueFromPackHeader(bArr);
    }

    private int finishReadDuration(ExtractorInput input) {
        this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        this.isDurationRead = true;
        input.resetPeekPosition();
        return 0;
    }

    private int readFirstScrValue(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException {
        int iMin = (int) Math.min(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, input.getLength());
        long j = 0;
        if (input.getPosition() != j) {
            seekPositionHolder.position = j;
            return 1;
        }
        this.packetBuffer.reset(iMin);
        input.resetPeekPosition();
        input.peekFully(this.packetBuffer.getData(), 0, iMin);
        this.firstScrValue = readFirstScrValueFromBuffer(this.packetBuffer);
        this.isFirstScrValueRead = true;
        return 0;
    }

    private long readFirstScrValueFromBuffer(ParsableByteArray packetBuffer) {
        int iLimit = packetBuffer.limit();
        for (int position = packetBuffer.getPosition(); position < iLimit - 3; position++) {
            if (peekIntAtPosition(packetBuffer.getData(), position) == 442) {
                packetBuffer.setPosition(position + 4);
                long scrValueFromPack = readScrValueFromPack(packetBuffer);
                if (scrValueFromPack != C.TIME_UNSET) {
                    return scrValueFromPack;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int readLastScrValue(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException {
        long length = input.getLength();
        int iMin = (int) Math.min(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, length);
        long j = length - iMin;
        if (input.getPosition() != j) {
            seekPositionHolder.position = j;
            return 1;
        }
        this.packetBuffer.reset(iMin);
        input.resetPeekPosition();
        input.peekFully(this.packetBuffer.getData(), 0, iMin);
        this.lastScrValue = readLastScrValueFromBuffer(this.packetBuffer);
        this.isLastScrValueRead = true;
        return 0;
    }

    private long readLastScrValueFromBuffer(ParsableByteArray packetBuffer) {
        int position = packetBuffer.getPosition();
        for (int iLimit = packetBuffer.limit() - 4; iLimit >= position; iLimit--) {
            if (peekIntAtPosition(packetBuffer.getData(), iLimit) == 442) {
                packetBuffer.setPosition(iLimit + 4);
                long scrValueFromPack = readScrValueFromPack(packetBuffer);
                if (scrValueFromPack != C.TIME_UNSET) {
                    return scrValueFromPack;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int peekIntAtPosition(byte[] data, int position) {
        return (data[position + 3] & 255) | ((data[position] & 255) << 24) | ((data[position + 1] & 255) << 16) | ((data[position + 2] & 255) << 8);
    }

    private static boolean checkMarkerBits(byte[] scrBytes) {
        return (scrBytes[0] & 196) == 68 && (scrBytes[2] & 4) == 4 && (scrBytes[4] & 4) == 4 && (scrBytes[5] & 1) == 1 && (scrBytes[8] & 3) == 3;
    }

    private static long readScrValueFromPackHeader(byte[] scrBytes) {
        byte b = scrBytes[0];
        long j = (((b & 56) >> 3) << 30) | ((b & 3) << 28) | ((scrBytes[1] & 255) << 20);
        byte b2 = scrBytes[2];
        return j | (((b2 & 248) >> 3) << 15) | ((b2 & 3) << 13) | ((scrBytes[3] & 255) << 5) | ((scrBytes[4] & 248) >> 3);
    }
}
