package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.sgu.SguSoundFavView;

/* loaded from: classes.dex */
public abstract class ItemSguSoundFavBinding extends ViewDataBinding {
    public final SguSoundFavView viewSoundPreset;

    protected ItemSguSoundFavBinding(Object _bindingComponent, View _root, int _localFieldCount, SguSoundFavView viewSoundPreset) {
        super(_bindingComponent, _root, _localFieldCount);
        this.viewSoundPreset = viewSoundPreset;
    }

    public static ItemSguSoundFavBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundFavBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSguSoundFavBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_sound_fav, root, attachToRoot, component);
    }

    public static ItemSguSoundFavBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundFavBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSguSoundFavBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_sound_fav, null, false, component);
    }

    public static ItemSguSoundFavBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundFavBinding bind(View view, Object component) {
        return (ItemSguSoundFavBinding) bind(component, view, R.layout.item_sgu_sound_fav);
    }
}
