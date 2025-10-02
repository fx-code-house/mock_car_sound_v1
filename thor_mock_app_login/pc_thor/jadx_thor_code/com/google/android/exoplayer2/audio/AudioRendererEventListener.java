package com.google.android.exoplayer2.audio;

import android.os.Handler;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public interface AudioRendererEventListener {
    default void onAudioCodecError(Exception audioCodecError) {
    }

    default void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    }

    default void onAudioDecoderReleased(String decoderName) {
    }

    default void onAudioDisabled(DecoderCounters counters) {
    }

    default void onAudioEnabled(DecoderCounters counters) {
    }

    @Deprecated
    default void onAudioInputFormatChanged(Format format) {
    }

    default void onAudioInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
    }

    default void onAudioPositionAdvancing(long playoutStartSystemTimeMs) {
    }

    default void onAudioSinkError(Exception audioSinkError) {
    }

    default void onAudioUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    }

    default void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
    }

    public static final class EventDispatcher {
        private final Handler handler;
        private final AudioRendererEventListener listener;

        public EventDispatcher(Handler handler, AudioRendererEventListener listener) {
            this.handler = listener != null ? (Handler) Assertions.checkNotNull(handler) : null;
            this.listener = listener;
        }

        public void enabled(final DecoderCounters decoderCounters) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m145x5024e2cf(decoderCounters);
                    }
                });
            }
        }

        /* renamed from: lambda$enabled$0$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m145x5024e2cf(DecoderCounters decoderCounters) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioEnabled(decoderCounters);
        }

        public void decoderInitialized(final String decoderName, final long initializedTimestampMs, final long initializationDurationMs) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m142x34ee4b45(decoderName, initializedTimestampMs, initializationDurationMs);
                    }
                });
            }
        }

        /* renamed from: lambda$decoderInitialized$1$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m142x34ee4b45(String str, long j, long j2) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioDecoderInitialized(str, j, j2);
        }

        public void inputFormatChanged(final Format format, final DecoderReuseEvaluation decoderReuseEvaluation) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m146xd066461(format, decoderReuseEvaluation);
                    }
                });
            }
        }

        /* renamed from: lambda$inputFormatChanged$2$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m146xd066461(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioInputFormatChanged(format);
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        public void positionAdvancing(final long playoutStartSystemTimeMs) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m147x4b664277(playoutStartSystemTimeMs);
                    }
                });
            }
        }

        /* renamed from: lambda$positionAdvancing$3$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m147x4b664277(long j) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioPositionAdvancing(j);
        }

        public void underrun(final int bufferSize, final long bufferSizeMs, final long elapsedSinceLastFeedMs) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m149x69131a3f(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
                    }
                });
            }
        }

        /* renamed from: lambda$underrun$4$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m149x69131a3f(int i, long j, long j2) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioUnderrun(i, j, j2);
        }

        public void decoderReleased(final String decoderName) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m143x97e584ca(decoderName);
                    }
                });
            }
        }

        /* renamed from: lambda$decoderReleased$5$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m143x97e584ca(String str) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioDecoderReleased(str);
        }

        public void disabled(final DecoderCounters counters) {
            counters.ensureUpdated();
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m144xa9a48754(counters);
                    }
                });
            }
        }

        /* renamed from: lambda$disabled$6$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m144xa9a48754(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioDisabled(decoderCounters);
        }

        public void skipSilenceEnabledChanged(final boolean skipSilenceEnabled) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m148x9104d974(skipSilenceEnabled);
                    }
                });
            }
        }

        /* renamed from: lambda$skipSilenceEnabledChanged$7$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m148x9104d974(boolean z) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onSkipSilenceEnabledChanged(z);
        }

        public void audioSinkError(final Exception audioSinkError) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m141xcf2a89af(audioSinkError);
                    }
                });
            }
        }

        /* renamed from: lambda$audioSinkError$8$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m141xcf2a89af(Exception exc) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioSinkError(exc);
        }

        public void audioCodecError(final Exception audioCodecError) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m140x305f60bf(audioCodecError);
                    }
                });
            }
        }

        /* renamed from: lambda$audioCodecError$9$com-google-android-exoplayer2-audio-AudioRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m140x305f60bf(Exception exc) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioCodecError(exc);
        }
    }
}
