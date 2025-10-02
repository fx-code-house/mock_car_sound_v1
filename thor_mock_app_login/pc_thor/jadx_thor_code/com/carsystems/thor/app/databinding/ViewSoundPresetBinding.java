package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public abstract class ViewSoundPresetBinding extends ViewDataBinding {
    public final Guideline guidelineHorizontal;

    @Bindable
    protected ModeType mModeType;

    @Bindable
    protected ShopSoundPackage mSoundPackage;
    public final TextView textViewModeType;
    public final TextView textViewSoundName;

    public abstract void setModeType(ModeType modeType);

    public abstract void setSoundPackage(ShopSoundPackage soundPackage);

    protected ViewSoundPresetBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guidelineHorizontal, TextView textViewModeType, TextView textViewSoundName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guidelineHorizontal = guidelineHorizontal;
        this.textViewModeType = textViewModeType;
        this.textViewSoundName = textViewSoundName;
    }

    public ShopSoundPackage getSoundPackage() {
        return this.mSoundPackage;
    }

    public ModeType getModeType() {
        return this.mModeType;
    }

    public static ViewSoundPresetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSoundPresetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSoundPresetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sound_preset, root, attachToRoot, component);
    }

    public static ViewSoundPresetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSoundPresetBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSoundPresetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sound_preset, null, false, component);
    }

    public static ViewSoundPresetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSoundPresetBinding bind(View view, Object component) {
        return (ViewSoundPresetBinding) bind(component, view, R.layout.view_sound_preset);
    }
}
