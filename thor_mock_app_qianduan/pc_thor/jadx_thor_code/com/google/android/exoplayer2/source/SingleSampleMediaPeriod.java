package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
final class SingleSampleMediaPeriod implements MediaPeriod, Loader.Callback<SourceLoadable> {
    private static final int INITIAL_SAMPLE_SIZE = 1024;
    private static final String TAG = "SingleSampleMediaPeriod";
    private final DataSource.Factory dataSourceFactory;
    private final DataSpec dataSpec;
    private final long durationUs;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    final Format format;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    boolean loadingFinished;
    byte[] sampleData;
    int sampleSize;
    private final TrackGroupArray tracks;
    private final TransferListener transferListener;
    final boolean treatLoadErrorsAsEndOfStream;
    private final ArrayList<SampleStreamImpl> sampleStreams = new ArrayList<>();
    final Loader loader = new Loader(TAG);

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() {
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
    }

    public SingleSampleMediaPeriod(DataSpec dataSpec, DataSource.Factory dataSourceFactory, TransferListener transferListener, Format format, long durationUs, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher, boolean treatLoadErrorsAsEndOfStream) {
        this.dataSpec = dataSpec;
        this.dataSourceFactory = dataSourceFactory;
        this.transferListener = transferListener;
        this.format = format;
        this.durationUs = durationUs;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.treatLoadErrorsAsEndOfStream = treatLoadErrorsAsEndOfStream;
        this.tracks = new TrackGroupArray(new TrackGroup(format));
    }

    public void release() {
        this.loader.release();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.tracks;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        for (int i = 0; i < selections.length; i++) {
            SampleStream sampleStream = streams[i];
            if (sampleStream != null && (selections[i] == null || !mayRetainStreamFlags[i])) {
                this.sampleStreams.remove(sampleStream);
                streams[i] = null;
            }
            if (streams[i] == null && selections[i] != null) {
                SampleStreamImpl sampleStreamImpl = new SampleStreamImpl();
                this.sampleStreams.add(sampleStreamImpl);
                streams[i] = sampleStreamImpl;
                streamResetFlags[i] = true;
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        if (this.loadingFinished || this.loader.isLoading() || this.loader.hasFatalError()) {
            return false;
        }
        DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener = this.transferListener;
        if (transferListener != null) {
            dataSourceCreateDataSource.addTransferListener(transferListener);
        }
        SourceLoadable sourceLoadable = new SourceLoadable(this.dataSpec, dataSourceCreateDataSource);
        this.eventDispatcher.loadStarted(new LoadEventInfo(sourceLoadable.loadTaskId, this.dataSpec, this.loader.startLoading(sourceLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(1))), 1, -1, this.format, 0, null, 0L, this.durationUs);
        return true;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.loader.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return (this.loadingFinished || this.loader.isLoading()) ? Long.MIN_VALUE : 0L;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.loadingFinished ? Long.MIN_VALUE : 0L;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long positionUs) {
        for (int i = 0; i < this.sampleStreams.size(); i++) {
            this.sampleStreams.get(i).reset();
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCompleted(SourceLoadable loadable, long elapsedRealtimeMs, long loadDurationMs) {
        this.sampleSize = (int) loadable.dataSource.getBytesRead();
        this.sampleData = (byte[]) Assertions.checkNotNull(loadable.sampleData);
        this.loadingFinished = true;
        StatsDataSource statsDataSource = loadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), elapsedRealtimeMs, loadDurationMs, this.sampleSize);
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.eventDispatcher.loadCompleted(loadEventInfo, 1, -1, this.format, 0, null, 0L, this.durationUs);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCanceled(SourceLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        StatsDataSource statsDataSource = loadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), elapsedRealtimeMs, loadDurationMs, statsDataSource.getBytesRead());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        this.eventDispatcher.loadCanceled(loadEventInfo, 1, -1, null, 0, null, 0L, this.durationUs);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public Loader.LoadErrorAction onLoadError(SourceLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        Loader.LoadErrorAction loadErrorActionCreateRetryAction;
        StatsDataSource statsDataSource = loadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(loadable.loadTaskId, loadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), elapsedRealtimeMs, loadDurationMs, statsDataSource.getBytesRead());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(1, -1, this.format, 0, null, 0L, C.usToMs(this.durationUs)), error, errorCount));
        boolean z = retryDelayMsFor == C.TIME_UNSET || errorCount >= this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(1);
        if (this.treatLoadErrorsAsEndOfStream && z) {
            Log.w(TAG, "Loading failed, treating as end-of-stream.", error);
            this.loadingFinished = true;
            loadErrorActionCreateRetryAction = Loader.DONT_RETRY;
        } else if (retryDelayMsFor != C.TIME_UNSET) {
            loadErrorActionCreateRetryAction = Loader.createRetryAction(false, retryDelayMsFor);
        } else {
            loadErrorActionCreateRetryAction = Loader.DONT_RETRY_FATAL;
        }
        Loader.LoadErrorAction loadErrorAction = loadErrorActionCreateRetryAction;
        boolean z2 = !loadErrorAction.isRetry();
        this.eventDispatcher.loadError(loadEventInfo, 1, -1, this.format, 0, null, 0L, this.durationUs, error, z2);
        if (z2) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(loadable.loadTaskId);
        }
        return loadErrorAction;
    }

    private final class SampleStreamImpl implements SampleStream {
        private static final int STREAM_STATE_END_OF_STREAM = 2;
        private static final int STREAM_STATE_SEND_FORMAT = 0;
        private static final int STREAM_STATE_SEND_SAMPLE = 1;
        private boolean notifiedDownstreamFormat;
        private int streamState;

        private SampleStreamImpl() {
        }

        public void reset() {
            if (this.streamState == 2) {
                this.streamState = 1;
            }
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return SingleSampleMediaPeriod.this.loadingFinished;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            if (SingleSampleMediaPeriod.this.treatLoadErrorsAsEndOfStream) {
                return;
            }
            SingleSampleMediaPeriod.this.loader.maybeThrowError();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            maybeNotifyDownstreamFormat();
            if (SingleSampleMediaPeriod.this.loadingFinished && SingleSampleMediaPeriod.this.sampleData == null) {
                this.streamState = 2;
            }
            int i = this.streamState;
            if (i == 2) {
                buffer.addFlag(4);
                return -4;
            }
            if ((readFlags & 2) != 0 || i == 0) {
                formatHolder.format = SingleSampleMediaPeriod.this.format;
                this.streamState = 1;
                return -5;
            }
            if (!SingleSampleMediaPeriod.this.loadingFinished) {
                return -3;
            }
            Assertions.checkNotNull(SingleSampleMediaPeriod.this.sampleData);
            buffer.addFlag(1);
            buffer.timeUs = 0L;
            if ((readFlags & 4) == 0) {
                buffer.ensureSpaceForWrite(SingleSampleMediaPeriod.this.sampleSize);
                buffer.data.put(SingleSampleMediaPeriod.this.sampleData, 0, SingleSampleMediaPeriod.this.sampleSize);
            }
            if ((readFlags & 1) == 0) {
                this.streamState = 2;
            }
            return -4;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long positionUs) {
            maybeNotifyDownstreamFormat();
            if (positionUs <= 0 || this.streamState == 2) {
                return 0;
            }
            this.streamState = 2;
            return 1;
        }

        private void maybeNotifyDownstreamFormat() {
            if (this.notifiedDownstreamFormat) {
                return;
            }
            SingleSampleMediaPeriod.this.eventDispatcher.downstreamFormatChanged(MimeTypes.getTrackType(SingleSampleMediaPeriod.this.format.sampleMimeType), SingleSampleMediaPeriod.this.format, 0, null, 0L);
            this.notifiedDownstreamFormat = true;
        }
    }

    static final class SourceLoadable implements Loader.Loadable {
        private final StatsDataSource dataSource;
        public final DataSpec dataSpec;
        public final long loadTaskId = LoadEventInfo.getNewId();
        private byte[] sampleData;

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
        }

        public SourceLoadable(DataSpec dataSpec, DataSource dataSource) {
            this.dataSpec = dataSpec;
            this.dataSource = new StatsDataSource(dataSource);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            this.dataSource.resetBytesRead();
            try {
                this.dataSource.open(this.dataSpec);
                int i = 0;
                while (i != -1) {
                    int bytesRead = (int) this.dataSource.getBytesRead();
                    byte[] bArr = this.sampleData;
                    if (bArr == null) {
                        this.sampleData = new byte[1024];
                    } else if (bytesRead == bArr.length) {
                        this.sampleData = Arrays.copyOf(bArr, bArr.length * 2);
                    }
                    StatsDataSource statsDataSource = this.dataSource;
                    byte[] bArr2 = this.sampleData;
                    i = statsDataSource.read(bArr2, bytesRead, bArr2.length - bytesRead);
                }
            } finally {
                Util.closeQuietly(this.dataSource);
            }
        }
    }
}
