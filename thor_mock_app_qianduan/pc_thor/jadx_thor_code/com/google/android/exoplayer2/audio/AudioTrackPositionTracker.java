package com.google.android.exoplayer2.audio;

import android.media.AudioTrack;
import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
final class AudioTrackPositionTracker {
    private static final long FORCE_RESET_WORKAROUND_TIMEOUT_MS = 200;
    private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000;
    private static final long MAX_LATENCY_US = 5000000;
    private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
    private static final int MIN_LATENCY_SAMPLE_INTERVAL_US = 500000;
    private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
    private static final long MODE_SWITCH_SMOOTHING_DURATION_US = 1000000;
    private static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PLAYING = 3;
    private static final int PLAYSTATE_STOPPED = 1;
    private AudioTimestampPoller audioTimestampPoller;
    private AudioTrack audioTrack;
    private float audioTrackPlaybackSpeed;
    private int bufferSize;
    private long bufferSizeUs;
    private long endPlaybackHeadPosition;
    private long forceResetWorkaroundTimeMs;
    private Method getLatencyMethod;
    private boolean hasData;
    private boolean isOutputPcm;
    private long lastLatencySampleTimeUs;
    private long lastPlayheadSampleTimeUs;
    private long lastPositionUs;
    private long lastRawPlaybackHeadPosition;
    private boolean lastSampleUsedGetTimestampMode;
    private long lastSystemTimeUs;
    private long latencyUs;
    private final Listener listener;
    private boolean needsPassthroughWorkarounds;
    private int nextPlayheadOffsetIndex;
    private boolean notifiedPositionIncreasing;
    private int outputPcmFrameSize;
    private int outputSampleRate;
    private long passthroughWorkaroundPauseOffset;
    private int playheadOffsetCount;
    private final long[] playheadOffsets;
    private long previousModePositionUs;
    private long previousModeSystemTimeUs;
    private long rawPlaybackHeadWrapCount;
    private long smoothedPlayheadOffsetUs;
    private long stopPlaybackHeadPosition;
    private long stopTimestampUs;

    public interface Listener {
        void onInvalidLatency(long latencyUs);

        void onPositionAdvancing(long playoutStartSystemTimeMs);

        void onPositionFramesMismatch(long audioTimestampPositionFrames, long audioTimestampSystemTimeUs, long systemTimeUs, long playbackPositionUs);

        void onSystemTimeUsMismatch(long audioTimestampPositionFrames, long audioTimestampSystemTimeUs, long systemTimeUs, long playbackPositionUs);

        void onUnderrun(int bufferSize, long bufferSizeMs);
    }

    public AudioTrackPositionTracker(Listener listener) {
        this.listener = (Listener) Assertions.checkNotNull(listener);
        if (Util.SDK_INT >= 18) {
            try {
                this.getLatencyMethod = AudioTrack.class.getMethod("getLatency", null);
            } catch (NoSuchMethodException unused) {
            }
        }
        this.playheadOffsets = new long[10];
    }

    public void setAudioTrack(AudioTrack audioTrack, boolean isPassthrough, int outputEncoding, int outputPcmFrameSize, int bufferSize) {
        this.audioTrack = audioTrack;
        this.outputPcmFrameSize = outputPcmFrameSize;
        this.bufferSize = bufferSize;
        this.audioTimestampPoller = new AudioTimestampPoller(audioTrack);
        this.outputSampleRate = audioTrack.getSampleRate();
        this.needsPassthroughWorkarounds = isPassthrough && needsPassthroughWorkarounds(outputEncoding);
        boolean zIsEncodingLinearPcm = Util.isEncodingLinearPcm(outputEncoding);
        this.isOutputPcm = zIsEncodingLinearPcm;
        this.bufferSizeUs = zIsEncodingLinearPcm ? framesToDurationUs(bufferSize / outputPcmFrameSize) : -9223372036854775807L;
        this.lastRawPlaybackHeadPosition = 0L;
        this.rawPlaybackHeadWrapCount = 0L;
        this.passthroughWorkaroundPauseOffset = 0L;
        this.hasData = false;
        this.stopTimestampUs = C.TIME_UNSET;
        this.forceResetWorkaroundTimeMs = C.TIME_UNSET;
        this.lastLatencySampleTimeUs = 0L;
        this.latencyUs = 0L;
        this.audioTrackPlaybackSpeed = 1.0f;
    }

    public void setAudioTrackPlaybackSpeed(float audioTrackPlaybackSpeed) {
        this.audioTrackPlaybackSpeed = audioTrackPlaybackSpeed;
        AudioTimestampPoller audioTimestampPoller = this.audioTimestampPoller;
        if (audioTimestampPoller != null) {
            audioTimestampPoller.reset();
        }
    }

    public long getCurrentPositionUs(boolean sourceEnded) {
        long jMax;
        if (((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3) {
            maybeSampleSyncParams();
        }
        long jNanoTime = System.nanoTime() / 1000;
        AudioTimestampPoller audioTimestampPoller = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        boolean zHasAdvancingTimestamp = audioTimestampPoller.hasAdvancingTimestamp();
        if (zHasAdvancingTimestamp) {
            jMax = framesToDurationUs(audioTimestampPoller.getTimestampPositionFrames()) + Util.getMediaDurationForPlayoutDuration(jNanoTime - audioTimestampPoller.getTimestampSystemTimeUs(), this.audioTrackPlaybackSpeed);
        } else {
            if (this.playheadOffsetCount == 0) {
                jMax = getPlaybackHeadPositionUs();
            } else {
                jMax = this.smoothedPlayheadOffsetUs + jNanoTime;
            }
            if (!sourceEnded) {
                jMax = Math.max(0L, jMax - this.latencyUs);
            }
        }
        if (this.lastSampleUsedGetTimestampMode != zHasAdvancingTimestamp) {
            this.previousModeSystemTimeUs = this.lastSystemTimeUs;
            this.previousModePositionUs = this.lastPositionUs;
        }
        long j = jNanoTime - this.previousModeSystemTimeUs;
        if (j < 1000000) {
            long mediaDurationForPlayoutDuration = this.previousModePositionUs + Util.getMediaDurationForPlayoutDuration(j, this.audioTrackPlaybackSpeed);
            long j2 = (j * 1000) / 1000000;
            jMax = ((jMax * j2) + ((1000 - j2) * mediaDurationForPlayoutDuration)) / 1000;
        }
        if (!this.notifiedPositionIncreasing) {
            long j3 = this.lastPositionUs;
            if (jMax > j3) {
                this.notifiedPositionIncreasing = true;
                this.listener.onPositionAdvancing(System.currentTimeMillis() - C.usToMs(Util.getPlayoutDurationForMediaDuration(C.usToMs(jMax - j3), this.audioTrackPlaybackSpeed)));
            }
        }
        this.lastSystemTimeUs = jNanoTime;
        this.lastPositionUs = jMax;
        this.lastSampleUsedGetTimestampMode = zHasAdvancingTimestamp;
        return jMax;
    }

    public void start() {
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
    }

    public boolean isPlaying() {
        return ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3;
    }

    public boolean mayHandleBuffer(long writtenFrames) {
        int playState = ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState();
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2) {
                this.hasData = false;
                return false;
            }
            if (playState == 1 && getPlaybackHeadPosition() == 0) {
                return false;
            }
        }
        boolean z = this.hasData;
        boolean zHasPendingData = hasPendingData(writtenFrames);
        this.hasData = zHasPendingData;
        if (z && !zHasPendingData && playState != 1) {
            this.listener.onUnderrun(this.bufferSize, C.usToMs(this.bufferSizeUs));
        }
        return true;
    }

    public int getAvailableBufferSize(long writtenBytes) {
        return this.bufferSize - ((int) (writtenBytes - (getPlaybackHeadPosition() * this.outputPcmFrameSize)));
    }

    public long getPendingBufferDurationMs(long writtenFrames) {
        return C.usToMs(framesToDurationUs(writtenFrames - getPlaybackHeadPosition()));
    }

    public boolean isStalled(long writtenFrames) {
        return this.forceResetWorkaroundTimeMs != C.TIME_UNSET && writtenFrames > 0 && SystemClock.elapsedRealtime() - this.forceResetWorkaroundTimeMs >= FORCE_RESET_WORKAROUND_TIMEOUT_MS;
    }

    public void handleEndOfStream(long writtenFrames) {
        this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
        this.stopTimestampUs = SystemClock.elapsedRealtime() * 1000;
        this.endPlaybackHeadPosition = writtenFrames;
    }

    public boolean hasPendingData(long writtenFrames) {
        return writtenFrames > getPlaybackHeadPosition() || forceHasPendingData();
    }

    public boolean pause() {
        resetSyncParams();
        if (this.stopTimestampUs != C.TIME_UNSET) {
            return false;
        }
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
        return true;
    }

    public void reset() {
        resetSyncParams();
        this.audioTrack = null;
        this.audioTimestampPoller = null;
    }

    private void maybeSampleSyncParams() {
        long playbackHeadPositionUs = getPlaybackHeadPositionUs();
        if (playbackHeadPositionUs == 0) {
            return;
        }
        long jNanoTime = System.nanoTime() / 1000;
        if (jNanoTime - this.lastPlayheadSampleTimeUs >= 30000) {
            long[] jArr = this.playheadOffsets;
            int i = this.nextPlayheadOffsetIndex;
            jArr[i] = playbackHeadPositionUs - jNanoTime;
            this.nextPlayheadOffsetIndex = (i + 1) % 10;
            int i2 = this.playheadOffsetCount;
            if (i2 < 10) {
                this.playheadOffsetCount = i2 + 1;
            }
            this.lastPlayheadSampleTimeUs = jNanoTime;
            this.smoothedPlayheadOffsetUs = 0L;
            int i3 = 0;
            while (true) {
                int i4 = this.playheadOffsetCount;
                if (i3 >= i4) {
                    break;
                }
                this.smoothedPlayheadOffsetUs += this.playheadOffsets[i3] / i4;
                i3++;
            }
        }
        if (this.needsPassthroughWorkarounds) {
            return;
        }
        maybePollAndCheckTimestamp(jNanoTime, playbackHeadPositionUs);
        maybeUpdateLatency(jNanoTime);
    }

    private void maybePollAndCheckTimestamp(long systemTimeUs, long playbackPositionUs) {
        AudioTimestampPoller audioTimestampPoller = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        if (audioTimestampPoller.maybePollTimestamp(systemTimeUs)) {
            long timestampSystemTimeUs = audioTimestampPoller.getTimestampSystemTimeUs();
            long timestampPositionFrames = audioTimestampPoller.getTimestampPositionFrames();
            if (Math.abs(timestampSystemTimeUs - systemTimeUs) > 5000000) {
                this.listener.onSystemTimeUsMismatch(timestampPositionFrames, timestampSystemTimeUs, systemTimeUs, playbackPositionUs);
                audioTimestampPoller.rejectTimestamp();
            } else if (Math.abs(framesToDurationUs(timestampPositionFrames) - playbackPositionUs) > 5000000) {
                this.listener.onPositionFramesMismatch(timestampPositionFrames, timestampSystemTimeUs, systemTimeUs, playbackPositionUs);
                audioTimestampPoller.rejectTimestamp();
            } else {
                audioTimestampPoller.acceptTimestamp();
            }
        }
    }

    private void maybeUpdateLatency(long systemTimeUs) {
        Method method;
        if (!this.isOutputPcm || (method = this.getLatencyMethod) == null || systemTimeUs - this.lastLatencySampleTimeUs < 500000) {
            return;
        }
        try {
            long jIntValue = (((Integer) Util.castNonNull((Integer) method.invoke(Assertions.checkNotNull(this.audioTrack), new Object[0]))).intValue() * 1000) - this.bufferSizeUs;
            this.latencyUs = jIntValue;
            long jMax = Math.max(jIntValue, 0L);
            this.latencyUs = jMax;
            if (jMax > 5000000) {
                this.listener.onInvalidLatency(jMax);
                this.latencyUs = 0L;
            }
        } catch (Exception unused) {
            this.getLatencyMethod = null;
        }
        this.lastLatencySampleTimeUs = systemTimeUs;
    }

    private long framesToDurationUs(long frameCount) {
        return (frameCount * 1000000) / this.outputSampleRate;
    }

    private void resetSyncParams() {
        this.smoothedPlayheadOffsetUs = 0L;
        this.playheadOffsetCount = 0;
        this.nextPlayheadOffsetIndex = 0;
        this.lastPlayheadSampleTimeUs = 0L;
        this.lastSystemTimeUs = 0L;
        this.previousModeSystemTimeUs = 0L;
        this.notifiedPositionIncreasing = false;
    }

    private boolean forceHasPendingData() {
        return this.needsPassthroughWorkarounds && ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 2 && getPlaybackHeadPosition() == 0;
    }

    private static boolean needsPassthroughWorkarounds(int outputEncoding) {
        return Util.SDK_INT < 23 && (outputEncoding == 5 || outputEncoding == 6);
    }

    private long getPlaybackHeadPositionUs() {
        return framesToDurationUs(getPlaybackHeadPosition());
    }

    private long getPlaybackHeadPosition() {
        AudioTrack audioTrack = (AudioTrack) Assertions.checkNotNull(this.audioTrack);
        if (this.stopTimestampUs != C.TIME_UNSET) {
            return Math.min(this.endPlaybackHeadPosition, this.stopPlaybackHeadPosition + ((((SystemClock.elapsedRealtime() * 1000) - this.stopTimestampUs) * this.outputSampleRate) / 1000000));
        }
        int playState = audioTrack.getPlayState();
        if (playState == 1) {
            return 0L;
        }
        long playbackHeadPosition = audioTrack.getPlaybackHeadPosition() & 4294967295L;
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2 && playbackHeadPosition == 0) {
                this.passthroughWorkaroundPauseOffset = this.lastRawPlaybackHeadPosition;
            }
            playbackHeadPosition += this.passthroughWorkaroundPauseOffset;
        }
        if (Util.SDK_INT <= 29) {
            if (playbackHeadPosition == 0 && this.lastRawPlaybackHeadPosition > 0 && playState == 3) {
                if (this.forceResetWorkaroundTimeMs == C.TIME_UNSET) {
                    this.forceResetWorkaroundTimeMs = SystemClock.elapsedRealtime();
                }
                return this.lastRawPlaybackHeadPosition;
            }
            this.forceResetWorkaroundTimeMs = C.TIME_UNSET;
        }
        if (this.lastRawPlaybackHeadPosition > playbackHeadPosition) {
            this.rawPlaybackHeadWrapCount++;
        }
        this.lastRawPlaybackHeadPosition = playbackHeadPosition;
        return playbackHeadPosition + (this.rawPlaybackHeadWrapCount << 32);
    }
}
