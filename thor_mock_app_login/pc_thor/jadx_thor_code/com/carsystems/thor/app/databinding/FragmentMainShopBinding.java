package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class FragmentMainShopBinding extends ViewDataBinding {
    public final NestedScrollView nestedScrollView;
    public final RecyclerView recyclerView;
    public final SwipeRefreshLayout swipeContainer;

    protected FragmentMainShopBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView nestedScrollView, RecyclerView recyclerView, SwipeRefreshLayout swipeContainer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.nestedScrollView = nestedScrollView;
        this.recyclerView = recyclerView;
        this.swipeContainer = swipeContainer;
    }

    public static FragmentMainShopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainShopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentMainShopBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_main_shop, root, attachToRoot, component);
    }

    public static FragmentMainShopBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainShopBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentMainShopBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_main_shop, null, false, component);
    }

    public static FragmentMainShopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainShopBinding bind(View view, Object component) {
        return (FragmentMainShopBinding) bind(component, view, R.layout.fragment_main_shop);
    }
}
