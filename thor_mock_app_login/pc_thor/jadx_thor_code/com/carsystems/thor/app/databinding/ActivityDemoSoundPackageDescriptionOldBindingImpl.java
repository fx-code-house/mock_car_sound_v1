package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.app.gui.widget.ViewFlipperIndicator;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public class ActivityDemoSoundPackageDescriptionOldBindingImpl extends ActivityDemoSoundPackageDescriptionOldBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final ConstraintLayout mboundView4;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 6);
        sparseIntArray.put(R.id.nested_scroll_view, 7);
        sparseIntArray.put(R.id.view_tachometer, 8);
        sparseIntArray.put(R.id.video_view, 9);
        sparseIntArray.put(R.id.fab_play_sound, 10);
    }

    public ActivityDemoSoundPackageDescriptionOldBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActivityDemoSoundPackageDescriptionOldBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (FloatingActionButton) bindings[10], (ImageView) bindings[5], (NestedScrollView) bindings[7], (TextView) bindings[3], (TextView) bindings[1], (ToolbarWidget) bindings[6], (VideoView) bindings[9], (ViewFlipperIndicator) bindings[2], (TachometerWithEqualizeView) bindings[8]);
        this.mDirtyFlags = -1L;
        this.imageViewPlayVideo.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        ConstraintLayout constraintLayout2 = (ConstraintLayout) bindings[4];
        this.mboundView4 = constraintLayout2;
        constraintLayout2.setTag(null);
        this.textViewDescription.setTag(null);
        this.textViewPackageName.setTag(null);
        this.viewFlipper.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
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
        } else if (9 == variableId) {
            setSoundPackage((ShopSoundPackage) variable);
        } else {
            if (5 != variableId) {
                return false;
            }
            setPackageInfo((SoundPackageDescriptionResponse) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivityDemoSoundPackageDescriptionOldBinding
    public void setModel(SoundPackageDescriptionViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivityDemoSoundPackageDescriptionOldBinding
    public void setSoundPackage(ShopSoundPackage SoundPackage) {
        this.mSoundPackage = SoundPackage;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivityDemoSoundPackageDescriptionOldBinding
    public void setPackageInfo(SoundPackageDescriptionResponse PackageInfo) {
        this.mPackageInfo = PackageInfo;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(5);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsPlayingVideo((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeModelIsGallery((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeModelIsVideo((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsPlayingVideo(ObservableBoolean ModelIsPlayingVideo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelIsGallery(ObservableBoolean ModelIsGallery, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelIsVideo(ObservableBoolean ModelIsVideo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0048 A[PHI: r2
      0x0048: PHI (r2v3 long) = (r2v0 long), (r2v11 long) binds: [B:9:0x0022, B:22:0x0044] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0076 A[PHI: r2
      0x0076: PHI (r2v5 long) = (r2v4 long), (r2v9 long) binds: [B:26:0x004d, B:39:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a4  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 255
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivityDemoSoundPackageDescriptionOldBindingImpl.executeBindings():void");
    }
}
