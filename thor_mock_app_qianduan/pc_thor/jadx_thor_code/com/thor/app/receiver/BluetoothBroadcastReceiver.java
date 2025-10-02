package com.thor.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothStateChangedEvent;
import com.thor.app.bus.events.bluetooth.StartScanBluetoothDevicesEvent;
import com.thor.app.managers.BleManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: BluetoothBroadcastReceiver.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002J\u001c\u0010\b\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "bleManager", "Lcom/thor/app/managers/BleManager;", "onBluetoothOff", "", "onBluetoothOn", "onReceive", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private BleManager bleManager;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action;
        this.bleManager = BleManager.INSTANCE.from(context);
        if (intent == null || (action = intent.getAction()) == null || !action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            return;
        }
        int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
        Log.i("BluetoothBroadcastReceiver", "Bluetooth state: " + intExtra);
        Timber.INSTANCE.d("NonRxState: %s", Integer.valueOf(intExtra));
        if (intExtra == 10) {
            onBluetoothOff();
        } else if (intExtra == 12) {
            onBluetoothOn();
        } else if (intExtra == 13) {
            onBluetoothOff();
        }
        EventBus.getDefault().post(new BluetoothStateChangedEvent(intExtra));
    }

    private final void onBluetoothOff() {
        EventBus.getDefault().post(new BluetoothDeviceConnectionChangedEvent(false));
        BleManager bleManager = this.bleManager;
        BleManager bleManager2 = null;
        if (bleManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleManager");
            bleManager = null;
        }
        bleManager.setBluetoothStateConnection(false);
        BleManager bleManager3 = this.bleManager;
        if (bleManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleManager");
        } else {
            bleManager2 = bleManager3;
        }
        bleManager2.disconnect(false);
    }

    private final void onBluetoothOn() {
        EventBus.getDefault().post(new StartScanBluetoothDevicesEvent(true));
        BleManager bleManager = this.bleManager;
        BleManager bleManager2 = null;
        if (bleManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleManager");
            bleManager = null;
        }
        bleManager.setBluetoothStateConnection(true);
        BleManager bleManager3 = this.bleManager;
        if (bleManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleManager");
        } else {
            bleManager2 = bleManager3;
        }
        bleManager2.connect();
    }
}
