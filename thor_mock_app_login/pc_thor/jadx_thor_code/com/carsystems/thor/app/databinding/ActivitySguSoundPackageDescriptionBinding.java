package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.basemodule.gui.view.AutoscrollRecyclerView;
import com.thor.businessmodule.viewmodel.shop.SoundPackageDescriptionViewModel;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;

/* loaded from: classes.dex */
public abstract class ActivitySguSoundPackageDescriptionBinding extends ViewDataBinding {
    public final AutoscrollRecyclerView autoscrollRecycler;
    public final LinearLayout bottomSheet;
    public final Button buttonBuy;
    public final Button buttonDownloadPackage;
    public final ConstraintLayout layoutMain;

    @Bindable
    protected SoundPackageDescriptionViewModel mModel;

    @Bindable
    protected SguSoundSet mSoundPackage;
    public final LinearLayout mainInfoLayout;
    public final NestedScrollView nestedScrollView;
    public final TextView textViewDescription;
    public final TextView textViewPackageName;
    public final ToolbarWidget toolbarWidget;

    public abstract void setModel(SoundPackageDescriptionViewModel model);

    public abstract void setSoundPackage(SguSoundSet soundPackage);

    protected ActivitySguSoundPackageDescriptionBinding(Object _bindingComponent, View _root, int _localFieldCount, AutoscrollRecyclerView autoscrollRecycler, LinearLayout bottomSheet, Button buttonBuy, Button buttonDownloadPackage, ConstraintLayout layoutMain, LinearLayout mainInfoLayout, NestedScrollView nestedScrollView, TextView textViewDescription, TextView textViewPackageName, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.autoscrollRecycler = autoscrollRecycler;
        this.bottomSheet = bottomSheet;
        this.buttonBuy = buttonBuy;
        this.buttonDownloadPackage = buttonDownloadPackage;
        this.layoutMain = layoutMain;
        this.mainInfoLayout = mainInfoLayout;
        this.nestedScrollView = nestedScrollView;
        this.textViewDescription = textViewDescription;
        this.textViewPackageName = textViewPackageName;
        this.toolbarWidget = toolbarWidget;
    }

    public SoundPackageDescriptionViewModel getModel() {
        return this.mModel;
    }

    public SguSoundSet getSoundPackage() {
        return this.mSoundPackage;
    }

    public static ActivitySguSoundPackageDescriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySguSoundPackageDescriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySguSoundPackageDescriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sgu_sound_package_description, root, attachToRoot, component);
    }

    public static ActivitySguSoundPackageDescriptionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySguSoundPackageDescriptionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySguSoundPackageDescriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_sgu_sound_package_description, null, false, component);
    }

    public static ActivitySguSoundPackageDescriptionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySguSoundPackageDescriptionBinding bind(View view, Object component) {
        return (ActivitySguSoundPackageDescriptionBinding) bind(component, view, R.layout.activity_sgu_sound_package_description);
    }
}
