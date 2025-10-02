package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.ImageViewBindingAdapter;
import com.thor.businessmodule.model.DemoSguPackage;

/* loaded from: classes.dex */
public class ViewDemoSguSoundConfigBindingImpl extends ViewDemoSguSoundConfigBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final CardView mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.main_layout, 3);
        sparseIntArray.put(R.id.view_cover_red_gradient, 4);
        sparseIntArray.put(R.id.image_config, 5);
    }

    public ViewDemoSguSoundConfigBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ViewDemoSguSoundConfigBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[5], (ImageView) bindings[1], (ConstraintLayout) bindings[3], (TextView) bindings[2], (View) bindings[4]);
        this.mDirtyFlags = -1L;
        this.imageViewCover.setTag(null);
        CardView cardView = (CardView) bindings[0];
        this.mboundView0 = cardView;
        cardView.setTag(null);
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
        setModel((DemoSguPackage) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewDemoSguSoundConfigBinding
    public void setModel(DemoSguPackage Model) {
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
        String drawableName;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        DemoSguPackage demoSguPackage = this.mModel;
        long j2 = j & 3;
        if (j2 == 0 || demoSguPackage == null) {
            name = null;
            drawableName = null;
        } else {
            name = demoSguPackage.getName();
            drawableName = demoSguPackage.getDrawableName();
        }
        if (j2 != 0) {
            ImageViewBindingAdapter.bindLoadImageFromFiles(this.imageViewCover, drawableName);
            TextViewBindingAdapter.setText(this.textViewSoundName, name);
        }
    }
}
