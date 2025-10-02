package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final BaseUrlExclusionList baseUrlExclusionList;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;
    private IOException fatalError;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;
    private final PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler;
    protected final RepresentationHolder[] representationHolders;
    private ExoTrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements DashChunkSource.Factory {
        private final ChunkExtractor.Factory chunkExtractorFactory;
        private final DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(DataSource.Factory dataSourceFactory) {
            this(dataSourceFactory, 1);
        }

        public Factory(DataSource.Factory dataSourceFactory, int maxSegmentsPerLoad) {
            this(BundledChunkExtractor.FACTORY, dataSourceFactory, maxSegmentsPerLoad);
        }

        public Factory(ChunkExtractor.Factory chunkExtractorFactory, DataSource.Factory dataSourceFactory, int maxSegmentsPerLoad) {
            this.chunkExtractorFactory = chunkExtractorFactory;
            this.dataSourceFactory = dataSourceFactory;
            this.maxSegmentsPerLoad = maxSegmentsPerLoad;
        }

        @Override // com.google.android.exoplayer2.source.dash.DashChunkSource.Factory
        public DashChunkSource createDashChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, DashManifest manifest, BaseUrlExclusionList baseUrlExclusionList, int periodIndex, int[] adaptationSetIndices, ExoTrackSelection trackSelection, int trackType, long elapsedRealtimeOffsetMs, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, PlayerEmsgHandler.PlayerTrackEmsgHandler playerEmsgHandler, TransferListener transferListener) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultDashChunkSource(this.chunkExtractorFactory, manifestLoaderErrorThrower, manifest, baseUrlExclusionList, periodIndex, adaptationSetIndices, trackSelection, trackType, dataSourceCreateDataSource, elapsedRealtimeOffsetMs, this.maxSegmentsPerLoad, enableEventMessageTrack, closedCaptionFormats, playerEmsgHandler);
        }
    }

    public DefaultDashChunkSource(ChunkExtractor.Factory chunkExtractorFactory, LoaderErrorThrower manifestLoaderErrorThrower, DashManifest manifest, BaseUrlExclusionList baseUrlExclusionList, int periodIndex, int[] adaptationSetIndices, ExoTrackSelection trackSelection, int trackType, DataSource dataSource, long elapsedRealtimeOffsetMs, int maxSegmentsPerLoad, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler) {
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower;
        this.manifest = manifest;
        this.baseUrlExclusionList = baseUrlExclusionList;
        this.adaptationSetIndices = adaptationSetIndices;
        this.trackSelection = trackSelection;
        this.trackType = trackType;
        this.dataSource = dataSource;
        this.periodIndex = periodIndex;
        this.elapsedRealtimeOffsetMs = elapsedRealtimeOffsetMs;
        this.maxSegmentsPerLoad = maxSegmentsPerLoad;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler;
        long periodDurationUs = manifest.getPeriodDurationUs(periodIndex);
        ArrayList<Representation> representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[trackSelection.length()];
        int i = 0;
        while (i < this.representationHolders.length) {
            Representation representation = representations.get(trackSelection.getIndexInTrackGroup(i));
            BaseUrl baseUrlSelectBaseUrl = baseUrlExclusionList.selectBaseUrl(representation.baseUrls);
            RepresentationHolder[] representationHolderArr = this.representationHolders;
            if (baseUrlSelectBaseUrl == null) {
                baseUrlSelectBaseUrl = representation.baseUrls.get(0);
            }
            int i2 = i;
            representationHolderArr[i2] = new RepresentationHolder(periodDurationUs, representation, baseUrlSelectBaseUrl, BundledChunkExtractor.FACTORY.createProgressiveMediaExtractor(trackType, representation.format, enableEventMessageTrack, closedCaptionFormats, playerTrackEmsgHandler), 0L, representation.getIndex());
            i = i2 + 1;
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            if (representationHolder.segmentIndex != null) {
                long segmentNum = representationHolder.getSegmentNum(positionUs);
                long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                long segmentCount = representationHolder.getSegmentCount();
                return seekParameters.resolveSeekPositionUs(positionUs, segmentStartTimeUs, (segmentStartTimeUs >= positionUs || (segmentCount != -1 && segmentNum >= (representationHolder.getFirstSegmentNum() + segmentCount) - 1)) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1));
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateManifest(DashManifest newManifest, int newPeriodIndex) {
        try {
            this.manifest = newManifest;
            this.periodIndex = newPeriodIndex;
            long periodDurationUs = newManifest.getPeriodDurationUs(newPeriodIndex);
            ArrayList<Representation> representations = getRepresentations();
            for (int i = 0; i < this.representationHolders.length; i++) {
                Representation representation = representations.get(this.trackSelection.getIndexInTrackGroup(i));
                RepresentationHolder[] representationHolderArr = this.representationHolders;
                representationHolderArr[i] = representationHolderArr[i].copyWithNewRepresentation(periodDurationUs, representation);
            }
        } catch (BehindLiveWindowException e) {
            this.fatalError = e;
        }
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateTrackSelection(ExoTrackSelection trackSelection) {
        this.trackSelection = trackSelection;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public int getPreferredQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        if (this.fatalError != null || this.trackSelection.length() < 2) {
            return queue.size();
        }
        return this.trackSelection.evaluateQueueSize(playbackPositionUs, queue);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long playbackPositionUs, Chunk loadingChunk, List<? extends MediaChunk> queue) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(playbackPositionUs, loadingChunk, queue);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void getNextChunk(long playbackPositionUs, long loadPositionUs, List<? extends MediaChunk> queue, ChunkHolder out) {
        int i;
        int i2;
        MediaChunkIterator[] mediaChunkIteratorArr;
        boolean z;
        long j;
        long j2;
        if (this.fatalError != null) {
            return;
        }
        long j3 = loadPositionUs - playbackPositionUs;
        long jMsToUs = C.msToUs(this.manifest.availabilityStartTimeMs) + C.msToUs(this.manifest.getPeriod(this.periodIndex).startMs) + loadPositionUs;
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler == null || !playerTrackEmsgHandler.maybeRefreshManifestBeforeLoadingNextChunk(jMsToUs)) {
            long jMsToUs2 = C.msToUs(Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs));
            long nowPeriodTimeUs = getNowPeriodTimeUs(jMsToUs2);
            boolean z2 = true;
            MediaChunk mediaChunk = queue.isEmpty() ? null : queue.get(queue.size() - 1);
            int length = this.trackSelection.length();
            MediaChunkIterator[] mediaChunkIteratorArr2 = new MediaChunkIterator[length];
            int i3 = 0;
            while (i3 < length) {
                RepresentationHolder representationHolder = this.representationHolders[i3];
                if (representationHolder.segmentIndex == null) {
                    mediaChunkIteratorArr2[i3] = MediaChunkIterator.EMPTY;
                    i = i3;
                    i2 = length;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    z = z2;
                    j = j3;
                    j2 = jMsToUs2;
                } else {
                    long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(jMsToUs2);
                    long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(jMsToUs2);
                    i = i3;
                    i2 = length;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    z = z2;
                    j = j3;
                    j2 = jMsToUs2;
                    long segmentNum = getSegmentNum(representationHolder, mediaChunk, loadPositionUs, firstAvailableSegmentNum, lastAvailableSegmentNum);
                    if (segmentNum < firstAvailableSegmentNum) {
                        mediaChunkIteratorArr[i] = MediaChunkIterator.EMPTY;
                    } else {
                        mediaChunkIteratorArr[i] = new RepresentationSegmentIterator(representationHolder, segmentNum, lastAvailableSegmentNum, nowPeriodTimeUs);
                    }
                }
                i3 = i + 1;
                jMsToUs2 = j2;
                z2 = z;
                length = i2;
                mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                j3 = j;
            }
            boolean z3 = z2;
            long j4 = j3;
            long j5 = jMsToUs2;
            this.trackSelection.updateSelectedTrack(playbackPositionUs, j4, getAvailableLiveDurationUs(j5, playbackPositionUs), queue, mediaChunkIteratorArr2);
            RepresentationHolder representationHolder2 = this.representationHolders[this.trackSelection.getSelectedIndex()];
            if (representationHolder2.chunkExtractor != null) {
                Representation representation = representationHolder2.representation;
                RangedUri initializationUri = representationHolder2.chunkExtractor.getSampleFormats() == null ? representation.getInitializationUri() : null;
                RangedUri indexUri = representationHolder2.segmentIndex == null ? representation.getIndexUri() : null;
                if (initializationUri != null || indexUri != null) {
                    out.chunk = newInitializationChunk(representationHolder2, this.dataSource, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), initializationUri, indexUri);
                    return;
                }
            }
            long j6 = representationHolder2.periodDurationUs;
            long j7 = C.TIME_UNSET;
            boolean z4 = j6 != C.TIME_UNSET ? z3 : false;
            if (representationHolder2.getSegmentCount() == 0) {
                out.endOfStream = z4;
                return;
            }
            long firstAvailableSegmentNum2 = representationHolder2.getFirstAvailableSegmentNum(j5);
            long lastAvailableSegmentNum2 = representationHolder2.getLastAvailableSegmentNum(j5);
            boolean z5 = z4;
            long segmentNum2 = getSegmentNum(representationHolder2, mediaChunk, loadPositionUs, firstAvailableSegmentNum2, lastAvailableSegmentNum2);
            if (segmentNum2 < firstAvailableSegmentNum2) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
            if (segmentNum2 > lastAvailableSegmentNum2 || (this.missingLastSegment && segmentNum2 >= lastAvailableSegmentNum2)) {
                out.endOfStream = z5;
                return;
            }
            if (z5 && representationHolder2.getSegmentStartTimeUs(segmentNum2) >= j6) {
                out.endOfStream = true;
                return;
            }
            int iMin = (int) Math.min(this.maxSegmentsPerLoad, (lastAvailableSegmentNum2 - segmentNum2) + 1);
            if (j6 != C.TIME_UNSET) {
                while (iMin > 1 && representationHolder2.getSegmentStartTimeUs((iMin + segmentNum2) - 1) >= j6) {
                    iMin--;
                }
            }
            int i4 = iMin;
            if (queue.isEmpty()) {
                j7 = loadPositionUs;
            }
            out.chunk = newMediaChunk(representationHolder2, this.dataSource, this.trackType, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), segmentNum2, i4, j7, nowPeriodTimeUs);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
        ChunkIndex chunkIndex;
        if (chunk instanceof InitializationChunk) {
            int iIndexOf = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[iIndexOf];
            if (representationHolder.segmentIndex == null && (chunkIndex = representationHolder.chunkExtractor.getChunkIndex()) != null) {
                this.representationHolders[iIndexOf] = representationHolder.copyWithNewSegmentIndex(new DashWrappingSegmentIndex(chunkIndex, representationHolder.representation.presentationTimeOffsetUs));
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null) {
            playerTrackEmsgHandler.onChunkLoadCompleted(chunk);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        int i = 0;
        if (!cancelable) {
            return false;
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null && playerTrackEmsgHandler.onChunkLoadError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk) && (loadErrorInfo.exception instanceof HttpDataSource.InvalidResponseCodeException) && ((HttpDataSource.InvalidResponseCodeException) loadErrorInfo.exception).responseCode == 404) {
            RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
            long segmentCount = representationHolder.getSegmentCount();
            if (segmentCount != -1 && segmentCount != 0) {
                if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + segmentCount) - 1) {
                    this.missingLastSegment = true;
                    return true;
                }
            }
        }
        int iIndexOf = this.trackSelection.indexOf(chunk.trackFormat);
        RepresentationHolder representationHolder2 = this.representationHolders[iIndexOf];
        LoadErrorHandlingPolicy.FallbackOptions fallbackOptionsCreateFallbackOptions = createFallbackOptions(this.trackSelection, representationHolder2.representation.baseUrls);
        if ((!fallbackOptionsCreateFallbackOptions.isFallbackAvailable(2) && !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(1)) || (fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(fallbackOptionsCreateFallbackOptions, loadErrorInfo)) == null) {
            return false;
        }
        if (fallbackSelectionFor.type == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            return exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs);
        }
        if (fallbackSelectionFor.type != 1) {
            return false;
        }
        this.baseUrlExclusionList.exclude(representationHolder2.selectedBaseUrl, fallbackSelectionFor.exclusionDurationMs);
        boolean z = false;
        while (true) {
            RepresentationHolder[] representationHolderArr = this.representationHolders;
            if (i >= representationHolderArr.length) {
                return z;
            }
            BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolderArr[i].representation.baseUrls);
            if (baseUrlSelectBaseUrl != null) {
                if (i == iIndexOf) {
                    z = true;
                }
                RepresentationHolder[] representationHolderArr2 = this.representationHolders;
                representationHolderArr2[i] = representationHolderArr2[i].copyWithNewSelectedBaseUrl(baseUrlSelectBaseUrl);
            }
            i++;
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            ChunkExtractor chunkExtractor = representationHolder.chunkExtractor;
            if (chunkExtractor != null) {
                chunkExtractor.release();
            }
        }
    }

    private LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection trackSelection, List<BaseUrl> baseUrls) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = trackSelection.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (trackSelection.isBlacklisted(i2, jElapsedRealtime)) {
                i++;
            }
        }
        int priorityCount = BaseUrlExclusionList.getPriorityCount(baseUrls);
        return new LoadErrorHandlingPolicy.FallbackOptions(priorityCount, priorityCount - this.baseUrlExclusionList.getPriorityCountAfterExclusion(baseUrls), length, i);
    }

    private long getSegmentNum(RepresentationHolder representationHolder, MediaChunk previousChunk, long loadPositionUs, long firstAvailableSegmentNum, long lastAvailableSegmentNum) {
        if (previousChunk != null) {
            return previousChunk.getNextChunkIndex();
        }
        return Util.constrainValue(representationHolder.getSegmentNum(loadPositionUs), firstAvailableSegmentNum, lastAvailableSegmentNum);
    }

    private ArrayList<Representation> getRepresentations() {
        List<AdaptationSet> list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList<>();
        for (int i : this.adaptationSetIndices) {
            arrayList.addAll(list.get(i).representations);
        }
        return arrayList;
    }

    private long getAvailableLiveDurationUs(long nowUnixTimeUs, long playbackPositionUs) {
        if (!this.manifest.dynamic) {
            return C.TIME_UNSET;
        }
        return Math.max(0L, Math.min(getNowPeriodTimeUs(nowUnixTimeUs), this.representationHolders[0].getSegmentEndTimeUs(this.representationHolders[0].getLastAvailableSegmentNum(nowUnixTimeUs))) - playbackPositionUs);
    }

    private long getNowPeriodTimeUs(long nowUnixTimeUs) {
        return this.manifest.availabilityStartTimeMs == C.TIME_UNSET ? C.TIME_UNSET : nowUnixTimeUs - C.msToUs(this.manifest.availabilityStartTimeMs + this.manifest.getPeriod(this.periodIndex).startMs);
    }

    protected Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource, Format trackFormat, int trackSelectionReason, Object trackSelectionData, RangedUri initializationUri, RangedUri indexUri) {
        RangedUri rangedUri = initializationUri;
        Representation representation = representationHolder.representation;
        if (rangedUri != null) {
            RangedUri rangedUriAttemptMerge = rangedUri.attemptMerge(indexUri, representationHolder.selectedBaseUrl.url);
            if (rangedUriAttemptMerge != null) {
                rangedUri = rangedUriAttemptMerge;
            }
        } else {
            rangedUri = indexUri;
        }
        return new InitializationChunk(dataSource, DashUtil.buildDataSpec(representationHolder.selectedBaseUrl.url, rangedUri, representation.getCacheKey(), 0), trackFormat, trackSelectionReason, trackSelectionData, representationHolder.chunkExtractor);
    }

    protected Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long firstSegmentNum, int maxSegmentCount, long seekTimeUs, long nowPeriodTimeUs) {
        Representation representation = representationHolder.representation;
        long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(firstSegmentNum);
        RangedUri segmentUrl = representationHolder.getSegmentUrl(firstSegmentNum);
        if (representationHolder.chunkExtractor == null) {
            return new SingleSampleMediaChunk(dataSource, DashUtil.buildDataSpec(representationHolder.selectedBaseUrl.url, segmentUrl, representation.getCacheKey(), representationHolder.isSegmentAvailableAtFullNetworkSpeed(firstSegmentNum, nowPeriodTimeUs) ? 0 : 8), trackFormat, trackSelectionReason, trackSelectionData, segmentStartTimeUs, representationHolder.getSegmentEndTimeUs(firstSegmentNum), firstSegmentNum, trackType, trackFormat);
        }
        int i = 1;
        int i2 = 1;
        while (i < maxSegmentCount) {
            RangedUri rangedUriAttemptMerge = segmentUrl.attemptMerge(representationHolder.getSegmentUrl(i + firstSegmentNum), representationHolder.selectedBaseUrl.url);
            if (rangedUriAttemptMerge == null) {
                break;
            }
            i2++;
            i++;
            segmentUrl = rangedUriAttemptMerge;
        }
        long j = (i2 + firstSegmentNum) - 1;
        long segmentEndTimeUs = representationHolder.getSegmentEndTimeUs(j);
        long j2 = representationHolder.periodDurationUs;
        return new ContainerMediaChunk(dataSource, DashUtil.buildDataSpec(representationHolder.selectedBaseUrl.url, segmentUrl, representation.getCacheKey(), representationHolder.isSegmentAvailableAtFullNetworkSpeed(j, nowPeriodTimeUs) ? 0 : 8), trackFormat, trackSelectionReason, trackSelectionData, segmentStartTimeUs, segmentEndTimeUs, seekTimeUs, (j2 == C.TIME_UNSET || j2 > segmentEndTimeUs) ? -9223372036854775807L : j2, firstSegmentNum, i2, -representation.presentationTimeOffsetUs, representationHolder.chunkExtractor);
    }

    protected static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final long nowPeriodTimeUs;
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representation, long firstAvailableSegmentNum, long lastAvailableSegmentNum, long nowPeriodTimeUs) {
            super(firstAvailableSegmentNum, lastAvailableSegmentNum);
            this.representationHolder = representation;
            this.nowPeriodTimeUs = nowPeriodTimeUs;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            long currentIndex = getCurrentIndex();
            return DashUtil.buildDataSpec(this.representationHolder.selectedBaseUrl.url, this.representationHolder.getSegmentUrl(currentIndex), this.representationHolder.representation.getCacheKey(), this.representationHolder.isSegmentAvailableAtFullNetworkSpeed(currentIndex, this.nowPeriodTimeUs) ? 0 : 8);
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentStartTimeUs(getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentEndTimeUs(getCurrentIndex());
        }
    }

    protected static final class RepresentationHolder {
        final ChunkExtractor chunkExtractor;
        private final long periodDurationUs;
        public final Representation representation;
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;
        public final BaseUrl selectedBaseUrl;

        RepresentationHolder(long periodDurationUs, Representation representation, BaseUrl selectedBaseUrl, ChunkExtractor chunkExtractor, long segmentNumShift, DashSegmentIndex segmentIndex) {
            this.periodDurationUs = periodDurationUs;
            this.representation = representation;
            this.selectedBaseUrl = selectedBaseUrl;
            this.segmentNumShift = segmentNumShift;
            this.chunkExtractor = chunkExtractor;
            this.segmentIndex = segmentIndex;
        }

        RepresentationHolder copyWithNewRepresentation(long newPeriodDurationUs, Representation newRepresentation) throws BehindLiveWindowException {
            long segmentNum;
            long segmentNum2;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = newRepresentation.getIndex();
            if (index == null) {
                return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index);
            }
            if (!index.isExplicit()) {
                return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index2);
            }
            long segmentCount = index.getSegmentCount(newPeriodDurationUs);
            if (segmentCount == 0) {
                return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index2);
            }
            long firstSegmentNum = index.getFirstSegmentNum();
            long timeUs = index.getTimeUs(firstSegmentNum);
            long j = (segmentCount + firstSegmentNum) - 1;
            long timeUs2 = index.getTimeUs(j) + index.getDurationUs(j, newPeriodDurationUs);
            long firstSegmentNum2 = index2.getFirstSegmentNum();
            long timeUs3 = index2.getTimeUs(firstSegmentNum2);
            long j2 = this.segmentNumShift;
            if (timeUs2 == timeUs3) {
                segmentNum = j + 1;
            } else {
                if (timeUs2 < timeUs3) {
                    throw new BehindLiveWindowException();
                }
                if (timeUs3 < timeUs) {
                    segmentNum2 = j2 - (index2.getSegmentNum(timeUs, newPeriodDurationUs) - firstSegmentNum);
                    return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.selectedBaseUrl, this.chunkExtractor, segmentNum2, index2);
                }
                segmentNum = index.getSegmentNum(timeUs3, newPeriodDurationUs);
            }
            segmentNum2 = j2 + (segmentNum - firstSegmentNum2);
            return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.selectedBaseUrl, this.chunkExtractor, segmentNum2, index2);
        }

        RepresentationHolder copyWithNewSegmentIndex(DashSegmentIndex segmentIndex) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, segmentIndex);
        }

        RepresentationHolder copyWithNewSelectedBaseUrl(BaseUrl selectedBaseUrl) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, this.segmentIndex);
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public long getFirstAvailableSegmentNum(long nowUnixTimeUs) {
            return this.segmentIndex.getFirstAvailableSegmentNum(this.periodDurationUs, nowUnixTimeUs) + this.segmentNumShift;
        }

        public long getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentStartTimeUs(long segmentNum) {
            return this.segmentIndex.getTimeUs(segmentNum - this.segmentNumShift);
        }

        public long getSegmentEndTimeUs(long segmentNum) {
            return getSegmentStartTimeUs(segmentNum) + this.segmentIndex.getDurationUs(segmentNum - this.segmentNumShift, this.periodDurationUs);
        }

        public long getSegmentNum(long positionUs) {
            return this.segmentIndex.getSegmentNum(positionUs, this.periodDurationUs) + this.segmentNumShift;
        }

        public RangedUri getSegmentUrl(long segmentNum) {
            return this.segmentIndex.getSegmentUrl(segmentNum - this.segmentNumShift);
        }

        public long getLastAvailableSegmentNum(long nowUnixTimeUs) {
            return (getFirstAvailableSegmentNum(nowUnixTimeUs) + this.segmentIndex.getAvailableSegmentCount(this.periodDurationUs, nowUnixTimeUs)) - 1;
        }

        public boolean isSegmentAvailableAtFullNetworkSpeed(long segmentNum, long nowPeriodTimeUs) {
            return this.segmentIndex.isExplicit() || nowPeriodTimeUs == C.TIME_UNSET || getSegmentEndTimeUs(segmentNum) <= nowPeriodTimeUs;
        }
    }
}
