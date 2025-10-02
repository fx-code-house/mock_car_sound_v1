package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ViewShopModeSwitchBinding extends ViewDataBinding {
    public final AppCompatTextView textBoombox;
    public final AppCompatTextView textCarSound;

    protected ViewShopModeSwitchBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView textBoombox, AppCompatTextView textCarSound) {
        super(_bindingComponent, _root, _localFieldCount);
        this.textBoombox = textBoombox;
        this.textCarSound = textCarSound;
    }

    public static ViewShopModeSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewShopModeSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewShopModeSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_shop_mode_switch, root, attachToRoot, component);
    }

    public static ViewShopModeSwitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewShopModeSwitchBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewShopModeSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_shop_mode_switch, null, false, component);
    }

    public static ViewShopModeSwitchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewShopModeSwitchBinding bind(View view, Object component) {
        return (ViewShopModeSwitchBinding) bind(component, view, R.layout.view_shop_mode_switch);
    }
}
