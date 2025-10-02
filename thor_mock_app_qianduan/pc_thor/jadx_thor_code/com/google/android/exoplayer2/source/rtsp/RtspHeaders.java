package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class RtspHeaders {
    public static final String ACCEPT = "Accept";
    public static final String ALLOW = "Allow";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BANDWIDTH = "Bandwidth";
    public static final String BLOCKSIZE = "Blocksize";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONNECTION = "Connection";
    public static final String CONTENT_BASE = "Content-Base";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LANGUAGE = "Content-Language";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_LOCATION = "Content-Location";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CSEQ = "CSeq";
    public static final String DATE = "Date";
    public static final RtspHeaders EMPTY = new Builder().build();
    public static final String EXPIRES = "Expires";
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
    public static final String PROXY_REQUIRE = "Proxy-Require";
    public static final String PUBLIC = "Public";
    public static final String RANGE = "Range";
    public static final String RTCP_INTERVAL = "RTCP-Interval";
    public static final String RTP_INFO = "RTP-Info";
    public static final String SCALE = "Scale";
    public static final String SESSION = "Session";
    public static final String SPEED = "Speed";
    public static final String SUPPORTED = "Supported";
    public static final String TIMESTAMP = "Timestamp";
    public static final String TRANSPORT = "Transport";
    public static final String USER_AGENT = "User-Agent";
    public static final String VIA = "Via";
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private final ImmutableListMultimap<String, String> namesAndValues;

    public static final class Builder {
        private final ImmutableListMultimap.Builder<String, String> namesAndValuesBuilder;

        public Builder() {
            this.namesAndValuesBuilder = new ImmutableListMultimap.Builder<>();
        }

        private Builder(ImmutableListMultimap.Builder<String, String> namesAndValuesBuilder) {
            this.namesAndValuesBuilder = namesAndValuesBuilder;
        }

        public Builder add(String headerName, String headerValue) {
            this.namesAndValuesBuilder.put((ImmutableListMultimap.Builder<String, String>) RtspHeaders.convertToStandardHeaderName(headerName.trim()), headerValue.trim());
            return this;
        }

        public Builder addAll(List<String> headers) {
            for (int i = 0; i < headers.size(); i++) {
                String[] strArrSplitAtFirst = Util.splitAtFirst(headers.get(i), ":\\s?");
                if (strArrSplitAtFirst.length == 2) {
                    add(strArrSplitAtFirst[0], strArrSplitAtFirst[1]);
                }
            }
            return this;
        }

        public Builder addAll(Map<String, String> headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public RtspHeaders build() {
            return new RtspHeaders(this);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RtspHeaders) {
            return this.namesAndValues.equals(((RtspHeaders) obj).namesAndValues);
        }
        return false;
    }

    public int hashCode() {
        return this.namesAndValues.hashCode();
    }

    public Builder buildUpon() {
        ImmutableListMultimap.Builder builder = new ImmutableListMultimap.Builder();
        builder.putAll((Multimap) this.namesAndValues);
        return new Builder(builder);
    }

    public ImmutableListMultimap<String, String> asMultiMap() {
        return this.namesAndValues;
    }

    public String get(String headerName) {
        ImmutableList<String> immutableListValues = values(headerName);
        if (immutableListValues.isEmpty()) {
            return null;
        }
        return (String) Iterables.getLast(immutableListValues);
    }

    public ImmutableList<String> values(String headerName) {
        return this.namesAndValues.get((ImmutableListMultimap<String, String>) convertToStandardHeaderName(headerName));
    }

    private RtspHeaders(Builder builder) {
        this.namesAndValues = builder.namesAndValuesBuilder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String convertToStandardHeaderName(String messageHeaderName) {
        return Ascii.equalsIgnoreCase(messageHeaderName, "Accept") ? "Accept" : Ascii.equalsIgnoreCase(messageHeaderName, "Allow") ? "Allow" : Ascii.equalsIgnoreCase(messageHeaderName, "Authorization") ? "Authorization" : Ascii.equalsIgnoreCase(messageHeaderName, BANDWIDTH) ? BANDWIDTH : Ascii.equalsIgnoreCase(messageHeaderName, BLOCKSIZE) ? BLOCKSIZE : Ascii.equalsIgnoreCase(messageHeaderName, "Cache-Control") ? "Cache-Control" : Ascii.equalsIgnoreCase(messageHeaderName, "Connection") ? "Connection" : Ascii.equalsIgnoreCase(messageHeaderName, CONTENT_BASE) ? CONTENT_BASE : Ascii.equalsIgnoreCase(messageHeaderName, "Content-Encoding") ? "Content-Encoding" : Ascii.equalsIgnoreCase(messageHeaderName, "Content-Language") ? "Content-Language" : Ascii.equalsIgnoreCase(messageHeaderName, "Content-Length") ? "Content-Length" : Ascii.equalsIgnoreCase(messageHeaderName, "Content-Location") ? "Content-Location" : Ascii.equalsIgnoreCase(messageHeaderName, "Content-Type") ? "Content-Type" : Ascii.equalsIgnoreCase(messageHeaderName, CSEQ) ? CSEQ : Ascii.equalsIgnoreCase(messageHeaderName, "Date") ? "Date" : Ascii.equalsIgnoreCase(messageHeaderName, "Expires") ? "Expires" : Ascii.equalsIgnoreCase(messageHeaderName, "Proxy-Authenticate") ? "Proxy-Authenticate" : Ascii.equalsIgnoreCase(messageHeaderName, PROXY_REQUIRE) ? PROXY_REQUIRE : Ascii.equalsIgnoreCase(messageHeaderName, PUBLIC) ? PUBLIC : Ascii.equalsIgnoreCase(messageHeaderName, "Range") ? "Range" : Ascii.equalsIgnoreCase(messageHeaderName, RTP_INFO) ? RTP_INFO : Ascii.equalsIgnoreCase(messageHeaderName, RTCP_INTERVAL) ? RTCP_INTERVAL : Ascii.equalsIgnoreCase(messageHeaderName, SCALE) ? SCALE : Ascii.equalsIgnoreCase(messageHeaderName, SESSION) ? SESSION : Ascii.equalsIgnoreCase(messageHeaderName, SPEED) ? SPEED : Ascii.equalsIgnoreCase(messageHeaderName, SUPPORTED) ? SUPPORTED : Ascii.equalsIgnoreCase(messageHeaderName, TIMESTAMP) ? TIMESTAMP : Ascii.equalsIgnoreCase(messageHeaderName, TRANSPORT) ? TRANSPORT : Ascii.equalsIgnoreCase(messageHeaderName, "User-Agent") ? "User-Agent" : Ascii.equalsIgnoreCase(messageHeaderName, "Via") ? "Via" : Ascii.equalsIgnoreCase(messageHeaderName, "WWW-Authenticate") ? "WWW-Authenticate" : messageHeaderName;
    }
}
