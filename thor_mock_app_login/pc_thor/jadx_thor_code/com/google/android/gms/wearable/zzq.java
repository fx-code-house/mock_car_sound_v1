package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzfw;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzq implements Runnable {
    final /* synthetic */ zzfw zza;
    final /* synthetic */ zzx zzb;

    zzq(zzx zzxVar, zzfw zzfwVar) {
        this.zzb = zzxVar;
        this.zza = zzfwVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.onPeerConnected(this.zza);
    }
}
