package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.camera.CameraSourcePreview;
import com.thor.app.gui.camera.GraphicOverlay;
import com.thor.app.gui.widget.DrawView;
import com.thor.businessmodule.viewmodel.main.BarcodeCaptureActivityViewModel;

/* loaded from: classes.dex */
public class ActivityBarcodeCaptureBindingImpl extends ActivityBarcodeCaptureBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.graphic_overlay, 3);
        sparseIntArray.put(R.id.constraintLayout, 4);
        sparseIntArray.put(R.id.image_view_back, 5);
        sparseIntArray.put(R.id.view_cover, 6);
        sparseIntArray.put(R.id.drawView, 7);
        sparseIntArray.put(R.id.linear_layout, 8);
        sparseIntArray.put(R.id.guideline3, 9);
        sparseIntArray.put(R.id.guideline2, 10);
        sparseIntArray.put(R.id.guideline4, 11);
        sparseIntArray.put(R.id.guideline5, 12);
    }

    public ActivityBarcodeCaptureBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActivityBarcodeCaptureBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (CameraSourcePreview) bindings[2], (ConstraintLayout) bindings[4], (DrawView) bindings[7], (GraphicOverlay) bindings[3], (Guideline) bindings[10], (Guideline) bindings[9], (Guideline) bindings[11], (Guideline) bindings[12], (AppCompatImageView) bindings[5], (ImageView) bindings[1], (View) bindings[8], (ConstraintLayout) bindings[0], (View) bindings[6]);
        this.mDirtyFlags = -1L;
        this.cameraPreview.setTag(null);
        this.imageViewSubstrate.setTag(null);
        this.mainLayout.setTag(null);
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
        if (4 != variableId) {
            return false;
        }
        setModel((BarcodeCaptureActivityViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivityBarcodeCaptureBinding
    public void setModel(BarcodeCaptureActivityViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeModelEnableCamera((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelEnableCamera(ObservableBoolean ModelEnableCamera, int fieldId) {
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
        int i;
        long j2;
        long j3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BarcodeCaptureActivityViewModel barcodeCaptureActivityViewModel = this.mModel;
        long j4 = j & 7;
        int i2 = 0;
        if (j4 != 0) {
            ObservableBoolean enableCamera = barcodeCaptureActivityViewModel != null ? barcodeCaptureActivityViewModel.getEnableCamera() : null;
            updateRegistration(0, enableCamera);
            boolean z = enableCamera != null ? enableCamera.get() : false;
            if (j4 != 0) {
                if (z) {
                    j2 = j | 16;
                    j3 = 64;
                } else {
                    j2 = j | 8;
                    j3 = 32;
                }
                j = j2 | j3;
            }
            i = z ? 8 : 0;
            if (!z) {
                i2 = 8;
            }
        } else {
            i = 0;
        }
        if ((j & 7) != 0) {
            this.cameraPreview.setVisibility(i2);
            this.imageViewSubstrate.setVisibility(i);
        }
    }
}
