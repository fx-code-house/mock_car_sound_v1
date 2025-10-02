package com.google.android.gms.internal.icing;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzdl extends zzcp<Double> implements zzee<Double>, zzfq, RandomAccess {
    private static final zzdl zzgz;
    private int size;
    private double[] zzha;

    public static zzdl zzax() {
        return zzgz;
    }

    zzdl() {
        this(new double[10], 0);
    }

    private zzdl(double[] dArr, int i) {
        this.zzha = dArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzaj();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        double[] dArr = this.zzha;
        System.arraycopy(dArr, i2, dArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdl)) {
            return super.equals(obj);
        }
        zzdl zzdlVar = (zzdl) obj;
        if (this.size != zzdlVar.size) {
            return false;
        }
        double[] dArr = zzdlVar.zzha;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzha[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzk = 1;
        for (int i = 0; i < this.size; i++) {
            iZzk = (iZzk * 31) + zzeb.zzk(Double.doubleToLongBits(this.zzha[i]));
        }
        return iZzk;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Double> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzdl)) {
            return super.addAll(collection);
        }
        zzdl zzdlVar = (zzdl) collection;
        int i = zzdlVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        double[] dArr = this.zzha;
        if (i3 > dArr.length) {
            this.zzha = Arrays.copyOf(dArr, i3);
        }
        System.arraycopy(zzdlVar.zzha, 0, this.zzha, this.size, zzdlVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzha[i]))) {
                double[] dArr = this.zzha;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
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
        double dDoubleValue = ((Double) obj).doubleValue();
        zzaj();
        zzh(i);
        double[] dArr = this.zzha;
        double d = dArr[i];
        dArr[i] = dDoubleValue;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzaj();
        zzh(i);
        double[] dArr = this.zzha;
        double d = dArr[i];
        if (i < this.size - 1) {
            System.arraycopy(dArr, i + 1, dArr, i, (r3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        double dDoubleValue = ((Double) obj).doubleValue();
        zzaj();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        double[] dArr = this.zzha;
        if (i2 < dArr.length) {
            System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
        } else {
            double[] dArr2 = new double[((i2 * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            System.arraycopy(this.zzha, i, dArr2, i + 1, this.size - i);
            this.zzha = dArr2;
        }
        this.zzha[i] = dDoubleValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        double dDoubleValue = ((Double) obj).doubleValue();
        zzaj();
        int i = this.size;
        double[] dArr = this.zzha;
        if (i == dArr.length) {
            double[] dArr2 = new double[((i * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            this.zzha = dArr2;
        }
        double[] dArr3 = this.zzha;
        int i2 = this.size;
        this.size = i2 + 1;
        dArr3[i2] = dDoubleValue;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Double> zzj(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzdl(Arrays.copyOf(this.zzha, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzh(i);
        return Double.valueOf(this.zzha[i]);
    }

    static {
        zzdl zzdlVar = new zzdl(new double[0], 0);
        zzgz = zzdlVar;
        zzdlVar.zzai();
    }
}
