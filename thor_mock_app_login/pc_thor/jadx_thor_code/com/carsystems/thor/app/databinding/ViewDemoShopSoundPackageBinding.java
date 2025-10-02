package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public abstract class ViewDemoShopSoundPackageBinding extends ViewDataBinding {
    public final ImageView imageViewCover;
    public final FrameLayout layoutCover;

    @Bindable
    protected ShopSoundPackage mSoundPackage;
    public final ConstraintLayout mainLayout;
    public final TextView textViewSoundName;
    public final View viewCover;

    public abstract void setSoundPackage(ShopSoundPackage soundPackage);

    protected ViewDemoShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, ImageView imageViewCover, FrameLayout layoutCover, ConstraintLayout mainLayout, TextView textViewSoundName, View viewCover) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imageViewCover = imageViewCover;
        this.layoutCover = layoutCover;
        this.mainLayout = mainLayout;
        this.textViewSoundName = textViewSoundName;
        this.viewCover = viewCover;
    }

    public ShopSoundPackage getSoundPackage() {
        return this.mSoundPackage;
    }

    public static ViewDemoShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDemoShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewDemoShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_demo_shop_sound_package, root, attachToRoot, component);
    }

    public static ViewDemoShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDemoShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewDemoShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_demo_shop_sound_package, null, false, component);
    }

    public static ViewDemoShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDemoShopSoundPackageBinding bind(View view, Object component) {
        return (ViewDemoShopSoundPackageBinding) bind(component, view, R.layout.view_demo_shop_sound_package);
    }
}
