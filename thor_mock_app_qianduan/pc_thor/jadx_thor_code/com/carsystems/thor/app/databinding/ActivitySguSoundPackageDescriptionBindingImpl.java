package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.basemodule.gui.view.AutoscrollRecyclerView;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;

/* loaded from: classes.dex */
public class ActivitySguSoundPackageDescriptionBindingImpl extends ActivitySguSoundPackageDescriptionBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ShapeableImageView mboundView1;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 6);
        sparseIntArray.put(R.id.nested_scroll_view, 7);
        sparseIntArray.put(R.id.main_info_layout, 8);
        sparseIntArray.put(R.id.autoscroll_recycler, 9);
        sparseIntArray.put(R.id.bottom_sheet, 10);
    }

    public ActivitySguSoundPackageDescriptionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActivitySguSoundPackageDescriptionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AutoscrollRecyclerView) bindings[9], (LinearLayout) bindings[10], (Button) bindings[4], (Button) bindings[5], (ConstraintLayout) bindings[0], (LinearLayout) bindings[8], (NestedScrollView) bindings[7], (TextView) bindings[3], (TextView) bindings[2], (ToolbarWidget) bindings[6]);
        this.mDirtyFlags = -1L;
        this.buttonBuy.setTag(null);
        this.buttonDownloadPackage.setTag(null);
        this.layoutMain.setTag(null);
        ShapeableImageView shapeableImageView = (ShapeableImageView) bindings[1];
        this.mboundView1 = shapeableImageView;
        shapeableImageView.setTag(null);
        this.textViewDescription.setTag(null);
        this.textViewPackageName.setTag(null);
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
            setModel((SoundPackageDescriptionViewModel) variable);
        } else {
            if (9 != variableId) {
                return false;
            }
            setSoundPackage((SguSoundSet) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySguSoundPackageDescriptionBinding
    public void setModel(SoundPackageDescriptionViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySguSoundPackageDescriptionBinding
    public void setSoundPackage(SguSoundSet SoundPackage) {
        this.mSoundPackage = SoundPackage;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsGooglePlayBilling((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeModelIsDownloadPackage((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsGooglePlayBilling(ObservableBoolean ModelIsGooglePlayBilling, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelIsDownloadPackage(ObservableBoolean ModelIsDownloadPackage, int fieldId) {
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
            Method dump skipped, instructions count: 191
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivitySguSoundPackageDescriptionBindingImpl.executeBindings():void");
    }
}
