package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public enum zzej {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, 0),
    LONG(Long.TYPE, Long.class, 0L),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.TYPE, Boolean.class, false),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzct.class, zzct.class, zzct.zzgi),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);

    private final Class<?> zzlo;
    private final Class<?> zzlp;
    private final Object zzlq;

    zzej(Class cls, Class cls2, Object obj) {
        this.zzlo = cls;
        this.zzlp = cls2;
        this.zzlq = obj;
    }

    public final Class<?> zzcb() {
        return this.zzlp;
    }
}
