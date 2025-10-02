package androidx.credentials.playservices.controllers.CreatePassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.CreatePasswordRequest;
import androidx.credentials.CreatePasswordResponse;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.CreateCredentialUnknownException;
import androidx.credentials.playservices.CredentialProviderPlayServicesImpl;
import androidx.credentials.playservices.HiddenActivity;
import androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController;
import androidx.credentials.playservices.controllers.CredentialProviderBaseController;
import androidx.credentials.playservices.controllers.CredentialProviderController;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;
import com.google.android.gms.auth.api.identity.SignInPassword;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: CredentialProviderCreatePasswordController.kt */
@Metadata(d1 = {"\u0000K\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0014\b\u0000\u0018\u0000 !2 \u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0001:\u0001!B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0002H\u0017J\u0015\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0004H\u0017¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0000¢\u0006\u0002\b\u001fJ6\u0010 \u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00022\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016R$\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u000b8\u0002@\u0002X\u0083.¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0002@\u0002X\u0083\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0010\u0010\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0015¨\u0006\""}, d2 = {"Landroidx/credentials/playservices/controllers/CreatePassword/CredentialProviderCreatePasswordController;", "Landroidx/credentials/playservices/controllers/CredentialProviderController;", "Landroidx/credentials/CreatePasswordRequest;", "Lcom/google/android/gms/auth/api/identity/SavePasswordRequest;", "", "Landroidx/credentials/CreateCredentialResponse;", "Landroidx/credentials/exceptions/CreateCredentialException;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "callback", "Landroidx/credentials/CredentialManagerCallback;", "getCallback$annotations", "()V", "cancellationSignal", "Landroid/os/CancellationSignal;", "getCancellationSignal$annotations", "executor", "Ljava/util/concurrent/Executor;", "resultReceiver", "androidx/credentials/playservices/controllers/CreatePassword/CredentialProviderCreatePasswordController$resultReceiver$1", "Landroidx/credentials/playservices/controllers/CreatePassword/CredentialProviderCreatePasswordController$resultReceiver$1;", "convertRequestToPlayServices", "request", "convertResponseToCredentialManager", "response", "(Lkotlin/Unit;)Landroidx/credentials/CreateCredentialResponse;", "handleResponse", "uniqueRequestCode", "", "resultCode", "handleResponse$credentials_play_services_auth_release", "invokePlayServices", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CredentialProviderCreatePasswordController extends CredentialProviderController<CreatePasswordRequest, SavePasswordRequest, Unit, CreateCredentialResponse, CreateCredentialException> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "CreatePassword";
    private static CredentialProviderCreatePasswordController controller;
    private CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException> callback;
    private CancellationSignal cancellationSignal;
    private final Context context;
    private Executor executor;
    private final CredentialProviderCreatePasswordController$resultReceiver$1 resultReceiver;

    private static /* synthetic */ void getCallback$annotations() {
    }

    private static /* synthetic */ void getCancellationSignal$annotations() {
    }

    @JvmStatic
    public static final CredentialProviderCreatePasswordController getInstance(Context context) {
        return INSTANCE.getInstance(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController$resultReceiver$1] */
    public CredentialProviderCreatePasswordController(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        final Handler handler = new Handler(Looper.getMainLooper());
        this.resultReceiver = new ResultReceiver(handler) { // from class: androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController$resultReceiver$1
            @Override // android.os.ResultReceiver
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Executor executor;
                CredentialManagerCallback credentialManagerCallback;
                Intrinsics.checkNotNullParameter(resultData, "resultData");
                CredentialProviderCreatePasswordController credentialProviderCreatePasswordController = this.this$0;
                CredentialProviderCreatePasswordController$resultReceiver$1$onReceiveResult$1 credentialProviderCreatePasswordController$resultReceiver$1$onReceiveResult$1 = new CredentialProviderCreatePasswordController$resultReceiver$1$onReceiveResult$1(CredentialProviderBaseController.INSTANCE);
                Executor executor2 = this.this$0.executor;
                if (executor2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("executor");
                    executor = null;
                } else {
                    executor = executor2;
                }
                CredentialManagerCallback credentialManagerCallback2 = this.this$0.callback;
                if (credentialManagerCallback2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("callback");
                    credentialManagerCallback = null;
                } else {
                    credentialManagerCallback = credentialManagerCallback2;
                }
                if (credentialProviderCreatePasswordController.maybeReportErrorFromResultReceiver(resultData, credentialProviderCreatePasswordController$resultReceiver$1$onReceiveResult$1, executor, credentialManagerCallback, this.this$0.cancellationSignal)) {
                    return;
                }
                this.this$0.handleResponse$credentials_play_services_auth_release(resultData.getInt(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG), resultCode);
            }
        };
    }

    @Override // androidx.credentials.playservices.controllers.CredentialProviderController
    public void invokePlayServices(CreatePasswordRequest request, CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException> callback, Executor executor, CancellationSignal cancellationSignal) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.cancellationSignal = cancellationSignal;
        this.callback = callback;
        this.executor = executor;
        if (CredentialProviderPlayServicesImpl.INSTANCE.cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
            return;
        }
        SavePasswordRequest savePasswordRequestConvertRequestToPlayServices = convertRequestToPlayServices(request);
        Intent intent = new Intent(this.context, (Class<?>) HiddenActivity.class);
        intent.putExtra(CredentialProviderBaseController.REQUEST_TAG, savePasswordRequestConvertRequestToPlayServices);
        generateHiddenActivityIntent(this.resultReceiver, intent, CredentialProviderBaseController.CREATE_PASSWORD_TAG);
        try {
            this.context.startActivity(intent);
        } catch (Exception unused) {
            CredentialProviderController.Companion companion = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(cancellationSignal, new AnonymousClass1());
        }
    }

    /* compiled from: CredentialProviderCreatePasswordController.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController$invokePlayServices$1, reason: invalid class name */
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
            Executor executor = CredentialProviderCreatePasswordController.this.executor;
            if (executor == null) {
                Intrinsics.throwUninitializedPropertyAccessException("executor");
                executor = null;
            }
            final CredentialProviderCreatePasswordController credentialProviderCreatePasswordController = CredentialProviderCreatePasswordController.this;
            executor.execute(new Runnable() { // from class: androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController$invokePlayServices$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CredentialProviderCreatePasswordController.AnonymousClass1.invoke$lambda$0(credentialProviderCreatePasswordController);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(CredentialProviderCreatePasswordController this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            CredentialManagerCallback credentialManagerCallback = this$0.callback;
            if (credentialManagerCallback == null) {
                Intrinsics.throwUninitializedPropertyAccessException("callback");
                credentialManagerCallback = null;
            }
            credentialManagerCallback.onError(new CreateCredentialUnknownException(CredentialProviderController.ERROR_MESSAGE_START_ACTIVITY_FAILED));
        }
    }

    public final void handleResponse$credentials_play_services_auth_release(int uniqueRequestCode, int resultCode) {
        CredentialProviderBaseController.Companion companion = CredentialProviderBaseController.INSTANCE;
        if (uniqueRequestCode != CredentialProviderBaseController.getCONTROLLER_REQUEST_CODE()) {
            StringBuilder sb = new StringBuilder("Returned request code ");
            CredentialProviderBaseController.Companion companion2 = CredentialProviderBaseController.INSTANCE;
            Log.w(TAG, sb.append(CredentialProviderBaseController.getCONTROLLER_REQUEST_CODE()).append(" which does not match what was given ").append(uniqueRequestCode).toString());
        } else {
            CredentialProviderController.Companion companion3 = CredentialProviderController.INSTANCE;
            if (CredentialProviderController.maybeReportErrorResultCodeCreate(resultCode, new Function2<CancellationSignal, Function0<? extends Unit>, Unit>() { // from class: androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController$handleResponse$1
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
            }, new CredentialProviderCreatePasswordController$handleResponse$2(this), this.cancellationSignal)) {
                return;
            }
            CreateCredentialResponse createCredentialResponseConvertResponseToCredentialManager = convertResponseToCredentialManager(Unit.INSTANCE);
            CredentialProviderController.Companion companion4 = CredentialProviderController.INSTANCE;
            CredentialProviderController.cancelOrCallbackExceptionOrResult(this.cancellationSignal, new CredentialProviderCreatePasswordController$handleResponse$3(this, createCredentialResponseConvertResponseToCredentialManager));
        }
    }

    @Override // androidx.credentials.playservices.controllers.CredentialProviderController
    public SavePasswordRequest convertRequestToPlayServices(CreatePasswordRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        SavePasswordRequest savePasswordRequestBuild = SavePasswordRequest.builder().setSignInPassword(new SignInPassword(request.getId(), request.getPassword())).build();
        Intrinsics.checkNotNullExpressionValue(savePasswordRequestBuild, "builder().setSignInPassw…ssword)\n        ).build()");
        return savePasswordRequestBuild;
    }

    @Override // androidx.credentials.playservices.controllers.CredentialProviderController
    public CreateCredentialResponse convertResponseToCredentialManager(Unit response) {
        Intrinsics.checkNotNullParameter(response, "response");
        return new CreatePasswordResponse();
    }

    /* compiled from: CredentialProviderCreatePasswordController.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/credentials/playservices/controllers/CreatePassword/CredentialProviderCreatePasswordController$Companion;", "", "()V", "TAG", "", "controller", "Landroidx/credentials/playservices/controllers/CreatePassword/CredentialProviderCreatePasswordController;", "getInstance", "context", "Landroid/content/Context;", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CredentialProviderCreatePasswordController getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (CredentialProviderCreatePasswordController.controller == null) {
                CredentialProviderCreatePasswordController.controller = new CredentialProviderCreatePasswordController(context);
            }
            CredentialProviderCreatePasswordController credentialProviderCreatePasswordController = CredentialProviderCreatePasswordController.controller;
            Intrinsics.checkNotNull(credentialProviderCreatePasswordController);
            return credentialProviderCreatePasswordController;
        }
    }
}
