package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzhz extends zzgi<Float> implements zzik<Float>, zzjz, RandomAccess {
    private static final zzhz zzxm;
    private int size;
    private float[] zzxn;

    zzhz() {
        this(new float[10], 0);
    }

    private zzhz(float[] fArr, int i) {
        this.zzxn = fArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzek();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        float[] fArr = this.zzxn;
        System.arraycopy(fArr, i2, fArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhz)) {
            return super.equals(obj);
        }
        zzhz zzhzVar = (zzhz) obj;
        if (this.size != zzhzVar.size) {
            return false;
        }
        float[] fArr = zzhzVar.zzxn;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.zzxn[i]) != Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iFloatToIntBits = 1;
        for (int i = 0; i < this.size; i++) {
            iFloatToIntBits = (iFloatToIntBits * 31) + Float.floatToIntBits(this.zzxn[i]);
        }
        return iFloatToIntBits;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Float)) {
            return -1;
        }
        float fFloatValue = ((Float) obj).floatValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.zzxn[i] == fFloatValue) {
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

    public final void zzu(float f) {
        zzek();
        int i = this.size;
        float[] fArr = this.zzxn;
        if (i == fArr.length) {
            float[] fArr2 = new float[((i * 3) / 2) + 1];
            System.arraycopy(fArr, 0, fArr2, 0, i);
            this.zzxn = fArr2;
        }
        float[] fArr3 = this.zzxn;
        int i2 = this.size;
        this.size = i2 + 1;
        fArr3[i2] = f;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Float> collection) {
        zzek();
        zzie.checkNotNull(collection);
        if (!(collection instanceof zzhz)) {
            return super.addAll(collection);
        }
        zzhz zzhzVar = (zzhz) collection;
        int i = zzhzVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        float[] fArr = this.zzxn;
        if (i3 > fArr.length) {
            this.zzxn = Arrays.copyOf(fArr, i3);
        }
        System.arraycopy(zzhzVar.zzxn, 0, this.zzxn, this.size, zzhzVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzek();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzxn[i]))) {
                float[] fArr = this.zzxn;
                System.arraycopy(fArr, i + 1, fArr, i, (this.size - i) - 1);
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
        float fFloatValue = ((Float) obj).floatValue();
        zzek();
        zzal(i);
        float[] fArr = this.zzxn;
        float f = fArr[i];
        fArr[i] = fFloatValue;
        return Float.valueOf(f);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzek();
        zzal(i);
        float[] fArr = this.zzxn;
        float f = fArr[i];
        if (i < this.size - 1) {
            System.arraycopy(fArr, i + 1, fArr, i, (r2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(f);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        float fFloatValue = ((Float) obj).floatValue();
        zzek();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzam(i));
        }
        float[] fArr = this.zzxn;
        if (i2 < fArr.length) {
            System.arraycopy(fArr, i, fArr, i + 1, i2 - i);
        } else {
            float[] fArr2 = new float[((i2 * 3) / 2) + 1];
            System.arraycopy(fArr, 0, fArr2, 0, i);
            System.arraycopy(this.zzxn, i, fArr2, i + 1, this.size - i);
            this.zzxn = fArr2;
        }
        this.zzxn[i] = fFloatValue;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        zzu(((Float) obj).floatValue());
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik<Float> zzan(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzhz(Arrays.copyOf(this.zzxn, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzal(i);
        return Float.valueOf(this.zzxn[i]);
    }

    static {
        zzhz zzhzVar = new zzhz(new float[0], 0);
        zzxm = zzhzVar;
        zzhzVar.zzej();
    }
}
