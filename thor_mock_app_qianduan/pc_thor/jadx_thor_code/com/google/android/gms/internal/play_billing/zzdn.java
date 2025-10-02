package com.google.android.gms.internal.play_billing;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzdn {
    private static final zzdn zza = new zzdn();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzdq zzb = new zzcx();

    private zzdn() {
    }

    public static zzdn zza() {
        return zza;
    }

    public final zzdp zzb(Class cls) {
        zzcg.zzc(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzdp zzdpVarZza = (zzdp) this.zzc.get(cls);
        if (zzdpVarZza == null) {
            zzdpVarZza = this.zzb.zza(cls);
            zzcg.zzc(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
            zzdp zzdpVar = (zzdp) this.zzc.putIfAbsent(cls, zzdpVarZza);
            if (zzdpVar != null) {
                return zzdpVar;
            }
        }
        return zzdpVarZza;
    }
}
