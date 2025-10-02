package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.carsystems.thor.app.R;
import com.google.android.material.tabs.TabLayout;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;

/* loaded from: classes.dex */
public abstract class ActivitySoundPackageDescriptionBinding extends ViewDataBinding {
    public final Button buttonBuy;
    public final Button buttonDownloadPackage;
    public final ImageView imageViewPlayVideo;
    public final ConstraintLayout layoutMain;

    @Bindable
    protected SoundPackageDescriptionViewModel mModel;

    @Bindable
    protected SoundPackageDescriptionResponse mPackageInfo;

    @Bindable
    protected ShopSoundPackage mSoundPackage;
    public final NestedScrollView nestedScrollView;
    public final TabLayout tabLayout;
    public final TextView textViewDescription;
    public final TextView textViewPackageName;
    public final ToolbarWidget toolbarWidget;
    public final VideoView videoView;
    public final ViewPager viewPager;
    public final CheckedTextView viewPlaySound;
    public final TachometerWithEqualizeView viewTachometer;

    public abstract void setModel(SoundPackageDescriptionViewModel model);

    public abstract void setPackageInfo(SoundPackageDescriptionResponse packageInfo);

    public abstract void setSoundPackage(ShopSoundPackage soundPackage);

    protected ActivitySoundPackageDescriptionBinding(Object _bindingComponent, View _root, int _localFieldCount, Button buttonBuy, Button buttonDownloadPackage, ImageView imageViewPlayVideo, ConstraintLayout layoutMain, NestedScrollView nestedScrollView, TabLayout tabLayout, TextView textViewDescription, TextView textViewPackageName, ToolbarWidget toolbarWidget, VideoView videoView, ViewPager viewPager, CheckedTextView viewPlaySound, TachometerWithEqualizeView viewTachometer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.buttonBuy = buttonBuy;
        this.buttonDownloadPackage = buttonDownloadPackage;
        this.imageViewPlayVideo = imageViewPlayVideo;
        this.layoutMain = layoutMain;
        this.nestedScrollView = nestedScrollView;
        this.tabLayout = tabLayout;
        this.textViewDescription = textViewDescription;
        this.textViewPackageName = textViewPackageName;
        this.toolbarWidget = toolbarWidget;
        this.videoView = videoView;
        this.viewPager = viewPager;
        this.viewPlaySound = viewPlaySound;
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

    public static ActivitySoundPackageDescriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySoundPackageDescriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySoundPackageDescriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sound_package_description, root, attachToRoot, component);
    }

    public static ActivitySoundPackageDescriptionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySoundPackageDescriptionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySoundPackageDescriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sound_package_description, null, false, component);
    }

    public static ActivitySoundPackageDescriptionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySoundPackageDescriptionBinding bind(View view, Object component) {
        return (ActivitySoundPackageDescriptionBinding) bind(component, view, R.layout.activity_sound_package_description);
    }
}
