package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaDrm;
import android.media.MediaDrmResetException;
import android.media.NotProvisionedException;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class DrmUtil {
    public static final int ERROR_SOURCE_EXO_MEDIA_DRM = 1;
    public static final int ERROR_SOURCE_LICENSE_ACQUISITION = 2;
    public static final int ERROR_SOURCE_PROVISIONING = 3;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorSource {
    }

    public static int getErrorCodeForMediaDrmException(Exception exception, int errorSource) {
        if (Util.SDK_INT >= 21 && PlatformOperationsWrapperV21.isMediaDrmStateException(exception)) {
            return PlatformOperationsWrapperV21.mediaDrmStateExceptionToErrorCode(exception);
        }
        if (Util.SDK_INT >= 23 && PlatformOperationsWrapperV23.isMediaDrmResetException(exception)) {
            return PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR;
        }
        if (Util.SDK_INT >= 18 && PlatformOperationsWrapperV18.isNotProvisionedException(exception)) {
            return PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED;
        }
        if (Util.SDK_INT >= 18 && PlatformOperationsWrapperV18.isDeniedByServerException(exception)) {
            return PlaybackException.ERROR_CODE_DRM_DEVICE_REVOKED;
        }
        if (exception instanceof UnsupportedDrmException) {
            return PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED;
        }
        if (exception instanceof DefaultDrmSessionManager.MissingSchemeDataException) {
            return PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR;
        }
        if (exception instanceof KeysExpiredException) {
            return PlaybackException.ERROR_CODE_DRM_LICENSE_EXPIRED;
        }
        if (errorSource == 1) {
            return PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR;
        }
        if (errorSource == 2) {
            return PlaybackException.ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED;
        }
        if (errorSource == 3) {
            return PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED;
        }
        throw new IllegalArgumentException();
    }

    private static final class PlatformOperationsWrapperV18 {
        private PlatformOperationsWrapperV18() {
        }

        public static boolean isNotProvisionedException(Throwable throwable) {
            return throwable instanceof NotProvisionedException;
        }

        public static boolean isDeniedByServerException(Throwable throwable) {
            return throwable instanceof DeniedByServerException;
        }
    }

    private static final class PlatformOperationsWrapperV21 {
        private PlatformOperationsWrapperV21() {
        }

        public static boolean isMediaDrmStateException(Throwable throwable) {
            return throwable instanceof MediaDrm.MediaDrmStateException;
        }

        public static int mediaDrmStateExceptionToErrorCode(Throwable throwable) {
            return C.getErrorCodeForMediaDrmErrorCode(Util.getErrorCodeFromPlatformDiagnosticsInfo(((MediaDrm.MediaDrmStateException) throwable).getDiagnosticInfo()));
        }
    }

    private static final class PlatformOperationsWrapperV23 {
        private PlatformOperationsWrapperV23() {
        }

        public static boolean isMediaDrmResetException(Throwable throwable) {
            return throwable instanceof MediaDrmResetException;
        }
    }

    private DrmUtil() {
    }
}
