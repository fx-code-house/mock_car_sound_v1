package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.imageview.ShapeableImageView;

/* loaded from: classes.dex */
public abstract class ItemSguSoundPreviewBinding extends ViewDataBinding {
    public final ShapeableImageView image;
    public final TextView title;

    protected ItemSguSoundPreviewBinding(Object _bindingComponent, View _root, int _localFieldCount, ShapeableImageView image, TextView title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.image = image;
        this.title = title;
    }

    public static ItemSguSoundPreviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundPreviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSguSoundPreviewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_sound_preview, root, attachToRoot, component);
    }

    public static ItemSguSoundPreviewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundPreviewBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSguSoundPreviewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sgu_sound_preview, null, false, component);
    }

    public static ItemSguSoundPreviewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSguSoundPreviewBinding bind(View view, Object component) {
        return (ItemSguSoundPreviewBinding) bind(component, view, R.layout.item_sgu_sound_preview);
    }
}
