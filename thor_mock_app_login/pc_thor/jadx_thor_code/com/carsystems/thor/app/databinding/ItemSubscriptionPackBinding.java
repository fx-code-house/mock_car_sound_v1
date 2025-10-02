package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.imageview.ShapeableImageView;

/* loaded from: classes.dex */
public abstract class ItemSubscriptionPackBinding extends ViewDataBinding {
    public final ShapeableImageView image;

    protected ItemSubscriptionPackBinding(Object _bindingComponent, View _root, int _localFieldCount, ShapeableImageView image) {
        super(_bindingComponent, _root, _localFieldCount);
        this.image = image;
    }

    public static ItemSubscriptionPackBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSubscriptionPackBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSubscriptionPackBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_subscription_pack, root, attachToRoot, component);
    }

    public static ItemSubscriptionPackBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSubscriptionPackBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSubscriptionPackBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_subscription_pack, null, false, component);
    }

    public static ItemSubscriptionPackBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSubscriptionPackBinding bind(View view, Object component) {
        return (ItemSubscriptionPackBinding) bind(component, view, R.layout.item_subscription_pack);
    }
}
