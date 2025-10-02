package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjx {
    private static final zzjv zzabh = zzii();
    private static final zzjv zzabi = new zzju();

    static zzjv zzig() {
        return zzabh;
    }

    static zzjv zzih() {
        return zzabi;
    }

    private static zzjv zzii() {
        try {
            return (zzjv) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
