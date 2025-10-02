package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzgh<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzfz zznx;
    private Iterator<Map.Entry<K, V>> zzny;
    private boolean zzoc;

    private zzgh(zzfz zzfzVar) {
        this.zznx = zzfzVar;
        this.pos = -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zznx.zzns.size() || (!this.zznx.zznt.isEmpty() && zzdi().hasNext());
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzoc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzoc = false;
        this.zznx.zzdg();
        if (this.pos < this.zznx.zzns.size()) {
            zzfz zzfzVar = this.zznx;
            int i = this.pos;
            this.pos = i - 1;
            zzfzVar.zzak(i);
            return;
        }
        zzdi().remove();
    }

    private final Iterator<Map.Entry<K, V>> zzdi() {
        if (this.zzny == null) {
            this.zzny = this.zznx.zznt.entrySet().iterator();
        }
        return this.zzny;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzoc = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i >= this.zznx.zzns.size()) {
            return zzdi().next();
        }
        return (Map.Entry) this.zznx.zzns.get(this.pos);
    }

    /* synthetic */ zzgh(zzfz zzfzVar, zzfy zzfyVar) {
        this(zzfzVar);
    }
}
