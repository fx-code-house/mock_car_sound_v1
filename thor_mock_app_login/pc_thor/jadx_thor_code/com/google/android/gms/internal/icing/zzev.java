package com.google.android.gms.internal.icing;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzev extends zzcp<Long> implements zzee<Long>, zzfq, RandomAccess {
    private static final zzev zzmf;
    private int size;
    private long[] zzmg;

    public static zzev zzci() {
        return zzmf;
    }

    zzev() {
        this(new long[10], 0);
    }

    private zzev(long[] jArr, int i) {
        this.zzmg = jArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzaj();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        long[] jArr = this.zzmg;
        System.arraycopy(jArr, i2, jArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzev)) {
            return super.equals(obj);
        }
        zzev zzevVar = (zzev) obj;
        if (this.size != zzevVar.size) {
            return false;
        }
        long[] jArr = zzevVar.zzmg;
        for (int i = 0; i < this.size; i++) {
            if (this.zzmg[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzk = 1;
        for (int i = 0; i < this.size; i++) {
            iZzk = (iZzk * 31) + zzeb.zzk(this.zzmg[i]);
        }
        return iZzk;
    }

    public final long getLong(int i) {
        zzh(i);
        return this.zzmg[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Long> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzev)) {
            return super.addAll(collection);
        }
        zzev zzevVar = (zzev) collection;
        int i = zzevVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        long[] jArr = this.zzmg;
        if (i3 > jArr.length) {
            this.zzmg = Arrays.copyOf(jArr, i3);
        }
        System.arraycopy(zzevVar.zzmg, 0, this.zzmg, this.size, zzevVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzmg[i]))) {
                long[] jArr = this.zzmg;
                System.arraycopy(jArr, i + 1, jArr, i, (this.size - i) - 1);
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
        long jLongValue = ((Long) obj).longValue();
        zzaj();
        zzh(i);
        long[] jArr = this.zzmg;
        long j = jArr[i];
        jArr[i] = jLongValue;
        return Long.valueOf(j);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzaj();
        zzh(i);
        long[] jArr = this.zzmg;
        long j = jArr[i];
        if (i < this.size - 1) {
            System.arraycopy(jArr, i + 1, jArr, i, (r3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        long jLongValue = ((Long) obj).longValue();
        zzaj();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        long[] jArr = this.zzmg;
        if (i2 < jArr.length) {
            System.arraycopy(jArr, i, jArr, i + 1, i2 - i);
        } else {
            long[] jArr2 = new long[((i2 * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            System.arraycopy(this.zzmg, i, jArr2, i + 1, this.size - i);
            this.zzmg = jArr2;
        }
        this.zzmg[i] = jLongValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        long jLongValue = ((Long) obj).longValue();
        zzaj();
        int i = this.size;
        long[] jArr = this.zzmg;
        if (i == jArr.length) {
            long[] jArr2 = new long[((i * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            this.zzmg = jArr2;
        }
        long[] jArr3 = this.zzmg;
        int i2 = this.size;
        this.size = i2 + 1;
        jArr3[i2] = jLongValue;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Long> zzj(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzev(Arrays.copyOf(this.zzmg, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    static {
        zzev zzevVar = new zzev(new long[0], 0);
        zzmf = zzevVar;
        zzevVar.zzai();
    }
}
