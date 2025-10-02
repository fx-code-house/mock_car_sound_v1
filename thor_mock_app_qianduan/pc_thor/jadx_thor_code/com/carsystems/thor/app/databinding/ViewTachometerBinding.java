package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;

/* loaded from: classes.dex */
public abstract class ViewTachometerBinding extends ViewDataBinding {
    public final Guideline guidelineHorizontalIndicator;
    public final Guideline guidelineHorizontalIndicatorBackground;
    public final AppCompatTextView textPresetName;
    public final View viewIndicator;
    public final View viewIndicatorBackground;
    public final View viewScale;

    protected ViewTachometerBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guidelineHorizontalIndicator, Guideline guidelineHorizontalIndicatorBackground, AppCompatTextView textPresetName, View viewIndicator, View viewIndicatorBackground, View viewScale) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guidelineHorizontalIndicator = guidelineHorizontalIndicator;
        this.guidelineHorizontalIndicatorBackground = guidelineHorizontalIndicatorBackground;
        this.textPresetName = textPresetName;
        this.viewIndicator = viewIndicator;
        this.viewIndicatorBackground = viewIndicatorBackground;
        this.viewScale = viewScale;
    }

    public static ViewTachometerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTachometerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewTachometerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_tachometer, root, attachToRoot, component);
    }

    public static ViewTachometerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTachometerBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewTachometerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_tachometer, null, false, component);
    }

    public static ViewTachometerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTachometerBinding bind(View view, Object component) {
        return (ViewTachometerBinding) bind(component, view, R.layout.view_tachometer);
    }
}
