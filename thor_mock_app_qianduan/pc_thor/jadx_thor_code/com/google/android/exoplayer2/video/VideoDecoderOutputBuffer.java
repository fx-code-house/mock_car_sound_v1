package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class VideoDecoderOutputBuffer extends OutputBuffer {
    public static final int COLORSPACE_BT2020 = 3;
    public static final int COLORSPACE_BT601 = 1;
    public static final int COLORSPACE_BT709 = 2;
    public static final int COLORSPACE_UNKNOWN = 0;
    public int colorspace;
    public ByteBuffer data;
    public int decoderPrivate;
    public Format format;
    public int height;
    public int mode;
    private final OutputBuffer.Owner<VideoDecoderOutputBuffer> owner;
    public ByteBuffer supplementalData;
    public int width;
    public ByteBuffer[] yuvPlanes;
    public int[] yuvStrides;

    public VideoDecoderOutputBuffer(OutputBuffer.Owner<VideoDecoderOutputBuffer> owner) {
        this.owner = owner;
    }

    @Override // com.google.android.exoplayer2.decoder.OutputBuffer
    public void release() {
        this.owner.releaseOutputBuffer(this);
    }

    public void init(long timeUs, int mode, ByteBuffer supplementalData) {
        this.timeUs = timeUs;
        this.mode = mode;
        if (supplementalData != null && supplementalData.hasRemaining()) {
            addFlag(268435456);
            int iLimit = supplementalData.limit();
            ByteBuffer byteBuffer = this.supplementalData;
            if (byteBuffer == null || byteBuffer.capacity() < iLimit) {
                this.supplementalData = ByteBuffer.allocate(iLimit);
            } else {
                this.supplementalData.clear();
            }
            this.supplementalData.put(supplementalData);
            this.supplementalData.flip();
            supplementalData.position(0);
            return;
        }
        this.supplementalData = null;
    }

    public boolean initForYuvFrame(int width, int height, int yStride, int uvStride, int colorspace) {
        this.width = width;
        this.height = height;
        this.colorspace = colorspace;
        int i = (int) ((height + 1) / 2);
        if (isSafeToMultiply(yStride, height) && isSafeToMultiply(uvStride, i)) {
            int i2 = height * yStride;
            int i3 = i * uvStride;
            int i4 = (i3 * 2) + i2;
            if (isSafeToMultiply(i3, 2) && i4 >= i2) {
                ByteBuffer byteBuffer = this.data;
                if (byteBuffer == null || byteBuffer.capacity() < i4) {
                    this.data = ByteBuffer.allocateDirect(i4);
                } else {
                    this.data.position(0);
                    this.data.limit(i4);
                }
                if (this.yuvPlanes == null) {
                    this.yuvPlanes = new ByteBuffer[3];
                }
                ByteBuffer byteBuffer2 = this.data;
                ByteBuffer[] byteBufferArr = this.yuvPlanes;
                ByteBuffer byteBufferSlice = byteBuffer2.slice();
                byteBufferArr[0] = byteBufferSlice;
                byteBufferSlice.limit(i2);
                byteBuffer2.position(i2);
                ByteBuffer byteBufferSlice2 = byteBuffer2.slice();
                byteBufferArr[1] = byteBufferSlice2;
                byteBufferSlice2.limit(i3);
                byteBuffer2.position(i2 + i3);
                ByteBuffer byteBufferSlice3 = byteBuffer2.slice();
                byteBufferArr[2] = byteBufferSlice3;
                byteBufferSlice3.limit(i3);
                if (this.yuvStrides == null) {
                    this.yuvStrides = new int[3];
                }
                int[] iArr = this.yuvStrides;
                iArr[0] = yStride;
                iArr[1] = uvStride;
                iArr[2] = uvStride;
                return true;
            }
        }
        return false;
    }

    public void initForPrivateFrame(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private static boolean isSafeToMultiply(int a, int b) {
        return a >= 0 && b >= 0 && (b <= 0 || a < Integer.MAX_VALUE / b);
    }
}
