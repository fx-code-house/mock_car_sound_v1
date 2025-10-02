package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityShopOldBinding extends ViewDataBinding {
    public final FragmentContainerView fragmentContainer;
    public final ConstraintLayout layoutMain;
    public final NestedScrollView nestedScrollView;
    public final RecyclerView recyclerView;
    public final SwipeRefreshLayout swipeContainer;
    public final ShopModeSwitchView switchShopMode;
    public final ToolbarWidget toolbarWidget;

    protected ActivityShopOldBinding(Object _bindingComponent, View _root, int _localFieldCount, FragmentContainerView fragmentContainer, ConstraintLayout layoutMain, NestedScrollView nestedScrollView, RecyclerView recyclerView, SwipeRefreshLayout swipeContainer, ShopModeSwitchView switchShopMode, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutMain = layoutMain;
        this.nestedScrollView = nestedScrollView;
        this.recyclerView = recyclerView;
        this.swipeContainer = swipeContainer;
        this.switchShopMode = switchShopMode;
        this.toolbarWidget = toolbarWidget;
    }

    public static ActivityShopOldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopOldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityShopOldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_shop_old, root, attachToRoot, component);
    }

    public static ActivityShopOldBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopOldBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityShopOldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_shop_old, null, false, component);
    }

    public static ActivityShopOldBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopOldBinding bind(View view, Object component) {
        return (ActivityShopOldBinding) bind(component, view, R.layout.activity_shop_old);
    }
}
