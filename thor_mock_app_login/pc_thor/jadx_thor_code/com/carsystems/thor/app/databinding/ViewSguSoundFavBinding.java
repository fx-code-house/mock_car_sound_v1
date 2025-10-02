package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.responses.sgu.SguSound;

/* loaded from: classes.dex */
public abstract class ViewSguSoundFavBinding extends ViewDataBinding {
    public final AppCompatImageView imageFavourite;

    @Bindable
    protected SguSound mModel;
    public final TextView textViewSoundName;

    public abstract void setModel(SguSound model);

    protected ViewSguSoundFavBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView imageFavourite, TextView textViewSoundName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imageFavourite = imageFavourite;
        this.textViewSoundName = textViewSoundName;
    }

    public SguSound getModel() {
        return this.mModel;
    }

    public static ViewSguSoundFavBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguSoundFavBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSguSoundFavBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sgu_sound_fav, root, attachToRoot, component);
    }

    public static ViewSguSoundFavBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguSoundFavBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSguSoundFavBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sgu_sound_fav, null, false, component);
    }

    public static ViewSguSoundFavBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguSoundFavBinding bind(View view, Object component) {
        return (ViewSguSoundFavBinding) bind(component, view, R.layout.view_sgu_sound_fav);
    }
}
