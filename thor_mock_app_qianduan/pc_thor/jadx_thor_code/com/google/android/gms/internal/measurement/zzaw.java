package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzaw extends zzag.zzb {
    private final /* synthetic */ zzt zzc;
    private final /* synthetic */ zzag zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaw(zzag zzagVar, zzt zztVar) {
        super(zzagVar);
        this.zzd = zzagVar;
        this.zzc = zztVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        this.zzd.zzm.getCachedAppInstanceId(this.zzc);
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    protected final void zzb() {
        this.zzc.zza((Bundle) null);
    }
}
