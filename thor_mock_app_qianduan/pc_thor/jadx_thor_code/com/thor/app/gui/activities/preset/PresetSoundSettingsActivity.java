package com.thor.app.gui.activities.preset;

import android.os.Bundle;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityPresetSoundSettingsBinding;
import com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity;
import com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.model.MainPresetPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: PresetSoundSettingsActivity.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/activities/preset/PresetSoundSettingsActivity;", "Lcom/thor/app/gui/activities/shop/main/SubscriptionCheckActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityPresetSoundSettingsBinding;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PresetSoundSettingsActivity extends SubscriptionCheckActivity {
    private ActivityPresetSoundSettingsBinding binding;

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        MainPresetPackage mainPresetPackage;
        super.onCreate(savedInstanceState);
        try {
            ThemingUtil.INSTANCE.onActivityCreateSetTheme(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        getWindow().setStatusBarColor(0);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_preset_sound_settings);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…ty_preset_sound_settings)");
        this.binding = (ActivityPresetSoundSettingsBinding) contentView;
        if (getIntent() != null) {
            mainPresetPackage = (MainPresetPackage) getIntent().getParcelableExtra("preset_package");
            Timber.INSTANCE.i("Preset package: %s", mainPresetPackage);
        } else {
            mainPresetPackage = null;
        }
        if (savedInstanceState == null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager != null ? supportFragmentManager.beginTransaction() : null;
            Intrinsics.checkNotNull(fragmentTransactionBeginTransaction);
            fragmentTransactionBeginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).add(R.id.frame_container, PresetSoundSettingsFragment.INSTANCE.newInstance(mainPresetPackage)).commit();
        }
    }
}
