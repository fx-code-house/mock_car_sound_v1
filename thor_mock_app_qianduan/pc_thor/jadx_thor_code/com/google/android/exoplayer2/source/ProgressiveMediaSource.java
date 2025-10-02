package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.ProgressiveMediaPeriod;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class ProgressiveMediaSource extends BaseMediaSource implements ProgressiveMediaPeriod.Listener {
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final int continueLoadingCheckIntervalBytes;
    private final DataSource.Factory dataSourceFactory;
    private final DrmSessionManager drmSessionManager;
    private final LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy;
    private final MediaItem mediaItem;
    private final MediaItem.PlaybackProperties playbackProperties;
    private final ProgressiveMediaExtractor.Factory progressiveMediaExtractorFactory;
    private long timelineDurationUs;
    private boolean timelineIsLive;
    private boolean timelineIsPlaceholder;
    private boolean timelineIsSeekable;
    private TransferListener transferListener;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    public static final class Factory implements MediaSourceFactory {
        private int continueLoadingCheckIntervalBytes;
        private String customCacheKey;
        private final DataSource.Factory dataSourceFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private ProgressiveMediaExtractor.Factory progressiveMediaExtractorFactory;
        private Object tag;
        private boolean usingCustomDrmSessionManagerProvider;

        static /* synthetic */ DrmSessionManager lambda$setDrmSessionManager$2(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{4};
        }

        public Factory(DataSource.Factory dataSourceFactory) {
            this(dataSourceFactory, new DefaultExtractorsFactory());
        }

        public Factory(DataSource.Factory dataSourceFactory, final ExtractorsFactory extractorsFactory) {
            this(dataSourceFactory, new ProgressiveMediaExtractor.Factory() { // from class: com.google.android.exoplayer2.source.ProgressiveMediaSource$Factory$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
                public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
                    return ProgressiveMediaSource.Factory.lambda$new$0(extractorsFactory);
                }
            });
        }

        static /* synthetic */ ProgressiveMediaExtractor lambda$new$0(ExtractorsFactory extractorsFactory) {
            return new BundledExtractorsAdapter(extractorsFactory);
        }

        public Factory(DataSource.Factory dataSourceFactory, ProgressiveMediaExtractor.Factory progressiveMediaExtractorFactory) {
            this.dataSourceFactory = dataSourceFactory;
            this.progressiveMediaExtractorFactory = progressiveMediaExtractorFactory;
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.continueLoadingCheckIntervalBytes = 1048576;
        }

        @Deprecated
        public Factory setExtractorsFactory(final ExtractorsFactory extractorsFactory) {
            this.progressiveMediaExtractorFactory = new ProgressiveMediaExtractor.Factory() { // from class: com.google.android.exoplayer2.source.ProgressiveMediaSource$Factory$$ExternalSyntheticLambda2
                @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
                public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
                    return ProgressiveMediaSource.Factory.lambda$setExtractorsFactory$1(extractorsFactory);
                }
            };
            return this;
        }

        static /* synthetic */ ProgressiveMediaExtractor lambda$setExtractorsFactory$1(ExtractorsFactory extractorsFactory) {
            if (extractorsFactory == null) {
                extractorsFactory = new DefaultExtractorsFactory();
            }
            return new BundledExtractorsAdapter(extractorsFactory);
        }

        @Deprecated
        public Factory setCustomCacheKey(String customCacheKey) {
            this.customCacheKey = customCacheKey;
            return this;
        }

        @Deprecated
        public Factory setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setContinueLoadingCheckIntervalBytes(int continueLoadingCheckIntervalBytes) {
            this.continueLoadingCheckIntervalBytes = continueLoadingCheckIntervalBytes;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
            if (drmSessionManagerProvider != null) {
                this.drmSessionManagerProvider = drmSessionManagerProvider;
                this.usingCustomDrmSessionManagerProvider = true;
            } else {
                this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
                this.usingCustomDrmSessionManagerProvider = false;
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManager(final DrmSessionManager drmSessionManager) {
            if (drmSessionManager == null) {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) null);
            } else {
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.ProgressiveMediaSource$Factory$$ExternalSyntheticLambda1
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        return ProgressiveMediaSource.Factory.lambda$setDrmSessionManager$2(drmSessionManager, mediaItem);
                    }
                });
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmHttpDataSourceFactory(HttpDataSource.Factory drmHttpDataSourceFactory) {
            if (!this.usingCustomDrmSessionManagerProvider) {
                ((DefaultDrmSessionManagerProvider) this.drmSessionManagerProvider).setDrmHttpDataSourceFactory(drmHttpDataSourceFactory);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmUserAgent(String userAgent) {
            if (!this.usingCustomDrmSessionManagerProvider) {
                ((DefaultDrmSessionManagerProvider) this.drmSessionManagerProvider).setDrmUserAgent(userAgent);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public ProgressiveMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public ProgressiveMediaSource createMediaSource(MediaItem mediaItem) {
            Assertions.checkNotNull(mediaItem.playbackProperties);
            boolean z = mediaItem.playbackProperties.tag == null && this.tag != null;
            boolean z2 = mediaItem.playbackProperties.customCacheKey == null && this.customCacheKey != null;
            if (z && z2) {
                mediaItem = mediaItem.buildUpon().setTag(this.tag).setCustomCacheKey(this.customCacheKey).build();
            } else if (z) {
                mediaItem = mediaItem.buildUpon().setTag(this.tag).build();
            } else if (z2) {
                mediaItem = mediaItem.buildUpon().setCustomCacheKey(this.customCacheKey).build();
            }
            MediaItem mediaItem2 = mediaItem;
            return new ProgressiveMediaSource(mediaItem2, this.dataSourceFactory, this.progressiveMediaExtractorFactory, this.drmSessionManagerProvider.get(mediaItem2), this.loadErrorHandlingPolicy, this.continueLoadingCheckIntervalBytes);
        }
    }

    private ProgressiveMediaSource(MediaItem mediaItem, DataSource.Factory dataSourceFactory, ProgressiveMediaExtractor.Factory progressiveMediaExtractorFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy, int continueLoadingCheckIntervalBytes) {
        this.playbackProperties = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.mediaItem = mediaItem;
        this.dataSourceFactory = dataSourceFactory;
        this.progressiveMediaExtractorFactory = progressiveMediaExtractorFactory;
        this.drmSessionManager = drmSessionManager;
        this.loadableLoadErrorHandlingPolicy = loadableLoadErrorHandlingPolicy;
        this.continueLoadingCheckIntervalBytes = continueLoadingCheckIntervalBytes;
        this.timelineIsPlaceholder = true;
        this.timelineDurationUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        this.transferListener = mediaTransferListener;
        this.drmSessionManager.prepare();
        notifySourceInfoRefreshed();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener = this.transferListener;
        if (transferListener != null) {
            dataSourceCreateDataSource.addTransferListener(transferListener);
        }
        return new ProgressiveMediaPeriod(this.playbackProperties.uri, dataSourceCreateDataSource, this.progressiveMediaExtractorFactory.createProgressiveMediaExtractor(), this.drmSessionManager, createDrmEventDispatcher(id), this.loadableLoadErrorHandlingPolicy, createEventDispatcher(id), this, allocator, this.playbackProperties.customCacheKey, this.continueLoadingCheckIntervalBytes);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((ProgressiveMediaPeriod) mediaPeriod).release();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.drmSessionManager.release();
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaPeriod.Listener
    public void onSourceInfoRefreshed(long durationUs, boolean isSeekable, boolean isLive) {
        if (durationUs == C.TIME_UNSET) {
            durationUs = this.timelineDurationUs;
        }
        if (!this.timelineIsPlaceholder && this.timelineDurationUs == durationUs && this.timelineIsSeekable == isSeekable && this.timelineIsLive == isLive) {
            return;
        }
        this.timelineDurationUs = durationUs;
        this.timelineIsSeekable = isSeekable;
        this.timelineIsLive = isLive;
        this.timelineIsPlaceholder = false;
        notifySourceInfoRefreshed();
    }

    private void notifySourceInfoRefreshed() {
        Timeline singlePeriodTimeline = new SinglePeriodTimeline(this.timelineDurationUs, this.timelineIsSeekable, false, this.timelineIsLive, (Object) null, this.mediaItem);
        if (this.timelineIsPlaceholder) {
            singlePeriodTimeline = new ForwardingTimeline(this, singlePeriodTimeline) { // from class: com.google.android.exoplayer2.source.ProgressiveMediaSource.1
                @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
                public Timeline.Window getWindow(int windowIndex, Timeline.Window window, long defaultPositionProjectionUs) {
                    super.getWindow(windowIndex, window, defaultPositionProjectionUs);
                    window.isPlaceholder = true;
                    return window;
                }

                @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
                public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
                    super.getPeriod(periodIndex, period, setIds);
                    period.isPlaceholder = true;
                    return period;
                }
            };
        }
        refreshSourceInfo(singlePeriodTimeline);
    }
}
