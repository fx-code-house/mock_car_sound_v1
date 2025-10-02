package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class SinglePeriodAdTimeline extends ForwardingTimeline {
    private final AdPlaybackState adPlaybackState;

    public SinglePeriodAdTimeline(Timeline contentTimeline, AdPlaybackState adPlaybackState) {
        super(contentTimeline);
        Assertions.checkState(contentTimeline.getPeriodCount() == 1);
        Assertions.checkState(contentTimeline.getWindowCount() == 1);
        this.adPlaybackState = adPlaybackState;
    }

    @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
    public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
        this.timeline.getPeriod(periodIndex, period, setIds);
        period.set(period.id, period.uid, period.windowIndex, period.durationUs == C.TIME_UNSET ? this.adPlaybackState.contentDurationUs : period.durationUs, period.getPositionInWindowUs(), this.adPlaybackState, period.isPlaceholder);
        return period;
    }
}
