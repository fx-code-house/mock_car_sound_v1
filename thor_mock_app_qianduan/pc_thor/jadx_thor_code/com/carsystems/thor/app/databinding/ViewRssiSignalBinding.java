package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ViewRssiSignalBinding extends ViewDataBinding {
    public final View signalFirst;
    public final View signalFourth;
    public final View signalSecond;
    public final View signalThird;

    protected ViewRssiSignalBinding(Object _bindingComponent, View _root, int _localFieldCount, View signalFirst, View signalFourth, View signalSecond, View signalThird) {
        super(_bindingComponent, _root, _localFieldCount);
        this.signalFirst = signalFirst;
        this.signalFourth = signalFourth;
        this.signalSecond = signalSecond;
        this.signalThird = signalThird;
    }

    public static ViewRssiSignalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewRssiSignalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewRssiSignalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_rssi_signal, root, attachToRoot, component);
    }

    public static ViewRssiSignalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewRssiSignalBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewRssiSignalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_rssi_signal, null, false, component);
    }

    public static ViewRssiSignalBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewRssiSignalBinding bind(View view, Object component) {
        return (ViewRssiSignalBinding) bind(component, view, R.layout.view_rssi_signal);
    }
}
