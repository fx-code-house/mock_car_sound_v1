package com.thor.app.gui.activities.shop.main;

import android.content.Context;
import androidx.activity.contextaware.OnContextAvailableListener;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;

/* loaded from: classes3.dex */
public abstract class Hilt_SubscriptionsActivity extends SubscriptionCheckActivity {
    private boolean injected = false;

    Hilt_SubscriptionsActivity() {
        _initHiltInternal();
    }

    private void _initHiltInternal() {
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: com.thor.app.gui.activities.shop.main.Hilt_SubscriptionsActivity.1
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public void onContextAvailable(Context context) {
                Hilt_SubscriptionsActivity.this.inject();
            }
        });
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity
    protected void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        ((SubscriptionsActivity_GeneratedInjector) ((GeneratedComponentManagerHolder) UnsafeCasts.unsafeCast(this)).generatedComponent()).injectSubscriptionsActivity((SubscriptionsActivity) UnsafeCasts.unsafeCast(this));
    }
}
