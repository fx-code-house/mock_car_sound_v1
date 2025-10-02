package com.thor.app.gui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySignUpAddDeviceBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.Constants;
import com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SignUpAddDeviceActivity.kt */
@Deprecated(message = "not used")
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0003J\u0012\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u000fH\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u001c"}, d2 = {"Lcom/thor/app/gui/activities/login/SignUpAddDeviceActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySignUpAddDeviceBinding;", "mProgressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSignUp", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SignUpAddDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpAddDeviceBinding binding;
    private ProgressDialogFragment mProgressDialog;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_add_device);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…ivity_sign_up_add_device)");
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding = (ActivitySignUpAddDeviceBinding) contentView;
        this.binding = activitySignUpAddDeviceBinding;
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding2 = null;
        if (activitySignUpAddDeviceBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding = null;
        }
        activitySignUpAddDeviceBinding.setModel(new SignUpAddDeviceActivityViewModel());
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding3 = this.binding;
        if (activitySignUpAddDeviceBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding3 = null;
        }
        SignUpAddDeviceActivityViewModel model = activitySignUpAddDeviceBinding3.getModel();
        if (model != null) {
            model.setMListener(new SignUpAddDeviceActivityViewModel.OnChangedResultListener() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity.onCreate.1
                @Override // com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel.OnChangedResultListener
                public void onChange(boolean result) {
                    ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding4 = SignUpAddDeviceActivity.this.binding;
                    if (activitySignUpAddDeviceBinding4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activitySignUpAddDeviceBinding4 = null;
                    }
                    activitySignUpAddDeviceBinding4.editTextDeviceId.setSelected(result);
                }
            });
        }
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding4 = this.binding;
        if (activitySignUpAddDeviceBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding4 = null;
        }
        activitySignUpAddDeviceBinding4.toolbarWidget.setHomeButtonVisibility(true);
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding5 = this.binding;
        if (activitySignUpAddDeviceBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding5 = null;
        }
        activitySignUpAddDeviceBinding5.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SignUpAddDeviceActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding6 = this.binding;
        if (activitySignUpAddDeviceBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpAddDeviceBinding2 = activitySignUpAddDeviceBinding6;
        }
        activitySignUpAddDeviceBinding2.buttonSignUp.setOnClickListener(this);
        ProgressDialogFragment progressDialogFragmentNewInstance = ProgressDialogFragment.newInstance();
        Intrinsics.checkNotNullExpressionValue(progressDialogFragmentNewInstance, "newInstance()");
        this.mProgressDialog = progressDialogFragmentNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SignUpAddDeviceActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.button_sign_up) {
            onSignUp();
        }
    }

    private final void onSignUp() {
        String strSubstring;
        Observable observableDoOnTerminate;
        ObservableField<String> deviceSn;
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding = this.binding;
        if (activitySignUpAddDeviceBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding = null;
        }
        SignUpAddDeviceActivityViewModel model = activitySignUpAddDeviceBinding.getModel();
        String str = (model == null || (deviceSn = model.getDeviceSn()) == null) ? null : deviceSn.get();
        if (str != null) {
            strSubstring = str.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        } else {
            strSubstring = null;
        }
        Observable observableSignIn$default = UsersManager.signIn$default(getUsersManager(), strSubstring, false, 2, null);
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity.onSignUp.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                ProgressDialogFragment progressDialogFragment = SignUpAddDeviceActivity.this.mProgressDialog;
                ProgressDialogFragment progressDialogFragment2 = null;
                if (progressDialogFragment == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                    progressDialogFragment = null;
                }
                if (progressDialogFragment.isAdded()) {
                    return;
                }
                ProgressDialogFragment progressDialogFragment3 = SignUpAddDeviceActivity.this.mProgressDialog;
                if (progressDialogFragment3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                } else {
                    progressDialogFragment2 = progressDialogFragment3;
                }
                progressDialogFragment2.show(SignUpAddDeviceActivity.this.getSupportFragmentManager(), ProgressDialogFragment.TAG);
            }
        };
        Observable observableDoOnSubscribe = observableSignIn$default.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpAddDeviceActivity.onSignUp$lambda$1(function1, obj);
            }
        });
        if (observableDoOnSubscribe == null || (observableDoOnTerminate = observableDoOnSubscribe.doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                SignUpAddDeviceActivity.onSignUp$lambda$2(this.f$0);
            }
        })) == null) {
            return;
        }
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
        Consumer consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpAddDeviceActivity.onSignUp$lambda$3(anonymousClass3, obj);
            }
        };
        final AnonymousClass4 anonymousClass4 = new AnonymousClass4(this);
        observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SignUpAddDeviceActivity.onSignUp$lambda$4(anonymousClass4, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: SignUpAddDeviceActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$onSignUp$3, reason: invalid class name */
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<SignInResponse, Unit> {
        AnonymousClass3(Object obj) {
            super(1, obj, SignUpAddDeviceActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/SignInResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
            invoke2(signInResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SignInResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpAddDeviceActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SignUpAddDeviceActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$onSignUp$4, reason: invalid class name */
    /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass4(Object obj) {
            super(1, obj, SignUpAddDeviceActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SignUpAddDeviceActivity) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$2(SignUpAddDeviceActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ProgressDialogFragment progressDialogFragment = this$0.mProgressDialog;
        ProgressDialogFragment progressDialogFragment2 = null;
        if (progressDialogFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            progressDialogFragment = null;
        }
        if (progressDialogFragment.isResumed()) {
            ProgressDialogFragment progressDialogFragment3 = this$0.mProgressDialog;
            if (progressDialogFragment3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
            } else {
                progressDialogFragment2 = progressDialogFragment3;
            }
            progressDialogFragment2.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SignInResponse response) {
        String strSubstring;
        ObservableField<String> deviceSn;
        Timber.INSTANCE.i("handleResponse: %s", response);
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            Settings.saveUserId(response.getUserId());
            String token = response.getToken();
            Intrinsics.checkNotNull(token);
            Settings.saveAccessToken(token);
            Settings.saveProfile(response);
            Settings.INSTANCE.setSignUp(true);
            Observable<BaseResponse> observableAddNotificationToken = getUsersManager().addNotificationToken();
            if (observableAddNotificationToken != null) {
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity.handleResponse.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                        invoke2(baseResponse);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(BaseResponse baseResponse) {
                        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption;
                        if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(SignUpAddDeviceActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                        }
                        Timber.INSTANCE.i("Response: %s", baseResponse);
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SignUpAddDeviceActivity.handleResponse$lambda$5(function1, obj);
                    }
                };
                final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity.handleResponse.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable th) {
                        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption;
                        if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                            AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption2 = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(SignUpAddDeviceActivity.this, th.getMessage(), 0);
                            if (alertDialogCreateErrorAlertDialogWithSendLogOption2 != null) {
                                alertDialogCreateErrorAlertDialogWithSendLogOption2.show();
                            }
                        } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(SignUpAddDeviceActivity.this, th.getMessage(), 0)) != null) {
                            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                        }
                        Timber.INSTANCE.e(th);
                    }
                };
                observableAddNotificationToken.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpAddDeviceActivity$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SignUpAddDeviceActivity.handleResponse$lambda$6(function12, obj);
                    }
                });
            }
            Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
            intent.setFlags(268468224);
            startActivity(intent);
            return;
        }
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding = this.binding;
        if (activitySignUpAddDeviceBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding = null;
        }
        SignUpAddDeviceActivityViewModel model = activitySignUpAddDeviceBinding.getModel();
        String str = (model == null || (deviceSn = model.getDeviceSn()) == null) ? null : deviceSn.get();
        SignUpInfo signUpInfo = new SignUpInfo(null, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        if (str != null) {
            strSubstring = str.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        } else {
            strSubstring = null;
        }
        signUpInfo.setDeviceSN(strSubstring);
        if (response.getCode() == 888) {
            DeviceLockingUtilsKt.onDeviceLocked(this);
            return;
        }
        SignUpAddDeviceActivity signUpAddDeviceActivity = this;
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(signUpAddDeviceActivity, response.getError(), Integer.valueOf(response.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
        Intent intent2 = new Intent(signUpAddDeviceActivity, (Class<?>) SignUpCarInfoActivity.class);
        intent2.putExtra(SignUpInfo.BUNDLE_NAME, signUpInfo);
        startActivity(intent2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleResponse$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleResponse$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialog2 != null) {
                alertDialogCreateErrorAlertDialog2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0)) != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        Timber.INSTANCE.e(error);
        UsersManager usersManager = getUsersManager();
        ActivitySignUpAddDeviceBinding activitySignUpAddDeviceBinding = this.binding;
        if (activitySignUpAddDeviceBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpAddDeviceBinding = null;
        }
        Snackbar snackbarMakeSnackBarNetworkError = usersManager.makeSnackBarNetworkError(activitySignUpAddDeviceBinding.mainLayout);
        if (snackbarMakeSnackBarNetworkError != null) {
            snackbarMakeSnackBarNetworkError.show();
        }
    }
}
