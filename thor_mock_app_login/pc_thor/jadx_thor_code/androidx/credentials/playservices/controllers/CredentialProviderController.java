package androidx.credentials.playservices.controllers;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.exceptions.CreateCredentialCancellationException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.CreateCredentialUnknownException;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialUnknownException;
import androidx.credentials.playservices.CredentialProviderPlayServicesImpl;
import androidx.credentials.playservices.controllers.CredentialProviderController;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;

/* compiled from: CredentialProviderController.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b \u0018\u0000  *\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0002*\b\b\u0002\u0010\u0004*\u00020\u0002*\b\b\u0003\u0010\u0005*\u00020\u0002*\b\b\u0004\u0010\u0006*\u00020\u00022\u00020\u0007:\u0001 B\r\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0015\u0010\u000b\u001a\u00028\u00012\u0006\u0010\f\u001a\u00028\u0000H$¢\u0006\u0002\u0010\rJ\u0015\u0010\u000e\u001a\u00028\u00032\u0006\u0010\u000f\u001a\u00028\u0002H$¢\u0006\u0002\u0010\rJ;\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00028\u00002\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H&¢\u0006\u0002\u0010\u0018JT\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u001c\u0010\u001d\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0012\u0004\u0012\u00028\u00040\u001e2\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0004R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Landroidx/credentials/playservices/controllers/CredentialProviderController;", "T1", "", "T2", "R2", "R1", "E1", "Landroidx/credentials/playservices/controllers/CredentialProviderBaseController;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "convertRequestToPlayServices", "request", "(Ljava/lang/Object;)Ljava/lang/Object;", "convertResponseToCredentialManager", "response", "invokePlayServices", "", "callback", "Landroidx/credentials/CredentialManagerCallback;", "executor", "Ljava/util/concurrent/Executor;", "cancellationSignal", "Landroid/os/CancellationSignal;", "(Ljava/lang/Object;Landroidx/credentials/CredentialManagerCallback;Ljava/util/concurrent/Executor;Landroid/os/CancellationSignal;)V", "maybeReportErrorFromResultReceiver", "", "resultData", "Landroid/os/Bundle;", "conversionFn", "Lkotlin/Function2;", "", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class CredentialProviderController<T1, T2, R2, R1, E1> extends CredentialProviderBaseController {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String ERROR_MESSAGE_START_ACTIVITY_FAILED = "Failed to launch the selector UI. Hint: ensure the `context` parameter is an Activity-based context.";
    private final Context context;

    /* JADX INFO: Access modifiers changed from: protected */
    @JvmStatic
    public static final void cancelOrCallbackExceptionOrResult(CancellationSignal cancellationSignal, Function0<Unit> function0) {
        INSTANCE.cancelOrCallbackExceptionOrResult(cancellationSignal, function0);
    }

    @JvmStatic
    protected static final boolean maybeReportErrorResultCodeCreate(int i, Function2<? super CancellationSignal, ? super Function0<Unit>, Unit> function2, Function1<? super CreateCredentialException, Unit> function1, CancellationSignal cancellationSignal) {
        return INSTANCE.maybeReportErrorResultCodeCreate(i, function2, function1, cancellationSignal);
    }

    @JvmStatic
    protected static final boolean maybeReportErrorResultCodeGet(int i, Function2<? super CancellationSignal, ? super Function0<Unit>, Unit> function2, Function1<? super GetCredentialException, Unit> function1, CancellationSignal cancellationSignal) {
        return INSTANCE.maybeReportErrorResultCodeGet(i, function2, function1, cancellationSignal);
    }

    protected abstract T2 convertRequestToPlayServices(T1 request);

    protected abstract R1 convertResponseToCredentialManager(R2 response);

    public abstract void invokePlayServices(T1 request, CredentialManagerCallback<R1, E1> callback, Executor executor, CancellationSignal cancellationSignal);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialProviderController(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* compiled from: CredentialProviderController.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0005J\r\u0010\u000b\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\fJ\u0015\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0010JP\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000f2 \u0010\u0013\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\n\u0012\u0004\u0012\u00020\u00060\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00060\u00162\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0005JP\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000f2 \u0010\u0013\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\n\u0012\u0004\u0012\u00020\u00060\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00060\u00162\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0005R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Landroidx/credentials/playservices/controllers/CredentialProviderController$Companion;", "", "()V", "ERROR_MESSAGE_START_ACTIVITY_FAILED", "", "cancelOrCallbackExceptionOrResult", "", "cancellationSignal", "Landroid/os/CancellationSignal;", "onResultOrException", "Lkotlin/Function0;", "generateErrorStringCanceled", "generateErrorStringCanceled$credentials_play_services_auth_release", "generateErrorStringUnknown", "resultCode", "", "generateErrorStringUnknown$credentials_play_services_auth_release", "maybeReportErrorResultCodeCreate", "", "cancelOnError", "Lkotlin/Function2;", "onError", "Lkotlin/Function1;", "Landroidx/credentials/exceptions/CreateCredentialException;", "maybeReportErrorResultCodeGet", "Landroidx/credentials/exceptions/GetCredentialException;", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String generateErrorStringCanceled$credentials_play_services_auth_release() {
            return "activity is cancelled by the user.";
        }

        private Companion() {
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [T, androidx.credentials.exceptions.CreateCredentialUnknownException] */
        /* JADX WARN: Type inference failed for: r4v4, types: [T, androidx.credentials.exceptions.CreateCredentialCancellationException] */
        @JvmStatic
        protected final boolean maybeReportErrorResultCodeCreate(int resultCode, Function2<? super CancellationSignal, ? super Function0<Unit>, Unit> cancelOnError, final Function1<? super CreateCredentialException, Unit> onError, CancellationSignal cancellationSignal) {
            Intrinsics.checkNotNullParameter(cancelOnError, "cancelOnError");
            Intrinsics.checkNotNullParameter(onError, "onError");
            if (resultCode == -1) {
                return false;
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new CreateCredentialUnknownException(generateErrorStringUnknown$credentials_play_services_auth_release(resultCode));
            if (resultCode == 0) {
                objectRef.element = new CreateCredentialCancellationException(generateErrorStringCanceled$credentials_play_services_auth_release());
            }
            cancelOnError.invoke(cancellationSignal, new Function0<Unit>() { // from class: androidx.credentials.playservices.controllers.CredentialProviderController$Companion$maybeReportErrorResultCodeCreate$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
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
                    onError.invoke(objectRef.element);
                }
            });
            return true;
        }

        public final String generateErrorStringUnknown$credentials_play_services_auth_release(int resultCode) {
            return "activity with result code: " + resultCode + " indicating not RESULT_OK";
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [T, androidx.credentials.exceptions.GetCredentialUnknownException] */
        /* JADX WARN: Type inference failed for: r4v4, types: [T, androidx.credentials.exceptions.GetCredentialCancellationException] */
        @JvmStatic
        protected final boolean maybeReportErrorResultCodeGet(int resultCode, Function2<? super CancellationSignal, ? super Function0<Unit>, Unit> cancelOnError, final Function1<? super GetCredentialException, Unit> onError, CancellationSignal cancellationSignal) {
            Intrinsics.checkNotNullParameter(cancelOnError, "cancelOnError");
            Intrinsics.checkNotNullParameter(onError, "onError");
            if (resultCode == -1) {
                return false;
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new GetCredentialUnknownException(generateErrorStringUnknown$credentials_play_services_auth_release(resultCode));
            if (resultCode == 0) {
                objectRef.element = new GetCredentialCancellationException(generateErrorStringCanceled$credentials_play_services_auth_release());
            }
            cancelOnError.invoke(cancellationSignal, new Function0<Unit>() { // from class: androidx.credentials.playservices.controllers.CredentialProviderController$Companion$maybeReportErrorResultCodeGet$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
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
                    onError.invoke(objectRef.element);
                }
            });
            return true;
        }

        @JvmStatic
        protected final void cancelOrCallbackExceptionOrResult(CancellationSignal cancellationSignal, Function0<Unit> onResultOrException) {
            Intrinsics.checkNotNullParameter(onResultOrException, "onResultOrException");
            if (CredentialProviderPlayServicesImpl.INSTANCE.cancellationReviewer$credentials_play_services_auth_release(cancellationSignal)) {
                return;
            }
            onResultOrException.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean maybeReportErrorFromResultReceiver(Bundle resultData, Function2<? super String, ? super String, ? extends E1> conversionFn, Executor executor, CredentialManagerCallback<R1, E1> callback, CancellationSignal cancellationSignal) {
        Intrinsics.checkNotNullParameter(resultData, "resultData");
        Intrinsics.checkNotNullParameter(conversionFn, "conversionFn");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (!resultData.getBoolean(CredentialProviderBaseController.FAILURE_RESPONSE_TAG)) {
            return false;
        }
        cancelOrCallbackExceptionOrResult(cancellationSignal, new AnonymousClass1(executor, callback, conversionFn.invoke(resultData.getString(CredentialProviderBaseController.EXCEPTION_TYPE_TAG), resultData.getString(CredentialProviderBaseController.EXCEPTION_MESSAGE_TAG))));
        return true;
    }

    /* compiled from: CredentialProviderController.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\b\b\u0002\u0010\u0005*\u00020\u0003\"\b\b\u0003\u0010\u0006*\u00020\u0003\"\b\b\u0004\u0010\u0007*\u00020\u0003H\n¢\u0006\u0002\b\b"}, d2 = {"<anonymous>", "", "T1", "", "T2", "R2", "R1", "E1", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.credentials.playservices.controllers.CredentialProviderController$maybeReportErrorFromResultReceiver$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function0<Unit> {
        final /* synthetic */ CredentialManagerCallback<R1, E1> $callback;
        final /* synthetic */ E1 $exception;
        final /* synthetic */ Executor $executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Executor executor, CredentialManagerCallback<R1, E1> credentialManagerCallback, E1 e1) {
            super(0);
            this.$executor = executor;
            this.$callback = credentialManagerCallback;
            this.$exception = e1;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(CredentialManagerCallback callback, Object exception) {
            Intrinsics.checkNotNullParameter(callback, "$callback");
            Intrinsics.checkNotNullParameter(exception, "$exception");
            callback.onError(exception);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            Executor executor = this.$executor;
            final CredentialManagerCallback<R1, E1> credentialManagerCallback = this.$callback;
            final E1 e1 = this.$exception;
            executor.execute(new Runnable() { // from class: androidx.credentials.playservices.controllers.CredentialProviderController$maybeReportErrorFromResultReceiver$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CredentialProviderController.AnonymousClass1.invoke$lambda$0(credentialManagerCallback, e1);
                }
            });
        }
    }
}
