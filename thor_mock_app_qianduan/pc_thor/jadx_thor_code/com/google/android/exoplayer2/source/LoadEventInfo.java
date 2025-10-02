package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public final class LoadEventInfo {
    private static final AtomicLong idSource = new AtomicLong();
    public final long bytesLoaded;
    public final DataSpec dataSpec;
    public final long elapsedRealtimeMs;
    public final long loadDurationMs;
    public final long loadTaskId;
    public final Map<String, List<String>> responseHeaders;
    public final Uri uri;

    public static long getNewId() {
        return idSource.getAndIncrement();
    }

    public LoadEventInfo(long loadTaskId, DataSpec dataSpec, long elapsedRealtimeMs) {
        this(loadTaskId, dataSpec, dataSpec.uri, Collections.emptyMap(), elapsedRealtimeMs, 0L, 0L);
    }

    public LoadEventInfo(long loadTaskId, DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        this.loadTaskId = loadTaskId;
        this.dataSpec = dataSpec;
        this.uri = uri;
        this.responseHeaders = responseHeaders;
        this.elapsedRealtimeMs = elapsedRealtimeMs;
        this.loadDurationMs = loadDurationMs;
        this.bytesLoaded = bytesLoaded;
    }
}
