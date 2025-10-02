package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.networkmodule.model.responses.sgu.SguSound;

/* loaded from: classes.dex */
public abstract class ViewSguSoundConfigBinding extends ViewDataBinding {
    public final AppCompatImageView imageConfig;
    public final ImageView imageViewCover;

    @Bindable
    protected SguSound mModel;
    public final ConstraintLayout mainLayout;
    public final TextView textViewSoundName;
    public final View viewCoverRedGradient;

    public abstract void setModel(SguSound model);

    protected ViewSguSoundConfigBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView imageConfig, ImageView imageViewCover, ConstraintLayout mainLayout, TextView textViewSoundName, View viewCoverRedGradient) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imageConfig = imageConfig;
        this.imageViewCover = imageViewCover;
        this.mainLayout = mainLayout;
        this.textViewSoundName = textViewSoundName;
        this.viewCoverRedGradient = viewCoverRedGradient;
    }

    public SguSound getModel() {
        return this.mModel;
    }

    public static ViewSguSoundConfigBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguSoundConfigBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSguSoundConfigBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sgu_sound_config, root, attachToRoot, component);
    }

    public static ViewSguSoundConfigBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguSoundConfigBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSguSoundConfigBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sgu_sound_config, null, false, component);
    }

    public static ViewSguSoundConfigBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSguSoundConfigBinding bind(View view, Object component) {
        return (ViewSguSoundConfigBinding) bind(component, view, R.layout.view_sgu_sound_config);
    }
}
