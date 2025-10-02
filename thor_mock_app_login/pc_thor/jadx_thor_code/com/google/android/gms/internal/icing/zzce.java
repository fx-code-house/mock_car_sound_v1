package com.google.android.gms.internal.icing;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzce<T> implements zzcc<T>, Serializable {

    @NullableDecl
    private transient T value;
    private final zzcc<T> zzdw;
    private volatile transient boolean zzdx;

    zzce(zzcc<T> zzccVar) {
        this.zzdw = (zzcc) zzca.checkNotNull(zzccVar);
    }

    @Override // com.google.android.gms.internal.icing.zzcc
    public final T get() {
        if (!this.zzdx) {
            synchronized (this) {
                if (!this.zzdx) {
                    T t = this.zzdw.get();
                    this.value = t;
                    this.zzdx = true;
                    return t;
                }
            }
        }
        return this.value;
    }

    public final String toString() {
        Object string;
        if (this.zzdx) {
            String strValueOf = String.valueOf(this.value);
            string = new StringBuilder(String.valueOf(strValueOf).length() + 25).append("<supplier that returned ").append(strValueOf).append(">").toString();
        } else {
            string = this.zzdw;
        }
        String strValueOf2 = String.valueOf(string);
        return new StringBuilder(String.valueOf(strValueOf2).length() + 19).append("Suppliers.memoize(").append(strValueOf2).append(")").toString();
    }
}
