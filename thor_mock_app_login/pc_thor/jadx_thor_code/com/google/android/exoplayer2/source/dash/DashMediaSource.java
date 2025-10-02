package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.Timeline;
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
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;
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
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.SntpClient;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import com.google.common.math.LongMath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class DashMediaSource extends BaseMediaSource {
    public static final long DEFAULT_FALLBACK_TARGET_LIVE_OFFSET_MS = 30000;

    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    public static final String DEFAULT_MEDIA_ID = "DashMediaSource";
    private static final long DEFAULT_NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final String TAG = "DashMediaSource";
    private final BaseUrlExclusionList baseUrlExclusionList;
    private final DashChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private DataSource dataSource;
    private final DrmSessionManager drmSessionManager;
    private long elapsedRealtimeOffsetMs;
    private long expiredManifestPublishTimeUs;
    private final long fallbackTargetLiveOffsetMs;
    private int firstPeriodId;
    private Handler handler;
    private Uri initialManifestUri;
    private MediaItem.LiveConfiguration liveConfiguration;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private Loader loader;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final DataSource.Factory manifestDataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher manifestEventDispatcher;
    private IOException manifestFatalError;
    private long manifestLoadEndTimestampMs;
    private final LoaderErrorThrower manifestLoadErrorThrower;
    private boolean manifestLoadPending;
    private long manifestLoadStartTimestampMs;
    private final ParsingLoadable.Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    private final MediaItem mediaItem;
    private TransferListener mediaTransferListener;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private int staleManifestReloadAttempt;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    public static final class Factory implements MediaSourceFactory {
        private final DashChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private long fallbackTargetLiveOffsetMs;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private final DataSource.Factory manifestDataSourceFactory;
        private ParsingLoadable.Parser<? extends DashManifest> manifestParser;
        private List<StreamKey> streamKeys;
        private Object tag;
        private long targetLiveOffsetOverrideMs;
        private boolean usingCustomDrmSessionManagerProvider;

        static /* synthetic */ DrmSessionManager lambda$setDrmSessionManager$0(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{0};
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(List streamKeys) {
            return setStreamKeys((List<StreamKey>) streamKeys);
        }

        public Factory(DataSource.Factory dataSourceFactory) {
            this(new DefaultDashChunkSource.Factory(dataSourceFactory), dataSourceFactory);
        }

        public Factory(DashChunkSource.Factory chunkSourceFactory, DataSource.Factory manifestDataSourceFactory) {
            this.chunkSourceFactory = (DashChunkSource.Factory) Assertions.checkNotNull(chunkSourceFactory);
            this.manifestDataSourceFactory = manifestDataSourceFactory;
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.targetLiveOffsetOverrideMs = C.TIME_UNSET;
            this.fallbackTargetLiveOffsetMs = 30000L;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            this.streamKeys = Collections.emptyList();
        }

        @Deprecated
        public Factory setTag(Object tag) {
            this.tag = tag;
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
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource$Factory$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        return DashMediaSource.Factory.lambda$setDrmSessionManager$0(drmSessionManager, mediaItem);
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
        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        @Deprecated
        public Factory setLivePresentationDelayMs(long livePresentationDelayMs, boolean overridesManifest) {
            this.targetLiveOffsetOverrideMs = overridesManifest ? livePresentationDelayMs : C.TIME_UNSET;
            if (!overridesManifest) {
                setFallbackTargetLiveOffsetMs(livePresentationDelayMs);
            }
            return this;
        }

        public Factory setFallbackTargetLiveOffsetMs(long fallbackTargetLiveOffsetMs) {
            this.fallbackTargetLiveOffsetMs = fallbackTargetLiveOffsetMs;
            return this;
        }

        public Factory setManifestParser(ParsingLoadable.Parser<? extends DashManifest> manifestParser) {
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

        public DashMediaSource createMediaSource(DashManifest manifest) {
            return createMediaSource(manifest, new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId("DashMediaSource").setMimeType(MimeTypes.APPLICATION_MPD).setStreamKeys(this.streamKeys).setTag(this.tag).build());
        }

        public DashMediaSource createMediaSource(DashManifest manifest, MediaItem mediaItem) {
            List<StreamKey> list;
            long j;
            DashManifest dashManifestCopy = manifest;
            Assertions.checkArgument(!dashManifestCopy.dynamic);
            if (mediaItem.playbackProperties != null && !mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = mediaItem.playbackProperties.streamKeys;
            } else {
                list = this.streamKeys;
            }
            if (!list.isEmpty()) {
                dashManifestCopy = dashManifestCopy.copy(list);
            }
            DashManifest dashManifest = dashManifestCopy;
            boolean z = mediaItem.playbackProperties != null;
            boolean z2 = z && mediaItem.playbackProperties.tag != null;
            boolean z3 = mediaItem.liveConfiguration.targetOffsetMs != C.TIME_UNSET;
            MediaItem.Builder tag = mediaItem.buildUpon().setMimeType(MimeTypes.APPLICATION_MPD).setUri(z ? mediaItem.playbackProperties.uri : Uri.EMPTY).setTag(z2 ? mediaItem.playbackProperties.tag : this.tag);
            if (z3) {
                j = mediaItem.liveConfiguration.targetOffsetMs;
            } else {
                j = this.targetLiveOffsetOverrideMs;
            }
            MediaItem mediaItemBuild = tag.setLiveTargetOffsetMs(j).setStreamKeys(list).build();
            return new DashMediaSource(mediaItemBuild, dashManifest, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManagerProvider.get(mediaItemBuild), this.loadErrorHandlingPolicy, this.fallbackTargetLiveOffsetMs);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public DashMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_MPD).setTag(this.tag).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public DashMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            MediaItem mediaItemBuild = mediaItem;
            Assertions.checkNotNull(mediaItemBuild.playbackProperties);
            ParsingLoadable.Parser dashManifestParser = this.manifestParser;
            if (dashManifestParser == null) {
                dashManifestParser = new DashManifestParser();
            }
            if (mediaItemBuild.playbackProperties.streamKeys.isEmpty()) {
                list = this.streamKeys;
            } else {
                list = mediaItemBuild.playbackProperties.streamKeys;
            }
            ParsingLoadable.Parser filteringManifestParser = !list.isEmpty() ? new FilteringManifestParser(dashManifestParser, list) : dashManifestParser;
            boolean z = mediaItemBuild.playbackProperties.tag == null && this.tag != null;
            boolean z2 = mediaItemBuild.playbackProperties.streamKeys.isEmpty() && !list.isEmpty();
            boolean z3 = mediaItemBuild.liveConfiguration.targetOffsetMs == C.TIME_UNSET && this.targetLiveOffsetOverrideMs != C.TIME_UNSET;
            if (z || z2 || z3) {
                MediaItem.Builder builderBuildUpon = mediaItem.buildUpon();
                if (z) {
                    builderBuildUpon.setTag(this.tag);
                }
                if (z2) {
                    builderBuildUpon.setStreamKeys(list);
                }
                if (z3) {
                    builderBuildUpon.setLiveTargetOffsetMs(this.targetLiveOffsetOverrideMs);
                }
                mediaItemBuild = builderBuildUpon.build();
            }
            MediaItem mediaItem2 = mediaItemBuild;
            return new DashMediaSource(mediaItem2, null, this.manifestDataSourceFactory, filteringManifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManagerProvider.get(mediaItem2), this.loadErrorHandlingPolicy, this.fallbackTargetLiveOffsetMs);
        }
    }

    private DashMediaSource(MediaItem mediaItem, DashManifest manifest, DataSource.Factory manifestDataSourceFactory, ParsingLoadable.Parser<? extends DashManifest> manifestParser, DashChunkSource.Factory chunkSourceFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long fallbackTargetLiveOffsetMs) {
        this.mediaItem = mediaItem;
        this.liveConfiguration = mediaItem.liveConfiguration;
        this.manifestUri = ((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)).uri;
        this.initialManifestUri = mediaItem.playbackProperties.uri;
        this.manifest = manifest;
        this.manifestDataSourceFactory = manifestDataSourceFactory;
        this.manifestParser = manifestParser;
        this.chunkSourceFactory = chunkSourceFactory;
        this.drmSessionManager = drmSessionManager;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.fallbackTargetLiveOffsetMs = fallbackTargetLiveOffsetMs;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.baseUrlExclusionList = new BaseUrlExclusionList();
        boolean z = manifest != null;
        this.sideloadedManifest = z;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray<>();
        this.playerEmsgCallback = new DefaultPlayerEmsgCallback();
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.elapsedRealtimeOffsetMs = C.TIME_UNSET;
        if (z) {
            Assertions.checkState(true ^ manifest.dynamic);
            this.manifestCallback = null;
            this.refreshManifestRunnable = null;
            this.simulateManifestRefreshRunnable = null;
            this.manifestLoadErrorThrower = new LoaderErrorThrower.Dummy();
            return;
        }
        this.manifestCallback = new ManifestCallback();
        this.manifestLoadErrorThrower = new ManifestLoadErrorThrower();
        this.refreshManifestRunnable = new Runnable() { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startLoadingManifest();
            }
        };
        this.simulateManifestRefreshRunnable = new Runnable() { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m189x86b5dcdd();
            }
        };
    }

    /* renamed from: lambda$new$0$com-google-android-exoplayer2-source-dash-DashMediaSource, reason: not valid java name */
    /* synthetic */ void m189x86b5dcdd() {
        processManifest(false);
    }

    public void replaceManifestUri(Uri manifestUri) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = manifestUri;
            this.initialManifestUri = manifestUri;
        }
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
            processManifest(false);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("DashMediaSource");
        this.handler = Util.createHandlerForCurrentLooper();
        startLoadingManifest();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoadErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        int iIntValue = ((Integer) id.periodUid).intValue() - this.firstPeriodId;
        MediaSourceEventListener.EventDispatcher eventDispatcherCreateEventDispatcher = createEventDispatcher(id, this.manifest.getPeriod(iIntValue).startMs);
        DashMediaPeriod dashMediaPeriod = new DashMediaPeriod(iIntValue + this.firstPeriodId, this.manifest, this.baseUrlExclusionList, iIntValue, this.chunkSourceFactory, this.mediaTransferListener, this.drmSessionManager, createDrmEventDispatcher(id), this.loadErrorHandlingPolicy, eventDispatcherCreateEventDispatcher, this.elapsedRealtimeOffsetMs, this.manifestLoadErrorThrower, allocator, this.compositeSequenceableLoaderFactory, this.playerEmsgCallback);
        this.periodsById.put(dashMediaPeriod.id, dashMediaPeriod);
        return dashMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.id);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.manifestLoadPending = false;
        this.dataSource = null;
        Loader loader = this.loader;
        if (loader != null) {
            loader.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestampMs = 0L;
        this.manifestLoadEndTimestampMs = 0L;
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestUri = this.initialManifestUri;
        this.manifestFatalError = null;
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = C.TIME_UNSET;
        this.staleManifestReloadAttempt = 0;
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.firstPeriodId = 0;
        this.periodsById.clear();
        this.baseUrlExclusionList.reset();
        this.drmSessionManager.release();
    }

    void onDashManifestRefreshRequested() {
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        startLoadingManifest();
    }

    void onDashManifestPublishTimeExpired(long expiredManifestPublishTimeUs) {
        long j = this.expiredManifestPublishTimeUs;
        if (j == C.TIME_UNSET || j < expiredManifestPublishTimeUs) {
            this.expiredManifestPublishTimeUs = expiredManifestPublishTimeUs;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void onManifestLoadCompleted(com.google.android.exoplayer2.upstream.ParsingLoadable<com.google.android.exoplayer2.source.dash.manifest.DashManifest> r19, long r20, long r22) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.DashMediaSource.onManifestLoadCompleted(com.google.android.exoplayer2.upstream.ParsingLoadable, long, long):void");
    }

    Loader.LoadErrorAction onManifestLoadError(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
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

    void onUtcTimestampLoadCompleted(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.manifestEventDispatcher.loadCompleted(loadEventInfo, loadable.type);
        onUtcTimestampResolved(loadable.getResult().longValue() - elapsedRealtimeMs);
    }

    Loader.LoadErrorAction onUtcTimestampLoadError(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error) {
        this.manifestEventDispatcher.loadError(new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded()), loadable.type, error, true);
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        onUtcTimestampResolutionError(error);
        return Loader.DONT_RETRY;
    }

    void onLoadCanceled(ParsingLoadable<?> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.manifestEventDispatcher.loadCanceled(loadEventInfo, loadable.type);
    }

    private void resolveUtcTimingElement(UtcTimingElement timingElement) {
        String str = timingElement.schemeIdUri;
        if (Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
            resolveUtcTimingElementDirect(timingElement);
            return;
        }
        if (Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
            resolveUtcTimingElementHttp(timingElement, new Iso8601Parser());
            return;
        }
        if (Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            resolveUtcTimingElementHttp(timingElement, new XsDateTimeParser());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2012")) {
            loadNtpTimeOffset();
        } else {
            onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement timingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(timingElement.value) - this.manifestLoadEndTimestampMs);
        } catch (ParserException e) {
            onUtcTimestampResolutionError(e);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement timingElement, ParsingLoadable.Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(timingElement.value), 5, parser), new UtcTimestampCallback(), 1);
    }

    private void loadNtpTimeOffset() {
        SntpClient.initialize(this.loader, new SntpClient.InitializationCallback() { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource.1
            @Override // com.google.android.exoplayer2.util.SntpClient.InitializationCallback
            public void onInitialized() {
                DashMediaSource.this.onUtcTimestampResolved(SntpClient.getElapsedRealtimeOffsetMs());
            }

            @Override // com.google.android.exoplayer2.util.SntpClient.InitializationCallback
            public void onInitializationFailed(IOException error) {
                DashMediaSource.this.onUtcTimestampResolutionError(error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUtcTimestampResolved(long elapsedRealtimeOffsetMs) {
        this.elapsedRealtimeOffsetMs = elapsedRealtimeOffsetMs;
        processManifest(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUtcTimestampResolutionError(IOException error) {
        Log.e("DashMediaSource", "Failed to resolve time offset.", error);
        processManifest(true);
    }

    private void processManifest(boolean scheduleRefresh) {
        long j;
        long j2;
        for (int i = 0; i < this.periodsById.size(); i++) {
            int iKeyAt = this.periodsById.keyAt(i);
            if (iKeyAt >= this.firstPeriodId) {
                this.periodsById.valueAt(i).updateManifest(this.manifest, iKeyAt - this.firstPeriodId);
            }
        }
        Period period = this.manifest.getPeriod(0);
        int periodCount = this.manifest.getPeriodCount() - 1;
        Period period2 = this.manifest.getPeriod(periodCount);
        long periodDurationUs = this.manifest.getPeriodDurationUs(periodCount);
        long jMsToUs = C.msToUs(Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs));
        long availableStartTimeInManifestUs = getAvailableStartTimeInManifestUs(period, this.manifest.getPeriodDurationUs(0), jMsToUs);
        long availableEndTimeInManifestUs = getAvailableEndTimeInManifestUs(period2, periodDurationUs, jMsToUs);
        boolean z = this.manifest.dynamic && !isIndexExplicit(period2);
        if (z && this.manifest.timeShiftBufferDepthMs != C.TIME_UNSET) {
            availableStartTimeInManifestUs = Math.max(availableStartTimeInManifestUs, availableEndTimeInManifestUs - C.msToUs(this.manifest.timeShiftBufferDepthMs));
        }
        long j3 = availableEndTimeInManifestUs - availableStartTimeInManifestUs;
        if (this.manifest.dynamic) {
            Assertions.checkState(this.manifest.availabilityStartTimeMs != C.TIME_UNSET);
            long jMsToUs2 = (jMsToUs - C.msToUs(this.manifest.availabilityStartTimeMs)) - availableStartTimeInManifestUs;
            updateMediaItemLiveConfiguration(jMsToUs2, j3);
            long jUsToMs = this.manifest.availabilityStartTimeMs + C.usToMs(availableStartTimeInManifestUs);
            long jMsToUs3 = jMsToUs2 - C.msToUs(this.liveConfiguration.targetOffsetMs);
            j = jUsToMs;
            long jMin = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j3 / 2);
            j2 = jMsToUs3 < jMin ? jMin : jMsToUs3;
        } else {
            j = C.TIME_UNSET;
            j2 = 0;
        }
        long jMsToUs4 = availableStartTimeInManifestUs - C.msToUs(period.startMs);
        long j4 = this.manifest.availabilityStartTimeMs;
        long j5 = this.elapsedRealtimeOffsetMs;
        int i2 = this.firstPeriodId;
        DashManifest dashManifest = this.manifest;
        refreshSourceInfo(new DashTimeline(j4, j, j5, i2, jMsToUs4, j3, j2, dashManifest, this.mediaItem, dashManifest.dynamic ? this.liveConfiguration : null));
        if (this.sideloadedManifest) {
            return;
        }
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        if (z) {
            this.handler.postDelayed(this.simulateManifestRefreshRunnable, getIntervalUntilNextManifestRefreshMs(this.manifest, Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs)));
        }
        if (this.manifestLoadPending) {
            startLoadingManifest();
            return;
        }
        if (scheduleRefresh && this.manifest.dynamic && this.manifest.minUpdatePeriodMs != C.TIME_UNSET) {
            long j6 = this.manifest.minUpdatePeriodMs;
            if (j6 == 0) {
                j6 = 5000;
            }
            scheduleManifestRefresh(Math.max(0L, (this.manifestLoadStartTimestampMs + j6) - SystemClock.elapsedRealtime()));
        }
    }

    private void updateMediaItemLiveConfiguration(long nowInWindowUs, long windowDurationUs) {
        long jUsToMs;
        long jUsToMs2;
        long jConstrainValue;
        float f;
        if (this.mediaItem.liveConfiguration.maxOffsetMs != C.TIME_UNSET) {
            jUsToMs = this.mediaItem.liveConfiguration.maxOffsetMs;
        } else if (this.manifest.serviceDescription != null && this.manifest.serviceDescription.maxOffsetMs != C.TIME_UNSET) {
            jUsToMs = this.manifest.serviceDescription.maxOffsetMs;
        } else {
            jUsToMs = C.usToMs(nowInWindowUs);
        }
        if (this.mediaItem.liveConfiguration.minOffsetMs != C.TIME_UNSET) {
            jUsToMs2 = this.mediaItem.liveConfiguration.minOffsetMs;
        } else if (this.manifest.serviceDescription != null && this.manifest.serviceDescription.minOffsetMs != C.TIME_UNSET) {
            jUsToMs2 = this.manifest.serviceDescription.minOffsetMs;
        } else {
            jUsToMs2 = C.usToMs(nowInWindowUs - windowDurationUs);
            if (jUsToMs2 < 0 && jUsToMs > 0) {
                jUsToMs2 = 0;
            }
            if (this.manifest.minBufferTimeMs != C.TIME_UNSET) {
                jUsToMs2 = Math.min(jUsToMs2 + this.manifest.minBufferTimeMs, jUsToMs);
            }
        }
        long j = jUsToMs2;
        if (this.liveConfiguration.targetOffsetMs != C.TIME_UNSET) {
            jConstrainValue = this.liveConfiguration.targetOffsetMs;
        } else if (this.manifest.serviceDescription != null && this.manifest.serviceDescription.targetOffsetMs != C.TIME_UNSET) {
            jConstrainValue = this.manifest.serviceDescription.targetOffsetMs;
        } else if (this.manifest.suggestedPresentationDelayMs != C.TIME_UNSET) {
            jConstrainValue = this.manifest.suggestedPresentationDelayMs;
        } else {
            jConstrainValue = this.fallbackTargetLiveOffsetMs;
        }
        if (jConstrainValue < j) {
            jConstrainValue = j;
        }
        if (jConstrainValue > jUsToMs) {
            jConstrainValue = Util.constrainValue(C.usToMs(nowInWindowUs - Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, windowDurationUs / 2)), j, jUsToMs);
        }
        long j2 = jConstrainValue;
        float f2 = -3.4028235E38f;
        if (this.mediaItem.liveConfiguration.minPlaybackSpeed != -3.4028235E38f) {
            f = this.mediaItem.liveConfiguration.minPlaybackSpeed;
        } else {
            f = this.manifest.serviceDescription != null ? this.manifest.serviceDescription.minPlaybackSpeed : -3.4028235E38f;
        }
        if (this.mediaItem.liveConfiguration.maxPlaybackSpeed != -3.4028235E38f) {
            f2 = this.mediaItem.liveConfiguration.maxPlaybackSpeed;
        } else if (this.manifest.serviceDescription != null) {
            f2 = this.manifest.serviceDescription.maxPlaybackSpeed;
        }
        this.liveConfiguration = new MediaItem.LiveConfiguration(j2, j, jUsToMs, f, f2);
    }

    private void scheduleManifestRefresh(long delayUntilNextLoadMs) {
        this.handler.postDelayed(this.refreshManifestRunnable, delayUntilNextLoadMs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLoadingManifest() {
        Uri uri;
        this.handler.removeCallbacks(this.refreshManifestRunnable);
        if (this.loader.hasFatalError()) {
            return;
        }
        if (this.loader.isLoading()) {
            this.manifestLoadPending = true;
            return;
        }
        synchronized (this.manifestUriLock) {
            uri = this.manifestUri;
        }
        this.manifestLoadPending = false;
        startLoading(new ParsingLoadable(this.dataSource, uri, 4, this.manifestParser), this.manifestCallback, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(4));
    }

    private long getManifestLoadRetryDelayMillis() {
        return Math.min((this.staleManifestReloadAttempt - 1) * 1000, 5000);
    }

    private <T> void startLoading(ParsingLoadable<T> loadable, Loader.Callback<ParsingLoadable<T>> callback, int minRetryCount) {
        this.manifestEventDispatcher.loadStarted(new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, this.loader.startLoading(loadable, callback, minRetryCount)), loadable.type);
    }

    private static long getIntervalUntilNextManifestRefreshMs(DashManifest manifest, long nowUnixTimeMs) {
        DashSegmentIndex index;
        int periodCount = manifest.getPeriodCount() - 1;
        Period period = manifest.getPeriod(periodCount);
        long jMsToUs = C.msToUs(period.startMs);
        long periodDurationUs = manifest.getPeriodDurationUs(periodCount);
        long jMsToUs2 = C.msToUs(nowUnixTimeMs);
        long jMsToUs3 = C.msToUs(manifest.availabilityStartTimeMs);
        long jMsToUs4 = C.msToUs(5000L);
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            List<Representation> list = period.adaptationSets.get(i).representations;
            if (!list.isEmpty() && (index = list.get(0).getIndex()) != null) {
                long nextSegmentAvailableTimeUs = ((jMsToUs3 + jMsToUs) + index.getNextSegmentAvailableTimeUs(periodDurationUs, jMsToUs2)) - jMsToUs2;
                if (nextSegmentAvailableTimeUs < jMsToUs4 - 100000 || (nextSegmentAvailableTimeUs > jMsToUs4 && nextSegmentAvailableTimeUs < jMsToUs4 + 100000)) {
                    jMsToUs4 = nextSegmentAvailableTimeUs;
                }
            }
        }
        return LongMath.divide(jMsToUs4, 1000L, RoundingMode.CEILING);
    }

    private static long getAvailableStartTimeInManifestUs(Period period, long periodDurationUs, long nowUnixTimeUs) {
        long jMsToUs = C.msToUs(period.startMs);
        boolean zHasVideoOrAudioAdaptationSets = hasVideoOrAudioAdaptationSets(period);
        long jMax = jMsToUs;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            if ((!zHasVideoOrAudioAdaptationSets || adaptationSet.type != 3) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null || index.getAvailableSegmentCount(periodDurationUs, nowUnixTimeUs) == 0) {
                    return jMsToUs;
                }
                jMax = Math.max(jMax, index.getTimeUs(index.getFirstAvailableSegmentNum(periodDurationUs, nowUnixTimeUs)) + jMsToUs);
            }
        }
        return jMax;
    }

    private static long getAvailableEndTimeInManifestUs(Period period, long periodDurationUs, long nowUnixTimeUs) {
        long jMsToUs = C.msToUs(period.startMs);
        boolean zHasVideoOrAudioAdaptationSets = hasVideoOrAudioAdaptationSets(period);
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            if ((!zHasVideoOrAudioAdaptationSets || adaptationSet.type != 3) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null) {
                    return jMsToUs + periodDurationUs;
                }
                long availableSegmentCount = index.getAvailableSegmentCount(periodDurationUs, nowUnixTimeUs);
                if (availableSegmentCount == 0) {
                    return jMsToUs;
                }
                long firstAvailableSegmentNum = (index.getFirstAvailableSegmentNum(periodDurationUs, nowUnixTimeUs) + availableSegmentCount) - 1;
                jMin = Math.min(jMin, index.getDurationUs(firstAvailableSegmentNum, periodDurationUs) + index.getTimeUs(firstAvailableSegmentNum) + jMsToUs);
            }
        }
        return jMin;
    }

    private static boolean isIndexExplicit(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            DashSegmentIndex index = period.adaptationSets.get(i).representations.get(0).getIndex();
            if (index == null || index.isExplicit()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasVideoOrAudioAdaptationSets(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            int i2 = period.adaptationSets.get(i).type;
            if (i2 == 1 || i2 == 2) {
                return true;
            }
        }
        return false;
    }

    private static final class DashTimeline extends Timeline {
        private final long elapsedRealtimeEpochOffsetMs;
        private final int firstPeriodId;
        private final MediaItem.LiveConfiguration liveConfiguration;
        private final DashManifest manifest;
        private final MediaItem mediaItem;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 1;
        }

        public DashTimeline(long presentationStartTimeMs, long windowStartTimeMs, long elapsedRealtimeEpochOffsetMs, int firstPeriodId, long offsetInFirstPeriodUs, long windowDurationUs, long windowDefaultStartPositionUs, DashManifest manifest, MediaItem mediaItem, MediaItem.LiveConfiguration liveConfiguration) {
            Assertions.checkState(manifest.dynamic == (liveConfiguration != null));
            this.presentationStartTimeMs = presentationStartTimeMs;
            this.windowStartTimeMs = windowStartTimeMs;
            this.elapsedRealtimeEpochOffsetMs = elapsedRealtimeEpochOffsetMs;
            this.firstPeriodId = firstPeriodId;
            this.offsetInFirstPeriodUs = offsetInFirstPeriodUs;
            this.windowDurationUs = windowDurationUs;
            this.windowDefaultStartPositionUs = windowDefaultStartPositionUs;
            this.manifest = manifest;
            this.mediaItem = mediaItem;
            this.liveConfiguration = liveConfiguration;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
            Assertions.checkIndex(periodIndex, 0, getPeriodCount());
            return period.set(setIds ? this.manifest.getPeriod(periodIndex).id : null, setIds ? Integer.valueOf(this.firstPeriodId + periodIndex) : null, 0, this.manifest.getPeriodDurationUs(periodIndex), C.msToUs(this.manifest.getPeriod(periodIndex).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int windowIndex, Timeline.Window window, long defaultPositionProjectionUs) {
            Assertions.checkIndex(windowIndex, 0, 1);
            long adjustedWindowDefaultStartPositionUs = getAdjustedWindowDefaultStartPositionUs(defaultPositionProjectionUs);
            Object obj = Timeline.Window.SINGLE_WINDOW_UID;
            MediaItem mediaItem = this.mediaItem;
            DashManifest dashManifest = this.manifest;
            return window.set(obj, mediaItem, dashManifest, this.presentationStartTimeMs, this.windowStartTimeMs, this.elapsedRealtimeEpochOffsetMs, true, isMovingLiveWindow(dashManifest), this.liveConfiguration, adjustedWindowDefaultStartPositionUs, this.windowDurationUs, 0, getPeriodCount() - 1, this.offsetInFirstPeriodUs);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object uid) {
            int iIntValue;
            if ((uid instanceof Integer) && (iIntValue = ((Integer) uid).intValue() - this.firstPeriodId) >= 0 && iIntValue < getPeriodCount()) {
                return iIntValue;
            }
            return -1;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long defaultPositionProjectionUs) {
            DashSegmentIndex index;
            long j = this.windowDefaultStartPositionUs;
            if (!isMovingLiveWindow(this.manifest)) {
                return j;
            }
            if (defaultPositionProjectionUs > 0) {
                j += defaultPositionProjectionUs;
                if (j > this.windowDurationUs) {
                    return C.TIME_UNSET;
                }
            }
            long j2 = this.offsetInFirstPeriodUs + j;
            long periodDurationUs = this.manifest.getPeriodDurationUs(0);
            int i = 0;
            while (i < this.manifest.getPeriodCount() - 1 && j2 >= periodDurationUs) {
                j2 -= periodDurationUs;
                i++;
                periodDurationUs = this.manifest.getPeriodDurationUs(i);
            }
            Period period = this.manifest.getPeriod(i);
            int adaptationSetIndex = period.getAdaptationSetIndex(2);
            return (adaptationSetIndex == -1 || (index = period.adaptationSets.get(adaptationSetIndex).representations.get(0).getIndex()) == null || index.getSegmentCount(periodDurationUs) == 0) ? j : (j + index.getTimeUs(index.getSegmentNum(j2, periodDurationUs))) - j2;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int periodIndex) {
            Assertions.checkIndex(periodIndex, 0, getPeriodCount());
            return Integer.valueOf(this.firstPeriodId + periodIndex);
        }

        private static boolean isMovingLiveWindow(DashManifest manifest) {
            return manifest.dynamic && manifest.minUpdatePeriodMs != C.TIME_UNSET && manifest.durationMs == C.TIME_UNSET;
        }
    }

    private final class DefaultPlayerEmsgCallback implements PlayerEmsgHandler.PlayerEmsgCallback {
        private DefaultPlayerEmsgCallback() {
        }

        @Override // com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.onDashManifestRefreshRequested();
        }

        @Override // com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestPublishTimeExpired(long expiredManifestPublishTimeUs) {
            DashMediaSource.this.onDashManifestPublishTimeExpired(expiredManifestPublishTimeUs);
        }
    }

    private final class ManifestCallback implements Loader.Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs) {
            DashMediaSource.this.onManifestLoadCompleted(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
            DashMediaSource.this.onLoadCanceled(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            return DashMediaSource.this.onManifestLoadError(loadable, elapsedRealtimeMs, loadDurationMs, error, errorCount);
        }
    }

    private final class UtcTimestampCallback implements Loader.Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
            DashMediaSource.this.onLoadCanceled(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            return DashMediaSource.this.onUtcTimestampLoadError(loadable, elapsedRealtimeMs, loadDurationMs, error);
        }
    }

    private static final class XsDateTimeParser implements ParsingLoadable.Parser<Long> {
        private XsDateTimeParser() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    static final class Iso8601Parser implements ParsingLoadable.Parser<Long> {
        private static final Pattern TIMESTAMP_WITH_TIMEZONE_PATTERN = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        Iso8601Parser() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
        public Long parse(Uri uri, InputStream inputStream) throws IOException, NumberFormatException {
            String line = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8)).readLine();
            try {
                Matcher matcher = TIMESTAMP_WITH_TIMEZONE_PATTERN.matcher(line);
                if (!matcher.matches()) {
                    String strValueOf = String.valueOf(line);
                    throw ParserException.createForMalformedManifest(strValueOf.length() != 0 ? "Couldn't parse timestamp: ".concat(strValueOf) : new String("Couldn't parse timestamp: "), null);
                }
                String strGroup = matcher.group(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                long time = simpleDateFormat.parse(strGroup).getTime();
                if (!"Z".equals(matcher.group(2))) {
                    long j = "+".equals(matcher.group(4)) ? 1L : -1L;
                    long j2 = Long.parseLong(matcher.group(5));
                    String strGroup2 = matcher.group(7);
                    time -= j * ((((j2 * 60) + (TextUtils.isEmpty(strGroup2) ? 0L : Long.parseLong(strGroup2))) * 60) * 1000);
                }
                return Long.valueOf(time);
            } catch (ParseException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }
    }

    final class ManifestLoadErrorThrower implements LoaderErrorThrower {
        ManifestLoadErrorThrower() {
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError() throws IOException {
            DashMediaSource.this.loader.maybeThrowError();
            maybeThrowManifestError();
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError(int minRetryCount) throws IOException {
            DashMediaSource.this.loader.maybeThrowError(minRetryCount);
            maybeThrowManifestError();
        }

        private void maybeThrowManifestError() throws IOException {
            if (DashMediaSource.this.manifestFatalError != null) {
                throw DashMediaSource.this.manifestFatalError;
            }
        }
    }
}
