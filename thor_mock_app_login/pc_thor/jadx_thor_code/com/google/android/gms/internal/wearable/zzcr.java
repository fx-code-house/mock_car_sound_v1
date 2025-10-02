package com.google.android.gms.internal.wearable;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzcr<K, V> extends LinkedHashMap<K, V> {
    private static final zzcr zzb;
    private boolean zza;

    static {
        zzcr zzcrVar = new zzcr();
        zzb = zzcrVar;
        zzcrVar.zza = false;
    }

    private zzcr() {
        this.zza = true;
    }

    public static <K, V> zzcr<K, V> zza() {
        return zzb;
    }

    private static int zzf(Object obj) {
        if (obj instanceof byte[]) {
            return zzca.zzg((byte[]) obj);
        }
        if (obj instanceof zzbu) {
            throw new UnsupportedOperationException();
        }
        return obj.hashCode();
    }

    private final void zzg() {
        if (!this.zza) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void clear() {
        zzg();
        super.clear();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (this == map) {
            return true;
        }
        if (size() != map.size()) {
            return false;
        }
        for (Map.Entry<K, V> entry : entrySet()) {
            if (!map.containsKey(entry.getKey())) {
                return false;
            }
            V value = entry.getValue();
            Object obj2 = map.get(entry.getKey());
            if (!(((value instanceof byte[]) && (obj2 instanceof byte[])) ? Arrays.equals((byte[]) value, (byte[]) obj2) : value.equals(obj2))) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        int iZzf = 0;
        for (Map.Entry<K, V> entry : entrySet()) {
            iZzf += zzf(entry.getValue()) ^ zzf(entry.getKey());
        }
        return iZzf;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final V put(K k, V v) {
        zzg();
        zzca.zza(k);
        zzca.zza(v);
        return (V) super.put(k, v);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void putAll(Map<? extends K, ? extends V> map) {
        zzg();
        for (K k : map.keySet()) {
            zzca.zza(k);
            zzca.zza(map.get(k));
        }
        super.putAll(map);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final V remove(Object obj) {
        zzg();
        return (V) super.remove(obj);
    }

    public final void zzb(zzcr<K, V> zzcrVar) {
        zzg();
        if (zzcrVar.isEmpty()) {
            return;
        }
        putAll(zzcrVar);
    }

    public final zzcr<K, V> zzc() {
        return isEmpty() ? new zzcr<>() : new zzcr<>(this);
    }

    public final void zzd() {
        this.zza = false;
    }

    public final boolean zze() {
        return this.zza;
    }

    private zzcr(Map<K, V> map) {
        super(map);
        this.zza = true;
    }
}
