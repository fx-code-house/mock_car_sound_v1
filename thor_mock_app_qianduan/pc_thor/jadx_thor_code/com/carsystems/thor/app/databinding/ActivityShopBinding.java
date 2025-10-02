package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityShopBinding extends ViewDataBinding {
    public final FragmentContainerView fragmentContainer;
    public final ConstraintLayout layoutMain;
    public final SwipeRefreshLayout swipeRefresh;
    public final ShopModeSwitchView switchShopMode;
    public final ToolbarWidget toolbarWidget;

    protected ActivityShopBinding(Object _bindingComponent, View _root, int _localFieldCount, FragmentContainerView fragmentContainer, ConstraintLayout layoutMain, SwipeRefreshLayout swipeRefresh, ShopModeSwitchView switchShopMode, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutMain = layoutMain;
        this.swipeRefresh = swipeRefresh;
        this.switchShopMode = switchShopMode;
        this.toolbarWidget = toolbarWidget;
    }

    public static ActivityShopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityShopBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_shop, root, attachToRoot, component);
    }

    public static ActivityShopBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityShopBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_shop, null, false, component);
    }

    public static ActivityShopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopBinding bind(View view, Object component) {
        return (ActivityShopBinding) bind(component, view, R.layout.activity_shop);
    }
}
