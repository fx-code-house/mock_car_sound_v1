package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.businessmodule.viewmodel.views.MainPresetPackageViewModel;

/* loaded from: classes.dex */
public abstract class ViewMainSoundPackageBinding extends ViewDataBinding {
    public final Guideline guidelineHorizontal;
    public final AppCompatImageView imageViewCover;
    public final ImageView imageViewSettings;
    public final FrameLayout layoutCover;
    public final FrameLayout layoutDelete;

    @Bindable
    protected MainPresetPackageViewModel mModel;

    @Bindable
    protected MainPresetPackage mPresetPackage;
    public final ConstraintLayout mainLayout;
    public final TextView textNeedUpload;
    public final TextView textViewDeleteInfo;
    public final TextView textViewDriveMode;
    public final TextView textViewModeType;
    public final TextView textViewSoundName;
    public final View viewCoverRedGradient;

    public abstract void setModel(MainPresetPackageViewModel model);

    public abstract void setPresetPackage(MainPresetPackage presetPackage);

    protected ViewMainSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guidelineHorizontal, AppCompatImageView imageViewCover, ImageView imageViewSettings, FrameLayout layoutCover, FrameLayout layoutDelete, ConstraintLayout mainLayout, TextView textNeedUpload, TextView textViewDeleteInfo, TextView textViewDriveMode, TextView textViewModeType, TextView textViewSoundName, View viewCoverRedGradient) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guidelineHorizontal = guidelineHorizontal;
        this.imageViewCover = imageViewCover;
        this.imageViewSettings = imageViewSettings;
        this.layoutCover = layoutCover;
        this.layoutDelete = layoutDelete;
        this.mainLayout = mainLayout;
        this.textNeedUpload = textNeedUpload;
        this.textViewDeleteInfo = textViewDeleteInfo;
        this.textViewDriveMode = textViewDriveMode;
        this.textViewModeType = textViewModeType;
        this.textViewSoundName = textViewSoundName;
        this.viewCoverRedGradient = viewCoverRedGradient;
    }

    public MainPresetPackageViewModel getModel() {
        return this.mModel;
    }

    public MainPresetPackage getPresetPackage() {
        return this.mPresetPackage;
    }

    public static ViewMainSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewMainSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewMainSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_main_sound_package, root, attachToRoot, component);
    }

    public static ViewMainSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewMainSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewMainSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_main_sound_package, null, false, component);
    }

    public static ViewMainSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewMainSoundPackageBinding bind(View view, Object component) {
        return (ViewMainSoundPackageBinding) bind(component, view, R.layout.view_main_sound_package);
    }
}
