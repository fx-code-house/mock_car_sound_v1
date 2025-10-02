package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.SoundPresetDataBindingAdapter;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public class ViewSoundPresetBindingImpl extends ViewSoundPresetBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ImageView mboundView0;
    private final ConstraintLayout mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.guideline_horizontal, 4);
    }

    public ViewSoundPresetBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ViewSoundPresetBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 0, (Guideline) bindings[4], (TextView) bindings[3], (TextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ImageView imageView = (ImageView) bindings[0];
        this.mboundView0 = imageView;
        imageView.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[1];
        this.mboundView1 = constraintLayout;
        constraintLayout.setTag(null);
        this.textViewModeType.setTag(null);
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
        if (3 == variableId) {
            setModeType((ModeType) variable);
        } else {
            if (9 != variableId) {
                return false;
            }
            setSoundPackage((ShopSoundPackage) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewSoundPresetBinding
    public void setModeType(ModeType ModeType) {
        this.mModeType = ModeType;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(3);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ViewSoundPresetBinding
    public void setSoundPackage(ShopSoundPackage SoundPackage) {
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
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ModeType modeType = this.mModeType;
        ShopSoundPackage shopSoundPackage = this.mSoundPackage;
        long j2 = 5 & j;
        long j3 = j & 6;
        String pkgName = (j3 == 0 || shopSoundPackage == null) ? null : shopSoundPackage.getPkgName();
        if (j2 != 0) {
            SoundPresetDataBindingAdapter.soundPresetShowCover(this.mboundView0, modeType);
            SoundPresetDataBindingAdapter.soundPresetModeType(this.textViewModeType, modeType);
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.textViewSoundName, pkgName);
        }
    }
}
