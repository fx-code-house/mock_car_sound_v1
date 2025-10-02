package com.google.android.gms.internal.vision;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
abstract class zzdw<T> implements Iterator<T> {
    private int currentIndex;
    private final /* synthetic */ zzdp zzmo;
    private int zzmp;
    private int zzmq;

    private zzdw(zzdp zzdpVar) {
        this.zzmo = zzdpVar;
        this.zzmp = zzdpVar.zzmk;
        this.currentIndex = zzdpVar.zzci();
        this.zzmq = -1;
    }

    abstract T zzu(int i);

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.currentIndex >= 0;
    }

    @Override // java.util.Iterator
    public T next() {
        zzcn();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.currentIndex;
        this.zzmq = i;
        T tZzu = zzu(i);
        this.currentIndex = this.zzmo.zzt(this.currentIndex);
        return tZzu;
    }

    @Override // java.util.Iterator
    public void remove() {
        zzcn();
        zzde.checkState(this.zzmq >= 0, "no calls to next() since the last call to remove()");
        this.zzmp += 32;
        zzdp zzdpVar = this.zzmo;
        zzdpVar.remove(zzdpVar.zzmi[this.zzmq]);
        this.currentIndex = zzdp.zzg(this.currentIndex, this.zzmq);
        this.zzmq = -1;
    }

    private final void zzcn() {
        if (this.zzmo.zzmk != this.zzmp) {
            throw new ConcurrentModificationException();
        }
    }

    /* synthetic */ zzdw(zzdp zzdpVar, zzds zzdsVar) {
        this(zzdpVar);
    }
}
