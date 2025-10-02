package com.google.android.gms.internal.measurement;

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

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzem<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Object zzd = new Object();

    @NullableDecl
    transient int[] zza;

    @NullableDecl
    transient Object[] zzb;

    @NullableDecl
    transient Object[] zzc;

    @NullableDecl
    private transient Object zze;
    private transient int zzf;
    private transient int zzg;

    @NullableDecl
    private transient Set<K> zzh;

    @NullableDecl
    private transient Set<Map.Entry<K, V>> zzi;

    @NullableDecl
    private transient Collection<V> zzj;

    zzem() {
        zzeb.zza(true, (Object) "Expected size must be >= 0");
        this.zzf = zzfz.zza(3, 1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }

    static int zzb(int i, int i2) {
        return i - 1;
    }

    final boolean zza() {
        return this.zze == null;
    }

    @NullableDecl
    final Map<K, V> zzb() {
        Object obj = this.zze;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    private final void zzb(int i) {
        this.zzf = zzex.zza(this.zzf, 32 - Integer.numberOfLeadingZeros(i), 31);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zzi() {
        return (1 << (this.zzf & 31)) - 1;
    }

    final void zzc() {
        this.zzf += 32;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public final V put(@NullableDecl K k, @NullableDecl V v) {
        int iMin;
        if (zza()) {
            zzeb.zzb(zza(), "Arrays already allocated");
            int i = this.zzf;
            int iMax = Math.max(i + 1, 2);
            int iHighestOneBit = Integer.highestOneBit(iMax);
            int iMax2 = Math.max(4, (iMax <= ((int) (((double) iHighestOneBit) * 1.0d)) || (iHighestOneBit = iHighestOneBit << 1) > 0) ? iHighestOneBit : 1073741824);
            this.zze = zzex.zza(iMax2);
            zzb(iMax2 - 1);
            this.zza = new int[i];
            this.zzb = new Object[i];
            this.zzc = new Object[i];
        }
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.put(k, v);
        }
        int[] iArr = this.zza;
        Object[] objArr = this.zzb;
        Object[] objArr2 = this.zzc;
        int i2 = this.zzg;
        int i3 = i2 + 1;
        int iZza = zzez.zza(k);
        int iZzi = zzi();
        int i4 = iZza & iZzi;
        int iZza2 = zzex.zza(this.zze, i4);
        if (iZza2 != 0) {
            int i5 = ~iZzi;
            int i6 = iZza & i5;
            int i7 = 0;
            while (true) {
                int i8 = iZza2 - 1;
                int i9 = iArr[i8];
                if ((i9 & i5) == i6 && zzdz.zza(k, objArr[i8])) {
                    V v2 = (V) objArr2[i8];
                    objArr2[i8] = v;
                    return v2;
                }
                int i10 = i9 & iZzi;
                Object[] objArr3 = objArr;
                int i11 = i7 + 1;
                if (i10 != 0) {
                    i7 = i11;
                    iZza2 = i10;
                    objArr = objArr3;
                } else {
                    if (i11 >= 9) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap(zzi() + 1, 1.0f);
                        int iZzd = zzd();
                        while (iZzd >= 0) {
                            linkedHashMap.put(this.zzb[iZzd], this.zzc[iZzd]);
                            iZzd = zza(iZzd);
                        }
                        this.zze = linkedHashMap;
                        this.zza = null;
                        this.zzb = null;
                        this.zzc = null;
                        zzc();
                        return (V) linkedHashMap.put(k, v);
                    }
                    if (i3 > iZzi) {
                        iZzi = zza(iZzi, zzex.zzb(iZzi), iZza, i2);
                    } else {
                        iArr[i8] = zzex.zza(i9, i3, iZzi);
                    }
                }
            }
        } else if (i3 > iZzi) {
            iZzi = zza(iZzi, zzex.zzb(iZzi), iZza, i2);
        } else {
            zzex.zza(this.zze, i4, i3);
        }
        int length = this.zza.length;
        if (i3 > length && (iMin = Math.min(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, 1 | (Math.max(1, length >>> 1) + length))) != length) {
            this.zza = Arrays.copyOf(this.zza, iMin);
            this.zzb = Arrays.copyOf(this.zzb, iMin);
            this.zzc = Arrays.copyOf(this.zzc, iMin);
        }
        this.zza[i2] = zzex.zza(iZza, 0, iZzi);
        this.zzb[i2] = k;
        this.zzc[i2] = v;
        this.zzg = i3;
        zzc();
        return null;
    }

    private final int zza(int i, int i2, int i3, int i4) {
        Object objZza = zzex.zza(i2);
        int i5 = i2 - 1;
        if (i4 != 0) {
            zzex.zza(objZza, i3 & i5, i4 + 1);
        }
        Object obj = this.zze;
        int[] iArr = this.zza;
        for (int i6 = 0; i6 <= i; i6++) {
            int iZza = zzex.zza(obj, i6);
            while (iZza != 0) {
                int i7 = iZza - 1;
                int i8 = iArr[i7];
                int i9 = ((~i) & i8) | i6;
                int i10 = i9 & i5;
                int iZza2 = zzex.zza(objZza, i10);
                zzex.zza(objZza, i10, iZza);
                iArr[i7] = zzex.zza(i9, iZza2, i5);
                iZza = i8 & i;
            }
        }
        this.zze = objZza;
        zzb(i5);
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zza(@NullableDecl Object obj) {
        if (zza()) {
            return -1;
        }
        int iZza = zzez.zza(obj);
        int iZzi = zzi();
        int iZza2 = zzex.zza(this.zze, iZza & iZzi);
        if (iZza2 == 0) {
            return -1;
        }
        int i = ~iZzi;
        int i2 = iZza & i;
        do {
            int i3 = iZza2 - 1;
            int i4 = this.zza[i3];
            if ((i4 & i) == i2 && zzdz.zza(obj, this.zzb[i3])) {
                return i3;
            }
            iZza2 = i4 & iZzi;
        } while (iZza2 != 0);
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(@NullableDecl Object obj) {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.containsKey(obj);
        }
        return zza(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V get(@NullableDecl Object obj) {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.get(obj);
        }
        int iZza = zza(obj);
        if (iZza == -1) {
            return null;
        }
        return (V) this.zzc[iZza];
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public final V remove(@NullableDecl Object obj) {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.remove(obj);
        }
        V v = (V) zzb(obj);
        if (v == zzd) {
            return null;
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NullableDecl
    public final Object zzb(@NullableDecl Object obj) {
        if (zza()) {
            return zzd;
        }
        int iZzi = zzi();
        int iZza = zzex.zza(obj, null, iZzi, this.zze, this.zza, this.zzb, null);
        if (iZza == -1) {
            return zzd;
        }
        Object obj2 = this.zzc[iZza];
        zza(iZza, iZzi);
        this.zzg--;
        zzc();
        return obj2;
    }

    final void zza(int i, int i2) {
        int size = size() - 1;
        if (i < size) {
            Object[] objArr = this.zzb;
            Object obj = objArr[size];
            objArr[i] = obj;
            Object[] objArr2 = this.zzc;
            objArr2[i] = objArr2[size];
            objArr[size] = null;
            objArr2[size] = null;
            int[] iArr = this.zza;
            iArr[i] = iArr[size];
            iArr[size] = 0;
            int iZza = zzez.zza(obj) & i2;
            int iZza2 = zzex.zza(this.zze, iZza);
            int i3 = size + 1;
            if (iZza2 == i3) {
                zzex.zza(this.zze, iZza, i + 1);
                return;
            }
            while (true) {
                int i4 = iZza2 - 1;
                int[] iArr2 = this.zza;
                int i5 = iArr2[i4];
                int i6 = i5 & i2;
                if (i6 == i3) {
                    iArr2[i4] = zzex.zza(i5, i + 1, i2);
                    return;
                }
                iZza2 = i6;
            }
        } else {
            this.zzb[i] = null;
            this.zzc[i] = null;
            this.zza[i] = 0;
        }
    }

    final int zzd() {
        return isEmpty() ? -1 : 0;
    }

    final int zza(int i) {
        int i2 = i + 1;
        if (i2 < this.zzg) {
            return i2;
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        Set<K> set = this.zzh;
        if (set != null) {
            return set;
        }
        zzes zzesVar = new zzes(this);
        this.zzh = zzesVar;
        return zzesVar;
    }

    final Iterator<K> zze() {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.keySet().iterator();
        }
        return new zzep(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.zzi;
        if (set != null) {
            return set;
        }
        zzeq zzeqVar = new zzeq(this);
        this.zzi = zzeqVar;
        return zzeqVar;
    }

    final Iterator<Map.Entry<K, V>> zzf() {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.entrySet().iterator();
        }
        return new zzeo(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        Map<K, V> mapZzb = zzb();
        return mapZzb != null ? mapZzb.size() : this.zzg;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(@NullableDecl Object obj) {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.containsValue(obj);
        }
        for (int i = 0; i < this.zzg; i++) {
            if (zzdz.zza(obj, this.zzc[i])) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzj;
        if (collection != null) {
            return collection;
        }
        zzeu zzeuVar = new zzeu(this);
        this.zzj = zzeuVar;
        return zzeuVar;
    }

    final Iterator<V> zzg() {
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            return mapZzb.values().iterator();
        }
        return new zzer(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (zza()) {
            return;
        }
        zzc();
        Map<K, V> mapZzb = zzb();
        if (mapZzb != null) {
            this.zzf = zzfz.zza(size(), 3, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
            mapZzb.clear();
            this.zze = null;
            this.zzg = 0;
            return;
        }
        Arrays.fill(this.zzb, 0, this.zzg, (Object) null);
        Arrays.fill(this.zzc, 0, this.zzg, (Object) null);
        Object obj = this.zze;
        if (obj instanceof byte[]) {
            Arrays.fill((byte[]) obj, (byte) 0);
        } else if (obj instanceof short[]) {
            Arrays.fill((short[]) obj, (short) 0);
        } else {
            Arrays.fill((int[]) obj, 0);
        }
        Arrays.fill(this.zza, 0, this.zzg, 0);
        this.zzg = 0;
    }

    static /* synthetic */ int zzd(zzem zzemVar) {
        int i = zzemVar.zzg;
        zzemVar.zzg = i - 1;
        return i;
    }
}
