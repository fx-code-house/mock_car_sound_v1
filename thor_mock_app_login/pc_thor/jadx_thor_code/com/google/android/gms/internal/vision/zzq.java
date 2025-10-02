package com.google.android.gms.internal.vision;

import android.content.Context;
import android.util.Log;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzq {
    public static boolean zza(Context context, String str, String str2) {
        String strZzl = zzdg.zzl(str2);
        if (!"face".equals(str) && !"ica".equals(str) && !"ocr".equals(str) && !"barcode".equals(str)) {
            Log.e("NativeLibraryLoader", String.format("Unrecognized engine: %s", str));
            return false;
        }
        int iLastIndexOf = strZzl.lastIndexOf(".so");
        if (iLastIndexOf == strZzl.length() - 3) {
            strZzl = strZzl.substring(0, iLastIndexOf);
        }
        if (strZzl.indexOf("lib") == 0) {
            strZzl = strZzl.substring(3);
        }
        boolean zZza = zzr.zza(str, strZzl);
        if (!zZza) {
            Log.d("NativeLibraryLoader", String.format("%s engine not loaded with %s.", str, strZzl));
        }
        return zZza;
    }
}
