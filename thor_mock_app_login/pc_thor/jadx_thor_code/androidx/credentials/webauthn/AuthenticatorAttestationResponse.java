package androidx.credentials.webauthn;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.google.firebase.messaging.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthenticatorAttestationResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\n\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0010J\b\u0010\u001c\u001a\u00020\u0005H\u0002J\r\u0010\u001d\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u001eJ\b\u0010\u001f\u001a\u00020\u0017H\u0016R\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Landroidx/credentials/webauthn/AuthenticatorAttestationResponse;", "Landroidx/credentials/webauthn/AuthenticatorResponse;", "requestOptions", "Landroidx/credentials/webauthn/PublicKeyCredentialCreationOptions;", "credentialId", "", "credentialPublicKey", "origin", "", "up", "", "uv", "be", "bs", Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, "clientDataHash", "(Landroidx/credentials/webauthn/PublicKeyCredentialCreationOptions;[B[BLjava/lang/String;ZZZZLjava/lang/String;[B)V", "attestationObject", "getAttestationObject", "()[B", "setAttestationObject", "([B)V", "clientJson", "Lorg/json/JSONObject;", "getClientJson", "()Lorg/json/JSONObject;", "setClientJson", "(Lorg/json/JSONObject;)V", "authData", "defaultAttestationObject", "defaultAttestationObject$credentials_release", "json", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AuthenticatorAttestationResponse implements AuthenticatorResponse {
    private byte[] attestationObject;
    private final boolean be;
    private final boolean bs;
    private final byte[] clientDataHash;
    private JSONObject clientJson;
    private final byte[] credentialId;
    private final byte[] credentialPublicKey;
    private final String origin;
    private final String packageName;
    private final PublicKeyCredentialCreationOptions requestOptions;
    private final boolean up;
    private final boolean uv;

    public AuthenticatorAttestationResponse(PublicKeyCredentialCreationOptions requestOptions, byte[] credentialId, byte[] credentialPublicKey, String origin, boolean z, boolean z2, boolean z3, boolean z4, String str, byte[] bArr) throws JSONException {
        Intrinsics.checkNotNullParameter(requestOptions, "requestOptions");
        Intrinsics.checkNotNullParameter(credentialId, "credentialId");
        Intrinsics.checkNotNullParameter(credentialPublicKey, "credentialPublicKey");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.requestOptions = requestOptions;
        this.credentialId = credentialId;
        this.credentialPublicKey = credentialPublicKey;
        this.origin = origin;
        this.up = z;
        this.uv = z2;
        this.be = z3;
        this.bs = z4;
        this.packageName = str;
        this.clientDataHash = bArr;
        this.clientJson = new JSONObject();
        getClientJson().put(SessionDescription.ATTR_TYPE, "webauthn.create");
        getClientJson().put(ClientData.KEY_CHALLENGE, WebAuthnUtils.INSTANCE.b64Encode(requestOptions.getChallenge()));
        getClientJson().put("origin", origin);
        if (str != null) {
            getClientJson().put("androidPackageName", str);
        }
        this.attestationObject = defaultAttestationObject$credentials_release();
    }

    public /* synthetic */ AuthenticatorAttestationResponse(PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions, byte[] bArr, byte[] bArr2, String str, boolean z, boolean z2, boolean z3, boolean z4, String str2, byte[] bArr3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(publicKeyCredentialCreationOptions, bArr, bArr2, str, z, z2, z3, z4, (i & 256) != 0 ? null : str2, (i & 512) != 0 ? null : bArr3);
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

    public final byte[] getAttestationObject() {
        return this.attestationObject;
    }

    public final void setAttestationObject(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.attestationObject = bArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v7 */
    private final byte[] authData() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = this.requestOptions.getRp().getId().getBytes(Charsets.UTF_8);
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
        ?? r1 = z3;
        if (this.bs) {
            r1 = (z3 ? 1 : 0) | 16;
        }
        int i = r1 | 64;
        byte[] bArr = new byte[16];
        for (int i2 = 0; i2 < 16; i2++) {
            bArr[i2] = 0;
        }
        byte[] bArr2 = this.credentialId;
        byte[] bArr3 = {(byte) (bArr2.length >> 8), (byte) bArr2.length};
        Intrinsics.checkNotNullExpressionValue(rpHash, "rpHash");
        return ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(ArraysKt.plus(rpHash, new byte[]{(byte) i}), new byte[]{0, 0, 0, 0}), bArr), bArr3), this.credentialId), this.credentialPublicKey);
    }

    public final byte[] defaultAttestationObject$credentials_release() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("fmt", "none");
        linkedHashMap.put("attStmt", MapsKt.emptyMap());
        linkedHashMap.put("authData", authData());
        return new Cbor().encode(linkedHashMap);
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
        jSONObject.put("attestationObject", WebAuthnUtils.INSTANCE.b64Encode(this.attestationObject));
        jSONObject.put("transports", new JSONArray((Collection) CollectionsKt.listOf((Object[]) new String[]{"internal", "hybrid"})));
        return jSONObject;
    }
}
