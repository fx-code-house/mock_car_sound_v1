package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ActivityBluetoothDevicesBinding extends ViewDataBinding {
    public final Button buttonDemo;
    public final Button buttonRequestPermissions;
    public final Guideline guideline1;
    public final Guideline guideline2;
    public final ConstraintLayout mainLayout;
    public final FrameLayout progressBar;
    public final RecyclerView recyclerView;
    public final Button shareLog;
    public final SwipeRefreshLayout swipeContainer;
    public final TextView textViewEnterCode;
    public final TextView textViewNeedPermissions;
    public final TextView textViewNoDevices;
    public final TextView textViewQrCode;
    public final TextView textViewTitleNeedPermissions;
    public final TextView textViewToolbarTitle;

    protected ActivityBluetoothDevicesBinding(Object _bindingComponent, View _root, int _localFieldCount, Button buttonDemo, Button buttonRequestPermissions, Guideline guideline1, Guideline guideline2, ConstraintLayout mainLayout, FrameLayout progressBar, RecyclerView recyclerView, Button shareLog, SwipeRefreshLayout swipeContainer, TextView textViewEnterCode, TextView textViewNeedPermissions, TextView textViewNoDevices, TextView textViewQrCode, TextView textViewTitleNeedPermissions, TextView textViewToolbarTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonDemo = buttonDemo;
        this.buttonRequestPermissions = buttonRequestPermissions;
        this.guideline1 = guideline1;
        this.guideline2 = guideline2;
        this.mainLayout = mainLayout;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.shareLog = shareLog;
        this.swipeContainer = swipeContainer;
        this.textViewEnterCode = textViewEnterCode;
        this.textViewNeedPermissions = textViewNeedPermissions;
        this.textViewNoDevices = textViewNoDevices;
        this.textViewQrCode = textViewQrCode;
        this.textViewTitleNeedPermissions = textViewTitleNeedPermissions;
        this.textViewToolbarTitle = textViewToolbarTitle;
    }

    public static ActivityBluetoothDevicesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBluetoothDevicesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityBluetoothDevicesBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_bluetooth_devices, root, attachToRoot, component);
    }

    public static ActivityBluetoothDevicesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBluetoothDevicesBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityBluetoothDevicesBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_bluetooth_devices, null, false, component);
    }

    public static ActivityBluetoothDevicesBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBluetoothDevicesBinding bind(View view, Object component) {
        return (ActivityBluetoothDevicesBinding) bind(component, view, R.layout.activity_bluetooth_devices);
    }
}
