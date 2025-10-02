package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivityNotificationsBinding extends ViewDataBinding {
    public final FragmentContainerView fragmentContainer;
    public final ConstraintLayout layoutMain;
    public final ToolbarWidget toolbarWidget;

    protected ActivityNotificationsBinding(Object _bindingComponent, View _root, int _localFieldCount, FragmentContainerView fragmentContainer, ConstraintLayout layoutMain, ToolbarWidget toolbarWidget) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutMain = layoutMain;
        this.toolbarWidget = toolbarWidget;
    }

    public static ActivityNotificationsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNotificationsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityNotificationsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_notifications, root, attachToRoot, component);
    }

    public static ActivityNotificationsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNotificationsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityNotificationsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_notifications, null, false, component);
    }

    public static ActivityNotificationsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNotificationsBinding bind(View view, Object component) {
        return (ActivityNotificationsBinding) bind(component, view, R.layout.activity_notifications);
    }
}
