package com.google.android.exoplayer2.transformer;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class TransformerVideoRenderer extends TransformerBaseRenderer {
    private static final String TAG = "TransformerVideoRenderer";
    private final DecoderInputBuffer buffer;
    private boolean formatRead;
    private boolean isBufferPending;
    private boolean isInputStreamEnded;
    private SampleTransformer sampleTransformer;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    public TransformerVideoRenderer(MuxerWrapper muxerWrapper, TransformerMediaClock mediaClock, Transformation transformation) {
        super(2, muxerWrapper, mediaClock, transformation);
        this.buffer = new DecoderInputBuffer(2);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long positionUs, long elapsedRealtimeUs) {
        boolean z;
        if (!this.isRendererStarted || isEnded()) {
            return;
        }
        if (!this.formatRead) {
            FormatHolder formatHolder = getFormatHolder();
            if (readSource(formatHolder, this.buffer, 2) != -5) {
                return;
            }
            Format format = (Format) Assertions.checkNotNull(formatHolder.format);
            this.formatRead = true;
            if (this.transformation.flattenForSlowMotion) {
                this.sampleTransformer = new SefSlowMotionVideoSampleTransformer(format);
            }
            this.muxerWrapper.addTrackFormat(format);
        }
        do {
            if (!this.isBufferPending && !readAndTransformBuffer()) {
                return;
            }
            z = !this.muxerWrapper.writeSample(getTrackType(), this.buffer.data, this.buffer.isKeyFrame(), this.buffer.timeUs);
            this.isBufferPending = z;
        } while (!z);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.isInputStreamEnded;
    }

    private boolean readAndTransformBuffer() {
        this.buffer.clear();
        int source = readSource(getFormatHolder(), this.buffer, 0);
        if (source == -5) {
            throw new IllegalStateException("Format changes are not supported.");
        }
        if (source == -3) {
            return false;
        }
        if (this.buffer.isEndOfStream()) {
            this.isInputStreamEnded = true;
            this.muxerWrapper.endTrack(getTrackType());
            return false;
        }
        this.mediaClock.updateTimeForTrackType(getTrackType(), this.buffer.timeUs);
        ((ByteBuffer) Assertions.checkNotNull(this.buffer.data)).flip();
        SampleTransformer sampleTransformer = this.sampleTransformer;
        if (sampleTransformer != null) {
            sampleTransformer.transformSample(this.buffer);
        }
        return true;
    }
}
