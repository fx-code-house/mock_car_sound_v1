package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.tachometer.EqualizeView;
import com.thor.app.gui.views.tachometer.TachometerView;

/* loaded from: classes.dex */
public abstract class ActivityTestBinding extends ViewDataBinding {
    public final EqualizeView viewEqualize;
    public final TextView viewPlay;
    public final TextView viewPlayAll;
    public final TextView viewPlayTachometer;
    public final TachometerView viewTachometer;

    protected ActivityTestBinding(Object _bindingComponent, View _root, int _localFieldCount, EqualizeView viewEqualize, TextView viewPlay, TextView viewPlayAll, TextView viewPlayTachometer, TachometerView viewTachometer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewEqualize = viewEqualize;
        this.viewPlay = viewPlay;
        this.viewPlayAll = viewPlayAll;
        this.viewPlayTachometer = viewPlayTachometer;
        this.viewTachometer = viewTachometer;
    }

    public static ActivityTestBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityTestBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_test, root, attachToRoot, component);
    }

    public static ActivityTestBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityTestBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_test, null, false, component);
    }

    public static ActivityTestBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestBinding bind(View view, Object component) {
        return (ActivityTestBinding) bind(component, view, R.layout.activity_test);
    }
}
