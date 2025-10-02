package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzby extends zzn<DataApi.DataItemResult> {
    final /* synthetic */ Uri zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzby(zzcj zzcjVar, GoogleApiClient googleApiClient, Uri uri) {
        super(googleApiClient);
        this.zza = uri;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return new zzcg(status, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeu) ((zzhv) anyClient).getService()).zze(new zzhh(this), this.zza);
    }
}
