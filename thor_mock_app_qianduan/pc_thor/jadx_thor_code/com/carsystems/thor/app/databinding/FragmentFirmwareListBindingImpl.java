package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.activities.testers.FirmwareListViewModel;

/* loaded from: classes.dex */
public class FragmentFirmwareListBindingImpl extends FragmentFirmwareListBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final FrameLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.swipe_container, 1);
        sparseIntArray.put(R.id.nested_scroll_view, 2);
        sparseIntArray.put(R.id.recycler_view, 3);
        sparseIntArray.put(R.id.text_view_no_devices, 4);
    }

    public FragmentFirmwareListBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private FragmentFirmwareListBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (NestedScrollView) bindings[2], (RecyclerView) bindings[3], (SwipeRefreshLayout) bindings[1], (TextView) bindings[4]);
        this.mDirtyFlags = -1L;
        FrameLayout frameLayout = (FrameLayout) bindings[0];
        this.mboundView0 = frameLayout;
        frameLayout.setTag(null);
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
        setModel((FirmwareListViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.FragmentFirmwareListBinding
    public void setModel(FirmwareListViewModel Model) {
        this.mModel = Model;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
