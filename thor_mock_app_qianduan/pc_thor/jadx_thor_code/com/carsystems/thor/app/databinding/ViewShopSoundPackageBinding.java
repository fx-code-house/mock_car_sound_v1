package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public abstract class ViewShopSoundPackageBinding extends ViewDataBinding {
    public final AppCompatImageView imageViewCover;
    public final FrameLayout layoutCover;
    public final FrameLayout layoutDelete;

    @Bindable
    protected SkuDetails mSkuDetails;

    @Bindable
    protected ShopSoundPackage mSoundPackage;
    public final ConstraintLayout mainLayout;
    public final TextView textViewDeleteInfo;
    public final TextView textViewPrice;
    public final TextView textViewSoundName;
    public final View viewCover;

    public abstract void setSkuDetails(SkuDetails skuDetails);

    public abstract void setSoundPackage(ShopSoundPackage soundPackage);

    protected ViewShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView imageViewCover, FrameLayout layoutCover, FrameLayout layoutDelete, ConstraintLayout mainLayout, TextView textViewDeleteInfo, TextView textViewPrice, TextView textViewSoundName, View viewCover) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imageViewCover = imageViewCover;
        this.layoutCover = layoutCover;
        this.layoutDelete = layoutDelete;
        this.mainLayout = mainLayout;
        this.textViewDeleteInfo = textViewDeleteInfo;
        this.textViewPrice = textViewPrice;
        this.textViewSoundName = textViewSoundName;
        this.viewCover = viewCover;
    }

    public ShopSoundPackage getSoundPackage() {
        return this.mSoundPackage;
    }

    public SkuDetails getSkuDetails() {
        return this.mSkuDetails;
    }

    public static ViewShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_shop_sound_package, root, attachToRoot, component);
    }

    public static ViewShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_shop_sound_package, null, false, component);
    }

    public static ViewShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewShopSoundPackageBinding bind(View view, Object component) {
        return (ViewShopSoundPackageBinding) bind(component, view, R.layout.view_shop_sound_package);
    }
}
