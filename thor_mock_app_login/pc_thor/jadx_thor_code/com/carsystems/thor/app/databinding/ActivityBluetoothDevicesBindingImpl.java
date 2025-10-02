package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public class ActivityBluetoothDevicesBindingImpl extends ActivityBluetoothDevicesBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.text_view_toolbar_title, 1);
        sparseIntArray.put(R.id.swipe_container, 2);
        sparseIntArray.put(R.id.recycler_view, 3);
        sparseIntArray.put(R.id.text_view_no_devices, 4);
        sparseIntArray.put(R.id.text_view_title_need_permissions, 5);
        sparseIntArray.put(R.id.text_view_need_permissions, 6);
        sparseIntArray.put(R.id.button_request_permissions, 7);
        sparseIntArray.put(R.id.text_view_enter_code, 8);
        sparseIntArray.put(R.id.text_view_qr_code, 9);
        sparseIntArray.put(R.id.share_log, 10);
        sparseIntArray.put(R.id.button_demo, 11);
        sparseIntArray.put(R.id.guideline1, 12);
        sparseIntArray.put(R.id.guideline2, 13);
        sparseIntArray.put(R.id.progressBar, 14);
    }

    public ActivityBluetoothDevicesBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActivityBluetoothDevicesBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Button) bindings[11], (Button) bindings[7], (Guideline) bindings[12], (Guideline) bindings[13], (ConstraintLayout) bindings[0], (FrameLayout) bindings[14], (RecyclerView) bindings[3], (Button) bindings[10], (SwipeRefreshLayout) bindings[2], (TextView) bindings[8], (TextView) bindings[6], (TextView) bindings[4], (TextView) bindings[9], (TextView) bindings[5], (TextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.mainLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
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
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
