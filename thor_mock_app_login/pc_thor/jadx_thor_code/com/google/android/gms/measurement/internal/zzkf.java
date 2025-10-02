package com.google.android.gms.measurement.internal;

import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.internal.measurement.zzms;
import java.lang.reflect.InvocationTargetException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzkf {
    final /* synthetic */ zzjx zza;

    zzkf(zzjx zzjxVar) {
        this.zza = zzjxVar;
    }

    final void zza() {
        this.zza.zzc();
        if (this.zza.zzr().zza(this.zza.zzl().currentTimeMillis())) {
            this.zza.zzr().zzm.zza(true);
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (runningAppProcessInfo.importance == 100) {
                this.zza.zzq().zzw().zza("Detected application was in foreground");
                zzb(this.zza.zzl().currentTimeMillis(), false);
            }
        }
    }

    final void zza(long j, boolean z) {
        this.zza.zzc();
        this.zza.zzaa();
        if (this.zza.zzr().zza(j)) {
            this.zza.zzr().zzm.zza(true);
        }
        this.zza.zzr().zzp.zza(j);
        if (this.zza.zzr().zzm.zza()) {
            zzb(j, z);
        }
    }

    private final void zzb(long j, boolean z) throws IllegalStateException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        this.zza.zzc();
        if (this.zza.zzy.zzaa()) {
            this.zza.zzr().zzp.zza(j);
            this.zza.zzq().zzw().zza("Session started, time", Long.valueOf(this.zza.zzl().elapsedRealtime()));
            Long lValueOf = Long.valueOf(j / 1000);
            this.zza.zze().zza("auto", "_sid", lValueOf, j);
            this.zza.zzr().zzm.zza(false);
            Bundle bundle = new Bundle();
            bundle.putLong("_sid", lValueOf.longValue());
            if (this.zza.zzs().zza(zzas.zzbj) && z) {
                bundle.putLong("_aib", 1L);
            }
            this.zza.zze().zza("auto", "_s", j, bundle);
            if (zzms.zzb() && this.zza.zzs().zza(zzas.zzbo)) {
                String strZza = this.zza.zzr().zzu.zza();
                if (TextUtils.isEmpty(strZza)) {
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("_ffr", strZza);
                this.zza.zze().zza("auto", "_ssr", j, bundle2);
            }
        }
    }
}
