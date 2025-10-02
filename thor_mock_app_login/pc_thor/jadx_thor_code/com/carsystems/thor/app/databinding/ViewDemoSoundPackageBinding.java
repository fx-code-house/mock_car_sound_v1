package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.viewmodel.views.DemoSoundPackageViewModel;

/* loaded from: classes.dex */
public abstract class ViewDemoSoundPackageBinding extends ViewDataBinding {
    public final AppCompatImageView imageViewCover;
    public final ImageView imageViewSettings;
    public final View layoutSelectedGradient;

    @Bindable
    protected DemoSoundPackageViewModel mModel;

    @Bindable
    protected DemoSoundPackage mSoundPackage;
    public final ConstraintLayout mainLayout;
    public final TextView textViewSoundName;

    public abstract void setModel(DemoSoundPackageViewModel model);

    public abstract void setSoundPackage(DemoSoundPackage soundPackage);

    protected ViewDemoSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView imageViewCover, ImageView imageViewSettings, View layoutSelectedGradient, ConstraintLayout mainLayout, TextView textViewSoundName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imageViewCover = imageViewCover;
        this.imageViewSettings = imageViewSettings;
        this.layoutSelectedGradient = layoutSelectedGradient;
        this.mainLayout = mainLayout;
        this.textViewSoundName = textViewSoundName;
    }

    public DemoSoundPackageViewModel getModel() {
        return this.mModel;
    }

    public DemoSoundPackage getSoundPackage() {
        return this.mSoundPackage;
    }

    public static ViewDemoSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDemoSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewDemoSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_demo_sound_package, root, attachToRoot, component);
    }

    public static ViewDemoSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDemoSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewDemoSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_demo_sound_package, null, false, component);
    }

    public static ViewDemoSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDemoSoundPackageBinding bind(View view, Object component) {
        return (ViewDemoSoundPackageBinding) bind(component, view, R.layout.view_demo_sound_package);
    }
}
