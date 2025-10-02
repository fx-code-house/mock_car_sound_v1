package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Predicate;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/* loaded from: classes.dex */
public class DefaultHttpDataSource extends BaseDataSource implements HttpDataSource {
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private static final int HTTP_STATUS_PERMANENT_REDIRECT = 308;
    private static final int HTTP_STATUS_TEMPORARY_REDIRECT = 307;
    private static final long MAX_BYTES_TO_DRAIN = 2048;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "DefaultHttpDataSource";
    private final boolean allowCrossProtocolRedirects;
    private long bytesRead;
    private long bytesToRead;
    private final int connectTimeoutMillis;
    private HttpURLConnection connection;
    private Predicate<String> contentTypePredicate;
    private DataSpec dataSpec;
    private final HttpDataSource.RequestProperties defaultRequestProperties;
    private InputStream inputStream;
    private final boolean keepPostFor302Redirects;
    private boolean opened;
    private final int readTimeoutMillis;
    private final HttpDataSource.RequestProperties requestProperties;
    private int responseCode;
    private final String userAgent;

    public static final class Factory implements HttpDataSource.Factory {
        private boolean allowCrossProtocolRedirects;
        private Predicate<String> contentTypePredicate;
        private boolean keepPostFor302Redirects;
        private TransferListener transferListener;
        private String userAgent;
        private final HttpDataSource.RequestProperties defaultRequestProperties = new HttpDataSource.RequestProperties();
        private int connectTimeoutMs = 8000;
        private int readTimeoutMs = 8000;

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public /* bridge */ /* synthetic */ HttpDataSource.Factory setDefaultRequestProperties(Map defaultRequestProperties) {
            return setDefaultRequestProperties((Map<String, String>) defaultRequestProperties);
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        @Deprecated
        public final HttpDataSource.RequestProperties getDefaultRequestProperties() {
            return this.defaultRequestProperties;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public final Factory setDefaultRequestProperties(Map<String, String> defaultRequestProperties) {
            this.defaultRequestProperties.clearAndSet(defaultRequestProperties);
            return this;
        }

        public Factory setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Factory setConnectTimeoutMs(int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
            return this;
        }

        public Factory setReadTimeoutMs(int readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
            return this;
        }

        public Factory setAllowCrossProtocolRedirects(boolean allowCrossProtocolRedirects) {
            this.allowCrossProtocolRedirects = allowCrossProtocolRedirects;
            return this;
        }

        public Factory setContentTypePredicate(Predicate<String> contentTypePredicate) {
            this.contentTypePredicate = contentTypePredicate;
            return this;
        }

        public Factory setTransferListener(TransferListener transferListener) {
            this.transferListener = transferListener;
            return this;
        }

        public Factory setKeepPostFor302Redirects(boolean keepPostFor302Redirects) {
            this.keepPostFor302Redirects = keepPostFor302Redirects;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory, com.google.android.exoplayer2.upstream.DataSource.Factory
        public DefaultHttpDataSource createDataSource() {
            DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(this.userAgent, this.connectTimeoutMs, this.readTimeoutMs, this.allowCrossProtocolRedirects, this.defaultRequestProperties, this.contentTypePredicate, this.keepPostFor302Redirects);
            TransferListener transferListener = this.transferListener;
            if (transferListener != null) {
                defaultHttpDataSource.addTransferListener(transferListener);
            }
            return defaultHttpDataSource;
        }
    }

    @Deprecated
    public DefaultHttpDataSource() {
        this(null, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(String userAgent) {
        this(userAgent, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(String userAgent, int connectTimeoutMillis, int readTimeoutMillis) {
        this(userAgent, connectTimeoutMillis, readTimeoutMillis, false, null);
    }

    @Deprecated
    public DefaultHttpDataSource(String userAgent, int connectTimeoutMillis, int readTimeoutMillis, boolean allowCrossProtocolRedirects, HttpDataSource.RequestProperties defaultRequestProperties) {
        this(userAgent, connectTimeoutMillis, readTimeoutMillis, allowCrossProtocolRedirects, defaultRequestProperties, null, false);
    }

    private DefaultHttpDataSource(String userAgent, int connectTimeoutMillis, int readTimeoutMillis, boolean allowCrossProtocolRedirects, HttpDataSource.RequestProperties defaultRequestProperties, Predicate<String> contentTypePredicate, boolean keepPostFor302Redirects) {
        super(true);
        this.userAgent = userAgent;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
        this.allowCrossProtocolRedirects = allowCrossProtocolRedirects;
        this.defaultRequestProperties = defaultRequestProperties;
        this.contentTypePredicate = contentTypePredicate;
        this.requestProperties = new HttpDataSource.RequestProperties();
        this.keepPostFor302Redirects = keepPostFor302Redirects;
    }

    @Deprecated
    public void setContentTypePredicate(Predicate<String> contentTypePredicate) {
        this.contentTypePredicate = contentTypePredicate;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection == null) {
            return null;
        }
        return Uri.parse(httpURLConnection.getURL().toString());
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public int getResponseCode() {
        int i;
        if (this.connection == null || (i = this.responseCode) <= 0) {
            return -1;
        }
        return i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        HttpURLConnection httpURLConnection = this.connection;
        return httpURLConnection == null ? Collections.emptyMap() : httpURLConnection.getHeaderFields();
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void setRequestProperty(String name, String value) {
        Assertions.checkNotNull(name);
        Assertions.checkNotNull(value);
        this.requestProperties.set(name, value);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearRequestProperty(String name) {
        Assertions.checkNotNull(name);
        this.requestProperties.remove(name);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException, NumberFormatException {
        byte[] byteArray;
        this.dataSpec = dataSpec;
        long j = 0;
        this.bytesRead = 0L;
        this.bytesToRead = 0L;
        transferInitializing(dataSpec);
        try {
            HttpURLConnection httpURLConnectionMakeConnection = makeConnection(dataSpec);
            this.connection = httpURLConnectionMakeConnection;
            this.responseCode = httpURLConnectionMakeConnection.getResponseCode();
            String responseMessage = httpURLConnectionMakeConnection.getResponseMessage();
            int i = this.responseCode;
            if (i < 200 || i > 299) {
                Map<String, List<String>> headerFields = httpURLConnectionMakeConnection.getHeaderFields();
                if (this.responseCode == 416) {
                    if (dataSpec.position == HttpUtil.getDocumentSize(httpURLConnectionMakeConnection.getHeaderField(HttpHeaders.CONTENT_RANGE))) {
                        this.opened = true;
                        transferStarted(dataSpec);
                        if (dataSpec.length != -1) {
                            return dataSpec.length;
                        }
                        return 0L;
                    }
                }
                InputStream errorStream = httpURLConnectionMakeConnection.getErrorStream();
                try {
                    byteArray = errorStream != null ? Util.toByteArray(errorStream) : Util.EMPTY_BYTE_ARRAY;
                } catch (IOException unused) {
                    byteArray = Util.EMPTY_BYTE_ARRAY;
                }
                byte[] bArr = byteArray;
                closeConnectionQuietly();
                throw new HttpDataSource.InvalidResponseCodeException(this.responseCode, responseMessage, this.responseCode == 416 ? new DataSourceException(2008) : null, headerFields, dataSpec, bArr);
            }
            String contentType = httpURLConnectionMakeConnection.getContentType();
            Predicate<String> predicate = this.contentTypePredicate;
            if (predicate != null && !predicate.apply(contentType)) {
                closeConnectionQuietly();
                throw new HttpDataSource.InvalidContentTypeException(contentType, dataSpec);
            }
            if (this.responseCode == 200 && dataSpec.position != 0) {
                j = dataSpec.position;
            }
            boolean zIsCompressed = isCompressed(httpURLConnectionMakeConnection);
            if (zIsCompressed || dataSpec.length != -1) {
                this.bytesToRead = dataSpec.length;
            } else {
                long contentLength = HttpUtil.getContentLength(httpURLConnectionMakeConnection.getHeaderField("Content-Length"), httpURLConnectionMakeConnection.getHeaderField(HttpHeaders.CONTENT_RANGE));
                this.bytesToRead = contentLength != -1 ? contentLength - j : -1L;
            }
            try {
                this.inputStream = httpURLConnectionMakeConnection.getInputStream();
                if (zIsCompressed) {
                    this.inputStream = new GZIPInputStream(this.inputStream);
                }
                this.opened = true;
                transferStarted(dataSpec);
                try {
                    skipFully(j, dataSpec);
                    return this.bytesToRead;
                } catch (IOException e) {
                    closeConnectionQuietly();
                    if (e instanceof HttpDataSource.HttpDataSourceException) {
                        throw ((HttpDataSource.HttpDataSourceException) e);
                    }
                    throw new HttpDataSource.HttpDataSourceException(e, dataSpec, 2000, 1);
                }
            } catch (IOException e2) {
                closeConnectionQuietly();
                throw new HttpDataSource.HttpDataSourceException(e2, dataSpec, 2000, 1);
            }
        } catch (IOException e3) {
            closeConnectionQuietly();
            throw HttpDataSource.HttpDataSourceException.createForIOException(e3, dataSpec, 1);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) throws HttpDataSource.HttpDataSourceException {
        try {
            return readInternal(buffer, offset, length);
        } catch (IOException e) {
            throw HttpDataSource.HttpDataSourceException.createForIOException(e, (DataSpec) Util.castNonNull(this.dataSpec), 2);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws HttpDataSource.HttpDataSourceException {
        try {
            InputStream inputStream = this.inputStream;
            if (inputStream != null) {
                long j = this.bytesToRead;
                long j2 = -1;
                if (j != -1) {
                    j2 = j - this.bytesRead;
                }
                maybeTerminateInputStream(this.connection, j2);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new HttpDataSource.HttpDataSourceException(e, (DataSpec) Util.castNonNull(this.dataSpec), 2000, 3);
                }
            }
        } finally {
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        }
    }

    private HttpURLConnection makeConnection(DataSpec dataSpec) throws IOException {
        HttpURLConnection httpURLConnectionMakeConnection;
        URL url = new URL(dataSpec.uri.toString());
        int i = dataSpec.httpMethod;
        byte[] bArr = dataSpec.httpBody;
        long j = dataSpec.position;
        long j2 = dataSpec.length;
        boolean zIsFlagSet = dataSpec.isFlagSet(1);
        if (!this.allowCrossProtocolRedirects && !this.keepPostFor302Redirects) {
            return makeConnection(url, i, bArr, j, j2, zIsFlagSet, true, dataSpec.httpRequestHeaders);
        }
        URL urlHandleRedirect = url;
        int i2 = i;
        byte[] bArr2 = bArr;
        int i3 = 0;
        while (true) {
            int i4 = i3 + 1;
            if (i3 <= 20) {
                long j3 = j;
                long j4 = j;
                int i5 = i2;
                URL url2 = urlHandleRedirect;
                long j5 = j2;
                httpURLConnectionMakeConnection = makeConnection(urlHandleRedirect, i2, bArr2, j3, j2, zIsFlagSet, false, dataSpec.httpRequestHeaders);
                int responseCode = httpURLConnectionMakeConnection.getResponseCode();
                String headerField = httpURLConnectionMakeConnection.getHeaderField(HttpHeaders.LOCATION);
                if ((i5 == 1 || i5 == 3) && (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307 || responseCode == 308)) {
                    httpURLConnectionMakeConnection.disconnect();
                    urlHandleRedirect = handleRedirect(url2, headerField, dataSpec);
                    i2 = i5;
                } else {
                    if (i5 != 2 || (responseCode != 300 && responseCode != 301 && responseCode != 302 && responseCode != 303)) {
                        break;
                    }
                    httpURLConnectionMakeConnection.disconnect();
                    if (this.keepPostFor302Redirects && responseCode == 302) {
                        i2 = i5;
                    } else {
                        bArr2 = null;
                        i2 = 1;
                    }
                    urlHandleRedirect = handleRedirect(url2, headerField, dataSpec);
                }
                i3 = i4;
                j = j4;
                j2 = j5;
            } else {
                throw new HttpDataSource.HttpDataSourceException(new NoRouteToHostException(new StringBuilder(31).append("Too many redirects: ").append(i4).toString()), dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, 1);
            }
        }
        return httpURLConnectionMakeConnection;
    }

    private HttpURLConnection makeConnection(URL url, int httpMethod, byte[] httpBody, long position, long length, boolean allowGzip, boolean followRedirects, Map<String, String> requestParameters) throws IOException {
        HttpURLConnection httpURLConnectionOpenConnection = openConnection(url);
        httpURLConnectionOpenConnection.setConnectTimeout(this.connectTimeoutMillis);
        httpURLConnectionOpenConnection.setReadTimeout(this.readTimeoutMillis);
        HashMap map = new HashMap();
        HttpDataSource.RequestProperties requestProperties = this.defaultRequestProperties;
        if (requestProperties != null) {
            map.putAll(requestProperties.getSnapshot());
        }
        map.putAll(this.requestProperties.getSnapshot());
        map.putAll(requestParameters);
        for (Map.Entry entry : map.entrySet()) {
            httpURLConnectionOpenConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        String strBuildRangeRequestHeader = HttpUtil.buildRangeRequestHeader(position, length);
        if (strBuildRangeRequestHeader != null) {
            httpURLConnectionOpenConnection.setRequestProperty("Range", strBuildRangeRequestHeader);
        }
        String str = this.userAgent;
        if (str != null) {
            httpURLConnectionOpenConnection.setRequestProperty("User-Agent", str);
        }
        httpURLConnectionOpenConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, allowGzip ? "gzip" : "identity");
        httpURLConnectionOpenConnection.setInstanceFollowRedirects(followRedirects);
        httpURLConnectionOpenConnection.setDoOutput(httpBody != null);
        httpURLConnectionOpenConnection.setRequestMethod(DataSpec.getStringForHttpMethod(httpMethod));
        if (httpBody != null) {
            httpURLConnectionOpenConnection.setFixedLengthStreamingMode(httpBody.length);
            httpURLConnectionOpenConnection.connect();
            OutputStream outputStream = httpURLConnectionOpenConnection.getOutputStream();
            outputStream.write(httpBody);
            outputStream.close();
        } else {
            httpURLConnectionOpenConnection.connect();
        }
        return httpURLConnectionOpenConnection;
    }

    HttpURLConnection openConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private URL handleRedirect(URL originalUrl, String location, DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        if (location == null) {
            throw new HttpDataSource.HttpDataSourceException("Null location redirect", dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, 1);
        }
        try {
            URL url = new URL(originalUrl, location);
            String protocol = url.getProtocol();
            if (!"https".equals(protocol) && !"http".equals(protocol)) {
                String strValueOf = String.valueOf(protocol);
                throw new HttpDataSource.HttpDataSourceException(strValueOf.length() != 0 ? "Unsupported protocol redirect: ".concat(strValueOf) : new String("Unsupported protocol redirect: "), dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, 1);
            }
            if (this.allowCrossProtocolRedirects || protocol.equals(originalUrl.getProtocol())) {
                return url;
            }
            String protocol2 = originalUrl.getProtocol();
            throw new HttpDataSource.HttpDataSourceException(new StringBuilder(String.valueOf(protocol2).length() + 41 + String.valueOf(protocol).length()).append("Disallowed cross-protocol redirect (").append(protocol2).append(" to ").append(protocol).append(")").toString(), dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, 1);
        } catch (MalformedURLException e) {
            throw new HttpDataSource.HttpDataSourceException(e, dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, 1);
        }
    }

    private void skipFully(long bytesToSkip, DataSpec dataSpec) throws IOException {
        if (bytesToSkip == 0) {
            return;
        }
        byte[] bArr = new byte[4096];
        while (bytesToSkip > 0) {
            int i = ((InputStream) Util.castNonNull(this.inputStream)).read(bArr, 0, (int) Math.min(bytesToSkip, 4096));
            if (Thread.currentThread().isInterrupted()) {
                throw new HttpDataSource.HttpDataSourceException(new InterruptedIOException(), dataSpec, 2000, 1);
            }
            if (i == -1) {
                throw new HttpDataSource.HttpDataSourceException(dataSpec, 2008, 1);
            }
            bytesToSkip -= i;
            bytesTransferred(i);
        }
    }

    private int readInternal(byte[] buffer, int offset, int readLength) throws IOException {
        if (readLength == 0) {
            return 0;
        }
        long j = this.bytesToRead;
        if (j != -1) {
            long j2 = j - this.bytesRead;
            if (j2 == 0) {
                return -1;
            }
            readLength = (int) Math.min(readLength, j2);
        }
        int i = ((InputStream) Util.castNonNull(this.inputStream)).read(buffer, offset, readLength);
        if (i == -1) {
            return -1;
        }
        this.bytesRead += i;
        bytesTransferred(i);
        return i;
    }

    private static void maybeTerminateInputStream(HttpURLConnection connection, long bytesRemaining) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (connection == null || Util.SDK_INT < 19 || Util.SDK_INT > 20) {
            return;
        }
        try {
            InputStream inputStream = connection.getInputStream();
            if (bytesRemaining == -1) {
                if (inputStream.read() == -1) {
                    return;
                }
            } else if (bytesRemaining <= 2048) {
                return;
            }
            String name = inputStream.getClass().getName();
            if ("com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream".equals(name) || "com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream".equals(name)) {
                Method declaredMethod = ((Class) Assertions.checkNotNull(inputStream.getClass().getSuperclass())).getDeclaredMethod("unexpectedEndOfInput", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(inputStream, new Object[0]);
            }
        } catch (Exception unused) {
        }
    }

    private void closeConnectionQuietly() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error while disconnecting", e);
            }
            this.connection = null;
        }
    }

    private static boolean isCompressed(HttpURLConnection connection) {
        return "gzip".equalsIgnoreCase(connection.getHeaderField("Content-Encoding"));
    }
}
