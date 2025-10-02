package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.soundpackage.MainSoundPackageView;

/* loaded from: classes.dex */
public abstract class ItemMainSoundPackageBinding extends ViewDataBinding {
    public final MainSoundPackageView mainSoundPackageView;

    protected ItemMainSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, MainSoundPackageView mainSoundPackageView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.mainSoundPackageView = mainSoundPackageView;
    }

    public static ItemMainSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMainSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMainSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_main_sound_package, root, attachToRoot, component);
    }

    public static ItemMainSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMainSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMainSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_main_sound_package, null, false, component);
    }

    public static ItemMainSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMainSoundPackageBinding bind(View view, Object component) {
        return (ItemMainSoundPackageBinding) bind(component, view, R.layout.item_main_sound_package);
    }
}
