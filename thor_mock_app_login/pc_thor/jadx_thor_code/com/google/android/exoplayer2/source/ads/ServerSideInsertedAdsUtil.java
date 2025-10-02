package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class ServerSideInsertedAdsUtil {
    private ServerSideInsertedAdsUtil() {
    }

    public static AdPlaybackState addAdGroupToAdPlaybackState(AdPlaybackState adPlaybackState, long fromPositionUs, long toPositionUs, long contentResumeOffsetUs) {
        long mediaPeriodPositionUsForContent = getMediaPeriodPositionUsForContent(fromPositionUs, -1, adPlaybackState);
        int i = adPlaybackState.removedAdGroupCount;
        while (i < adPlaybackState.adGroupCount && adPlaybackState.getAdGroup(i).timeUs != Long.MIN_VALUE && adPlaybackState.getAdGroup(i).timeUs <= mediaPeriodPositionUsForContent) {
            i++;
        }
        long j = toPositionUs - fromPositionUs;
        AdPlaybackState adPlaybackStateWithContentResumeOffsetUs = adPlaybackState.withNewAdGroup(i, mediaPeriodPositionUsForContent).withIsServerSideInserted(i, true).withAdCount(i, 1).withAdDurationsUs(i, j).withContentResumeOffsetUs(i, contentResumeOffsetUs);
        long j2 = (-j) + contentResumeOffsetUs;
        for (int i2 = i + 1; i2 < adPlaybackStateWithContentResumeOffsetUs.adGroupCount; i2++) {
            long j3 = adPlaybackStateWithContentResumeOffsetUs.getAdGroup(i2).timeUs;
            if (j3 != Long.MIN_VALUE) {
                adPlaybackStateWithContentResumeOffsetUs = adPlaybackStateWithContentResumeOffsetUs.withAdGroupTimeUs(i2, j3 + j2);
            }
        }
        return adPlaybackStateWithContentResumeOffsetUs;
    }

    public static long getStreamDurationUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        Timeline.Period period = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period());
        return period.durationUs == C.TIME_UNSET ? C.TIME_UNSET : getStreamPositionUsForContent(period.durationUs, -1, adPlaybackState);
    }

    public static long getStreamPositionUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        Timeline.Period period = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period());
        if (!Util.areEqual(period.getAdsId(), adPlaybackState.adsId)) {
            return C.TIME_UNSET;
        }
        if (player.isPlayingAd()) {
            return getStreamPositionUsForAd(C.msToUs(player.getCurrentPosition()), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adPlaybackState);
        }
        return getStreamPositionUsForContent(C.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs(), -1, adPlaybackState);
    }

    public static long getStreamPositionUs(long positionUs, MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        if (mediaPeriodId.isAd()) {
            return getStreamPositionUsForAd(positionUs, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        }
        return getStreamPositionUsForContent(positionUs, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }

    public static long getMediaPeriodPositionUs(long positionUs, MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        if (mediaPeriodId.isAd()) {
            return getMediaPeriodPositionUsForAd(positionUs, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        }
        return getMediaPeriodPositionUsForContent(positionUs, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }

    public static long getStreamPositionUsForAd(long positionUs, int adGroupIndex, int adIndexInAdGroup, AdPlaybackState adPlaybackState) {
        int i;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(adGroupIndex);
        long j = positionUs + adGroup.timeUs;
        int i2 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i = 0;
            if (i2 >= adGroupIndex) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i2);
            while (i < getAdCountInGroup(adPlaybackState, i2)) {
                j += adGroup2.durationsUs[i];
                i++;
            }
            j -= adGroup2.contentResumeOffsetUs;
            i2++;
        }
        if (adIndexInAdGroup < getAdCountInGroup(adPlaybackState, adGroupIndex)) {
            while (i < adIndexInAdGroup) {
                j += adGroup.durationsUs[i];
                i++;
            }
        }
        return j;
    }

    public static long getMediaPeriodPositionUsForAd(long positionUs, int adGroupIndex, int adIndexInAdGroup, AdPlaybackState adPlaybackState) {
        int i;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(adGroupIndex);
        long j = positionUs - adGroup.timeUs;
        int i2 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i = 0;
            if (i2 >= adGroupIndex) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i2);
            while (i < getAdCountInGroup(adPlaybackState, i2)) {
                j -= adGroup2.durationsUs[i];
                i++;
            }
            j += adGroup2.contentResumeOffsetUs;
            i2++;
        }
        if (adIndexInAdGroup < getAdCountInGroup(adPlaybackState, adGroupIndex)) {
            while (i < adIndexInAdGroup) {
                j -= adGroup.durationsUs[i];
                i++;
            }
        }
        return j;
    }

    public static long getStreamPositionUsForContent(long positionUs, int nextAdGroupIndex, AdPlaybackState adPlaybackState) {
        if (nextAdGroupIndex == -1) {
            nextAdGroupIndex = adPlaybackState.adGroupCount;
        }
        long j = 0;
        for (int i = adPlaybackState.removedAdGroupCount; i < nextAdGroupIndex; i++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
            if (adGroup.timeUs == Long.MIN_VALUE || adGroup.timeUs > positionUs) {
                break;
            }
            long j2 = adGroup.timeUs + j;
            for (int i2 = 0; i2 < getAdCountInGroup(adPlaybackState, i); i2++) {
                j += adGroup.durationsUs[i2];
            }
            j -= adGroup.contentResumeOffsetUs;
            if (adGroup.timeUs + adGroup.contentResumeOffsetUs > positionUs) {
                return Math.max(j2, positionUs + j);
            }
        }
        return positionUs + j;
    }

    public static long getMediaPeriodPositionUsForContent(long positionUs, int nextAdGroupIndex, AdPlaybackState adPlaybackState) {
        if (nextAdGroupIndex == -1) {
            nextAdGroupIndex = adPlaybackState.adGroupCount;
        }
        long j = 0;
        for (int i = adPlaybackState.removedAdGroupCount; i < nextAdGroupIndex; i++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
            if (adGroup.timeUs == Long.MIN_VALUE || adGroup.timeUs > positionUs - j) {
                break;
            }
            for (int i2 = 0; i2 < getAdCountInGroup(adPlaybackState, i); i2++) {
                j += adGroup.durationsUs[i2];
            }
            j -= adGroup.contentResumeOffsetUs;
            long j2 = positionUs - j;
            if (adGroup.timeUs + adGroup.contentResumeOffsetUs > j2) {
                return Math.max(adGroup.timeUs, j2);
            }
        }
        return positionUs - j;
    }

    public static int getAdCountInGroup(AdPlaybackState adPlaybackState, int adGroupIndex) {
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(adGroupIndex);
        if (adGroup.count == -1) {
            return 0;
        }
        return adGroup.count;
    }
}
