package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class DefaultHlsPlaylistTracker implements HlsPlaylistTracker, Loader.Callback<ParsingLoadable<HlsPlaylist>> {
    public static final double DEFAULT_PLAYLIST_STUCK_TARGET_DURATION_COEFFICIENT = 3.5d;
    public static final HlsPlaylistTracker.Factory FACTORY = new HlsPlaylistTracker.Factory() { // from class: com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory
        public final HlsPlaylistTracker createTracker(HlsDataSourceFactory hlsDataSourceFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistParserFactory hlsPlaylistParserFactory) {
            return new DefaultHlsPlaylistTracker(hlsDataSourceFactory, loadErrorHandlingPolicy, hlsPlaylistParserFactory);
        }
    };
    private final HlsDataSourceFactory dataSourceFactory;
    private MediaSourceEventListener.EventDispatcher eventDispatcher;
    private Loader initialPlaylistLoader;
    private long initialStartTimeUs;
    private boolean isLive;
    private final CopyOnWriteArrayList<HlsPlaylistTracker.PlaylistEventListener> listeners;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private HlsMasterPlaylist masterPlaylist;
    private final HashMap<Uri, MediaPlaylistBundle> playlistBundles;
    private final HlsPlaylistParserFactory playlistParserFactory;
    private Handler playlistRefreshHandler;
    private final double playlistStuckTargetDurationCoefficient;
    private HlsMediaPlaylist primaryMediaPlaylistSnapshot;
    private Uri primaryMediaPlaylistUrl;
    private HlsPlaylistTracker.PrimaryPlaylistListener primaryPlaylistListener;

    public DefaultHlsPlaylistTracker(HlsDataSourceFactory dataSourceFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistParserFactory playlistParserFactory) {
        this(dataSourceFactory, loadErrorHandlingPolicy, playlistParserFactory, 3.5d);
    }

    public DefaultHlsPlaylistTracker(HlsDataSourceFactory dataSourceFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistParserFactory playlistParserFactory, double playlistStuckTargetDurationCoefficient) {
        this.dataSourceFactory = dataSourceFactory;
        this.playlistParserFactory = playlistParserFactory;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.playlistStuckTargetDurationCoefficient = playlistStuckTargetDurationCoefficient;
        this.listeners = new CopyOnWriteArrayList<>();
        this.playlistBundles = new HashMap<>();
        this.initialStartTimeUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void start(Uri initialPlaylistUri, MediaSourceEventListener.EventDispatcher eventDispatcher, HlsPlaylistTracker.PrimaryPlaylistListener primaryPlaylistListener) {
        this.playlistRefreshHandler = Util.createHandlerForCurrentLooper();
        this.eventDispatcher = eventDispatcher;
        this.primaryPlaylistListener = primaryPlaylistListener;
        ParsingLoadable parsingLoadable = new ParsingLoadable(this.dataSourceFactory.createDataSource(4), initialPlaylistUri, 4, this.playlistParserFactory.createPlaylistParser());
        Assertions.checkState(this.initialPlaylistLoader == null);
        Loader loader = new Loader("DefaultHlsPlaylistTracker:MasterPlaylist");
        this.initialPlaylistLoader = loader;
        eventDispatcher.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, loader.startLoading(parsingLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type))), parsingLoadable.type);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void stop() {
        this.primaryMediaPlaylistUrl = null;
        this.primaryMediaPlaylistSnapshot = null;
        this.masterPlaylist = null;
        this.initialStartTimeUs = C.TIME_UNSET;
        this.initialPlaylistLoader.release();
        this.initialPlaylistLoader = null;
        Iterator<MediaPlaylistBundle> it = this.playlistBundles.values().iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.playlistRefreshHandler.removeCallbacksAndMessages(null);
        this.playlistRefreshHandler = null;
        this.playlistBundles.clear();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void addListener(HlsPlaylistTracker.PlaylistEventListener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void removeListener(HlsPlaylistTracker.PlaylistEventListener listener) {
        this.listeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public HlsMasterPlaylist getMasterPlaylist() {
        return this.masterPlaylist;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public HlsMediaPlaylist getPlaylistSnapshot(Uri url, boolean isForPlayback) {
        HlsMediaPlaylist playlistSnapshot = this.playlistBundles.get(url).getPlaylistSnapshot();
        if (playlistSnapshot != null && isForPlayback) {
            maybeSetPrimaryUrl(url);
        }
        return playlistSnapshot;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public long getInitialStartTimeUs() {
        return this.initialStartTimeUs;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public boolean isSnapshotValid(Uri url) {
        return this.playlistBundles.get(url).isSnapshotValid();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void maybeThrowPrimaryPlaylistRefreshError() throws IOException {
        Loader loader = this.initialPlaylistLoader;
        if (loader != null) {
            loader.maybeThrowError();
        }
        Uri uri = this.primaryMediaPlaylistUrl;
        if (uri != null) {
            maybeThrowPlaylistRefreshError(uri);
        }
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void maybeThrowPlaylistRefreshError(Uri url) throws IOException {
        this.playlistBundles.get(url).maybeThrowPlaylistRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void refreshPlaylist(Uri url) {
        this.playlistBundles.get(url).loadPlaylist();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public boolean isLive() {
        return this.isLive;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public boolean excludeMediaPlaylist(Uri playlistUrl, long exclusionDurationMs) {
        if (this.playlistBundles.get(playlistUrl) != null) {
            return !r2.excludePlaylist(exclusionDurationMs);
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCompleted(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        HlsMasterPlaylist hlsMasterPlaylistCreateSingleVariantMasterPlaylist;
        HlsPlaylist result = loadable.getResult();
        boolean z = result instanceof HlsMediaPlaylist;
        if (z) {
            hlsMasterPlaylistCreateSingleVariantMasterPlaylist = HlsMasterPlaylist.createSingleVariantMasterPlaylist(result.baseUri);
        } else {
            hlsMasterPlaylistCreateSingleVariantMasterPlaylist = (HlsMasterPlaylist) result;
        }
        this.masterPlaylist = hlsMasterPlaylistCreateSingleVariantMasterPlaylist;
        this.primaryMediaPlaylistUrl = hlsMasterPlaylistCreateSingleVariantMasterPlaylist.variants.get(0).url;
        this.listeners.add(new FirstPrimaryMediaPlaylistListener());
        createBundles(hlsMasterPlaylistCreateSingleVariantMasterPlaylist.mediaPlaylistUrls);
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        MediaPlaylistBundle mediaPlaylistBundle = this.playlistBundles.get(this.primaryMediaPlaylistUrl);
        if (!z) {
            mediaPlaylistBundle.loadPlaylist();
        } else {
            mediaPlaylistBundle.processLoadedPlaylist((HlsMediaPlaylist) result, loadEventInfo);
        }
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.eventDispatcher.loadCompleted(loadEventInfo, 4);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCanceled(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.eventDispatcher.loadCanceled(loadEventInfo, 4);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public Loader.LoadErrorAction onLoadError(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(loadable.type), error, errorCount));
        boolean z = retryDelayMsFor == C.TIME_UNSET;
        this.eventDispatcher.loadError(loadEventInfo, loadable.type, error, z);
        if (z) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        }
        if (z) {
            return Loader.DONT_RETRY_FATAL;
        }
        return Loader.createRetryAction(false, retryDelayMsFor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean maybeSelectNewPrimaryUrl() {
        List<HlsMasterPlaylist.Variant> list = this.masterPlaylist.variants;
        int size = list.size();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        for (int i = 0; i < size; i++) {
            MediaPlaylistBundle mediaPlaylistBundle = (MediaPlaylistBundle) Assertions.checkNotNull(this.playlistBundles.get(list.get(i).url));
            if (jElapsedRealtime > mediaPlaylistBundle.excludeUntilMs) {
                Uri uri = mediaPlaylistBundle.playlistUrl;
                this.primaryMediaPlaylistUrl = uri;
                mediaPlaylistBundle.loadPlaylistInternal(getRequestUriForPrimaryChange(uri));
                return true;
            }
        }
        return false;
    }

    private void maybeSetPrimaryUrl(Uri url) {
        if (url.equals(this.primaryMediaPlaylistUrl) || !isVariantUrl(url)) {
            return;
        }
        HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
        if (hlsMediaPlaylist == null || !hlsMediaPlaylist.hasEndTag) {
            this.primaryMediaPlaylistUrl = url;
            MediaPlaylistBundle mediaPlaylistBundle = this.playlistBundles.get(url);
            HlsMediaPlaylist hlsMediaPlaylist2 = mediaPlaylistBundle.playlistSnapshot;
            if (hlsMediaPlaylist2 != null && hlsMediaPlaylist2.hasEndTag) {
                this.primaryMediaPlaylistSnapshot = hlsMediaPlaylist2;
                this.primaryPlaylistListener.onPrimaryPlaylistRefreshed(hlsMediaPlaylist2);
            } else {
                mediaPlaylistBundle.loadPlaylistInternal(getRequestUriForPrimaryChange(url));
            }
        }
    }

    private Uri getRequestUriForPrimaryChange(Uri newPrimaryPlaylistUri) {
        HlsMediaPlaylist.RenditionReport renditionReport;
        HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
        if (hlsMediaPlaylist == null || !hlsMediaPlaylist.serverControl.canBlockReload || (renditionReport = this.primaryMediaPlaylistSnapshot.renditionReports.get(newPrimaryPlaylistUri)) == null) {
            return newPrimaryPlaylistUri;
        }
        Uri.Builder builderBuildUpon = newPrimaryPlaylistUri.buildUpon();
        builderBuildUpon.appendQueryParameter("_HLS_msn", String.valueOf(renditionReport.lastMediaSequence));
        if (renditionReport.lastPartIndex != -1) {
            builderBuildUpon.appendQueryParameter("_HLS_part", String.valueOf(renditionReport.lastPartIndex));
        }
        return builderBuildUpon.build();
    }

    private boolean isVariantUrl(Uri playlistUrl) {
        List<HlsMasterPlaylist.Variant> list = this.masterPlaylist.variants;
        for (int i = 0; i < list.size(); i++) {
            if (playlistUrl.equals(list.get(i).url)) {
                return true;
            }
        }
        return false;
    }

    private void createBundles(List<Uri> urls) {
        int size = urls.size();
        for (int i = 0; i < size; i++) {
            Uri uri = urls.get(i);
            this.playlistBundles.put(uri, new MediaPlaylistBundle(uri));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPlaylistUpdated(Uri url, HlsMediaPlaylist newSnapshot) {
        if (url.equals(this.primaryMediaPlaylistUrl)) {
            if (this.primaryMediaPlaylistSnapshot == null) {
                this.isLive = !newSnapshot.hasEndTag;
                this.initialStartTimeUs = newSnapshot.startTimeUs;
            }
            this.primaryMediaPlaylistSnapshot = newSnapshot;
            this.primaryPlaylistListener.onPrimaryPlaylistRefreshed(newSnapshot);
        }
        Iterator<HlsPlaylistTracker.PlaylistEventListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPlaylistChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean notifyPlaylistError(Uri playlistUrl, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean forceRetry) {
        Iterator<HlsPlaylistTracker.PlaylistEventListener> it = this.listeners.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= !it.next().onPlaylistError(playlistUrl, loadErrorInfo, forceRetry);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HlsMediaPlaylist getLatestPlaylistSnapshot(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        if (loadedPlaylist.isNewerThan(oldPlaylist)) {
            return loadedPlaylist.copyWith(getLoadedPlaylistStartTimeUs(oldPlaylist, loadedPlaylist), getLoadedPlaylistDiscontinuitySequence(oldPlaylist, loadedPlaylist));
        }
        return loadedPlaylist.hasEndTag ? oldPlaylist.copyWithEndTag() : oldPlaylist;
    }

    private long getLoadedPlaylistStartTimeUs(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        if (loadedPlaylist.hasProgramDateTime) {
            return loadedPlaylist.startTimeUs;
        }
        HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
        long j = hlsMediaPlaylist != null ? hlsMediaPlaylist.startTimeUs : 0L;
        if (oldPlaylist == null) {
            return j;
        }
        int size = oldPlaylist.segments.size();
        HlsMediaPlaylist.Segment firstOldOverlappingSegment = getFirstOldOverlappingSegment(oldPlaylist, loadedPlaylist);
        if (firstOldOverlappingSegment != null) {
            return oldPlaylist.startTimeUs + firstOldOverlappingSegment.relativeStartTimeUs;
        }
        return ((long) size) == loadedPlaylist.mediaSequence - oldPlaylist.mediaSequence ? oldPlaylist.getEndTimeUs() : j;
    }

    private int getLoadedPlaylistDiscontinuitySequence(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        HlsMediaPlaylist.Segment firstOldOverlappingSegment;
        if (loadedPlaylist.hasDiscontinuitySequence) {
            return loadedPlaylist.discontinuitySequence;
        }
        HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
        int i = hlsMediaPlaylist != null ? hlsMediaPlaylist.discontinuitySequence : 0;
        return (oldPlaylist == null || (firstOldOverlappingSegment = getFirstOldOverlappingSegment(oldPlaylist, loadedPlaylist)) == null) ? i : (oldPlaylist.discontinuitySequence + firstOldOverlappingSegment.relativeDiscontinuitySequence) - loadedPlaylist.segments.get(0).relativeDiscontinuitySequence;
    }

    private static HlsMediaPlaylist.Segment getFirstOldOverlappingSegment(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        int i = (int) (loadedPlaylist.mediaSequence - oldPlaylist.mediaSequence);
        List<HlsMediaPlaylist.Segment> list = oldPlaylist.segments;
        if (i < list.size()) {
            return list.get(i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MediaPlaylistBundle implements Loader.Callback<ParsingLoadable<HlsPlaylist>> {
        private static final String BLOCK_MSN_PARAM = "_HLS_msn";
        private static final String BLOCK_PART_PARAM = "_HLS_part";
        private static final String SKIP_PARAM = "_HLS_skip";
        private long earliestNextLoadTimeMs;
        private long excludeUntilMs;
        private long lastSnapshotChangeMs;
        private long lastSnapshotLoadMs;
        private boolean loadPending;
        private final DataSource mediaPlaylistDataSource;
        private final Loader mediaPlaylistLoader = new Loader("DefaultHlsPlaylistTracker:MediaPlaylist");
        private IOException playlistError;
        private HlsMediaPlaylist playlistSnapshot;
        private final Uri playlistUrl;

        public MediaPlaylistBundle(Uri playlistUrl) {
            this.playlistUrl = playlistUrl;
            this.mediaPlaylistDataSource = DefaultHlsPlaylistTracker.this.dataSourceFactory.createDataSource(4);
        }

        public HlsMediaPlaylist getPlaylistSnapshot() {
            return this.playlistSnapshot;
        }

        public boolean isSnapshotValid() {
            if (this.playlistSnapshot == null) {
                return false;
            }
            return this.playlistSnapshot.hasEndTag || this.playlistSnapshot.playlistType == 2 || this.playlistSnapshot.playlistType == 1 || this.lastSnapshotLoadMs + Math.max(30000L, C.usToMs(this.playlistSnapshot.durationUs)) > SystemClock.elapsedRealtime();
        }

        public void loadPlaylist() {
            loadPlaylistInternal(this.playlistUrl);
        }

        public void maybeThrowPlaylistRefreshError() throws IOException {
            this.mediaPlaylistLoader.maybeThrowError();
            IOException iOException = this.playlistError;
            if (iOException != null) {
                throw iOException;
            }
        }

        public void release() {
            this.mediaPlaylistLoader.release();
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs) {
            HlsPlaylist result = loadable.getResult();
            LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
            if (result instanceof HlsMediaPlaylist) {
                processLoadedPlaylist((HlsMediaPlaylist) result, loadEventInfo);
                DefaultHlsPlaylistTracker.this.eventDispatcher.loadCompleted(loadEventInfo, 4);
            } else {
                this.playlistError = ParserException.createForMalformedManifest("Loaded playlist has unexpected type.", null);
                DefaultHlsPlaylistTracker.this.eventDispatcher.loadError(loadEventInfo, 4, this.playlistError, true);
            }
            DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
            LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
            DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
            DefaultHlsPlaylistTracker.this.eventDispatcher.loadCanceled(loadEventInfo, 4);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            Loader.LoadErrorAction loadErrorActionCreateRetryAction;
            LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
            boolean z = error instanceof HlsPlaylistParser.DeltaUpdateException;
            if ((loadable.getUri().getQueryParameter(BLOCK_MSN_PARAM) != null) || z) {
                int i = error instanceof HttpDataSource.InvalidResponseCodeException ? ((HttpDataSource.InvalidResponseCodeException) error).responseCode : Integer.MAX_VALUE;
                if (z || i == 400 || i == 503) {
                    this.earliestNextLoadTimeMs = SystemClock.elapsedRealtime();
                    loadPlaylist();
                    ((MediaSourceEventListener.EventDispatcher) Util.castNonNull(DefaultHlsPlaylistTracker.this.eventDispatcher)).loadError(loadEventInfo, loadable.type, error, true);
                    return Loader.DONT_RETRY;
                }
            }
            LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo = new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(loadable.type), error, errorCount);
            if (DefaultHlsPlaylistTracker.this.notifyPlaylistError(this.playlistUrl, loadErrorInfo, false)) {
                long retryDelayMsFor = DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo);
                if (retryDelayMsFor != C.TIME_UNSET) {
                    loadErrorActionCreateRetryAction = Loader.createRetryAction(false, retryDelayMsFor);
                } else {
                    loadErrorActionCreateRetryAction = Loader.DONT_RETRY_FATAL;
                }
            } else {
                loadErrorActionCreateRetryAction = Loader.DONT_RETRY;
            }
            boolean zIsRetry = true ^ loadErrorActionCreateRetryAction.isRetry();
            DefaultHlsPlaylistTracker.this.eventDispatcher.loadError(loadEventInfo, loadable.type, error, zIsRetry);
            if (zIsRetry) {
                DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
            }
            return loadErrorActionCreateRetryAction;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void loadPlaylistInternal(final Uri playlistRequestUri) {
            this.excludeUntilMs = 0L;
            if (this.loadPending || this.mediaPlaylistLoader.isLoading() || this.mediaPlaylistLoader.hasFatalError()) {
                return;
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (jElapsedRealtime < this.earliestNextLoadTimeMs) {
                this.loadPending = true;
                DefaultHlsPlaylistTracker.this.playlistRefreshHandler.postDelayed(new Runnable() { // from class: com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker$MediaPlaylistBundle$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m190x4fadff69(playlistRequestUri);
                    }
                }, this.earliestNextLoadTimeMs - jElapsedRealtime);
            } else {
                loadPlaylistImmediately(playlistRequestUri);
            }
        }

        /* renamed from: lambda$loadPlaylistInternal$0$com-google-android-exoplayer2-source-hls-playlist-DefaultHlsPlaylistTracker$MediaPlaylistBundle, reason: not valid java name */
        /* synthetic */ void m190x4fadff69(Uri uri) {
            this.loadPending = false;
            loadPlaylistImmediately(uri);
        }

        private void loadPlaylistImmediately(Uri playlistRequestUri) {
            ParsingLoadable parsingLoadable = new ParsingLoadable(this.mediaPlaylistDataSource, playlistRequestUri, 4, DefaultHlsPlaylistTracker.this.playlistParserFactory.createPlaylistParser(DefaultHlsPlaylistTracker.this.masterPlaylist, this.playlistSnapshot));
            DefaultHlsPlaylistTracker.this.eventDispatcher.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.mediaPlaylistLoader.startLoading(parsingLoadable, this, DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type))), parsingLoadable.type);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void processLoadedPlaylist(HlsMediaPlaylist loadedPlaylist, LoadEventInfo loadEventInfo) {
            IOException playlistStuckException;
            boolean z;
            long j;
            HlsMediaPlaylist hlsMediaPlaylist = this.playlistSnapshot;
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.lastSnapshotLoadMs = jElapsedRealtime;
            HlsMediaPlaylist latestPlaylistSnapshot = DefaultHlsPlaylistTracker.this.getLatestPlaylistSnapshot(hlsMediaPlaylist, loadedPlaylist);
            this.playlistSnapshot = latestPlaylistSnapshot;
            if (latestPlaylistSnapshot != hlsMediaPlaylist) {
                this.playlistError = null;
                this.lastSnapshotChangeMs = jElapsedRealtime;
                DefaultHlsPlaylistTracker.this.onPlaylistUpdated(this.playlistUrl, latestPlaylistSnapshot);
            } else if (!latestPlaylistSnapshot.hasEndTag) {
                if (loadedPlaylist.mediaSequence + loadedPlaylist.segments.size() < this.playlistSnapshot.mediaSequence) {
                    playlistStuckException = new HlsPlaylistTracker.PlaylistResetException(this.playlistUrl);
                    z = true;
                } else {
                    playlistStuckException = ((double) (jElapsedRealtime - this.lastSnapshotChangeMs)) > ((double) C.usToMs(this.playlistSnapshot.targetDurationUs)) * DefaultHlsPlaylistTracker.this.playlistStuckTargetDurationCoefficient ? new HlsPlaylistTracker.PlaylistStuckException(this.playlistUrl) : null;
                    z = false;
                }
                if (playlistStuckException != null) {
                    this.playlistError = playlistStuckException;
                    DefaultHlsPlaylistTracker.this.notifyPlaylistError(this.playlistUrl, new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(4), playlistStuckException, 1), z);
                }
            }
            if (this.playlistSnapshot.serverControl.canBlockReload) {
                j = 0;
            } else {
                HlsMediaPlaylist hlsMediaPlaylist2 = this.playlistSnapshot;
                if (hlsMediaPlaylist2 != hlsMediaPlaylist) {
                    j = hlsMediaPlaylist2.targetDurationUs;
                } else {
                    j = hlsMediaPlaylist2.targetDurationUs / 2;
                }
            }
            this.earliestNextLoadTimeMs = jElapsedRealtime + C.usToMs(j);
            if (!(this.playlistSnapshot.partTargetDurationUs != C.TIME_UNSET || this.playlistUrl.equals(DefaultHlsPlaylistTracker.this.primaryMediaPlaylistUrl)) || this.playlistSnapshot.hasEndTag) {
                return;
            }
            loadPlaylistInternal(getMediaPlaylistUriForReload());
        }

        private Uri getMediaPlaylistUriForReload() {
            HlsMediaPlaylist hlsMediaPlaylist = this.playlistSnapshot;
            if (hlsMediaPlaylist == null || (hlsMediaPlaylist.serverControl.skipUntilUs == C.TIME_UNSET && !this.playlistSnapshot.serverControl.canBlockReload)) {
                return this.playlistUrl;
            }
            Uri.Builder builderBuildUpon = this.playlistUrl.buildUpon();
            if (this.playlistSnapshot.serverControl.canBlockReload) {
                builderBuildUpon.appendQueryParameter(BLOCK_MSN_PARAM, String.valueOf(this.playlistSnapshot.mediaSequence + this.playlistSnapshot.segments.size()));
                if (this.playlistSnapshot.partTargetDurationUs != C.TIME_UNSET) {
                    List<HlsMediaPlaylist.Part> list = this.playlistSnapshot.trailingParts;
                    int size = list.size();
                    if (!list.isEmpty() && ((HlsMediaPlaylist.Part) Iterables.getLast(list)).isPreload) {
                        size--;
                    }
                    builderBuildUpon.appendQueryParameter(BLOCK_PART_PARAM, String.valueOf(size));
                }
            }
            if (this.playlistSnapshot.serverControl.skipUntilUs != C.TIME_UNSET) {
                builderBuildUpon.appendQueryParameter(SKIP_PARAM, this.playlistSnapshot.serverControl.canSkipDateRanges ? "v2" : "YES");
            }
            return builderBuildUpon.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean excludePlaylist(long exclusionDurationMs) {
            this.excludeUntilMs = SystemClock.elapsedRealtime() + exclusionDurationMs;
            return this.playlistUrl.equals(DefaultHlsPlaylistTracker.this.primaryMediaPlaylistUrl) && !DefaultHlsPlaylistTracker.this.maybeSelectNewPrimaryUrl();
        }
    }

    private class FirstPrimaryMediaPlaylistListener implements HlsPlaylistTracker.PlaylistEventListener {
        private FirstPrimaryMediaPlaylistListener() {
        }

        @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
        public void onPlaylistChanged() {
            DefaultHlsPlaylistTracker.this.listeners.remove(this);
        }

        @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
        public boolean onPlaylistError(Uri url, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean forceRetry) {
            MediaPlaylistBundle mediaPlaylistBundle;
            if (DefaultHlsPlaylistTracker.this.primaryMediaPlaylistSnapshot == null) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                List<HlsMasterPlaylist.Variant> list = ((HlsMasterPlaylist) Util.castNonNull(DefaultHlsPlaylistTracker.this.masterPlaylist)).variants;
                int i = 0;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    MediaPlaylistBundle mediaPlaylistBundle2 = (MediaPlaylistBundle) DefaultHlsPlaylistTracker.this.playlistBundles.get(list.get(i2).url);
                    if (mediaPlaylistBundle2 != null && jElapsedRealtime < mediaPlaylistBundle2.excludeUntilMs) {
                        i++;
                    }
                }
                LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getFallbackSelectionFor(new LoadErrorHandlingPolicy.FallbackOptions(1, 0, DefaultHlsPlaylistTracker.this.masterPlaylist.variants.size(), i), loadErrorInfo);
                if (fallbackSelectionFor != null && fallbackSelectionFor.type == 2 && (mediaPlaylistBundle = (MediaPlaylistBundle) DefaultHlsPlaylistTracker.this.playlistBundles.get(url)) != null) {
                    mediaPlaylistBundle.excludePlaylist(fallbackSelectionFor.exclusionDurationMs);
                }
            }
            return false;
        }
    }
}
