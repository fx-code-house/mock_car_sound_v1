package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzcs {
    private static final Class<?> zzgg = zzo("libcore.io.Memory");
    private static final boolean zzgh;

    static boolean zzal() {
        return (zzgg == null || zzgh) ? false : true;
    }

    static Class<?> zzam() {
        return zzgg;
    }

    private static <T> Class<T> zzo(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    static {
        zzgh = zzo("org.robolectric.Robolectric") != null;
    }
}
