package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ItemDemoEvDividerBinding extends ViewDataBinding {
    protected ItemDemoEvDividerBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ItemDemoEvDividerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoEvDividerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDemoEvDividerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_demo_ev_divider, root, attachToRoot, component);
    }

    public static ItemDemoEvDividerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoEvDividerBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDemoEvDividerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_demo_ev_divider, null, false, component);
    }

    public static ItemDemoEvDividerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoEvDividerBinding bind(View view, Object component) {
        return (ItemDemoEvDividerBinding) bind(component, view, R.layout.item_demo_ev_divider);
    }
}
