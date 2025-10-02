package com.google.android.gms.internal.icing;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzcb {
    public static <T> zzcc<T> zza(zzcc<T> zzccVar) {
        return ((zzccVar instanceof zzcd) || (zzccVar instanceof zzce)) ? zzccVar : zzccVar instanceof Serializable ? new zzce(zzccVar) : new zzcd(zzccVar);
    }

    public static <T> zzcc<T> zzc(@NullableDecl T t) {
        return new zzcg(t);
    }
}
