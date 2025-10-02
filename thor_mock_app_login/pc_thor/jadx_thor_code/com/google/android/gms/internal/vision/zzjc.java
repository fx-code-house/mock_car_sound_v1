package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjc implements zzjk {
    private zzjk[] zzaah;

    zzjc(zzjk... zzjkVarArr) {
        this.zzaah = zzjkVarArr;
    }

    @Override // com.google.android.gms.internal.vision.zzjk
    public final boolean zza(Class<?> cls) {
        for (zzjk zzjkVar : this.zzaah) {
            if (zzjkVar.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.vision.zzjk
    public final zzjl zzb(Class<?> cls) {
        for (zzjk zzjkVar : this.zzaah) {
            if (zzjkVar.zza(cls)) {
                return zzjkVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
