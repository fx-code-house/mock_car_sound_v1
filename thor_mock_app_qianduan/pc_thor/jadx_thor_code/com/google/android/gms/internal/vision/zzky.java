package com.google.android.gms.internal.vision;

import java.util.ListIterator;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzky implements ListIterator<String> {
    private ListIterator<String> zzach;
    private final /* synthetic */ int zzaci;
    private final /* synthetic */ zzkz zzacj;

    zzky(zzkz zzkzVar, int i) {
        this.zzacj = zzkzVar;
        this.zzaci = i;
        this.zzach = zzkzVar.zzack.listIterator(i);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.zzach.hasNext();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.zzach.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.zzach.nextIndex();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.zzach.previousIndex();
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
        return this.zzach.previous();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.zzach.next();
    }
}
