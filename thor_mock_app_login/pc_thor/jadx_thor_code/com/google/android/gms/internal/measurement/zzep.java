package com.google.android.gms.internal.measurement;

/* JADX INFO: Add missing generic type declarations: [K] */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzep<K> extends zzet<K> {
    private final /* synthetic */ zzem zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzep(zzem zzemVar) {
        super(zzemVar, null);
        this.zza = zzemVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzet
    final K zza(int i) {
        return (K) this.zza.zzb[i];
    }
}
