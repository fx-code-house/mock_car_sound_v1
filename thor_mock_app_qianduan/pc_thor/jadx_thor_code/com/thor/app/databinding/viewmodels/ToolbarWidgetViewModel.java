package com.thor.app.databinding.viewmodels;

import androidx.databinding.ObservableBoolean;
import kotlin.Metadata;

/* compiled from: ToolbarWidgetViewModel.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0005R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0005¨\u0006\b"}, d2 = {"Lcom/thor/app/databinding/viewmodels/ToolbarWidgetViewModel;", "", "()V", "isBluetoothConnected", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "isBluetoothIndicator", "isNotificationDeleteBtn", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ToolbarWidgetViewModel {
    private final ObservableBoolean isBluetoothConnected = new ObservableBoolean(false);
    private final ObservableBoolean isBluetoothIndicator = new ObservableBoolean(false);
    private final ObservableBoolean isNotificationDeleteBtn = new ObservableBoolean(false);

    /* renamed from: isBluetoothConnected, reason: from getter */
    public final ObservableBoolean getIsBluetoothConnected() {
        return this.isBluetoothConnected;
    }

    /* renamed from: isBluetoothIndicator, reason: from getter */
    public final ObservableBoolean getIsBluetoothIndicator() {
        return this.isBluetoothIndicator;
    }

    /* renamed from: isNotificationDeleteBtn, reason: from getter */
    public final ObservableBoolean getIsNotificationDeleteBtn() {
        return this.isNotificationDeleteBtn;
    }
}
