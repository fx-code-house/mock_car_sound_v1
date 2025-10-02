package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzax;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzw implements Runnable {
    final /* synthetic */ zzax zza;
    final /* synthetic */ zzx zzb;

    zzw(zzx zzxVar, zzax zzaxVar) {
        this.zzb = zzxVar;
        this.zza = zzaxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zza(this.zzb.zza);
        this.zza.zza(this.zzb.zza.zzh);
    }
}
