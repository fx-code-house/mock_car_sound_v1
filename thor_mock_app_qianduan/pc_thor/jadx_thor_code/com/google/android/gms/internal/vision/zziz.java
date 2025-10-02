package com.google.android.gms.internal.vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zziz extends zzix {
    private static final Class<?> zzaac = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zziz() {
        super();
    }

    @Override // com.google.android.gms.internal.vision.zzix
    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    @Override // com.google.android.gms.internal.vision.zzix
    final void zzb(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzla.zzp(obj, j);
        if (list instanceof zziu) {
            objUnmodifiableList = ((zziu) list).zzht();
        } else {
            if (zzaac.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzjz) && (list instanceof zzik)) {
                zzik zzikVar = (zzik) list;
                if (zzikVar.zzei()) {
                    zzikVar.zzej();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzla.zza(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <L> List<L> zza(Object obj, long j, int i) {
        zziv zzivVar;
        List<L> arrayList;
        List<L> listZzd = zzd(obj, j);
        if (listZzd.isEmpty()) {
            if (listZzd instanceof zziu) {
                arrayList = new zziv(i);
            } else if ((listZzd instanceof zzjz) && (listZzd instanceof zzik)) {
                arrayList = ((zzik) listZzd).zzan(i);
            } else {
                arrayList = new ArrayList<>(i);
            }
            zzla.zza(obj, j, arrayList);
            return arrayList;
        }
        if (zzaac.isAssignableFrom(listZzd.getClass())) {
            ArrayList arrayList2 = new ArrayList(listZzd.size() + i);
            arrayList2.addAll(listZzd);
            zzla.zza(obj, j, arrayList2);
            zzivVar = arrayList2;
        } else if (listZzd instanceof zzkz) {
            zziv zzivVar2 = new zziv(listZzd.size() + i);
            zzivVar2.addAll((zzkz) listZzd);
            zzla.zza(obj, j, zzivVar2);
            zzivVar = zzivVar2;
        } else {
            if (!(listZzd instanceof zzjz) || !(listZzd instanceof zzik)) {
                return listZzd;
            }
            zzik zzikVar = (zzik) listZzd;
            if (zzikVar.zzei()) {
                return listZzd;
            }
            zzik zzikVarZzan = zzikVar.zzan(listZzd.size() + i);
            zzla.zza(obj, j, zzikVarZzan);
            return zzikVarZzan;
        }
        return zzivVar;
    }

    @Override // com.google.android.gms.internal.vision.zzix
    final <E> void zza(Object obj, Object obj2, long j) {
        List listZzd = zzd(obj2, j);
        List listZza = zza(obj, j, listZzd.size());
        int size = listZza.size();
        int size2 = listZzd.size();
        if (size > 0 && size2 > 0) {
            listZza.addAll(listZzd);
        }
        if (size > 0) {
            listZzd = listZza;
        }
        zzla.zza(obj, j, listZzd);
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzla.zzp(obj, j);
    }
}
