package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ButtonWidget;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.viewmodel.login.SignUpCarInfoActivityViewModel;
import com.thor.networkmodule.model.SignUpInfo;

/* loaded from: classes.dex */
public abstract class ActivitySignUpCarInfoBinding extends ViewDataBinding {
    public final ButtonWidget buttonContinue;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final ConstraintLayout layoutMain;
    public final ConstraintLayout layoutSelector;

    @Bindable
    protected SignUpCarInfoActivityViewModel mModel;

    @Bindable
    protected SignUpInfo mSignUpInfo;
    public final TextView textViewApplySelector;
    public final TextView textViewCarGeneration;
    public final TextView textViewCarMark;
    public final TextView textViewCarModel;
    public final TextView textViewCarSeries;
    public final TextView textViewDescription;
    public final TextView textViewHeader;
    public final ToolbarWidget toolbarWidget;
    public final View viewDividerSelectorBottom;
    public final View viewDividerSelectorTop;
    public final NumberPicker viewPickerSelector;

    public abstract void setModel(SignUpCarInfoActivityViewModel model);

    public abstract void setSignUpInfo(SignUpInfo signUpInfo);

    protected ActivitySignUpCarInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, ButtonWidget buttonContinue, Guideline guidelineLeft, Guideline guidelineRight, ConstraintLayout layoutMain, ConstraintLayout layoutSelector, TextView textViewApplySelector, TextView textViewCarGeneration, TextView textViewCarMark, TextView textViewCarModel, TextView textViewCarSeries, TextView textViewDescription, TextView textViewHeader, ToolbarWidget toolbarWidget, View viewDividerSelectorBottom, View viewDividerSelectorTop, NumberPicker viewPickerSelector) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonContinue = buttonContinue;
        this.guidelineLeft = guidelineLeft;
        this.guidelineRight = guidelineRight;
        this.layoutMain = layoutMain;
        this.layoutSelector = layoutSelector;
        this.textViewApplySelector = textViewApplySelector;
        this.textViewCarGeneration = textViewCarGeneration;
        this.textViewCarMark = textViewCarMark;
        this.textViewCarModel = textViewCarModel;
        this.textViewCarSeries = textViewCarSeries;
        this.textViewDescription = textViewDescription;
        this.textViewHeader = textViewHeader;
        this.toolbarWidget = toolbarWidget;
        this.viewDividerSelectorBottom = viewDividerSelectorBottom;
        this.viewDividerSelectorTop = viewDividerSelectorTop;
        this.viewPickerSelector = viewPickerSelector;
    }

    public SignUpInfo getSignUpInfo() {
        return this.mSignUpInfo;
    }

    public SignUpCarInfoActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivitySignUpCarInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpCarInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySignUpCarInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sign_up_car_info, root, attachToRoot, component);
    }

    public static ActivitySignUpCarInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpCarInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySignUpCarInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sign_up_car_info, null, false, component);
    }

    public static ActivitySignUpCarInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySignUpCarInfoBinding bind(View view, Object component) {
        return (ActivitySignUpCarInfoBinding) bind(component, view, R.layout.activity_sign_up_car_info);
    }
}
