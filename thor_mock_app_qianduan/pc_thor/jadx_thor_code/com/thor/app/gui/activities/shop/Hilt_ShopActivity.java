package com.thor.app.gui.activities.shop;

import android.content.Context;
import androidx.activity.contextaware.OnContextAvailableListener;
import com.thor.app.gui.activities.EmergencySituationBaseActivity;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;

/* loaded from: classes3.dex */
public abstract class Hilt_ShopActivity extends EmergencySituationBaseActivity {
    private boolean injected = false;

    Hilt_ShopActivity() {
        _initHiltInternal();
    }

    private void _initHiltInternal() {
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: com.thor.app.gui.activities.shop.Hilt_ShopActivity.1
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public void onContextAvailable(Context context) {
                Hilt_ShopActivity.this.inject();
            }
        });
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity
    protected void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        ((ShopActivity_GeneratedInjector) ((GeneratedComponentManagerHolder) UnsafeCasts.unsafeCast(this)).generatedComponent()).injectShopActivity((ShopActivity) UnsafeCasts.unsafeCast(this));
    }
}
