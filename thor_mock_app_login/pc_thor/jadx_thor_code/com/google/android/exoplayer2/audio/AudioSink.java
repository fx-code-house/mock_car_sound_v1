package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public interface AudioSink {
    public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
    public static final int SINK_FORMAT_SUPPORTED_DIRECTLY = 2;
    public static final int SINK_FORMAT_SUPPORTED_WITH_TRANSCODING = 1;
    public static final int SINK_FORMAT_UNSUPPORTED = 0;

    public interface Listener {
        default void onAudioSinkError(Exception audioSinkError) {
        }

        default void onOffloadBufferEmptying() {
        }

        default void onOffloadBufferFull(long bufferEmptyingDeadlineMs) {
        }

        default void onPositionAdvancing(long playoutStartSystemTimeMs) {
        }

        void onPositionDiscontinuity();

        void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled);

        void onUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SinkFormatSupport {
    }

    void configure(Format inputFormat, int specifiedBufferSize, int[] outputChannels) throws ConfigurationException;

    void disableTunneling();

    void enableTunnelingV21();

    void experimentalFlushWithoutAudioTrackRelease();

    void flush();

    long getCurrentPositionUs(boolean sourceEnded);

    int getFormatSupport(Format format);

    PlaybackParameters getPlaybackParameters();

    boolean getSkipSilenceEnabled();

    boolean handleBuffer(ByteBuffer buffer, long presentationTimeUs, int encodedAccessUnitCount) throws WriteException, InitializationException;

    void handleDiscontinuity();

    boolean hasPendingData();

    boolean isEnded();

    void pause();

    void play();

    void playToEndOfStream() throws WriteException;

    void reset();

    void setAudioAttributes(AudioAttributes audioAttributes);

    void setAudioSessionId(int audioSessionId);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setListener(Listener listener);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setSkipSilenceEnabled(boolean skipSilenceEnabled);

    void setVolume(float volume);

    boolean supportsFormat(Format format);

    public static final class ConfigurationException extends Exception {
        public final Format format;

        public ConfigurationException(Throwable cause, Format format) {
            super(cause);
            this.format = format;
        }

        public ConfigurationException(String message, Format format) {
            super(message);
            this.format = format;
        }
    }

    public static final class InitializationException extends Exception {
        public final int audioTrackState;
        public final Format format;
        public final boolean isRecoverable;

        /* JADX WARN: Illegal instructions before constructor call */
        public InitializationException(int audioTrackState, int sampleRate, int channelConfig, int bufferSize, Format format, boolean isRecoverable, Exception audioTrackException) {
            String str = isRecoverable ? " (recoverable)" : "";
            super(new StringBuilder(str.length() + 80).append("AudioTrack init failed ").append(audioTrackState).append(" Config(").append(sampleRate).append(", ").append(channelConfig).append(", ").append(bufferSize).append(")").append(str).toString(), audioTrackException);
            this.audioTrackState = audioTrackState;
            this.isRecoverable = isRecoverable;
            this.format = format;
        }
    }

    public static final class WriteException extends Exception {
        public final int errorCode;
        public final Format format;
        public final boolean isRecoverable;

        public WriteException(int errorCode, Format format, boolean isRecoverable) {
            super(new StringBuilder(36).append("AudioTrack write failed: ").append(errorCode).toString());
            this.isRecoverable = isRecoverable;
            this.errorCode = errorCode;
            this.format = format;
        }
    }

    public static final class UnexpectedDiscontinuityException extends Exception {
        public final long actualPresentationTimeUs;
        public final long expectedPresentationTimeUs;

        public UnexpectedDiscontinuityException(long actualPresentationTimeUs, long expectedPresentationTimeUs) {
            super(new StringBuilder(103).append("Unexpected audio track timestamp discontinuity: expected ").append(expectedPresentationTimeUs).append(", got ").append(actualPresentationTimeUs).toString());
            this.actualPresentationTimeUs = actualPresentationTimeUs;
            this.expectedPresentationTimeUs = expectedPresentationTimeUs;
        }
    }
}
