package com.google.android.exoplayer2.source.smoothstreaming;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class SsMediaPeriod implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<SsChunkSource>> {
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final SsChunkSource.Factory chunkSourceFactory;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
    private ChunkSampleStream<SsChunkSource>[] sampleStreams;
    private final TrackGroupArray trackGroups;
    private final TransferListener transferListener;

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    public SsMediaPeriod(SsManifest manifest, SsChunkSource.Factory chunkSourceFactory, TransferListener transferListener, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher drmEventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher, LoaderErrorThrower manifestLoaderErrorThrower, Allocator allocator) {
        this.manifest = manifest;
        this.chunkSourceFactory = chunkSourceFactory;
        this.transferListener = transferListener;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = drmEventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.mediaSourceEventDispatcher = mediaSourceEventDispatcher;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.trackGroups = buildTrackGroups(manifest, drmSessionManager);
        ChunkSampleStream<SsChunkSource>[] chunkSampleStreamArrNewSampleStreamArray = newSampleStreamArray(0);
        this.sampleStreams = chunkSampleStreamArrNewSampleStreamArray;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(chunkSampleStreamArrNewSampleStreamArray);
    }

    public void updateManifest(SsManifest manifest) {
        this.manifest = manifest;
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.sampleStreams) {
            ((SsChunkSource) chunkSampleStream.getChunkSource()).updateManifest(manifest);
        }
        this.callback.onContinueLoadingRequested(this);
    }

    public void release() {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.release();
        }
        this.callback = null;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        this.callback = callback;
        callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        ExoTrackSelection exoTrackSelection;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < selections.length; i++) {
            SampleStream sampleStream = streams[i];
            if (sampleStream != null) {
                ChunkSampleStream chunkSampleStream = (ChunkSampleStream) sampleStream;
                if (selections[i] == null || !mayRetainStreamFlags[i]) {
                    chunkSampleStream.release();
                    streams[i] = null;
                } else {
                    ((SsChunkSource) chunkSampleStream.getChunkSource()).updateTrackSelection(selections[i]);
                    arrayList.add(chunkSampleStream);
                }
            }
            if (streams[i] == null && (exoTrackSelection = selections[i]) != null) {
                ChunkSampleStream<SsChunkSource> chunkSampleStreamBuildSampleStream = buildSampleStream(exoTrackSelection, positionUs);
                arrayList.add(chunkSampleStreamBuildSampleStream);
                streams[i] = chunkSampleStreamBuildSampleStream;
                streamResetFlags[i] = true;
            }
        }
        ChunkSampleStream<SsChunkSource>[] chunkSampleStreamArrNewSampleStreamArray = newSampleStreamArray(arrayList.size());
        this.sampleStreams = chunkSampleStreamArrNewSampleStreamArray;
        arrayList.toArray(chunkSampleStreamArrNewSampleStreamArray);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < trackSelections.size(); i++) {
            ExoTrackSelection exoTrackSelection = trackSelections.get(i);
            int iIndexOf = this.trackGroups.indexOf(exoTrackSelection.getTrackGroup());
            for (int i2 = 0; i2 < exoTrackSelection.length(); i2++) {
                arrayList.add(new StreamKey(iIndexOf, exoTrackSelection.getIndexInTrackGroup(i2)));
            }
        }
        return arrayList;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.discardBuffer(positionUs, toKeyframe);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        return this.compositeSequenceableLoader.continueLoading(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.compositeSequenceableLoader.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long positionUs) {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.seekToUs(positionUs);
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.sampleStreams) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(positionUs, seekParameters);
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(ChunkSampleStream<SsChunkSource> sampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private ChunkSampleStream<SsChunkSource> buildSampleStream(ExoTrackSelection selection, long positionUs) {
        int iIndexOf = this.trackGroups.indexOf(selection.getTrackGroup());
        return new ChunkSampleStream<>(this.manifest.streamElements[iIndexOf].type, null, null, this.chunkSourceFactory.createChunkSource(this.manifestLoaderErrorThrower, this.manifest, iIndexOf, selection, this.transferListener), this, this.allocator, positionUs, this.drmSessionManager, this.drmEventDispatcher, this.loadErrorHandlingPolicy, this.mediaSourceEventDispatcher);
    }

    private static TrackGroupArray buildTrackGroups(SsManifest manifest, DrmSessionManager drmSessionManager) {
        TrackGroup[] trackGroupArr = new TrackGroup[manifest.streamElements.length];
        for (int i = 0; i < manifest.streamElements.length; i++) {
            Format[] formatArr = manifest.streamElements[i].formats;
            Format[] formatArr2 = new Format[formatArr.length];
            for (int i2 = 0; i2 < formatArr.length; i2++) {
                Format format = formatArr[i2];
                formatArr2[i2] = format.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(format));
            }
            trackGroupArr[i] = new TrackGroup(formatArr2);
        }
        return new TrackGroupArray(trackGroupArr);
    }

    private static ChunkSampleStream<SsChunkSource>[] newSampleStreamArray(int length) {
        return new ChunkSampleStream[length];
    }
}
