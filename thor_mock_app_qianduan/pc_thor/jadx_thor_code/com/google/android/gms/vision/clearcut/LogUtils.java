package com.google.android.gms.vision.clearcut;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.vision.zzfl;
import com.google.android.gms.internal.vision.zzid;
import com.google.android.gms.vision.L;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class LogUtils {
    public static zzfl.zza zza(Context context) {
        zzfl.zza.C0029zza c0029zzaZzm = zzfl.zza.zzdd().zzm(context.getPackageName());
        String strZzb = zzb(context);
        if (strZzb != null) {
            c0029zzaZzm.zzn(strZzb);
        }
        return (zzfl.zza) ((zzid) c0029zzaZzm.zzgw());
    }

    private static String zzb(Context context) {
        try {
            return Wrappers.packageManager(context).getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            L.e(e, "Unable to find calling package info for %s", context.getPackageName());
            return null;
        }
    }
}
