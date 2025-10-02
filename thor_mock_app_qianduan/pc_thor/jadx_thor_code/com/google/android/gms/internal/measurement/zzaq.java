package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzaq extends zzag.zzb {
    private final /* synthetic */ zzag zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaq(zzag zzagVar) {
        super(zzagVar);
        this.zzc = zzagVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        this.zzc.zzm.resetAnalyticsData(this.zza);
    }
}
