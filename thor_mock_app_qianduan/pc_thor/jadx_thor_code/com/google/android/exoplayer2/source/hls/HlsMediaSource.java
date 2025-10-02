package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.FilteringHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsMediaSource extends BaseMediaSource implements HlsPlaylistTracker.PrimaryPlaylistListener {
    public static final int METADATA_TYPE_EMSG = 3;
    public static final int METADATA_TYPE_ID3 = 1;
    private final boolean allowChunklessPreparation;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final DrmSessionManager drmSessionManager;
    private final long elapsedRealTimeOffsetMs;
    private final HlsExtractorFactory extractorFactory;
    private MediaItem.LiveConfiguration liveConfiguration;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final MediaItem mediaItem;
    private TransferListener mediaTransferListener;
    private final int metadataType;
    private final MediaItem.PlaybackProperties playbackProperties;
    private final HlsPlaylistTracker playlistTracker;
    private final boolean useSessionKeys;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MetadataType {
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    public static final class Factory implements MediaSourceFactory {
        private boolean allowChunklessPreparation;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private long elapsedRealTimeOffsetMs;
        private HlsExtractorFactory extractorFactory;
        private final HlsDataSourceFactory hlsDataSourceFactory;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private int metadataType;
        private HlsPlaylistParserFactory playlistParserFactory;
        private HlsPlaylistTracker.Factory playlistTrackerFactory;
        private List<StreamKey> streamKeys;
        private Object tag;
        private boolean useSessionKeys;
        private boolean usingCustomDrmSessionManagerProvider;

        static /* synthetic */ DrmSessionManager lambda$setDrmSessionManager$0(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{2};
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(List streamKeys) {
            return setStreamKeys((List<StreamKey>) streamKeys);
        }

        public Factory(DataSource.Factory dataSourceFactory) {
            this(new DefaultHlsDataSourceFactory(dataSourceFactory));
        }

        public Factory(HlsDataSourceFactory hlsDataSourceFactory) {
            this.hlsDataSourceFactory = (HlsDataSourceFactory) Assertions.checkNotNull(hlsDataSourceFactory);
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.playlistParserFactory = new DefaultHlsPlaylistParserFactory();
            this.playlistTrackerFactory = DefaultHlsPlaylistTracker.FACTORY;
            this.extractorFactory = HlsExtractorFactory.DEFAULT;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            this.metadataType = 1;
            this.streamKeys = Collections.emptyList();
            this.elapsedRealTimeOffsetMs = C.TIME_UNSET;
        }

        @Deprecated
        public Factory setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Factory setExtractorFactory(HlsExtractorFactory extractorFactory) {
            if (extractorFactory == null) {
                extractorFactory = HlsExtractorFactory.DEFAULT;
            }
            this.extractorFactory = extractorFactory;
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

        public Factory setPlaylistParserFactory(HlsPlaylistParserFactory playlistParserFactory) {
            if (playlistParserFactory == null) {
                playlistParserFactory = new DefaultHlsPlaylistParserFactory();
            }
            this.playlistParserFactory = playlistParserFactory;
            return this;
        }

        public Factory setPlaylistTrackerFactory(HlsPlaylistTracker.Factory playlistTrackerFactory) {
            if (playlistTrackerFactory == null) {
                playlistTrackerFactory = DefaultHlsPlaylistTracker.FACTORY;
            }
            this.playlistTrackerFactory = playlistTrackerFactory;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            if (compositeSequenceableLoaderFactory == null) {
                compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            }
            this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
            return this;
        }

        public Factory setAllowChunklessPreparation(boolean allowChunklessPreparation) {
            this.allowChunklessPreparation = allowChunklessPreparation;
            return this;
        }

        public Factory setMetadataType(int metadataType) {
            this.metadataType = metadataType;
            return this;
        }

        public Factory setUseSessionKeys(boolean useSessionKeys) {
            this.useSessionKeys = useSessionKeys;
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
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        return HlsMediaSource.Factory.lambda$setDrmSessionManager$0(drmSessionManager, mediaItem);
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

        Factory setElapsedRealTimeOffsetMs(long elapsedRealTimeOffsetMs) {
            this.elapsedRealTimeOffsetMs = elapsedRealTimeOffsetMs;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public HlsMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_M3U8).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public HlsMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            MediaItem mediaItemBuild = mediaItem;
            Assertions.checkNotNull(mediaItemBuild.playbackProperties);
            HlsPlaylistParserFactory filteringHlsPlaylistParserFactory = this.playlistParserFactory;
            if (mediaItemBuild.playbackProperties.streamKeys.isEmpty()) {
                list = this.streamKeys;
            } else {
                list = mediaItemBuild.playbackProperties.streamKeys;
            }
            if (!list.isEmpty()) {
                filteringHlsPlaylistParserFactory = new FilteringHlsPlaylistParserFactory(filteringHlsPlaylistParserFactory, list);
            }
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
            HlsDataSourceFactory hlsDataSourceFactory = this.hlsDataSourceFactory;
            HlsExtractorFactory hlsExtractorFactory = this.extractorFactory;
            CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory = this.compositeSequenceableLoaderFactory;
            DrmSessionManager drmSessionManager = this.drmSessionManagerProvider.get(mediaItem2);
            LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.loadErrorHandlingPolicy;
            return new HlsMediaSource(mediaItem2, hlsDataSourceFactory, hlsExtractorFactory, compositeSequenceableLoaderFactory, drmSessionManager, loadErrorHandlingPolicy, this.playlistTrackerFactory.createTracker(this.hlsDataSourceFactory, loadErrorHandlingPolicy, filteringHlsPlaylistParserFactory), this.elapsedRealTimeOffsetMs, this.allowChunklessPreparation, this.metadataType, this.useSessionKeys);
        }
    }

    private HlsMediaSource(MediaItem mediaItem, HlsDataSourceFactory dataSourceFactory, HlsExtractorFactory extractorFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistTracker playlistTracker, long elapsedRealTimeOffsetMs, boolean allowChunklessPreparation, int metadataType, boolean useSessionKeys) {
        this.playbackProperties = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.mediaItem = mediaItem;
        this.liveConfiguration = mediaItem.liveConfiguration;
        this.dataSourceFactory = dataSourceFactory;
        this.extractorFactory = extractorFactory;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.drmSessionManager = drmSessionManager;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.playlistTracker = playlistTracker;
        this.elapsedRealTimeOffsetMs = elapsedRealTimeOffsetMs;
        this.allowChunklessPreparation = allowChunklessPreparation;
        this.metadataType = metadataType;
        this.useSessionKeys = useSessionKeys;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        this.mediaTransferListener = mediaTransferListener;
        this.drmSessionManager.prepare();
        this.playlistTracker.start(this.playbackProperties.uri, createEventDispatcher(null), this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        MediaSourceEventListener.EventDispatcher eventDispatcherCreateEventDispatcher = createEventDispatcher(id);
        return new HlsMediaPeriod(this.extractorFactory, this.playlistTracker, this.dataSourceFactory, this.mediaTransferListener, this.drmSessionManager, createDrmEventDispatcher(id), this.loadErrorHandlingPolicy, eventDispatcherCreateEventDispatcher, allocator, this.compositeSequenceableLoaderFactory, this.allowChunklessPreparation, this.metadataType, this.useSessionKeys);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.playlistTracker.stop();
        this.drmSessionManager.release();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PrimaryPlaylistListener
    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist mediaPlaylist) {
        SinglePeriodTimeline singlePeriodTimelineCreateTimelineForOnDemand;
        long jUsToMs = mediaPlaylist.hasProgramDateTime ? C.usToMs(mediaPlaylist.startTimeUs) : -9223372036854775807L;
        long j = (mediaPlaylist.playlistType == 2 || mediaPlaylist.playlistType == 1) ? jUsToMs : -9223372036854775807L;
        HlsManifest hlsManifest = new HlsManifest((HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist()), mediaPlaylist);
        if (this.playlistTracker.isLive()) {
            singlePeriodTimelineCreateTimelineForOnDemand = createTimelineForLive(mediaPlaylist, j, jUsToMs, hlsManifest);
        } else {
            singlePeriodTimelineCreateTimelineForOnDemand = createTimelineForOnDemand(mediaPlaylist, j, jUsToMs, hlsManifest);
        }
        refreshSourceInfo(singlePeriodTimelineCreateTimelineForOnDemand);
    }

    private SinglePeriodTimeline createTimelineForLive(HlsMediaPlaylist playlist, long presentationStartTimeMs, long windowStartTimeMs, HlsManifest manifest) {
        long targetLiveOffsetUs;
        long initialStartTimeUs = playlist.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        long j = playlist.hasEndTag ? initialStartTimeUs + playlist.durationUs : -9223372036854775807L;
        long liveEdgeOffsetUs = getLiveEdgeOffsetUs(playlist);
        if (this.liveConfiguration.targetOffsetMs != C.TIME_UNSET) {
            targetLiveOffsetUs = C.msToUs(this.liveConfiguration.targetOffsetMs);
        } else {
            targetLiveOffsetUs = getTargetLiveOffsetUs(playlist, liveEdgeOffsetUs);
        }
        maybeUpdateLiveConfiguration(Util.constrainValue(targetLiveOffsetUs, liveEdgeOffsetUs, playlist.durationUs + liveEdgeOffsetUs));
        return new SinglePeriodTimeline(presentationStartTimeMs, windowStartTimeMs, C.TIME_UNSET, j, playlist.durationUs, initialStartTimeUs, getLiveWindowDefaultStartPositionUs(playlist, liveEdgeOffsetUs), true, !playlist.hasEndTag, playlist.playlistType == 2 && playlist.hasPositiveStartOffset, manifest, this.mediaItem, this.liveConfiguration);
    }

    private SinglePeriodTimeline createTimelineForOnDemand(HlsMediaPlaylist playlist, long presentationStartTimeMs, long windowStartTimeMs, HlsManifest manifest) {
        long j;
        if (playlist.startOffsetUs == C.TIME_UNSET || playlist.segments.isEmpty()) {
            j = 0;
        } else if (playlist.preciseStart || playlist.startOffsetUs == playlist.durationUs) {
            j = playlist.startOffsetUs;
        } else {
            j = findClosestPrecedingSegment(playlist.segments, playlist.startOffsetUs).relativeStartTimeUs;
        }
        return new SinglePeriodTimeline(presentationStartTimeMs, windowStartTimeMs, C.TIME_UNSET, playlist.durationUs, playlist.durationUs, 0L, j, true, false, true, manifest, this.mediaItem, null);
    }

    private long getLiveEdgeOffsetUs(HlsMediaPlaylist playlist) {
        if (playlist.hasProgramDateTime) {
            return C.msToUs(Util.getNowUnixTimeMs(this.elapsedRealTimeOffsetMs)) - playlist.getEndTimeUs();
        }
        return 0L;
    }

    private long getLiveWindowDefaultStartPositionUs(HlsMediaPlaylist playlist, long liveEdgeOffsetUs) {
        long jMsToUs;
        if (playlist.startOffsetUs != C.TIME_UNSET) {
            jMsToUs = playlist.startOffsetUs;
        } else {
            jMsToUs = (playlist.durationUs + liveEdgeOffsetUs) - C.msToUs(this.liveConfiguration.targetOffsetMs);
        }
        if (playlist.preciseStart) {
            return jMsToUs;
        }
        HlsMediaPlaylist.Part partFindClosestPrecedingIndependentPart = findClosestPrecedingIndependentPart(playlist.trailingParts, jMsToUs);
        if (partFindClosestPrecedingIndependentPart != null) {
            return partFindClosestPrecedingIndependentPart.relativeStartTimeUs;
        }
        if (playlist.segments.isEmpty()) {
            return 0L;
        }
        HlsMediaPlaylist.Segment segmentFindClosestPrecedingSegment = findClosestPrecedingSegment(playlist.segments, jMsToUs);
        HlsMediaPlaylist.Part partFindClosestPrecedingIndependentPart2 = findClosestPrecedingIndependentPart(segmentFindClosestPrecedingSegment.parts, jMsToUs);
        if (partFindClosestPrecedingIndependentPart2 != null) {
            return partFindClosestPrecedingIndependentPart2.relativeStartTimeUs;
        }
        return segmentFindClosestPrecedingSegment.relativeStartTimeUs;
    }

    private void maybeUpdateLiveConfiguration(long targetLiveOffsetUs) {
        long jUsToMs = C.usToMs(targetLiveOffsetUs);
        if (jUsToMs != this.liveConfiguration.targetOffsetMs) {
            this.liveConfiguration = this.mediaItem.buildUpon().setLiveTargetOffsetMs(jUsToMs).build().liveConfiguration;
        }
    }

    private static long getTargetLiveOffsetUs(HlsMediaPlaylist playlist, long liveEdgeOffsetUs) {
        long j;
        HlsMediaPlaylist.ServerControl serverControl = playlist.serverControl;
        if (playlist.startOffsetUs != C.TIME_UNSET) {
            j = playlist.durationUs - playlist.startOffsetUs;
        } else if (serverControl.partHoldBackUs != C.TIME_UNSET && playlist.partTargetDurationUs != C.TIME_UNSET) {
            j = serverControl.partHoldBackUs;
        } else if (serverControl.holdBackUs != C.TIME_UNSET) {
            j = serverControl.holdBackUs;
        } else {
            j = 3 * playlist.targetDurationUs;
        }
        return j + liveEdgeOffsetUs;
    }

    private static HlsMediaPlaylist.Part findClosestPrecedingIndependentPart(List<HlsMediaPlaylist.Part> parts, long positionUs) {
        HlsMediaPlaylist.Part part = null;
        for (int i = 0; i < parts.size(); i++) {
            HlsMediaPlaylist.Part part2 = parts.get(i);
            if (part2.relativeStartTimeUs > positionUs || !part2.isIndependent) {
                if (part2.relativeStartTimeUs > positionUs) {
                    break;
                }
            } else {
                part = part2;
            }
        }
        return part;
    }

    private static HlsMediaPlaylist.Segment findClosestPrecedingSegment(List<HlsMediaPlaylist.Segment> segments, long positionUs) {
        return segments.get(Util.binarySearchFloor((List<? extends Comparable<? super Long>>) segments, Long.valueOf(positionUs), true, true));
    }
}
