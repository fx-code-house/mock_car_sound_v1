package com.google.android.exoplayer2.source.rtsp;

/* loaded from: classes.dex */
final class RtspResponse {
    public final RtspHeaders headers;
    public final String messageBody;
    public final int status;

    public RtspResponse(int status, RtspHeaders headers, String messageBody) {
        this.status = status;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public RtspResponse(int status, RtspHeaders headers) {
        this(status, headers, "");
    }
}
