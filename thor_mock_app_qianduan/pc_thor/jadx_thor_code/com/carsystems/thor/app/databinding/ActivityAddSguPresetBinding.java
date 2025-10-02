package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityAddSguPresetBinding extends ViewDataBinding {
    public final RecyclerView recyclerView;
    public final ToolbarWidget toolbarWidget;

    protected ActivityAddSguPresetBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView recyclerView, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.recyclerView = recyclerView;
        this.toolbarWidget = toolbarWidget;
    }

    public static ActivityAddSguPresetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAddSguPresetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityAddSguPresetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_add_sgu_preset, root, attachToRoot, component);
    }

    public static ActivityAddSguPresetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAddSguPresetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityAddSguPresetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_add_sgu_preset, null, false, component);
    }

    public static ActivityAddSguPresetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAddSguPresetBinding bind(View view, Object component) {
        return (ActivityAddSguPresetBinding) bind(component, view, R.layout.activity_add_sgu_preset);
    }
}
