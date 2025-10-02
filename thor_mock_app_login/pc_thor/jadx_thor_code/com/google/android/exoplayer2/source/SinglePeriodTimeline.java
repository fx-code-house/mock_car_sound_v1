package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class SinglePeriodTimeline extends Timeline {
    private final long elapsedRealtimeEpochOffsetMs;
    private final boolean isDynamic;
    private final boolean isSeekable;
    private final MediaItem.LiveConfiguration liveConfiguration;
    private final Object manifest;
    private final MediaItem mediaItem;
    private final long periodDurationUs;
    private final long presentationStartTimeMs;
    private final boolean suppressPositionProjection;
    private final long windowDefaultStartPositionUs;
    private final long windowDurationUs;
    private final long windowPositionInPeriodUs;
    private final long windowStartTimeMs;
    private static final Object UID = new Object();
    private static final MediaItem MEDIA_ITEM = new MediaItem.Builder().setMediaId("SinglePeriodTimeline").setUri(Uri.EMPTY).build();

    @Override // com.google.android.exoplayer2.Timeline
    public int getPeriodCount() {
        return 1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getWindowCount() {
        return 1;
    }

    @Deprecated
    public SinglePeriodTimeline(long durationUs, boolean isSeekable, boolean isDynamic, boolean isLive, Object manifest, Object tag) {
        this(durationUs, durationUs, 0L, 0L, isSeekable, isDynamic, isLive, manifest, tag);
    }

    public SinglePeriodTimeline(long durationUs, boolean isSeekable, boolean isDynamic, boolean useLiveConfiguration, Object manifest, MediaItem mediaItem) {
        this(durationUs, durationUs, 0L, 0L, isSeekable, isDynamic, useLiveConfiguration, manifest, mediaItem);
    }

    @Deprecated
    public SinglePeriodTimeline(long periodDurationUs, long windowDurationUs, long windowPositionInPeriodUs, long windowDefaultStartPositionUs, boolean isSeekable, boolean isDynamic, boolean isLive, Object manifest, Object tag) {
        this(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, periodDurationUs, windowDurationUs, windowPositionInPeriodUs, windowDefaultStartPositionUs, isSeekable, isDynamic, isLive, manifest, tag);
    }

    public SinglePeriodTimeline(long periodDurationUs, long windowDurationUs, long windowPositionInPeriodUs, long windowDefaultStartPositionUs, boolean isSeekable, boolean isDynamic, boolean useLiveConfiguration, Object manifest, MediaItem mediaItem) {
        this(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, periodDurationUs, windowDurationUs, windowPositionInPeriodUs, windowDefaultStartPositionUs, isSeekable, isDynamic, false, manifest, mediaItem, useLiveConfiguration ? mediaItem.liveConfiguration : null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @Deprecated
    public SinglePeriodTimeline(long presentationStartTimeMs, long windowStartTimeMs, long elapsedRealtimeEpochOffsetMs, long periodDurationUs, long windowDurationUs, long windowPositionInPeriodUs, long windowDefaultStartPositionUs, boolean isSeekable, boolean isDynamic, boolean isLive, Object manifest, Object tag) {
        MediaItem mediaItem = MEDIA_ITEM;
        this(presentationStartTimeMs, windowStartTimeMs, elapsedRealtimeEpochOffsetMs, periodDurationUs, windowDurationUs, windowPositionInPeriodUs, windowDefaultStartPositionUs, isSeekable, isDynamic, false, manifest, mediaItem.buildUpon().setTag(tag).build(), isLive ? mediaItem.liveConfiguration : null);
    }

    @Deprecated
    public SinglePeriodTimeline(long presentationStartTimeMs, long windowStartTimeMs, long elapsedRealtimeEpochOffsetMs, long periodDurationUs, long windowDurationUs, long windowPositionInPeriodUs, long windowDefaultStartPositionUs, boolean isSeekable, boolean isDynamic, Object manifest, MediaItem mediaItem, MediaItem.LiveConfiguration liveConfiguration) {
        this(presentationStartTimeMs, windowStartTimeMs, elapsedRealtimeEpochOffsetMs, periodDurationUs, windowDurationUs, windowPositionInPeriodUs, windowDefaultStartPositionUs, isSeekable, isDynamic, false, manifest, mediaItem, liveConfiguration);
    }

    public SinglePeriodTimeline(long presentationStartTimeMs, long windowStartTimeMs, long elapsedRealtimeEpochOffsetMs, long periodDurationUs, long windowDurationUs, long windowPositionInPeriodUs, long windowDefaultStartPositionUs, boolean isSeekable, boolean isDynamic, boolean suppressPositionProjection, Object manifest, MediaItem mediaItem, MediaItem.LiveConfiguration liveConfiguration) {
        this.presentationStartTimeMs = presentationStartTimeMs;
        this.windowStartTimeMs = windowStartTimeMs;
        this.elapsedRealtimeEpochOffsetMs = elapsedRealtimeEpochOffsetMs;
        this.periodDurationUs = periodDurationUs;
        this.windowDurationUs = windowDurationUs;
        this.windowPositionInPeriodUs = windowPositionInPeriodUs;
        this.windowDefaultStartPositionUs = windowDefaultStartPositionUs;
        this.isSeekable = isSeekable;
        this.isDynamic = isDynamic;
        this.suppressPositionProjection = suppressPositionProjection;
        this.manifest = manifest;
        this.mediaItem = (MediaItem) Assertions.checkNotNull(mediaItem);
        this.liveConfiguration = liveConfiguration;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002e A[PHI: r1
      0x002e: PHI (r1v2 long) = (r1v1 long), (r1v1 long), (r1v1 long), (r1v10 long) binds: [B:3:0x000d, B:5:0x0011, B:7:0x0017, B:12:0x002b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.exoplayer2.Timeline
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.google.android.exoplayer2.Timeline.Window getWindow(int r29, com.google.android.exoplayer2.Timeline.Window r30, long r31) {
        /*
            r28 = this;
            r0 = r28
            r1 = 0
            r2 = 1
            r3 = r29
            com.google.android.exoplayer2.util.Assertions.checkIndex(r3, r1, r2)
            long r1 = r0.windowDefaultStartPositionUs
            boolean r3 = r0.isDynamic
            if (r3 == 0) goto L2e
            boolean r3 = r0.suppressPositionProjection
            if (r3 != 0) goto L2e
            r3 = 0
            int r3 = (r31 > r3 ? 1 : (r31 == r3 ? 0 : -1))
            if (r3 == 0) goto L2e
            long r3 = r0.windowDurationUs
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L27
        L24:
            r20 = r5
            goto L30
        L27:
            long r1 = r1 + r31
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 <= 0) goto L2e
            goto L24
        L2e:
            r20 = r1
        L30:
            java.lang.Object r8 = com.google.android.exoplayer2.Timeline.Window.SINGLE_WINDOW_UID
            com.google.android.exoplayer2.MediaItem r9 = r0.mediaItem
            java.lang.Object r10 = r0.manifest
            long r11 = r0.presentationStartTimeMs
            long r13 = r0.windowStartTimeMs
            long r1 = r0.elapsedRealtimeEpochOffsetMs
            r15 = r1
            boolean r1 = r0.isSeekable
            r17 = r1
            boolean r1 = r0.isDynamic
            r18 = r1
            com.google.android.exoplayer2.MediaItem$LiveConfiguration r1 = r0.liveConfiguration
            r19 = r1
            long r1 = r0.windowDurationUs
            r22 = r1
            r24 = 0
            r25 = 0
            long r1 = r0.windowPositionInPeriodUs
            r26 = r1
            r7 = r30
            com.google.android.exoplayer2.Timeline$Window r1 = r7.set(r8, r9, r10, r11, r13, r15, r17, r18, r19, r20, r22, r24, r25, r26)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SinglePeriodTimeline.getWindow(int, com.google.android.exoplayer2.Timeline$Window, long):com.google.android.exoplayer2.Timeline$Window");
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
        Assertions.checkIndex(periodIndex, 0, 1);
        return period.set(null, setIds ? UID : null, 0, this.periodDurationUs, -this.windowPositionInPeriodUs);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getIndexOfPeriod(Object uid) {
        return UID.equals(uid) ? 0 : -1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Object getUidOfPeriod(int periodIndex) {
        Assertions.checkIndex(periodIndex, 0, 1);
        return UID;
    }
}
