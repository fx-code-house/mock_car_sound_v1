package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzga extends zzgg {
    private final /* synthetic */ zzfz zznx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzga(zzfz zzfzVar) {
        super(zzfzVar, null);
        this.zznx = zzfzVar;
    }

    @Override // com.google.android.gms.internal.icing.zzgg, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzgb(this.zznx, null);
    }

    /* synthetic */ zzga(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
