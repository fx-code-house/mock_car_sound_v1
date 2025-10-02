package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzes {
    private final int zza;
    private final boolean zzb;
    private final boolean zzc;
    private final /* synthetic */ zzeq zzd;

    zzes(zzeq zzeqVar, int i, boolean z, boolean z2) {
        this.zzd = zzeqVar;
        this.zza = i;
        this.zzb = z;
        this.zzc = z2;
    }

    public final void zza(String str) throws IllegalStateException {
        this.zzd.zza(this.zza, this.zzb, this.zzc, str, null, null, null);
    }

    public final void zza(String str, Object obj) throws IllegalStateException {
        this.zzd.zza(this.zza, this.zzb, this.zzc, str, obj, null, null);
    }

    public final void zza(String str, Object obj, Object obj2) throws IllegalStateException {
        this.zzd.zza(this.zza, this.zzb, this.zzc, str, obj, obj2, null);
    }

    public final void zza(String str, Object obj, Object obj2, Object obj3) throws IllegalStateException {
        this.zzd.zza(this.zza, this.zzb, this.zzc, str, obj, obj2, obj3);
    }
}
