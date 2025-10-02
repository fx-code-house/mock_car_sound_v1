package com.google.android.gms.internal.icing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzep extends zzcp<String> implements zzeo, RandomAccess {
    private static final zzep zzlx;
    private static final zzeo zzly;
    private final List<Object> zzlz;

    public zzep() {
        this(10);
    }

    public zzep(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    private zzep(ArrayList<Object> arrayList) {
        this.zzlz = arrayList;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzlz.size();
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzaj();
        if (collection instanceof zzeo) {
            collection = ((zzeo) collection).zzcd();
        }
        boolean zAddAll = this.zzlz.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zzaj();
        this.zzlz.clear();
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final void zzc(zzct zzctVar) {
        zzaj();
        this.zzlz.add(zzctVar);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final Object zzad(int i) {
        return this.zzlz.get(i);
    }

    private static String zzh(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzct) {
            return ((zzct) obj).zzan();
        }
        return zzeb.zze((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final List<?> zzcd() {
        return Collections.unmodifiableList(this.zzlz);
    }

    @Override // com.google.android.gms.internal.icing.zzeo
    public final zzeo zzce() {
        return zzah() ? new zzgr(this) : this;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        zzaj();
        return zzh(this.zzlz.set(i, (String) obj));
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzaj();
        Object objRemove = this.zzlz.remove(i);
        this.modCount++;
        return zzh(objRemove);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, com.google.android.gms.internal.icing.zzee
    public final /* bridge */ /* synthetic */ boolean zzah() {
        return super.zzah();
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzaj();
        this.zzlz.add(i, (String) obj);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee zzj(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzlz);
        return new zzep((ArrayList<Object>) arrayList);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzlz.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzct) {
            zzct zzctVar = (zzct) obj;
            String strZzan = zzctVar.zzan();
            if (zzctVar.zzao()) {
                this.zzlz.set(i, strZzan);
            }
            return strZzan;
        }
        byte[] bArr = (byte[]) obj;
        String strZze = zzeb.zze(bArr);
        if (zzeb.zzd(bArr)) {
            this.zzlz.set(i, strZze);
        }
        return strZze;
    }

    static {
        zzep zzepVar = new zzep();
        zzlx = zzepVar;
        zzepVar.zzai();
        zzly = zzepVar;
    }
}
