package com.google.android.gms.measurement.internal;

import com.google.android.exoplayer2.SimpleExoPlayer;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjy {
    final /* synthetic */ zzjx zza;
    private zzkb zzb;

    zzjy(zzjx zzjxVar) {
        this.zza = zzjxVar;
    }

    final void zza() {
        this.zza.zzc();
        if (this.zzb != null) {
            this.zza.zzc.removeCallbacks(this.zzb);
        }
        if (this.zza.zzs().zza(zzas.zzbu)) {
            this.zza.zzr().zzr.zza(false);
        }
    }

    final void zza(long j) {
        this.zzb = new zzkb(this, this.zza.zzl().currentTimeMillis(), j);
        this.zza.zzc.postDelayed(this.zzb, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }
}
