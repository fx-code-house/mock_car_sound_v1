package com.google.android.gms.internal.vision;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzkz extends AbstractList<String> implements zziu, RandomAccess {
    private final zziu zzack;

    public zzkz(zziu zziuVar) {
        this.zzack = zziuVar;
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final zziu zzht() {
        return this;
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final Object zzbt(int i) {
        return this.zzack.zzbt(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzack.size();
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final void zzc(zzgs zzgsVar) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zzky(this, i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzlb(this);
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final List<?> zzhs() {
        return this.zzack.zzhs();
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return (String) this.zzack.get(i);
    }
}
