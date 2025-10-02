package com.google.android.exoplayer2;

import android.util.Pair;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public abstract class AbstractConcatenatedTimeline extends Timeline {
    private final int childCount;
    private final boolean isAtomic;
    private final ShuffleOrder shuffleOrder;

    protected abstract int getChildIndexByChildUid(Object childUid);

    protected abstract int getChildIndexByPeriodIndex(int periodIndex);

    protected abstract int getChildIndexByWindowIndex(int windowIndex);

    protected abstract Object getChildUidByChildIndex(int childIndex);

    protected abstract int getFirstPeriodIndexByChildIndex(int childIndex);

    protected abstract int getFirstWindowIndexByChildIndex(int childIndex);

    protected abstract Timeline getTimelineByChildIndex(int childIndex);

    public static Object getChildTimelineUidFromConcatenatedUid(Object concatenatedUid) {
        return ((Pair) concatenatedUid).first;
    }

    public static Object getChildPeriodUidFromConcatenatedUid(Object concatenatedUid) {
        return ((Pair) concatenatedUid).second;
    }

    public static Object getConcatenatedUid(Object childTimelineUid, Object childPeriodOrWindowUid) {
        return Pair.create(childTimelineUid, childPeriodOrWindowUid);
    }

    public AbstractConcatenatedTimeline(boolean isAtomic, ShuffleOrder shuffleOrder) {
        this.isAtomic = isAtomic;
        this.shuffleOrder = shuffleOrder;
        this.childCount = shuffleOrder.getLength();
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        if (this.isAtomic) {
            if (repeatMode == 1) {
                repeatMode = 2;
            }
            shuffleModeEnabled = false;
        }
        int childIndexByWindowIndex = getChildIndexByWindowIndex(windowIndex);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByWindowIndex);
        int nextWindowIndex = getTimelineByChildIndex(childIndexByWindowIndex).getNextWindowIndex(windowIndex - firstWindowIndexByChildIndex, repeatMode != 2 ? repeatMode : 0, shuffleModeEnabled);
        if (nextWindowIndex != -1) {
            return firstWindowIndexByChildIndex + nextWindowIndex;
        }
        int nextChildIndex = getNextChildIndex(childIndexByWindowIndex, shuffleModeEnabled);
        while (nextChildIndex != -1 && getTimelineByChildIndex(nextChildIndex).isEmpty()) {
            nextChildIndex = getNextChildIndex(nextChildIndex, shuffleModeEnabled);
        }
        if (nextChildIndex != -1) {
            return getFirstWindowIndexByChildIndex(nextChildIndex) + getTimelineByChildIndex(nextChildIndex).getFirstWindowIndex(shuffleModeEnabled);
        }
        if (repeatMode == 2) {
            return getFirstWindowIndex(shuffleModeEnabled);
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        if (this.isAtomic) {
            if (repeatMode == 1) {
                repeatMode = 2;
            }
            shuffleModeEnabled = false;
        }
        int childIndexByWindowIndex = getChildIndexByWindowIndex(windowIndex);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByWindowIndex);
        int previousWindowIndex = getTimelineByChildIndex(childIndexByWindowIndex).getPreviousWindowIndex(windowIndex - firstWindowIndexByChildIndex, repeatMode != 2 ? repeatMode : 0, shuffleModeEnabled);
        if (previousWindowIndex != -1) {
            return firstWindowIndexByChildIndex + previousWindowIndex;
        }
        int previousChildIndex = getPreviousChildIndex(childIndexByWindowIndex, shuffleModeEnabled);
        while (previousChildIndex != -1 && getTimelineByChildIndex(previousChildIndex).isEmpty()) {
            previousChildIndex = getPreviousChildIndex(previousChildIndex, shuffleModeEnabled);
        }
        if (previousChildIndex != -1) {
            return getFirstWindowIndexByChildIndex(previousChildIndex) + getTimelineByChildIndex(previousChildIndex).getLastWindowIndex(shuffleModeEnabled);
        }
        if (repeatMode == 2) {
            return getLastWindowIndex(shuffleModeEnabled);
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getLastWindowIndex(boolean shuffleModeEnabled) {
        int i = this.childCount;
        if (i == 0) {
            return -1;
        }
        if (this.isAtomic) {
            shuffleModeEnabled = false;
        }
        int lastIndex = shuffleModeEnabled ? this.shuffleOrder.getLastIndex() : i - 1;
        while (getTimelineByChildIndex(lastIndex).isEmpty()) {
            lastIndex = getPreviousChildIndex(lastIndex, shuffleModeEnabled);
            if (lastIndex == -1) {
                return -1;
            }
        }
        return getFirstWindowIndexByChildIndex(lastIndex) + getTimelineByChildIndex(lastIndex).getLastWindowIndex(shuffleModeEnabled);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getFirstWindowIndex(boolean shuffleModeEnabled) {
        if (this.childCount == 0) {
            return -1;
        }
        if (this.isAtomic) {
            shuffleModeEnabled = false;
        }
        int firstIndex = shuffleModeEnabled ? this.shuffleOrder.getFirstIndex() : 0;
        while (getTimelineByChildIndex(firstIndex).isEmpty()) {
            firstIndex = getNextChildIndex(firstIndex, shuffleModeEnabled);
            if (firstIndex == -1) {
                return -1;
            }
        }
        return getFirstWindowIndexByChildIndex(firstIndex) + getTimelineByChildIndex(firstIndex).getFirstWindowIndex(shuffleModeEnabled);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Timeline.Window getWindow(int windowIndex, Timeline.Window window, long defaultPositionProjectionUs) {
        int childIndexByWindowIndex = getChildIndexByWindowIndex(windowIndex);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByWindowIndex);
        int firstPeriodIndexByChildIndex = getFirstPeriodIndexByChildIndex(childIndexByWindowIndex);
        getTimelineByChildIndex(childIndexByWindowIndex).getWindow(windowIndex - firstWindowIndexByChildIndex, window, defaultPositionProjectionUs);
        Object childUidByChildIndex = getChildUidByChildIndex(childIndexByWindowIndex);
        if (!Timeline.Window.SINGLE_WINDOW_UID.equals(window.uid)) {
            childUidByChildIndex = getConcatenatedUid(childUidByChildIndex, window.uid);
        }
        window.uid = childUidByChildIndex;
        window.firstPeriodIndex += firstPeriodIndexByChildIndex;
        window.lastPeriodIndex += firstPeriodIndexByChildIndex;
        return window;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Timeline.Period getPeriodByUid(Object periodUid, Timeline.Period period) {
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(periodUid);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(periodUid);
        int childIndexByChildUid = getChildIndexByChildUid(childTimelineUidFromConcatenatedUid);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByChildUid);
        getTimelineByChildIndex(childIndexByChildUid).getPeriodByUid(childPeriodUidFromConcatenatedUid, period);
        period.windowIndex += firstWindowIndexByChildIndex;
        period.uid = periodUid;
        return period;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
        int childIndexByPeriodIndex = getChildIndexByPeriodIndex(periodIndex);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByPeriodIndex);
        getTimelineByChildIndex(childIndexByPeriodIndex).getPeriod(periodIndex - getFirstPeriodIndexByChildIndex(childIndexByPeriodIndex), period, setIds);
        period.windowIndex += firstWindowIndexByChildIndex;
        if (setIds) {
            period.uid = getConcatenatedUid(getChildUidByChildIndex(childIndexByPeriodIndex), Assertions.checkNotNull(period.uid));
        }
        return period;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final int getIndexOfPeriod(Object uid) {
        int indexOfPeriod;
        if (!(uid instanceof Pair)) {
            return -1;
        }
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(uid);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(uid);
        int childIndexByChildUid = getChildIndexByChildUid(childTimelineUidFromConcatenatedUid);
        if (childIndexByChildUid == -1 || (indexOfPeriod = getTimelineByChildIndex(childIndexByChildUid).getIndexOfPeriod(childPeriodUidFromConcatenatedUid)) == -1) {
            return -1;
        }
        return getFirstPeriodIndexByChildIndex(childIndexByChildUid) + indexOfPeriod;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Object getUidOfPeriod(int periodIndex) {
        int childIndexByPeriodIndex = getChildIndexByPeriodIndex(periodIndex);
        return getConcatenatedUid(getChildUidByChildIndex(childIndexByPeriodIndex), getTimelineByChildIndex(childIndexByPeriodIndex).getUidOfPeriod(periodIndex - getFirstPeriodIndexByChildIndex(childIndexByPeriodIndex)));
    }

    private int getNextChildIndex(int childIndex, boolean shuffleModeEnabled) {
        if (shuffleModeEnabled) {
            return this.shuffleOrder.getNextIndex(childIndex);
        }
        if (childIndex < this.childCount - 1) {
            return childIndex + 1;
        }
        return -1;
    }

    private int getPreviousChildIndex(int childIndex, boolean shuffleModeEnabled) {
        if (shuffleModeEnabled) {
            return this.shuffleOrder.getPreviousIndex(childIndex);
        }
        if (childIndex > 0) {
            return childIndex - 1;
        }
        return -1;
    }
}
