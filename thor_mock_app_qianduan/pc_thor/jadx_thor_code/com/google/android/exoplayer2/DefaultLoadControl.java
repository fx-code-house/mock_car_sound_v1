package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public class DefaultLoadControl implements LoadControl {
    public static final int DEFAULT_AUDIO_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_BACK_BUFFER_DURATION_MS = 0;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_CAMERA_MOTION_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MAX_BUFFER_MS = 50000;
    public static final int DEFAULT_METADATA_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MIN_BUFFER_MS = 50000;
    public static final int DEFAULT_MIN_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_MUXED_BUFFER_SIZE = 144310272;
    public static final boolean DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS = false;
    public static final boolean DEFAULT_RETAIN_BACK_BUFFER_FROM_KEYFRAME = false;
    public static final int DEFAULT_TARGET_BUFFER_BYTES = -1;
    public static final int DEFAULT_TEXT_BUFFER_SIZE = 131072;
    public static final int DEFAULT_VIDEO_BUFFER_SIZE = 131072000;
    private final DefaultAllocator allocator;
    private final long backBufferDurationUs;
    private final long bufferForPlaybackAfterRebufferUs;
    private final long bufferForPlaybackUs;
    private boolean isLoading;
    private final long maxBufferUs;
    private final long minBufferUs;
    private final boolean prioritizeTimeOverSizeThresholds;
    private final boolean retainBackBufferFromKeyframe;
    private int targetBufferBytes;
    private final int targetBufferBytesOverwrite;

    public static final class Builder {
        private DefaultAllocator allocator;
        private boolean buildCalled;
        private int minBufferMs = 50000;
        private int maxBufferMs = 50000;
        private int bufferForPlaybackMs = DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS;
        private int bufferForPlaybackAfterRebufferMs = 5000;
        private int targetBufferBytes = -1;
        private boolean prioritizeTimeOverSizeThresholds = false;
        private int backBufferDurationMs = 0;
        private boolean retainBackBufferFromKeyframe = false;

        public Builder setAllocator(DefaultAllocator allocator) {
            Assertions.checkState(!this.buildCalled);
            this.allocator = allocator;
            return this;
        }

        public Builder setBufferDurationsMs(int minBufferMs, int maxBufferMs, int bufferForPlaybackMs, int bufferForPlaybackAfterRebufferMs) {
            Assertions.checkState(!this.buildCalled);
            DefaultLoadControl.assertGreaterOrEqual(bufferForPlaybackMs, 0, "bufferForPlaybackMs", SessionDescription.SUPPORTED_SDP_VERSION);
            DefaultLoadControl.assertGreaterOrEqual(bufferForPlaybackAfterRebufferMs, 0, "bufferForPlaybackAfterRebufferMs", SessionDescription.SUPPORTED_SDP_VERSION);
            DefaultLoadControl.assertGreaterOrEqual(minBufferMs, bufferForPlaybackMs, "minBufferMs", "bufferForPlaybackMs");
            DefaultLoadControl.assertGreaterOrEqual(minBufferMs, bufferForPlaybackAfterRebufferMs, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
            DefaultLoadControl.assertGreaterOrEqual(maxBufferMs, minBufferMs, "maxBufferMs", "minBufferMs");
            this.minBufferMs = minBufferMs;
            this.maxBufferMs = maxBufferMs;
            this.bufferForPlaybackMs = bufferForPlaybackMs;
            this.bufferForPlaybackAfterRebufferMs = bufferForPlaybackAfterRebufferMs;
            return this;
        }

        public Builder setTargetBufferBytes(int targetBufferBytes) {
            Assertions.checkState(!this.buildCalled);
            this.targetBufferBytes = targetBufferBytes;
            return this;
        }

        public Builder setPrioritizeTimeOverSizeThresholds(boolean prioritizeTimeOverSizeThresholds) {
            Assertions.checkState(!this.buildCalled);
            this.prioritizeTimeOverSizeThresholds = prioritizeTimeOverSizeThresholds;
            return this;
        }

        public Builder setBackBuffer(int backBufferDurationMs, boolean retainBackBufferFromKeyframe) {
            Assertions.checkState(!this.buildCalled);
            DefaultLoadControl.assertGreaterOrEqual(backBufferDurationMs, 0, "backBufferDurationMs", SessionDescription.SUPPORTED_SDP_VERSION);
            this.backBufferDurationMs = backBufferDurationMs;
            this.retainBackBufferFromKeyframe = retainBackBufferFromKeyframe;
            return this;
        }

        @Deprecated
        public DefaultLoadControl createDefaultLoadControl() {
            return build();
        }

        public DefaultLoadControl build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            if (this.allocator == null) {
                this.allocator = new DefaultAllocator(true, 65536);
            }
            return new DefaultLoadControl(this.allocator, this.minBufferMs, this.maxBufferMs, this.bufferForPlaybackMs, this.bufferForPlaybackAfterRebufferMs, this.targetBufferBytes, this.prioritizeTimeOverSizeThresholds, this.backBufferDurationMs, this.retainBackBufferFromKeyframe);
        }
    }

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, 65536), 50000, 50000, DEFAULT_BUFFER_FOR_PLAYBACK_MS, 5000, -1, false, 0, false);
    }

    protected DefaultLoadControl(DefaultAllocator allocator, int minBufferMs, int maxBufferMs, int bufferForPlaybackMs, int bufferForPlaybackAfterRebufferMs, int targetBufferBytes, boolean prioritizeTimeOverSizeThresholds, int backBufferDurationMs, boolean retainBackBufferFromKeyframe) {
        assertGreaterOrEqual(bufferForPlaybackMs, 0, "bufferForPlaybackMs", SessionDescription.SUPPORTED_SDP_VERSION);
        assertGreaterOrEqual(bufferForPlaybackAfterRebufferMs, 0, "bufferForPlaybackAfterRebufferMs", SessionDescription.SUPPORTED_SDP_VERSION);
        assertGreaterOrEqual(minBufferMs, bufferForPlaybackMs, "minBufferMs", "bufferForPlaybackMs");
        assertGreaterOrEqual(minBufferMs, bufferForPlaybackAfterRebufferMs, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
        assertGreaterOrEqual(maxBufferMs, minBufferMs, "maxBufferMs", "minBufferMs");
        assertGreaterOrEqual(backBufferDurationMs, 0, "backBufferDurationMs", SessionDescription.SUPPORTED_SDP_VERSION);
        this.allocator = allocator;
        this.minBufferUs = C.msToUs(minBufferMs);
        this.maxBufferUs = C.msToUs(maxBufferMs);
        this.bufferForPlaybackUs = C.msToUs(bufferForPlaybackMs);
        this.bufferForPlaybackAfterRebufferUs = C.msToUs(bufferForPlaybackAfterRebufferMs);
        this.targetBufferBytesOverwrite = targetBufferBytes;
        this.targetBufferBytes = targetBufferBytes == -1 ? 13107200 : targetBufferBytes;
        this.prioritizeTimeOverSizeThresholds = prioritizeTimeOverSizeThresholds;
        this.backBufferDurationUs = C.msToUs(backBufferDurationMs);
        this.retainBackBufferFromKeyframe = retainBackBufferFromKeyframe;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onPrepared() {
        reset(false);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onTracksSelected(Renderer[] renderers, TrackGroupArray trackGroups, ExoTrackSelection[] trackSelections) {
        int iCalculateTargetBufferBytes = this.targetBufferBytesOverwrite;
        if (iCalculateTargetBufferBytes == -1) {
            iCalculateTargetBufferBytes = calculateTargetBufferBytes(renderers, trackSelections);
        }
        this.targetBufferBytes = iCalculateTargetBufferBytes;
        this.allocator.setTargetBufferSize(iCalculateTargetBufferBytes);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onStopped() {
        reset(true);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onReleased() {
        reset(true);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public Allocator getAllocator() {
        return this.allocator;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public long getBackBufferDurationUs() {
        return this.backBufferDurationUs;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean retainBackBufferFromKeyframe() {
        return this.retainBackBufferFromKeyframe;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean shouldContinueLoading(long playbackPositionUs, long bufferedDurationUs, float playbackSpeed) {
        boolean z = true;
        boolean z2 = this.allocator.getTotalBytesAllocated() >= this.targetBufferBytes;
        long jMin = this.minBufferUs;
        if (playbackSpeed > 1.0f) {
            jMin = Math.min(Util.getMediaDurationForPlayoutDuration(jMin, playbackSpeed), this.maxBufferUs);
        }
        if (bufferedDurationUs < Math.max(jMin, 500000L)) {
            if (!this.prioritizeTimeOverSizeThresholds && z2) {
                z = false;
            }
            this.isLoading = z;
            if (!z && bufferedDurationUs < 500000) {
                Log.w("DefaultLoadControl", "Target buffer size reached with less than 500ms of buffered media data.");
            }
        } else if (bufferedDurationUs >= this.maxBufferUs || z2) {
            this.isLoading = false;
        }
        return this.isLoading;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean shouldStartPlayback(long bufferedDurationUs, float playbackSpeed, boolean rebuffering, long targetLiveOffsetUs) {
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(bufferedDurationUs, playbackSpeed);
        long jMin = rebuffering ? this.bufferForPlaybackAfterRebufferUs : this.bufferForPlaybackUs;
        if (targetLiveOffsetUs != C.TIME_UNSET) {
            jMin = Math.min(targetLiveOffsetUs / 2, jMin);
        }
        return jMin <= 0 || playoutDurationForMediaDuration >= jMin || (!this.prioritizeTimeOverSizeThresholds && this.allocator.getTotalBytesAllocated() >= this.targetBufferBytes);
    }

    protected int calculateTargetBufferBytes(Renderer[] renderers, ExoTrackSelection[] trackSelectionArray) {
        int defaultBufferSize = 0;
        for (int i = 0; i < renderers.length; i++) {
            if (trackSelectionArray[i] != null) {
                defaultBufferSize += getDefaultBufferSize(renderers[i].getTrackType());
            }
        }
        return Math.max(13107200, defaultBufferSize);
    }

    private void reset(boolean resetAllocator) {
        int i = this.targetBufferBytesOverwrite;
        if (i == -1) {
            i = 13107200;
        }
        this.targetBufferBytes = i;
        this.isLoading = false;
        if (resetAllocator) {
            this.allocator.reset();
        }
    }

    private static int getDefaultBufferSize(int trackType) {
        if (trackType == 0) {
            return DEFAULT_MUXED_BUFFER_SIZE;
        }
        if (trackType == 1) {
            return 13107200;
        }
        if (trackType == 2) {
            return DEFAULT_VIDEO_BUFFER_SIZE;
        }
        if (trackType == 3 || trackType == 5 || trackType == 6) {
            return 131072;
        }
        if (trackType == 7) {
            return 0;
        }
        throw new IllegalArgumentException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void assertGreaterOrEqual(int value1, int value2, String name1, String name2) {
        Assertions.checkArgument(value1 >= value2, new StringBuilder(String.valueOf(name1).length() + 21 + String.valueOf(name2).length()).append(name1).append(" cannot be less than ").append(name2).toString());
    }
}
