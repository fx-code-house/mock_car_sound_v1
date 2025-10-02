package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.viewmodel.menu.MainMenuViewModel;

/* loaded from: classes.dex */
public class LayoutMainMenuBindingImpl extends LayoutMainMenuBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final CoordinatorLayout mboundView0;
    private final View mboundView6;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.image_view_close, 12);
        sparseIntArray.put(R.id.image_view_logo, 13);
        sparseIntArray.put(R.id.text_view_info, 14);
        sparseIntArray.put(R.id.nested_scroll_view, 15);
        sparseIntArray.put(R.id.flag_notifications_unread, 16);
        sparseIntArray.put(R.id.text_view_share_logs, 17);
        sparseIntArray.put(R.id.text_view_support_form, 18);
        sparseIntArray.put(R.id.text_view_settings, 19);
        sparseIntArray.put(R.id.layout_language, 20);
        sparseIntArray.put(R.id.view_divider_language_top, 21);
        sparseIntArray.put(R.id.text_view_change_language, 22);
        sparseIntArray.put(R.id.view_divider_language_bottom, 23);
    }

    public LayoutMainMenuBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }

    private LayoutMainMenuBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (View) bindings[16], (ImageView) bindings[12], (ImageView) bindings[13], (ConstraintLayout) bindings[20], (NestedScrollView) bindings[15], (TextView) bindings[5], (TextView) bindings[2], (TextView) bindings[22], (TextView) bindings[9], (TextView) bindings[14], (TextView) bindings[11], (TextView) bindings[10], (TextView) bindings[8], (TextView) bindings[1], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[18], (TextView) bindings[7], (TextView) bindings[4], (TextView) bindings[3], (View) bindings[23], (View) bindings[21]);
        this.mDirtyFlags = -1L;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bindings[0];
        this.mboundView0 = coordinatorLayout;
        coordinatorLayout.setTag(null);
        View view = (View) bindings[6];
        this.mboundView6 = view;
        view.setTag(null);
        this.textNotifications.setTag(null);
        this.textViewAppVersion.setTag(null);
        this.textViewDemoMode.setTag(null);
        this.textViewLanguage.setTag(null);
        this.textViewMyCar.setTag(null);
        this.textViewNewDevice.setTag(null);
        this.textViewSerialNumber.setTag(null);
        this.textViewUpdateSoftware.setTag(null);
        this.textViewUserId.setTag(null);
        this.textViewVersion.setTag(null);
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
        if (2 != variableId) {
            return false;
        }
        setMainMenu((MainMenuViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.LayoutMainMenuBinding
    public void setMainMenu(MainMenuViewModel MainMenu) {
        this.mMainMenu = MainMenu;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeMainMenuAppVersion((ObservableField) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeMainMenuIsUpdateSoftware((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeMainMenuSerialNumber((ObservableField) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeMainMenuVersion((ObservableField) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeMainMenuUserId((ObservableField) object, fieldId);
    }

    private boolean onChangeMainMenuAppVersion(ObservableField<String> MainMenuAppVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeMainMenuIsUpdateSoftware(ObservableBoolean MainMenuIsUpdateSoftware, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeMainMenuSerialNumber(ObservableField<String> MainMenuSerialNumber, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeMainMenuVersion(ObservableField<String> MainMenuVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeMainMenuUserId(ObservableField<String> MainMenuUserId, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0075 A[PHI: r2
      0x0075: PHI (r2v3 long) = (r2v0 long), (r2v5 long) binds: [B:20:0x004f, B:33:0x006f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0107  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 434
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.LayoutMainMenuBindingImpl.executeBindings():void");
    }
}
