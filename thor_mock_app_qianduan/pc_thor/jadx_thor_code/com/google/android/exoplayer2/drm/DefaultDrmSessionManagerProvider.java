package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.util.Map;

/* loaded from: classes.dex */
public final class DefaultDrmSessionManagerProvider implements DrmSessionManagerProvider {
    private MediaItem.DrmConfiguration drmConfiguration;
    private HttpDataSource.Factory drmHttpDataSourceFactory;
    private final Object lock = new Object();
    private DrmSessionManager manager;
    private String userAgent;

    public void setDrmHttpDataSourceFactory(HttpDataSource.Factory drmHttpDataSourceFactory) {
        this.drmHttpDataSourceFactory = drmHttpDataSourceFactory;
    }

    public void setDrmUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
    public DrmSessionManager get(MediaItem mediaItem) {
        DrmSessionManager drmSessionManager;
        Assertions.checkNotNull(mediaItem.playbackProperties);
        MediaItem.DrmConfiguration drmConfiguration = mediaItem.playbackProperties.drmConfiguration;
        if (drmConfiguration == null || Util.SDK_INT < 18) {
            return DrmSessionManager.DRM_UNSUPPORTED;
        }
        synchronized (this.lock) {
            if (!Util.areEqual(drmConfiguration, this.drmConfiguration)) {
                this.drmConfiguration = drmConfiguration;
                this.manager = createManager(drmConfiguration);
            }
            drmSessionManager = (DrmSessionManager) Assertions.checkNotNull(this.manager);
        }
        return drmSessionManager;
    }

    private DrmSessionManager createManager(MediaItem.DrmConfiguration drmConfiguration) {
        HttpDataSource.Factory userAgent = this.drmHttpDataSourceFactory;
        if (userAgent == null) {
            userAgent = new DefaultHttpDataSource.Factory().setUserAgent(this.userAgent);
        }
        HttpMediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(drmConfiguration.licenseUri == null ? null : drmConfiguration.licenseUri.toString(), drmConfiguration.forceDefaultLicenseUri, userAgent);
        for (Map.Entry<String, String> entry : drmConfiguration.requestHeaders.entrySet()) {
            httpMediaDrmCallback.setKeyRequestProperty(entry.getKey(), entry.getValue());
        }
        DefaultDrmSessionManager defaultDrmSessionManagerBuild = new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(drmConfiguration.uuid, FrameworkMediaDrm.DEFAULT_PROVIDER).setMultiSession(drmConfiguration.multiSession).setPlayClearSamplesWithoutKeys(drmConfiguration.playClearContentWithoutKey).setUseDrmSessionsForClearContent(Ints.toArray(drmConfiguration.sessionForClearTypes)).build(httpMediaDrmCallback);
        defaultDrmSessionManagerBuild.setMode(0, drmConfiguration.getKeySetId());
        return defaultDrmSessionManagerBuild;
    }
}
