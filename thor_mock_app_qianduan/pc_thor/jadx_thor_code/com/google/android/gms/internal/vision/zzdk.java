package com.google.android.gms.internal.vision;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdk<T> implements zzdf<T> {

    @NullableDecl
    private T value;
    private volatile zzdf<T> zzmb;
    private volatile boolean zzmc;

    zzdk(zzdf<T> zzdfVar) {
        this.zzmb = (zzdf) zzde.checkNotNull(zzdfVar);
    }

    @Override // com.google.android.gms.internal.vision.zzdf
    public final T get() {
        if (!this.zzmc) {
            synchronized (this) {
                if (!this.zzmc) {
                    T t = this.zzmb.get();
                    this.value = t;
                    this.zzmc = true;
                    this.zzmb = null;
                    return t;
                }
            }
        }
        return this.value;
    }

    public final String toString() {
        Object string = this.zzmb;
        if (string == null) {
            String strValueOf = String.valueOf(this.value);
            string = new StringBuilder(String.valueOf(strValueOf).length() + 25).append("<supplier that returned ").append(strValueOf).append(">").toString();
        }
        String strValueOf2 = String.valueOf(string);
        return new StringBuilder(String.valueOf(strValueOf2).length() + 19).append("Suppliers.memoize(").append(strValueOf2).append(")").toString();
    }
}
