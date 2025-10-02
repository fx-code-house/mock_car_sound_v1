package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import com.carsystems.thor.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityMainBinding extends ViewDataBinding {
    public final FrameLayout buttonAddSound;
    public final ConstraintLayout buttonsContainer;
    public final DrawerLayout drawerLayout;
    public final SwitchMaterial driveSelectSwitch;
    public final FloatingActionButton floatingButtonAddSound;
    public final FragmentContainerView fragmentContainer;
    public final AppCompatImageView iconCloseSguCongig;
    public final AppCompatImageView iconRepeatSguConfig;
    public final AppCompatImageView iconSguVolume;
    public final LayoutMainMenuBinding layoutMainMenu;

    @Bindable
    protected MainActivityViewModel mModel;
    public final NavigationView navigationView;
    public final FrameLayout progressBar;
    public final AppCompatSeekBar seekBarSguVolume;
    public final ConstraintLayout sguConfigLayout;
    public final ShopModeSwitchView switchShopMode;
    public final ToolbarWidget toolbarWidget;

    public abstract void setModel(MainActivityViewModel model);

    protected ActivityMainBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout buttonAddSound, ConstraintLayout buttonsContainer, DrawerLayout drawerLayout, SwitchMaterial driveSelectSwitch, FloatingActionButton floatingButtonAddSound, FragmentContainerView fragmentContainer, AppCompatImageView iconCloseSguCongig, AppCompatImageView iconRepeatSguConfig, AppCompatImageView iconSguVolume, LayoutMainMenuBinding layoutMainMenu, NavigationView navigationView, FrameLayout progressBar, AppCompatSeekBar seekBarSguVolume, ConstraintLayout sguConfigLayout, ShopModeSwitchView switchShopMode, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonAddSound = buttonAddSound;
        this.buttonsContainer = buttonsContainer;
        this.drawerLayout = drawerLayout;
        this.driveSelectSwitch = driveSelectSwitch;
        this.floatingButtonAddSound = floatingButtonAddSound;
        this.fragmentContainer = fragmentContainer;
        this.iconCloseSguCongig = iconCloseSguCongig;
        this.iconRepeatSguConfig = iconRepeatSguConfig;
        this.iconSguVolume = iconSguVolume;
        this.layoutMainMenu = layoutMainMenu;
        this.navigationView = navigationView;
        this.progressBar = progressBar;
        this.seekBarSguVolume = seekBarSguVolume;
        this.sguConfigLayout = sguConfigLayout;
        this.switchShopMode = switchShopMode;
        this.toolbarWidget = toolbarWidget;
    }

    public MainActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_main, root, attachToRoot, component);
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_main, null, false, component);
    }

    public static ActivityMainBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding bind(View view, Object component) {
        return (ActivityMainBinding) bind(component, view, R.layout.activity_main);
    }
}
