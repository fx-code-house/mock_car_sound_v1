package androidx.credentials.playservices;

import android.content.Context;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.CreateCredentialRequest;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.CreatePasswordRequest;
import androidx.credentials.CreatePublicKeyCredentialRequest;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CredentialOption;
import androidx.credentials.CredentialProvider;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.playservices.CredentialProviderPlayServicesImpl;
import androidx.credentials.playservices.controllers.BeginSignIn.CredentialProviderBeginSignInController;
import androidx.credentials.playservices.controllers.CreatePassword.CredentialProviderCreatePasswordController;
import androidx.credentials.playservices.controllers.CreatePublicKeyCredential.CredentialProviderCreatePublicKeyCredentialController;
import androidx.credentials.playservices.controllers.GetSignInIntent.CredentialProviderGetSignInIntentController;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: CredentialProviderPlayServicesImpl.kt */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 %2\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J8\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0014\u0010\u0019\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001b\u0012\u0004\u0012\u00020\u001c0\u001aH\u0016J>\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u001e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001aH\u0016J>\u0010!\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\"2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\u001aH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006&"}, d2 = {"Landroidx/credentials/playservices/CredentialProviderPlayServicesImpl;", "Landroidx/credentials/CredentialProvider;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "googleApiAvailability", "Lcom/google/android/gms/common/GoogleApiAvailability;", "getGoogleApiAvailability$annotations", "()V", "getGoogleApiAvailability", "()Lcom/google/android/gms/common/GoogleApiAvailability;", "setGoogleApiAvailability", "(Lcom/google/android/gms/common/GoogleApiAvailability;)V", "isAvailableOnDevice", "", "isGooglePlayServicesAvailable", "", "onClearCredential", "", "request", "Landroidx/credentials/ClearCredentialStateRequest;", "cancellationSignal", "Landroid/os/CancellationSignal;", "executor", "Ljava/util/concurrent/Executor;", "callback", "Landroidx/credentials/CredentialManagerCallback;", "Ljava/lang/Void;", "Landroidx/credentials/exceptions/ClearCredentialException;", "onCreateCredential", "Landroidx/credentials/CreateCredentialRequest;", "Landroidx/credentials/CreateCredentialResponse;", "Landroidx/credentials/exceptions/CreateCredentialException;", "onGetCredential", "Landroidx/credentials/GetCredentialRequest;", "Landroidx/credentials/GetCredentialResponse;", "Landroidx/credentials/exceptions/GetCredentialException;", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CredentialProviderPlayServicesImpl implements CredentialProvider {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "PlayServicesImpl";
    private final Context context;
    private GoogleApiAvailability googleApiAvailability;

    public static /* synthetic */ void getGoogleApiAvailability$annotations() {
    }

    public CredentialProviderPlayServicesImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Intrinsics.checkNotNullExpressionValue(googleApiAvailability, "getInstance()");
        this.googleApiAvailability = googleApiAvailability;
    }

    public final GoogleApiAvailability getGoogleApiAvailability() {
        return this.googleApiAvailability;
    }

    public final void setGoogleApiAvailability(GoogleApiAvailability googleApiAvailability) {
        Intrinsics.checkNotNullParameter(googleApiAvailability, "<set-?>");
        this.googleApiAvailability = googleApiAvailability;
    }

    @Override // androidx.credentials.CredentialProvider
    public void onGetCredential(Context context, GetCredentialRequest request, CancellationSignal cancellationSignal, Executor executor, CredentialManagerCallback<GetCredentialResponse, GetCredentialException> callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Companion companion = INSTANCE;
        if (companion.cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
            return;
        }
        if (companion.isGetSignInIntentRequest$credentials_play_services_auth_release(request)) {
            new CredentialProviderGetSignInIntentController(context).invokePlayServices(request, callback, executor, cancellationSignal);
        } else {
            new CredentialProviderBeginSignInController(context).invokePlayServices(request, callback, executor, cancellationSignal);
        }
    }

    @Override // androidx.credentials.CredentialProvider
    public void onCreateCredential(Context context, CreateCredentialRequest request, CancellationSignal cancellationSignal, Executor executor, CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException> callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (INSTANCE.cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
            return;
        }
        if (request instanceof CreatePasswordRequest) {
            CredentialProviderCreatePasswordController.INSTANCE.getInstance(context).invokePlayServices((CreatePasswordRequest) request, callback, executor, cancellationSignal);
        } else {
            if (request instanceof CreatePublicKeyCredentialRequest) {
                CredentialProviderCreatePublicKeyCredentialController.INSTANCE.getInstance(context).invokePlayServices((CreatePublicKeyCredentialRequest) request, callback, executor, cancellationSignal);
                return;
            }
            throw new UnsupportedOperationException("Create Credential request is unsupported, not password or publickeycredential");
        }
    }

    @Override // androidx.credentials.CredentialProvider
    public boolean isAvailableOnDevice() {
        int iIsGooglePlayServicesAvailable = isGooglePlayServicesAvailable(this.context);
        boolean z = iIsGooglePlayServicesAvailable == 0;
        if (!z) {
            Log.w(TAG, "Connection with Google Play Services was not successful. Connection result is: " + new ConnectionResult(iIsGooglePlayServicesAvailable));
        }
        return z;
    }

    private final int isGooglePlayServicesAvailable(Context context) {
        return this.googleApiAvailability.isGooglePlayServicesAvailable(context);
    }

    @Override // androidx.credentials.CredentialProvider
    public void onClearCredential(ClearCredentialStateRequest request, final CancellationSignal cancellationSignal, final Executor executor, final CredentialManagerCallback<Void, ClearCredentialException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (INSTANCE.cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
            return;
        }
        Task<Void> taskSignOut = Identity.getSignInClient(this.context).signOut();
        final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: androidx.credentials.playservices.CredentialProviderPlayServicesImpl.onClearCredential.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* compiled from: CredentialProviderPlayServicesImpl.kt */
            @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
            /* renamed from: androidx.credentials.playservices.CredentialProviderPlayServicesImpl$onClearCredential$1$1, reason: invalid class name and collision with other inner class name */
            static final class C00011 extends Lambda implements Function0<Unit> {
                final /* synthetic */ CredentialManagerCallback<Void, ClearCredentialException> $callback;
                final /* synthetic */ Executor $executor;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00011(Executor executor, CredentialManagerCallback<Void, ClearCredentialException> credentialManagerCallback) {
                    super(0);
                    this.$executor = executor;
                    this.$callback = credentialManagerCallback;
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Log.i(CredentialProviderPlayServicesImpl.TAG, "During clear credential, signed out successfully!");
                    Executor executor = this.$executor;
                    final CredentialManagerCallback<Void, ClearCredentialException> credentialManagerCallback = this.$callback;
                    executor.execute(new Runnable() { // from class: androidx.credentials.playservices.CredentialProviderPlayServicesImpl$onClearCredential$1$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CredentialProviderPlayServicesImpl.AnonymousClass1.C00011.invoke$lambda$0(credentialManagerCallback);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static final void invoke$lambda$0(CredentialManagerCallback callback) {
                    Intrinsics.checkNotNullParameter(callback, "$callback");
                    callback.onResult(null);
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                invoke2(r1);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Void r5) {
                CredentialProviderPlayServicesImpl.INSTANCE.cancellationReviewerWithCallback$credentials_play_services_auth_release(cancellationSignal, new C00011(executor, callback));
            }
        };
        taskSignOut.addOnSuccessListener(new OnSuccessListener() { // from class: androidx.credentials.playservices.CredentialProviderPlayServicesImpl$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                CredentialProviderPlayServicesImpl.onClearCredential$lambda$0(function1, obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: androidx.credentials.playservices.CredentialProviderPlayServicesImpl$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                CredentialProviderPlayServicesImpl.onClearCredential$lambda$2(this.f$0, cancellationSignal, executor, callback, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onClearCredential$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onClearCredential$lambda$2(CredentialProviderPlayServicesImpl this$0, CancellationSignal cancellationSignal, Executor executor, CredentialManagerCallback callback, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(executor, "$executor");
        Intrinsics.checkNotNullParameter(callback, "$callback");
        Intrinsics.checkNotNullParameter(e, "e");
        INSTANCE.cancellationReviewerWithCallback$credentials_play_services_auth_release(cancellationSignal, new CredentialProviderPlayServicesImpl$onClearCredential$2$1$1(e, executor, callback));
    }

    /* compiled from: CredentialProviderPlayServicesImpl.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0000¢\u0006\u0002\b\tJ%\u0010\n\u001a\u00020\u000b2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u0000¢\u0006\u0002\b\u000eJ\u0015\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Landroidx/credentials/playservices/CredentialProviderPlayServicesImpl$Companion;", "", "()V", "TAG", "", "cancellationReviewer", "", "cancellationSignal", "Landroid/os/CancellationSignal;", "cancellationReviewer$credentials_play_services_auth_release", "cancellationReviewerWithCallback", "", "callback", "Lkotlin/Function0;", "cancellationReviewerWithCallback$credentials_play_services_auth_release", "isGetSignInIntentRequest", "request", "Landroidx/credentials/GetCredentialRequest;", "isGetSignInIntentRequest$credentials_play_services_auth_release", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void cancellationReviewerWithCallback$credentials_play_services_auth_release(CancellationSignal cancellationSignal, Function0<Unit> callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            if (cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
                return;
            }
            callback.invoke();
        }

        public final boolean cancellationReviewer$credentials_play_services_auth_release(CancellationSignal cancellationSignal) {
            if (cancellationSignal != null) {
                if (!cancellationSignal.isCanceled()) {
                    return false;
                }
                Log.i(CredentialProviderPlayServicesImpl.TAG, "the flow has been canceled");
                return true;
            }
            Log.i(CredentialProviderPlayServicesImpl.TAG, "No cancellationSignal found");
            return false;
        }

        public final boolean isGetSignInIntentRequest$credentials_play_services_auth_release(GetCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            Iterator<CredentialOption> it = request.getCredentialOptions().iterator();
            while (it.hasNext()) {
                if (it.next() instanceof GetSignInWithGoogleOption) {
                    return true;
                }
            }
            return false;
        }
    }
}
