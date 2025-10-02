package androidx.credentials.provider;

import android.credentials.ClearCredentialStateException;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import android.service.credentials.ClearCredentialStateRequest;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.provider.utils.BeginCreateCredentialUtil;
import androidx.credentials.provider.utils.BeginGetCredentialUtil;
import androidx.credentials.provider.utils.ClearCredentialUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CredentialProviderService.kt */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\"J,\u0010%\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'0\"H&J*\u0010(\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020)2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+0\"J,\u0010,\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020.0\"H&J*\u0010/\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u0002002\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002020\"J.\u00103\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 2\u0014\u0010!\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u000101\u0012\u0004\u0012\u0002040\"H&R&\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR*\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\t8G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR*\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u000f8G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R*\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\u0010\u0003\u001a\u0004\u0018\u00010\u00158G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u00065"}, d2 = {"Landroidx/credentials/provider/CredentialProviderService;", "Landroid/service/credentials/CredentialProviderService;", "()V", "<set-?>", "", "isTestMode", "()Z", "setTestMode", "(Z)V", "Landroidx/credentials/provider/ProviderClearCredentialStateRequest;", "lastClearRequest", "getLastClearRequest", "()Landroidx/credentials/provider/ProviderClearCredentialStateRequest;", "setLastClearRequest", "(Landroidx/credentials/provider/ProviderClearCredentialStateRequest;)V", "Landroidx/credentials/provider/BeginCreateCredentialRequest;", "lastCreateRequest", "getLastCreateRequest", "()Landroidx/credentials/provider/BeginCreateCredentialRequest;", "setLastCreateRequest", "(Landroidx/credentials/provider/BeginCreateCredentialRequest;)V", "Landroidx/credentials/provider/BeginGetCredentialRequest;", "lastGetRequest", "getLastGetRequest", "()Landroidx/credentials/provider/BeginGetCredentialRequest;", "setLastGetRequest", "(Landroidx/credentials/provider/BeginGetCredentialRequest;)V", "onBeginCreateCredential", "", "request", "Landroid/service/credentials/BeginCreateCredentialRequest;", "cancellationSignal", "Landroid/os/CancellationSignal;", "callback", "Landroid/os/OutcomeReceiver;", "Landroid/service/credentials/BeginCreateCredentialResponse;", "Landroid/credentials/CreateCredentialException;", "onBeginCreateCredentialRequest", "Landroidx/credentials/provider/BeginCreateCredentialResponse;", "Landroidx/credentials/exceptions/CreateCredentialException;", "onBeginGetCredential", "Landroid/service/credentials/BeginGetCredentialRequest;", "Landroid/service/credentials/BeginGetCredentialResponse;", "Landroid/credentials/GetCredentialException;", "onBeginGetCredentialRequest", "Landroidx/credentials/provider/BeginGetCredentialResponse;", "Landroidx/credentials/exceptions/GetCredentialException;", "onClearCredentialState", "Landroid/service/credentials/ClearCredentialStateRequest;", "Ljava/lang/Void;", "Landroid/credentials/ClearCredentialStateException;", "onClearCredentialStateRequest", "Landroidx/credentials/exceptions/ClearCredentialException;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class CredentialProviderService extends android.service.credentials.CredentialProviderService {
    private boolean isTestMode;
    private ProviderClearCredentialStateRequest lastClearRequest;
    private BeginCreateCredentialRequest lastCreateRequest;
    private BeginGetCredentialRequest lastGetRequest;

    public abstract void onBeginCreateCredentialRequest(BeginCreateCredentialRequest request, CancellationSignal cancellationSignal, OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException> callback);

    public abstract void onBeginGetCredentialRequest(BeginGetCredentialRequest request, CancellationSignal cancellationSignal, OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException> callback);

    public abstract void onClearCredentialStateRequest(ProviderClearCredentialStateRequest request, CancellationSignal cancellationSignal, OutcomeReceiver<Void, ClearCredentialException> callback);

    /* renamed from: isTestMode, reason: from getter */
    public final boolean getIsTestMode() {
        return this.isTestMode;
    }

    public final void setTestMode(boolean z) {
        this.isTestMode = z;
    }

    public final BeginCreateCredentialRequest getLastCreateRequest() {
        return this.lastCreateRequest;
    }

    public final void setLastCreateRequest(BeginCreateCredentialRequest beginCreateCredentialRequest) {
        this.lastCreateRequest = beginCreateCredentialRequest;
    }

    public final BeginGetCredentialRequest getLastGetRequest() {
        return this.lastGetRequest;
    }

    public final void setLastGetRequest(BeginGetCredentialRequest beginGetCredentialRequest) {
        this.lastGetRequest = beginGetCredentialRequest;
    }

    public final ProviderClearCredentialStateRequest getLastClearRequest() {
        return this.lastClearRequest;
    }

    public final void setLastClearRequest(ProviderClearCredentialStateRequest providerClearCredentialStateRequest) {
        this.lastClearRequest = providerClearCredentialStateRequest;
    }

    @Override // android.service.credentials.CredentialProviderService
    public final void onBeginGetCredential(android.service.credentials.BeginGetCredentialRequest request, CancellationSignal cancellationSignal, final OutcomeReceiver<android.service.credentials.BeginGetCredentialResponse, android.credentials.GetCredentialException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(cancellationSignal, "cancellationSignal");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BeginGetCredentialRequest beginGetCredentialRequestConvertToJetpackRequest$credentials_release = BeginGetCredentialUtil.INSTANCE.convertToJetpackRequest$credentials_release(request);
        OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException> outcomeReceiver = new OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException>() { // from class: androidx.credentials.provider.CredentialProviderService$onBeginGetCredential$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(BeginGetCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                callback.onResult(BeginGetCredentialUtil.INSTANCE.convertToFrameworkResponse(response));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(GetCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                callback.onError(new android.credentials.GetCredentialException(error.getType(), error.getMessage()));
            }
        };
        if (this.isTestMode) {
            this.lastGetRequest = beginGetCredentialRequestConvertToJetpackRequest$credentials_release;
        }
        onBeginGetCredentialRequest(beginGetCredentialRequestConvertToJetpackRequest$credentials_release, cancellationSignal, outcomeReceiver);
    }

    @Override // android.service.credentials.CredentialProviderService
    public final void onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest request, CancellationSignal cancellationSignal, final OutcomeReceiver<android.service.credentials.BeginCreateCredentialResponse, android.credentials.CreateCredentialException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(cancellationSignal, "cancellationSignal");
        Intrinsics.checkNotNullParameter(callback, "callback");
        OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException> outcomeReceiver = new OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException>() { // from class: androidx.credentials.provider.CredentialProviderService$onBeginCreateCredential$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(BeginCreateCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                callback.onResult(BeginCreateCredentialUtil.INSTANCE.convertToFrameworkResponse(response));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(CreateCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                callback.onError(new android.credentials.CreateCredentialException(error.getType(), error.getMessage()));
            }
        };
        BeginCreateCredentialRequest beginCreateCredentialRequestConvertToJetpackRequest$credentials_release = BeginCreateCredentialUtil.INSTANCE.convertToJetpackRequest$credentials_release(request);
        if (this.isTestMode) {
            this.lastCreateRequest = beginCreateCredentialRequestConvertToJetpackRequest$credentials_release;
        }
        onBeginCreateCredentialRequest(beginCreateCredentialRequestConvertToJetpackRequest$credentials_release, cancellationSignal, outcomeReceiver);
    }

    @Override // android.service.credentials.CredentialProviderService
    public final void onClearCredentialState(ClearCredentialStateRequest request, CancellationSignal cancellationSignal, final OutcomeReceiver<Void, ClearCredentialStateException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(cancellationSignal, "cancellationSignal");
        Intrinsics.checkNotNullParameter(callback, "callback");
        OutcomeReceiver<Void, ClearCredentialException> outcomeReceiver = new OutcomeReceiver<Void, ClearCredentialException>() { // from class: androidx.credentials.provider.CredentialProviderService$onClearCredentialState$outcome$1
            @Override // android.os.OutcomeReceiver
            public void onResult(Void response) {
                callback.onResult(response);
            }

            @Override // android.os.OutcomeReceiver
            public void onError(ClearCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                callback.onError(new ClearCredentialStateException(error.getType(), error.getMessage()));
            }
        };
        ProviderClearCredentialStateRequest providerClearCredentialStateRequestConvertToJetpackRequest$credentials_release = ClearCredentialUtil.INSTANCE.convertToJetpackRequest$credentials_release(request);
        if (this.isTestMode) {
            this.lastClearRequest = providerClearCredentialStateRequestConvertToJetpackRequest$credentials_release;
        }
        onClearCredentialStateRequest(providerClearCredentialStateRequestConvertToJetpackRequest$credentials_release, cancellationSignal, outcomeReceiver);
    }
}
