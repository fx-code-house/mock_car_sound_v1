package com.google.android.exoplayer2.analytics;

import android.os.SystemClock;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager;
import com.google.android.exoplayer2.analytics.PlaybackStats;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class PlaybackStatsListener implements AnalyticsListener, PlaybackSessionManager.Listener {
    private Format audioFormat;
    private long bandwidthBytes;
    private long bandwidthTimeMs;
    private final Callback callback;
    private long discontinuityFromPositionMs;
    private String discontinuityFromSession;
    private int discontinuityReason;
    private int droppedFrames;
    private PlaybackStats finishedPlaybackStats;
    private final boolean keepHistory;
    private Exception nonFatalException;
    private final Timeline.Period period;
    private final Map<String, PlaybackStatsTracker> playbackStatsTrackers;
    private final PlaybackSessionManager sessionManager;
    private final Map<String, AnalyticsListener.EventTime> sessionStartEventTimes;
    private Format videoFormat;
    private VideoSize videoSize;

    public interface Callback {
        void onPlaybackStatsReady(AnalyticsListener.EventTime eventTime, PlaybackStats playbackStats);
    }

    public PlaybackStatsListener(boolean keepHistory, Callback callback) {
        this.callback = callback;
        this.keepHistory = keepHistory;
        DefaultPlaybackSessionManager defaultPlaybackSessionManager = new DefaultPlaybackSessionManager();
        this.sessionManager = defaultPlaybackSessionManager;
        this.playbackStatsTrackers = new HashMap();
        this.sessionStartEventTimes = new HashMap();
        this.finishedPlaybackStats = PlaybackStats.EMPTY;
        this.period = new Timeline.Period();
        this.videoSize = VideoSize.UNKNOWN;
        defaultPlaybackSessionManager.setListener(this);
    }

    public PlaybackStats getCombinedPlaybackStats() {
        int i = 1;
        PlaybackStats[] playbackStatsArr = new PlaybackStats[this.playbackStatsTrackers.size() + 1];
        playbackStatsArr[0] = this.finishedPlaybackStats;
        Iterator<PlaybackStatsTracker> it = this.playbackStatsTrackers.values().iterator();
        while (it.hasNext()) {
            playbackStatsArr[i] = it.next().build(false);
            i++;
        }
        return PlaybackStats.merge(playbackStatsArr);
    }

    public PlaybackStats getPlaybackStats() {
        String activeSessionId = this.sessionManager.getActiveSessionId();
        PlaybackStatsTracker playbackStatsTracker = activeSessionId == null ? null : this.playbackStatsTrackers.get(activeSessionId);
        if (playbackStatsTracker == null) {
            return null;
        }
        return playbackStatsTracker.build(false);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onSessionCreated(AnalyticsListener.EventTime eventTime, String sessionId) {
        this.playbackStatsTrackers.put(sessionId, new PlaybackStatsTracker(this.keepHistory, eventTime));
        this.sessionStartEventTimes.put(sessionId, eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onSessionActive(AnalyticsListener.EventTime eventTime, String sessionId) {
        ((PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.get(sessionId))).onForeground();
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onAdPlaybackStarted(AnalyticsListener.EventTime eventTime, String contentSessionId, String adSessionId) {
        ((PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.get(contentSessionId))).onInterruptedByAd();
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onSessionFinished(AnalyticsListener.EventTime eventTime, String sessionId, boolean automaticTransitionToNextPlayback) {
        PlaybackStatsTracker playbackStatsTracker = (PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.remove(sessionId));
        AnalyticsListener.EventTime eventTime2 = (AnalyticsListener.EventTime) Assertions.checkNotNull(this.sessionStartEventTimes.remove(sessionId));
        playbackStatsTracker.onFinished(eventTime, automaticTransitionToNextPlayback, sessionId.equals(this.discontinuityFromSession) ? this.discontinuityFromPositionMs : C.TIME_UNSET);
        PlaybackStats playbackStatsBuild = playbackStatsTracker.build(true);
        this.finishedPlaybackStats = PlaybackStats.merge(this.finishedPlaybackStats, playbackStatsBuild);
        Callback callback = this.callback;
        if (callback != null) {
            callback.onPlaybackStatsReady(eventTime2, playbackStatsBuild);
        }
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
        if (this.discontinuityFromSession == null) {
            this.discontinuityFromSession = this.sessionManager.getActiveSessionId();
            this.discontinuityFromPositionMs = oldPosition.positionMs;
        }
        this.discontinuityReason = reason;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int droppedFrames, long elapsedMs) {
        this.droppedFrames = droppedFrames;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        this.nonFatalException = error;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception error) {
        this.nonFatalException = error;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
        this.bandwidthTimeMs = totalLoadTimeMs;
        this.bandwidthBytes = totalBytesLoaded;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        if (mediaLoadData.trackType == 2 || mediaLoadData.trackType == 0) {
            this.videoFormat = mediaLoadData.trackFormat;
        } else if (mediaLoadData.trackType == 1) {
            this.audioFormat = mediaLoadData.trackFormat;
        }
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        this.videoSize = videoSize;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onEvents(Player player, AnalyticsListener.Events events) {
        if (events.size() == 0) {
            return;
        }
        maybeAddSessions(events);
        for (String str : this.playbackStatsTrackers.keySet()) {
            Pair<AnalyticsListener.EventTime, Boolean> pairFindBestEventTime = findBestEventTime(events, str);
            PlaybackStatsTracker playbackStatsTracker = this.playbackStatsTrackers.get(str);
            boolean zHasEvent = hasEvent(events, str, 12);
            boolean zHasEvent2 = hasEvent(events, str, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES);
            boolean zHasEvent3 = hasEvent(events, str, 1012);
            boolean zHasEvent4 = hasEvent(events, str, 1000);
            boolean zHasEvent5 = hasEvent(events, str, 11);
            boolean z = hasEvent(events, str, 1003) || hasEvent(events, str, AnalyticsListener.EVENT_DRM_SESSION_MANAGER_ERROR);
            boolean zHasEvent6 = hasEvent(events, str, 1006);
            boolean zHasEvent7 = hasEvent(events, str, 1004);
            playbackStatsTracker.onEvents(player, (AnalyticsListener.EventTime) pairFindBestEventTime.first, ((Boolean) pairFindBestEventTime.second).booleanValue(), str.equals(this.discontinuityFromSession) ? this.discontinuityFromPositionMs : C.TIME_UNSET, zHasEvent, zHasEvent2 ? this.droppedFrames : 0, zHasEvent3, zHasEvent4, zHasEvent5 ? player.getPlayerError() : null, z ? this.nonFatalException : null, zHasEvent6 ? this.bandwidthTimeMs : 0L, zHasEvent6 ? this.bandwidthBytes : 0L, zHasEvent7 ? this.videoFormat : null, zHasEvent7 ? this.audioFormat : null, hasEvent(events, str, AnalyticsListener.EVENT_VIDEO_SIZE_CHANGED) ? this.videoSize : null);
        }
        this.videoFormat = null;
        this.audioFormat = null;
        this.discontinuityFromSession = null;
        if (events.contains(AnalyticsListener.EVENT_PLAYER_RELEASED)) {
            this.sessionManager.finishAllSessions(events.getEventTime(AnalyticsListener.EVENT_PLAYER_RELEASED));
        }
    }

    private void maybeAddSessions(AnalyticsListener.Events events) {
        for (int i = 0; i < events.size(); i++) {
            int i2 = events.get(i);
            AnalyticsListener.EventTime eventTime = events.getEventTime(i2);
            if (i2 == 0) {
                this.sessionManager.updateSessionsWithTimelineChange(eventTime);
            } else if (i2 == 12) {
                this.sessionManager.updateSessionsWithDiscontinuity(eventTime, this.discontinuityReason);
            } else {
                this.sessionManager.updateSessions(eventTime);
            }
        }
    }

    private Pair<AnalyticsListener.EventTime, Boolean> findBestEventTime(AnalyticsListener.Events events, String session) {
        AnalyticsListener.EventTime eventTime = null;
        boolean zBelongsToSession = false;
        for (int i = 0; i < events.size(); i++) {
            AnalyticsListener.EventTime eventTime2 = events.getEventTime(events.get(i));
            boolean zBelongsToSession2 = this.sessionManager.belongsToSession(eventTime2, session);
            if (eventTime == null || ((zBelongsToSession2 && !zBelongsToSession) || (zBelongsToSession2 == zBelongsToSession && eventTime2.realtimeMs > eventTime.realtimeMs))) {
                eventTime = eventTime2;
                zBelongsToSession = zBelongsToSession2;
            }
        }
        Assertions.checkNotNull(eventTime);
        if (!zBelongsToSession && eventTime.mediaPeriodId != null && eventTime.mediaPeriodId.isAd()) {
            long adGroupTimeUs = eventTime.timeline.getPeriodByUid(eventTime.mediaPeriodId.periodUid, this.period).getAdGroupTimeUs(eventTime.mediaPeriodId.adGroupIndex);
            if (adGroupTimeUs == Long.MIN_VALUE) {
                adGroupTimeUs = this.period.durationUs;
            }
            AnalyticsListener.EventTime eventTime3 = new AnalyticsListener.EventTime(eventTime.realtimeMs, eventTime.timeline, eventTime.windowIndex, new MediaSource.MediaPeriodId(eventTime.mediaPeriodId.periodUid, eventTime.mediaPeriodId.windowSequenceNumber, eventTime.mediaPeriodId.adGroupIndex), C.usToMs(adGroupTimeUs + this.period.getPositionInWindowUs()), eventTime.timeline, eventTime.currentWindowIndex, eventTime.currentMediaPeriodId, eventTime.currentPlaybackPositionMs, eventTime.totalBufferedDurationMs);
            zBelongsToSession = this.sessionManager.belongsToSession(eventTime3, session);
            eventTime = eventTime3;
        }
        return Pair.create(eventTime, Boolean.valueOf(zBelongsToSession));
    }

    private boolean hasEvent(AnalyticsListener.Events events, String session, int event) {
        return events.contains(event) && this.sessionManager.belongsToSession(events.getEventTime(event), session);
    }

    private static final class PlaybackStatsTracker {
        private long audioFormatBitrateTimeProduct;
        private final List<PlaybackStats.EventTimeAndFormat> audioFormatHistory;
        private long audioFormatTimeMs;
        private long audioUnderruns;
        private long bandwidthBytes;
        private long bandwidthTimeMs;
        private Format currentAudioFormat;
        private float currentPlaybackSpeed;
        private int currentPlaybackState;
        private long currentPlaybackStateStartTimeMs;
        private Format currentVideoFormat;
        private long droppedFrames;
        private int fatalErrorCount;
        private final List<PlaybackStats.EventTimeAndException> fatalErrorHistory;
        private long firstReportedTimeMs;
        private boolean hasBeenReady;
        private boolean hasEnded;
        private boolean hasFatalError;
        private long initialAudioFormatBitrate;
        private long initialVideoFormatBitrate;
        private int initialVideoFormatHeight;
        private final boolean isAd;
        private boolean isForeground;
        private boolean isInterruptedByAd;
        private boolean isJoinTimeInvalid;
        private boolean isSeeking;
        private final boolean keepHistory;
        private long lastAudioFormatStartTimeMs;
        private long lastRebufferStartTimeMs;
        private long lastVideoFormatStartTimeMs;
        private long maxRebufferTimeMs;
        private final List<long[]> mediaTimeHistory;
        private int nonFatalErrorCount;
        private final List<PlaybackStats.EventTimeAndException> nonFatalErrorHistory;
        private int pauseBufferCount;
        private int pauseCount;
        private final long[] playbackStateDurationsMs = new long[16];
        private final List<PlaybackStats.EventTimeAndPlaybackState> playbackStateHistory;
        private int rebufferCount;
        private int seekCount;
        private boolean startedLoading;
        private long videoFormatBitrateTimeMs;
        private long videoFormatBitrateTimeProduct;
        private long videoFormatHeightTimeMs;
        private long videoFormatHeightTimeProduct;
        private final List<PlaybackStats.EventTimeAndFormat> videoFormatHistory;

        private static boolean isInvalidJoinTransition(int oldState, int newState) {
            return ((oldState != 1 && oldState != 2 && oldState != 14) || newState == 1 || newState == 2 || newState == 14 || newState == 3 || newState == 4 || newState == 9 || newState == 11) ? false : true;
        }

        private static boolean isPausedState(int state) {
            return state == 4 || state == 7;
        }

        private static boolean isReadyState(int state) {
            return state == 3 || state == 4 || state == 9;
        }

        private static boolean isRebufferingState(int state) {
            return state == 6 || state == 7 || state == 10;
        }

        public PlaybackStatsTracker(boolean keepHistory, AnalyticsListener.EventTime startTime) {
            this.keepHistory = keepHistory;
            this.playbackStateHistory = keepHistory ? new ArrayList<>() : Collections.emptyList();
            this.mediaTimeHistory = keepHistory ? new ArrayList<>() : Collections.emptyList();
            this.videoFormatHistory = keepHistory ? new ArrayList<>() : Collections.emptyList();
            this.audioFormatHistory = keepHistory ? new ArrayList<>() : Collections.emptyList();
            this.fatalErrorHistory = keepHistory ? new ArrayList<>() : Collections.emptyList();
            this.nonFatalErrorHistory = keepHistory ? new ArrayList<>() : Collections.emptyList();
            boolean z = false;
            this.currentPlaybackState = 0;
            this.currentPlaybackStateStartTimeMs = startTime.realtimeMs;
            this.firstReportedTimeMs = C.TIME_UNSET;
            this.maxRebufferTimeMs = C.TIME_UNSET;
            if (startTime.mediaPeriodId != null && startTime.mediaPeriodId.isAd()) {
                z = true;
            }
            this.isAd = z;
            this.initialAudioFormatBitrate = -1L;
            this.initialVideoFormatBitrate = -1L;
            this.initialVideoFormatHeight = -1;
            this.currentPlaybackSpeed = 1.0f;
        }

        public void onForeground() {
            this.isForeground = true;
        }

        public void onInterruptedByAd() {
            this.isInterruptedByAd = true;
            this.isSeeking = false;
        }

        public void onFinished(AnalyticsListener.EventTime eventTime, boolean automaticTransition, long discontinuityFromPositionMs) {
            int i = 11;
            if (this.currentPlaybackState != 11 && !automaticTransition) {
                i = 15;
            }
            maybeUpdateMediaTimeHistory(eventTime.realtimeMs, discontinuityFromPositionMs);
            maybeRecordVideoFormatTime(eventTime.realtimeMs);
            maybeRecordAudioFormatTime(eventTime.realtimeMs);
            updatePlaybackState(i, eventTime);
        }

        public void onEvents(Player player, AnalyticsListener.EventTime eventTime, boolean belongsToPlayback, long discontinuityFromPositionMs, boolean hasDiscontinuity, int droppedFrameCount, boolean hasAudioUnderun, boolean startedLoading, PlaybackException fatalError, Exception nonFatalException, long bandwidthTimeMs, long bandwidthBytes, Format videoFormat, Format audioFormat, VideoSize videoSize) {
            if (discontinuityFromPositionMs != C.TIME_UNSET) {
                maybeUpdateMediaTimeHistory(eventTime.realtimeMs, discontinuityFromPositionMs);
                this.isSeeking = true;
            }
            if (player.getPlaybackState() != 2) {
                this.isSeeking = false;
            }
            int playbackState = player.getPlaybackState();
            if (playbackState == 1 || playbackState == 4 || hasDiscontinuity) {
                this.isInterruptedByAd = false;
            }
            if (fatalError != null) {
                this.hasFatalError = true;
                this.fatalErrorCount++;
                if (this.keepHistory) {
                    this.fatalErrorHistory.add(new PlaybackStats.EventTimeAndException(eventTime, fatalError));
                }
            } else if (player.getPlayerError() == null) {
                this.hasFatalError = false;
            }
            if (this.isForeground && !this.isInterruptedByAd) {
                boolean z = false;
                boolean z2 = false;
                for (TrackSelection trackSelection : player.getCurrentTrackSelections().getAll()) {
                    if (trackSelection != null && trackSelection.length() > 0) {
                        int trackType = MimeTypes.getTrackType(trackSelection.getFormat(0).sampleMimeType);
                        if (trackType == 2) {
                            z = true;
                        } else if (trackType == 1) {
                            z2 = true;
                        }
                    }
                }
                if (!z) {
                    maybeUpdateVideoFormat(eventTime, null);
                }
                if (!z2) {
                    maybeUpdateAudioFormat(eventTime, null);
                }
            }
            if (videoFormat != null) {
                maybeUpdateVideoFormat(eventTime, videoFormat);
            }
            if (audioFormat != null) {
                maybeUpdateAudioFormat(eventTime, audioFormat);
            }
            Format format = this.currentVideoFormat;
            if (format != null && format.height == -1 && videoSize != null) {
                maybeUpdateVideoFormat(eventTime, this.currentVideoFormat.buildUpon().setWidth(videoSize.width).setHeight(videoSize.height).build());
            }
            if (startedLoading) {
                this.startedLoading = true;
            }
            if (hasAudioUnderun) {
                this.audioUnderruns++;
            }
            this.droppedFrames += droppedFrameCount;
            this.bandwidthTimeMs += bandwidthTimeMs;
            this.bandwidthBytes += bandwidthBytes;
            if (nonFatalException != null) {
                this.nonFatalErrorCount++;
                if (this.keepHistory) {
                    this.nonFatalErrorHistory.add(new PlaybackStats.EventTimeAndException(eventTime, nonFatalException));
                }
            }
            int iResolveNewPlaybackState = resolveNewPlaybackState(player);
            float f = player.getPlaybackParameters().speed;
            if (this.currentPlaybackState != iResolveNewPlaybackState || this.currentPlaybackSpeed != f) {
                maybeUpdateMediaTimeHistory(eventTime.realtimeMs, belongsToPlayback ? eventTime.eventPlaybackPositionMs : C.TIME_UNSET);
                maybeRecordVideoFormatTime(eventTime.realtimeMs);
                maybeRecordAudioFormatTime(eventTime.realtimeMs);
            }
            this.currentPlaybackSpeed = f;
            if (this.currentPlaybackState != iResolveNewPlaybackState) {
                updatePlaybackState(iResolveNewPlaybackState, eventTime);
            }
        }

        public PlaybackStats build(boolean z) {
            long[] jArr;
            List<long[]> list;
            long[] jArr2 = this.playbackStateDurationsMs;
            List<long[]> list2 = this.mediaTimeHistory;
            if (z) {
                jArr = jArr2;
                list = list2;
            } else {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                long[] jArrCopyOf = Arrays.copyOf(this.playbackStateDurationsMs, 16);
                long jMax = Math.max(0L, jElapsedRealtime - this.currentPlaybackStateStartTimeMs);
                int i = this.currentPlaybackState;
                jArrCopyOf[i] = jArrCopyOf[i] + jMax;
                maybeUpdateMaxRebufferTimeMs(jElapsedRealtime);
                maybeRecordVideoFormatTime(jElapsedRealtime);
                maybeRecordAudioFormatTime(jElapsedRealtime);
                ArrayList arrayList = new ArrayList(this.mediaTimeHistory);
                if (this.keepHistory && this.currentPlaybackState == 3) {
                    arrayList.add(guessMediaTimeBasedOnElapsedRealtime(jElapsedRealtime));
                }
                jArr = jArrCopyOf;
                list = arrayList;
            }
            int i2 = (this.isJoinTimeInvalid || !this.hasBeenReady) ? 1 : 0;
            long j = i2 != 0 ? C.TIME_UNSET : jArr[2];
            int i3 = jArr[1] > 0 ? 1 : 0;
            List arrayList2 = z ? this.videoFormatHistory : new ArrayList(this.videoFormatHistory);
            List arrayList3 = z ? this.audioFormatHistory : new ArrayList(this.audioFormatHistory);
            List arrayList4 = z ? this.playbackStateHistory : new ArrayList(this.playbackStateHistory);
            long j2 = this.firstReportedTimeMs;
            boolean z2 = this.isForeground;
            int i4 = !this.hasBeenReady ? 1 : 0;
            boolean z3 = this.hasEnded;
            int i5 = i2 ^ 1;
            int i6 = this.pauseCount;
            int i7 = this.pauseBufferCount;
            int i8 = this.seekCount;
            int i9 = this.rebufferCount;
            long j3 = this.maxRebufferTimeMs;
            boolean z4 = this.isAd;
            long[] jArr3 = jArr;
            long j4 = this.videoFormatHeightTimeMs;
            long j5 = this.videoFormatHeightTimeProduct;
            long j6 = this.videoFormatBitrateTimeMs;
            long j7 = this.videoFormatBitrateTimeProduct;
            long j8 = this.audioFormatTimeMs;
            long j9 = this.audioFormatBitrateTimeProduct;
            int i10 = this.initialVideoFormatHeight;
            int i11 = i10 == -1 ? 0 : 1;
            long j10 = this.initialVideoFormatBitrate;
            int i12 = j10 == -1 ? 0 : 1;
            long j11 = this.initialAudioFormatBitrate;
            int i13 = j11 == -1 ? 0 : 1;
            long j12 = this.bandwidthTimeMs;
            long j13 = this.bandwidthBytes;
            long j14 = this.droppedFrames;
            long j15 = this.audioUnderruns;
            int i14 = this.fatalErrorCount;
            return new PlaybackStats(1, jArr3, arrayList4, list, j2, z2 ? 1 : 0, i4, z3 ? 1 : 0, i3, j, i5, i6, i7, i8, i9, j3, z4 ? 1 : 0, arrayList2, arrayList3, j4, j5, j6, j7, j8, j9, i11, i12, i10, j10, i13, j11, j12, j13, j14, j15, i14 > 0 ? 1 : 0, i14, this.nonFatalErrorCount, this.fatalErrorHistory, this.nonFatalErrorHistory);
        }

        private void updatePlaybackState(int newPlaybackState, AnalyticsListener.EventTime eventTime) {
            Assertions.checkArgument(eventTime.realtimeMs >= this.currentPlaybackStateStartTimeMs);
            long j = eventTime.realtimeMs - this.currentPlaybackStateStartTimeMs;
            long[] jArr = this.playbackStateDurationsMs;
            int i = this.currentPlaybackState;
            jArr[i] = jArr[i] + j;
            if (this.firstReportedTimeMs == C.TIME_UNSET) {
                this.firstReportedTimeMs = eventTime.realtimeMs;
            }
            this.isJoinTimeInvalid |= isInvalidJoinTransition(this.currentPlaybackState, newPlaybackState);
            this.hasBeenReady |= isReadyState(newPlaybackState);
            this.hasEnded |= newPlaybackState == 11;
            if (!isPausedState(this.currentPlaybackState) && isPausedState(newPlaybackState)) {
                this.pauseCount++;
            }
            if (newPlaybackState == 5) {
                this.seekCount++;
            }
            if (!isRebufferingState(this.currentPlaybackState) && isRebufferingState(newPlaybackState)) {
                this.rebufferCount++;
                this.lastRebufferStartTimeMs = eventTime.realtimeMs;
            }
            if (isRebufferingState(this.currentPlaybackState) && this.currentPlaybackState != 7 && newPlaybackState == 7) {
                this.pauseBufferCount++;
            }
            maybeUpdateMaxRebufferTimeMs(eventTime.realtimeMs);
            this.currentPlaybackState = newPlaybackState;
            this.currentPlaybackStateStartTimeMs = eventTime.realtimeMs;
            if (this.keepHistory) {
                this.playbackStateHistory.add(new PlaybackStats.EventTimeAndPlaybackState(eventTime, this.currentPlaybackState));
            }
        }

        private int resolveNewPlaybackState(Player player) {
            int playbackState = player.getPlaybackState();
            if (this.isSeeking && this.isForeground) {
                return 5;
            }
            if (this.hasFatalError) {
                return 13;
            }
            if (!this.isForeground) {
                return this.startedLoading ? 1 : 0;
            }
            if (this.isInterruptedByAd) {
                return 14;
            }
            if (playbackState == 4) {
                return 11;
            }
            if (playbackState != 2) {
                if (playbackState == 3) {
                    if (player.getPlayWhenReady()) {
                        return player.getPlaybackSuppressionReason() != 0 ? 9 : 3;
                    }
                    return 4;
                }
                if (playbackState != 1 || this.currentPlaybackState == 0) {
                    return this.currentPlaybackState;
                }
                return 12;
            }
            int i = this.currentPlaybackState;
            if (i == 0 || i == 1 || i == 2 || i == 14) {
                return 2;
            }
            if (player.getPlayWhenReady()) {
                return player.getPlaybackSuppressionReason() != 0 ? 10 : 6;
            }
            return 7;
        }

        private void maybeUpdateMaxRebufferTimeMs(long nowMs) {
            if (isRebufferingState(this.currentPlaybackState)) {
                long j = nowMs - this.lastRebufferStartTimeMs;
                long j2 = this.maxRebufferTimeMs;
                if (j2 == C.TIME_UNSET || j > j2) {
                    this.maxRebufferTimeMs = j;
                }
            }
        }

        private void maybeUpdateMediaTimeHistory(long realtimeMs, long mediaTimeMs) {
            if (this.keepHistory) {
                if (this.currentPlaybackState != 3) {
                    if (mediaTimeMs == C.TIME_UNSET) {
                        return;
                    }
                    if (!this.mediaTimeHistory.isEmpty()) {
                        List<long[]> list = this.mediaTimeHistory;
                        long j = list.get(list.size() - 1)[1];
                        if (j != mediaTimeMs) {
                            this.mediaTimeHistory.add(new long[]{realtimeMs, j});
                        }
                    }
                }
                this.mediaTimeHistory.add(mediaTimeMs == C.TIME_UNSET ? guessMediaTimeBasedOnElapsedRealtime(realtimeMs) : new long[]{realtimeMs, mediaTimeMs});
            }
        }

        private long[] guessMediaTimeBasedOnElapsedRealtime(long realtimeMs) {
            List<long[]> list = this.mediaTimeHistory;
            return new long[]{realtimeMs, list.get(list.size() - 1)[1] + ((long) ((realtimeMs - r0[0]) * this.currentPlaybackSpeed))};
        }

        private void maybeUpdateVideoFormat(AnalyticsListener.EventTime eventTime, Format newFormat) {
            if (Util.areEqual(this.currentVideoFormat, newFormat)) {
                return;
            }
            maybeRecordVideoFormatTime(eventTime.realtimeMs);
            if (newFormat != null) {
                if (this.initialVideoFormatHeight == -1 && newFormat.height != -1) {
                    this.initialVideoFormatHeight = newFormat.height;
                }
                if (this.initialVideoFormatBitrate == -1 && newFormat.bitrate != -1) {
                    this.initialVideoFormatBitrate = newFormat.bitrate;
                }
            }
            this.currentVideoFormat = newFormat;
            if (this.keepHistory) {
                this.videoFormatHistory.add(new PlaybackStats.EventTimeAndFormat(eventTime, this.currentVideoFormat));
            }
        }

        private void maybeUpdateAudioFormat(AnalyticsListener.EventTime eventTime, Format newFormat) {
            if (Util.areEqual(this.currentAudioFormat, newFormat)) {
                return;
            }
            maybeRecordAudioFormatTime(eventTime.realtimeMs);
            if (newFormat != null && this.initialAudioFormatBitrate == -1 && newFormat.bitrate != -1) {
                this.initialAudioFormatBitrate = newFormat.bitrate;
            }
            this.currentAudioFormat = newFormat;
            if (this.keepHistory) {
                this.audioFormatHistory.add(new PlaybackStats.EventTimeAndFormat(eventTime, this.currentAudioFormat));
            }
        }

        private void maybeRecordVideoFormatTime(long nowMs) {
            Format format;
            if (this.currentPlaybackState == 3 && (format = this.currentVideoFormat) != null) {
                long j = (long) ((nowMs - this.lastVideoFormatStartTimeMs) * this.currentPlaybackSpeed);
                if (format.height != -1) {
                    this.videoFormatHeightTimeMs += j;
                    this.videoFormatHeightTimeProduct += this.currentVideoFormat.height * j;
                }
                if (this.currentVideoFormat.bitrate != -1) {
                    this.videoFormatBitrateTimeMs += j;
                    this.videoFormatBitrateTimeProduct += j * this.currentVideoFormat.bitrate;
                }
            }
            this.lastVideoFormatStartTimeMs = nowMs;
        }

        private void maybeRecordAudioFormatTime(long nowMs) {
            Format format;
            if (this.currentPlaybackState == 3 && (format = this.currentAudioFormat) != null && format.bitrate != -1) {
                long j = (long) ((nowMs - this.lastAudioFormatStartTimeMs) * this.currentPlaybackSpeed);
                this.audioFormatTimeMs += j;
                this.audioFormatBitrateTimeProduct += j * this.currentAudioFormat.bitrate;
            }
            this.lastAudioFormatStartTimeMs = nowMs;
        }
    }
}
