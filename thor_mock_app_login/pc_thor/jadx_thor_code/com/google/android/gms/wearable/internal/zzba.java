package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzba extends zzn<Status> {
    final /* synthetic */ int zza;
    final /* synthetic */ zzbi zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzba(zzbi zzbiVar, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.zzb = zzbiVar;
        this.zza = i;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeu) ((zzhv) anyClient).getService()).zzw(new zzgz(this), this.zzb.zza, this.zza);
    }
}
