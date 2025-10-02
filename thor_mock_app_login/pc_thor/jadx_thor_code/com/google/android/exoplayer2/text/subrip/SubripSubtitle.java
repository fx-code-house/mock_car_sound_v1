package com.google.android.exoplayer2.text.subrip;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class SubripSubtitle implements Subtitle {
    private final long[] cueTimesUs;
    private final Cue[] cues;

    public SubripSubtitle(Cue[] cues, long[] cueTimesUs) {
        this.cues = cues;
        this.cueTimesUs = cueTimesUs;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long timeUs) {
        int iBinarySearchCeil = Util.binarySearchCeil(this.cueTimesUs, timeUs, false, false);
        if (iBinarySearchCeil < this.cueTimesUs.length) {
            return iBinarySearchCeil;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.cueTimesUs.length;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int index) {
        Assertions.checkArgument(index >= 0);
        Assertions.checkArgument(index < this.cueTimesUs.length);
        return this.cueTimesUs[index];
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long timeUs) {
        int iBinarySearchFloor = Util.binarySearchFloor(this.cueTimesUs, timeUs, true, false);
        if (iBinarySearchFloor == -1 || this.cues[iBinarySearchFloor] == Cue.EMPTY) {
            return Collections.emptyList();
        }
        return Collections.singletonList(this.cues[iBinarySearchFloor]);
    }
}
