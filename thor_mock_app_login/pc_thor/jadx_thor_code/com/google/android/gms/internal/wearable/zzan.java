package com.google.android.gms.internal.wearable;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzan implements Comparator<zzau> {
    zzan() {
    }

    @Override // java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(zzau zzauVar, zzau zzauVar2) {
        zzau zzauVar3 = zzauVar;
        zzau zzauVar4 = zzauVar2;
        zzam zzamVar = new zzam(zzauVar3);
        zzam zzamVar2 = new zzam(zzauVar4);
        while (zzamVar.hasNext() && zzamVar2.hasNext()) {
            int iCompare = Integer.compare(zzamVar.zza() & 255, zzamVar2.zza() & 255);
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzauVar3.zzc(), zzauVar4.zzc());
    }
}
