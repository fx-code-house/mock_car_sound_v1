package com.thor.app.gui.activities.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentTransaction;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityDemoPresetSoundSettingsBinding;
import com.thor.app.gui.fragments.demo.DemoPresetSoundSettingsFragment;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.model.DemoSoundPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoPresetSoundSettingsActivity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/gui/activities/demo/DemoPresetSoundSettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityDemoPresetSoundSettingsBinding;", "handleIntentData", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoPresetSoundSettingsActivity extends AppCompatActivity {
    private ActivityDemoPresetSoundSettingsBinding binding;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoPresetSoundSettingsActivity demoPresetSoundSettingsActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(demoPresetSoundSettingsActivity);
        ViewDataBinding contentView = DataBindingUtil.setContentView(demoPresetSoundSettingsActivity, R.layout.activity_demo_preset_sound_settings);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…mo_preset_sound_settings)");
        this.binding = (ActivityDemoPresetSoundSettingsBinding) contentView;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).add(R.id.frame_container, DemoPresetSoundSettingsFragment.INSTANCE.newInstance()).commit();
        }
    }

    private final void handleIntentData() {
        Bundle extras = getIntent().getExtras();
        DemoSoundPackage demoSoundPackage = extras != null ? (DemoSoundPackage) extras.getParcelable(DemoSoundPackage.BUNDLE_NAME) : null;
        if (Intrinsics.areEqual(demoSoundPackage != null ? demoSoundPackage.getCategory() : null, DemoSoundPackage.FuelCategory.EV.getValue())) {
            setTheme(R.style.Theme_Eco);
        } else {
            setTheme(R.style.Theme_Main);
        }
    }
}
