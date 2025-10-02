package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class MediaPeriodInfo {
    public final long durationUs;
    public final long endPositionUs;
    public final MediaSource.MediaPeriodId id;
    public final boolean isFinal;
    public final boolean isFollowedByTransitionToSameStream;
    public final boolean isLastInTimelinePeriod;
    public final boolean isLastInTimelineWindow;
    public final long requestedContentPositionUs;
    public final long startPositionUs;

    MediaPeriodInfo(MediaSource.MediaPeriodId id, long startPositionUs, long requestedContentPositionUs, long endPositionUs, long durationUs, boolean isFollowedByTransitionToSameStream, boolean isLastInTimelinePeriod, boolean isLastInTimelineWindow, boolean isFinal) {
        boolean z = false;
        Assertions.checkArgument(!isFinal || isLastInTimelinePeriod);
        Assertions.checkArgument(!isLastInTimelineWindow || isLastInTimelinePeriod);
        if (!isFollowedByTransitionToSameStream || (!isLastInTimelinePeriod && !isLastInTimelineWindow && !isFinal)) {
            z = true;
        }
        Assertions.checkArgument(z);
        this.id = id;
        this.startPositionUs = startPositionUs;
        this.requestedContentPositionUs = requestedContentPositionUs;
        this.endPositionUs = endPositionUs;
        this.durationUs = durationUs;
        this.isFollowedByTransitionToSameStream = isFollowedByTransitionToSameStream;
        this.isLastInTimelinePeriod = isLastInTimelinePeriod;
        this.isLastInTimelineWindow = isLastInTimelineWindow;
        this.isFinal = isFinal;
    }

    public MediaPeriodInfo copyWithStartPositionUs(long startPositionUs) {
        return startPositionUs == this.startPositionUs ? this : new MediaPeriodInfo(this.id, startPositionUs, this.requestedContentPositionUs, this.endPositionUs, this.durationUs, this.isFollowedByTransitionToSameStream, this.isLastInTimelinePeriod, this.isLastInTimelineWindow, this.isFinal);
    }

    public MediaPeriodInfo copyWithRequestedContentPositionUs(long requestedContentPositionUs) {
        return requestedContentPositionUs == this.requestedContentPositionUs ? this : new MediaPeriodInfo(this.id, this.startPositionUs, requestedContentPositionUs, this.endPositionUs, this.durationUs, this.isFollowedByTransitionToSameStream, this.isLastInTimelinePeriod, this.isLastInTimelineWindow, this.isFinal);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaPeriodInfo mediaPeriodInfo = (MediaPeriodInfo) o;
        return this.startPositionUs == mediaPeriodInfo.startPositionUs && this.requestedContentPositionUs == mediaPeriodInfo.requestedContentPositionUs && this.endPositionUs == mediaPeriodInfo.endPositionUs && this.durationUs == mediaPeriodInfo.durationUs && this.isFollowedByTransitionToSameStream == mediaPeriodInfo.isFollowedByTransitionToSameStream && this.isLastInTimelinePeriod == mediaPeriodInfo.isLastInTimelinePeriod && this.isLastInTimelineWindow == mediaPeriodInfo.isLastInTimelineWindow && this.isFinal == mediaPeriodInfo.isFinal && Util.areEqual(this.id, mediaPeriodInfo.id);
    }

    public int hashCode() {
        return ((((((((((((((((527 + this.id.hashCode()) * 31) + ((int) this.startPositionUs)) * 31) + ((int) this.requestedContentPositionUs)) * 31) + ((int) this.endPositionUs)) * 31) + ((int) this.durationUs)) * 31) + (this.isFollowedByTransitionToSameStream ? 1 : 0)) * 31) + (this.isLastInTimelinePeriod ? 1 : 0)) * 31) + (this.isLastInTimelineWindow ? 1 : 0)) * 31) + (this.isFinal ? 1 : 0);
    }
}
