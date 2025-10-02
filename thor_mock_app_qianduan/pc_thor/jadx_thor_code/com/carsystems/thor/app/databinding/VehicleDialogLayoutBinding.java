package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class VehicleDialogLayoutBinding extends ViewDataBinding {
    public final RecyclerView recyclerView;

    protected VehicleDialogLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView recyclerView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.recyclerView = recyclerView;
    }

    public static VehicleDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static VehicleDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (VehicleDialogLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.vehicle_dialog_layout, root, attachToRoot, component);
    }

    public static VehicleDialogLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static VehicleDialogLayoutBinding inflate(LayoutInflater inflater, Object component) {
        return (VehicleDialogLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.vehicle_dialog_layout, null, false, component);
    }

    public static VehicleDialogLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static VehicleDialogLayoutBinding bind(View view, Object component) {
        return (VehicleDialogLayoutBinding) bind(component, view, R.layout.vehicle_dialog_layout);
    }
}
