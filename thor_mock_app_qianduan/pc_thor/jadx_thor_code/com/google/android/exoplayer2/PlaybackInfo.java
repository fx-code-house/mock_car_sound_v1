package com.google.android.exoplayer2;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.common.collect.ImmutableList;
import java.util.List;

/* loaded from: classes.dex */
final class PlaybackInfo {
    private static final MediaSource.MediaPeriodId PLACEHOLDER_MEDIA_PERIOD_ID = new MediaSource.MediaPeriodId(new Object());
    public volatile long bufferedPositionUs;
    public final long discontinuityStartPositionUs;
    public final boolean isLoading;
    public final MediaSource.MediaPeriodId loadingMediaPeriodId;
    public final boolean offloadSchedulingEnabled;
    public final MediaSource.MediaPeriodId periodId;
    public final boolean playWhenReady;
    public final ExoPlaybackException playbackError;
    public final PlaybackParameters playbackParameters;
    public final int playbackState;
    public final int playbackSuppressionReason;
    public volatile long positionUs;
    public final long requestedContentPositionUs;
    public final boolean sleepingForOffload;
    public final List<Metadata> staticMetadata;
    public final Timeline timeline;
    public volatile long totalBufferedDurationUs;
    public final TrackGroupArray trackGroups;
    public final TrackSelectorResult trackSelectorResult;

    public static PlaybackInfo createDummy(TrackSelectorResult emptyTrackSelectorResult) {
        Timeline timeline = Timeline.EMPTY;
        MediaSource.MediaPeriodId mediaPeriodId = PLACEHOLDER_MEDIA_PERIOD_ID;
        return new PlaybackInfo(timeline, mediaPeriodId, C.TIME_UNSET, 0L, 1, null, false, TrackGroupArray.EMPTY, emptyTrackSelectorResult, ImmutableList.of(), mediaPeriodId, false, 0, PlaybackParameters.DEFAULT, 0L, 0L, 0L, false, false);
    }

    public PlaybackInfo(Timeline timeline, MediaSource.MediaPeriodId periodId, long requestedContentPositionUs, long discontinuityStartPositionUs, int playbackState, ExoPlaybackException playbackError, boolean isLoading, TrackGroupArray trackGroups, TrackSelectorResult trackSelectorResult, List<Metadata> staticMetadata, MediaSource.MediaPeriodId loadingMediaPeriodId, boolean playWhenReady, int playbackSuppressionReason, PlaybackParameters playbackParameters, long bufferedPositionUs, long totalBufferedDurationUs, long positionUs, boolean offloadSchedulingEnabled, boolean sleepingForOffload) {
        this.timeline = timeline;
        this.periodId = periodId;
        this.requestedContentPositionUs = requestedContentPositionUs;
        this.discontinuityStartPositionUs = discontinuityStartPositionUs;
        this.playbackState = playbackState;
        this.playbackError = playbackError;
        this.isLoading = isLoading;
        this.trackGroups = trackGroups;
        this.trackSelectorResult = trackSelectorResult;
        this.staticMetadata = staticMetadata;
        this.loadingMediaPeriodId = loadingMediaPeriodId;
        this.playWhenReady = playWhenReady;
        this.playbackSuppressionReason = playbackSuppressionReason;
        this.playbackParameters = playbackParameters;
        this.bufferedPositionUs = bufferedPositionUs;
        this.totalBufferedDurationUs = totalBufferedDurationUs;
        this.positionUs = positionUs;
        this.offloadSchedulingEnabled = offloadSchedulingEnabled;
        this.sleepingForOffload = sleepingForOffload;
    }

    public static MediaSource.MediaPeriodId getDummyPeriodForEmptyTimeline() {
        return PLACEHOLDER_MEDIA_PERIOD_ID;
    }

    public PlaybackInfo copyWithNewPosition(MediaSource.MediaPeriodId periodId, long positionUs, long requestedContentPositionUs, long discontinuityStartPositionUs, long totalBufferedDurationUs, TrackGroupArray trackGroups, TrackSelectorResult trackSelectorResult, List<Metadata> staticMetadata) {
        return new PlaybackInfo(this.timeline, periodId, requestedContentPositionUs, discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, trackGroups, trackSelectorResult, staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, totalBufferedDurationUs, positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithTimeline(Timeline timeline) {
        return new PlaybackInfo(timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithPlaybackState(int playbackState) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithPlaybackError(ExoPlaybackException playbackError) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithIsLoading(boolean isLoading) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithLoadingMediaPeriodId(MediaSource.MediaPeriodId loadingMediaPeriodId) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithPlayWhenReady(boolean playWhenReady, int playbackSuppressionReason) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, playWhenReady, playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithPlaybackParameters(PlaybackParameters playbackParameters) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithOffloadSchedulingEnabled(boolean offloadSchedulingEnabled) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, offloadSchedulingEnabled, this.sleepingForOffload);
    }

    public PlaybackInfo copyWithSleepingForOffload(boolean sleepingForOffload) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, sleepingForOffload);
    }
}
