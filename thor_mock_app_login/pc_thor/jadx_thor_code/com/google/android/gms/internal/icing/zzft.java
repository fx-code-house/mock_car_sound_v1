package com.google.android.gms.internal.icing;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzft {
    private static final zzft zznk = new zzft();
    private final ConcurrentMap<Class<?>, zzfu<?>> zznm = new ConcurrentHashMap();
    private final zzfx zznl = new zzeu();

    public static zzft zzcv() {
        return zznk;
    }

    public final <T> zzfu<T> zze(Class<T> cls) {
        zzeb.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzfu<T> zzfuVar = (zzfu) this.zznm.get(cls);
        if (zzfuVar != null) {
            return zzfuVar;
        }
        zzfu<T> zzfuVarZzd = this.zznl.zzd(cls);
        zzeb.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzeb.zza(zzfuVarZzd, "schema");
        zzfu<T> zzfuVar2 = (zzfu) this.zznm.putIfAbsent(cls, zzfuVarZzd);
        return zzfuVar2 != null ? zzfuVar2 : zzfuVarZzd;
    }

    public final <T> zzfu<T> zzo(T t) {
        return zze(t.getClass());
    }

    private zzft() {
    }
}
