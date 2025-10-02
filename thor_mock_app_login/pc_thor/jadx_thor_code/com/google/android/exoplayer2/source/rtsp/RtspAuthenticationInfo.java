package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.util.Base64;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;
import com.google.android.exoplayer2.util.Util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
final class RtspAuthenticationInfo {
    private static final String ALGORITHM = "MD5";
    public static final int BASIC = 1;
    public static final int DIGEST = 2;
    private static final String DIGEST_FORMAT = "Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\"";
    private static final String DIGEST_FORMAT_WITH_OPAQUE = "Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\", opaque=\"%s\"";
    public final int authenticationMechanism;
    public final String nonce;
    public final String opaque;
    public final String realm;

    public RtspAuthenticationInfo(int authenticationMechanism, String realm, String nonce, String opaque) {
        this.authenticationMechanism = authenticationMechanism;
        this.realm = realm;
        this.nonce = nonce;
        this.opaque = opaque;
    }

    public String getAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo authUserInfo, Uri uri, int requestMethod) throws ParserException {
        int i = this.authenticationMechanism;
        if (i == 1) {
            return getBasicAuthorizationHeaderValue(authUserInfo);
        }
        if (i == 2) {
            return getDigestAuthorizationHeaderValue(authUserInfo, uri, requestMethod);
        }
        throw ParserException.createForManifestWithUnsupportedFeature(null, new UnsupportedOperationException());
    }

    private String getBasicAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo authUserInfo) {
        String str = authUserInfo.username;
        String str2 = authUserInfo.password;
        return Base64.encodeToString(RtspMessageUtil.getStringBytes(new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(":").append(str2).toString()), 0);
    }

    private String getDigestAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo authUserInfo, Uri uri, int requestMethod) throws ParserException, NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            String methodString = RtspMessageUtil.toMethodString(requestMethod);
            String str = authUserInfo.username;
            String str2 = this.realm;
            String str3 = authUserInfo.password;
            String hexString = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append(":").append(str2).append(":").append(str3).toString())));
            String strValueOf = String.valueOf(uri);
            String hexString2 = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(new StringBuilder(String.valueOf(methodString).length() + 1 + String.valueOf(strValueOf).length()).append(methodString).append(":").append(strValueOf).toString())));
            String str4 = this.nonce;
            String hexString3 = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(new StringBuilder(String.valueOf(hexString).length() + 2 + String.valueOf(str4).length() + String.valueOf(hexString2).length()).append(hexString).append(":").append(str4).append(":").append(hexString2).toString())));
            return this.opaque.isEmpty() ? Util.formatInvariant(DIGEST_FORMAT, authUserInfo.username, this.realm, this.nonce, uri, hexString3) : Util.formatInvariant(DIGEST_FORMAT_WITH_OPAQUE, authUserInfo.username, this.realm, this.nonce, uri, hexString3, this.opaque);
        } catch (NoSuchAlgorithmException e) {
            throw ParserException.createForManifestWithUnsupportedFeature(null, e);
        }
    }
}
