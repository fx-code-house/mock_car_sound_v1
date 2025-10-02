package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
final class WebvttSubtitle implements Subtitle {
    private final List<WebvttCueInfo> cueInfos;
    private final long[] cueTimesUs;
    private final long[] sortedCueTimesUs;

    public WebvttSubtitle(List<WebvttCueInfo> cueInfos) {
        this.cueInfos = Collections.unmodifiableList(new ArrayList(cueInfos));
        this.cueTimesUs = new long[cueInfos.size() * 2];
        for (int i = 0; i < cueInfos.size(); i++) {
            WebvttCueInfo webvttCueInfo = cueInfos.get(i);
            int i2 = i * 2;
            this.cueTimesUs[i2] = webvttCueInfo.startTimeUs;
            this.cueTimesUs[i2 + 1] = webvttCueInfo.endTimeUs;
        }
        long[] jArr = this.cueTimesUs;
        long[] jArrCopyOf = Arrays.copyOf(jArr, jArr.length);
        this.sortedCueTimesUs = jArrCopyOf;
        Arrays.sort(jArrCopyOf);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long timeUs) {
        int iBinarySearchCeil = Util.binarySearchCeil(this.sortedCueTimesUs, timeUs, false, false);
        if (iBinarySearchCeil < this.sortedCueTimesUs.length) {
            return iBinarySearchCeil;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.sortedCueTimesUs.length;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int index) {
        Assertions.checkArgument(index >= 0);
        Assertions.checkArgument(index < this.sortedCueTimesUs.length);
        return this.sortedCueTimesUs[index];
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long timeUs) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.cueInfos.size(); i++) {
            long[] jArr = this.cueTimesUs;
            int i2 = i * 2;
            if (jArr[i2] <= timeUs && timeUs < jArr[i2 + 1]) {
                WebvttCueInfo webvttCueInfo = this.cueInfos.get(i);
                if (webvttCueInfo.cue.line == -3.4028235E38f) {
                    arrayList2.add(webvttCueInfo);
                } else {
                    arrayList.add(webvttCueInfo.cue);
                }
            }
        }
        Collections.sort(arrayList2, new Comparator() { // from class: com.google.android.exoplayer2.text.webvtt.WebvttSubtitle$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Long.compare(((WebvttCueInfo) obj).startTimeUs, ((WebvttCueInfo) obj2).startTimeUs);
            }
        });
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            arrayList.add(((WebvttCueInfo) arrayList2.get(i3)).cue.buildUpon().setLine((-1) - i3, 1).build());
        }
        return arrayList;
    }
}
