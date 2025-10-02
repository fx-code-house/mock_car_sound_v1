package com.google.android.gms.internal.vision;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdd<T> extends zzcy<T> {
    private final T zzma;

    zzdd(T t) {
        this.zzma = t;
    }

    @Override // com.google.android.gms.internal.vision.zzcy
    public final boolean isPresent() {
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzcy
    public final T get() {
        return this.zzma;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzdd) {
            return this.zzma.equals(((zzdd) obj).zzma);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzma.hashCode() + 1502476572;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzma);
        return new StringBuilder(String.valueOf(strValueOf).length() + 13).append("Optional.of(").append(strValueOf).append(")").toString();
    }
}
