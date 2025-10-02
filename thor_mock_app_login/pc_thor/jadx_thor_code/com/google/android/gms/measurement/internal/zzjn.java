package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjn implements Runnable {
    private final /* synthetic */ ComponentName zza;
    private final /* synthetic */ zzjl zzb;

    zzjn(zzjl zzjlVar, ComponentName componentName) {
        this.zzb = zzjlVar;
        this.zza = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zza.zza(this.zza);
    }
}
