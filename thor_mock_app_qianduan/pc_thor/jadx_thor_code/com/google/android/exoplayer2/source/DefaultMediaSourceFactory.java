package com.google.android.exoplayer2.source;

import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class DefaultMediaSourceFactory implements MediaSourceFactory {
    private static final String TAG = "DefaultMediaSourceFactory";
    private AdViewProvider adViewProvider;
    private AdsLoaderProvider adsLoaderProvider;
    private final DataSource.Factory dataSourceFactory;
    private long liveMaxOffsetMs;
    private float liveMaxSpeed;
    private long liveMinOffsetMs;
    private float liveMinSpeed;
    private long liveTargetOffsetMs;
    private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final SparseArray<MediaSourceFactory> mediaSourceFactories;
    private final int[] supportedTypes;

    public interface AdsLoaderProvider {
        AdsLoader getAdsLoader(MediaItem.AdsConfiguration adsConfiguration);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(List streamKeys) {
        return setStreamKeys((List<StreamKey>) streamKeys);
    }

    public DefaultMediaSourceFactory(Context context) {
        this(new DefaultDataSourceFactory(context));
    }

    public DefaultMediaSourceFactory(Context context, ExtractorsFactory extractorsFactory) {
        this(new DefaultDataSourceFactory(context), extractorsFactory);
    }

    public DefaultMediaSourceFactory(DataSource.Factory dataSourceFactory) {
        this(dataSourceFactory, new DefaultExtractorsFactory());
    }

    public DefaultMediaSourceFactory(DataSource.Factory dataSourceFactory, ExtractorsFactory extractorsFactory) {
        this.dataSourceFactory = dataSourceFactory;
        SparseArray<MediaSourceFactory> sparseArrayLoadDelegates = loadDelegates(dataSourceFactory, extractorsFactory);
        this.mediaSourceFactories = sparseArrayLoadDelegates;
        this.supportedTypes = new int[sparseArrayLoadDelegates.size()];
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.supportedTypes[i] = this.mediaSourceFactories.keyAt(i);
        }
        this.liveTargetOffsetMs = C.TIME_UNSET;
        this.liveMinOffsetMs = C.TIME_UNSET;
        this.liveMaxOffsetMs = C.TIME_UNSET;
        this.liveMinSpeed = -3.4028235E38f;
        this.liveMaxSpeed = -3.4028235E38f;
    }

    public DefaultMediaSourceFactory setAdsLoaderProvider(AdsLoaderProvider adsLoaderProvider) {
        this.adsLoaderProvider = adsLoaderProvider;
        return this;
    }

    public DefaultMediaSourceFactory setAdViewProvider(AdViewProvider adViewProvider) {
        this.adViewProvider = adViewProvider;
        return this;
    }

    public DefaultMediaSourceFactory setLiveTargetOffsetMs(long liveTargetOffsetMs) {
        this.liveTargetOffsetMs = liveTargetOffsetMs;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinOffsetMs(long liveMinOffsetMs) {
        this.liveMinOffsetMs = liveMinOffsetMs;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxOffsetMs(long liveMaxOffsetMs) {
        this.liveMaxOffsetMs = liveMaxOffsetMs;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinSpeed(float minSpeed) {
        this.liveMinSpeed = minSpeed;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxSpeed(float maxSpeed) {
        this.liveMaxSpeed = maxSpeed;
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmHttpDataSourceFactory(HttpDataSource.Factory drmHttpDataSourceFactory) {
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.mediaSourceFactories.valueAt(i).setDrmHttpDataSourceFactory(drmHttpDataSourceFactory);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmUserAgent(String userAgent) {
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.mediaSourceFactories.valueAt(i).setDrmUserAgent(userAgent);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmSessionManager(DrmSessionManager drmSessionManager) {
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.mediaSourceFactories.valueAt(i).setDrmSessionManager(drmSessionManager);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.mediaSourceFactories.valueAt(i).setDrmSessionManagerProvider(drmSessionManagerProvider);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.mediaSourceFactories.valueAt(i).setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public DefaultMediaSourceFactory setStreamKeys(List<StreamKey> streamKeys) {
        for (int i = 0; i < this.mediaSourceFactories.size(); i++) {
            this.mediaSourceFactories.valueAt(i).setStreamKeys(streamKeys);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public int[] getSupportedTypes() {
        int[] iArr = this.supportedTypes;
        return Arrays.copyOf(iArr, iArr.length);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public MediaSource createMediaSource(MediaItem mediaItem) {
        long j;
        float f;
        float f2;
        long j2;
        long j3;
        Assertions.checkNotNull(mediaItem.playbackProperties);
        int iInferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(mediaItem.playbackProperties.uri, mediaItem.playbackProperties.mimeType);
        MediaSourceFactory mediaSourceFactory = this.mediaSourceFactories.get(iInferContentTypeForUriAndMimeType);
        Assertions.checkNotNull(mediaSourceFactory, new StringBuilder(68).append("No suitable media source factory found for content type: ").append(iInferContentTypeForUriAndMimeType).toString());
        if ((mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET && this.liveTargetOffsetMs != C.TIME_UNSET) || ((mediaItem.liveConfiguration.minPlaybackSpeed == -3.4028235E38f && this.liveMinSpeed != -3.4028235E38f) || ((mediaItem.liveConfiguration.maxPlaybackSpeed == -3.4028235E38f && this.liveMaxSpeed != -3.4028235E38f) || ((mediaItem.liveConfiguration.minOffsetMs == C.TIME_UNSET && this.liveMinOffsetMs != C.TIME_UNSET) || (mediaItem.liveConfiguration.maxOffsetMs == C.TIME_UNSET && this.liveMaxOffsetMs != C.TIME_UNSET))))) {
            MediaItem.Builder builderBuildUpon = mediaItem.buildUpon();
            if (mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET) {
                j = this.liveTargetOffsetMs;
            } else {
                j = mediaItem.liveConfiguration.targetOffsetMs;
            }
            MediaItem.Builder liveTargetOffsetMs = builderBuildUpon.setLiveTargetOffsetMs(j);
            if (mediaItem.liveConfiguration.minPlaybackSpeed == -3.4028235E38f) {
                f = this.liveMinSpeed;
            } else {
                f = mediaItem.liveConfiguration.minPlaybackSpeed;
            }
            MediaItem.Builder liveMinPlaybackSpeed = liveTargetOffsetMs.setLiveMinPlaybackSpeed(f);
            if (mediaItem.liveConfiguration.maxPlaybackSpeed == -3.4028235E38f) {
                f2 = this.liveMaxSpeed;
            } else {
                f2 = mediaItem.liveConfiguration.maxPlaybackSpeed;
            }
            MediaItem.Builder liveMaxPlaybackSpeed = liveMinPlaybackSpeed.setLiveMaxPlaybackSpeed(f2);
            if (mediaItem.liveConfiguration.minOffsetMs == C.TIME_UNSET) {
                j2 = this.liveMinOffsetMs;
            } else {
                j2 = mediaItem.liveConfiguration.minOffsetMs;
            }
            MediaItem.Builder liveMinOffsetMs = liveMaxPlaybackSpeed.setLiveMinOffsetMs(j2);
            if (mediaItem.liveConfiguration.maxOffsetMs == C.TIME_UNSET) {
                j3 = this.liveMaxOffsetMs;
            } else {
                j3 = mediaItem.liveConfiguration.maxOffsetMs;
            }
            mediaItem = liveMinOffsetMs.setLiveMaxOffsetMs(j3).build();
        }
        MediaSource mediaSourceCreateMediaSource = mediaSourceFactory.createMediaSource(mediaItem);
        List<MediaItem.Subtitle> list = ((MediaItem.PlaybackProperties) Util.castNonNull(mediaItem.playbackProperties)).subtitles;
        if (!list.isEmpty()) {
            MediaSource[] mediaSourceArr = new MediaSource[list.size() + 1];
            int i = 0;
            mediaSourceArr[0] = mediaSourceCreateMediaSource;
            SingleSampleMediaSource.Factory loadErrorHandlingPolicy = new SingleSampleMediaSource.Factory(this.dataSourceFactory).setLoadErrorHandlingPolicy(this.loadErrorHandlingPolicy);
            while (i < list.size()) {
                int i2 = i + 1;
                mediaSourceArr[i2] = loadErrorHandlingPolicy.createMediaSource(list.get(i), C.TIME_UNSET);
                i = i2;
            }
            mediaSourceCreateMediaSource = new MergingMediaSource(mediaSourceArr);
        }
        return maybeWrapWithAdsMediaSource(mediaItem, maybeClipMediaSource(mediaItem, mediaSourceCreateMediaSource));
    }

    private static MediaSource maybeClipMediaSource(MediaItem mediaItem, MediaSource mediaSource) {
        return (mediaItem.clippingProperties.startPositionMs == 0 && mediaItem.clippingProperties.endPositionMs == Long.MIN_VALUE && !mediaItem.clippingProperties.relativeToDefaultPosition) ? mediaSource : new ClippingMediaSource(mediaSource, C.msToUs(mediaItem.clippingProperties.startPositionMs), C.msToUs(mediaItem.clippingProperties.endPositionMs), !mediaItem.clippingProperties.startsAtKeyFrame, mediaItem.clippingProperties.relativeToLiveWindow, mediaItem.clippingProperties.relativeToDefaultPosition);
    }

    private MediaSource maybeWrapWithAdsMediaSource(MediaItem mediaItem, MediaSource mediaSource) {
        Object objOf;
        Assertions.checkNotNull(mediaItem.playbackProperties);
        MediaItem.AdsConfiguration adsConfiguration = mediaItem.playbackProperties.adsConfiguration;
        if (adsConfiguration == null) {
            return mediaSource;
        }
        AdsLoaderProvider adsLoaderProvider = this.adsLoaderProvider;
        AdViewProvider adViewProvider = this.adViewProvider;
        if (adsLoaderProvider == null || adViewProvider == null) {
            Log.w(TAG, "Playing media without ads. Configure ad support by calling setAdsLoaderProvider and setAdViewProvider.");
            return mediaSource;
        }
        AdsLoader adsLoader = adsLoaderProvider.getAdsLoader(adsConfiguration);
        if (adsLoader == null) {
            Log.w(TAG, "Playing media without ads, as no AdsLoader was provided.");
            return mediaSource;
        }
        DataSpec dataSpec = new DataSpec(adsConfiguration.adTagUri);
        if (adsConfiguration.adsId != null) {
            objOf = adsConfiguration.adsId;
        } else {
            objOf = ImmutableList.of((Uri) mediaItem.mediaId, mediaItem.playbackProperties.uri, adsConfiguration.adTagUri);
        }
        return new AdsMediaSource(mediaSource, dataSpec, objOf, this, adsLoader, adViewProvider);
    }

    private static SparseArray<MediaSourceFactory> loadDelegates(DataSource.Factory dataSourceFactory, ExtractorsFactory extractorsFactory) {
        SparseArray<MediaSourceFactory> sparseArray = new SparseArray<>();
        try {
            sparseArray.put(0, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.dash.DashMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(DataSource.Factory.class).newInstance(dataSourceFactory));
        } catch (Exception unused) {
        }
        try {
            sparseArray.put(1, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(DataSource.Factory.class).newInstance(dataSourceFactory));
        } catch (Exception unused2) {
        }
        try {
            sparseArray.put(2, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(DataSource.Factory.class).newInstance(dataSourceFactory));
        } catch (Exception unused3) {
        }
        try {
            sparseArray.put(3, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.rtsp.RtspMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (Exception unused4) {
        }
        sparseArray.put(4, new ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory));
        return sparseArray;
    }
}
