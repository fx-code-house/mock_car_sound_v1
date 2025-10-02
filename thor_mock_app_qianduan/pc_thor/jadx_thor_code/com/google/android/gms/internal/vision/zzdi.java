package com.google.android.gms.internal.vision;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzdi {
    public static <T> zzdf<T> zza(zzdf<T> zzdfVar) {
        return ((zzdfVar instanceof zzdk) || (zzdfVar instanceof zzdh)) ? zzdfVar : zzdfVar instanceof Serializable ? new zzdh(zzdfVar) : new zzdk(zzdfVar);
    }

    public static <T> zzdf<T> zzd(@NullableDecl T t) {
        return new zzdj(t);
    }
}
