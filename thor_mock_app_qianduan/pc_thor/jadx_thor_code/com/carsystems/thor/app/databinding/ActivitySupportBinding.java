package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;
import im.delight.android.webview.AdvancedWebView;

/* loaded from: classes.dex */
public abstract class ActivitySupportBinding extends ViewDataBinding {
    public final SwipeRefreshLayout swipeRefresh;
    public final ToolbarWidget toolbarWidget;
    public final AdvancedWebView webView;

    protected ActivitySupportBinding(Object _bindingComponent, View _root, int _localFieldCount, SwipeRefreshLayout swipeRefresh, ToolbarWidget toolbarWidget, AdvancedWebView webView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.swipeRefresh = swipeRefresh;
        this.toolbarWidget = toolbarWidget;
        this.webView = webView;
    }

    public static ActivitySupportBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySupportBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySupportBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_support, root, attachToRoot, component);
    }

    public static ActivitySupportBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySupportBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySupportBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_support, null, false, component);
    }

    public static ActivitySupportBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySupportBinding bind(View view, Object component) {
        return (ActivitySupportBinding) bind(component, view, R.layout.activity_support);
    }
}
