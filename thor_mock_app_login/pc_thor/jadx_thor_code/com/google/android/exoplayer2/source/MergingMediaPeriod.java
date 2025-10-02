package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

/* loaded from: classes.dex */
final class MergingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final MediaPeriod[] periods;
    private TrackGroupArray trackGroups;
    private final ArrayList<MediaPeriod> childrenPendingPreparation = new ArrayList<>();
    private final IdentityHashMap<SampleStream, Integer> streamPeriodIndices = new IdentityHashMap<>();
    private MediaPeriod[] enabledPeriods = new MediaPeriod[0];

    public MergingMediaPeriod(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, long[] periodTimeOffsetsUs, MediaPeriod... periods) {
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.periods = periods;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        for (int i = 0; i < periods.length; i++) {
            if (periodTimeOffsetsUs[i] != 0) {
                this.periods[i] = new TimeOffsetMediaPeriod(periods[i], periodTimeOffsetsUs[i]);
            }
        }
    }

    public MediaPeriod getChildPeriod(int index) {
        MediaPeriod mediaPeriod = this.periods[index];
        return mediaPeriod instanceof TimeOffsetMediaPeriod ? ((TimeOffsetMediaPeriod) mediaPeriod).mediaPeriod : mediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        this.callback = callback;
        Collections.addAll(this.childrenPendingPreparation, this.periods);
        for (MediaPeriod mediaPeriod : this.periods) {
            mediaPeriod.prepare(this, positionUs);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        for (MediaPeriod mediaPeriod : this.periods) {
            mediaPeriod.maybeThrowPrepareError();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.trackGroups);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int[] iArr = new int[selections.length];
        int[] iArr2 = new int[selections.length];
        for (int i = 0; i < selections.length; i++) {
            SampleStream sampleStream = streams[i];
            Integer num = sampleStream == null ? null : this.streamPeriodIndices.get(sampleStream);
            iArr[i] = num == null ? -1 : num.intValue();
            iArr2[i] = -1;
            ExoTrackSelection exoTrackSelection = selections[i];
            if (exoTrackSelection != null) {
                TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                int i2 = 0;
                while (true) {
                    MediaPeriod[] mediaPeriodArr = this.periods;
                    if (i2 >= mediaPeriodArr.length) {
                        break;
                    }
                    if (mediaPeriodArr[i2].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i] = i2;
                        break;
                    }
                    i2++;
                }
            }
        }
        this.streamPeriodIndices.clear();
        int length = selections.length;
        SampleStream[] sampleStreamArr = new SampleStream[length];
        SampleStream[] sampleStreamArr2 = new SampleStream[selections.length];
        ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[selections.length];
        ArrayList arrayList = new ArrayList(this.periods.length);
        long j = positionUs;
        int i3 = 0;
        while (i3 < this.periods.length) {
            for (int i4 = 0; i4 < selections.length; i4++) {
                sampleStreamArr2[i4] = iArr[i4] == i3 ? streams[i4] : null;
                exoTrackSelectionArr[i4] = iArr2[i4] == i3 ? selections[i4] : null;
            }
            int i5 = i3;
            ArrayList arrayList2 = arrayList;
            ExoTrackSelection[] exoTrackSelectionArr2 = exoTrackSelectionArr;
            long jSelectTracks = this.periods[i3].selectTracks(exoTrackSelectionArr, mayRetainStreamFlags, sampleStreamArr2, streamResetFlags, j);
            if (i5 == 0) {
                j = jSelectTracks;
            } else if (jSelectTracks != j) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            boolean z = false;
            for (int i6 = 0; i6 < selections.length; i6++) {
                if (iArr2[i6] == i5) {
                    SampleStream sampleStream2 = (SampleStream) Assertions.checkNotNull(sampleStreamArr2[i6]);
                    sampleStreamArr[i6] = sampleStreamArr2[i6];
                    this.streamPeriodIndices.put(sampleStream2, Integer.valueOf(i5));
                    z = true;
                } else if (iArr[i6] == i5) {
                    Assertions.checkState(sampleStreamArr2[i6] == null);
                }
            }
            if (z) {
                arrayList2.add(this.periods[i5]);
            }
            i3 = i5 + 1;
            arrayList = arrayList2;
            exoTrackSelectionArr = exoTrackSelectionArr2;
        }
        System.arraycopy(sampleStreamArr, 0, streams, 0, length);
        MediaPeriod[] mediaPeriodArr2 = (MediaPeriod[]) arrayList.toArray(new MediaPeriod[0]);
        this.enabledPeriods = mediaPeriodArr2;
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(mediaPeriodArr2);
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (MediaPeriod mediaPeriod : this.enabledPeriods) {
            mediaPeriod.discardBuffer(positionUs, toKeyframe);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        if (!this.childrenPendingPreparation.isEmpty()) {
            int size = this.childrenPendingPreparation.size();
            for (int i = 0; i < size; i++) {
                this.childrenPendingPreparation.get(i).continueLoading(positionUs);
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

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        long j = -9223372036854775807L;
        for (MediaPeriod mediaPeriod : this.enabledPeriods) {
            long discontinuity = mediaPeriod.readDiscontinuity();
            if (discontinuity == C.TIME_UNSET) {
                if (j != C.TIME_UNSET && mediaPeriod.seekToUs(j) != j) {
                    throw new IllegalStateException("Unexpected child seekToUs result.");
                }
            } else if (j == C.TIME_UNSET) {
                for (MediaPeriod mediaPeriod2 : this.enabledPeriods) {
                    if (mediaPeriod2 == mediaPeriod) {
                        break;
                    }
                    if (mediaPeriod2.seekToUs(discontinuity) != discontinuity) {
                        throw new IllegalStateException("Unexpected child seekToUs result.");
                    }
                }
                j = discontinuity;
            } else if (discontinuity != j) {
                throw new IllegalStateException("Conflicting discontinuities.");
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long positionUs) {
        long jSeekToUs = this.enabledPeriods[0].seekToUs(positionUs);
        int i = 1;
        while (true) {
            MediaPeriod[] mediaPeriodArr = this.enabledPeriods;
            if (i >= mediaPeriodArr.length) {
                return jSeekToUs;
            }
            if (mediaPeriodArr[i].seekToUs(jSeekToUs) != jSeekToUs) {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
            i++;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        MediaPeriod[] mediaPeriodArr = this.enabledPeriods;
        return (mediaPeriodArr.length > 0 ? mediaPeriodArr[0] : this.periods[0]).getAdjustedSeekPositionUs(positionUs, seekParameters);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod preparedPeriod) {
        this.childrenPendingPreparation.remove(preparedPeriod);
        if (this.childrenPendingPreparation.isEmpty()) {
            int i = 0;
            for (MediaPeriod mediaPeriod : this.periods) {
                i += mediaPeriod.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i];
            int i2 = 0;
            for (MediaPeriod mediaPeriod2 : this.periods) {
                TrackGroupArray trackGroups = mediaPeriod2.getTrackGroups();
                int i3 = trackGroups.length;
                int i4 = 0;
                while (i4 < i3) {
                    trackGroupArr[i2] = trackGroups.get(i4);
                    i4++;
                    i2++;
                }
            }
            this.trackGroups = new TrackGroupArray(trackGroupArr);
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
        }
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(MediaPeriod ignored) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
    }

    private static final class TimeOffsetMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
        private MediaPeriod.Callback callback;
        private final MediaPeriod mediaPeriod;
        private final long timeOffsetUs;

        public TimeOffsetMediaPeriod(MediaPeriod mediaPeriod, long timeOffsetUs) {
            this.mediaPeriod = mediaPeriod;
            this.timeOffsetUs = timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long positionUs) {
            this.callback = callback;
            this.mediaPeriod.prepare(this, positionUs - this.timeOffsetUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() throws IOException {
            this.mediaPeriod.maybeThrowPrepareError();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return this.mediaPeriod.getTrackGroups();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> trackSelections) {
            return this.mediaPeriod.getStreamKeys(trackSelections);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
            SampleStream[] sampleStreamArr = new SampleStream[streams.length];
            int i = 0;
            while (true) {
                SampleStream childStream = null;
                if (i >= streams.length) {
                    break;
                }
                TimeOffsetSampleStream timeOffsetSampleStream = (TimeOffsetSampleStream) streams[i];
                if (timeOffsetSampleStream != null) {
                    childStream = timeOffsetSampleStream.getChildStream();
                }
                sampleStreamArr[i] = childStream;
                i++;
            }
            long jSelectTracks = this.mediaPeriod.selectTracks(selections, mayRetainStreamFlags, sampleStreamArr, streamResetFlags, positionUs - this.timeOffsetUs);
            for (int i2 = 0; i2 < streams.length; i2++) {
                SampleStream sampleStream = sampleStreamArr[i2];
                if (sampleStream == null) {
                    streams[i2] = null;
                } else {
                    SampleStream sampleStream2 = streams[i2];
                    if (sampleStream2 == null || ((TimeOffsetSampleStream) sampleStream2).getChildStream() != sampleStream) {
                        streams[i2] = new TimeOffsetSampleStream(sampleStream, this.timeOffsetUs);
                    }
                }
            }
            return jSelectTracks + this.timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long positionUs, boolean toKeyframe) {
            this.mediaPeriod.discardBuffer(positionUs - this.timeOffsetUs, toKeyframe);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            long discontinuity = this.mediaPeriod.readDiscontinuity();
            return discontinuity == C.TIME_UNSET ? C.TIME_UNSET : this.timeOffsetUs + discontinuity;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long positionUs) {
            return this.mediaPeriod.seekToUs(positionUs - this.timeOffsetUs) + this.timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
            return this.mediaPeriod.getAdjustedSeekPositionUs(positionUs - this.timeOffsetUs, seekParameters) + this.timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
            if (bufferedPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.timeOffsetUs + bufferedPositionUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.timeOffsetUs + nextLoadPositionUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long positionUs) {
            return this.mediaPeriod.continueLoading(positionUs - this.timeOffsetUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return this.mediaPeriod.isLoading();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long positionUs) {
            this.mediaPeriod.reevaluateBuffer(positionUs - this.timeOffsetUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
        }

        @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
        public void onContinueLoadingRequested(MediaPeriod source) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
        }
    }

    private static final class TimeOffsetSampleStream implements SampleStream {
        private final SampleStream sampleStream;
        private final long timeOffsetUs;

        public TimeOffsetSampleStream(SampleStream sampleStream, long timeOffsetUs) {
            this.sampleStream = sampleStream;
            this.timeOffsetUs = timeOffsetUs;
        }

        public SampleStream getChildStream() {
            return this.sampleStream;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return this.sampleStream.isReady();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.sampleStream.maybeThrowError();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            int data = this.sampleStream.readData(formatHolder, buffer, readFlags);
            if (data == -4) {
                buffer.timeUs = Math.max(0L, buffer.timeUs + this.timeOffsetUs);
            }
            return data;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long positionUs) {
            return this.sampleStream.skipData(positionUs - this.timeOffsetUs);
        }
    }
}
