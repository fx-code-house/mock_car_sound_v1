package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.viewmodels.DemoActivityViewModel;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public class ActivityDemoBindingImpl extends ActivityDemoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 1);
        sparseIntArray.put(R.id.switch_shop_mode, 2);
        sparseIntArray.put(R.id.fragment_container, 3);
        sparseIntArray.put(R.id.bottom_menu, 4);
        sparseIntArray.put(R.id.text_view_order_device, 5);
        sparseIntArray.put(R.id.telegram_btn, 6);
        sparseIntArray.put(R.id.site_btn, 7);
    }

    public ActivityDemoBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActivityDemoBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ConstraintLayout) bindings[4], (FragmentContainerView) bindings[3], (ConstraintLayout) bindings[0], (AppCompatImageButton) bindings[7], (ShopModeSwitchView) bindings[2], (AppCompatImageButton) bindings[6], (TextView) bindings[5], (ToolbarWidget) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutMain.setTag(null);
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
        setModel((DemoActivityViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivityDemoBinding
    public void setModel(DemoActivityViewModel Model) {
        this.mModel = Model;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
