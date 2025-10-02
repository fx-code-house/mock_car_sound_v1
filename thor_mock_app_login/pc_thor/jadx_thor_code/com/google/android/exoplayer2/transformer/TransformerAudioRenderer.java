package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.SonicAudioProcessor;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class TransformerAudioRenderer extends TransformerBaseRenderer {
    private static final int DEFAULT_ENCODER_BITRATE = 131072;
    private static final float SPEED_UNSET = -1.0f;
    private static final String TAG = "TransformerAudioRenderer";
    private float currentSpeed;
    private MediaCodecAdapterWrapper decoder;
    private final DecoderInputBuffer decoderInputBuffer;
    private boolean drainingSonicForSpeedChange;
    private MediaCodecAdapterWrapper encoder;
    private AudioProcessor.AudioFormat encoderInputAudioFormat;
    private final DecoderInputBuffer encoderInputBuffer;
    private boolean hasEncoderOutputFormat;
    private Format inputFormat;
    private boolean muxerWrapperTrackEnded;
    private long nextEncoderInputBufferTimeUs;
    private final SonicAudioProcessor sonicAudioProcessor;
    private ByteBuffer sonicOutputBuffer;
    private SpeedProvider speedProvider;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    public TransformerAudioRenderer(MuxerWrapper muxerWrapper, TransformerMediaClock mediaClock, Transformation transformation) {
        super(1, muxerWrapper, mediaClock, transformation);
        this.decoderInputBuffer = new DecoderInputBuffer(0);
        this.encoderInputBuffer = new DecoderInputBuffer(0);
        this.sonicAudioProcessor = new SonicAudioProcessor();
        this.sonicOutputBuffer = AudioProcessor.EMPTY_BUFFER;
        this.nextEncoderInputBufferTimeUs = 0L;
        this.currentSpeed = -1.0f;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.muxerWrapperTrackEnded;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onReset() {
        this.decoderInputBuffer.clear();
        this.decoderInputBuffer.data = null;
        this.encoderInputBuffer.clear();
        this.encoderInputBuffer.data = null;
        this.sonicAudioProcessor.reset();
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = this.decoder;
        if (mediaCodecAdapterWrapper != null) {
            mediaCodecAdapterWrapper.release();
            this.decoder = null;
        }
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper2 = this.encoder;
        if (mediaCodecAdapterWrapper2 != null) {
            mediaCodecAdapterWrapper2.release();
            this.encoder = null;
        }
        this.speedProvider = null;
        this.inputFormat = null;
        this.encoderInputAudioFormat = null;
        this.sonicOutputBuffer = AudioProcessor.EMPTY_BUFFER;
        this.nextEncoderInputBufferTimeUs = 0L;
        this.currentSpeed = -1.0f;
        this.muxerWrapperTrackEnded = false;
        this.hasEncoderOutputFormat = false;
        this.drainingSonicForSpeedChange = false;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        if (this.isRendererStarted && !isEnded() && ensureDecoderConfigured()) {
            if (ensureEncoderAndAudioProcessingConfigured()) {
                while (drainEncoderToFeedMuxer()) {
                }
                if (this.sonicAudioProcessor.isActive()) {
                    while (drainSonicToFeedEncoder()) {
                    }
                    while (drainDecoderToFeedSonic()) {
                    }
                } else {
                    while (drainDecoderToFeedEncoder()) {
                    }
                }
            }
            while (feedDecoderInputFromSource()) {
            }
        }
    }

    private boolean drainEncoderToFeedMuxer() {
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = (MediaCodecAdapterWrapper) Assertions.checkNotNull(this.encoder);
        if (!this.hasEncoderOutputFormat) {
            Format outputFormat = mediaCodecAdapterWrapper.getOutputFormat();
            if (outputFormat == null) {
                return false;
            }
            this.hasEncoderOutputFormat = true;
            this.muxerWrapper.addTrackFormat(outputFormat);
        }
        if (mediaCodecAdapterWrapper.isEnded()) {
            this.muxerWrapper.endTrack(getTrackType());
            this.muxerWrapperTrackEnded = true;
            return false;
        }
        ByteBuffer outputBuffer = mediaCodecAdapterWrapper.getOutputBuffer();
        if (outputBuffer == null) {
            return false;
        }
        if (!this.muxerWrapper.writeSample(getTrackType(), outputBuffer, true, ((MediaCodec.BufferInfo) Assertions.checkNotNull(mediaCodecAdapterWrapper.getOutputBufferInfo())).presentationTimeUs)) {
            return false;
        }
        mediaCodecAdapterWrapper.releaseOutputBuffer();
        return true;
    }

    private boolean drainDecoderToFeedEncoder() {
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = (MediaCodecAdapterWrapper) Assertions.checkNotNull(this.decoder);
        if (!((MediaCodecAdapterWrapper) Assertions.checkNotNull(this.encoder)).maybeDequeueInputBuffer(this.encoderInputBuffer)) {
            return false;
        }
        if (mediaCodecAdapterWrapper.isEnded()) {
            queueEndOfStreamToEncoder();
            return false;
        }
        ByteBuffer outputBuffer = mediaCodecAdapterWrapper.getOutputBuffer();
        if (outputBuffer == null) {
            return false;
        }
        if (isSpeedChanging((MediaCodec.BufferInfo) Assertions.checkNotNull(mediaCodecAdapterWrapper.getOutputBufferInfo()))) {
            flushSonicAndSetSpeed(this.currentSpeed);
            return false;
        }
        feedEncoder(outputBuffer);
        if (outputBuffer.hasRemaining()) {
            return true;
        }
        mediaCodecAdapterWrapper.releaseOutputBuffer();
        return true;
    }

    private boolean drainSonicToFeedEncoder() {
        if (!((MediaCodecAdapterWrapper) Assertions.checkNotNull(this.encoder)).maybeDequeueInputBuffer(this.encoderInputBuffer)) {
            return false;
        }
        if (!this.sonicOutputBuffer.hasRemaining()) {
            ByteBuffer output = this.sonicAudioProcessor.getOutput();
            this.sonicOutputBuffer = output;
            if (!output.hasRemaining()) {
                if (((MediaCodecAdapterWrapper) Assertions.checkNotNull(this.decoder)).isEnded() && this.sonicAudioProcessor.isEnded()) {
                    queueEndOfStreamToEncoder();
                }
                return false;
            }
        }
        feedEncoder(this.sonicOutputBuffer);
        return true;
    }

    private boolean drainDecoderToFeedSonic() {
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = (MediaCodecAdapterWrapper) Assertions.checkNotNull(this.decoder);
        if (this.drainingSonicForSpeedChange) {
            if (this.sonicAudioProcessor.isEnded() && !this.sonicOutputBuffer.hasRemaining()) {
                flushSonicAndSetSpeed(this.currentSpeed);
                this.drainingSonicForSpeedChange = false;
            }
            return false;
        }
        if (this.sonicOutputBuffer.hasRemaining()) {
            return false;
        }
        if (mediaCodecAdapterWrapper.isEnded()) {
            this.sonicAudioProcessor.queueEndOfStream();
            return false;
        }
        Assertions.checkState(!this.sonicAudioProcessor.isEnded());
        ByteBuffer outputBuffer = mediaCodecAdapterWrapper.getOutputBuffer();
        if (outputBuffer == null) {
            return false;
        }
        if (isSpeedChanging((MediaCodec.BufferInfo) Assertions.checkNotNull(mediaCodecAdapterWrapper.getOutputBufferInfo()))) {
            this.sonicAudioProcessor.queueEndOfStream();
            this.drainingSonicForSpeedChange = true;
            return false;
        }
        this.sonicAudioProcessor.queueInput(outputBuffer);
        if (!outputBuffer.hasRemaining()) {
            mediaCodecAdapterWrapper.releaseOutputBuffer();
        }
        return true;
    }

    private boolean feedDecoderInputFromSource() {
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = (MediaCodecAdapterWrapper) Assertions.checkNotNull(this.decoder);
        if (!mediaCodecAdapterWrapper.maybeDequeueInputBuffer(this.decoderInputBuffer)) {
            return false;
        }
        this.decoderInputBuffer.clear();
        int source = readSource(getFormatHolder(), this.decoderInputBuffer, 0);
        if (source == -5) {
            throw new IllegalStateException("Format changes are not supported.");
        }
        if (source != -4) {
            return false;
        }
        this.mediaClock.updateTimeForTrackType(getTrackType(), this.decoderInputBuffer.timeUs);
        this.decoderInputBuffer.flip();
        mediaCodecAdapterWrapper.queueInputBuffer(this.decoderInputBuffer);
        return !this.decoderInputBuffer.isEndOfStream();
    }

    private void feedEncoder(ByteBuffer inputBuffer) {
        AudioProcessor.AudioFormat audioFormat = (AudioProcessor.AudioFormat) Assertions.checkNotNull(this.encoderInputAudioFormat);
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = (MediaCodecAdapterWrapper) Assertions.checkNotNull(this.encoder);
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(this.encoderInputBuffer.data);
        int iLimit = inputBuffer.limit();
        inputBuffer.limit(Math.min(iLimit, inputBuffer.position() + byteBuffer.capacity()));
        byteBuffer.put(inputBuffer);
        this.encoderInputBuffer.timeUs = this.nextEncoderInputBufferTimeUs;
        this.nextEncoderInputBufferTimeUs += getBufferDurationUs(byteBuffer.position(), audioFormat.bytesPerFrame, audioFormat.sampleRate);
        this.encoderInputBuffer.setFlags(0);
        this.encoderInputBuffer.flip();
        inputBuffer.limit(iLimit);
        mediaCodecAdapterWrapper.queueInputBuffer(this.encoderInputBuffer);
    }

    private void queueEndOfStreamToEncoder() {
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = (MediaCodecAdapterWrapper) Assertions.checkNotNull(this.encoder);
        Assertions.checkState(((ByteBuffer) Assertions.checkNotNull(this.encoderInputBuffer.data)).position() == 0);
        this.encoderInputBuffer.addFlag(4);
        this.encoderInputBuffer.flip();
        mediaCodecAdapterWrapper.queueInputBuffer(this.encoderInputBuffer);
    }

    private boolean ensureEncoderAndAudioProcessingConfigured() throws ExoPlaybackException {
        if (this.encoder != null) {
            return true;
        }
        Format outputFormat = ((MediaCodecAdapterWrapper) Assertions.checkNotNull(this.decoder)).getOutputFormat();
        if (outputFormat == null) {
            return false;
        }
        AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(outputFormat.sampleRate, outputFormat.channelCount, outputFormat.pcmEncoding);
        if (this.transformation.flattenForSlowMotion) {
            try {
                audioFormat = this.sonicAudioProcessor.configure(audioFormat);
                flushSonicAndSetSpeed(this.currentSpeed);
            } catch (AudioProcessor.UnhandledAudioFormatException e) {
                throw createRendererException(e, 1000);
            }
        }
        try {
            this.encoder = MediaCodecAdapterWrapper.createForAudioEncoding(new Format.Builder().setSampleMimeType(((Format) Assertions.checkNotNull(this.inputFormat)).sampleMimeType).setSampleRate(audioFormat.sampleRate).setChannelCount(audioFormat.channelCount).setAverageBitrate(131072).build());
            this.encoderInputAudioFormat = audioFormat;
            return true;
        } catch (IOException e2) {
            throw createRendererException(e2, 1000);
        }
    }

    private boolean ensureDecoderConfigured() throws ExoPlaybackException {
        if (this.decoder != null) {
            return true;
        }
        FormatHolder formatHolder = getFormatHolder();
        if (readSource(formatHolder, this.decoderInputBuffer, 2) != -5) {
            return false;
        }
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        this.inputFormat = format;
        try {
            this.decoder = MediaCodecAdapterWrapper.createForAudioDecoding(format);
            SegmentSpeedProvider segmentSpeedProvider = new SegmentSpeedProvider(this.inputFormat);
            this.speedProvider = segmentSpeedProvider;
            this.currentSpeed = segmentSpeedProvider.getSpeed(0L);
            return true;
        } catch (IOException e) {
            throw createRendererException(e, 1000);
        }
    }

    private boolean isSpeedChanging(MediaCodec.BufferInfo bufferInfo) {
        if (!this.transformation.flattenForSlowMotion) {
            return false;
        }
        float speed = ((SpeedProvider) Assertions.checkNotNull(this.speedProvider)).getSpeed(bufferInfo.presentationTimeUs);
        boolean z = speed != this.currentSpeed;
        this.currentSpeed = speed;
        return z;
    }

    private void flushSonicAndSetSpeed(float speed) {
        this.sonicAudioProcessor.setSpeed(speed);
        this.sonicAudioProcessor.setPitch(speed);
        this.sonicAudioProcessor.flush();
    }

    private ExoPlaybackException createRendererException(Throwable cause, int errorCode) {
        return ExoPlaybackException.createForRenderer(cause, TAG, getIndex(), this.inputFormat, 4, false, errorCode);
    }

    private static long getBufferDurationUs(long bytesWritten, int bytesPerFrame, int sampleRate) {
        return ((bytesWritten / bytesPerFrame) * 1000000) / sampleRate;
    }
}
