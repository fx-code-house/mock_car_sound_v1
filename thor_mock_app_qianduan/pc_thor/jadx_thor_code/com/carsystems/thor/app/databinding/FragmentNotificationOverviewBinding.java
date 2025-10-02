package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.notifications.NotificationInfo;

/* loaded from: classes.dex */
public abstract class FragmentNotificationOverviewBinding extends ViewDataBinding {

    @Bindable
    protected NotificationInfo mModel;
    public final NestedScrollView nestedScrollView;
    public final AppCompatImageView notificationImage;
    public final LinearLayoutCompat notificationInfoLayout;
    public final TextView textViewClick;

    public abstract void setModel(NotificationInfo model);

    protected FragmentNotificationOverviewBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView nestedScrollView, AppCompatImageView notificationImage, LinearLayoutCompat notificationInfoLayout, TextView textViewClick) {
        super(_bindingComponent, _root, _localFieldCount);
        this.nestedScrollView = nestedScrollView;
        this.notificationImage = notificationImage;
        this.notificationInfoLayout = notificationInfoLayout;
        this.textViewClick = textViewClick;
    }

    public NotificationInfo getModel() {
        return this.mModel;
    }

    public static FragmentNotificationOverviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNotificationOverviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentNotificationOverviewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_notification_overview, root, attachToRoot, component);
    }

    public static FragmentNotificationOverviewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNotificationOverviewBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentNotificationOverviewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_notification_overview, null, false, component);
    }

    public static FragmentNotificationOverviewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNotificationOverviewBinding bind(View view, Object component) {
        return (FragmentNotificationOverviewBinding) bind(component, view, R.layout.fragment_notification_overview);
    }
}
