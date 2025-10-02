package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzey<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zza = new Object[0];

    zzey() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public abstract boolean contains(@NullableDecl Object obj);

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public abstract zzfx<E> iterator();

    @NullableDecl
    Object[] zzb() {
        return null;
    }

    abstract boolean zzf();

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return toArray(zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        zzeb.zza(tArr);
        int size = size();
        if (tArr.length < size) {
            Object[] objArrZzb = zzb();
            if (objArrZzb != null) {
                return (T[]) Arrays.copyOfRange(objArrZzb, zzc(), zzd(), tArr.getClass());
            }
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        zza(tArr, 0);
        return tArr;
    }

    int zzc() {
        throw new UnsupportedOperationException();
    }

    int zzd() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public zzfb<E> zze() {
        return isEmpty() ? zzfb.zzg() : zzfb.zza(toArray());
    }

    int zza(Object[] objArr, int i) {
        zzfx zzfxVar = (zzfx) iterator();
        while (zzfxVar.hasNext()) {
            objArr[i] = zzfxVar.next();
            i++;
        }
        return i;
    }
}
