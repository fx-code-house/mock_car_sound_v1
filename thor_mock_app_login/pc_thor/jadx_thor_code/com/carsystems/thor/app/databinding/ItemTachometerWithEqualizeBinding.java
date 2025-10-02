package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;

/* loaded from: classes.dex */
public abstract class ItemTachometerWithEqualizeBinding extends ViewDataBinding {
    public final TachometerWithEqualizeView viewTachometerWithEqualize;

    protected ItemTachometerWithEqualizeBinding(Object _bindingComponent, View _root, int _localFieldCount, TachometerWithEqualizeView viewTachometerWithEqualize) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewTachometerWithEqualize = viewTachometerWithEqualize;
    }

    public static ItemTachometerWithEqualizeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTachometerWithEqualizeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTachometerWithEqualizeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_tachometer_with_equalize, root, attachToRoot, component);
    }

    public static ItemTachometerWithEqualizeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTachometerWithEqualizeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTachometerWithEqualizeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_tachometer_with_equalize, null, false, component);
    }

    public static ItemTachometerWithEqualizeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTachometerWithEqualizeBinding bind(View view, Object component) {
        return (ItemTachometerWithEqualizeBinding) bind(component, view, R.layout.item_tachometer_with_equalize);
    }
}
