package com.google.android.gms.internal.play_billing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzcp extends zzct {
    private static final Class zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzcp() {
        super(null);
    }

    /* synthetic */ zzcp(zzco zzcoVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.play_billing.zzct
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzeq.zzf(obj, j);
        if (list instanceof zzcn) {
            objUnmodifiableList = ((zzcn) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzdm) && (list instanceof zzcf)) {
                zzcf zzcfVar = (zzcf) list;
                if (zzcfVar.zzc()) {
                    zzcfVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzeq.zzs(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.play_billing.zzct
    final void zzb(Object obj, Object obj2, long j) {
        zzcm zzcmVar;
        List list = (List) zzeq.zzf(obj2, j);
        int size = list.size();
        List listZzd = (List) zzeq.zzf(obj, j);
        if (listZzd.isEmpty()) {
            listZzd = listZzd instanceof zzcn ? new zzcm(size) : ((listZzd instanceof zzdm) && (listZzd instanceof zzcf)) ? ((zzcf) listZzd).zzd(size) : new ArrayList(size);
            zzeq.zzs(obj, j, listZzd);
        } else {
            if (zza.isAssignableFrom(listZzd.getClass())) {
                ArrayList arrayList = new ArrayList(listZzd.size() + size);
                arrayList.addAll(listZzd);
                zzeq.zzs(obj, j, arrayList);
                zzcmVar = arrayList;
            } else if (listZzd instanceof zzel) {
                zzcm zzcmVar2 = new zzcm(listZzd.size() + size);
                zzcmVar2.addAll(zzcmVar2.size(), (zzel) listZzd);
                zzeq.zzs(obj, j, zzcmVar2);
                zzcmVar = zzcmVar2;
            } else if ((listZzd instanceof zzdm) && (listZzd instanceof zzcf)) {
                zzcf zzcfVar = (zzcf) listZzd;
                if (!zzcfVar.zzc()) {
                    listZzd = zzcfVar.zzd(listZzd.size() + size);
                    zzeq.zzs(obj, j, listZzd);
                }
            }
            listZzd = zzcmVar;
        }
        int size2 = listZzd.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            listZzd.addAll(list);
        }
        if (size2 > 0) {
            list = listZzd;
        }
        zzeq.zzs(obj, j, list);
    }
}
