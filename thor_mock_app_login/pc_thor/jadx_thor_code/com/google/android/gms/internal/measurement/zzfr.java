package com.google.android.gms.internal.measurement;

import java.util.AbstractMap;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfr<K, V> extends zzfb<Map.Entry<K, V>> {
    private final /* synthetic */ zzfo zza;

    zzfr(zzfo zzfoVar) {
        this.zza = zzfoVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    public final boolean zzf() {
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.zzd;
    }

    @Override // java.util.List
    public final /* synthetic */ Object get(int i) {
        zzeb.zza(i, this.zza.zzd);
        int i2 = i * 2;
        return new AbstractMap.SimpleImmutableEntry(this.zza.zzb[i2], this.zza.zzb[i2 + 1]);
    }
}
