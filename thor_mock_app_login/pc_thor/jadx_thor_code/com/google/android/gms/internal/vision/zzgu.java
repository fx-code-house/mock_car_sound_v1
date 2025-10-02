package com.google.android.gms.internal.vision;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgu implements Comparator<zzgs> {
    zzgu() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzgs zzgsVar, zzgs zzgsVar2) {
        zzgs zzgsVar3 = zzgsVar;
        zzgs zzgsVar4 = zzgsVar2;
        zzhb zzhbVar = (zzhb) zzgsVar3.iterator();
        zzhb zzhbVar2 = (zzhb) zzgsVar4.iterator();
        while (zzhbVar.hasNext() && zzhbVar2.hasNext()) {
            int iCompare = Integer.compare(zzgs.zza(zzhbVar.nextByte()), zzgs.zza(zzhbVar2.nextByte()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzgsVar3.size(), zzgsVar4.size());
    }
}
