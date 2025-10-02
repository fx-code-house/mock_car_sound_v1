package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public class AdaptiveTrackSelection extends BaseTrackSelection {
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.7f;
    public static final float DEFAULT_BUFFERED_FRACTION_TO_LIVE_EDGE_FOR_QUALITY_INCREASE = 0.75f;
    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
    private static final long MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS = 1000;
    private static final String TAG = "AdaptiveTrackSelection";
    private final ImmutableList<AdaptationCheckpoint> adaptationCheckpoints;
    private final float bandwidthFraction;
    private final BandwidthMeter bandwidthMeter;
    private final float bufferedFractionToLiveEdgeForQualityIncrease;
    private final Clock clock;
    private MediaChunk lastBufferEvaluationMediaChunk;
    private long lastBufferEvaluationMs;
    private final long maxDurationForQualityDecreaseUs;
    private final long minDurationForQualityIncreaseUs;
    private final long minDurationToRetainAfterDiscardUs;
    private float playbackSpeed;
    private int reason;
    private int selectedIndex;

    protected boolean canSelectFormat(Format format, int trackBitrate, long effectiveBitrate) {
        return ((long) trackBitrate) <= effectiveBitrate;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public Object getSelectionData() {
        return null;
    }

    public static class Factory implements ExoTrackSelection.Factory {
        private final float bandwidthFraction;
        private final float bufferedFractionToLiveEdgeForQualityIncrease;
        private final Clock clock;
        private final int maxDurationForQualityDecreaseMs;
        private final int minDurationForQualityIncreaseMs;
        private final int minDurationToRetainAfterDiscardMs;

        public Factory() {
            this(10000, 25000, 25000, 0.7f, 0.75f, Clock.DEFAULT);
        }

        public Factory(int minDurationForQualityIncreaseMs, int maxDurationForQualityDecreaseMs, int minDurationToRetainAfterDiscardMs, float bandwidthFraction) {
            this(minDurationForQualityIncreaseMs, maxDurationForQualityDecreaseMs, minDurationToRetainAfterDiscardMs, bandwidthFraction, 0.75f, Clock.DEFAULT);
        }

        public Factory(int minDurationForQualityIncreaseMs, int maxDurationForQualityDecreaseMs, int minDurationToRetainAfterDiscardMs, float bandwidthFraction, float bufferedFractionToLiveEdgeForQualityIncrease, Clock clock) {
            this.minDurationForQualityIncreaseMs = minDurationForQualityIncreaseMs;
            this.maxDurationForQualityDecreaseMs = maxDurationForQualityDecreaseMs;
            this.minDurationToRetainAfterDiscardMs = minDurationToRetainAfterDiscardMs;
            this.bandwidthFraction = bandwidthFraction;
            this.bufferedFractionToLiveEdgeForQualityIncrease = bufferedFractionToLiveEdgeForQualityIncrease;
            this.clock = clock;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection.Factory
        public final ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitions, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            ExoTrackSelection exoTrackSelectionCreateAdaptiveTrackSelection;
            ImmutableList adaptationCheckpoints = AdaptiveTrackSelection.getAdaptationCheckpoints(definitions);
            ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitions.length];
            for (int i = 0; i < definitions.length; i++) {
                ExoTrackSelection.Definition definition = definitions[i];
                if (definition != null && definition.tracks.length != 0) {
                    if (definition.tracks.length == 1) {
                        exoTrackSelectionCreateAdaptiveTrackSelection = new FixedTrackSelection(definition.group, definition.tracks[0], definition.type);
                    } else {
                        exoTrackSelectionCreateAdaptiveTrackSelection = createAdaptiveTrackSelection(definition.group, definition.tracks, definition.type, bandwidthMeter, (ImmutableList) adaptationCheckpoints.get(i));
                    }
                    exoTrackSelectionArr[i] = exoTrackSelectionCreateAdaptiveTrackSelection;
                }
            }
            return exoTrackSelectionArr;
        }

        protected AdaptiveTrackSelection createAdaptiveTrackSelection(TrackGroup group, int[] tracks, int type, BandwidthMeter bandwidthMeter, ImmutableList<AdaptationCheckpoint> adaptationCheckpoints) {
            return new AdaptiveTrackSelection(group, tracks, type, bandwidthMeter, this.minDurationForQualityIncreaseMs, this.maxDurationForQualityDecreaseMs, this.minDurationToRetainAfterDiscardMs, this.bandwidthFraction, this.bufferedFractionToLiveEdgeForQualityIncrease, adaptationCheckpoints, this.clock);
        }
    }

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter) {
        this(group, tracks, 0, bandwidthMeter, 10000L, 25000L, 25000L, 0.7f, 0.75f, ImmutableList.of(), Clock.DEFAULT);
    }

    protected AdaptiveTrackSelection(TrackGroup group, int[] tracks, int type, BandwidthMeter bandwidthMeter, long minDurationForQualityIncreaseMs, long maxDurationForQualityDecreaseMs, long minDurationToRetainAfterDiscardMs, float bandwidthFraction, float bufferedFractionToLiveEdgeForQualityIncrease, List<AdaptationCheckpoint> adaptationCheckpoints, Clock clock) {
        super(group, tracks, type);
        if (minDurationToRetainAfterDiscardMs < minDurationForQualityIncreaseMs) {
            Log.w(TAG, "Adjusting minDurationToRetainAfterDiscardMs to be at least minDurationForQualityIncreaseMs");
            minDurationToRetainAfterDiscardMs = minDurationForQualityIncreaseMs;
        }
        this.bandwidthMeter = bandwidthMeter;
        this.minDurationForQualityIncreaseUs = minDurationForQualityIncreaseMs * 1000;
        this.maxDurationForQualityDecreaseUs = maxDurationForQualityDecreaseMs * 1000;
        this.minDurationToRetainAfterDiscardUs = minDurationToRetainAfterDiscardMs * 1000;
        this.bandwidthFraction = bandwidthFraction;
        this.bufferedFractionToLiveEdgeForQualityIncrease = bufferedFractionToLiveEdgeForQualityIncrease;
        this.adaptationCheckpoints = ImmutableList.copyOf((Collection) adaptationCheckpoints);
        this.clock = clock;
        this.playbackSpeed = 1.0f;
        this.reason = 0;
        this.lastBufferEvaluationMs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void enable() {
        this.lastBufferEvaluationMs = C.TIME_UNSET;
        this.lastBufferEvaluationMediaChunk = null;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void disable() {
        this.lastBufferEvaluationMediaChunk = null;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void onPlaybackSpeed(float playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> queue, MediaChunkIterator[] mediaChunkIterators) {
        long jElapsedRealtime = this.clock.elapsedRealtime();
        long nextChunkDurationUs = getNextChunkDurationUs(mediaChunkIterators, queue);
        int i = this.reason;
        if (i == 0) {
            this.reason = 1;
            this.selectedIndex = determineIdealSelectedIndex(jElapsedRealtime, nextChunkDurationUs);
            return;
        }
        int i2 = this.selectedIndex;
        int iIndexOf = queue.isEmpty() ? -1 : indexOf(((MediaChunk) Iterables.getLast(queue)).trackFormat);
        if (iIndexOf != -1) {
            i = ((MediaChunk) Iterables.getLast(queue)).trackSelectionReason;
            i2 = iIndexOf;
        }
        int iDetermineIdealSelectedIndex = determineIdealSelectedIndex(jElapsedRealtime, nextChunkDurationUs);
        if (!isBlacklisted(i2, jElapsedRealtime)) {
            Format format = getFormat(i2);
            Format format2 = getFormat(iDetermineIdealSelectedIndex);
            if ((format2.bitrate > format.bitrate && bufferedDurationUs < minDurationForQualityIncreaseUs(availableDurationUs)) || (format2.bitrate < format.bitrate && bufferedDurationUs >= this.maxDurationForQualityDecreaseUs)) {
                iDetermineIdealSelectedIndex = i2;
            }
        }
        if (iDetermineIdealSelectedIndex != i2) {
            i = 3;
        }
        this.reason = i;
        this.selectedIndex = iDetermineIdealSelectedIndex;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int getSelectionReason() {
        return this.reason;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        long jElapsedRealtime = this.clock.elapsedRealtime();
        if (!shouldEvaluateQueueSize(jElapsedRealtime, queue)) {
            return queue.size();
        }
        this.lastBufferEvaluationMs = jElapsedRealtime;
        this.lastBufferEvaluationMediaChunk = queue.isEmpty() ? null : (MediaChunk) Iterables.getLast(queue);
        if (queue.isEmpty()) {
            return 0;
        }
        int size = queue.size();
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(queue.get(size - 1).startTimeUs - playbackPositionUs, this.playbackSpeed);
        long minDurationToRetainAfterDiscardUs = getMinDurationToRetainAfterDiscardUs();
        if (playoutDurationForMediaDuration < minDurationToRetainAfterDiscardUs) {
            return size;
        }
        Format format = getFormat(determineIdealSelectedIndex(jElapsedRealtime, getLastChunkDurationUs(queue)));
        for (int i = 0; i < size; i++) {
            MediaChunk mediaChunk = queue.get(i);
            Format format2 = mediaChunk.trackFormat;
            if (Util.getPlayoutDurationForMediaDuration(mediaChunk.startTimeUs - playbackPositionUs, this.playbackSpeed) >= minDurationToRetainAfterDiscardUs && format2.bitrate < format.bitrate && format2.height != -1 && format2.height < 720 && format2.width != -1 && format2.width < 1280 && format2.height < format.height) {
                return i;
            }
        }
        return size;
    }

    protected boolean shouldEvaluateQueueSize(long nowMs, List<? extends MediaChunk> queue) {
        long j = this.lastBufferEvaluationMs;
        return j == C.TIME_UNSET || nowMs - j >= 1000 || !(queue.isEmpty() || ((MediaChunk) Iterables.getLast(queue)).equals(this.lastBufferEvaluationMediaChunk));
    }

    protected long getMinDurationToRetainAfterDiscardUs() {
        return this.minDurationToRetainAfterDiscardUs;
    }

    private int determineIdealSelectedIndex(long nowMs, long chunkDurationUs) {
        long allocatedBandwidth = getAllocatedBandwidth(chunkDurationUs);
        int i = 0;
        for (int i2 = 0; i2 < this.length; i2++) {
            if (nowMs == Long.MIN_VALUE || !isBlacklisted(i2, nowMs)) {
                Format format = getFormat(i2);
                if (canSelectFormat(format, format.bitrate, allocatedBandwidth)) {
                    return i2;
                }
                i = i2;
            }
        }
        return i;
    }

    private long minDurationForQualityIncreaseUs(long availableDurationUs) {
        if (availableDurationUs != C.TIME_UNSET && availableDurationUs <= this.minDurationForQualityIncreaseUs) {
            return (long) (availableDurationUs * this.bufferedFractionToLiveEdgeForQualityIncrease);
        }
        return this.minDurationForQualityIncreaseUs;
    }

    private long getNextChunkDurationUs(MediaChunkIterator[] mediaChunkIterators, List<? extends MediaChunk> queue) {
        int i = this.selectedIndex;
        if (i < mediaChunkIterators.length && mediaChunkIterators[i].next()) {
            MediaChunkIterator mediaChunkIterator = mediaChunkIterators[this.selectedIndex];
            return mediaChunkIterator.getChunkEndTimeUs() - mediaChunkIterator.getChunkStartTimeUs();
        }
        for (MediaChunkIterator mediaChunkIterator2 : mediaChunkIterators) {
            if (mediaChunkIterator2.next()) {
                return mediaChunkIterator2.getChunkEndTimeUs() - mediaChunkIterator2.getChunkStartTimeUs();
            }
        }
        return getLastChunkDurationUs(queue);
    }

    private long getLastChunkDurationUs(List<? extends MediaChunk> queue) {
        if (queue.isEmpty()) {
            return C.TIME_UNSET;
        }
        MediaChunk mediaChunk = (MediaChunk) Iterables.getLast(queue);
        return (mediaChunk.startTimeUs == C.TIME_UNSET || mediaChunk.endTimeUs == C.TIME_UNSET) ? C.TIME_UNSET : mediaChunk.endTimeUs - mediaChunk.startTimeUs;
    }

    private long getAllocatedBandwidth(long chunkDurationUs) {
        long totalAllocatableBandwidth = getTotalAllocatableBandwidth(chunkDurationUs);
        if (this.adaptationCheckpoints.isEmpty()) {
            return totalAllocatableBandwidth;
        }
        int i = 1;
        while (i < this.adaptationCheckpoints.size() - 1 && this.adaptationCheckpoints.get(i).totalBandwidth < totalAllocatableBandwidth) {
            i++;
        }
        AdaptationCheckpoint adaptationCheckpoint = this.adaptationCheckpoints.get(i - 1);
        AdaptationCheckpoint adaptationCheckpoint2 = this.adaptationCheckpoints.get(i);
        return adaptationCheckpoint.allocatedBandwidth + ((long) (((totalAllocatableBandwidth - adaptationCheckpoint.totalBandwidth) / (adaptationCheckpoint2.totalBandwidth - adaptationCheckpoint.totalBandwidth)) * (adaptationCheckpoint2.allocatedBandwidth - adaptationCheckpoint.allocatedBandwidth)));
    }

    private long getTotalAllocatableBandwidth(long chunkDurationUs) {
        long bitrateEstimate = (long) (this.bandwidthMeter.getBitrateEstimate() * this.bandwidthFraction);
        long timeToFirstByteEstimateUs = this.bandwidthMeter.getTimeToFirstByteEstimateUs();
        if (timeToFirstByteEstimateUs == C.TIME_UNSET || chunkDurationUs == C.TIME_UNSET) {
            return (long) (bitrateEstimate / this.playbackSpeed);
        }
        float f = chunkDurationUs;
        return (long) ((bitrateEstimate * Math.max((f / this.playbackSpeed) - timeToFirstByteEstimateUs, 0.0f)) / f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<ImmutableList<AdaptationCheckpoint>> getAdaptationCheckpoints(ExoTrackSelection.Definition[] definitions) {
        ArrayList arrayList = new ArrayList();
        for (ExoTrackSelection.Definition definition : definitions) {
            if (definition != null && definition.tracks.length > 1) {
                ImmutableList.Builder builder = ImmutableList.builder();
                builder.add((ImmutableList.Builder) new AdaptationCheckpoint(0L, 0L));
                arrayList.add(builder);
            } else {
                arrayList.add(null);
            }
        }
        long[][] sortedTrackBitrates = getSortedTrackBitrates(definitions);
        int[] iArr = new int[sortedTrackBitrates.length];
        long[] jArr = new long[sortedTrackBitrates.length];
        for (int i = 0; i < sortedTrackBitrates.length; i++) {
            long[] jArr2 = sortedTrackBitrates[i];
            jArr[i] = jArr2.length == 0 ? 0L : jArr2[0];
        }
        addCheckpoint(arrayList, jArr);
        ImmutableList<Integer> switchOrder = getSwitchOrder(sortedTrackBitrates);
        for (int i2 = 0; i2 < switchOrder.size(); i2++) {
            int iIntValue = switchOrder.get(i2).intValue();
            int i3 = iArr[iIntValue] + 1;
            iArr[iIntValue] = i3;
            jArr[iIntValue] = sortedTrackBitrates[iIntValue][i3];
            addCheckpoint(arrayList, jArr);
        }
        for (int i4 = 0; i4 < definitions.length; i4++) {
            if (arrayList.get(i4) != null) {
                jArr[i4] = jArr[i4] * 2;
            }
        }
        addCheckpoint(arrayList, jArr);
        ImmutableList.Builder builder2 = ImmutableList.builder();
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            ImmutableList.Builder builder3 = (ImmutableList.Builder) arrayList.get(i5);
            builder2.add((ImmutableList.Builder) (builder3 == null ? ImmutableList.of() : builder3.build()));
        }
        return builder2.build();
    }

    private static long[][] getSortedTrackBitrates(ExoTrackSelection.Definition[] definitions) {
        long[][] jArr = new long[definitions.length][];
        for (int i = 0; i < definitions.length; i++) {
            ExoTrackSelection.Definition definition = definitions[i];
            if (definition == null) {
                jArr[i] = new long[0];
            } else {
                jArr[i] = new long[definition.tracks.length];
                for (int i2 = 0; i2 < definition.tracks.length; i2++) {
                    jArr[i][i2] = definition.group.getFormat(definition.tracks[i2]).bitrate;
                }
                Arrays.sort(jArr[i]);
            }
        }
        return jArr;
    }

    private static ImmutableList<Integer> getSwitchOrder(long[][] trackBitrates) {
        Multimap multimapBuild = MultimapBuilder.treeKeys().arrayListValues().build();
        for (int i = 0; i < trackBitrates.length; i++) {
            long[] jArr = trackBitrates[i];
            if (jArr.length > 1) {
                int length = jArr.length;
                double[] dArr = new double[length];
                int i2 = 0;
                while (true) {
                    long[] jArr2 = trackBitrates[i];
                    double dLog = 0.0d;
                    if (i2 >= jArr2.length) {
                        break;
                    }
                    long j = jArr2[i2];
                    if (j != -1) {
                        dLog = Math.log(j);
                    }
                    dArr[i2] = dLog;
                    i2++;
                }
                int i3 = length - 1;
                double d = dArr[i3] - dArr[0];
                int i4 = 0;
                while (i4 < i3) {
                    double d2 = dArr[i4];
                    i4++;
                    multimapBuild.put(Double.valueOf(d == 0.0d ? 1.0d : (((d2 + dArr[i4]) * 0.5d) - dArr[0]) / d), Integer.valueOf(i));
                }
            }
        }
        return ImmutableList.copyOf(multimapBuild.values());
    }

    private static void addCheckpoint(List<ImmutableList.Builder<AdaptationCheckpoint>> checkPointBuilders, long[] checkpointBitrates) {
        long j = 0;
        for (long j2 : checkpointBitrates) {
            j += j2;
        }
        for (int i = 0; i < checkPointBuilders.size(); i++) {
            ImmutableList.Builder<AdaptationCheckpoint> builder = checkPointBuilders.get(i);
            if (builder != null) {
                builder.add((ImmutableList.Builder<AdaptationCheckpoint>) new AdaptationCheckpoint(j, checkpointBitrates[i]));
            }
        }
    }

    public static final class AdaptationCheckpoint {
        public final long allocatedBandwidth;
        public final long totalBandwidth;

        public AdaptationCheckpoint(long totalBandwidth, long allocatedBandwidth) {
            this.totalBandwidth = totalBandwidth;
            this.allocatedBandwidth = allocatedBandwidth;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof AdaptationCheckpoint)) {
                return false;
            }
            AdaptationCheckpoint adaptationCheckpoint = (AdaptationCheckpoint) o;
            return this.totalBandwidth == adaptationCheckpoint.totalBandwidth && this.allocatedBandwidth == adaptationCheckpoint.allocatedBandwidth;
        }

        public int hashCode() {
            return (((int) this.totalBandwidth) * 31) + ((int) this.allocatedBandwidth);
        }
    }
}
