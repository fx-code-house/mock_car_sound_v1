package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public enum zzip {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, 0),
    LONG(Long.TYPE, Long.class, 0L),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.TYPE, Boolean.class, false),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzgs.class, zzgs.class, zzgs.zztt),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);

    private final Class<?> zzzo;
    private final Class<?> zzzp;
    private final Object zzzq;

    zzip(Class cls, Class cls2, Object obj) {
        this.zzzo = cls;
        this.zzzp = cls2;
        this.zzzq = obj;
    }

    public final Class<?> zzhq() {
        return this.zzzp;
    }
}
