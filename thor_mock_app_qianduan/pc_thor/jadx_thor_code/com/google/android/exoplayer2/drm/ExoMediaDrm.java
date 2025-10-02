package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.os.PersistableBundle;
import com.google.android.exoplayer2.drm.DrmInitData;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public interface ExoMediaDrm {
    public static final int EVENT_KEY_EXPIRED = 3;
    public static final int EVENT_KEY_REQUIRED = 2;
    public static final int EVENT_PROVISION_REQUIRED = 1;
    public static final int KEY_TYPE_OFFLINE = 2;
    public static final int KEY_TYPE_RELEASE = 3;
    public static final int KEY_TYPE_STREAMING = 1;

    public interface OnEventListener {
        void onEvent(ExoMediaDrm mediaDrm, byte[] sessionId, int event, int extra, byte[] data);
    }

    public interface OnExpirationUpdateListener {
        void onExpirationUpdate(ExoMediaDrm mediaDrm, byte[] sessionId, long expirationTimeMs);
    }

    public interface OnKeyStatusChangeListener {
        void onKeyStatusChange(ExoMediaDrm mediaDrm, byte[] sessionId, List<KeyStatus> exoKeyInformation, boolean hasNewUsableKey);
    }

    public interface Provider {
        ExoMediaDrm acquireExoMediaDrm(UUID uuid);
    }

    void acquire();

    void closeSession(byte[] sessionId);

    ExoMediaCrypto createMediaCrypto(byte[] sessionId) throws MediaCryptoException;

    Class<? extends ExoMediaCrypto> getExoMediaCryptoType();

    KeyRequest getKeyRequest(byte[] scope, List<DrmInitData.SchemeData> schemeDatas, int keyType, HashMap<String, String> optionalParameters) throws NotProvisionedException;

    PersistableBundle getMetrics();

    byte[] getPropertyByteArray(String propertyName);

    String getPropertyString(String propertyName);

    ProvisionRequest getProvisionRequest();

    byte[] openSession() throws MediaDrmException;

    byte[] provideKeyResponse(byte[] scope, byte[] response) throws DeniedByServerException, NotProvisionedException;

    void provideProvisionResponse(byte[] response) throws DeniedByServerException;

    Map<String, String> queryKeyStatus(byte[] sessionId);

    void release();

    void restoreKeys(byte[] sessionId, byte[] keySetId);

    void setOnEventListener(OnEventListener listener);

    void setOnExpirationUpdateListener(OnExpirationUpdateListener listener);

    void setOnKeyStatusChangeListener(OnKeyStatusChangeListener listener);

    void setPropertyByteArray(String propertyName, byte[] value);

    void setPropertyString(String propertyName, String value);

    public static final class AppManagedProvider implements Provider {
        private final ExoMediaDrm exoMediaDrm;

        public AppManagedProvider(ExoMediaDrm exoMediaDrm) {
            this.exoMediaDrm = exoMediaDrm;
        }

        @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.Provider
        public ExoMediaDrm acquireExoMediaDrm(UUID uuid) {
            this.exoMediaDrm.acquire();
            return this.exoMediaDrm;
        }
    }

    public static final class KeyStatus {
        private final byte[] keyId;
        private final int statusCode;

        public KeyStatus(int statusCode, byte[] keyId) {
            this.statusCode = statusCode;
            this.keyId = keyId;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public byte[] getKeyId() {
            return this.keyId;
        }
    }

    public static final class KeyRequest {
        public static final int REQUEST_TYPE_INITIAL = 0;
        public static final int REQUEST_TYPE_NONE = 3;
        public static final int REQUEST_TYPE_RELEASE = 2;
        public static final int REQUEST_TYPE_RENEWAL = 1;
        public static final int REQUEST_TYPE_UNKNOWN = Integer.MIN_VALUE;
        public static final int REQUEST_TYPE_UPDATE = 4;
        private final byte[] data;
        private final String licenseServerUrl;
        private final int requestType;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        public KeyRequest(byte[] data, String licenseServerUrl) {
            this(data, licenseServerUrl, Integer.MIN_VALUE);
        }

        public KeyRequest(byte[] data, String licenseServerUrl, int requestType) {
            this.data = data;
            this.licenseServerUrl = licenseServerUrl;
            this.requestType = requestType;
        }

        public byte[] getData() {
            return this.data;
        }

        public String getLicenseServerUrl() {
            return this.licenseServerUrl;
        }

        public int getRequestType() {
            return this.requestType;
        }
    }

    public static final class ProvisionRequest {
        private final byte[] data;
        private final String defaultUrl;

        public ProvisionRequest(byte[] data, String defaultUrl) {
            this.data = data;
            this.defaultUrl = defaultUrl;
        }

        public byte[] getData() {
            return this.data;
        }

        public String getDefaultUrl() {
            return this.defaultUrl;
        }
    }
}
