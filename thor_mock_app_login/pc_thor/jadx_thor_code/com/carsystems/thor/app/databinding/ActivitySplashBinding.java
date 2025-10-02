package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.activities.splash.SplashActivityViewModel;

/* loaded from: classes.dex */
public abstract class ActivitySplashBinding extends ViewDataBinding {

    @Bindable
    protected SplashActivityViewModel mViewModel;

    public abstract void setViewModel(SplashActivityViewModel viewModel);

    protected ActivitySplashBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public SplashActivityViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySplashBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySplashBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_splash, root, attachToRoot, component);
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySplashBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySplashBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_splash, null, false, component);
    }

    public static ActivitySplashBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySplashBinding bind(View view, Object component) {
        return (ActivitySplashBinding) bind(component, view, R.layout.activity_splash);
    }
}
