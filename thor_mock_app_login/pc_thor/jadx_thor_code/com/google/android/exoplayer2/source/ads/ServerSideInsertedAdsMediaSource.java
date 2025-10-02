package com.google.android.exoplayer2.source.ads;

import android.os.Handler;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ServerSideInsertedAdsMediaSource extends BaseMediaSource implements MediaSource.MediaSourceCaller, MediaSourceEventListener, DrmSessionEventListener {
    private Timeline contentTimeline;
    private SharedMediaPeriod lastUsedMediaPeriod;
    private final MediaSource mediaSource;
    private Handler playbackHandler;
    private final ListMultimap<Long, SharedMediaPeriod> mediaPeriods = ArrayListMultimap.create();
    private AdPlaybackState adPlaybackState = AdPlaybackState.NONE;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcherWithoutId = createEventDispatcher(null);
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcherWithoutId = createDrmEventDispatcher(null);

    public ServerSideInsertedAdsMediaSource(MediaSource mediaSource) {
        this.mediaSource = mediaSource;
    }

    public void setAdPlaybackState(final AdPlaybackState adPlaybackState) {
        Assertions.checkArgument(adPlaybackState.adGroupCount >= this.adPlaybackState.adGroupCount);
        for (int i = adPlaybackState.removedAdGroupCount; i < adPlaybackState.adGroupCount; i++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
            Assertions.checkArgument(adGroup.isServerSideInserted);
            if (i < this.adPlaybackState.adGroupCount) {
                Assertions.checkArgument(ServerSideInsertedAdsUtil.getAdCountInGroup(adPlaybackState, i) >= ServerSideInsertedAdsUtil.getAdCountInGroup(this.adPlaybackState, i));
            }
            if (adGroup.timeUs == Long.MIN_VALUE) {
                Assertions.checkArgument(ServerSideInsertedAdsUtil.getAdCountInGroup(adPlaybackState, i) == 0);
            }
        }
        synchronized (this) {
            Handler handler = this.playbackHandler;
            if (handler == null) {
                this.adPlaybackState = adPlaybackState;
            } else {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.ServerSideInsertedAdsMediaSource$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m188xa5a83354(adPlaybackState);
                    }
                });
            }
        }
    }

    /* renamed from: lambda$setAdPlaybackState$0$com-google-android-exoplayer2-source-ads-ServerSideInsertedAdsMediaSource, reason: not valid java name */
    /* synthetic */ void m188xa5a83354(AdPlaybackState adPlaybackState) {
        Iterator<SharedMediaPeriod> it = this.mediaPeriods.values().iterator();
        while (it.hasNext()) {
            it.next().updateAdPlaybackState(adPlaybackState);
        }
        SharedMediaPeriod sharedMediaPeriod = this.lastUsedMediaPeriod;
        if (sharedMediaPeriod != null) {
            sharedMediaPeriod.updateAdPlaybackState(adPlaybackState);
        }
        this.adPlaybackState = adPlaybackState;
        if (this.contentTimeline != null) {
            refreshSourceInfo(new ServerSideInsertedAdsTimeline(this.contentTimeline, adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        Handler handlerCreateHandlerForCurrentLooper = Util.createHandlerForCurrentLooper();
        synchronized (this) {
            this.playbackHandler = handlerCreateHandlerForCurrentLooper;
        }
        this.mediaSource.addEventListener(handlerCreateHandlerForCurrentLooper, this);
        this.mediaSource.addDrmEventListener(handlerCreateHandlerForCurrentLooper, this);
        this.mediaSource.prepareSource(this, mediaTransferListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.mediaSource.maybeThrowSourceInfoRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void enableInternal() {
        this.mediaSource.enable(this);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void disableInternal() {
        releaseLastUsedMediaPeriod();
        this.mediaSource.disable(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
    public void onSourceInfoRefreshed(MediaSource source, Timeline timeline) {
        this.contentTimeline = timeline;
        if (AdPlaybackState.NONE.equals(this.adPlaybackState)) {
            return;
        }
        refreshSourceInfo(new ServerSideInsertedAdsTimeline(timeline, this.adPlaybackState));
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        releaseLastUsedMediaPeriod();
        this.contentTimeline = null;
        synchronized (this) {
            this.playbackHandler = null;
        }
        this.mediaSource.releaseSource(this);
        this.mediaSource.removeEventListener(this);
        this.mediaSource.removeDrmEventListener(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        SharedMediaPeriod sharedMediaPeriod = this.lastUsedMediaPeriod;
        if (sharedMediaPeriod != null) {
            this.lastUsedMediaPeriod = null;
            this.mediaPeriods.put(Long.valueOf(id.windowSequenceNumber), sharedMediaPeriod);
        } else {
            sharedMediaPeriod = (SharedMediaPeriod) Iterables.getLast(this.mediaPeriods.get((ListMultimap<Long, SharedMediaPeriod>) Long.valueOf(id.windowSequenceNumber)), null);
            if (sharedMediaPeriod == null || !sharedMediaPeriod.canReuseMediaPeriod(id, startPositionUs)) {
                sharedMediaPeriod = new SharedMediaPeriod(this.mediaSource.createPeriod(new MediaSource.MediaPeriodId(id.periodUid, id.windowSequenceNumber), allocator, ServerSideInsertedAdsUtil.getStreamPositionUs(startPositionUs, id, this.adPlaybackState)), this.adPlaybackState);
                this.mediaPeriods.put(Long.valueOf(id.windowSequenceNumber), sharedMediaPeriod);
            }
        }
        MediaPeriodImpl mediaPeriodImpl = new MediaPeriodImpl(sharedMediaPeriod, id, createEventDispatcher(id), createDrmEventDispatcher(id));
        sharedMediaPeriod.add(mediaPeriodImpl);
        return mediaPeriodImpl;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MediaPeriodImpl mediaPeriodImpl = (MediaPeriodImpl) mediaPeriod;
        mediaPeriodImpl.sharedPeriod.remove(mediaPeriodImpl);
        if (mediaPeriodImpl.sharedPeriod.isUnused()) {
            this.mediaPeriods.remove(Long.valueOf(mediaPeriodImpl.mediaPeriodId.windowSequenceNumber), mediaPeriodImpl.sharedPeriod);
            if (this.mediaPeriods.isEmpty()) {
                this.lastUsedMediaPeriod = mediaPeriodImpl.sharedPeriod;
            } else {
                mediaPeriodImpl.sharedPeriod.release(this.mediaSource);
            }
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionAcquired(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, int state) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, true);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmSessionAcquired(state);
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmSessionAcquired(state);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysLoaded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmKeysLoaded();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmKeysLoaded();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionManagerError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, Exception error) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmSessionManagerError(error);
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmSessionManagerError(error);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysRestored(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmKeysRestored();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmKeysRestored();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysRemoved(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmKeysRemoved();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmKeysRemoved();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmSessionReleased();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmSessionReleased();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadStarted(loadEventInfo, mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onLoadStarted(loadEventInfo, mediaLoadData);
            mediaPeriodForEvent.mediaSourceEventDispatcher.loadStarted(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadCompleted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadCompleted(loadEventInfo, mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onLoadFinished(loadEventInfo);
            mediaPeriodForEvent.mediaSourceEventDispatcher.loadCompleted(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadCanceled(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadCanceled(loadEventInfo, mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onLoadFinished(loadEventInfo);
            mediaPeriodForEvent.mediaSourceEventDispatcher.loadCanceled(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadError(loadEventInfo, mediaLoadData, error, wasCanceled);
            return;
        }
        if (wasCanceled) {
            mediaPeriodForEvent.sharedPeriod.onLoadFinished(loadEventInfo);
        }
        mediaPeriodForEvent.mediaSourceEventDispatcher.loadError(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState), error, wasCanceled);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, false);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.upstreamDiscarded(mediaLoadData);
        } else {
            mediaPeriodForEvent.mediaSourceEventDispatcher.upstreamDiscarded(correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onDownstreamFormatChanged(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, false);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.downstreamFormatChanged(mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onDownstreamFormatChanged(mediaPeriodForEvent, mediaLoadData);
            mediaPeriodForEvent.mediaSourceEventDispatcher.downstreamFormatChanged(correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    private void releaseLastUsedMediaPeriod() {
        SharedMediaPeriod sharedMediaPeriod = this.lastUsedMediaPeriod;
        if (sharedMediaPeriod != null) {
            sharedMediaPeriod.release(this.mediaSource);
            this.lastUsedMediaPeriod = null;
        }
    }

    private MediaPeriodImpl getMediaPeriodForEvent(MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData, boolean useLoadingPeriod) {
        if (mediaPeriodId == null) {
            return null;
        }
        List<SharedMediaPeriod> list = this.mediaPeriods.get((ListMultimap<Long, SharedMediaPeriod>) Long.valueOf(mediaPeriodId.windowSequenceNumber));
        if (list.isEmpty()) {
            return null;
        }
        if (useLoadingPeriod) {
            SharedMediaPeriod sharedMediaPeriod = (SharedMediaPeriod) Iterables.getLast(list);
            return sharedMediaPeriod.loadingPeriod != null ? sharedMediaPeriod.loadingPeriod : (MediaPeriodImpl) Iterables.getLast(sharedMediaPeriod.mediaPeriods);
        }
        for (int i = 0; i < list.size(); i++) {
            MediaPeriodImpl mediaPeriodForEvent = list.get(i).getMediaPeriodForEvent(mediaLoadData);
            if (mediaPeriodForEvent != null) {
                return mediaPeriodForEvent;
            }
        }
        return (MediaPeriodImpl) list.get(0).mediaPeriods.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getMediaPeriodEndPositionUs(MediaPeriodImpl mediaPeriod, AdPlaybackState adPlaybackState) {
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriod.mediaPeriodId;
        if (mediaPeriodId.isAd()) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(mediaPeriodId.adGroupIndex);
            if (adGroup.count == -1) {
                return 0L;
            }
            return adGroup.durationsUs[mediaPeriodId.adIndexInAdGroup];
        }
        if (mediaPeriodId.nextAdGroupIndex == -1) {
            return Long.MAX_VALUE;
        }
        AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(mediaPeriodId.nextAdGroupIndex);
        if (adGroup2.timeUs == Long.MIN_VALUE) {
            return Long.MAX_VALUE;
        }
        return adGroup2.timeUs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaLoadData correctMediaLoadData(MediaPeriodImpl mediaPeriod, MediaLoadData mediaLoadData, AdPlaybackState adPlaybackState) {
        return new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, correctMediaLoadDataPositionMs(mediaLoadData.mediaStartTimeMs, mediaPeriod, adPlaybackState), correctMediaLoadDataPositionMs(mediaLoadData.mediaEndTimeMs, mediaPeriod, adPlaybackState));
    }

    private static long correctMediaLoadDataPositionMs(long mediaPositionMs, MediaPeriodImpl mediaPeriod, AdPlaybackState adPlaybackState) {
        long mediaPeriodPositionUsForContent;
        if (mediaPositionMs == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long jMsToUs = C.msToUs(mediaPositionMs);
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriod.mediaPeriodId;
        if (mediaPeriodId.isAd()) {
            mediaPeriodPositionUsForContent = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForAd(jMsToUs, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        } else {
            mediaPeriodPositionUsForContent = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(jMsToUs, -1, adPlaybackState);
        }
        return C.usToMs(mediaPeriodPositionUsForContent);
    }

    private static final class SharedMediaPeriod implements MediaPeriod.Callback {
        private final MediaPeriod actualMediaPeriod;
        private AdPlaybackState adPlaybackState;
        private boolean hasStartedPreparing;
        private boolean isPrepared;
        private MediaPeriodImpl loadingPeriod;
        private final List<MediaPeriodImpl> mediaPeriods = new ArrayList();
        private final Map<Long, Pair<LoadEventInfo, MediaLoadData>> activeLoads = new HashMap();
        public ExoTrackSelection[] trackSelections = new ExoTrackSelection[0];
        public SampleStream[] sampleStreams = new SampleStream[0];
        public MediaLoadData[] lastDownstreamFormatChangeData = new MediaLoadData[0];

        public SharedMediaPeriod(MediaPeriod actualMediaPeriod, AdPlaybackState adPlaybackState) {
            this.actualMediaPeriod = actualMediaPeriod;
            this.adPlaybackState = adPlaybackState;
        }

        public void updateAdPlaybackState(AdPlaybackState adPlaybackState) {
            this.adPlaybackState = adPlaybackState;
        }

        public void add(MediaPeriodImpl mediaPeriod) {
            this.mediaPeriods.add(mediaPeriod);
        }

        public void remove(MediaPeriodImpl mediaPeriod) {
            if (mediaPeriod.equals(this.loadingPeriod)) {
                this.loadingPeriod = null;
                this.activeLoads.clear();
            }
            this.mediaPeriods.remove(mediaPeriod);
        }

        public boolean isUnused() {
            return this.mediaPeriods.isEmpty();
        }

        public void release(MediaSource mediaSource) {
            mediaSource.releasePeriod(this.actualMediaPeriod);
        }

        public boolean canReuseMediaPeriod(MediaSource.MediaPeriodId id, long positionUs) {
            MediaPeriodImpl mediaPeriodImpl = (MediaPeriodImpl) Iterables.getLast(this.mediaPeriods);
            return ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, id, this.adPlaybackState) == ServerSideInsertedAdsUtil.getStreamPositionUs(ServerSideInsertedAdsMediaSource.getMediaPeriodEndPositionUs(mediaPeriodImpl, this.adPlaybackState), mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        public MediaPeriodImpl getMediaPeriodForEvent(MediaLoadData mediaLoadData) {
            if (mediaLoadData == null || mediaLoadData.mediaStartTimeMs == C.TIME_UNSET) {
                return null;
            }
            for (int i = 0; i < this.mediaPeriods.size(); i++) {
                MediaPeriodImpl mediaPeriodImpl = this.mediaPeriods.get(i);
                long mediaPeriodPositionUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(C.msToUs(mediaLoadData.mediaStartTimeMs), mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
                long mediaPeriodEndPositionUs = ServerSideInsertedAdsMediaSource.getMediaPeriodEndPositionUs(mediaPeriodImpl, this.adPlaybackState);
                if (mediaPeriodPositionUs >= 0 && mediaPeriodPositionUs < mediaPeriodEndPositionUs) {
                    return mediaPeriodImpl;
                }
            }
            return null;
        }

        public void prepare(MediaPeriodImpl mediaPeriod, long positionUs) {
            mediaPeriod.lastStartPositionUs = positionUs;
            if (this.hasStartedPreparing) {
                if (this.isPrepared) {
                    ((MediaPeriod.Callback) Assertions.checkNotNull(mediaPeriod.callback)).onPrepared(mediaPeriod);
                }
            } else {
                this.hasStartedPreparing = true;
                this.actualMediaPeriod.prepare(this, ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState));
            }
        }

        public void maybeThrowPrepareError() throws IOException {
            this.actualMediaPeriod.maybeThrowPrepareError();
        }

        public TrackGroupArray getTrackGroups() {
            return this.actualMediaPeriod.getTrackGroups();
        }

        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
            return this.actualMediaPeriod.getStreamKeys(trackSelections);
        }

        public boolean continueLoading(MediaPeriodImpl mediaPeriod, long positionUs) {
            MediaPeriodImpl mediaPeriodImpl = this.loadingPeriod;
            if (mediaPeriodImpl != null && !mediaPeriod.equals(mediaPeriodImpl)) {
                for (Pair<LoadEventInfo, MediaLoadData> pair : this.activeLoads.values()) {
                    mediaPeriodImpl.mediaSourceEventDispatcher.loadCompleted((LoadEventInfo) pair.first, ServerSideInsertedAdsMediaSource.correctMediaLoadData(mediaPeriodImpl, (MediaLoadData) pair.second, this.adPlaybackState));
                    mediaPeriod.mediaSourceEventDispatcher.loadStarted((LoadEventInfo) pair.first, ServerSideInsertedAdsMediaSource.correctMediaLoadData(mediaPeriod, (MediaLoadData) pair.second, this.adPlaybackState));
                }
            }
            this.loadingPeriod = mediaPeriod;
            return this.actualMediaPeriod.continueLoading(getStreamPositionUsWithNotYetStartedHandling(mediaPeriod, positionUs));
        }

        public boolean isLoading(MediaPeriodImpl mediaPeriod) {
            return mediaPeriod.equals(this.loadingPeriod) && this.actualMediaPeriod.isLoading();
        }

        public long getBufferedPositionUs(MediaPeriodImpl mediaPeriod) {
            return getMediaPeriodPositionUsWithEndOfSourceHandling(mediaPeriod, this.actualMediaPeriod.getBufferedPositionUs());
        }

        public long getNextLoadPositionUs(MediaPeriodImpl mediaPeriod) {
            return getMediaPeriodPositionUsWithEndOfSourceHandling(mediaPeriod, this.actualMediaPeriod.getNextLoadPositionUs());
        }

        public long seekToUs(MediaPeriodImpl mediaPeriod, long positionUs) {
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(this.actualMediaPeriod.seekToUs(ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState)), mediaPeriod.mediaPeriodId, this.adPlaybackState);
        }

        public long getAdjustedSeekPositionUs(MediaPeriodImpl mediaPeriod, long positionUs, SeekParameters seekParameters) {
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(this.actualMediaPeriod.getAdjustedSeekPositionUs(ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState), seekParameters), mediaPeriod.mediaPeriodId, this.adPlaybackState);
        }

        public void discardBuffer(MediaPeriodImpl mediaPeriod, long positionUs, boolean toKeyframe) {
            this.actualMediaPeriod.discardBuffer(ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState), toKeyframe);
        }

        public void reevaluateBuffer(MediaPeriodImpl mediaPeriod, long positionUs) {
            this.actualMediaPeriod.reevaluateBuffer(getStreamPositionUsWithNotYetStartedHandling(mediaPeriod, positionUs));
        }

        public long readDiscontinuity(MediaPeriodImpl mediaPeriod) {
            if (!mediaPeriod.equals(this.mediaPeriods.get(0))) {
                return C.TIME_UNSET;
            }
            long discontinuity = this.actualMediaPeriod.readDiscontinuity();
            return discontinuity == C.TIME_UNSET ? C.TIME_UNSET : ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(discontinuity, mediaPeriod.mediaPeriodId, this.adPlaybackState);
        }

        public long selectTracks(MediaPeriodImpl mediaPeriod, ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
            SampleStream emptySampleStream;
            SampleStream[] sampleStreamArr;
            mediaPeriod.lastStartPositionUs = positionUs;
            if (mediaPeriod.equals(this.mediaPeriods.get(0))) {
                this.trackSelections = (ExoTrackSelection[]) Arrays.copyOf(selections, selections.length);
                long streamPositionUs = ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState);
                SampleStream[] sampleStreamArr2 = this.sampleStreams;
                if (sampleStreamArr2.length == 0) {
                    sampleStreamArr = new SampleStream[selections.length];
                } else {
                    sampleStreamArr = (SampleStream[]) Arrays.copyOf(sampleStreamArr2, sampleStreamArr2.length);
                }
                SampleStream[] sampleStreamArr3 = sampleStreamArr;
                long jSelectTracks = this.actualMediaPeriod.selectTracks(selections, mayRetainStreamFlags, sampleStreamArr3, streamResetFlags, streamPositionUs);
                this.sampleStreams = (SampleStream[]) Arrays.copyOf(sampleStreamArr3, sampleStreamArr3.length);
                this.lastDownstreamFormatChangeData = (MediaLoadData[]) Arrays.copyOf(this.lastDownstreamFormatChangeData, sampleStreamArr3.length);
                for (int i = 0; i < sampleStreamArr3.length; i++) {
                    if (sampleStreamArr3[i] == null) {
                        streams[i] = null;
                        this.lastDownstreamFormatChangeData[i] = null;
                    } else if (streams[i] == null || streamResetFlags[i]) {
                        streams[i] = new SampleStreamImpl(mediaPeriod, i);
                        this.lastDownstreamFormatChangeData[i] = null;
                    }
                }
                return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(jSelectTracks, mediaPeriod.mediaPeriodId, this.adPlaybackState);
            }
            for (int i2 = 0; i2 < selections.length; i2++) {
                ExoTrackSelection exoTrackSelection = selections[i2];
                boolean z = true;
                if (exoTrackSelection != null) {
                    if (mayRetainStreamFlags[i2] && streams[i2] != null) {
                        z = false;
                    }
                    streamResetFlags[i2] = z;
                    if (z) {
                        if (Util.areEqual(this.trackSelections[i2], exoTrackSelection)) {
                            emptySampleStream = new SampleStreamImpl(mediaPeriod, i2);
                        } else {
                            emptySampleStream = new EmptySampleStream();
                        }
                        streams[i2] = emptySampleStream;
                    }
                } else {
                    streams[i2] = null;
                    streamResetFlags[i2] = true;
                }
            }
            return positionUs;
        }

        public int readData(MediaPeriodImpl mediaPeriod, int streamIndex, FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            int data = ((SampleStream) Util.castNonNull(this.sampleStreams[streamIndex])).readData(formatHolder, buffer, readFlags | 1 | 4);
            long mediaPeriodPositionUsWithEndOfSourceHandling = getMediaPeriodPositionUsWithEndOfSourceHandling(mediaPeriod, buffer.timeUs);
            if ((data == -4 && mediaPeriodPositionUsWithEndOfSourceHandling == Long.MIN_VALUE) || (data == -3 && getBufferedPositionUs(mediaPeriod) == Long.MIN_VALUE && !buffer.waitingForKeys)) {
                maybeNotifyDownstreamFormatChanged(mediaPeriod, streamIndex);
                buffer.clear();
                buffer.addFlag(4);
                return -4;
            }
            if (data == -4) {
                maybeNotifyDownstreamFormatChanged(mediaPeriod, streamIndex);
                ((SampleStream) Util.castNonNull(this.sampleStreams[streamIndex])).readData(formatHolder, buffer, readFlags);
                buffer.timeUs = mediaPeriodPositionUsWithEndOfSourceHandling;
            }
            return data;
        }

        public int skipData(MediaPeriodImpl mediaPeriod, int streamIndex, long positionUs) {
            return ((SampleStream) Util.castNonNull(this.sampleStreams[streamIndex])).skipData(ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState));
        }

        public boolean isReady(int streamIndex) {
            return ((SampleStream) Util.castNonNull(this.sampleStreams[streamIndex])).isReady();
        }

        public void maybeThrowError(int streamIndex) throws IOException {
            ((SampleStream) Util.castNonNull(this.sampleStreams[streamIndex])).maybeThrowError();
        }

        public void onDownstreamFormatChanged(MediaPeriodImpl mediaPeriod, MediaLoadData mediaLoadData) {
            int iFindMatchingStreamIndex = findMatchingStreamIndex(mediaLoadData);
            if (iFindMatchingStreamIndex != -1) {
                this.lastDownstreamFormatChangeData[iFindMatchingStreamIndex] = mediaLoadData;
                mediaPeriod.hasNotifiedDownstreamFormatChange[iFindMatchingStreamIndex] = true;
            }
        }

        public void onLoadStarted(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            this.activeLoads.put(Long.valueOf(loadEventInfo.loadTaskId), Pair.create(loadEventInfo, mediaLoadData));
        }

        public void onLoadFinished(LoadEventInfo loadEventInfo) {
            this.activeLoads.remove(Long.valueOf(loadEventInfo.loadTaskId));
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod actualMediaPeriod) {
            this.isPrepared = true;
            for (int i = 0; i < this.mediaPeriods.size(); i++) {
                MediaPeriodImpl mediaPeriodImpl = this.mediaPeriods.get(i);
                if (mediaPeriodImpl.callback != null) {
                    mediaPeriodImpl.callback.onPrepared(mediaPeriodImpl);
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
        public void onContinueLoadingRequested(MediaPeriod source) {
            MediaPeriodImpl mediaPeriodImpl = this.loadingPeriod;
            if (mediaPeriodImpl == null) {
                return;
            }
            ((MediaPeriod.Callback) Assertions.checkNotNull(mediaPeriodImpl.callback)).onContinueLoadingRequested(this.loadingPeriod);
        }

        private long getStreamPositionUsWithNotYetStartedHandling(MediaPeriodImpl mediaPeriod, long positionUs) {
            if (positionUs < mediaPeriod.lastStartPositionUs) {
                return ServerSideInsertedAdsUtil.getStreamPositionUs(mediaPeriod.lastStartPositionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState) - (mediaPeriod.lastStartPositionUs - positionUs);
            }
            return ServerSideInsertedAdsUtil.getStreamPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState);
        }

        private long getMediaPeriodPositionUsWithEndOfSourceHandling(MediaPeriodImpl mediaPeriod, long positionUs) {
            if (positionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            long mediaPeriodPositionUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(positionUs, mediaPeriod.mediaPeriodId, this.adPlaybackState);
            if (mediaPeriodPositionUs >= ServerSideInsertedAdsMediaSource.getMediaPeriodEndPositionUs(mediaPeriod, this.adPlaybackState)) {
                return Long.MIN_VALUE;
            }
            return mediaPeriodPositionUs;
        }

        private int findMatchingStreamIndex(MediaLoadData mediaLoadData) {
            if (mediaLoadData.trackFormat == null) {
                return -1;
            }
            int i = 0;
            loop0: while (true) {
                ExoTrackSelection[] exoTrackSelectionArr = this.trackSelections;
                if (i >= exoTrackSelectionArr.length) {
                    return -1;
                }
                ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i];
                if (exoTrackSelection != null) {
                    TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                    boolean z = mediaLoadData.trackType == 0 && trackGroup.equals(getTrackGroups().get(0));
                    for (int i2 = 0; i2 < trackGroup.length; i2++) {
                        Format format = trackGroup.getFormat(i2);
                        if (format.equals(mediaLoadData.trackFormat) || (z && format.id != null && format.id.equals(mediaLoadData.trackFormat.id))) {
                            break loop0;
                        }
                    }
                }
                i++;
            }
            return i;
        }

        private void maybeNotifyDownstreamFormatChanged(MediaPeriodImpl mediaPeriod, int streamIndex) {
            if (mediaPeriod.hasNotifiedDownstreamFormatChange[streamIndex] || this.lastDownstreamFormatChangeData[streamIndex] == null) {
                return;
            }
            mediaPeriod.hasNotifiedDownstreamFormatChange[streamIndex] = true;
            mediaPeriod.mediaSourceEventDispatcher.downstreamFormatChanged(ServerSideInsertedAdsMediaSource.correctMediaLoadData(mediaPeriod, this.lastDownstreamFormatChangeData[streamIndex], this.adPlaybackState));
        }
    }

    private static final class ServerSideInsertedAdsTimeline extends ForwardingTimeline {
        private final AdPlaybackState adPlaybackState;

        public ServerSideInsertedAdsTimeline(Timeline contentTimeline, AdPlaybackState adPlaybackState) {
            super(contentTimeline);
            Assertions.checkState(contentTimeline.getPeriodCount() == 1);
            Assertions.checkState(contentTimeline.getWindowCount() == 1);
            this.adPlaybackState = adPlaybackState;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int windowIndex, Timeline.Window window, long defaultPositionProjectionUs) {
            super.getWindow(windowIndex, window, defaultPositionProjectionUs);
            long mediaPeriodPositionUsForContent = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(window.positionInFirstPeriodUs, -1, this.adPlaybackState);
            if (window.durationUs != C.TIME_UNSET) {
                window.durationUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(window.positionInFirstPeriodUs + window.durationUs, -1, this.adPlaybackState) - mediaPeriodPositionUsForContent;
            } else if (this.adPlaybackState.contentDurationUs != C.TIME_UNSET) {
                window.durationUs = this.adPlaybackState.contentDurationUs - mediaPeriodPositionUsForContent;
            }
            window.positionInFirstPeriodUs = mediaPeriodPositionUsForContent;
            return window;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
            long mediaPeriodPositionUsForContent;
            super.getPeriod(periodIndex, period, setIds);
            long j = period.durationUs;
            if (j == C.TIME_UNSET) {
                mediaPeriodPositionUsForContent = this.adPlaybackState.contentDurationUs;
            } else {
                mediaPeriodPositionUsForContent = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(j, -1, this.adPlaybackState);
            }
            period.set(period.id, period.uid, period.windowIndex, mediaPeriodPositionUsForContent, -ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(-period.getPositionInWindowUs(), -1, this.adPlaybackState), this.adPlaybackState, period.isPlaceholder);
            return period;
        }
    }

    private static final class MediaPeriodImpl implements MediaPeriod {
        public MediaPeriod.Callback callback;
        public final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
        public boolean[] hasNotifiedDownstreamFormatChange = new boolean[0];
        public long lastStartPositionUs;
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
        public final SharedMediaPeriod sharedPeriod;

        public MediaPeriodImpl(SharedMediaPeriod sharedPeriod, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher, DrmSessionEventListener.EventDispatcher drmEventDispatcher) {
            this.sharedPeriod = sharedPeriod;
            this.mediaPeriodId = mediaPeriodId;
            this.mediaSourceEventDispatcher = mediaSourceEventDispatcher;
            this.drmEventDispatcher = drmEventDispatcher;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long positionUs) {
            this.callback = callback;
            this.sharedPeriod.prepare(this, positionUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() throws IOException {
            this.sharedPeriod.maybeThrowPrepareError();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return this.sharedPeriod.getTrackGroups();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
            return this.sharedPeriod.getStreamKeys(trackSelections);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
            if (this.hasNotifiedDownstreamFormatChange.length == 0) {
                this.hasNotifiedDownstreamFormatChange = new boolean[streams.length];
            }
            return this.sharedPeriod.selectTracks(this, selections, mayRetainStreamFlags, streams, streamResetFlags, positionUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long positionUs, boolean toKeyframe) {
            this.sharedPeriod.discardBuffer(this, positionUs, toKeyframe);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            return this.sharedPeriod.readDiscontinuity(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long positionUs) {
            return this.sharedPeriod.seekToUs(this, positionUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
            return this.sharedPeriod.getAdjustedSeekPositionUs(this, positionUs, seekParameters);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            return this.sharedPeriod.getBufferedPositionUs(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            return this.sharedPeriod.getNextLoadPositionUs(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long positionUs) {
            return this.sharedPeriod.continueLoading(this, positionUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return this.sharedPeriod.isLoading(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long positionUs) {
            this.sharedPeriod.reevaluateBuffer(this, positionUs);
        }
    }

    private static final class SampleStreamImpl implements SampleStream {
        private final MediaPeriodImpl mediaPeriod;
        private final int streamIndex;

        public SampleStreamImpl(MediaPeriodImpl mediaPeriod, int streamIndex) {
            this.mediaPeriod = mediaPeriod;
            this.streamIndex = streamIndex;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return this.mediaPeriod.sharedPeriod.isReady(this.streamIndex);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.mediaPeriod.sharedPeriod.maybeThrowError(this.streamIndex);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            return this.mediaPeriod.sharedPeriod.readData(this.mediaPeriod, this.streamIndex, formatHolder, buffer, readFlags);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long positionUs) {
            return this.mediaPeriod.sharedPeriod.skipData(this.mediaPeriod, this.streamIndex, positionUs);
        }
    }
}
