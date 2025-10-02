package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.CircleProgressView;
import com.thor.businessmodule.viewmodel.main.UpdateFirmwareFragmentDialogViewModel;

/* loaded from: classes.dex */
public class FragmentDialogUpdateFirmwareBindingImpl extends FragmentDialogUpdateFirmwareBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.text_signal, 3);
        sparseIntArray.put(R.id.text_view_cancel, 4);
        sparseIntArray.put(R.id.progressBar, 5);
    }

    public FragmentDialogUpdateFirmwareBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private FragmentDialogUpdateFirmwareBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (FrameLayout) bindings[5], (TextView) bindings[3], (TextView) bindings[4], (TextView) bindings[2], (CircleProgressView) bindings[1]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.textViewInfo.setTag(null);
        this.updateProgress.setTag(null);
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
        if (4 != variableId) {
            return false;
        }
        setModel((UpdateFirmwareFragmentDialogViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.FragmentDialogUpdateFirmwareBinding
    public void setModel(UpdateFirmwareFragmentDialogViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsUpdatingFirmware((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeModelInfo((ObservableField) object, fieldId);
    }

    private boolean onChangeModelIsUpdatingFirmware(ObservableBoolean ModelIsUpdatingFirmware, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelInfo(ObservableField<String> ModelInfo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
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
        UpdateFirmwareFragmentDialogViewModel updateFirmwareFragmentDialogViewModel = this.mModel;
        String str = null;
        int i = 0;
        if ((15 & j) != 0) {
            long j2 = j & 13;
            if (j2 != 0) {
                ObservableBoolean isUpdatingFirmware = updateFirmwareFragmentDialogViewModel != null ? updateFirmwareFragmentDialogViewModel.getIsUpdatingFirmware() : null;
                updateRegistration(0, isUpdatingFirmware);
                boolean z = isUpdatingFirmware != null ? isUpdatingFirmware.get() : false;
                if (j2 != 0) {
                    j |= z ? 32L : 16L;
                }
                if (!z) {
                    i = 4;
                }
            }
            if ((j & 14) != 0) {
                ObservableField<String> info = updateFirmwareFragmentDialogViewModel != null ? updateFirmwareFragmentDialogViewModel.getInfo() : null;
                updateRegistration(1, info);
                if (info != null) {
                    str = info.get();
                }
            }
        }
        if ((j & 14) != 0) {
            TextViewBindingAdapter.setText(this.textViewInfo, str);
        }
        if ((j & 13) != 0) {
            this.updateProgress.setVisibility(i);
        }
    }
}
