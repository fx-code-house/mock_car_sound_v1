package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzbc extends zzag.zzb {
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ zzt zzd;
    private final /* synthetic */ zzag zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbc(zzag zzagVar, Bundle bundle, zzt zztVar) {
        super(zzagVar);
        this.zze = zzagVar;
        this.zzc = bundle;
        this.zzd = zztVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        this.zze.zzm.performAction(this.zzc, this.zzd, this.zza);
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    protected final void zzb() {
        this.zzd.zza((Bundle) null);
    }
}
