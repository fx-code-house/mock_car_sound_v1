package com.google.android.exoplayer2.analytics;

import android.util.SparseArray;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes.dex */
public interface AnalyticsListener {
    public static final int EVENT_AUDIO_ATTRIBUTES_CHANGED = 1016;
    public static final int EVENT_AUDIO_CODEC_ERROR = 1037;
    public static final int EVENT_AUDIO_DECODER_INITIALIZED = 1009;
    public static final int EVENT_AUDIO_DECODER_RELEASED = 1013;
    public static final int EVENT_AUDIO_DISABLED = 1014;
    public static final int EVENT_AUDIO_ENABLED = 1008;
    public static final int EVENT_AUDIO_INPUT_FORMAT_CHANGED = 1010;
    public static final int EVENT_AUDIO_POSITION_ADVANCING = 1011;
    public static final int EVENT_AUDIO_SESSION_ID = 1015;
    public static final int EVENT_AUDIO_SINK_ERROR = 1018;
    public static final int EVENT_AUDIO_UNDERRUN = 1012;
    public static final int EVENT_AVAILABLE_COMMANDS_CHANGED = 14;
    public static final int EVENT_BANDWIDTH_ESTIMATE = 1006;
    public static final int EVENT_DOWNSTREAM_FORMAT_CHANGED = 1004;
    public static final int EVENT_DRM_KEYS_LOADED = 1031;
    public static final int EVENT_DRM_KEYS_REMOVED = 1034;
    public static final int EVENT_DRM_KEYS_RESTORED = 1033;
    public static final int EVENT_DRM_SESSION_ACQUIRED = 1030;
    public static final int EVENT_DRM_SESSION_MANAGER_ERROR = 1032;
    public static final int EVENT_DRM_SESSION_RELEASED = 1035;
    public static final int EVENT_DROPPED_VIDEO_FRAMES = 1023;
    public static final int EVENT_IS_LOADING_CHANGED = 4;
    public static final int EVENT_IS_PLAYING_CHANGED = 8;
    public static final int EVENT_LOAD_CANCELED = 1002;
    public static final int EVENT_LOAD_COMPLETED = 1001;
    public static final int EVENT_LOAD_ERROR = 1003;
    public static final int EVENT_LOAD_STARTED = 1000;
    public static final int EVENT_MAX_SEEK_TO_PREVIOUS_POSITION_CHANGED = 19;
    public static final int EVENT_MEDIA_ITEM_TRANSITION = 1;
    public static final int EVENT_MEDIA_METADATA_CHANGED = 15;
    public static final int EVENT_METADATA = 1007;
    public static final int EVENT_PLAYBACK_PARAMETERS_CHANGED = 13;
    public static final int EVENT_PLAYBACK_STATE_CHANGED = 5;
    public static final int EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED = 7;
    public static final int EVENT_PLAYER_ERROR = 11;
    public static final int EVENT_PLAYER_RELEASED = 1036;
    public static final int EVENT_PLAYLIST_METADATA_CHANGED = 16;
    public static final int EVENT_PLAY_WHEN_READY_CHANGED = 6;
    public static final int EVENT_POSITION_DISCONTINUITY = 12;
    public static final int EVENT_RENDERED_FIRST_FRAME = 1027;
    public static final int EVENT_REPEAT_MODE_CHANGED = 9;
    public static final int EVENT_SEEK_BACK_INCREMENT_CHANGED = 17;
    public static final int EVENT_SEEK_FORWARD_INCREMENT_CHANGED = 18;
    public static final int EVENT_SHUFFLE_MODE_ENABLED_CHANGED = 10;
    public static final int EVENT_SKIP_SILENCE_ENABLED_CHANGED = 1017;

    @Deprecated
    public static final int EVENT_STATIC_METADATA_CHANGED = 3;
    public static final int EVENT_SURFACE_SIZE_CHANGED = 1029;
    public static final int EVENT_TIMELINE_CHANGED = 0;
    public static final int EVENT_TRACKS_CHANGED = 2;
    public static final int EVENT_UPSTREAM_DISCARDED = 1005;
    public static final int EVENT_VIDEO_CODEC_ERROR = 1038;
    public static final int EVENT_VIDEO_DECODER_INITIALIZED = 1021;
    public static final int EVENT_VIDEO_DECODER_RELEASED = 1024;
    public static final int EVENT_VIDEO_DISABLED = 1025;
    public static final int EVENT_VIDEO_ENABLED = 1020;
    public static final int EVENT_VIDEO_FRAME_PROCESSING_OFFSET = 1026;
    public static final int EVENT_VIDEO_INPUT_FORMAT_CHANGED = 1022;
    public static final int EVENT_VIDEO_SIZE_CHANGED = 1028;
    public static final int EVENT_VOLUME_CHANGED = 1019;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventFlags {
    }

    default void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes) {
    }

    default void onAudioCodecError(EventTime eventTime, Exception audioCodecError) {
    }

    @Deprecated
    default void onAudioDecoderInitialized(EventTime eventTime, String decoderName, long initializationDurationMs) {
    }

    default void onAudioDecoderInitialized(EventTime eventTime, String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    }

    default void onAudioDecoderReleased(EventTime eventTime, String decoderName) {
    }

    default void onAudioDisabled(EventTime eventTime, DecoderCounters decoderCounters) {
    }

    default void onAudioEnabled(EventTime eventTime, DecoderCounters decoderCounters) {
    }

    @Deprecated
    default void onAudioInputFormatChanged(EventTime eventTime, Format format) {
    }

    default void onAudioInputFormatChanged(EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
    }

    default void onAudioPositionAdvancing(EventTime eventTime, long playoutStartSystemTimeMs) {
    }

    default void onAudioSessionIdChanged(EventTime eventTime, int audioSessionId) {
    }

    default void onAudioSinkError(EventTime eventTime, Exception audioSinkError) {
    }

    default void onAudioUnderrun(EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    }

    default void onAvailableCommandsChanged(EventTime eventTime, Player.Commands availableCommands) {
    }

    default void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
    }

    @Deprecated
    default void onDecoderDisabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
    }

    @Deprecated
    default void onDecoderEnabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
    }

    @Deprecated
    default void onDecoderInitialized(EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {
    }

    @Deprecated
    default void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {
    }

    default void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData) {
    }

    default void onDrmKeysLoaded(EventTime eventTime) {
    }

    default void onDrmKeysRemoved(EventTime eventTime) {
    }

    default void onDrmKeysRestored(EventTime eventTime) {
    }

    @Deprecated
    default void onDrmSessionAcquired(EventTime eventTime) {
    }

    default void onDrmSessionAcquired(EventTime eventTime, int state) {
    }

    default void onDrmSessionManagerError(EventTime eventTime, Exception error) {
    }

    default void onDrmSessionReleased(EventTime eventTime) {
    }

    default void onDroppedVideoFrames(EventTime eventTime, int droppedFrames, long elapsedMs) {
    }

    default void onEvents(Player player, Events events) {
    }

    default void onIsLoadingChanged(EventTime eventTime, boolean isLoading) {
    }

    default void onIsPlayingChanged(EventTime eventTime, boolean isPlaying) {
    }

    default void onLoadCanceled(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onLoadCompleted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onLoadError(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
    }

    default void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Deprecated
    default void onLoadingChanged(EventTime eventTime, boolean isLoading) {
    }

    default void onMaxSeekToPreviousPositionChanged(EventTime eventTime, int maxSeekToPreviousPositionMs) {
    }

    default void onMediaItemTransition(EventTime eventTime, MediaItem mediaItem, int reason) {
    }

    default void onMediaMetadataChanged(EventTime eventTime, MediaMetadata mediaMetadata) {
    }

    default void onMetadata(EventTime eventTime, Metadata metadata) {
    }

    default void onPlayWhenReadyChanged(EventTime eventTime, boolean playWhenReady, int reason) {
    }

    default void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
    }

    default void onPlaybackStateChanged(EventTime eventTime, int state) {
    }

    default void onPlaybackSuppressionReasonChanged(EventTime eventTime, int playbackSuppressionReason) {
    }

    default void onPlayerError(EventTime eventTime, PlaybackException error) {
    }

    default void onPlayerReleased(EventTime eventTime) {
    }

    @Deprecated
    default void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
    }

    default void onPlaylistMetadataChanged(EventTime eventTime, MediaMetadata playlistMetadata) {
    }

    @Deprecated
    default void onPositionDiscontinuity(EventTime eventTime, int reason) {
    }

    default void onPositionDiscontinuity(EventTime eventTime, Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
    }

    default void onRenderedFirstFrame(EventTime eventTime, Object output, long renderTimeMs) {
    }

    default void onRepeatModeChanged(EventTime eventTime, int repeatMode) {
    }

    default void onSeekBackIncrementChanged(EventTime eventTime, long seekBackIncrementMs) {
    }

    default void onSeekForwardIncrementChanged(EventTime eventTime, long seekForwardIncrementMs) {
    }

    @Deprecated
    default void onSeekProcessed(EventTime eventTime) {
    }

    @Deprecated
    default void onSeekStarted(EventTime eventTime) {
    }

    default void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {
    }

    default void onSkipSilenceEnabledChanged(EventTime eventTime, boolean skipSilenceEnabled) {
    }

    @Deprecated
    default void onStaticMetadataChanged(EventTime eventTime, List<Metadata> metadataList) {
    }

    default void onSurfaceSizeChanged(EventTime eventTime, int width, int height) {
    }

    default void onTimelineChanged(EventTime eventTime, int reason) {
    }

    default void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    default void onUpstreamDiscarded(EventTime eventTime, MediaLoadData mediaLoadData) {
    }

    default void onVideoCodecError(EventTime eventTime, Exception videoCodecError) {
    }

    @Deprecated
    default void onVideoDecoderInitialized(EventTime eventTime, String decoderName, long initializationDurationMs) {
    }

    default void onVideoDecoderInitialized(EventTime eventTime, String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    }

    default void onVideoDecoderReleased(EventTime eventTime, String decoderName) {
    }

    default void onVideoDisabled(EventTime eventTime, DecoderCounters decoderCounters) {
    }

    default void onVideoEnabled(EventTime eventTime, DecoderCounters decoderCounters) {
    }

    default void onVideoFrameProcessingOffset(EventTime eventTime, long totalProcessingOffsetUs, int frameCount) {
    }

    @Deprecated
    default void onVideoInputFormatChanged(EventTime eventTime, Format format) {
    }

    default void onVideoInputFormatChanged(EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
    }

    @Deprecated
    default void onVideoSizeChanged(EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
    }

    default void onVideoSizeChanged(EventTime eventTime, VideoSize videoSize) {
    }

    default void onVolumeChanged(EventTime eventTime, float volume) {
    }

    public static final class Events {
        private final SparseArray<EventTime> eventTimes;
        private final FlagSet flags;

        public Events(FlagSet flags, SparseArray<EventTime> eventTimes) {
            this.flags = flags;
            SparseArray<EventTime> sparseArray = new SparseArray<>(flags.size());
            for (int i = 0; i < flags.size(); i++) {
                int i2 = flags.get(i);
                sparseArray.append(i2, (EventTime) Assertions.checkNotNull(eventTimes.get(i2)));
            }
            this.eventTimes = sparseArray;
        }

        public EventTime getEventTime(int event) {
            return (EventTime) Assertions.checkNotNull(this.eventTimes.get(event));
        }

        public boolean contains(int event) {
            return this.flags.contains(event);
        }

        public boolean containsAny(int... events) {
            return this.flags.containsAny(events);
        }

        public int size() {
            return this.flags.size();
        }

        public int get(int index) {
            return this.flags.get(index);
        }
    }

    public static final class EventTime {
        public final MediaSource.MediaPeriodId currentMediaPeriodId;
        public final long currentPlaybackPositionMs;
        public final Timeline currentTimeline;
        public final int currentWindowIndex;
        public final long eventPlaybackPositionMs;
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final long realtimeMs;
        public final Timeline timeline;
        public final long totalBufferedDurationMs;
        public final int windowIndex;

        public EventTime(long realtimeMs, Timeline timeline, int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, long eventPlaybackPositionMs, Timeline currentTimeline, int currentWindowIndex, MediaSource.MediaPeriodId currentMediaPeriodId, long currentPlaybackPositionMs, long totalBufferedDurationMs) {
            this.realtimeMs = realtimeMs;
            this.timeline = timeline;
            this.windowIndex = windowIndex;
            this.mediaPeriodId = mediaPeriodId;
            this.eventPlaybackPositionMs = eventPlaybackPositionMs;
            this.currentTimeline = currentTimeline;
            this.currentWindowIndex = currentWindowIndex;
            this.currentMediaPeriodId = currentMediaPeriodId;
            this.currentPlaybackPositionMs = currentPlaybackPositionMs;
            this.totalBufferedDurationMs = totalBufferedDurationMs;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EventTime eventTime = (EventTime) o;
            return this.realtimeMs == eventTime.realtimeMs && this.windowIndex == eventTime.windowIndex && this.eventPlaybackPositionMs == eventTime.eventPlaybackPositionMs && this.currentWindowIndex == eventTime.currentWindowIndex && this.currentPlaybackPositionMs == eventTime.currentPlaybackPositionMs && this.totalBufferedDurationMs == eventTime.totalBufferedDurationMs && Objects.equal(this.timeline, eventTime.timeline) && Objects.equal(this.mediaPeriodId, eventTime.mediaPeriodId) && Objects.equal(this.currentTimeline, eventTime.currentTimeline) && Objects.equal(this.currentMediaPeriodId, eventTime.currentMediaPeriodId);
        }

        public int hashCode() {
            return Objects.hashCode(Long.valueOf(this.realtimeMs), this.timeline, Integer.valueOf(this.windowIndex), this.mediaPeriodId, Long.valueOf(this.eventPlaybackPositionMs), this.currentTimeline, Integer.valueOf(this.currentWindowIndex), this.currentMediaPeriodId, Long.valueOf(this.currentPlaybackPositionMs), Long.valueOf(this.totalBufferedDurationMs));
        }
    }
}
