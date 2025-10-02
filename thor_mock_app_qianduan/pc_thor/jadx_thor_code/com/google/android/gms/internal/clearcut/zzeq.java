package com.google.android.gms.internal.clearcut;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes2.dex */
final class zzeq<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private Iterator<Map.Entry<K, V>> zzor;
    private final /* synthetic */ zzei zzos;
    private boolean zzow;

    private zzeq(zzei zzeiVar) {
        this.zzos = zzeiVar;
        this.pos = -1;
    }

    /* synthetic */ zzeq(zzei zzeiVar, zzej zzejVar) {
        this(zzeiVar);
    }

    private final Iterator<Map.Entry<K, V>> zzdw() {
        if (this.zzor == null) {
            this.zzor = this.zzos.zzon.entrySet().iterator();
        }
        return this.zzor;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zzos.zzom.size() || (!this.zzos.zzon.isEmpty() && zzdw().hasNext());
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzow = true;
        int i = this.pos + 1;
        this.pos = i;
        return i < this.zzos.zzom.size() ? (Map.Entry<K, V>) this.zzos.zzom.get(this.pos) : zzdw().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzow) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzow = false;
        this.zzos.zzdu();
        if (this.pos >= this.zzos.zzom.size()) {
            zzdw().remove();
            return;
        }
        zzei zzeiVar = this.zzos;
        int i = this.pos;
        this.pos = i - 1;
        zzeiVar.zzal(i);
    }
}
