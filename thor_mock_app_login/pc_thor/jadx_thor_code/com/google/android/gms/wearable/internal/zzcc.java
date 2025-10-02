package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzcc extends zzn<DataApi.GetFdForAssetResult> {
    final /* synthetic */ Asset zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcc(zzcj zzcjVar, GoogleApiClient googleApiClient, Asset asset) {
        super(googleApiClient);
        this.zza = asset;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return new zzci(status, null);
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzhv) anyClient).zzq(this, this.zza);
    }
}
