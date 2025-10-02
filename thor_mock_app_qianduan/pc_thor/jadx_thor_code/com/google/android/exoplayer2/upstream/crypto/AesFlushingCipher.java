package com.google.android.exoplayer2.upstream.crypto;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class AesFlushingCipher {
    private final int blockSize;
    private final Cipher cipher;
    private final byte[] flushedBlock;
    private int pendingXorBytes;
    private final byte[] zerosBlock;

    public AesFlushingCipher(int mode, byte[] secretKey, long nonce, long offset) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            this.cipher = cipher;
            int blockSize = cipher.getBlockSize();
            this.blockSize = blockSize;
            this.zerosBlock = new byte[blockSize];
            this.flushedBlock = new byte[blockSize];
            long j = offset / blockSize;
            int i = (int) (offset % blockSize);
            cipher.init(mode, new SecretKeySpec(secretKey, Util.splitAtFirst(cipher.getAlgorithm(), "/")[0]), new IvParameterSpec(getInitializationVector(nonce, j)));
            if (i != 0) {
                updateInPlace(new byte[i], 0, i);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateInPlace(byte[] data, int offset, int length) {
        update(data, offset, length, data, offset);
    }

    public void update(byte[] in, int inOffset, int length, byte[] out, int outOffset) {
        int i = inOffset;
        do {
            int i2 = this.pendingXorBytes;
            if (i2 > 0) {
                out[outOffset] = (byte) (in[i] ^ this.flushedBlock[this.blockSize - i2]);
                outOffset++;
                i++;
                this.pendingXorBytes = i2 - 1;
                length--;
            } else {
                int iNonFlushingUpdate = nonFlushingUpdate(in, i, length, out, outOffset);
                if (length == iNonFlushingUpdate) {
                    return;
                }
                int i3 = length - iNonFlushingUpdate;
                int i4 = 0;
                Assertions.checkState(i3 < this.blockSize);
                int i5 = outOffset + iNonFlushingUpdate;
                int i6 = this.blockSize - i3;
                this.pendingXorBytes = i6;
                Assertions.checkState(nonFlushingUpdate(this.zerosBlock, 0, i6, this.flushedBlock, 0) == this.blockSize);
                while (i4 < i3) {
                    out[i5] = this.flushedBlock[i4];
                    i4++;
                    i5++;
                }
                return;
            }
        } while (length != 0);
    }

    private int nonFlushingUpdate(byte[] in, int inOffset, int length, byte[] out, int outOffset) {
        try {
            return this.cipher.update(in, inOffset, length, out, outOffset);
        } catch (ShortBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getInitializationVector(long nonce, long counter) {
        return ByteBuffer.allocate(16).putLong(nonce).putLong(counter).array();
    }
}
