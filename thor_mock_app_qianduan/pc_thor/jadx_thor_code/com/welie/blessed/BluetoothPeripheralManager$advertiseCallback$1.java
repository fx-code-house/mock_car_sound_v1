package com.welie.blessed;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.os.Handler;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothPeripheralManager.kt */
@Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"com/welie/blessed/BluetoothPeripheralManager$advertiseCallback$1", "Landroid/bluetooth/le/AdvertiseCallback;", "onStartFailure", "", "errorCode", "", "onStartSuccess", "settingsInEffect", "Landroid/bluetooth/le/AdvertiseSettings;", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothPeripheralManager$advertiseCallback$1 extends AdvertiseCallback {
    final /* synthetic */ BluetoothPeripheralManager this$0;

    BluetoothPeripheralManager$advertiseCallback$1(BluetoothPeripheralManager bluetoothPeripheralManager) {
        this.this$0 = bluetoothPeripheralManager;
    }

    @Override // android.bluetooth.le.AdvertiseCallback
    public void onStartSuccess(final AdvertiseSettings settingsInEffect) {
        Intrinsics.checkNotNullParameter(settingsInEffect, "settingsInEffect");
        Logger.INSTANCE.i(BluetoothPeripheralManager.TAG, "advertising started");
        this.this$0.isAdvertising = true;
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$advertiseCallback$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$advertiseCallback$1.onStartSuccess$lambda$0(bluetoothPeripheralManager, settingsInEffect);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStartSuccess$lambda$0(BluetoothPeripheralManager this$0, AdvertiseSettings settingsInEffect) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(settingsInEffect, "$settingsInEffect");
        this$0.callback.onAdvertisingStarted(settingsInEffect);
    }

    @Override // android.bluetooth.le.AdvertiseCallback
    public void onStartFailure(int errorCode) {
        final AdvertiseError advertiseErrorFromValue = AdvertiseError.INSTANCE.fromValue(errorCode);
        Logger.INSTANCE.e(BluetoothPeripheralManager.TAG, "advertising failed with error '%s'", advertiseErrorFromValue);
        Handler handler = this.this$0.mainHandler;
        final BluetoothPeripheralManager bluetoothPeripheralManager = this.this$0;
        handler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$advertiseCallback$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager$advertiseCallback$1.onStartFailure$lambda$1(bluetoothPeripheralManager, advertiseErrorFromValue);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStartFailure$lambda$1(BluetoothPeripheralManager this$0, AdvertiseError advertiseError) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(advertiseError, "$advertiseError");
        this$0.callback.onAdvertiseFailure(advertiseError);
    }
}
