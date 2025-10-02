package com.google.android.gms.internal.play_billing;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzda {
    zzda() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzcz zzczVar = (zzcz) obj;
        if (zzczVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzczVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }

    public static final Object zzb(Object obj, Object obj2) {
        zzcz zzczVarZzb = (zzcz) obj;
        zzcz zzczVar = (zzcz) obj2;
        if (!zzczVar.isEmpty()) {
            if (!zzczVarZzb.zze()) {
                zzczVarZzb = zzczVarZzb.zzb();
            }
            zzczVarZzb.zzd(zzczVar);
        }
        return zzczVarZzb;
    }
}
