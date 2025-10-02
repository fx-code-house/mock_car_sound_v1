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
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;

/* loaded from: classes.dex */
public abstract class ViewSguShopSoundPackageBinding extends ViewDataBinding {
    public final AppCompatImageView imageViewCover;
    public final FrameLayout layoutCover;
    public final FrameLayout layoutDelete;

    @Bindable
    protected SkuDetails mSkuDetails;

    @Bindable
    protected SguSoundSet mSoundPackage;
    public final ConstraintLayout mainLayout;
    public final TextView textViewDeleteInfo;
    public final TextView textViewPrice;
    public final TextView textViewSoundName;
    public final View viewCover;

    public abstract void setSkuDetails(SkuDetails skuDetails);

    public abstract void setSoundPackage(SguSoundSet soundPackage);

    protected ViewSguShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView imageViewCover, FrameLayout layoutCover, FrameLayout layoutDelete, ConstraintLayout mainLayout, TextView textViewDeleteInfo, TextView textViewPrice, TextView textViewSoundName, View viewCover) {
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

    public SguSoundSet getSoundPackage() {
        return this.mSoundPackage;
    }

    public SkuDetails getSkuDetails() {
        return this.mSkuDetails;
    }

    public static ViewSguShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSguShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sgu_shop_sound_package, root, attachToRoot, component);
    }

    public static ViewSguShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSguShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sgu_shop_sound_package, null, false, component);
    }

    public static ViewSguShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguShopSoundPackageBinding bind(View view, Object component) {
        return (ViewSguShopSoundPackageBinding) bind(component, view, R.layout.view_sgu_shop_sound_package);
    }
}
