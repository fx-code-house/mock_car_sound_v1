package com.google.android.gms.internal.vision;

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

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
class zzkh<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int zzabr;
    private List<zzkm> zzabs;
    private Map<K, V> zzabt;
    private volatile zzko zzabu;
    private Map<K, V> zzabv;
    private volatile zzki zzabw;
    private boolean zzuy;

    static <FieldDescriptorType extends zzhv<FieldDescriptorType>> zzkh<FieldDescriptorType, Object> zzcb(int i) {
        return new zzkg(i);
    }

    private zzkh(int i) {
        this.zzabr = i;
        this.zzabs = Collections.emptyList();
        this.zzabt = Collections.emptyMap();
        this.zzabv = Collections.emptyMap();
    }

    public void zzej() {
        Map<K, V> mapUnmodifiableMap;
        Map<K, V> mapUnmodifiableMap2;
        if (this.zzuy) {
            return;
        }
        if (this.zzabt.isEmpty()) {
            mapUnmodifiableMap = Collections.emptyMap();
        } else {
            mapUnmodifiableMap = Collections.unmodifiableMap(this.zzabt);
        }
        this.zzabt = mapUnmodifiableMap;
        if (this.zzabv.isEmpty()) {
            mapUnmodifiableMap2 = Collections.emptyMap();
        } else {
            mapUnmodifiableMap2 = Collections.unmodifiableMap(this.zzabv);
        }
        this.zzabv = mapUnmodifiableMap2;
        this.zzuy = true;
    }

    public final boolean isImmutable() {
        return this.zzuy;
    }

    public final int zzis() {
        return this.zzabs.size();
    }

    public final Map.Entry<K, V> zzcc(int i) {
        return this.zzabs.get(i);
    }

    public final Iterable<Map.Entry<K, V>> zzit() {
        if (this.zzabt.isEmpty()) {
            return zzkl.zziy();
        }
        return this.zzabt.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzabs.size() + this.zzabt.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzkh<K, V>) comparable) >= 0 || this.zzabt.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzkh<K, V>) comparable);
        if (iZza >= 0) {
            return (V) this.zzabs.get(iZza).getValue();
        }
        return this.zzabt.get(comparable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final V zza(K k, V v) {
        zziv();
        int iZza = zza((zzkh<K, V>) k);
        if (iZza >= 0) {
            return (V) this.zzabs.get(iZza).setValue(v);
        }
        zziv();
        if (this.zzabs.isEmpty() && !(this.zzabs instanceof ArrayList)) {
            this.zzabs = new ArrayList(this.zzabr);
        }
        int i = -(iZza + 1);
        if (i >= this.zzabr) {
            return zziw().put(k, v);
        }
        int size = this.zzabs.size();
        int i2 = this.zzabr;
        if (size == i2) {
            zzkm zzkmVarRemove = this.zzabs.remove(i2 - 1);
            zziw().put((Comparable) zzkmVarRemove.getKey(), zzkmVarRemove.getValue());
        }
        this.zzabs.add(i, new zzkm(this, k, v));
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zziv();
        if (!this.zzabs.isEmpty()) {
            this.zzabs.clear();
        }
        if (this.zzabt.isEmpty()) {
            return;
        }
        this.zzabt.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zziv();
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zzkh<K, V>) comparable);
        if (iZza >= 0) {
            return zzcd(iZza);
        }
        if (this.zzabt.isEmpty()) {
            return null;
        }
        return this.zzabt.remove(comparable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzcd(int i) {
        zziv();
        V v = (V) this.zzabs.remove(i).getValue();
        if (!this.zzabt.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zziw().entrySet().iterator();
            this.zzabs.add(new zzkm(this, it.next()));
            it.remove();
        }
        return v;
    }

    private final int zza(K k) {
        int size = this.zzabs.size() - 1;
        if (size >= 0) {
            int iCompareTo = k.compareTo((Comparable) this.zzabs.get(size).getKey());
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
            int iCompareTo2 = k.compareTo((Comparable) this.zzabs.get(i2).getKey());
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
        if (this.zzabu == null) {
            this.zzabu = new zzko(this, null);
        }
        return this.zzabu;
    }

    final Set<Map.Entry<K, V>> zziu() {
        if (this.zzabw == null) {
            this.zzabw = new zzki(this, null);
        }
        return this.zzabw;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zziv() {
        if (this.zzuy) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zziw() {
        zziv();
        if (this.zzabt.isEmpty() && !(this.zzabt instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zzabt = treeMap;
            this.zzabv = treeMap.descendingMap();
        }
        return (SortedMap) this.zzabt;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzkh)) {
            return super.equals(obj);
        }
        zzkh zzkhVar = (zzkh) obj;
        int size = size();
        if (size != zzkhVar.size()) {
            return false;
        }
        int iZzis = zzis();
        if (iZzis != zzkhVar.zzis()) {
            return entrySet().equals(zzkhVar.entrySet());
        }
        for (int i = 0; i < iZzis; i++) {
            if (!zzcc(i).equals(zzkhVar.zzcc(i))) {
                return false;
            }
        }
        if (iZzis != size) {
            return this.zzabt.equals(zzkhVar.zzabt);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzis = zzis();
        int iHashCode = 0;
        for (int i = 0; i < iZzis; i++) {
            iHashCode += this.zzabs.get(i).hashCode();
        }
        return this.zzabt.size() > 0 ? iHashCode + this.zzabt.hashCode() : iHashCode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzkh<K, V>) obj, (Comparable) obj2);
    }

    /* synthetic */ zzkh(int i, zzkg zzkgVar) {
        this(i);
    }
}
