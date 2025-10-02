package com.thor.businessmodule.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import kotlin.Metadata;

/* compiled from: ChangeCarActivityViewModel.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0014\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0016\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\u0018\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u001a\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011¨\u0006\u001b"}, d2 = {"Lcom/thor/businessmodule/viewmodel/ChangeCarActivityViewModel;", "", "()V", "carGeneration", "Landroidx/databinding/ObservableField;", "", "getCarGeneration", "()Landroidx/databinding/ObservableField;", "carMark", "getCarMark", "carModel", "getCarModel", "carSeries", "getCarSeries", "enableCarGeneration", "Landroidx/databinding/ObservableBoolean;", "getEnableCarGeneration", "()Landroidx/databinding/ObservableBoolean;", "enableCarMark", "getEnableCarMark", "enableCarModel", "getEnableCarModel", "enableCarSeries", "getEnableCarSeries", "enableChangeCar", "getEnableChangeCar", "isSelector", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ChangeCarActivityViewModel {
    private final ObservableField<String> carMark = new ObservableField<>("");
    private final ObservableField<String> carModel = new ObservableField<>("");
    private final ObservableField<String> carGeneration = new ObservableField<>("");
    private final ObservableField<String> carSeries = new ObservableField<>("");
    private final ObservableBoolean enableChangeCar = new ObservableBoolean(false);
    private final ObservableBoolean isSelector = new ObservableBoolean(false);
    private final ObservableBoolean enableCarMark = new ObservableBoolean(true);
    private final ObservableBoolean enableCarModel = new ObservableBoolean(false);
    private final ObservableBoolean enableCarGeneration = new ObservableBoolean(false);
    private final ObservableBoolean enableCarSeries = new ObservableBoolean(false);

    public final ObservableField<String> getCarMark() {
        return this.carMark;
    }

    public final ObservableField<String> getCarModel() {
        return this.carModel;
    }

    public final ObservableField<String> getCarGeneration() {
        return this.carGeneration;
    }

    public final ObservableField<String> getCarSeries() {
        return this.carSeries;
    }

    public final ObservableBoolean getEnableChangeCar() {
        return this.enableChangeCar;
    }

    /* renamed from: isSelector, reason: from getter */
    public final ObservableBoolean getIsSelector() {
        return this.isSelector;
    }

    public final ObservableBoolean getEnableCarMark() {
        return this.enableCarMark;
    }

    public final ObservableBoolean getEnableCarModel() {
        return this.enableCarModel;
    }

    public final ObservableBoolean getEnableCarGeneration() {
        return this.enableCarGeneration;
    }

    public final ObservableBoolean getEnableCarSeries() {
        return this.enableCarSeries;
    }
}
