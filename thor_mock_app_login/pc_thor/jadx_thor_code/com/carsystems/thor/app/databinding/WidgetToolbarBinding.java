package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.viewmodels.ToolbarWidgetViewModel;

/* loaded from: classes.dex */
public abstract class WidgetToolbarBinding extends ViewDataBinding {
    public final ImageButton btnNotificationsDelete;
    public final Button buttonDeviceConnectingStatus;
    public final ImageView imageToolbarHome;

    @Bindable
    protected ToolbarWidgetViewModel mModel;
    public final TextView textToolbarTitle;
    public final View viewDevServerMarker;
    public final View viewToolbarDivider;

    public abstract void setModel(ToolbarWidgetViewModel model);

    protected WidgetToolbarBinding(Object _bindingComponent, View _root, int _localFieldCount, ImageButton btnNotificationsDelete, Button buttonDeviceConnectingStatus, ImageView imageToolbarHome, TextView textToolbarTitle, View viewDevServerMarker, View viewToolbarDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnNotificationsDelete = btnNotificationsDelete;
        this.buttonDeviceConnectingStatus = buttonDeviceConnectingStatus;
        this.imageToolbarHome = imageToolbarHome;
        this.textToolbarTitle = textToolbarTitle;
        this.viewDevServerMarker = viewDevServerMarker;
        this.viewToolbarDivider = viewToolbarDivider;
    }

    public ToolbarWidgetViewModel getModel() {
        return this.mModel;
    }

    public static WidgetToolbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static WidgetToolbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (WidgetToolbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.widget_toolbar, root, attachToRoot, component);
    }

    public static WidgetToolbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static WidgetToolbarBinding inflate(LayoutInflater inflater, Object component) {
        return (WidgetToolbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.widget_toolbar, null, false, component);
    }

    public static WidgetToolbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static WidgetToolbarBinding bind(View view, Object component) {
        return (WidgetToolbarBinding) bind(component, view, R.layout.widget_toolbar);
    }
}
