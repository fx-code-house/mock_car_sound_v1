package androidx.credentials.provider;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.SigningInfo;
import android.credentials.Credential;
import android.credentials.CredentialOption;
import android.os.Bundle;
import android.service.credentials.CreateCredentialRequest;
import android.service.credentials.GetCredentialRequest;
import android.util.Log;
import androidx.credentials.CreateCredentialRequest;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.CredentialOption;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.internal.FrameworkClassParsingException;
import androidx.credentials.provider.PendingIntentHandler;
import androidx.credentials.provider.ProviderGetCredentialRequest;
import androidx.credentials.provider.utils.BeginGetCredentialUtil;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PendingIntentHandler.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/provider/PendingIntentHandler;", "", "()V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class PendingIntentHandler {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "PendingIntentHandler";

    @JvmStatic
    public static final BeginGetCredentialRequest retrieveBeginGetCredentialRequest(Intent intent) {
        return INSTANCE.retrieveBeginGetCredentialRequest(intent);
    }

    @JvmStatic
    public static final ProviderCreateCredentialRequest retrieveProviderCreateCredentialRequest(Intent intent) {
        return INSTANCE.retrieveProviderCreateCredentialRequest(intent);
    }

    @JvmStatic
    public static final ProviderGetCredentialRequest retrieveProviderGetCredentialRequest(Intent intent) {
        return INSTANCE.retrieveProviderGetCredentialRequest(intent);
    }

    @JvmStatic
    public static final void setBeginGetCredentialResponse(Intent intent, BeginGetCredentialResponse beginGetCredentialResponse) {
        INSTANCE.setBeginGetCredentialResponse(intent, beginGetCredentialResponse);
    }

    @JvmStatic
    public static final void setCreateCredentialException(Intent intent, CreateCredentialException createCredentialException) {
        INSTANCE.setCreateCredentialException(intent, createCredentialException);
    }

    @JvmStatic
    public static final void setCreateCredentialResponse(Intent intent, CreateCredentialResponse createCredentialResponse) {
        INSTANCE.setCreateCredentialResponse(intent, createCredentialResponse);
    }

    @JvmStatic
    public static final void setGetCredentialException(Intent intent, GetCredentialException getCredentialException) {
        INSTANCE.setGetCredentialException(intent, getCredentialException);
    }

    @JvmStatic
    public static final void setGetCredentialResponse(Intent intent, GetCredentialResponse getCredentialResponse) {
        INSTANCE.setGetCredentialResponse(intent, getCredentialResponse);
    }

    /* compiled from: PendingIntentHandler.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0018\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0015H\u0007J\u0018\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0017H\u0007J\u0018\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Landroidx/credentials/provider/PendingIntentHandler$Companion;", "", "()V", "TAG", "", "retrieveBeginGetCredentialRequest", "Landroidx/credentials/provider/BeginGetCredentialRequest;", "intent", "Landroid/content/Intent;", "retrieveProviderCreateCredentialRequest", "Landroidx/credentials/provider/ProviderCreateCredentialRequest;", "retrieveProviderGetCredentialRequest", "Landroidx/credentials/provider/ProviderGetCredentialRequest;", "setBeginGetCredentialResponse", "", "response", "Landroidx/credentials/provider/BeginGetCredentialResponse;", "setCreateCredentialException", "exception", "Landroidx/credentials/exceptions/CreateCredentialException;", "setCreateCredentialResponse", "Landroidx/credentials/CreateCredentialResponse;", "setGetCredentialException", "Landroidx/credentials/exceptions/GetCredentialException;", "setGetCredentialResponse", "Landroidx/credentials/GetCredentialResponse;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @JvmStatic
        public final ProviderCreateCredentialRequest retrieveProviderCreateCredentialRequest(Intent intent) throws FrameworkClassParsingException {
            Intrinsics.checkNotNullParameter(intent, "intent");
            CreateCredentialRequest createCredentialRequest = (CreateCredentialRequest) intent.getParcelableExtra("android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST", CreateCredentialRequest.class);
            if (createCredentialRequest == 0) {
                Log.i(PendingIntentHandler.TAG, "Request not found in pendingIntent");
                return (ProviderCreateCredentialRequest) createCredentialRequest;
            }
            CreateCredentialRequest.Companion companion = androidx.credentials.CreateCredentialRequest.INSTANCE;
            String type = createCredentialRequest.getType();
            Intrinsics.checkNotNullExpressionValue(type, "frameworkReq.type");
            Bundle data = createCredentialRequest.getData();
            Intrinsics.checkNotNullExpressionValue(data, "frameworkReq.data");
            Bundle data2 = createCredentialRequest.getData();
            Intrinsics.checkNotNullExpressionValue(data2, "frameworkReq.data");
            androidx.credentials.CreateCredentialRequest createCredentialRequestCreateFrom = companion.createFrom(type, data, data2, false, createCredentialRequest.getCallingAppInfo().getOrigin());
            if (createCredentialRequestCreateFrom == null) {
                return null;
            }
            String packageName = createCredentialRequest.getCallingAppInfo().getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "frameworkReq.callingAppInfo.packageName");
            SigningInfo signingInfo = createCredentialRequest.getCallingAppInfo().getSigningInfo();
            Intrinsics.checkNotNullExpressionValue(signingInfo, "frameworkReq.callingAppInfo.signingInfo");
            return new ProviderCreateCredentialRequest(createCredentialRequestCreateFrom, new CallingAppInfo(packageName, signingInfo, createCredentialRequest.getCallingAppInfo().getOrigin()));
        }

        @JvmStatic
        public final BeginGetCredentialRequest retrieveBeginGetCredentialRequest(Intent intent) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest = (android.service.credentials.BeginGetCredentialRequest) intent.getParcelableExtra("android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST", android.service.credentials.BeginGetCredentialRequest.class);
            if (beginGetCredentialRequest != null) {
                return BeginGetCredentialUtil.INSTANCE.convertToJetpackRequest$credentials_release(beginGetCredentialRequest);
            }
            return null;
        }

        @JvmStatic
        public final void setCreateCredentialResponse(Intent intent, CreateCredentialResponse response) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            Intrinsics.checkNotNullParameter(response, "response");
            intent.putExtra("android.service.credentials.extra.CREATE_CREDENTIAL_RESPONSE", new android.credentials.CreateCredentialResponse(response.getData()));
        }

        @JvmStatic
        public final ProviderGetCredentialRequest retrieveProviderGetCredentialRequest(Intent intent) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            GetCredentialRequest getCredentialRequest = (GetCredentialRequest) intent.getParcelableExtra("android.service.credentials.extra.GET_CREDENTIAL_REQUEST", GetCredentialRequest.class);
            if (getCredentialRequest == null) {
                Log.i(PendingIntentHandler.TAG, "Get request from framework is null");
                return null;
            }
            ProviderGetCredentialRequest.Companion companion = ProviderGetCredentialRequest.INSTANCE;
            Stream<CredentialOption> stream = getCredentialRequest.getCredentialOptions().stream();
            final PendingIntentHandler$Companion$retrieveProviderGetCredentialRequest$1 pendingIntentHandler$Companion$retrieveProviderGetCredentialRequest$1 = new Function1<CredentialOption, androidx.credentials.CredentialOption>() { // from class: androidx.credentials.provider.PendingIntentHandler$Companion$retrieveProviderGetCredentialRequest$1
                @Override // kotlin.jvm.functions.Function1
                public final androidx.credentials.CredentialOption invoke(CredentialOption credentialOption) {
                    CredentialOption.Companion companion2 = androidx.credentials.CredentialOption.INSTANCE;
                    String type = credentialOption.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "option.type");
                    Bundle credentialRetrievalData = credentialOption.getCredentialRetrievalData();
                    Intrinsics.checkNotNullExpressionValue(credentialRetrievalData, "option.credentialRetrievalData");
                    Bundle candidateQueryData = credentialOption.getCandidateQueryData();
                    Intrinsics.checkNotNullExpressionValue(candidateQueryData, "option.candidateQueryData");
                    boolean zIsSystemProviderRequired = credentialOption.isSystemProviderRequired();
                    Set<ComponentName> allowedProviders = credentialOption.getAllowedProviders();
                    Intrinsics.checkNotNullExpressionValue(allowedProviders, "option.allowedProviders");
                    return companion2.createFrom(type, credentialRetrievalData, candidateQueryData, zIsSystemProviderRequired, allowedProviders);
                }
            };
            Object objCollect = stream.map(new Function() { // from class: androidx.credentials.provider.PendingIntentHandler$Companion$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PendingIntentHandler.Companion.retrieveProviderGetCredentialRequest$lambda$1(pendingIntentHandler$Companion$retrieveProviderGetCredentialRequest$1, obj);
                }
            }).collect(Collectors.toList());
            Intrinsics.checkNotNullExpressionValue(objCollect, "frameworkReq.credentialO…lect(Collectors.toList())");
            String packageName = getCredentialRequest.getCallingAppInfo().getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "frameworkReq.callingAppInfo.packageName");
            SigningInfo signingInfo = getCredentialRequest.getCallingAppInfo().getSigningInfo();
            Intrinsics.checkNotNullExpressionValue(signingInfo, "frameworkReq.callingAppInfo.signingInfo");
            return companion.createFrom$credentials_release((List) objCollect, new CallingAppInfo(packageName, signingInfo, getCredentialRequest.getCallingAppInfo().getOrigin()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final androidx.credentials.CredentialOption retrieveProviderGetCredentialRequest$lambda$1(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (androidx.credentials.CredentialOption) tmp0.invoke(obj);
        }

        @JvmStatic
        public final void setGetCredentialResponse(Intent intent, GetCredentialResponse response) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            Intrinsics.checkNotNullParameter(response, "response");
            intent.putExtra("android.service.credentials.extra.GET_CREDENTIAL_RESPONSE", new android.credentials.GetCredentialResponse(new Credential(response.getCredential().getType(), response.getCredential().getData())));
        }

        @JvmStatic
        public final void setBeginGetCredentialResponse(Intent intent, BeginGetCredentialResponse response) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            Intrinsics.checkNotNullParameter(response, "response");
            intent.putExtra("android.service.credentials.extra.BEGIN_GET_CREDENTIAL_RESPONSE", BeginGetCredentialUtil.INSTANCE.convertToFrameworkResponse(response));
        }

        @JvmStatic
        public final void setGetCredentialException(Intent intent, GetCredentialException exception) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            Intrinsics.checkNotNullParameter(exception, "exception");
            intent.putExtra("android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION", new android.credentials.GetCredentialException(exception.getType(), exception.getMessage()));
        }

        @JvmStatic
        public final void setCreateCredentialException(Intent intent, CreateCredentialException exception) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            Intrinsics.checkNotNullParameter(exception, "exception");
            intent.putExtra("android.service.credentials.extra.CREATE_CREDENTIAL_EXCEPTION", new android.credentials.CreateCredentialException(exception.getType(), exception.getMessage()));
        }
    }
}
