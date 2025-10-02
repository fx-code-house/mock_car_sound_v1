package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjj implements zzjg {
    zzjj() {
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final Map<?, ?> zzn(Object obj) {
        return (zzjh) obj;
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final zzje<?, ?> zzs(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final Map<?, ?> zzo(Object obj) {
        return (zzjh) obj;
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final boolean zzp(Object obj) {
        return !((zzjh) obj).isMutable();
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final Object zzq(Object obj) {
        ((zzjh) obj).zzej();
        return obj;
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final Object zzr(Object obj) {
        return zzjh.zzhx().zzhy();
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final Object zzc(Object obj, Object obj2) {
        zzjh zzjhVarZzhy = (zzjh) obj;
        zzjh zzjhVar = (zzjh) obj2;
        if (!zzjhVar.isEmpty()) {
            if (!zzjhVarZzhy.isMutable()) {
                zzjhVarZzhy = zzjhVarZzhy.zzhy();
            }
            zzjhVarZzhy.zza(zzjhVar);
        }
        return zzjhVarZzhy;
    }

    @Override // com.google.android.gms.internal.vision.zzjg
    public final int zzb(int i, Object obj, Object obj2) {
        zzjh zzjhVar = (zzjh) obj;
        if (zzjhVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzjhVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
