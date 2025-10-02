package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSource;

/* loaded from: classes.dex */
public interface PlaybackSessionManager {

    public interface Listener {
        void onAdPlaybackStarted(AnalyticsListener.EventTime eventTime, String contentSessionId, String adSessionId);

        void onSessionActive(AnalyticsListener.EventTime eventTime, String sessionId);

        void onSessionCreated(AnalyticsListener.EventTime eventTime, String sessionId);

        void onSessionFinished(AnalyticsListener.EventTime eventTime, String sessionId, boolean automaticTransitionToNextPlayback);
    }

    boolean belongsToSession(AnalyticsListener.EventTime eventTime, String sessionId);

    void finishAllSessions(AnalyticsListener.EventTime eventTime);

    String getActiveSessionId();

    String getSessionForMediaPeriodId(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId);

    void setListener(Listener listener);

    void updateSessions(AnalyticsListener.EventTime eventTime);

    void updateSessionsWithDiscontinuity(AnalyticsListener.EventTime eventTime, int reason);

    void updateSessionsWithTimelineChange(AnalyticsListener.EventTime eventTime);
}
