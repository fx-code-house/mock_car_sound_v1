package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.common.base.Supplier;
import java.nio.ByteBuffer;
import java.util.Objects;

/* loaded from: classes.dex */
final class AsynchronousMediaCodecAdapter implements MediaCodecAdapter {
    private static final int STATE_CREATED = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_SHUT_DOWN = 2;
    private final AsynchronousMediaCodecCallback asynchronousMediaCodecCallback;
    private final AsynchronousMediaCodecBufferEnqueuer bufferEnqueuer;
    private final MediaCodec codec;
    private boolean codecReleased;
    private int state;
    private final boolean synchronizeCodecInteractionsWithQueueing;

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public boolean needsReconfiguration() {
        return false;
    }

    public static final class Factory implements MediaCodecAdapter.Factory {
        private final Supplier<HandlerThread> callbackThreadSupplier;
        private final boolean forceQueueingSynchronizationWorkaround;
        private final Supplier<HandlerThread> queueingThreadSupplier;
        private final boolean synchronizeCodecInteractionsWithQueueing;

        public Factory(int trackType) {
            this(trackType, false, false);
        }

        public Factory(final int trackType, boolean forceQueueingSynchronizationWorkaround, boolean synchronizeCodecInteractionsWithQueueing) {
            this(new Supplier() { // from class: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter$Factory$$ExternalSyntheticLambda0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return AsynchronousMediaCodecAdapter.Factory.lambda$new$0(trackType);
                }
            }, new Supplier() { // from class: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter$Factory$$ExternalSyntheticLambda1
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return AsynchronousMediaCodecAdapter.Factory.lambda$new$1(trackType);
                }
            }, forceQueueingSynchronizationWorkaround, synchronizeCodecInteractionsWithQueueing);
        }

        static /* synthetic */ HandlerThread lambda$new$0(int i) {
            return new HandlerThread(AsynchronousMediaCodecAdapter.createCallbackThreadLabel(i));
        }

        static /* synthetic */ HandlerThread lambda$new$1(int i) {
            return new HandlerThread(AsynchronousMediaCodecAdapter.createQueueingThreadLabel(i));
        }

        Factory(Supplier<HandlerThread> callbackThreadSupplier, Supplier<HandlerThread> queueingThreadSupplier, boolean forceQueueingSynchronizationWorkaround, boolean synchronizeCodecInteractionsWithQueueing) {
            this.callbackThreadSupplier = callbackThreadSupplier;
            this.queueingThreadSupplier = queueingThreadSupplier;
            this.forceQueueingSynchronizationWorkaround = forceQueueingSynchronizationWorkaround;
            this.synchronizeCodecInteractionsWithQueueing = synchronizeCodecInteractionsWithQueueing;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
        public AsynchronousMediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws Exception {
            MediaCodec mediaCodecCreateByCodecName;
            String str = configuration.codecInfo.name;
            AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter = null;
            try {
                String strValueOf = String.valueOf(str);
                TraceUtil.beginSection(strValueOf.length() != 0 ? "createCodec:".concat(strValueOf) : new String("createCodec:"));
                mediaCodecCreateByCodecName = MediaCodec.createByCodecName(str);
                try {
                    AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter2 = new AsynchronousMediaCodecAdapter(mediaCodecCreateByCodecName, this.callbackThreadSupplier.get(), this.queueingThreadSupplier.get(), this.forceQueueingSynchronizationWorkaround, this.synchronizeCodecInteractionsWithQueueing);
                    try {
                        TraceUtil.endSection();
                        asynchronousMediaCodecAdapter2.initialize(configuration.mediaFormat, configuration.surface, configuration.crypto, configuration.flags);
                        return asynchronousMediaCodecAdapter2;
                    } catch (Exception e) {
                        e = e;
                        asynchronousMediaCodecAdapter = asynchronousMediaCodecAdapter2;
                        if (asynchronousMediaCodecAdapter != null) {
                            asynchronousMediaCodecAdapter.release();
                        } else if (mediaCodecCreateByCodecName != null) {
                            mediaCodecCreateByCodecName.release();
                        }
                        throw e;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                mediaCodecCreateByCodecName = null;
            }
        }
    }

    private AsynchronousMediaCodecAdapter(MediaCodec codec, HandlerThread callbackThread, HandlerThread enqueueingThread, boolean forceQueueingSynchronizationWorkaround, boolean synchronizeCodecInteractionsWithQueueing) {
        this.codec = codec;
        this.asynchronousMediaCodecCallback = new AsynchronousMediaCodecCallback(callbackThread);
        this.bufferEnqueuer = new AsynchronousMediaCodecBufferEnqueuer(codec, enqueueingThread, forceQueueingSynchronizationWorkaround);
        this.synchronizeCodecInteractionsWithQueueing = synchronizeCodecInteractionsWithQueueing;
        this.state = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize(MediaFormat mediaFormat, Surface surface, MediaCrypto crypto, int flags) {
        this.asynchronousMediaCodecCallback.initialize(this.codec);
        TraceUtil.beginSection("configureCodec");
        this.codec.configure(mediaFormat, surface, crypto, flags);
        TraceUtil.endSection();
        this.bufferEnqueuer.start();
        TraceUtil.beginSection("startCodec");
        this.codec.start();
        TraceUtil.endSection();
        this.state = 1;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueInputBuffer(int index, int offset, int size, long presentationTimeUs, int flags) {
        this.bufferEnqueuer.queueInputBuffer(index, offset, size, presentationTimeUs, flags);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueSecureInputBuffer(int index, int offset, CryptoInfo info, long presentationTimeUs, int flags) {
        this.bufferEnqueuer.queueSecureInputBuffer(index, offset, info, presentationTimeUs, flags);
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
    public int dequeueInputBufferIndex() {
        return this.asynchronousMediaCodecCallback.dequeueInputBufferIndex();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        return this.asynchronousMediaCodecCallback.dequeueOutputBufferIndex(bufferInfo);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public MediaFormat getOutputFormat() {
        return this.asynchronousMediaCodecCallback.getOutputFormat();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public ByteBuffer getInputBuffer(int index) {
        return this.codec.getInputBuffer(index);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public ByteBuffer getOutputBuffer(int index) {
        return this.codec.getOutputBuffer(index);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void flush() {
        this.bufferEnqueuer.flush();
        this.codec.flush();
        AsynchronousMediaCodecCallback asynchronousMediaCodecCallback = this.asynchronousMediaCodecCallback;
        final MediaCodec mediaCodec = this.codec;
        Objects.requireNonNull(mediaCodec);
        asynchronousMediaCodecCallback.flushAsync(new Runnable() { // from class: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                mediaCodec.start();
            }
        });
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void release() {
        try {
            if (this.state == 1) {
                this.bufferEnqueuer.shutdown();
                this.asynchronousMediaCodecCallback.shutdown();
            }
            this.state = 2;
        } finally {
            if (!this.codecReleased) {
                this.codec.release();
                this.codecReleased = true;
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOnFrameRenderedListener(final MediaCodecAdapter.OnFrameRenderedListener listener, Handler handler) {
        maybeBlockOnQueueing();
        this.codec.setOnFrameRenderedListener(new MediaCodec.OnFrameRenderedListener() { // from class: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter$$ExternalSyntheticLambda0
            @Override // android.media.MediaCodec.OnFrameRenderedListener
            public final void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
                this.f$0.m161x272a3f72(listener, mediaCodec, j, j2);
            }
        }, handler);
    }

    /* renamed from: lambda$setOnFrameRenderedListener$0$com-google-android-exoplayer2-mediacodec-AsynchronousMediaCodecAdapter, reason: not valid java name */
    /* synthetic */ void m161x272a3f72(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, MediaCodec mediaCodec, long j, long j2) {
        onFrameRenderedListener.onFrameRendered(this, j, j2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOutputSurface(Surface surface) {
        maybeBlockOnQueueing();
        this.codec.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setParameters(Bundle params) {
        maybeBlockOnQueueing();
        this.codec.setParameters(params);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setVideoScalingMode(int scalingMode) {
        maybeBlockOnQueueing();
        this.codec.setVideoScalingMode(scalingMode);
    }

    void onError(MediaCodec.CodecException error) {
        this.asynchronousMediaCodecCallback.onError(this.codec, error);
    }

    void onOutputFormatChanged(MediaFormat format) {
        this.asynchronousMediaCodecCallback.onOutputFormatChanged(this.codec, format);
    }

    private void maybeBlockOnQueueing() {
        if (this.synchronizeCodecInteractionsWithQueueing) {
            try {
                this.bufferEnqueuer.waitUntilQueueingComplete();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String createCallbackThreadLabel(int trackType) {
        return createThreadLabel(trackType, "ExoPlayer:MediaCodecAsyncAdapter:");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String createQueueingThreadLabel(int trackType) {
        return createThreadLabel(trackType, "ExoPlayer:MediaCodecQueueingThread:");
    }

    private static String createThreadLabel(int trackType, String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        if (trackType == 1) {
            sb.append("Audio");
        } else if (trackType == 2) {
            sb.append("Video");
        } else {
            sb.append("Unknown(").append(trackType).append(")");
        }
        return sb.toString();
    }
}
