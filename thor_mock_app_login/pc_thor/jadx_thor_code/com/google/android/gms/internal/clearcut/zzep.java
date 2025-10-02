package com.google.android.gms.internal.clearcut;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes2.dex */
final class zzep<K, V> implements Comparable<zzep>, Map.Entry<K, V> {
    private V value;
    private final /* synthetic */ zzei zzos;

    /* JADX INFO: Incorrect field signature: TK; */
    private final Comparable zzov;

    /* JADX WARN: Multi-variable type inference failed */
    zzep(zzei zzeiVar, K k, V v) {
        this.zzos = zzeiVar;
        this.zzov = k;
        this.value = v;
    }

    zzep(zzei zzeiVar, Map.Entry<K, V> entry) {
        this(zzeiVar, (Comparable) entry.getKey(), entry.getValue());
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzep zzepVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzepVar.getKey());
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return equals(this.zzov, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zzov;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.zzov;
        int iHashCode = comparable == null ? 0 : comparable.hashCode();
        V v = this.value;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zzos.zzdu();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzov);
        String strValueOf2 = String.valueOf(this.value);
        return new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(strValueOf2).length()).append(strValueOf).append("=").append(strValueOf2).toString();
    }
}
