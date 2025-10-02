package com.thor.app.utils.theming;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import com.carsystems.thor.app.R;
import com.thor.app.settings.Settings;
import com.thor.app.utils.alias.AvasLauncherAlias;
import com.thor.app.utils.alias.EcoLauncherAlias;
import com.thor.app.utils.alias.MainLauncherAlias;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ThemingUtil.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\f"}, d2 = {"Lcom/thor/app/utils/theming/ThemingUtil;", "", "()V", "changeModeToAvas", "", "activity", "Landroid/app/Activity;", "changeModeToEco", "changeModeToMain", "onActivityCreateSetTheme", "onChangeTheme", "onSplashActivityCreateSetTheme", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ThemingUtil {
    public static final ThemingUtil INSTANCE = new ThemingUtil();

    private ThemingUtil() {
    }

    public final void onChangeTheme(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }

    public final void onActivityCreateSetTheme(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        if (carFuelType == 1) {
            activity.setTheme(R.style.Theme_Eco);
            return;
        }
        if (carFuelType == 2) {
            activity.setTheme(R.style.Theme_Main);
        } else if (carFuelType == 3) {
            activity.setTheme(R.style.Theme_Avas);
        } else {
            activity.setTheme(R.style.Theme_Main);
        }
    }

    public final void onSplashActivityCreateSetTheme(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Log.i("ThemingUtil", String.valueOf(Settings.INSTANCE.getCarFuelType()));
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        if (carFuelType == 1) {
            changeModeToEco(activity);
            return;
        }
        if (carFuelType == 2) {
            changeModeToMain(activity);
        } else if (carFuelType == 3) {
            changeModeToAvas(activity);
        } else {
            changeModeToMain(activity);
        }
    }

    private final void changeModeToEco(Activity activity) {
        Activity activity2 = activity;
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) MainLauncherAlias.class), 2, 1);
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) AvasLauncherAlias.class), 2, 1);
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) EcoLauncherAlias.class), 1, 1);
    }

    private final void changeModeToAvas(Activity activity) {
        Activity activity2 = activity;
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) MainLauncherAlias.class), 2, 1);
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) EcoLauncherAlias.class), 2, 1);
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) AvasLauncherAlias.class), 1, 1);
    }

    private final void changeModeToMain(Activity activity) {
        Activity activity2 = activity;
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) EcoLauncherAlias.class), 2, 1);
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) AvasLauncherAlias.class), 2, 1);
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity2, (Class<?>) MainLauncherAlias.class), 1, 1);
    }
}
