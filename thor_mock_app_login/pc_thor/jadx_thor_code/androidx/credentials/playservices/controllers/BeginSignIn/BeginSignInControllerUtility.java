package androidx.credentials.playservices.controllers.BeginSignIn;

import android.content.Context;
import androidx.credentials.CredentialOption;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetPasswordOption;
import androidx.credentials.GetPublicKeyCredentialOption;
import androidx.credentials.playservices.controllers.CreatePublicKeyCredential.PublicKeyCredentialControllerUtility;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BeginSignInControllerUtility.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/playservices/controllers/BeginSignIn/BeginSignInControllerUtility;", "", "()V", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BeginSignInControllerUtility {
    private static final long AUTH_MIN_VERSION_JSON_PARSING = 231815000;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "BeginSignInUtility";

    /* compiled from: BeginSignInControllerUtility.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0000¢\u0006\u0002\b\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Landroidx/credentials/playservices/controllers/BeginSignIn/BeginSignInControllerUtility$Companion;", "", "()V", "AUTH_MIN_VERSION_JSON_PARSING", "", "TAG", "", "constructBeginSignInRequest", "Lcom/google/android/gms/auth/api/identity/BeginSignInRequest;", "request", "Landroidx/credentials/GetCredentialRequest;", "context", "Landroid/content/Context;", "constructBeginSignInRequest$credentials_play_services_auth_release", "convertToGoogleIdTokenOption", "Lcom/google/android/gms/auth/api/identity/BeginSignInRequest$GoogleIdTokenRequestOptions;", "option", "Lcom/google/android/libraries/identity/googleid/GetGoogleIdOption;", "determineDeviceGMSVersionCode", "needsBackwardsCompatibleRequest", "", "curAuthVersion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean needsBackwardsCompatibleRequest(long curAuthVersion) {
            return curAuthVersion < BeginSignInControllerUtility.AUTH_MIN_VERSION_JSON_PARSING;
        }

        private Companion() {
        }

        public final BeginSignInRequest constructBeginSignInRequest$credentials_play_services_auth_release(GetCredentialRequest request, Context context) {
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(context, "context");
            BeginSignInRequest.Builder builder = new BeginSignInRequest.Builder();
            boolean z = false;
            boolean z2 = false;
            for (CredentialOption credentialOption : request.getCredentialOptions()) {
                if (credentialOption instanceof GetPasswordOption) {
                    builder.setPasswordRequestOptions(new BeginSignInRequest.PasswordRequestOptions.Builder().setSupported(true).build());
                    z = z || credentialOption.getIsAutoSelectAllowed();
                } else if ((credentialOption instanceof GetPublicKeyCredentialOption) && !z2) {
                    if (needsBackwardsCompatibleRequest(determineDeviceGMSVersionCode(context))) {
                        builder.setPasskeysSignInRequestOptions(PublicKeyCredentialControllerUtility.INSTANCE.convertToPlayAuthPasskeyRequest((GetPublicKeyCredentialOption) credentialOption));
                    } else {
                        builder.setPasskeyJsonSignInRequestOptions(PublicKeyCredentialControllerUtility.INSTANCE.convertToPlayAuthPasskeyJsonRequest((GetPublicKeyCredentialOption) credentialOption));
                    }
                    z2 = true;
                } else if (credentialOption instanceof GetGoogleIdOption) {
                    GetGoogleIdOption getGoogleIdOption = (GetGoogleIdOption) credentialOption;
                    builder.setGoogleIdTokenRequestOptions(convertToGoogleIdTokenOption(getGoogleIdOption));
                    if (z || getGoogleIdOption.getZzg()) {
                    }
                }
            }
            BeginSignInRequest beginSignInRequestBuild = builder.setAutoSelectEnabled(z).build();
            Intrinsics.checkNotNullExpressionValue(beginSignInRequestBuild, "requestBuilder\n         …\n                .build()");
            return beginSignInRequestBuild;
        }

        private final long determineDeviceGMSVersionCode(Context context) {
            Intrinsics.checkNotNullExpressionValue(context.getPackageManager(), "context.packageManager");
            return r3.getPackageInfo("com.google.android.gms", 0).versionCode;
        }

        private final BeginSignInRequest.GoogleIdTokenRequestOptions convertToGoogleIdTokenOption(GetGoogleIdOption option) {
            BeginSignInRequest.GoogleIdTokenRequestOptions.Builder supported = BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setFilterByAuthorizedAccounts(option.getZzc()).setNonce(option.getZzb()).setRequestVerifiedPhoneNumber(option.getZzf()).setServerClientId(option.getZza()).setSupported(true);
            Intrinsics.checkNotNullExpressionValue(supported, "builder()\n              …      .setSupported(true)");
            if (option.getZzd() != null) {
                String zzd = option.getZzd();
                Intrinsics.checkNotNull(zzd);
                supported.associateLinkedAccounts(zzd, option.getIdTokenDepositionScopes());
            }
            BeginSignInRequest.GoogleIdTokenRequestOptions googleIdTokenRequestOptionsBuild = supported.build();
            Intrinsics.checkNotNullExpressionValue(googleIdTokenRequestOptionsBuild, "idTokenOption.build()");
            return googleIdTokenRequestOptionsBuild;
        }
    }
}
