package com.welie.blessed;

import android.bluetooth.le.ScanResult;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothCentralManagerCallback.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H\u0016¨\u0006\u0015"}, d2 = {"Lcom/welie/blessed/BluetoothCentralManagerCallback;", "", "()V", "onBluetoothAdapterStateChanged", "", "state", "", "onConnectedPeripheral", "peripheral", "Lcom/welie/blessed/BluetoothPeripheral;", "onConnectionFailed", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/HciStatus;", "onDisconnectedPeripheral", "onDiscoveredPeripheral", "scanResult", "Landroid/bluetooth/le/ScanResult;", "onScanFailed", "scanFailure", "Lcom/welie/blessed/ScanFailure;", "NULL", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BluetoothCentralManagerCallback {

    /* compiled from: BluetoothCentralManagerCallback.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/welie/blessed/BluetoothCentralManagerCallback$NULL;", "Lcom/welie/blessed/BluetoothCentralManagerCallback;", "()V", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class NULL extends BluetoothCentralManagerCallback {
    }

    public void onBluetoothAdapterStateChanged(int state) {
    }

    public void onConnectedPeripheral(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }

    public void onConnectionFailed(BluetoothPeripheral peripheral, HciStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onDisconnectedPeripheral(BluetoothPeripheral peripheral, HciStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onDiscoveredPeripheral(BluetoothPeripheral peripheral, ScanResult scanResult) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(scanResult, "scanResult");
    }

    public void onScanFailed(ScanFailure scanFailure) {
        Intrinsics.checkNotNullParameter(scanFailure, "scanFailure");
    }
}
