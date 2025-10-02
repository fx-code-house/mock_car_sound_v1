package com.google.android.gms.internal.icing;

import java.io.Serializable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzcg<T> implements zzcc<T>, Serializable {

    @NullableDecl
    private final T zzea;

    zzcg(@NullableDecl T t) {
        this.zzea = t;
    }

    @Override // com.google.android.gms.internal.icing.zzcc
    public final T get() {
        return this.zzea;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof zzcg)) {
            return false;
        }
        T t = this.zzea;
        T t2 = ((zzcg) obj).zzea;
        if (t != t2) {
            return t != null && t.equals(t2);
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzea});
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzea);
        return new StringBuilder(String.valueOf(strValueOf).length() + 22).append("Suppliers.ofInstance(").append(strValueOf).append(")").toString();
    }
}
