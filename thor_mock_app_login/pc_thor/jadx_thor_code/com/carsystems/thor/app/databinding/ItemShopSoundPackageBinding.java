package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.soundpackage.ShopSoundPackageView;

/* loaded from: classes.dex */
public abstract class ItemShopSoundPackageBinding extends ViewDataBinding {
    public final ShopSoundPackageView shopSoundPackageView;

    protected ItemShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, ShopSoundPackageView shopSoundPackageView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.shopSoundPackageView = shopSoundPackageView;
    }

    public static ItemShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_shop_sound_package, root, attachToRoot, component);
    }

    public static ItemShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_shop_sound_package, null, false, component);
    }

    public static ItemShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShopSoundPackageBinding bind(View view, Object component) {
        return (ItemShopSoundPackageBinding) bind(component, view, R.layout.item_shop_sound_package);
    }
}
