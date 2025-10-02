package com.google.android.gms.internal.fido;

import java.io.Serializable;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzbv extends zzbr implements Serializable {
    static final zzbv zza = new zzbv();

    private zzbv() {
    }

    @Override // com.google.android.gms.internal.fido.zzbr, java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        Comparable comparable = (Comparable) obj;
        Comparable comparable2 = (Comparable) obj2;
        comparable.getClass();
        if (comparable == comparable2) {
            return 0;
        }
        return comparable2.compareTo(comparable);
    }

    public final String toString() {
        return "Ordering.natural().reverse()";
    }

    @Override // com.google.android.gms.internal.fido.zzbr
    public final zzbr zza() {
        return zzbp.zza;
    }
}
