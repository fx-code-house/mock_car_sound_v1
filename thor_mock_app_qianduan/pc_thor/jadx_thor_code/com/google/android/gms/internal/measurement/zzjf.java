package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjf implements zzjc {
    zzjf() {
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final Map<?, ?> zza(Object obj) {
        return (zzjd) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final zzja<?, ?> zzf(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final Map<?, ?> zzb(Object obj) {
        return (zzjd) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final boolean zzc(Object obj) {
        return !((zzjd) obj).zzd();
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final Object zzd(Object obj) {
        ((zzjd) obj).zzc();
        return obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final Object zze(Object obj) {
        return zzjd.zza().zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final Object zza(Object obj, Object obj2) {
        zzjd zzjdVarZzb = (zzjd) obj;
        zzjd zzjdVar = (zzjd) obj2;
        if (!zzjdVar.isEmpty()) {
            if (!zzjdVarZzb.zzd()) {
                zzjdVarZzb = zzjdVarZzb.zzb();
            }
            zzjdVarZzb.zza(zzjdVar);
        }
        return zzjdVarZzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    public final int zza(int i, Object obj, Object obj2) {
        zzjd zzjdVar = (zzjd) obj;
        if (zzjdVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzjdVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
