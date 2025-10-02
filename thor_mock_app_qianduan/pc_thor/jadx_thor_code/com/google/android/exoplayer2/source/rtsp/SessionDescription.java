package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
final class SessionDescription {
    public static final String ATTR_CONTROL = "control";
    public static final String ATTR_FMTP = "fmtp";
    public static final String ATTR_LENGTH = "length";
    public static final String ATTR_RANGE = "range";
    public static final String ATTR_RTPMAP = "rtpmap";
    public static final String ATTR_TOOL = "tool";
    public static final String ATTR_TYPE = "type";
    public static final String SUPPORTED_SDP_VERSION = "0";
    public final ImmutableMap<String, String> attributes;
    public final int bitrate;
    public final String connection;
    public final String emailAddress;
    public final String key;
    public final ImmutableList<MediaDescription> mediaDescriptionList;
    public final String origin;
    public final String phoneNumber;
    public final String sessionInfo;
    public final String sessionName;
    public final String timing;
    public final Uri uri;

    public static final class Builder {
        private String connection;
        private String emailAddress;
        private String key;
        private String origin;
        private String phoneNumber;
        private String sessionInfo;
        private String sessionName;
        private String timing;
        private Uri uri;
        private final HashMap<String, String> attributes = new HashMap<>();
        private final ImmutableList.Builder<MediaDescription> mediaDescriptionListBuilder = new ImmutableList.Builder<>();
        private int bitrate = -1;

        public Builder setSessionName(String sessionName) {
            this.sessionName = sessionName;
            return this;
        }

        public Builder setSessionInfo(String sessionInfo) {
            this.sessionInfo = sessionInfo;
            return this;
        }

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder setOrigin(String origin) {
            this.origin = origin;
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

        public Builder setTiming(String timing) {
            this.timing = timing;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder addAttribute(String attributeName, String attributeValue) {
            this.attributes.put(attributeName, attributeValue);
            return this;
        }

        public Builder addMediaDescription(MediaDescription mediaDescription) {
            this.mediaDescriptionListBuilder.add((ImmutableList.Builder<MediaDescription>) mediaDescription);
            return this;
        }

        public SessionDescription build() {
            if (this.sessionName == null || this.origin == null || this.timing == null) {
                throw new IllegalStateException("One of more mandatory SDP fields are not set.");
            }
            return new SessionDescription(this);
        }
    }

    private SessionDescription(Builder builder) {
        this.attributes = ImmutableMap.copyOf((Map) builder.attributes);
        this.mediaDescriptionList = builder.mediaDescriptionListBuilder.build();
        this.sessionName = (String) Util.castNonNull(builder.sessionName);
        this.origin = (String) Util.castNonNull(builder.origin);
        this.timing = (String) Util.castNonNull(builder.timing);
        this.uri = builder.uri;
        this.connection = builder.connection;
        this.bitrate = builder.bitrate;
        this.key = builder.key;
        this.emailAddress = builder.emailAddress;
        this.phoneNumber = builder.phoneNumber;
        this.sessionInfo = builder.sessionInfo;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionDescription sessionDescription = (SessionDescription) o;
        return this.bitrate == sessionDescription.bitrate && this.attributes.equals(sessionDescription.attributes) && this.mediaDescriptionList.equals(sessionDescription.mediaDescriptionList) && this.origin.equals(sessionDescription.origin) && this.sessionName.equals(sessionDescription.sessionName) && this.timing.equals(sessionDescription.timing) && Util.areEqual(this.sessionInfo, sessionDescription.sessionInfo) && Util.areEqual(this.uri, sessionDescription.uri) && Util.areEqual(this.emailAddress, sessionDescription.emailAddress) && Util.areEqual(this.phoneNumber, sessionDescription.phoneNumber) && Util.areEqual(this.connection, sessionDescription.connection) && Util.areEqual(this.key, sessionDescription.key);
    }

    public int hashCode() {
        int iHashCode = (((((((((((217 + this.attributes.hashCode()) * 31) + this.mediaDescriptionList.hashCode()) * 31) + this.origin.hashCode()) * 31) + this.sessionName.hashCode()) * 31) + this.timing.hashCode()) * 31) + this.bitrate) * 31;
        String str = this.sessionInfo;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        Uri uri = this.uri;
        int iHashCode3 = (iHashCode2 + (uri == null ? 0 : uri.hashCode())) * 31;
        String str2 = this.emailAddress;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.phoneNumber;
        int iHashCode5 = (iHashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.connection;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.key;
        return iHashCode6 + (str5 != null ? str5.hashCode() : 0);
    }
}
