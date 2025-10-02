package com.google.android.gms.internal.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* loaded from: classes2.dex */
final class zzfg extends zzfk {
    private static final Class zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzfg() {
        super(null);
    }

    /* synthetic */ zzfg(zzff zzffVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.auth.zzfk
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzhi.zzf(obj, j);
        if (list instanceof zzfe) {
            objUnmodifiableList = ((zzfe) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzgd) && (list instanceof zzey)) {
                zzey zzeyVar = (zzey) list;
                if (zzeyVar.zzc()) {
                    zzeyVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzhi.zzp(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.auth.zzfk
    final void zzb(Object obj, Object obj2, long j) {
        zzfd zzfdVar;
        List list = (List) zzhi.zzf(obj2, j);
        int size = list.size();
        List listZzd = (List) zzhi.zzf(obj, j);
        if (listZzd.isEmpty()) {
            listZzd = listZzd instanceof zzfe ? new zzfd(size) : ((listZzd instanceof zzgd) && (listZzd instanceof zzey)) ? ((zzey) listZzd).zzd(size) : new ArrayList(size);
            zzhi.zzp(obj, j, listZzd);
        } else {
            if (zza.isAssignableFrom(listZzd.getClass())) {
                ArrayList arrayList = new ArrayList(listZzd.size() + size);
                arrayList.addAll(listZzd);
                zzhi.zzp(obj, j, arrayList);
                zzfdVar = arrayList;
            } else if (listZzd instanceof zzhd) {
                zzfd zzfdVar2 = new zzfd(listZzd.size() + size);
                zzfdVar2.addAll(zzfdVar2.size(), (zzhd) listZzd);
                zzhi.zzp(obj, j, zzfdVar2);
                zzfdVar = zzfdVar2;
            } else if ((listZzd instanceof zzgd) && (listZzd instanceof zzey)) {
                zzey zzeyVar = (zzey) listZzd;
                if (!zzeyVar.zzc()) {
                    listZzd = zzeyVar.zzd(listZzd.size() + size);
                    zzhi.zzp(obj, j, listZzd);
                }
            }
            listZzd = zzfdVar;
        }
        int size2 = listZzd.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            listZzd.addAll(list);
        }
        if (size2 > 0) {
            list = listZzd;
        }
        zzhi.zzp(obj, j, list);
    }
}
