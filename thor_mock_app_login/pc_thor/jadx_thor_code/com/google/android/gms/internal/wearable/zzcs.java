package com.google.android.gms.internal.wearable;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzcs {
    zzcs() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzcr zzcrVar = (zzcr) obj;
        if (zzcrVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzcrVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }

    public static final Object zzb(Object obj, Object obj2) {
        zzcr zzcrVarZzc = (zzcr) obj;
        zzcr zzcrVar = (zzcr) obj2;
        if (!zzcrVar.isEmpty()) {
            if (!zzcrVarZzc.zze()) {
                zzcrVarZzc = zzcrVarZzc.zzc();
            }
            zzcrVarZzc.zzb(zzcrVar);
        }
        return zzcrVarZzc;
    }
}
