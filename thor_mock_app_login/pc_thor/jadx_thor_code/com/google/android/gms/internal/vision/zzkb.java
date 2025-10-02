package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkb<E> extends zzgi<E> implements RandomAccess {
    private static final zzkb<Object> zzabm;
    private int size;
    private E[] zznl;

    public static <E> zzkb<E> zzim() {
        return (zzkb<E>) zzabm;
    }

    zzkb() {
        this(new Object[10], 0);
    }

    private zzkb(E[] eArr, int i) {
        this.zznl = eArr;
        this.size = i;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(E e) {
        zzek();
        int i = this.size;
        E[] eArr = this.zznl;
        if (i == eArr.length) {
            this.zznl = (E[]) Arrays.copyOf(eArr, ((i * 3) / 2) + 1);
        }
        E[] eArr2 = this.zznl;
        int i2 = this.size;
        this.size = i2 + 1;
        eArr2[i2] = e;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final void add(int i, E e) {
        int i2;
        zzek();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
        E[] eArr = this.zznl;
        if (i2 < eArr.length) {
            System.arraycopy(eArr, i, eArr, i + 1, i2 - i);
        } else {
            E[] eArr2 = (E[]) new Object[((i2 * 3) / 2) + 1];
            System.arraycopy(eArr, 0, eArr2, 0, i);
            System.arraycopy(this.zznl, i, eArr2, i + 1, this.size - i);
            this.zznl = eArr2;
        }
        this.zznl[i] = e;
        this.size++;
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final E get(int i) {
        zzal(i);
        return this.zznl[i];
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final E remove(int i) {
        zzek();
        zzal(i);
        E[] eArr = this.zznl;
        E e = eArr[i];
        if (i < this.size - 1) {
            System.arraycopy(eArr, i + 1, eArr, i, (r2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return e;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final E set(int i, E e) {
        zzek();
        zzal(i);
        E[] eArr = this.zznl;
        E e2 = eArr[i];
        eArr[i] = e;
        this.modCount++;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    private final void zzal(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
    }

    private final String zzam(int i) {
        return new StringBuilder(35).append("Index:").append(i).append(", Size:").append(this.size).toString();
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik zzan(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzkb(Arrays.copyOf(this.zznl, i), this.size);
    }

    static {
        zzkb<Object> zzkbVar = new zzkb<>(new Object[0], 0);
        zzabm = zzkbVar;
        zzkbVar.zzej();
    }
}
