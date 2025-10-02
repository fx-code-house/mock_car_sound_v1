package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzhm extends zzgi<Double> implements zzik<Double>, zzjz, RandomAccess {
    private static final zzhm zzup;
    private int size;
    private double[] zzuq;

    zzhm() {
        this(new double[10], 0);
    }

    private zzhm(double[] dArr, int i) {
        this.zzuq = dArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzek();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        double[] dArr = this.zzuq;
        System.arraycopy(dArr, i2, dArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhm)) {
            return super.equals(obj);
        }
        zzhm zzhmVar = (zzhm) obj;
        if (this.size != zzhmVar.size) {
            return false;
        }
        double[] dArr = zzhmVar.zzuq;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzuq[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzab = 1;
        for (int i = 0; i < this.size; i++) {
            iZzab = (iZzab * 31) + zzie.zzab(Double.doubleToLongBits(this.zzuq[i]));
        }
        return iZzab;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Double)) {
            return -1;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.zzuq[i] == dDoubleValue) {
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

    public final void zzc(double d) {
        zzek();
        int i = this.size;
        double[] dArr = this.zzuq;
        if (i == dArr.length) {
            double[] dArr2 = new double[((i * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            this.zzuq = dArr2;
        }
        double[] dArr3 = this.zzuq;
        int i2 = this.size;
        this.size = i2 + 1;
        dArr3[i2] = d;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Double> collection) {
        zzek();
        zzie.checkNotNull(collection);
        if (!(collection instanceof zzhm)) {
            return super.addAll(collection);
        }
        zzhm zzhmVar = (zzhm) collection;
        int i = zzhmVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        double[] dArr = this.zzuq;
        if (i3 > dArr.length) {
            this.zzuq = Arrays.copyOf(dArr, i3);
        }
        System.arraycopy(zzhmVar.zzuq, 0, this.zzuq, this.size, zzhmVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzek();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzuq[i]))) {
                double[] dArr = this.zzuq;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
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
        double dDoubleValue = ((Double) obj).doubleValue();
        zzek();
        zzal(i);
        double[] dArr = this.zzuq;
        double d = dArr[i];
        dArr[i] = dDoubleValue;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzek();
        zzal(i);
        double[] dArr = this.zzuq;
        double d = dArr[i];
        if (i < this.size - 1) {
            System.arraycopy(dArr, i + 1, dArr, i, (r3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        double dDoubleValue = ((Double) obj).doubleValue();
        zzek();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
        double[] dArr = this.zzuq;
        if (i2 < dArr.length) {
            System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
        } else {
            double[] dArr2 = new double[((i2 * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            System.arraycopy(this.zzuq, i, dArr2, i + 1, this.size - i);
            this.zzuq = dArr2;
        }
        this.zzuq[i] = dDoubleValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        zzc(((Double) obj).doubleValue());
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik<Double> zzan(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzhm(Arrays.copyOf(this.zzuq, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzal(i);
        return Double.valueOf(this.zzuq[i]);
    }

    static {
        zzhm zzhmVar = new zzhm(new double[0], 0);
        zzup = zzhmVar;
        zzhmVar.zzej();
    }
}
