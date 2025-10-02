package com.google.android.gms.internal.icing;

import java.util.ListIterator;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzgq implements ListIterator<String> {
    private ListIterator<String> zzoh;
    private final /* synthetic */ int zzoi;
    private final /* synthetic */ zzgr zzoj;

    zzgq(zzgr zzgrVar, int i) {
        this.zzoj = zzgrVar;
        this.zzoi = i;
        this.zzoh = zzgrVar.zzok.listIterator(i);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.zzoh.hasNext();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.zzoh.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.zzoh.nextIndex();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.zzoh.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void add(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void set(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ String previous() {
        return this.zzoh.previous();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.zzoh.next();
    }
}
