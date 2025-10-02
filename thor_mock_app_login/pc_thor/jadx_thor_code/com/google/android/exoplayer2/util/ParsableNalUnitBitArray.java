package com.google.android.exoplayer2.util;

/* loaded from: classes.dex */
public final class ParsableNalUnitBitArray {
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;
    private byte[] data;

    public ParsableNalUnitBitArray(byte[] data, int offset, int limit) {
        reset(data, offset, limit);
    }

    public void reset(byte[] data, int offset, int limit) {
        this.data = data;
        this.byteOffset = offset;
        this.byteLimit = limit;
        this.bitOffset = 0;
        assertValidOffset();
    }

    public void skipBit() {
        int i = this.bitOffset + 1;
        this.bitOffset = i;
        if (i == 8) {
            this.bitOffset = 0;
            int i2 = this.byteOffset;
            this.byteOffset = i2 + (shouldSkipByte(i2 + 1) ? 2 : 1);
        }
        assertValidOffset();
    }

    public void skipBits(int numBits) {
        int i = this.byteOffset;
        int i2 = numBits / 8;
        int i3 = i + i2;
        this.byteOffset = i3;
        int i4 = this.bitOffset + (numBits - (i2 * 8));
        this.bitOffset = i4;
        if (i4 > 7) {
            this.byteOffset = i3 + 1;
            this.bitOffset = i4 - 8;
        }
        while (true) {
            i++;
            if (i <= this.byteOffset) {
                if (shouldSkipByte(i)) {
                    this.byteOffset++;
                    i += 2;
                }
            } else {
                assertValidOffset();
                return;
            }
        }
    }

    public boolean canReadBits(int numBits) {
        int i = this.byteOffset;
        int i2 = numBits / 8;
        int i3 = i + i2;
        int i4 = (this.bitOffset + numBits) - (i2 * 8);
        if (i4 > 7) {
            i3++;
            i4 -= 8;
        }
        while (true) {
            i++;
            if (i > i3 || i3 >= this.byteLimit) {
                break;
            }
            if (shouldSkipByte(i)) {
                i3++;
                i += 2;
            }
        }
        int i5 = this.byteLimit;
        if (i3 >= i5) {
            return i3 == i5 && i4 == 0;
        }
        return true;
    }

    public boolean readBit() {
        boolean z = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return z;
    }

    public int readBits(int numBits) {
        int i;
        this.bitOffset += numBits;
        int i2 = 0;
        while (true) {
            i = this.bitOffset;
            if (i <= 8) {
                break;
            }
            int i3 = i - 8;
            this.bitOffset = i3;
            byte[] bArr = this.data;
            int i4 = this.byteOffset;
            i2 |= (bArr[i4] & 255) << i3;
            if (!shouldSkipByte(i4 + 1)) {
                i = 1;
            }
            this.byteOffset = i4 + i;
        }
        byte[] bArr2 = this.data;
        int i5 = this.byteOffset;
        int i6 = ((-1) >>> (32 - numBits)) & (i2 | ((bArr2[i5] & 255) >> (8 - i)));
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset = i5 + (shouldSkipByte(i5 + 1) ? 2 : 1);
        }
        assertValidOffset();
        return i6;
    }

    public boolean canReadExpGolombCodedNum() {
        int i = this.byteOffset;
        int i2 = this.bitOffset;
        int i3 = 0;
        while (this.byteOffset < this.byteLimit && !readBit()) {
            i3++;
        }
        boolean z = this.byteOffset == this.byteLimit;
        this.byteOffset = i;
        this.bitOffset = i2;
        return !z && canReadBits((i3 * 2) + 1);
    }

    public int readUnsignedExpGolombCodedInt() {
        return readExpGolombCodeNum();
    }

    public int readSignedExpGolombCodedInt() {
        int expGolombCodeNum = readExpGolombCodeNum();
        return (expGolombCodeNum % 2 == 0 ? -1 : 1) * ((expGolombCodeNum + 1) / 2);
    }

    private int readExpGolombCodeNum() {
        int i = 0;
        while (!readBit()) {
            i++;
        }
        return ((1 << i) - 1) + (i > 0 ? readBits(i) : 0);
    }

    private boolean shouldSkipByte(int offset) {
        if (2 <= offset && offset < this.byteLimit) {
            byte[] bArr = this.data;
            if (bArr[offset] == 3 && bArr[offset - 2] == 0 && bArr[offset - 1] == 0) {
                return true;
            }
        }
        return false;
    }

    private void assertValidOffset() {
        int i;
        int i2 = this.byteOffset;
        Assertions.checkState(i2 >= 0 && (i2 < (i = this.byteLimit) || (i2 == i && this.bitOffset == 0)));
    }
}
