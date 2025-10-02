package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class StopwatchLapBuilder extends IndexableBuilder<StopwatchLapBuilder> {
    StopwatchLapBuilder() {
        super("StopwatchLap");
    }

    public StopwatchLapBuilder setElapsedTime(long j) {
        return put("elapsedTime", j);
    }

    public StopwatchLapBuilder setAccumulatedTime(long j) {
        return put("accumulatedTime", j);
    }
}
