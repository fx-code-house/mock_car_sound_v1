package com.thor.basemodule.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: BaseActivity.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/thor/basemodule/gui/activity/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onBackPressed", "", "onSupportNavigateUp", "", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BaseActivity extends AppCompatActivity {
    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        Timber.i("onSupportNavigateUp", new Object[0]);
        onBackPressed();
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Timber.i("onBackPressed", new Object[0]);
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
        super.onBackPressed();
    }
}
