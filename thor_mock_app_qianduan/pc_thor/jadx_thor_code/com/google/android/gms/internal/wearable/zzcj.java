package com.google.android.gms.internal.wearable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzcj extends zzcl {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzcj() {
        super(null);
    }

    /* synthetic */ zzcj(zzci zzciVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.wearable.zzcl
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzeg.zzn(obj, j);
        if (list instanceof zzch) {
            objUnmodifiableList = ((zzch) list).zzi();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzde) && (list instanceof zzbz)) {
                zzbz zzbzVar = (zzbz) list;
                if (zzbzVar.zza()) {
                    zzbzVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzeg.zzo(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.wearable.zzcl
    final <E> void zzb(Object obj, Object obj2, long j) {
        zzcg zzcgVar;
        List list = (List) zzeg.zzn(obj2, j);
        int size = list.size();
        List listZze = (List) zzeg.zzn(obj, j);
        if (listZze.isEmpty()) {
            listZze = listZze instanceof zzch ? new zzcg(size) : ((listZze instanceof zzde) && (listZze instanceof zzbz)) ? ((zzbz) listZze).zze(size) : new ArrayList(size);
            zzeg.zzo(obj, j, listZze);
        } else {
            if (zza.isAssignableFrom(listZze.getClass())) {
                ArrayList arrayList = new ArrayList(listZze.size() + size);
                arrayList.addAll(listZze);
                zzeg.zzo(obj, j, arrayList);
                zzcgVar = arrayList;
            } else if (listZze instanceof zzeb) {
                zzcg zzcgVar2 = new zzcg(listZze.size() + size);
                zzcgVar2.addAll(zzcgVar2.size(), (zzeb) listZze);
                zzeg.zzo(obj, j, zzcgVar2);
                zzcgVar = zzcgVar2;
            } else if ((listZze instanceof zzde) && (listZze instanceof zzbz)) {
                zzbz zzbzVar = (zzbz) listZze;
                if (!zzbzVar.zza()) {
                    listZze = zzbzVar.zze(listZze.size() + size);
                    zzeg.zzo(obj, j, listZze);
                }
            }
            listZze = zzcgVar;
        }
        int size2 = listZze.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            listZze.addAll(list);
        }
        if (size2 > 0) {
            list = listZze;
        }
        zzeg.zzo(obj, j, list);
    }
}
