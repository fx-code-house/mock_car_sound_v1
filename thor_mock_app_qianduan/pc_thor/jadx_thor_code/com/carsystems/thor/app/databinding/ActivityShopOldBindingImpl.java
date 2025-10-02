package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public class ActivityShopOldBindingImpl extends ActivityShopOldBinding {
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
        sparseIntArray.put(R.id.toolbar_widget, 1);
        sparseIntArray.put(R.id.switch_shop_mode, 2);
        sparseIntArray.put(R.id.fragment_container, 3);
        sparseIntArray.put(R.id.swipe_container, 4);
        sparseIntArray.put(R.id.nested_scroll_view, 5);
        sparseIntArray.put(R.id.recycler_view, 6);
    }

    public ActivityShopOldBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActivityShopOldBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (FragmentContainerView) bindings[3], (ConstraintLayout) bindings[0], (NestedScrollView) bindings[5], (RecyclerView) bindings[6], (SwipeRefreshLayout) bindings[4], (ShopModeSwitchView) bindings[2], (ToolbarWidget) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutMain.setTag(null);
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
