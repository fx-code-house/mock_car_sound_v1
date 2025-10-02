package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.viewmodels.DemoActivityViewModel;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityDemoBinding extends ViewDataBinding {
    public final ConstraintLayout bottomMenu;
    public final FragmentContainerView fragmentContainer;
    public final ConstraintLayout layoutMain;

    @Bindable
    protected DemoActivityViewModel mModel;
    public final AppCompatImageButton siteBtn;
    public final ShopModeSwitchView switchShopMode;
    public final AppCompatImageButton telegramBtn;
    public final TextView textViewOrderDevice;
    public final ToolbarWidget toolbarWidget;

    public abstract void setModel(DemoActivityViewModel model);

    protected ActivityDemoBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout bottomMenu, FragmentContainerView fragmentContainer, ConstraintLayout layoutMain, AppCompatImageButton siteBtn, ShopModeSwitchView switchShopMode, AppCompatImageButton telegramBtn, TextView textViewOrderDevice, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bottomMenu = bottomMenu;
        this.fragmentContainer = fragmentContainer;
        this.layoutMain = layoutMain;
        this.siteBtn = siteBtn;
        this.switchShopMode = switchShopMode;
        this.telegramBtn = telegramBtn;
        this.textViewOrderDevice = textViewOrderDevice;
        this.toolbarWidget = toolbarWidget;
    }

    public DemoActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivityDemoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityDemoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_demo, root, attachToRoot, component);
    }

    public static ActivityDemoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityDemoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_demo, null, false, component);
    }

    public static ActivityDemoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoBinding bind(View view, Object component) {
        return (ActivityDemoBinding) bind(component, view, R.layout.activity_demo);
    }
}
