package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel;
import com.thor.app.gui.views.RssiSignalView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public class ActivitySettingsBindingImpl extends ActivitySettingsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 2);
        sparseIntArray.put(R.id.action_new_device, 3);
        sparseIntArray.put(R.id.action_my_auto, 4);
        sparseIntArray.put(R.id.action_drive_select, 5);
        sparseIntArray.put(R.id.drive_select_text, 6);
        sparseIntArray.put(R.id.drive_select_tip, 7);
        sparseIntArray.put(R.id.drive_select_switch, 8);
        sparseIntArray.put(R.id.action_native_button_control, 9);
        sparseIntArray.put(R.id.native_control_text, 10);
        sparseIntArray.put(R.id.native_control_tip, 11);
        sparseIntArray.put(R.id.native_control_switch, 12);
        sparseIntArray.put(R.id.action_two_speaker_installed, 13);
        sparseIntArray.put(R.id.two_speaker_switch, 14);
        sparseIntArray.put(R.id.bluetooth_signal, 15);
        sparseIntArray.put(R.id.action_format_flash_button, 16);
        sparseIntArray.put(R.id.format_flash_text, 17);
        sparseIntArray.put(R.id.format_flash_tip, 18);
        sparseIntArray.put(R.id.progressBar, 19);
    }

    public ActivitySettingsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }

    private ActivitySettingsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[16], (LinearLayout) bindings[4], (ConstraintLayout) bindings[9], (LinearLayout) bindings[3], (LinearLayout) bindings[13], (ConstraintLayout) bindings[15], (RssiSignalView) bindings[1], (SwitchMaterial) bindings[8], (AppCompatTextView) bindings[6], (AppCompatImageButton) bindings[7], (AppCompatTextView) bindings[17], (AppCompatImageButton) bindings[18], (SwitchMaterial) bindings[12], (AppCompatTextView) bindings[10], (AppCompatImageButton) bindings[11], (FrameLayout) bindings[19], (ToolbarWidget) bindings[2], (SwitchMaterial) bindings[14]);
        this.mDirtyFlags = -1L;
        this.customView.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        setModel((SettingsActivityViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivitySettingsBinding
    public void setModel(SettingsActivityViewModel Model) {
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
            return onChangeModelIsBluetoothConnected((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeModelRssiSignal((ObservableInt) object, fieldId);
    }

    private boolean onChangeModelIsBluetoothConnected(ObservableBoolean ModelIsBluetoothConnected, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelRssiSignal(ObservableInt ModelRssiSignal, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0040 A[PHI: r0
      0x0040: PHI (r0v6 long) = (r0v1 long), (r0v8 long) binds: [B:8:0x001b, B:21:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0047  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            r15 = this;
            monitor-enter(r15)
            long r0 = r15.mDirtyFlags     // Catch: java.lang.Throwable -> L70
            r2 = 0
            r15.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L70
            monitor-exit(r15)     // Catch: java.lang.Throwable -> L70
            com.thor.app.gui.activities.settings.SettingsActivityViewModel r4 = r15.mModel
            r5 = 15
            long r5 = r5 & r0
            int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            r6 = 14
            r8 = 13
            r10 = 0
            if (r5 == 0) goto L5a
            long r11 = r0 & r8
            int r5 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            r11 = 0
            if (r5 == 0) goto L40
            if (r4 == 0) goto L24
            androidx.databinding.ObservableBoolean r12 = r4.getIsBluetoothConnected()
            goto L25
        L24:
            r12 = r11
        L25:
            r15.updateRegistration(r10, r12)
            if (r12 == 0) goto L2f
            boolean r12 = r12.get()
            goto L30
        L2f:
            r12 = r10
        L30:
            if (r5 == 0) goto L3a
            if (r12 == 0) goto L37
            r13 = 32
            goto L39
        L37:
            r13 = 16
        L39:
            long r0 = r0 | r13
        L3a:
            if (r12 == 0) goto L3d
            goto L40
        L3d:
            r5 = 8
            goto L41
        L40:
            r5 = r10
        L41:
            long r12 = r0 & r6
            int r12 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r12 == 0) goto L57
            if (r4 == 0) goto L4d
            androidx.databinding.ObservableInt r11 = r4.getRssiSignal()
        L4d:
            r4 = 1
            r15.updateRegistration(r4, r11)
            if (r11 == 0) goto L57
            int r10 = r11.get()
        L57:
            r4 = r10
            r10 = r5
            goto L5b
        L5a:
            r4 = r10
        L5b:
            long r8 = r8 & r0
            int r5 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r5 == 0) goto L65
            com.thor.app.gui.views.RssiSignalView r5 = r15.customView
            r5.setVisibility(r10)
        L65:
            long r0 = r0 & r6
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L6f
            com.thor.app.gui.views.RssiSignalView r0 = r15.customView
            com.thor.app.databinding.adapters.ViewBindingAdapter.setNewLevel(r0, r4)
        L6f:
            return
        L70:
            r0 = move-exception
            monitor-exit(r15)     // Catch: java.lang.Throwable -> L70
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.ActivitySettingsBindingImpl.executeBindings():void");
    }
}
