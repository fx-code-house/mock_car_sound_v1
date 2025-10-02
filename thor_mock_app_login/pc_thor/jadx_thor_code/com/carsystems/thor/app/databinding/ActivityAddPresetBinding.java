package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.views.soundpackage.SoundPresetView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityAddPresetBinding extends ViewDataBinding {
    public final ConstraintLayout layoutMain;
    public final LinearLayout layoutPresets;
    public final FrameLayout progressBar;
    public final ToolbarWidget toolbarWidget;
    public final SoundPresetView viewCity;
    public final SoundPresetView viewOwn;
    public final SoundPresetView viewSport;

    protected ActivityAddPresetBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutMain, LinearLayout layoutPresets, FrameLayout progressBar, ToolbarWidget toolbarWidget, SoundPresetView viewCity, SoundPresetView viewOwn, SoundPresetView viewSport) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutMain = layoutMain;
        this.layoutPresets = layoutPresets;
        this.progressBar = progressBar;
        this.toolbarWidget = toolbarWidget;
        this.viewCity = viewCity;
        this.viewOwn = viewOwn;
        this.viewSport = viewSport;
    }

    public static ActivityAddPresetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAddPresetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityAddPresetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_add_preset, root, attachToRoot, component);
    }

    public static ActivityAddPresetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAddPresetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityAddPresetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_add_preset, null, false, component);
    }

    public static ActivityAddPresetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAddPresetBinding bind(View view, Object component) {
        return (ActivityAddPresetBinding) bind(component, view, R.layout.activity_add_preset);
    }
}
