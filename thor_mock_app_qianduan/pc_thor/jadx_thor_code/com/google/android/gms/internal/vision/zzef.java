package com.google.android.gms.internal.vision;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzef<K, V> implements Serializable, Map<K, V> {
    private static final Map.Entry<?, ?>[] zzmx = new Map.Entry[0];
    private transient zzej<Map.Entry<K, V>> zzmy;
    private transient zzej<K> zzmz;
    private transient zzeb<V> zzna;

    zzef() {
    }

    @Override // java.util.Map
    public abstract V get(@NullableDecl Object obj);

    abstract zzej<Map.Entry<K, V>> zzcw();

    abstract zzej<K> zzcx();

    abstract zzeb<V> zzcy();

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
        return ((zzeb) values()).contains(obj);
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
        return zzey.zza((zzej) entrySet());
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
        zzej<Map.Entry<K, V>> zzejVar = this.zzmy;
        if (zzejVar != null) {
            return zzejVar;
        }
        zzej<Map.Entry<K, V>> zzejVarZzcw = zzcw();
        this.zzmy = zzejVarZzcw;
        return zzejVarZzcw;
    }

    @Override // java.util.Map
    public /* synthetic */ Collection values() {
        zzeb<V> zzebVar = this.zzna;
        if (zzebVar != null) {
            return zzebVar;
        }
        zzeb<V> zzebVarZzcy = zzcy();
        this.zzna = zzebVarZzcy;
        return zzebVarZzcy;
    }

    @Override // java.util.Map
    public /* synthetic */ Set keySet() {
        zzej<K> zzejVar = this.zzmz;
        if (zzejVar != null) {
            return zzejVar;
        }
        zzej<K> zzejVarZzcx = zzcx();
        this.zzmz = zzejVarZzcx;
        return zzejVarZzcx;
    }
}
