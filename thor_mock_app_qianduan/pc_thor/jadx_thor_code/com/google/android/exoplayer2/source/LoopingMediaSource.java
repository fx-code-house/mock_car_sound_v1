package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public final class LoopingMediaSource extends CompositeMediaSource<Void> {
    private final Map<MediaSource.MediaPeriodId, MediaSource.MediaPeriodId> childMediaPeriodIdToMediaPeriodId;
    private final int loopCount;
    private final MaskingMediaSource maskingMediaSource;
    private final Map<MediaPeriod, MediaSource.MediaPeriodId> mediaPeriodToChildMediaPeriodId;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public boolean isSingleWindow() {
        return false;
    }

    public LoopingMediaSource(MediaSource childSource) {
        this(childSource, Integer.MAX_VALUE);
    }

    public LoopingMediaSource(MediaSource childSource, int loopCount) {
        Assertions.checkArgument(loopCount > 0);
        this.maskingMediaSource = new MaskingMediaSource(childSource, false);
        this.loopCount = loopCount;
        this.childMediaPeriodIdToMediaPeriodId = new HashMap();
        this.mediaPeriodToChildMediaPeriodId = new HashMap();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.maskingMediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public Timeline getInitialTimeline() {
        if (this.loopCount != Integer.MAX_VALUE) {
            return new LoopingTimeline(this.maskingMediaSource.getTimeline(), this.loopCount);
        }
        return new InfinitelyLoopingTimeline(this.maskingMediaSource.getTimeline());
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        super.prepareSourceInternal(mediaTransferListener);
        prepareChildSource(null, this.maskingMediaSource);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        if (this.loopCount == Integer.MAX_VALUE) {
            return this.maskingMediaSource.createPeriod(id, allocator, startPositionUs);
        }
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithPeriodUid = id.copyWithPeriodUid(LoopingTimeline.getChildPeriodUidFromConcatenatedUid(id.periodUid));
        this.childMediaPeriodIdToMediaPeriodId.put(mediaPeriodIdCopyWithPeriodUid, id);
        MaskingMediaPeriod maskingMediaPeriodCreatePeriod = this.maskingMediaSource.createPeriod(mediaPeriodIdCopyWithPeriodUid, allocator, startPositionUs);
        this.mediaPeriodToChildMediaPeriodId.put(maskingMediaPeriodCreatePeriod, mediaPeriodIdCopyWithPeriodUid);
        return maskingMediaPeriodCreatePeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.maskingMediaSource.releasePeriod(mediaPeriod);
        MediaSource.MediaPeriodId mediaPeriodIdRemove = this.mediaPeriodToChildMediaPeriodId.remove(mediaPeriod);
        if (mediaPeriodIdRemove != null) {
            this.childMediaPeriodIdToMediaPeriodId.remove(mediaPeriodIdRemove);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    /* renamed from: onChildSourceInfoRefreshed, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public void m172x365769cd(Void id, MediaSource mediaSource, Timeline timeline) {
        Timeline infinitelyLoopingTimeline;
        if (this.loopCount != Integer.MAX_VALUE) {
            infinitelyLoopingTimeline = new LoopingTimeline(timeline, this.loopCount);
        } else {
            infinitelyLoopingTimeline = new InfinitelyLoopingTimeline(timeline);
        }
        refreshSourceInfo(infinitelyLoopingTimeline);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Void id, MediaSource.MediaPeriodId mediaPeriodId) {
        return this.loopCount != Integer.MAX_VALUE ? this.childMediaPeriodIdToMediaPeriodId.get(mediaPeriodId) : mediaPeriodId;
    }

    private static final class LoopingTimeline extends AbstractConcatenatedTimeline {
        private final int childPeriodCount;
        private final Timeline childTimeline;
        private final int childWindowCount;
        private final int loopCount;

        public LoopingTimeline(Timeline childTimeline, int loopCount) {
            super(false, new ShuffleOrder.UnshuffledShuffleOrder(loopCount));
            this.childTimeline = childTimeline;
            int periodCount = childTimeline.getPeriodCount();
            this.childPeriodCount = periodCount;
            this.childWindowCount = childTimeline.getWindowCount();
            this.loopCount = loopCount;
            if (periodCount > 0) {
                Assertions.checkState(loopCount <= Integer.MAX_VALUE / periodCount, "LoopingMediaSource contains too many periods");
            }
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.childWindowCount * this.loopCount;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.childPeriodCount * this.loopCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByPeriodIndex(int periodIndex) {
            return periodIndex / this.childPeriodCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByWindowIndex(int windowIndex) {
            return windowIndex / this.childWindowCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByChildUid(Object childUid) {
            if (childUid instanceof Integer) {
                return ((Integer) childUid).intValue();
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected Timeline getTimelineByChildIndex(int childIndex) {
            return this.childTimeline;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getFirstPeriodIndexByChildIndex(int childIndex) {
            return childIndex * this.childPeriodCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getFirstWindowIndexByChildIndex(int childIndex) {
            return childIndex * this.childWindowCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected Object getChildUidByChildIndex(int childIndex) {
            return Integer.valueOf(childIndex);
        }
    }

    private static final class InfinitelyLoopingTimeline extends ForwardingTimeline {
        public InfinitelyLoopingTimeline(Timeline timeline) {
            super(timeline);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
            int nextWindowIndex = this.timeline.getNextWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
            return nextWindowIndex == -1 ? getFirstWindowIndex(shuffleModeEnabled) : nextWindowIndex;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
            int previousWindowIndex = this.timeline.getPreviousWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
            return previousWindowIndex == -1 ? getLastWindowIndex(shuffleModeEnabled) : previousWindowIndex;
        }
    }
}
