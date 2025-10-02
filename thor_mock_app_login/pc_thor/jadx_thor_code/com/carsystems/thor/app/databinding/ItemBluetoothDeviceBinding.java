package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;

/* loaded from: classes.dex */
public abstract class ItemBluetoothDeviceBinding extends ViewDataBinding {
    public final FrameLayout layoutMain;

    @Bindable
    protected BluetoothDeviceItem mModel;
    public final TextView textViewDeviceName;

    public abstract void setModel(BluetoothDeviceItem model);

    protected ItemBluetoothDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutMain, TextView textViewDeviceName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutMain = layoutMain;
        this.textViewDeviceName = textViewDeviceName;
    }

    public BluetoothDeviceItem getModel() {
        return this.mModel;
    }

    public static ItemBluetoothDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBluetoothDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBluetoothDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bluetooth_device, root, attachToRoot, component);
    }

    public static ItemBluetoothDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBluetoothDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBluetoothDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bluetooth_device, null, false, component);
    }

    public static ItemBluetoothDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBluetoothDeviceBinding bind(View view, Object component) {
        return (ItemBluetoothDeviceBinding) bind(component, view, R.layout.item_bluetooth_device);
    }
}
