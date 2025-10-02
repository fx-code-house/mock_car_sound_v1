package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.activities.main.MainActivityViewModel;

/* loaded from: classes.dex */
public abstract class FragmentMainSoundsBinding extends ViewDataBinding {

    @Bindable
    protected MainActivityViewModel mModel;
    public final NestedScrollView nestedScrollView;
    public final RecyclerView recyclerView;
    public final SwipeRefreshLayout swipeContainer;
    public final TextView textViewNoDevices;

    public abstract void setModel(MainActivityViewModel model);

    protected FragmentMainSoundsBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView nestedScrollView, RecyclerView recyclerView, SwipeRefreshLayout swipeContainer, TextView textViewNoDevices) {
        super(_bindingComponent, _root, _localFieldCount);
        this.nestedScrollView = nestedScrollView;
        this.recyclerView = recyclerView;
        this.swipeContainer = swipeContainer;
        this.textViewNoDevices = textViewNoDevices;
    }

    public MainActivityViewModel getModel() {
        return this.mModel;
    }

    public static FragmentMainSoundsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainSoundsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentMainSoundsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_main_sounds, root, attachToRoot, component);
    }

    public static FragmentMainSoundsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainSoundsBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentMainSoundsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_main_sounds, null, false, component);
    }

    public static FragmentMainSoundsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainSoundsBinding bind(View view, Object component) {
        return (FragmentMainSoundsBinding) bind(component, view, R.layout.fragment_main_sounds);
    }
}
