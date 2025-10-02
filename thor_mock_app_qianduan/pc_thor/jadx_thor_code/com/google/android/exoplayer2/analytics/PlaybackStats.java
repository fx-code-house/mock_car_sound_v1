package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class PlaybackStats {
    public static final PlaybackStats EMPTY = merge(new PlaybackStats[0]);
    public static final int PLAYBACK_STATE_ABANDONED = 15;
    public static final int PLAYBACK_STATE_BUFFERING = 6;
    static final int PLAYBACK_STATE_COUNT = 16;
    public static final int PLAYBACK_STATE_ENDED = 11;
    public static final int PLAYBACK_STATE_FAILED = 13;
    public static final int PLAYBACK_STATE_INTERRUPTED_BY_AD = 14;
    public static final int PLAYBACK_STATE_JOINING_BACKGROUND = 1;
    public static final int PLAYBACK_STATE_JOINING_FOREGROUND = 2;
    public static final int PLAYBACK_STATE_NOT_STARTED = 0;
    public static final int PLAYBACK_STATE_PAUSED = 4;
    public static final int PLAYBACK_STATE_PAUSED_BUFFERING = 7;
    public static final int PLAYBACK_STATE_PLAYING = 3;
    public static final int PLAYBACK_STATE_SEEKING = 5;
    public static final int PLAYBACK_STATE_STOPPED = 12;
    public static final int PLAYBACK_STATE_SUPPRESSED = 9;
    public static final int PLAYBACK_STATE_SUPPRESSED_BUFFERING = 10;
    public final int abandonedBeforeReadyCount;
    public final int adPlaybackCount;
    public final List<EventTimeAndFormat> audioFormatHistory;
    public final int backgroundJoiningCount;
    public final int endedCount;
    public final int fatalErrorCount;
    public final List<EventTimeAndException> fatalErrorHistory;
    public final int fatalErrorPlaybackCount;
    public final long firstReportedTimeMs;
    public final int foregroundPlaybackCount;
    public final int initialAudioFormatBitrateCount;
    public final int initialVideoFormatBitrateCount;
    public final int initialVideoFormatHeightCount;
    public final long maxRebufferTimeMs;
    public final List<long[]> mediaTimeHistory;
    public final int nonFatalErrorCount;
    public final List<EventTimeAndException> nonFatalErrorHistory;
    public final int playbackCount;
    private final long[] playbackStateDurationsMs;
    public final List<EventTimeAndPlaybackState> playbackStateHistory;
    public final long totalAudioFormatBitrateTimeProduct;
    public final long totalAudioFormatTimeMs;
    public final long totalAudioUnderruns;
    public final long totalBandwidthBytes;
    public final long totalBandwidthTimeMs;
    public final long totalDroppedFrames;
    public final long totalInitialAudioFormatBitrate;
    public final long totalInitialVideoFormatBitrate;
    public final int totalInitialVideoFormatHeight;
    public final int totalPauseBufferCount;
    public final int totalPauseCount;
    public final int totalRebufferCount;
    public final int totalSeekCount;
    public final long totalValidJoinTimeMs;
    public final long totalVideoFormatBitrateTimeMs;
    public final long totalVideoFormatBitrateTimeProduct;
    public final long totalVideoFormatHeightTimeMs;
    public final long totalVideoFormatHeightTimeProduct;
    public final int validJoinTimeCount;
    public final List<EventTimeAndFormat> videoFormatHistory;

    public static final class EventTimeAndPlaybackState {
        public final AnalyticsListener.EventTime eventTime;
        public final int playbackState;

        public EventTimeAndPlaybackState(AnalyticsListener.EventTime eventTime, int playbackState) {
            this.eventTime = eventTime;
            this.playbackState = playbackState;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EventTimeAndPlaybackState eventTimeAndPlaybackState = (EventTimeAndPlaybackState) o;
            if (this.playbackState != eventTimeAndPlaybackState.playbackState) {
                return false;
            }
            return this.eventTime.equals(eventTimeAndPlaybackState.eventTime);
        }

        public int hashCode() {
            return (this.eventTime.hashCode() * 31) + this.playbackState;
        }
    }

    public static final class EventTimeAndFormat {
        public final AnalyticsListener.EventTime eventTime;
        public final Format format;

        public EventTimeAndFormat(AnalyticsListener.EventTime eventTime, Format format) {
            this.eventTime = eventTime;
            this.format = format;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EventTimeAndFormat eventTimeAndFormat = (EventTimeAndFormat) o;
            if (!this.eventTime.equals(eventTimeAndFormat.eventTime)) {
                return false;
            }
            Format format = this.format;
            Format format2 = eventTimeAndFormat.format;
            return format != null ? format.equals(format2) : format2 == null;
        }

        public int hashCode() {
            int iHashCode = this.eventTime.hashCode() * 31;
            Format format = this.format;
            return iHashCode + (format != null ? format.hashCode() : 0);
        }
    }

    public static final class EventTimeAndException {
        public final AnalyticsListener.EventTime eventTime;
        public final Exception exception;

        public EventTimeAndException(AnalyticsListener.EventTime eventTime, Exception exception) {
            this.eventTime = eventTime;
            this.exception = exception;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EventTimeAndException eventTimeAndException = (EventTimeAndException) o;
            if (this.eventTime.equals(eventTimeAndException.eventTime)) {
                return this.exception.equals(eventTimeAndException.exception);
            }
            return false;
        }

        public int hashCode() {
            return (this.eventTime.hashCode() * 31) + this.exception.hashCode();
        }
    }

    public static PlaybackStats merge(PlaybackStats... playbackStats) {
        int i;
        int i2 = 16;
        long[] jArr = new long[16];
        int length = playbackStats.length;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        long j10 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = -1;
        long jMax = C.TIME_UNSET;
        long jMin = C.TIME_UNSET;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        long j11 = C.TIME_UNSET;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        long j12 = -1;
        int i18 = 0;
        long j13 = -1;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        while (i3 < length) {
            PlaybackStats playbackStats2 = playbackStats[i3];
            i4 += playbackStats2.playbackCount;
            for (int i22 = 0; i22 < i2; i22++) {
                jArr[i22] = jArr[i22] + playbackStats2.playbackStateDurationsMs[i22];
            }
            if (jMin == C.TIME_UNSET) {
                jMin = playbackStats2.firstReportedTimeMs;
                i = length;
            } else {
                i = length;
                long j14 = playbackStats2.firstReportedTimeMs;
                if (j14 != C.TIME_UNSET) {
                    jMin = Math.min(jMin, j14);
                }
            }
            i6 += playbackStats2.foregroundPlaybackCount;
            i7 += playbackStats2.abandonedBeforeReadyCount;
            i8 += playbackStats2.endedCount;
            i9 += playbackStats2.backgroundJoiningCount;
            if (j11 == C.TIME_UNSET) {
                j11 = playbackStats2.totalValidJoinTimeMs;
            } else {
                long j15 = playbackStats2.totalValidJoinTimeMs;
                if (j15 != C.TIME_UNSET) {
                    j11 += j15;
                }
            }
            i10 += playbackStats2.validJoinTimeCount;
            i11 += playbackStats2.totalPauseCount;
            i12 += playbackStats2.totalPauseBufferCount;
            i13 += playbackStats2.totalSeekCount;
            i14 += playbackStats2.totalRebufferCount;
            if (jMax == C.TIME_UNSET) {
                jMax = playbackStats2.maxRebufferTimeMs;
            } else {
                long j16 = playbackStats2.maxRebufferTimeMs;
                if (j16 != C.TIME_UNSET) {
                    jMax = Math.max(jMax, j16);
                }
            }
            i15 += playbackStats2.adPlaybackCount;
            j += playbackStats2.totalVideoFormatHeightTimeMs;
            j2 += playbackStats2.totalVideoFormatHeightTimeProduct;
            j3 += playbackStats2.totalVideoFormatBitrateTimeMs;
            j4 += playbackStats2.totalVideoFormatBitrateTimeProduct;
            j5 += playbackStats2.totalAudioFormatTimeMs;
            j6 += playbackStats2.totalAudioFormatBitrateTimeProduct;
            i16 += playbackStats2.initialVideoFormatHeightCount;
            i17 += playbackStats2.initialVideoFormatBitrateCount;
            if (i5 == -1) {
                i5 = playbackStats2.totalInitialVideoFormatHeight;
            } else {
                int i23 = playbackStats2.totalInitialVideoFormatHeight;
                if (i23 != -1) {
                    i5 += i23;
                }
            }
            if (j12 == -1) {
                j12 = playbackStats2.totalInitialVideoFormatBitrate;
            } else {
                long j17 = playbackStats2.totalInitialVideoFormatBitrate;
                if (j17 != -1) {
                    j12 += j17;
                }
            }
            i18 += playbackStats2.initialAudioFormatBitrateCount;
            if (j13 == -1) {
                j13 = playbackStats2.totalInitialAudioFormatBitrate;
            } else {
                long j18 = playbackStats2.totalInitialAudioFormatBitrate;
                if (j18 != -1) {
                    j13 += j18;
                }
            }
            j7 += playbackStats2.totalBandwidthTimeMs;
            j8 += playbackStats2.totalBandwidthBytes;
            j9 += playbackStats2.totalDroppedFrames;
            j10 += playbackStats2.totalAudioUnderruns;
            i19 += playbackStats2.fatalErrorPlaybackCount;
            i20 += playbackStats2.fatalErrorCount;
            i21 += playbackStats2.nonFatalErrorCount;
            i3++;
            length = i;
            i2 = 16;
        }
        return new PlaybackStats(i4, jArr, Collections.emptyList(), Collections.emptyList(), jMin, i6, i7, i8, i9, j11, i10, i11, i12, i13, i14, jMax, i15, Collections.emptyList(), Collections.emptyList(), j, j2, j3, j4, j5, j6, i16, i17, i5, j12, i18, j13, j7, j8, j9, j10, i19, i20, i21, Collections.emptyList(), Collections.emptyList());
    }

    PlaybackStats(int playbackCount, long[] playbackStateDurationsMs, List<EventTimeAndPlaybackState> playbackStateHistory, List<long[]> mediaTimeHistory, long firstReportedTimeMs, int foregroundPlaybackCount, int abandonedBeforeReadyCount, int endedCount, int backgroundJoiningCount, long totalValidJoinTimeMs, int validJoinTimeCount, int totalPauseCount, int totalPauseBufferCount, int totalSeekCount, int totalRebufferCount, long maxRebufferTimeMs, int adPlaybackCount, List<EventTimeAndFormat> videoFormatHistory, List<EventTimeAndFormat> audioFormatHistory, long totalVideoFormatHeightTimeMs, long totalVideoFormatHeightTimeProduct, long totalVideoFormatBitrateTimeMs, long totalVideoFormatBitrateTimeProduct, long totalAudioFormatTimeMs, long totalAudioFormatBitrateTimeProduct, int initialVideoFormatHeightCount, int initialVideoFormatBitrateCount, int totalInitialVideoFormatHeight, long totalInitialVideoFormatBitrate, int initialAudioFormatBitrateCount, long totalInitialAudioFormatBitrate, long totalBandwidthTimeMs, long totalBandwidthBytes, long totalDroppedFrames, long totalAudioUnderruns, int fatalErrorPlaybackCount, int fatalErrorCount, int nonFatalErrorCount, List<EventTimeAndException> fatalErrorHistory, List<EventTimeAndException> nonFatalErrorHistory) {
        this.playbackCount = playbackCount;
        this.playbackStateDurationsMs = playbackStateDurationsMs;
        this.playbackStateHistory = Collections.unmodifiableList(playbackStateHistory);
        this.mediaTimeHistory = Collections.unmodifiableList(mediaTimeHistory);
        this.firstReportedTimeMs = firstReportedTimeMs;
        this.foregroundPlaybackCount = foregroundPlaybackCount;
        this.abandonedBeforeReadyCount = abandonedBeforeReadyCount;
        this.endedCount = endedCount;
        this.backgroundJoiningCount = backgroundJoiningCount;
        this.totalValidJoinTimeMs = totalValidJoinTimeMs;
        this.validJoinTimeCount = validJoinTimeCount;
        this.totalPauseCount = totalPauseCount;
        this.totalPauseBufferCount = totalPauseBufferCount;
        this.totalSeekCount = totalSeekCount;
        this.totalRebufferCount = totalRebufferCount;
        this.maxRebufferTimeMs = maxRebufferTimeMs;
        this.adPlaybackCount = adPlaybackCount;
        this.videoFormatHistory = Collections.unmodifiableList(videoFormatHistory);
        this.audioFormatHistory = Collections.unmodifiableList(audioFormatHistory);
        this.totalVideoFormatHeightTimeMs = totalVideoFormatHeightTimeMs;
        this.totalVideoFormatHeightTimeProduct = totalVideoFormatHeightTimeProduct;
        this.totalVideoFormatBitrateTimeMs = totalVideoFormatBitrateTimeMs;
        this.totalVideoFormatBitrateTimeProduct = totalVideoFormatBitrateTimeProduct;
        this.totalAudioFormatTimeMs = totalAudioFormatTimeMs;
        this.totalAudioFormatBitrateTimeProduct = totalAudioFormatBitrateTimeProduct;
        this.initialVideoFormatHeightCount = initialVideoFormatHeightCount;
        this.initialVideoFormatBitrateCount = initialVideoFormatBitrateCount;
        this.totalInitialVideoFormatHeight = totalInitialVideoFormatHeight;
        this.totalInitialVideoFormatBitrate = totalInitialVideoFormatBitrate;
        this.initialAudioFormatBitrateCount = initialAudioFormatBitrateCount;
        this.totalInitialAudioFormatBitrate = totalInitialAudioFormatBitrate;
        this.totalBandwidthTimeMs = totalBandwidthTimeMs;
        this.totalBandwidthBytes = totalBandwidthBytes;
        this.totalDroppedFrames = totalDroppedFrames;
        this.totalAudioUnderruns = totalAudioUnderruns;
        this.fatalErrorPlaybackCount = fatalErrorPlaybackCount;
        this.fatalErrorCount = fatalErrorCount;
        this.nonFatalErrorCount = nonFatalErrorCount;
        this.fatalErrorHistory = Collections.unmodifiableList(fatalErrorHistory);
        this.nonFatalErrorHistory = Collections.unmodifiableList(nonFatalErrorHistory);
    }

    public long getPlaybackStateDurationMs(int playbackState) {
        return this.playbackStateDurationsMs[playbackState];
    }

    public int getPlaybackStateAtTime(long realtimeMs) {
        int i = 0;
        for (EventTimeAndPlaybackState eventTimeAndPlaybackState : this.playbackStateHistory) {
            if (eventTimeAndPlaybackState.eventTime.realtimeMs > realtimeMs) {
                break;
            }
            i = eventTimeAndPlaybackState.playbackState;
        }
        return i;
    }

    public long getMediaTimeMsAtRealtimeMs(long realtimeMs) {
        if (this.mediaTimeHistory.isEmpty()) {
            return C.TIME_UNSET;
        }
        int i = 0;
        while (i < this.mediaTimeHistory.size() && this.mediaTimeHistory.get(i)[0] <= realtimeMs) {
            i++;
        }
        if (i == 0) {
            return this.mediaTimeHistory.get(0)[1];
        }
        if (i == this.mediaTimeHistory.size()) {
            List<long[]> list = this.mediaTimeHistory;
            return list.get(list.size() - 1)[1];
        }
        int i2 = i - 1;
        long j = this.mediaTimeHistory.get(i2)[0];
        long j2 = this.mediaTimeHistory.get(i2)[1];
        long j3 = this.mediaTimeHistory.get(i)[0];
        long j4 = this.mediaTimeHistory.get(i)[1];
        long j5 = j3 - j;
        if (j5 == 0) {
            return j2;
        }
        return j2 + ((long) ((j4 - j2) * ((realtimeMs - j) / j5)));
    }

    public long getMeanJoinTimeMs() {
        int i = this.validJoinTimeCount;
        return i == 0 ? C.TIME_UNSET : this.totalValidJoinTimeMs / i;
    }

    public long getTotalJoinTimeMs() {
        return getPlaybackStateDurationMs(2);
    }

    public long getTotalPlayTimeMs() {
        return getPlaybackStateDurationMs(3);
    }

    public long getMeanPlayTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPlayTimeMs() / this.foregroundPlaybackCount;
    }

    public long getTotalPausedTimeMs() {
        return getPlaybackStateDurationMs(4) + getPlaybackStateDurationMs(7);
    }

    public long getMeanPausedTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPausedTimeMs() / this.foregroundPlaybackCount;
    }

    public long getTotalRebufferTimeMs() {
        return getPlaybackStateDurationMs(6);
    }

    public long getMeanRebufferTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalRebufferTimeMs() / this.foregroundPlaybackCount;
    }

    public long getMeanSingleRebufferTimeMs() {
        return this.totalRebufferCount == 0 ? C.TIME_UNSET : (getPlaybackStateDurationMs(6) + getPlaybackStateDurationMs(7)) / this.totalRebufferCount;
    }

    public long getTotalSeekTimeMs() {
        return getPlaybackStateDurationMs(5);
    }

    public long getMeanSeekTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalSeekTimeMs() / this.foregroundPlaybackCount;
    }

    public long getMeanSingleSeekTimeMs() {
        return this.totalSeekCount == 0 ? C.TIME_UNSET : getTotalSeekTimeMs() / this.totalSeekCount;
    }

    public long getTotalWaitTimeMs() {
        return getPlaybackStateDurationMs(2) + getPlaybackStateDurationMs(6) + getPlaybackStateDurationMs(5);
    }

    public long getMeanWaitTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalWaitTimeMs() / this.foregroundPlaybackCount;
    }

    public long getTotalPlayAndWaitTimeMs() {
        return getTotalPlayTimeMs() + getTotalWaitTimeMs();
    }

    public long getMeanPlayAndWaitTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPlayAndWaitTimeMs() / this.foregroundPlaybackCount;
    }

    public long getTotalElapsedTimeMs() {
        long j = 0;
        for (int i = 0; i < 16; i++) {
            j += this.playbackStateDurationsMs[i];
        }
        return j;
    }

    public long getMeanElapsedTimeMs() {
        return this.playbackCount == 0 ? C.TIME_UNSET : getTotalElapsedTimeMs() / this.playbackCount;
    }

    public float getAbandonedBeforeReadyRatio() {
        int i = this.abandonedBeforeReadyCount;
        int i2 = this.playbackCount;
        int i3 = this.foregroundPlaybackCount;
        int i4 = i - (i2 - i3);
        if (i3 == 0) {
            return 0.0f;
        }
        return i4 / i3;
    }

    public float getEndedRatio() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.endedCount / i;
    }

    public float getMeanPauseCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalPauseCount / i;
    }

    public float getMeanPauseBufferCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalPauseBufferCount / i;
    }

    public float getMeanSeekCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalSeekCount / i;
    }

    public float getMeanRebufferCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalRebufferCount / i;
    }

    public float getWaitTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalWaitTimeMs() / totalPlayAndWaitTimeMs;
    }

    public float getJoinTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalJoinTimeMs() / totalPlayAndWaitTimeMs;
    }

    public float getRebufferTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalRebufferTimeMs() / totalPlayAndWaitTimeMs;
    }

    public float getSeekTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalSeekTimeMs() / totalPlayAndWaitTimeMs;
    }

    public float getRebufferRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalRebufferCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getMeanTimeBetweenRebuffers() {
        return 1.0f / getRebufferRate();
    }

    public int getMeanInitialVideoFormatHeight() {
        int i = this.initialVideoFormatHeightCount;
        if (i == 0) {
            return -1;
        }
        return this.totalInitialVideoFormatHeight / i;
    }

    public int getMeanInitialVideoFormatBitrate() {
        int i = this.initialVideoFormatBitrateCount;
        if (i == 0) {
            return -1;
        }
        return (int) (this.totalInitialVideoFormatBitrate / i);
    }

    public int getMeanInitialAudioFormatBitrate() {
        int i = this.initialAudioFormatBitrateCount;
        if (i == 0) {
            return -1;
        }
        return (int) (this.totalInitialAudioFormatBitrate / i);
    }

    public int getMeanVideoFormatHeight() {
        long j = this.totalVideoFormatHeightTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) (this.totalVideoFormatHeightTimeProduct / j);
    }

    public int getMeanVideoFormatBitrate() {
        long j = this.totalVideoFormatBitrateTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) (this.totalVideoFormatBitrateTimeProduct / j);
    }

    public int getMeanAudioFormatBitrate() {
        long j = this.totalAudioFormatTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) (this.totalAudioFormatBitrateTimeProduct / j);
    }

    public int getMeanBandwidth() {
        long j = this.totalBandwidthTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) ((this.totalBandwidthBytes * RtspMediaSource.DEFAULT_TIMEOUT_MS) / j);
    }

    public float getDroppedFramesRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalDroppedFrames * 1000.0f) / totalPlayTimeMs;
    }

    public float getAudioUnderrunRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalAudioUnderruns * 1000.0f) / totalPlayTimeMs;
    }

    public float getFatalErrorRatio() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.fatalErrorPlaybackCount / i;
    }

    public float getFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.fatalErrorCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getMeanTimeBetweenFatalErrors() {
        return 1.0f / getFatalErrorRate();
    }

    public float getMeanNonFatalErrorCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.nonFatalErrorCount / i;
    }

    public float getNonFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.nonFatalErrorCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getMeanTimeBetweenNonFatalErrors() {
        return 1.0f / getNonFatalErrorRate();
    }
}
