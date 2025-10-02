package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.basemodule.gui.view.CircleProgressView;
import com.thor.businessmodule.viewmodel.main.DownloadSoundPackageFragmentDialogViewModel;

/* loaded from: classes.dex */
public abstract class FragmentDialogDownloadPackageBinding extends ViewDataBinding {

    @Bindable
    protected DownloadSoundPackageFragmentDialogViewModel mModel;
    public final ProgressBar progressBar;
    public final FrameLayout progressBarPolling;
    public final TextView textSignal;
    public final TextView textViewCancel;
    public final TextView textViewInfo;
    public final TextView textViewPackageName;
    public final TextView textViewTitle;
    public final CircleProgressView updateProgress;

    public abstract void setModel(DownloadSoundPackageFragmentDialogViewModel model);

    protected FragmentDialogDownloadPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, ProgressBar progressBar, FrameLayout progressBarPolling, TextView textSignal, TextView textViewCancel, TextView textViewInfo, TextView textViewPackageName, TextView textViewTitle, CircleProgressView updateProgress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.progressBar = progressBar;
        this.progressBarPolling = progressBarPolling;
        this.textSignal = textSignal;
        this.textViewCancel = textViewCancel;
        this.textViewInfo = textViewInfo;
        this.textViewPackageName = textViewPackageName;
        this.textViewTitle = textViewTitle;
        this.updateProgress = updateProgress;
    }

    public DownloadSoundPackageFragmentDialogViewModel getModel() {
        return this.mModel;
    }

    public static FragmentDialogDownloadPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogDownloadPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentDialogDownloadPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_dialog_download_package, root, attachToRoot, component);
    }

    public static FragmentDialogDownloadPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogDownloadPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentDialogDownloadPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_dialog_download_package, null, false, component);
    }

    public static FragmentDialogDownloadPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDialogDownloadPackageBinding bind(View view, Object component) {
        return (FragmentDialogDownloadPackageBinding) bind(component, view, R.layout.fragment_dialog_download_package);
    }
}
