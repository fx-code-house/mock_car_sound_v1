package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.CircleProgressView;
import com.thor.businessmodule.viewmodel.main.DownloadSoundPackageFragmentDialogViewModel;

/* loaded from: classes.dex */
public class FragmentDialogDownloadPackageBindingImpl extends FragmentDialogDownloadPackageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.text_view_title, 5);
        sparseIntArray.put(R.id.text_signal, 6);
        sparseIntArray.put(R.id.text_view_cancel, 7);
        sparseIntArray.put(R.id.progressBarPolling, 8);
    }

    public FragmentDialogDownloadPackageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private FragmentDialogDownloadPackageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4, (ProgressBar) bindings[4], (FrameLayout) bindings[8], (TextView) bindings[6], (TextView) bindings[7], (TextView) bindings[3], (TextView) bindings[1], (TextView) bindings[5], (CircleProgressView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.progressBar.setTag(null);
        this.textViewInfo.setTag(null);
        this.textViewPackageName.setTag(null);
        this.updateProgress.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
        setModel((DownloadSoundPackageFragmentDialogViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.FragmentDialogDownloadPackageBinding
    public void setModel(DownloadSoundPackageFragmentDialogViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsDownloading((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeModelPackageName((ObservableField) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeModelInfo((ObservableField) object, fieldId);
        }
        if (localFieldId != 3) {
            return false;
        }
        return onChangeModelIsUploading((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsDownloading(ObservableBoolean ModelIsDownloading, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelPackageName(ObservableField<String> ModelPackageName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelInfo(ObservableField<String> ModelInfo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeModelIsUploading(ObservableBoolean ModelIsUploading, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0048 A[PHI: r2
      0x0048: PHI (r2v5 long) = (r2v0 long), (r2v10 long) binds: [B:9:0x0020, B:22:0x0042] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ae  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 232
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.FragmentDialogDownloadPackageBindingImpl.executeBindings():void");
    }
}
