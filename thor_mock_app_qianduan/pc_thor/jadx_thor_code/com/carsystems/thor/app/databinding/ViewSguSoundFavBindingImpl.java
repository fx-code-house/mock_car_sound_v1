package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.ImageViewBindingAdapter;
import com.thor.networkmodule.model.responses.sgu.SguSound;

/* loaded from: classes.dex */
public class ViewSguSoundFavBindingImpl extends ViewSguSoundFavBinding {
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
        sparseIntArray.put(R.id.image_favourite, 3);
    }

    public ViewSguSoundFavBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }

    private ViewSguSoundFavBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 0, (AppCompatImageView) bindings[3], (TextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ImageView imageView = (ImageView) bindings[0];
        this.mboundView0 = imageView;
        imageView.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[1];
        this.mboundView1 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (4 != variableId) {
            return false;
        }
        setModel((SguSound) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewSguSoundFavBinding
    public void setModel(SguSound Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String name;
        String image;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SguSound sguSound = this.mModel;
        long j2 = j & 3;
        if (j2 == 0 || sguSound == null) {
            name = null;
            image = null;
        } else {
            name = sguSound.getName();
            image = sguSound.getImage();
        }
        if (j2 != 0) {
            ImageViewBindingAdapter.bindLoadImage(this.mboundView0, image);
            TextViewBindingAdapter.setText(this.textViewSoundName, name);
        }
    }
}
