package com.google.android.gms.internal.vision;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzex<E> extends zzej<E> {
    private final transient E zznu;
    private transient int zznv;

    zzex(E e) {
        this.zznu = (E) zzde.checkNotNull(e);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final boolean zzcu() {
        return false;
    }

    zzex(E e, int i) {
        this.zznu = e;
        this.zznv = i;
    }

    @Override // com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        return this.zznu.equals(obj);
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    /* renamed from: zzcp */
    public final zzfa<E> iterator() {
        return new zzeo(this.zznu);
    }

    @Override // com.google.android.gms.internal.vision.zzej
    final zzee<E> zzda() {
        return zzee.zzg(this.zznu);
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final int zza(Object[] objArr, int i) {
        objArr[i] = this.zznu;
        return i + 1;
    }

    @Override // com.google.android.gms.internal.vision.zzej, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.zznv;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.zznu.hashCode();
        this.zznv = iHashCode;
        return iHashCode;
    }

    @Override // com.google.android.gms.internal.vision.zzej
    final boolean zzcz() {
        return this.zznv != 0;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String string = this.zznu.toString();
        return new StringBuilder(String.valueOf(string).length() + 2).append('[').append(string).append(']').toString();
    }

    @Override // com.google.android.gms.internal.vision.zzej, com.google.android.gms.internal.vision.zzeb, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
