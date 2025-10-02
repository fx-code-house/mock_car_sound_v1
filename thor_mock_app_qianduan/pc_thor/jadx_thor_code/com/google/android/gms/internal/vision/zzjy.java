package com.google.android.gms.internal.vision;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjy {
    private static final zzjy zzabj = new zzjy();
    private final ConcurrentMap<Class<?>, zzkc<?>> zzabl = new ConcurrentHashMap();
    private final zzkf zzabk = new zzja();

    public static zzjy zzij() {
        return zzabj;
    }

    public final <T> zzkc<T> zzf(Class<T> cls) {
        zzie.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzkc<T> zzkcVar = (zzkc) this.zzabl.get(cls);
        if (zzkcVar != null) {
            return zzkcVar;
        }
        zzkc<T> zzkcVarZze = this.zzabk.zze(cls);
        zzie.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzie.zza(zzkcVarZze, "schema");
        zzkc<T> zzkcVar2 = (zzkc) this.zzabl.putIfAbsent(cls, zzkcVarZze);
        return zzkcVar2 != null ? zzkcVar2 : zzkcVarZze;
    }

    public final <T> zzkc<T> zzx(T t) {
        return zzf(t.getClass());
    }

    private zzjy() {
    }
}
