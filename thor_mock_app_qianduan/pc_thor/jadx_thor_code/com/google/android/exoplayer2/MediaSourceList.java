package com.google.android.exoplayer2;

import android.os.Handler;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MaskingMediaSource;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
final class MediaSourceList {
    private static final String TAG = "MediaSourceList";
    private final HashMap<MediaSourceHolder, MediaSourceAndListener> childSources;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final Set<MediaSourceHolder> enabledMediaSourceHolders;
    private boolean isPrepared;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
    private final MediaSourceListInfoRefreshListener mediaSourceListInfoListener;
    private TransferListener mediaTransferListener;
    private ShuffleOrder shuffleOrder = new ShuffleOrder.DefaultShuffleOrder(0);
    private final IdentityHashMap<MediaPeriod, MediaSourceHolder> mediaSourceByMediaPeriod = new IdentityHashMap<>();
    private final Map<Object, MediaSourceHolder> mediaSourceByUid = new HashMap();
    private final List<MediaSourceHolder> mediaSourceHolders = new ArrayList();

    public interface MediaSourceListInfoRefreshListener {
        void onPlaylistUpdateRequested();
    }

    public MediaSourceList(MediaSourceListInfoRefreshListener listener, AnalyticsCollector analyticsCollector, Handler analyticsCollectorHandler) {
        this.mediaSourceListInfoListener = listener;
        MediaSourceEventListener.EventDispatcher eventDispatcher = new MediaSourceEventListener.EventDispatcher();
        this.mediaSourceEventDispatcher = eventDispatcher;
        DrmSessionEventListener.EventDispatcher eventDispatcher2 = new DrmSessionEventListener.EventDispatcher();
        this.drmEventDispatcher = eventDispatcher2;
        this.childSources = new HashMap<>();
        this.enabledMediaSourceHolders = new HashSet();
        if (analyticsCollector != null) {
            eventDispatcher.addEventListener(analyticsCollectorHandler, analyticsCollector);
            eventDispatcher2.addEventListener(analyticsCollectorHandler, analyticsCollector);
        }
    }

    public Timeline setMediaSources(List<MediaSourceHolder> holders, ShuffleOrder shuffleOrder) {
        removeMediaSourcesInternal(0, this.mediaSourceHolders.size());
        return addMediaSources(this.mediaSourceHolders.size(), holders, shuffleOrder);
    }

    public Timeline addMediaSources(int index, List<MediaSourceHolder> holders, ShuffleOrder shuffleOrder) {
        if (!holders.isEmpty()) {
            this.shuffleOrder = shuffleOrder;
            for (int i = index; i < holders.size() + index; i++) {
                MediaSourceHolder mediaSourceHolder = holders.get(i - index);
                if (i > 0) {
                    MediaSourceHolder mediaSourceHolder2 = this.mediaSourceHolders.get(i - 1);
                    mediaSourceHolder.reset(mediaSourceHolder2.firstWindowIndexInChild + mediaSourceHolder2.mediaSource.getTimeline().getWindowCount());
                } else {
                    mediaSourceHolder.reset(0);
                }
                correctOffsets(i, mediaSourceHolder.mediaSource.getTimeline().getWindowCount());
                this.mediaSourceHolders.add(i, mediaSourceHolder);
                this.mediaSourceByUid.put(mediaSourceHolder.uid, mediaSourceHolder);
                if (this.isPrepared) {
                    prepareChildSource(mediaSourceHolder);
                    if (this.mediaSourceByMediaPeriod.isEmpty()) {
                        this.enabledMediaSourceHolders.add(mediaSourceHolder);
                    } else {
                        disableChildSource(mediaSourceHolder);
                    }
                }
            }
        }
        return createTimeline();
    }

    public Timeline removeMediaSourceRange(int fromIndex, int toIndex, ShuffleOrder shuffleOrder) {
        Assertions.checkArgument(fromIndex >= 0 && fromIndex <= toIndex && toIndex <= getSize());
        this.shuffleOrder = shuffleOrder;
        removeMediaSourcesInternal(fromIndex, toIndex);
        return createTimeline();
    }

    public Timeline moveMediaSource(int currentIndex, int newIndex, ShuffleOrder shuffleOrder) {
        return moveMediaSourceRange(currentIndex, currentIndex + 1, newIndex, shuffleOrder);
    }

    public Timeline moveMediaSourceRange(int fromIndex, int toIndex, int newFromIndex, ShuffleOrder shuffleOrder) {
        Assertions.checkArgument(fromIndex >= 0 && fromIndex <= toIndex && toIndex <= getSize() && newFromIndex >= 0);
        this.shuffleOrder = shuffleOrder;
        if (fromIndex == toIndex || fromIndex == newFromIndex) {
            return createTimeline();
        }
        int iMin = Math.min(fromIndex, newFromIndex);
        int iMax = Math.max(((toIndex - fromIndex) + newFromIndex) - 1, toIndex - 1);
        int windowCount = this.mediaSourceHolders.get(iMin).firstWindowIndexInChild;
        Util.moveItems(this.mediaSourceHolders, fromIndex, toIndex, newFromIndex);
        while (iMin <= iMax) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(iMin);
            mediaSourceHolder.firstWindowIndexInChild = windowCount;
            windowCount += mediaSourceHolder.mediaSource.getTimeline().getWindowCount();
            iMin++;
        }
        return createTimeline();
    }

    public Timeline clear(ShuffleOrder shuffleOrder) {
        if (shuffleOrder == null) {
            shuffleOrder = this.shuffleOrder.cloneAndClear();
        }
        this.shuffleOrder = shuffleOrder;
        removeMediaSourcesInternal(0, getSize());
        return createTimeline();
    }

    public boolean isPrepared() {
        return this.isPrepared;
    }

    public int getSize() {
        return this.mediaSourceHolders.size();
    }

    public Timeline setShuffleOrder(ShuffleOrder shuffleOrder) {
        int size = getSize();
        if (shuffleOrder.getLength() != size) {
            shuffleOrder = shuffleOrder.cloneAndClear().cloneAndInsert(0, size);
        }
        this.shuffleOrder = shuffleOrder;
        return createTimeline();
    }

    public void prepare(TransferListener mediaTransferListener) {
        Assertions.checkState(!this.isPrepared);
        this.mediaTransferListener = mediaTransferListener;
        for (int i = 0; i < this.mediaSourceHolders.size(); i++) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(i);
            prepareChildSource(mediaSourceHolder);
            this.enabledMediaSourceHolders.add(mediaSourceHolder);
        }
        this.isPrepared = true;
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        Object mediaSourceHolderUid = getMediaSourceHolderUid(id.periodUid);
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithPeriodUid = id.copyWithPeriodUid(getChildPeriodUid(id.periodUid));
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) Assertions.checkNotNull(this.mediaSourceByUid.get(mediaSourceHolderUid));
        enableMediaSource(mediaSourceHolder);
        mediaSourceHolder.activeMediaPeriodIds.add(mediaPeriodIdCopyWithPeriodUid);
        MaskingMediaPeriod maskingMediaPeriodCreatePeriod = mediaSourceHolder.mediaSource.createPeriod(mediaPeriodIdCopyWithPeriodUid, allocator, startPositionUs);
        this.mediaSourceByMediaPeriod.put(maskingMediaPeriodCreatePeriod, mediaSourceHolder);
        disableUnusedMediaSources();
        return maskingMediaPeriodCreatePeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) Assertions.checkNotNull(this.mediaSourceByMediaPeriod.remove(mediaPeriod));
        mediaSourceHolder.mediaSource.releasePeriod(mediaPeriod);
        mediaSourceHolder.activeMediaPeriodIds.remove(((MaskingMediaPeriod) mediaPeriod).id);
        if (!this.mediaSourceByMediaPeriod.isEmpty()) {
            disableUnusedMediaSources();
        }
        maybeReleaseChildSource(mediaSourceHolder);
    }

    public void release() {
        for (MediaSourceAndListener mediaSourceAndListener : this.childSources.values()) {
            try {
                mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to release child source.", e);
            }
            mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
            mediaSourceAndListener.mediaSource.removeDrmEventListener(mediaSourceAndListener.eventListener);
        }
        this.childSources.clear();
        this.enabledMediaSourceHolders.clear();
        this.isPrepared = false;
    }

    public Timeline createTimeline() {
        if (this.mediaSourceHolders.isEmpty()) {
            return Timeline.EMPTY;
        }
        int windowCount = 0;
        for (int i = 0; i < this.mediaSourceHolders.size(); i++) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(i);
            mediaSourceHolder.firstWindowIndexInChild = windowCount;
            windowCount += mediaSourceHolder.mediaSource.getTimeline().getWindowCount();
        }
        return new PlaylistTimeline(this.mediaSourceHolders, this.shuffleOrder);
    }

    private void enableMediaSource(MediaSourceHolder mediaSourceHolder) {
        this.enabledMediaSourceHolders.add(mediaSourceHolder);
        MediaSourceAndListener mediaSourceAndListener = this.childSources.get(mediaSourceHolder);
        if (mediaSourceAndListener != null) {
            mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
        }
    }

    private void disableUnusedMediaSources() {
        Iterator<MediaSourceHolder> it = this.enabledMediaSourceHolders.iterator();
        while (it.hasNext()) {
            MediaSourceHolder next = it.next();
            if (next.activeMediaPeriodIds.isEmpty()) {
                disableChildSource(next);
                it.remove();
            }
        }
    }

    private void disableChildSource(MediaSourceHolder holder) {
        MediaSourceAndListener mediaSourceAndListener = this.childSources.get(holder);
        if (mediaSourceAndListener != null) {
            mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
        }
    }

    private void removeMediaSourcesInternal(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            MediaSourceHolder mediaSourceHolderRemove = this.mediaSourceHolders.remove(i);
            this.mediaSourceByUid.remove(mediaSourceHolderRemove.uid);
            correctOffsets(i, -mediaSourceHolderRemove.mediaSource.getTimeline().getWindowCount());
            mediaSourceHolderRemove.isRemoved = true;
            if (this.isPrepared) {
                maybeReleaseChildSource(mediaSourceHolderRemove);
            }
        }
    }

    private void correctOffsets(int startIndex, int windowOffsetUpdate) {
        while (startIndex < this.mediaSourceHolders.size()) {
            this.mediaSourceHolders.get(startIndex).firstWindowIndexInChild += windowOffsetUpdate;
            startIndex++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSourceHolder mediaSourceHolder, MediaSource.MediaPeriodId mediaPeriodId) {
        for (int i = 0; i < mediaSourceHolder.activeMediaPeriodIds.size(); i++) {
            if (mediaSourceHolder.activeMediaPeriodIds.get(i).windowSequenceNumber == mediaPeriodId.windowSequenceNumber) {
                return mediaPeriodId.copyWithPeriodUid(getPeriodUid(mediaSourceHolder, mediaPeriodId.periodUid));
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getWindowIndexForChildWindowIndex(MediaSourceHolder mediaSourceHolder, int windowIndex) {
        return windowIndex + mediaSourceHolder.firstWindowIndexInChild;
    }

    private void prepareChildSource(MediaSourceHolder holder) {
        MaskingMediaSource maskingMediaSource = holder.mediaSource;
        MediaSource.MediaSourceCaller mediaSourceCaller = new MediaSource.MediaSourceCaller() { // from class: com.google.android.exoplayer2.MediaSourceList$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
            public final void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
                this.f$0.m136x21892de0(mediaSource, timeline);
            }
        };
        ForwardingEventListener forwardingEventListener = new ForwardingEventListener(holder);
        this.childSources.put(holder, new MediaSourceAndListener(maskingMediaSource, mediaSourceCaller, forwardingEventListener));
        maskingMediaSource.addEventListener(Util.createHandlerForCurrentOrMainLooper(), forwardingEventListener);
        maskingMediaSource.addDrmEventListener(Util.createHandlerForCurrentOrMainLooper(), forwardingEventListener);
        maskingMediaSource.prepareSource(mediaSourceCaller, this.mediaTransferListener);
    }

    /* renamed from: lambda$prepareChildSource$0$com-google-android-exoplayer2-MediaSourceList, reason: not valid java name */
    /* synthetic */ void m136x21892de0(MediaSource mediaSource, Timeline timeline) {
        this.mediaSourceListInfoListener.onPlaylistUpdateRequested();
    }

    private void maybeReleaseChildSource(MediaSourceHolder mediaSourceHolder) {
        if (mediaSourceHolder.isRemoved && mediaSourceHolder.activeMediaPeriodIds.isEmpty()) {
            MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.remove(mediaSourceHolder));
            mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
            mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
            mediaSourceAndListener.mediaSource.removeDrmEventListener(mediaSourceAndListener.eventListener);
            this.enabledMediaSourceHolders.remove(mediaSourceHolder);
        }
    }

    private static Object getMediaSourceHolderUid(Object periodUid) {
        return PlaylistTimeline.getChildTimelineUidFromConcatenatedUid(periodUid);
    }

    private static Object getChildPeriodUid(Object periodUid) {
        return PlaylistTimeline.getChildPeriodUidFromConcatenatedUid(periodUid);
    }

    private static Object getPeriodUid(MediaSourceHolder holder, Object childPeriodUid) {
        return PlaylistTimeline.getConcatenatedUid(holder.uid, childPeriodUid);
    }

    static final class MediaSourceHolder implements MediaSourceInfoHolder {
        public int firstWindowIndexInChild;
        public boolean isRemoved;
        public final MaskingMediaSource mediaSource;
        public final List<MediaSource.MediaPeriodId> activeMediaPeriodIds = new ArrayList();
        public final Object uid = new Object();

        public MediaSourceHolder(MediaSource mediaSource, boolean useLazyPreparation) {
            this.mediaSource = new MaskingMediaSource(mediaSource, useLazyPreparation);
        }

        public void reset(int firstWindowIndexInChild) {
            this.firstWindowIndexInChild = firstWindowIndexInChild;
            this.isRemoved = false;
            this.activeMediaPeriodIds.clear();
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Object getUid() {
            return this.uid;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Timeline getTimeline() {
            return this.mediaSource.getTimeline();
        }
    }

    private static final class MediaSourceAndListener {
        public final MediaSource.MediaSourceCaller caller;
        public final ForwardingEventListener eventListener;
        public final MediaSource mediaSource;

        public MediaSourceAndListener(MediaSource mediaSource, MediaSource.MediaSourceCaller caller, ForwardingEventListener eventListener) {
            this.mediaSource = mediaSource;
            this.caller = caller;
            this.eventListener = eventListener;
        }
    }

    private final class ForwardingEventListener implements MediaSourceEventListener, DrmSessionEventListener {
        private DrmSessionEventListener.EventDispatcher drmEventDispatcher;
        private final MediaSourceHolder id;
        private MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;

        public ForwardingEventListener(MediaSourceHolder id) {
            this.mediaSourceEventDispatcher = MediaSourceList.this.mediaSourceEventDispatcher;
            this.drmEventDispatcher = MediaSourceList.this.drmEventDispatcher;
            this.id = id;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadStarted(loadEventData, mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCompleted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadCompleted(loadEventData, mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCanceled(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadCanceled(loadEventData, mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventData, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadError(loadEventData, mediaLoadData, error, wasCanceled);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.upstreamDiscarded(mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onDownstreamFormatChanged(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.downstreamFormatChanged(mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionAcquired(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, int state) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionAcquired(state);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysLoaded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysLoaded();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionManagerError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, Exception error) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionManagerError(error);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRestored(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysRestored();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRemoved(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysRemoved();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionReleased();
            }
        }

        private boolean maybeUpdateEventDispatcher(int childWindowIndex, MediaSource.MediaPeriodId childMediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodIdForChildMediaPeriodId;
            if (childMediaPeriodId != null) {
                mediaPeriodIdForChildMediaPeriodId = MediaSourceList.getMediaPeriodIdForChildMediaPeriodId(this.id, childMediaPeriodId);
                if (mediaPeriodIdForChildMediaPeriodId == null) {
                    return false;
                }
            } else {
                mediaPeriodIdForChildMediaPeriodId = null;
            }
            int windowIndexForChildWindowIndex = MediaSourceList.getWindowIndexForChildWindowIndex(this.id, childWindowIndex);
            if (this.mediaSourceEventDispatcher.windowIndex != windowIndexForChildWindowIndex || !Util.areEqual(this.mediaSourceEventDispatcher.mediaPeriodId, mediaPeriodIdForChildMediaPeriodId)) {
                this.mediaSourceEventDispatcher = MediaSourceList.this.mediaSourceEventDispatcher.withParameters(windowIndexForChildWindowIndex, mediaPeriodIdForChildMediaPeriodId, 0L);
            }
            if (this.drmEventDispatcher.windowIndex == windowIndexForChildWindowIndex && Util.areEqual(this.drmEventDispatcher.mediaPeriodId, mediaPeriodIdForChildMediaPeriodId)) {
                return true;
            }
            this.drmEventDispatcher = MediaSourceList.this.drmEventDispatcher.withParameters(windowIndexForChildWindowIndex, mediaPeriodIdForChildMediaPeriodId);
            return true;
        }
    }
}
