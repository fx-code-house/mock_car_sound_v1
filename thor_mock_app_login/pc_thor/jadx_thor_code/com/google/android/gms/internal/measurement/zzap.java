package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzap extends zzag.zzb {
    private final /* synthetic */ Boolean zzc;
    private final /* synthetic */ zzag zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzap(zzag zzagVar, Boolean bool) {
        super(zzagVar);
        this.zzd = zzagVar;
        this.zzc = bool;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        if (this.zzc != null) {
            this.zzd.zzm.setMeasurementEnabled(this.zzc.booleanValue(), this.zza);
        } else {
            this.zzd.zzm.clearMeasurementEnabled(this.zza);
        }
    }
}
