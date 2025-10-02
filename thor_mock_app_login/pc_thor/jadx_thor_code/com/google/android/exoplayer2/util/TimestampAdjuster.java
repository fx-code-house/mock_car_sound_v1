package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C;

/* loaded from: classes.dex */
public final class TimestampAdjuster {
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    public static final long MODE_NO_OFFSET = Long.MAX_VALUE;
    public static final long MODE_SHARED = 9223372036854775806L;
    private long firstSampleTimestampUs;
    private long lastUnadjustedTimestampUs;
    private final ThreadLocal<Long> nextSampleTimestampUs = new ThreadLocal<>();
    private long timestampOffsetUs;

    public TimestampAdjuster(long firstSampleTimestampUs) {
        reset(firstSampleTimestampUs);
    }

    public synchronized void sharedInitializeOrWait(boolean canInitialize, long nextSampleTimestampUs) throws InterruptedException {
        Assertions.checkState(this.firstSampleTimestampUs == MODE_SHARED);
        if (this.timestampOffsetUs != C.TIME_UNSET) {
            return;
        }
        if (canInitialize) {
            this.nextSampleTimestampUs.set(Long.valueOf(nextSampleTimestampUs));
        } else {
            while (this.timestampOffsetUs == C.TIME_UNSET) {
                wait();
            }
        }
    }

    public synchronized long getFirstSampleTimestampUs() {
        long j;
        j = this.firstSampleTimestampUs;
        if (j == Long.MAX_VALUE || j == MODE_SHARED) {
            j = C.TIME_UNSET;
        }
        return j;
    }

    public synchronized long getLastAdjustedTimestampUs() {
        long firstSampleTimestampUs;
        long j = this.lastUnadjustedTimestampUs;
        if (j != C.TIME_UNSET) {
            firstSampleTimestampUs = j + this.timestampOffsetUs;
        } else {
            firstSampleTimestampUs = getFirstSampleTimestampUs();
        }
        return firstSampleTimestampUs;
    }

    public synchronized long getTimestampOffsetUs() {
        return this.timestampOffsetUs;
    }

    public synchronized void reset(long firstSampleTimestampUs) {
        this.firstSampleTimestampUs = firstSampleTimestampUs;
        this.timestampOffsetUs = firstSampleTimestampUs == Long.MAX_VALUE ? 0L : -9223372036854775807L;
        this.lastUnadjustedTimestampUs = C.TIME_UNSET;
    }

    public synchronized long adjustTsTimestamp(long pts90Khz) {
        if (pts90Khz == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long j = this.lastUnadjustedTimestampUs;
        if (j != C.TIME_UNSET) {
            long jUsToNonWrappedPts = usToNonWrappedPts(j);
            long j2 = (4294967296L + jUsToNonWrappedPts) / MAX_PTS_PLUS_ONE;
            long j3 = ((j2 - 1) * MAX_PTS_PLUS_ONE) + pts90Khz;
            pts90Khz += j2 * MAX_PTS_PLUS_ONE;
            if (Math.abs(j3 - jUsToNonWrappedPts) < Math.abs(pts90Khz - jUsToNonWrappedPts)) {
                pts90Khz = j3;
            }
        }
        return adjustSampleTimestamp(ptsToUs(pts90Khz));
    }

    public synchronized long adjustSampleTimestamp(long timeUs) {
        if (timeUs == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.timestampOffsetUs == C.TIME_UNSET) {
            long jLongValue = this.firstSampleTimestampUs;
            if (jLongValue == MODE_SHARED) {
                jLongValue = ((Long) Assertions.checkNotNull(this.nextSampleTimestampUs.get())).longValue();
            }
            this.timestampOffsetUs = jLongValue - timeUs;
            notifyAll();
        }
        this.lastUnadjustedTimestampUs = timeUs;
        return timeUs + this.timestampOffsetUs;
    }

    public static long ptsToUs(long pts) {
        return (pts * 1000000) / 90000;
    }

    public static long usToWrappedPts(long us) {
        return usToNonWrappedPts(us) % MAX_PTS_PLUS_ONE;
    }

    public static long usToNonWrappedPts(long us) {
        return (us * 90000) / 1000000;
    }
}
