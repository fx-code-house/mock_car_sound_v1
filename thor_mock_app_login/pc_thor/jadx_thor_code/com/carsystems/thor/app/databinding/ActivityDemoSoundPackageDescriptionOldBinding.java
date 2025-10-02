package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.app.gui.widget.ViewFlipperIndicator;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public abstract class ActivityDemoSoundPackageDescriptionOldBinding extends ViewDataBinding {
    public final FloatingActionButton fabPlaySound;
    public final ImageView imageViewPlayVideo;

    @Bindable
    protected SoundPackageDescriptionViewModel mModel;

    @Bindable
    protected SoundPackageDescriptionResponse mPackageInfo;

    @Bindable
    protected ShopSoundPackage mSoundPackage;
    public final NestedScrollView nestedScrollView;
    public final TextView textViewDescription;
    public final TextView textViewPackageName;
    public final ToolbarWidget toolbarWidget;
    public final VideoView videoView;
    public final ViewFlipperIndicator viewFlipper;
    public final TachometerWithEqualizeView viewTachometer;

    public abstract void setModel(SoundPackageDescriptionViewModel model);

    public abstract void setPackageInfo(SoundPackageDescriptionResponse packageInfo);

    public abstract void setSoundPackage(ShopSoundPackage soundPackage);

    protected ActivityDemoSoundPackageDescriptionOldBinding(Object _bindingComponent, View _root, int _localFieldCount, FloatingActionButton fabPlaySound, ImageView imageViewPlayVideo, NestedScrollView nestedScrollView, TextView textViewDescription, TextView textViewPackageName, ToolbarWidget toolbarWidget, VideoView videoView, ViewFlipperIndicator viewFlipper, TachometerWithEqualizeView viewTachometer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fabPlaySound = fabPlaySound;
        this.imageViewPlayVideo = imageViewPlayVideo;
        this.nestedScrollView = nestedScrollView;
        this.textViewDescription = textViewDescription;
        this.textViewPackageName = textViewPackageName;
        this.toolbarWidget = toolbarWidget;
        this.videoView = videoView;
        this.viewFlipper = viewFlipper;
        this.viewTachometer = viewTachometer;
    }

    public SoundPackageDescriptionViewModel getModel() {
        return this.mModel;
    }

    public ShopSoundPackage getSoundPackage() {
        return this.mSoundPackage;
    }

    public SoundPackageDescriptionResponse getPackageInfo() {
        return this.mPackageInfo;
    }

    public static ActivityDemoSoundPackageDescriptionOldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoSoundPackageDescriptionOldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityDemoSoundPackageDescriptionOldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_demo_sound_package_description_old, root, attachToRoot, component);
    }

    public static ActivityDemoSoundPackageDescriptionOldBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoSoundPackageDescriptionOldBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityDemoSoundPackageDescriptionOldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_demo_sound_package_description_old, null, false, component);
    }

    public static ActivityDemoSoundPackageDescriptionOldBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoSoundPackageDescriptionOldBinding bind(View view, Object component) {
        return (ActivityDemoSoundPackageDescriptionOldBinding) bind(component, view, R.layout.activity_demo_sound_package_description_old);
    }
}
