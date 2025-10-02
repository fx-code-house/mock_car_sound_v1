package com.google.android.exoplayer2;

import android.os.SystemClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Longs;

/* loaded from: classes.dex */
public final class DefaultLivePlaybackSpeedControl implements LivePlaybackSpeedControl {
    public static final float DEFAULT_FALLBACK_MAX_PLAYBACK_SPEED = 1.03f;
    public static final float DEFAULT_FALLBACK_MIN_PLAYBACK_SPEED = 0.97f;
    public static final long DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED = 20;
    public static final float DEFAULT_MIN_POSSIBLE_LIVE_OFFSET_SMOOTHING_FACTOR = 0.999f;
    public static final long DEFAULT_MIN_UPDATE_INTERVAL_MS = 1000;
    public static final float DEFAULT_PROPORTIONAL_CONTROL_FACTOR = 0.1f;
    public static final long DEFAULT_TARGET_LIVE_OFFSET_INCREMENT_ON_REBUFFER_MS = 500;
    private float adjustedPlaybackSpeed;
    private long currentTargetLiveOffsetUs;
    private final float fallbackMaxPlaybackSpeed;
    private final float fallbackMinPlaybackSpeed;
    private long idealTargetLiveOffsetUs;
    private long lastPlaybackSpeedUpdateMs;
    private final long maxLiveOffsetErrorUsForUnitSpeed;
    private float maxPlaybackSpeed;
    private long maxTargetLiveOffsetUs;
    private long mediaConfigurationTargetLiveOffsetUs;
    private float minPlaybackSpeed;
    private final float minPossibleLiveOffsetSmoothingFactor;
    private long minTargetLiveOffsetUs;
    private final long minUpdateIntervalMs;
    private final float proportionalControlFactor;
    private long smoothedMinPossibleLiveOffsetDeviationUs;
    private long smoothedMinPossibleLiveOffsetUs;
    private long targetLiveOffsetOverrideUs;
    private final long targetLiveOffsetRebufferDeltaUs;

    private static long smooth(long smoothedValue, long newValue, float smoothingFactor) {
        return (long) ((smoothedValue * smoothingFactor) + ((1.0f - smoothingFactor) * newValue));
    }

    public static final class Builder {
        private float fallbackMinPlaybackSpeed = 0.97f;
        private float fallbackMaxPlaybackSpeed = 1.03f;
        private long minUpdateIntervalMs = 1000;
        private float proportionalControlFactorUs = 1.0E-7f;
        private long maxLiveOffsetErrorUsForUnitSpeed = C.msToUs(20);
        private long targetLiveOffsetIncrementOnRebufferUs = C.msToUs(500);
        private float minPossibleLiveOffsetSmoothingFactor = 0.999f;

        public Builder setFallbackMinPlaybackSpeed(float fallbackMinPlaybackSpeed) {
            Assertions.checkArgument(0.0f < fallbackMinPlaybackSpeed && fallbackMinPlaybackSpeed <= 1.0f);
            this.fallbackMinPlaybackSpeed = fallbackMinPlaybackSpeed;
            return this;
        }

        public Builder setFallbackMaxPlaybackSpeed(float fallbackMaxPlaybackSpeed) {
            Assertions.checkArgument(fallbackMaxPlaybackSpeed >= 1.0f);
            this.fallbackMaxPlaybackSpeed = fallbackMaxPlaybackSpeed;
            return this;
        }

        public Builder setMinUpdateIntervalMs(long minUpdateIntervalMs) {
            Assertions.checkArgument(minUpdateIntervalMs > 0);
            this.minUpdateIntervalMs = minUpdateIntervalMs;
            return this;
        }

        public Builder setProportionalControlFactor(float proportionalControlFactor) {
            Assertions.checkArgument(proportionalControlFactor > 0.0f);
            this.proportionalControlFactorUs = proportionalControlFactor / 1000000.0f;
            return this;
        }

        public Builder setMaxLiveOffsetErrorMsForUnitSpeed(long maxLiveOffsetErrorMsForUnitSpeed) {
            Assertions.checkArgument(maxLiveOffsetErrorMsForUnitSpeed > 0);
            this.maxLiveOffsetErrorUsForUnitSpeed = C.msToUs(maxLiveOffsetErrorMsForUnitSpeed);
            return this;
        }

        public Builder setTargetLiveOffsetIncrementOnRebufferMs(long targetLiveOffsetIncrementOnRebufferMs) {
            Assertions.checkArgument(targetLiveOffsetIncrementOnRebufferMs >= 0);
            this.targetLiveOffsetIncrementOnRebufferUs = C.msToUs(targetLiveOffsetIncrementOnRebufferMs);
            return this;
        }

        public Builder setMinPossibleLiveOffsetSmoothingFactor(float minPossibleLiveOffsetSmoothingFactor) {
            Assertions.checkArgument(minPossibleLiveOffsetSmoothingFactor >= 0.0f && minPossibleLiveOffsetSmoothingFactor < 1.0f);
            this.minPossibleLiveOffsetSmoothingFactor = minPossibleLiveOffsetSmoothingFactor;
            return this;
        }

        public DefaultLivePlaybackSpeedControl build() {
            return new DefaultLivePlaybackSpeedControl(this.fallbackMinPlaybackSpeed, this.fallbackMaxPlaybackSpeed, this.minUpdateIntervalMs, this.proportionalControlFactorUs, this.maxLiveOffsetErrorUsForUnitSpeed, this.targetLiveOffsetIncrementOnRebufferUs, this.minPossibleLiveOffsetSmoothingFactor);
        }
    }

    private DefaultLivePlaybackSpeedControl(float fallbackMinPlaybackSpeed, float fallbackMaxPlaybackSpeed, long minUpdateIntervalMs, float proportionalControlFactor, long maxLiveOffsetErrorUsForUnitSpeed, long targetLiveOffsetRebufferDeltaUs, float minPossibleLiveOffsetSmoothingFactor) {
        this.fallbackMinPlaybackSpeed = fallbackMinPlaybackSpeed;
        this.fallbackMaxPlaybackSpeed = fallbackMaxPlaybackSpeed;
        this.minUpdateIntervalMs = minUpdateIntervalMs;
        this.proportionalControlFactor = proportionalControlFactor;
        this.maxLiveOffsetErrorUsForUnitSpeed = maxLiveOffsetErrorUsForUnitSpeed;
        this.targetLiveOffsetRebufferDeltaUs = targetLiveOffsetRebufferDeltaUs;
        this.minPossibleLiveOffsetSmoothingFactor = minPossibleLiveOffsetSmoothingFactor;
        this.mediaConfigurationTargetLiveOffsetUs = C.TIME_UNSET;
        this.targetLiveOffsetOverrideUs = C.TIME_UNSET;
        this.minTargetLiveOffsetUs = C.TIME_UNSET;
        this.maxTargetLiveOffsetUs = C.TIME_UNSET;
        this.minPlaybackSpeed = fallbackMinPlaybackSpeed;
        this.maxPlaybackSpeed = fallbackMaxPlaybackSpeed;
        this.adjustedPlaybackSpeed = 1.0f;
        this.lastPlaybackSpeedUpdateMs = C.TIME_UNSET;
        this.idealTargetLiveOffsetUs = C.TIME_UNSET;
        this.currentTargetLiveOffsetUs = C.TIME_UNSET;
        this.smoothedMinPossibleLiveOffsetUs = C.TIME_UNSET;
        this.smoothedMinPossibleLiveOffsetDeviationUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public void setLiveConfiguration(MediaItem.LiveConfiguration liveConfiguration) {
        float f;
        float f2;
        this.mediaConfigurationTargetLiveOffsetUs = C.msToUs(liveConfiguration.targetOffsetMs);
        this.minTargetLiveOffsetUs = C.msToUs(liveConfiguration.minOffsetMs);
        this.maxTargetLiveOffsetUs = C.msToUs(liveConfiguration.maxOffsetMs);
        if (liveConfiguration.minPlaybackSpeed != -3.4028235E38f) {
            f = liveConfiguration.minPlaybackSpeed;
        } else {
            f = this.fallbackMinPlaybackSpeed;
        }
        this.minPlaybackSpeed = f;
        if (liveConfiguration.maxPlaybackSpeed != -3.4028235E38f) {
            f2 = liveConfiguration.maxPlaybackSpeed;
        } else {
            f2 = this.fallbackMaxPlaybackSpeed;
        }
        this.maxPlaybackSpeed = f2;
        maybeResetTargetLiveOffsetUs();
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public void setTargetLiveOffsetOverrideUs(long liveOffsetUs) {
        this.targetLiveOffsetOverrideUs = liveOffsetUs;
        maybeResetTargetLiveOffsetUs();
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public void notifyRebuffer() {
        long j = this.currentTargetLiveOffsetUs;
        if (j == C.TIME_UNSET) {
            return;
        }
        long j2 = j + this.targetLiveOffsetRebufferDeltaUs;
        this.currentTargetLiveOffsetUs = j2;
        long j3 = this.maxTargetLiveOffsetUs;
        if (j3 != C.TIME_UNSET && j2 > j3) {
            this.currentTargetLiveOffsetUs = j3;
        }
        this.lastPlaybackSpeedUpdateMs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public float getAdjustedPlaybackSpeed(long liveOffsetUs, long bufferedDurationUs) {
        if (this.mediaConfigurationTargetLiveOffsetUs == C.TIME_UNSET) {
            return 1.0f;
        }
        updateSmoothedMinPossibleLiveOffsetUs(liveOffsetUs, bufferedDurationUs);
        if (this.lastPlaybackSpeedUpdateMs != C.TIME_UNSET && SystemClock.elapsedRealtime() - this.lastPlaybackSpeedUpdateMs < this.minUpdateIntervalMs) {
            return this.adjustedPlaybackSpeed;
        }
        this.lastPlaybackSpeedUpdateMs = SystemClock.elapsedRealtime();
        adjustTargetLiveOffsetUs(liveOffsetUs);
        long j = liveOffsetUs - this.currentTargetLiveOffsetUs;
        if (Math.abs(j) < this.maxLiveOffsetErrorUsForUnitSpeed) {
            this.adjustedPlaybackSpeed = 1.0f;
        } else {
            this.adjustedPlaybackSpeed = Util.constrainValue((this.proportionalControlFactor * j) + 1.0f, this.minPlaybackSpeed, this.maxPlaybackSpeed);
        }
        return this.adjustedPlaybackSpeed;
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public long getTargetLiveOffsetUs() {
        return this.currentTargetLiveOffsetUs;
    }

    private void maybeResetTargetLiveOffsetUs() {
        long j = this.mediaConfigurationTargetLiveOffsetUs;
        if (j != C.TIME_UNSET) {
            long j2 = this.targetLiveOffsetOverrideUs;
            if (j2 != C.TIME_UNSET) {
                j = j2;
            }
            long j3 = this.minTargetLiveOffsetUs;
            if (j3 != C.TIME_UNSET && j < j3) {
                j = j3;
            }
            long j4 = this.maxTargetLiveOffsetUs;
            if (j4 != C.TIME_UNSET && j > j4) {
                j = j4;
            }
        } else {
            j = -9223372036854775807L;
        }
        if (this.idealTargetLiveOffsetUs == j) {
            return;
        }
        this.idealTargetLiveOffsetUs = j;
        this.currentTargetLiveOffsetUs = j;
        this.smoothedMinPossibleLiveOffsetUs = C.TIME_UNSET;
        this.smoothedMinPossibleLiveOffsetDeviationUs = C.TIME_UNSET;
        this.lastPlaybackSpeedUpdateMs = C.TIME_UNSET;
    }

    private void updateSmoothedMinPossibleLiveOffsetUs(long liveOffsetUs, long bufferedDurationUs) {
        long j = liveOffsetUs - bufferedDurationUs;
        long j2 = this.smoothedMinPossibleLiveOffsetUs;
        if (j2 == C.TIME_UNSET) {
            this.smoothedMinPossibleLiveOffsetUs = j;
            this.smoothedMinPossibleLiveOffsetDeviationUs = 0L;
        } else {
            long jMax = Math.max(j, smooth(j2, j, this.minPossibleLiveOffsetSmoothingFactor));
            this.smoothedMinPossibleLiveOffsetUs = jMax;
            this.smoothedMinPossibleLiveOffsetDeviationUs = smooth(this.smoothedMinPossibleLiveOffsetDeviationUs, Math.abs(j - jMax), this.minPossibleLiveOffsetSmoothingFactor);
        }
    }

    private void adjustTargetLiveOffsetUs(long liveOffsetUs) {
        long j = this.smoothedMinPossibleLiveOffsetUs + (this.smoothedMinPossibleLiveOffsetDeviationUs * 3);
        if (this.currentTargetLiveOffsetUs > j) {
            float fMsToUs = C.msToUs(this.minUpdateIntervalMs);
            this.currentTargetLiveOffsetUs = Longs.max(j, this.idealTargetLiveOffsetUs, this.currentTargetLiveOffsetUs - (((long) ((this.adjustedPlaybackSpeed - 1.0f) * fMsToUs)) + ((long) ((this.maxPlaybackSpeed - 1.0f) * fMsToUs))));
            return;
        }
        long jConstrainValue = Util.constrainValue(liveOffsetUs - ((long) (Math.max(0.0f, this.adjustedPlaybackSpeed - 1.0f) / this.proportionalControlFactor)), this.currentTargetLiveOffsetUs, j);
        this.currentTargetLiveOffsetUs = jConstrainValue;
        long j2 = this.maxTargetLiveOffsetUs;
        if (j2 == C.TIME_UNSET || jConstrainValue <= j2) {
            return;
        }
        this.currentTargetLiveOffsetUs = j2;
    }
}
