package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class SynchronousMediaCodecAdapter implements MediaCodecAdapter {
    private final MediaCodec codec;
    private ByteBuffer[] inputByteBuffers;
    private ByteBuffer[] outputByteBuffers;

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public boolean needsReconfiguration() {
        return false;
    }

    public static class Factory implements MediaCodecAdapter.Factory {
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter$1] */
        /* JADX WARN: Type inference failed for: r0v2 */
        /* JADX WARN: Type inference failed for: r0v3 */
        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
        public MediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws Throwable {
            MediaCodec mediaCodecCreateCodec;
            MediaCodec mediaCodec = 0;
            mediaCodec = 0;
            try {
                mediaCodecCreateCodec = createCodec(configuration);
            } catch (IOException e) {
                e = e;
            } catch (RuntimeException e2) {
                e = e2;
            }
            try {
                TraceUtil.beginSection("configureCodec");
                mediaCodecCreateCodec.configure(configuration.mediaFormat, configuration.surface, configuration.crypto, configuration.flags);
                TraceUtil.endSection();
                TraceUtil.beginSection("startCodec");
                mediaCodecCreateCodec.start();
                TraceUtil.endSection();
                return new SynchronousMediaCodecAdapter(mediaCodecCreateCodec);
            } catch (IOException | RuntimeException e3) {
                e = e3;
                mediaCodec = mediaCodecCreateCodec;
                if (mediaCodec != 0) {
                    mediaCodec.release();
                }
                throw e;
            }
        }

        protected MediaCodec createCodec(MediaCodecAdapter.Configuration configuration) throws IOException {
            Assertions.checkNotNull(configuration.codecInfo);
            String str = configuration.codecInfo.name;
            String strValueOf = String.valueOf(str);
            TraceUtil.beginSection(strValueOf.length() != 0 ? "createCodec:".concat(strValueOf) : new String("createCodec:"));
            MediaCodec mediaCodecCreateByCodecName = MediaCodec.createByCodecName(str);
            TraceUtil.endSection();
            return mediaCodecCreateByCodecName;
        }
    }

    private SynchronousMediaCodecAdapter(MediaCodec mediaCodec) {
        this.codec = mediaCodec;
        if (Util.SDK_INT < 21) {
            this.inputByteBuffers = mediaCodec.getInputBuffers();
            this.outputByteBuffers = mediaCodec.getOutputBuffers();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueInputBufferIndex() {
        return this.codec.dequeueInputBuffer(0L);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        int iDequeueOutputBuffer;
        do {
            iDequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, 0L);
            if (iDequeueOutputBuffer == -3 && Util.SDK_INT < 21) {
                this.outputByteBuffers = this.codec.getOutputBuffers();
            }
        } while (iDequeueOutputBuffer == -3);
        return iDequeueOutputBuffer;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public MediaFormat getOutputFormat() {
        return this.codec.getOutputFormat();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public ByteBuffer getInputBuffer(int index) {
        if (Util.SDK_INT >= 21) {
            return this.codec.getInputBuffer(index);
        }
        return ((ByteBuffer[]) Util.castNonNull(this.inputByteBuffers))[index];
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public ByteBuffer getOutputBuffer(int index) {
        if (Util.SDK_INT >= 21) {
            return this.codec.getOutputBuffer(index);
        }
        return ((ByteBuffer[]) Util.castNonNull(this.outputByteBuffers))[index];
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueInputBuffer(int index, int offset, int size, long presentationTimeUs, int flags) throws MediaCodec.CryptoException {
        this.codec.queueInputBuffer(index, offset, size, presentationTimeUs, flags);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueSecureInputBuffer(int index, int offset, CryptoInfo info, long presentationTimeUs, int flags) throws MediaCodec.CryptoException {
        this.codec.queueSecureInputBuffer(index, offset, info.getFrameworkCryptoInfo(), presentationTimeUs, flags);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int index, boolean render) {
        this.codec.releaseOutputBuffer(index, render);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int index, long renderTimeStampNs) {
        this.codec.releaseOutputBuffer(index, renderTimeStampNs);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void flush() {
        this.codec.flush();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void release() {
        this.inputByteBuffers = null;
        this.outputByteBuffers = null;
        this.codec.release();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOnFrameRenderedListener(final MediaCodecAdapter.OnFrameRenderedListener listener, Handler handler) {
        this.codec.setOnFrameRenderedListener(new MediaCodec.OnFrameRenderedListener() { // from class: com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter$$ExternalSyntheticLambda0
            @Override // android.media.MediaCodec.OnFrameRenderedListener
            public final void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
                this.f$0.m163x143bf9f7(listener, mediaCodec, j, j2);
            }
        }, handler);
    }

    /* renamed from: lambda$setOnFrameRenderedListener$0$com-google-android-exoplayer2-mediacodec-SynchronousMediaCodecAdapter, reason: not valid java name */
    /* synthetic */ void m163x143bf9f7(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, MediaCodec mediaCodec, long j, long j2) {
        onFrameRenderedListener.onFrameRendered(this, j, j2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOutputSurface(Surface surface) {
        this.codec.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setParameters(Bundle params) {
        this.codec.setParameters(params);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setVideoScalingMode(int scalingMode) {
        this.codec.setVideoScalingMode(scalingMode);
    }
}
