package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.tachometer.EqualizeView;
import com.thor.app.gui.views.tachometer.TachometerView;

/* loaded from: classes.dex */
public abstract class ViewTachometerWithEqualizeBinding extends ViewDataBinding {
    public final EqualizeView viewEqualize;
    public final TachometerView viewTachometer;

    protected ViewTachometerWithEqualizeBinding(Object _bindingComponent, View _root, int _localFieldCount, EqualizeView viewEqualize, TachometerView viewTachometer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewEqualize = viewEqualize;
        this.viewTachometer = viewTachometer;
    }

    public static ViewTachometerWithEqualizeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTachometerWithEqualizeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewTachometerWithEqualizeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_tachometer_with_equalize, root, attachToRoot, component);
    }

    public static ViewTachometerWithEqualizeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTachometerWithEqualizeBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewTachometerWithEqualizeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_tachometer_with_equalize, null, false, component);
    }

    public static ViewTachometerWithEqualizeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTachometerWithEqualizeBinding bind(View view, Object component) {
        return (ViewTachometerWithEqualizeBinding) bind(component, view, R.layout.view_tachometer_with_equalize);
    }
}
