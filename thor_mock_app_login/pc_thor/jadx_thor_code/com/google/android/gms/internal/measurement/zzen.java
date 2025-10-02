package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzen {
    static void zza(Object obj, Object obj2) {
        if (obj == null) {
            String strValueOf = String.valueOf(obj2);
            throw new NullPointerException(new StringBuilder(String.valueOf(strValueOf).length() + 24).append("null key in entry: null=").append(strValueOf).toString());
        }
        if (obj2 != null) {
            return;
        }
        String strValueOf2 = String.valueOf(obj);
        throw new NullPointerException(new StringBuilder(String.valueOf(strValueOf2).length() + 26).append("null value in entry: ").append(strValueOf2).append("=null").toString());
    }
}
