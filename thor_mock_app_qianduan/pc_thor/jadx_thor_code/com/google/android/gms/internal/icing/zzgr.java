package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzgr extends AbstractList<String> implements zzeo, RandomAccess {
    private final zzeo zzok;

    public zzgr(zzeo zzeoVar) {
        this.zzok = zzeoVar;
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final zzeo zzce() {
        return this;
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final Object zzad(int i) {
        return this.zzok.zzad(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzok.size();
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final void zzc(zzct zzctVar) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zzgq(this, i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzgt(this);
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final List<?> zzcd() {
        return this.zzok.zzcd();
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return (String) this.zzok.get(i);
    }
}
