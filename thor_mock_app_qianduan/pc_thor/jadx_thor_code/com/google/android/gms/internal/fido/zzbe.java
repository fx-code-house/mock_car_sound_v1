package com.google.android.gms.internal.fido;

import java.util.AbstractMap;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzbe extends zzaz {
    final /* synthetic */ zzbf zza;

    zzbe(zzbf zzbfVar) {
        this.zza = zzbfVar;
    }

    @Override // java.util.List
    public final /* bridge */ /* synthetic */ Object get(int i) {
        return new AbstractMap.SimpleImmutableEntry(this.zza.zza.zze.zzd.get(i), this.zza.zza.zzf.get(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.zza.size();
    }
}
