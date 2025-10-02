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
import com.thor.businessmodule.model.TypeDialog;

/* loaded from: classes.dex */
public abstract class DialogFragmentTipBinding extends ViewDataBinding {
    public final TextView dialogTitle;

    @Bindable
    protected TypeDialog mType;
    public final AppCompatButton positiveBtn;

    public abstract void setType(TypeDialog type);

    protected DialogFragmentTipBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView dialogTitle, AppCompatButton positiveBtn) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dialogTitle = dialogTitle;
        this.positiveBtn = positiveBtn;
    }

    public TypeDialog getType() {
        return this.mType;
    }

    public static DialogFragmentTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogFragmentTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fragment_tip, root, attachToRoot, component);
    }

    public static DialogFragmentTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentTipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogFragmentTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fragment_tip, null, false, component);
    }

    public static DialogFragmentTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentTipBinding bind(View view, Object component) {
        return (DialogFragmentTipBinding) bind(component, view, R.layout.dialog_fragment_tip);
    }
}
