package com.google.android.exoplayer2.source.dash;

import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.Descriptor;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class DashMediaPeriod implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<DashChunkSource>>, ChunkSampleStream.ReleaseCallback<DashChunkSource> {
    private static final Pattern CEA608_SERVICE_DESCRIPTOR_REGEX = Pattern.compile("CC([1-4])=(.+)");
    private static final Pattern CEA708_SERVICE_DESCRIPTOR_REGEX = Pattern.compile("([1-4])=lang:(\\w+)(,.+)?");
    private final Allocator allocator;
    private final BaseUrlExclusionList baseUrlExclusionList;
    private MediaPeriod.Callback callback;
    private final DashChunkSource.Factory chunkSourceFactory;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private final long elapsedRealtimeOffsetMs;
    private List<EventStream> eventStreams;
    final int id;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
    private int periodIndex;
    private final PlayerEmsgHandler playerEmsgHandler;
    private final TrackGroupInfo[] trackGroupInfos;
    private final TrackGroupArray trackGroups;
    private final TransferListener transferListener;
    private ChunkSampleStream<DashChunkSource>[] sampleStreams = newSampleStreamArray(0);
    private EventSampleStream[] eventSampleStreams = new EventSampleStream[0];
    private final IdentityHashMap<ChunkSampleStream<DashChunkSource>, PlayerEmsgHandler.PlayerTrackEmsgHandler> trackEmsgHandlerBySampleStream = new IdentityHashMap<>();

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    public DashMediaPeriod(int id, DashManifest manifest, BaseUrlExclusionList baseUrlExclusionList, int periodIndex, DashChunkSource.Factory chunkSourceFactory, TransferListener transferListener, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher drmEventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher, long elapsedRealtimeOffsetMs, LoaderErrorThrower manifestLoaderErrorThrower, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback) {
        this.id = id;
        this.manifest = manifest;
        this.baseUrlExclusionList = baseUrlExclusionList;
        this.periodIndex = periodIndex;
        this.chunkSourceFactory = chunkSourceFactory;
        this.transferListener = transferListener;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = drmEventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.mediaSourceEventDispatcher = mediaSourceEventDispatcher;
        this.elapsedRealtimeOffsetMs = elapsedRealtimeOffsetMs;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.playerEmsgHandler = new PlayerEmsgHandler(manifest, playerEmsgCallback, allocator);
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        Period period = manifest.getPeriod(periodIndex);
        this.eventStreams = period.eventStreams;
        Pair<TrackGroupArray, TrackGroupInfo[]> pairBuildTrackGroups = buildTrackGroups(drmSessionManager, period.adaptationSets, this.eventStreams);
        this.trackGroups = (TrackGroupArray) pairBuildTrackGroups.first;
        this.trackGroupInfos = (TrackGroupInfo[]) pairBuildTrackGroups.second;
    }

    public void updateManifest(DashManifest manifest, int periodIndex) {
        this.manifest = manifest;
        this.periodIndex = periodIndex;
        this.playerEmsgHandler.updateManifest(manifest);
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArr = this.sampleStreams;
        if (chunkSampleStreamArr != null) {
            for (ChunkSampleStream<DashChunkSource> chunkSampleStream : chunkSampleStreamArr) {
                ((DashChunkSource) chunkSampleStream.getChunkSource()).updateManifest(manifest, periodIndex);
            }
            this.callback.onContinueLoadingRequested(this);
        }
        this.eventStreams = manifest.getPeriod(periodIndex).eventStreams;
        for (EventSampleStream eventSampleStream : this.eventSampleStreams) {
            Iterator<EventStream> it = this.eventStreams.iterator();
            while (true) {
                if (it.hasNext()) {
                    EventStream next = it.next();
                    if (next.id().equals(eventSampleStream.eventStreamId())) {
                        eventSampleStream.updateEventStream(next, manifest.dynamic && periodIndex == manifest.getPeriodCount() - 1);
                    }
                }
            }
        }
    }

    public void release() {
        this.playerEmsgHandler.release();
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.release(this);
        }
        this.callback = null;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSampleStream.ReleaseCallback
    public synchronized void onSampleStreamReleased(ChunkSampleStream<DashChunkSource> stream) {
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandlerRemove = this.trackEmsgHandlerBySampleStream.remove(stream);
        if (playerTrackEmsgHandlerRemove != null) {
            playerTrackEmsgHandlerRemove.release();
        }
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
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
        List<AdaptationSet> list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList arrayList = new ArrayList();
        for (ExoTrackSelection exoTrackSelection : trackSelections) {
            TrackGroupInfo trackGroupInfo = this.trackGroupInfos[this.trackGroups.indexOf(exoTrackSelection.getTrackGroup())];
            if (trackGroupInfo.trackGroupCategory == 0) {
                int[] iArr = trackGroupInfo.adaptationSetIndices;
                int length = exoTrackSelection.length();
                int[] iArr2 = new int[length];
                for (int i = 0; i < exoTrackSelection.length(); i++) {
                    iArr2[i] = exoTrackSelection.getIndexInTrackGroup(i);
                }
                Arrays.sort(iArr2);
                int size = list.get(iArr[0]).representations.size();
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < length; i4++) {
                    int i5 = iArr2[i4];
                    while (true) {
                        int i6 = i3 + size;
                        if (i5 >= i6) {
                            i2++;
                            size = list.get(iArr[i2]).representations.size();
                            i3 = i6;
                        }
                    }
                    arrayList.add(new StreamKey(this.periodIndex, iArr[i2], i5 - i3));
                }
            }
        }
        return arrayList;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int[] streamIndexToTrackGroupIndex = getStreamIndexToTrackGroupIndex(selections);
        releaseDisabledStreams(selections, mayRetainStreamFlags, streams);
        releaseOrphanEmbeddedStreams(selections, streams, streamIndexToTrackGroupIndex);
        selectNewStreams(selections, streams, streamResetFlags, positionUs, streamIndexToTrackGroupIndex);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (SampleStream sampleStream : streams) {
            if (sampleStream instanceof ChunkSampleStream) {
                arrayList.add((ChunkSampleStream) sampleStream);
            } else if (sampleStream instanceof EventSampleStream) {
                arrayList2.add((EventSampleStream) sampleStream);
            }
        }
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArrNewSampleStreamArray = newSampleStreamArray(arrayList.size());
        this.sampleStreams = chunkSampleStreamArrNewSampleStreamArray;
        arrayList.toArray(chunkSampleStreamArrNewSampleStreamArray);
        EventSampleStream[] eventSampleStreamArr = new EventSampleStream[arrayList2.size()];
        this.eventSampleStreams = eventSampleStreamArr;
        arrayList2.toArray(eventSampleStreamArr);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
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
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.seekToUs(positionUs);
        }
        for (EventSampleStream eventSampleStream : this.eventSampleStreams) {
            eventSampleStream.seekToUs(positionUs);
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(positionUs, seekParameters);
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> sampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private int[] getStreamIndexToTrackGroupIndex(ExoTrackSelection[] selections) {
        int[] iArr = new int[selections.length];
        for (int i = 0; i < selections.length; i++) {
            ExoTrackSelection exoTrackSelection = selections[i];
            if (exoTrackSelection != null) {
                iArr[i] = this.trackGroups.indexOf(exoTrackSelection.getTrackGroup());
            } else {
                iArr[i] = -1;
            }
        }
        return iArr;
    }

    private void releaseDisabledStreams(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams) {
        for (int i = 0; i < selections.length; i++) {
            if (selections[i] == null || !mayRetainStreamFlags[i]) {
                SampleStream sampleStream = streams[i];
                if (sampleStream instanceof ChunkSampleStream) {
                    ((ChunkSampleStream) sampleStream).release(this);
                } else if (sampleStream instanceof ChunkSampleStream.EmbeddedSampleStream) {
                    ((ChunkSampleStream.EmbeddedSampleStream) sampleStream).release();
                }
                streams[i] = null;
            }
        }
    }

    private void releaseOrphanEmbeddedStreams(ExoTrackSelection[] selections, SampleStream[] streams, int[] streamIndexToTrackGroupIndex) {
        boolean z;
        for (int i = 0; i < selections.length; i++) {
            SampleStream sampleStream = streams[i];
            if ((sampleStream instanceof EmptySampleStream) || (sampleStream instanceof ChunkSampleStream.EmbeddedSampleStream)) {
                int primaryStreamIndex = getPrimaryStreamIndex(i, streamIndexToTrackGroupIndex);
                if (primaryStreamIndex == -1) {
                    z = streams[i] instanceof EmptySampleStream;
                } else {
                    SampleStream sampleStream2 = streams[i];
                    z = (sampleStream2 instanceof ChunkSampleStream.EmbeddedSampleStream) && ((ChunkSampleStream.EmbeddedSampleStream) sampleStream2).parent == streams[primaryStreamIndex];
                }
                if (!z) {
                    SampleStream sampleStream3 = streams[i];
                    if (sampleStream3 instanceof ChunkSampleStream.EmbeddedSampleStream) {
                        ((ChunkSampleStream.EmbeddedSampleStream) sampleStream3).release();
                    }
                    streams[i] = null;
                }
            }
        }
    }

    private void selectNewStreams(ExoTrackSelection[] selections, SampleStream[] streams, boolean[] streamResetFlags, long positionUs, int[] streamIndexToTrackGroupIndex) {
        for (int i = 0; i < selections.length; i++) {
            ExoTrackSelection exoTrackSelection = selections[i];
            if (exoTrackSelection != null) {
                SampleStream sampleStream = streams[i];
                if (sampleStream == null) {
                    streamResetFlags[i] = true;
                    TrackGroupInfo trackGroupInfo = this.trackGroupInfos[streamIndexToTrackGroupIndex[i]];
                    if (trackGroupInfo.trackGroupCategory == 0) {
                        streams[i] = buildSampleStream(trackGroupInfo, exoTrackSelection, positionUs);
                    } else if (trackGroupInfo.trackGroupCategory == 2) {
                        streams[i] = new EventSampleStream(this.eventStreams.get(trackGroupInfo.eventStreamGroupIndex), exoTrackSelection.getTrackGroup().getFormat(0), this.manifest.dynamic);
                    }
                } else if (sampleStream instanceof ChunkSampleStream) {
                    ((DashChunkSource) ((ChunkSampleStream) sampleStream).getChunkSource()).updateTrackSelection(exoTrackSelection);
                }
            }
        }
        for (int i2 = 0; i2 < selections.length; i2++) {
            if (streams[i2] == null && selections[i2] != null) {
                TrackGroupInfo trackGroupInfo2 = this.trackGroupInfos[streamIndexToTrackGroupIndex[i2]];
                if (trackGroupInfo2.trackGroupCategory == 1) {
                    int primaryStreamIndex = getPrimaryStreamIndex(i2, streamIndexToTrackGroupIndex);
                    if (primaryStreamIndex == -1) {
                        streams[i2] = new EmptySampleStream();
                    } else {
                        streams[i2] = ((ChunkSampleStream) streams[primaryStreamIndex]).selectEmbeddedTrack(positionUs, trackGroupInfo2.trackType);
                    }
                }
            }
        }
    }

    private int getPrimaryStreamIndex(int embeddedStreamIndex, int[] streamIndexToTrackGroupIndex) {
        int i = streamIndexToTrackGroupIndex[embeddedStreamIndex];
        if (i == -1) {
            return -1;
        }
        int i2 = this.trackGroupInfos[i].primaryTrackGroupIndex;
        for (int i3 = 0; i3 < streamIndexToTrackGroupIndex.length; i3++) {
            int i4 = streamIndexToTrackGroupIndex[i3];
            if (i4 == i2 && this.trackGroupInfos[i4].trackGroupCategory == 0) {
                return i3;
            }
        }
        return -1;
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> buildTrackGroups(DrmSessionManager drmSessionManager, List<AdaptationSet> adaptationSets, List<EventStream> eventStreams) {
        int[][] groupedAdaptationSetIndices = getGroupedAdaptationSetIndices(adaptationSets);
        int length = groupedAdaptationSetIndices.length;
        boolean[] zArr = new boolean[length];
        Format[][] formatArr = new Format[length][];
        int iIdentifyEmbeddedTracks = identifyEmbeddedTracks(length, adaptationSets, groupedAdaptationSetIndices, zArr, formatArr) + length + eventStreams.size();
        TrackGroup[] trackGroupArr = new TrackGroup[iIdentifyEmbeddedTracks];
        TrackGroupInfo[] trackGroupInfoArr = new TrackGroupInfo[iIdentifyEmbeddedTracks];
        buildManifestEventTrackGroupInfos(eventStreams, trackGroupArr, trackGroupInfoArr, buildPrimaryAndEmbeddedTrackGroupInfos(drmSessionManager, adaptationSets, groupedAdaptationSetIndices, length, zArr, formatArr, trackGroupArr, trackGroupInfoArr));
        return Pair.create(new TrackGroupArray(trackGroupArr), trackGroupInfoArr);
    }

    private static int[][] getGroupedAdaptationSetIndices(List<AdaptationSet> adaptationSets) {
        int iMin;
        Descriptor descriptorFindAdaptationSetSwitchingProperty;
        int size = adaptationSets.size();
        SparseIntArray sparseIntArray = new SparseIntArray(size);
        ArrayList arrayList = new ArrayList(size);
        SparseArray sparseArray = new SparseArray(size);
        for (int i = 0; i < size; i++) {
            sparseIntArray.put(adaptationSets.get(i).id, i);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(Integer.valueOf(i));
            arrayList.add(arrayList2);
            sparseArray.put(i, arrayList2);
        }
        for (int i2 = 0; i2 < size; i2++) {
            AdaptationSet adaptationSet = adaptationSets.get(i2);
            Descriptor descriptorFindTrickPlayProperty = findTrickPlayProperty(adaptationSet.essentialProperties);
            if (descriptorFindTrickPlayProperty == null) {
                descriptorFindTrickPlayProperty = findTrickPlayProperty(adaptationSet.supplementalProperties);
            }
            if (descriptorFindTrickPlayProperty == null || (iMin = sparseIntArray.get(Integer.parseInt(descriptorFindTrickPlayProperty.value), -1)) == -1) {
                iMin = i2;
            }
            if (iMin == i2 && (descriptorFindAdaptationSetSwitchingProperty = findAdaptationSetSwitchingProperty(adaptationSet.supplementalProperties)) != null) {
                for (String str : Util.split(descriptorFindAdaptationSetSwitchingProperty.value, ",")) {
                    int i3 = sparseIntArray.get(Integer.parseInt(str), -1);
                    if (i3 != -1) {
                        iMin = Math.min(iMin, i3);
                    }
                }
            }
            if (iMin != i2) {
                List list = (List) sparseArray.get(i2);
                List list2 = (List) sparseArray.get(iMin);
                list2.addAll(list);
                sparseArray.put(i2, list2);
                arrayList.remove(list);
            }
        }
        int size2 = arrayList.size();
        int[][] iArr = new int[size2][];
        for (int i4 = 0; i4 < size2; i4++) {
            int[] array = Ints.toArray((Collection) arrayList.get(i4));
            iArr[i4] = array;
            Arrays.sort(array);
        }
        return iArr;
    }

    private static int identifyEmbeddedTracks(int primaryGroupCount, List<AdaptationSet> adaptationSets, int[][] groupedAdaptationSetIndices, boolean[] primaryGroupHasEventMessageTrackFlags, Format[][] primaryGroupClosedCaptionTrackFormats) {
        int i = 0;
        for (int i2 = 0; i2 < primaryGroupCount; i2++) {
            if (hasEventMessageTrack(adaptationSets, groupedAdaptationSetIndices[i2])) {
                primaryGroupHasEventMessageTrackFlags[i2] = true;
                i++;
            }
            Format[] closedCaptionTrackFormats = getClosedCaptionTrackFormats(adaptationSets, groupedAdaptationSetIndices[i2]);
            primaryGroupClosedCaptionTrackFormats[i2] = closedCaptionTrackFormats;
            if (closedCaptionTrackFormats.length != 0) {
                i++;
            }
        }
        return i;
    }

    private static int buildPrimaryAndEmbeddedTrackGroupInfos(DrmSessionManager drmSessionManager, List<AdaptationSet> adaptationSets, int[][] groupedAdaptationSetIndices, int primaryGroupCount, boolean[] primaryGroupHasEventMessageTrackFlags, Format[][] primaryGroupClosedCaptionTrackFormats, TrackGroup[] trackGroups, TrackGroupInfo[] trackGroupInfos) {
        int i;
        int i2;
        int i3 = 0;
        int i4 = 0;
        while (i3 < primaryGroupCount) {
            int[] iArr = groupedAdaptationSetIndices[i3];
            ArrayList arrayList = new ArrayList();
            for (int i5 : iArr) {
                arrayList.addAll(adaptationSets.get(i5).representations);
            }
            int size = arrayList.size();
            Format[] formatArr = new Format[size];
            for (int i6 = 0; i6 < size; i6++) {
                Format format = ((Representation) arrayList.get(i6)).format;
                formatArr[i6] = format.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(format));
            }
            AdaptationSet adaptationSet = adaptationSets.get(iArr[0]);
            int i7 = i4 + 1;
            if (primaryGroupHasEventMessageTrackFlags[i3]) {
                i = i7 + 1;
            } else {
                i = i7;
                i7 = -1;
            }
            if (primaryGroupClosedCaptionTrackFormats[i3].length != 0) {
                i2 = i + 1;
            } else {
                i2 = i;
                i = -1;
            }
            trackGroups[i4] = new TrackGroup(formatArr);
            trackGroupInfos[i4] = TrackGroupInfo.primaryTrack(adaptationSet.type, iArr, i4, i7, i);
            if (i7 != -1) {
                trackGroups[i7] = new TrackGroup(new Format.Builder().setId(new StringBuilder(16).append(adaptationSet.id).append(":emsg").toString()).setSampleMimeType(MimeTypes.APPLICATION_EMSG).build());
                trackGroupInfos[i7] = TrackGroupInfo.embeddedEmsgTrack(iArr, i4);
            }
            if (i != -1) {
                trackGroups[i] = new TrackGroup(primaryGroupClosedCaptionTrackFormats[i3]);
                trackGroupInfos[i] = TrackGroupInfo.embeddedClosedCaptionTrack(iArr, i4);
            }
            i3++;
            i4 = i2;
        }
        return i4;
    }

    private static void buildManifestEventTrackGroupInfos(List<EventStream> eventStreams, TrackGroup[] trackGroups, TrackGroupInfo[] trackGroupInfos, int existingTrackGroupCount) {
        int i = 0;
        while (i < eventStreams.size()) {
            trackGroups[existingTrackGroupCount] = new TrackGroup(new Format.Builder().setId(eventStreams.get(i).id()).setSampleMimeType(MimeTypes.APPLICATION_EMSG).build());
            trackGroupInfos[existingTrackGroupCount] = TrackGroupInfo.mpdEventTrack(i);
            i++;
            existingTrackGroupCount++;
        }
    }

    private ChunkSampleStream<DashChunkSource> buildSampleStream(TrackGroupInfo trackGroupInfo, ExoTrackSelection selection, long positionUs) {
        TrackGroup trackGroup;
        int i;
        TrackGroup trackGroup2;
        int i2;
        boolean z = trackGroupInfo.embeddedEventMessageTrackGroupIndex != -1;
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandlerNewPlayerTrackEmsgHandler = null;
        if (z) {
            trackGroup = this.trackGroups.get(trackGroupInfo.embeddedEventMessageTrackGroupIndex);
            i = 1;
        } else {
            trackGroup = null;
            i = 0;
        }
        boolean z2 = trackGroupInfo.embeddedClosedCaptionTrackGroupIndex != -1;
        if (z2) {
            trackGroup2 = this.trackGroups.get(trackGroupInfo.embeddedClosedCaptionTrackGroupIndex);
            i += trackGroup2.length;
        } else {
            trackGroup2 = null;
        }
        Format[] formatArr = new Format[i];
        int[] iArr = new int[i];
        if (z) {
            formatArr[0] = trackGroup.getFormat(0);
            iArr[0] = 5;
            i2 = 1;
        } else {
            i2 = 0;
        }
        ArrayList arrayList = new ArrayList();
        if (z2) {
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                Format format = trackGroup2.getFormat(i3);
                formatArr[i2] = format;
                iArr[i2] = 3;
                arrayList.add(format);
                i2++;
            }
        }
        if (this.manifest.dynamic && z) {
            playerTrackEmsgHandlerNewPlayerTrackEmsgHandler = this.playerEmsgHandler.newPlayerTrackEmsgHandler();
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = playerTrackEmsgHandlerNewPlayerTrackEmsgHandler;
        ChunkSampleStream<DashChunkSource> chunkSampleStream = new ChunkSampleStream<>(trackGroupInfo.trackType, iArr, formatArr, this.chunkSourceFactory.createDashChunkSource(this.manifestLoaderErrorThrower, this.manifest, this.baseUrlExclusionList, this.periodIndex, trackGroupInfo.adaptationSetIndices, selection, trackGroupInfo.trackType, this.elapsedRealtimeOffsetMs, z, arrayList, playerTrackEmsgHandler, this.transferListener), this, this.allocator, positionUs, this.drmSessionManager, this.drmEventDispatcher, this.loadErrorHandlingPolicy, this.mediaSourceEventDispatcher);
        synchronized (this) {
            this.trackEmsgHandlerBySampleStream.put(chunkSampleStream, playerTrackEmsgHandler);
        }
        return chunkSampleStream;
    }

    private static Descriptor findAdaptationSetSwitchingProperty(List<Descriptor> descriptors) {
        return findDescriptor(descriptors, "urn:mpeg:dash:adaptation-set-switching:2016");
    }

    private static Descriptor findTrickPlayProperty(List<Descriptor> descriptors) {
        return findDescriptor(descriptors, "http://dashif.org/guidelines/trickmode");
    }

    private static Descriptor findDescriptor(List<Descriptor> descriptors, String schemeIdUri) {
        for (int i = 0; i < descriptors.size(); i++) {
            Descriptor descriptor = descriptors.get(i);
            if (schemeIdUri.equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    private static boolean hasEventMessageTrack(List<AdaptationSet> adaptationSets, int[] adaptationSetIndices) {
        for (int i : adaptationSetIndices) {
            List<Representation> list = adaptationSets.get(i).representations;
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (!list.get(i2).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Format[] getClosedCaptionTrackFormats(List<AdaptationSet> adaptationSets, int[] adaptationSetIndices) {
        for (int i : adaptationSetIndices) {
            AdaptationSet adaptationSet = adaptationSets.get(i);
            List<Descriptor> list = adaptationSets.get(i).accessibilityDescriptors;
            for (int i2 = 0; i2 < list.size(); i2++) {
                Descriptor descriptor = list.get(i2);
                if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri)) {
                    return parseClosedCaptionDescriptor(descriptor, CEA608_SERVICE_DESCRIPTOR_REGEX, new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608).setId(new StringBuilder(18).append(adaptationSet.id).append(":cea608").toString()).build());
                }
                if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri)) {
                    return parseClosedCaptionDescriptor(descriptor, CEA708_SERVICE_DESCRIPTOR_REGEX, new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA708).setId(new StringBuilder(18).append(adaptationSet.id).append(":cea708").toString()).build());
                }
            }
        }
        return new Format[0];
    }

    private static Format[] parseClosedCaptionDescriptor(Descriptor descriptor, Pattern serviceDescriptorRegex, Format baseFormat) throws NumberFormatException {
        String str = descriptor.value;
        if (str == null) {
            return new Format[]{baseFormat};
        }
        String[] strArrSplit = Util.split(str, ";");
        Format[] formatArr = new Format[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            Matcher matcher = serviceDescriptorRegex.matcher(strArrSplit[i]);
            if (!matcher.matches()) {
                return new Format[]{baseFormat};
            }
            int i2 = Integer.parseInt(matcher.group(1));
            Format.Builder builderBuildUpon = baseFormat.buildUpon();
            String str2 = baseFormat.id;
            formatArr[i] = builderBuildUpon.setId(new StringBuilder(String.valueOf(str2).length() + 12).append(str2).append(":").append(i2).toString()).setAccessibilityChannel(i2).setLanguage(matcher.group(2)).build();
        }
        return formatArr;
    }

    private static ChunkSampleStream<DashChunkSource>[] newSampleStreamArray(int length) {
        return new ChunkSampleStream[length];
    }

    private static final class TrackGroupInfo {
        private static final int CATEGORY_EMBEDDED = 1;
        private static final int CATEGORY_MANIFEST_EVENTS = 2;
        private static final int CATEGORY_PRIMARY = 0;
        public final int[] adaptationSetIndices;
        public final int embeddedClosedCaptionTrackGroupIndex;
        public final int embeddedEventMessageTrackGroupIndex;
        public final int eventStreamGroupIndex;
        public final int primaryTrackGroupIndex;
        public final int trackGroupCategory;
        public final int trackType;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface TrackGroupCategory {
        }

        public static TrackGroupInfo primaryTrack(int trackType, int[] adaptationSetIndices, int primaryTrackGroupIndex, int embeddedEventMessageTrackGroupIndex, int embeddedClosedCaptionTrackGroupIndex) {
            return new TrackGroupInfo(trackType, 0, adaptationSetIndices, primaryTrackGroupIndex, embeddedEventMessageTrackGroupIndex, embeddedClosedCaptionTrackGroupIndex, -1);
        }

        public static TrackGroupInfo embeddedEmsgTrack(int[] adaptationSetIndices, int primaryTrackGroupIndex) {
            return new TrackGroupInfo(5, 1, adaptationSetIndices, primaryTrackGroupIndex, -1, -1, -1);
        }

        public static TrackGroupInfo embeddedClosedCaptionTrack(int[] adaptationSetIndices, int primaryTrackGroupIndex) {
            return new TrackGroupInfo(3, 1, adaptationSetIndices, primaryTrackGroupIndex, -1, -1, -1);
        }

        public static TrackGroupInfo mpdEventTrack(int eventStreamIndex) {
            return new TrackGroupInfo(5, 2, new int[0], -1, -1, -1, eventStreamIndex);
        }

        private TrackGroupInfo(int trackType, int trackGroupCategory, int[] adaptationSetIndices, int primaryTrackGroupIndex, int embeddedEventMessageTrackGroupIndex, int embeddedClosedCaptionTrackGroupIndex, int eventStreamGroupIndex) {
            this.trackType = trackType;
            this.adaptationSetIndices = adaptationSetIndices;
            this.trackGroupCategory = trackGroupCategory;
            this.primaryTrackGroupIndex = primaryTrackGroupIndex;
            this.embeddedEventMessageTrackGroupIndex = embeddedEventMessageTrackGroupIndex;
            this.embeddedClosedCaptionTrackGroupIndex = embeddedClosedCaptionTrackGroupIndex;
            this.eventStreamGroupIndex = eventStreamGroupIndex;
        }
    }
}
