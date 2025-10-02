package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class FragmentNotificationsBinding extends ViewDataBinding {
    public final NestedScrollView nestedScrollView;
    public final RecyclerView recyclerView;
    public final SwipeRefreshLayout swipeContainer;
    public final TextView textViewNoDevices;
    public final TextView textViewUpdateAll;

    protected FragmentNotificationsBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView nestedScrollView, RecyclerView recyclerView, SwipeRefreshLayout swipeContainer, TextView textViewNoDevices, TextView textViewUpdateAll) {
        super(_bindingComponent, _root, _localFieldCount);
        this.nestedScrollView = nestedScrollView;
        this.recyclerView = recyclerView;
        this.swipeContainer = swipeContainer;
        this.textViewNoDevices = textViewNoDevices;
        this.textViewUpdateAll = textViewUpdateAll;
    }

    public static FragmentNotificationsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNotificationsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentNotificationsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_notifications, root, attachToRoot, component);
    }

    public static FragmentNotificationsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNotificationsBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentNotificationsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_notifications, null, false, component);
    }

    public static FragmentNotificationsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNotificationsBinding bind(View view, Object component) {
        return (FragmentNotificationsBinding) bind(component, view, R.layout.fragment_notifications);
    }
}
