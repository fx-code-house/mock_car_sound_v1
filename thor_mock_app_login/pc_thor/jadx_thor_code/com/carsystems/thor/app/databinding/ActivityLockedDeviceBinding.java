package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ActivityLockedDeviceBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView;
    public final AppCompatTextView appVersion;
    public final AppCompatButton buttonNewDevice;
    public final AppCompatButton buttonUpdate;
    public final AppCompatTextView deviceSn;
    public final AppCompatTextView fwVersion;
    public final LinearLayout linearLayoutInfo;
    public final AppCompatTextView textViewTitle;
    public final AppCompatTextView userId;

    protected ActivityLockedDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView, AppCompatTextView appVersion, AppCompatButton buttonNewDevice, AppCompatButton buttonUpdate, AppCompatTextView deviceSn, AppCompatTextView fwVersion, LinearLayout linearLayoutInfo, AppCompatTextView textViewTitle, AppCompatTextView userId) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView = appCompatTextView;
        this.appVersion = appVersion;
        this.buttonNewDevice = buttonNewDevice;
        this.buttonUpdate = buttonUpdate;
        this.deviceSn = deviceSn;
        this.fwVersion = fwVersion;
        this.linearLayoutInfo = linearLayoutInfo;
        this.textViewTitle = textViewTitle;
        this.userId = userId;
    }

    public static ActivityLockedDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityLockedDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityLockedDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_locked_device, root, attachToRoot, component);
    }

    public static ActivityLockedDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityLockedDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityLockedDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_locked_device, null, false, component);
    }

    public static ActivityLockedDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityLockedDeviceBinding bind(View view, Object component) {
        return (ActivityLockedDeviceBinding) bind(component, view, R.layout.activity_locked_device);
    }
}
