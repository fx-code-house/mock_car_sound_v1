package com.google.android.gms.internal.wearable;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzz extends AbstractList<Float> implements RandomAccess, Serializable {
    final float[] zza;
    final int zzb;
    final int zzc;

    zzz(float[] fArr, int i, int i2) {
        this.zza = fArr;
        this.zzb = i;
        this.zzc = i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return (obj instanceof Float) && zzaa.zzb(this.zza, ((Float) obj).floatValue(), this.zzb, this.zzc) != -1;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzz)) {
            return super.equals(obj);
        }
        zzz zzzVar = (zzz) obj;
        int i = this.zzc - this.zzb;
        if (zzzVar.zzc - zzzVar.zzb != i) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zza[this.zzb + i2] != zzzVar.zza[zzzVar.zzb + i2]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object get(int i) {
        zzx.zza(i, this.zzc - this.zzb, FirebaseAnalytics.Param.INDEX);
        return Float.valueOf(this.zza[this.zzb + i]);
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iHashCode = 1;
        for (int i = this.zzb; i < this.zzc; i++) {
            iHashCode = (iHashCode * 31) + Float.valueOf(this.zza[i]).hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        int iZzb;
        if (!(obj instanceof Float) || (iZzb = zzaa.zzb(this.zza, ((Float) obj).floatValue(), this.zzb, this.zzc)) < 0) {
            return -1;
        }
        return iZzb - this.zzb;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        if (obj instanceof Float) {
            float[] fArr = this.zza;
            float fFloatValue = ((Float) obj).floatValue();
            int i = this.zzb;
            int i2 = this.zzc - 1;
            while (true) {
                if (i2 < i) {
                    i2 = -1;
                    break;
                }
                if (fArr[i2] == fFloatValue) {
                    break;
                }
                i2--;
            }
            if (i2 >= 0) {
                return i2 - this.zzb;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        Float f = (Float) obj;
        zzx.zza(i, this.zzc - this.zzb, FirebaseAnalytics.Param.INDEX);
        float[] fArr = this.zza;
        int i2 = this.zzb + i;
        float f2 = fArr[i2];
        f.getClass();
        fArr[i2] = f.floatValue();
        return Float.valueOf(f2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc - this.zzb;
    }

    @Override // java.util.AbstractList, java.util.List
    public final List<Float> subList(int i, int i2) {
        zzx.zzb(i, i2, this.zzc - this.zzb);
        if (i == i2) {
            return Collections.emptyList();
        }
        float[] fArr = this.zza;
        int i3 = this.zzb;
        return new zzz(fArr, i + i3, i3 + i2);
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        StringBuilder sb = new StringBuilder((this.zzc - this.zzb) * 12);
        sb.append('[');
        sb.append(this.zza[this.zzb]);
        int i = this.zzb;
        while (true) {
            i++;
            if (i >= this.zzc) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            sb.append(this.zza[i]);
        }
    }
}
