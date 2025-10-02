package com.google.android.gms.internal.fido;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzbf extends zzbb {
    final /* synthetic */ zzbg zza;

    zzbf(zzbg zzbgVar) {
        this.zza = zzbgVar;
    }

    @Override // com.google.android.gms.internal.fido.zzbc, com.google.android.gms.internal.fido.zzav, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return zzi().listIterator(0);
    }

    @Override // com.google.android.gms.internal.fido.zzbc, com.google.android.gms.internal.fido.zzav
    /* renamed from: zzd */
    public final zzcb iterator() {
        return zzi().listIterator(0);
    }

    @Override // com.google.android.gms.internal.fido.zzbb
    final zzba zzf() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.fido.zzbc
    final zzaz zzj() {
        return new zzbe(this);
    }
}
