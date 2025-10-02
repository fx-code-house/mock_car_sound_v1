package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class DefaultSsChunkSource implements SsChunkSource {
    private final ChunkExtractor[] chunkExtractors;
    private int currentManifestChunkOffset;
    private final DataSource dataSource;
    private IOException fatalError;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int streamElementIndex;
    private ExoTrackSelection trackSelection;

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
    }

    public static final class Factory implements SsChunkSource.Factory {
        private final DataSource.Factory dataSourceFactory;

        public Factory(DataSource.Factory dataSourceFactory) {
            this.dataSourceFactory = dataSourceFactory;
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory
        public SsChunkSource createChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, SsManifest manifest, int streamElementIndex, ExoTrackSelection trackSelection, TransferListener transferListener) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultSsChunkSource(manifestLoaderErrorThrower, manifest, streamElementIndex, trackSelection, dataSourceCreateDataSource);
        }
    }

    public DefaultSsChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, SsManifest manifest, int streamElementIndex, ExoTrackSelection trackSelection, DataSource dataSource) {
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower;
        this.manifest = manifest;
        this.streamElementIndex = streamElementIndex;
        this.trackSelection = trackSelection;
        this.dataSource = dataSource;
        SsManifest.StreamElement streamElement = manifest.streamElements[streamElementIndex];
        this.chunkExtractors = new ChunkExtractor[trackSelection.length()];
        int i = 0;
        while (i < this.chunkExtractors.length) {
            int indexInTrackGroup = trackSelection.getIndexInTrackGroup(i);
            Format format = streamElement.formats[indexInTrackGroup];
            int i2 = i;
            this.chunkExtractors[i2] = new BundledChunkExtractor(new FragmentedMp4Extractor(3, null, new Track(indexInTrackGroup, streamElement.type, streamElement.timescale, C.TIME_UNSET, manifest.durationUs, format, 0, format.drmInitData != null ? ((SsManifest.ProtectionElement) Assertions.checkNotNull(manifest.protectionElement)).trackEncryptionBoxes : null, streamElement.type == 2 ? 4 : 0, null, null)), streamElement.type, format);
            i = i2 + 1;
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int chunkIndex = streamElement.getChunkIndex(positionUs);
        long startTimeUs = streamElement.getStartTimeUs(chunkIndex);
        return seekParameters.resolveSeekPositionUs(positionUs, startTimeUs, (startTimeUs >= positionUs || chunkIndex >= streamElement.chunkCount + (-1)) ? startTimeUs : streamElement.getStartTimeUs(chunkIndex + 1));
    }

    @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource
    public void updateManifest(SsManifest newManifest) {
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int i = streamElement.chunkCount;
        SsManifest.StreamElement streamElement2 = newManifest.streamElements[this.streamElementIndex];
        if (i == 0 || streamElement2.chunkCount == 0) {
            this.currentManifestChunkOffset += i;
        } else {
            int i2 = i - 1;
            long startTimeUs = streamElement.getStartTimeUs(i2) + streamElement.getChunkDurationUs(i2);
            long startTimeUs2 = streamElement2.getStartTimeUs(0);
            if (startTimeUs <= startTimeUs2) {
                this.currentManifestChunkOffset += i;
            } else {
                this.currentManifestChunkOffset += streamElement.getChunkIndex(startTimeUs2);
            }
        }
        this.manifest = newManifest;
    }

    @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource
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
    public final void getNextChunk(long playbackPositionUs, long loadPositionUs, List<? extends MediaChunk> queue, ChunkHolder out) {
        int nextChunkIndex;
        long j = loadPositionUs;
        if (this.fatalError != null) {
            return;
        }
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        if (streamElement.chunkCount == 0) {
            out.endOfStream = !this.manifest.isLive;
            return;
        }
        if (queue.isEmpty()) {
            nextChunkIndex = streamElement.getChunkIndex(j);
        } else {
            nextChunkIndex = (int) (queue.get(queue.size() - 1).getNextChunkIndex() - this.currentManifestChunkOffset);
            if (nextChunkIndex < 0) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
        }
        if (nextChunkIndex >= streamElement.chunkCount) {
            out.endOfStream = !this.manifest.isLive;
            return;
        }
        long j2 = j - playbackPositionUs;
        long jResolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(playbackPositionUs);
        int length = this.trackSelection.length();
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[length];
        for (int i = 0; i < length; i++) {
            mediaChunkIteratorArr[i] = new StreamElementIterator(streamElement, this.trackSelection.getIndexInTrackGroup(i), nextChunkIndex);
        }
        this.trackSelection.updateSelectedTrack(playbackPositionUs, j2, jResolveTimeToLiveEdgeUs, queue, mediaChunkIteratorArr);
        long startTimeUs = streamElement.getStartTimeUs(nextChunkIndex);
        long chunkDurationUs = startTimeUs + streamElement.getChunkDurationUs(nextChunkIndex);
        if (!queue.isEmpty()) {
            j = C.TIME_UNSET;
        }
        long j3 = j;
        int i2 = nextChunkIndex + this.currentManifestChunkOffset;
        int selectedIndex = this.trackSelection.getSelectedIndex();
        out.chunk = newMediaChunk(this.trackSelection.getSelectedFormat(), this.dataSource, streamElement.buildRequestUri(this.trackSelection.getIndexInTrackGroup(selectedIndex), nextChunkIndex), i2, startTimeUs, chunkDurationUs, j3, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.chunkExtractors[selectedIndex]);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.trackSelection), loadErrorInfo);
        if (cancelable && fallbackSelectionFor != null && fallbackSelectionFor.type == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            if (exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (ChunkExtractor chunkExtractor : this.chunkExtractors) {
            chunkExtractor.release();
        }
    }

    private static MediaChunk newMediaChunk(Format format, DataSource dataSource, Uri uri, int chunkIndex, long chunkStartTimeUs, long chunkEndTimeUs, long chunkSeekTimeUs, int trackSelectionReason, Object trackSelectionData, ChunkExtractor chunkExtractor) {
        return new ContainerMediaChunk(dataSource, new DataSpec(uri), format, trackSelectionReason, trackSelectionData, chunkStartTimeUs, chunkEndTimeUs, chunkSeekTimeUs, C.TIME_UNSET, chunkIndex, 1, chunkStartTimeUs, chunkExtractor);
    }

    private long resolveTimeToLiveEdgeUs(long playbackPositionUs) {
        if (!this.manifest.isLive) {
            return C.TIME_UNSET;
        }
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int i = streamElement.chunkCount - 1;
        return (streamElement.getStartTimeUs(i) + streamElement.getChunkDurationUs(i)) - playbackPositionUs;
    }

    private static final class StreamElementIterator extends BaseMediaChunkIterator {
        private final SsManifest.StreamElement streamElement;
        private final int trackIndex;

        public StreamElementIterator(SsManifest.StreamElement streamElement, int trackIndex, int chunkIndex) {
            super(chunkIndex, streamElement.chunkCount - 1);
            this.streamElement = streamElement;
            this.trackIndex = trackIndex;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            return new DataSpec(this.streamElement.buildRequestUri(this.trackIndex, (int) getCurrentIndex()));
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.streamElement.getStartTimeUs((int) getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            return getChunkStartTimeUs() + this.streamElement.getChunkDurationUs((int) getCurrentIndex());
        }
    }
}
