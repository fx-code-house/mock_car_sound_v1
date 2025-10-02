package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgq extends zzgi<Boolean> implements zzik<Boolean>, zzjz, RandomAccess {
    private static final zzgq zzto;
    private int size;
    private boolean[] zztp;

    zzgq() {
        this(new boolean[10], 0);
    }

    private zzgq(boolean[] zArr, int i) {
        this.zztp = zArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzek();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        boolean[] zArr = this.zztp;
        System.arraycopy(zArr, i2, zArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgq)) {
            return super.equals(obj);
        }
        zzgq zzgqVar = (zzgq) obj;
        if (this.size != zzgqVar.size) {
            return false;
        }
        boolean[] zArr = zzgqVar.zztp;
        for (int i = 0; i < this.size; i++) {
            if (this.zztp[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzm = 1;
        for (int i = 0; i < this.size; i++) {
            iZzm = (iZzm * 31) + zzie.zzm(this.zztp[i]);
        }
        return iZzm;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Boolean)) {
            return -1;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.zztp[i] == zBooleanValue) {
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

    public final void addBoolean(boolean z) {
        zzek();
        int i = this.size;
        boolean[] zArr = this.zztp;
        if (i == zArr.length) {
            boolean[] zArr2 = new boolean[((i * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            this.zztp = zArr2;
        }
        boolean[] zArr3 = this.zztp;
        int i2 = this.size;
        this.size = i2 + 1;
        zArr3[i2] = z;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzek();
        zzie.checkNotNull(collection);
        if (!(collection instanceof zzgq)) {
            return super.addAll(collection);
        }
        zzgq zzgqVar = (zzgq) collection;
        int i = zzgqVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        boolean[] zArr = this.zztp;
        if (i3 > zArr.length) {
            this.zztp = Arrays.copyOf(zArr, i3);
        }
        System.arraycopy(zzgqVar.zztp, 0, this.zztp, this.size, zzgqVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzek();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zztp[i]))) {
                boolean[] zArr = this.zztp;
                System.arraycopy(zArr, i + 1, zArr, i, (this.size - i) - 1);
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
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzek();
        zzal(i);
        boolean[] zArr = this.zztp;
        boolean z = zArr[i];
        zArr[i] = zBooleanValue;
        return Boolean.valueOf(z);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzek();
        zzal(i);
        boolean[] zArr = this.zztp;
        boolean z = zArr[i];
        if (i < this.size - 1) {
            System.arraycopy(zArr, i + 1, zArr, i, (r2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzek();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
        boolean[] zArr = this.zztp;
        if (i2 < zArr.length) {
            System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
        } else {
            boolean[] zArr2 = new boolean[((i2 * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            System.arraycopy(this.zztp, i, zArr2, i + 1, this.size - i);
            this.zztp = zArr2;
        }
        this.zztp[i] = zBooleanValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        addBoolean(((Boolean) obj).booleanValue());
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik<Boolean> zzan(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzgq(Arrays.copyOf(this.zztp, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzal(i);
        return Boolean.valueOf(this.zztp[i]);
    }

    static {
        zzgq zzgqVar = new zzgq(new boolean[0], 0);
        zzto = zzgqVar;
        zzgqVar.zzej();
    }
}
