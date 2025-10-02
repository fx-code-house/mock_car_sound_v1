package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.viewmodel.login.SignUpActivityViewModel;

/* loaded from: classes.dex */
public abstract class ActivitySignUpBinding extends ViewDataBinding {
    public final Button buttonSignUp;
    public final CheckBox cbInformMe;
    public final EditText editTextEmail;
    public final EditText editTextPassword;
    public final ConstraintLayout layoutMain;

    @Bindable
    protected SignUpActivityViewModel mModel;
    public final ToolbarWidget toolbarWidget;

    public abstract void setModel(SignUpActivityViewModel model);

    protected ActivitySignUpBinding(Object _bindingComponent, View _root, int _localFieldCount, Button buttonSignUp, CheckBox cbInformMe, EditText editTextEmail, EditText editTextPassword, ConstraintLayout layoutMain, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonSignUp = buttonSignUp;
        this.cbInformMe = cbInformMe;
        this.editTextEmail = editTextEmail;
        this.editTextPassword = editTextPassword;
        this.layoutMain = layoutMain;
        this.toolbarWidget = toolbarWidget;
    }

    public SignUpActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivitySignUpBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySignUpBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sign_up, root, attachToRoot, component);
    }

    public static ActivitySignUpBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySignUpBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sign_up, null, false, component);
    }

    public static ActivitySignUpBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpBinding bind(View view, Object component) {
        return (ActivitySignUpBinding) bind(component, view, R.layout.activity_sign_up);
    }
}
