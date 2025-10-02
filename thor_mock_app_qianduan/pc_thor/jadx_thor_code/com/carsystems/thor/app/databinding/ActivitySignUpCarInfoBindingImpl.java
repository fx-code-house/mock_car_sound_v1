package com.carsystems.thor.app.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ButtonWidget;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.viewmodel.login.SignUpCarInfoActivityViewModel;
import com.thor.networkmodule.model.SignUpInfo;

/* loaded from: classes.dex */
public class ActivitySignUpCarInfoBindingImpl extends ActivitySignUpCarInfoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 8);
        sparseIntArray.put(R.id.guideline_left, 9);
        sparseIntArray.put(R.id.guideline_right, 10);
        sparseIntArray.put(R.id.layout_selector, 11);
        sparseIntArray.put(R.id.view_divider_selector_top, 12);
        sparseIntArray.put(R.id.text_view_apply_selector, 13);
        sparseIntArray.put(R.id.view_divider_selector_bottom, 14);
        sparseIntArray.put(R.id.view_picker_selector, 15);
    }

    public ActivitySignUpCarInfoBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private ActivitySignUpCarInfoBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 10, (ButtonWidget) bindings[7], (Guideline) bindings[9], (Guideline) bindings[10], (ConstraintLayout) bindings[0], (ConstraintLayout) bindings[11], (TextView) bindings[13], (TextView) bindings[5], (TextView) bindings[3], (TextView) bindings[4], (TextView) bindings[6], (TextView) bindings[2], (TextView) bindings[1], (ToolbarWidget) bindings[8], (View) bindings[14], (View) bindings[12], (NumberPicker) bindings[15]);
        this.mDirtyFlags = -1L;
        this.buttonContinue.setTag(null);
        this.layoutMain.setTag(null);
        this.textViewCarGeneration.setTag(null);
        this.textViewCarMark.setTag(null);
        this.textViewCarModel.setTag(null);
        this.textViewCarSeries.setTag(null);
        this.textViewDescription.setTag(null);
        this.textViewHeader.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
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
            setModel((SignUpCarInfoActivityViewModel) variable);
        } else {
            if (7 != variableId) {
                return false;
            }
            setSignUpInfo((SignUpInfo) variable);
        }
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySignUpCarInfoBinding
    public void setModel(SignUpCarInfoActivityViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySignUpCarInfoBinding
    public void setSignUpInfo(SignUpInfo SignUpInfo) {
        this.mSignUpInfo = SignUpInfo;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        notifyPropertyChanged(7);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeModelEnableCarGeneration((ObservableBoolean) object, fieldId);
            case 1:
                return onChangeModelEnableCarMark((ObservableBoolean) object, fieldId);
            case 2:
                return onChangeModelCarMark((ObservableField) object, fieldId);
            case 3:
                return onChangeModelEnableCarSeries((ObservableBoolean) object, fieldId);
            case 4:
                return onChangeModelIsSelector((ObservableBoolean) object, fieldId);
            case 5:
                return onChangeModelCarGeneration((ObservableField) object, fieldId);
            case 6:
                return onChangeModelEnableSignUp((ObservableBoolean) object, fieldId);
            case 7:
                return onChangeModelCarModel((ObservableField) object, fieldId);
            case 8:
                return onChangeModelCarSeries((ObservableField) object, fieldId);
            case 9:
                return onChangeModelEnableCarModel((ObservableBoolean) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeModelEnableCarGeneration(ObservableBoolean ModelEnableCarGeneration, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelEnableCarMark(ObservableBoolean ModelEnableCarMark, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelCarMark(ObservableField<String> ModelCarMark, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeModelEnableCarSeries(ObservableBoolean ModelEnableCarSeries, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeModelIsSelector(ObservableBoolean ModelIsSelector, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeModelCarGeneration(ObservableField<String> ModelCarGeneration, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeModelEnableSignUp(ObservableBoolean ModelEnableSignUp, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeModelCarModel(ObservableField<String> ModelCarModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeModelCarSeries(ObservableField<String> ModelCarSeries, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeModelEnableCarModel(ObservableBoolean ModelEnableCarModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c5 A[PHI: r2
      0x00c5: PHI (r2v3 long) = (r2v0 long), (r2v9 long) binds: [B:45:0x009e, B:58:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0114 A[PHI: r2
      0x0114: PHI (r2v5 long) = (r2v4 long), (r2v7 long) binds: [B:71:0x00e8, B:84:0x010e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x013f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 567
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivitySignUpCarInfoBindingImpl.executeBindings():void");
    }
}
