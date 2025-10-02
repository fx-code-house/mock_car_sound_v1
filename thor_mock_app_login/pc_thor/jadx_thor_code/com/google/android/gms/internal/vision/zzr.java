package com.google.android.gms.internal.vision;

import com.google.android.gms.vision.L;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzr {
    private static final Object sLock = new Object();
    private static final HashMap<String, Integer> zzcf = new HashMap<>();

    public static boolean zza(String str, String str2) {
        synchronized (sLock) {
            String strValueOf = String.valueOf(str);
            String strValueOf2 = String.valueOf(str2);
            String strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
            HashMap<String, Integer> map = zzcf;
            int iIntValue = map.containsKey(strConcat) ? map.get(strConcat).intValue() : 0;
            if ((iIntValue & 1) != 0) {
                return true;
            }
            try {
                System.loadLibrary(str2);
                map.put(strConcat, Integer.valueOf(iIntValue | 1));
                return true;
            } catch (UnsatisfiedLinkError e) {
                if ((iIntValue & 4) == 0) {
                    L.e(e, "System.loadLibrary failed: %s", str2);
                    zzcf.put(strConcat, Integer.valueOf(iIntValue | 4));
                }
                return false;
            }
        }
    }
}
