package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrm;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.media.UnsupportedSchemeException;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;

/* loaded from: classes.dex */
public final class FrameworkMediaDrm implements ExoMediaDrm {
    private static final String CENC_SCHEME_MIME_TYPE = "cenc";
    public static final ExoMediaDrm.Provider DEFAULT_PROVIDER = new ExoMediaDrm.Provider() { // from class: com.google.android.exoplayer2.drm.FrameworkMediaDrm$$ExternalSyntheticLambda2
        @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.Provider
        public final ExoMediaDrm acquireExoMediaDrm(UUID uuid) {
            return FrameworkMediaDrm.lambda$static$0(uuid);
        }
    };
    private static final String MOCK_LA_URL = "<LA_URL>https://x</LA_URL>";
    private static final String MOCK_LA_URL_VALUE = "https://x";
    private static final String TAG = "FrameworkMediaDrm";
    private static final int UTF_16_BYTES_PER_CHARACTER = 2;
    private final MediaDrm mediaDrm;
    private int referenceCount;
    private final UUID uuid;

    static /* synthetic */ ExoMediaDrm lambda$static$0(UUID uuid) {
        try {
            return newInstance(uuid);
        } catch (UnsupportedDrmException unused) {
            String strValueOf = String.valueOf(uuid);
            Log.e(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 53).append("Failed to instantiate a FrameworkMediaDrm for uuid: ").append(strValueOf).append(".").toString());
            return new DummyExoMediaDrm();
        }
    }

    public static boolean isCryptoSchemeSupported(UUID uuid) {
        return MediaDrm.isCryptoSchemeSupported(adjustUuid(uuid));
    }

    public static FrameworkMediaDrm newInstance(UUID uuid) throws UnsupportedDrmException {
        try {
            return new FrameworkMediaDrm(uuid);
        } catch (UnsupportedSchemeException e) {
            throw new UnsupportedDrmException(1, e);
        } catch (Exception e2) {
            throw new UnsupportedDrmException(2, e2);
        }
    }

    private FrameworkMediaDrm(UUID uuid) throws UnsupportedSchemeException {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(!C.COMMON_PSSH_UUID.equals(uuid), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid;
        MediaDrm mediaDrm = new MediaDrm(adjustUuid(uuid));
        this.mediaDrm = mediaDrm;
        this.referenceCount = 1;
        if (C.WIDEVINE_UUID.equals(uuid) && needsForceWidevineL3Workaround()) {
            forceWidevineL3(mediaDrm);
        }
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void setOnEventListener(final ExoMediaDrm.OnEventListener listener) {
        this.mediaDrm.setOnEventListener(listener == null ? null : new MediaDrm.OnEventListener() { // from class: com.google.android.exoplayer2.drm.FrameworkMediaDrm$$ExternalSyntheticLambda1
            @Override // android.media.MediaDrm.OnEventListener
            public final void onEvent(MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
                this.f$0.m158x6bb92dcc(listener, mediaDrm, bArr, i, i2, bArr2);
            }
        });
    }

    /* renamed from: lambda$setOnEventListener$1$com-google-android-exoplayer2-drm-FrameworkMediaDrm, reason: not valid java name */
    /* synthetic */ void m158x6bb92dcc(ExoMediaDrm.OnEventListener onEventListener, MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
        onEventListener.onEvent(this, bArr, i, i2, bArr2);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void setOnKeyStatusChangeListener(final ExoMediaDrm.OnKeyStatusChangeListener listener) {
        if (Util.SDK_INT < 23) {
            throw new UnsupportedOperationException();
        }
        this.mediaDrm.setOnKeyStatusChangeListener(listener == null ? null : new MediaDrm.OnKeyStatusChangeListener() { // from class: com.google.android.exoplayer2.drm.FrameworkMediaDrm$$ExternalSyntheticLambda3
            @Override // android.media.MediaDrm.OnKeyStatusChangeListener
            public final void onKeyStatusChange(MediaDrm mediaDrm, byte[] bArr, List list, boolean z) {
                this.f$0.m160x8be3cdb4(listener, mediaDrm, bArr, list, z);
            }
        }, (Handler) null);
    }

    /* renamed from: lambda$setOnKeyStatusChangeListener$2$com-google-android-exoplayer2-drm-FrameworkMediaDrm, reason: not valid java name */
    /* synthetic */ void m160x8be3cdb4(ExoMediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener, MediaDrm mediaDrm, byte[] bArr, List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaDrm.KeyStatus keyStatus = (MediaDrm.KeyStatus) it.next();
            arrayList.add(new ExoMediaDrm.KeyStatus(keyStatus.getStatusCode(), keyStatus.getKeyId()));
        }
        onKeyStatusChangeListener.onKeyStatusChange(this, bArr, arrayList, z);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void setOnExpirationUpdateListener(final ExoMediaDrm.OnExpirationUpdateListener listener) {
        if (Util.SDK_INT < 23) {
            throw new UnsupportedOperationException();
        }
        this.mediaDrm.setOnExpirationUpdateListener(listener == null ? null : new MediaDrm.OnExpirationUpdateListener() { // from class: com.google.android.exoplayer2.drm.FrameworkMediaDrm$$ExternalSyntheticLambda0
            @Override // android.media.MediaDrm.OnExpirationUpdateListener
            public final void onExpirationUpdate(MediaDrm mediaDrm, byte[] bArr, long j) {
                this.f$0.m159x78401754(listener, mediaDrm, bArr, j);
            }
        }, (Handler) null);
    }

    /* renamed from: lambda$setOnExpirationUpdateListener$3$com-google-android-exoplayer2-drm-FrameworkMediaDrm, reason: not valid java name */
    /* synthetic */ void m159x78401754(ExoMediaDrm.OnExpirationUpdateListener onExpirationUpdateListener, MediaDrm mediaDrm, byte[] bArr, long j) {
        onExpirationUpdateListener.onExpirationUpdate(this, bArr, j);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public byte[] openSession() throws MediaDrmException {
        return this.mediaDrm.openSession();
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void closeSession(byte[] sessionId) {
        this.mediaDrm.closeSession(sessionId);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public ExoMediaDrm.KeyRequest getKeyRequest(byte[] scope, List<DrmInitData.SchemeData> schemeDatas, int keyType, HashMap<String, String> optionalParameters) throws NotProvisionedException {
        DrmInitData.SchemeData schemeData;
        byte[] bArrAdjustRequestInitData;
        String strAdjustRequestMimeType;
        if (schemeDatas != null) {
            schemeData = getSchemeData(this.uuid, schemeDatas);
            bArrAdjustRequestInitData = adjustRequestInitData(this.uuid, (byte[]) Assertions.checkNotNull(schemeData.data));
            strAdjustRequestMimeType = adjustRequestMimeType(this.uuid, schemeData.mimeType);
        } else {
            schemeData = null;
            bArrAdjustRequestInitData = null;
            strAdjustRequestMimeType = null;
        }
        MediaDrm.KeyRequest keyRequest = this.mediaDrm.getKeyRequest(scope, bArrAdjustRequestInitData, strAdjustRequestMimeType, keyType, optionalParameters);
        byte[] bArrAdjustRequestData = adjustRequestData(this.uuid, keyRequest.getData());
        String defaultUrl = keyRequest.getDefaultUrl();
        if (MOCK_LA_URL_VALUE.equals(defaultUrl)) {
            defaultUrl = "";
        }
        if (TextUtils.isEmpty(defaultUrl) && schemeData != null && !TextUtils.isEmpty(schemeData.licenseServerUrl)) {
            defaultUrl = schemeData.licenseServerUrl;
        }
        return new ExoMediaDrm.KeyRequest(bArrAdjustRequestData, defaultUrl, Util.SDK_INT >= 23 ? keyRequest.getRequestType() : Integer.MIN_VALUE);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public byte[] provideKeyResponse(byte[] scope, byte[] response) throws JSONException, DeniedByServerException, NotProvisionedException {
        if (C.CLEARKEY_UUID.equals(this.uuid)) {
            response = ClearKeyUtil.adjustResponseData(response);
        }
        return this.mediaDrm.provideKeyResponse(scope, response);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public ExoMediaDrm.ProvisionRequest getProvisionRequest() {
        MediaDrm.ProvisionRequest provisionRequest = this.mediaDrm.getProvisionRequest();
        return new ExoMediaDrm.ProvisionRequest(provisionRequest.getData(), provisionRequest.getDefaultUrl());
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void provideProvisionResponse(byte[] response) throws DeniedByServerException {
        this.mediaDrm.provideProvisionResponse(response);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public Map<String, String> queryKeyStatus(byte[] sessionId) {
        return this.mediaDrm.queryKeyStatus(sessionId);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public synchronized void acquire() {
        Assertions.checkState(this.referenceCount > 0);
        this.referenceCount++;
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public synchronized void release() {
        int i = this.referenceCount - 1;
        this.referenceCount = i;
        if (i == 0) {
            this.mediaDrm.release();
        }
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void restoreKeys(byte[] sessionId, byte[] keySetId) {
        this.mediaDrm.restoreKeys(sessionId, keySetId);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public PersistableBundle getMetrics() {
        if (Util.SDK_INT < 28) {
            return null;
        }
        return this.mediaDrm.getMetrics();
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public String getPropertyString(String propertyName) {
        return this.mediaDrm.getPropertyString(propertyName);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public byte[] getPropertyByteArray(String propertyName) {
        return this.mediaDrm.getPropertyByteArray(propertyName);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void setPropertyString(String propertyName, String value) {
        this.mediaDrm.setPropertyString(propertyName, value);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public void setPropertyByteArray(String propertyName, byte[] value) {
        this.mediaDrm.setPropertyByteArray(propertyName, value);
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public FrameworkMediaCrypto createMediaCrypto(byte[] sessionId) throws MediaCryptoException {
        return new FrameworkMediaCrypto(adjustUuid(this.uuid), sessionId, Util.SDK_INT < 21 && C.WIDEVINE_UUID.equals(this.uuid) && "L3".equals(getPropertyString("securityLevel")));
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm
    public Class<FrameworkMediaCrypto> getExoMediaCryptoType() {
        return FrameworkMediaCrypto.class;
    }

    private static DrmInitData.SchemeData getSchemeData(UUID uuid, List<DrmInitData.SchemeData> schemeDatas) {
        boolean z;
        if (!C.WIDEVINE_UUID.equals(uuid)) {
            return schemeDatas.get(0);
        }
        if (Util.SDK_INT >= 28 && schemeDatas.size() > 1) {
            DrmInitData.SchemeData schemeData = schemeDatas.get(0);
            int length = 0;
            for (int i = 0; i < schemeDatas.size(); i++) {
                DrmInitData.SchemeData schemeData2 = schemeDatas.get(i);
                byte[] bArr = (byte[]) Assertions.checkNotNull(schemeData2.data);
                if (!Util.areEqual(schemeData2.mimeType, schemeData.mimeType) || !Util.areEqual(schemeData2.licenseServerUrl, schemeData.licenseServerUrl) || !PsshAtomUtil.isPsshAtom(bArr)) {
                    z = false;
                    break;
                }
                length += bArr.length;
            }
            z = true;
            if (z) {
                byte[] bArr2 = new byte[length];
                int i2 = 0;
                for (int i3 = 0; i3 < schemeDatas.size(); i3++) {
                    byte[] bArr3 = (byte[]) Assertions.checkNotNull(schemeDatas.get(i3).data);
                    int length2 = bArr3.length;
                    System.arraycopy(bArr3, 0, bArr2, i2, length2);
                    i2 += length2;
                }
                return schemeData.copyWithData(bArr2);
            }
        }
        for (int i4 = 0; i4 < schemeDatas.size(); i4++) {
            DrmInitData.SchemeData schemeData3 = schemeDatas.get(i4);
            int version = PsshAtomUtil.parseVersion((byte[]) Assertions.checkNotNull(schemeData3.data));
            if (Util.SDK_INT < 23 && version == 0) {
                return schemeData3;
            }
            if (Util.SDK_INT >= 23 && version == 1) {
                return schemeData3;
            }
        }
        return schemeDatas.get(0);
    }

    private static UUID adjustUuid(UUID uuid) {
        return (Util.SDK_INT >= 27 || !C.CLEARKEY_UUID.equals(uuid)) ? uuid : C.COMMON_PSSH_UUID;
    }

    private static byte[] adjustRequestInitData(UUID uuid, byte[] initData) {
        byte[] schemeSpecificData;
        if (C.PLAYREADY_UUID.equals(uuid)) {
            byte[] schemeSpecificData2 = PsshAtomUtil.parseSchemeSpecificData(initData, uuid);
            if (schemeSpecificData2 != null) {
                initData = schemeSpecificData2;
            }
            initData = PsshAtomUtil.buildPsshAtom(C.PLAYREADY_UUID, addLaUrlAttributeIfMissing(initData));
        }
        return (((Util.SDK_INT >= 23 || !C.WIDEVINE_UUID.equals(uuid)) && !(C.PLAYREADY_UUID.equals(uuid) && "Amazon".equals(Util.MANUFACTURER) && ("AFTB".equals(Util.MODEL) || "AFTS".equals(Util.MODEL) || "AFTM".equals(Util.MODEL) || "AFTT".equals(Util.MODEL)))) || (schemeSpecificData = PsshAtomUtil.parseSchemeSpecificData(initData, uuid)) == null) ? initData : schemeSpecificData;
    }

    private static String adjustRequestMimeType(UUID uuid, String mimeType) {
        return (Util.SDK_INT < 26 && C.CLEARKEY_UUID.equals(uuid) && (MimeTypes.VIDEO_MP4.equals(mimeType) || MimeTypes.AUDIO_MP4.equals(mimeType))) ? "cenc" : mimeType;
    }

    private static byte[] adjustRequestData(UUID uuid, byte[] requestData) {
        return C.CLEARKEY_UUID.equals(uuid) ? ClearKeyUtil.adjustRequestData(requestData) : requestData;
    }

    private static void forceWidevineL3(MediaDrm mediaDrm) {
        mediaDrm.setPropertyString("securityLevel", "L3");
    }

    private static boolean needsForceWidevineL3Workaround() {
        return "ASUS_Z00AD".equals(Util.MODEL);
    }

    private static byte[] addLaUrlAttributeIfMissing(byte[] data) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(data);
        int littleEndianInt = parsableByteArray.readLittleEndianInt();
        short littleEndianShort = parsableByteArray.readLittleEndianShort();
        short littleEndianShort2 = parsableByteArray.readLittleEndianShort();
        if (littleEndianShort != 1 || littleEndianShort2 != 1) {
            Log.i(TAG, "Unexpected record count or type. Skipping LA_URL workaround.");
            return data;
        }
        String string = parsableByteArray.readString(parsableByteArray.readLittleEndianShort(), Charsets.UTF_16LE);
        if (string.contains("<LA_URL>")) {
            return data;
        }
        int iIndexOf = string.indexOf("</DATA>");
        if (iIndexOf == -1) {
            Log.w(TAG, "Could not find the </DATA> tag. Skipping LA_URL workaround.");
        }
        String strSubstring = string.substring(0, iIndexOf);
        String strSubstring2 = string.substring(iIndexOf);
        String string2 = new StringBuilder(String.valueOf(strSubstring).length() + 26 + String.valueOf(strSubstring2).length()).append(strSubstring).append(MOCK_LA_URL).append(strSubstring2).toString();
        int i = littleEndianInt + 52;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(i);
        byteBufferAllocate.putShort(littleEndianShort);
        byteBufferAllocate.putShort(littleEndianShort2);
        byteBufferAllocate.putShort((short) (string2.length() * 2));
        byteBufferAllocate.put(string2.getBytes(Charsets.UTF_16LE));
        return byteBufferAllocate.array();
    }
}
