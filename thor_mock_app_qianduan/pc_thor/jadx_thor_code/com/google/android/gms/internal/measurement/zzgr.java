package com.google.android.gms.internal.measurement;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgr implements Comparator<zzgp> {
    zzgr() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzgp zzgpVar, zzgp zzgpVar2) {
        zzgp zzgpVar3 = zzgpVar;
        zzgp zzgpVar4 = zzgpVar2;
        zzgy zzgyVar = (zzgy) zzgpVar3.iterator();
        zzgy zzgyVar2 = (zzgy) zzgpVar4.iterator();
        while (zzgyVar.hasNext() && zzgyVar2.hasNext()) {
            int iCompare = Integer.compare(zzgp.zzb(zzgyVar.zza()), zzgp.zzb(zzgyVar2.zza()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzgpVar3.zza(), zzgpVar4.zza());
    }
}
