package com.thor.app.gui.activities.updatecheck;

import android.content.Context;
import androidx.activity.contextaware.OnContextAvailableListener;
import com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;

/* loaded from: classes3.dex */
public abstract class Hilt_UpdateAppActivity extends BaseScanningBluetoothDevicesActivity {
    private boolean injected = false;

    Hilt_UpdateAppActivity() {
        _initHiltInternal();
    }

    private void _initHiltInternal() {
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: com.thor.app.gui.activities.updatecheck.Hilt_UpdateAppActivity.1
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public void onContextAvailable(Context context) {
                Hilt_UpdateAppActivity.this.inject();
            }
        });
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity
    protected void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        ((UpdateAppActivity_GeneratedInjector) ((GeneratedComponentManagerHolder) UnsafeCasts.unsafeCast(this)).generatedComponent()).injectUpdateAppActivity((UpdateAppActivity) UnsafeCasts.unsafeCast(this));
    }
}
