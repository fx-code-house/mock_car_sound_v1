package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.AutoscrollRecyclerView;

/* loaded from: classes.dex */
public abstract class ActivityShopSoundPackageBinding extends ViewDataBinding {
    public final AppCompatButton actionBuy;
    public final AppCompatImageView actionClose;
    public final AppCompatButton actionSubscribe;
    public final AutoscrollRecyclerView autoscrollRecycler;
    public final CardView cardSoundPack;
    public final CardView cardSubscription;
    public final AppCompatImageView imagePack;
    public final AppCompatTextView presetName;
    public final AppCompatTextView soundsInPreset;
    public final AppCompatTextView textPrefixDivider;

    protected ActivityShopSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton actionBuy, AppCompatImageView actionClose, AppCompatButton actionSubscribe, AutoscrollRecyclerView autoscrollRecycler, CardView cardSoundPack, CardView cardSubscription, AppCompatImageView imagePack, AppCompatTextView presetName, AppCompatTextView soundsInPreset, AppCompatTextView textPrefixDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actionBuy = actionBuy;
        this.actionClose = actionClose;
        this.actionSubscribe = actionSubscribe;
        this.autoscrollRecycler = autoscrollRecycler;
        this.cardSoundPack = cardSoundPack;
        this.cardSubscription = cardSubscription;
        this.imagePack = imagePack;
        this.presetName = presetName;
        this.soundsInPreset = soundsInPreset;
        this.textPrefixDivider = textPrefixDivider;
    }

    public static ActivityShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_shop_sound_package, root, attachToRoot, component);
    }

    public static ActivityShopSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityShopSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_shop_sound_package, null, false, component);
    }

    public static ActivityShopSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityShopSoundPackageBinding bind(View view, Object component) {
        return (ActivityShopSoundPackageBinding) bind(component, view, R.layout.activity_shop_sound_package);
    }
}
