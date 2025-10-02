package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgl {
    private static final Class<?> zzti = zzu("libcore.io.Memory");
    private static final boolean zztj;

    static boolean zzel() {
        return (zzti == null || zztj) ? false : true;
    }

    static Class<?> zzem() {
        return zzti;
    }

    private static <T> Class<T> zzu(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    static {
        zztj = zzu("org.robolectric.Robolectric") != null;
    }
}
