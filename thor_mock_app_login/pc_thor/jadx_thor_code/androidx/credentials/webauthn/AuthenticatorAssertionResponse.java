package androidx.credentials.webauthn;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.google.firebase.messaging.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthenticatorAssertionResponse.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0010J\u0006\u0010\u001f\u001a\u00020\u0005J\u0006\u0010 \u001a\u00020\u0005J\b\u0010!\u001a\u00020\u0017H\u0016R\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0013\"\u0004\b\u001e\u0010\u0015R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Landroidx/credentials/webauthn/AuthenticatorAssertionResponse;", "Landroidx/credentials/webauthn/AuthenticatorResponse;", "requestOptions", "Landroidx/credentials/webauthn/PublicKeyCredentialRequestOptions;", "credentialId", "", "origin", "", "up", "", "uv", "be", "bs", "userHandle", Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, "clientDataHash", "(Landroidx/credentials/webauthn/PublicKeyCredentialRequestOptions;[BLjava/lang/String;ZZZZ[BLjava/lang/String;[B)V", "authenticatorData", "getAuthenticatorData", "()[B", "setAuthenticatorData", "([B)V", "clientJson", "Lorg/json/JSONObject;", "getClientJson", "()Lorg/json/JSONObject;", "setClientJson", "(Lorg/json/JSONObject;)V", "signature", "getSignature", "setSignature", "dataToSign", "defaultAuthenticatorData", "json", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AuthenticatorAssertionResponse implements AuthenticatorResponse {
    private byte[] authenticatorData;
    private final boolean be;
    private final boolean bs;
    private final byte[] clientDataHash;
    private JSONObject clientJson;
    private final byte[] credentialId;
    private final String origin;
    private final String packageName;
    private final PublicKeyCredentialRequestOptions requestOptions;
    private byte[] signature;
    private final boolean up;
    private byte[] userHandle;
    private final boolean uv;

    public AuthenticatorAssertionResponse(PublicKeyCredentialRequestOptions requestOptions, byte[] credentialId, String origin, boolean z, boolean z2, boolean z3, boolean z4, byte[] userHandle, String str, byte[] bArr) throws JSONException {
        Intrinsics.checkNotNullParameter(requestOptions, "requestOptions");
        Intrinsics.checkNotNullParameter(credentialId, "credentialId");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(userHandle, "userHandle");
        this.requestOptions = requestOptions;
        this.credentialId = credentialId;
        this.origin = origin;
        this.up = z;
        this.uv = z2;
        this.be = z3;
        this.bs = z4;
        this.userHandle = userHandle;
        this.packageName = str;
        this.clientDataHash = bArr;
        this.clientJson = new JSONObject();
        this.signature = new byte[0];
        getClientJson().put(SessionDescription.ATTR_TYPE, "webauthn.get");
        getClientJson().put(ClientData.KEY_CHALLENGE, WebAuthnUtils.INSTANCE.b64Encode(requestOptions.getChallenge()));
        getClientJson().put("origin", origin);
        if (str != null) {
            getClientJson().put("androidPackageName", str);
        }
        this.authenticatorData = defaultAuthenticatorData();
    }

    public /* synthetic */ AuthenticatorAssertionResponse(PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions, byte[] bArr, String str, boolean z, boolean z2, boolean z3, boolean z4, byte[] bArr2, String str2, byte[] bArr3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(publicKeyCredentialRequestOptions, bArr, str, z, z2, z3, z4, bArr2, (i & 256) != 0 ? null : str2, (i & 512) != 0 ? null : bArr3);
    }

    @Override // androidx.credentials.webauthn.AuthenticatorResponse
    public JSONObject getClientJson() {
        return this.clientJson;
    }

    @Override // androidx.credentials.webauthn.AuthenticatorResponse
    public void setClientJson(JSONObject jSONObject) {
        Intrinsics.checkNotNullParameter(jSONObject, "<set-?>");
        this.clientJson = jSONObject;
    }

    public final byte[] getAuthenticatorData() {
        return this.authenticatorData;
    }

    public final void setAuthenticatorData(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.authenticatorData = bArr;
    }

    public final byte[] getSignature() {
        return this.signature;
    }

    public final void setSignature(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.signature = bArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final byte[] defaultAuthenticatorData() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = this.requestOptions.getRpId().getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        byte[] rpHash = messageDigest.digest(bytes);
        boolean z = this.up;
        boolean z2 = z;
        if (this.uv) {
            z2 = (z ? 1 : 0) | 4;
        }
        boolean z3 = z2;
        if (this.be) {
            z3 = (z2 ? 1 : 0) | '\b';
        }
        int i = z3;
        if (this.bs) {
            i = (z3 ? 1 : 0) | 16;
        }
        Intrinsics.checkNotNullExpressionValue(rpHash, "rpHash");
        return ArraysKt.plus(ArraysKt.plus(rpHash, new byte[]{(byte) i}), new byte[]{0, 0, 0, 0});
    }

    public final byte[] dataToSign() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bArrDigest = this.clientDataHash;
        if (bArrDigest == null) {
            String string = getClientJson().toString();
            Intrinsics.checkNotNullExpressionValue(string, "clientJson.toString()");
            byte[] bytes = string.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            bArrDigest = messageDigest.digest(bytes);
            Intrinsics.checkNotNullExpressionValue(bArrDigest, "md.digest(clientJson.toString().toByteArray())");
        }
        return ArraysKt.plus(this.authenticatorData, bArrDigest);
    }

    @Override // androidx.credentials.webauthn.AuthenticatorResponse
    public JSONObject json() throws JSONException {
        String string = getClientJson().toString();
        Intrinsics.checkNotNullExpressionValue(string, "clientJson.toString()");
        byte[] bytes = string.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        JSONObject jSONObject = new JSONObject();
        if (this.clientDataHash == null) {
            jSONObject.put("clientDataJSON", WebAuthnUtils.INSTANCE.b64Encode(bytes));
        }
        jSONObject.put("authenticatorData", WebAuthnUtils.INSTANCE.b64Encode(this.authenticatorData));
        jSONObject.put("signature", WebAuthnUtils.INSTANCE.b64Encode(this.signature));
        jSONObject.put("userHandle", WebAuthnUtils.INSTANCE.b64Encode(this.userHandle));
        return jSONObject;
    }
}
