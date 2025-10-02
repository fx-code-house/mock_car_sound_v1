package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Pair;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Timeline implements Bundleable {
    private static final int FIELD_PERIODS = 1;
    private static final int FIELD_SHUFFLED_WINDOW_INDICES = 2;
    private static final int FIELD_WINDOWS = 0;
    public static final Timeline EMPTY = new Timeline() { // from class: com.google.android.exoplayer2.Timeline.1
        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object uid) {
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Window getWindow(int windowIndex, Window window, long defaultPositionProjectionUs) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Period getPeriod(int periodIndex, Period period, boolean setIds) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int periodIndex) {
            throw new IndexOutOfBoundsException();
        }
    };
    public static final Bundleable.Creator<Timeline> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.Timeline$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return Timeline.fromBundle(bundle);
        }
    };

    public abstract int getIndexOfPeriod(Object uid);

    public abstract Period getPeriod(int periodIndex, Period period, boolean setIds);

    public abstract int getPeriodCount();

    public abstract Object getUidOfPeriod(int periodIndex);

    public abstract Window getWindow(int windowIndex, Window window, long defaultPositionProjectionUs);

    public abstract int getWindowCount();

    public static final class Window implements Bundleable {
        private static final int FIELD_DEFAULT_POSITION_US = 9;
        private static final int FIELD_DURATION_US = 10;
        private static final int FIELD_ELAPSED_REALTIME_EPOCH_OFFSET_MS = 4;
        private static final int FIELD_FIRST_PERIOD_INDEX = 11;
        private static final int FIELD_IS_DYNAMIC = 6;
        private static final int FIELD_IS_PLACEHOLDER = 8;
        private static final int FIELD_IS_SEEKABLE = 5;
        private static final int FIELD_LAST_PERIOD_INDEX = 12;
        private static final int FIELD_LIVE_CONFIGURATION = 7;
        private static final int FIELD_MEDIA_ITEM = 1;
        private static final int FIELD_POSITION_IN_FIRST_PERIOD_US = 13;
        private static final int FIELD_PRESENTATION_START_TIME_MS = 2;
        private static final int FIELD_WINDOW_START_TIME_MS = 3;
        public long defaultPositionUs;
        public long durationUs;
        public long elapsedRealtimeEpochOffsetMs;
        public int firstPeriodIndex;
        public boolean isDynamic;

        @Deprecated
        public boolean isLive;
        public boolean isPlaceholder;
        public boolean isSeekable;
        public int lastPeriodIndex;
        public MediaItem.LiveConfiguration liveConfiguration;
        public Object manifest;
        public long positionInFirstPeriodUs;
        public long presentationStartTimeMs;

        @Deprecated
        public Object tag;
        public long windowStartTimeMs;
        public static final Object SINGLE_WINDOW_UID = new Object();
        private static final Object FAKE_WINDOW_UID = new Object();
        private static final MediaItem EMPTY_MEDIA_ITEM = new MediaItem.Builder().setMediaId("com.google.android.exoplayer2.Timeline").setUri(Uri.EMPTY).build();
        public static final Bundleable.Creator<Window> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.Timeline$Window$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return Timeline.Window.fromBundle(bundle);
            }
        };
        public Object uid = SINGLE_WINDOW_UID;
        public MediaItem mediaItem = EMPTY_MEDIA_ITEM;

        public Window set(Object uid, MediaItem mediaItem, Object manifest, long presentationStartTimeMs, long windowStartTimeMs, long elapsedRealtimeEpochOffsetMs, boolean isSeekable, boolean isDynamic, MediaItem.LiveConfiguration liveConfiguration, long defaultPositionUs, long durationUs, int firstPeriodIndex, int lastPeriodIndex, long positionInFirstPeriodUs) {
            this.uid = uid;
            this.mediaItem = mediaItem != null ? mediaItem : EMPTY_MEDIA_ITEM;
            this.tag = (mediaItem == null || mediaItem.playbackProperties == null) ? null : mediaItem.playbackProperties.tag;
            this.manifest = manifest;
            this.presentationStartTimeMs = presentationStartTimeMs;
            this.windowStartTimeMs = windowStartTimeMs;
            this.elapsedRealtimeEpochOffsetMs = elapsedRealtimeEpochOffsetMs;
            this.isSeekable = isSeekable;
            this.isDynamic = isDynamic;
            this.isLive = liveConfiguration != null;
            this.liveConfiguration = liveConfiguration;
            this.defaultPositionUs = defaultPositionUs;
            this.durationUs = durationUs;
            this.firstPeriodIndex = firstPeriodIndex;
            this.lastPeriodIndex = lastPeriodIndex;
            this.positionInFirstPeriodUs = positionInFirstPeriodUs;
            this.isPlaceholder = false;
            return this;
        }

        public long getDefaultPositionMs() {
            return C.usToMs(this.defaultPositionUs);
        }

        public long getDefaultPositionUs() {
            return this.defaultPositionUs;
        }

        public long getDurationMs() {
            return C.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInFirstPeriodMs() {
            return C.usToMs(this.positionInFirstPeriodUs);
        }

        public long getPositionInFirstPeriodUs() {
            return this.positionInFirstPeriodUs;
        }

        public long getCurrentUnixTimeMs() {
            return Util.getNowUnixTimeMs(this.elapsedRealtimeEpochOffsetMs);
        }

        public boolean isLive() {
            Assertions.checkState(this.isLive == (this.liveConfiguration != null));
            return this.liveConfiguration != null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Window window = (Window) obj;
            return Util.areEqual(this.uid, window.uid) && Util.areEqual(this.mediaItem, window.mediaItem) && Util.areEqual(this.manifest, window.manifest) && Util.areEqual(this.liveConfiguration, window.liveConfiguration) && this.presentationStartTimeMs == window.presentationStartTimeMs && this.windowStartTimeMs == window.windowStartTimeMs && this.elapsedRealtimeEpochOffsetMs == window.elapsedRealtimeEpochOffsetMs && this.isSeekable == window.isSeekable && this.isDynamic == window.isDynamic && this.isPlaceholder == window.isPlaceholder && this.defaultPositionUs == window.defaultPositionUs && this.durationUs == window.durationUs && this.firstPeriodIndex == window.firstPeriodIndex && this.lastPeriodIndex == window.lastPeriodIndex && this.positionInFirstPeriodUs == window.positionInFirstPeriodUs;
        }

        public int hashCode() {
            int iHashCode = (((217 + this.uid.hashCode()) * 31) + this.mediaItem.hashCode()) * 31;
            Object obj = this.manifest;
            int iHashCode2 = (iHashCode + (obj == null ? 0 : obj.hashCode())) * 31;
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            int iHashCode3 = (iHashCode2 + (liveConfiguration != null ? liveConfiguration.hashCode() : 0)) * 31;
            long j = this.presentationStartTimeMs;
            int i = (iHashCode3 + ((int) (j ^ (j >>> 32)))) * 31;
            long j2 = this.windowStartTimeMs;
            int i2 = (i + ((int) (j2 ^ (j2 >>> 32)))) * 31;
            long j3 = this.elapsedRealtimeEpochOffsetMs;
            int i3 = (((((((i2 + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.isSeekable ? 1 : 0)) * 31) + (this.isDynamic ? 1 : 0)) * 31) + (this.isPlaceholder ? 1 : 0)) * 31;
            long j4 = this.defaultPositionUs;
            int i4 = (i3 + ((int) (j4 ^ (j4 >>> 32)))) * 31;
            long j5 = this.durationUs;
            int i5 = (((((i4 + ((int) (j5 ^ (j5 >>> 32)))) * 31) + this.firstPeriodIndex) * 31) + this.lastPeriodIndex) * 31;
            long j6 = this.positionInFirstPeriodUs;
            return i5 + ((int) (j6 ^ (j6 >>> 32)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bundle toBundle(boolean excludeMediaItem) {
            Bundle bundle = new Bundle();
            bundle.putBundle(keyForField(1), (excludeMediaItem ? MediaItem.EMPTY : this.mediaItem).toBundle());
            bundle.putLong(keyForField(2), this.presentationStartTimeMs);
            bundle.putLong(keyForField(3), this.windowStartTimeMs);
            bundle.putLong(keyForField(4), this.elapsedRealtimeEpochOffsetMs);
            bundle.putBoolean(keyForField(5), this.isSeekable);
            bundle.putBoolean(keyForField(6), this.isDynamic);
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            if (liveConfiguration != null) {
                bundle.putBundle(keyForField(7), liveConfiguration.toBundle());
            }
            bundle.putBoolean(keyForField(8), this.isPlaceholder);
            bundle.putLong(keyForField(9), this.defaultPositionUs);
            bundle.putLong(keyForField(10), this.durationUs);
            bundle.putInt(keyForField(11), this.firstPeriodIndex);
            bundle.putInt(keyForField(12), this.lastPeriodIndex);
            bundle.putLong(keyForField(13), this.positionInFirstPeriodUs);
            return bundle;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            return toBundle(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Window fromBundle(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(keyForField(1));
            MediaItem mediaItem = bundle2 != null ? (MediaItem) MediaItem.CREATOR.fromBundle(bundle2) : null;
            long j = bundle.getLong(keyForField(2), C.TIME_UNSET);
            long j2 = bundle.getLong(keyForField(3), C.TIME_UNSET);
            long j3 = bundle.getLong(keyForField(4), C.TIME_UNSET);
            boolean z = bundle.getBoolean(keyForField(5), false);
            boolean z2 = bundle.getBoolean(keyForField(6), false);
            Bundle bundle3 = bundle.getBundle(keyForField(7));
            MediaItem.LiveConfiguration liveConfiguration = bundle3 != null ? (MediaItem.LiveConfiguration) MediaItem.LiveConfiguration.CREATOR.fromBundle(bundle3) : null;
            boolean z3 = bundle.getBoolean(keyForField(8), false);
            long j4 = bundle.getLong(keyForField(9), 0L);
            long j5 = bundle.getLong(keyForField(10), C.TIME_UNSET);
            int i = bundle.getInt(keyForField(11), 0);
            int i2 = bundle.getInt(keyForField(12), 0);
            long j6 = bundle.getLong(keyForField(13), 0L);
            Window window = new Window();
            window.set(FAKE_WINDOW_UID, mediaItem, null, j, j2, j3, z, z2, liveConfiguration, j4, j5, i, i2, j6);
            window.isPlaceholder = z3;
            return window;
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }

    public static final class Period implements Bundleable {
        public static final Bundleable.Creator<Period> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.Timeline$Period$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return Timeline.Period.fromBundle(bundle);
            }
        };
        private static final int FIELD_AD_PLAYBACK_STATE = 4;
        private static final int FIELD_DURATION_US = 1;
        private static final int FIELD_PLACEHOLDER = 3;
        private static final int FIELD_POSITION_IN_WINDOW_US = 2;
        private static final int FIELD_WINDOW_INDEX = 0;
        private AdPlaybackState adPlaybackState = AdPlaybackState.NONE;
        public long durationUs;
        public Object id;
        public boolean isPlaceholder;
        public long positionInWindowUs;
        public Object uid;
        public int windowIndex;

        public Period set(Object id, Object uid, int windowIndex, long durationUs, long positionInWindowUs) {
            return set(id, uid, windowIndex, durationUs, positionInWindowUs, AdPlaybackState.NONE, false);
        }

        public Period set(Object id, Object uid, int windowIndex, long durationUs, long positionInWindowUs, AdPlaybackState adPlaybackState, boolean isPlaceholder) {
            this.id = id;
            this.uid = uid;
            this.windowIndex = windowIndex;
            this.durationUs = durationUs;
            this.positionInWindowUs = positionInWindowUs;
            this.adPlaybackState = adPlaybackState;
            this.isPlaceholder = isPlaceholder;
            return this;
        }

        public long getDurationMs() {
            return C.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInWindowMs() {
            return C.usToMs(this.positionInWindowUs);
        }

        public long getPositionInWindowUs() {
            return this.positionInWindowUs;
        }

        public Object getAdsId() {
            return this.adPlaybackState.adsId;
        }

        public int getAdGroupCount() {
            return this.adPlaybackState.adGroupCount;
        }

        public int getRemovedAdGroupCount() {
            return this.adPlaybackState.removedAdGroupCount;
        }

        public long getAdGroupTimeUs(int adGroupIndex) {
            return this.adPlaybackState.getAdGroup(adGroupIndex).timeUs;
        }

        public int getFirstAdIndexToPlay(int adGroupIndex) {
            return this.adPlaybackState.getAdGroup(adGroupIndex).getFirstAdIndexToPlay();
        }

        public int getNextAdIndexToPlay(int adGroupIndex, int lastPlayedAdIndex) {
            return this.adPlaybackState.getAdGroup(adGroupIndex).getNextAdIndexToPlay(lastPlayedAdIndex);
        }

        public boolean hasPlayedAdGroup(int adGroupIndex) {
            return !this.adPlaybackState.getAdGroup(adGroupIndex).hasUnplayedAds();
        }

        public int getAdGroupIndexForPositionUs(long positionUs) {
            return this.adPlaybackState.getAdGroupIndexForPositionUs(positionUs, this.durationUs);
        }

        public int getAdGroupIndexAfterPositionUs(long positionUs) {
            return this.adPlaybackState.getAdGroupIndexAfterPositionUs(positionUs, this.durationUs);
        }

        public int getAdCountInAdGroup(int adGroupIndex) {
            return this.adPlaybackState.getAdGroup(adGroupIndex).count;
        }

        public long getAdDurationUs(int adGroupIndex, int adIndexInAdGroup) {
            AdPlaybackState.AdGroup adGroup = this.adPlaybackState.getAdGroup(adGroupIndex);
            return adGroup.count != -1 ? adGroup.durationsUs[adIndexInAdGroup] : C.TIME_UNSET;
        }

        public long getAdResumePositionUs() {
            return this.adPlaybackState.adResumePositionUs;
        }

        public boolean isServerSideInsertedAdGroup(int adGroupIndex) {
            return this.adPlaybackState.getAdGroup(adGroupIndex).isServerSideInserted;
        }

        public long getContentResumeOffsetUs(int adGroupIndex) {
            return this.adPlaybackState.getAdGroup(adGroupIndex).contentResumeOffsetUs;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Period period = (Period) obj;
            return Util.areEqual(this.id, period.id) && Util.areEqual(this.uid, period.uid) && this.windowIndex == period.windowIndex && this.durationUs == period.durationUs && this.positionInWindowUs == period.positionInWindowUs && this.isPlaceholder == period.isPlaceholder && Util.areEqual(this.adPlaybackState, period.adPlaybackState);
        }

        public int hashCode() {
            Object obj = this.id;
            int iHashCode = (217 + (obj == null ? 0 : obj.hashCode())) * 31;
            Object obj2 = this.uid;
            int iHashCode2 = (((iHashCode + (obj2 != null ? obj2.hashCode() : 0)) * 31) + this.windowIndex) * 31;
            long j = this.durationUs;
            int i = (iHashCode2 + ((int) (j ^ (j >>> 32)))) * 31;
            long j2 = this.positionInWindowUs;
            return ((((i + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (this.isPlaceholder ? 1 : 0)) * 31) + this.adPlaybackState.hashCode();
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(keyForField(0), this.windowIndex);
            bundle.putLong(keyForField(1), this.durationUs);
            bundle.putLong(keyForField(2), this.positionInWindowUs);
            bundle.putBoolean(keyForField(3), this.isPlaceholder);
            bundle.putBundle(keyForField(4), this.adPlaybackState.toBundle());
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Period fromBundle(Bundle bundle) {
            AdPlaybackState adPlaybackState;
            int i = bundle.getInt(keyForField(0), 0);
            long j = bundle.getLong(keyForField(1), C.TIME_UNSET);
            long j2 = bundle.getLong(keyForField(2), 0L);
            boolean z = bundle.getBoolean(keyForField(3));
            Bundle bundle2 = bundle.getBundle(keyForField(4));
            if (bundle2 != null) {
                adPlaybackState = (AdPlaybackState) AdPlaybackState.CREATOR.fromBundle(bundle2);
            } else {
                adPlaybackState = AdPlaybackState.NONE;
            }
            AdPlaybackState adPlaybackState2 = adPlaybackState;
            Period period = new Period();
            period.set(null, null, i, j, j2, adPlaybackState2, z);
            return period;
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }

    public final boolean isEmpty() {
        return getWindowCount() == 0;
    }

    public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        if (repeatMode == 0) {
            if (windowIndex == getLastWindowIndex(shuffleModeEnabled)) {
                return -1;
            }
            return windowIndex + 1;
        }
        if (repeatMode == 1) {
            return windowIndex;
        }
        if (repeatMode == 2) {
            return windowIndex == getLastWindowIndex(shuffleModeEnabled) ? getFirstWindowIndex(shuffleModeEnabled) : windowIndex + 1;
        }
        throw new IllegalStateException();
    }

    public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        if (repeatMode == 0) {
            if (windowIndex == getFirstWindowIndex(shuffleModeEnabled)) {
                return -1;
            }
            return windowIndex - 1;
        }
        if (repeatMode == 1) {
            return windowIndex;
        }
        if (repeatMode == 2) {
            return windowIndex == getFirstWindowIndex(shuffleModeEnabled) ? getLastWindowIndex(shuffleModeEnabled) : windowIndex - 1;
        }
        throw new IllegalStateException();
    }

    public int getLastWindowIndex(boolean shuffleModeEnabled) {
        if (isEmpty()) {
            return -1;
        }
        return getWindowCount() - 1;
    }

    public int getFirstWindowIndex(boolean shuffleModeEnabled) {
        return isEmpty() ? -1 : 0;
    }

    public final Window getWindow(int windowIndex, Window window) {
        return getWindow(windowIndex, window, 0L);
    }

    public final int getNextPeriodIndex(int periodIndex, Period period, Window window, int repeatMode, boolean shuffleModeEnabled) {
        int i = getPeriod(periodIndex, period).windowIndex;
        if (getWindow(i, window).lastPeriodIndex != periodIndex) {
            return periodIndex + 1;
        }
        int nextWindowIndex = getNextWindowIndex(i, repeatMode, shuffleModeEnabled);
        if (nextWindowIndex == -1) {
            return -1;
        }
        return getWindow(nextWindowIndex, window).firstPeriodIndex;
    }

    public final boolean isLastPeriod(int periodIndex, Period period, Window window, int repeatMode, boolean shuffleModeEnabled) {
        return getNextPeriodIndex(periodIndex, period, window, repeatMode, shuffleModeEnabled) == -1;
    }

    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int windowIndex, long windowPositionUs) {
        return (Pair) Assertions.checkNotNull(getPeriodPosition(window, period, windowIndex, windowPositionUs, 0L));
    }

    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int windowIndex, long windowPositionUs, long defaultPositionProjectionUs) {
        Assertions.checkIndex(windowIndex, 0, getWindowCount());
        getWindow(windowIndex, window, defaultPositionProjectionUs);
        if (windowPositionUs == C.TIME_UNSET) {
            windowPositionUs = window.getDefaultPositionUs();
            if (windowPositionUs == C.TIME_UNSET) {
                return null;
            }
        }
        int i = window.firstPeriodIndex;
        getPeriod(i, period);
        while (i < window.lastPeriodIndex && period.positionInWindowUs != windowPositionUs) {
            int i2 = i + 1;
            if (getPeriod(i2, period).positionInWindowUs > windowPositionUs) {
                break;
            }
            i = i2;
        }
        getPeriod(i, period, true);
        return Pair.create(Assertions.checkNotNull(period.uid), Long.valueOf(windowPositionUs - period.positionInWindowUs));
    }

    public Period getPeriodByUid(Object periodUid, Period period) {
        return getPeriod(getIndexOfPeriod(periodUid), period, true);
    }

    public final Period getPeriod(int periodIndex, Period period) {
        return getPeriod(periodIndex, period, false);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Timeline)) {
            return false;
        }
        Timeline timeline = (Timeline) obj;
        if (timeline.getWindowCount() != getWindowCount() || timeline.getPeriodCount() != getPeriodCount()) {
            return false;
        }
        Window window = new Window();
        Period period = new Period();
        Window window2 = new Window();
        Period period2 = new Period();
        for (int i = 0; i < getWindowCount(); i++) {
            if (!getWindow(i, window).equals(timeline.getWindow(i, window2))) {
                return false;
            }
        }
        for (int i2 = 0; i2 < getPeriodCount(); i2++) {
            if (!getPeriod(i2, period, true).equals(timeline.getPeriod(i2, period2, true))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        Window window = new Window();
        Period period = new Period();
        int windowCount = 217 + getWindowCount();
        for (int i = 0; i < getWindowCount(); i++) {
            windowCount = (windowCount * 31) + getWindow(i, window).hashCode();
        }
        int periodCount = (windowCount * 31) + getPeriodCount();
        for (int i2 = 0; i2 < getPeriodCount(); i2++) {
            periodCount = (periodCount * 31) + getPeriod(i2, period, true).hashCode();
        }
        return periodCount;
    }

    public final Bundle toBundle(boolean excludeMediaItems) {
        ArrayList arrayList = new ArrayList();
        int windowCount = getWindowCount();
        Window window = new Window();
        for (int i = 0; i < windowCount; i++) {
            arrayList.add(getWindow(i, window, 0L).toBundle(excludeMediaItems));
        }
        ArrayList arrayList2 = new ArrayList();
        int periodCount = getPeriodCount();
        Period period = new Period();
        for (int i2 = 0; i2 < periodCount; i2++) {
            arrayList2.add(getPeriod(i2, period, false).toBundle());
        }
        int[] iArr = new int[windowCount];
        if (windowCount > 0) {
            iArr[0] = getFirstWindowIndex(true);
        }
        for (int i3 = 1; i3 < windowCount; i3++) {
            iArr[i3] = getNextWindowIndex(iArr[i3 - 1], 0, true);
        }
        Bundle bundle = new Bundle();
        BundleUtil.putBinder(bundle, keyForField(0), new BundleListRetriever(arrayList));
        BundleUtil.putBinder(bundle, keyForField(1), new BundleListRetriever(arrayList2));
        bundle.putIntArray(keyForField(2), iArr);
        return bundle;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public final Bundle toBundle() {
        return toBundle(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Timeline fromBundle(Bundle bundle) {
        ImmutableList immutableListFromBundleListRetriever = fromBundleListRetriever(Window.CREATOR, BundleUtil.getBinder(bundle, keyForField(0)));
        ImmutableList immutableListFromBundleListRetriever2 = fromBundleListRetriever(Period.CREATOR, BundleUtil.getBinder(bundle, keyForField(1)));
        int[] intArray = bundle.getIntArray(keyForField(2));
        if (intArray == null) {
            intArray = generateUnshuffledIndices(immutableListFromBundleListRetriever.size());
        }
        return new RemotableTimeline(immutableListFromBundleListRetriever, immutableListFromBundleListRetriever2, intArray);
    }

    private static <T extends Bundleable> ImmutableList<T> fromBundleListRetriever(Bundleable.Creator<T> creator, IBinder binder) {
        if (binder == null) {
            return ImmutableList.of();
        }
        ImmutableList.Builder builder = new ImmutableList.Builder();
        ImmutableList<Bundle> list = BundleListRetriever.getList(binder);
        for (int i = 0; i < list.size(); i++) {
            builder.add((ImmutableList.Builder) creator.fromBundle(list.get(i)));
        }
        return builder.build();
    }

    private static String keyForField(int field) {
        return Integer.toString(field, 36);
    }

    private static int[] generateUnshuffledIndices(int n) {
        int[] iArr = new int[n];
        for (int i = 0; i < n; i++) {
            iArr[i] = i;
        }
        return iArr;
    }

    public static final class RemotableTimeline extends Timeline {
        private final ImmutableList<Period> periods;
        private final int[] shuffledWindowIndices;
        private final int[] windowIndicesInShuffled;
        private final ImmutableList<Window> windows;

        public RemotableTimeline(ImmutableList<Window> windows, ImmutableList<Period> periods, int[] shuffledWindowIndices) {
            Assertions.checkArgument(windows.size() == shuffledWindowIndices.length);
            this.windows = windows;
            this.periods = periods;
            this.shuffledWindowIndices = shuffledWindowIndices;
            this.windowIndicesInShuffled = new int[shuffledWindowIndices.length];
            for (int i = 0; i < shuffledWindowIndices.length; i++) {
                this.windowIndicesInShuffled[shuffledWindowIndices[i]] = i;
            }
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.windows.size();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Window getWindow(int windowIndex, Window window, long defaultPositionProjectionUs) {
            Window window2 = this.windows.get(windowIndex);
            window.set(window2.uid, window2.mediaItem, window2.manifest, window2.presentationStartTimeMs, window2.windowStartTimeMs, window2.elapsedRealtimeEpochOffsetMs, window2.isSeekable, window2.isDynamic, window2.liveConfiguration, window2.defaultPositionUs, window2.durationUs, window2.firstPeriodIndex, window2.lastPeriodIndex, window2.positionInFirstPeriodUs);
            window.isPlaceholder = window2.isPlaceholder;
            return window;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
            if (repeatMode == 1) {
                return windowIndex;
            }
            if (windowIndex != getLastWindowIndex(shuffleModeEnabled)) {
                return shuffleModeEnabled ? this.shuffledWindowIndices[this.windowIndicesInShuffled[windowIndex] + 1] : windowIndex + 1;
            }
            if (repeatMode == 2) {
                return getFirstWindowIndex(shuffleModeEnabled);
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
            if (repeatMode == 1) {
                return windowIndex;
            }
            if (windowIndex != getFirstWindowIndex(shuffleModeEnabled)) {
                return shuffleModeEnabled ? this.shuffledWindowIndices[this.windowIndicesInShuffled[windowIndex] - 1] : windowIndex - 1;
            }
            if (repeatMode == 2) {
                return getLastWindowIndex(shuffleModeEnabled);
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getLastWindowIndex(boolean shuffleModeEnabled) {
            if (isEmpty()) {
                return -1;
            }
            if (shuffleModeEnabled) {
                return this.shuffledWindowIndices[getWindowCount() - 1];
            }
            return getWindowCount() - 1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getFirstWindowIndex(boolean shuffleModeEnabled) {
            if (isEmpty()) {
                return -1;
            }
            if (shuffleModeEnabled) {
                return this.shuffledWindowIndices[0];
            }
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.periods.size();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Period getPeriod(int periodIndex, Period period, boolean setIds) {
            Period period2 = this.periods.get(periodIndex);
            period.set(period2.id, period2.uid, period2.windowIndex, period2.durationUs, period2.positionInWindowUs, period2.adPlaybackState, period2.isPlaceholder);
            return period;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object uid) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int periodIndex) {
            throw new UnsupportedOperationException();
        }
    }
}
