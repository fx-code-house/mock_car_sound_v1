package com.google.android.gms.internal.clearcut;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
final class zzea {
    private static final zzea zznc = new zzea();
    private final zzeg zznd;
    private final ConcurrentMap<Class<?>, zzef<?>> zzne = new ConcurrentHashMap();

    private zzea() {
        String[] strArr = {"com.google.protobuf.AndroidProto3SchemaFactory"};
        zzeg zzegVarZzk = null;
        for (int i = 0; i <= 0; i++) {
            zzegVarZzk = zzk(strArr[0]);
            if (zzegVarZzk != null) {
                break;
            }
        }
        this.zznd = zzegVarZzk == null ? new zzdd() : zzegVarZzk;
    }

    public static zzea zzcm() {
        return zznc;
    }

    private static zzeg zzk(String str) {
        try {
            return (zzeg) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public final <T> zzef<T> zze(Class<T> cls) {
        zzci.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzef<T> zzefVar = (zzef) this.zzne.get(cls);
        if (zzefVar != null) {
            return zzefVar;
        }
        zzef<T> zzefVarZzd = this.zznd.zzd(cls);
        zzci.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzci.zza(zzefVarZzd, "schema");
        zzef<T> zzefVar2 = (zzef) this.zzne.putIfAbsent(cls, zzefVarZzd);
        return zzefVar2 != null ? zzefVar2 : zzefVarZzd;
    }

    public final <T> zzef<T> zzp(T t) {
        return zze(t.getClass());
    }
}
