package com.google.android.gms.internal.icing;

import android.content.Context;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class zzaz {
    private static UserManager zzcf;
    private static volatile boolean zzcg = !zzk();
    private static boolean zzch = false;

    private zzaz() {
    }

    public static boolean zzk() {
        return true;
    }

    public static boolean isUserUnlocked(Context context) {
        return !zzk() || zzb(context);
    }

    private static boolean zza(Context context) {
        boolean z;
        boolean z2 = true;
        int i = 1;
        while (true) {
            z = false;
            if (i > 2) {
                break;
            }
            if (zzcf == null) {
                zzcf = (UserManager) context.getSystemService(UserManager.class);
            }
            UserManager userManager = zzcf;
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
                zzcf = null;
                i++;
            }
        }
        z = z2;
        if (z) {
            zzcf = null;
        }
        return z;
    }

    private static boolean zzb(Context context) {
        if (zzcg) {
            return true;
        }
        synchronized (zzaz.class) {
            if (zzcg) {
                return true;
            }
            boolean zZza = zza(context);
            if (zZza) {
                zzcg = zZza;
            }
            return zZza;
        }
    }
}
