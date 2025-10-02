package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class DefaultRtpPayloadReaderFactory implements RtpPayloadReader.Factory {
    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader.Factory
    public RtpPayloadReader createPayloadReader(RtpPayloadFormat payloadFormat) {
        String str = (String) Assertions.checkNotNull(payloadFormat.format.sampleMimeType);
        str.hashCode();
        switch (str) {
            case "audio/mp4a-latm":
                return new RtpAacReader(payloadFormat);
            case "audio/ac3":
                return new RtpAc3Reader(payloadFormat);
            case "video/avc":
                return new RtpH264Reader(payloadFormat);
            default:
                return null;
        }
    }
}
