package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.AutoscrollRecyclerView;

/* loaded from: classes.dex */
public class ActivityShopSoundPackageBindingImpl extends ActivityShopSoundPackageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

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
        sparseIntArray.put(R.id.action_close, 1);
        sparseIntArray.put(R.id.card_sound_pack, 2);
        sparseIntArray.put(R.id.image_pack, 3);
        sparseIntArray.put(R.id.sounds_in_preset, 4);
        sparseIntArray.put(R.id.preset_name, 5);
        sparseIntArray.put(R.id.action_buy, 6);
        sparseIntArray.put(R.id.text_prefix_divider, 7);
        sparseIntArray.put(R.id.card_subscription, 8);
        sparseIntArray.put(R.id.autoscroll_recycler, 9);
        sparseIntArray.put(R.id.action_subscribe, 10);
    }

    public ActivityShopSoundPackageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActivityShopSoundPackageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatButton) bindings[6], (AppCompatImageView) bindings[1], (AppCompatButton) bindings[10], (AutoscrollRecyclerView) bindings[9], (CardView) bindings[2], (CardView) bindings[8], (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[7]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
