package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.demo.DemoShopSoundPackageView;

/* loaded from: classes.dex */
public abstract class ItemDemoShopSoundPackageBinding extends ViewDataBinding {
    public final DemoShopSoundPackageView demoShopSoundPackageView;

    protected ItemDemoShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, DemoShopSoundPackageView demoShopSoundPackageView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.demoShopSoundPackageView = demoShopSoundPackageView;
    }

    public static ItemDemoShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDemoShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_demo_shop_sound_package, root, attachToRoot, component);
    }

    public static ItemDemoShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDemoShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_demo_shop_sound_package, null, false, component);
    }

    public static ItemDemoShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoShopSoundPackageBinding bind(View view, Object component) {
        return (ItemDemoShopSoundPackageBinding) bind(component, view, R.layout.item_demo_shop_sound_package);
    }
}
