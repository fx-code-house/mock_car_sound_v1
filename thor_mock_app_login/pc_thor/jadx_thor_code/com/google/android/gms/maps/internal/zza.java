package com.google.android.gms.maps.internal;

/* loaded from: classes2.dex */
public final class zza {
    public static Boolean zza(byte b) {
        if (b == 0) {
            return Boolean.FALSE;
        }
        if (b != 1) {
            return null;
        }
        return Boolean.TRUE;
    }

    public static byte zza(Boolean bool) {
        if (bool != null) {
            return bool.booleanValue() ? (byte) 1 : (byte) 0;
        }
        return (byte) -1;
    }
}
