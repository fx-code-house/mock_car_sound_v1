package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.notifications.NotificationInfo;

/* loaded from: classes.dex */
public abstract class ItemNotificationBinding extends ViewDataBinding {

    @Bindable
    protected NotificationInfo mModel;
    public final AppCompatImageButton notificationBtn;
    public final AppCompatTextView notificationText;

    public abstract void setModel(NotificationInfo model);

    protected ItemNotificationBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageButton notificationBtn, AppCompatTextView notificationText) {
        super(_bindingComponent, _root, _localFieldCount);
        this.notificationBtn = notificationBtn;
        this.notificationText = notificationText;
    }

    public NotificationInfo getModel() {
        return this.mModel;
    }

    public static ItemNotificationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNotificationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemNotificationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_notification, root, attachToRoot, component);
    }

    public static ItemNotificationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNotificationBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemNotificationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_notification, null, false, component);
    }

    public static ItemNotificationBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNotificationBinding bind(View view, Object component) {
        return (ItemNotificationBinding) bind(component, view, R.layout.item_notification);
    }
}
