package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzeg<T> implements zzec<T>, Serializable {

    @NullableDecl
    private final T zza;

    zzeg(@NullableDecl T t) {
        this.zza = t;
    }

    @Override // com.google.android.gms.internal.measurement.zzec
    public final T zza() {
        return this.zza;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzeg) {
            return zzdz.zza(this.zza, ((zzeg) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        return new StringBuilder(String.valueOf(strValueOf).length() + 22).append("Suppliers.ofInstance(").append(strValueOf).append(")").toString();
    }
}
