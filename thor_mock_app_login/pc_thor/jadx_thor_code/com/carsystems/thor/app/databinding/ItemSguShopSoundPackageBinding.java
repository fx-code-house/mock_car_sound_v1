package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.sgu.SguShopSoundPackageView;

/* loaded from: classes.dex */
public abstract class ItemSguShopSoundPackageBinding extends ViewDataBinding {
    public final SguShopSoundPackageView shopSoundPackageView;

    protected ItemSguShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, SguShopSoundPackageView shopSoundPackageView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.shopSoundPackageView = shopSoundPackageView;
    }

    public static ItemSguShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSguShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_shop_sound_package, root, attachToRoot, component);
    }

    public static ItemSguShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSguShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_shop_sound_package, null, false, component);
    }

    public static ItemSguShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguShopSoundPackageBinding bind(View view, Object component) {
        return (ItemSguShopSoundPackageBinding) bind(component, view, R.layout.item_sgu_shop_sound_package);
    }
}
