package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ActivityPresetSoundSettingsBinding extends ViewDataBinding {
    public final FrameLayout frameContainer;

    protected ActivityPresetSoundSettingsBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout frameContainer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.frameContainer = frameContainer;
    }

    public static ActivityPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_preset_sound_settings, root, attachToRoot, component);
    }

    public static ActivityPresetSoundSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPresetSoundSettingsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_preset_sound_settings, null, false, component);
    }

    public static ActivityPresetSoundSettingsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPresetSoundSettingsBinding bind(View view, Object component) {
        return (ActivityPresetSoundSettingsBinding) bind(component, view, R.layout.activity_preset_sound_settings);
    }
}
