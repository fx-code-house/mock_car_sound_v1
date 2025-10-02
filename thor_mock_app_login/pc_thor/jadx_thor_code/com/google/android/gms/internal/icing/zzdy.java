package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzdy implements zzfe {
    private static final zzdy zzkf = new zzdy();

    private zzdy() {
    }

    public static zzdy zzbs() {
        return zzkf;
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final boolean zzb(Class<?> cls) {
        return zzdx.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final zzff zzc(Class<?> cls) {
        if (!zzdx.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzff) zzdx.zza(cls.asSubclass(zzdx.class)).zza(zzdx.zze.zzko, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
