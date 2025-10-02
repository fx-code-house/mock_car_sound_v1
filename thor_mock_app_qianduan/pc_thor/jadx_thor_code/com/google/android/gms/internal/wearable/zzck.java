package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzck extends zzcl {
    private zzck() {
        super(null);
    }

    /* synthetic */ zzck(zzci zzciVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.wearable.zzcl
    final void zza(Object obj, long j) {
        ((zzbz) zzeg.zzn(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.wearable.zzcl
    final <E> void zzb(Object obj, Object obj2, long j) {
        zzbz zzbzVarZze = (zzbz) zzeg.zzn(obj, j);
        zzbz zzbzVar = (zzbz) zzeg.zzn(obj2, j);
        int size = zzbzVarZze.size();
        int size2 = zzbzVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzbzVarZze.zza()) {
                zzbzVarZze = zzbzVarZze.zze(size2 + size);
            }
            zzbzVarZze.addAll(zzbzVar);
        }
        if (size > 0) {
            zzbzVar = zzbzVarZze;
        }
        zzeg.zzo(obj, j, zzbzVar);
    }
}
