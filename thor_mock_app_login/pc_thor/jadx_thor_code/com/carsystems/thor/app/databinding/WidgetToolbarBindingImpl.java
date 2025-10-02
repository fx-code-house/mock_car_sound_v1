package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import com.thor.app.databinding.viewmodels.ToolbarWidgetViewModel;

/* loaded from: classes.dex */
public class WidgetToolbarBindingImpl extends WidgetToolbarBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;

    public WidgetToolbarBindingImpl(DataBindingComponent bindingComponent, View[] root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private WidgetToolbarBindingImpl(DataBindingComponent bindingComponent, View[] root, Object[] bindings) {
        super(bindingComponent, root[0], 3, (ImageButton) bindings[5], (Button) bindings[4], (ImageView) bindings[0], (TextView) bindings[1], (View) bindings[3], (View) bindings[2]);
        this.mDirtyFlags = -1L;
        this.btnNotificationsDelete.setTag(null);
        this.buttonDeviceConnectingStatus.setTag(null);
        this.imageToolbarHome.setTag(null);
        this.textToolbarTitle.setTag(null);
        this.viewDevServerMarker.setTag(null);
        this.viewToolbarDivider.setTag(null);
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
        setModel((ToolbarWidgetViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.WidgetToolbarBinding
    public void setModel(ToolbarWidgetViewModel Model) {
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
            return onChangeModelIsBluetoothConnected((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeModelIsBluetoothIndicator((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeModelIsNotificationDeleteBtn((ObservableBoolean) object, fieldId);
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

    private boolean onChangeModelIsBluetoothIndicator(ObservableBoolean ModelIsBluetoothIndicator, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelIsNotificationDeleteBtn(ObservableBoolean ModelIsNotificationDeleteBtn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0061 A[PHI: r2
      0x0061: PHI (r2v6 long) = (r2v0 long), (r2v10 long) binds: [B:18:0x0039, B:31:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 181
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.WidgetToolbarBindingImpl.executeBindings():void");
    }
}
