package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class AdPlaybackState implements Bundleable {
    public static final int AD_STATE_AVAILABLE = 1;
    public static final int AD_STATE_ERROR = 4;
    public static final int AD_STATE_PLAYED = 3;
    public static final int AD_STATE_SKIPPED = 2;
    public static final int AD_STATE_UNAVAILABLE = 0;
    private static final int FIELD_AD_GROUPS = 1;
    private static final int FIELD_AD_RESUME_POSITION_US = 2;
    private static final int FIELD_CONTENT_DURATION_US = 3;
    private static final int FIELD_REMOVED_AD_GROUP_COUNT = 4;
    public final int adGroupCount;
    private final AdGroup[] adGroups;
    public final long adResumePositionUs;
    public final Object adsId;
    public final long contentDurationUs;
    public final int removedAdGroupCount;
    public static final AdPlaybackState NONE = new AdPlaybackState(null, new AdGroup[0], 0, C.TIME_UNSET, 0);
    private static final AdGroup REMOVED_AD_GROUP = new AdGroup(0).withAdCount(0);
    public static final Bundleable.Creator<AdPlaybackState> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.source.ads.AdPlaybackState$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return AdPlaybackState.fromBundle(bundle);
        }
    };

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AdState {
    }

    public static final class AdGroup implements Bundleable {
        public static final Bundleable.Creator<AdGroup> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.source.ads.AdPlaybackState$AdGroup$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return AdPlaybackState.AdGroup.fromBundle(bundle);
            }
        };
        private static final int FIELD_CONTENT_RESUME_OFFSET_US = 5;
        private static final int FIELD_COUNT = 1;
        private static final int FIELD_DURATIONS_US = 4;
        private static final int FIELD_IS_SERVER_SIDE_INSERTED = 6;
        private static final int FIELD_STATES = 3;
        private static final int FIELD_TIME_US = 0;
        private static final int FIELD_URIS = 2;
        public final long contentResumeOffsetUs;
        public final int count;
        public final long[] durationsUs;
        public final boolean isServerSideInserted;
        public final int[] states;
        public final long timeUs;
        public final Uri[] uris;

        public AdGroup(long timeUs) {
            this(timeUs, -1, new int[0], new Uri[0], new long[0], 0L, false);
        }

        private AdGroup(long timeUs, int count, int[] states, Uri[] uris, long[] durationsUs, long contentResumeOffsetUs, boolean isServerSideInserted) {
            Assertions.checkArgument(states.length == uris.length);
            this.timeUs = timeUs;
            this.count = count;
            this.states = states;
            this.uris = uris;
            this.durationsUs = durationsUs;
            this.contentResumeOffsetUs = contentResumeOffsetUs;
            this.isServerSideInserted = isServerSideInserted;
        }

        public int getFirstAdIndexToPlay() {
            return getNextAdIndexToPlay(-1);
        }

        public int getNextAdIndexToPlay(int lastPlayedAdIndex) {
            int i;
            int i2 = lastPlayedAdIndex + 1;
            while (true) {
                int[] iArr = this.states;
                if (i2 >= iArr.length || this.isServerSideInserted || (i = iArr[i2]) == 0 || i == 1) {
                    break;
                }
                i2++;
            }
            return i2;
        }

        public boolean shouldPlayAdGroup() {
            return this.count == -1 || getFirstAdIndexToPlay() < this.count;
        }

        public boolean hasUnplayedAds() {
            if (this.count == -1) {
                return true;
            }
            for (int i = 0; i < this.count; i++) {
                int i2 = this.states[i];
                if (i2 == 0 || i2 == 1) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AdGroup adGroup = (AdGroup) o;
            return this.timeUs == adGroup.timeUs && this.count == adGroup.count && Arrays.equals(this.uris, adGroup.uris) && Arrays.equals(this.states, adGroup.states) && Arrays.equals(this.durationsUs, adGroup.durationsUs) && this.contentResumeOffsetUs == adGroup.contentResumeOffsetUs && this.isServerSideInserted == adGroup.isServerSideInserted;
        }

        public int hashCode() {
            int i = this.count * 31;
            long j = this.timeUs;
            int iHashCode = (((((((i + ((int) (j ^ (j >>> 32)))) * 31) + Arrays.hashCode(this.uris)) * 31) + Arrays.hashCode(this.states)) * 31) + Arrays.hashCode(this.durationsUs)) * 31;
            long j2 = this.contentResumeOffsetUs;
            return ((iHashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (this.isServerSideInserted ? 1 : 0);
        }

        public AdGroup withTimeUs(long timeUs) {
            return new AdGroup(timeUs, this.count, this.states, this.uris, this.durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        public AdGroup withAdCount(int count) {
            int[] iArrCopyStatesWithSpaceForAdCount = copyStatesWithSpaceForAdCount(this.states, count);
            long[] jArrCopyDurationsUsWithSpaceForAdCount = copyDurationsUsWithSpaceForAdCount(this.durationsUs, count);
            return new AdGroup(this.timeUs, count, iArrCopyStatesWithSpaceForAdCount, (Uri[]) Arrays.copyOf(this.uris, count), jArrCopyDurationsUsWithSpaceForAdCount, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        public AdGroup withAdUri(Uri uri, int index) {
            int[] iArrCopyStatesWithSpaceForAdCount = copyStatesWithSpaceForAdCount(this.states, index + 1);
            long[] jArrCopyDurationsUsWithSpaceForAdCount = this.durationsUs;
            if (jArrCopyDurationsUsWithSpaceForAdCount.length != iArrCopyStatesWithSpaceForAdCount.length) {
                jArrCopyDurationsUsWithSpaceForAdCount = copyDurationsUsWithSpaceForAdCount(jArrCopyDurationsUsWithSpaceForAdCount, iArrCopyStatesWithSpaceForAdCount.length);
            }
            long[] jArr = jArrCopyDurationsUsWithSpaceForAdCount;
            Uri[] uriArr = (Uri[]) Arrays.copyOf(this.uris, iArrCopyStatesWithSpaceForAdCount.length);
            uriArr[index] = uri;
            iArrCopyStatesWithSpaceForAdCount[index] = 1;
            return new AdGroup(this.timeUs, this.count, iArrCopyStatesWithSpaceForAdCount, uriArr, jArr, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        public AdGroup withAdState(int state, int index) {
            int i = this.count;
            Assertions.checkArgument(i == -1 || index < i);
            int[] iArrCopyStatesWithSpaceForAdCount = copyStatesWithSpaceForAdCount(this.states, index + 1);
            int i2 = iArrCopyStatesWithSpaceForAdCount[index];
            Assertions.checkArgument(i2 == 0 || i2 == 1 || i2 == state);
            long[] jArrCopyDurationsUsWithSpaceForAdCount = this.durationsUs;
            if (jArrCopyDurationsUsWithSpaceForAdCount.length != iArrCopyStatesWithSpaceForAdCount.length) {
                jArrCopyDurationsUsWithSpaceForAdCount = copyDurationsUsWithSpaceForAdCount(jArrCopyDurationsUsWithSpaceForAdCount, iArrCopyStatesWithSpaceForAdCount.length);
            }
            long[] jArr = jArrCopyDurationsUsWithSpaceForAdCount;
            Uri[] uriArr = this.uris;
            if (uriArr.length != iArrCopyStatesWithSpaceForAdCount.length) {
                uriArr = (Uri[]) Arrays.copyOf(uriArr, iArrCopyStatesWithSpaceForAdCount.length);
            }
            iArrCopyStatesWithSpaceForAdCount[index] = state;
            return new AdGroup(this.timeUs, this.count, iArrCopyStatesWithSpaceForAdCount, uriArr, jArr, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        public AdGroup withAdDurationsUs(long[] durationsUs) {
            int length = durationsUs.length;
            Uri[] uriArr = this.uris;
            if (length < uriArr.length) {
                durationsUs = copyDurationsUsWithSpaceForAdCount(durationsUs, uriArr.length);
            } else if (this.count != -1 && durationsUs.length > uriArr.length) {
                durationsUs = Arrays.copyOf(durationsUs, uriArr.length);
            }
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        public AdGroup withContentResumeOffsetUs(long contentResumeOffsetUs) {
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, this.durationsUs, contentResumeOffsetUs, this.isServerSideInserted);
        }

        public AdGroup withIsServerSideInserted(boolean isServerSideInserted) {
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, this.durationsUs, this.contentResumeOffsetUs, isServerSideInserted);
        }

        public AdGroup withAllAdsSkipped() {
            if (this.count == -1) {
                return new AdGroup(this.timeUs, 0, new int[0], new Uri[0], new long[0], this.contentResumeOffsetUs, this.isServerSideInserted);
            }
            int[] iArr = this.states;
            int length = iArr.length;
            int[] iArrCopyOf = Arrays.copyOf(iArr, length);
            for (int i = 0; i < length; i++) {
                int i2 = iArrCopyOf[i];
                if (i2 == 1 || i2 == 0) {
                    iArrCopyOf[i] = 2;
                }
            }
            return new AdGroup(this.timeUs, length, iArrCopyOf, this.uris, this.durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        private static int[] copyStatesWithSpaceForAdCount(int[] states, int count) {
            int length = states.length;
            int iMax = Math.max(count, length);
            int[] iArrCopyOf = Arrays.copyOf(states, iMax);
            Arrays.fill(iArrCopyOf, length, iMax, 0);
            return iArrCopyOf;
        }

        private static long[] copyDurationsUsWithSpaceForAdCount(long[] durationsUs, int count) {
            int length = durationsUs.length;
            int iMax = Math.max(count, length);
            long[] jArrCopyOf = Arrays.copyOf(durationsUs, iMax);
            Arrays.fill(jArrCopyOf, length, iMax, C.TIME_UNSET);
            return jArrCopyOf;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(keyForField(0), this.timeUs);
            bundle.putInt(keyForField(1), this.count);
            bundle.putParcelableArrayList(keyForField(2), new ArrayList<>(Arrays.asList(this.uris)));
            bundle.putIntArray(keyForField(3), this.states);
            bundle.putLongArray(keyForField(4), this.durationsUs);
            bundle.putLong(keyForField(5), this.contentResumeOffsetUs);
            bundle.putBoolean(keyForField(6), this.isServerSideInserted);
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static AdGroup fromBundle(Bundle bundle) {
            long j = bundle.getLong(keyForField(0));
            int i = bundle.getInt(keyForField(1), -1);
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(keyForField(2));
            int[] intArray = bundle.getIntArray(keyForField(3));
            long[] longArray = bundle.getLongArray(keyForField(4));
            long j2 = bundle.getLong(keyForField(5));
            boolean z = bundle.getBoolean(keyForField(6));
            if (intArray == null) {
                intArray = new int[0];
            }
            return new AdGroup(j, i, intArray, parcelableArrayList == null ? new Uri[0] : (Uri[]) parcelableArrayList.toArray(new Uri[0]), longArray == null ? new long[0] : longArray, j2, z);
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }

    public AdPlaybackState(Object adsId, long... adGroupTimesUs) {
        this(adsId, createEmptyAdGroups(adGroupTimesUs), 0L, C.TIME_UNSET, 0);
    }

    private AdPlaybackState(Object adsId, AdGroup[] adGroups, long adResumePositionUs, long contentDurationUs, int removedAdGroupCount) {
        this.adsId = adsId;
        this.adResumePositionUs = adResumePositionUs;
        this.contentDurationUs = contentDurationUs;
        this.adGroupCount = adGroups.length + removedAdGroupCount;
        this.adGroups = adGroups;
        this.removedAdGroupCount = removedAdGroupCount;
    }

    public AdGroup getAdGroup(int adGroupIndex) {
        int i = this.removedAdGroupCount;
        if (adGroupIndex < i) {
            return REMOVED_AD_GROUP;
        }
        return this.adGroups[adGroupIndex - i];
    }

    public int getAdGroupIndexForPositionUs(long positionUs, long periodDurationUs) {
        int i = this.adGroupCount - 1;
        while (i >= 0 && isPositionBeforeAdGroup(positionUs, periodDurationUs, i)) {
            i--;
        }
        if (i < 0 || !getAdGroup(i).hasUnplayedAds()) {
            return -1;
        }
        return i;
    }

    public int getAdGroupIndexAfterPositionUs(long positionUs, long periodDurationUs) {
        if (positionUs == Long.MIN_VALUE) {
            return -1;
        }
        if (periodDurationUs != C.TIME_UNSET && positionUs >= periodDurationUs) {
            return -1;
        }
        int i = this.removedAdGroupCount;
        while (i < this.adGroupCount && ((getAdGroup(i).timeUs != Long.MIN_VALUE && getAdGroup(i).timeUs <= positionUs) || !getAdGroup(i).shouldPlayAdGroup())) {
            i++;
        }
        if (i < this.adGroupCount) {
            return i;
        }
        return -1;
    }

    public boolean isAdInErrorState(int adGroupIndex, int adIndexInAdGroup) {
        if (adGroupIndex >= this.adGroupCount) {
            return false;
        }
        AdGroup adGroup = getAdGroup(adGroupIndex);
        return adGroup.count != -1 && adIndexInAdGroup < adGroup.count && adGroup.states[adIndexInAdGroup] == 4;
    }

    public AdPlaybackState withAdGroupTimeUs(int adGroupIndex, long adGroupTimeUs) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = this.adGroups[i].withTimeUs(adGroupTimeUs);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withNewAdGroup(int adGroupIndex, long adGroupTimeUs) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup adGroup = new AdGroup(adGroupTimeUs);
        AdGroup[] adGroupArr = (AdGroup[]) Util.nullSafeArrayAppend(this.adGroups, adGroup);
        System.arraycopy(adGroupArr, i, adGroupArr, i + 1, this.adGroups.length - i);
        adGroupArr[i] = adGroup;
        return new AdPlaybackState(this.adsId, adGroupArr, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withAdCount(int adGroupIndex, int adCount) {
        Assertions.checkArgument(adCount > 0);
        int i = adGroupIndex - this.removedAdGroupCount;
        if (this.adGroups[i].count == adCount) {
            return this;
        }
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = this.adGroups[i].withAdCount(adCount);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withAdUri(int adGroupIndex, int adIndexInAdGroup, Uri uri) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withAdUri(uri, adIndexInAdGroup);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withPlayedAd(int adGroupIndex, int adIndexInAdGroup) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withAdState(3, adIndexInAdGroup);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withSkippedAd(int adGroupIndex, int adIndexInAdGroup) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withAdState(2, adIndexInAdGroup);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withAdLoadError(int adGroupIndex, int adIndexInAdGroup) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withAdState(4, adIndexInAdGroup);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withSkippedAdGroup(int adGroupIndex) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withAllAdsSkipped();
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withAdDurationsUs(long[][] adDurationUs) {
        Assertions.checkState(this.removedAdGroupCount == 0);
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        for (int i = 0; i < this.adGroupCount; i++) {
            adGroupArr2[i] = adGroupArr2[i].withAdDurationsUs(adDurationUs[i]);
        }
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withAdDurationsUs(int adGroupIndex, long... adDurationsUs) {
        int i = adGroupIndex - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withAdDurationsUs(adDurationsUs);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withAdResumePositionUs(long adResumePositionUs) {
        return this.adResumePositionUs == adResumePositionUs ? this : new AdPlaybackState(this.adsId, this.adGroups, adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withContentDurationUs(long contentDurationUs) {
        return this.contentDurationUs == contentDurationUs ? this : new AdPlaybackState(this.adsId, this.adGroups, this.adResumePositionUs, contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withRemovedAdGroupCount(int removedAdGroupCount) {
        int i = this.removedAdGroupCount;
        if (i == removedAdGroupCount) {
            return this;
        }
        Assertions.checkArgument(removedAdGroupCount > i);
        int i2 = this.adGroupCount - removedAdGroupCount;
        AdGroup[] adGroupArr = new AdGroup[i2];
        System.arraycopy(this.adGroups, removedAdGroupCount - this.removedAdGroupCount, adGroupArr, 0, i2);
        return new AdPlaybackState(this.adsId, adGroupArr, this.adResumePositionUs, this.contentDurationUs, removedAdGroupCount);
    }

    public AdPlaybackState withContentResumeOffsetUs(int adGroupIndex, long contentResumeOffsetUs) {
        int i = adGroupIndex - this.removedAdGroupCount;
        if (this.adGroups[i].contentResumeOffsetUs == contentResumeOffsetUs) {
            return this;
        }
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withContentResumeOffsetUs(contentResumeOffsetUs);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public AdPlaybackState withIsServerSideInserted(int adGroupIndex, boolean isServerSideInserted) {
        int i = adGroupIndex - this.removedAdGroupCount;
        if (this.adGroups[i].isServerSideInserted == isServerSideInserted) {
            return this;
        }
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i] = adGroupArr2[i].withIsServerSideInserted(isServerSideInserted);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdPlaybackState adPlaybackState = (AdPlaybackState) o;
        return Util.areEqual(this.adsId, adPlaybackState.adsId) && this.adGroupCount == adPlaybackState.adGroupCount && this.adResumePositionUs == adPlaybackState.adResumePositionUs && this.contentDurationUs == adPlaybackState.contentDurationUs && this.removedAdGroupCount == adPlaybackState.removedAdGroupCount && Arrays.equals(this.adGroups, adPlaybackState.adGroups);
    }

    public int hashCode() {
        int i = this.adGroupCount * 31;
        Object obj = this.adsId;
        return ((((((((i + (obj == null ? 0 : obj.hashCode())) * 31) + ((int) this.adResumePositionUs)) * 31) + ((int) this.contentDurationUs)) * 31) + this.removedAdGroupCount) * 31) + Arrays.hashCode(this.adGroups);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AdPlaybackState(adsId=");
        sb.append(this.adsId);
        sb.append(", adResumePositionUs=");
        sb.append(this.adResumePositionUs);
        sb.append(", adGroups=[");
        for (int i = 0; i < this.adGroups.length; i++) {
            sb.append("adGroup(timeUs=");
            sb.append(this.adGroups[i].timeUs);
            sb.append(", ads=[");
            for (int i2 = 0; i2 < this.adGroups[i].states.length; i2++) {
                sb.append("ad(state=");
                int i3 = this.adGroups[i].states[i2];
                if (i3 == 0) {
                    sb.append('_');
                } else if (i3 == 1) {
                    sb.append('R');
                } else if (i3 == 2) {
                    sb.append('S');
                } else if (i3 == 3) {
                    sb.append('P');
                } else if (i3 == 4) {
                    sb.append('!');
                } else {
                    sb.append('?');
                }
                sb.append(", durationUs=");
                sb.append(this.adGroups[i].durationsUs[i2]);
                sb.append(')');
                if (i2 < this.adGroups[i].states.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("])");
            if (i < this.adGroups.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("])");
        return sb.toString();
    }

    private boolean isPositionBeforeAdGroup(long positionUs, long periodDurationUs, int adGroupIndex) {
        if (positionUs == Long.MIN_VALUE) {
            return false;
        }
        long j = getAdGroup(adGroupIndex).timeUs;
        return j == Long.MIN_VALUE ? periodDurationUs == C.TIME_UNSET || positionUs < periodDurationUs : positionUs < j;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (AdGroup adGroup : this.adGroups) {
            arrayList.add(adGroup.toBundle());
        }
        bundle.putParcelableArrayList(keyForField(1), arrayList);
        bundle.putLong(keyForField(2), this.adResumePositionUs);
        bundle.putLong(keyForField(3), this.contentDurationUs);
        bundle.putInt(keyForField(4), this.removedAdGroupCount);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AdPlaybackState fromBundle(Bundle bundle) {
        AdGroup[] adGroupArr;
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(keyForField(1));
        if (parcelableArrayList == null) {
            adGroupArr = new AdGroup[0];
        } else {
            AdGroup[] adGroupArr2 = new AdGroup[parcelableArrayList.size()];
            for (int i = 0; i < parcelableArrayList.size(); i++) {
                adGroupArr2[i] = (AdGroup) AdGroup.CREATOR.fromBundle((Bundle) parcelableArrayList.get(i));
            }
            adGroupArr = adGroupArr2;
        }
        return new AdPlaybackState(null, adGroupArr, bundle.getLong(keyForField(2), 0L), bundle.getLong(keyForField(3), C.TIME_UNSET), bundle.getInt(keyForField(4)));
    }

    private static String keyForField(int field) {
        return Integer.toString(field, 36);
    }

    private static AdGroup[] createEmptyAdGroups(long[] adGroupTimesUs) {
        int length = adGroupTimesUs.length;
        AdGroup[] adGroupArr = new AdGroup[length];
        for (int i = 0; i < length; i++) {
            adGroupArr[i] = new AdGroup(adGroupTimesUs[i]);
        }
        return adGroupArr;
    }
}
