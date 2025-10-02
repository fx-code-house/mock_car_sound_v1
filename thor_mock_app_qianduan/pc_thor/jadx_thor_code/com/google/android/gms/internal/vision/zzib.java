package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzib implements zzjk {
    private static final zzib zzxu = new zzib();

    private zzib() {
    }

    public static zzib zzgq() {
        return zzxu;
    }

    @Override // com.google.android.gms.internal.vision.zzjk
    public final boolean zza(Class<?> cls) {
        return zzid.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.vision.zzjk
    public final zzjl zzb(Class<?> cls) {
        if (!zzid.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzjl) zzid.zzd(cls.asSubclass(zzid.class)).zza(zzid.zzf.zzyj, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
