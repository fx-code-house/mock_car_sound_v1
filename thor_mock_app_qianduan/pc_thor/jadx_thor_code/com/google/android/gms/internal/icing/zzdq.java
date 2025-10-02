package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzdq extends zzdn<zzdx.zzc> {
    zzdq() {
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    final boolean zze(zzfh zzfhVar) {
        return zzfhVar instanceof zzdx.zzd;
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    final zzds<zzdx.zzc> zzd(Object obj) {
        return ((zzdx.zzd) obj).zzkj;
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    final zzds<zzdx.zzc> zze(Object obj) {
        zzdx.zzd zzdVar = (zzdx.zzd) obj;
        if (zzdVar.zzkj.isImmutable()) {
            zzdVar.zzkj = (zzds) zzdVar.zzkj.clone();
        }
        return zzdVar.zzkj;
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    final void zzf(Object obj) {
        zzd(obj).zzai();
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    final int zza(Map.Entry<?, ?> entry) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    final void zza(zzhg zzhgVar, Map.Entry<?, ?> entry) throws IOException {
        throw new NoSuchMethodError();
    }
}
