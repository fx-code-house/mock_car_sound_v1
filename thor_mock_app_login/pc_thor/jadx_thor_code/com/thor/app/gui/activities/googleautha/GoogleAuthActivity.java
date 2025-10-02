package com.thor.app.gui.activities.googleautha;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.lifecycle.LifecycleOwnerKt;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityGoogleAuthBinding;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.gui.activities.LockedDeviceActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.googleauth.SingInFromGoogleResponse;
import com.thor.networkmodule.utils.ThrowExtKt;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import org.greenrobot.eventbus.Subscribe;

/* compiled from: GoogleAuthActivity.kt */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0010H\u0002J\u0012\u0010\u001a\u001a\u00020\u00102\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\b\u0010 \u001a\u00020\u0010H\u0002J\u0010\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006%"}, d2 = {"Lcom/thor/app/gui/activities/googleautha/GoogleAuthActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityGoogleAuthBinding;", "credentialManager", "Landroidx/credentials/CredentialManager;", "request", "Landroidx/credentials/GetCredentialRequest;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/googleauth/SingInFromGoogleResponse;", "handleSignIn", "result", "Landroidx/credentials/GetCredentialResponse;", "initClickListener", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMessageEvent", "event", "Lcom/thor/app/bus/events/SendLogsEvent;", "onShareLogs", "saveUserGoogleToken", "idToken", "", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class GoogleAuthActivity extends Hilt_GoogleAuthActivity {
    private static final int REQ_ONE_TAP = 2;
    private static final String TOKEN = "1033603676734-74kmi8uj3sn2r87l6bef490k6aahhk52.apps.googleusercontent.com";
    private ActivityGoogleAuthBinding binding;
    private CredentialManager credentialManager;
    private GetCredentialRequest request;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.googleautha.GoogleAuthActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final void onShareLogs() {
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.app.gui.activities.googleautha.Hilt_GoogleAuthActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        ThemingUtil.INSTANCE.onSplashActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        ActivityGoogleAuthBinding activityGoogleAuthBindingInflate = ActivityGoogleAuthBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityGoogleAuthBindingInflate, "inflate(layoutInflater)");
        this.binding = activityGoogleAuthBindingInflate;
        ActivityGoogleAuthBinding activityGoogleAuthBinding = null;
        if (activityGoogleAuthBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityGoogleAuthBindingInflate = null;
        }
        setContentView(activityGoogleAuthBindingInflate.getRoot());
        this.credentialManager = CredentialManager.INSTANCE.create(this);
        this.request = new GetCredentialRequest.Builder().addCredentialOption(new GetSignInWithGoogleOption.Builder(TOKEN).build()).build();
        initClickListener();
        ActivityGoogleAuthBinding activityGoogleAuthBinding2 = this.binding;
        if (activityGoogleAuthBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityGoogleAuthBinding = activityGoogleAuthBinding2;
        }
        activityGoogleAuthBinding.googleAuthButton.setTextColor(ViewCompat.MEASURED_STATE_MASK);
    }

    private static final void onCreate$lambda$0(GoogleAuthActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShareLogs();
    }

    private final void initClickListener() {
        ActivityGoogleAuthBinding activityGoogleAuthBinding = this.binding;
        if (activityGoogleAuthBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityGoogleAuthBinding = null;
        }
        activityGoogleAuthBinding.googleAuthButton.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.googleautha.GoogleAuthActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GoogleAuthActivity.initClickListener$lambda$1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initClickListener$lambda$1(GoogleAuthActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityGoogleAuthBinding activityGoogleAuthBinding = this$0.binding;
        if (activityGoogleAuthBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityGoogleAuthBinding = null;
        }
        activityGoogleAuthBinding.progressBar.setVisibility(0);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new GoogleAuthActivity$initClickListener$1$1(this$0, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleSignIn(GetCredentialResponse result) {
        Credential credential = result.getCredential();
        if (credential instanceof CustomCredential) {
            if (Intrinsics.areEqual(credential.getType(), GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                try {
                    saveUserGoogleToken(GoogleIdTokenCredential.INSTANCE.createFrom(credential.getData()).getZzb());
                    return;
                } catch (GoogleIdTokenParsingException e) {
                    handleError(e);
                    return;
                }
            }
            handleError(new Throwable("Google auth error unexpected credential type"));
            return;
        }
        handleError(new Throwable("Google auth error unexpected credential type"));
    }

    /* compiled from: GoogleAuthActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.googleautha.GoogleAuthActivity$saveUserGoogleToken$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<SingInFromGoogleResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, GoogleAuthActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/googleauth/SingInFromGoogleResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SingInFromGoogleResponse singInFromGoogleResponse) {
            invoke2(singInFromGoogleResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SingInFromGoogleResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((GoogleAuthActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: GoogleAuthActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.googleautha.GoogleAuthActivity$saveUserGoogleToken$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, GoogleAuthActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((GoogleAuthActivity) this.receiver).handleError(p0);
        }
    }

    private final void saveUserGoogleToken(String idToken) {
        Observable<SingInFromGoogleResponse> observableSingInGoogleAuth = getUsersManager().singInGoogleAuth(idToken);
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
        Consumer<? super SingInFromGoogleResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.googleautha.GoogleAuthActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                GoogleAuthActivity.saveUserGoogleToken$lambda$2(anonymousClass1, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        observableSingInGoogleAuth.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.googleautha.GoogleAuthActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                GoogleAuthActivity.saveUserGoogleToken$lambda$3(anonymousClass2, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveUserGoogleToken$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveUserGoogleToken$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SingInFromGoogleResponse response) {
        if (!response.getStatus()) {
            AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, this, response.getError(), null, 4, null);
            if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
                alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
            }
            handleError(new Throwable("Google auth error response status false"));
            return;
        }
        Settings.saveUserGoogleUserId(String.valueOf(response.getIdUser()));
        Settings.saveUserGoogleToken(response.getToken());
        startActivity(new Intent(this, (Class<?>) SplashActivity.class));
        finish();
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GoogleAuthActivity googleAuthActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(googleAuthActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, googleAuthActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        getUsersManager().sendErrorLogToApi(uriForFile, event.getErrorMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        ActivityGoogleAuthBinding activityGoogleAuthBinding = this.binding;
        if (activityGoogleAuthBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityGoogleAuthBinding = null;
        }
        activityGoogleAuthBinding.progressBar.setVisibility(8);
        try {
            BaseResponse errorClass = ThrowExtKt.toErrorClass(error);
            AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default != null) {
                alertDialogCreateErrorAlertDialog$default.show();
            }
            if (errorClass.getCode() == 888) {
                startActivity(new Intent(this, (Class<?>) LockedDeviceActivity.class));
                finish();
                Toast.makeText(this, getString(R.string.google_auth_error) + error.getMessage(), 1).show();
            }
        } catch (Exception e) {
            GoogleAuthActivity googleAuthActivity = this;
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, googleAuthActivity, e.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
            Toast.makeText(googleAuthActivity, getString(R.string.google_auth_error) + e.getMessage(), 1).show();
            e.printStackTrace();
        }
    }
}
