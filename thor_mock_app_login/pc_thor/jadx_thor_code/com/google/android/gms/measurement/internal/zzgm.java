package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgm implements Callable<List<zzkw>> {
    private final /* synthetic */ zzn zza;
    private final /* synthetic */ zzfz zzb;

    zzgm(zzfz zzfzVar, zzn zznVar) {
        this.zzb = zzfzVar;
        this.zza = zznVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzkw> call() throws Exception {
        this.zzb.zza.zzr();
        return this.zzb.zza.zze().zza(this.zza.zza);
    }
}
