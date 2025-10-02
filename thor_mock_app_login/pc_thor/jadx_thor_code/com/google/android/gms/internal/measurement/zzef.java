package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzef {
    public static <T> zzec<T> zza(zzec<T> zzecVar) {
        return ((zzecVar instanceof zzeh) || (zzecVar instanceof zzee)) ? zzecVar : zzecVar instanceof Serializable ? new zzee(zzecVar) : new zzeh(zzecVar);
    }

    public static <T> zzec<T> zza(@NullableDecl T t) {
        return new zzeg(t);
    }
}
