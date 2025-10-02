package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzgb<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzfz zznx;
    private Iterator<Map.Entry<K, V>> zzny;

    private zzgb(zzfz zzfzVar) {
        this.zznx = zzfzVar;
        this.pos = zzfzVar.zzns.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zznx.zzns.size()) || zzdi().hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Map.Entry<K, V>> zzdi() {
        if (this.zzny == null) {
            this.zzny = this.zznx.zznv.entrySet().iterator();
        }
        return this.zzny;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        if (zzdi().hasNext()) {
            return zzdi().next();
        }
        List list = this.zznx.zzns;
        int i = this.pos - 1;
        this.pos = i;
        return (Map.Entry) list.get(i);
    }

    /* synthetic */ zzgb(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
