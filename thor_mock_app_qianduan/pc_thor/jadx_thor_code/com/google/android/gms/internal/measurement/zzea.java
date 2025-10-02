package com.google.android.gms.internal.measurement;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzea<T> extends zzdy<T> {
    private final T zza;

    zzea(T t) {
        this.zza = t;
    }

    @Override // com.google.android.gms.internal.measurement.zzdy
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdy
    public final T zzb() {
        return this.zza;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzea) {
            return this.zza.equals(((zzea) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode() + 1502476572;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        return new StringBuilder(String.valueOf(strValueOf).length() + 13).append("Optional.of(").append(strValueOf).append(")").toString();
    }
}
