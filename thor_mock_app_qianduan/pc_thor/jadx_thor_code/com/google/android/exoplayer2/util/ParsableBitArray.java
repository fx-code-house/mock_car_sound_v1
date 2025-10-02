package com.google.android.exoplayer2.util;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Charsets;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final class ParsableBitArray {
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;
    public byte[] data;

    public ParsableBitArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    public ParsableBitArray(byte[] data) {
        this(data, data.length);
    }

    public ParsableBitArray(byte[] data, int limit) {
        this.data = data;
        this.byteLimit = limit;
    }

    public void reset(byte[] data) {
        reset(data, data.length);
    }

    public void reset(ParsableByteArray parsableByteArray) {
        reset(parsableByteArray.getData(), parsableByteArray.limit());
        setPosition(parsableByteArray.getPosition() * 8);
    }

    public void reset(byte[] data, int limit) {
        this.data = data;
        this.byteOffset = 0;
        this.bitOffset = 0;
        this.byteLimit = limit;
    }

    public int bitsLeft() {
        return ((this.byteLimit - this.byteOffset) * 8) - this.bitOffset;
    }

    public int getPosition() {
        return (this.byteOffset * 8) + this.bitOffset;
    }

    public int getBytePosition() {
        Assertions.checkState(this.bitOffset == 0);
        return this.byteOffset;
    }

    public void setPosition(int position) {
        int i = position / 8;
        this.byteOffset = i;
        this.bitOffset = position - (i * 8);
        assertValidOffset();
    }

    public void skipBit() {
        int i = this.bitOffset + 1;
        this.bitOffset = i;
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset++;
        }
        assertValidOffset();
    }

    public void skipBits(int numBits) {
        int i = numBits / 8;
        int i2 = this.byteOffset + i;
        this.byteOffset = i2;
        int i3 = this.bitOffset + (numBits - (i * 8));
        this.bitOffset = i3;
        if (i3 > 7) {
            this.byteOffset = i2 + 1;
            this.bitOffset = i3 - 8;
        }
        assertValidOffset();
    }

    public boolean readBit() {
        boolean z = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return z;
    }

    public int readBits(int numBits) {
        int i;
        if (numBits == 0) {
            return 0;
        }
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
            this.byteOffset = i4 + 1;
            i2 |= (bArr[i4] & 255) << i3;
        }
        byte[] bArr2 = this.data;
        int i5 = this.byteOffset;
        int i6 = ((-1) >>> (32 - numBits)) & (i2 | ((bArr2[i5] & 255) >> (8 - i)));
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset = i5 + 1;
        }
        assertValidOffset();
        return i6;
    }

    public long readBitsToLong(int numBits) {
        if (numBits <= 32) {
            return Util.toUnsignedLong(readBits(numBits));
        }
        return Util.toLong(readBits(numBits - 32), readBits(32));
    }

    public void readBits(byte[] buffer, int offset, int numBits) {
        int i = (numBits >> 3) + offset;
        while (offset < i) {
            byte[] bArr = this.data;
            int i2 = this.byteOffset;
            int i3 = i2 + 1;
            this.byteOffset = i3;
            byte b = bArr[i2];
            int i4 = this.bitOffset;
            byte b2 = (byte) (b << i4);
            buffer[offset] = b2;
            buffer[offset] = (byte) (((255 & bArr[i3]) >> (8 - i4)) | b2);
            offset++;
        }
        int i5 = numBits & 7;
        if (i5 == 0) {
            return;
        }
        byte b3 = (byte) (buffer[i] & (255 >> i5));
        buffer[i] = b3;
        int i6 = this.bitOffset;
        if (i6 + i5 > 8) {
            byte[] bArr2 = this.data;
            int i7 = this.byteOffset;
            this.byteOffset = i7 + 1;
            buffer[i] = (byte) (b3 | ((bArr2[i7] & 255) << i6));
            this.bitOffset = i6 - 8;
        }
        int i8 = this.bitOffset + i5;
        this.bitOffset = i8;
        byte[] bArr3 = this.data;
        int i9 = this.byteOffset;
        buffer[i] = (byte) (((byte) (((255 & bArr3[i9]) >> (8 - i8)) << (8 - i5))) | buffer[i]);
        if (i8 == 8) {
            this.bitOffset = 0;
            this.byteOffset = i9 + 1;
        }
        assertValidOffset();
    }

    public void byteAlign() {
        if (this.bitOffset == 0) {
            return;
        }
        this.bitOffset = 0;
        this.byteOffset++;
        assertValidOffset();
    }

    public void readBytes(byte[] buffer, int offset, int length) {
        Assertions.checkState(this.bitOffset == 0);
        System.arraycopy(this.data, this.byteOffset, buffer, offset, length);
        this.byteOffset += length;
        assertValidOffset();
    }

    public void skipBytes(int length) {
        Assertions.checkState(this.bitOffset == 0);
        this.byteOffset += length;
        assertValidOffset();
    }

    public String readBytesAsString(int length) {
        return readBytesAsString(length, Charsets.UTF_8);
    }

    public String readBytesAsString(int length, Charset charset) {
        byte[] bArr = new byte[length];
        readBytes(bArr, 0, length);
        return new String(bArr, charset);
    }

    public void putInt(int value, int numBits) {
        if (numBits < 32) {
            value &= (1 << numBits) - 1;
        }
        int iMin = Math.min(8 - this.bitOffset, numBits);
        int i = this.bitOffset;
        int i2 = (8 - i) - iMin;
        int i3 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK >> i) | ((1 << i2) - 1);
        byte[] bArr = this.data;
        int i4 = this.byteOffset;
        byte b = (byte) (i3 & bArr[i4]);
        bArr[i4] = b;
        int i5 = numBits - iMin;
        bArr[i4] = (byte) (b | ((value >>> i5) << i2));
        int i6 = i4 + 1;
        while (i5 > 8) {
            this.data[i6] = (byte) (value >>> (i5 - 8));
            i5 -= 8;
            i6++;
        }
        int i7 = 8 - i5;
        byte[] bArr2 = this.data;
        byte b2 = (byte) (bArr2[i6] & ((1 << i7) - 1));
        bArr2[i6] = b2;
        bArr2[i6] = (byte) (((value & ((1 << i5) - 1)) << i7) | b2);
        skipBits(numBits);
        assertValidOffset();
    }

    private void assertValidOffset() {
        int i;
        int i2 = this.byteOffset;
        Assertions.checkState(i2 >= 0 && (i2 < (i = this.byteLimit) || (i2 == i && this.bitOffset == 0)));
    }
}
