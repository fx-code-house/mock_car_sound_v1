package com.google.android.gms.internal.fido;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzcn {
    static final String zza;
    static final Comparator zzb;

    static {
        Comparator comparator;
        String strConcat = String.valueOf(zzcn.class.getName()).concat("$UnsafeComparator");
        zza = strConcat;
        try {
            Object[] enumConstants = Class.forName(strConcat).getEnumConstants();
            enumConstants.getClass();
            comparator = (Comparator) enumConstants[0];
        } catch (Throwable unused) {
            comparator = zzcm.INSTANCE;
        }
        zzb = comparator;
    }

    zzcn() {
    }
}
