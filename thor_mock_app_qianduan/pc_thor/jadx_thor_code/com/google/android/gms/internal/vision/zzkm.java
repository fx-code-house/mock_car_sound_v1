package com.google.android.gms.internal.vision;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkm<K, V> implements Comparable<zzkm>, Map.Entry<K, V> {
    private V value;
    private final /* synthetic */ zzkh zzabx;

    /* JADX INFO: Incorrect field signature: TK; */
    private final Comparable zzacb;

    zzkm(zzkh zzkhVar, Map.Entry<K, V> entry) {
        this(zzkhVar, (Comparable) entry.getKey(), entry.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    zzkm(zzkh zzkhVar, K k, V v) {
        this.zzabx = zzkhVar;
        this.zzacb = k;
        this.value = v;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zzabx.zziv();
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
        return equals(this.zzacb, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.zzacb;
        int iHashCode = comparable == null ? 0 : comparable.hashCode();
        V v = this.value;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzacb);
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
        return this.zzacb;
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzkm zzkmVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzkmVar.getKey());
    }
}
