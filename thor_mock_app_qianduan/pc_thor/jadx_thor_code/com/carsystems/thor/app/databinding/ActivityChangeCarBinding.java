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
import com.thor.businessmodule.viewmodel.ChangeCarActivityViewModel;
import com.thor.networkmodule.model.ChangeCarInfo;

/* loaded from: classes.dex */
public abstract class ActivityChangeCarBinding extends ViewDataBinding {
    public final ButtonWidget buttonChange;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final ConstraintLayout layoutMain;
    public final ConstraintLayout layoutSelector;

    @Bindable
    protected ChangeCarInfo mChangeCarInfo;

    @Bindable
    protected ChangeCarActivityViewModel mModel;
    public final TextView textViewApplySelector;
    public final TextView textViewCarGeneration;
    public final TextView textViewCarMark;
    public final TextView textViewCarModel;
    public final TextView textViewCarSeries;
    public final ToolbarWidget toolbarWidget;
    public final View viewDividerSelectorBottom;
    public final View viewDividerSelectorTop;
    public final NumberPicker viewPickerSelector;

    public abstract void setChangeCarInfo(ChangeCarInfo changeCarInfo);

    public abstract void setModel(ChangeCarActivityViewModel model);

    protected ActivityChangeCarBinding(Object _bindingComponent, View _root, int _localFieldCount, ButtonWidget buttonChange, Guideline guidelineLeft, Guideline guidelineRight, ConstraintLayout layoutMain, ConstraintLayout layoutSelector, TextView textViewApplySelector, TextView textViewCarGeneration, TextView textViewCarMark, TextView textViewCarModel, TextView textViewCarSeries, ToolbarWidget toolbarWidget, View viewDividerSelectorBottom, View viewDividerSelectorTop, NumberPicker viewPickerSelector) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonChange = buttonChange;
        this.guidelineLeft = guidelineLeft;
        this.guidelineRight = guidelineRight;
        this.layoutMain = layoutMain;
        this.layoutSelector = layoutSelector;
        this.textViewApplySelector = textViewApplySelector;
        this.textViewCarGeneration = textViewCarGeneration;
        this.textViewCarMark = textViewCarMark;
        this.textViewCarModel = textViewCarModel;
        this.textViewCarSeries = textViewCarSeries;
        this.toolbarWidget = toolbarWidget;
        this.viewDividerSelectorBottom = viewDividerSelectorBottom;
        this.viewDividerSelectorTop = viewDividerSelectorTop;
        this.viewPickerSelector = viewPickerSelector;
    }

    public ChangeCarInfo getChangeCarInfo() {
        return this.mChangeCarInfo;
    }

    public ChangeCarActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivityChangeCarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityChangeCarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityChangeCarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_change_car, root, attachToRoot, component);
    }

    public static ActivityChangeCarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityChangeCarBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityChangeCarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_change_car, null, false, component);
    }

    public static ActivityChangeCarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityChangeCarBinding bind(View view, Object component) {
        return (ActivityChangeCarBinding) bind(component, view, R.layout.activity_change_car);
    }
}
