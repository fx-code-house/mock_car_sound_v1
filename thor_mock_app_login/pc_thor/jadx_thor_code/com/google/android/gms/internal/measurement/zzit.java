package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzit {
    private static final zzit zza;
    private static final zzit zzb;

    private zzit() {
    }

    abstract <L> List<L> zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    abstract void zzb(Object obj, long j);

    static zzit zza() {
        return zza;
    }

    static zzit zzb() {
        return zzb;
    }

    static {
        zzis zzisVar = null;
        zza = new zziv();
        zzb = new zziu();
    }
}
