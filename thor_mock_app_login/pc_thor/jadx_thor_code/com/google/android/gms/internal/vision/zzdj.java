package com.google.android.gms.internal.vision;

import java.io.Serializable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdj<T> implements zzdf<T>, Serializable {

    @NullableDecl
    private final T zzmd;

    zzdj(@NullableDecl T t) {
        this.zzmd = t;
    }

    @Override // com.google.android.gms.internal.vision.zzdf
    public final T get() {
        return this.zzmd;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzdj) {
            return zzcz.equal(this.zzmd, ((zzdj) obj).zzmd);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzmd});
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzmd);
        return new StringBuilder(String.valueOf(strValueOf).length() + 22).append("Suppliers.ofInstance(").append(strValueOf).append(")").toString();
    }
}
