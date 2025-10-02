package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzif extends zzgi<Integer> implements zzik<Integer>, zzjz, RandomAccess {
    private static final zzif zzzb;
    private int size;
    private int[] zzzc;

    public static zzif zzhg() {
        return zzzb;
    }

    zzif() {
        this(new int[10], 0);
    }

    private zzif(int[] iArr, int i) {
        this.zzzc = iArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzek();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        int[] iArr = this.zzzc;
        System.arraycopy(iArr, i2, iArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzif)) {
            return super.equals(obj);
        }
        zzif zzifVar = (zzif) obj;
        if (this.size != zzifVar.size) {
            return false;
        }
        int[] iArr = zzifVar.zzzc;
        for (int i = 0; i < this.size; i++) {
            if (this.zzzc[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzzc[i2];
        }
        return i;
    }

    public final int getInt(int i) {
        zzal(i);
        return this.zzzc[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Integer)) {
            return -1;
        }
        int iIntValue = ((Integer) obj).intValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.zzzc[i] == iIntValue) {
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

    public final void zzbs(int i) {
        zzek();
        int i2 = this.size;
        int[] iArr = this.zzzc;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[((i2 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.zzzc = iArr2;
        }
        int[] iArr3 = this.zzzc;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr3[i3] = i;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Integer> collection) {
        zzek();
        zzie.checkNotNull(collection);
        if (!(collection instanceof zzif)) {
            return super.addAll(collection);
        }
        zzif zzifVar = (zzif) collection;
        int i = zzifVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        int[] iArr = this.zzzc;
        if (i3 > iArr.length) {
            this.zzzc = Arrays.copyOf(iArr, i3);
        }
        System.arraycopy(zzifVar.zzzc, 0, this.zzzc, this.size, zzifVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzek();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzzc[i]))) {
                int[] iArr = this.zzzc;
                System.arraycopy(iArr, i + 1, iArr, i, (this.size - i) - 1);
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
        int iIntValue = ((Integer) obj).intValue();
        zzek();
        zzal(i);
        int[] iArr = this.zzzc;
        int i2 = iArr[i];
        iArr[i] = iIntValue;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzek();
        zzal(i);
        int[] iArr = this.zzzc;
        int i2 = iArr[i];
        if (i < this.size - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (r2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        int iIntValue = ((Integer) obj).intValue();
        zzek();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
        int[] iArr = this.zzzc;
        if (i2 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i2 - i);
        } else {
            int[] iArr2 = new int[((i2 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.zzzc, i, iArr2, i + 1, this.size - i);
            this.zzzc = iArr2;
        }
        this.zzzc[i] = iIntValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        zzbs(((Integer) obj).intValue());
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik<Integer> zzan(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzif(Arrays.copyOf(this.zzzc, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    static {
        zzif zzifVar = new zzif(new int[0], 0);
        zzzb = zzifVar;
        zzifVar.zzej();
    }
}
