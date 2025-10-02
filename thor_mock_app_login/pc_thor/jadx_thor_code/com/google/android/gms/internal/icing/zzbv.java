package com.google.android.gms.internal.icing;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzbv<T> extends zzbx<T> {
    static final zzbv<Object> zzdt = new zzbv<>();

    private zzbv() {
    }

    public final boolean equals(@NullableDecl Object obj) {
        return obj == this;
    }

    public final int hashCode() {
        return 2040732332;
    }

    @Override // com.google.android.gms.internal.icing.zzbx
    public final boolean isPresent() {
        return false;
    }

    public final String toString() {
        return "Optional.absent()";
    }

    @Override // com.google.android.gms.internal.icing.zzbx
    public final T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }
}
