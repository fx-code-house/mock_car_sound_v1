package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zziv extends zzit {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zziv() {
        super();
    }

    @Override // com.google.android.gms.internal.measurement.zzit
    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    @Override // com.google.android.gms.internal.measurement.zzit
    final void zzb(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzkz.zzf(obj, j);
        if (list instanceof zziq) {
            objUnmodifiableList = ((zziq) list).h_();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzjv) && (list instanceof zzig)) {
                zzig zzigVar = (zzig) list;
                if (zzigVar.zza()) {
                    zzigVar.i_();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzkz.zza(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <L> List<L> zza(Object obj, long j, int i) {
        zzir zzirVar;
        List<L> arrayList;
        List<L> listZzc = zzc(obj, j);
        if (listZzc.isEmpty()) {
            if (listZzc instanceof zziq) {
                arrayList = new zzir(i);
            } else if ((listZzc instanceof zzjv) && (listZzc instanceof zzig)) {
                arrayList = ((zzig) listZzc).zza(i);
            } else {
                arrayList = new ArrayList<>(i);
            }
            zzkz.zza(obj, j, arrayList);
            return arrayList;
        }
        if (zza.isAssignableFrom(listZzc.getClass())) {
            ArrayList arrayList2 = new ArrayList(listZzc.size() + i);
            arrayList2.addAll(listZzc);
            zzkz.zza(obj, j, arrayList2);
            zzirVar = arrayList2;
        } else if (listZzc instanceof zzku) {
            zzir zzirVar2 = new zzir(listZzc.size() + i);
            zzirVar2.addAll((zzku) listZzc);
            zzkz.zza(obj, j, zzirVar2);
            zzirVar = zzirVar2;
        } else {
            if (!(listZzc instanceof zzjv) || !(listZzc instanceof zzig)) {
                return listZzc;
            }
            zzig zzigVar = (zzig) listZzc;
            if (zzigVar.zza()) {
                return listZzc;
            }
            zzig zzigVarZza = zzigVar.zza(listZzc.size() + i);
            zzkz.zza(obj, j, zzigVarZza);
            return zzigVarZza;
        }
        return zzirVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzit
    final <E> void zza(Object obj, Object obj2, long j) {
        List listZzc = zzc(obj2, j);
        List listZza = zza(obj, j, listZzc.size());
        int size = listZza.size();
        int size2 = listZzc.size();
        if (size > 0 && size2 > 0) {
            listZza.addAll(listZzc);
        }
        if (size > 0) {
            listZzc = listZza;
        }
        zzkz.zza(obj, j, listZzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzkz.zzf(obj, j);
    }
}
