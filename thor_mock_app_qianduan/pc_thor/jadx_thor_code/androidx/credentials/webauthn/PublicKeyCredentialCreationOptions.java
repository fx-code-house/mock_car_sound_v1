package androidx.credentials.webauthn;

import android.util.Log;
import androidx.credentials.webauthn.WebAuthnUtils;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PublicKeyCredentialCreationOptions.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0004R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0014¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.¨\u0006/"}, d2 = {"Landroidx/credentials/webauthn/PublicKeyCredentialCreationOptions;", "", "requestJson", "", "(Ljava/lang/String;)V", "attestation", "getAttestation", "()Ljava/lang/String;", "setAttestation", "authenticatorSelection", "Landroidx/credentials/webauthn/AuthenticatorSelectionCriteria;", "getAuthenticatorSelection", "()Landroidx/credentials/webauthn/AuthenticatorSelectionCriteria;", "setAuthenticatorSelection", "(Landroidx/credentials/webauthn/AuthenticatorSelectionCriteria;)V", ClientData.KEY_CHALLENGE, "", "getChallenge", "()[B", "excludeCredentials", "", "Landroidx/credentials/webauthn/PublicKeyCredentialDescriptor;", "getExcludeCredentials", "()Ljava/util/List;", "setExcludeCredentials", "(Ljava/util/List;)V", "json", "Lorg/json/JSONObject;", "getJson", "()Lorg/json/JSONObject;", "pubKeyCredParams", "Landroidx/credentials/webauthn/PublicKeyCredentialParameters;", "getPubKeyCredParams", "rp", "Landroidx/credentials/webauthn/PublicKeyCredentialRpEntity;", "getRp", "()Landroidx/credentials/webauthn/PublicKeyCredentialRpEntity;", "timeout", "", "getTimeout", "()J", "setTimeout", "(J)V", "user", "Landroidx/credentials/webauthn/PublicKeyCredentialUserEntity;", "getUser", "()Landroidx/credentials/webauthn/PublicKeyCredentialUserEntity;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class PublicKeyCredentialCreationOptions {
    private String attestation;
    private AuthenticatorSelectionCriteria authenticatorSelection;
    private final byte[] challenge;
    private List<PublicKeyCredentialDescriptor> excludeCredentials;
    private final JSONObject json;
    private final List<PublicKeyCredentialParameters> pubKeyCredParams;
    private final PublicKeyCredentialRpEntity rp;
    private long timeout;
    private final PublicKeyCredentialUserEntity user;

    public PublicKeyCredentialCreationOptions(String requestJson) throws JSONException {
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
        JSONObject jSONObject = new JSONObject(requestJson);
        this.json = jSONObject;
        String challengeString = jSONObject.getString(ClientData.KEY_CHALLENGE);
        WebAuthnUtils.Companion companion = WebAuthnUtils.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(challengeString, "challengeString");
        this.challenge = companion.b64Decode(challengeString);
        JSONObject jSONObject2 = jSONObject.getJSONObject("rp");
        String string = jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
        Intrinsics.checkNotNullExpressionValue(string, "rpJson.getString(\"name\")");
        String string2 = jSONObject2.getString(TtmlNode.ATTR_ID);
        Intrinsics.checkNotNullExpressionValue(string2, "rpJson.getString(\"id\")");
        this.rp = new PublicKeyCredentialRpEntity(string, string2);
        JSONObject jSONObject3 = jSONObject.getJSONObject("user");
        WebAuthnUtils.Companion companion2 = WebAuthnUtils.INSTANCE;
        String string3 = jSONObject3.getString(TtmlNode.ATTR_ID);
        Intrinsics.checkNotNullExpressionValue(string3, "rpUser.getString(\"id\")");
        byte[] bArrB64Decode = companion2.b64Decode(string3);
        String string4 = jSONObject3.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
        Intrinsics.checkNotNullExpressionValue(string4, "rpUser.getString(\"name\")");
        String string5 = jSONObject3.getString("displayName");
        Intrinsics.checkNotNullExpressionValue(string5, "rpUser.getString(\"displayName\")");
        this.user = new PublicKeyCredentialUserEntity(string4, bArrB64Decode, string5);
        JSONArray jSONArray = jSONObject.getJSONArray("pubKeyCredParams");
        ArrayList arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject4 = jSONArray.getJSONObject(i);
            String string6 = jSONObject4.getString(SessionDescription.ATTR_TYPE);
            Intrinsics.checkNotNullExpressionValue(string6, "e.getString(\"type\")");
            arrayList.add(new PublicKeyCredentialParameters(string6, jSONObject4.getLong("alg")));
        }
        List<PublicKeyCredentialParameters> list = CollectionsKt.toList(arrayList);
        this.pubKeyCredParams = list;
        this.timeout = this.json.optLong("timeout", 0L);
        this.excludeCredentials = CollectionsKt.emptyList();
        this.authenticatorSelection = new AuthenticatorSelectionCriteria("platform", "required", false, null, 12, null);
        String strOptString = this.json.optString("attestation", "none");
        Intrinsics.checkNotNullExpressionValue(strOptString, "json.optString(\"attestation\", \"none\")");
        this.attestation = strOptString;
        Log.i("WebAuthn", "Challenge " + this.challenge + "()");
        Log.i("WebAuthn", "rp " + this.rp);
        Log.i("WebAuthn", "user " + this.user);
        Log.i("WebAuthn", "pubKeyCredParams " + list);
        Log.i("WebAuthn", "timeout " + this.timeout);
        Log.i("WebAuthn", "excludeCredentials " + this.excludeCredentials);
        Log.i("WebAuthn", "authenticatorSelection " + this.authenticatorSelection);
        Log.i("WebAuthn", "attestation " + this.attestation);
    }

    public final JSONObject getJson() {
        return this.json;
    }

    public final PublicKeyCredentialRpEntity getRp() {
        return this.rp;
    }

    public final PublicKeyCredentialUserEntity getUser() {
        return this.user;
    }

    public final byte[] getChallenge() {
        return this.challenge;
    }

    public final List<PublicKeyCredentialParameters> getPubKeyCredParams() {
        return this.pubKeyCredParams;
    }

    public final long getTimeout() {
        return this.timeout;
    }

    public final void setTimeout(long j) {
        this.timeout = j;
    }

    public final List<PublicKeyCredentialDescriptor> getExcludeCredentials() {
        return this.excludeCredentials;
    }

    public final void setExcludeCredentials(List<PublicKeyCredentialDescriptor> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.excludeCredentials = list;
    }

    public final AuthenticatorSelectionCriteria getAuthenticatorSelection() {
        return this.authenticatorSelection;
    }

    public final void setAuthenticatorSelection(AuthenticatorSelectionCriteria authenticatorSelectionCriteria) {
        Intrinsics.checkNotNullParameter(authenticatorSelectionCriteria, "<set-?>");
        this.authenticatorSelection = authenticatorSelectionCriteria;
    }

    public final String getAttestation() {
        return this.attestation;
    }

    public final void setAttestation(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.attestation = str;
    }
}
