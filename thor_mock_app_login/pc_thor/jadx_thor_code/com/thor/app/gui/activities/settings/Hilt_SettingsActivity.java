package com.thor.app.gui.activities.settings;

import android.content.Context;
import androidx.activity.contextaware.OnContextAvailableListener;
import com.thor.app.gui.activities.EmergencySituationBaseActivity;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;

/* loaded from: classes3.dex */
public abstract class Hilt_SettingsActivity extends EmergencySituationBaseActivity {
    private boolean injected = false;

    Hilt_SettingsActivity() {
        _initHiltInternal();
    }

    private void _initHiltInternal() {
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: com.thor.app.gui.activities.settings.Hilt_SettingsActivity.1
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public void onContextAvailable(Context context) {
                Hilt_SettingsActivity.this.inject();
            }
        });
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity
    protected void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        ((SettingsActivity_GeneratedInjector) ((GeneratedComponentManagerHolder) UnsafeCasts.unsafeCast(this)).generatedComponent()).injectSettingsActivity((SettingsActivity) UnsafeCasts.unsafeCast(this));
    }
}
