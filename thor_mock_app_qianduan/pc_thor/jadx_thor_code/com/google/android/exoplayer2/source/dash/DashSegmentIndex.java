package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.source.dash.manifest.RangedUri;

/* loaded from: classes.dex */
public interface DashSegmentIndex {
    public static final int INDEX_UNBOUNDED = -1;

    long getAvailableSegmentCount(long periodDurationUs, long nowUnixTimeUs);

    long getDurationUs(long segmentNum, long periodDurationUs);

    long getFirstAvailableSegmentNum(long periodDurationUs, long nowUnixTimeUs);

    long getFirstSegmentNum();

    long getNextSegmentAvailableTimeUs(long periodDurationUs, long nowUnixTimeUs);

    long getSegmentCount(long periodDurationUs);

    long getSegmentNum(long timeUs, long periodDurationUs);

    RangedUri getSegmentUrl(long segmentNum);

    long getTimeUs(long segmentNum);

    boolean isExplicit();
}
