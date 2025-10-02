package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;

/* loaded from: classes.dex */
final class RtspSetupResponse {
    public final RtspMessageUtil.RtspSessionHeader sessionHeader;
    public final int status;
    public final String transport;

    public RtspSetupResponse(int status, RtspMessageUtil.RtspSessionHeader sessionHeader, String transport) {
        this.status = status;
        this.sessionHeader = sessionHeader;
        this.transport = transport;
    }
}
