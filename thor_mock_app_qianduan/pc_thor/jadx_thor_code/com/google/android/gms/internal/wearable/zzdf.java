package com.google.android.gms.internal.wearable;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzdf {
    private static final zzdf zza = new zzdf();
    private final ConcurrentMap<Class<?>, zzdi<?>> zzc = new ConcurrentHashMap();
    private final zzdj zzb = new zzcp();

    private zzdf() {
    }

    public static zzdf zza() {
        return zza;
    }

    public final <T> zzdi<T> zzb(Class<T> cls) {
        zzca.zzb(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzdi<T> zzdiVarZza = (zzdi) this.zzc.get(cls);
        if (zzdiVarZza == null) {
            zzdiVarZza = this.zzb.zza(cls);
            zzca.zzb(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
            zzca.zzb(zzdiVarZza, "schema");
            zzdi<T> zzdiVar = (zzdi) this.zzc.putIfAbsent(cls, zzdiVarZza);
            if (zzdiVar != null) {
                return zzdiVar;
            }
        }
        return zzdiVarZza;
    }
}
