package com.thor.app.utils.security;

import android.app.Activity;
import android.content.Intent;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.thor.app.gui.activities.LockedDeviceActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceLockingUtils.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"onDeviceLocked", "", "activity", "Landroid/app/Activity;", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DeviceLockingUtilsKt {
    public static final void onDeviceLocked(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intent intent = new Intent(activity, (Class<?>) LockedDeviceActivity.class);
        intent.setFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        activity.startActivity(intent);
        activity.finishAffinity();
    }
}
