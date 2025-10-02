package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.camera.CameraSourcePreview;
import com.thor.app.gui.camera.GraphicOverlay;
import com.thor.app.gui.widget.DrawView;
import com.thor.businessmodule.viewmodel.main.BarcodeCaptureActivityViewModel;

/* loaded from: classes.dex */
public abstract class ActivityBarcodeCaptureBinding extends ViewDataBinding {
    public final CameraSourcePreview cameraPreview;
    public final ConstraintLayout constraintLayout;
    public final DrawView drawView;
    public final GraphicOverlay graphicOverlay;
    public final Guideline guideline2;
    public final Guideline guideline3;
    public final Guideline guideline4;
    public final Guideline guideline5;
    public final AppCompatImageView imageViewBack;
    public final ImageView imageViewSubstrate;
    public final View linearLayout;

    @Bindable
    protected BarcodeCaptureActivityViewModel mModel;
    public final ConstraintLayout mainLayout;
    public final View viewCover;

    public abstract void setModel(BarcodeCaptureActivityViewModel model);

    protected ActivityBarcodeCaptureBinding(Object _bindingComponent, View _root, int _localFieldCount, CameraSourcePreview cameraPreview, ConstraintLayout constraintLayout, DrawView drawView, GraphicOverlay graphicOverlay, Guideline guideline2, Guideline guideline3, Guideline guideline4, Guideline guideline5, AppCompatImageView imageViewBack, ImageView imageViewSubstrate, View linearLayout, ConstraintLayout mainLayout, View viewCover) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cameraPreview = cameraPreview;
        this.constraintLayout = constraintLayout;
        this.drawView = drawView;
        this.graphicOverlay = graphicOverlay;
        this.guideline2 = guideline2;
        this.guideline3 = guideline3;
        this.guideline4 = guideline4;
        this.guideline5 = guideline5;
        this.imageViewBack = imageViewBack;
        this.imageViewSubstrate = imageViewSubstrate;
        this.linearLayout = linearLayout;
        this.mainLayout = mainLayout;
        this.viewCover = viewCover;
    }

    public BarcodeCaptureActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivityBarcodeCaptureBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBarcodeCaptureBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityBarcodeCaptureBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_barcode_capture, root, attachToRoot, component);
    }

    public static ActivityBarcodeCaptureBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBarcodeCaptureBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityBarcodeCaptureBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_barcode_capture, null, false, component);
    }

    public static ActivityBarcodeCaptureBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBarcodeCaptureBinding bind(View view, Object component) {
        return (ActivityBarcodeCaptureBinding) bind(component, view, R.layout.activity_barcode_capture);
    }
}
