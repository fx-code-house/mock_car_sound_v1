package com.facebook.crypto.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class TailInputStream extends FilterInputStream {
    private int mCount;
    private boolean mEOF;
    private final byte[] mTail;
    private final int mTailTength;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    protected TailInputStream(InputStream inputStream, int i) {
        super(inputStream);
        this.mTail = new byte[i];
        this.mTailTength = i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = new byte[1];
        int i = read(bArr, 0, 1);
        while (i == 0) {
            i = read(bArr, 0, 1);
        }
        if (i == -1) {
            return -1;
        }
        return bArr[0] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.mEOF) {
            return -1;
        }
        int tail = 0;
        if (i2 == 0) {
            return 0;
        }
        while (tail == 0) {
            tail = readTail(bArr, i, i2);
        }
        return tail;
    }

    private int readTail(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.mCount;
        int i4 = 0;
        if (i2 >= i3) {
            int i5 = this.in.read(bArr, this.mCount + i, i2 - i3);
            if (i5 == -1) {
                this.mEOF = true;
                return -1;
            }
            int i6 = this.mCount;
            if (i6 > 0) {
                System.arraycopy(this.mTail, 0, bArr, i, i6);
            }
            int i7 = this.mCount + i5;
            int i8 = this.in.read(this.mTail, 0, this.mTailTength);
            if (i8 == -1) {
                this.mEOF = true;
            } else {
                i4 = i8;
            }
            return extractTail(bArr, i7, i4, i);
        }
        int i9 = i3 - i2;
        System.arraycopy(this.mTail, 0, bArr, i, i2);
        byte[] bArr2 = this.mTail;
        System.arraycopy(bArr2, i2, bArr2, 0, i9);
        int i10 = this.in.read(this.mTail, i9, this.mTailTength - i9);
        if (i10 == -1) {
            byte[] bArr3 = this.mTail;
            System.arraycopy(bArr3, 0, bArr3, i2, i9);
            System.arraycopy(bArr, i, this.mTail, 0, i2);
            this.mEOF = true;
            return -1;
        }
        return extractTail(bArr, i2, i10 + i9, i);
    }

    private int extractTail(byte[] bArr, int i, int i2, int i3) {
        int i4 = this.mTailTength - i2;
        int iMax = Math.max(0, i - i4) + i3;
        int iMin = Math.min(i4, i);
        if (iMin > 0) {
            if (i2 > 0) {
                byte[] bArr2 = this.mTail;
                System.arraycopy(bArr2, 0, bArr2, iMin, i2);
            }
            System.arraycopy(bArr, iMax, this.mTail, 0, iMin);
        }
        this.mCount = iMin + i2;
        return iMax - i3;
    }

    public byte[] getTail() throws IOException {
        if (this.mCount != this.mTailTength) {
            throw new IOException("Not enough tail data");
        }
        return this.mTail;
    }
}
