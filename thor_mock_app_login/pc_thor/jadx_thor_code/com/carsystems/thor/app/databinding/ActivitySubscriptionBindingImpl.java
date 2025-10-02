package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.subscription.SubscriptionPlanOption;
import com.thor.basemodule.gui.view.AutoscrollRecyclerView;

/* loaded from: classes.dex */
public class ActivitySubscriptionBindingImpl extends ActivitySubscriptionBinding {
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
        sparseIntArray.put(R.id.scrollable, 1);
        sparseIntArray.put(R.id.logo, 2);
        sparseIntArray.put(R.id.action_close, 3);
        sparseIntArray.put(R.id.title, 4);
        sparseIntArray.put(R.id.autoscroll_recycler, 5);
        sparseIntArray.put(R.id.features, 6);
        sparseIntArray.put(R.id.subscription_type, 7);
        sparseIntArray.put(R.id.subscription_option_monthly, 8);
        sparseIntArray.put(R.id.subscription_option_annually, 9);
        sparseIntArray.put(R.id.text_cancellation, 10);
        sparseIntArray.put(R.id.action_subscribe, 11);
        sparseIntArray.put(R.id.footer_links, 12);
        sparseIntArray.put(R.id.eula_link, 13);
        sparseIntArray.put(R.id.privacy_policy_link, 14);
    }

    public ActivitySubscriptionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActivitySubscriptionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (AppCompatButton) bindings[11], (AutoscrollRecyclerView) bindings[5], (AppCompatTextView) bindings[13], (LinearLayout) bindings[6], (LinearLayout) bindings[12], (AppCompatImageView) bindings[2], (AppCompatTextView) bindings[14], (NestedScrollView) bindings[1], (SubscriptionPlanOption) bindings[9], (SubscriptionPlanOption) bindings[8], (LinearLayout) bindings[7], (SwipeRefreshLayout) bindings[0], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.swipeRefresh.setTag(null);
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
