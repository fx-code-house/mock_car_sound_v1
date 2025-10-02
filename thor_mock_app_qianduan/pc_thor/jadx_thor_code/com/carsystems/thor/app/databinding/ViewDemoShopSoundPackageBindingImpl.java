package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.ImageViewBindingAdapter;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public class ViewDemoShopSoundPackageBindingImpl extends ViewDemoShopSoundPackageBinding {
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
        sparseIntArray.put(R.id.layout_cover, 3);
        sparseIntArray.put(R.id.view_cover, 4);
    }

    public ViewDemoShopSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ViewDemoShopSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 0, (ImageView) bindings[1], (FrameLayout) bindings[3], (ConstraintLayout) bindings[0], (TextView) bindings[2], (View) bindings[4]);
        this.mDirtyFlags = -1L;
        this.imageViewCover.setTag(null);
        this.mainLayout.setTag(null);
        this.textViewSoundName.setTag(null);
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
        if (9 != variableId) {
            return false;
        }
        setSoundPackage((ShopSoundPackage) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewDemoShopSoundPackageBinding
    public void setSoundPackage(ShopSoundPackage SoundPackage) {
        this.mSoundPackage = SoundPackage;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String pkgName;
        String pkgImageUrl;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ShopSoundPackage shopSoundPackage = this.mSoundPackage;
        long j2 = j & 3;
        if (j2 == 0 || shopSoundPackage == null) {
            pkgName = null;
            pkgImageUrl = null;
        } else {
            pkgName = shopSoundPackage.getPkgName();
            pkgImageUrl = shopSoundPackage.getPkgImageUrl();
        }
        if (j2 != 0) {
            ImageViewBindingAdapter.bindLoadImage(this.imageViewCover, pkgImageUrl);
            TextViewBindingAdapter.setText(this.textViewSoundName, pkgName);
        }
    }
}
