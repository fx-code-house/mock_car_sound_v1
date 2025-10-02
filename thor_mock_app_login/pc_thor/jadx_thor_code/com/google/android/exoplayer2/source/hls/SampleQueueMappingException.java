package com.google.android.exoplayer2.source.hls;

import java.io.IOException;

/* loaded from: classes.dex */
public final class SampleQueueMappingException extends IOException {
    public SampleQueueMappingException(String mimeType) {
        super(new StringBuilder(String.valueOf(mimeType).length() + 60).append("Unable to bind a sample queue to TrackGroup with mime type ").append(mimeType).append(".").toString());
    }
}
