package androidx.credentials.playservices.controllers.BeginSignIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.PasswordCredential;
import androidx.credentials.PublicKeyCredential;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialInterruptedException;
import androidx.credentials.exceptions.GetCredentialUnknownException;
import androidx.credentials.playservices.CredentialProviderPlayServicesImpl;
import androidx.credentials.playservices.HiddenActivity;
import androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController;
import androidx.credentials.playservices.controllers.CreatePublicKeyCredential.PublicKeyCredentialControllerUtility;
import androidx.credentials.playservices.controllers.CredentialProviderBaseController;
import androidx.credentials.playservices.controllers.CredentialProviderController;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;

/* compiled from: CredentialProviderBeginSignInController.kt */
@Metadata(d1 = {"\u0000a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u001d\b\u0000\u0018\u0000 .2 \u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0001:\u0001.B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u0002H\u0017J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u0004H\u0017J\u0010\u0010#\u001a\u00020$2\u0006\u0010\"\u001a\u00020\u0004H\u0002J'\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\b\u0010*\u001a\u0004\u0018\u00010+H\u0000¢\u0006\u0002\b,J6\u0010-\u001a\u00020&2\u0006\u0010 \u001a\u00020\u00022\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016R0\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u000b8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00138\u0002@\u0002X\u0083\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0014\u0010\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\u0017\u0010\r\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0010\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001e¨\u0006/"}, d2 = {"Landroidx/credentials/playservices/controllers/BeginSignIn/CredentialProviderBeginSignInController;", "Landroidx/credentials/playservices/controllers/CredentialProviderController;", "Landroidx/credentials/GetCredentialRequest;", "Lcom/google/android/gms/auth/api/identity/BeginSignInRequest;", "Lcom/google/android/gms/auth/api/identity/SignInCredential;", "Landroidx/credentials/GetCredentialResponse;", "Landroidx/credentials/exceptions/GetCredentialException;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "callback", "Landroidx/credentials/CredentialManagerCallback;", "getCallback$annotations", "()V", "getCallback", "()Landroidx/credentials/CredentialManagerCallback;", "setCallback", "(Landroidx/credentials/CredentialManagerCallback;)V", "cancellationSignal", "Landroid/os/CancellationSignal;", "getCancellationSignal$annotations", "executor", "Ljava/util/concurrent/Executor;", "getExecutor$annotations", "getExecutor", "()Ljava/util/concurrent/Executor;", "setExecutor", "(Ljava/util/concurrent/Executor;)V", "resultReceiver", "androidx/credentials/playservices/controllers/BeginSignIn/CredentialProviderBeginSignInController$resultReceiver$1", "Landroidx/credentials/playservices/controllers/BeginSignIn/CredentialProviderBeginSignInController$resultReceiver$1;", "convertRequestToPlayServices", "request", "convertResponseToCredentialManager", "response", "createGoogleIdCredential", "Lcom/google/android/libraries/identity/googleid/GoogleIdTokenCredential;", "handleResponse", "", "uniqueRequestCode", "", "resultCode", "data", "Landroid/content/Intent;", "handleResponse$credentials_play_services_auth_release", "invokePlayServices", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CredentialProviderBeginSignInController extends CredentialProviderController<GetCredentialRequest, BeginSignInRequest, SignInCredential, GetCredentialResponse, GetCredentialException> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "BeginSignIn";
    private static CredentialProviderBeginSignInController controller;
    public CredentialManagerCallback<GetCredentialResponse, GetCredentialException> callback;
    private CancellationSignal cancellationSignal;
    private final Context context;
    public Executor executor;
    private final CredentialProviderBeginSignInController$resultReceiver$1 resultReceiver;

    public static /* synthetic */ void getCallback$annotations() {
    }

    private static /* synthetic */ void getCancellationSignal$annotations() {
    }

    public static /* synthetic */ void getExecutor$annotations() {
    }

    @JvmStatic
    public static final CredentialProviderBeginSignInController getInstance(Context context) {
        return INSTANCE.getInstance(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController$resultReceiver$1] */
    public CredentialProviderBeginSignInController(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        final Handler handler = new Handler(Looper.getMainLooper());
        this.resultReceiver = new ResultReceiver(handler) { // from class: androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController$resultReceiver$1
            @Override // android.os.ResultReceiver
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Intrinsics.checkNotNullParameter(resultData, "resultData");
                if (this.this$0.maybeReportErrorFromResultReceiver(resultData, new CredentialProviderBeginSignInController$resultReceiver$1$onReceiveResult$1(CredentialProviderBaseController.INSTANCE), this.this$0.getExecutor(), this.this$0.getCallback(), this.this$0.cancellationSignal)) {
                    return;
                }
                this.this$0.handleResponse$credentials_play_services_auth_release(resultData.getInt(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG), resultCode, (Intent) resultData.getParcelable(CredentialProviderBaseController.RESULT_DATA_TAG));
            }
        };
    }

    public final CredentialManagerCallback<GetCredentialResponse, GetCredentialException> getCallback() {
        CredentialManagerCallback<GetCredentialResponse, GetCredentialException> credentialManagerCallback = this.callback;
        if (credentialManagerCallback != null) {
            return credentialManagerCallback;
        }
        Intrinsics.throwUninitializedPropertyAccessException("callback");
        return null;
    }

    public final void setCallback(CredentialManagerCallback<GetCredentialResponse, GetCredentialException> credentialManagerCallback) {
        Intrinsics.checkNotNullParameter(credentialManagerCallback, "<set-?>");
        this.callback = credentialManagerCallback;
    }

    public final Executor getExecutor() {
        Executor executor = this.executor;
        if (executor != null) {
            return executor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("executor");
        return null;
    }

    public final void setExecutor(Executor executor) {
        Intrinsics.checkNotNullParameter(executor, "<set-?>");
        this.executor = executor;
    }

    @Override // androidx.credentials.playservices.controllers.CredentialProviderController
    public void invokePlayServices(GetCredentialRequest request, CredentialManagerCallback<GetCredentialResponse, GetCredentialException> callback, Executor executor, CancellationSignal cancellationSignal) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.cancellationSignal = cancellationSignal;
        setCallback(callback);
        setExecutor(executor);
        if (CredentialProviderPlayServicesImpl.INSTANCE.cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
            return;
        }
        BeginSignInRequest beginSignInRequestConvertRequestToPlayServices = convertRequestToPlayServices(request);
        Intent intent = new Intent(this.context, (Class<?>) HiddenActivity.class);
        intent.putExtra(CredentialProviderBaseController.REQUEST_TAG, beginSignInRequestConvertRequestToPlayServices);
        generateHiddenActivityIntent(this.resultReceiver, intent, CredentialProviderBaseController.BEGIN_SIGN_IN_TAG);
        try {
            this.context.startActivity(intent);
        } catch (Exception unused) {
            CredentialProviderController.Companion companion = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(cancellationSignal, new AnonymousClass1());
        }
    }

    /* compiled from: CredentialProviderBeginSignInController.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController$invokePlayServices$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function0<Unit> {
        AnonymousClass1() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            Executor executor = CredentialProviderBeginSignInController.this.getExecutor();
            final CredentialProviderBeginSignInController credentialProviderBeginSignInController = CredentialProviderBeginSignInController.this;
            executor.execute(new Runnable() { // from class: androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController$invokePlayServices$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CredentialProviderBeginSignInController.AnonymousClass1.invoke$lambda$0(credentialProviderBeginSignInController);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(CredentialProviderBeginSignInController this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.getCallback().onError(new GetCredentialUnknownException(CredentialProviderController.ERROR_MESSAGE_START_ACTIVITY_FAILED));
        }
    }

    /* JADX WARN: Type inference failed for: r5v11, types: [T, androidx.credentials.exceptions.GetCredentialCancellationException] */
    /* JADX WARN: Type inference failed for: r5v3, types: [T, androidx.credentials.exceptions.GetCredentialUnknownException] */
    /* JADX WARN: Type inference failed for: r5v8, types: [T, androidx.credentials.exceptions.GetCredentialInterruptedException] */
    public final void handleResponse$credentials_play_services_auth_release(int uniqueRequestCode, int resultCode, Intent data) {
        CredentialProviderBaseController.Companion companion = CredentialProviderBaseController.INSTANCE;
        if (uniqueRequestCode != CredentialProviderBaseController.getCONTROLLER_REQUEST_CODE()) {
            StringBuilder sb = new StringBuilder("Returned request code ");
            CredentialProviderBaseController.Companion companion2 = CredentialProviderBaseController.INSTANCE;
            Log.w(TAG, sb.append(CredentialProviderBaseController.getCONTROLLER_REQUEST_CODE()).append(" which  does not match what was given ").append(uniqueRequestCode).toString());
            return;
        }
        CredentialProviderController.Companion companion3 = CredentialProviderController.INSTANCE;
        if (CredentialProviderController.maybeReportErrorResultCodeGet(resultCode, new Function2<CancellationSignal, Function0<? extends Unit>, Unit>() { // from class: androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController$handleResponse$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(CancellationSignal cancellationSignal, Function0<? extends Unit> function0) {
                invoke2(cancellationSignal, (Function0<Unit>) function0);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(CancellationSignal cancellationSignal, Function0<Unit> f) {
                Intrinsics.checkNotNullParameter(f, "f");
                CredentialProviderController.Companion companion4 = CredentialProviderController.INSTANCE;
                CredentialProviderController.cancelOrCallbackExceptionOrResult(cancellationSignal, f);
            }
        }, new CredentialProviderBeginSignInController$handleResponse$2(this), this.cancellationSignal)) {
            return;
        }
        try {
            SignInCredential signInCredentialFromIntent = Identity.getSignInClient(this.context).getSignInCredentialFromIntent(data);
            Intrinsics.checkNotNullExpressionValue(signInCredentialFromIntent, "getSignInClient(context)…redentialFromIntent(data)");
            GetCredentialResponse getCredentialResponseConvertResponseToCredentialManager = convertResponseToCredentialManager(signInCredentialFromIntent);
            CredentialProviderController.Companion companion4 = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(this.cancellationSignal, new CredentialProviderBeginSignInController$handleResponse$3(this, getCredentialResponseConvertResponseToCredentialManager));
        } catch (GetCredentialException e) {
            CredentialProviderController.Companion companion5 = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(this.cancellationSignal, new CredentialProviderBeginSignInController$handleResponse$5(this, e));
        } catch (ApiException e2) {
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new GetCredentialUnknownException(e2.getMessage());
            if (e2.getStatusCode() == 16) {
                objectRef.element = new GetCredentialCancellationException(e2.getMessage());
            } else if (CredentialProviderBaseController.INSTANCE.getRetryables().contains(Integer.valueOf(e2.getStatusCode()))) {
                objectRef.element = new GetCredentialInterruptedException(e2.getMessage());
            }
            CredentialProviderController.Companion companion6 = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(this.cancellationSignal, new CredentialProviderBeginSignInController$handleResponse$4(this, objectRef));
        } catch (Throwable th) {
            GetCredentialUnknownException getCredentialUnknownException = new GetCredentialUnknownException(th.getMessage());
            CredentialProviderController.Companion companion7 = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(this.cancellationSignal, new CredentialProviderBeginSignInController$handleResponse$6(this, getCredentialUnknownException));
        }
    }

    @Override // androidx.credentials.playservices.controllers.CredentialProviderController
    public BeginSignInRequest convertRequestToPlayServices(GetCredentialRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        return BeginSignInControllerUtility.INSTANCE.constructBeginSignInRequest$credentials_play_services_auth_release(request, this.context);
    }

    @Override // androidx.credentials.playservices.controllers.CredentialProviderController
    public GetCredentialResponse convertResponseToCredentialManager(SignInCredential response) throws GetCredentialUnknownException {
        PublicKeyCredential publicKeyCredential;
        Intrinsics.checkNotNullParameter(response, "response");
        if (response.getPassword() != null) {
            String id = response.getId();
            Intrinsics.checkNotNullExpressionValue(id, "response.id");
            String password = response.getPassword();
            Intrinsics.checkNotNull(password);
            publicKeyCredential = new PasswordCredential(id, password);
        } else if (response.getGoogleIdToken() != null) {
            publicKeyCredential = createGoogleIdCredential(response);
        } else if (response.getPublicKeyCredential() != null) {
            publicKeyCredential = new PublicKeyCredential(PublicKeyCredentialControllerUtility.INSTANCE.toAssertPasskeyResponse(response));
        } else {
            Log.w(TAG, "Credential returned but no google Id or password or passkey found");
            publicKeyCredential = null;
        }
        if (publicKeyCredential == null) {
            throw new GetCredentialUnknownException("When attempting to convert get response, null credential found");
        }
        return new GetCredentialResponse(publicKeyCredential);
    }

    private final GoogleIdTokenCredential createGoogleIdCredential(SignInCredential response) {
        GoogleIdTokenCredential.Builder builder = new GoogleIdTokenCredential.Builder();
        String id = response.getId();
        Intrinsics.checkNotNullExpressionValue(id, "response.id");
        GoogleIdTokenCredential.Builder id2 = builder.setId(id);
        String googleIdToken = response.getGoogleIdToken();
        Intrinsics.checkNotNull(googleIdToken);
        GoogleIdTokenCredential.Builder idToken = id2.setIdToken(googleIdToken);
        if (response.getDisplayName() != null) {
            idToken.setDisplayName(response.getDisplayName());
        }
        if (response.getGivenName() != null) {
            idToken.setGivenName(response.getGivenName());
        }
        if (response.getFamilyName() != null) {
            idToken.setFamilyName(response.getFamilyName());
        }
        if (response.getPhoneNumber() != null) {
            idToken.setPhoneNumber(response.getPhoneNumber());
        }
        if (response.getProfilePictureUri() != null) {
            idToken.setProfilePictureUri(response.getProfilePictureUri());
        }
        return idToken.build();
    }

    /* compiled from: CredentialProviderBeginSignInController.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/credentials/playservices/controllers/BeginSignIn/CredentialProviderBeginSignInController$Companion;", "", "()V", "TAG", "", "controller", "Landroidx/credentials/playservices/controllers/BeginSignIn/CredentialProviderBeginSignInController;", "getInstance", "context", "Landroid/content/Context;", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CredentialProviderBeginSignInController getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (CredentialProviderBeginSignInController.controller == null) {
                CredentialProviderBeginSignInController.controller = new CredentialProviderBeginSignInController(context);
            }
            CredentialProviderBeginSignInController credentialProviderBeginSignInController = CredentialProviderBeginSignInController.controller;
            Intrinsics.checkNotNull(credentialProviderBeginSignInController);
            return credentialProviderBeginSignInController;
        }
    }
}
