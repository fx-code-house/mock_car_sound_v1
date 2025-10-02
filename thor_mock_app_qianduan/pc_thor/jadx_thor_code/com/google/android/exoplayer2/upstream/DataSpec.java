package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class DataSpec {
    public static final int FLAG_ALLOW_CACHE_FRAGMENTATION = 4;
    public static final int FLAG_ALLOW_GZIP = 1;
    public static final int FLAG_DONT_CACHE_IF_LENGTH_UNKNOWN = 2;
    public static final int FLAG_MIGHT_NOT_USE_FULL_NETWORK_SPEED = 8;
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_HEAD = 3;
    public static final int HTTP_METHOD_POST = 2;

    @Deprecated
    public final long absoluteStreamPosition;
    public final Object customData;
    public final int flags;
    public final byte[] httpBody;
    public final int httpMethod;
    public final Map<String, String> httpRequestHeaders;
    public final String key;
    public final long length;
    public final long position;
    public final Uri uri;
    public final long uriPositionOffset;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {
    }

    public static final class Builder {
        private Object customData;
        private int flags;
        private byte[] httpBody;
        private int httpMethod;
        private Map<String, String> httpRequestHeaders;
        private String key;
        private long length;
        private long position;
        private Uri uri;
        private long uriPositionOffset;

        public Builder() {
            this.httpMethod = 1;
            this.httpRequestHeaders = Collections.emptyMap();
            this.length = -1L;
        }

        private Builder(DataSpec dataSpec) {
            this.uri = dataSpec.uri;
            this.uriPositionOffset = dataSpec.uriPositionOffset;
            this.httpMethod = dataSpec.httpMethod;
            this.httpBody = dataSpec.httpBody;
            this.httpRequestHeaders = dataSpec.httpRequestHeaders;
            this.position = dataSpec.position;
            this.length = dataSpec.length;
            this.key = dataSpec.key;
            this.flags = dataSpec.flags;
            this.customData = dataSpec.customData;
        }

        public Builder setUri(String uriString) {
            this.uri = Uri.parse(uriString);
            return this;
        }

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder setUriPositionOffset(long uriPositionOffset) {
            this.uriPositionOffset = uriPositionOffset;
            return this;
        }

        public Builder setHttpMethod(int httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder setHttpBody(byte[] httpBody) {
            this.httpBody = httpBody;
            return this;
        }

        public Builder setHttpRequestHeaders(Map<String, String> httpRequestHeaders) {
            this.httpRequestHeaders = httpRequestHeaders;
            return this;
        }

        public Builder setPosition(long position) {
            this.position = position;
            return this;
        }

        public Builder setLength(long length) {
            this.length = length;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder setCustomData(Object customData) {
            this.customData = customData;
            return this;
        }

        public DataSpec build() {
            Assertions.checkStateNotNull(this.uri, "The uri must be set.");
            return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
        }
    }

    public static String getStringForHttpMethod(int httpMethod) {
        if (httpMethod == 1) {
            return "GET";
        }
        if (httpMethod == 2) {
            return "POST";
        }
        if (httpMethod == 3) {
            return "HEAD";
        }
        throw new IllegalStateException();
    }

    public DataSpec(Uri uri) {
        this(uri, 0L, -1L);
    }

    public DataSpec(Uri uri, long position, long length) {
        this(uri, 0L, 1, null, Collections.emptyMap(), position, length, null, 0, null);
    }

    @Deprecated
    public DataSpec(Uri uri, int flags) {
        this(uri, 0L, -1L, null, flags);
    }

    @Deprecated
    public DataSpec(Uri uri, long position, long length, String key) {
        this(uri, position, position, length, key, 0);
    }

    @Deprecated
    public DataSpec(Uri uri, long position, long length, String key, int flags) {
        this(uri, position, position, length, key, flags);
    }

    @Deprecated
    public DataSpec(Uri uri, long position, long length, String key, int flags, Map<String, String> httpRequestHeaders) {
        this(uri, 1, null, position, position, length, key, flags, httpRequestHeaders);
    }

    @Deprecated
    public DataSpec(Uri uri, long absoluteStreamPosition, long position, long length, String key, int flags) {
        this(uri, null, absoluteStreamPosition, position, length, key, flags);
    }

    @Deprecated
    public DataSpec(Uri uri, byte[] postBody, long absoluteStreamPosition, long position, long length, String key, int flags) {
        this(uri, postBody != null ? 2 : 1, postBody, absoluteStreamPosition, position, length, key, flags);
    }

    @Deprecated
    public DataSpec(Uri uri, int httpMethod, byte[] httpBody, long absoluteStreamPosition, long position, long length, String key, int flags) {
        this(uri, httpMethod, httpBody, absoluteStreamPosition, position, length, key, flags, Collections.emptyMap());
    }

    @Deprecated
    public DataSpec(Uri uri, int httpMethod, byte[] httpBody, long absoluteStreamPosition, long position, long length, String key, int flags, Map<String, String> httpRequestHeaders) {
        this(uri, absoluteStreamPosition - position, httpMethod, httpBody, httpRequestHeaders, position, length, key, flags, null);
    }

    private DataSpec(Uri uri, long uriPositionOffset, int httpMethod, byte[] httpBody, Map<String, String> httpRequestHeaders, long position, long length, String key, int flags, Object customData) {
        byte[] bArr = httpBody;
        long j = uriPositionOffset + position;
        boolean z = true;
        Assertions.checkArgument(j >= 0);
        Assertions.checkArgument(position >= 0);
        if (length <= 0 && length != -1) {
            z = false;
        }
        Assertions.checkArgument(z);
        this.uri = uri;
        this.uriPositionOffset = uriPositionOffset;
        this.httpMethod = httpMethod;
        this.httpBody = (bArr == null || bArr.length == 0) ? null : bArr;
        this.httpRequestHeaders = Collections.unmodifiableMap(new HashMap(httpRequestHeaders));
        this.position = position;
        this.absoluteStreamPosition = j;
        this.length = length;
        this.key = key;
        this.flags = flags;
        this.customData = customData;
    }

    public boolean isFlagSet(int flag) {
        return (this.flags & flag) == flag;
    }

    public final String getHttpMethodString() {
        return getStringForHttpMethod(this.httpMethod);
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public DataSpec subrange(long offset) {
        long j = this.length;
        return subrange(offset, j != -1 ? j - offset : -1L);
    }

    public DataSpec subrange(long offset, long length) {
        return (offset == 0 && this.length == length) ? this : new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position + offset, length, this.key, this.flags, this.customData);
    }

    public DataSpec withUri(Uri uri) {
        return new DataSpec(uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withRequestHeaders(Map<String, String> httpRequestHeaders) {
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withAdditionalHeaders(Map<String, String> additionalHttpRequestHeaders) {
        HashMap map = new HashMap(this.httpRequestHeaders);
        map.putAll(additionalHttpRequestHeaders);
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, map, this.position, this.length, this.key, this.flags, this.customData);
    }

    public String toString() {
        String httpMethodString = getHttpMethodString();
        String strValueOf = String.valueOf(this.uri);
        long j = this.position;
        long j2 = this.length;
        String str = this.key;
        return new StringBuilder(String.valueOf(httpMethodString).length() + 70 + String.valueOf(strValueOf).length() + String.valueOf(str).length()).append("DataSpec[").append(httpMethodString).append(StringUtils.SPACE).append(strValueOf).append(", ").append(j).append(", ").append(j2).append(", ").append(str).append(", ").append(this.flags).append("]").toString();
    }
}
