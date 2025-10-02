package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjb extends zzgi<Long> implements zzik<Long>, zzjz, RandomAccess {
    private static final zzjb zzaaf;
    private int size;
    private long[] zzaag;

    zzjb() {
        this(new long[10], 0);
    }

    private zzjb(long[] jArr, int i) {
        this.zzaag = jArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzek();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        long[] jArr = this.zzaag;
        System.arraycopy(jArr, i2, jArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzjb)) {
            return super.equals(obj);
        }
        zzjb zzjbVar = (zzjb) obj;
        if (this.size != zzjbVar.size) {
            return false;
        }
        long[] jArr = zzjbVar.zzaag;
        for (int i = 0; i < this.size; i++) {
            if (this.zzaag[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzab = 1;
        for (int i = 0; i < this.size; i++) {
            iZzab = (iZzab * 31) + zzie.zzab(this.zzaag[i]);
        }
        return iZzab;
    }

    public final long getLong(int i) {
        zzal(i);
        return this.zzaag[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Long)) {
            return -1;
        }
        long jLongValue = ((Long) obj).longValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.zzaag[i] == jLongValue) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    public final void zzac(long j) {
        zzek();
        int i = this.size;
        long[] jArr = this.zzaag;
        if (i == jArr.length) {
            long[] jArr2 = new long[((i * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            this.zzaag = jArr2;
        }
        long[] jArr3 = this.zzaag;
        int i2 = this.size;
        this.size = i2 + 1;
        jArr3[i2] = j;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Long> collection) {
        zzek();
        zzie.checkNotNull(collection);
        if (!(collection instanceof zzjb)) {
            return super.addAll(collection);
        }
        zzjb zzjbVar = (zzjb) collection;
        int i = zzjbVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        long[] jArr = this.zzaag;
        if (i3 > jArr.length) {
            this.zzaag = Arrays.copyOf(jArr, i3);
        }
        System.arraycopy(zzjbVar.zzaag, 0, this.zzaag, this.size, zzjbVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzek();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzaag[i]))) {
                long[] jArr = this.zzaag;
                System.arraycopy(jArr, i + 1, jArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzal(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
    }

    private final String zzam(int i) {
        return new StringBuilder(35).append("Index:").append(i).append(", Size:").append(this.size).toString();
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        long jLongValue = ((Long) obj).longValue();
        zzek();
        zzal(i);
        long[] jArr = this.zzaag;
        long j = jArr[i];
        jArr[i] = jLongValue;
        return Long.valueOf(j);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzek();
        zzal(i);
        long[] jArr = this.zzaag;
        long j = jArr[i];
        if (i < this.size - 1) {
            System.arraycopy(jArr, i + 1, jArr, i, (r3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        long jLongValue = ((Long) obj).longValue();
        zzek();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
        long[] jArr = this.zzaag;
        if (i2 < jArr.length) {
            System.arraycopy(jArr, i, jArr, i + 1, i2 - i);
        } else {
            long[] jArr2 = new long[((i2 * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            System.arraycopy(this.zzaag, i, jArr2, i + 1, this.size - i);
            this.zzaag = jArr2;
        }
        this.zzaag[i] = jLongValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        zzac(((Long) obj).longValue());
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik<Long> zzan(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzjb(Arrays.copyOf(this.zzaag, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    static {
        zzjb zzjbVar = new zzjb(new long[0], 0);
        zzaaf = zzjbVar;
        zzjbVar.zzej();
    }
}
