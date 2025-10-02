package com.google.android.exoplayer2.analytics;

import android.os.Looper;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public class AnalyticsCollector implements Player.Listener, AudioRendererEventListener, VideoRendererEventListener, MediaSourceEventListener, BandwidthMeter.EventListener, DrmSessionEventListener {
    private final Clock clock;
    private final SparseArray<AnalyticsListener.EventTime> eventTimes;
    private HandlerWrapper handler;
    private boolean isSeeking;
    private ListenerSet<AnalyticsListener> listeners;
    private final MediaPeriodQueueTracker mediaPeriodQueueTracker;
    private final Timeline.Period period;
    private Player player;
    private final Timeline.Window window;

    static /* synthetic */ void lambda$new$0(AnalyticsListener analyticsListener, FlagSet flagSet) {
    }

    public AnalyticsCollector(Clock clock) {
        this.clock = (Clock) Assertions.checkNotNull(clock);
        this.listeners = new ListenerSet<>(Util.getCurrentOrMainLooper(), clock, new ListenerSet.IterationFinishedEvent() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda8
            @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                AnalyticsCollector.lambda$new$0((AnalyticsListener) obj, flagSet);
            }
        });
        Timeline.Period period = new Timeline.Period();
        this.period = period;
        this.window = new Timeline.Window();
        this.mediaPeriodQueueTracker = new MediaPeriodQueueTracker(period);
        this.eventTimes = new SparseArray<>();
    }

    public void addListener(AnalyticsListener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.add(listener);
    }

    public void removeListener(AnalyticsListener listener) {
        this.listeners.remove(listener);
    }

    public void setPlayer(final Player player, Looper looper) {
        Assertions.checkState(this.player == null || this.mediaPeriodQueueTracker.mediaPeriodQueue.isEmpty());
        this.player = (Player) Assertions.checkNotNull(player);
        this.handler = this.clock.createHandler(looper, null);
        this.listeners = this.listeners.copy(looper, new ListenerSet.IterationFinishedEvent() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda9
            @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                this.f$0.m139x59ac8031(player, (AnalyticsListener) obj, flagSet);
            }
        });
    }

    /* renamed from: lambda$setPlayer$1$com-google-android-exoplayer2-analytics-AnalyticsCollector, reason: not valid java name */
    /* synthetic */ void m139x59ac8031(Player player, AnalyticsListener analyticsListener, FlagSet flagSet) {
        analyticsListener.onEvents(player, new AnalyticsListener.Events(flagSet, this.eventTimes));
    }

    public void release() {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        this.eventTimes.put(AnalyticsListener.EVENT_PLAYER_RELEASED, eventTimeGenerateCurrentPlayerMediaPeriodEventTime);
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, AnalyticsListener.EVENT_PLAYER_RELEASED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda31
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlayerReleased(eventTimeGenerateCurrentPlayerMediaPeriodEventTime);
            }
        });
        ((HandlerWrapper) Assertions.checkStateNotNull(this.handler)).post(new Runnable() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m138xb36c0e8b();
            }
        });
    }

    /* renamed from: lambda$release$3$com-google-android-exoplayer2-analytics-AnalyticsCollector, reason: not valid java name */
    /* synthetic */ void m138xb36c0e8b() {
        this.listeners.release();
    }

    public final void updateMediaPeriodQueueInfo(List<MediaSource.MediaPeriodId> queue, MediaSource.MediaPeriodId readingPeriod) {
        this.mediaPeriodQueueTracker.onQueueUpdated(queue, readingPeriod, (Player) Assertions.checkNotNull(this.player));
    }

    public final void notifySeekStarted() {
        if (this.isSeeking) {
            return;
        }
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        this.isSeeking = true;
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, -1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda40
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekStarted(eventTimeGenerateCurrentPlayerMediaPeriodEventTime);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioEnabled(final DecoderCounters counters) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1008, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda18
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onAudioEnabled$5(eventTimeGenerateReadingMediaPeriodEventTime, counters, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onAudioEnabled$5(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioEnabled(eventTime, decoderCounters);
        analyticsListener.onDecoderEnabled(eventTime, 1, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDecoderInitialized(final String decoderName, final long initializedTimestampMs, final long initializationDurationMs) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1009, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda26
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onAudioDecoderInitialized$6(eventTimeGenerateReadingMediaPeriodEventTime, decoderName, initializationDurationMs, initializedTimestampMs, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onAudioDecoderInitialized$6(AnalyticsListener.EventTime eventTime, String str, long j, long j2, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioDecoderInitialized(eventTime, str, j);
        analyticsListener.onAudioDecoderInitialized(eventTime, str, j2, j);
        analyticsListener.onDecoderInitialized(eventTime, 1, str, j);
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioInputFormatChanged(final Format format, final DecoderReuseEvaluation decoderReuseEvaluation) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1010, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda15
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onAudioInputFormatChanged$7(eventTimeGenerateReadingMediaPeriodEventTime, format, decoderReuseEvaluation, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onAudioInputFormatChanged$7(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioInputFormatChanged(eventTime, format);
        analyticsListener.onAudioInputFormatChanged(eventTime, format, decoderReuseEvaluation);
        analyticsListener.onDecoderInputFormatChanged(eventTime, 1, format);
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioPositionAdvancing(final long playoutStartSystemTimeMs) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1011, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda58
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioPositionAdvancing(eventTimeGenerateReadingMediaPeriodEventTime, playoutStartSystemTimeMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioUnderrun(final int bufferSize, final long bufferSizeMs, final long elapsedSinceLastFeedMs) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1012, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda54
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioUnderrun(eventTimeGenerateReadingMediaPeriodEventTime, bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDecoderReleased(final String decoderName) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1013, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda34
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioDecoderReleased(eventTimeGenerateReadingMediaPeriodEventTime, decoderName);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDisabled(final DecoderCounters counters) {
        final AnalyticsListener.EventTime eventTimeGeneratePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        sendEvent(eventTimeGeneratePlayingMediaPeriodEventTime, 1014, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda61
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onAudioDisabled$11(eventTimeGeneratePlayingMediaPeriodEventTime, counters, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onAudioDisabled$11(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioDisabled(eventTime, decoderCounters);
        analyticsListener.onDecoderDisabled(eventTime, 1, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onSkipSilenceEnabledChanged(final boolean skipSilenceEnabled) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1017, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda22
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSkipSilenceEnabledChanged(eventTimeGenerateReadingMediaPeriodEventTime, skipSilenceEnabled);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioSinkError(final Exception audioSinkError) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1018, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda43
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioSinkError(eventTimeGenerateReadingMediaPeriodEventTime, audioSinkError);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioCodecError(final Exception audioCodecError) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, AnalyticsListener.EVENT_AUDIO_CODEC_ERROR, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda56
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioCodecError(eventTimeGenerateReadingMediaPeriodEventTime, audioCodecError);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onAudioSessionIdChanged(final int audioSessionId) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1015, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda53
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioSessionIdChanged(eventTimeGenerateReadingMediaPeriodEventTime, audioSessionId);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onAudioAttributesChanged(final AudioAttributes audioAttributes) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1016, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda41
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioAttributesChanged(eventTimeGenerateReadingMediaPeriodEventTime, audioAttributes);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onVolumeChanged(final float volume) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1019, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda39
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVolumeChanged(eventTimeGenerateReadingMediaPeriodEventTime, volume);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoEnabled(final DecoderCounters counters) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1020, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda47
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onVideoEnabled$18(eventTimeGenerateReadingMediaPeriodEventTime, counters, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onVideoEnabled$18(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoEnabled(eventTime, decoderCounters);
        analyticsListener.onDecoderEnabled(eventTime, 2, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDecoderInitialized(final String decoderName, final long initializedTimestampMs, final long initializationDurationMs) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1021, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda60
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onVideoDecoderInitialized$19(eventTimeGenerateReadingMediaPeriodEventTime, decoderName, initializationDurationMs, initializedTimestampMs, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onVideoDecoderInitialized$19(AnalyticsListener.EventTime eventTime, String str, long j, long j2, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoDecoderInitialized(eventTime, str, j);
        analyticsListener.onVideoDecoderInitialized(eventTime, str, j2, j);
        analyticsListener.onDecoderInitialized(eventTime, 2, str, j);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoInputFormatChanged(final Format format, final DecoderReuseEvaluation decoderReuseEvaluation) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, AnalyticsListener.EVENT_VIDEO_INPUT_FORMAT_CHANGED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda5
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onVideoInputFormatChanged$20(eventTimeGenerateReadingMediaPeriodEventTime, format, decoderReuseEvaluation, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onVideoInputFormatChanged$20(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoInputFormatChanged(eventTime, format);
        analyticsListener.onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation);
        analyticsListener.onDecoderInputFormatChanged(eventTime, 2, format);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onDroppedFrames(final int count, final long elapsedMs) {
        final AnalyticsListener.EventTime eventTimeGeneratePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        sendEvent(eventTimeGeneratePlayingMediaPeriodEventTime, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda62
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDroppedVideoFrames(eventTimeGeneratePlayingMediaPeriodEventTime, count, elapsedMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDecoderReleased(final String decoderName) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, 1024, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda2
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVideoDecoderReleased(eventTimeGenerateReadingMediaPeriodEventTime, decoderName);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDisabled(final DecoderCounters counters) {
        final AnalyticsListener.EventTime eventTimeGeneratePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        sendEvent(eventTimeGeneratePlayingMediaPeriodEventTime, 1025, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda29
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onVideoDisabled$23(eventTimeGeneratePlayingMediaPeriodEventTime, counters, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onVideoDisabled$23(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoDisabled(eventTime, decoderCounters);
        analyticsListener.onDecoderDisabled(eventTime, 2, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
    public final void onVideoSizeChanged(final VideoSize videoSize) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, AnalyticsListener.EVENT_VIDEO_SIZE_CHANGED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda44
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onVideoSizeChanged$24(eventTimeGenerateReadingMediaPeriodEventTime, videoSize, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onVideoSizeChanged$24(AnalyticsListener.EventTime eventTime, VideoSize videoSize, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoSizeChanged(eventTime, videoSize);
        analyticsListener.onVideoSizeChanged(eventTime, videoSize.width, videoSize.height, videoSize.unappliedRotationDegrees, videoSize.pixelWidthHeightRatio);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onRenderedFirstFrame(final Object output, final long renderTimeMs) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, AnalyticsListener.EVENT_RENDERED_FIRST_FRAME, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda6
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onRenderedFirstFrame(eventTimeGenerateReadingMediaPeriodEventTime, output, renderTimeMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoFrameProcessingOffset(final long totalProcessingOffsetUs, final int frameCount) {
        final AnalyticsListener.EventTime eventTimeGeneratePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        sendEvent(eventTimeGeneratePlayingMediaPeriodEventTime, AnalyticsListener.EVENT_VIDEO_FRAME_PROCESSING_OFFSET, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda10
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVideoFrameProcessingOffset(eventTimeGeneratePlayingMediaPeriodEventTime, totalProcessingOffsetUs, frameCount);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoCodecError(final Exception videoCodecError) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, AnalyticsListener.EVENT_VIDEO_CODEC_ERROR, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda36
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVideoCodecError(eventTimeGenerateReadingMediaPeriodEventTime, videoCodecError);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
    public void onSurfaceSizeChanged(final int width, final int height) {
        final AnalyticsListener.EventTime eventTimeGenerateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateReadingMediaPeriodEventTime, AnalyticsListener.EVENT_SURFACE_SIZE_CHANGED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda14
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSurfaceSizeChanged(eventTimeGenerateReadingMediaPeriodEventTime, width, height);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, 1000, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda20
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadStarted(eventTimeGenerateMediaPeriodEventTime, loadEventInfo, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadCompleted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, 1001, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda30
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadCompleted(eventTimeGenerateMediaPeriodEventTime, loadEventInfo, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadCanceled(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, 1002, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda55
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadCanceled(eventTimeGenerateMediaPeriodEventTime, loadEventInfo, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData, final IOException error, final boolean wasCanceled) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, 1003, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda16
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadError(eventTimeGenerateMediaPeriodEventTime, loadEventInfo, mediaLoadData, error, wasCanceled);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, 1005, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda37
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onUpstreamDiscarded(eventTimeGenerateMediaPeriodEventTime, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onDownstreamFormatChanged(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, 1004, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda52
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDownstreamFormatChanged(eventTimeGenerateMediaPeriodEventTime, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onTimelineChanged(Timeline timeline, final int reason) {
        this.mediaPeriodQueueTracker.onTimelineChanged((Player) Assertions.checkNotNull(this.player));
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 0, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda4
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onTimelineChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, reason);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onMediaItemTransition(final MediaItem mediaItem, final int reason) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda21
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMediaItemTransition(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, mediaItem, reason);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onTracksChanged(final TrackGroupArray trackGroups, final TrackSelectionArray trackSelections) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda59
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onTracksChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, trackGroups, trackSelections);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    @Deprecated
    public final void onStaticMetadataChanged(final List<Metadata> metadataList) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 3, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda19
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onStaticMetadataChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, metadataList);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onIsLoadingChanged(final boolean isLoading) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 4, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda45
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onIsLoadingChanged$39(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, isLoading, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onIsLoadingChanged$39(AnalyticsListener.EventTime eventTime, boolean z, AnalyticsListener analyticsListener) {
        analyticsListener.onLoadingChanged(eventTime, z);
        analyticsListener.onIsLoadingChanged(eventTime, z);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onAvailableCommandsChanged(final Player.Commands availableCommands) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 14, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda49
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAvailableCommandsChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, availableCommands);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlayerStateChanged(final boolean playWhenReady, final int playbackState) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, -1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda1
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlayerStateChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, playWhenReady, playbackState);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackStateChanged(final int playbackState) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 5, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda50
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaybackStateChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, playbackState);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlayWhenReadyChanged(final boolean playWhenReady, final int reason) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 6, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda63
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlayWhenReadyChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, playWhenReady, reason);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackSuppressionReasonChanged(final int playbackSuppressionReason) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 7, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda33
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaybackSuppressionReasonChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, playbackSuppressionReason);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onIsPlayingChanged(final boolean isPlaying) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 8, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda57
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onIsPlayingChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, isPlaying);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onRepeatModeChanged(final int repeatMode) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 9, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda64
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onRepeatModeChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, repeatMode);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onShuffleModeEnabledChanged(final boolean shuffleModeEnabled) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda17
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onShuffleModeChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, shuffleModeEnabled);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onPlayerError(final com.google.android.exoplayer2.PlaybackException r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof com.google.android.exoplayer2.ExoPlaybackException
            if (r0 == 0) goto L17
            r0 = r3
            com.google.android.exoplayer2.ExoPlaybackException r0 = (com.google.android.exoplayer2.ExoPlaybackException) r0
            com.google.android.exoplayer2.source.MediaPeriodId r1 = r0.mediaPeriodId
            if (r1 == 0) goto L17
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = new com.google.android.exoplayer2.source.MediaSource$MediaPeriodId
            com.google.android.exoplayer2.source.MediaPeriodId r0 = r0.mediaPeriodId
            r1.<init>(r0)
            com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime r0 = r2.generateEventTime(r1)
            goto L18
        L17:
            r0 = 0
        L18:
            if (r0 != 0) goto L1e
            com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime r0 = r2.generateCurrentPlayerMediaPeriodEventTime()
        L1e:
            com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda11 r1 = new com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda11
            r1.<init>()
            r3 = 11
            r2.sendEvent(r0, r3, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.analytics.AnalyticsCollector.onPlayerError(com.google.android.exoplayer2.PlaybackException):void");
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(final Player.PositionInfo oldPosition, final Player.PositionInfo newPosition, final int reason) {
        if (reason == 1) {
            this.isSeeking = false;
        }
        this.mediaPeriodQueueTracker.onPositionDiscontinuity((Player) Assertions.checkNotNull(this.player));
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 12, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda42
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onPositionDiscontinuity$49(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, reason, oldPosition, newPosition, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onPositionDiscontinuity$49(AnalyticsListener.EventTime eventTime, int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, AnalyticsListener analyticsListener) {
        analyticsListener.onPositionDiscontinuity(eventTime, i);
        analyticsListener.onPositionDiscontinuity(eventTime, positionInfo, positionInfo2, i);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackParametersChanged(final PlaybackParameters playbackParameters) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 13, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda35
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaybackParametersChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, playbackParameters);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onSeekBackIncrementChanged(final long seekBackIncrementMs) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 17, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda3
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekBackIncrementChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, seekBackIncrementMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onSeekForwardIncrementChanged(final long seekForwardIncrementMs) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 18, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda23
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekForwardIncrementChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, seekForwardIncrementMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onMaxSeekToPreviousPositionChanged(final int maxSeekToPreviousPositionMs) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 19, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda46
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMaxSeekToPreviousPositionChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, maxSeekToPreviousPositionMs);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onMediaMetadataChanged(final MediaMetadata mediaMetadata) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda28
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMediaMetadataChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, mediaMetadata);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onPlaylistMetadataChanged(final MediaMetadata playlistMetadata) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 16, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda48
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaylistMetadataChanged(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, playlistMetadata);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.metadata.MetadataOutput
    public final void onMetadata(final Metadata metadata) {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, 1007, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda12
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMetadata(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, metadata);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onSeekProcessed() {
        final AnalyticsListener.EventTime eventTimeGenerateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(eventTimeGenerateCurrentPlayerMediaPeriodEventTime, -1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekProcessed(eventTimeGenerateCurrentPlayerMediaPeriodEventTime);
            }
        });
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter.EventListener
    public final void onBandwidthSample(final int elapsedMs, final long bytesTransferred, final long bitrateEstimate) {
        final AnalyticsListener.EventTime eventTimeGenerateLoadingMediaPeriodEventTime = generateLoadingMediaPeriodEventTime();
        sendEvent(eventTimeGenerateLoadingMediaPeriodEventTime, 1006, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda38
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onBandwidthEstimate(eventTimeGenerateLoadingMediaPeriodEventTime, elapsedMs, bytesTransferred, bitrateEstimate);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmSessionAcquired(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final int state) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, AnalyticsListener.EVENT_DRM_SESSION_ACQUIRED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda27
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.lambda$onDrmSessionAcquired$59(eventTimeGenerateMediaPeriodEventTime, state, (AnalyticsListener) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onDrmSessionAcquired$59(AnalyticsListener.EventTime eventTime, int i, AnalyticsListener analyticsListener) {
        analyticsListener.onDrmSessionAcquired(eventTime);
        analyticsListener.onDrmSessionAcquired(eventTime, i);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmKeysLoaded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, AnalyticsListener.EVENT_DRM_KEYS_LOADED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda13
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmKeysLoaded(eventTimeGenerateMediaPeriodEventTime);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmSessionManagerError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, final Exception error) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, AnalyticsListener.EVENT_DRM_SESSION_MANAGER_ERROR, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda25
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmSessionManagerError(eventTimeGenerateMediaPeriodEventTime, error);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmKeysRestored(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, AnalyticsListener.EVENT_DRM_KEYS_RESTORED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda24
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmKeysRestored(eventTimeGenerateMediaPeriodEventTime);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmKeysRemoved(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, AnalyticsListener.EVENT_DRM_KEYS_REMOVED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda7
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmKeysRemoved(eventTimeGenerateMediaPeriodEventTime);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmSessionReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime eventTimeGenerateMediaPeriodEventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        sendEvent(eventTimeGenerateMediaPeriodEventTime, AnalyticsListener.EVENT_DRM_SESSION_RELEASED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.AnalyticsCollector$$ExternalSyntheticLambda51
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmSessionReleased(eventTimeGenerateMediaPeriodEventTime);
            }
        });
    }

    protected final void sendEvent(AnalyticsListener.EventTime eventTime, int eventFlag, ListenerSet.Event<AnalyticsListener> eventInvocation) {
        this.eventTimes.put(eventFlag, eventTime);
        this.listeners.sendEvent(eventFlag, eventInvocation);
    }

    protected final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getCurrentPlayerMediaPeriod());
    }

    @RequiresNonNull({"player"})
    protected final AnalyticsListener.EventTime generateEventTime(Timeline timeline, int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        long contentPosition;
        MediaSource.MediaPeriodId mediaPeriodId2 = timeline.isEmpty() ? null : mediaPeriodId;
        long jElapsedRealtime = this.clock.elapsedRealtime();
        boolean z = timeline.equals(this.player.getCurrentTimeline()) && windowIndex == this.player.getCurrentWindowIndex();
        long defaultPositionMs = 0;
        if (mediaPeriodId2 != null && mediaPeriodId2.isAd()) {
            if (z && this.player.getCurrentAdGroupIndex() == mediaPeriodId2.adGroupIndex && this.player.getCurrentAdIndexInAdGroup() == mediaPeriodId2.adIndexInAdGroup) {
                defaultPositionMs = this.player.getCurrentPosition();
            }
        } else {
            if (z) {
                contentPosition = this.player.getContentPosition();
                return new AnalyticsListener.EventTime(jElapsedRealtime, timeline, windowIndex, mediaPeriodId2, contentPosition, this.player.getCurrentTimeline(), this.player.getCurrentWindowIndex(), this.mediaPeriodQueueTracker.getCurrentPlayerMediaPeriod(), this.player.getCurrentPosition(), this.player.getTotalBufferedDuration());
            }
            if (!timeline.isEmpty()) {
                defaultPositionMs = timeline.getWindow(windowIndex, this.window).getDefaultPositionMs();
            }
        }
        contentPosition = defaultPositionMs;
        return new AnalyticsListener.EventTime(jElapsedRealtime, timeline, windowIndex, mediaPeriodId2, contentPosition, this.player.getCurrentTimeline(), this.player.getCurrentWindowIndex(), this.mediaPeriodQueueTracker.getCurrentPlayerMediaPeriod(), this.player.getCurrentPosition(), this.player.getTotalBufferedDuration());
    }

    private AnalyticsListener.EventTime generateEventTime(MediaSource.MediaPeriodId mediaPeriodId) {
        Assertions.checkNotNull(this.player);
        Timeline mediaPeriodIdTimeline = mediaPeriodId == null ? null : this.mediaPeriodQueueTracker.getMediaPeriodIdTimeline(mediaPeriodId);
        if (mediaPeriodId == null || mediaPeriodIdTimeline == null) {
            int currentWindowIndex = this.player.getCurrentWindowIndex();
            Timeline currentTimeline = this.player.getCurrentTimeline();
            if (!(currentWindowIndex < currentTimeline.getWindowCount())) {
                currentTimeline = Timeline.EMPTY;
            }
            return generateEventTime(currentTimeline, currentWindowIndex, null);
        }
        return generateEventTime(mediaPeriodIdTimeline, mediaPeriodIdTimeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, mediaPeriodId);
    }

    private AnalyticsListener.EventTime generatePlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getPlayingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateReadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getReadingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateLoadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLoadingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateMediaPeriodEventTime(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        Assertions.checkNotNull(this.player);
        if (mediaPeriodId != null) {
            if (this.mediaPeriodQueueTracker.getMediaPeriodIdTimeline(mediaPeriodId) != null) {
                return generateEventTime(mediaPeriodId);
            }
            return generateEventTime(Timeline.EMPTY, windowIndex, mediaPeriodId);
        }
        Timeline currentTimeline = this.player.getCurrentTimeline();
        if (!(windowIndex < currentTimeline.getWindowCount())) {
            currentTimeline = Timeline.EMPTY;
        }
        return generateEventTime(currentTimeline, windowIndex, null);
    }

    private static final class MediaPeriodQueueTracker {
        private MediaSource.MediaPeriodId currentPlayerMediaPeriod;
        private ImmutableList<MediaSource.MediaPeriodId> mediaPeriodQueue = ImmutableList.of();
        private ImmutableMap<MediaSource.MediaPeriodId, Timeline> mediaPeriodTimelines = ImmutableMap.of();
        private final Timeline.Period period;
        private MediaSource.MediaPeriodId playingMediaPeriod;
        private MediaSource.MediaPeriodId readingMediaPeriod;

        public MediaPeriodQueueTracker(Timeline.Period period) {
            this.period = period;
        }

        public MediaSource.MediaPeriodId getCurrentPlayerMediaPeriod() {
            return this.currentPlayerMediaPeriod;
        }

        public MediaSource.MediaPeriodId getPlayingMediaPeriod() {
            return this.playingMediaPeriod;
        }

        public MediaSource.MediaPeriodId getReadingMediaPeriod() {
            return this.readingMediaPeriod;
        }

        public MediaSource.MediaPeriodId getLoadingMediaPeriod() {
            if (this.mediaPeriodQueue.isEmpty()) {
                return null;
            }
            return (MediaSource.MediaPeriodId) Iterables.getLast(this.mediaPeriodQueue);
        }

        public Timeline getMediaPeriodIdTimeline(MediaSource.MediaPeriodId mediaPeriodId) {
            return this.mediaPeriodTimelines.get(mediaPeriodId);
        }

        public void onPositionDiscontinuity(Player player) {
            this.currentPlayerMediaPeriod = findCurrentPlayerMediaPeriodInQueue(player, this.mediaPeriodQueue, this.playingMediaPeriod, this.period);
        }

        public void onTimelineChanged(Player player) {
            this.currentPlayerMediaPeriod = findCurrentPlayerMediaPeriodInQueue(player, this.mediaPeriodQueue, this.playingMediaPeriod, this.period);
            updateMediaPeriodTimelines(player.getCurrentTimeline());
        }

        public void onQueueUpdated(List<MediaSource.MediaPeriodId> queue, MediaSource.MediaPeriodId readingPeriod, Player player) {
            this.mediaPeriodQueue = ImmutableList.copyOf((Collection) queue);
            if (!queue.isEmpty()) {
                this.playingMediaPeriod = queue.get(0);
                this.readingMediaPeriod = (MediaSource.MediaPeriodId) Assertions.checkNotNull(readingPeriod);
            }
            if (this.currentPlayerMediaPeriod == null) {
                this.currentPlayerMediaPeriod = findCurrentPlayerMediaPeriodInQueue(player, this.mediaPeriodQueue, this.playingMediaPeriod, this.period);
            }
            updateMediaPeriodTimelines(player.getCurrentTimeline());
        }

        private void updateMediaPeriodTimelines(Timeline preferredTimeline) {
            ImmutableMap.Builder<MediaSource.MediaPeriodId, Timeline> builder = ImmutableMap.builder();
            if (this.mediaPeriodQueue.isEmpty()) {
                addTimelineForMediaPeriodId(builder, this.playingMediaPeriod, preferredTimeline);
                if (!Objects.equal(this.readingMediaPeriod, this.playingMediaPeriod)) {
                    addTimelineForMediaPeriodId(builder, this.readingMediaPeriod, preferredTimeline);
                }
                if (!Objects.equal(this.currentPlayerMediaPeriod, this.playingMediaPeriod) && !Objects.equal(this.currentPlayerMediaPeriod, this.readingMediaPeriod)) {
                    addTimelineForMediaPeriodId(builder, this.currentPlayerMediaPeriod, preferredTimeline);
                }
            } else {
                for (int i = 0; i < this.mediaPeriodQueue.size(); i++) {
                    addTimelineForMediaPeriodId(builder, this.mediaPeriodQueue.get(i), preferredTimeline);
                }
                if (!this.mediaPeriodQueue.contains(this.currentPlayerMediaPeriod)) {
                    addTimelineForMediaPeriodId(builder, this.currentPlayerMediaPeriod, preferredTimeline);
                }
            }
            this.mediaPeriodTimelines = builder.build();
        }

        private void addTimelineForMediaPeriodId(ImmutableMap.Builder<MediaSource.MediaPeriodId, Timeline> mediaPeriodTimelinesBuilder, MediaSource.MediaPeriodId mediaPeriodId, Timeline preferredTimeline) {
            if (mediaPeriodId == null) {
                return;
            }
            if (preferredTimeline.getIndexOfPeriod(mediaPeriodId.periodUid) != -1) {
                mediaPeriodTimelinesBuilder.put(mediaPeriodId, preferredTimeline);
                return;
            }
            Timeline timeline = this.mediaPeriodTimelines.get(mediaPeriodId);
            if (timeline != null) {
                mediaPeriodTimelinesBuilder.put(mediaPeriodId, timeline);
            }
        }

        private static MediaSource.MediaPeriodId findCurrentPlayerMediaPeriodInQueue(Player player, ImmutableList<MediaSource.MediaPeriodId> mediaPeriodQueue, MediaSource.MediaPeriodId playingMediaPeriod, Timeline.Period period) {
            Timeline currentTimeline = player.getCurrentTimeline();
            int currentPeriodIndex = player.getCurrentPeriodIndex();
            Object uidOfPeriod = currentTimeline.isEmpty() ? null : currentTimeline.getUidOfPeriod(currentPeriodIndex);
            int adGroupIndexAfterPositionUs = (player.isPlayingAd() || currentTimeline.isEmpty()) ? -1 : currentTimeline.getPeriod(currentPeriodIndex, period).getAdGroupIndexAfterPositionUs(C.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs());
            for (int i = 0; i < mediaPeriodQueue.size(); i++) {
                MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodQueue.get(i);
                if (isMatchingMediaPeriod(mediaPeriodId, uidOfPeriod, player.isPlayingAd(), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adGroupIndexAfterPositionUs)) {
                    return mediaPeriodId;
                }
            }
            if (mediaPeriodQueue.isEmpty() && playingMediaPeriod != null) {
                if (isMatchingMediaPeriod(playingMediaPeriod, uidOfPeriod, player.isPlayingAd(), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adGroupIndexAfterPositionUs)) {
                    return playingMediaPeriod;
                }
            }
            return null;
        }

        private static boolean isMatchingMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, Object playerPeriodUid, boolean isPlayingAd, int playerAdGroupIndex, int playerAdIndexInAdGroup, int playerNextAdGroupIndex) {
            if (mediaPeriodId.periodUid.equals(playerPeriodUid)) {
                return (isPlayingAd && mediaPeriodId.adGroupIndex == playerAdGroupIndex && mediaPeriodId.adIndexInAdGroup == playerAdIndexInAdGroup) || (!isPlayingAd && mediaPeriodId.adGroupIndex == -1 && mediaPeriodId.nextAdGroupIndex == playerNextAdGroupIndex);
            }
            return false;
        }
    }
}
