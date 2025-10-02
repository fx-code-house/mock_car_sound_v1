package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class DownloadHelper {

    @Deprecated
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS;
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT;

    @Deprecated
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_VIEWPORT;
    private Callback callback;
    private final Handler callbackHandler;
    private List<ExoTrackSelection>[][] immutableTrackSelectionsByPeriodAndRenderer;
    private boolean isPreparedWithMedia;
    private MappingTrackSelector.MappedTrackInfo[] mappedTrackInfos;
    private MediaPreparer mediaPreparer;
    private final MediaSource mediaSource;
    private final MediaItem.PlaybackProperties playbackProperties;
    private final RendererCapabilities[] rendererCapabilities;
    private final SparseIntArray scratchSet;
    private TrackGroupArray[] trackGroupArrays;
    private List<ExoTrackSelection>[][] trackSelectionsByPeriodAndRenderer;
    private final DefaultTrackSelector trackSelector;
    private final Timeline.Window window;

    public interface Callback {
        void onPrepareError(DownloadHelper helper, IOException e);

        void onPrepared(DownloadHelper helper);
    }

    public static class LiveContentUnsupportedException extends IOException {
    }

    static /* synthetic */ void lambda$getRendererCapabilities$0(List list) {
    }

    static /* synthetic */ void lambda$getRendererCapabilities$1(Metadata metadata) {
    }

    static /* synthetic */ void lambda$new$2() {
    }

    static {
        DefaultTrackSelector.Parameters parametersBuild = DefaultTrackSelector.Parameters.DEFAULT_WITHOUT_CONTEXT.buildUpon().setForceHighestSupportedBitrate(true).build();
        DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT = parametersBuild;
        DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_VIEWPORT = parametersBuild;
        DEFAULT_TRACK_SELECTOR_PARAMETERS = parametersBuild;
    }

    public static DefaultTrackSelector.Parameters getDefaultTrackSelectorParameters(Context context) {
        return DefaultTrackSelector.Parameters.getDefaults(context).buildUpon().setForceHighestSupportedBitrate(true).build();
    }

    public static RendererCapabilities[] getRendererCapabilities(RenderersFactory renderersFactory) {
        Renderer[] rendererArrCreateRenderers = renderersFactory.createRenderers(Util.createHandlerForCurrentOrMainLooper(), new VideoRendererEventListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper.1
        }, new AudioRendererEventListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper.2
        }, new TextOutput() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$$ExternalSyntheticLambda2
            @Override // com.google.android.exoplayer2.text.TextOutput
            public final void onCues(List list) {
                DownloadHelper.lambda$getRendererCapabilities$0(list);
            }
        }, new MetadataOutput() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$$ExternalSyntheticLambda3
            @Override // com.google.android.exoplayer2.metadata.MetadataOutput
            public final void onMetadata(Metadata metadata) {
                DownloadHelper.lambda$getRendererCapabilities$1(metadata);
            }
        });
        RendererCapabilities[] rendererCapabilitiesArr = new RendererCapabilities[rendererArrCreateRenderers.length];
        for (int i = 0; i < rendererArrCreateRenderers.length; i++) {
            rendererCapabilitiesArr[i] = rendererArrCreateRenderers[i].getCapabilities();
        }
        return rendererCapabilitiesArr;
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).build());
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri, String cacheKey) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).setCustomCacheKey(cacheKey).build());
    }

    @Deprecated
    public static DownloadHelper forDash(Context context, Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forDash(uri, dataSourceFactory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forDash(Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory, DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters trackSelectorParameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_MPD).build(), trackSelectorParameters, renderersFactory, dataSourceFactory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forHls(Context context, Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forHls(uri, dataSourceFactory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forHls(Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory, DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters trackSelectorParameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_M3U8).build(), trackSelectorParameters, renderersFactory, dataSourceFactory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, dataSourceFactory, renderersFactory, null, DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT);
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Context context, Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, dataSourceFactory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory, DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters trackSelectorParameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_SS).build(), trackSelectorParameters, renderersFactory, dataSourceFactory, drmSessionManager);
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem) {
        Assertions.checkArgument(isProgressive((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)));
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), null, null, null);
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem, RenderersFactory renderersFactory, DataSource.Factory dataSourceFactory) {
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), renderersFactory, dataSourceFactory, null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters trackSelectorParameters, RenderersFactory renderersFactory, DataSource.Factory dataSourceFactory) {
        return forMediaItem(mediaItem, trackSelectorParameters, renderersFactory, dataSourceFactory, null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters trackSelectorParameters, RenderersFactory renderersFactory, DataSource.Factory dataSourceFactory, DrmSessionManager drmSessionManager) {
        boolean zIsProgressive = isProgressive((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties));
        Assertions.checkArgument(zIsProgressive || dataSourceFactory != null);
        return new DownloadHelper(mediaItem, zIsProgressive ? null : createMediaSourceInternal(mediaItem, (DataSource.Factory) Util.castNonNull(dataSourceFactory), drmSessionManager), trackSelectorParameters, renderersFactory != null ? getRendererCapabilities(renderersFactory) : new RendererCapabilities[0]);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory dataSourceFactory) {
        return createMediaSource(downloadRequest, dataSourceFactory, null);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory dataSourceFactory, DrmSessionManager drmSessionManager) {
        return createMediaSourceInternal(downloadRequest.toMediaItem(), dataSourceFactory, drmSessionManager);
    }

    public DownloadHelper(MediaItem mediaItem, MediaSource mediaSource, DefaultTrackSelector.Parameters trackSelectorParameters, RendererCapabilities[] rendererCapabilities) {
        this.playbackProperties = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.mediaSource = mediaSource;
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(trackSelectorParameters, new DownloadTrackSelection.Factory());
        this.trackSelector = defaultTrackSelector;
        this.rendererCapabilities = rendererCapabilities;
        this.scratchSet = new SparseIntArray();
        defaultTrackSelector.init(new TrackSelector.InvalidationListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
            public final void onTrackSelectionsInvalidated() {
                DownloadHelper.lambda$new$2();
            }
        }, new FakeBandwidthMeter());
        this.callbackHandler = Util.createHandlerForCurrentOrMainLooper();
        this.window = new Timeline.Window();
    }

    public void prepare(final Callback callback) {
        Assertions.checkState(this.callback == null);
        this.callback = callback;
        if (this.mediaSource != null) {
            this.mediaPreparer = new MediaPreparer(this.mediaSource, this);
        } else {
            this.callbackHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m166xf9781d7d(callback);
                }
            });
        }
    }

    /* renamed from: lambda$prepare$3$com-google-android-exoplayer2-offline-DownloadHelper, reason: not valid java name */
    /* synthetic */ void m166xf9781d7d(Callback callback) {
        callback.onPrepared(this);
    }

    public void release() {
        MediaPreparer mediaPreparer = this.mediaPreparer;
        if (mediaPreparer != null) {
            mediaPreparer.release();
        }
    }

    public Object getManifest() {
        if (this.mediaSource == null) {
            return null;
        }
        assertPreparedWithMedia();
        if (this.mediaPreparer.timeline.getWindowCount() > 0) {
            return this.mediaPreparer.timeline.getWindow(0, this.window).manifest;
        }
        return null;
    }

    public int getPeriodCount() {
        if (this.mediaSource == null) {
            return 0;
        }
        assertPreparedWithMedia();
        return this.trackGroupArrays.length;
    }

    public TrackGroupArray getTrackGroups(int periodIndex) {
        assertPreparedWithMedia();
        return this.trackGroupArrays[periodIndex];
    }

    public MappingTrackSelector.MappedTrackInfo getMappedTrackInfo(int periodIndex) {
        assertPreparedWithMedia();
        return this.mappedTrackInfos[periodIndex];
    }

    public List<ExoTrackSelection> getTrackSelections(int periodIndex, int rendererIndex) {
        assertPreparedWithMedia();
        return this.immutableTrackSelectionsByPeriodAndRenderer[periodIndex][rendererIndex];
    }

    public void clearTrackSelections(int periodIndex) {
        assertPreparedWithMedia();
        for (int i = 0; i < this.rendererCapabilities.length; i++) {
            this.trackSelectionsByPeriodAndRenderer[periodIndex][i].clear();
        }
    }

    public void replaceTrackSelections(int periodIndex, DefaultTrackSelector.Parameters trackSelectorParameters) {
        clearTrackSelections(periodIndex);
        addTrackSelection(periodIndex, trackSelectorParameters);
    }

    public void addTrackSelection(int periodIndex, DefaultTrackSelector.Parameters trackSelectorParameters) {
        assertPreparedWithMedia();
        this.trackSelector.setParameters(trackSelectorParameters);
        runTrackSelection(periodIndex);
    }

    public void addAudioLanguagesToSelection(String... languages) {
        assertPreparedWithMedia();
        for (int i = 0; i < this.mappedTrackInfos.length; i++) {
            DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.mappedTrackInfos[i];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i2 = 0; i2 < rendererCount; i2++) {
                if (mappedTrackInfo.getRendererType(i2) != 1) {
                    parametersBuilderBuildUpon.setRendererDisabled(i2, true);
                }
            }
            for (String str : languages) {
                parametersBuilderBuildUpon.setPreferredAudioLanguage(str);
                addTrackSelection(i, parametersBuilderBuildUpon.build());
            }
        }
    }

    public void addTextLanguagesToSelection(boolean selectUndeterminedTextLanguage, String... languages) {
        assertPreparedWithMedia();
        for (int i = 0; i < this.mappedTrackInfos.length; i++) {
            DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.mappedTrackInfos[i];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i2 = 0; i2 < rendererCount; i2++) {
                if (mappedTrackInfo.getRendererType(i2) != 3) {
                    parametersBuilderBuildUpon.setRendererDisabled(i2, true);
                }
            }
            parametersBuilderBuildUpon.setSelectUndeterminedTextLanguage(selectUndeterminedTextLanguage);
            for (String str : languages) {
                parametersBuilderBuildUpon.setPreferredTextLanguage(str);
                addTrackSelection(i, parametersBuilderBuildUpon.build());
            }
        }
    }

    public void addTrackSelectionForSingleRenderer(int periodIndex, int rendererIndex, DefaultTrackSelector.Parameters trackSelectorParameters, List<DefaultTrackSelector.SelectionOverride> overrides) {
        assertPreparedWithMedia();
        DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = trackSelectorParameters.buildUpon();
        int i = 0;
        while (i < this.mappedTrackInfos[periodIndex].getRendererCount()) {
            parametersBuilderBuildUpon.setRendererDisabled(i, i != rendererIndex);
            i++;
        }
        if (overrides.isEmpty()) {
            addTrackSelection(periodIndex, parametersBuilderBuildUpon.build());
            return;
        }
        TrackGroupArray trackGroups = this.mappedTrackInfos[periodIndex].getTrackGroups(rendererIndex);
        for (int i2 = 0; i2 < overrides.size(); i2++) {
            parametersBuilderBuildUpon.setSelectionOverride(rendererIndex, trackGroups, overrides.get(i2));
            addTrackSelection(periodIndex, parametersBuilderBuildUpon.build());
        }
    }

    public DownloadRequest getDownloadRequest(byte[] data) {
        return getDownloadRequest(this.playbackProperties.uri.toString(), data);
    }

    public DownloadRequest getDownloadRequest(String id, byte[] data) {
        DownloadRequest.Builder data2 = new DownloadRequest.Builder(id, this.playbackProperties.uri).setMimeType(this.playbackProperties.mimeType).setKeySetId(this.playbackProperties.drmConfiguration != null ? this.playbackProperties.drmConfiguration.getKeySetId() : null).setCustomCacheKey(this.playbackProperties.customCacheKey).setData(data);
        if (this.mediaSource == null) {
            return data2.build();
        }
        assertPreparedWithMedia();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int length = this.trackSelectionsByPeriodAndRenderer.length;
        for (int i = 0; i < length; i++) {
            arrayList2.clear();
            int length2 = this.trackSelectionsByPeriodAndRenderer[i].length;
            for (int i2 = 0; i2 < length2; i2++) {
                arrayList2.addAll(this.trackSelectionsByPeriodAndRenderer[i][i2]);
            }
            arrayList.addAll(this.mediaPreparer.mediaPeriods[i].getStreamKeys(arrayList2));
        }
        return data2.setStreamKeys(arrayList).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMediaPrepared() {
        Assertions.checkNotNull(this.mediaPreparer);
        Assertions.checkNotNull(this.mediaPreparer.mediaPeriods);
        Assertions.checkNotNull(this.mediaPreparer.timeline);
        int length = this.mediaPreparer.mediaPeriods.length;
        int length2 = this.rendererCapabilities.length;
        this.trackSelectionsByPeriodAndRenderer = (List[][]) Array.newInstance((Class<?>) List.class, length, length2);
        this.immutableTrackSelectionsByPeriodAndRenderer = (List[][]) Array.newInstance((Class<?>) List.class, length, length2);
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length2; i2++) {
                this.trackSelectionsByPeriodAndRenderer[i][i2] = new ArrayList();
                this.immutableTrackSelectionsByPeriodAndRenderer[i][i2] = Collections.unmodifiableList(this.trackSelectionsByPeriodAndRenderer[i][i2]);
            }
        }
        this.trackGroupArrays = new TrackGroupArray[length];
        this.mappedTrackInfos = new MappingTrackSelector.MappedTrackInfo[length];
        for (int i3 = 0; i3 < length; i3++) {
            this.trackGroupArrays[i3] = this.mediaPreparer.mediaPeriods[i3].getTrackGroups();
            this.trackSelector.onSelectionActivated(runTrackSelection(i3).info);
            this.mappedTrackInfos[i3] = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.trackSelector.getCurrentMappedTrackInfo());
        }
        setPreparedWithMedia();
        ((Handler) Assertions.checkNotNull(this.callbackHandler)).post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m165xbc158121();
            }
        });
    }

    /* renamed from: lambda$onMediaPrepared$4$com-google-android-exoplayer2-offline-DownloadHelper, reason: not valid java name */
    /* synthetic */ void m165xbc158121() {
        ((Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMediaPreparationFailed(final IOException error) {
        ((Handler) Assertions.checkNotNull(this.callbackHandler)).post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m164xa7571479(error);
            }
        });
    }

    /* renamed from: lambda$onMediaPreparationFailed$5$com-google-android-exoplayer2-offline-DownloadHelper, reason: not valid java name */
    /* synthetic */ void m164xa7571479(IOException iOException) {
        ((Callback) Assertions.checkNotNull(this.callback)).onPrepareError(this, iOException);
    }

    @RequiresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void setPreparedWithMedia() {
        this.isPreparedWithMedia = true;
    }

    @EnsuresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void assertPreparedWithMedia() {
        Assertions.checkState(this.isPreparedWithMedia);
    }

    @RequiresNonNull({"trackGroupArrays", "trackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline"})
    private TrackSelectorResult runTrackSelection(int periodIndex) {
        boolean z;
        try {
            TrackSelectorResult trackSelectorResultSelectTracks = this.trackSelector.selectTracks(this.rendererCapabilities, this.trackGroupArrays[periodIndex], new MediaSource.MediaPeriodId(this.mediaPreparer.timeline.getUidOfPeriod(periodIndex)), this.mediaPreparer.timeline);
            for (int i = 0; i < trackSelectorResultSelectTracks.length; i++) {
                ExoTrackSelection exoTrackSelection = trackSelectorResultSelectTracks.selections[i];
                if (exoTrackSelection != null) {
                    List<ExoTrackSelection> list = this.trackSelectionsByPeriodAndRenderer[periodIndex][i];
                    int i2 = 0;
                    while (true) {
                        if (i2 >= list.size()) {
                            z = false;
                            break;
                        }
                        ExoTrackSelection exoTrackSelection2 = list.get(i2);
                        if (exoTrackSelection2.getTrackGroup() == exoTrackSelection.getTrackGroup()) {
                            this.scratchSet.clear();
                            for (int i3 = 0; i3 < exoTrackSelection2.length(); i3++) {
                                this.scratchSet.put(exoTrackSelection2.getIndexInTrackGroup(i3), 0);
                            }
                            for (int i4 = 0; i4 < exoTrackSelection.length(); i4++) {
                                this.scratchSet.put(exoTrackSelection.getIndexInTrackGroup(i4), 0);
                            }
                            int[] iArr = new int[this.scratchSet.size()];
                            for (int i5 = 0; i5 < this.scratchSet.size(); i5++) {
                                iArr[i5] = this.scratchSet.keyAt(i5);
                            }
                            list.set(i2, new DownloadTrackSelection(exoTrackSelection2.getTrackGroup(), iArr));
                            z = true;
                        } else {
                            i2++;
                        }
                    }
                    if (!z) {
                        list.add(exoTrackSelection);
                    }
                }
            }
            return trackSelectorResultSelectTracks;
        } catch (ExoPlaybackException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static MediaSource createMediaSourceInternal(MediaItem mediaItem, DataSource.Factory dataSourceFactory, DrmSessionManager drmSessionManager) {
        return new DefaultMediaSourceFactory(dataSourceFactory, ExtractorsFactory.EMPTY).setDrmSessionManager(drmSessionManager).createMediaSource(mediaItem);
    }

    private static boolean isProgressive(MediaItem.PlaybackProperties playbackProperties) {
        return Util.inferContentTypeForUriAndMimeType(playbackProperties.uri, playbackProperties.mimeType) == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MediaPreparer implements MediaSource.MediaSourceCaller, MediaPeriod.Callback, Handler.Callback {
        private static final int DOWNLOAD_HELPER_CALLBACK_MESSAGE_FAILED = 1;
        private static final int DOWNLOAD_HELPER_CALLBACK_MESSAGE_PREPARED = 0;
        private static final int MESSAGE_CHECK_FOR_FAILURE = 1;
        private static final int MESSAGE_CONTINUE_LOADING = 2;
        private static final int MESSAGE_PREPARE_SOURCE = 0;
        private static final int MESSAGE_RELEASE = 3;
        private final DownloadHelper downloadHelper;
        public MediaPeriod[] mediaPeriods;
        private final MediaSource mediaSource;
        private final Handler mediaSourceHandler;
        private final HandlerThread mediaSourceThread;
        private boolean released;
        public Timeline timeline;
        private final Allocator allocator = new DefaultAllocator(true, 65536);
        private final ArrayList<MediaPeriod> pendingMediaPeriods = new ArrayList<>();
        private final Handler downloadHelperHandler = Util.createHandlerForCurrentOrMainLooper(new Handler.Callback() { // from class: com.google.android.exoplayer2.offline.DownloadHelper$MediaPreparer$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.handleDownloadHelperCallbackMessage(message);
            }
        });

        public MediaPreparer(MediaSource mediaSource, DownloadHelper downloadHelper) {
            this.mediaSource = mediaSource;
            this.downloadHelper = downloadHelper;
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadHelper");
            this.mediaSourceThread = handlerThread;
            handlerThread.start();
            Handler handlerCreateHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.mediaSourceHandler = handlerCreateHandler;
            handlerCreateHandler.sendEmptyMessage(0);
        }

        public void release() {
            if (this.released) {
                return;
            }
            this.released = true;
            this.mediaSourceHandler.sendEmptyMessage(3);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                this.mediaSource.prepareSource(this, null);
                this.mediaSourceHandler.sendEmptyMessage(1);
                return true;
            }
            int i2 = 0;
            if (i == 1) {
                try {
                    if (this.mediaPeriods == null) {
                        this.mediaSource.maybeThrowSourceInfoRefreshError();
                    } else {
                        while (i2 < this.pendingMediaPeriods.size()) {
                            this.pendingMediaPeriods.get(i2).maybeThrowPrepareError();
                            i2++;
                        }
                    }
                    this.mediaSourceHandler.sendEmptyMessageDelayed(1, 100L);
                } catch (IOException e) {
                    this.downloadHelperHandler.obtainMessage(1, e).sendToTarget();
                }
                return true;
            }
            if (i == 2) {
                MediaPeriod mediaPeriod = (MediaPeriod) msg.obj;
                if (this.pendingMediaPeriods.contains(mediaPeriod)) {
                    mediaPeriod.continueLoading(0L);
                }
                return true;
            }
            if (i != 3) {
                return false;
            }
            MediaPeriod[] mediaPeriodArr = this.mediaPeriods;
            if (mediaPeriodArr != null) {
                int length = mediaPeriodArr.length;
                while (i2 < length) {
                    this.mediaSource.releasePeriod(mediaPeriodArr[i2]);
                    i2++;
                }
            }
            this.mediaSource.releaseSource(this);
            this.mediaSourceHandler.removeCallbacksAndMessages(null);
            this.mediaSourceThread.quit();
            return true;
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
        public void onSourceInfoRefreshed(MediaSource source, Timeline timeline) {
            MediaPeriod[] mediaPeriodArr;
            if (this.timeline != null) {
                return;
            }
            if (timeline.getWindow(0, new Timeline.Window()).isLive()) {
                this.downloadHelperHandler.obtainMessage(1, new LiveContentUnsupportedException()).sendToTarget();
                return;
            }
            this.timeline = timeline;
            this.mediaPeriods = new MediaPeriod[timeline.getPeriodCount()];
            int i = 0;
            while (true) {
                mediaPeriodArr = this.mediaPeriods;
                if (i >= mediaPeriodArr.length) {
                    break;
                }
                MediaPeriod mediaPeriodCreatePeriod = this.mediaSource.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(i)), this.allocator, 0L);
                this.mediaPeriods[i] = mediaPeriodCreatePeriod;
                this.pendingMediaPeriods.add(mediaPeriodCreatePeriod);
                i++;
            }
            for (MediaPeriod mediaPeriod : mediaPeriodArr) {
                mediaPeriod.prepare(this, 0L);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            this.pendingMediaPeriods.remove(mediaPeriod);
            if (this.pendingMediaPeriods.isEmpty()) {
                this.mediaSourceHandler.removeMessages(1);
                this.downloadHelperHandler.sendEmptyMessage(0);
            }
        }

        @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            if (this.pendingMediaPeriods.contains(mediaPeriod)) {
                this.mediaSourceHandler.obtainMessage(2, mediaPeriod).sendToTarget();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean handleDownloadHelperCallbackMessage(Message msg) {
            if (this.released) {
                return false;
            }
            int i = msg.what;
            if (i == 0) {
                this.downloadHelper.onMediaPrepared();
                return true;
            }
            if (i != 1) {
                return false;
            }
            release();
            this.downloadHelper.onMediaPreparationFailed((IOException) Util.castNonNull(msg.obj));
            return true;
        }
    }

    private static final class DownloadTrackSelection extends BaseTrackSelection {
        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectedIndex() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public Object getSelectionData() {
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectionReason() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> queue, MediaChunkIterator[] mediaChunkIterators) {
        }

        private static final class Factory implements ExoTrackSelection.Factory {
            private Factory() {
            }

            @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection.Factory
            public ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitions, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
                ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitions.length];
                for (int i = 0; i < definitions.length; i++) {
                    exoTrackSelectionArr[i] = definitions[i] == null ? null : new DownloadTrackSelection(definitions[i].group, definitions[i].tracks);
                }
                return exoTrackSelectionArr;
            }
        }

        public DownloadTrackSelection(TrackGroup trackGroup, int[] tracks) {
            super(trackGroup, tracks);
        }
    }

    private static final class FakeBandwidthMeter implements BandwidthMeter {
        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public void addEventListener(Handler eventHandler, BandwidthMeter.EventListener eventListener) {
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public long getBitrateEstimate() {
            return 0L;
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public TransferListener getTransferListener() {
            return null;
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        }

        private FakeBandwidthMeter() {
        }
    }
}
