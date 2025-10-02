package com.google.android.gms.internal.icing;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzcr extends zzcp<Boolean> implements zzee<Boolean>, zzfq, RandomAccess {
    private static final zzcr zzge;
    private int size;
    private boolean[] zzgf;

    public static zzcr zzak() {
        return zzge;
    }

    zzcr() {
        this(new boolean[10], 0);
    }

    private zzcr(boolean[] zArr, int i) {
        this.zzgf = zArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzaj();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        boolean[] zArr = this.zzgf;
        System.arraycopy(zArr, i2, zArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcr)) {
            return super.equals(obj);
        }
        zzcr zzcrVar = (zzcr) obj;
        if (this.size != zzcrVar.size) {
            return false;
        }
        boolean[] zArr = zzcrVar.zzgf;
        for (int i = 0; i < this.size; i++) {
            if (this.zzgf[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzg = 1;
        for (int i = 0; i < this.size; i++) {
            iZzg = (iZzg * 31) + zzeb.zzg(this.zzgf[i]);
        }
        return iZzg;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzcr)) {
            return super.addAll(collection);
        }
        zzcr zzcrVar = (zzcr) collection;
        int i = zzcrVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        boolean[] zArr = this.zzgf;
        if (i3 > zArr.length) {
            this.zzgf = Arrays.copyOf(zArr, i3);
        }
        System.arraycopy(zzcrVar.zzgf, 0, this.zzgf, this.size, zzcrVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzgf[i]))) {
                boolean[] zArr = this.zzgf;
                System.arraycopy(zArr, i + 1, zArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzh(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
    }

    private final String zzi(int i) {
        return new StringBuilder(35).append("Index:").append(i).append(", Size:").append(this.size).toString();
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzaj();
        zzh(i);
        boolean[] zArr = this.zzgf;
        boolean z = zArr[i];
        zArr[i] = zBooleanValue;
        return Boolean.valueOf(z);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzaj();
        zzh(i);
        boolean[] zArr = this.zzgf;
        boolean z = zArr[i];
        if (i < this.size - 1) {
            System.arraycopy(zArr, i + 1, zArr, i, (r2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzaj();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        boolean[] zArr = this.zzgf;
        if (i2 < zArr.length) {
            System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
        } else {
            boolean[] zArr2 = new boolean[((i2 * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            System.arraycopy(this.zzgf, i, zArr2, i + 1, this.size - i);
            this.zzgf = zArr2;
        }
        this.zzgf[i] = zBooleanValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzaj();
        int i = this.size;
        boolean[] zArr = this.zzgf;
        if (i == zArr.length) {
            boolean[] zArr2 = new boolean[((i * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            this.zzgf = zArr2;
        }
        boolean[] zArr3 = this.zzgf;
        int i2 = this.size;
        this.size = i2 + 1;
        zArr3[i2] = zBooleanValue;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Boolean> zzj(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzcr(Arrays.copyOf(this.zzgf, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzh(i);
        return Boolean.valueOf(this.zzgf[i]);
    }

    static {
        zzcr zzcrVar = new zzcr(new boolean[0], 0);
        zzge = zzcrVar;
        zzcrVar.zzai();
    }
}
