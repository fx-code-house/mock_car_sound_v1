package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgk implements Callable<byte[]> {
    private final /* synthetic */ zzaq zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzfz zzc;

    zzgk(zzfz zzfzVar, zzaq zzaqVar, String str) {
        this.zzc = zzfzVar;
        this.zza = zzaqVar;
        this.zzb = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ byte[] call() throws Exception {
        this.zzc.zza.zzr();
        return this.zzc.zza.zzg().zza(this.zza, this.zzb);
    }
}
