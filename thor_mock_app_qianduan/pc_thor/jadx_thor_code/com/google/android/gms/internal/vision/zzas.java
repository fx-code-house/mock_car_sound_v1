package com.google.android.gms.internal.vision;

import android.content.Context;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class zzas {
    private static UserManager zzfm;
    private static volatile boolean zzfn = !zzu();
    private static boolean zzfo = false;

    private zzas() {
    }

    public static boolean zzu() {
        return true;
    }

    public static boolean isUserUnlocked(Context context) {
        return !zzu() || zzd(context);
    }

    private static boolean zzc(Context context) {
        boolean z;
        boolean z2 = true;
        int i = 1;
        while (true) {
            z = false;
            if (i > 2) {
                break;
            }
            if (zzfm == null) {
                zzfm = (UserManager) context.getSystemService(UserManager.class);
            }
            UserManager userManager = zzfm;
            if (userManager == null) {
                return true;
            }
            try {
                if (userManager.isUserUnlocked()) {
                    break;
                }
                if (userManager.isUserRunning(Process.myUserHandle())) {
                    z2 = false;
                }
            } catch (NullPointerException e) {
                Log.w("DirectBootUtils", "Failed to check if user is unlocked.", e);
                zzfm = null;
                i++;
            }
        }
        z = z2;
        if (z) {
            zzfm = null;
        }
        return z;
    }

    private static boolean zzd(Context context) {
        if (zzfn) {
            return true;
        }
        synchronized (zzas.class) {
            if (zzfn) {
                return true;
            }
            boolean zZzc = zzc(context);
            if (zZzc) {
                zzfn = zZzc;
            }
            return zZzc;
        }
    }
}
