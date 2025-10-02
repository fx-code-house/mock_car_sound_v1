package com.welie.blessed;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothPeripheralCallback.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b \u0018\u00002\u00020\u0001:\u0001'B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J0\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010#\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010&\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006("}, d2 = {"Lcom/welie/blessed/BluetoothPeripheralCallback;", "", "()V", "onBondLost", "", "peripheral", "Lcom/welie/blessed/BluetoothPeripheral;", "onBondingFailed", "onBondingStarted", "onBondingSucceeded", "onCharacteristicRead", "value", "", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/GattStatus;", "onCharacteristicUpdate", "onCharacteristicWrite", "onConnectionUpdated", "interval", "", "latency", "timeout", "onDescriptorRead", "descriptor", "Landroid/bluetooth/BluetoothGattDescriptor;", "onDescriptorWrite", "onMtuChanged", "mtu", "onNotificationStateUpdate", "onPhyUpdate", "txPhy", "Lcom/welie/blessed/PhyType;", "rxPhy", "onReadRemoteRssi", "rssi", "onRequestedConnectionPriority", "onServicesDiscovered", "NULL", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BluetoothPeripheralCallback {

    /* compiled from: BluetoothPeripheralCallback.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/welie/blessed/BluetoothPeripheralCallback$NULL;", "Lcom/welie/blessed/BluetoothPeripheralCallback;", "()V", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class NULL extends BluetoothPeripheralCallback {
    }

    public void onBondLost(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }

    public void onBondingFailed(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }

    public void onBondingStarted(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }

    public void onBondingSucceeded(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }

    public void onCharacteristicRead(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onCharacteristicUpdate(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onCharacteristicWrite(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onConnectionUpdated(BluetoothPeripheral peripheral, int interval, int latency, int timeout, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onDescriptorRead(BluetoothPeripheral peripheral, byte[] value, BluetoothGattDescriptor descriptor, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onDescriptorWrite(BluetoothPeripheral peripheral, byte[] value, BluetoothGattDescriptor descriptor, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onMtuChanged(BluetoothPeripheral peripheral, int mtu, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onNotificationStateUpdate(BluetoothPeripheral peripheral, BluetoothGattCharacteristic characteristic, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onPhyUpdate(BluetoothPeripheral peripheral, PhyType txPhy, PhyType rxPhy, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(txPhy, "txPhy");
        Intrinsics.checkNotNullParameter(rxPhy, "rxPhy");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onReadRemoteRssi(BluetoothPeripheral peripheral, int rssi, GattStatus status) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onRequestedConnectionPriority(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }

    public void onServicesDiscovered(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
    }
}
