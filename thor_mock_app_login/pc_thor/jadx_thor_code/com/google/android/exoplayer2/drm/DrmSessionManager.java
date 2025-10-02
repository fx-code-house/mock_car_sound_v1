package com.google.android.exoplayer2.drm;

import android.os.Looper;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;

/* loaded from: classes.dex */
public interface DrmSessionManager {
    public static final DrmSessionManager DRM_UNSUPPORTED;

    @Deprecated
    public static final DrmSessionManager DUMMY;

    public interface DrmSessionReference {
        public static final DrmSessionReference EMPTY = new DrmSessionReference() { // from class: com.google.android.exoplayer2.drm.DrmSessionManager$DrmSessionReference$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.drm.DrmSessionManager.DrmSessionReference
            public final void release() {
                DrmSessionManager.DrmSessionReference.lambda$static$0();
            }
        };

        static /* synthetic */ void lambda$static$0() {
        }

        void release();
    }

    DrmSession acquireSession(Looper playbackLooper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format);

    default void prepare() {
    }

    default void release() {
    }

    static {
        DrmSessionManager drmSessionManager = new DrmSessionManager() { // from class: com.google.android.exoplayer2.drm.DrmSessionManager.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public DrmSession acquireSession(Looper playbackLooper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
                if (format.drmInitData == null) {
                    return null;
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(new UnsupportedDrmException(1), PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED));
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public Class<UnsupportedMediaCrypto> getExoMediaCryptoType(Format format) {
                if (format.drmInitData != null) {
                    return UnsupportedMediaCrypto.class;
                }
                return null;
            }
        };
        DRM_UNSUPPORTED = drmSessionManager;
        DUMMY = drmSessionManager;
    }

    @Deprecated
    static DrmSessionManager getDummyDrmSessionManager() {
        return DRM_UNSUPPORTED;
    }

    default DrmSessionReference preacquireSession(Looper playbackLooper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        return DrmSessionReference.EMPTY;
    }
}
