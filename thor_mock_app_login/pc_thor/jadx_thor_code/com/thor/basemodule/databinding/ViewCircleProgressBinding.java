package com.thor.basemodule.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.thor.basemodule.R;
import com.thor.basemodule.gui.view.CircleBarView;

/* loaded from: classes3.dex */
public abstract class ViewCircleProgressBinding extends ViewDataBinding {
    public final CircleBarView circleBarView;
    public final TextView textView;

    protected ViewCircleProgressBinding(Object obj, View view, int i, CircleBarView circleBarView, TextView textView) {
        super(obj, view, i);
        this.circleBarView = circleBarView;
        this.textView = textView;
    }

    public static ViewCircleProgressBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCircleProgressBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ViewCircleProgressBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.view_circle_progress, viewGroup, z, obj);
    }

    public static ViewCircleProgressBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCircleProgressBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ViewCircleProgressBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.view_circle_progress, null, false, obj);
    }

    public static ViewCircleProgressBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCircleProgressBinding bind(View view, Object obj) {
        return (ViewCircleProgressBinding) bind(obj, view, R.layout.view_circle_progress);
    }
}
