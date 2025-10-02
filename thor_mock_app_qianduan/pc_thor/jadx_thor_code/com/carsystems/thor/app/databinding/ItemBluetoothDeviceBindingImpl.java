package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.thor.app.databinding.adapters.BluetoothDeviceDataBindingAdapter;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;

/* loaded from: classes.dex */
public class ItemBluetoothDeviceBindingImpl extends ItemBluetoothDeviceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemBluetoothDeviceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }

    private ItemBluetoothDeviceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (FrameLayout) bindings[0], (TextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutMain.setTag(null);
        this.textViewDeviceName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (4 != variableId) {
            return false;
        }
        setModel((BluetoothDeviceItem) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ItemBluetoothDeviceBinding
    public void setModel(BluetoothDeviceItem Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BluetoothDeviceItem bluetoothDeviceItem = this.mModel;
        if ((j & 3) != 0) {
            BluetoothDeviceDataBindingAdapter.bluetoothDeviceAuthorize(this.layoutMain, bluetoothDeviceItem);
            BluetoothDeviceDataBindingAdapter.showBluetoothDeviceName(this.textViewDeviceName, bluetoothDeviceItem);
        }
    }
}
