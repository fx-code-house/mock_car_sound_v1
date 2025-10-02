package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ActivityUpdateAppBinding extends ViewDataBinding {
    public final Button buttonLog;
    public final AppCompatButton buttonSupport;
    public final AppCompatButton buttonUpdateApp;
    public final AppCompatButton changeCar;
    public final AppCompatButton formatFlash;
    public final FrameLayout progressBar;
    public final TextView textUpdate;
    public final TextView textUpdateScreen;

    protected ActivityUpdateAppBinding(Object _bindingComponent, View _root, int _localFieldCount, Button buttonLog, AppCompatButton buttonSupport, AppCompatButton buttonUpdateApp, AppCompatButton changeCar, AppCompatButton formatFlash, FrameLayout progressBar, TextView textUpdate, TextView textUpdateScreen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonLog = buttonLog;
        this.buttonSupport = buttonSupport;
        this.buttonUpdateApp = buttonUpdateApp;
        this.changeCar = changeCar;
        this.formatFlash = formatFlash;
        this.progressBar = progressBar;
        this.textUpdate = textUpdate;
        this.textUpdateScreen = textUpdateScreen;
    }

    public static ActivityUpdateAppBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityUpdateAppBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityUpdateAppBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_update_app, root, attachToRoot, component);
    }

    public static ActivityUpdateAppBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityUpdateAppBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityUpdateAppBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_update_app, null, false, component);
    }

    public static ActivityUpdateAppBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityUpdateAppBinding bind(View view, Object component) {
        return (ActivityUpdateAppBinding) bind(component, view, R.layout.activity_update_app);
    }
}
