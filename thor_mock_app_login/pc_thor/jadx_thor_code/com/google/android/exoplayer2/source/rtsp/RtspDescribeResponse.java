package com.google.android.exoplayer2.source.rtsp;

/* loaded from: classes.dex */
final class RtspDescribeResponse {
    public final SessionDescription sessionDescription;
    public final int status;

    public RtspDescribeResponse(int status, SessionDescription sessionDescription) {
        this.status = status;
        this.sessionDescription = sessionDescription;
    }
}
