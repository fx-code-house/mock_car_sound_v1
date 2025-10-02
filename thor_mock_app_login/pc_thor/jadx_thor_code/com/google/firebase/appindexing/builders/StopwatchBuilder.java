package com.google.firebase.appindexing.builders;

import com.google.firebase.appindexing.internal.zzad;
import java.util.Calendar;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class StopwatchBuilder extends IndexableBuilder<StopwatchBuilder> {
    public static final String PAUSED = "Paused";
    public static final String STARTED = "Started";
    public static final String UNKNOWN = "Unknown";

    StopwatchBuilder() {
        super("Stopwatch");
    }

    public final StopwatchBuilder setElapsedTime(long j) {
        return put("elapsedTime", j);
    }

    public final StopwatchBuilder setStartTime(Calendar calendar) {
        return put("startTime", zzad.zza(calendar));
    }

    public final StopwatchBuilder setStopwatchStatus(String str) {
        if (!"Started".equals(str) && !"Paused".equals(str) && !"Unknown".equals(str)) {
            String strValueOf = String.valueOf(str);
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid stopwatch status ".concat(strValueOf) : new String("Invalid stopwatch status "));
        }
        return put("stopwatchStatus", str);
    }

    public final StopwatchBuilder setLaps(StopwatchLapBuilder... stopwatchLapBuilderArr) {
        return put("laps", stopwatchLapBuilderArr);
    }
}
