package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
final class MediaDescription {
    public static final String MEDIA_TYPE_AUDIO = "audio";
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final String RTP_AVP_PROFILE = "RTP/AVP";
    public final ImmutableMap<String, String> attributes;
    public final int bitrate;
    public final String connection;
    public final String key;
    public final String mediaTitle;
    public final String mediaType;
    public final int payloadType;
    public final int port;
    public final RtpMapAttribute rtpMapAttribute;
    public final String transportProtocol;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaType {
    }

    public static final class RtpMapAttribute {
        public final int clockRate;
        public final int encodingParameters;
        public final String mediaEncoding;
        public final int payloadType;

        public static RtpMapAttribute parse(String rtpmapString) throws ParserException {
            String[] strArrSplit = Util.split(rtpmapString, StringUtils.SPACE);
            Assertions.checkArgument(strArrSplit.length == 2);
            int i = RtspMessageUtil.parseInt(strArrSplit[0]);
            String[] strArrSplit2 = Util.split(strArrSplit[1], "/");
            Assertions.checkArgument(strArrSplit2.length >= 2);
            return new RtpMapAttribute(i, strArrSplit2[0], RtspMessageUtil.parseInt(strArrSplit2[1]), strArrSplit2.length == 3 ? RtspMessageUtil.parseInt(strArrSplit2[2]) : -1);
        }

        private RtpMapAttribute(int payloadType, String mediaEncoding, int clockRate, int encodingParameters) {
            this.payloadType = payloadType;
            this.mediaEncoding = mediaEncoding;
            this.clockRate = clockRate;
            this.encodingParameters = encodingParameters;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RtpMapAttribute rtpMapAttribute = (RtpMapAttribute) o;
            return this.payloadType == rtpMapAttribute.payloadType && this.mediaEncoding.equals(rtpMapAttribute.mediaEncoding) && this.clockRate == rtpMapAttribute.clockRate && this.encodingParameters == rtpMapAttribute.encodingParameters;
        }

        public int hashCode() {
            return ((((((217 + this.payloadType) * 31) + this.mediaEncoding.hashCode()) * 31) + this.clockRate) * 31) + this.encodingParameters;
        }
    }

    public static final class Builder {
        private final HashMap<String, String> attributes = new HashMap<>();
        private int bitrate = -1;
        private String connection;
        private String key;
        private String mediaTitle;
        private final String mediaType;
        private final int payloadType;
        private final int port;
        private final String transportProtocol;

        public Builder(String mediaType, int port, String transportProtocol, int payloadType) {
            this.mediaType = mediaType;
            this.port = port;
            this.transportProtocol = transportProtocol;
            this.payloadType = payloadType;
        }

        public Builder setMediaTitle(String mediaTitle) {
            this.mediaTitle = mediaTitle;
            return this;
        }

        public Builder setConnection(String connection) {
            this.connection = connection;
            return this;
        }

        public Builder setBitrate(int bitrate) {
            this.bitrate = bitrate;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder addAttribute(String attributeName, String attributeValue) {
            this.attributes.put(attributeName, attributeValue);
            return this;
        }

        public MediaDescription build() {
            try {
                Assertions.checkState(this.attributes.containsKey(SessionDescription.ATTR_RTPMAP));
                return new MediaDescription(this, ImmutableMap.copyOf((Map) this.attributes), RtpMapAttribute.parse((String) Util.castNonNull(this.attributes.get(SessionDescription.ATTR_RTPMAP))));
            } catch (ParserException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private MediaDescription(Builder builder, ImmutableMap<String, String> attributes, RtpMapAttribute rtpMapAttribute) {
        this.mediaType = builder.mediaType;
        this.port = builder.port;
        this.transportProtocol = builder.transportProtocol;
        this.payloadType = builder.payloadType;
        this.mediaTitle = builder.mediaTitle;
        this.connection = builder.connection;
        this.bitrate = builder.bitrate;
        this.key = builder.key;
        this.attributes = attributes;
        this.rtpMapAttribute = rtpMapAttribute;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaDescription mediaDescription = (MediaDescription) o;
        return this.mediaType.equals(mediaDescription.mediaType) && this.port == mediaDescription.port && this.transportProtocol.equals(mediaDescription.transportProtocol) && this.payloadType == mediaDescription.payloadType && this.bitrate == mediaDescription.bitrate && this.attributes.equals(mediaDescription.attributes) && this.rtpMapAttribute.equals(mediaDescription.rtpMapAttribute) && Util.areEqual(this.mediaTitle, mediaDescription.mediaTitle) && Util.areEqual(this.connection, mediaDescription.connection) && Util.areEqual(this.key, mediaDescription.key);
    }

    public int hashCode() {
        int iHashCode = (((((((((((((217 + this.mediaType.hashCode()) * 31) + this.port) * 31) + this.transportProtocol.hashCode()) * 31) + this.payloadType) * 31) + this.bitrate) * 31) + this.attributes.hashCode()) * 31) + this.rtpMapAttribute.hashCode()) * 31;
        String str = this.mediaTitle;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.connection;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.key;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public ImmutableMap<String, String> getFmtpParametersAsMap() {
        String str = this.attributes.get(SessionDescription.ATTR_FMTP);
        if (str == null) {
            return ImmutableMap.of();
        }
        String[] strArrSplitAtFirst = Util.splitAtFirst(str, StringUtils.SPACE);
        Assertions.checkArgument(strArrSplitAtFirst.length == 2, str);
        String[] strArrSplit = strArrSplitAtFirst[1].split(";\\s?", 0);
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        for (String str2 : strArrSplit) {
            String[] strArrSplitAtFirst2 = Util.splitAtFirst(str2, "=");
            builder.put(strArrSplitAtFirst2[0], strArrSplitAtFirst2[1]);
        }
        return builder.build();
    }
}
