package com.google.android.gms.internal.measurement;

/* JADX INFO: Add missing generic type declarations: [V] */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzer<V> extends zzet<V> {
    private final /* synthetic */ zzem zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzer(zzem zzemVar) {
        super(zzemVar, null);
        this.zza = zzemVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzet
    final V zza(int i) {
        return (V) this.zza.zzc[i];
    }
}
