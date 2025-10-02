package com.google.android.exoplayer2.drm;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private static final int MAX_MANUAL_REDIRECTS = 5;
    private final HttpDataSource.Factory dataSourceFactory;
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    public HttpMediaDrmCallback(String defaultLicenseUrl, HttpDataSource.Factory dataSourceFactory) {
        this(defaultLicenseUrl, false, dataSourceFactory);
    }

    public HttpMediaDrmCallback(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, HttpDataSource.Factory dataSourceFactory) {
        Assertions.checkArgument((forceDefaultLicenseUrl && TextUtils.isEmpty(defaultLicenseUrl)) ? false : true);
        this.dataSourceFactory = dataSourceFactory;
        this.defaultLicenseUrl = defaultLicenseUrl;
        this.forceDefaultLicenseUrl = forceDefaultLicenseUrl;
        this.keyRequestProperties = new HashMap();
    }

    public void setKeyRequestProperty(String name, String value) {
        Assertions.checkNotNull(name);
        Assertions.checkNotNull(value);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(name, value);
        }
    }

    public void clearKeyRequestProperty(String name) {
        Assertions.checkNotNull(name);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(name);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws MediaDrmCallbackException {
        String defaultUrl = request.getDefaultUrl();
        String strFromUtf8Bytes = Util.fromUtf8Bytes(request.getData());
        return executePost(this.dataSourceFactory, new StringBuilder(String.valueOf(defaultUrl).length() + 15 + String.valueOf(strFromUtf8Bytes).length()).append(defaultUrl).append("&signedRequest=").append(strFromUtf8Bytes).toString(), null, Collections.emptyMap());
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws MediaDrmCallbackException {
        String str;
        String licenseServerUrl = request.getLicenseServerUrl();
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(licenseServerUrl)) {
            licenseServerUrl = this.defaultLicenseUrl;
        }
        if (TextUtils.isEmpty(licenseServerUrl)) {
            throw new MediaDrmCallbackException(new DataSpec.Builder().setUri(Uri.EMPTY).build(), Uri.EMPTY, ImmutableMap.of(), 0L, new IllegalStateException("No license URL"));
        }
        HashMap map = new HashMap();
        if (C.PLAYREADY_UUID.equals(uuid)) {
            str = "text/xml";
        } else {
            str = C.CLEARKEY_UUID.equals(uuid) ? AbstractSpiCall.ACCEPT_JSON_VALUE : "application/octet-stream";
        }
        map.put("Content-Type", str);
        if (C.PLAYREADY_UUID.equals(uuid)) {
            map.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            map.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, licenseServerUrl, request.getData(), map);
    }

    private static byte[] executePost(HttpDataSource.Factory dataSourceFactory, String url, byte[] httpBody, Map<String, String> requestProperties) throws MediaDrmCallbackException {
        StatsDataSource statsDataSource = new StatsDataSource(dataSourceFactory.createDataSource());
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(url).setHttpRequestHeaders(requestProperties).setHttpMethod(2).setHttpBody(httpBody).setFlags(1).build();
        int i = 0;
        DataSpec dataSpecBuild2 = dataSpecBuild;
        while (true) {
            try {
                DataSourceInputStream dataSourceInputStream = new DataSourceInputStream(statsDataSource, dataSpecBuild2);
                try {
                    return Util.toByteArray(dataSourceInputStream);
                } catch (HttpDataSource.InvalidResponseCodeException e) {
                    String redirectUrl = getRedirectUrl(e, i);
                    if (redirectUrl == null) {
                        throw e;
                    }
                    i++;
                    dataSpecBuild2 = dataSpecBuild2.buildUpon().setUri(redirectUrl).build();
                } finally {
                    Util.closeQuietly(dataSourceInputStream);
                }
            } catch (Exception e2) {
                throw new MediaDrmCallbackException(dataSpecBuild, (Uri) Assertions.checkNotNull(statsDataSource.getLastOpenedUri()), statsDataSource.getResponseHeaders(), statsDataSource.getBytesRead(), e2);
            }
        }
    }

    private static String getRedirectUrl(HttpDataSource.InvalidResponseCodeException exception, int manualRedirectCount) {
        Map<String, List<String>> map;
        List<String> list;
        if (!((exception.responseCode == 307 || exception.responseCode == 308) && manualRedirectCount < 5) || (map = exception.headerFields) == null || (list = map.get(HttpHeaders.LOCATION)) == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
