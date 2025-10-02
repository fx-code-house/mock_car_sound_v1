package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhn implements Runnable {
    private final /* synthetic */ Bundle zza;
    private final /* synthetic */ zzhb zzb;

    zzhn(zzhb zzhbVar, Bundle bundle) {
        this.zzb = zzhbVar;
        this.zza = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zzc(this.zza);
    }
}
