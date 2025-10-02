package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.CanFile;

/* loaded from: classes.dex */
public abstract class DialogFragmentCarInfoBinding extends ViewDataBinding {
    public final TextView dialogConnectionPoint;
    public final TextView dialogDriveSelect;
    public final TextView dialogNativeControl;
    public final TextView dialogNoInfo;
    public final TextView dialogTitle;
    public final TextView dialogTitleConnectionPoint;
    public final TextView dialogTitleDriveSelect;
    public final TextView dialogTitleNativeControl;

    @Bindable
    protected CanFile mModel;
    public final AppCompatButton positiveBtn;

    public abstract void setModel(CanFile model);

    protected DialogFragmentCarInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView dialogConnectionPoint, TextView dialogDriveSelect, TextView dialogNativeControl, TextView dialogNoInfo, TextView dialogTitle, TextView dialogTitleConnectionPoint, TextView dialogTitleDriveSelect, TextView dialogTitleNativeControl, AppCompatButton positiveBtn) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dialogConnectionPoint = dialogConnectionPoint;
        this.dialogDriveSelect = dialogDriveSelect;
        this.dialogNativeControl = dialogNativeControl;
        this.dialogNoInfo = dialogNoInfo;
        this.dialogTitle = dialogTitle;
        this.dialogTitleConnectionPoint = dialogTitleConnectionPoint;
        this.dialogTitleDriveSelect = dialogTitleDriveSelect;
        this.dialogTitleNativeControl = dialogTitleNativeControl;
        this.positiveBtn = positiveBtn;
    }

    public CanFile getModel() {
        return this.mModel;
    }

    public static DialogFragmentCarInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentCarInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogFragmentCarInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fragment_car_info, root, attachToRoot, component);
    }

    public static DialogFragmentCarInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentCarInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogFragmentCarInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fragment_car_info, null, false, component);
    }

    public static DialogFragmentCarInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentCarInfoBinding bind(View view, Object component) {
        return (DialogFragmentCarInfoBinding) bind(component, view, R.layout.dialog_fragment_car_info);
    }
}
