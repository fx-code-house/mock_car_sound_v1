package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzbo implements zzcv {
    private static final zzbo zza = new zzbo();

    private zzbo() {
    }

    public static zzbo zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.wearable.zzcv
    public final boolean zzb(Class<?> cls) {
        return zzbs.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.wearable.zzcv
    public final zzcu zzc(Class<?> cls) {
        if (!zzbs.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzcu) zzbs.zzQ(cls.asSubclass(zzbs.class)).zzG(3, null, null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
