package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.viewmodel.login.SignUpActivityViewModel;

/* loaded from: classes.dex */
public class ActivitySignUpBindingImpl extends ActivitySignUpBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener editTextEmailandroidTextAttrChanged;
    private InverseBindingListener editTextPasswordandroidTextAttrChanged;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 4);
        sparseIntArray.put(R.id.cbInformMe, 5);
    }

    public ActivitySignUpBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ActivitySignUpBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (Button) bindings[1], (CheckBox) bindings[5], (EditText) bindings[2], (EditText) bindings[3], (ConstraintLayout) bindings[0], (ToolbarWidget) bindings[4]);
        this.editTextEmailandroidTextAttrChanged = new InverseBindingListener() { // from class: com.carsystems.thor.app.databinding.ActivitySignUpBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActivitySignUpBindingImpl.this.editTextEmail);
                SignUpActivityViewModel signUpActivityViewModel = ActivitySignUpBindingImpl.this.mModel;
                if (signUpActivityViewModel != null) {
                    ObservableField<String> email = signUpActivityViewModel.getEmail();
                    if (email != null) {
                        email.set(textString);
                    }
                }
            }
        };
        this.editTextPasswordandroidTextAttrChanged = new InverseBindingListener() { // from class: com.carsystems.thor.app.databinding.ActivitySignUpBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActivitySignUpBindingImpl.this.editTextPassword);
                SignUpActivityViewModel signUpActivityViewModel = ActivitySignUpBindingImpl.this.mModel;
                if (signUpActivityViewModel != null) {
                    ObservableField<String> password = signUpActivityViewModel.getPassword();
                    if (password != null) {
                        password.set(textString);
                    }
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.buttonSignUp.setTag(null);
        this.editTextEmail.setTag(null);
        this.editTextPassword.setTag(null);
        this.layoutMain.setTag(null);
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
        if (4 != variableId) {
            return false;
        }
        setModel((SignUpActivityViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySignUpBinding
    public void setModel(SignUpActivityViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelPassword((ObservableField) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeModelEnableSignUp((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeModelEmail((ObservableField) object, fieldId);
    }

    private boolean onChangeModelPassword(ObservableField<String> ModelPassword, int fieldId) {
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

    private boolean onChangeModelEmail(ObservableField<String> ModelEmail, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006b  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            r17 = this;
            r1 = r17
            monitor-enter(r17)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lad
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lad
            monitor-exit(r17)     // Catch: java.lang.Throwable -> Lad
            com.thor.businessmodule.viewmodel.login.SignUpActivityViewModel r0 = r1.mModel
            r6 = 31
            long r6 = r6 & r2
            int r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            r7 = 28
            r9 = 25
            r11 = 26
            r13 = 0
            r14 = 0
            if (r6 == 0) goto L6d
            long r15 = r2 & r9
            int r6 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r6 == 0) goto L35
            if (r0 == 0) goto L28
            androidx.databinding.ObservableField r6 = r0.getPassword()
            goto L29
        L28:
            r6 = r14
        L29:
            r1.updateRegistration(r13, r6)
            if (r6 == 0) goto L35
            java.lang.Object r6 = r6.get()
            java.lang.String r6 = (java.lang.String) r6
            goto L36
        L35:
            r6 = r14
        L36:
            long r15 = r2 & r11
            int r15 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r15 == 0) goto L4f
            if (r0 == 0) goto L43
            androidx.databinding.ObservableBoolean r15 = r0.getEnableSignUp()
            goto L44
        L43:
            r15 = r14
        L44:
            r13 = 1
            r1.updateRegistration(r13, r15)
            if (r15 == 0) goto L4f
            boolean r13 = r15.get()
            goto L50
        L4f:
            r13 = 0
        L50:
            long r15 = r2 & r7
            int r15 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r15 == 0) goto L6b
            if (r0 == 0) goto L5d
            androidx.databinding.ObservableField r0 = r0.getEmail()
            goto L5e
        L5d:
            r0 = r14
        L5e:
            r15 = 2
            r1.updateRegistration(r15, r0)
            if (r0 == 0) goto L6b
            java.lang.Object r0 = r0.get()
            java.lang.String r0 = (java.lang.String) r0
            goto L70
        L6b:
            r0 = r14
            goto L70
        L6d:
            r0 = r14
            r6 = r0
            r13 = 0
        L70:
            long r11 = r11 & r2
            int r11 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r11 == 0) goto L7a
            android.widget.Button r11 = r1.buttonSignUp
            r11.setEnabled(r13)
        L7a:
            long r7 = r7 & r2
            int r7 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r7 == 0) goto L84
            android.widget.EditText r7 = r1.editTextEmail
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r7, r0)
        L84:
            r7 = 16
            long r7 = r7 & r2
            int r0 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r0 == 0) goto La2
            android.widget.EditText r0 = r1.editTextEmail
            r7 = r14
            androidx.databinding.adapters.TextViewBindingAdapter$BeforeTextChanged r7 = (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged) r7
            r7 = r14
            androidx.databinding.adapters.TextViewBindingAdapter$OnTextChanged r7 = (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged) r7
            r7 = r14
            androidx.databinding.adapters.TextViewBindingAdapter$AfterTextChanged r7 = (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged) r7
            androidx.databinding.InverseBindingListener r7 = r1.editTextEmailandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r0, r14, r14, r14, r7)
            android.widget.EditText r0 = r1.editTextPassword
            androidx.databinding.InverseBindingListener r7 = r1.editTextPasswordandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r0, r14, r14, r14, r7)
        La2:
            long r2 = r2 & r9
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto Lac
            android.widget.EditText r0 = r1.editTextPassword
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r6)
        Lac:
            return
        Lad:
            r0 = move-exception
            monitor-exit(r17)     // Catch: java.lang.Throwable -> Lad
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivitySignUpBindingImpl.executeBindings():void");
    }
}
