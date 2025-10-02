package com.google.android.gms.internal.icing;

import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzgt implements Iterator<String> {
    private final /* synthetic */ zzgr zzoj;
    private Iterator<String> zzpf;

    zzgt(zzgr zzgrVar) {
        this.zzoj = zzgrVar;
        this.zzpf = zzgrVar.zzok.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzpf.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzpf.next();
    }
}
