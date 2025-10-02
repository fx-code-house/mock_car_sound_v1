package com.google.android.gms.internal.measurement;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzeo<K, V> extends zzet<Map.Entry<K, V>> {
    private final /* synthetic */ zzem zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeo(zzem zzemVar) {
        super(zzemVar, null);
        this.zza = zzemVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzet
    final /* synthetic */ Object zza(int i) {
        return new zzev(this.zza, i);
    }
}
