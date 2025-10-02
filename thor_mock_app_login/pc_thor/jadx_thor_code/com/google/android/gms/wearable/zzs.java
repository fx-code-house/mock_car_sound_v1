package com.google.android.gms.wearable;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzs implements Runnable {
    final /* synthetic */ List zza;
    final /* synthetic */ zzx zzb;

    zzs(zzx zzxVar, List list) {
        this.zzb = zzxVar;
        this.zza = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.onConnectedNodes(this.zza);
    }
}
