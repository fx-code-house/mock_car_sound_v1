package androidx.credentials.playservices.controllers.CreatePublicKeyCredential;

import android.util.Base64;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.credentials.CreatePublicKeyCredentialRequest;
import androidx.credentials.GetPublicKeyCredentialOption;
import androidx.credentials.exceptions.CreateCredentialCancellationException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialUnknownException;
import androidx.credentials.exceptions.domerrors.AbortError;
import androidx.credentials.exceptions.domerrors.ConstraintError;
import androidx.credentials.exceptions.domerrors.DataError;
import androidx.credentials.exceptions.domerrors.DomError;
import androidx.credentials.exceptions.domerrors.EncodingError;
import androidx.credentials.exceptions.domerrors.InvalidStateError;
import androidx.credentials.exceptions.domerrors.NetworkError;
import androidx.credentials.exceptions.domerrors.NotAllowedError;
import androidx.credentials.exceptions.domerrors.NotReadableError;
import androidx.credentials.exceptions.domerrors.NotSupportedError;
import androidx.credentials.exceptions.domerrors.SecurityError;
import androidx.credentials.exceptions.domerrors.TimeoutError;
import androidx.credentials.exceptions.domerrors.UnknownError;
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialDomException;
import androidx.credentials.exceptions.publickeycredential.GetPublicKeyCredentialDomException;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.fido.common.Transport;
import com.google.android.gms.fido.fido2.api.common.Attachment;
import com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference;
import com.google.android.gms.fido.fido2.api.common.AuthenticationExtensions;
import com.google.android.gms.fido.fido2.api.common.AuthenticatorAssertionResponse;
import com.google.android.gms.fido.fido2.api.common.AuthenticatorErrorResponse;
import com.google.android.gms.fido.fido2.api.common.AuthenticatorResponse;
import com.google.android.gms.fido.fido2.api.common.AuthenticatorSelectionCriteria;
import com.google.android.gms.fido.fido2.api.common.COSEAlgorithmIdentifier;
import com.google.android.gms.fido.fido2.api.common.ErrorCode;
import com.google.android.gms.fido.fido2.api.common.FidoAppIdExtension;
import com.google.android.gms.fido.fido2.api.common.GoogleThirdPartyPaymentExtension;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredential;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialCreationOptions;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialDescriptor;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialParameters;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialRpEntity;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialUserEntity;
import com.google.android.gms.fido.fido2.api.common.ResidentKeyRequirement;
import com.google.android.gms.fido.fido2.api.common.UserVerificationMethodExtension;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PublicKeyCredentialControllerUtility.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/playservices/controllers/CreatePublicKeyCredential/PublicKeyCredentialControllerUtility;", "", "()V", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class PublicKeyCredentialControllerUtility {
    private static final int FLAGS = 11;
    private static final String TAG = "PublicKeyUtility";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String JSON_KEY_CLIENT_DATA = "clientDataJSON";
    private static final String JSON_KEY_ATTESTATION_OBJ = "attestationObject";
    private static final String JSON_KEY_AUTH_DATA = "authenticatorData";
    private static final String JSON_KEY_SIGNATURE = "signature";
    private static final String JSON_KEY_USER_HANDLE = "userHandle";
    private static final String JSON_KEY_RESPONSE = "response";
    private static final String JSON_KEY_ID = TtmlNode.ATTR_ID;
    private static final String JSON_KEY_RAW_ID = "rawId";
    private static final String JSON_KEY_TYPE = SessionDescription.ATTR_TYPE;
    private static final String JSON_KEY_RPID = "rpId";
    private static final String JSON_KEY_CHALLENGE = ClientData.KEY_CHALLENGE;
    private static final String JSON_KEY_APPID = "appid";
    private static final String JSON_KEY_THIRD_PARTY_PAYMENT = "thirdPartyPayment";
    private static final String JSON_KEY_AUTH_SELECTION = "authenticatorSelection";
    private static final String JSON_KEY_REQUIRE_RES_KEY = "requireResidentKey";
    private static final String JSON_KEY_RES_KEY = "residentKey";
    private static final String JSON_KEY_AUTH_ATTACHMENT = "authenticatorAttachment";
    private static final String JSON_KEY_TIMEOUT = "timeout";
    private static final String JSON_KEY_EXCLUDE_CREDENTIALS = "excludeCredentials";
    private static final String JSON_KEY_TRANSPORTS = "transports";
    private static final String JSON_KEY_RP = "rp";
    private static final String JSON_KEY_NAME = AppMeasurementSdk.ConditionalUserProperty.NAME;
    private static final String JSON_KEY_ICON = "icon";
    private static final String JSON_KEY_ALG = "alg";
    private static final String JSON_KEY_USER = "user";
    private static final String JSON_KEY_DISPLAY_NAME = "displayName";
    private static final String JSON_KEY_USER_VERIFICATION_METHOD = "userVerificationMethod";
    private static final String JSON_KEY_KEY_PROTECTION_TYPE = "keyProtectionType";
    private static final String JSON_KEY_MATCHER_PROTECTION_TYPE = "matcherProtectionType";
    private static final String JSON_KEY_EXTENSTIONS = "extensions";
    private static final String JSON_KEY_ATTESTATION = "attestation";
    private static final String JSON_KEY_PUB_KEY_CRED_PARAMS = "pubKeyCredParams";
    private static final String JSON_KEY_CLIENT_EXTENSION_RESULTS = "clientExtensionResults";
    private static final String JSON_KEY_RK = "rk";
    private static final String JSON_KEY_CRED_PROPS = "credProps";
    private static final LinkedHashMap<ErrorCode, DomError> orderedErrorCodeToExceptions = MapsKt.linkedMapOf(TuplesKt.to(ErrorCode.UNKNOWN_ERR, new UnknownError()), TuplesKt.to(ErrorCode.ABORT_ERR, new AbortError()), TuplesKt.to(ErrorCode.ATTESTATION_NOT_PRIVATE_ERR, new NotReadableError()), TuplesKt.to(ErrorCode.CONSTRAINT_ERR, new ConstraintError()), TuplesKt.to(ErrorCode.DATA_ERR, new DataError()), TuplesKt.to(ErrorCode.INVALID_STATE_ERR, new InvalidStateError()), TuplesKt.to(ErrorCode.ENCODING_ERR, new EncodingError()), TuplesKt.to(ErrorCode.NETWORK_ERR, new NetworkError()), TuplesKt.to(ErrorCode.NOT_ALLOWED_ERR, new NotAllowedError()), TuplesKt.to(ErrorCode.NOT_SUPPORTED_ERR, new NotSupportedError()), TuplesKt.to(ErrorCode.SECURITY_ERR, new SecurityError()), TuplesKt.to(ErrorCode.TIMEOUT_ERR, new TimeoutError()));

    @JvmStatic
    public static final PublicKeyCredentialCreationOptions convert(CreatePublicKeyCredentialRequest createPublicKeyCredentialRequest) {
        return INSTANCE.convert(createPublicKeyCredentialRequest);
    }

    /* compiled from: PublicKeyCredentialControllerUtility.kt */
    @Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\bH\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J7\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020X2\u000e\u0010Z\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060[2\u0006\u0010\\\u001a\u00020]H\u0000¢\u0006\u0004\b^\u0010_J\u000e\u0010`\u001a\u00020X2\u0006\u0010a\u001a\u00020\u0006J\u000e\u0010b\u001a\u00020\u00062\u0006\u0010c\u001a\u00020XJ\u001f\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020P2\b\u0010g\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0002\bhJ\u000e\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020\u0004J\u0010\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020oH\u0007J\u0015\u0010p\u001a\u00020m2\u0006\u0010\\\u001a\u00020]H\u0000¢\u0006\u0002\bqJ\u000e\u0010r\u001a\u00020s2\u0006\u0010t\u001a\u00020uJ\u0010\u0010v\u001a\u00020w2\u0006\u0010t\u001a\u00020uH\u0007J\u0010\u0010x\u001a\u00020X2\u0006\u0010\\\u001a\u00020]H\u0002J\u001d\u0010y\u001a\u00020V2\u0006\u0010\\\u001a\u00020]2\u0006\u0010z\u001a\u00020{H\u0000¢\u0006\u0002\b|J\u001d\u0010}\u001a\u00020V2\u0006\u0010\\\u001a\u00020]2\u0006\u0010z\u001a\u00020{H\u0000¢\u0006\u0002\b~J\u001e\u0010\u007f\u001a\u00020V2\u0006\u0010\\\u001a\u00020]2\u0006\u0010z\u001a\u00020{H\u0000¢\u0006\u0003\b\u0080\u0001J\u001f\u0010\u0081\u0001\u001a\u00020V2\u0006\u0010\\\u001a\u00020]2\u0006\u0010z\u001a\u00020{H\u0000¢\u0006\u0003\b\u0082\u0001J\u001f\u0010\u0083\u0001\u001a\u00020V2\u0006\u0010\\\u001a\u00020]2\u0006\u0010z\u001a\u00020{H\u0000¢\u0006\u0003\b\u0084\u0001J\u001f\u0010\u0085\u0001\u001a\u00020V2\u0006\u0010\\\u001a\u00020]2\u0006\u0010z\u001a\u00020{H\u0000¢\u0006\u0003\b\u0086\u0001J\u0014\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0088\u00012\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001J\u0011\u0010\u008b\u0001\u001a\u00020\u00062\b\u0010\u0089\u0001\u001a\u00030\u008c\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0014\u0010\u0013\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0014\u0010\u0015\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u0014\u0010\u0017\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\bR\u0014\u0010\u001b\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\bR\u0014\u0010\u001d\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\bR\u0014\u0010\u001f\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b \u0010\bR\u0014\u0010!\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\bR\u0014\u0010#\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\bR\u0014\u0010%\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\bR\u0014\u0010'\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\bR\u0014\u0010)\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\bR\u0014\u0010+\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\bR\u0014\u0010-\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\bR\u0014\u0010/\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\bR\u0014\u00101\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\bR\u0014\u00103\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\bR\u0014\u00105\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\bR\u0014\u00107\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\bR\u0014\u00109\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\bR\u0014\u0010;\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\bR\u0014\u0010=\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\bR\u0014\u0010?\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\bR\u0014\u0010A\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\bR\u0014\u0010C\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\bR\u0014\u0010E\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\bR\u0014\u0010G\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\bR\u0014\u0010I\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\bR\u0014\u0010K\u001a\u00020\u0006X\u0080D¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\bR\u000e\u0010M\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R0\u0010N\u001a\u001e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020Q0Oj\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020Q`RX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010T¨\u0006\u008d\u0001"}, d2 = {"Landroidx/credentials/playservices/controllers/CreatePublicKeyCredential/PublicKeyCredentialControllerUtility$Companion;", "", "()V", "FLAGS", "", "JSON_KEY_ALG", "", "getJSON_KEY_ALG$credentials_play_services_auth_release", "()Ljava/lang/String;", "JSON_KEY_APPID", "getJSON_KEY_APPID$credentials_play_services_auth_release", "JSON_KEY_ATTESTATION", "getJSON_KEY_ATTESTATION$credentials_play_services_auth_release", "JSON_KEY_ATTESTATION_OBJ", "getJSON_KEY_ATTESTATION_OBJ$credentials_play_services_auth_release", "JSON_KEY_AUTH_ATTACHMENT", "getJSON_KEY_AUTH_ATTACHMENT$credentials_play_services_auth_release", "JSON_KEY_AUTH_DATA", "getJSON_KEY_AUTH_DATA$credentials_play_services_auth_release", "JSON_KEY_AUTH_SELECTION", "getJSON_KEY_AUTH_SELECTION$credentials_play_services_auth_release", "JSON_KEY_CHALLENGE", "getJSON_KEY_CHALLENGE$credentials_play_services_auth_release", "JSON_KEY_CLIENT_DATA", "getJSON_KEY_CLIENT_DATA$credentials_play_services_auth_release", "JSON_KEY_CLIENT_EXTENSION_RESULTS", "getJSON_KEY_CLIENT_EXTENSION_RESULTS$credentials_play_services_auth_release", "JSON_KEY_CRED_PROPS", "getJSON_KEY_CRED_PROPS$credentials_play_services_auth_release", "JSON_KEY_DISPLAY_NAME", "getJSON_KEY_DISPLAY_NAME$credentials_play_services_auth_release", "JSON_KEY_EXCLUDE_CREDENTIALS", "getJSON_KEY_EXCLUDE_CREDENTIALS$credentials_play_services_auth_release", "JSON_KEY_EXTENSTIONS", "getJSON_KEY_EXTENSTIONS$credentials_play_services_auth_release", "JSON_KEY_ICON", "getJSON_KEY_ICON$credentials_play_services_auth_release", "JSON_KEY_ID", "getJSON_KEY_ID$credentials_play_services_auth_release", "JSON_KEY_KEY_PROTECTION_TYPE", "getJSON_KEY_KEY_PROTECTION_TYPE$credentials_play_services_auth_release", "JSON_KEY_MATCHER_PROTECTION_TYPE", "getJSON_KEY_MATCHER_PROTECTION_TYPE$credentials_play_services_auth_release", "JSON_KEY_NAME", "getJSON_KEY_NAME$credentials_play_services_auth_release", "JSON_KEY_PUB_KEY_CRED_PARAMS", "getJSON_KEY_PUB_KEY_CRED_PARAMS$credentials_play_services_auth_release", "JSON_KEY_RAW_ID", "getJSON_KEY_RAW_ID$credentials_play_services_auth_release", "JSON_KEY_REQUIRE_RES_KEY", "getJSON_KEY_REQUIRE_RES_KEY$credentials_play_services_auth_release", "JSON_KEY_RESPONSE", "getJSON_KEY_RESPONSE$credentials_play_services_auth_release", "JSON_KEY_RES_KEY", "getJSON_KEY_RES_KEY$credentials_play_services_auth_release", "JSON_KEY_RK", "getJSON_KEY_RK$credentials_play_services_auth_release", "JSON_KEY_RP", "getJSON_KEY_RP$credentials_play_services_auth_release", "JSON_KEY_RPID", "getJSON_KEY_RPID$credentials_play_services_auth_release", "JSON_KEY_SIGNATURE", "getJSON_KEY_SIGNATURE$credentials_play_services_auth_release", "JSON_KEY_THIRD_PARTY_PAYMENT", "getJSON_KEY_THIRD_PARTY_PAYMENT$credentials_play_services_auth_release", "JSON_KEY_TIMEOUT", "getJSON_KEY_TIMEOUT$credentials_play_services_auth_release", "JSON_KEY_TRANSPORTS", "getJSON_KEY_TRANSPORTS$credentials_play_services_auth_release", "JSON_KEY_TYPE", "getJSON_KEY_TYPE$credentials_play_services_auth_release", "JSON_KEY_USER", "getJSON_KEY_USER$credentials_play_services_auth_release", "JSON_KEY_USER_HANDLE", "getJSON_KEY_USER_HANDLE$credentials_play_services_auth_release", "JSON_KEY_USER_VERIFICATION_METHOD", "getJSON_KEY_USER_VERIFICATION_METHOD$credentials_play_services_auth_release", "TAG", "orderedErrorCodeToExceptions", "Ljava/util/LinkedHashMap;", "Lcom/google/android/gms/fido/fido2/api/common/ErrorCode;", "Landroidx/credentials/exceptions/domerrors/DomError;", "Lkotlin/collections/LinkedHashMap;", "getOrderedErrorCodeToExceptions$credentials_play_services_auth_release", "()Ljava/util/LinkedHashMap;", "addAuthenticatorAttestationResponse", "", "clientDataJSON", "", "attestationObject", "transportArray", "", "json", "Lorg/json/JSONObject;", "addAuthenticatorAttestationResponse$credentials_play_services_auth_release", "([B[B[Ljava/lang/String;Lorg/json/JSONObject;)V", "b64Decode", "str", "b64Encode", "data", "beginSignInPublicKeyCredentialResponseContainsError", "Landroidx/credentials/exceptions/GetCredentialException;", "code", NotificationCompat.CATEGORY_MESSAGE, "beginSignInPublicKeyCredentialResponseContainsError$credentials_play_services_auth_release", "checkAlgSupported", "", "alg", "convert", "Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredentialCreationOptions;", "request", "Landroidx/credentials/CreatePublicKeyCredentialRequest;", "convertJSON", "convertJSON$credentials_play_services_auth_release", "convertToPlayAuthPasskeyJsonRequest", "Lcom/google/android/gms/auth/api/identity/BeginSignInRequest$PasskeyJsonRequestOptions;", "option", "Landroidx/credentials/GetPublicKeyCredentialOption;", "convertToPlayAuthPasskeyRequest", "Lcom/google/android/gms/auth/api/identity/BeginSignInRequest$PasskeysRequestOptions;", "getChallenge", "parseOptionalAuthenticatorSelection", "builder", "Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredentialCreationOptions$Builder;", "parseOptionalAuthenticatorSelection$credentials_play_services_auth_release", "parseOptionalExtensions", "parseOptionalExtensions$credentials_play_services_auth_release", "parseOptionalTimeout", "parseOptionalTimeout$credentials_play_services_auth_release", "parseOptionalWithRequiredDefaultsAttestationAndExcludeCredentials", "parseOptionalWithRequiredDefaultsAttestationAndExcludeCredentials$credentials_play_services_auth_release", "parseRequiredChallengeAndUser", "parseRequiredChallengeAndUser$credentials_play_services_auth_release", "parseRequiredRpAndParams", "parseRequiredRpAndParams$credentials_play_services_auth_release", "publicKeyCredentialResponseContainsError", "Landroidx/credentials/exceptions/CreateCredentialException;", "cred", "Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredential;", "toAssertPasskeyResponse", "Lcom/google/android/gms/auth/api/identity/SignInCredential;", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getJSON_KEY_CLIENT_DATA$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_CLIENT_DATA;
        }

        public final String getJSON_KEY_ATTESTATION_OBJ$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_ATTESTATION_OBJ;
        }

        public final String getJSON_KEY_AUTH_DATA$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_AUTH_DATA;
        }

        public final String getJSON_KEY_SIGNATURE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_SIGNATURE;
        }

        public final String getJSON_KEY_USER_HANDLE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_USER_HANDLE;
        }

        public final String getJSON_KEY_RESPONSE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_RESPONSE;
        }

        public final String getJSON_KEY_ID$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_ID;
        }

        public final String getJSON_KEY_RAW_ID$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_RAW_ID;
        }

        public final String getJSON_KEY_TYPE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_TYPE;
        }

        public final String getJSON_KEY_RPID$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_RPID;
        }

        public final String getJSON_KEY_CHALLENGE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_CHALLENGE;
        }

        public final String getJSON_KEY_APPID$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_APPID;
        }

        public final String getJSON_KEY_THIRD_PARTY_PAYMENT$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_THIRD_PARTY_PAYMENT;
        }

        public final String getJSON_KEY_AUTH_SELECTION$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_AUTH_SELECTION;
        }

        public final String getJSON_KEY_REQUIRE_RES_KEY$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_REQUIRE_RES_KEY;
        }

        public final String getJSON_KEY_RES_KEY$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_RES_KEY;
        }

        public final String getJSON_KEY_AUTH_ATTACHMENT$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_AUTH_ATTACHMENT;
        }

        public final String getJSON_KEY_TIMEOUT$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_TIMEOUT;
        }

        public final String getJSON_KEY_EXCLUDE_CREDENTIALS$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_EXCLUDE_CREDENTIALS;
        }

        public final String getJSON_KEY_TRANSPORTS$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_TRANSPORTS;
        }

        public final String getJSON_KEY_RP$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_RP;
        }

        public final String getJSON_KEY_NAME$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_NAME;
        }

        public final String getJSON_KEY_ICON$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_ICON;
        }

        public final String getJSON_KEY_ALG$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_ALG;
        }

        public final String getJSON_KEY_USER$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_USER;
        }

        public final String getJSON_KEY_DISPLAY_NAME$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_DISPLAY_NAME;
        }

        public final String getJSON_KEY_USER_VERIFICATION_METHOD$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_USER_VERIFICATION_METHOD;
        }

        public final String getJSON_KEY_KEY_PROTECTION_TYPE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_KEY_PROTECTION_TYPE;
        }

        public final String getJSON_KEY_MATCHER_PROTECTION_TYPE$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_MATCHER_PROTECTION_TYPE;
        }

        public final String getJSON_KEY_EXTENSTIONS$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_EXTENSTIONS;
        }

        public final String getJSON_KEY_ATTESTATION$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_ATTESTATION;
        }

        public final String getJSON_KEY_PUB_KEY_CRED_PARAMS$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_PUB_KEY_CRED_PARAMS;
        }

        public final String getJSON_KEY_CLIENT_EXTENSION_RESULTS$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_CLIENT_EXTENSION_RESULTS;
        }

        public final String getJSON_KEY_RK$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_RK;
        }

        public final String getJSON_KEY_CRED_PROPS$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.JSON_KEY_CRED_PROPS;
        }

        @JvmStatic
        public final PublicKeyCredentialCreationOptions convert(CreatePublicKeyCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            return convertJSON$credentials_play_services_auth_release(new JSONObject(request.getRequestJson()));
        }

        public final PublicKeyCredentialCreationOptions convertJSON$credentials_play_services_auth_release(JSONObject json) throws JSONException, CreatePublicKeyCredentialDomException {
            Intrinsics.checkNotNullParameter(json, "json");
            PublicKeyCredentialCreationOptions.Builder builder = new PublicKeyCredentialCreationOptions.Builder();
            parseRequiredChallengeAndUser$credentials_play_services_auth_release(json, builder);
            parseRequiredRpAndParams$credentials_play_services_auth_release(json, builder);
            parseOptionalWithRequiredDefaultsAttestationAndExcludeCredentials$credentials_play_services_auth_release(json, builder);
            parseOptionalTimeout$credentials_play_services_auth_release(json, builder);
            parseOptionalAuthenticatorSelection$credentials_play_services_auth_release(json, builder);
            parseOptionalExtensions$credentials_play_services_auth_release(json, builder);
            PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptionsBuild = builder.build();
            Intrinsics.checkNotNullExpressionValue(publicKeyCredentialCreationOptionsBuild, "builder.build()");
            return publicKeyCredentialCreationOptionsBuild;
        }

        public final void addAuthenticatorAttestationResponse$credentials_play_services_auth_release(byte[] clientDataJSON, byte[] attestationObject, String[] transportArray, JSONObject json) throws JSONException {
            Intrinsics.checkNotNullParameter(clientDataJSON, "clientDataJSON");
            Intrinsics.checkNotNullParameter(attestationObject, "attestationObject");
            Intrinsics.checkNotNullParameter(transportArray, "transportArray");
            Intrinsics.checkNotNullParameter(json, "json");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(getJSON_KEY_CLIENT_DATA$credentials_play_services_auth_release(), b64Encode(clientDataJSON));
            jSONObject.put(getJSON_KEY_ATTESTATION_OBJ$credentials_play_services_auth_release(), b64Encode(attestationObject));
            jSONObject.put(getJSON_KEY_TRANSPORTS$credentials_play_services_auth_release(), new JSONArray(transportArray));
            json.put(getJSON_KEY_RESPONSE$credentials_play_services_auth_release(), jSONObject);
        }

        public final String toAssertPasskeyResponse(SignInCredential cred) throws GetCredentialException {
            Intrinsics.checkNotNullParameter(cred, "cred");
            JSONObject jSONObject = new JSONObject();
            PublicKeyCredential publicKeyCredential = cred.getPublicKeyCredential();
            AuthenticatorResponse response = publicKeyCredential != null ? publicKeyCredential.getResponse() : null;
            Intrinsics.checkNotNull(response);
            if (response instanceof AuthenticatorErrorResponse) {
                AuthenticatorErrorResponse authenticatorErrorResponse = (AuthenticatorErrorResponse) response;
                ErrorCode errorCode = authenticatorErrorResponse.getErrorCode();
                Intrinsics.checkNotNullExpressionValue(errorCode, "authenticatorResponse.errorCode");
                throw beginSignInPublicKeyCredentialResponseContainsError$credentials_play_services_auth_release(errorCode, authenticatorErrorResponse.getErrorMessage());
            }
            if (response instanceof AuthenticatorAssertionResponse) {
                try {
                    String json = publicKeyCredential.toJson();
                    Intrinsics.checkNotNullExpressionValue(json, "publicKeyCred.toJson()");
                    return json;
                } catch (Throwable th) {
                    throw new GetCredentialUnknownException("The PublicKeyCredential response json had an unexpected exception when parsing: " + th.getMessage());
                }
            }
            Log.e(PublicKeyCredentialControllerUtility.TAG, "AuthenticatorResponse expected assertion response but got: " + response.getClass().getName());
            String string = jSONObject.toString();
            Intrinsics.checkNotNullExpressionValue(string, "json.toString()");
            return string;
        }

        public final BeginSignInRequest.PasskeyJsonRequestOptions convertToPlayAuthPasskeyJsonRequest(GetPublicKeyCredentialOption option) {
            Intrinsics.checkNotNullParameter(option, "option");
            BeginSignInRequest.PasskeyJsonRequestOptions passkeyJsonRequestOptionsBuild = new BeginSignInRequest.PasskeyJsonRequestOptions.Builder().setSupported(true).setRequestJson(option.getRequestJson()).build();
            Intrinsics.checkNotNullExpressionValue(passkeyJsonRequestOptionsBuild, "Builder()\n        .setSu…estJson)\n        .build()");
            return passkeyJsonRequestOptionsBuild;
        }

        @Deprecated(message = "Upgrade GMS version so 'convertToPlayAuthPasskeyJsonRequest' is used")
        public final BeginSignInRequest.PasskeysRequestOptions convertToPlayAuthPasskeyRequest(GetPublicKeyCredentialOption option) throws JSONException {
            Intrinsics.checkNotNullParameter(option, "option");
            JSONObject jSONObject = new JSONObject(option.getRequestJson());
            String rpId = jSONObject.optString(getJSON_KEY_RPID$credentials_play_services_auth_release(), "");
            Intrinsics.checkNotNullExpressionValue(rpId, "rpId");
            if (rpId.length() == 0) {
                throw new JSONException("GetPublicKeyCredentialOption - rpId not specified in the request or is unexpectedly empty");
            }
            BeginSignInRequest.PasskeysRequestOptions passkeysRequestOptionsBuild = new BeginSignInRequest.PasskeysRequestOptions.Builder().setSupported(true).setRpId(rpId).setChallenge(getChallenge(jSONObject)).build();
            Intrinsics.checkNotNullExpressionValue(passkeysRequestOptionsBuild, "Builder()\n        .setSu…allenge)\n        .build()");
            return passkeysRequestOptionsBuild;
        }

        private final byte[] getChallenge(JSONObject json) throws JSONException {
            String challengeB64 = json.optString(getJSON_KEY_CHALLENGE$credentials_play_services_auth_release(), "");
            Intrinsics.checkNotNullExpressionValue(challengeB64, "challengeB64");
            if (challengeB64.length() == 0) {
                throw new JSONException("Challenge not found in request or is unexpectedly empty");
            }
            return b64Decode(challengeB64);
        }

        public final CreateCredentialException publicKeyCredentialResponseContainsError(PublicKeyCredential cred) {
            Intrinsics.checkNotNullParameter(cred, "cred");
            AuthenticatorResponse response = cred.getResponse();
            Intrinsics.checkNotNullExpressionValue(response, "cred.response");
            if (!(response instanceof AuthenticatorErrorResponse)) {
                return null;
            }
            AuthenticatorErrorResponse authenticatorErrorResponse = (AuthenticatorErrorResponse) response;
            ErrorCode errorCode = authenticatorErrorResponse.getErrorCode();
            Intrinsics.checkNotNullExpressionValue(errorCode, "authenticatorResponse.errorCode");
            DomError domError = getOrderedErrorCodeToExceptions$credentials_play_services_auth_release().get(errorCode);
            String errorMessage = authenticatorErrorResponse.getErrorMessage();
            if (domError == null) {
                return new CreatePublicKeyCredentialDomException(new UnknownError(), "unknown fido gms exception - " + errorMessage);
            }
            if (errorCode == ErrorCode.CONSTRAINT_ERR) {
                boolean z = false;
                if (errorMessage != null && StringsKt.contains$default((CharSequence) errorMessage, (CharSequence) "Unable to get sync account", false, 2, (Object) null)) {
                    z = true;
                }
                if (z) {
                    return new CreateCredentialCancellationException("Passkey registration was cancelled by the user.");
                }
            }
            return new CreatePublicKeyCredentialDomException(domError, errorMessage);
        }

        public final GetCredentialException beginSignInPublicKeyCredentialResponseContainsError$credentials_play_services_auth_release(ErrorCode code, String msg) {
            Intrinsics.checkNotNullParameter(code, "code");
            DomError domError = getOrderedErrorCodeToExceptions$credentials_play_services_auth_release().get(code);
            if (domError == null) {
                return new GetPublicKeyCredentialDomException(new UnknownError(), "unknown fido gms exception - " + msg);
            }
            if (code == ErrorCode.CONSTRAINT_ERR) {
                boolean z = false;
                if (msg != null && StringsKt.contains$default((CharSequence) msg, (CharSequence) "Unable to get sync account", false, 2, (Object) null)) {
                    z = true;
                }
                if (z) {
                    return new GetCredentialCancellationException("Passkey retrieval was cancelled by the user.");
                }
            }
            return new GetPublicKeyCredentialDomException(domError, msg);
        }

        public final void parseOptionalExtensions$credentials_play_services_auth_release(JSONObject json, PublicKeyCredentialCreationOptions.Builder builder) throws JSONException {
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(builder, "builder");
            if (json.has(getJSON_KEY_EXTENSTIONS$credentials_play_services_auth_release())) {
                JSONObject jSONObject = json.getJSONObject(getJSON_KEY_EXTENSTIONS$credentials_play_services_auth_release());
                AuthenticationExtensions.Builder builder2 = new AuthenticationExtensions.Builder();
                String appIdExtension = jSONObject.optString(getJSON_KEY_APPID$credentials_play_services_auth_release(), "");
                Intrinsics.checkNotNullExpressionValue(appIdExtension, "appIdExtension");
                if (appIdExtension.length() > 0) {
                    builder2.setFido2Extension(new FidoAppIdExtension(appIdExtension));
                }
                if (jSONObject.optBoolean(getJSON_KEY_THIRD_PARTY_PAYMENT$credentials_play_services_auth_release(), false)) {
                    builder2.setGoogleThirdPartyPaymentExtension(new GoogleThirdPartyPaymentExtension(true));
                }
                if (jSONObject.optBoolean("uvm", false)) {
                    builder2.setUserVerificationMethodExtension(new UserVerificationMethodExtension(true));
                }
                builder.setAuthenticationExtensions(builder2.build());
            }
        }

        public final void parseOptionalAuthenticatorSelection$credentials_play_services_auth_release(JSONObject json, PublicKeyCredentialCreationOptions.Builder builder) throws JSONException {
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(builder, "builder");
            if (json.has(getJSON_KEY_AUTH_SELECTION$credentials_play_services_auth_release())) {
                JSONObject jSONObject = json.getJSONObject(getJSON_KEY_AUTH_SELECTION$credentials_play_services_auth_release());
                AuthenticatorSelectionCriteria.Builder builder2 = new AuthenticatorSelectionCriteria.Builder();
                boolean zOptBoolean = jSONObject.optBoolean(getJSON_KEY_REQUIRE_RES_KEY$credentials_play_services_auth_release(), false);
                String residentKey = jSONObject.optString(getJSON_KEY_RES_KEY$credentials_play_services_auth_release(), "");
                Intrinsics.checkNotNullExpressionValue(residentKey, "residentKey");
                builder2.setRequireResidentKey(Boolean.valueOf(zOptBoolean)).setResidentKeyRequirement(residentKey.length() > 0 ? ResidentKeyRequirement.fromString(residentKey) : null);
                String authenticatorAttachmentString = jSONObject.optString(getJSON_KEY_AUTH_ATTACHMENT$credentials_play_services_auth_release(), "");
                Intrinsics.checkNotNullExpressionValue(authenticatorAttachmentString, "authenticatorAttachmentString");
                if (authenticatorAttachmentString.length() > 0) {
                    builder2.setAttachment(Attachment.fromString(authenticatorAttachmentString));
                }
                builder.setAuthenticatorSelection(builder2.build());
            }
        }

        public final void parseOptionalTimeout$credentials_play_services_auth_release(JSONObject json, PublicKeyCredentialCreationOptions.Builder builder) {
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(builder, "builder");
            if (json.has(getJSON_KEY_TIMEOUT$credentials_play_services_auth_release())) {
                builder.setTimeoutSeconds(Double.valueOf(json.getLong(getJSON_KEY_TIMEOUT$credentials_play_services_auth_release()) / 1000));
            }
        }

        public final void parseOptionalWithRequiredDefaultsAttestationAndExcludeCredentials$credentials_play_services_auth_release(JSONObject json, PublicKeyCredentialCreationOptions.Builder builder) throws JSONException, CreatePublicKeyCredentialDomException {
            ArrayList arrayList;
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(builder, "builder");
            ArrayList arrayList2 = new ArrayList();
            if (json.has(getJSON_KEY_EXCLUDE_CREDENTIALS$credentials_play_services_auth_release())) {
                JSONArray jSONArray = json.getJSONArray(getJSON_KEY_EXCLUDE_CREDENTIALS$credentials_play_services_auth_release());
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String string = jSONObject.getString(getJSON_KEY_ID$credentials_play_services_auth_release());
                    Intrinsics.checkNotNullExpressionValue(string, "descriptorJSON.getString(JSON_KEY_ID)");
                    byte[] bArrB64Decode = b64Decode(string);
                    String descriptorType = jSONObject.getString(getJSON_KEY_TYPE$credentials_play_services_auth_release());
                    Intrinsics.checkNotNullExpressionValue(descriptorType, "descriptorType");
                    if (descriptorType.length() == 0) {
                        throw new JSONException("PublicKeyCredentialDescriptor type value is not found or unexpectedly empty");
                    }
                    if (bArrB64Decode.length == 0) {
                        throw new JSONException("PublicKeyCredentialDescriptor id value is not found or unexpectedly empty");
                    }
                    if (jSONObject.has(getJSON_KEY_TRANSPORTS$credentials_play_services_auth_release())) {
                        arrayList = new ArrayList();
                        JSONArray jSONArray2 = jSONObject.getJSONArray(getJSON_KEY_TRANSPORTS$credentials_play_services_auth_release());
                        int length2 = jSONArray2.length();
                        for (int i2 = 0; i2 < length2; i2++) {
                            try {
                                Transport transportFromString = Transport.fromString(jSONArray2.getString(i2));
                                Intrinsics.checkNotNullExpressionValue(transportFromString, "fromString(descriptorTransports.getString(j))");
                                arrayList.add(transportFromString);
                            } catch (Transport.UnsupportedTransportException e) {
                                throw new CreatePublicKeyCredentialDomException(new EncodingError(), e.getMessage());
                            }
                        }
                    } else {
                        arrayList = null;
                    }
                    arrayList2.add(new PublicKeyCredentialDescriptor(descriptorType, bArrB64Decode, arrayList));
                }
            }
            builder.setExcludeList(arrayList2);
            String attestationString = json.optString(getJSON_KEY_ATTESTATION$credentials_play_services_auth_release(), "none");
            Intrinsics.checkNotNullExpressionValue(attestationString, "attestationString");
            builder.setAttestationConveyancePreference(AttestationConveyancePreference.fromString(attestationString.length() == 0 ? "none" : attestationString));
        }

        public final void parseRequiredRpAndParams$credentials_play_services_auth_release(JSONObject json, PublicKeyCredentialCreationOptions.Builder builder) throws JSONException {
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(builder, "builder");
            JSONObject jSONObject = json.getJSONObject(getJSON_KEY_RP$credentials_play_services_auth_release());
            String rpId = jSONObject.getString(getJSON_KEY_ID$credentials_play_services_auth_release());
            String rpName = jSONObject.optString(getJSON_KEY_NAME$credentials_play_services_auth_release(), "");
            String strOptString = jSONObject.optString(getJSON_KEY_ICON$credentials_play_services_auth_release(), "");
            Intrinsics.checkNotNull(strOptString);
            if (strOptString.length() == 0) {
                strOptString = null;
            }
            Intrinsics.checkNotNullExpressionValue(rpName, "rpName");
            if (rpName.length() == 0) {
                throw new JSONException("PublicKeyCredentialCreationOptions rp name is missing or unexpectedly empty");
            }
            Intrinsics.checkNotNullExpressionValue(rpId, "rpId");
            if (rpId.length() == 0) {
                throw new JSONException("PublicKeyCredentialCreationOptions rp ID is missing or unexpectedly empty");
            }
            builder.setRp(new PublicKeyCredentialRpEntity(rpId, rpName, strOptString));
            JSONArray jSONArray = json.getJSONArray(getJSON_KEY_PUB_KEY_CRED_PARAMS$credentials_play_services_auth_release());
            ArrayList arrayList = new ArrayList();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                int i2 = (int) jSONObject2.getLong(getJSON_KEY_ALG$credentials_play_services_auth_release());
                String typeParam = jSONObject2.optString(getJSON_KEY_TYPE$credentials_play_services_auth_release(), "");
                Intrinsics.checkNotNullExpressionValue(typeParam, "typeParam");
                if (typeParam.length() == 0) {
                    throw new JSONException("PublicKeyCredentialCreationOptions PublicKeyCredentialParameter type missing or unexpectedly empty");
                }
                if (checkAlgSupported(i2)) {
                    arrayList.add(new PublicKeyCredentialParameters(typeParam, i2));
                }
            }
            builder.setParameters(arrayList);
        }

        public final void parseRequiredChallengeAndUser$credentials_play_services_auth_release(JSONObject json, PublicKeyCredentialCreationOptions.Builder builder) throws JSONException {
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(builder, "builder");
            builder.setChallenge(getChallenge(json));
            JSONObject jSONObject = json.getJSONObject(getJSON_KEY_USER$credentials_play_services_auth_release());
            String string = jSONObject.getString(getJSON_KEY_ID$credentials_play_services_auth_release());
            Intrinsics.checkNotNullExpressionValue(string, "user.getString(JSON_KEY_ID)");
            byte[] bArrB64Decode = b64Decode(string);
            String userName = jSONObject.getString(getJSON_KEY_NAME$credentials_play_services_auth_release());
            String displayName = jSONObject.getString(getJSON_KEY_DISPLAY_NAME$credentials_play_services_auth_release());
            String strOptString = jSONObject.optString(getJSON_KEY_ICON$credentials_play_services_auth_release(), "");
            Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
            if (displayName.length() == 0) {
                throw new JSONException("PublicKeyCredentialCreationOptions UserEntity missing displayName or they are unexpectedly empty");
            }
            if (bArrB64Decode.length == 0) {
                throw new JSONException("PublicKeyCredentialCreationOptions UserEntity missing user id or they are unexpectedly empty");
            }
            Intrinsics.checkNotNullExpressionValue(userName, "userName");
            if (userName.length() == 0) {
                throw new JSONException("PublicKeyCredentialCreationOptions UserEntity missing user name or they are unexpectedly empty");
            }
            builder.setUser(new PublicKeyCredentialUserEntity(bArrB64Decode, userName, strOptString, displayName));
        }

        public final byte[] b64Decode(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            byte[] bArrDecode = Base64.decode(str, 11);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(str, FLAGS)");
            return bArrDecode;
        }

        public final String b64Encode(byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            String strEncodeToString = Base64.encodeToString(data, 11);
            Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(data, FLAGS)");
            return strEncodeToString;
        }

        public final boolean checkAlgSupported(int alg) {
            try {
                COSEAlgorithmIdentifier.fromCoseValue(alg);
                return true;
            } catch (Throwable unused) {
                return false;
            }
        }

        public final LinkedHashMap<ErrorCode, DomError> getOrderedErrorCodeToExceptions$credentials_play_services_auth_release() {
            return PublicKeyCredentialControllerUtility.orderedErrorCodeToExceptions;
        }
    }
}
