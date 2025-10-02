package com.google.android.gms.internal.vision;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zziy extends zzix {
    private zziy() {
        super();
    }

    @Override // com.google.android.gms.internal.vision.zzix
    final <L> List<L> zza(Object obj, long j) {
        zzik zzikVarZzc = zzc(obj, j);
        if (zzikVarZzc.zzei()) {
            return zzikVarZzc;
        }
        int size = zzikVarZzc.size();
        zzik zzikVarZzan = zzikVarZzc.zzan(size == 0 ? 10 : size << 1);
        zzla.zza(obj, j, zzikVarZzan);
        return zzikVarZzan;
    }

    @Override // com.google.android.gms.internal.vision.zzix
    final void zzb(Object obj, long j) {
        zzc(obj, j).zzej();
    }

    @Override // com.google.android.gms.internal.vision.zzix
    final <E> void zza(Object obj, Object obj2, long j) {
        zzik zzikVarZzc = zzc(obj, j);
        zzik zzikVarZzc2 = zzc(obj2, j);
        int size = zzikVarZzc.size();
        int size2 = zzikVarZzc2.size();
        if (size > 0 && size2 > 0) {
            if (!zzikVarZzc.zzei()) {
                zzikVarZzc = zzikVarZzc.zzan(size2 + size);
            }
            zzikVarZzc.addAll(zzikVarZzc2);
        }
        if (size > 0) {
            zzikVarZzc2 = zzikVarZzc;
        }
        zzla.zza(obj, j, zzikVarZzc2);
    }

    private static <E> zzik<E> zzc(Object obj, long j) {
        return (zzik) zzla.zzp(obj, j);
    }
}
