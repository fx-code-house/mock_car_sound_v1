package com.thor.basemodule.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.thor.basemodule.gui.view.CircleBarView;

/* loaded from: classes3.dex */
public class ViewCircleProgressBindingImpl extends ViewCircleProgressBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        return true;
    }

    public ViewCircleProgressBindingImpl(DataBindingComponent dataBindingComponent, View[] viewArr) {
        this(dataBindingComponent, viewArr, mapBindings(dataBindingComponent, viewArr, 2, sIncludes, sViewsWithIds));
    }

    private ViewCircleProgressBindingImpl(DataBindingComponent dataBindingComponent, View[] viewArr, Object[] objArr) {
        super(dataBindingComponent, viewArr[0], 0, (CircleBarView) objArr[0], (TextView) objArr[1]);
        this.mDirtyFlags = -1L;
        this.circleBarView.setTag(null);
        this.textView.setTag(null);
        setRootTag(viewArr);
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
