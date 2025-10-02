package com.google.android.exoplayer2.util;

import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import okio.Utf8;

/* loaded from: classes.dex */
public final class ParsableByteArray {
    private byte[] data;
    private int limit;
    private int position;

    public ParsableByteArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    public ParsableByteArray(int limit) {
        this.data = new byte[limit];
        this.limit = limit;
    }

    public ParsableByteArray(byte[] data) {
        this.data = data;
        this.limit = data.length;
    }

    public ParsableByteArray(byte[] data, int limit) {
        this.data = data;
        this.limit = limit;
    }

    public void reset(int limit) {
        reset(capacity() < limit ? new byte[limit] : this.data, limit);
    }

    public void reset(byte[] data) {
        reset(data, data.length);
    }

    public void reset(byte[] data, int limit) {
        this.data = data;
        this.limit = limit;
        this.position = 0;
    }

    public void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > capacity()) {
            this.data = Arrays.copyOf(this.data, requiredCapacity);
        }
    }

    public int bytesLeft() {
        return this.limit - this.position;
    }

    public int limit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        Assertions.checkArgument(limit >= 0 && limit <= this.data.length);
        this.limit = limit;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        Assertions.checkArgument(position >= 0 && position <= this.limit);
        this.position = position;
    }

    public byte[] getData() {
        return this.data;
    }

    public int capacity() {
        return this.data.length;
    }

    public void skipBytes(int bytes) {
        setPosition(this.position + bytes);
    }

    public void readBytes(ParsableBitArray bitArray, int length) {
        readBytes(bitArray.data, 0, length);
        bitArray.setPosition(0);
    }

    public void readBytes(byte[] buffer, int offset, int length) {
        System.arraycopy(this.data, this.position, buffer, offset, length);
        this.position += length;
    }

    public void readBytes(ByteBuffer buffer, int length) {
        buffer.put(this.data, this.position, length);
        this.position += length;
    }

    public int peekUnsignedByte() {
        return this.data[this.position] & 255;
    }

    public char peekChar() {
        byte[] bArr = this.data;
        int i = this.position;
        return (char) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public int readUnsignedByte() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & 255;
    }

    public int readUnsignedShort() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = (bArr[i] & 255) << 8;
        this.position = i2 + 1;
        return (bArr[i2] & 255) | i3;
    }

    public int readLittleEndianUnsignedShort() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        this.position = i2 + 1;
        return ((bArr[i2] & 255) << 8) | i3;
    }

    public short readShort() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = (bArr[i] & 255) << 8;
        this.position = i2 + 1;
        return (short) ((bArr[i2] & 255) | i3);
    }

    public short readLittleEndianShort() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        this.position = i2 + 1;
        return (short) (((bArr[i2] & 255) << 8) | i3);
    }

    public int readUnsignedInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i] & 255) << 16) | ((bArr[i2] & 255) << 8);
        this.position = i3 + 1;
        return (bArr[i3] & 255) | i4;
    }

    public int readInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = (((bArr[i] & 255) << 24) >> 8) | ((bArr[i2] & 255) << 8);
        this.position = i3 + 1;
        return (bArr[i3] & 255) | i4;
    }

    public int readLittleEndianInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        this.position = i3 + 1;
        return ((bArr[i3] & 255) << 16) | i4;
    }

    public int readLittleEndianUnsignedInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        this.position = i3 + 1;
        return ((bArr[i3] & 255) << 16) | i4;
    }

    public long readUnsignedInt() {
        byte[] bArr = this.data;
        long j = (bArr[r1] & 255) << 24;
        int i = this.position + 1 + 1 + 1;
        long j2 = j | ((bArr[r2] & 255) << 16) | ((bArr[r1] & 255) << 8);
        this.position = i + 1;
        return j2 | (bArr[i] & 255);
    }

    public long readLittleEndianUnsignedInt() {
        byte[] bArr = this.data;
        long j = bArr[r1] & 255;
        int i = this.position + 1 + 1 + 1;
        long j2 = j | ((bArr[r2] & 255) << 8) | ((bArr[r1] & 255) << 16);
        this.position = i + 1;
        return j2 | ((bArr[i] & 255) << 24);
    }

    public int readInt() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i] & 255) << 24) | ((bArr[i2] & 255) << 16);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 8);
        this.position = i5 + 1;
        return (bArr[i5] & 255) | i6;
    }

    public int readLittleEndianInt() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 16);
        this.position = i5 + 1;
        return ((bArr[i5] & 255) << 24) | i6;
    }

    public long readLong() {
        byte[] bArr = this.data;
        long j = (bArr[r1] & 255) << 56;
        int i = this.position + 1 + 1 + 1;
        long j2 = j | ((bArr[r2] & 255) << 48) | ((bArr[r1] & 255) << 40);
        long j3 = j2 | ((bArr[i] & 255) << 32);
        long j4 = j3 | ((bArr[r3] & 255) << 24);
        long j5 = j4 | ((bArr[r4] & 255) << 16);
        long j6 = j5 | ((bArr[r3] & 255) << 8);
        this.position = i + 1 + 1 + 1 + 1 + 1;
        return j6 | (bArr[r4] & 255);
    }

    public long readLittleEndianLong() {
        byte[] bArr = this.data;
        long j = bArr[r1] & 255;
        int i = this.position + 1 + 1 + 1;
        long j2 = j | ((bArr[r2] & 255) << 8) | ((bArr[r1] & 255) << 16);
        long j3 = j2 | ((bArr[i] & 255) << 24);
        long j4 = j3 | ((bArr[r3] & 255) << 32);
        long j5 = j4 | ((bArr[r4] & 255) << 40);
        long j6 = j5 | ((bArr[r3] & 255) << 48);
        this.position = i + 1 + 1 + 1 + 1 + 1;
        return j6 | ((bArr[r4] & 255) << 56);
    }

    public int readUnsignedFixedPoint1616() {
        byte[] bArr = this.data;
        int i = this.position;
        int i2 = i + 1;
        int i3 = (bArr[i2] & 255) | ((bArr[i] & 255) << 8);
        this.position = i2 + 1 + 2;
        return i3;
    }

    public int readSynchSafeInt() {
        return (readUnsignedByte() << 21) | (readUnsignedByte() << 14) | (readUnsignedByte() << 7) | readUnsignedByte();
    }

    public int readUnsignedIntToInt() {
        int i = readInt();
        if (i >= 0) {
            return i;
        }
        throw new IllegalStateException(new StringBuilder(29).append("Top bit not zero: ").append(i).toString());
    }

    public int readLittleEndianUnsignedIntToInt() {
        int littleEndianInt = readLittleEndianInt();
        if (littleEndianInt >= 0) {
            return littleEndianInt;
        }
        throw new IllegalStateException(new StringBuilder(29).append("Top bit not zero: ").append(littleEndianInt).toString());
    }

    public long readUnsignedLongToLong() {
        long j = readLong();
        if (j >= 0) {
            return j;
        }
        throw new IllegalStateException(new StringBuilder(38).append("Top bit not zero: ").append(j).toString());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public String readString(int length) {
        return readString(length, Charsets.UTF_8);
    }

    public String readString(int length, Charset charset) {
        String str = new String(this.data, this.position, length, charset);
        this.position += length;
        return str;
    }

    public String readNullTerminatedString(int length) {
        if (length == 0) {
            return "";
        }
        int i = this.position;
        int i2 = (i + length) - 1;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(this.data, i, (i2 >= this.limit || this.data[i2] != 0) ? length : length - 1);
        this.position += length;
        return strFromUtf8Bytes;
    }

    public String readNullTerminatedString() {
        return readDelimiterTerminatedString((char) 0);
    }

    public String readDelimiterTerminatedString(char delimiter) {
        if (bytesLeft() == 0) {
            return null;
        }
        int i = this.position;
        while (i < this.limit && this.data[i] != delimiter) {
            i++;
        }
        byte[] bArr = this.data;
        int i2 = this.position;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(bArr, i2, i - i2);
        this.position = i;
        if (i < this.limit) {
            this.position = i + 1;
        }
        return strFromUtf8Bytes;
    }

    public String readLine() {
        if (bytesLeft() == 0) {
            return null;
        }
        int i = this.position;
        while (i < this.limit && !Util.isLinebreak(this.data[i])) {
            i++;
        }
        int i2 = this.position;
        if (i - i2 >= 3) {
            byte[] bArr = this.data;
            if (bArr[i2] == -17 && bArr[i2 + 1] == -69 && bArr[i2 + 2] == -65) {
                this.position = i2 + 3;
            }
        }
        byte[] bArr2 = this.data;
        int i3 = this.position;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(bArr2, i3, i - i3);
        this.position = i;
        int i4 = this.limit;
        if (i == i4) {
            return strFromUtf8Bytes;
        }
        byte[] bArr3 = this.data;
        if (bArr3[i] == 13) {
            int i5 = i + 1;
            this.position = i5;
            if (i5 == i4) {
                return strFromUtf8Bytes;
            }
        }
        int i6 = this.position;
        if (bArr3[i6] == 10) {
            this.position = i6 + 1;
        }
        return strFromUtf8Bytes;
    }

    public long readUtf8EncodedLong() {
        int i;
        int i2;
        long j = this.data[this.position];
        int i3 = 7;
        while (true) {
            if (i3 < 0) {
                break;
            }
            if (((1 << i3) & j) != 0) {
                i3--;
            } else if (i3 < 6) {
                j &= r6 - 1;
                i2 = 7 - i3;
            } else if (i3 == 7) {
                i2 = 1;
            }
        }
        i2 = 0;
        if (i2 == 0) {
            throw new NumberFormatException(new StringBuilder(55).append("Invalid UTF-8 sequence first byte: ").append(j).toString());
        }
        for (i = 1; i < i2; i++) {
            if ((this.data[this.position + i] & 192) != 128) {
                throw new NumberFormatException(new StringBuilder(62).append("Invalid UTF-8 sequence continuation byte: ").append(j).toString());
            }
            j = (j << 6) | (r3 & Utf8.REPLACEMENT_BYTE);
        }
        this.position += i2;
        return j;
    }
}
