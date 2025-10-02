package com.google.android.exoplayer2.source.rtsp;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
final class RtspPlayResponse {
    public final RtspSessionTiming sessionTiming;
    public final int status;
    public final ImmutableList<RtspTrackTiming> trackTimingList;

    public RtspPlayResponse(int status, RtspSessionTiming sessionTiming, List<RtspTrackTiming> trackTimingList) {
        this.status = status;
        this.sessionTiming = sessionTiming;
        this.trackTimingList = ImmutableList.copyOf((Collection) trackTimingList);
    }
}
