package com.google.android.exoplayer2.transformer;

import android.util.SparseLongArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class TransformerMediaClock implements MediaClock {
    private long minTrackTimeUs;
    private final SparseLongArray trackTypeToTimeUs = new SparseLongArray();

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
    }

    public void updateTimeForTrackType(int trackType, long timeUs) {
        long j = this.trackTypeToTimeUs.get(trackType, C.TIME_UNSET);
        if (j == C.TIME_UNSET || timeUs > j) {
            this.trackTypeToTimeUs.put(trackType, timeUs);
            if (j == C.TIME_UNSET || j == this.minTrackTimeUs) {
                this.minTrackTimeUs = Util.minValue(this.trackTypeToTimeUs);
            }
        }
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        return this.minTrackTimeUs;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return PlaybackParameters.DEFAULT;
    }
}
