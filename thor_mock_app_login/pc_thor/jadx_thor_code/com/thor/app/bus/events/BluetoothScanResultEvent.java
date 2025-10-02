package com.thor.app.bus.events;

import android.bluetooth.le.ScanResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothScanResultEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/BluetoothScanResultEvent;", "", "scanResult", "Landroid/bluetooth/le/ScanResult;", "(Landroid/bluetooth/le/ScanResult;)V", "getScanResult", "()Landroid/bluetooth/le/ScanResult;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class BluetoothScanResultEvent {
    private final ScanResult scanResult;

    public static /* synthetic */ BluetoothScanResultEvent copy$default(BluetoothScanResultEvent bluetoothScanResultEvent, ScanResult scanResult, int i, Object obj) {
        if ((i & 1) != 0) {
            scanResult = bluetoothScanResultEvent.scanResult;
        }
        return bluetoothScanResultEvent.copy(scanResult);
    }

    /* renamed from: component1, reason: from getter */
    public final ScanResult getScanResult() {
        return this.scanResult;
    }

    public final BluetoothScanResultEvent copy(ScanResult scanResult) {
        Intrinsics.checkNotNullParameter(scanResult, "scanResult");
        return new BluetoothScanResultEvent(scanResult);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof BluetoothScanResultEvent) && Intrinsics.areEqual(this.scanResult, ((BluetoothScanResultEvent) other).scanResult);
    }

    public int hashCode() {
        return this.scanResult.hashCode();
    }

    public String toString() {
        return "BluetoothScanResultEvent(scanResult=" + this.scanResult + ")";
    }

    public BluetoothScanResultEvent(ScanResult scanResult) {
        Intrinsics.checkNotNullParameter(scanResult, "scanResult");
        this.scanResult = scanResult;
    }

    public final ScanResult getScanResult() {
        return this.scanResult;
    }
}
