package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.CircleProgressView;

/* loaded from: classes.dex */
public abstract class FragmentDialogFormatProgressBinding extends ViewDataBinding {
    public final CircleProgressView updateProgress;

    protected FragmentDialogFormatProgressBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleProgressView updateProgress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.updateProgress = updateProgress;
    }

    public static FragmentDialogFormatProgressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogFormatProgressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentDialogFormatProgressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_dialog_format_progress, root, attachToRoot, component);
    }

    public static FragmentDialogFormatProgressBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogFormatProgressBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentDialogFormatProgressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_dialog_format_progress, null, false, component);
    }

    public static FragmentDialogFormatProgressBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogFormatProgressBinding bind(View view, Object component) {
        return (FragmentDialogFormatProgressBinding) bind(component, view, R.layout.fragment_dialog_format_progress);
    }
}
