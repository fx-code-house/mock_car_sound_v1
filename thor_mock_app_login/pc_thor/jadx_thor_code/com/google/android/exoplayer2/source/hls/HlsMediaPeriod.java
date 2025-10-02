package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class HlsMediaPeriod implements MediaPeriod, HlsSampleStreamWrapper.Callback, HlsPlaylistTracker.PlaylistEventListener {
    private final Allocator allocator;
    private final boolean allowChunklessPreparation;
    private int audioVideoSampleStreamWrapperCount;
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final TransferListener mediaTransferListener;
    private final int metadataType;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private TrackGroupArray trackGroups;
    private final boolean useSessionKeys;
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap<>();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private int[][] manifestUrlIndicesPerWrapper = new int[0][];

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    public HlsMediaPeriod(HlsExtractorFactory extractorFactory, HlsPlaylistTracker playlistTracker, HlsDataSourceFactory dataSourceFactory, TransferListener mediaTransferListener, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher drmEventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, boolean allowChunklessPreparation, int metadataType, boolean useSessionKeys) {
        this.extractorFactory = extractorFactory;
        this.playlistTracker = playlistTracker;
        this.dataSourceFactory = dataSourceFactory;
        this.mediaTransferListener = mediaTransferListener;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = drmEventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.allowChunklessPreparation = allowChunklessPreparation;
        this.metadataType = metadataType;
        this.useSessionKeys = useSessionKeys;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
    }

    public void release() {
        this.playlistTracker.removeListener(this);
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.release();
        }
        this.callback = null;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        this.callback = callback;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.maybeThrowPrepareError();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.trackGroups);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [int] */
    /* JADX WARN: Type inference failed for: r15v7 */
    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        int[] iArr;
        TrackGroupArray trackGroups;
        int primaryTrackGroupIndex;
        HlsMediaPeriod hlsMediaPeriod = this;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(hlsMediaPeriod.playlistTracker.getMasterPlaylist());
        boolean z = !hlsMasterPlaylist.variants.isEmpty();
        int length = hlsMediaPeriod.sampleStreamWrappers.length - hlsMasterPlaylist.subtitles.size();
        int i = 0;
        if (z) {
            HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsMediaPeriod.sampleStreamWrappers[0];
            iArr = hlsMediaPeriod.manifestUrlIndicesPerWrapper[0];
            trackGroups = hlsSampleStreamWrapper.getTrackGroups();
            primaryTrackGroupIndex = hlsSampleStreamWrapper.getPrimaryTrackGroupIndex();
        } else {
            iArr = new int[0];
            trackGroups = TrackGroupArray.EMPTY;
            primaryTrackGroupIndex = 0;
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        boolean z3 = false;
        for (ExoTrackSelection exoTrackSelection : list) {
            TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
            int iIndexOf = trackGroups.indexOf(trackGroup);
            if (iIndexOf == -1) {
                ?? r15 = z;
                while (true) {
                    HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = hlsMediaPeriod.sampleStreamWrappers;
                    if (r15 >= hlsSampleStreamWrapperArr.length) {
                        break;
                    }
                    if (hlsSampleStreamWrapperArr[r15].getTrackGroups().indexOf(trackGroup) != -1) {
                        int i2 = r15 < length ? 1 : 2;
                        int[] iArr2 = hlsMediaPeriod.manifestUrlIndicesPerWrapper[r15];
                        for (int i3 = 0; i3 < exoTrackSelection.length(); i3++) {
                            arrayList.add(new StreamKey(i2, iArr2[exoTrackSelection.getIndexInTrackGroup(i3)]));
                        }
                    } else {
                        hlsMediaPeriod = this;
                        r15++;
                    }
                }
            } else if (iIndexOf == primaryTrackGroupIndex) {
                for (int i4 = i; i4 < exoTrackSelection.length(); i4++) {
                    arrayList.add(new StreamKey(i, iArr[exoTrackSelection.getIndexInTrackGroup(i4)]));
                }
                z3 = true;
            } else {
                z2 = true;
            }
            hlsMediaPeriod = this;
            i = 0;
        }
        if (z2 && !z3) {
            int i5 = iArr[0];
            int i6 = hlsMasterPlaylist.variants.get(iArr[0]).format.bitrate;
            for (int i7 = 1; i7 < iArr.length; i7++) {
                int i8 = hlsMasterPlaylist.variants.get(iArr[i7]).format.bitrate;
                if (i8 < i6) {
                    i5 = iArr[i7];
                    i6 = i8;
                }
            }
            arrayList.add(new StreamKey(0, i5));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00df  */
    @Override // com.google.android.exoplayer2.source.MediaPeriod
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r22, boolean[] r23, com.google.android.exoplayer2.source.SampleStream[] r24, boolean[] r25, long r26) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.enabledSampleStreamWrappers) {
            hlsSampleStreamWrapper.discardBuffer(positionUs, toKeyframe);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        if (this.trackGroups == null) {
            for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
                hlsSampleStreamWrapper.continuePreparing();
            }
            return false;
        }
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
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.enabledSampleStreamWrappers;
        if (hlsSampleStreamWrapperArr.length > 0) {
            boolean zSeekToUs = hlsSampleStreamWrapperArr[0].seekToUs(positionUs, false);
            int i = 1;
            while (true) {
                HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = this.enabledSampleStreamWrappers;
                if (i >= hlsSampleStreamWrapperArr2.length) {
                    break;
                }
                hlsSampleStreamWrapperArr2[i].seekToUs(positionUs, zSeekToUs);
                i++;
            }
            if (zSeekToUs) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPrepared() {
        int i = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i;
        if (i > 0) {
            return;
        }
        int i2 = 0;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            i2 += hlsSampleStreamWrapper.getTrackGroups().length;
        }
        TrackGroup[] trackGroupArr = new TrackGroup[i2];
        int i3 = 0;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper2 : this.sampleStreamWrappers) {
            int i4 = hlsSampleStreamWrapper2.getTrackGroups().length;
            int i5 = 0;
            while (i5 < i4) {
                trackGroupArr[i3] = hlsSampleStreamWrapper2.getTrackGroups().get(i5);
                i5++;
                i3++;
            }
        }
        this.trackGroups = new TrackGroupArray(trackGroupArr);
        this.callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPlaylistRefreshRequired(Uri url) {
        this.playlistTracker.refreshPlaylist(url);
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(HlsSampleStreamWrapper sampleStreamWrapper) {
        this.callback.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public void onPlaylistChanged() {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.onPlaylistUpdated();
        }
        this.callback.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public boolean onPlaylistError(Uri url, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean forceRetry) {
        boolean zOnPlaylistError = true;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            zOnPlaylistError &= hlsSampleStreamWrapper.onPlaylistError(url, loadErrorInfo, forceRetry);
        }
        this.callback.onContinueLoadingRequested(this);
        return zOnPlaylistError;
    }

    private void buildAndPrepareSampleStreamWrappers(long positionUs) {
        Map<String, DrmInitData> mapEmptyMap;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist());
        if (this.useSessionKeys) {
            mapEmptyMap = deriveOverridingDrmInitData(hlsMasterPlaylist.sessionKeyDrmInitData);
        } else {
            mapEmptyMap = Collections.emptyMap();
        }
        Map<String, DrmInitData> map = mapEmptyMap;
        boolean z = !hlsMasterPlaylist.variants.isEmpty();
        List<HlsMasterPlaylist.Rendition> list = hlsMasterPlaylist.audios;
        List<HlsMasterPlaylist.Rendition> list2 = hlsMasterPlaylist.subtitles;
        this.pendingPrepareCount = 0;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            buildAndPrepareMainSampleStreamWrapper(hlsMasterPlaylist, positionUs, arrayList, arrayList2, map);
        }
        buildAndPrepareAudioSampleStreamWrappers(positionUs, list, arrayList, arrayList2, map);
        this.audioVideoSampleStreamWrapperCount = arrayList.size();
        int i = 0;
        while (i < list2.size()) {
            HlsMasterPlaylist.Rendition rendition = list2.get(i);
            int i2 = i;
            HlsSampleStreamWrapper hlsSampleStreamWrapperBuildSampleStreamWrapper = buildSampleStreamWrapper(3, new Uri[]{rendition.url}, new Format[]{rendition.format}, null, Collections.emptyList(), map, positionUs);
            arrayList2.add(new int[]{i2});
            arrayList.add(hlsSampleStreamWrapperBuildSampleStreamWrapper);
            hlsSampleStreamWrapperBuildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroup[]{new TrackGroup(rendition.format)}, 0, new int[0]);
            i = i2 + 1;
        }
        this.sampleStreamWrappers = (HlsSampleStreamWrapper[]) arrayList.toArray(new HlsSampleStreamWrapper[0]);
        this.manifestUrlIndicesPerWrapper = (int[][]) arrayList2.toArray(new int[0][]);
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
        this.pendingPrepareCount = hlsSampleStreamWrapperArr.length;
        hlsSampleStreamWrapperArr[0].setIsTimestampMaster(true);
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.continuePreparing();
        }
        this.enabledSampleStreamWrappers = this.sampleStreamWrappers;
    }

    private void buildAndPrepareMainSampleStreamWrapper(HlsMasterPlaylist masterPlaylist, long positionUs, List<HlsSampleStreamWrapper> sampleStreamWrappers, List<int[]> manifestUrlIndicesPerWrapper, Map<String, DrmInitData> overridingDrmInitData) {
        boolean z;
        boolean z2;
        int size = masterPlaylist.variants.size();
        int[] iArr = new int[size];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < masterPlaylist.variants.size(); i3++) {
            Format format = masterPlaylist.variants.get(i3).format;
            if (format.height > 0 || Util.getCodecsOfType(format.codecs, 2) != null) {
                iArr[i3] = 2;
                i++;
            } else if (Util.getCodecsOfType(format.codecs, 1) != null) {
                iArr[i3] = 1;
                i2++;
            } else {
                iArr[i3] = -1;
            }
        }
        if (i > 0) {
            size = i;
            z = true;
            z2 = false;
        } else if (i2 < size) {
            size -= i2;
            z = false;
            z2 = true;
        } else {
            z = false;
            z2 = false;
        }
        Uri[] uriArr = new Uri[size];
        Format[] formatArr = new Format[size];
        int[] iArr2 = new int[size];
        int i4 = 0;
        for (int i5 = 0; i5 < masterPlaylist.variants.size(); i5++) {
            if ((!z || iArr[i5] == 2) && (!z2 || iArr[i5] != 1)) {
                HlsMasterPlaylist.Variant variant = masterPlaylist.variants.get(i5);
                uriArr[i4] = variant.url;
                formatArr[i4] = variant.format;
                iArr2[i4] = i5;
                i4++;
            }
        }
        String str = formatArr[0].codecs;
        int codecCountOfType = Util.getCodecCountOfType(str, 2);
        int codecCountOfType2 = Util.getCodecCountOfType(str, 1);
        boolean z3 = codecCountOfType2 <= 1 && codecCountOfType <= 1 && codecCountOfType2 + codecCountOfType > 0;
        HlsSampleStreamWrapper hlsSampleStreamWrapperBuildSampleStreamWrapper = buildSampleStreamWrapper((z || codecCountOfType2 <= 0) ? 0 : 1, uriArr, formatArr, masterPlaylist.muxedAudioFormat, masterPlaylist.muxedCaptionFormats, overridingDrmInitData, positionUs);
        sampleStreamWrappers.add(hlsSampleStreamWrapperBuildSampleStreamWrapper);
        manifestUrlIndicesPerWrapper.add(iArr2);
        if (this.allowChunklessPreparation && z3) {
            ArrayList arrayList = new ArrayList();
            if (codecCountOfType > 0) {
                Format[] formatArr2 = new Format[size];
                for (int i6 = 0; i6 < size; i6++) {
                    formatArr2[i6] = deriveVideoFormat(formatArr[i6]);
                }
                arrayList.add(new TrackGroup(formatArr2));
                if (codecCountOfType2 > 0 && (masterPlaylist.muxedAudioFormat != null || masterPlaylist.audios.isEmpty())) {
                    arrayList.add(new TrackGroup(deriveAudioFormat(formatArr[0], masterPlaylist.muxedAudioFormat, false)));
                }
                List<Format> list = masterPlaylist.muxedCaptionFormats;
                if (list != null) {
                    for (int i7 = 0; i7 < list.size(); i7++) {
                        arrayList.add(new TrackGroup(list.get(i7)));
                    }
                }
            } else {
                Format[] formatArr3 = new Format[size];
                for (int i8 = 0; i8 < size; i8++) {
                    formatArr3[i8] = deriveAudioFormat(formatArr[i8], masterPlaylist.muxedAudioFormat, true);
                }
                arrayList.add(new TrackGroup(formatArr3));
            }
            TrackGroup trackGroup = new TrackGroup(new Format.Builder().setId("ID3").setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
            arrayList.add(trackGroup);
            hlsSampleStreamWrapperBuildSampleStreamWrapper.prepareWithMasterPlaylistInfo((TrackGroup[]) arrayList.toArray(new TrackGroup[0]), 0, arrayList.indexOf(trackGroup));
        }
    }

    private void buildAndPrepareAudioSampleStreamWrappers(long positionUs, List<HlsMasterPlaylist.Rendition> audioRenditions, List<HlsSampleStreamWrapper> sampleStreamWrappers, List<int[]> manifestUrlsIndicesPerWrapper, Map<String, DrmInitData> overridingDrmInitData) {
        ArrayList arrayList = new ArrayList(audioRenditions.size());
        ArrayList arrayList2 = new ArrayList(audioRenditions.size());
        ArrayList arrayList3 = new ArrayList(audioRenditions.size());
        HashSet hashSet = new HashSet();
        for (int i = 0; i < audioRenditions.size(); i++) {
            String str = audioRenditions.get(i).name;
            if (hashSet.add(str)) {
                arrayList.clear();
                arrayList2.clear();
                arrayList3.clear();
                boolean z = true;
                for (int i2 = 0; i2 < audioRenditions.size(); i2++) {
                    if (Util.areEqual(str, audioRenditions.get(i2).name)) {
                        HlsMasterPlaylist.Rendition rendition = audioRenditions.get(i2);
                        arrayList3.add(Integer.valueOf(i2));
                        arrayList.add(rendition.url);
                        arrayList2.add(rendition.format);
                        z &= Util.getCodecCountOfType(rendition.format.codecs, 1) == 1;
                    }
                }
                HlsSampleStreamWrapper hlsSampleStreamWrapperBuildSampleStreamWrapper = buildSampleStreamWrapper(1, (Uri[]) arrayList.toArray((Uri[]) Util.castNonNullTypeArray(new Uri[0])), (Format[]) arrayList2.toArray(new Format[0]), null, Collections.emptyList(), overridingDrmInitData, positionUs);
                manifestUrlsIndicesPerWrapper.add(Ints.toArray(arrayList3));
                sampleStreamWrappers.add(hlsSampleStreamWrapperBuildSampleStreamWrapper);
                if (this.allowChunklessPreparation && z) {
                    hlsSampleStreamWrapperBuildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroup[]{new TrackGroup((Format[]) arrayList2.toArray(new Format[0]))}, 0, new int[0]);
                }
            }
        }
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int trackType, Uri[] playlistUrls, Format[] playlistFormats, Format muxedAudioFormat, List<Format> muxedCaptionFormats, Map<String, DrmInitData> overridingDrmInitData, long positionUs) {
        return new HlsSampleStreamWrapper(trackType, this, new HlsChunkSource(this.extractorFactory, this.playlistTracker, playlistUrls, playlistFormats, this.dataSourceFactory, this.mediaTransferListener, this.timestampAdjusterProvider, muxedCaptionFormats), overridingDrmInitData, this.allocator, positionUs, muxedAudioFormat, this.drmSessionManager, this.drmEventDispatcher, this.loadErrorHandlingPolicy, this.eventDispatcher, this.metadataType);
    }

    private static Map<String, DrmInitData> deriveOverridingDrmInitData(List<DrmInitData> sessionKeyDrmInitData) {
        ArrayList arrayList = new ArrayList(sessionKeyDrmInitData);
        HashMap map = new HashMap();
        int i = 0;
        while (i < arrayList.size()) {
            DrmInitData drmInitDataMerge = sessionKeyDrmInitData.get(i);
            String str = drmInitDataMerge.schemeType;
            i++;
            int i2 = i;
            while (i2 < arrayList.size()) {
                DrmInitData drmInitData = (DrmInitData) arrayList.get(i2);
                if (TextUtils.equals(drmInitData.schemeType, str)) {
                    drmInitDataMerge = drmInitDataMerge.merge(drmInitData);
                    arrayList.remove(i2);
                } else {
                    i2++;
                }
            }
            map.put(str, drmInitDataMerge);
        }
        return map;
    }

    private static Format deriveVideoFormat(Format variantFormat) {
        String codecsOfType = Util.getCodecsOfType(variantFormat.codecs, 2);
        return new Format.Builder().setId(variantFormat.id).setLabel(variantFormat.label).setContainerMimeType(variantFormat.containerMimeType).setSampleMimeType(MimeTypes.getMediaMimeType(codecsOfType)).setCodecs(codecsOfType).setMetadata(variantFormat.metadata).setAverageBitrate(variantFormat.averageBitrate).setPeakBitrate(variantFormat.peakBitrate).setWidth(variantFormat.width).setHeight(variantFormat.height).setFrameRate(variantFormat.frameRate).setSelectionFlags(variantFormat.selectionFlags).setRoleFlags(variantFormat.roleFlags).build();
    }

    private static Format deriveAudioFormat(Format variantFormat, Format mediaTagFormat, boolean isPrimaryTrackInVariant) {
        String codecsOfType;
        Metadata metadata;
        int i;
        String str;
        int i2;
        int i3;
        String str2;
        if (mediaTagFormat != null) {
            codecsOfType = mediaTagFormat.codecs;
            metadata = mediaTagFormat.metadata;
            i2 = mediaTagFormat.channelCount;
            i = mediaTagFormat.selectionFlags;
            i3 = mediaTagFormat.roleFlags;
            str = mediaTagFormat.language;
            str2 = mediaTagFormat.label;
        } else {
            codecsOfType = Util.getCodecsOfType(variantFormat.codecs, 1);
            metadata = variantFormat.metadata;
            if (isPrimaryTrackInVariant) {
                i2 = variantFormat.channelCount;
                i = variantFormat.selectionFlags;
                i3 = variantFormat.roleFlags;
                str = variantFormat.language;
                str2 = variantFormat.label;
            } else {
                i = 0;
                str = null;
                i2 = -1;
                i3 = 0;
                str2 = null;
            }
        }
        return new Format.Builder().setId(variantFormat.id).setLabel(str2).setContainerMimeType(variantFormat.containerMimeType).setSampleMimeType(MimeTypes.getMediaMimeType(codecsOfType)).setCodecs(codecsOfType).setMetadata(metadata).setAverageBitrate(isPrimaryTrackInVariant ? variantFormat.averageBitrate : -1).setPeakBitrate(isPrimaryTrackInVariant ? variantFormat.peakBitrate : -1).setChannelCount(i2).setSelectionFlags(i).setRoleFlags(i3).setLanguage(str).build();
    }
}
