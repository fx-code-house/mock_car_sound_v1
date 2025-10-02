package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjp implements Runnable {
    private final /* synthetic */ zzjl zza;

    zzjp(zzjl zzjlVar) {
        this.zza = zzjlVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zza.zza.zza(new ComponentName(this.zza.zza.zzm(), "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
