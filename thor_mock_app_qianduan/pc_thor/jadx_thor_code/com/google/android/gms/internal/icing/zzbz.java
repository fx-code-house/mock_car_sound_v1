package com.google.android.gms.internal.icing;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzbz<T> extends zzbx<T> {
    private final T zzdv;

    zzbz(T t) {
        this.zzdv = t;
    }

    @Override // com.google.android.gms.internal.icing.zzbx
    public final boolean isPresent() {
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzbx
    public final T get() {
        return this.zzdv;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzbz) {
            return this.zzdv.equals(((zzbz) obj).zzdv);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzdv.hashCode() + 1502476572;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzdv);
        return new StringBuilder(String.valueOf(strValueOf).length() + 13).append("Optional.of(").append(strValueOf).append(")").toString();
    }
}
