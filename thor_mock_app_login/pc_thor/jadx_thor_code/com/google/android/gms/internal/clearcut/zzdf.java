package com.google.android.gms.internal.clearcut;

/* loaded from: classes2.dex */
final class zzdf implements zzdn {
    private zzdn[] zzma;

    zzdf(zzdn... zzdnVarArr) {
        this.zzma = zzdnVarArr;
    }

    @Override // com.google.android.gms.internal.clearcut.zzdn
    public final boolean zza(Class<?> cls) {
        for (zzdn zzdnVar : this.zzma) {
            if (zzdnVar.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.clearcut.zzdn
    public final zzdm zzb(Class<?> cls) {
        for (zzdn zzdnVar : this.zzma) {
            if (zzdnVar.zza(cls)) {
                return zzdnVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
