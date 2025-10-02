package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.CircleProgressView;
import com.thor.businessmodule.viewmodel.main.UpdateFirmwareFragmentDialogViewModel;

/* loaded from: classes.dex */
public abstract class FragmentDialogUpdateFirmwareBinding extends ViewDataBinding {

    @Bindable
    protected UpdateFirmwareFragmentDialogViewModel mModel;
    public final FrameLayout progressBar;
    public final TextView textSignal;
    public final TextView textViewCancel;
    public final TextView textViewInfo;
    public final CircleProgressView updateProgress;

    public abstract void setModel(UpdateFirmwareFragmentDialogViewModel model);

    protected FragmentDialogUpdateFirmwareBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout progressBar, TextView textSignal, TextView textViewCancel, TextView textViewInfo, CircleProgressView updateProgress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.progressBar = progressBar;
        this.textSignal = textSignal;
        this.textViewCancel = textViewCancel;
        this.textViewInfo = textViewInfo;
        this.updateProgress = updateProgress;
    }

    public UpdateFirmwareFragmentDialogViewModel getModel() {
        return this.mModel;
    }

    public static FragmentDialogUpdateFirmwareBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogUpdateFirmwareBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentDialogUpdateFirmwareBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_dialog_update_firmware, root, attachToRoot, component);
    }

    public static FragmentDialogUpdateFirmwareBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogUpdateFirmwareBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentDialogUpdateFirmwareBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_dialog_update_firmware, null, false, component);
    }

    public static FragmentDialogUpdateFirmwareBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogUpdateFirmwareBinding bind(View view, Object component) {
        return (FragmentDialogUpdateFirmwareBinding) bind(component, view, R.layout.fragment_dialog_update_firmware);
    }
}
