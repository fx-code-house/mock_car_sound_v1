package com.thor.businessmodule.viewmodel.main;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpdateFirmwareFragmentDialogViewModel.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0012"}, d2 = {"Lcom/thor/businessmodule/viewmodel/main/UpdateFirmwareFragmentDialogViewModel;", "Landroidx/databinding/Observable$OnPropertyChangedCallback;", "()V", "info", "Landroidx/databinding/ObservableField;", "", "getInfo", "()Landroidx/databinding/ObservableField;", "isUpdatingFirmware", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "isWaiting", "onPropertyChanged", "", "sender", "Landroidx/databinding/Observable;", "propertyId", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UpdateFirmwareFragmentDialogViewModel extends Observable.OnPropertyChangedCallback {
    private final ObservableField<String> info;
    private final ObservableBoolean isUpdatingFirmware;
    private final ObservableBoolean isWaiting;

    public UpdateFirmwareFragmentDialogViewModel() {
        ObservableBoolean observableBoolean = new ObservableBoolean(false);
        this.isWaiting = observableBoolean;
        ObservableBoolean observableBoolean2 = new ObservableBoolean(false);
        this.isUpdatingFirmware = observableBoolean2;
        this.info = new ObservableField<>("");
        UpdateFirmwareFragmentDialogViewModel updateFirmwareFragmentDialogViewModel = this;
        observableBoolean.addOnPropertyChangedCallback(updateFirmwareFragmentDialogViewModel);
        observableBoolean2.addOnPropertyChangedCallback(updateFirmwareFragmentDialogViewModel);
    }

    /* renamed from: isWaiting, reason: from getter */
    public final ObservableBoolean getIsWaiting() {
        return this.isWaiting;
    }

    /* renamed from: isUpdatingFirmware, reason: from getter */
    public final ObservableBoolean getIsUpdatingFirmware() {
        return this.isUpdatingFirmware;
    }

    public final ObservableField<String> getInfo() {
        return this.info;
    }

    @Override // androidx.databinding.Observable.OnPropertyChangedCallback
    public void onPropertyChanged(Observable sender, int propertyId) {
        if (Intrinsics.areEqual(sender, this.isWaiting)) {
            if (this.isWaiting.get()) {
                this.isUpdatingFirmware.set(false);
            }
        } else if (Intrinsics.areEqual(sender, this.isUpdatingFirmware) && this.isUpdatingFirmware.get()) {
            this.isWaiting.set(false);
        }
    }
}
