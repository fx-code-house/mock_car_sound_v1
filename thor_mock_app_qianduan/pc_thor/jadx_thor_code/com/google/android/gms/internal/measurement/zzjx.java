package com.google.android.gms.internal.measurement;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjx {
    private static final zzjx zza = new zzjx();
    private final ConcurrentMap<Class<?>, zzkb<?>> zzc = new ConcurrentHashMap();
    private final zzka zzb = new zziw();

    public static zzjx zza() {
        return zza;
    }

    public final <T> zzkb<T> zza(Class<T> cls) {
        zzia.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzkb<T> zzkbVar = (zzkb) this.zzc.get(cls);
        if (zzkbVar != null) {
            return zzkbVar;
        }
        zzkb<T> zzkbVarZza = this.zzb.zza(cls);
        zzia.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzia.zza(zzkbVarZza, "schema");
        zzkb<T> zzkbVar2 = (zzkb) this.zzc.putIfAbsent(cls, zzkbVarZza);
        return zzkbVar2 != null ? zzkbVar2 : zzkbVarZza;
    }

    public final <T> zzkb<T> zza(T t) {
        return zza((Class) t.getClass());
    }

    private zzjx() {
    }
}
