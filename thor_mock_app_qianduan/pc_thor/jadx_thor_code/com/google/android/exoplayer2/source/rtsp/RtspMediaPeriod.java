package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.source.rtsp.RtpDataLoadable;
import com.google.android.exoplayer2.source.rtsp.RtspClient;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
final class RtspMediaPeriod implements MediaPeriod {
    private static final int PORT_BINDING_MAX_RETRY_COUNT = 3;
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final Handler handler = Util.createHandlerForCurrentLooper();
    private final InternalListener internalListener;
    private boolean isUsingRtpTcp;
    private long lastSeekPositionUs;
    private final Listener listener;
    private boolean loadingFinished;
    private long pendingSeekPositionUs;
    private RtspMediaSource.RtspPlaybackException playbackException;
    private int portBindingRetryCount;
    private IOException preparationError;
    private boolean prepared;
    private boolean released;
    private final RtpDataChannel.Factory rtpDataChannelFactory;
    private final RtspClient rtspClient;
    private final List<RtspLoaderWrapper> rtspLoaderWrappers;
    private final List<RtpLoadInfo> selectedLoadInfos;
    private ImmutableList<TrackGroup> trackGroups;
    private boolean trackSelected;

    interface Listener {
        void onSourceInfoRefreshed(RtspSessionTiming timing);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
    }

    static /* synthetic */ int access$1008(RtspMediaPeriod rtspMediaPeriod) {
        int i = rtspMediaPeriod.portBindingRetryCount;
        rtspMediaPeriod.portBindingRetryCount = i + 1;
        return i;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public /* bridge */ /* synthetic */ List getStreamKeys(List trackSelections) {
        return getStreamKeys((List<ExoTrackSelection>) trackSelections);
    }

    public RtspMediaPeriod(Allocator allocator, RtpDataChannel.Factory rtpDataChannelFactory, Uri uri, Listener listener, String userAgent) {
        this.allocator = allocator;
        this.rtpDataChannelFactory = rtpDataChannelFactory;
        this.listener = listener;
        InternalListener internalListener = new InternalListener();
        this.internalListener = internalListener;
        this.rtspClient = new RtspClient(internalListener, internalListener, userAgent, uri);
        this.rtspLoaderWrappers = new ArrayList();
        this.selectedLoadInfos = new ArrayList();
        this.pendingSeekPositionUs = C.TIME_UNSET;
    }

    public void release() {
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            this.rtspLoaderWrappers.get(i).release();
        }
        Util.closeQuietly(this.rtspClient);
        this.released = true;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) throws NumberFormatException, IOException {
        this.callback = callback;
        try {
            this.rtspClient.start();
        } catch (IOException e) {
            this.preparationError = e;
            Util.closeQuietly(this.rtspClient);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        IOException iOException = this.preparationError;
        if (iOException != null) {
            throw iOException;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        Assertions.checkState(this.prepared);
        return new TrackGroupArray((TrackGroup[]) ((ImmutableList) Assertions.checkNotNull(this.trackGroups)).toArray(new TrackGroup[0]));
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public ImmutableList<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
        return ImmutableList.of();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) throws NumberFormatException {
        for (int i = 0; i < selections.length; i++) {
            if (streams[i] != null && (selections[i] == null || !mayRetainStreamFlags[i])) {
                streams[i] = null;
            }
        }
        this.selectedLoadInfos.clear();
        for (int i2 = 0; i2 < selections.length; i2++) {
            ExoTrackSelection exoTrackSelection = selections[i2];
            if (exoTrackSelection != null) {
                TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                int iIndexOf = ((ImmutableList) Assertions.checkNotNull(this.trackGroups)).indexOf(trackGroup);
                this.selectedLoadInfos.add(((RtspLoaderWrapper) Assertions.checkNotNull(this.rtspLoaderWrappers.get(iIndexOf))).loadInfo);
                if (this.trackGroups.contains(trackGroup) && streams[i2] == null) {
                    streams[i2] = new SampleStreamImpl(iIndexOf);
                    streamResetFlags[i2] = true;
                }
            }
        }
        for (int i3 = 0; i3 < this.rtspLoaderWrappers.size(); i3++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i3);
            if (!this.selectedLoadInfos.contains(rtspLoaderWrapper.loadInfo)) {
                rtspLoaderWrapper.cancelLoad();
            }
        }
        this.trackSelected = true;
        maybeSetupTracks();
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        if (isSeekPending()) {
            return;
        }
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i);
            if (!rtspLoaderWrapper.canceled) {
                rtspLoaderWrapper.sampleQueue.discardTo(positionUs, toKeyframe, true);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long positionUs) throws NumberFormatException {
        if (isSeekPending()) {
            return this.pendingSeekPositionUs;
        }
        if (seekInsideBufferUs(positionUs)) {
            return positionUs;
        }
        this.lastSeekPositionUs = positionUs;
        this.pendingSeekPositionUs = positionUs;
        this.rtspClient.seekToUs(positionUs);
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            this.rtspLoaderWrappers.get(i).seekTo(positionUs);
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        if (this.loadingFinished || this.rtspLoaderWrappers.isEmpty()) {
            return Long.MIN_VALUE;
        }
        if (isSeekPending()) {
            return this.pendingSeekPositionUs;
        }
        boolean z = true;
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i);
            if (!rtspLoaderWrapper.canceled) {
                jMin = Math.min(jMin, rtspLoaderWrapper.getBufferedPositionUs());
                z = false;
            }
        }
        return (z || jMin == Long.MIN_VALUE) ? this.lastSeekPositionUs : jMin;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        return isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return !this.loadingFinished;
    }

    boolean isReady(int trackGroupIndex) {
        return this.rtspLoaderWrappers.get(trackGroupIndex).isSampleQueueReady();
    }

    int readData(int sampleQueueIndex, FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
        return this.rtspLoaderWrappers.get(sampleQueueIndex).read(formatHolder, buffer, readFlags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RtpDataLoadable getLoadableByTrackUri(Uri trackUri) {
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            if (!this.rtspLoaderWrappers.get(i).canceled) {
                RtpLoadInfo rtpLoadInfo = this.rtspLoaderWrappers.get(i).loadInfo;
                if (rtpLoadInfo.getTrackUri().equals(trackUri)) {
                    return rtpLoadInfo.loadable;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSeekPending() {
        return this.pendingSeekPositionUs != C.TIME_UNSET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeFinishPrepare() {
        if (this.released || this.prepared) {
            return;
        }
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            if (this.rtspLoaderWrappers.get(i).sampleQueue.getUpstreamFormat() == null) {
                return;
            }
        }
        this.prepared = true;
        this.trackGroups = buildTrackGroups(ImmutableList.copyOf((Collection) this.rtspLoaderWrappers));
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    private boolean seekInsideBufferUs(long positionUs) {
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            if (!this.rtspLoaderWrappers.get(i).sampleQueue.seekTo(positionUs, false)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeSetupTracks() throws NumberFormatException {
        boolean zIsTransportReady = true;
        for (int i = 0; i < this.selectedLoadInfos.size(); i++) {
            zIsTransportReady &= this.selectedLoadInfos.get(i).isTransportReady();
        }
        if (zIsTransportReady && this.trackSelected) {
            this.rtspClient.setupSelectedTracks(this.selectedLoadInfos);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLoadingFinished() {
        this.loadingFinished = true;
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            this.loadingFinished &= this.rtspLoaderWrappers.get(i).canceled;
        }
    }

    private static ImmutableList<TrackGroup> buildTrackGroups(ImmutableList<RtspLoaderWrapper> rtspLoaderWrappers) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i = 0; i < rtspLoaderWrappers.size(); i++) {
            builder.add((ImmutableList.Builder) new TrackGroup((Format) Assertions.checkNotNull(rtspLoaderWrappers.get(i).sampleQueue.getUpstreamFormat())));
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class InternalListener implements ExtractorOutput, Loader.Callback<RtpDataLoadable>, SampleQueue.UpstreamFormatChangedListener, RtspClient.SessionInfoListener, RtspClient.PlaybackEventListener {
        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(RtpDataLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void seekMap(SeekMap seekMap) {
        }

        private InternalListener() {
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public TrackOutput track(int id, int type) {
            return ((RtspLoaderWrapper) Assertions.checkNotNull((RtspLoaderWrapper) RtspMediaPeriod.this.rtspLoaderWrappers.get(id))).sampleQueue;
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void endTracks() {
            Handler handler = RtspMediaPeriod.this.handler;
            final RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod$InternalListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    rtspMediaPeriod.maybeFinishPrepare();
                }
            });
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(RtpDataLoadable loadable, long elapsedRealtimeMs, long loadDurationMs) throws NumberFormatException {
            if (RtspMediaPeriod.this.getBufferedPositionUs() == 0) {
                if (RtspMediaPeriod.this.isUsingRtpTcp) {
                    return;
                }
                RtspMediaPeriod.this.retryWithRtpTcp();
                RtspMediaPeriod.this.isUsingRtpTcp = true;
                return;
            }
            for (int i = 0; i < RtspMediaPeriod.this.rtspLoaderWrappers.size(); i++) {
                RtspLoaderWrapper rtspLoaderWrapper = (RtspLoaderWrapper) RtspMediaPeriod.this.rtspLoaderWrappers.get(i);
                if (rtspLoaderWrapper.loadInfo.loadable == loadable) {
                    rtspLoaderWrapper.cancelLoad();
                    return;
                }
            }
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(RtpDataLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            if (!RtspMediaPeriod.this.prepared) {
                RtspMediaPeriod.this.preparationError = error;
            } else if (error.getCause() instanceof BindException) {
                if (RtspMediaPeriod.access$1008(RtspMediaPeriod.this) < 3) {
                    return Loader.RETRY;
                }
            } else {
                RtspMediaPeriod.this.playbackException = new RtspMediaSource.RtspPlaybackException(loadable.rtspMediaTrack.uri.toString(), error);
            }
            return Loader.DONT_RETRY;
        }

        @Override // com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener
        public void onUpstreamFormatChanged(Format format) {
            Handler handler = RtspMediaPeriod.this.handler;
            final RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod$InternalListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    rtspMediaPeriod.maybeFinishPrepare();
                }
            });
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.PlaybackEventListener
        public void onRtspSetupCompleted() throws NumberFormatException {
            RtspMediaPeriod.this.rtspClient.startPlayback(0L);
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.PlaybackEventListener
        public void onPlaybackStarted(long startPositionUs, ImmutableList<RtspTrackTiming> trackTimingList) {
            ArrayList arrayList = new ArrayList(trackTimingList.size());
            for (int i = 0; i < trackTimingList.size(); i++) {
                arrayList.add((String) Assertions.checkNotNull(trackTimingList.get(i).uri.getPath()));
            }
            for (int i2 = 0; i2 < RtspMediaPeriod.this.selectedLoadInfos.size(); i2++) {
                RtpLoadInfo rtpLoadInfo = (RtpLoadInfo) RtspMediaPeriod.this.selectedLoadInfos.get(i2);
                if (!arrayList.contains(rtpLoadInfo.getTrackUri().getPath())) {
                    RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
                    String strValueOf = String.valueOf(rtpLoadInfo.getTrackUri());
                    rtspMediaPeriod.playbackException = new RtspMediaSource.RtspPlaybackException(new StringBuilder(String.valueOf(strValueOf).length() + 40).append("Server did not provide timing for track ").append(strValueOf).toString());
                    return;
                }
            }
            for (int i3 = 0; i3 < trackTimingList.size(); i3++) {
                RtspTrackTiming rtspTrackTiming = trackTimingList.get(i3);
                RtpDataLoadable loadableByTrackUri = RtspMediaPeriod.this.getLoadableByTrackUri(rtspTrackTiming.uri);
                if (loadableByTrackUri != null) {
                    loadableByTrackUri.setTimestamp(rtspTrackTiming.rtpTimestamp);
                    loadableByTrackUri.setSequenceNumber(rtspTrackTiming.sequenceNumber);
                    if (RtspMediaPeriod.this.isSeekPending()) {
                        loadableByTrackUri.seekToUs(startPositionUs, rtspTrackTiming.rtpTimestamp);
                    }
                }
            }
            if (RtspMediaPeriod.this.isSeekPending()) {
                RtspMediaPeriod.this.pendingSeekPositionUs = C.TIME_UNSET;
            }
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.PlaybackEventListener
        public void onPlaybackError(RtspMediaSource.RtspPlaybackException error) {
            RtspMediaPeriod.this.playbackException = error;
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.SessionInfoListener
        public void onSessionTimelineUpdated(RtspSessionTiming timing, ImmutableList<RtspMediaTrack> tracks) {
            for (int i = 0; i < tracks.size(); i++) {
                RtspMediaTrack rtspMediaTrack = tracks.get(i);
                RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
                RtspLoaderWrapper rtspLoaderWrapper = rtspMediaPeriod.new RtspLoaderWrapper(rtspMediaTrack, i, rtspMediaPeriod.rtpDataChannelFactory);
                RtspMediaPeriod.this.rtspLoaderWrappers.add(rtspLoaderWrapper);
                rtspLoaderWrapper.startLoading();
            }
            RtspMediaPeriod.this.listener.onSourceInfoRefreshed(timing);
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.SessionInfoListener
        public void onSessionTimelineRequestFailed(String message, Throwable cause) {
            RtspMediaPeriod.this.preparationError = cause == null ? new IOException(message) : new IOException(message, cause);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void retryWithRtpTcp() throws NumberFormatException {
        this.rtspClient.retryWithRtpTcp();
        RtpDataChannel.Factory factoryCreateFallbackDataChannelFactory = this.rtpDataChannelFactory.createFallbackDataChannelFactory();
        if (factoryCreateFallbackDataChannelFactory == null) {
            this.playbackException = new RtspMediaSource.RtspPlaybackException("No fallback data channel factory for TCP retry");
            return;
        }
        ArrayList arrayList = new ArrayList(this.rtspLoaderWrappers.size());
        ArrayList arrayList2 = new ArrayList(this.selectedLoadInfos.size());
        for (int i = 0; i < this.rtspLoaderWrappers.size(); i++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i);
            if (!rtspLoaderWrapper.canceled) {
                RtspLoaderWrapper rtspLoaderWrapper2 = new RtspLoaderWrapper(rtspLoaderWrapper.loadInfo.mediaTrack, i, factoryCreateFallbackDataChannelFactory);
                arrayList.add(rtspLoaderWrapper2);
                rtspLoaderWrapper2.startLoading();
                if (this.selectedLoadInfos.contains(rtspLoaderWrapper.loadInfo)) {
                    arrayList2.add(rtspLoaderWrapper2.loadInfo);
                }
            } else {
                arrayList.add(rtspLoaderWrapper);
            }
        }
        ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.rtspLoaderWrappers);
        this.rtspLoaderWrappers.clear();
        this.rtspLoaderWrappers.addAll(arrayList);
        this.selectedLoadInfos.clear();
        this.selectedLoadInfos.addAll(arrayList2);
        for (int i2 = 0; i2 < immutableListCopyOf.size(); i2++) {
            ((RtspLoaderWrapper) immutableListCopyOf.get(i2)).cancelLoad();
        }
    }

    private final class SampleStreamImpl implements SampleStream {
        private final int track;

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long positionUs) {
            return 0;
        }

        public SampleStreamImpl(int track) {
            this.track = track;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return RtspMediaPeriod.this.isReady(this.track);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws RtspMediaSource.RtspPlaybackException {
            if (RtspMediaPeriod.this.playbackException != null) {
                throw RtspMediaPeriod.this.playbackException;
            }
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            return RtspMediaPeriod.this.readData(this.track, formatHolder, buffer, readFlags);
        }
    }

    private final class RtspLoaderWrapper {
        private boolean canceled;
        public final RtpLoadInfo loadInfo;
        private final Loader loader;
        private boolean released;
        private final SampleQueue sampleQueue;

        public RtspLoaderWrapper(RtspMediaTrack mediaTrack, int trackId, RtpDataChannel.Factory rtpDataChannelFactory) {
            this.loadInfo = RtspMediaPeriod.this.new RtpLoadInfo(mediaTrack, trackId, rtpDataChannelFactory);
            this.loader = new Loader(new StringBuilder(55).append("ExoPlayer:RtspMediaPeriod:RtspLoaderWrapper ").append(trackId).toString());
            SampleQueue sampleQueueCreateWithoutDrm = SampleQueue.createWithoutDrm(RtspMediaPeriod.this.allocator);
            this.sampleQueue = sampleQueueCreateWithoutDrm;
            sampleQueueCreateWithoutDrm.setUpstreamFormatChangeListener(RtspMediaPeriod.this.internalListener);
        }

        public long getBufferedPositionUs() {
            return this.sampleQueue.getLargestQueuedTimestampUs();
        }

        public void startLoading() {
            this.loader.startLoading(this.loadInfo.loadable, RtspMediaPeriod.this.internalListener, 0);
        }

        public boolean isSampleQueueReady() {
            return this.sampleQueue.isReady(this.canceled);
        }

        public int read(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            return this.sampleQueue.read(formatHolder, buffer, readFlags, this.canceled);
        }

        public void cancelLoad() {
            if (this.canceled) {
                return;
            }
            this.loadInfo.loadable.cancelLoad();
            this.canceled = true;
            RtspMediaPeriod.this.updateLoadingFinished();
        }

        public void seekTo(long positionUs) {
            if (this.canceled) {
                return;
            }
            this.loadInfo.loadable.resetForSeek();
            this.sampleQueue.reset();
            this.sampleQueue.setStartTimeUs(positionUs);
        }

        public void release() {
            if (this.released) {
                return;
            }
            this.loader.release();
            this.sampleQueue.release();
            this.released = true;
        }
    }

    final class RtpLoadInfo {
        private final RtpDataLoadable loadable;
        public final RtspMediaTrack mediaTrack;
        private String transport;

        public RtpLoadInfo(RtspMediaTrack mediaTrack, int trackId, RtpDataChannel.Factory rtpDataChannelFactory) {
            this.mediaTrack = mediaTrack;
            this.loadable = new RtpDataLoadable(trackId, mediaTrack, new RtpDataLoadable.EventListener() { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod$RtpLoadInfo$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.source.rtsp.RtpDataLoadable.EventListener
                public final void onTransportReady(String str, RtpDataChannel rtpDataChannel) throws NumberFormatException {
                    this.f$0.m193x3d57be61(str, rtpDataChannel);
                }
            }, RtspMediaPeriod.this.internalListener, rtpDataChannelFactory);
        }

        /* renamed from: lambda$new$0$com-google-android-exoplayer2-source-rtsp-RtspMediaPeriod$RtpLoadInfo, reason: not valid java name */
        /* synthetic */ void m193x3d57be61(String str, RtpDataChannel rtpDataChannel) throws NumberFormatException {
            this.transport = str;
            RtspMessageChannel.InterleavedBinaryDataListener interleavedBinaryDataListener = rtpDataChannel.getInterleavedBinaryDataListener();
            if (interleavedBinaryDataListener != null) {
                RtspMediaPeriod.this.rtspClient.registerInterleavedDataChannel(rtpDataChannel.getLocalPort(), interleavedBinaryDataListener);
                RtspMediaPeriod.this.isUsingRtpTcp = true;
            }
            RtspMediaPeriod.this.maybeSetupTracks();
        }

        public boolean isTransportReady() {
            return this.transport != null;
        }

        public String getTransport() {
            Assertions.checkStateNotNull(this.transport);
            return this.transport;
        }

        public Uri getTrackUri() {
            return this.loadable.rtspMediaTrack.uri;
        }
    }
}
