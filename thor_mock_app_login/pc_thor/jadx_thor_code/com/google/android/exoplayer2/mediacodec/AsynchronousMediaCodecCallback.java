package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.IntArrayQueue;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;

/* loaded from: classes.dex */
final class AsynchronousMediaCodecCallback extends MediaCodec.Callback {
    private final HandlerThread callbackThread;
    private MediaFormat currentFormat;
    private Handler handler;
    private IllegalStateException internalException;
    private MediaCodec.CodecException mediaCodecException;
    private long pendingFlushCount;
    private MediaFormat pendingOutputFormat;
    private boolean shutDown;
    private final Object lock = new Object();
    private final IntArrayQueue availableInputBuffers = new IntArrayQueue();
    private final IntArrayQueue availableOutputBuffers = new IntArrayQueue();
    private final ArrayDeque<MediaCodec.BufferInfo> bufferInfos = new ArrayDeque<>();
    private final ArrayDeque<MediaFormat> formats = new ArrayDeque<>();

    AsynchronousMediaCodecCallback(HandlerThread callbackThread) {
        this.callbackThread = callbackThread;
    }

    public void initialize(MediaCodec codec) {
        Assertions.checkState(this.handler == null);
        this.callbackThread.start();
        Handler handler = new Handler(this.callbackThread.getLooper());
        codec.setCallback(this, handler);
        this.handler = handler;
    }

    public void shutdown() {
        synchronized (this.lock) {
            this.shutDown = true;
            this.callbackThread.quit();
            flushInternal();
        }
    }

    public int dequeueInputBufferIndex() {
        synchronized (this.lock) {
            int iRemove = -1;
            if (isFlushingOrShutdown()) {
                return -1;
            }
            maybeThrowException();
            if (!this.availableInputBuffers.isEmpty()) {
                iRemove = this.availableInputBuffers.remove();
            }
            return iRemove;
        }
    }

    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.lock) {
            if (isFlushingOrShutdown()) {
                return -1;
            }
            maybeThrowException();
            if (this.availableOutputBuffers.isEmpty()) {
                return -1;
            }
            int iRemove = this.availableOutputBuffers.remove();
            if (iRemove >= 0) {
                Assertions.checkStateNotNull(this.currentFormat);
                MediaCodec.BufferInfo bufferInfoRemove = this.bufferInfos.remove();
                bufferInfo.set(bufferInfoRemove.offset, bufferInfoRemove.size, bufferInfoRemove.presentationTimeUs, bufferInfoRemove.flags);
            } else if (iRemove == -2) {
                this.currentFormat = this.formats.remove();
            }
            return iRemove;
        }
    }

    public MediaFormat getOutputFormat() {
        MediaFormat mediaFormat;
        synchronized (this.lock) {
            mediaFormat = this.currentFormat;
            if (mediaFormat == null) {
                throw new IllegalStateException();
            }
        }
        return mediaFormat;
    }

    public void flushAsync(final Runnable onFlushCompleted) {
        synchronized (this.lock) {
            this.pendingFlushCount++;
            ((Handler) Util.castNonNull(this.handler)).post(new Runnable() { // from class: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m162x6cd93be5(onFlushCompleted);
                }
            });
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onInputBufferAvailable(MediaCodec codec, int index) {
        synchronized (this.lock) {
            this.availableInputBuffers.add(index);
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
        synchronized (this.lock) {
            MediaFormat mediaFormat = this.pendingOutputFormat;
            if (mediaFormat != null) {
                addOutputFormat(mediaFormat);
                this.pendingOutputFormat = null;
            }
            this.availableOutputBuffers.add(index);
            this.bufferInfos.add(info);
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onError(MediaCodec codec, MediaCodec.CodecException e) {
        synchronized (this.lock) {
            this.mediaCodecException = e;
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
        synchronized (this.lock) {
            addOutputFormat(format);
            this.pendingOutputFormat = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onFlushCompleted, reason: merged with bridge method [inline-methods] */
    public void m162x6cd93be5(Runnable onFlushCompleted) {
        synchronized (this.lock) {
            onFlushCompletedSynchronized(onFlushCompleted);
        }
    }

    private void onFlushCompletedSynchronized(Runnable onFlushCompleted) {
        if (this.shutDown) {
            return;
        }
        long j = this.pendingFlushCount - 1;
        this.pendingFlushCount = j;
        if (j > 0) {
            return;
        }
        if (j < 0) {
            setInternalException(new IllegalStateException());
            return;
        }
        flushInternal();
        try {
            onFlushCompleted.run();
        } catch (IllegalStateException e) {
            setInternalException(e);
        } catch (Exception e2) {
            setInternalException(new IllegalStateException(e2));
        }
    }

    private void flushInternal() {
        if (!this.formats.isEmpty()) {
            this.pendingOutputFormat = this.formats.getLast();
        }
        this.availableInputBuffers.clear();
        this.availableOutputBuffers.clear();
        this.bufferInfos.clear();
        this.formats.clear();
        this.mediaCodecException = null;
    }

    private boolean isFlushingOrShutdown() {
        return this.pendingFlushCount > 0 || this.shutDown;
    }

    private void addOutputFormat(MediaFormat mediaFormat) {
        this.availableOutputBuffers.add(-2);
        this.formats.add(mediaFormat);
    }

    private void maybeThrowException() {
        maybeThrowInternalException();
        maybeThrowMediaCodecException();
    }

    private void maybeThrowInternalException() {
        IllegalStateException illegalStateException = this.internalException;
        if (illegalStateException == null) {
            return;
        }
        this.internalException = null;
        throw illegalStateException;
    }

    private void maybeThrowMediaCodecException() {
        MediaCodec.CodecException codecException = this.mediaCodecException;
        if (codecException == null) {
            return;
        }
        this.mediaCodecException = null;
        throw codecException;
    }

    private void setInternalException(IllegalStateException e) {
        synchronized (this.lock) {
            this.internalException = e;
        }
    }
}
