package com.karumi.dexter;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

/* loaded from: classes2.dex */
class AndroidPermissionService {
    AndroidPermissionService() {
    }

    int checkSelfPermission(Context context, String str) {
        return PermissionChecker.checkSelfPermission(context, str);
    }

    boolean isPermissionPermanentlyDenied(Activity activity, String str) {
        return !shouldShowRequestPermissionRationale(activity, str);
    }

    void requestPermissions(Activity activity, String[] strArr, int i) {
        if (activity == null) {
            return;
        }
        ActivityCompat.requestPermissions(activity, strArr, i);
    }

    boolean shouldShowRequestPermissionRationale(Activity activity, String str) {
        if (activity == null) {
            return false;
        }
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, str);
    }
}
