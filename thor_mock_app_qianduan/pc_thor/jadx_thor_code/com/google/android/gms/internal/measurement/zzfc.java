package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzfc<K, V> implements Serializable, Map<K, V> {
    private static final Map.Entry<?, ?>[] zza = new Map.Entry[0];
    private transient zzfg<Map.Entry<K, V>> zzb;
    private transient zzfg<K> zzc;
    private transient zzey<V> zzd;

    zzfc() {
    }

    @Override // java.util.Map
    public abstract V get(@NullableDecl Object obj);

    abstract zzfg<Map.Entry<K, V>> zza();

    abstract zzfg<K> zzb();

    abstract zzey<V> zzc();

    @Override // java.util.Map
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        return ((zzey) values()).contains(obj);
    }

    @Override // java.util.Map
    public final V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    @Override // java.util.Map
    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    public int hashCode() {
        return zzfv.zza((zzfg) entrySet());
    }

    public String toString() {
        int size = size();
        if (size < 0) {
            throw new IllegalArgumentException(new StringBuilder("size".length() + 40).append("size cannot be negative but was: ").append(size).toString());
        }
        StringBuilder sbAppend = new StringBuilder((int) Math.min(size << 3, 1073741824L)).append('{');
        boolean z = true;
        for (Map.Entry<K, V> entry : entrySet()) {
            if (!z) {
                sbAppend.append(", ");
            }
            sbAppend.append(entry.getKey()).append('=').append(entry.getValue());
            z = false;
        }
        return sbAppend.append('}').toString();
    }

    @Override // java.util.Map
    public /* synthetic */ Set entrySet() {
        zzfg<Map.Entry<K, V>> zzfgVar = this.zzb;
        if (zzfgVar != null) {
            return zzfgVar;
        }
        zzfg<Map.Entry<K, V>> zzfgVarZza = zza();
        this.zzb = zzfgVarZza;
        return zzfgVarZza;
    }

    @Override // java.util.Map
    public /* synthetic */ Collection values() {
        zzey<V> zzeyVar = this.zzd;
        if (zzeyVar != null) {
            return zzeyVar;
        }
        zzey<V> zzeyVarZzc = zzc();
        this.zzd = zzeyVarZzc;
        return zzeyVarZzc;
    }

    @Override // java.util.Map
    public /* synthetic */ Set keySet() {
        zzfg<K> zzfgVar = this.zzc;
        if (zzfgVar != null) {
            return zzfgVar;
        }
        zzfg<K> zzfgVarZzb = zzb();
        this.zzc = zzfgVarZzb;
        return zzfgVarZzb;
    }
}
