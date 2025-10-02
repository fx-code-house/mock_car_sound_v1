package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ItemShopSubscriptionCardBinding extends ViewDataBinding {
    public final AppCompatButton actionSubscription;
    public final AppCompatTextView description;
    public final AppCompatImageView logo;

    protected ItemShopSubscriptionCardBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton actionSubscription, AppCompatTextView description, AppCompatImageView logo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actionSubscription = actionSubscription;
        this.description = description;
        this.logo = logo;
    }

    public static ItemShopSubscriptionCardBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShopSubscriptionCardBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemShopSubscriptionCardBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_shop_subscription_card, root, attachToRoot, component);
    }

    public static ItemShopSubscriptionCardBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShopSubscriptionCardBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemShopSubscriptionCardBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_shop_subscription_card, null, false, component);
    }

    public static ItemShopSubscriptionCardBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShopSubscriptionCardBinding bind(View view, Object component) {
        return (ItemShopSubscriptionCardBinding) bind(component, view, R.layout.item_shop_subscription_card);
    }
}
