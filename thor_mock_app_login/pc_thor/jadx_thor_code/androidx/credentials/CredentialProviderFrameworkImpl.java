package androidx.credentials;

import android.content.Context;
import android.credentials.ClearCredentialStateException;
import android.credentials.CreateCredentialRequest;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialRequest;
import android.credentials.PrepareGetCredentialResponse;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import android.util.Log;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.Credential;
import androidx.credentials.PrepareGetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.ClearCredentialUnknownException;
import androidx.credentials.exceptions.ClearCredentialUnsupportedException;
import androidx.credentials.exceptions.CreateCredentialCancellationException;
import androidx.credentials.exceptions.CreateCredentialCustomException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.CreateCredentialInterruptedException;
import androidx.credentials.exceptions.CreateCredentialNoCreateOptionException;
import androidx.credentials.exceptions.CreateCredentialUnknownException;
import androidx.credentials.exceptions.CreateCredentialUnsupportedException;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialCustomException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialInterruptedException;
import androidx.credentials.exceptions.GetCredentialUnknownException;
import androidx.credentials.exceptions.GetCredentialUnsupportedException;
import androidx.credentials.exceptions.NoCredentialException;
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialException;
import androidx.credentials.exceptions.publickeycredential.GetPublicKeyCredentialException;
import androidx.credentials.internal.FrameworkImplHelper;
import com.google.firebase.messaging.Constants;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: CredentialProviderFrameworkImpl.kt */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0001\u0018\u0000 =2\u00020\u0001:\u0001=B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\rH\u0002J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u0012J\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u0015H\u0000¢\u0006\u0002\b\u0016J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0000¢\u0006\u0002\b\u001bJ\u0015\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0019\u001a\u00020\u001eH\u0000¢\u0006\u0002\b\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020#H\u0016J\u0016\u0010$\u001a\u00020#2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&H\u0002J8\u0010(\u001a\u00020'2\u0006\u0010\t\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-2\u0014\u0010.\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u000100\u0012\u0004\u0012\u0002010/H\u0016J>\u00102\u001a\u00020'2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\u00180/H\u0016J>\u00104\u001a\u00020'2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\r2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u001d0/H\u0016J>\u00104\u001a\u00020'2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u00105\u001a\u0002062\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u001d0/H\u0016J6\u00107\u001a\u00020'2\u0006\u0010\t\u001a\u00020\r2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u001d0/H\u0016J\u0018\u00108\u001a\u00020'2\u0006\u0010\t\u001a\u00020\n2\u0006\u00109\u001a\u00020:H\u0003J\u0018\u0010;\u001a\u00020'2\u0006\u0010\t\u001a\u00020\r2\u0006\u00109\u001a\u00020<H\u0003R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Landroidx/credentials/CredentialProviderFrameworkImpl;", "Landroidx/credentials/CredentialProvider;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "credentialManager", "Landroid/credentials/CredentialManager;", "convertCreateRequestToFrameworkClass", "Landroid/credentials/CreateCredentialRequest;", "request", "Landroidx/credentials/CreateCredentialRequest;", "convertGetRequestToFrameworkClass", "Landroid/credentials/GetCredentialRequest;", "Landroidx/credentials/GetCredentialRequest;", "convertGetResponseToJetpackClass", "Landroidx/credentials/GetCredentialResponse;", "response", "Landroid/credentials/GetCredentialResponse;", "convertGetResponseToJetpackClass$credentials_release", "convertPrepareGetResponseToJetpackClass", "Landroidx/credentials/PrepareGetCredentialResponse;", "Landroid/credentials/PrepareGetCredentialResponse;", "convertPrepareGetResponseToJetpackClass$credentials_release", "convertToJetpackCreateException", "Landroidx/credentials/exceptions/CreateCredentialException;", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Landroid/credentials/CreateCredentialException;", "convertToJetpackCreateException$credentials_release", "convertToJetpackGetException", "Landroidx/credentials/exceptions/GetCredentialException;", "Landroid/credentials/GetCredentialException;", "convertToJetpackGetException$credentials_release", "createFrameworkClearCredentialRequest", "Landroid/credentials/ClearCredentialStateRequest;", "isAvailableOnDevice", "", "isCredmanDisabled", "handleNullCredMan", "Lkotlin/Function0;", "", "onClearCredential", "Landroidx/credentials/ClearCredentialStateRequest;", "cancellationSignal", "Landroid/os/CancellationSignal;", "executor", "Ljava/util/concurrent/Executor;", "callback", "Landroidx/credentials/CredentialManagerCallback;", "Ljava/lang/Void;", "Landroidx/credentials/exceptions/ClearCredentialException;", "onCreateCredential", "Landroidx/credentials/CreateCredentialResponse;", "onGetCredential", "pendingGetCredentialHandle", "Landroidx/credentials/PrepareGetCredentialResponse$PendingGetCredentialHandle;", "onPrepareCredential", "setOriginForCreateRequest", "builder", "Landroid/credentials/CreateCredentialRequest$Builder;", "setOriginForGetRequest", "Landroid/credentials/GetCredentialRequest$Builder;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CredentialProviderFrameworkImpl implements CredentialProvider {
    private static final String CREATE_DOM_EXCEPTION_PREFIX = "androidx.credentials.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_DOM_EXCEPTION";
    private static final Companion Companion = new Companion(null);
    private static final String GET_DOM_EXCEPTION_PREFIX = "androidx.credentials.TYPE_GET_PUBLIC_KEY_CREDENTIAL_DOM_EXCEPTION";
    private static final String TAG = "CredManProvService";
    private final android.credentials.CredentialManager credentialManager;

    public CredentialProviderFrameworkImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.credentialManager = (android.credentials.CredentialManager) context.getSystemService("credential");
    }

    @Override // androidx.credentials.CredentialProvider
    public void onPrepareCredential(GetCredentialRequest request, CancellationSignal cancellationSignal, Executor executor, final CredentialManagerCallback<PrepareGetCredentialResponse, GetCredentialException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isCredmanDisabled(new Function0<Unit>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl.onPrepareCredential.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                callback.onError(new GetCredentialUnsupportedException("Your device doesn't support credential manager"));
            }
        })) {
            return;
        }
        OutcomeReceiver<android.credentials.PrepareGetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver = new OutcomeReceiver<android.credentials.PrepareGetCredentialResponse, android.credentials.GetCredentialException>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl$onPrepareCredential$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(android.credentials.PrepareGetCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                callback.onResult(this.convertPrepareGetResponseToJetpackClass$credentials_release(response));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.credentials.GetCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                callback.onError(this.convertToJetpackGetException$credentials_release(error));
            }
        };
        android.credentials.CredentialManager credentialManager = this.credentialManager;
        Intrinsics.checkNotNull(credentialManager);
        credentialManager.prepareGetCredential(convertGetRequestToFrameworkClass(request), cancellationSignal, executor, outcomeReceiver);
    }

    @Override // androidx.credentials.CredentialProvider
    public void onGetCredential(Context context, PrepareGetCredentialResponse.PendingGetCredentialHandle pendingGetCredentialHandle, CancellationSignal cancellationSignal, Executor executor, final CredentialManagerCallback<GetCredentialResponse, GetCredentialException> callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pendingGetCredentialHandle, "pendingGetCredentialHandle");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isCredmanDisabled(new Function0<Unit>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl.onGetCredential.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                callback.onError(new GetCredentialUnsupportedException("Your device doesn't support credential manager"));
            }
        })) {
            return;
        }
        OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver = new OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl$onGetCredential$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(android.credentials.GetCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                callback.onResult(this.convertGetResponseToJetpackClass$credentials_release(response));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.credentials.GetCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                callback.onError(this.convertToJetpackGetException$credentials_release(error));
            }
        };
        android.credentials.CredentialManager credentialManager = this.credentialManager;
        Intrinsics.checkNotNull(credentialManager);
        PrepareGetCredentialResponse.PendingGetCredentialHandle frameworkHandle = pendingGetCredentialHandle.getFrameworkHandle();
        Intrinsics.checkNotNull(frameworkHandle);
        credentialManager.getCredential(context, frameworkHandle, cancellationSignal, executor, outcomeReceiver);
    }

    @Override // androidx.credentials.CredentialProvider
    public void onGetCredential(Context context, GetCredentialRequest request, CancellationSignal cancellationSignal, Executor executor, final CredentialManagerCallback<GetCredentialResponse, GetCredentialException> callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isCredmanDisabled(new Function0<Unit>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl.onGetCredential.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                callback.onError(new GetCredentialUnsupportedException("Your device doesn't support credential manager"));
            }
        })) {
            return;
        }
        OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver = new OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl$onGetCredential$outcome$2
            @Override // android.os.OutcomeReceiver
            public void onResult(android.credentials.GetCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                Log.i("CredManProvService", "GetCredentialResponse returned from framework");
                callback.onResult(this.convertGetResponseToJetpackClass$credentials_release(response));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.credentials.GetCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.i("CredManProvService", "GetCredentialResponse error returned from framework");
                callback.onError(this.convertToJetpackGetException$credentials_release(error));
            }
        };
        android.credentials.CredentialManager credentialManager = this.credentialManager;
        Intrinsics.checkNotNull(credentialManager);
        credentialManager.getCredential(context, convertGetRequestToFrameworkClass(request), cancellationSignal, executor, outcomeReceiver);
    }

    private final boolean isCredmanDisabled(Function0<Unit> handleNullCredMan) {
        if (this.credentialManager != null) {
            return false;
        }
        handleNullCredMan.invoke();
        return true;
    }

    @Override // androidx.credentials.CredentialProvider
    public void onCreateCredential(Context context, final CreateCredentialRequest request, CancellationSignal cancellationSignal, Executor executor, final CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException> callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isCredmanDisabled(new Function0<Unit>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl.onCreateCredential.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                callback.onError(new CreateCredentialUnsupportedException("Your device doesn't support credential manager"));
            }
        })) {
            return;
        }
        OutcomeReceiver<android.credentials.CreateCredentialResponse, android.credentials.CreateCredentialException> outcomeReceiver = new OutcomeReceiver<android.credentials.CreateCredentialResponse, android.credentials.CreateCredentialException>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl$onCreateCredential$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(android.credentials.CreateCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                Log.i("CredManProvService", "Create Result returned from framework: ");
                CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException> credentialManagerCallback = callback;
                CreateCredentialResponse.Companion companion = CreateCredentialResponse.INSTANCE;
                String type = request.getType();
                Bundle data = response.getData();
                Intrinsics.checkNotNullExpressionValue(data, "response.data");
                credentialManagerCallback.onResult(companion.createFrom(type, data));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.credentials.CreateCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.i("CredManProvService", "CreateCredentialResponse error returned from framework");
                callback.onError(this.convertToJetpackCreateException$credentials_release(error));
            }
        };
        android.credentials.CredentialManager credentialManager = this.credentialManager;
        Intrinsics.checkNotNull(credentialManager);
        credentialManager.createCredential(context, convertCreateRequestToFrameworkClass(request, context), cancellationSignal, executor, outcomeReceiver);
    }

    private final android.credentials.CreateCredentialRequest convertCreateRequestToFrameworkClass(CreateCredentialRequest request, Context context) {
        CreateCredentialRequest.Builder alwaysSendAppInfoToProvider = new CreateCredentialRequest.Builder(request.getType(), FrameworkImplHelper.INSTANCE.getFinalCreateCredentialData(request, context), request.getCandidateQueryData()).setIsSystemProviderRequired(request.getIsSystemProviderRequired()).setAlwaysSendAppInfoToProvider(true);
        Intrinsics.checkNotNullExpressionValue(alwaysSendAppInfoToProvider, "Builder(request.type,\n  …ndAppInfoToProvider(true)");
        setOriginForCreateRequest(request, alwaysSendAppInfoToProvider);
        android.credentials.CreateCredentialRequest createCredentialRequestBuild = alwaysSendAppInfoToProvider.build();
        Intrinsics.checkNotNullExpressionValue(createCredentialRequestBuild, "createCredentialRequestBuilder.build()");
        return createCredentialRequestBuild;
    }

    private final void setOriginForCreateRequest(CreateCredentialRequest request, CreateCredentialRequest.Builder builder) {
        if (request.getOrigin() != null) {
            builder.setOrigin(request.getOrigin());
        }
    }

    private final android.credentials.GetCredentialRequest convertGetRequestToFrameworkClass(GetCredentialRequest request) {
        GetCredentialRequest.Builder builder = new GetCredentialRequest.Builder(GetCredentialRequest.INSTANCE.toRequestDataBundle(request));
        for (CredentialOption credentialOption : request.getCredentialOptions()) {
            builder.addCredentialOption(new CredentialOption.Builder(credentialOption.getType(), credentialOption.getRequestData(), credentialOption.getCandidateQueryData()).setIsSystemProviderRequired(credentialOption.getIsSystemProviderRequired()).setAllowedProviders(credentialOption.getAllowedProviders()).build());
        }
        setOriginForGetRequest(request, builder);
        android.credentials.GetCredentialRequest getCredentialRequestBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(getCredentialRequestBuild, "builder.build()");
        return getCredentialRequestBuild;
    }

    private final void setOriginForGetRequest(GetCredentialRequest request, GetCredentialRequest.Builder builder) {
        if (request.getOrigin() != null) {
            builder.setOrigin(request.getOrigin());
        }
    }

    private final android.credentials.ClearCredentialStateRequest createFrameworkClearCredentialRequest() {
        return new android.credentials.ClearCredentialStateRequest(new Bundle());
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    public final GetCredentialException convertToJetpackGetException$credentials_release(android.credentials.GetCredentialException error) {
        Intrinsics.checkNotNullParameter(error, "error");
        String type = error.getType();
        switch (type.hashCode()) {
            case -781118336:
                if (type.equals(GetCredentialUnknownException.TYPE_GET_CREDENTIAL_UNKNOWN_EXCEPTION)) {
                    return new GetCredentialUnknownException(error.getMessage());
                }
                break;
            case -45448328:
                if (type.equals(GetCredentialInterruptedException.TYPE_GET_CREDENTIAL_INTERRUPTED_EXCEPTION)) {
                    return new GetCredentialInterruptedException(error.getMessage());
                }
                break;
            case 580557411:
                if (type.equals(GetCredentialCancellationException.TYPE_GET_CREDENTIAL_CANCELLATION_EXCEPTION)) {
                    return new GetCredentialCancellationException(error.getMessage());
                }
                break;
            case 627896683:
                if (type.equals(NoCredentialException.TYPE_FRAMEWORK_TYPE_NO_CREDENTIAL)) {
                    return new NoCredentialException(error.getMessage());
                }
                break;
        }
        String type2 = error.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "error.type");
        if (!StringsKt.startsWith$default(type2, "androidx.credentials.TYPE_GET_PUBLIC_KEY_CREDENTIAL_DOM_EXCEPTION", false, 2, (Object) null)) {
            String type3 = error.getType();
            Intrinsics.checkNotNullExpressionValue(type3, "error.type");
            return new GetCredentialCustomException(type3, error.getMessage());
        }
        GetPublicKeyCredentialException.Companion companion = GetPublicKeyCredentialException.INSTANCE;
        String type4 = error.getType();
        Intrinsics.checkNotNullExpressionValue(type4, "error.type");
        return companion.createFrom(type4, error.getMessage());
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    public final CreateCredentialException convertToJetpackCreateException$credentials_release(android.credentials.CreateCredentialException error) {
        Intrinsics.checkNotNullParameter(error, "error");
        String type = error.getType();
        switch (type.hashCode()) {
            case -2055374133:
                if (type.equals(CreateCredentialCancellationException.TYPE_CREATE_CREDENTIAL_CANCELLATION_EXCEPTION)) {
                    return new CreateCredentialCancellationException(error.getMessage());
                }
                break;
            case 1316905704:
                if (type.equals(CreateCredentialUnknownException.TYPE_CREATE_CREDENTIAL_UNKNOWN_EXCEPTION)) {
                    return new CreateCredentialUnknownException(error.getMessage());
                }
                break;
            case 2092588512:
                if (type.equals(CreateCredentialInterruptedException.TYPE_CREATE_CREDENTIAL_INTERRUPTED_EXCEPTION)) {
                    return new CreateCredentialInterruptedException(error.getMessage());
                }
                break;
            case 2131915191:
                if (type.equals(CreateCredentialNoCreateOptionException.TYPE_CREATE_CREDENTIAL_NO_CREATE_OPTION)) {
                    return new CreateCredentialNoCreateOptionException(error.getMessage());
                }
                break;
        }
        String type2 = error.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "error.type");
        if (!StringsKt.startsWith$default(type2, "androidx.credentials.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_DOM_EXCEPTION", false, 2, (Object) null)) {
            String type3 = error.getType();
            Intrinsics.checkNotNullExpressionValue(type3, "error.type");
            return new CreateCredentialCustomException(type3, error.getMessage());
        }
        CreatePublicKeyCredentialException.Companion companion = CreatePublicKeyCredentialException.INSTANCE;
        String type4 = error.getType();
        Intrinsics.checkNotNullExpressionValue(type4, "error.type");
        return companion.createFrom(type4, error.getMessage());
    }

    public final GetCredentialResponse convertGetResponseToJetpackClass$credentials_release(android.credentials.GetCredentialResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        android.credentials.Credential credential = response.getCredential();
        Intrinsics.checkNotNullExpressionValue(credential, "response.credential");
        Credential.Companion companion = Credential.INSTANCE;
        String type = credential.getType();
        Intrinsics.checkNotNullExpressionValue(type, "credential.type");
        Bundle data = credential.getData();
        Intrinsics.checkNotNullExpressionValue(data, "credential.data");
        return new GetCredentialResponse(companion.createFrom(type, data));
    }

    public final PrepareGetCredentialResponse convertPrepareGetResponseToJetpackClass$credentials_release(android.credentials.PrepareGetCredentialResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        return new PrepareGetCredentialResponse.Builder().setFrameworkResponse(response).setPendingGetCredentialHandle(new PrepareGetCredentialResponse.PendingGetCredentialHandle(response.getPendingGetCredentialHandle())).build();
    }

    @Override // androidx.credentials.CredentialProvider
    public boolean isAvailableOnDevice() {
        return Build.VERSION.SDK_INT >= 34;
    }

    @Override // androidx.credentials.CredentialProvider
    public void onClearCredential(ClearCredentialStateRequest request, CancellationSignal cancellationSignal, Executor executor, final CredentialManagerCallback<Void, ClearCredentialException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Log.i(TAG, "In CredentialProviderFrameworkImpl onClearCredential");
        if (isCredmanDisabled(new Function0<Unit>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl.onClearCredential.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                callback.onError(new ClearCredentialUnsupportedException("Your device doesn't support credential manager"));
            }
        })) {
            return;
        }
        OutcomeReceiver<Void, ClearCredentialStateException> outcomeReceiver = new OutcomeReceiver<Void, ClearCredentialStateException>() { // from class: androidx.credentials.CredentialProviderFrameworkImpl$onClearCredential$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(Void response) {
                Intrinsics.checkNotNullParameter(response, "response");
                Log.i("CredManProvService", "Clear result returned from framework: ");
                callback.onResult(response);
            }

            @Override // android.os.OutcomeReceiver
            public void onError(ClearCredentialStateException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.i("CredManProvService", "ClearCredentialStateException error returned from framework");
                callback.onError(new ClearCredentialUnknownException(null, 1, null));
            }
        };
        android.credentials.CredentialManager credentialManager = this.credentialManager;
        Intrinsics.checkNotNull(credentialManager);
        credentialManager.clearCredentialState(createFrameworkClearCredentialRequest(), cancellationSignal, executor, outcomeReceiver);
    }

    /* compiled from: CredentialProviderFrameworkImpl.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Landroidx/credentials/CredentialProviderFrameworkImpl$Companion;", "", "()V", "CREATE_DOM_EXCEPTION_PREFIX", "", "GET_DOM_EXCEPTION_PREFIX", "TAG", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
