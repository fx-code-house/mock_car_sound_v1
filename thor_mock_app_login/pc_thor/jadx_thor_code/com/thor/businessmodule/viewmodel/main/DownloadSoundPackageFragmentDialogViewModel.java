package com.thor.businessmodule.viewmodel.main;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.google.firebase.messaging.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DownloadSoundPackageFragmentDialogViewModel.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/thor/businessmodule/viewmodel/main/DownloadSoundPackageFragmentDialogViewModel;", "Landroidx/databinding/Observable$OnPropertyChangedCallback;", "()V", "info", "Landroidx/databinding/ObservableField;", "", "getInfo", "()Landroidx/databinding/ObservableField;", "isDownloading", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "isUploading", Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, "getPackageName", "onPropertyChanged", "", "sender", "Landroidx/databinding/Observable;", "propertyId", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DownloadSoundPackageFragmentDialogViewModel extends Observable.OnPropertyChangedCallback {
    private final ObservableField<String> info;
    private final ObservableBoolean isDownloading;
    private final ObservableBoolean isUploading;
    private final ObservableField<String> packageName;

    public DownloadSoundPackageFragmentDialogViewModel() {
        ObservableBoolean observableBoolean = new ObservableBoolean(false);
        this.isDownloading = observableBoolean;
        ObservableBoolean observableBoolean2 = new ObservableBoolean(false);
        this.isUploading = observableBoolean2;
        this.info = new ObservableField<>("");
        this.packageName = new ObservableField<>("");
        DownloadSoundPackageFragmentDialogViewModel downloadSoundPackageFragmentDialogViewModel = this;
        observableBoolean.addOnPropertyChangedCallback(downloadSoundPackageFragmentDialogViewModel);
        observableBoolean2.addOnPropertyChangedCallback(downloadSoundPackageFragmentDialogViewModel);
    }

    /* renamed from: isDownloading, reason: from getter */
    public final ObservableBoolean getIsDownloading() {
        return this.isDownloading;
    }

    /* renamed from: isUploading, reason: from getter */
    public final ObservableBoolean getIsUploading() {
        return this.isUploading;
    }

    public final ObservableField<String> getInfo() {
        return this.info;
    }

    public final ObservableField<String> getPackageName() {
        return this.packageName;
    }

    @Override // androidx.databinding.Observable.OnPropertyChangedCallback
    public void onPropertyChanged(Observable sender, int propertyId) {
        if (Intrinsics.areEqual(sender, this.isDownloading)) {
            if (this.isDownloading.get()) {
                this.isUploading.set(false);
            }
        } else if (Intrinsics.areEqual(sender, this.isUploading) && this.isUploading.get()) {
            this.isDownloading.set(false);
        }
    }
}
