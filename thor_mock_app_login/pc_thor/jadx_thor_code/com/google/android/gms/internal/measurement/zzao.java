package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzao extends zzag.zzb {
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ zzag zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzao(zzag zzagVar, Bundle bundle) {
        super(zzagVar);
        this.zzd = zzagVar;
        this.zzc = bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        this.zzd.zzm.setConsent(this.zzc, this.zza);
    }
}
