package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.firmware.FirmwareProfileShort;

/* loaded from: classes.dex */
public abstract class ItemListFirmwareInfoBinding extends ViewDataBinding {
    public final AppCompatTextView devTypeName;

    @Bindable
    protected FirmwareProfileShort mModel;
    public final AppCompatTextView versionFw;

    public abstract void setModel(FirmwareProfileShort model);

    protected ItemListFirmwareInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView devTypeName, AppCompatTextView versionFw) {
        super(_bindingComponent, _root, _localFieldCount);
        this.devTypeName = devTypeName;
        this.versionFw = versionFw;
    }

    public FirmwareProfileShort getModel() {
        return this.mModel;
    }

    public static ItemListFirmwareInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListFirmwareInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemListFirmwareInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_list_firmware_info, root, attachToRoot, component);
    }

    public static ItemListFirmwareInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListFirmwareInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemListFirmwareInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_list_firmware_info, null, false, component);
    }

    public static ItemListFirmwareInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListFirmwareInfoBinding bind(View view, Object component) {
        return (ItemListFirmwareInfoBinding) bind(component, view, R.layout.item_list_firmware_info);
    }
}
