package com.google.android.exoplayer2;

import android.os.Handler;
import android.util.Pair;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;

/* loaded from: classes.dex */
final class MediaPeriodQueue {
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;
    private final AnalyticsCollector analyticsCollector;
    private final Handler analyticsCollectorHandler;
    private int length;
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;
    private MediaPeriodHolder playing;
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private final Timeline.Period period = new Timeline.Period();
    private final Timeline.Window window = new Timeline.Window();

    private boolean areDurationsCompatible(long previousDurationUs, long newDurationUs) {
        return previousDurationUs == C.TIME_UNSET || previousDurationUs == newDurationUs;
    }

    public MediaPeriodQueue(AnalyticsCollector analyticsCollector, Handler analyticsCollectorHandler) {
        this.analyticsCollector = analyticsCollector;
        this.analyticsCollectorHandler = analyticsCollectorHandler;
    }

    public boolean updateRepeatMode(Timeline timeline, int repeatMode) {
        this.repeatMode = repeatMode;
        return updateForPlaybackModeChange(timeline);
    }

    public boolean updateShuffleModeEnabled(Timeline timeline, boolean shuffleModeEnabled) {
        this.shuffleModeEnabled = shuffleModeEnabled;
        return updateForPlaybackModeChange(timeline);
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder != null && mediaPeriodHolder.mediaPeriod == mediaPeriod;
    }

    public void reevaluateBuffer(long rendererPositionUs) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder != null) {
            mediaPeriodHolder.reevaluateBuffer(rendererPositionUs);
        }
    }

    public boolean shouldLoadNextMediaPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder == null || (!mediaPeriodHolder.info.isFinal && this.loading.isFullyBuffered() && this.loading.info.durationUs != C.TIME_UNSET && this.length < 100);
    }

    public MediaPeriodInfo getNextMediaPeriodInfo(long rendererPositionUs, PlaybackInfo playbackInfo) {
        if (this.loading == null) {
            return getFirstMediaPeriodInfo(playbackInfo);
        }
        return getFollowingMediaPeriodInfo(playbackInfo.timeline, this.loading, rendererPositionUs);
    }

    public MediaPeriodHolder enqueueNextMediaPeriodHolder(RendererCapabilities[] rendererCapabilities, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, MediaPeriodInfo info, TrackSelectorResult emptyTrackSelectorResult) {
        long rendererOffset;
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder == null) {
            rendererOffset = (!info.id.isAd() || info.requestedContentPositionUs == C.TIME_UNSET) ? 0L : info.requestedContentPositionUs;
        } else {
            rendererOffset = (mediaPeriodHolder.getRendererOffset() + this.loading.info.durationUs) - info.startPositionUs;
        }
        MediaPeriodHolder mediaPeriodHolder2 = new MediaPeriodHolder(rendererCapabilities, rendererOffset, trackSelector, allocator, mediaSourceList, info, emptyTrackSelectorResult);
        MediaPeriodHolder mediaPeriodHolder3 = this.loading;
        if (mediaPeriodHolder3 != null) {
            mediaPeriodHolder3.setNext(mediaPeriodHolder2);
        } else {
            this.playing = mediaPeriodHolder2;
            this.reading = mediaPeriodHolder2;
        }
        this.oldFrontPeriodUid = null;
        this.loading = mediaPeriodHolder2;
        this.length++;
        notifyQueueUpdate();
        return mediaPeriodHolder2;
    }

    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.reading;
        Assertions.checkState((mediaPeriodHolder == null || mediaPeriodHolder.getNext() == null) ? false : true);
        this.reading = this.reading.getNext();
        notifyQueueUpdate();
        return this.reading;
    }

    public MediaPeriodHolder advancePlayingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder == null) {
            return null;
        }
        if (mediaPeriodHolder == this.reading) {
            this.reading = mediaPeriodHolder.getNext();
        }
        this.playing.release();
        int i = this.length - 1;
        this.length = i;
        if (i == 0) {
            this.loading = null;
            this.oldFrontPeriodUid = this.playing.uid;
            this.oldFrontPeriodWindowSequenceNumber = this.playing.info.id.windowSequenceNumber;
        }
        this.playing = this.playing.getNext();
        notifyQueueUpdate();
        return this.playing;
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        boolean z = false;
        Assertions.checkState(mediaPeriodHolder != null);
        if (mediaPeriodHolder.equals(this.loading)) {
            return false;
        }
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.getNext() != null) {
            mediaPeriodHolder = mediaPeriodHolder.getNext();
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                z = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.setNext(null);
        notifyQueueUpdate();
        return z;
    }

    public void clear() {
        if (this.length == 0) {
            return;
        }
        MediaPeriodHolder next = (MediaPeriodHolder) Assertions.checkStateNotNull(this.playing);
        this.oldFrontPeriodUid = next.uid;
        this.oldFrontPeriodWindowSequenceNumber = next.info.id.windowSequenceNumber;
        while (next != null) {
            next.release();
            next = next.getNext();
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = 0;
        notifyQueueUpdate();
    }

    public boolean updateQueuedPeriods(Timeline timeline, long rendererPositionUs, long maxRendererReadPositionUs) {
        MediaPeriodInfo updatedMediaPeriodInfo;
        MediaPeriodHolder next = this.playing;
        MediaPeriodHolder mediaPeriodHolder = null;
        while (next != null) {
            MediaPeriodInfo mediaPeriodInfo = next.info;
            if (mediaPeriodHolder == null) {
                updatedMediaPeriodInfo = getUpdatedMediaPeriodInfo(timeline, mediaPeriodInfo);
            } else {
                MediaPeriodInfo followingMediaPeriodInfo = getFollowingMediaPeriodInfo(timeline, mediaPeriodHolder, rendererPositionUs);
                if (followingMediaPeriodInfo == null) {
                    return !removeAfter(mediaPeriodHolder);
                }
                if (!canKeepMediaPeriodHolder(mediaPeriodInfo, followingMediaPeriodInfo)) {
                    return !removeAfter(mediaPeriodHolder);
                }
                updatedMediaPeriodInfo = followingMediaPeriodInfo;
            }
            next.info = updatedMediaPeriodInfo.copyWithRequestedContentPositionUs(mediaPeriodInfo.requestedContentPositionUs);
            if (!areDurationsCompatible(mediaPeriodInfo.durationUs, updatedMediaPeriodInfo.durationUs)) {
                next.updateClipping();
                return (removeAfter(next) || (next == this.reading && !next.info.isFollowedByTransitionToSameStream && ((maxRendererReadPositionUs > Long.MIN_VALUE ? 1 : (maxRendererReadPositionUs == Long.MIN_VALUE ? 0 : -1)) == 0 || (maxRendererReadPositionUs > ((updatedMediaPeriodInfo.durationUs > C.TIME_UNSET ? 1 : (updatedMediaPeriodInfo.durationUs == C.TIME_UNSET ? 0 : -1)) == 0 ? Long.MAX_VALUE : next.toRendererTime(updatedMediaPeriodInfo.durationUs)) ? 1 : (maxRendererReadPositionUs == ((updatedMediaPeriodInfo.durationUs > C.TIME_UNSET ? 1 : (updatedMediaPeriodInfo.durationUs == C.TIME_UNSET ? 0 : -1)) == 0 ? Long.MAX_VALUE : next.toRendererTime(updatedMediaPeriodInfo.durationUs)) ? 0 : -1)) >= 0))) ? false : true;
            }
            mediaPeriodHolder = next;
            next = next.getNext();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.google.android.exoplayer2.MediaPeriodInfo getUpdatedMediaPeriodInfo(com.google.android.exoplayer2.Timeline r19, com.google.android.exoplayer2.MediaPeriodInfo r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r2.id
            boolean r12 = r0.isLastInPeriod(r3)
            boolean r13 = r0.isLastInWindow(r1, r3)
            boolean r14 = r0.isLastInTimeline(r1, r3, r12)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r4 = r2.id
            java.lang.Object r4 = r4.periodUid
            com.google.android.exoplayer2.Timeline$Period r5 = r0.period
            r1.getPeriodByUid(r4, r5)
            boolean r1 = r3.isAd()
            r4 = -1
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r1 != 0) goto L37
            int r1 = r3.nextAdGroupIndex
            if (r1 != r4) goto L2e
            goto L37
        L2e:
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r7 = r3.nextAdGroupIndex
            long r7 = r1.getAdGroupTimeUs(r7)
            goto L38
        L37:
            r7 = r5
        L38:
            boolean r1 = r3.isAd()
            if (r1 == 0) goto L4a
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r5 = r3.adGroupIndex
            int r6 = r3.adIndexInAdGroup
            long r5 = r1.getAdDurationUs(r5, r6)
        L48:
            r9 = r5
            goto L5e
        L4a:
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 == 0) goto L57
            r5 = -9223372036854775808
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 != 0) goto L55
            goto L57
        L55:
            r9 = r7
            goto L5e
        L57:
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            long r5 = r1.getDurationUs()
            goto L48
        L5e:
            boolean r1 = r3.isAd()
            if (r1 == 0) goto L6e
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r4 = r3.adGroupIndex
            boolean r1 = r1.isServerSideInsertedAdGroup(r4)
        L6c:
            r11 = r1
            goto L80
        L6e:
            int r1 = r3.nextAdGroupIndex
            if (r1 == r4) goto L7e
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r4 = r3.nextAdGroupIndex
            boolean r1 = r1.isServerSideInsertedAdGroup(r4)
            if (r1 == 0) goto L7e
            r1 = 1
            goto L6c
        L7e:
            r1 = 0
            goto L6c
        L80:
            com.google.android.exoplayer2.MediaPeriodInfo r15 = new com.google.android.exoplayer2.MediaPeriodInfo
            long r4 = r2.startPositionUs
            long r1 = r2.requestedContentPositionUs
            r16 = r1
            r1 = r15
            r2 = r3
            r3 = r4
            r5 = r16
            r1.<init>(r2, r3, r5, r7, r9, r11, r12, r13, r14)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.MediaPeriodQueue.getUpdatedMediaPeriodInfo(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.MediaPeriodInfo):com.google.android.exoplayer2.MediaPeriodInfo");
    }

    public MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Timeline timeline, Object periodUid, long positionUs) {
        return resolveMediaPeriodIdForAds(timeline, periodUid, positionUs, resolvePeriodIndexToWindowSequenceNumber(timeline, periodUid), this.period);
    }

    private void notifyQueueUpdate() {
        if (this.analyticsCollector != null) {
            final ImmutableList.Builder builder = ImmutableList.builder();
            for (MediaPeriodHolder next = this.playing; next != null; next = next.getNext()) {
                builder.add((ImmutableList.Builder) next.info.id);
            }
            MediaPeriodHolder mediaPeriodHolder = this.reading;
            final MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodHolder == null ? null : mediaPeriodHolder.info.id;
            this.analyticsCollectorHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.MediaPeriodQueue$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m135xb2cc2342(builder, mediaPeriodId);
                }
            });
        }
    }

    /* renamed from: lambda$notifyQueueUpdate$0$com-google-android-exoplayer2-MediaPeriodQueue, reason: not valid java name */
    /* synthetic */ void m135xb2cc2342(ImmutableList.Builder builder, MediaSource.MediaPeriodId mediaPeriodId) {
        this.analyticsCollector.updateMediaPeriodQueueInfo(builder.build(), mediaPeriodId);
    }

    private static MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Timeline timeline, Object periodUid, long positionUs, long windowSequenceNumber, Timeline.Period period) {
        timeline.getPeriodByUid(periodUid, period);
        int adGroupIndexForPositionUs = period.getAdGroupIndexForPositionUs(positionUs);
        if (adGroupIndexForPositionUs == -1) {
            return new MediaSource.MediaPeriodId(periodUid, windowSequenceNumber, period.getAdGroupIndexAfterPositionUs(positionUs));
        }
        return new MediaSource.MediaPeriodId(periodUid, adGroupIndexForPositionUs, period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), windowSequenceNumber);
    }

    private long resolvePeriodIndexToWindowSequenceNumber(Timeline timeline, Object periodUid) {
        int indexOfPeriod;
        int i = timeline.getPeriodByUid(periodUid, this.period).windowIndex;
        Object obj = this.oldFrontPeriodUid;
        if (obj != null && (indexOfPeriod = timeline.getIndexOfPeriod(obj)) != -1 && timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i) {
            return this.oldFrontPeriodWindowSequenceNumber;
        }
        for (MediaPeriodHolder next = this.playing; next != null; next = next.getNext()) {
            if (next.uid.equals(periodUid)) {
                return next.info.id.windowSequenceNumber;
            }
        }
        for (MediaPeriodHolder next2 = this.playing; next2 != null; next2 = next2.getNext()) {
            int indexOfPeriod2 = timeline.getIndexOfPeriod(next2.uid);
            if (indexOfPeriod2 != -1 && timeline.getPeriod(indexOfPeriod2, this.period).windowIndex == i) {
                return next2.info.id.windowSequenceNumber;
            }
        }
        long j = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j;
        if (this.playing == null) {
            this.oldFrontPeriodUid = periodUid;
            this.oldFrontPeriodWindowSequenceNumber = j;
        }
        return j;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodInfo oldInfo, MediaPeriodInfo newInfo) {
        return oldInfo.startPositionUs == newInfo.startPositionUs && oldInfo.id.equals(newInfo.id);
    }

    private boolean updateForPlaybackModeChange(Timeline timeline) {
        MediaPeriodHolder next = this.playing;
        if (next == null) {
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(next.uid);
        while (true) {
            indexOfPeriod = timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (next.getNext() != null && !next.info.isLastInTimelinePeriod) {
                next = next.getNext();
            }
            MediaPeriodHolder next2 = next.getNext();
            if (indexOfPeriod == -1 || next2 == null || timeline.getIndexOfPeriod(next2.uid) != indexOfPeriod) {
                break;
            }
            next = next2;
        }
        boolean zRemoveAfter = removeAfter(next);
        next.info = getUpdatedMediaPeriodInfo(timeline, next.info);
        return !zRemoveAfter;
    }

    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.timeline, playbackInfo.periodId, playbackInfo.requestedContentPositionUs, playbackInfo.positionUs);
    }

    private MediaPeriodInfo getFollowingMediaPeriodInfo(Timeline timeline, MediaPeriodHolder mediaPeriodHolder, long rendererPositionUs) {
        long j;
        MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolder.info;
        long rendererOffset = (mediaPeriodHolder.getRendererOffset() + mediaPeriodInfo.durationUs) - rendererPositionUs;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            long j2 = 0;
            int nextPeriodIndex = timeline.getNextPeriodIndex(timeline.getIndexOfPeriod(mediaPeriodInfo.id.periodUid), this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int i = timeline.getPeriod(nextPeriodIndex, this.period, true).windowIndex;
            Object obj = this.period.uid;
            long j3 = mediaPeriodInfo.id.windowSequenceNumber;
            if (timeline.getWindow(i, this.window).firstPeriodIndex == nextPeriodIndex) {
                Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.window, this.period, i, C.TIME_UNSET, Math.max(0L, rendererOffset));
                if (periodPosition == null) {
                    return null;
                }
                obj = periodPosition.first;
                long jLongValue = ((Long) periodPosition.second).longValue();
                MediaPeriodHolder next = mediaPeriodHolder.getNext();
                if (next != null && next.uid.equals(obj)) {
                    j3 = next.info.id.windowSequenceNumber;
                } else {
                    j3 = this.nextWindowSequenceNumber;
                    this.nextWindowSequenceNumber = 1 + j3;
                }
                j = jLongValue;
                j2 = C.TIME_UNSET;
            } else {
                j = 0;
            }
            return getMediaPeriodInfo(timeline, resolveMediaPeriodIdForAds(timeline, obj, j, j3, this.period), j2, j);
        }
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodInfo.id;
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        if (mediaPeriodId.isAd()) {
            int i2 = mediaPeriodId.adGroupIndex;
            int adCountInAdGroup = this.period.getAdCountInAdGroup(i2);
            if (adCountInAdGroup == -1) {
                return null;
            }
            int nextAdIndexToPlay = this.period.getNextAdIndexToPlay(i2, mediaPeriodId.adIndexInAdGroup);
            if (nextAdIndexToPlay < adCountInAdGroup) {
                return getMediaPeriodInfoForAd(timeline, mediaPeriodId.periodUid, i2, nextAdIndexToPlay, mediaPeriodInfo.requestedContentPositionUs, mediaPeriodId.windowSequenceNumber);
            }
            long jLongValue2 = mediaPeriodInfo.requestedContentPositionUs;
            if (jLongValue2 == C.TIME_UNSET) {
                Timeline.Window window = this.window;
                Timeline.Period period = this.period;
                Pair<Object, Long> periodPosition2 = timeline.getPeriodPosition(window, period, period.windowIndex, C.TIME_UNSET, Math.max(0L, rendererOffset));
                if (periodPosition2 == null) {
                    return null;
                }
                jLongValue2 = ((Long) periodPosition2.second).longValue();
            }
            return getMediaPeriodInfoForContent(timeline, mediaPeriodId.periodUid, Math.max(getMinStartPositionAfterAdGroupUs(timeline, mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex), jLongValue2), mediaPeriodInfo.requestedContentPositionUs, mediaPeriodId.windowSequenceNumber);
        }
        int firstAdIndexToPlay = this.period.getFirstAdIndexToPlay(mediaPeriodId.nextAdGroupIndex);
        if (firstAdIndexToPlay == this.period.getAdCountInAdGroup(mediaPeriodId.nextAdGroupIndex)) {
            return getMediaPeriodInfoForContent(timeline, mediaPeriodId.periodUid, getMinStartPositionAfterAdGroupUs(timeline, mediaPeriodId.periodUid, mediaPeriodId.nextAdGroupIndex), mediaPeriodInfo.durationUs, mediaPeriodId.windowSequenceNumber);
        }
        return getMediaPeriodInfoForAd(timeline, mediaPeriodId.periodUid, mediaPeriodId.nextAdGroupIndex, firstAdIndexToPlay, mediaPeriodInfo.durationUs, mediaPeriodId.windowSequenceNumber);
    }

    private MediaPeriodInfo getMediaPeriodInfo(Timeline timeline, MediaSource.MediaPeriodId id, long requestedContentPositionUs, long startPositionUs) {
        timeline.getPeriodByUid(id.periodUid, this.period);
        if (id.isAd()) {
            return getMediaPeriodInfoForAd(timeline, id.periodUid, id.adGroupIndex, id.adIndexInAdGroup, requestedContentPositionUs, id.windowSequenceNumber);
        }
        return getMediaPeriodInfoForContent(timeline, id.periodUid, startPositionUs, requestedContentPositionUs, id.windowSequenceNumber);
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(Timeline timeline, Object periodUid, int adGroupIndex, int adIndexInAdGroup, long contentPositionUs, long windowSequenceNumber) {
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(periodUid, adGroupIndex, adIndexInAdGroup, windowSequenceNumber);
        long adDurationUs = timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        long adResumePositionUs = adIndexInAdGroup == this.period.getFirstAdIndexToPlay(adGroupIndex) ? this.period.getAdResumePositionUs() : 0L;
        return new MediaPeriodInfo(mediaPeriodId, (adDurationUs == C.TIME_UNSET || adResumePositionUs < adDurationUs) ? adResumePositionUs : Math.max(0L, adDurationUs - 1), contentPositionUs, C.TIME_UNSET, adDurationUs, this.period.isServerSideInsertedAdGroup(mediaPeriodId.adGroupIndex), false, false, false);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(Timeline timeline, Object periodUid, long startPositionUs, long requestedContentPositionUs, long windowSequenceNumber) {
        long jMax = startPositionUs;
        timeline.getPeriodByUid(periodUid, this.period);
        int adGroupIndexAfterPositionUs = this.period.getAdGroupIndexAfterPositionUs(jMax);
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(periodUid, windowSequenceNumber, adGroupIndexAfterPositionUs);
        boolean zIsLastInPeriod = isLastInPeriod(mediaPeriodId);
        boolean zIsLastInWindow = isLastInWindow(timeline, mediaPeriodId);
        boolean zIsLastInTimeline = isLastInTimeline(timeline, mediaPeriodId, zIsLastInPeriod);
        boolean z = adGroupIndexAfterPositionUs != -1 && this.period.isServerSideInsertedAdGroup(adGroupIndexAfterPositionUs);
        long adGroupTimeUs = adGroupIndexAfterPositionUs != -1 ? this.period.getAdGroupTimeUs(adGroupIndexAfterPositionUs) : -9223372036854775807L;
        long j = (adGroupTimeUs == C.TIME_UNSET || adGroupTimeUs == Long.MIN_VALUE) ? this.period.durationUs : adGroupTimeUs;
        if (j != C.TIME_UNSET && jMax >= j) {
            jMax = Math.max(0L, j - 1);
        }
        return new MediaPeriodInfo(mediaPeriodId, jMax, requestedContentPositionUs, adGroupTimeUs, j, z, zIsLastInPeriod, zIsLastInWindow, zIsLastInTimeline);
    }

    private boolean isLastInPeriod(MediaSource.MediaPeriodId id) {
        return !id.isAd() && id.nextAdGroupIndex == -1;
    }

    private boolean isLastInWindow(Timeline timeline, MediaSource.MediaPeriodId id) {
        if (isLastInPeriod(id)) {
            return timeline.getWindow(timeline.getPeriodByUid(id.periodUid, this.period).windowIndex, this.window).lastPeriodIndex == timeline.getIndexOfPeriod(id.periodUid);
        }
        return false;
    }

    private boolean isLastInTimeline(Timeline timeline, MediaSource.MediaPeriodId id, boolean isLastMediaPeriodInPeriod) {
        int indexOfPeriod = timeline.getIndexOfPeriod(id.periodUid);
        return !timeline.getWindow(timeline.getPeriod(indexOfPeriod, this.period).windowIndex, this.window).isDynamic && timeline.isLastPeriod(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) && isLastMediaPeriodInPeriod;
    }

    private long getMinStartPositionAfterAdGroupUs(Timeline timeline, Object periodUid, int adGroupIndex) {
        timeline.getPeriodByUid(periodUid, this.period);
        long adGroupTimeUs = this.period.getAdGroupTimeUs(adGroupIndex);
        if (adGroupTimeUs == Long.MIN_VALUE) {
            return this.period.durationUs;
        }
        return adGroupTimeUs + this.period.getContentResumeOffsetUs(adGroupIndex);
    }
}
