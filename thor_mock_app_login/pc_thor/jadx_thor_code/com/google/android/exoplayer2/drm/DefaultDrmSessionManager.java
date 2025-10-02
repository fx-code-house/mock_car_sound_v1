package com.google.android.exoplayer2.drm;

import android.media.ResourceBusyException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.drm.DefaultDrmSession;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes.dex */
public class DefaultDrmSessionManager implements DrmSessionManager {
    public static final long DEFAULT_SESSION_KEEPALIVE_MS = 300000;
    public static final int INITIAL_DRM_REQUEST_RETRY_COUNT = 3;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    private static final String TAG = "DefaultDrmSessionMgr";
    private final MediaDrmCallback callback;
    private ExoMediaDrm exoMediaDrm;
    private final ExoMediaDrm.Provider exoMediaDrmProvider;
    private final Set<DefaultDrmSession> keepaliveSessions;
    private final HashMap<String, String> keyRequestParameters;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    volatile MediaDrmHandler mediaDrmHandler;
    private int mode;
    private final boolean multiSession;
    private DefaultDrmSession noMultiSessionDrmSession;
    private byte[] offlineLicenseKeySetId;
    private DefaultDrmSession placeholderDrmSession;
    private final boolean playClearSamplesWithoutKeys;
    private Handler playbackHandler;
    private Looper playbackLooper;
    private final Set<PreacquiredSessionReference> preacquiredSessionReferences;
    private int prepareCallsCount;
    private final ProvisioningManagerImpl provisioningManagerImpl;
    private final ReferenceCountListenerImpl referenceCountListener;
    private final long sessionKeepaliveMs;
    private final List<DefaultDrmSession> sessions;
    private final int[] useDrmSessionsForClearContentTrackTypes;
    private final UUID uuid;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static final class Builder {
        private boolean multiSession;
        private boolean playClearSamplesWithoutKeys;
        private final HashMap<String, String> keyRequestParameters = new HashMap<>();
        private UUID uuid = C.WIDEVINE_UUID;
        private ExoMediaDrm.Provider exoMediaDrmProvider = FrameworkMediaDrm.DEFAULT_PROVIDER;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        private int[] useDrmSessionsForClearContentTrackTypes = new int[0];
        private long sessionKeepaliveMs = 300000;

        public Builder setKeyRequestParameters(Map<String, String> keyRequestParameters) {
            this.keyRequestParameters.clear();
            if (keyRequestParameters != null) {
                this.keyRequestParameters.putAll(keyRequestParameters);
            }
            return this;
        }

        public Builder setUuidAndExoMediaDrmProvider(UUID uuid, ExoMediaDrm.Provider exoMediaDrmProvider) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid);
            this.exoMediaDrmProvider = (ExoMediaDrm.Provider) Assertions.checkNotNull(exoMediaDrmProvider);
            return this;
        }

        public Builder setMultiSession(boolean multiSession) {
            this.multiSession = multiSession;
            return this;
        }

        public Builder setUseDrmSessionsForClearContent(int... useDrmSessionsForClearContentTrackTypes) {
            for (int i : useDrmSessionsForClearContentTrackTypes) {
                boolean z = true;
                if (i != 2 && i != 1) {
                    z = false;
                }
                Assertions.checkArgument(z);
            }
            this.useDrmSessionsForClearContentTrackTypes = (int[]) useDrmSessionsForClearContentTrackTypes.clone();
            return this;
        }

        public Builder setPlayClearSamplesWithoutKeys(boolean playClearSamplesWithoutKeys) {
            this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys;
            return this;
        }

        public Builder setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy);
            return this;
        }

        public Builder setSessionKeepaliveMs(long sessionKeepaliveMs) {
            Assertions.checkArgument(sessionKeepaliveMs > 0 || sessionKeepaliveMs == C.TIME_UNSET);
            this.sessionKeepaliveMs = sessionKeepaliveMs;
            return this;
        }

        public DefaultDrmSessionManager build(MediaDrmCallback mediaDrmCallback) {
            return new DefaultDrmSessionManager(this.uuid, this.exoMediaDrmProvider, mediaDrmCallback, this.keyRequestParameters, this.multiSession, this.useDrmSessionsForClearContentTrackTypes, this.playClearSamplesWithoutKeys, this.loadErrorHandlingPolicy, this.sessionKeepaliveMs);
        }
    }

    public static final class MissingSchemeDataException extends Exception {
        /* JADX WARN: Illegal instructions before constructor call */
        private MissingSchemeDataException(UUID uuid) {
            String strValueOf = String.valueOf(uuid);
            super(new StringBuilder(String.valueOf(strValueOf).length() + 29).append("Media does not support uuid: ").append(strValueOf).toString());
        }
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback callback, HashMap<String, String> keyRequestParameters) {
        this(uuid, exoMediaDrm, callback, keyRequestParameters == null ? new HashMap<>() : keyRequestParameters, false, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback callback, HashMap<String, String> keyRequestParameters, boolean multiSession) {
        this(uuid, exoMediaDrm, callback, keyRequestParameters == null ? new HashMap<>() : keyRequestParameters, multiSession, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback callback, HashMap<String, String> keyRequestParameters, boolean multiSession, int initialDrmRequestRetryCount) {
        this(uuid, new ExoMediaDrm.AppManagedProvider(exoMediaDrm), callback, keyRequestParameters == null ? new HashMap<>() : keyRequestParameters, multiSession, new int[0], false, new DefaultLoadErrorHandlingPolicy(initialDrmRequestRetryCount), 300000L);
    }

    private DefaultDrmSessionManager(UUID uuid, ExoMediaDrm.Provider exoMediaDrmProvider, MediaDrmCallback callback, HashMap<String, String> keyRequestParameters, boolean multiSession, int[] useDrmSessionsForClearContentTrackTypes, boolean playClearSamplesWithoutKeys, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long sessionKeepaliveMs) {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(!C.COMMON_PSSH_UUID.equals(uuid), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid;
        this.exoMediaDrmProvider = exoMediaDrmProvider;
        this.callback = callback;
        this.keyRequestParameters = keyRequestParameters;
        this.multiSession = multiSession;
        this.useDrmSessionsForClearContentTrackTypes = useDrmSessionsForClearContentTrackTypes;
        this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.provisioningManagerImpl = new ProvisioningManagerImpl(this);
        this.referenceCountListener = new ReferenceCountListenerImpl();
        this.mode = 0;
        this.sessions = new ArrayList();
        this.preacquiredSessionReferences = Sets.newIdentityHashSet();
        this.keepaliveSessions = Sets.newIdentityHashSet();
        this.sessionKeepaliveMs = sessionKeepaliveMs;
    }

    public void setMode(int mode, byte[] offlineLicenseKeySetId) {
        Assertions.checkState(this.sessions.isEmpty());
        if (mode == 1 || mode == 3) {
            Assertions.checkNotNull(offlineLicenseKeySetId);
        }
        this.mode = mode;
        this.offlineLicenseKeySetId = offlineLicenseKeySetId;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public final void prepare() {
        int i = this.prepareCallsCount;
        this.prepareCallsCount = i + 1;
        if (i != 0) {
            return;
        }
        if (this.exoMediaDrm == null) {
            ExoMediaDrm exoMediaDrmAcquireExoMediaDrm = this.exoMediaDrmProvider.acquireExoMediaDrm(this.uuid);
            this.exoMediaDrm = exoMediaDrmAcquireExoMediaDrm;
            exoMediaDrmAcquireExoMediaDrm.setOnEventListener(new MediaDrmEventListener());
        } else if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            for (int i2 = 0; i2 < this.sessions.size(); i2++) {
                this.sessions.get(i2).acquire(null);
            }
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public final void release() {
        int i = this.prepareCallsCount - 1;
        this.prepareCallsCount = i;
        if (i != 0) {
            return;
        }
        if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            ArrayList arrayList = new ArrayList(this.sessions);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ((DefaultDrmSession) arrayList.get(i2)).release(null);
            }
        }
        releaseAllPreacquiredSessions();
        maybeReleaseMediaDrm();
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public DrmSessionManager.DrmSessionReference preacquireSession(Looper playbackLooper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        Assertions.checkState(this.prepareCallsCount > 0);
        initPlaybackLooper(playbackLooper);
        PreacquiredSessionReference preacquiredSessionReference = new PreacquiredSessionReference(eventDispatcher);
        preacquiredSessionReference.acquire(format);
        return preacquiredSessionReference;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public DrmSession acquireSession(Looper playbackLooper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        Assertions.checkState(this.prepareCallsCount > 0);
        initPlaybackLooper(playbackLooper);
        return acquireSession(playbackLooper, eventDispatcher, format, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public DrmSession acquireSession(Looper looper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format, boolean z) {
        List<DrmInitData.SchemeData> schemeDatas;
        maybeCreateMediaDrmHandler(looper);
        if (format.drmInitData == null) {
            return maybeAcquirePlaceholderSession(MimeTypes.getTrackType(format.sampleMimeType), z);
        }
        DefaultDrmSession defaultDrmSessionCreateAndAcquireSessionWithRetry = null;
        Object[] objArr = 0;
        if (this.offlineLicenseKeySetId == null) {
            schemeDatas = getSchemeDatas((DrmInitData) Assertions.checkNotNull(format.drmInitData), this.uuid, false);
            if (schemeDatas.isEmpty()) {
                MissingSchemeDataException missingSchemeDataException = new MissingSchemeDataException(this.uuid);
                Log.e(TAG, "DRM error", missingSchemeDataException);
                if (eventDispatcher != null) {
                    eventDispatcher.drmSessionManagerError(missingSchemeDataException);
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(missingSchemeDataException, PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR));
            }
        } else {
            schemeDatas = null;
        }
        if (!this.multiSession) {
            defaultDrmSessionCreateAndAcquireSessionWithRetry = this.noMultiSessionDrmSession;
        } else {
            Iterator<DefaultDrmSession> it = this.sessions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DefaultDrmSession next = it.next();
                if (Util.areEqual(next.schemeDatas, schemeDatas)) {
                    defaultDrmSessionCreateAndAcquireSessionWithRetry = next;
                    break;
                }
            }
        }
        if (defaultDrmSessionCreateAndAcquireSessionWithRetry == null) {
            defaultDrmSessionCreateAndAcquireSessionWithRetry = createAndAcquireSessionWithRetry(schemeDatas, false, eventDispatcher, z);
            if (!this.multiSession) {
                this.noMultiSessionDrmSession = defaultDrmSessionCreateAndAcquireSessionWithRetry;
            }
            this.sessions.add(defaultDrmSessionCreateAndAcquireSessionWithRetry);
        } else {
            defaultDrmSessionCreateAndAcquireSessionWithRetry.acquire(eventDispatcher);
        }
        return defaultDrmSessionCreateAndAcquireSessionWithRetry;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format) {
        Class<? extends ExoMediaCrypto> exoMediaCryptoType = ((ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm)).getExoMediaCryptoType();
        if (format.drmInitData != null) {
            return canAcquireSession(format.drmInitData) ? exoMediaCryptoType : UnsupportedMediaCrypto.class;
        }
        if (Util.linearSearch(this.useDrmSessionsForClearContentTrackTypes, MimeTypes.getTrackType(format.sampleMimeType)) != -1) {
            return exoMediaCryptoType;
        }
        return null;
    }

    private DrmSession maybeAcquirePlaceholderSession(int trackType, boolean shouldReleasePreacquiredSessionsBeforeRetrying) {
        ExoMediaDrm exoMediaDrm = (ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm);
        if ((FrameworkMediaCrypto.class.equals(exoMediaDrm.getExoMediaCryptoType()) && FrameworkMediaCrypto.WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC) || Util.linearSearch(this.useDrmSessionsForClearContentTrackTypes, trackType) == -1 || UnsupportedMediaCrypto.class.equals(exoMediaDrm.getExoMediaCryptoType())) {
            return null;
        }
        DefaultDrmSession defaultDrmSession = this.placeholderDrmSession;
        if (defaultDrmSession == null) {
            DefaultDrmSession defaultDrmSessionCreateAndAcquireSessionWithRetry = createAndAcquireSessionWithRetry(ImmutableList.of(), true, null, shouldReleasePreacquiredSessionsBeforeRetrying);
            this.sessions.add(defaultDrmSessionCreateAndAcquireSessionWithRetry);
            this.placeholderDrmSession = defaultDrmSessionCreateAndAcquireSessionWithRetry;
        } else {
            defaultDrmSession.acquire(null);
        }
        return this.placeholderDrmSession;
    }

    private boolean canAcquireSession(DrmInitData drmInitData) {
        if (this.offlineLicenseKeySetId != null) {
            return true;
        }
        if (getSchemeDatas(drmInitData, this.uuid, true).isEmpty()) {
            if (drmInitData.schemeDataCount != 1 || !drmInitData.get(0).matches(C.COMMON_PSSH_UUID)) {
                return false;
            }
            String strValueOf = String.valueOf(this.uuid);
            Log.w(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 72).append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ").append(strValueOf).toString());
        }
        String str = drmInitData.schemeType;
        if (str == null || C.CENC_TYPE_cenc.equals(str)) {
            return true;
        }
        return C.CENC_TYPE_cbcs.equals(str) ? Util.SDK_INT >= 25 : (C.CENC_TYPE_cbc1.equals(str) || C.CENC_TYPE_cens.equals(str)) ? false : true;
    }

    @EnsuresNonNull({"this.playbackLooper", "this.playbackHandler"})
    private synchronized void initPlaybackLooper(Looper playbackLooper) {
        Looper looper = this.playbackLooper;
        if (looper == null) {
            this.playbackLooper = playbackLooper;
            this.playbackHandler = new Handler(playbackLooper);
        } else {
            Assertions.checkState(looper == playbackLooper);
            Assertions.checkNotNull(this.playbackHandler);
        }
    }

    private void maybeCreateMediaDrmHandler(Looper playbackLooper) {
        if (this.mediaDrmHandler == null) {
            this.mediaDrmHandler = new MediaDrmHandler(playbackLooper);
        }
    }

    private DefaultDrmSession createAndAcquireSessionWithRetry(List<DrmInitData.SchemeData> schemeDatas, boolean isPlaceholderSession, DrmSessionEventListener.EventDispatcher eventDispatcher, boolean shouldReleasePreacquiredSessionsBeforeRetrying) {
        DefaultDrmSession defaultDrmSessionCreateAndAcquireSession = createAndAcquireSession(schemeDatas, isPlaceholderSession, eventDispatcher);
        if (acquisitionFailedIndicatingResourceShortage(defaultDrmSessionCreateAndAcquireSession) && !this.keepaliveSessions.isEmpty()) {
            UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.keepaliveSessions).iterator();
            while (it.hasNext()) {
                ((DrmSession) it.next()).release(null);
            }
            undoAcquisition(defaultDrmSessionCreateAndAcquireSession, eventDispatcher);
            defaultDrmSessionCreateAndAcquireSession = createAndAcquireSession(schemeDatas, isPlaceholderSession, eventDispatcher);
        }
        if (!acquisitionFailedIndicatingResourceShortage(defaultDrmSessionCreateAndAcquireSession) || !shouldReleasePreacquiredSessionsBeforeRetrying || this.preacquiredSessionReferences.isEmpty()) {
            return defaultDrmSessionCreateAndAcquireSession;
        }
        releaseAllPreacquiredSessions();
        undoAcquisition(defaultDrmSessionCreateAndAcquireSession, eventDispatcher);
        return createAndAcquireSession(schemeDatas, isPlaceholderSession, eventDispatcher);
    }

    private static boolean acquisitionFailedIndicatingResourceShortage(DrmSession session) {
        return session.getState() == 1 && (Util.SDK_INT < 19 || (((DrmSession.DrmSessionException) Assertions.checkNotNull(session.getError())).getCause() instanceof ResourceBusyException));
    }

    private void undoAcquisition(DrmSession session, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        session.release(eventDispatcher);
        if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            session.release(null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void releaseAllPreacquiredSessions() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.preacquiredSessionReferences).iterator();
        while (it.hasNext()) {
            ((PreacquiredSessionReference) it.next()).release();
        }
    }

    private DefaultDrmSession createAndAcquireSession(List<DrmInitData.SchemeData> schemeDatas, boolean isPlaceholderSession, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        Assertions.checkNotNull(this.exoMediaDrm);
        DefaultDrmSession defaultDrmSession = new DefaultDrmSession(this.uuid, this.exoMediaDrm, this.provisioningManagerImpl, this.referenceCountListener, schemeDatas, this.mode, this.playClearSamplesWithoutKeys | isPlaceholderSession, isPlaceholderSession, this.offlineLicenseKeySetId, this.keyRequestParameters, this.callback, (Looper) Assertions.checkNotNull(this.playbackLooper), this.loadErrorHandlingPolicy);
        defaultDrmSession.acquire(eventDispatcher);
        if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            defaultDrmSession.acquire(null);
        }
        return defaultDrmSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeReleaseMediaDrm() {
        if (this.exoMediaDrm != null && this.prepareCallsCount == 0 && this.sessions.isEmpty() && this.preacquiredSessionReferences.isEmpty()) {
            ((ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm)).release();
            this.exoMediaDrm = null;
        }
    }

    private static List<DrmInitData.SchemeData> getSchemeDatas(DrmInitData drmInitData, UUID uuid, boolean allowMissingData) {
        ArrayList arrayList = new ArrayList(drmInitData.schemeDataCount);
        for (int i = 0; i < drmInitData.schemeDataCount; i++) {
            DrmInitData.SchemeData schemeData = drmInitData.get(i);
            if ((schemeData.matches(uuid) || (C.CLEARKEY_UUID.equals(uuid) && schemeData.matches(C.COMMON_PSSH_UUID))) && (schemeData.data != null || allowMissingData)) {
                arrayList.add(schemeData);
            }
        }
        return arrayList;
    }

    private class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            byte[] bArr = (byte[]) msg.obj;
            if (bArr == null) {
                return;
            }
            for (DefaultDrmSession defaultDrmSession : DefaultDrmSessionManager.this.sessions) {
                if (defaultDrmSession.hasSessionId(bArr)) {
                    defaultDrmSession.onMediaDrmEvent(msg.what);
                    return;
                }
            }
        }
    }

    private class ProvisioningManagerImpl implements DefaultDrmSession.ProvisioningManager {
        private DefaultDrmSession provisioningSession;
        private final Set<DefaultDrmSession> sessionsAwaitingProvisioning = new HashSet();

        public ProvisioningManagerImpl(final DefaultDrmSessionManager this$0) {
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void provisionRequired(DefaultDrmSession session) {
            this.sessionsAwaitingProvisioning.add(session);
            if (this.provisioningSession != null) {
                return;
            }
            this.provisioningSession = session;
            session.provision();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void onProvisionCompleted() {
            this.provisioningSession = null;
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.sessionsAwaitingProvisioning);
            this.sessionsAwaitingProvisioning.clear();
            UnmodifiableIterator it = immutableListCopyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).onProvisionCompleted();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void onProvisionError(Exception error, boolean thrownByExoMediaDrm) {
            this.provisioningSession = null;
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.sessionsAwaitingProvisioning);
            this.sessionsAwaitingProvisioning.clear();
            UnmodifiableIterator it = immutableListCopyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).onProvisionError(error, thrownByExoMediaDrm);
            }
        }

        public void onSessionFullyReleased(DefaultDrmSession session) {
            this.sessionsAwaitingProvisioning.remove(session);
            if (this.provisioningSession == session) {
                this.provisioningSession = null;
                if (this.sessionsAwaitingProvisioning.isEmpty()) {
                    return;
                }
                DefaultDrmSession next = this.sessionsAwaitingProvisioning.iterator().next();
                this.provisioningSession = next;
                next.provision();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ReferenceCountListenerImpl implements DefaultDrmSession.ReferenceCountListener {
        private ReferenceCountListenerImpl() {
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ReferenceCountListener
        public void onReferenceCountIncremented(DefaultDrmSession session, int newReferenceCount) {
            if (DefaultDrmSessionManager.this.sessionKeepaliveMs != C.TIME_UNSET) {
                DefaultDrmSessionManager.this.keepaliveSessions.remove(session);
                ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).removeCallbacksAndMessages(session);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ReferenceCountListener
        public void onReferenceCountDecremented(final DefaultDrmSession session, int newReferenceCount) {
            if (newReferenceCount == 1 && DefaultDrmSessionManager.this.prepareCallsCount > 0 && DefaultDrmSessionManager.this.sessionKeepaliveMs != C.TIME_UNSET) {
                DefaultDrmSessionManager.this.keepaliveSessions.add(session);
                ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).postAtTime(new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionManager$ReferenceCountListenerImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        session.release(null);
                    }
                }, session, SystemClock.uptimeMillis() + DefaultDrmSessionManager.this.sessionKeepaliveMs);
            } else if (newReferenceCount == 0) {
                DefaultDrmSessionManager.this.sessions.remove(session);
                if (DefaultDrmSessionManager.this.placeholderDrmSession == session) {
                    DefaultDrmSessionManager.this.placeholderDrmSession = null;
                }
                if (DefaultDrmSessionManager.this.noMultiSessionDrmSession == session) {
                    DefaultDrmSessionManager.this.noMultiSessionDrmSession = null;
                }
                DefaultDrmSessionManager.this.provisioningManagerImpl.onSessionFullyReleased(session);
                if (DefaultDrmSessionManager.this.sessionKeepaliveMs != C.TIME_UNSET) {
                    ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).removeCallbacksAndMessages(session);
                    DefaultDrmSessionManager.this.keepaliveSessions.remove(session);
                }
            }
            DefaultDrmSessionManager.this.maybeReleaseMediaDrm();
        }
    }

    private class MediaDrmEventListener implements ExoMediaDrm.OnEventListener {
        private MediaDrmEventListener() {
        }

        @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener
        public void onEvent(ExoMediaDrm md, byte[] sessionId, int event, int extra, byte[] data) {
            ((MediaDrmHandler) Assertions.checkNotNull(DefaultDrmSessionManager.this.mediaDrmHandler)).obtainMessage(event, sessionId).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PreacquiredSessionReference implements DrmSessionManager.DrmSessionReference {
        private final DrmSessionEventListener.EventDispatcher eventDispatcher;
        private boolean isReleased;
        private DrmSession session;

        public PreacquiredSessionReference(DrmSessionEventListener.EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        public void acquire(final Format format) {
            ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).post(new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionManager$PreacquiredSessionReference$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m150xe3bb136(format);
                }
            });
        }

        /* renamed from: lambda$acquire$0$com-google-android-exoplayer2-drm-DefaultDrmSessionManager$PreacquiredSessionReference, reason: not valid java name */
        /* synthetic */ void m150xe3bb136(Format format) {
            if (DefaultDrmSessionManager.this.prepareCallsCount == 0 || this.isReleased) {
                return;
            }
            DefaultDrmSessionManager defaultDrmSessionManager = DefaultDrmSessionManager.this;
            this.session = defaultDrmSessionManager.acquireSession((Looper) Assertions.checkNotNull(defaultDrmSessionManager.playbackLooper), this.eventDispatcher, format, false);
            DefaultDrmSessionManager.this.preacquiredSessionReferences.add(this);
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionManager.DrmSessionReference
        public void release() {
            Util.postOrRun((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler), new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionManager$PreacquiredSessionReference$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m151xd40d204();
                }
            });
        }

        /* renamed from: lambda$release$1$com-google-android-exoplayer2-drm-DefaultDrmSessionManager$PreacquiredSessionReference, reason: not valid java name */
        /* synthetic */ void m151xd40d204() {
            if (this.isReleased) {
                return;
            }
            DrmSession drmSession = this.session;
            if (drmSession != null) {
                drmSession.release(this.eventDispatcher);
            }
            DefaultDrmSessionManager.this.preacquiredSessionReferences.remove(this);
            this.isReleased = true;
        }
    }
}
