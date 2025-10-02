package com.thor.businessmodule.viewmodel.login;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import kotlin.Metadata;

/* compiled from: SignUpActivityViewModel.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/thor/businessmodule/viewmodel/login/SignUpActivityViewModel;", "Landroidx/databinding/Observable$OnPropertyChangedCallback;", "()V", "email", "Landroidx/databinding/ObservableField;", "", "getEmail", "()Landroidx/databinding/ObservableField;", "enableSignUp", "Landroidx/databinding/ObservableBoolean;", "getEnableSignUp", "()Landroidx/databinding/ObservableBoolean;", "password", "getPassword", "onPropertyChanged", "", "sender", "Landroidx/databinding/Observable;", "propertyId", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SignUpActivityViewModel extends Observable.OnPropertyChangedCallback {
    private final ObservableField<String> email;
    private final ObservableBoolean enableSignUp;
    private final ObservableField<String> password;

    public SignUpActivityViewModel() {
        ObservableField<String> observableField = new ObservableField<>("");
        this.email = observableField;
        ObservableField<String> observableField2 = new ObservableField<>("");
        this.password = observableField2;
        this.enableSignUp = new ObservableBoolean(false);
        SignUpActivityViewModel signUpActivityViewModel = this;
        observableField.addOnPropertyChangedCallback(signUpActivityViewModel);
        observableField2.addOnPropertyChangedCallback(signUpActivityViewModel);
    }

    public final ObservableField<String> getEmail() {
        return this.email;
    }

    public final ObservableField<String> getPassword() {
        return this.password;
    }

    public final ObservableBoolean getEnableSignUp() {
        return this.enableSignUp;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
    @Override // androidx.databinding.Observable.OnPropertyChangedCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onPropertyChanged(androidx.databinding.Observable r2, int r3) {
        /*
            r1 = this;
            androidx.databinding.ObservableField<java.lang.String> r2 = r1.email
            java.lang.Object r2 = r2.get()
            java.lang.String r2 = (java.lang.String) r2
            androidx.databinding.ObservableField<java.lang.String> r3 = r1.password
            java.lang.Object r3 = r3.get()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            boolean r0 = android.text.TextUtils.isEmpty(r2)
            if (r0 != 0) goto L45
            r0 = r3
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L45
            java.util.regex.Pattern r0 = android.util.Patterns.EMAIL_ADDRESS
            java.util.regex.Matcher r2 = r0.matcher(r2)
            boolean r2 = r2.matches()
            if (r2 == 0) goto L45
            if (r3 == 0) goto L38
            int r2 = r3.length()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L39
        L38:
            r2 = 0
        L39:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            int r2 = r2.intValue()
            r3 = 6
            if (r2 < r3) goto L45
            r2 = 1
            goto L46
        L45:
            r2 = 0
        L46:
            androidx.databinding.ObservableBoolean r3 = r1.enableSignUp
            r3.set(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.businessmodule.viewmodel.login.SignUpActivityViewModel.onPropertyChanged(androidx.databinding.Observable, int):void");
    }
}
