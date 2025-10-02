package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.demo.DemoSoundPackageView;

/* loaded from: classes.dex */
public abstract class ItemDemoSoundPackageBinding extends ViewDataBinding {
    public final DemoSoundPackageView demoSoundPackageView;

    protected ItemDemoSoundPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, DemoSoundPackageView demoSoundPackageView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.demoSoundPackageView = demoSoundPackageView;
    }

    public static ItemDemoSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoSoundPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDemoSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_demo_sound_package, root, attachToRoot, component);
    }

    public static ItemDemoSoundPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoSoundPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDemoSoundPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_demo_sound_package, null, false, component);
    }

    public static ItemDemoSoundPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDemoSoundPackageBinding bind(View view, Object component) {
        return (ItemDemoSoundPackageBinding) bind(component, view, R.layout.item_demo_sound_package);
    }
}
