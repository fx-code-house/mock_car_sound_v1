package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class LayoutSubscriptionPlanOptionBinding extends ViewDataBinding {
    public final ConstraintLayout content;
    public final AppCompatTextView discountBadge;
    public final AppCompatTextView planName;
    public final AppCompatTextView planPrice;
    public final AppCompatTextView prefix;
    public final AppCompatTextView textTrialInfo;

    protected LayoutSubscriptionPlanOptionBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout content, AppCompatTextView discountBadge, AppCompatTextView planName, AppCompatTextView planPrice, AppCompatTextView prefix, AppCompatTextView textTrialInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.content = content;
        this.discountBadge = discountBadge;
        this.planName = planName;
        this.planPrice = planPrice;
        this.prefix = prefix;
        this.textTrialInfo = textTrialInfo;
    }

    public static LayoutSubscriptionPlanOptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutSubscriptionPlanOptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutSubscriptionPlanOptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_subscription_plan_option, root, attachToRoot, component);
    }

    public static LayoutSubscriptionPlanOptionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutSubscriptionPlanOptionBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutSubscriptionPlanOptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_subscription_plan_option, null, false, component);
    }

    public static LayoutSubscriptionPlanOptionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutSubscriptionPlanOptionBinding bind(View view, Object component) {
        return (LayoutSubscriptionPlanOptionBinding) bind(component, view, R.layout.layout_subscription_plan_option);
    }
}
