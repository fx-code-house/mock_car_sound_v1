package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.thor.app.databinding.adapters.NotificationsBindingAdapter;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;

/* loaded from: classes.dex */
public class ItemNotificationBindingImpl extends ItemNotificationBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemNotificationBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ItemNotificationBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageButton) bindings[2], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.notificationBtn.setTag(null);
        this.notificationText.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        setModel((NotificationInfo) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ItemNotificationBinding
    public void setModel(NotificationInfo Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        NotificationType notificationType;
        String title;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        NotificationInfo notificationInfo = this.mModel;
        long j2 = j & 3;
        if (j2 == 0 || notificationInfo == null) {
            notificationType = null;
            title = null;
        } else {
            notificationType = notificationInfo.getNotificationType();
            title = notificationInfo.getTitle();
        }
        if (j2 != 0) {
            NotificationsBindingAdapter.bindBtnDrawable(this.mboundView0, notificationType);
            NotificationsBindingAdapter.bindBtnDrawable(this.notificationBtn, notificationType);
            TextViewBindingAdapter.setText(this.notificationText, title);
            NotificationsBindingAdapter.bindBtnDrawable(this.notificationText, notificationType);
        }
    }
}
