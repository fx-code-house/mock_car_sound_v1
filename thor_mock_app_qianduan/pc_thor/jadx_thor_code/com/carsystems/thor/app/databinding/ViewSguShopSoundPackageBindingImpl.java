package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.ImageViewBindingAdapter;
import com.thor.app.databinding.adapters.SguShopDataBindingAdapter;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;

/* loaded from: classes.dex */
public class ViewSguShopSoundPackageBindingImpl extends ViewSguShopSoundPackageBinding {
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
        sparseIntArray.put(R.id.layout_cover, 5);
        sparseIntArray.put(R.id.view_cover, 6);
        sparseIntArray.put(R.id.text_view_delete_info, 7);
    }

    public ViewSguShopSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ViewSguShopSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 0, (AppCompatImageView) bindings[1], (FrameLayout) bindings[5], (FrameLayout) bindings[4], (ConstraintLayout) bindings[0], (TextView) bindings[7], (TextView) bindings[2], (TextView) bindings[3], (View) bindings[6]);
        this.mDirtyFlags = -1L;
        this.imageViewCover.setTag(null);
        this.layoutDelete.setTag(null);
        this.mainLayout.setTag(null);
        this.textViewPrice.setTag(null);
        this.textViewSoundName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (8 == variableId) {
            setSkuDetails((SkuDetails) variable);
        } else {
            if (9 != variableId) {
                return false;
            }
            setSoundPackage((SguSoundSet) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewSguShopSoundPackageBinding
    public void setSkuDetails(SkuDetails SkuDetails) {
        this.mSkuDetails = SkuDetails;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ViewSguShopSoundPackageBinding
    public void setSoundPackage(SguSoundSet SoundPackage) {
        this.mSoundPackage = SoundPackage;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String setImageUrl;
        String setName;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SkuDetails skuDetails = this.mSkuDetails;
        SguSoundSet sguSoundSet = this.mSoundPackage;
        long j2 = 7 & j;
        if (j2 == 0 || (j & 6) == 0 || sguSoundSet == null) {
            setImageUrl = null;
            setName = null;
        } else {
            setImageUrl = sguSoundSet.getSetImageUrl();
            setName = sguSoundSet.getSetName();
        }
        if ((j & 6) != 0) {
            ImageViewBindingAdapter.bindLoadImage(this.imageViewCover, setImageUrl);
            TextViewBindingAdapter.setText(this.textViewSoundName, setName);
        }
        if (j2 != 0) {
            SguShopDataBindingAdapter.bindPackagePurchased(this.textViewPrice, sguSoundSet, skuDetails);
        }
    }
}
