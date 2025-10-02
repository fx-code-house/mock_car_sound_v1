package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class SsMediaSource extends BaseMediaSource implements Loader.Callback<ParsingLoadable<SsManifest>> {
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    private static final int MINIMUM_MANIFEST_REFRESH_PERIOD_MS = 5000;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private final SsChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final DrmSessionManager drmSessionManager;
    private final long livePresentationDelayMs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private SsManifest manifest;
    private DataSource manifestDataSource;
    private final DataSource.Factory manifestDataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher manifestEventDispatcher;
    private long manifestLoadStartTimestamp;
    private Loader manifestLoader;
    private LoaderErrorThrower manifestLoaderErrorThrower;
    private final ParsingLoadable.Parser<? extends SsManifest> manifestParser;
    private Handler manifestRefreshHandler;
    private final Uri manifestUri;
    private final MediaItem mediaItem;
    private final ArrayList<SsMediaPeriod> mediaPeriods;
    private TransferListener mediaTransferListener;
    private final MediaItem.PlaybackProperties playbackProperties;
    private final boolean sideloadedManifest;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.smoothstreaming");
    }

    public static final class Factory implements MediaSourceFactory {
        private final SsChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private long livePresentationDelayMs;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private final DataSource.Factory manifestDataSourceFactory;
        private ParsingLoadable.Parser<? extends SsManifest> manifestParser;
        private List<StreamKey> streamKeys;
        private Object tag;
        private boolean usingCustomDrmSessionManagerProvider;

        static /* synthetic */ DrmSessionManager lambda$setDrmSessionManager$0(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{1};
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(List streamKeys) {
            return setStreamKeys((List<StreamKey>) streamKeys);
        }

        public Factory(DataSource.Factory dataSourceFactory) {
            this(new DefaultSsChunkSource.Factory(dataSourceFactory), dataSourceFactory);
        }

        public Factory(SsChunkSource.Factory chunkSourceFactory, DataSource.Factory manifestDataSourceFactory) {
            this.chunkSourceFactory = (SsChunkSource.Factory) Assertions.checkNotNull(chunkSourceFactory);
            this.manifestDataSourceFactory = manifestDataSourceFactory;
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.livePresentationDelayMs = 30000L;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            this.streamKeys = Collections.emptyList();
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

        public Factory setLivePresentationDelayMs(long livePresentationDelayMs) {
            this.livePresentationDelayMs = livePresentationDelayMs;
            return this;
        }

        public Factory setManifestParser(ParsingLoadable.Parser<? extends SsManifest> manifestParser) {
            this.manifestParser = manifestParser;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            if (compositeSequenceableLoaderFactory == null) {
                compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            }
            this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
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
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        return SsMediaSource.Factory.lambda$setDrmSessionManager$0(drmSessionManager, mediaItem);
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
        public Factory setStreamKeys(List<StreamKey> streamKeys) {
            if (streamKeys == null) {
                streamKeys = Collections.emptyList();
            }
            this.streamKeys = streamKeys;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public SsMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).build());
        }

        public SsMediaSource createMediaSource(SsManifest manifest) {
            return createMediaSource(manifest, MediaItem.fromUri(Uri.EMPTY));
        }

        public SsMediaSource createMediaSource(SsManifest manifest, MediaItem mediaItem) {
            List<StreamKey> list;
            SsManifest ssManifestCopy = manifest;
            Assertions.checkArgument(!ssManifestCopy.isLive);
            if (mediaItem.playbackProperties != null && !mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = mediaItem.playbackProperties.streamKeys;
            } else {
                list = this.streamKeys;
            }
            if (!list.isEmpty()) {
                ssManifestCopy = ssManifestCopy.copy(list);
            }
            SsManifest ssManifest = ssManifestCopy;
            boolean z = mediaItem.playbackProperties != null;
            MediaItem mediaItemBuild = mediaItem.buildUpon().setMimeType(MimeTypes.APPLICATION_SS).setUri(z ? mediaItem.playbackProperties.uri : Uri.EMPTY).setTag(z && mediaItem.playbackProperties.tag != null ? mediaItem.playbackProperties.tag : this.tag).setStreamKeys(list).build();
            return new SsMediaSource(mediaItemBuild, ssManifest, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManagerProvider.get(mediaItemBuild), this.loadErrorHandlingPolicy, this.livePresentationDelayMs);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public SsMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            MediaItem mediaItemBuild = mediaItem;
            Assertions.checkNotNull(mediaItemBuild.playbackProperties);
            ParsingLoadable.Parser ssManifestParser = this.manifestParser;
            if (ssManifestParser == null) {
                ssManifestParser = new SsManifestParser();
            }
            if (!mediaItemBuild.playbackProperties.streamKeys.isEmpty()) {
                list = mediaItemBuild.playbackProperties.streamKeys;
            } else {
                list = this.streamKeys;
            }
            ParsingLoadable.Parser filteringManifestParser = !list.isEmpty() ? new FilteringManifestParser(ssManifestParser, list) : ssManifestParser;
            boolean z = mediaItemBuild.playbackProperties.tag == null && this.tag != null;
            boolean z2 = mediaItemBuild.playbackProperties.streamKeys.isEmpty() && !list.isEmpty();
            if (z && z2) {
                mediaItemBuild = mediaItem.buildUpon().setTag(this.tag).setStreamKeys(list).build();
            } else if (z) {
                mediaItemBuild = mediaItem.buildUpon().setTag(this.tag).build();
            } else if (z2) {
                mediaItemBuild = mediaItem.buildUpon().setStreamKeys(list).build();
            }
            MediaItem mediaItem2 = mediaItemBuild;
            return new SsMediaSource(mediaItem2, null, this.manifestDataSourceFactory, filteringManifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManagerProvider.get(mediaItem2), this.loadErrorHandlingPolicy, this.livePresentationDelayMs);
        }
    }

    private SsMediaSource(MediaItem mediaItem, SsManifest manifest, DataSource.Factory manifestDataSourceFactory, ParsingLoadable.Parser<? extends SsManifest> manifestParser, SsChunkSource.Factory chunkSourceFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long livePresentationDelayMs) {
        Assertions.checkState(manifest == null || !manifest.isLive);
        this.mediaItem = mediaItem;
        MediaItem.PlaybackProperties playbackProperties = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.playbackProperties = playbackProperties;
        this.manifest = manifest;
        this.manifestUri = playbackProperties.uri.equals(Uri.EMPTY) ? null : Util.fixSmoothStreamingIsmManifestUri(playbackProperties.uri);
        this.manifestDataSourceFactory = manifestDataSourceFactory;
        this.manifestParser = manifestParser;
        this.chunkSourceFactory = chunkSourceFactory;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.drmSessionManager = drmSessionManager;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.livePresentationDelayMs = livePresentationDelayMs;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.sideloadedManifest = manifest != null;
        this.mediaPeriods = new ArrayList<>();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        this.mediaTransferListener = mediaTransferListener;
        this.drmSessionManager.prepare();
        if (this.sideloadedManifest) {
            this.manifestLoaderErrorThrower = new LoaderErrorThrower.Dummy();
            processManifest();
            return;
        }
        this.manifestDataSource = this.manifestDataSourceFactory.createDataSource();
        Loader loader = new Loader("SsMediaSource");
        this.manifestLoader = loader;
        this.manifestLoaderErrorThrower = loader;
        this.manifestRefreshHandler = Util.createHandlerForCurrentLooper();
        startLoadingManifest();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        MediaSourceEventListener.EventDispatcher eventDispatcherCreateEventDispatcher = createEventDispatcher(id);
        SsMediaPeriod ssMediaPeriod = new SsMediaPeriod(this.manifest, this.chunkSourceFactory, this.mediaTransferListener, this.compositeSequenceableLoaderFactory, this.drmSessionManager, createDrmEventDispatcher(id), this.loadErrorHandlingPolicy, eventDispatcherCreateEventDispatcher, this.manifestLoaderErrorThrower, allocator);
        this.mediaPeriods.add(ssMediaPeriod);
        return ssMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((SsMediaPeriod) mediaPeriod).release();
        this.mediaPeriods.remove(mediaPeriod);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestDataSource = null;
        this.manifestLoadStartTimestamp = 0L;
        Loader loader = this.manifestLoader;
        if (loader != null) {
            loader.release();
            this.manifestLoader = null;
        }
        Handler handler = this.manifestRefreshHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.manifestRefreshHandler = null;
        }
        this.drmSessionManager.release();
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCompleted(ParsingLoadable<SsManifest> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.manifestEventDispatcher.loadCompleted(loadEventInfo, loadable.type);
        this.manifest = loadable.getResult();
        this.manifestLoadStartTimestamp = elapsedRealtimeMs - loadDurationMs;
        processManifest();
        scheduleManifestRefresh();
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCanceled(ParsingLoadable<SsManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.manifestEventDispatcher.loadCanceled(loadEventInfo, loadable.type);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public Loader.LoadErrorAction onLoadError(ParsingLoadable<SsManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        Loader.LoadErrorAction loadErrorActionCreateRetryAction;
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(loadable.type), error, errorCount));
        if (retryDelayMsFor == C.TIME_UNSET) {
            loadErrorActionCreateRetryAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorActionCreateRetryAction = Loader.createRetryAction(false, retryDelayMsFor);
        }
        boolean z = !loadErrorActionCreateRetryAction.isRetry();
        this.manifestEventDispatcher.loadError(loadEventInfo, loadable.type, error, z);
        if (z) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        }
        return loadErrorActionCreateRetryAction;
    }

    private void processManifest() {
        SinglePeriodTimeline singlePeriodTimeline;
        for (int i = 0; i < this.mediaPeriods.size(); i++) {
            this.mediaPeriods.get(i).updateManifest(this.manifest);
        }
        long jMax = Long.MIN_VALUE;
        long jMax2 = Long.MAX_VALUE;
        for (SsManifest.StreamElement streamElement : this.manifest.streamElements) {
            if (streamElement.chunkCount > 0) {
                jMax2 = Math.min(jMax2, streamElement.getStartTimeUs(0));
                jMax = Math.max(jMax, streamElement.getStartTimeUs(streamElement.chunkCount - 1) + streamElement.getChunkDurationUs(streamElement.chunkCount - 1));
            }
        }
        if (jMax2 == Long.MAX_VALUE) {
            singlePeriodTimeline = new SinglePeriodTimeline(this.manifest.isLive ? -9223372036854775807L : 0L, 0L, 0L, 0L, true, this.manifest.isLive, this.manifest.isLive, (Object) this.manifest, this.mediaItem);
        } else if (this.manifest.isLive) {
            if (this.manifest.dvrWindowLengthUs != C.TIME_UNSET && this.manifest.dvrWindowLengthUs > 0) {
                jMax2 = Math.max(jMax2, jMax - this.manifest.dvrWindowLengthUs);
            }
            long j = jMax2;
            long j2 = jMax - j;
            long jMsToUs = j2 - C.msToUs(this.livePresentationDelayMs);
            if (jMsToUs < MIN_LIVE_DEFAULT_START_POSITION_US) {
                jMsToUs = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j2 / 2);
            }
            singlePeriodTimeline = new SinglePeriodTimeline(C.TIME_UNSET, j2, j, jMsToUs, true, true, true, (Object) this.manifest, this.mediaItem);
        } else {
            long j3 = this.manifest.durationUs != C.TIME_UNSET ? this.manifest.durationUs : jMax - jMax2;
            singlePeriodTimeline = new SinglePeriodTimeline(jMax2 + j3, j3, jMax2, 0L, true, false, false, (Object) this.manifest, this.mediaItem);
        }
        refreshSourceInfo(singlePeriodTimeline);
    }

    private void scheduleManifestRefresh() {
        if (this.manifest.isLive) {
            this.manifestRefreshHandler.postDelayed(new Runnable() { // from class: com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.startLoadingManifest();
                }
            }, Math.max(0L, (this.manifestLoadStartTimestamp + 5000) - SystemClock.elapsedRealtime()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLoadingManifest() {
        if (this.manifestLoader.hasFatalError()) {
            return;
        }
        ParsingLoadable parsingLoadable = new ParsingLoadable(this.manifestDataSource, this.manifestUri, 4, this.manifestParser);
        this.manifestEventDispatcher.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.manifestLoader.startLoading(parsingLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type))), parsingLoadable.type);
    }
}
