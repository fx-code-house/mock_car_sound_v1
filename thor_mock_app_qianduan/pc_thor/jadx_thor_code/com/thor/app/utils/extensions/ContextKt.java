package com.thor.app.utils.extensions;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import androidx.core.app.ActivityCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Context.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0002\u001a\n\u0010\u0007\u001a\u00020\u0006*\u00020\u0002\u001a\u0014\u0010\b\u001a\u00020\t*\u00020\u00022\b\b\u0001\u0010\n\u001a\u00020\t¨\u0006\u000b"}, d2 = {"checkBluetoothPermissionAndInvoke", "", "Landroid/content/Context;", "action", "Lkotlin/Function0;", "checkBluetoothPermissions", "", "checkLocationPermission", "fetchAttrValue", "", "resId", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ContextKt {
    public static final int fetchAttrValue(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.data;
    }

    public static final boolean checkBluetoothPermissions(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return ActivityCompat.checkSelfPermission(context, "android.permission.BLUETOOTH_SCAN") == 0 && ActivityCompat.checkSelfPermission(context, "android.permission.BLUETOOTH_CONNECT") == 0 && ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public static final boolean checkLocationPermission(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public static final void checkBluetoothPermissionAndInvoke(Context context, Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        if (Build.VERSION.SDK_INT >= 31) {
            if (ActivityCompat.checkSelfPermission(context, "android.permission.BLUETOOTH_CONNECT") != 0) {
                return;
            }
            action.invoke();
            return;
        }
        action.invoke();
    }
}
