package com.google.android.gms.internal.measurement;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzeq<K, V> extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzem zza;

    zzeq(zzem zzemVar) {
        this.zza = zzemVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return this.zza.zzf();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@NullableDecl Object obj) {
        Map<K, V> mapZzb = this.zza.zzb();
        if (mapZzb != null) {
            return mapZzb.entrySet().contains(obj);
        }
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            int iZza = this.zza.zza(entry.getKey());
            if (iZza != -1 && zzdz.zza(this.zza.zzc[iZza], entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(@NullableDecl Object obj) {
        Map<K, V> mapZzb = this.zza.zzb();
        if (mapZzb != null) {
            return mapZzb.entrySet().remove(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.zza.zza()) {
            return false;
        }
        int iZzi = this.zza.zzi();
        int iZza = zzex.zza(entry.getKey(), entry.getValue(), iZzi, this.zza.zze, this.zza.zza, this.zza.zzb, this.zza.zzc);
        if (iZza == -1) {
            return false;
        }
        this.zza.zza(iZza, iZzi);
        zzem.zzd(this.zza);
        this.zza.zzc();
        return true;
    }
}
