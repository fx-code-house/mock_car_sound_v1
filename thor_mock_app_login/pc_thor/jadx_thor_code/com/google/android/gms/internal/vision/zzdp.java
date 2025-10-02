package com.google.android.gms.internal.vision;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzdp<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Object zzmf = new Object();
    private transient int size;

    @NullableDecl
    private transient Object zzmg;

    @NullableDecl
    transient int[] zzmh;

    @NullableDecl
    transient Object[] zzmi;

    @NullableDecl
    transient Object[] zzmj;
    private transient int zzmk;

    @NullableDecl
    private transient Set<K> zzml;

    @NullableDecl
    private transient Set<Map.Entry<K, V>> zzmm;

    @NullableDecl
    private transient Collection<V> zzmn;

    zzdp() {
        zzde.checkArgument(true, "Expected size must be >= 0");
        this.zzmk = zzfc.zzc(3, 1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }

    static int zzg(int i, int i2) {
        return i - 1;
    }

    final boolean zzce() {
        return this.zzmg == null;
    }

    @NullableDecl
    final Map<K, V> zzcf() {
        Object obj = this.zzmg;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    private final void zzs(int i) {
        this.zzmk = zzea.zzb(this.zzmk, 32 - Integer.numberOfLeadingZeros(i), 31);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zzcg() {
        return (1 << (this.zzmk & 31)) - 1;
    }

    final void zzch() {
        this.zzmk += 32;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public final V put(@NullableDecl K k, @NullableDecl V v) {
        int iMin;
        if (zzce()) {
            zzde.checkState(zzce(), "Arrays already allocated");
            int i = this.zzmk;
            int iMax = Math.max(i + 1, 2);
            int iHighestOneBit = Integer.highestOneBit(iMax);
            int iMax2 = Math.max(4, (iMax <= ((int) (((double) iHighestOneBit) * 1.0d)) || (iHighestOneBit = iHighestOneBit << 1) > 0) ? iHighestOneBit : 1073741824);
            this.zzmg = zzea.zzv(iMax2);
            zzs(iMax2 - 1);
            this.zzmh = new int[i];
            this.zzmi = new Object[i];
            this.zzmj = new Object[i];
        }
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.put(k, v);
        }
        int[] iArr = this.zzmh;
        Object[] objArr = this.zzmi;
        Object[] objArr2 = this.zzmj;
        int i2 = this.size;
        int i3 = i2 + 1;
        int iZzf = zzec.zzf(k);
        int iZzcg = zzcg();
        int i4 = iZzf & iZzcg;
        int iZza = zzea.zza(this.zzmg, i4);
        if (iZza != 0) {
            int i5 = ~iZzcg;
            int i6 = iZzf & i5;
            int i7 = 0;
            while (true) {
                int i8 = iZza - 1;
                int i9 = iArr[i8];
                if ((i9 & i5) == i6 && zzcz.equal(k, objArr[i8])) {
                    V v2 = (V) objArr2[i8];
                    objArr2[i8] = v;
                    return v2;
                }
                int i10 = i9 & iZzcg;
                Object[] objArr3 = objArr;
                int i11 = i7 + 1;
                if (i10 != 0) {
                    i7 = i11;
                    iZza = i10;
                    objArr = objArr3;
                } else {
                    if (i11 >= 9) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap(zzcg() + 1, 1.0f);
                        int iZzci = zzci();
                        while (iZzci >= 0) {
                            linkedHashMap.put(this.zzmi[iZzci], this.zzmj[iZzci]);
                            iZzci = zzt(iZzci);
                        }
                        this.zzmg = linkedHashMap;
                        this.zzmh = null;
                        this.zzmi = null;
                        this.zzmj = null;
                        zzch();
                        return (V) linkedHashMap.put(k, v);
                    }
                    if (i3 > iZzcg) {
                        iZzcg = zza(iZzcg, zzea.zzw(iZzcg), iZzf, i2);
                    } else {
                        iArr[i8] = zzea.zzb(i9, i3, iZzcg);
                    }
                }
            }
        } else if (i3 > iZzcg) {
            iZzcg = zza(iZzcg, zzea.zzw(iZzcg), iZzf, i2);
        } else {
            zzea.zza(this.zzmg, i4, i3);
        }
        int length = this.zzmh.length;
        if (i3 > length && (iMin = Math.min(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, 1 | (Math.max(1, length >>> 1) + length))) != length) {
            this.zzmh = Arrays.copyOf(this.zzmh, iMin);
            this.zzmi = Arrays.copyOf(this.zzmi, iMin);
            this.zzmj = Arrays.copyOf(this.zzmj, iMin);
        }
        this.zzmh[i2] = zzea.zzb(iZzf, 0, iZzcg);
        this.zzmi[i2] = k;
        this.zzmj[i2] = v;
        this.size = i3;
        zzch();
        return null;
    }

    private final int zza(int i, int i2, int i3, int i4) {
        Object objZzv = zzea.zzv(i2);
        int i5 = i2 - 1;
        if (i4 != 0) {
            zzea.zza(objZzv, i3 & i5, i4 + 1);
        }
        Object obj = this.zzmg;
        int[] iArr = this.zzmh;
        for (int i6 = 0; i6 <= i; i6++) {
            int iZza = zzea.zza(obj, i6);
            while (iZza != 0) {
                int i7 = iZza - 1;
                int i8 = iArr[i7];
                int i9 = ((~i) & i8) | i6;
                int i10 = i9 & i5;
                int iZza2 = zzea.zza(objZzv, i10);
                zzea.zza(objZzv, i10, iZza);
                iArr[i7] = zzea.zzb(i9, iZza2, i5);
                iZza = i8 & i;
            }
        }
        this.zzmg = objZzv;
        zzs(i5);
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int indexOf(@NullableDecl Object obj) {
        if (zzce()) {
            return -1;
        }
        int iZzf = zzec.zzf(obj);
        int iZzcg = zzcg();
        int iZza = zzea.zza(this.zzmg, iZzf & iZzcg);
        if (iZza == 0) {
            return -1;
        }
        int i = ~iZzcg;
        int i2 = iZzf & i;
        do {
            int i3 = iZza - 1;
            int i4 = this.zzmh[i3];
            if ((i4 & i) == i2 && zzcz.equal(obj, this.zzmi[i3])) {
                return i3;
            }
            iZza = i4 & iZzcg;
        } while (iZza != 0);
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(@NullableDecl Object obj) {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.containsKey(obj);
        }
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V get(@NullableDecl Object obj) {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.get(obj);
        }
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return null;
        }
        return (V) this.zzmj[iIndexOf];
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public final V remove(@NullableDecl Object obj) {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.remove(obj);
        }
        V v = (V) zze(obj);
        if (v == zzmf) {
            return null;
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NullableDecl
    public final Object zze(@NullableDecl Object obj) {
        if (zzce()) {
            return zzmf;
        }
        int iZzcg = zzcg();
        int iZza = zzea.zza(obj, null, iZzcg, this.zzmg, this.zzmh, this.zzmi, null);
        if (iZza == -1) {
            return zzmf;
        }
        Object obj2 = this.zzmj[iZza];
        zzf(iZza, iZzcg);
        this.size--;
        zzch();
        return obj2;
    }

    final void zzf(int i, int i2) {
        int size = size() - 1;
        if (i < size) {
            Object[] objArr = this.zzmi;
            Object obj = objArr[size];
            objArr[i] = obj;
            Object[] objArr2 = this.zzmj;
            objArr2[i] = objArr2[size];
            objArr[size] = null;
            objArr2[size] = null;
            int[] iArr = this.zzmh;
            iArr[i] = iArr[size];
            iArr[size] = 0;
            int iZzf = zzec.zzf(obj) & i2;
            int iZza = zzea.zza(this.zzmg, iZzf);
            int i3 = size + 1;
            if (iZza == i3) {
                zzea.zza(this.zzmg, iZzf, i + 1);
                return;
            }
            while (true) {
                int i4 = iZza - 1;
                int[] iArr2 = this.zzmh;
                int i5 = iArr2[i4];
                int i6 = i5 & i2;
                if (i6 == i3) {
                    iArr2[i4] = zzea.zzb(i5, i + 1, i2);
                    return;
                }
                iZza = i6;
            }
        } else {
            this.zzmi[i] = null;
            this.zzmj[i] = null;
            this.zzmh[i] = 0;
        }
    }

    final int zzci() {
        return isEmpty() ? -1 : 0;
    }

    final int zzt(int i) {
        int i2 = i + 1;
        if (i2 < this.size) {
            return i2;
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        Set<K> set = this.zzml;
        if (set != null) {
            return set;
        }
        zzdv zzdvVar = new zzdv(this);
        this.zzml = zzdvVar;
        return zzdvVar;
    }

    final Iterator<K> zzcj() {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.keySet().iterator();
        }
        return new zzds(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.zzmm;
        if (set != null) {
            return set;
        }
        zzdt zzdtVar = new zzdt(this);
        this.zzmm = zzdtVar;
        return zzdtVar;
    }

    final Iterator<Map.Entry<K, V>> zzck() {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.entrySet().iterator();
        }
        return new zzdr(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        Map<K, V> mapZzcf = zzcf();
        return mapZzcf != null ? mapZzcf.size() : this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(@NullableDecl Object obj) {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.containsValue(obj);
        }
        for (int i = 0; i < this.size; i++) {
            if (zzcz.equal(obj, this.zzmj[i])) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzmn;
        if (collection != null) {
            return collection;
        }
        zzdx zzdxVar = new zzdx(this);
        this.zzmn = zzdxVar;
        return zzdxVar;
    }

    final Iterator<V> zzcl() {
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            return mapZzcf.values().iterator();
        }
        return new zzdu(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (zzce()) {
            return;
        }
        zzch();
        Map<K, V> mapZzcf = zzcf();
        if (mapZzcf != null) {
            this.zzmk = zzfc.zzc(size(), 3, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
            mapZzcf.clear();
            this.zzmg = null;
            this.size = 0;
            return;
        }
        Arrays.fill(this.zzmi, 0, this.size, (Object) null);
        Arrays.fill(this.zzmj, 0, this.size, (Object) null);
        Object obj = this.zzmg;
        if (obj instanceof byte[]) {
            Arrays.fill((byte[]) obj, (byte) 0);
        } else if (obj instanceof short[]) {
            Arrays.fill((short[]) obj, (short) 0);
        } else {
            Arrays.fill((int[]) obj, 0);
        }
        Arrays.fill(this.zzmh, 0, this.size, 0);
        this.size = 0;
    }

    static /* synthetic */ int zzd(zzdp zzdpVar) {
        int i = zzdpVar.size;
        zzdpVar.size = i - 1;
        return i;
    }
}
