package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.businessmodule.viewmodel.views.MainPresetPackageViewModel;

/* loaded from: classes.dex */
public class ViewMainSoundPackageBindingImpl extends ViewMainSoundPackageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView3;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_cover, 8);
        sparseIntArray.put(R.id.text_need_upload, 9);
        sparseIntArray.put(R.id.image_view_settings, 10);
        sparseIntArray.put(R.id.guideline_horizontal, 11);
        sparseIntArray.put(R.id.text_view_delete_info, 12);
    }

    public ViewMainSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ViewMainSoundPackageBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 2, (Guideline) bindings[11], (AppCompatImageView) bindings[1], (ImageView) bindings[10], (FrameLayout) bindings[8], (FrameLayout) bindings[7], (ConstraintLayout) bindings[0], (TextView) bindings[9], (TextView) bindings[12], (TextView) bindings[4], (TextView) bindings[6], (TextView) bindings[5], (View) bindings[2]);
        this.mDirtyFlags = -1L;
        this.imageViewCover.setTag(null);
        this.layoutDelete.setTag(null);
        this.mainLayout.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[3];
        this.mboundView3 = constraintLayout;
        constraintLayout.setTag(null);
        this.textViewDriveMode.setTag(null);
        this.textViewModeType.setTag(null);
        this.textViewSoundName.setTag(null);
        this.viewCoverRedGradient.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
            setModel((MainPresetPackageViewModel) variable);
        } else {
            if (6 != variableId) {
                return false;
            }
            setPresetPackage((MainPresetPackage) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ViewMainSoundPackageBinding
    public void setModel(MainPresetPackageViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ViewMainSoundPackageBinding
    public void setPresetPackage(MainPresetPackage PresetPackage) {
        this.mPresetPackage = PresetPackage;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsDamageEnabled((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeModelIsActivate((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsDamageEnabled(ObservableBoolean ModelIsDamageEnabled, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelIsActivate(ObservableBoolean ModelIsActivate, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0045 A[PHI: r2
      0x0045: PHI (r2v5 long) = (r2v0 long), (r2v10 long) binds: [B:9:0x0020, B:22:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004c  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 192
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ViewMainSoundPackageBindingImpl.executeBindings():void");
    }
}
