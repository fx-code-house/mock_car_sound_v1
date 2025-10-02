package androidx.credentials;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import androidx.credentials.CreateCredentialRequest;
import androidx.credentials.internal.FrameworkClassParsingException;
import androidx.credentials.internal.RequestValidationHelper;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CreatePublicKeyCredentialRequest.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B;\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nB=\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\fBQ\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010¢\u0006\u0002\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0018"}, d2 = {"Landroidx/credentials/CreatePublicKeyCredentialRequest;", "Landroidx/credentials/CreateCredentialRequest;", "requestJson", "", "clientDataHash", "", "preferImmediatelyAvailableCredentials", "", "origin", "isAutoSelectAllowed", "(Ljava/lang/String;[BZLjava/lang/String;Z)V", "preferDefaultProvider", "(Ljava/lang/String;[BZLjava/lang/String;Ljava/lang/String;Z)V", "displayInfo", "Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "credentialData", "Landroid/os/Bundle;", "candidateQueryData", "(Ljava/lang/String;[BZZLandroidx/credentials/CreateCredentialRequest$DisplayInfo;Ljava/lang/String;Landroid/os/Bundle;Landroid/os/Bundle;)V", "getClientDataHash", "()[B", "getRequestJson", "()Ljava/lang/String;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CreatePublicKeyCredentialRequest extends CreateCredentialRequest {
    public static final String BUNDLE_KEY_CLIENT_DATA_HASH = "androidx.credentials.BUNDLE_KEY_CLIENT_DATA_HASH";
    public static final String BUNDLE_KEY_REQUEST_JSON = "androidx.credentials.BUNDLE_KEY_REQUEST_JSON";
    public static final String BUNDLE_VALUE_SUBTYPE_CREATE_PUBLIC_KEY_CREDENTIAL_REQUEST = "androidx.credentials.BUNDLE_VALUE_SUBTYPE_CREATE_PUBLIC_KEY_CREDENTIAL_REQUEST";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final byte[] clientDataHash;
    private final String requestJson;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialRequest(String requestJson) {
        this(requestJson, null, false, null, false, 30, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialRequest(String requestJson, byte[] bArr) {
        this(requestJson, bArr, false, null, false, 28, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialRequest(String requestJson, byte[] bArr, boolean z) {
        this(requestJson, bArr, z, null, false, 24, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialRequest(String requestJson, byte[] bArr, boolean z, String str) {
        this(requestJson, bArr, z, str, false, 16, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    public /* synthetic */ CreatePublicKeyCredentialRequest(String str, byte[] bArr, boolean z, boolean z2, CreateCredentialRequest.DisplayInfo displayInfo, String str2, Bundle bundle, Bundle bundle2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bArr, z, z2, displayInfo, str2, bundle, bundle2);
    }

    public final String getRequestJson() {
        return this.requestJson;
    }

    public final byte[] getClientDataHash() {
        return this.clientDataHash;
    }

    /* synthetic */ CreatePublicKeyCredentialRequest(String str, byte[] bArr, boolean z, boolean z2, CreateCredentialRequest.DisplayInfo displayInfo, String str2, Bundle bundle, Bundle bundle2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bArr, z, z2, displayInfo, (i & 32) != 0 ? null : str2, (i & 64) != 0 ? INSTANCE.toCredentialDataBundle$credentials_release(str, bArr) : bundle, (i & 128) != 0 ? INSTANCE.toCandidateDataBundle$credentials_release(str, bArr) : bundle2);
    }

    private CreatePublicKeyCredentialRequest(String str, byte[] bArr, boolean z, boolean z2, CreateCredentialRequest.DisplayInfo displayInfo, String str2, Bundle bundle, Bundle bundle2) {
        super(PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL, bundle, bundle2, false, z, displayInfo, str2, z2);
        this.requestJson = str;
        this.clientDataHash = bArr;
        if (!RequestValidationHelper.INSTANCE.isValidJSON(str)) {
            throw new IllegalArgumentException("requestJson must not be empty, and must be a valid JSON".toString());
        }
    }

    public /* synthetic */ CreatePublicKeyCredentialRequest(String str, byte[] bArr, boolean z, String str2, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : bArr, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : str2, (i & 16) != 0 ? false : z2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialRequest(String requestJson, byte[] bArr, boolean z, String str, boolean z2) {
        this(requestJson, bArr, z2, z, Companion.getRequestDisplayInfo$credentials_release$default(INSTANCE, requestJson, null, 2, null), str, null, null, PsExtractor.AUDIO_STREAM, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialRequest(String requestJson, byte[] bArr, boolean z, String str, String str2, boolean z2) {
        this(requestJson, bArr, z2, z, INSTANCE.getRequestDisplayInfo$credentials_release(requestJson, str2), str, null, null, PsExtractor.AUDIO_STREAM, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    /* compiled from: CreatePublicKeyCredentialRequest.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0004\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\nH\u0001¢\u0006\u0002\b\rJ!\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0001¢\u0006\u0002\b\u0012J\u001f\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0001¢\u0006\u0002\b\u0016J!\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00042\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0001¢\u0006\u0002\b\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Landroidx/credentials/CreatePublicKeyCredentialRequest$Companion;", "", "()V", "BUNDLE_KEY_CLIENT_DATA_HASH", "", "BUNDLE_KEY_REQUEST_JSON", "BUNDLE_VALUE_SUBTYPE_CREATE_PUBLIC_KEY_CREDENTIAL_REQUEST", "createFrom", "Landroidx/credentials/CreatePublicKeyCredentialRequest;", "data", "Landroid/os/Bundle;", "origin", "candidateQueryData", "createFrom$credentials_release", "getRequestDisplayInfo", "Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "requestJson", "defaultProvider", "getRequestDisplayInfo$credentials_release", "toCandidateDataBundle", "clientDataHash", "", "toCandidateDataBundle$credentials_release", "toCredentialDataBundle", "toCredentialDataBundle$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ CreateCredentialRequest.DisplayInfo getRequestDisplayInfo$credentials_release$default(Companion companion, String str, String str2, int i, Object obj) {
            if ((i & 2) != 0) {
                str2 = null;
            }
            return companion.getRequestDisplayInfo$credentials_release(str, str2);
        }

        @JvmStatic
        public final CreateCredentialRequest.DisplayInfo getRequestDisplayInfo$credentials_release(String requestJson, String defaultProvider) throws JSONException {
            Intrinsics.checkNotNullParameter(requestJson, "requestJson");
            try {
                JSONObject jSONObject = new JSONObject(requestJson).getJSONObject("user");
                String userName = jSONObject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                String string = jSONObject.isNull("displayName") ? null : jSONObject.getString("displayName");
                Intrinsics.checkNotNullExpressionValue(userName, "userName");
                return new CreateCredentialRequest.DisplayInfo(userName, string, (Icon) null, defaultProvider);
            } catch (Exception unused) {
                throw new IllegalArgumentException("user.name must be defined in requestJson");
            }
        }

        public static /* synthetic */ Bundle toCredentialDataBundle$credentials_release$default(Companion companion, String str, byte[] bArr, int i, Object obj) {
            if ((i & 2) != 0) {
                bArr = null;
            }
            return companion.toCredentialDataBundle$credentials_release(str, bArr);
        }

        @JvmStatic
        public final Bundle toCredentialDataBundle$credentials_release(String requestJson, byte[] clientDataHash) {
            Intrinsics.checkNotNullParameter(requestJson, "requestJson");
            Bundle bundle = new Bundle();
            bundle.putString(PublicKeyCredential.BUNDLE_KEY_SUBTYPE, CreatePublicKeyCredentialRequest.BUNDLE_VALUE_SUBTYPE_CREATE_PUBLIC_KEY_CREDENTIAL_REQUEST);
            bundle.putString("androidx.credentials.BUNDLE_KEY_REQUEST_JSON", requestJson);
            bundle.putByteArray("androidx.credentials.BUNDLE_KEY_CLIENT_DATA_HASH", clientDataHash);
            return bundle;
        }

        @JvmStatic
        public final Bundle toCandidateDataBundle$credentials_release(String requestJson, byte[] clientDataHash) {
            Intrinsics.checkNotNullParameter(requestJson, "requestJson");
            Bundle bundle = new Bundle();
            bundle.putString(PublicKeyCredential.BUNDLE_KEY_SUBTYPE, CreatePublicKeyCredentialRequest.BUNDLE_VALUE_SUBTYPE_CREATE_PUBLIC_KEY_CREDENTIAL_REQUEST);
            bundle.putString("androidx.credentials.BUNDLE_KEY_REQUEST_JSON", requestJson);
            bundle.putByteArray("androidx.credentials.BUNDLE_KEY_CLIENT_DATA_HASH", clientDataHash);
            return bundle;
        }

        @JvmStatic
        public final CreatePublicKeyCredentialRequest createFrom$credentials_release(Bundle data, String origin, Bundle candidateQueryData) throws FrameworkClassParsingException {
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
            try {
                String string = data.getString("androidx.credentials.BUNDLE_KEY_REQUEST_JSON");
                Intrinsics.checkNotNull(string);
                byte[] byteArray = data.getByteArray("androidx.credentials.BUNDLE_KEY_CLIENT_DATA_HASH");
                boolean z = data.getBoolean("androidx.credentials.BUNDLE_KEY_PREFER_IMMEDIATELY_AVAILABLE_CREDENTIALS", false);
                CreateCredentialRequest.DisplayInfo fromCredentialDataBundle = CreateCredentialRequest.DisplayInfo.INSTANCE.parseFromCredentialDataBundle(data);
                if (fromCredentialDataBundle == null) {
                    fromCredentialDataBundle = getRequestDisplayInfo$credentials_release$default(this, string, null, 2, null);
                }
                return new CreatePublicKeyCredentialRequest(string, byteArray, data.getBoolean("androidx.credentials.BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED", false), z, fromCredentialDataBundle, origin, data, candidateQueryData, null);
            } catch (Exception unused) {
                throw new FrameworkClassParsingException();
            }
        }
    }
}
