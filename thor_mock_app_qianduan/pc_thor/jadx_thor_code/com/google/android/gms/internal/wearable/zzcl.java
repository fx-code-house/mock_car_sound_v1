package com.google.android.gms.internal.wearable;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
abstract class zzcl {
    private static final zzcl zza;
    private static final zzcl zzb;

    static {
        zzci zzciVar = null;
        zza = new zzcj(zzciVar);
        zzb = new zzck(zzciVar);
    }

    /* synthetic */ zzcl(zzci zzciVar) {
    }

    static zzcl zzc() {
        return zza;
    }

    static zzcl zzd() {
        return zzb;
    }

    abstract void zza(Object obj, long j);

    abstract <L> void zzb(Object obj, Object obj2, long j);
}
