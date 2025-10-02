package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class FragmentSguShopBinding extends ViewDataBinding {
    public final RecyclerView recyclerView;
    public final SwipeRefreshLayout swipeContainer;

    protected FragmentSguShopBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView recyclerView, SwipeRefreshLayout swipeContainer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.recyclerView = recyclerView;
        this.swipeContainer = swipeContainer;
    }

    public static FragmentSguShopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentSguShopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentSguShopBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_sgu_shop, root, attachToRoot, component);
    }

    public static FragmentSguShopBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentSguShopBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentSguShopBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_sgu_shop, null, false, component);
    }

    public static FragmentSguShopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentSguShopBinding bind(View view, Object component) {
        return (FragmentSguShopBinding) bind(component, view, R.layout.fragment_sgu_shop);
    }
}
