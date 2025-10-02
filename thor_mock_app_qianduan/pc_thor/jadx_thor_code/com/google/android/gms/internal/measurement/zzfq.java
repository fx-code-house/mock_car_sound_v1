package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfq<K> extends zzfg<K> {
    private final transient zzfc<K, ?> zza;
    private final transient zzfb<K> zzb;

    zzfq(zzfc<K, ?> zzfcVar, zzfb<K> zzfbVar) {
        this.zza = zzfcVar;
        this.zzb = zzfbVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    final boolean zzf() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    /* renamed from: zza */
    public final zzfx<K> iterator() {
        return (zzfx) zze().iterator();
    }

    @Override // com.google.android.gms.internal.measurement.zzey
    final int zza(Object[] objArr, int i) {
        return zze().zza(objArr, i);
    }

    @Override // com.google.android.gms.internal.measurement.zzfg, com.google.android.gms.internal.measurement.zzey
    public final zzfb<K> zze() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzey, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@NullableDecl Object obj) {
        return this.zza.get(obj) != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.measurement.zzfg, com.google.android.gms.internal.measurement.zzey, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
