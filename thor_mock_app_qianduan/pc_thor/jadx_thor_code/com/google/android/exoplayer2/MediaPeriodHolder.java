package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.ClippingMediaPeriod;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes.dex */
final class MediaPeriodHolder {
    private static final String TAG = "MediaPeriodHolder";
    public boolean allRenderersInCorrectState;
    public boolean hasEnabledTracks;
    public MediaPeriodInfo info;
    private final boolean[] mayRetainStreamFlags;
    public final MediaPeriod mediaPeriod;
    private final MediaSourceList mediaSourceList;
    private MediaPeriodHolder next;
    public boolean prepared;
    private final RendererCapabilities[] rendererCapabilities;
    private long rendererPositionOffsetUs;
    public final SampleStream[] sampleStreams;
    private TrackGroupArray trackGroups = TrackGroupArray.EMPTY;
    private final TrackSelector trackSelector;
    private TrackSelectorResult trackSelectorResult;
    public final Object uid;

    public MediaPeriodHolder(RendererCapabilities[] rendererCapabilities, long rendererPositionOffsetUs, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, MediaPeriodInfo info, TrackSelectorResult emptyTrackSelectorResult) {
        this.rendererCapabilities = rendererCapabilities;
        this.rendererPositionOffsetUs = rendererPositionOffsetUs;
        this.trackSelector = trackSelector;
        this.mediaSourceList = mediaSourceList;
        this.uid = info.id.periodUid;
        this.info = info;
        this.trackSelectorResult = emptyTrackSelectorResult;
        this.sampleStreams = new SampleStream[rendererCapabilities.length];
        this.mayRetainStreamFlags = new boolean[rendererCapabilities.length];
        this.mediaPeriod = createMediaPeriod(info.id, mediaSourceList, allocator, info.startPositionUs, info.endPositionUs);
    }

    public long toRendererTime(long periodTimeUs) {
        return periodTimeUs + getRendererOffset();
    }

    public long toPeriodTime(long rendererTimeUs) {
        return rendererTimeUs - getRendererOffset();
    }

    public long getRendererOffset() {
        return this.rendererPositionOffsetUs;
    }

    public void setRendererOffset(long rendererPositionOffsetUs) {
        this.rendererPositionOffsetUs = rendererPositionOffsetUs;
    }

    public long getStartPositionRendererTime() {
        return this.info.startPositionUs + this.rendererPositionOffsetUs;
    }

    public boolean isFullyBuffered() {
        return this.prepared && (!this.hasEnabledTracks || this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE);
    }

    public long getBufferedPositionUs() {
        if (!this.prepared) {
            return this.info.startPositionUs;
        }
        long bufferedPositionUs = this.hasEnabledTracks ? this.mediaPeriod.getBufferedPositionUs() : Long.MIN_VALUE;
        return bufferedPositionUs == Long.MIN_VALUE ? this.info.durationUs : bufferedPositionUs;
    }

    public long getNextLoadPositionUs() {
        if (this.prepared) {
            return this.mediaPeriod.getNextLoadPositionUs();
        }
        return 0L;
    }

    public void handlePrepared(float playbackSpeed, Timeline timeline) throws ExoPlaybackException {
        this.prepared = true;
        this.trackGroups = this.mediaPeriod.getTrackGroups();
        TrackSelectorResult trackSelectorResultSelectTracks = selectTracks(playbackSpeed, timeline);
        long jMax = this.info.startPositionUs;
        if (this.info.durationUs != C.TIME_UNSET && jMax >= this.info.durationUs) {
            jMax = Math.max(0L, this.info.durationUs - 1);
        }
        long jApplyTrackSelection = applyTrackSelection(trackSelectorResultSelectTracks, jMax, false);
        this.rendererPositionOffsetUs += this.info.startPositionUs - jApplyTrackSelection;
        this.info = this.info.copyWithStartPositionUs(jApplyTrackSelection);
    }

    public void reevaluateBuffer(long rendererPositionUs) {
        Assertions.checkState(isLoadingMediaPeriod());
        if (this.prepared) {
            this.mediaPeriod.reevaluateBuffer(toPeriodTime(rendererPositionUs));
        }
    }

    public void continueLoading(long rendererPositionUs) {
        Assertions.checkState(isLoadingMediaPeriod());
        this.mediaPeriod.continueLoading(toPeriodTime(rendererPositionUs));
    }

    public TrackSelectorResult selectTracks(float playbackSpeed, Timeline timeline) throws ExoPlaybackException {
        TrackSelectorResult trackSelectorResultSelectTracks = this.trackSelector.selectTracks(this.rendererCapabilities, getTrackGroups(), this.info.id, timeline);
        for (ExoTrackSelection exoTrackSelection : trackSelectorResultSelectTracks.selections) {
            if (exoTrackSelection != null) {
                exoTrackSelection.onPlaybackSpeed(playbackSpeed);
            }
        }
        return trackSelectorResultSelectTracks;
    }

    public long applyTrackSelection(TrackSelectorResult trackSelectorResult, long positionUs, boolean forceRecreateStreams) {
        return applyTrackSelection(trackSelectorResult, positionUs, forceRecreateStreams, new boolean[this.rendererCapabilities.length]);
    }

    public long applyTrackSelection(TrackSelectorResult newTrackSelectorResult, long positionUs, boolean forceRecreateStreams, boolean[] streamResetFlags) {
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= newTrackSelectorResult.length) {
                break;
            }
            boolean[] zArr = this.mayRetainStreamFlags;
            if (forceRecreateStreams || !newTrackSelectorResult.isEquivalent(this.trackSelectorResult, i)) {
                z = false;
            }
            zArr[i] = z;
            i++;
        }
        disassociateNoSampleRenderersWithEmptySampleStream(this.sampleStreams);
        disableTrackSelectionsInResult();
        this.trackSelectorResult = newTrackSelectorResult;
        enableTrackSelectionsInResult();
        long jSelectTracks = this.mediaPeriod.selectTracks(newTrackSelectorResult.selections, this.mayRetainStreamFlags, this.sampleStreams, streamResetFlags, positionUs);
        associateNoSampleRenderersWithEmptySampleStream(this.sampleStreams);
        this.hasEnabledTracks = false;
        int i2 = 0;
        while (true) {
            SampleStream[] sampleStreamArr = this.sampleStreams;
            if (i2 >= sampleStreamArr.length) {
                return jSelectTracks;
            }
            if (sampleStreamArr[i2] != null) {
                Assertions.checkState(newTrackSelectorResult.isRendererEnabled(i2));
                if (this.rendererCapabilities[i2].getTrackType() != 7) {
                    this.hasEnabledTracks = true;
                }
            } else {
                Assertions.checkState(newTrackSelectorResult.selections[i2] == null);
            }
            i2++;
        }
    }

    public void release() {
        disableTrackSelectionsInResult();
        releaseMediaPeriod(this.mediaSourceList, this.mediaPeriod);
    }

    public void setNext(MediaPeriodHolder nextMediaPeriodHolder) {
        if (nextMediaPeriodHolder == this.next) {
            return;
        }
        disableTrackSelectionsInResult();
        this.next = nextMediaPeriodHolder;
        enableTrackSelectionsInResult();
    }

    public MediaPeriodHolder getNext() {
        return this.next;
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public TrackSelectorResult getTrackSelectorResult() {
        return this.trackSelectorResult;
    }

    public void updateClipping() {
        if (this.mediaPeriod instanceof ClippingMediaPeriod) {
            ((ClippingMediaPeriod) this.mediaPeriod).updateClipping(0L, this.info.endPositionUs == C.TIME_UNSET ? Long.MIN_VALUE : this.info.endPositionUs);
        }
    }

    private void enableTrackSelectionsInResult() {
        if (isLoadingMediaPeriod()) {
            for (int i = 0; i < this.trackSelectorResult.length; i++) {
                boolean zIsRendererEnabled = this.trackSelectorResult.isRendererEnabled(i);
                ExoTrackSelection exoTrackSelection = this.trackSelectorResult.selections[i];
                if (zIsRendererEnabled && exoTrackSelection != null) {
                    exoTrackSelection.enable();
                }
            }
        }
    }

    private void disableTrackSelectionsInResult() {
        if (isLoadingMediaPeriod()) {
            for (int i = 0; i < this.trackSelectorResult.length; i++) {
                boolean zIsRendererEnabled = this.trackSelectorResult.isRendererEnabled(i);
                ExoTrackSelection exoTrackSelection = this.trackSelectorResult.selections[i];
                if (zIsRendererEnabled && exoTrackSelection != null) {
                    exoTrackSelection.disable();
                }
            }
        }
    }

    private void disassociateNoSampleRenderersWithEmptySampleStream(SampleStream[] sampleStreams) {
        int i = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.rendererCapabilities;
            if (i >= rendererCapabilitiesArr.length) {
                return;
            }
            if (rendererCapabilitiesArr[i].getTrackType() == 7) {
                sampleStreams[i] = null;
            }
            i++;
        }
    }

    private void associateNoSampleRenderersWithEmptySampleStream(SampleStream[] sampleStreams) {
        int i = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.rendererCapabilities;
            if (i >= rendererCapabilitiesArr.length) {
                return;
            }
            if (rendererCapabilitiesArr[i].getTrackType() == 7 && this.trackSelectorResult.isRendererEnabled(i)) {
                sampleStreams[i] = new EmptySampleStream();
            }
            i++;
        }
    }

    private boolean isLoadingMediaPeriod() {
        return this.next == null;
    }

    private static MediaPeriod createMediaPeriod(MediaSource.MediaPeriodId id, MediaSourceList mediaSourceList, Allocator allocator, long startPositionUs, long endPositionUs) {
        MediaPeriod mediaPeriodCreatePeriod = mediaSourceList.createPeriod(id, allocator, startPositionUs);
        return endPositionUs != C.TIME_UNSET ? new ClippingMediaPeriod(mediaPeriodCreatePeriod, true, 0L, endPositionUs) : mediaPeriodCreatePeriod;
    }

    private static void releaseMediaPeriod(MediaSourceList mediaSourceList, MediaPeriod mediaPeriod) {
        try {
            if (mediaPeriod instanceof ClippingMediaPeriod) {
                mediaSourceList.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
            } else {
                mediaSourceList.releasePeriod(mediaPeriod);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Period release failed.", e);
        }
    }
}
