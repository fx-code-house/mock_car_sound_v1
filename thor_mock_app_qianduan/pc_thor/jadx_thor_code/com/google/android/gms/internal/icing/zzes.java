package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzes extends zzer {
    private zzes() {
        super();
    }

    @Override // com.google.android.gms.internal.icing.zzer
    final void zza(Object obj, long j) {
        zzb(obj, j).zzai();
    }

    @Override // com.google.android.gms.internal.icing.zzer
    final <E> void zza(Object obj, Object obj2, long j) {
        zzee zzeeVarZzb = zzb(obj, j);
        zzee zzeeVarZzb2 = zzb(obj2, j);
        int size = zzeeVarZzb.size();
        int size2 = zzeeVarZzb2.size();
        if (size > 0 && size2 > 0) {
            if (!zzeeVarZzb.zzah()) {
                zzeeVarZzb = zzeeVarZzb.zzj(size2 + size);
            }
            zzeeVarZzb.addAll(zzeeVarZzb2);
        }
        if (size > 0) {
            zzeeVarZzb2 = zzeeVarZzb;
        }
        zzgs.zza(obj, j, zzeeVarZzb2);
    }

    private static <E> zzee<E> zzb(Object obj, long j) {
        return (zzee) zzgs.zzo(obj, j);
    }
}
