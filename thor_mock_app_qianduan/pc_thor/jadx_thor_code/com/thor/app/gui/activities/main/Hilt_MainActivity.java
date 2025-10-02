package com.thor.app.gui.activities.main;

import android.content.Context;
import androidx.activity.contextaware.OnContextAvailableListener;
import com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;

/* loaded from: classes3.dex */
public abstract class Hilt_MainActivity extends BaseScanningBluetoothDevicesActivity {
    private boolean injected = false;

    Hilt_MainActivity() {
        _initHiltInternal();
    }

    private void _initHiltInternal() {
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: com.thor.app.gui.activities.main.Hilt_MainActivity.1
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public void onContextAvailable(Context context) {
                Hilt_MainActivity.this.inject();
            }
        });
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity
    protected void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        ((MainActivity_GeneratedInjector) ((GeneratedComponentManagerHolder) UnsafeCasts.unsafeCast(this)).generatedComponent()).injectMainActivity((MainActivity) UnsafeCasts.unsafeCast(this));
    }
}
