package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.carsystems.thor.app.R;
import com.google.android.material.tabs.TabLayout;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public class ActivitySoundPackageDescriptionBindingImpl extends ActivitySoundPackageDescriptionBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView6;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 11);
        sparseIntArray.put(R.id.nested_scroll_view, 12);
        sparseIntArray.put(R.id.video_view, 13);
    }

    public ActivitySoundPackageDescriptionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private ActivitySoundPackageDescriptionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (Button) bindings[8], (Button) bindings[9], (ImageView) bindings[7], (ConstraintLayout) bindings[0], (NestedScrollView) bindings[12], (TabLayout) bindings[4], (TextView) bindings[5], (TextView) bindings[2], (ToolbarWidget) bindings[11], (VideoView) bindings[13], (ViewPager) bindings[3], (CheckedTextView) bindings[10], (TachometerWithEqualizeView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.buttonBuy.setTag(null);
        this.buttonDownloadPackage.setTag(null);
        this.imageViewPlayVideo.setTag(null);
        this.layoutMain.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[6];
        this.mboundView6 = constraintLayout;
        constraintLayout.setTag(null);
        this.tabLayout.setTag(null);
        this.textViewDescription.setTag(null);
        this.textViewPackageName.setTag(null);
        this.viewPager.setTag(null);
        this.viewPlaySound.setTag(null);
        this.viewTachometer.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 512L;
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

    @Override // com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBinding
    public void setModel(SoundPackageDescriptionViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBinding
    public void setSoundPackage(ShopSoundPackage SoundPackage) {
        this.mSoundPackage = SoundPackage;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBinding
    public void setPackageInfo(SoundPackageDescriptionResponse PackageInfo) {
        this.mPackageInfo = PackageInfo;
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        notifyPropertyChanged(5);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsGooglePlayBilling((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeModelIsPlayingVideo((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeModelIsGallery((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeModelIsVideo((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 4) {
            return onChangeModelIsDownloadPackage((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 5) {
            return false;
        }
        return onChangeModelIsPlaying((ObservableBoolean) object, fieldId);
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

    private boolean onChangeModelIsPlayingVideo(ObservableBoolean ModelIsPlayingVideo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelIsGallery(ObservableBoolean ModelIsGallery, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeModelIsVideo(ObservableBoolean ModelIsVideo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeModelIsDownloadPackage(ObservableBoolean ModelIsDownloadPackage, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeModelIsPlaying(ObservableBoolean ModelIsPlaying, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0051 A[PHI: r2
      0x0051: PHI (r2v3 long) = (r2v0 long), (r2v20 long) binds: [B:9:0x0026, B:22:0x004b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x007f A[PHI: r2
      0x007f: PHI (r2v5 long) = (r2v4 long), (r2v18 long) binds: [B:27:0x0056, B:40:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ac A[PHI: r2
      0x00ac: PHI (r2v7 long) = (r2v6 long), (r2v16 long) binds: [B:44:0x0084, B:57:0x00a6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00d9 A[PHI: r2
      0x00d9: PHI (r2v9 long) = (r2v8 long), (r2v14 long) binds: [B:62:0x00b1, B:75:0x00d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x010b  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 453
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBindingImpl.executeBindings():void");
    }
}
