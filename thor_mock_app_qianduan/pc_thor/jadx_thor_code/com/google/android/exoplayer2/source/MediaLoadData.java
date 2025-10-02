package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;

/* loaded from: classes.dex */
public final class MediaLoadData {
    public final int dataType;
    public final long mediaEndTimeMs;
    public final long mediaStartTimeMs;
    public final Format trackFormat;
    public final Object trackSelectionData;
    public final int trackSelectionReason;
    public final int trackType;

    public MediaLoadData(int dataType) {
        this(dataType, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
    }

    public MediaLoadData(int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs) {
        this.dataType = dataType;
        this.trackType = trackType;
        this.trackFormat = trackFormat;
        this.trackSelectionReason = trackSelectionReason;
        this.trackSelectionData = trackSelectionData;
        this.mediaStartTimeMs = mediaStartTimeMs;
        this.mediaEndTimeMs = mediaEndTimeMs;
    }
}
