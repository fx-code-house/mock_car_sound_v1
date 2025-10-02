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
import com.thor.businessmodule.viewmodel.ChangeCarActivityViewModel;
import com.thor.networkmodule.model.ChangeCarInfo;

/* loaded from: classes.dex */
public class ActivityChangeCarBindingImpl extends ActivityChangeCarBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 6);
        sparseIntArray.put(R.id.guideline_left, 7);
        sparseIntArray.put(R.id.guideline_right, 8);
        sparseIntArray.put(R.id.layout_selector, 9);
        sparseIntArray.put(R.id.view_divider_selector_top, 10);
        sparseIntArray.put(R.id.text_view_apply_selector, 11);
        sparseIntArray.put(R.id.view_divider_selector_bottom, 12);
        sparseIntArray.put(R.id.view_picker_selector, 13);
    }

    public ActivityChangeCarBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private ActivityChangeCarBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 9, (ButtonWidget) bindings[5], (Guideline) bindings[7], (Guideline) bindings[8], (ConstraintLayout) bindings[0], (ConstraintLayout) bindings[9], (TextView) bindings[11], (TextView) bindings[3], (TextView) bindings[1], (TextView) bindings[2], (TextView) bindings[4], (ToolbarWidget) bindings[6], (View) bindings[12], (View) bindings[10], (NumberPicker) bindings[13]);
        this.mDirtyFlags = -1L;
        this.buttonChange.setTag(null);
        this.layoutMain.setTag(null);
        this.textViewCarGeneration.setTag(null);
        this.textViewCarMark.setTag(null);
        this.textViewCarModel.setTag(null);
        this.textViewCarSeries.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
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
            setModel((ChangeCarActivityViewModel) variable);
            return true;
        }
        if (1 != variableId) {
            return false;
        }
        setChangeCarInfo((ChangeCarInfo) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivityChangeCarBinding
    public void setModel(ChangeCarActivityViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.carsystems.thor.app.databinding.ActivityChangeCarBinding
    public void setChangeCarInfo(ChangeCarInfo ChangeCarInfo) {
        this.mChangeCarInfo = ChangeCarInfo;
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
                return onChangeModelCarGeneration((ObservableField) object, fieldId);
            case 5:
                return onChangeModelEnableChangeCar((ObservableBoolean) object, fieldId);
            case 6:
                return onChangeModelCarModel((ObservableField) object, fieldId);
            case 7:
                return onChangeModelCarSeries((ObservableField) object, fieldId);
            case 8:
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

    private boolean onChangeModelCarGeneration(ObservableField<String> ModelCarGeneration, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeModelEnableChangeCar(ObservableBoolean ModelEnableChangeCar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeModelCarModel(ObservableField<String> ModelCarModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeModelCarSeries(ObservableField<String> ModelCarSeries, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeModelEnableCarModel(ObservableBoolean ModelEnableCarModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d4 A[PHI: r2
      0x00d4: PHI (r2v3 long) = (r2v0 long), (r2v5 long) binds: [B:54:0x00ad, B:67:0x00ce] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0135  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 434
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivityChangeCarBindingImpl.executeBindings():void");
    }
}
