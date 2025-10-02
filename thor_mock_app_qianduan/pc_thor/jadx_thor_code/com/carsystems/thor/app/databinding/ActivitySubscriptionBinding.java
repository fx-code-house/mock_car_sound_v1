package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.subscription.SubscriptionPlanOption;
import com.thor.basemodule.gui.view.AutoscrollRecyclerView;

/* loaded from: classes.dex */
public abstract class ActivitySubscriptionBinding extends ViewDataBinding {
    public final AppCompatImageView actionClose;
    public final AppCompatButton actionSubscribe;
    public final AutoscrollRecyclerView autoscrollRecycler;
    public final AppCompatTextView eulaLink;
    public final LinearLayout features;
    public final LinearLayout footerLinks;
    public final AppCompatImageView logo;
    public final AppCompatTextView privacyPolicyLink;
    public final NestedScrollView scrollable;
    public final SubscriptionPlanOption subscriptionOptionAnnually;
    public final SubscriptionPlanOption subscriptionOptionMonthly;
    public final LinearLayout subscriptionType;
    public final SwipeRefreshLayout swipeRefresh;
    public final AppCompatTextView textCancellation;
    public final AppCompatTextView title;

    protected ActivitySubscriptionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView actionClose, AppCompatButton actionSubscribe, AutoscrollRecyclerView autoscrollRecycler, AppCompatTextView eulaLink, LinearLayout features, LinearLayout footerLinks, AppCompatImageView logo, AppCompatTextView privacyPolicyLink, NestedScrollView scrollable, SubscriptionPlanOption subscriptionOptionAnnually, SubscriptionPlanOption subscriptionOptionMonthly, LinearLayout subscriptionType, SwipeRefreshLayout swipeRefresh, AppCompatTextView textCancellation, AppCompatTextView title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actionClose = actionClose;
        this.actionSubscribe = actionSubscribe;
        this.autoscrollRecycler = autoscrollRecycler;
        this.eulaLink = eulaLink;
        this.features = features;
        this.footerLinks = footerLinks;
        this.logo = logo;
        this.privacyPolicyLink = privacyPolicyLink;
        this.scrollable = scrollable;
        this.subscriptionOptionAnnually = subscriptionOptionAnnually;
        this.subscriptionOptionMonthly = subscriptionOptionMonthly;
        this.subscriptionType = subscriptionType;
        this.swipeRefresh = swipeRefresh;
        this.textCancellation = textCancellation;
        this.title = title;
    }

    public static ActivitySubscriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySubscriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySubscriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_subscription, root, attachToRoot, component);
    }

    public static ActivitySubscriptionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySubscriptionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySubscriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_subscription, null, false, component);
    }

    public static ActivitySubscriptionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySubscriptionBinding bind(View view, Object component) {
        return (ActivitySubscriptionBinding) bind(component, view, R.layout.activity_subscription);
    }
}
