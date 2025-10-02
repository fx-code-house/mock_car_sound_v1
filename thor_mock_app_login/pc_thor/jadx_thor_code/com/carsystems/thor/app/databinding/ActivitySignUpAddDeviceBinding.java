package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel;
import com.thor.app.gui.widget.ButtonWidget;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivitySignUpAddDeviceBinding extends ViewDataBinding {
    public final ButtonWidget buttonSignUp;
    public final EditText editTextDeviceId;
    public final Guideline guideline4;
    public final Guideline guideline7;

    @Bindable
    protected SignUpAddDeviceActivityViewModel mModel;
    public final ConstraintLayout mainLayout;
    public final TextView textViewDescription;
    public final ToolbarWidget toolbarWidget;

    public abstract void setModel(SignUpAddDeviceActivityViewModel model);

    protected ActivitySignUpAddDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, ButtonWidget buttonSignUp, EditText editTextDeviceId, Guideline guideline4, Guideline guideline7, ConstraintLayout mainLayout, TextView textViewDescription, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonSignUp = buttonSignUp;
        this.editTextDeviceId = editTextDeviceId;
        this.guideline4 = guideline4;
        this.guideline7 = guideline7;
        this.mainLayout = mainLayout;
        this.textViewDescription = textViewDescription;
        this.toolbarWidget = toolbarWidget;
    }

    public SignUpAddDeviceActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivitySignUpAddDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpAddDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySignUpAddDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sign_up_add_device, root, attachToRoot, component);
    }

    public static ActivitySignUpAddDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpAddDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySignUpAddDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sign_up_add_device, null, false, component);
    }

    public static ActivitySignUpAddDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpAddDeviceBinding bind(View view, Object component) {
        return (ActivitySignUpAddDeviceBinding) bind(component, view, R.layout.activity_sign_up_add_device);
    }
}
