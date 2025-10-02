package com.google.android.gms.internal.icing;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzge<K, V> implements Comparable<zzge>, Map.Entry<K, V> {
    private V value;
    private final /* synthetic */ zzfz zznx;

    /* JADX INFO: Incorrect field signature: TK; */
    private final Comparable zzob;

    zzge(zzfz zzfzVar, Map.Entry<K, V> entry) {
        this(zzfzVar, (Comparable) entry.getKey(), entry.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    zzge(zzfz zzfzVar, K k, V v) {
        this.zznx = zzfzVar;
        this.zzob = k;
        this.value = v;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zznx.zzdg();
        V v2 = this.value;
        this.value = v;
        return v2;
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
        return equals(this.zzob, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.zzob;
        int iHashCode = comparable == null ? 0 : comparable.hashCode();
        V v = this.value;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzob);
        String strValueOf2 = String.valueOf(this.value);
        return new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(strValueOf2).length()).append(strValueOf).append("=").append(strValueOf2).toString();
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zzob;
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzge zzgeVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzgeVar.getKey());
    }
}
