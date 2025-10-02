package com.facebook.crypto.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

/* loaded from: classes.dex */
public class BetterCipherInputStream extends FilterInputStream {
    private static final int UPDATE_BUFFER_SIZE = 256;
    private final Cipher mCipher;
    private final byte[] mUpdateBuffer;

    public BetterCipherInputStream(InputStream inputStream, Cipher cipher) {
        super(inputStream);
        this.mCipher = cipher;
        this.mUpdateBuffer = new byte[256];
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException, ShortBufferException {
        int i3 = this.in.read(bArr, i, i2);
        if (i3 == -1) {
            return -1;
        }
        int i4 = i3 / 256;
        int i5 = i3 % 256;
        int i6 = i;
        int i7 = i6;
        for (int i8 = 0; i8 < i4; i8++) {
            try {
                int iUpdate = this.mCipher.update(bArr, i6, 256, this.mUpdateBuffer);
                System.arraycopy(this.mUpdateBuffer, 0, bArr, i7, iUpdate);
                i7 += iUpdate;
                i6 += 256;
            } catch (ShortBufferException unused) {
            }
        }
        if (i5 > 0) {
            int iUpdate2 = this.mCipher.update(bArr, i6, i5, this.mUpdateBuffer);
            System.arraycopy(this.mUpdateBuffer, 0, bArr, i7, iUpdate2);
            i7 += iUpdate2;
        }
        return i7 - i;
    }
}
