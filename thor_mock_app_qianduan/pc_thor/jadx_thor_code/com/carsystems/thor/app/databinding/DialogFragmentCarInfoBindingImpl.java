package com.carsystems.thor.app.databinding;

import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.ViewBindingAdapter;
import com.thor.networkmodule.model.CanFile;

/* loaded from: classes.dex */
public class DialogFragmentCarInfoBindingImpl extends DialogFragmentCarInfoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.dialog_title, 8);
        sparseIntArray.put(R.id.positive_btn, 9);
    }

    public DialogFragmentCarInfoBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private DialogFragmentCarInfoBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (TextView) bindings[2], (TextView) bindings[6], (TextView) bindings[4], (TextView) bindings[7], (TextView) bindings[8], (TextView) bindings[1], (TextView) bindings[5], (TextView) bindings[3], (AppCompatButton) bindings[9]);
        this.mDirtyFlags = -1L;
        this.dialogConnectionPoint.setTag(null);
        this.dialogDriveSelect.setTag(null);
        this.dialogNativeControl.setTag(null);
        this.dialogNoInfo.setTag(null);
        this.dialogTitleConnectionPoint.setTag(null);
        this.dialogTitleDriveSelect.setTag(null);
        this.dialogTitleNativeControl.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
        setModel((CanFile) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.DialogFragmentCarInfoBinding
    public void setModel(CanFile Model) {
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
        boolean zIsEmpty;
        String str;
        String nativeControlInfo;
        boolean zIsEmpty2;
        boolean zIsEmpty3;
        boolean z;
        boolean z2;
        boolean z3;
        String canConnectionInfo;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        CanFile canFile = this.mModel;
        long j2 = j & 3;
        String driveSelectInfo = null;
        if (j2 != 0) {
            if (canFile != null) {
                driveSelectInfo = canFile.getDriveSelectInfo();
                canConnectionInfo = canFile.getCanConnectionInfo();
                nativeControlInfo = canFile.getNativeControlInfo();
            } else {
                canConnectionInfo = null;
                nativeControlInfo = null;
            }
            zIsEmpty = TextUtils.isEmpty(driveSelectInfo);
            zIsEmpty2 = TextUtils.isEmpty(canConnectionInfo);
            zIsEmpty3 = TextUtils.isEmpty(nativeControlInfo);
            z = !zIsEmpty;
            z2 = !zIsEmpty2;
            z3 = !zIsEmpty3;
            String str2 = canConnectionInfo;
            str = driveSelectInfo;
            driveSelectInfo = str2;
        } else {
            zIsEmpty = false;
            str = null;
            nativeControlInfo = null;
            zIsEmpty2 = false;
            zIsEmpty3 = false;
            z = false;
            z2 = false;
            z3 = false;
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.dialogConnectionPoint, driveSelectInfo);
            ViewBindingAdapter.bindIsVisible(this.dialogConnectionPoint, z2);
            TextViewBindingAdapter.setText(this.dialogDriveSelect, str);
            ViewBindingAdapter.bindIsVisible(this.dialogDriveSelect, z);
            TextViewBindingAdapter.setText(this.dialogNativeControl, nativeControlInfo);
            ViewBindingAdapter.bindIsVisible(this.dialogNativeControl, z3);
            ViewBindingAdapter.bindIsNoInfoVisible(this.dialogNoInfo, zIsEmpty3, zIsEmpty, zIsEmpty2);
            ViewBindingAdapter.bindIsVisible(this.dialogTitleConnectionPoint, z2);
            ViewBindingAdapter.bindIsVisible(this.dialogTitleDriveSelect, z);
            ViewBindingAdapter.bindIsVisible(this.dialogTitleNativeControl, z3);
        }
    }
}
