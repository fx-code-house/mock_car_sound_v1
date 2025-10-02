package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.sgu.SguSoundConfigView;

/* loaded from: classes.dex */
public abstract class ItemSguSoundConfigBinding extends ViewDataBinding {
    public final SguSoundConfigView viewSoundPreset;

    protected ItemSguSoundConfigBinding(Object _bindingComponent, View _root, int _localFieldCount, SguSoundConfigView viewSoundPreset) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewSoundPreset = viewSoundPreset;
    }

    public static ItemSguSoundConfigBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundConfigBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSguSoundConfigBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_sound_config, root, attachToRoot, component);
    }

    public static ItemSguSoundConfigBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundConfigBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSguSoundConfigBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_sound_config, null, false, component);
    }

    public static ItemSguSoundConfigBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundConfigBinding bind(View view, Object component) {
        return (ItemSguSoundConfigBinding) bind(component, view, R.layout.item_sgu_sound_config);
    }
}
