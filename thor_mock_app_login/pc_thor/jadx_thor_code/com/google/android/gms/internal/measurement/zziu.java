package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zziu extends zzit {
    private zziu() {
        super();
    }

    @Override // com.google.android.gms.internal.measurement.zzit
    final <L> List<L> zza(Object obj, long j) {
        zzig zzigVarZzc = zzc(obj, j);
        if (zzigVarZzc.zza()) {
            return zzigVarZzc;
        }
        int size = zzigVarZzc.size();
        zzig zzigVarZza = zzigVarZzc.zza(size == 0 ? 10 : size << 1);
        zzkz.zza(obj, j, zzigVarZza);
        return zzigVarZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzit
    final void zzb(Object obj, long j) {
        zzc(obj, j).i_();
    }

    @Override // com.google.android.gms.internal.measurement.zzit
    final <E> void zza(Object obj, Object obj2, long j) {
        zzig zzigVarZzc = zzc(obj, j);
        zzig zzigVarZzc2 = zzc(obj2, j);
        int size = zzigVarZzc.size();
        int size2 = zzigVarZzc2.size();
        if (size > 0 && size2 > 0) {
            if (!zzigVarZzc.zza()) {
                zzigVarZzc = zzigVarZzc.zza(size2 + size);
            }
            zzigVarZzc.addAll(zzigVarZzc2);
        }
        if (size > 0) {
            zzigVarZzc2 = zzigVarZzc;
        }
        zzkz.zza(obj, j, zzigVarZzc2);
    }

    private static <E> zzig<E> zzc(Object obj, long j) {
        return (zzig) zzkz.zzf(obj, j);
    }
}
