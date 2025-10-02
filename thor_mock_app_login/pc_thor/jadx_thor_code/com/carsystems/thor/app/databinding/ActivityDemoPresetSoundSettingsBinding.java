package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ActivityDemoPresetSoundSettingsBinding extends ViewDataBinding {
    public final FrameLayout frameContainer;

    protected ActivityDemoPresetSoundSettingsBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout frameContainer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.frameContainer = frameContainer;
    }

    public static ActivityDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityDemoPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_demo_preset_sound_settings, root, attachToRoot, component);
    }

    public static ActivityDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityDemoPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_demo_preset_sound_settings, null, false, component);
    }

    public static ActivityDemoPresetSoundSettingsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDemoPresetSoundSettingsBinding bind(View view, Object component) {
        return (ActivityDemoPresetSoundSettingsBinding) bind(component, view, R.layout.activity_demo_preset_sound_settings);
    }
}
