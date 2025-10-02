package com.thor.app.gui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySignUpBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.Constants;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.businessmodule.viewmodel.login.SignUpActivityViewModel;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.SignUpResponse;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import timber.log.Timber;

/* compiled from: SignUpActivity.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u001c"}, d2 = {"Lcom/thor/app/gui/activities/login/SignUpActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySignUpBinding;", "mProgressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/SignUpResponse;", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSignUp", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpBinding binding;
    private ProgressDialogFragment mProgressDialog;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.login.SignUpActivity$usersManager$2
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
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_sign_up)");
        ActivitySignUpBinding activitySignUpBinding = (ActivitySignUpBinding) contentView;
        this.binding = activitySignUpBinding;
        ActivitySignUpBinding activitySignUpBinding2 = null;
        if (activitySignUpBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpBinding = null;
        }
        activitySignUpBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivitySignUpBinding activitySignUpBinding3 = this.binding;
        if (activitySignUpBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpBinding3 = null;
        }
        activitySignUpBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.login.SignUpActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SignUpActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivitySignUpBinding activitySignUpBinding4 = this.binding;
        if (activitySignUpBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpBinding4 = null;
        }
        activitySignUpBinding4.setModel(new SignUpActivityViewModel());
        ActivitySignUpBinding activitySignUpBinding5 = this.binding;
        if (activitySignUpBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySignUpBinding2 = activitySignUpBinding5;
        }
        activitySignUpBinding2.buttonSignUp.setOnClickListener(this);
        ProgressDialogFragment progressDialogFragmentNewInstance = ProgressDialogFragment.newInstance();
        Intrinsics.checkNotNullExpressionValue(progressDialogFragmentNewInstance, "newInstance()");
        this.mProgressDialog = progressDialogFragmentNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SignUpActivity this$0, View view) {
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
        Observable<SignUpResponse> observableDoOnTerminate;
        ObservableField<String> password;
        ObservableField<String> email;
        ActivitySignUpBinding activitySignUpBinding = this.binding;
        String str = null;
        if (activitySignUpBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpBinding = null;
        }
        SignUpActivityViewModel model = activitySignUpBinding.getModel();
        String str2 = (model == null || (email = model.getEmail()) == null) ? null : email.get();
        ActivitySignUpBinding activitySignUpBinding2 = this.binding;
        if (activitySignUpBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpBinding2 = null;
        }
        SignUpActivityViewModel model2 = activitySignUpBinding2.getModel();
        if (model2 != null && (password = model2.getPassword()) != null) {
            str = password.get();
        }
        SignUpInfo signUpInfo = (SignUpInfo) getIntent().getParcelableExtra(SignUpInfo.BUNDLE_NAME);
        if (signUpInfo != null) {
            signUpInfo.setEmail(str2);
            signUpInfo.setPassword(str);
            Observable<SignUpResponse> observableSignUp = getUsersManager().signUp(signUpInfo);
            final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.login.SignUpActivity$onSignUp$1$1
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
                    ProgressDialogFragment progressDialogFragment = this.this$0.mProgressDialog;
                    ProgressDialogFragment progressDialogFragment2 = null;
                    if (progressDialogFragment == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                        progressDialogFragment = null;
                    }
                    if (progressDialogFragment.isAdded()) {
                        return;
                    }
                    ProgressDialogFragment progressDialogFragment3 = this.this$0.mProgressDialog;
                    if (progressDialogFragment3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mProgressDialog");
                    } else {
                        progressDialogFragment2 = progressDialogFragment3;
                    }
                    progressDialogFragment2.show(this.this$0.getSupportFragmentManager(), ProgressDialogFragment.TAG);
                }
            };
            Observable<SignUpResponse> observableDoOnSubscribe = observableSignUp.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SignUpActivity.onSignUp$lambda$5$lambda$1(function1, obj);
                }
            });
            if (observableDoOnSubscribe == null || (observableDoOnTerminate = observableDoOnSubscribe.doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.login.SignUpActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    SignUpActivity.onSignUp$lambda$5$lambda$2(this.f$0);
                }
            })) == null) {
                return;
            }
            final SignUpActivity$onSignUp$1$3 signUpActivity$onSignUp$1$3 = new SignUpActivity$onSignUp$1$3(this);
            Consumer<? super SignUpResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SignUpActivity.onSignUp$lambda$5$lambda$3(signUpActivity$onSignUp$1$3, obj);
                }
            };
            final SignUpActivity$onSignUp$1$4 signUpActivity$onSignUp$1$4 = new SignUpActivity$onSignUp$1$4(this);
            observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.login.SignUpActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SignUpActivity.onSignUp$lambda$5$lambda$4(signUpActivity$onSignUp$1$4, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$5$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$5$lambda$2(SignUpActivity this$0) {
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
    public static final void onSignUp$lambda$5$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSignUp$lambda$5$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SignUpResponse response) {
        Timber.INSTANCE.i("handleResponse: %s", response);
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            SignUpInfo signUpInfo = (SignUpInfo) getIntent().getParcelableExtra(SignUpInfo.BUNDLE_NAME);
            if (signUpInfo != null) {
                SignInResponse signInResponse = new SignInResponse(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
                signInResponse.setUserId(response.getUserId());
                String deviceSN = signUpInfo.getDeviceSN();
                signInResponse.setDeviceSN(deviceSN != null ? StringsKt.removeRange((CharSequence) deviceSN, 0, 4).toString() : null);
                signInResponse.setCarMarkId(signUpInfo.getCarMarkId());
                signInResponse.setCarMarkName(signUpInfo.getCarMarkName());
                signInResponse.setCarModelId(signUpInfo.getCarModelId());
                signInResponse.setCarModelName(signUpInfo.getCarModelName());
                signInResponse.setCarGenerationId(signUpInfo.getCarGenerationId());
                signInResponse.setCarGenerationName(signUpInfo.getCarGenerationName());
                signInResponse.setCarSerieId(signUpInfo.getCarSeriesId());
                signInResponse.setCarSerieName(signUpInfo.getCarSeriesName());
                Settings.saveProfile(signInResponse);
            }
            Settings.saveUserId(response.getUserId());
            String token = response.getToken();
            Intrinsics.checkNotNull(token);
            Settings.saveAccessToken(token);
            Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
            intent.setFlags(268468224);
            startActivity(intent);
            return;
        }
        if (response.getCode() == 888) {
            DeviceLockingUtilsKt.onDeviceLocked(this);
            return;
        }
        UsersManager usersManager = getUsersManager();
        ActivitySignUpBinding activitySignUpBinding = this.binding;
        if (activitySignUpBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySignUpBinding = null;
        }
        Snackbar snackbarMakeSnackBarError = usersManager.makeSnackBarError(activitySignUpBinding.layoutMain, response.getError());
        if (snackbarMakeSnackBarError != null) {
            snackbarMakeSnackBarError.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        Timber.INSTANCE.e(error);
    }
}
