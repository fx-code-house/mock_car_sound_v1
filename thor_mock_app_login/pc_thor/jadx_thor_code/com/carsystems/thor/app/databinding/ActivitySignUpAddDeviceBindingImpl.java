package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel;
import com.thor.app.gui.widget.ButtonWidget;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public class ActivitySignUpAddDeviceBindingImpl extends ActivitySignUpAddDeviceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener editTextDeviceIdandroidTextAttrChanged;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 3);
        sparseIntArray.put(R.id.text_view_description, 4);
        sparseIntArray.put(R.id.guideline4, 5);
        sparseIntArray.put(R.id.guideline7, 6);
    }

    public ActivitySignUpAddDeviceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActivitySignUpAddDeviceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (ButtonWidget) bindings[2], (EditText) bindings[1], (Guideline) bindings[5], (Guideline) bindings[6], (ConstraintLayout) bindings[0], (TextView) bindings[4], (ToolbarWidget) bindings[3]);
        this.editTextDeviceIdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.carsystems.thor.app.databinding.ActivitySignUpAddDeviceBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActivitySignUpAddDeviceBindingImpl.this.editTextDeviceId);
                SignUpAddDeviceActivityViewModel signUpAddDeviceActivityViewModel = ActivitySignUpAddDeviceBindingImpl.this.mModel;
                if (signUpAddDeviceActivityViewModel != null) {
                    ObservableField<String> deviceSn = signUpAddDeviceActivityViewModel.getDeviceSn();
                    if (deviceSn != null) {
                        deviceSn.set(textString);
                    }
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.buttonSignUp.setTag(null);
        this.editTextDeviceId.setTag(null);
        this.mainLayout.setTag(null);
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
        setModel((SignUpAddDeviceActivityViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySignUpAddDeviceBinding
    public void setModel(SignUpAddDeviceActivityViewModel Model) {
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
            return onChangeModelDeviceSn((ObservableField) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeModelEnableSignUp((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelDeviceSn(ObservableField<String> ModelDeviceSn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelEnableSignUp(ObservableBoolean ModelEnableSignUp, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> L78
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L78
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L78
            com.thor.app.databinding.viewmodels.SignUpAddDeviceActivityViewModel r4 = r14.mModel
            r5 = 15
            long r5 = r5 & r0
            int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            r6 = 13
            r8 = 14
            r10 = 0
            r11 = 0
            if (r5 == 0) goto L4b
            long r12 = r0 & r6
            int r5 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r5 == 0) goto L31
            if (r4 == 0) goto L24
            androidx.databinding.ObservableField r5 = r4.getDeviceSn()
            goto L25
        L24:
            r5 = r11
        L25:
            r14.updateRegistration(r10, r5)
            if (r5 == 0) goto L31
            java.lang.Object r5 = r5.get()
            java.lang.String r5 = (java.lang.String) r5
            goto L32
        L31:
            r5 = r11
        L32:
            long r12 = r0 & r8
            int r12 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r12 == 0) goto L4c
            if (r4 == 0) goto L3f
            androidx.databinding.ObservableBoolean r4 = r4.getEnableSignUp()
            goto L40
        L3f:
            r4 = r11
        L40:
            r12 = 1
            r14.updateRegistration(r12, r4)
            if (r4 == 0) goto L4c
            boolean r10 = r4.get()
            goto L4c
        L4b:
            r5 = r11
        L4c:
            long r8 = r8 & r0
            int r4 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r4 == 0) goto L56
            com.thor.app.gui.widget.ButtonWidget r4 = r14.buttonSignUp
            r4.setEnabled(r10)
        L56:
            long r6 = r6 & r0
            int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r4 == 0) goto L60
            android.widget.EditText r4 = r14.editTextDeviceId
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r4, r5)
        L60:
            r4 = 8
            long r0 = r0 & r4
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L77
            android.widget.EditText r0 = r14.editTextDeviceId
            r1 = r11
            androidx.databinding.adapters.TextViewBindingAdapter$BeforeTextChanged r1 = (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged) r1
            r1 = r11
            androidx.databinding.adapters.TextViewBindingAdapter$OnTextChanged r1 = (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged) r1
            r1 = r11
            androidx.databinding.adapters.TextViewBindingAdapter$AfterTextChanged r1 = (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged) r1
            androidx.databinding.InverseBindingListener r1 = r14.editTextDeviceIdandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r0, r11, r11, r11, r1)
        L77:
            return
        L78:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L78
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivitySignUpAddDeviceBindingImpl.executeBindings():void");
    }
}
