package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ViewEqualizeBinding extends ViewDataBinding {
    public final View viewBackground;
    public final View viewProgress;

    protected ViewEqualizeBinding(Object _bindingComponent, View _root, int _localFieldCount, View viewBackground, View viewProgress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewBackground = viewBackground;
        this.viewProgress = viewProgress;
    }

    public static ViewEqualizeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEqualizeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewEqualizeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_equalize, root, attachToRoot, component);
    }

    public static ViewEqualizeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEqualizeBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewEqualizeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_equalize, null, false, component);
    }

    public static ViewEqualizeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEqualizeBinding bind(View view, Object component) {
        return (ViewEqualizeBinding) bind(component, view, R.layout.view_equalize);
    }
}
