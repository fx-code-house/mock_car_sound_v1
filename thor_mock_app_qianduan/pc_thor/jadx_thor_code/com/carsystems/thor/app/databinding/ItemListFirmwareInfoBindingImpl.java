package com.carsystems.thor.app.databinding;

import android.content.res.Resources;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.firmware.FirmwareProfileShort;

/* loaded from: classes.dex */
public class ItemListFirmwareInfoBindingImpl extends ItemListFirmwareInfoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemListFirmwareInfoBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ItemListFirmwareInfoBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.devTypeName.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.versionFw.setTag(null);
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
        setModel((FirmwareProfileShort) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ItemListFirmwareInfoBinding
    public void setModel(FirmwareProfileShort Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() throws Resources.NotFoundException {
        long j;
        String string;
        int versionFW;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FirmwareProfileShort firmwareProfileShort = this.mModel;
        long j2 = j & 3;
        String string2 = null;
        if (j2 != 0) {
            if (firmwareProfileShort != null) {
                string2 = firmwareProfileShort.getDevTypeName();
                versionFW = firmwareProfileShort.getVersionFW();
            } else {
                versionFW = 0;
            }
            string2 = this.devTypeName.getResources().getString(R.string.text_type_firmware, string2);
            string = this.versionFw.getResources().getString(R.string.text_version, Integer.valueOf(versionFW));
        } else {
            string = null;
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.devTypeName, string2);
            TextViewBindingAdapter.setText(this.versionFw, string);
        }
    }
}
