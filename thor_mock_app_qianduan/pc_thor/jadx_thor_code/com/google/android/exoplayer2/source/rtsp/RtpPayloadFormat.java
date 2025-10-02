package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class RtpPayloadFormat {
    private static final String RTP_MEDIA_AC3 = "AC3";
    private static final String RTP_MEDIA_H264 = "H264";
    private static final String RTP_MEDIA_MPEG4_GENERIC = "MPEG4-GENERIC";
    public final int clockRate;
    public final ImmutableMap<String, String> fmtpParameters;
    public final Format format;
    public final int rtpPayloadType;

    public static boolean isFormatSupported(MediaDescription mediaDescription) {
        String upperCase = Ascii.toUpperCase(mediaDescription.rtpMapAttribute.mediaEncoding);
        upperCase.hashCode();
        switch (upperCase) {
            case "MPEG4-GENERIC":
            case "AC3":
            case "H264":
                return true;
            default:
                return false;
        }
    }

    public static String getMimeTypeFromRtpMediaType(String mediaType) {
        String upperCase = Ascii.toUpperCase(mediaType);
        upperCase.hashCode();
        switch (upperCase) {
            case "MPEG4-GENERIC":
                return MimeTypes.AUDIO_AAC;
            case "AC3":
                return MimeTypes.AUDIO_AC3;
            case "H264":
                return MimeTypes.VIDEO_H264;
            default:
                throw new IllegalArgumentException(mediaType);
        }
    }

    public RtpPayloadFormat(Format format, int rtpPayloadType, int clockRate, Map<String, String> fmtpParameters) {
        this.rtpPayloadType = rtpPayloadType;
        this.clockRate = clockRate;
        this.format = format;
        this.fmtpParameters = ImmutableMap.copyOf((Map) fmtpParameters);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RtpPayloadFormat rtpPayloadFormat = (RtpPayloadFormat) o;
        return this.rtpPayloadType == rtpPayloadFormat.rtpPayloadType && this.clockRate == rtpPayloadFormat.clockRate && this.format.equals(rtpPayloadFormat.format) && this.fmtpParameters.equals(rtpPayloadFormat.fmtpParameters);
    }

    public int hashCode() {
        return ((((((217 + this.rtpPayloadType) * 31) + this.clockRate) * 31) + this.format.hashCode()) * 31) + this.fmtpParameters.hashCode();
    }
}
