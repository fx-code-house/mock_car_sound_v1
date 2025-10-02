package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.DemoSoundPackageDataBindingAdapter;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.viewmodel.views.DemoSoundPackageViewModel;

/* loaded from: classes.dex */
public class ViewDemoSoundPackageBindingImpl extends ViewDemoSoundPackageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.image_view_settings, 4);
    }

    public ViewDemoSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ViewDemoSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 1, (AppCompatImageView) bindings[0], (ImageView) bindings[4], (View) bindings[2], (ConstraintLayout) bindings[1], (TextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.imageViewCover.setTag(null);
        this.layoutSelectedGradient.setTag(null);
        this.mainLayout.setTag(null);
        this.textViewSoundName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        if (4 == variableId) {
            setModel((DemoSoundPackageViewModel) variable);
        } else {
            if (9 != variableId) {
                return false;
            }
            setSoundPackage((DemoSoundPackage) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewDemoSoundPackageBinding
    public void setModel(DemoSoundPackageViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ViewDemoSoundPackageBinding
    public void setSoundPackage(DemoSoundPackage SoundPackage) {
        this.mSoundPackage = SoundPackage;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeModelIsActivate((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsActivate(ObservableBoolean ModelIsActivate, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        DemoSoundPackageViewModel demoSoundPackageViewModel = this.mModel;
        DemoSoundPackage demoSoundPackage = this.mSoundPackage;
        long j2 = j & 11;
        String name = null;
        int i = 0;
        if (j2 != 0) {
            ObservableBoolean isActivate = demoSoundPackageViewModel != null ? demoSoundPackageViewModel.getIsActivate() : null;
            updateRegistration(0, isActivate);
            boolean z = isActivate != null ? isActivate.get() : false;
            if (j2 != 0) {
                j |= z ? 32L : 16L;
            }
            if (!z) {
                i = 8;
            }
        }
        long j3 = 12 & j;
        if (j3 != 0 && demoSoundPackage != null) {
            name = demoSoundPackage.getName();
        }
        if (j3 != 0) {
            DemoSoundPackageDataBindingAdapter.demoSoundPackageShowCover(this.imageViewCover, demoSoundPackage);
            TextViewBindingAdapter.setText(this.textViewSoundName, name);
        }
        if ((j & 11) != 0) {
            this.layoutSelectedGradient.setVisibility(i);
        }
    }
}
