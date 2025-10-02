package com.google.android.gms.internal.vision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zziv extends zzgi<String> implements zziu, RandomAccess {
    private static final zziv zzzx;
    private static final zziu zzzy;
    private final List<Object> zzzz;

    public zziv() {
        this(10);
    }

    public zziv(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    private zziv(ArrayList<Object> arrayList) {
        this.zzzz = arrayList;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzzz.size();
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzek();
        if (collection instanceof zziu) {
            collection = ((zziu) collection).zzhs();
        }
        boolean zAddAll = this.zzzz.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zzek();
        this.zzzz.clear();
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final void zzc(zzgs zzgsVar) {
        zzek();
        this.zzzz.add(zzgsVar);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final Object zzbt(int i) {
        return this.zzzz.get(i);
    }

    private static String zzm(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgs) {
            return ((zzgs) obj).zzfl();
        }
        return zzie.zzh((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final List<?> zzhs() {
        return Collections.unmodifiableList(this.zzzz);
    }

    @Override // com.google.android.gms.internal.vision.zziu
    public final zziu zzht() {
        return zzei() ? new zzkz(this) : this;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        zzek();
        return zzm(this.zzzz.set(i, (String) obj));
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzek();
        Object objRemove = this.zzzz.remove(i);
        this.modCount++;
        return zzm(objRemove);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, com.google.android.gms.internal.vision.zzik
    public final /* bridge */ /* synthetic */ boolean zzei() {
        return super.zzei();
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzek();
        this.zzzz.add(i, (String) obj);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.vision.zzgi, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.vision.zzik
    public final /* synthetic */ zzik zzan(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzzz);
        return new zziv((ArrayList<Object>) arrayList);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzzz.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgs) {
            zzgs zzgsVar = (zzgs) obj;
            String strZzfl = zzgsVar.zzfl();
            if (zzgsVar.zzfm()) {
                this.zzzz.set(i, strZzfl);
            }
            return strZzfl;
        }
        byte[] bArr = (byte[]) obj;
        String strZzh = zzie.zzh(bArr);
        if (zzie.zzg(bArr)) {
            this.zzzz.set(i, strZzh);
        }
        return strZzh;
    }

    static {
        zziv zzivVar = new zziv();
        zzzx = zzivVar;
        zzivVar.zzej();
        zzzy = zzivVar;
    }
}
