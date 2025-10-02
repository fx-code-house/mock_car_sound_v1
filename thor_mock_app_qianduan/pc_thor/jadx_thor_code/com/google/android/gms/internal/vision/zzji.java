package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzji {
    private static final zzjg zzaam = zzic();
    private static final zzjg zzaan = new zzjj();

    static zzjg zzia() {
        return zzaam;
    }

    static zzjg zzib() {
        return zzaan;
    }

    private static zzjg zzic() {
        try {
            return (zzjg) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
