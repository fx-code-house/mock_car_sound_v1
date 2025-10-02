package com.google.android.gms.internal.measurement;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzkl<K, V> implements Comparable<zzkl>, Map.Entry<K, V> {

    /* JADX INFO: Incorrect field signature: TK; */
    private final Comparable zza;
    private V zzb;
    private final /* synthetic */ zzkc zzc;

    zzkl(zzkc zzkcVar, Map.Entry<K, V> entry) {
        this(zzkcVar, (Comparable) entry.getKey(), entry.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    zzkl(zzkc zzkcVar, K k, V v) {
        this.zzc = zzkcVar;
        this.zza = k;
        this.zzb = v;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.zzb;
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zzc.zzf();
        V v2 = this.zzb;
        this.zzb = v;
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
        return zza(this.zza, entry.getKey()) && zza(this.zzb, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.zza;
        int iHashCode = comparable == null ? 0 : comparable.hashCode();
        V v = this.zzb;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        return new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(strValueOf2).length()).append(strValueOf).append("=").append(strValueOf2).toString();
    }

    private static boolean zza(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zza;
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzkl zzklVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzklVar.getKey());
    }
}
