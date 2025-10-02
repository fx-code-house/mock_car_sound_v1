package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public interface MediaCodecAdapter {

    public interface Factory {
        public static final Factory DEFAULT = new SynchronousMediaCodecAdapter.Factory();

        MediaCodecAdapter createAdapter(Configuration configuration) throws IOException;
    }

    public interface OnFrameRenderedListener {
        void onFrameRendered(MediaCodecAdapter codec, long presentationTimeUs, long nanoTime);
    }

    int dequeueInputBufferIndex();

    int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo);

    void flush();

    ByteBuffer getInputBuffer(int index);

    ByteBuffer getOutputBuffer(int index);

    MediaFormat getOutputFormat();

    boolean needsReconfiguration();

    void queueInputBuffer(int index, int offset, int size, long presentationTimeUs, int flags);

    void queueSecureInputBuffer(int index, int offset, CryptoInfo info, long presentationTimeUs, int flags);

    void release();

    void releaseOutputBuffer(int index, long renderTimeStampNs);

    void releaseOutputBuffer(int index, boolean render);

    void setOnFrameRenderedListener(OnFrameRenderedListener listener, Handler handler);

    void setOutputSurface(Surface surface);

    void setParameters(Bundle params);

    void setVideoScalingMode(int scalingMode);

    public static final class Configuration {
        public final MediaCodecInfo codecInfo;
        public final MediaCrypto crypto;
        public final int flags;
        public final Format format;
        public final MediaFormat mediaFormat;
        public final Surface surface;

        public Configuration(MediaCodecInfo codecInfo, MediaFormat mediaFormat, Format format, Surface surface, MediaCrypto crypto, int flags) {
            this.codecInfo = codecInfo;
            this.mediaFormat = mediaFormat;
            this.format = format;
            this.surface = surface;
            this.crypto = crypto;
            this.flags = flags;
        }
    }
}
