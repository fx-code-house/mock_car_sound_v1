package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;

/* loaded from: classes.dex */
final class VarintReader {
    private static final int STATE_BEGIN_READING = 0;
    private static final int STATE_READ_CONTENTS = 1;
    private static final long[] VARINT_LENGTH_MASKS = {128, 64, 32, 16, 8, 4, 2, 1};
    private int length;
    private final byte[] scratch = new byte[8];
    private int state;

    public void reset() {
        this.state = 0;
        this.length = 0;
    }

    public long readUnsignedVarint(ExtractorInput input, boolean allowEndOfInput, boolean removeLengthMask, int maximumAllowedLength) throws IOException {
        if (this.state == 0) {
            if (!input.readFully(this.scratch, 0, 1, allowEndOfInput)) {
                return -1L;
            }
            int unsignedVarintLength = parseUnsignedVarintLength(this.scratch[0] & 255);
            this.length = unsignedVarintLength;
            if (unsignedVarintLength == -1) {
                throw new IllegalStateException("No valid varint length mask found");
            }
            this.state = 1;
        }
        int i = this.length;
        if (i > maximumAllowedLength) {
            this.state = 0;
            return -2L;
        }
        if (i != 1) {
            input.readFully(this.scratch, 1, i - 1);
        }
        this.state = 0;
        return assembleVarint(this.scratch, this.length, removeLengthMask);
    }

    public int getLastLength() {
        return this.length;
    }

    public static int parseUnsignedVarintLength(int firstByte) {
        int i = 0;
        while (true) {
            long[] jArr = VARINT_LENGTH_MASKS;
            if (i >= jArr.length) {
                return -1;
            }
            if ((jArr[i] & firstByte) != 0) {
                return i + 1;
            }
            i++;
        }
    }

    public static long assembleVarint(byte[] varintBytes, int varintLength, boolean removeLengthMask) {
        long j = varintBytes[0] & 255;
        if (removeLengthMask) {
            j &= ~VARINT_LENGTH_MASKS[varintLength - 1];
        }
        for (int i = 1; i < varintLength; i++) {
            j = (j << 8) | (varintBytes[i] & 255);
        }
        return j;
    }
}
