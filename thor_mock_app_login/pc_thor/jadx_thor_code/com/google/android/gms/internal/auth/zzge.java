package com.google.android.gms.internal.auth;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* loaded from: classes2.dex */
final class zzge {
    private static final zzge zza = new zzge();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzgi zzb = new zzfo();

    private zzge() {
    }

    public static zzge zza() {
        return zza;
    }

    public final zzgh zzb(Class cls) {
        zzez.zzf(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzgh zzghVarZza = (zzgh) this.zzc.get(cls);
        if (zzghVarZza == null) {
            zzghVarZza = this.zzb.zza(cls);
            zzez.zzf(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
            zzez.zzf(zzghVarZza, "schema");
            zzgh zzghVar = (zzgh) this.zzc.putIfAbsent(cls, zzghVarZza);
            if (zzghVar != null) {
                return zzghVar;
            }
        }
        return zzghVarZza;
    }
}
