package com.google.android.gms.internal.vision;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzeb<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zzmu = new Object[0];

    zzeb() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public abstract boolean contains(@NullableDecl Object obj);

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* renamed from: zzcp, reason: merged with bridge method [inline-methods] */
    public abstract zzfa<E> iterator();

    @NullableDecl
    Object[] zzcq() {
        return null;
    }

    abstract boolean zzcu();

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return toArray(zzmu);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        zzde.checkNotNull(tArr);
        int size = size();
        if (tArr.length < size) {
            Object[] objArrZzcq = zzcq();
            if (objArrZzcq != null) {
                return (T[]) Arrays.copyOfRange(objArrZzcq, zzcr(), zzcs(), tArr.getClass());
            }
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        zza(tArr, 0);
        return tArr;
    }

    int zzcr() {
        throw new UnsupportedOperationException();
    }

    int zzcs() {
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

    public zzee<E> zzct() {
        return isEmpty() ? zzee.zzcv() : zzee.zza(toArray());
    }

    int zza(Object[] objArr, int i) {
        zzfa zzfaVar = (zzfa) iterator();
        while (zzfaVar.hasNext()) {
            objArr[i] = zzfaVar.next();
            i++;
        }
        return i;
    }
}
