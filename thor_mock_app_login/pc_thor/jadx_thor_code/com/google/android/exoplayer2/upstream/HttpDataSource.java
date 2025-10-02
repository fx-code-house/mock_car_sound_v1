package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface HttpDataSource extends DataSource {
    public static final Predicate<String> REJECT_PAYWALL_TYPES = new Predicate() { // from class: com.google.android.exoplayer2.upstream.HttpDataSource$$ExternalSyntheticLambda0
        @Override // com.google.common.base.Predicate
        public final boolean apply(Object obj) {
            return HttpDataSource.lambda$static$0((String) obj);
        }
    };

    public interface Factory extends DataSource.Factory {
        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        HttpDataSource createDataSource();

        @Deprecated
        RequestProperties getDefaultRequestProperties();

        Factory setDefaultRequestProperties(Map<String, String> defaultRequestProperties);
    }

    void clearAllRequestProperties();

    void clearRequestProperty(String name);

    @Override // com.google.android.exoplayer2.upstream.DataSource
    void close() throws HttpDataSourceException;

    int getResponseCode();

    @Override // com.google.android.exoplayer2.upstream.DataSource
    Map<String, List<String>> getResponseHeaders();

    @Override // com.google.android.exoplayer2.upstream.DataSource
    long open(DataSpec dataSpec) throws HttpDataSourceException;

    @Override // com.google.android.exoplayer2.upstream.DataReader
    int read(byte[] buffer, int offset, int length) throws HttpDataSourceException;

    void setRequestProperty(String name, String value);

    public static final class RequestProperties {
        private final Map<String, String> requestProperties = new HashMap();
        private Map<String, String> requestPropertiesSnapshot;

        public synchronized void set(String name, String value) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.put(name, value);
        }

        public synchronized void set(Map<String, String> properties) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.putAll(properties);
        }

        public synchronized void clearAndSet(Map<String, String> properties) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
            this.requestProperties.putAll(properties);
        }

        public synchronized void remove(String name) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.remove(name);
        }

        public synchronized void clear() {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
        }

        public synchronized Map<String, String> getSnapshot() {
            if (this.requestPropertiesSnapshot == null) {
                this.requestPropertiesSnapshot = Collections.unmodifiableMap(new HashMap(this.requestProperties));
            }
            return this.requestPropertiesSnapshot;
        }
    }

    public static abstract class BaseFactory implements Factory {
        private final RequestProperties defaultRequestProperties = new RequestProperties();

        protected abstract HttpDataSource createDataSourceInternal(RequestProperties defaultRequestProperties);

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory, com.google.android.exoplayer2.upstream.DataSource.Factory
        public final HttpDataSource createDataSource() {
            return createDataSourceInternal(this.defaultRequestProperties);
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        @Deprecated
        public final RequestProperties getDefaultRequestProperties() {
            return this.defaultRequestProperties;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public final Factory setDefaultRequestProperties(Map<String, String> defaultRequestProperties) {
            this.defaultRequestProperties.clearAndSet(defaultRequestProperties);
            return this;
        }
    }

    static /* synthetic */ boolean lambda$static$0(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = Ascii.toLowerCase(str);
        if (TextUtils.isEmpty(lowerCase)) {
            return false;
        }
        return ((lowerCase.contains("text") && !lowerCase.contains(MimeTypes.TEXT_VTT)) || lowerCase.contains("html") || lowerCase.contains("xml")) ? false : true;
    }

    public static class HttpDataSourceException extends DataSourceException {
        public static final int TYPE_CLOSE = 3;
        public static final int TYPE_OPEN = 1;
        public static final int TYPE_READ = 2;
        public final DataSpec dataSpec;
        public final int type;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        private static int assignErrorCode(int errorCode, int type) {
            return (errorCode == 2000 && type == 1) ? PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED : errorCode;
        }

        public static HttpDataSourceException createForIOException(IOException cause, DataSpec dataSpec, int type) {
            int i;
            String message = cause.getMessage();
            if (cause instanceof SocketTimeoutException) {
                i = PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT;
            } else if (cause instanceof InterruptedIOException) {
                i = 1004;
            } else {
                i = (message == null || !Ascii.toLowerCase(message).matches("cleartext.*not permitted.*")) ? PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED : 2007;
            }
            if (i == 2007) {
                return new CleartextNotPermittedException(cause, dataSpec);
            }
            return new HttpDataSourceException(cause, dataSpec, i, type);
        }

        @Deprecated
        public HttpDataSourceException(DataSpec dataSpec, int type) {
            this(dataSpec, 2000, type);
        }

        public HttpDataSourceException(DataSpec dataSpec, int errorCode, int type) {
            super(assignErrorCode(errorCode, type));
            this.dataSpec = dataSpec;
            this.type = type;
        }

        @Deprecated
        public HttpDataSourceException(String message, DataSpec dataSpec, int type) {
            this(message, dataSpec, 2000, type);
        }

        public HttpDataSourceException(String message, DataSpec dataSpec, int errorCode, int type) {
            super(message, assignErrorCode(errorCode, type));
            this.dataSpec = dataSpec;
            this.type = type;
        }

        @Deprecated
        public HttpDataSourceException(IOException cause, DataSpec dataSpec, int type) {
            this(cause, dataSpec, 2000, type);
        }

        public HttpDataSourceException(IOException cause, DataSpec dataSpec, int errorCode, int type) {
            super(cause, assignErrorCode(errorCode, type));
            this.dataSpec = dataSpec;
            this.type = type;
        }

        @Deprecated
        public HttpDataSourceException(String message, IOException cause, DataSpec dataSpec, int type) {
            this(message, cause, dataSpec, 2000, type);
        }

        public HttpDataSourceException(String message, IOException cause, DataSpec dataSpec, int errorCode, int type) {
            super(message, cause, assignErrorCode(errorCode, type));
            this.dataSpec = dataSpec;
            this.type = type;
        }
    }

    public static final class CleartextNotPermittedException extends HttpDataSourceException {
        public CleartextNotPermittedException(IOException cause, DataSpec dataSpec) {
            super("Cleartext HTTP traffic not permitted. See https://exoplayer.dev/issues/cleartext-not-permitted", cause, dataSpec, PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED, 1);
        }
    }

    public static final class InvalidContentTypeException extends HttpDataSourceException {
        public final String contentType;

        /* JADX WARN: Illegal instructions before constructor call */
        public InvalidContentTypeException(String contentType, DataSpec dataSpec) {
            String strValueOf = String.valueOf(contentType);
            super(strValueOf.length() != 0 ? "Invalid content type: ".concat(strValueOf) : new String("Invalid content type: "), dataSpec, PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE, 1);
            this.contentType = contentType;
        }
    }

    public static final class InvalidResponseCodeException extends HttpDataSourceException {
        public final Map<String, List<String>> headerFields;
        public final byte[] responseBody;
        public final int responseCode;
        public final String responseMessage;

        @Deprecated
        public InvalidResponseCodeException(int responseCode, Map<String, List<String>> headerFields, DataSpec dataSpec) {
            this(responseCode, null, null, headerFields, dataSpec, Util.EMPTY_BYTE_ARRAY);
        }

        @Deprecated
        public InvalidResponseCodeException(int responseCode, String responseMessage, Map<String, List<String>> headerFields, DataSpec dataSpec) {
            this(responseCode, responseMessage, null, headerFields, dataSpec, Util.EMPTY_BYTE_ARRAY);
        }

        public InvalidResponseCodeException(int responseCode, String responseMessage, IOException cause, Map<String, List<String>> headerFields, DataSpec dataSpec, byte[] responseBody) {
            super(new StringBuilder(26).append("Response code: ").append(responseCode).toString(), cause, dataSpec, PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS, 1);
            this.responseCode = responseCode;
            this.responseMessage = responseMessage;
            this.headerFields = headerFields;
            this.responseBody = responseBody;
        }
    }
}
