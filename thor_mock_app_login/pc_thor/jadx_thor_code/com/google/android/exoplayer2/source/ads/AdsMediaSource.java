package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class AdsMediaSource extends CompositeMediaSource<MediaSource.MediaPeriodId> {
    private static final MediaSource.MediaPeriodId CHILD_SOURCE_MEDIA_PERIOD_ID = new MediaSource.MediaPeriodId(new Object());
    private final MediaSourceFactory adMediaSourceFactory;
    private AdPlaybackState adPlaybackState;
    private final DataSpec adTagDataSpec;
    private final AdViewProvider adViewProvider;
    private final Object adsId;
    private final AdsLoader adsLoader;
    private ComponentListener componentListener;
    private final MediaSource contentMediaSource;
    private Timeline contentTimeline;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Timeline.Period period = new Timeline.Period();
    private AdMediaSourceHolder[][] adMediaSourceHolders = new AdMediaSourceHolder[0][];

    public static final class AdLoadException extends IOException {
        public static final int TYPE_AD = 0;
        public static final int TYPE_AD_GROUP = 1;
        public static final int TYPE_ALL_ADS = 2;
        public static final int TYPE_UNEXPECTED = 3;
        public final int type;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        public static AdLoadException createForAd(Exception error) {
            return new AdLoadException(0, error);
        }

        public static AdLoadException createForAdGroup(Exception error, int adGroupIndex) {
            return new AdLoadException(1, new IOException(new StringBuilder(35).append("Failed to load ad group ").append(adGroupIndex).toString(), error));
        }

        public static AdLoadException createForAllAds(Exception error) {
            return new AdLoadException(2, error);
        }

        public static AdLoadException createForUnexpected(RuntimeException error) {
            return new AdLoadException(3, error);
        }

        private AdLoadException(int type, Exception cause) {
            super(cause);
            this.type = type;
        }

        public RuntimeException getRuntimeExceptionForUnexpected() {
            Assertions.checkState(this.type == 3);
            return (RuntimeException) Assertions.checkNotNull(getCause());
        }
    }

    public AdsMediaSource(MediaSource contentMediaSource, DataSpec adTagDataSpec, Object adsId, MediaSourceFactory adMediaSourceFactory, AdsLoader adsLoader, AdViewProvider adViewProvider) {
        this.contentMediaSource = contentMediaSource;
        this.adMediaSourceFactory = adMediaSourceFactory;
        this.adsLoader = adsLoader;
        this.adViewProvider = adViewProvider;
        this.adTagDataSpec = adTagDataSpec;
        this.adsId = adsId;
        adsLoader.setSupportedContentTypes(adMediaSourceFactory.getSupportedTypes());
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.contentMediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        super.prepareSourceInternal(mediaTransferListener);
        final ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        prepareChildSource(CHILD_SOURCE_MEDIA_PERIOD_ID, this.contentMediaSource);
        this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.AdsMediaSource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m183x4dfad2b6(componentListener);
            }
        });
    }

    /* renamed from: lambda$prepareSourceInternal$0$com-google-android-exoplayer2-source-ads-AdsMediaSource, reason: not valid java name */
    /* synthetic */ void m183x4dfad2b6(ComponentListener componentListener) {
        this.adsLoader.start(this, this.adTagDataSpec, this.adsId, this.adViewProvider, componentListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        if (((AdPlaybackState) Assertions.checkNotNull(this.adPlaybackState)).adGroupCount > 0 && id.isAd()) {
            int i = id.adGroupIndex;
            int i2 = id.adIndexInAdGroup;
            AdMediaSourceHolder[][] adMediaSourceHolderArr = this.adMediaSourceHolders;
            AdMediaSourceHolder[] adMediaSourceHolderArr2 = adMediaSourceHolderArr[i];
            if (adMediaSourceHolderArr2.length <= i2) {
                adMediaSourceHolderArr[i] = (AdMediaSourceHolder[]) Arrays.copyOf(adMediaSourceHolderArr2, i2 + 1);
            }
            AdMediaSourceHolder adMediaSourceHolder = this.adMediaSourceHolders[i][i2];
            if (adMediaSourceHolder == null) {
                adMediaSourceHolder = new AdMediaSourceHolder(id);
                this.adMediaSourceHolders[i][i2] = adMediaSourceHolder;
                maybeUpdateAdMediaSources();
            }
            return adMediaSourceHolder.createMediaPeriod(id, allocator, startPositionUs);
        }
        MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(id, allocator, startPositionUs);
        maskingMediaPeriod.setMediaSource(this.contentMediaSource);
        maskingMediaPeriod.createPeriod(id);
        return maskingMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MaskingMediaPeriod maskingMediaPeriod = (MaskingMediaPeriod) mediaPeriod;
        MediaSource.MediaPeriodId mediaPeriodId = maskingMediaPeriod.id;
        if (mediaPeriodId.isAd()) {
            AdMediaSourceHolder adMediaSourceHolder = (AdMediaSourceHolder) Assertions.checkNotNull(this.adMediaSourceHolders[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup]);
            adMediaSourceHolder.releaseMediaPeriod(maskingMediaPeriod);
            if (adMediaSourceHolder.isInactive()) {
                adMediaSourceHolder.release();
                this.adMediaSourceHolders[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup] = null;
                return;
            }
            return;
        }
        maskingMediaPeriod.releasePeriod();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        super.releaseSourceInternal();
        final ComponentListener componentListener = (ComponentListener) Assertions.checkNotNull(this.componentListener);
        this.componentListener = null;
        componentListener.stop();
        this.contentTimeline = null;
        this.adPlaybackState = null;
        this.adMediaSourceHolders = new AdMediaSourceHolder[0][];
        this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.AdsMediaSource$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m184x85e6f6b7(componentListener);
            }
        });
    }

    /* renamed from: lambda$releaseSourceInternal$1$com-google-android-exoplayer2-source-ads-AdsMediaSource, reason: not valid java name */
    /* synthetic */ void m184x85e6f6b7(ComponentListener componentListener) {
        this.adsLoader.stop(this, componentListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    /* renamed from: onChildSourceInfoRefreshed, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public void m172x365769cd(MediaSource.MediaPeriodId mediaPeriodId, MediaSource mediaSource, Timeline timeline) {
        if (mediaPeriodId.isAd()) {
            ((AdMediaSourceHolder) Assertions.checkNotNull(this.adMediaSourceHolders[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup])).handleSourceInfoRefresh(timeline);
        } else {
            Assertions.checkArgument(timeline.getPeriodCount() == 1);
            this.contentTimeline = timeline;
        }
        maybeUpdateSourceInfo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSource.MediaPeriodId childId, MediaSource.MediaPeriodId mediaPeriodId) {
        return childId.isAd() ? childId : mediaPeriodId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdPlaybackState(AdPlaybackState adPlaybackState) {
        if (this.adPlaybackState == null) {
            AdMediaSourceHolder[][] adMediaSourceHolderArr = new AdMediaSourceHolder[adPlaybackState.adGroupCount][];
            this.adMediaSourceHolders = adMediaSourceHolderArr;
            Arrays.fill(adMediaSourceHolderArr, new AdMediaSourceHolder[0]);
        } else {
            Assertions.checkState(adPlaybackState.adGroupCount == this.adPlaybackState.adGroupCount);
        }
        this.adPlaybackState = adPlaybackState;
        maybeUpdateAdMediaSources();
        maybeUpdateSourceInfo();
    }

    private void maybeUpdateAdMediaSources() {
        Uri uri;
        AdPlaybackState adPlaybackState = this.adPlaybackState;
        if (adPlaybackState == null) {
            return;
        }
        for (int i = 0; i < this.adMediaSourceHolders.length; i++) {
            int i2 = 0;
            while (true) {
                AdMediaSourceHolder[] adMediaSourceHolderArr = this.adMediaSourceHolders[i];
                if (i2 < adMediaSourceHolderArr.length) {
                    AdMediaSourceHolder adMediaSourceHolder = adMediaSourceHolderArr[i2];
                    AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
                    if (adMediaSourceHolder != null && !adMediaSourceHolder.hasMediaSource() && i2 < adGroup.uris.length && (uri = adGroup.uris[i2]) != null) {
                        MediaItem.Builder uri2 = new MediaItem.Builder().setUri(uri);
                        MediaItem.PlaybackProperties playbackProperties = this.contentMediaSource.getMediaItem().playbackProperties;
                        if (playbackProperties != null && playbackProperties.drmConfiguration != null) {
                            MediaItem.DrmConfiguration drmConfiguration = playbackProperties.drmConfiguration;
                            uri2.setDrmUuid(drmConfiguration.uuid);
                            uri2.setDrmKeySetId(drmConfiguration.getKeySetId());
                            uri2.setDrmLicenseUri(drmConfiguration.licenseUri);
                            uri2.setDrmForceDefaultLicenseUri(drmConfiguration.forceDefaultLicenseUri);
                            uri2.setDrmLicenseRequestHeaders(drmConfiguration.requestHeaders);
                            uri2.setDrmMultiSession(drmConfiguration.multiSession);
                            uri2.setDrmPlayClearContentWithoutKey(drmConfiguration.playClearContentWithoutKey);
                            uri2.setDrmSessionForClearTypes(drmConfiguration.sessionForClearTypes);
                        }
                        adMediaSourceHolder.initializeWithMediaSource(this.adMediaSourceFactory.createMediaSource(uri2.build()), uri);
                    }
                    i2++;
                }
            }
        }
    }

    private void maybeUpdateSourceInfo() {
        Timeline timeline = this.contentTimeline;
        AdPlaybackState adPlaybackState = this.adPlaybackState;
        if (adPlaybackState == null || timeline == null) {
            return;
        }
        if (adPlaybackState.adGroupCount == 0) {
            refreshSourceInfo(timeline);
        } else {
            this.adPlaybackState = this.adPlaybackState.withAdDurationsUs(getAdDurationsUs());
            refreshSourceInfo(new SinglePeriodAdTimeline(timeline, this.adPlaybackState));
        }
    }

    private long[][] getAdDurationsUs() {
        long[][] jArr = new long[this.adMediaSourceHolders.length][];
        int i = 0;
        while (true) {
            AdMediaSourceHolder[][] adMediaSourceHolderArr = this.adMediaSourceHolders;
            if (i >= adMediaSourceHolderArr.length) {
                return jArr;
            }
            jArr[i] = new long[adMediaSourceHolderArr[i].length];
            int i2 = 0;
            while (true) {
                AdMediaSourceHolder[] adMediaSourceHolderArr2 = this.adMediaSourceHolders[i];
                if (i2 < adMediaSourceHolderArr2.length) {
                    AdMediaSourceHolder adMediaSourceHolder = adMediaSourceHolderArr2[i2];
                    jArr[i][i2] = adMediaSourceHolder == null ? C.TIME_UNSET : adMediaSourceHolder.getDurationUs();
                    i2++;
                }
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ComponentListener implements AdsLoader.EventListener {
        private final Handler playerHandler = Util.createHandlerForCurrentLooper();
        private volatile boolean stopped;

        public ComponentListener() {
        }

        public void stop() {
            this.stopped = true;
            this.playerHandler.removeCallbacksAndMessages(null);
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public void onAdPlaybackState(final AdPlaybackState adPlaybackState) {
            if (this.stopped) {
                return;
            }
            this.playerHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.AdsMediaSource$ComponentListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m187x9c1f3358(adPlaybackState);
                }
            });
        }

        /* renamed from: lambda$onAdPlaybackState$0$com-google-android-exoplayer2-source-ads-AdsMediaSource$ComponentListener, reason: not valid java name */
        /* synthetic */ void m187x9c1f3358(AdPlaybackState adPlaybackState) {
            if (this.stopped) {
                return;
            }
            AdsMediaSource.this.onAdPlaybackState(adPlaybackState);
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public void onAdLoadError(final AdLoadException error, DataSpec dataSpec) {
            if (this.stopped) {
                return;
            }
            AdsMediaSource.this.createEventDispatcher(null).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), dataSpec, SystemClock.elapsedRealtime()), 6, (IOException) error, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class AdPrepareListener implements MaskingMediaPeriod.PrepareListener {
        private final Uri adUri;

        public AdPrepareListener(Uri adUri) {
            this.adUri = adUri;
        }

        @Override // com.google.android.exoplayer2.source.MaskingMediaPeriod.PrepareListener
        public void onPrepareComplete(final MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.AdsMediaSource$AdPrepareListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m185x605e06cc(mediaPeriodId);
                }
            });
        }

        /* renamed from: lambda$onPrepareComplete$0$com-google-android-exoplayer2-source-ads-AdsMediaSource$AdPrepareListener, reason: not valid java name */
        /* synthetic */ void m185x605e06cc(MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.adsLoader.handlePrepareComplete(AdsMediaSource.this, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        }

        @Override // com.google.android.exoplayer2.source.MaskingMediaPeriod.PrepareListener
        public void onPrepareError(final MediaSource.MediaPeriodId mediaPeriodId, final IOException exception) {
            AdsMediaSource.this.createEventDispatcher(mediaPeriodId).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), new DataSpec(this.adUri), SystemClock.elapsedRealtime()), 6, (IOException) AdLoadException.createForAd(exception), true);
            AdsMediaSource.this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.AdsMediaSource$AdPrepareListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m186x1f837766(mediaPeriodId, exception);
                }
            });
        }

        /* renamed from: lambda$onPrepareError$1$com-google-android-exoplayer2-source-ads-AdsMediaSource$AdPrepareListener, reason: not valid java name */
        /* synthetic */ void m186x1f837766(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException) {
            AdsMediaSource.this.adsLoader.handlePrepareError(AdsMediaSource.this, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, iOException);
        }
    }

    private final class AdMediaSourceHolder {
        private final List<MaskingMediaPeriod> activeMediaPeriods = new ArrayList();
        private MediaSource adMediaSource;
        private Uri adUri;
        private final MediaSource.MediaPeriodId id;
        private Timeline timeline;

        public AdMediaSourceHolder(MediaSource.MediaPeriodId id) {
            this.id = id;
        }

        public void initializeWithMediaSource(MediaSource adMediaSource, Uri adUri) {
            this.adMediaSource = adMediaSource;
            this.adUri = adUri;
            for (int i = 0; i < this.activeMediaPeriods.size(); i++) {
                MaskingMediaPeriod maskingMediaPeriod = this.activeMediaPeriods.get(i);
                maskingMediaPeriod.setMediaSource(adMediaSource);
                maskingMediaPeriod.setPrepareListener(AdsMediaSource.this.new AdPrepareListener(adUri));
            }
            AdsMediaSource.this.prepareChildSource(this.id, adMediaSource);
        }

        public MediaPeriod createMediaPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(id, allocator, startPositionUs);
            this.activeMediaPeriods.add(maskingMediaPeriod);
            MediaSource mediaSource = this.adMediaSource;
            if (mediaSource != null) {
                maskingMediaPeriod.setMediaSource(mediaSource);
                maskingMediaPeriod.setPrepareListener(AdsMediaSource.this.new AdPrepareListener((Uri) Assertions.checkNotNull(this.adUri)));
            }
            Timeline timeline = this.timeline;
            if (timeline != null) {
                maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(0), id.windowSequenceNumber));
            }
            return maskingMediaPeriod;
        }

        public void handleSourceInfoRefresh(Timeline timeline) {
            Assertions.checkArgument(timeline.getPeriodCount() == 1);
            if (this.timeline == null) {
                Object uidOfPeriod = timeline.getUidOfPeriod(0);
                for (int i = 0; i < this.activeMediaPeriods.size(); i++) {
                    MaskingMediaPeriod maskingMediaPeriod = this.activeMediaPeriods.get(i);
                    maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(uidOfPeriod, maskingMediaPeriod.id.windowSequenceNumber));
                }
            }
            this.timeline = timeline;
        }

        public long getDurationUs() {
            Timeline timeline = this.timeline;
            return timeline == null ? C.TIME_UNSET : timeline.getPeriod(0, AdsMediaSource.this.period).getDurationUs();
        }

        public void releaseMediaPeriod(MaskingMediaPeriod maskingMediaPeriod) {
            this.activeMediaPeriods.remove(maskingMediaPeriod);
            maskingMediaPeriod.releasePeriod();
        }

        public void release() {
            if (hasMediaSource()) {
                AdsMediaSource.this.releaseChildSource(this.id);
            }
        }

        public boolean hasMediaSource() {
            return this.adMediaSource != null;
        }

        public boolean isInactive() {
            return this.activeMediaPeriods.isEmpty();
        }
    }
}
