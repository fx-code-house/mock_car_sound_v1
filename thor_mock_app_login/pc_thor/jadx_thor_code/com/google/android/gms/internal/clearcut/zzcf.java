package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg;

/* loaded from: classes2.dex */
final class zzcf implements zzdn {
    private static final zzcf zzjo = new zzcf();

    private zzcf() {
    }

    public static zzcf zzay() {
        return zzjo;
    }

    @Override // com.google.android.gms.internal.clearcut.zzdn
    public final boolean zza(Class<?> cls) {
        return zzcg.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.clearcut.zzdn
    public final zzdm zzb(Class<?> cls) {
        if (!zzcg.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzdm) zzcg.zzc(cls.asSubclass(zzcg.class)).zza(zzcg.zzg.zzkf, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
