package com.google.android.gms.internal.clearcut;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes2.dex */
final class zzek<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private Iterator<Map.Entry<K, V>> zzor;
    private final /* synthetic */ zzei zzos;

    private zzek(zzei zzeiVar) {
        this.zzos = zzeiVar;
        this.pos = zzeiVar.zzom.size();
    }

    /* synthetic */ zzek(zzei zzeiVar, zzej zzejVar) {
        this(zzeiVar);
    }

    private final Iterator<Map.Entry<K, V>> zzdw() {
        if (this.zzor == null) {
            this.zzor = this.zzos.zzop.entrySet().iterator();
        }
        return this.zzor;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zzos.zzom.size()) || zzdw().hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, V> next;
        if (zzdw().hasNext()) {
            next = zzdw().next();
        } else {
            List list = this.zzos.zzom;
            int i = this.pos - 1;
            this.pos = i;
            next = (Map.Entry<K, V>) list.get(i);
        }
        return next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
