package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class SeekParameters {
    public static final SeekParameters CLOSEST_SYNC;
    public static final SeekParameters DEFAULT;
    public static final SeekParameters EXACT;
    public static final SeekParameters NEXT_SYNC;
    public static final SeekParameters PREVIOUS_SYNC;
    public final long toleranceAfterUs;
    public final long toleranceBeforeUs;

    static {
        SeekParameters seekParameters = new SeekParameters(0L, 0L);
        EXACT = seekParameters;
        CLOSEST_SYNC = new SeekParameters(Long.MAX_VALUE, Long.MAX_VALUE);
        PREVIOUS_SYNC = new SeekParameters(Long.MAX_VALUE, 0L);
        NEXT_SYNC = new SeekParameters(0L, Long.MAX_VALUE);
        DEFAULT = seekParameters;
    }

    public SeekParameters(long toleranceBeforeUs, long toleranceAfterUs) {
        Assertions.checkArgument(toleranceBeforeUs >= 0);
        Assertions.checkArgument(toleranceAfterUs >= 0);
        this.toleranceBeforeUs = toleranceBeforeUs;
        this.toleranceAfterUs = toleranceAfterUs;
    }

    public long resolveSeekPositionUs(long positionUs, long firstSyncUs, long secondSyncUs) {
        long j = this.toleranceBeforeUs;
        if (j == 0 && this.toleranceAfterUs == 0) {
            return positionUs;
        }
        long jSubtractWithOverflowDefault = Util.subtractWithOverflowDefault(positionUs, j, Long.MIN_VALUE);
        long jAddWithOverflowDefault = Util.addWithOverflowDefault(positionUs, this.toleranceAfterUs, Long.MAX_VALUE);
        boolean z = jSubtractWithOverflowDefault <= firstSyncUs && firstSyncUs <= jAddWithOverflowDefault;
        boolean z2 = jSubtractWithOverflowDefault <= secondSyncUs && secondSyncUs <= jAddWithOverflowDefault;
        return (z && z2) ? Math.abs(firstSyncUs - positionUs) <= Math.abs(secondSyncUs - positionUs) ? firstSyncUs : secondSyncUs : z ? firstSyncUs : z2 ? secondSyncUs : jSubtractWithOverflowDefault;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SeekParameters seekParameters = (SeekParameters) obj;
        return this.toleranceBeforeUs == seekParameters.toleranceBeforeUs && this.toleranceAfterUs == seekParameters.toleranceAfterUs;
    }

    public int hashCode() {
        return (((int) this.toleranceBeforeUs) * 31) + ((int) this.toleranceAfterUs);
    }
}
