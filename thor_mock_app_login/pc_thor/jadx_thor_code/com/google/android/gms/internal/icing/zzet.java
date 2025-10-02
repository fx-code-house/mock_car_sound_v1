package com.google.android.gms.internal.icing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzet extends zzer {
    private static final Class<?> zzmc = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzet() {
        super();
    }

    @Override // com.google.android.gms.internal.icing.zzer
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzgs.zzo(obj, j);
        if (list instanceof zzeo) {
            objUnmodifiableList = ((zzeo) list).zzce();
        } else {
            if (zzmc.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzfq) && (list instanceof zzee)) {
                zzee zzeeVar = (zzee) list;
                if (zzeeVar.zzah()) {
                    zzeeVar.zzai();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzgs.zza(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.icing.zzer
    final <E> void zza(Object obj, Object obj2, long j) {
        zzep zzepVar;
        List listZzc = zzc(obj2, j);
        int size = listZzc.size();
        List listZzc2 = zzc(obj, j);
        if (listZzc2.isEmpty()) {
            if (listZzc2 instanceof zzeo) {
                listZzc2 = new zzep(size);
            } else if ((listZzc2 instanceof zzfq) && (listZzc2 instanceof zzee)) {
                listZzc2 = ((zzee) listZzc2).zzj(size);
            } else {
                listZzc2 = new ArrayList(size);
            }
            zzgs.zza(obj, j, listZzc2);
        } else {
            if (zzmc.isAssignableFrom(listZzc2.getClass())) {
                ArrayList arrayList = new ArrayList(listZzc2.size() + size);
                arrayList.addAll(listZzc2);
                zzgs.zza(obj, j, arrayList);
                zzepVar = arrayList;
            } else if (listZzc2 instanceof zzgr) {
                zzep zzepVar2 = new zzep(listZzc2.size() + size);
                zzepVar2.addAll((zzgr) listZzc2);
                zzgs.zza(obj, j, zzepVar2);
                zzepVar = zzepVar2;
            } else if ((listZzc2 instanceof zzfq) && (listZzc2 instanceof zzee)) {
                zzee zzeeVar = (zzee) listZzc2;
                if (!zzeeVar.zzah()) {
                    listZzc2 = zzeeVar.zzj(listZzc2.size() + size);
                    zzgs.zza(obj, j, listZzc2);
                }
            }
            listZzc2 = zzepVar;
        }
        int size2 = listZzc2.size();
        int size3 = listZzc.size();
        if (size2 > 0 && size3 > 0) {
            listZzc2.addAll(listZzc);
        }
        if (size2 > 0) {
            listZzc = listZzc2;
        }
        zzgs.zza(obj, j, listZzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzgs.zzo(obj, j);
    }
}
