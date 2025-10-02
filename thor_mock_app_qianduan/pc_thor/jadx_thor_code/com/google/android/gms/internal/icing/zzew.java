package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzew implements zzfe {
    private zzfe[] zzmh;

    zzew(zzfe... zzfeVarArr) {
        this.zzmh = zzfeVarArr;
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final boolean zzb(Class<?> cls) {
        for (zzfe zzfeVar : this.zzmh) {
            if (zzfeVar.zzb(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final zzff zzc(Class<?> cls) {
        for (zzfe zzfeVar : this.zzmh) {
            if (zzfeVar.zzb(cls)) {
                return zzfeVar.zzc(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
