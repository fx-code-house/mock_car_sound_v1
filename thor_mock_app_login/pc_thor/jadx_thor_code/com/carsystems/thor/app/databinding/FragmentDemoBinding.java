package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class FragmentDemoBinding extends ViewDataBinding {
    public final ConstraintLayout layoutMain;
    public final NestedScrollView nestedScrollView;
    public final RecyclerView recyclerView;

    protected FragmentDemoBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutMain, NestedScrollView nestedScrollView, RecyclerView recyclerView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutMain = layoutMain;
        this.nestedScrollView = nestedScrollView;
        this.recyclerView = recyclerView;
    }

    public static FragmentDemoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDemoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentDemoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_demo, root, attachToRoot, component);
    }

    public static FragmentDemoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDemoBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentDemoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_demo, null, false, component);
    }

    public static FragmentDemoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDemoBinding bind(View view, Object component) {
        return (FragmentDemoBinding) bind(component, view, R.layout.fragment_demo);
    }
}
