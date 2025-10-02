package com.google.android.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public final class OfflineLicenseHelper {
    private static final Format FORMAT_WITH_EMPTY_DRM_INIT_DATA = new Format.Builder().setDrmInitData(new DrmInitData(new DrmInitData.SchemeData[0])).build();
    private final ConditionVariable conditionVariable;
    private final DefaultDrmSessionManager drmSessionManager;
    private final DrmSessionEventListener.EventDispatcher eventDispatcher;
    private final HandlerThread handlerThread;

    public static OfflineLicenseHelper newWidevineInstance(String defaultLicenseUrl, HttpDataSource.Factory httpDataSourceFactory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(defaultLicenseUrl, false, httpDataSourceFactory, eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, HttpDataSource.Factory httpDataSourceFactory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(defaultLicenseUrl, forceDefaultLicenseUrl, httpDataSourceFactory, null, eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, HttpDataSource.Factory httpDataSourceFactory, Map<String, String> optionalKeyRequestParameters, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new OfflineLicenseHelper(new DefaultDrmSessionManager.Builder().setKeyRequestParameters(optionalKeyRequestParameters).build(new HttpMediaDrmCallback(defaultLicenseUrl, forceDefaultLicenseUrl, httpDataSourceFactory)), eventDispatcher);
    }

    @Deprecated
    public OfflineLicenseHelper(UUID uuid, ExoMediaDrm.Provider mediaDrmProvider, MediaDrmCallback callback, Map<String, String> optionalKeyRequestParameters, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this(new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(uuid, mediaDrmProvider).setKeyRequestParameters(optionalKeyRequestParameters).build(callback), eventDispatcher);
    }

    public OfflineLicenseHelper(DefaultDrmSessionManager defaultDrmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.drmSessionManager = defaultDrmSessionManager;
        this.eventDispatcher = eventDispatcher;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:OfflineLicenseHelper");
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.conditionVariable = new ConditionVariable();
        eventDispatcher.addEventListener(new Handler(handlerThread.getLooper()), new DrmSessionEventListener() { // from class: com.google.android.exoplayer2.drm.OfflineLicenseHelper.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysLoaded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmSessionManagerError(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, Exception e) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysRestored(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysRemoved(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }
        });
    }

    public synchronized byte[] downloadLicense(Format format) throws DrmSession.DrmSessionException {
        Assertions.checkArgument(format.drmInitData != null);
        return blockingKeyRequest(2, null, format);
    }

    public synchronized byte[] renewLicense(byte[] offlineLicenseKeySetId) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        return blockingKeyRequest(2, offlineLicenseKeySetId, FORMAT_WITH_EMPTY_DRM_INIT_DATA);
    }

    public synchronized void releaseLicense(byte[] offlineLicenseKeySetId) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        blockingKeyRequest(3, offlineLicenseKeySetId, FORMAT_WITH_EMPTY_DRM_INIT_DATA);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] offlineLicenseKeySetId) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        this.drmSessionManager.prepare();
        DrmSession drmSessionOpenBlockingKeyRequest = openBlockingKeyRequest(1, offlineLicenseKeySetId, FORMAT_WITH_EMPTY_DRM_INIT_DATA);
        DrmSession.DrmSessionException error = drmSessionOpenBlockingKeyRequest.getError();
        Pair<Long, Long> licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(drmSessionOpenBlockingKeyRequest);
        drmSessionOpenBlockingKeyRequest.release(this.eventDispatcher);
        this.drmSessionManager.release();
        if (error != null) {
            if (error.getCause() instanceof KeysExpiredException) {
                return Pair.create(0L, 0L);
            }
            throw error;
        }
        return (Pair) Assertions.checkNotNull(licenseDurationRemainingSec);
    }

    public void release() {
        this.handlerThread.quit();
    }

    private byte[] blockingKeyRequest(int licenseMode, byte[] offlineLicenseKeySetId, Format format) throws DrmSession.DrmSessionException {
        this.drmSessionManager.prepare();
        DrmSession drmSessionOpenBlockingKeyRequest = openBlockingKeyRequest(licenseMode, offlineLicenseKeySetId, format);
        DrmSession.DrmSessionException error = drmSessionOpenBlockingKeyRequest.getError();
        byte[] offlineLicenseKeySetId2 = drmSessionOpenBlockingKeyRequest.getOfflineLicenseKeySetId();
        drmSessionOpenBlockingKeyRequest.release(this.eventDispatcher);
        this.drmSessionManager.release();
        if (error != null) {
            throw error;
        }
        return (byte[]) Assertions.checkNotNull(offlineLicenseKeySetId2);
    }

    private DrmSession openBlockingKeyRequest(int licenseMode, byte[] offlineLicenseKeySetId, Format format) {
        Assertions.checkNotNull(format.drmInitData);
        this.drmSessionManager.setMode(licenseMode, offlineLicenseKeySetId);
        this.conditionVariable.close();
        DrmSession drmSessionAcquireSession = this.drmSessionManager.acquireSession(this.handlerThread.getLooper(), this.eventDispatcher, format);
        this.conditionVariable.block();
        return (DrmSession) Assertions.checkNotNull(drmSessionAcquireSession);
    }
}
