package com.google.android.gms.internal.fido;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzcr implements Comparator {
    zzcr() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzcz zzczVar = (zzcz) obj;
        zzcz zzczVar2 = (zzcz) obj2;
        zzcq zzcqVar = new zzcq(zzczVar);
        zzcq zzcqVar2 = new zzcq(zzczVar2);
        while (zzcqVar.hasNext() && zzcqVar2.hasNext()) {
            int iCompareTo = Integer.valueOf(zzcqVar.zza() & 255).compareTo(Integer.valueOf(zzcqVar2.zza() & 255));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return Integer.valueOf(zzczVar.zzd()).compareTo(Integer.valueOf(zzczVar2.zzd()));
    }
}
