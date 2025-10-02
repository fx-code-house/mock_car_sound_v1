package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzee<T> implements zzec<T>, Serializable {
    private final zzec<T> zza;
    private volatile transient boolean zzb;

    @NullableDecl
    private transient T zzc;

    zzee(zzec<T> zzecVar) {
        this.zza = (zzec) zzeb.zza(zzecVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzec
    public final T zza() {
        if (!this.zzb) {
            synchronized (this) {
                if (!this.zzb) {
                    T tZza = this.zza.zza();
                    this.zzc = tZza;
                    this.zzb = true;
                    return tZza;
                }
            }
        }
        return this.zzc;
    }

    public final String toString() {
        Object string;
        if (this.zzb) {
            String strValueOf = String.valueOf(this.zzc);
            string = new StringBuilder(String.valueOf(strValueOf).length() + 25).append("<supplier that returned ").append(strValueOf).append(">").toString();
        } else {
            string = this.zza;
        }
        String strValueOf2 = String.valueOf(string);
        return new StringBuilder(String.valueOf(strValueOf2).length() + 19).append("Suppliers.memoize(").append(strValueOf2).append(")").toString();
    }
}
