package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class DialogFragmentTestersBinding extends ViewDataBinding {
    public final EditText dialogEditText;
    public final AppCompatButton positiveBtn;

    protected DialogFragmentTestersBinding(Object _bindingComponent, View _root, int _localFieldCount, EditText dialogEditText, AppCompatButton positiveBtn) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dialogEditText = dialogEditText;
        this.positiveBtn = positiveBtn;
    }

    public static DialogFragmentTestersBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentTestersBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogFragmentTestersBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fragment_testers, root, attachToRoot, component);
    }

    public static DialogFragmentTestersBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentTestersBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogFragmentTestersBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fragment_testers, null, false, component);
    }

    public static DialogFragmentTestersBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFragmentTestersBinding bind(View view, Object component) {
        return (DialogFragmentTestersBinding) bind(component, view, R.layout.dialog_fragment_testers);
    }
}
