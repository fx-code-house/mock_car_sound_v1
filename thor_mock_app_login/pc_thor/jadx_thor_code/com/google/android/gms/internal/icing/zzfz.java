package com.google.android.gms.internal.icing;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
class zzfz<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzhl;
    private final int zznr;
    private List<zzge> zzns;
    private Map<K, V> zznt;
    private volatile zzgg zznu;
    private Map<K, V> zznv;
    private volatile zzga zznw;

    static <FieldDescriptorType extends zzdu<FieldDescriptorType>> zzfz<FieldDescriptorType, Object> zzai(int i) {
        return new zzfy(i);
    }

    private zzfz(int i) {
        this.zznr = i;
        this.zzns = Collections.emptyList();
        this.zznt = Collections.emptyMap();
        this.zznv = Collections.emptyMap();
    }

    public void zzai() {
        Map<K, V> mapUnmodifiableMap;
        Map<K, V> mapUnmodifiableMap2;
        if (this.zzhl) {
            return;
        }
        if (this.zznt.isEmpty()) {
            mapUnmodifiableMap = Collections.emptyMap();
        } else {
            mapUnmodifiableMap = Collections.unmodifiableMap(this.zznt);
        }
        this.zznt = mapUnmodifiableMap;
        if (this.zznv.isEmpty()) {
            mapUnmodifiableMap2 = Collections.emptyMap();
        } else {
            mapUnmodifiableMap2 = Collections.unmodifiableMap(this.zznv);
        }
        this.zznv = mapUnmodifiableMap2;
        this.zzhl = true;
    }

    public final boolean isImmutable() {
        return this.zzhl;
    }

    public final int zzdd() {
        return this.zzns.size();
    }

    public final Map.Entry<K, V> zzaj(int i) {
        return this.zzns.get(i);
    }

    public final Iterable<Map.Entry<K, V>> zzde() {
        if (this.zznt.isEmpty()) {
            return zzgd.zzdj();
        }
        return this.zznt.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzns.size() + this.zznt.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzfz<K, V>) comparable) >= 0 || this.zznt.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzfz<K, V>) comparable);
        if (iZza >= 0) {
            return (V) this.zzns.get(iZza).getValue();
        }
        return this.zznt.get(comparable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final V zza(K k, V v) {
        zzdg();
        int iZza = zza((zzfz<K, V>) k);
        if (iZza >= 0) {
            return (V) this.zzns.get(iZza).setValue(v);
        }
        zzdg();
        if (this.zzns.isEmpty() && !(this.zzns instanceof ArrayList)) {
            this.zzns = new ArrayList(this.zznr);
        }
        int i = -(iZza + 1);
        if (i >= this.zznr) {
            return zzdh().put(k, v);
        }
        int size = this.zzns.size();
        int i2 = this.zznr;
        if (size == i2) {
            zzge zzgeVarRemove = this.zzns.remove(i2 - 1);
            zzdh().put((Comparable) zzgeVarRemove.getKey(), zzgeVarRemove.getValue());
        }
        this.zzns.add(i, new zzge(this, k, v));
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzdg();
        if (!this.zzns.isEmpty()) {
            this.zzns.clear();
        }
        if (this.zznt.isEmpty()) {
            return;
        }
        this.zznt.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzdg();
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzfz<K, V>) comparable);
        if (iZza >= 0) {
            return zzak(iZza);
        }
        if (this.zznt.isEmpty()) {
            return null;
        }
        return this.zznt.remove(comparable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzak(int i) {
        zzdg();
        V v = (V) this.zzns.remove(i).getValue();
        if (!this.zznt.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzdh().entrySet().iterator();
            this.zzns.add(new zzge(this, it.next()));
            it.remove();
        }
        return v;
    }

    private final int zza(K k) {
        int size = this.zzns.size() - 1;
        if (size >= 0) {
            int iCompareTo = k.compareTo((Comparable) this.zzns.get(size).getKey());
            if (iCompareTo > 0) {
                return -(size + 2);
            }
            if (iCompareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int iCompareTo2 = k.compareTo((Comparable) this.zzns.get(i2).getKey());
            if (iCompareTo2 < 0) {
                size = i2 - 1;
            } else {
                if (iCompareTo2 <= 0) {
                    return i2;
                }
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zznu == null) {
            this.zznu = new zzgg(this, null);
        }
        return this.zznu;
    }

    final Set<Map.Entry<K, V>> zzdf() {
        if (this.zznw == null) {
            this.zznw = new zzga(this, null);
        }
        return this.zznw;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzdg() {
        if (this.zzhl) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzdh() {
        zzdg();
        if (this.zznt.isEmpty() && !(this.zznt instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zznt = treeMap;
            this.zznv = treeMap.descendingMap();
        }
        return (SortedMap) this.zznt;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfz)) {
            return super.equals(obj);
        }
        zzfz zzfzVar = (zzfz) obj;
        int size = size();
        if (size != zzfzVar.size()) {
            return false;
        }
        int iZzdd = zzdd();
        if (iZzdd != zzfzVar.zzdd()) {
            return entrySet().equals(zzfzVar.entrySet());
        }
        for (int i = 0; i < iZzdd; i++) {
            if (!zzaj(i).equals(zzfzVar.zzaj(i))) {
                return false;
            }
        }
        if (iZzdd != size) {
            return this.zznt.equals(zzfzVar.zznt);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzdd = zzdd();
        int iHashCode = 0;
        for (int i = 0; i < iZzdd; i++) {
            iHashCode += this.zzns.get(i).hashCode();
        }
        return this.zznt.size() > 0 ? iHashCode + this.zznt.hashCode() : iHashCode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzfz<K, V>) obj, (Comparable) obj2);
    }

    /* synthetic */ zzfz(int i, zzfy zzfyVar) {
        this(i);
    }
}
