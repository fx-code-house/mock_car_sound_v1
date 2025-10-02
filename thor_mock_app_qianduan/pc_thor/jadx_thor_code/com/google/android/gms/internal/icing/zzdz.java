package com.google.android.gms.internal.icing;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzdz extends zzcp<Integer> implements zzee<Integer>, zzfq, RandomAccess {
    private static final zzdz zzkk;
    private int size;
    private int[] zzkl;

    zzdz() {
        this(new int[10], 0);
    }

    private zzdz(int[] iArr, int i) {
        this.zzkl = iArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzaj();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        int[] iArr = this.zzkl;
        System.arraycopy(iArr, i2, iArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdz)) {
            return super.equals(obj);
        }
        zzdz zzdzVar = (zzdz) obj;
        if (this.size != zzdzVar.size) {
            return false;
        }
        int[] iArr = zzdzVar.zzkl;
        for (int i = 0; i < this.size; i++) {
            if (this.zzkl[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzkl[i2];
        }
        return i;
    }

    public final int getInt(int i) {
        zzh(i);
        return this.zzkl[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Integer> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzdz)) {
            return super.addAll(collection);
        }
        zzdz zzdzVar = (zzdz) collection;
        int i = zzdzVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        int[] iArr = this.zzkl;
        if (i3 > iArr.length) {
            this.zzkl = Arrays.copyOf(iArr, i3);
        }
        System.arraycopy(zzdzVar.zzkl, 0, this.zzkl, this.size, zzdzVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzkl[i]))) {
                int[] iArr = this.zzkl;
                System.arraycopy(iArr, i + 1, iArr, i, (this.size - i) - 1);
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
        int iIntValue = ((Integer) obj).intValue();
        zzaj();
        zzh(i);
        int[] iArr = this.zzkl;
        int i2 = iArr[i];
        iArr[i] = iIntValue;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzaj();
        zzh(i);
        int[] iArr = this.zzkl;
        int i2 = iArr[i];
        if (i < this.size - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (r2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        int iIntValue = ((Integer) obj).intValue();
        zzaj();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        int[] iArr = this.zzkl;
        if (i2 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i2 - i);
        } else {
            int[] iArr2 = new int[((i2 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.zzkl, i, iArr2, i + 1, this.size - i);
            this.zzkl = iArr2;
        }
        this.zzkl[i] = iIntValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        int iIntValue = ((Integer) obj).intValue();
        zzaj();
        int i = this.size;
        int[] iArr = this.zzkl;
        if (i == iArr.length) {
            int[] iArr2 = new int[((i * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            this.zzkl = iArr2;
        }
        int[] iArr3 = this.zzkl;
        int i2 = this.size;
        this.size = i2 + 1;
        iArr3[i2] = iIntValue;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Integer> zzj(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzdz(Arrays.copyOf(this.zzkl, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    static {
        zzdz zzdzVar = new zzdz(new int[0], 0);
        zzkk = zzdzVar;
        zzdzVar.zzai();
    }
}
