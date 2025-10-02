package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
final class PlaylistTimeline extends AbstractConcatenatedTimeline {
    private final HashMap<Object, Integer> childIndexByUid;
    private final int[] firstPeriodInChildIndices;
    private final int[] firstWindowInChildIndices;
    private final int periodCount;
    private final Timeline[] timelines;
    private final Object[] uids;
    private final int windowCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PlaylistTimeline(Collection<? extends MediaSourceInfoHolder> mediaSourceInfoHolders, ShuffleOrder shuffleOrder) {
        super(false, shuffleOrder);
        int windowCount = 0;
        int size = mediaSourceInfoHolders.size();
        this.firstPeriodInChildIndices = new int[size];
        this.firstWindowInChildIndices = new int[size];
        this.timelines = new Timeline[size];
        this.uids = new Object[size];
        this.childIndexByUid = new HashMap<>();
        int periodCount = 0;
        int i = 0;
        for (MediaSourceInfoHolder mediaSourceInfoHolder : mediaSourceInfoHolders) {
            this.timelines[i] = mediaSourceInfoHolder.getTimeline();
            this.firstWindowInChildIndices[i] = windowCount;
            this.firstPeriodInChildIndices[i] = periodCount;
            windowCount += this.timelines[i].getWindowCount();
            periodCount += this.timelines[i].getPeriodCount();
            this.uids[i] = mediaSourceInfoHolder.getUid();
            this.childIndexByUid.put(this.uids[i], Integer.valueOf(i));
            i++;
        }
        this.windowCount = windowCount;
        this.periodCount = periodCount;
    }

    List<Timeline> getChildTimelines() {
        return Arrays.asList(this.timelines);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getChildIndexByPeriodIndex(int periodIndex) {
        return Util.binarySearchFloor(this.firstPeriodInChildIndices, periodIndex + 1, false, false);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getChildIndexByWindowIndex(int windowIndex) {
        return Util.binarySearchFloor(this.firstWindowInChildIndices, windowIndex + 1, false, false);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getChildIndexByChildUid(Object childUid) {
        Integer num = this.childIndexByUid.get(childUid);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected Timeline getTimelineByChildIndex(int childIndex) {
        return this.timelines[childIndex];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getFirstPeriodIndexByChildIndex(int childIndex) {
        return this.firstPeriodInChildIndices[childIndex];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getFirstWindowIndexByChildIndex(int childIndex) {
        return this.firstWindowInChildIndices[childIndex];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected Object getChildUidByChildIndex(int childIndex) {
        return this.uids[childIndex];
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getWindowCount() {
        return this.windowCount;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPeriodCount() {
        return this.periodCount;
    }
}
