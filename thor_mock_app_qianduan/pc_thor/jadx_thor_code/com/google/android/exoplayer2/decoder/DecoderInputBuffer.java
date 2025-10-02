package com.google.android.exoplayer2.decoder;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes.dex */
public class DecoderInputBuffer extends Buffer {
    public static final int BUFFER_REPLACEMENT_MODE_DIRECT = 2;
    public static final int BUFFER_REPLACEMENT_MODE_DISABLED = 0;
    public static final int BUFFER_REPLACEMENT_MODE_NORMAL = 1;
    private final int bufferReplacementMode;
    public final CryptoInfo cryptoInfo;
    public ByteBuffer data;
    private final int paddingSize;
    public ByteBuffer supplementalData;
    public long timeUs;
    public boolean waitingForKeys;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferReplacementMode {
    }

    public static final class InsufficientCapacityException extends IllegalStateException {
        public final int currentCapacity;
        public final int requiredCapacity;

        public InsufficientCapacityException(int currentCapacity, int requiredCapacity) {
            super(new StringBuilder(44).append("Buffer too small (").append(currentCapacity).append(" < ").append(requiredCapacity).append(")").toString());
            this.currentCapacity = currentCapacity;
            this.requiredCapacity = requiredCapacity;
        }
    }

    public static DecoderInputBuffer newNoDataInstance() {
        return new DecoderInputBuffer(0);
    }

    public DecoderInputBuffer(int bufferReplacementMode) {
        this(bufferReplacementMode, 0);
    }

    public DecoderInputBuffer(int bufferReplacementMode, int paddingSize) {
        this.cryptoInfo = new CryptoInfo();
        this.bufferReplacementMode = bufferReplacementMode;
        this.paddingSize = paddingSize;
    }

    @EnsuresNonNull({"supplementalData"})
    public void resetSupplementalData(int length) {
        ByteBuffer byteBuffer = this.supplementalData;
        if (byteBuffer == null || byteBuffer.capacity() < length) {
            this.supplementalData = ByteBuffer.allocate(length);
        } else {
            this.supplementalData.clear();
        }
    }

    @EnsuresNonNull({"data"})
    public void ensureSpaceForWrite(int length) {
        int i = length + this.paddingSize;
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer == null) {
            this.data = createReplacementByteBuffer(i);
            return;
        }
        int iCapacity = byteBuffer.capacity();
        int iPosition = byteBuffer.position();
        int i2 = i + iPosition;
        if (iCapacity >= i2) {
            this.data = byteBuffer;
            return;
        }
        ByteBuffer byteBufferCreateReplacementByteBuffer = createReplacementByteBuffer(i2);
        byteBufferCreateReplacementByteBuffer.order(byteBuffer.order());
        if (iPosition > 0) {
            byteBuffer.flip();
            byteBufferCreateReplacementByteBuffer.put(byteBuffer);
        }
        this.data = byteBufferCreateReplacementByteBuffer;
    }

    public final boolean isEncrypted() {
        return getFlag(1073741824);
    }

    public final void flip() {
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.flip();
        }
        ByteBuffer byteBuffer2 = this.supplementalData;
        if (byteBuffer2 != null) {
            byteBuffer2.flip();
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        ByteBuffer byteBuffer2 = this.supplementalData;
        if (byteBuffer2 != null) {
            byteBuffer2.clear();
        }
        this.waitingForKeys = false;
    }

    private ByteBuffer createReplacementByteBuffer(int requiredCapacity) {
        int i = this.bufferReplacementMode;
        if (i == 1) {
            return ByteBuffer.allocate(requiredCapacity);
        }
        if (i == 2) {
            return ByteBuffer.allocateDirect(requiredCapacity);
        }
        ByteBuffer byteBuffer = this.data;
        throw new InsufficientCapacityException(byteBuffer == null ? 0 : byteBuffer.capacity(), requiredCapacity);
    }
}
