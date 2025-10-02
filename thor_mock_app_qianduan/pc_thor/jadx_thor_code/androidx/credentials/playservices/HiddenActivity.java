package androidx.credentials.playservices;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.credentials.playservices.controllers.CredentialProviderBaseController;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;
import com.google.android.gms.auth.api.identity.SavePasswordResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.fido.Fido;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialCreationOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HiddenActivity.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0017\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\bH\u0002J\b\u0010\n\u001a\u00020\bH\u0002J\b\u0010\u000b\u001a\u00020\bH\u0002J\"\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0012\u0010\u0012\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0014H\u0014J\u0012\u0010\u0017\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J \u0010\u0018\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Landroidx/credentials/playservices/HiddenActivity;", "Landroid/app/Activity;", "()V", "mWaitingForActivityResult", "", "resultReceiver", "Landroid/os/ResultReceiver;", "handleBeginSignIn", "", "handleCreatePassword", "handleCreatePublicKeyCredential", "handleGetSignInIntent", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSaveInstanceState", "outState", "restoreState", "setupFailure", "errName", "", "errMsg", "Companion", "credentials-play-services-auth_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class HiddenActivity extends Activity {
    private static final int DEFAULT_VALUE = 1;
    private static final String KEY_AWAITING_RESULT = "androidx.credentials.playservices.AWAITING_RESULT";
    private static final String TAG = "HiddenActivity";
    private boolean mWaitingForActivityResult;
    private ResultReceiver resultReceiver;

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        String stringExtra = getIntent().getStringExtra(CredentialProviderBaseController.TYPE_TAG);
        ResultReceiver resultReceiver = (ResultReceiver) getIntent().getParcelableExtra(CredentialProviderBaseController.RESULT_RECEIVER_TAG);
        this.resultReceiver = resultReceiver;
        if (resultReceiver == null) {
            finish();
        }
        restoreState(savedInstanceState);
        if (this.mWaitingForActivityResult) {
            return;
        }
        if (stringExtra != null) {
            switch (stringExtra.hashCode()) {
                case -441061071:
                    if (stringExtra.equals(CredentialProviderBaseController.BEGIN_SIGN_IN_TAG)) {
                        handleBeginSignIn();
                        return;
                    }
                    break;
                case 15545322:
                    if (stringExtra.equals(CredentialProviderBaseController.CREATE_PUBLIC_KEY_CREDENTIAL_TAG)) {
                        handleCreatePublicKeyCredential();
                        return;
                    }
                    break;
                case 1246634622:
                    if (stringExtra.equals(CredentialProviderBaseController.CREATE_PASSWORD_TAG)) {
                        handleCreatePassword();
                        return;
                    }
                    break;
                case 1980564212:
                    if (stringExtra.equals(CredentialProviderBaseController.SIGN_IN_INTENT_TAG)) {
                        handleGetSignInIntent();
                        return;
                    }
                    break;
            }
        }
        Log.w(TAG, "Activity handed an unsupported type");
        finish();
    }

    private final void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mWaitingForActivityResult = savedInstanceState.getBoolean(KEY_AWAITING_RESULT, false);
        }
    }

    private final void handleCreatePublicKeyCredential() {
        Task<PendingIntent> taskAddOnFailureListener;
        PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions = (PublicKeyCredentialCreationOptions) getIntent().getParcelableExtra(CredentialProviderBaseController.REQUEST_TAG);
        final int intExtra = getIntent().getIntExtra(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG, 1);
        if (publicKeyCredentialCreationOptions != null) {
            Task<PendingIntent> registerPendingIntent = Fido.getFido2ApiClient((Activity) this).getRegisterPendingIntent(publicKeyCredentialCreationOptions);
            final Function1<PendingIntent, Unit> function1 = new Function1<PendingIntent, Unit>() { // from class: androidx.credentials.playservices.HiddenActivity$handleCreatePublicKeyCredential$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(PendingIntent pendingIntent) {
                    invoke2(pendingIntent);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(PendingIntent result) {
                    Intrinsics.checkNotNullParameter(result, "result");
                    try {
                        this.this$0.mWaitingForActivityResult = true;
                        this.this$0.startIntentSenderForResult(result.getIntentSender(), intExtra, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        HiddenActivity hiddenActivity = this.this$0;
                        ResultReceiver resultReceiver = hiddenActivity.resultReceiver;
                        Intrinsics.checkNotNull(resultReceiver);
                        hiddenActivity.setupFailure(resultReceiver, CredentialProviderBaseController.CREATE_UNKNOWN, "During public key credential, found IntentSender failure on public key creation: " + e.getMessage());
                    }
                }
            };
            taskAddOnFailureListener = registerPendingIntent.addOnSuccessListener(new OnSuccessListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HiddenActivity.handleCreatePublicKeyCredential$lambda$2$lambda$0(function1, obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda1
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    HiddenActivity.handleCreatePublicKeyCredential$lambda$2$lambda$1(this.f$0, exc);
                }
            });
        } else {
            taskAddOnFailureListener = null;
        }
        if (taskAddOnFailureListener == null) {
            Log.w(TAG, "During create public key credential, request is null, so nothing to launch for public key credentials");
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleCreatePublicKeyCredential$lambda$2$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleCreatePublicKeyCredential$lambda$2$lambda$1(HiddenActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "e");
        String str = ((e instanceof ApiException) && CredentialProviderBaseController.INSTANCE.getRetryables().contains(Integer.valueOf(((ApiException) e).getStatusCode()))) ? CredentialProviderBaseController.CREATE_INTERRUPTED : CredentialProviderBaseController.CREATE_UNKNOWN;
        ResultReceiver resultReceiver = this$0.resultReceiver;
        Intrinsics.checkNotNull(resultReceiver);
        this$0.setupFailure(resultReceiver, str, "During create public key credential, fido registration failure: " + e.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setupFailure(ResultReceiver resultReceiver, String errName, String errMsg) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(CredentialProviderBaseController.FAILURE_RESPONSE_TAG, true);
        bundle.putString(CredentialProviderBaseController.EXCEPTION_TYPE_TAG, errName);
        bundle.putString(CredentialProviderBaseController.EXCEPTION_MESSAGE_TAG, errMsg);
        resultReceiver.send(Integer.MAX_VALUE, bundle);
        finish();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        outState.putBoolean(KEY_AWAITING_RESULT, this.mWaitingForActivityResult);
        super.onSaveInstanceState(outState);
    }

    private final void handleGetSignInIntent() {
        Task<PendingIntent> taskAddOnFailureListener;
        GetSignInIntentRequest getSignInIntentRequest = (GetSignInIntentRequest) getIntent().getParcelableExtra(CredentialProviderBaseController.REQUEST_TAG);
        final int intExtra = getIntent().getIntExtra(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG, 1);
        if (getSignInIntentRequest != null) {
            Task<PendingIntent> signInIntent = Identity.getSignInClient((Activity) this).getSignInIntent(getSignInIntentRequest);
            final Function1<PendingIntent, Unit> function1 = new Function1<PendingIntent, Unit>() { // from class: androidx.credentials.playservices.HiddenActivity$handleGetSignInIntent$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(PendingIntent pendingIntent) {
                    invoke2(pendingIntent);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(PendingIntent pendingIntent) {
                    try {
                        this.this$0.mWaitingForActivityResult = true;
                        this.this$0.startIntentSenderForResult(pendingIntent.getIntentSender(), intExtra, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        HiddenActivity hiddenActivity = this.this$0;
                        ResultReceiver resultReceiver = hiddenActivity.resultReceiver;
                        Intrinsics.checkNotNull(resultReceiver);
                        hiddenActivity.setupFailure(resultReceiver, CredentialProviderBaseController.GET_UNKNOWN, "During get sign-in intent, one tap ui intent sender failure: " + e.getMessage());
                    }
                }
            };
            taskAddOnFailureListener = signInIntent.addOnSuccessListener(new OnSuccessListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda2
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HiddenActivity.handleGetSignInIntent$lambda$6$lambda$4(function1, obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda3
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    HiddenActivity.handleGetSignInIntent$lambda$6$lambda$5(this.f$0, exc);
                }
            });
        } else {
            taskAddOnFailureListener = null;
        }
        if (taskAddOnFailureListener == null) {
            Log.i(TAG, "During get sign-in intent, params is null, nothing to launch for get sign-in intent");
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleGetSignInIntent$lambda$6$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleGetSignInIntent$lambda$6$lambda$5(HiddenActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "e");
        String str = ((e instanceof ApiException) && CredentialProviderBaseController.INSTANCE.getRetryables().contains(Integer.valueOf(((ApiException) e).getStatusCode()))) ? CredentialProviderBaseController.GET_INTERRUPTED : CredentialProviderBaseController.GET_NO_CREDENTIALS;
        ResultReceiver resultReceiver = this$0.resultReceiver;
        Intrinsics.checkNotNull(resultReceiver);
        this$0.setupFailure(resultReceiver, str, "During get sign-in intent, failure response from one tap: " + e.getMessage());
    }

    private final void handleBeginSignIn() {
        Task<BeginSignInResult> taskAddOnFailureListener;
        BeginSignInRequest beginSignInRequest = (BeginSignInRequest) getIntent().getParcelableExtra(CredentialProviderBaseController.REQUEST_TAG);
        final int intExtra = getIntent().getIntExtra(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG, 1);
        if (beginSignInRequest != null) {
            Task<BeginSignInResult> taskBeginSignIn = Identity.getSignInClient((Activity) this).beginSignIn(beginSignInRequest);
            final Function1<BeginSignInResult, Unit> function1 = new Function1<BeginSignInResult, Unit>() { // from class: androidx.credentials.playservices.HiddenActivity$handleBeginSignIn$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BeginSignInResult beginSignInResult) {
                    invoke2(beginSignInResult);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BeginSignInResult beginSignInResult) {
                    try {
                        this.this$0.mWaitingForActivityResult = true;
                        this.this$0.startIntentSenderForResult(beginSignInResult.getPendingIntent().getIntentSender(), intExtra, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        HiddenActivity hiddenActivity = this.this$0;
                        ResultReceiver resultReceiver = hiddenActivity.resultReceiver;
                        Intrinsics.checkNotNull(resultReceiver);
                        hiddenActivity.setupFailure(resultReceiver, CredentialProviderBaseController.GET_UNKNOWN, "During begin sign in, one tap ui intent sender failure: " + e.getMessage());
                    }
                }
            };
            taskAddOnFailureListener = taskBeginSignIn.addOnSuccessListener(new OnSuccessListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda4
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HiddenActivity.handleBeginSignIn$lambda$10$lambda$8(function1, obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda5
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    HiddenActivity.handleBeginSignIn$lambda$10$lambda$9(this.f$0, exc);
                }
            });
        } else {
            taskAddOnFailureListener = null;
        }
        if (taskAddOnFailureListener == null) {
            Log.i(TAG, "During begin sign in, params is null, nothing to launch for begin sign in");
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleBeginSignIn$lambda$10$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleBeginSignIn$lambda$10$lambda$9(HiddenActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "e");
        String str = ((e instanceof ApiException) && CredentialProviderBaseController.INSTANCE.getRetryables().contains(Integer.valueOf(((ApiException) e).getStatusCode()))) ? CredentialProviderBaseController.GET_INTERRUPTED : CredentialProviderBaseController.GET_NO_CREDENTIALS;
        ResultReceiver resultReceiver = this$0.resultReceiver;
        Intrinsics.checkNotNull(resultReceiver);
        this$0.setupFailure(resultReceiver, str, "During begin sign in, failure response from one tap: " + e.getMessage());
    }

    private final void handleCreatePassword() {
        Task<SavePasswordResult> taskAddOnFailureListener;
        SavePasswordRequest savePasswordRequest = (SavePasswordRequest) getIntent().getParcelableExtra(CredentialProviderBaseController.REQUEST_TAG);
        final int intExtra = getIntent().getIntExtra(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG, 1);
        if (savePasswordRequest != null) {
            Task<SavePasswordResult> taskSavePassword = Identity.getCredentialSavingClient((Activity) this).savePassword(savePasswordRequest);
            final Function1<SavePasswordResult, Unit> function1 = new Function1<SavePasswordResult, Unit>() { // from class: androidx.credentials.playservices.HiddenActivity$handleCreatePassword$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SavePasswordResult savePasswordResult) {
                    invoke2(savePasswordResult);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SavePasswordResult savePasswordResult) {
                    try {
                        this.this$0.mWaitingForActivityResult = true;
                        this.this$0.startIntentSenderForResult(savePasswordResult.getPendingIntent().getIntentSender(), intExtra, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        HiddenActivity hiddenActivity = this.this$0;
                        ResultReceiver resultReceiver = hiddenActivity.resultReceiver;
                        Intrinsics.checkNotNull(resultReceiver);
                        hiddenActivity.setupFailure(resultReceiver, CredentialProviderBaseController.CREATE_UNKNOWN, "During save password, found UI intent sender failure: " + e.getMessage());
                    }
                }
            };
            taskAddOnFailureListener = taskSavePassword.addOnSuccessListener(new OnSuccessListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda6
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HiddenActivity.handleCreatePassword$lambda$14$lambda$12(function1, obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: androidx.credentials.playservices.HiddenActivity$$ExternalSyntheticLambda7
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    HiddenActivity.handleCreatePassword$lambda$14$lambda$13(this.f$0, exc);
                }
            });
        } else {
            taskAddOnFailureListener = null;
        }
        if (taskAddOnFailureListener == null) {
            Log.i(TAG, "During save password, params is null, nothing to launch for create password");
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleCreatePassword$lambda$14$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleCreatePassword$lambda$14$lambda$13(HiddenActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "e");
        String str = ((e instanceof ApiException) && CredentialProviderBaseController.INSTANCE.getRetryables().contains(Integer.valueOf(((ApiException) e).getStatusCode()))) ? CredentialProviderBaseController.CREATE_INTERRUPTED : CredentialProviderBaseController.CREATE_UNKNOWN;
        ResultReceiver resultReceiver = this$0.resultReceiver;
        Intrinsics.checkNotNull(resultReceiver);
        this$0.setupFailure(resultReceiver, str, "During save password, found password failure response from one tap " + e.getMessage());
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CredentialProviderBaseController.FAILURE_RESPONSE_TAG, false);
        bundle.putInt(CredentialProviderBaseController.ACTIVITY_REQUEST_CODE_TAG, requestCode);
        bundle.putParcelable(CredentialProviderBaseController.RESULT_DATA_TAG, data);
        ResultReceiver resultReceiver = this.resultReceiver;
        if (resultReceiver != null) {
            resultReceiver.send(resultCode, bundle);
        }
        this.mWaitingForActivityResult = false;
        finish();
    }
}
