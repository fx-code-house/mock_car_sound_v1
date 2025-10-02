package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.NotificationsBindingAdapter;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;

/* loaded from: classes.dex */
public class FragmentNotificationOverviewBindingImpl extends FragmentNotificationOverviewBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView2;
    private final AppCompatTextView mboundView3;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.nested_scroll_view, 5);
        sparseIntArray.put(R.id.notification_info_layout, 6);
    }

    public FragmentNotificationOverviewBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private FragmentNotificationOverviewBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (NestedScrollView) bindings[5], (AppCompatImageView) bindings[1], (LinearLayoutCompat) bindings[6], (TextView) bindings[4]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.notificationImage.setTag(null);
        this.textViewClick.setTag(null);
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

    @Override // com.carsystems.thor.app.databinding.FragmentNotificationOverviewBinding
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
        String image;
        String message;
        String title;
        NotificationType notificationType;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        NotificationInfo notificationInfo = this.mModel;
        long j2 = j & 3;
        if (j2 == 0 || notificationInfo == null) {
            image = null;
            message = null;
            title = null;
            notificationType = null;
        } else {
            image = notificationInfo.getImage();
            message = notificationInfo.getMessage();
            title = notificationInfo.getTitle();
            notificationType = notificationInfo.getNotificationType();
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, title);
            TextViewBindingAdapter.setText(this.mboundView3, message);
            NotificationsBindingAdapter.bindLoadImage(this.notificationImage, image);
            NotificationsBindingAdapter.bindBtnText(this.textViewClick, notificationType);
        }
    }
}
