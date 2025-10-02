package com.google.android.exoplayer2.source.rtsp;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
final class RtspOptionsResponse {
    public final int status;
    public final ImmutableList<Integer> supportedMethods;

    public RtspOptionsResponse(int status, List<Integer> supportedMethods) {
        this.status = status;
        this.supportedMethods = ImmutableList.copyOf((Collection) supportedMethods);
    }
}
