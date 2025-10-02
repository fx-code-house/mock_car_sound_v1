package com.google.android.gms.internal.clearcut;

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

/* loaded from: classes2.dex */
class zzei<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzgu;
    private final int zzol;
    private List<zzep> zzom;
    private Map<K, V> zzon;
    private volatile zzer zzoo;
    private Map<K, V> zzop;
    private volatile zzel zzoq;

    private zzei(int i) {
        this.zzol = i;
        this.zzom = Collections.emptyList();
        this.zzon = Collections.emptyMap();
        this.zzop = Collections.emptyMap();
    }

    /* synthetic */ zzei(int i, zzej zzejVar) {
        this(i);
    }

    private final int zza(K k) {
        int size = this.zzom.size() - 1;
        if (size >= 0) {
            int iCompareTo = k.compareTo((Comparable) this.zzom.get(size).getKey());
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
            int iCompareTo2 = k.compareTo((Comparable) this.zzom.get(i2).getKey());
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

    static <FieldDescriptorType extends zzca<FieldDescriptorType>> zzei<FieldDescriptorType, Object> zzaj(int i) {
        return new zzej(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzal(int i) {
        zzdu();
        V v = (V) this.zzom.remove(i).getValue();
        if (!this.zzon.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzdv().entrySet().iterator();
            this.zzom.add(new zzep(this, it.next()));
            it.remove();
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzdu() {
        if (this.zzgu) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzdv() {
        zzdu();
        if (this.zzon.isEmpty() && !(this.zzon instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zzon = treeMap;
            this.zzop = treeMap.descendingMap();
        }
        return (SortedMap) this.zzon;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzdu();
        if (!this.zzom.isEmpty()) {
            this.zzom.clear();
        }
        if (this.zzon.isEmpty()) {
            return;
        }
        this.zzon.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzei<K, V>) comparable) >= 0 || this.zzon.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzoo == null) {
            this.zzoo = new zzer(this, null);
        }
        return this.zzoo;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        Object objEntrySet;
        Object objEntrySet2;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzei)) {
            return super.equals(obj);
        }
        zzei zzeiVar = (zzei) obj;
        int size = size();
        if (size != zzeiVar.size()) {
            return false;
        }
        int iZzdr = zzdr();
        if (iZzdr != zzeiVar.zzdr()) {
            objEntrySet = entrySet();
            objEntrySet2 = zzeiVar.entrySet();
        } else {
            for (int i = 0; i < iZzdr; i++) {
                if (!zzak(i).equals(zzeiVar.zzak(i))) {
                    return false;
                }
            }
            if (iZzdr == size) {
                return true;
            }
            objEntrySet = this.zzon;
            objEntrySet2 = zzeiVar.zzon;
        }
        return objEntrySet.equals(objEntrySet2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzei<K, V>) comparable);
        return iZza >= 0 ? (V) this.zzom.get(iZza).getValue() : this.zzon.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzdr = zzdr();
        int iHashCode = 0;
        for (int i = 0; i < iZzdr; i++) {
            iHashCode += this.zzom.get(i).hashCode();
        }
        return this.zzon.size() > 0 ? iHashCode + this.zzon.hashCode() : iHashCode;
    }

    public final boolean isImmutable() {
        return this.zzgu;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzei<K, V>) obj, (Comparable) obj2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzdu();
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzei<K, V>) comparable);
        if (iZza >= 0) {
            return zzal(iZza);
        }
        if (this.zzon.isEmpty()) {
            return null;
        }
        return this.zzon.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzom.size() + this.zzon.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final V zza(K k, V v) {
        zzdu();
        int iZza = zza((zzei<K, V>) k);
        if (iZza >= 0) {
            return (V) this.zzom.get(iZza).setValue(v);
        }
        zzdu();
        if (this.zzom.isEmpty() && !(this.zzom instanceof ArrayList)) {
            this.zzom = new ArrayList(this.zzol);
        }
        int i = -(iZza + 1);
        if (i >= this.zzol) {
            return zzdv().put(k, v);
        }
        int size = this.zzom.size();
        int i2 = this.zzol;
        if (size == i2) {
            zzep zzepVarRemove = this.zzom.remove(i2 - 1);
            zzdv().put((Comparable) zzepVarRemove.getKey(), zzepVarRemove.getValue());
        }
        this.zzom.add(i, new zzep(this, k, v));
        return null;
    }

    public final Map.Entry<K, V> zzak(int i) {
        return this.zzom.get(i);
    }

    public final int zzdr() {
        return this.zzom.size();
    }

    public final Iterable<Map.Entry<K, V>> zzds() {
        return this.zzon.isEmpty() ? zzem.zzdx() : this.zzon.entrySet();
    }

    final Set<Map.Entry<K, V>> zzdt() {
        if (this.zzoq == null) {
            this.zzoq = new zzel(this, null);
        }
        return this.zzoq;
    }

    public void zzv() {
        if (this.zzgu) {
            return;
        }
        this.zzon = this.zzon.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzon);
        this.zzop = this.zzop.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzop);
        this.zzgu = true;
    }
}
