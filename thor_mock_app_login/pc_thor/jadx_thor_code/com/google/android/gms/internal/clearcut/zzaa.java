package com.google.android.gms.internal.clearcut;

import android.content.Context;
import android.os.UserManager;

/* loaded from: classes2.dex */
public class zzaa {
    private static volatile UserManager zzdc;
    private static volatile boolean zzdd = !zzf();

    private zzaa() {
    }

    public static boolean zze(Context context) {
        return zzf() && !zzf(context);
    }

    private static boolean zzf() {
        return true;
    }

    private static boolean zzf(Context context) {
        boolean zIsUserUnlocked = zzdd;
        if (!zIsUserUnlocked) {
            UserManager userManager = zzdc;
            if (userManager == null) {
                synchronized (zzaa.class) {
                    userManager = zzdc;
                    if (userManager == null) {
                        UserManager userManager2 = (UserManager) context.getSystemService(UserManager.class);
                        zzdc = userManager2;
                        if (userManager2 == null) {
                            zzdd = true;
                            return true;
                        }
                        userManager = userManager2;
                    }
                }
            }
            zIsUserUnlocked = userManager.isUserUnlocked();
            zzdd = zIsUserUnlocked;
            if (zIsUserUnlocked) {
                zzdc = null;
            }
        }
        return zIsUserUnlocked;
    }
}
