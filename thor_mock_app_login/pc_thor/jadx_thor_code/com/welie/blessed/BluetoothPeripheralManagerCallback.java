package com.welie.blessed;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.AdvertiseSettings;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothPeripheralManagerCallback.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J \u0010\u0017\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010\u0018\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J \u0010\u001b\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J \u0010\u001c\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J(\u0010\u001d\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u0014H\u0016J\u0018\u0010\u001f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010 \u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010!\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020#H\u0016¨\u0006$"}, d2 = {"Lcom/welie/blessed/BluetoothPeripheralManagerCallback;", "", "()V", "onAdvertiseFailure", "", "advertiseError", "Lcom/welie/blessed/AdvertiseError;", "onAdvertisingStarted", "settingsInEffect", "Landroid/bluetooth/le/AdvertiseSettings;", "onAdvertisingStopped", "onCentralConnected", "bluetoothCentral", "Lcom/welie/blessed/BluetoothCentral;", "onCentralDisconnected", "onCharacteristicRead", "Lcom/welie/blessed/ReadResponse;", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "onCharacteristicWrite", "Lcom/welie/blessed/GattStatus;", "value", "", "onCharacteristicWriteCompleted", "onDescriptorRead", "descriptor", "Landroid/bluetooth/BluetoothGattDescriptor;", "onDescriptorWrite", "onDescriptorWriteCompleted", "onNotificationSent", NotificationCompat.CATEGORY_STATUS, "onNotifyingDisabled", "onNotifyingEnabled", "onServiceAdded", NotificationCompat.CATEGORY_SERVICE, "Landroid/bluetooth/BluetoothGattService;", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BluetoothPeripheralManagerCallback {
    public void onAdvertiseFailure(AdvertiseError advertiseError) {
        Intrinsics.checkNotNullParameter(advertiseError, "advertiseError");
    }

    public void onAdvertisingStarted(AdvertiseSettings settingsInEffect) {
        Intrinsics.checkNotNullParameter(settingsInEffect, "settingsInEffect");
    }

    public void onAdvertisingStopped() {
    }

    public void onCentralConnected(BluetoothCentral bluetoothCentral) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
    }

    public void onCentralDisconnected(BluetoothCentral bluetoothCentral) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
    }

    public void onCharacteristicWriteCompleted(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic, byte[] value) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(value, "value");
    }

    public void onDescriptorWriteCompleted(BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor, byte[] value) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(value, "value");
    }

    public void onNotificationSent(BluetoothCentral bluetoothCentral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(status, "status");
    }

    public void onNotifyingDisabled(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
    }

    public void onNotifyingEnabled(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
    }

    public void onServiceAdded(GattStatus status, BluetoothGattService service) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(service, "service");
    }

    public ReadResponse onCharacteristicRead(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        return new ReadResponse(GattStatus.SUCCESS, new byte[0]);
    }

    public GattStatus onCharacteristicWrite(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic, byte[] value) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Intrinsics.checkNotNullParameter(value, "value");
        return GattStatus.SUCCESS;
    }

    public ReadResponse onDescriptorRead(BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new ReadResponse(GattStatus.SUCCESS, new byte[0]);
    }

    public GattStatus onDescriptorWrite(BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor, byte[] value) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(value, "value");
        return GattStatus.SUCCESS;
    }
}
