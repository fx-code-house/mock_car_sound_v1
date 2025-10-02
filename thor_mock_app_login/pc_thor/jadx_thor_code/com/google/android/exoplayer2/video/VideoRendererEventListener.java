package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.os.SystemClock;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public interface VideoRendererEventListener {
    default void onDroppedFrames(int count, long elapsedMs) {
    }

    default void onRenderedFirstFrame(Object output, long renderTimeMs) {
    }

    default void onVideoCodecError(Exception videoCodecError) {
    }

    default void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    }

    default void onVideoDecoderReleased(String decoderName) {
    }

    default void onVideoDisabled(DecoderCounters counters) {
    }

    default void onVideoEnabled(DecoderCounters counters) {
    }

    default void onVideoFrameProcessingOffset(long totalProcessingOffsetUs, int frameCount) {
    }

    @Deprecated
    default void onVideoInputFormatChanged(Format format) {
    }

    default void onVideoInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
    }

    default void onVideoSizeChanged(VideoSize videoSize) {
    }

    public static final class EventDispatcher {
        private final Handler handler;
        private final VideoRendererEventListener listener;

        public EventDispatcher(Handler handler, VideoRendererEventListener listener) {
            this.handler = listener != null ? (Handler) Assertions.checkNotNull(handler) : null;
            this.listener = listener;
        }

        public void enabled(final DecoderCounters decoderCounters) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m219x14ecf85(decoderCounters);
                    }
                });
            }
        }

        /* renamed from: lambda$enabled$0$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m219x14ecf85(DecoderCounters decoderCounters) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoEnabled(decoderCounters);
        }

        public void decoderInitialized(final String decoderName, final long initializedTimestampMs, final long initializationDurationMs) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m215xe61837fb(decoderName, initializedTimestampMs, initializationDurationMs);
                    }
                });
            }
        }

        /* renamed from: lambda$decoderInitialized$1$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m215xe61837fb(String str, long j, long j2) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoDecoderInitialized(str, j, j2);
        }

        public void inputFormatChanged(final Format format, final DecoderReuseEvaluation decoderReuseEvaluation) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m220xbe305117(format, decoderReuseEvaluation);
                    }
                });
            }
        }

        /* renamed from: lambda$inputFormatChanged$2$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m220xbe305117(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoInputFormatChanged(format);
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        public void droppedFrames(final int droppedFrameCount, final long elapsedMs) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m218xb0fc5cbd(droppedFrameCount, elapsedMs);
                    }
                });
            }
        }

        /* renamed from: lambda$droppedFrames$3$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m218xb0fc5cbd(int i, long j) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onDroppedFrames(i, j);
        }

        public void reportVideoFrameProcessingOffset(final long totalProcessingOffsetUs, final int frameCount) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m222x6dc4981c(totalProcessingOffsetUs, frameCount);
                    }
                });
            }
        }

        /* renamed from: lambda$reportVideoFrameProcessingOffset$4$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m222x6dc4981c(long j, int i) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoFrameProcessingOffset(j, i);
        }

        public void videoSizeChanged(final VideoSize videoSize) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m224x75d86d2f(videoSize);
                    }
                });
            }
        }

        /* renamed from: lambda$videoSizeChanged$5$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m224x75d86d2f(VideoSize videoSize) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoSizeChanged(videoSize);
        }

        public void renderedFirstFrame(final Object output) {
            if (this.handler != null) {
                final long jElapsedRealtime = SystemClock.elapsedRealtime();
                this.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m221xc9a6e54(output, jElapsedRealtime);
                    }
                });
            }
        }

        /* renamed from: lambda$renderedFirstFrame$6$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m221xc9a6e54(Object obj, long j) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onRenderedFirstFrame(obj, j);
        }

        public void decoderReleased(final String decoderName) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m216x4f63d3e(decoderName);
                    }
                });
            }
        }

        /* renamed from: lambda$decoderReleased$7$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m216x4f63d3e(String str) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoDecoderReleased(str);
        }

        public void disabled(final DecoderCounters counters) {
            counters.ensureUpdated();
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m217x16b53fc8(counters);
                    }
                });
            }
        }

        /* renamed from: lambda$disabled$8$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m217x16b53fc8(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoDisabled(decoderCounters);
        }

        public void videoCodecError(final Exception videoCodecError) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m223xd2363db0(videoCodecError);
                    }
                });
            }
        }

        /* renamed from: lambda$videoCodecError$9$com-google-android-exoplayer2-video-VideoRendererEventListener$EventDispatcher, reason: not valid java name */
        /* synthetic */ void m223xd2363db0(Exception exc) {
            ((VideoRendererEventListener) Util.castNonNull(this.listener)).onVideoCodecError(exc);
        }
    }
}
