package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzp extends zzn<CapabilityApi.GetAllCapabilitiesResult> {
    final /* synthetic */ int zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzp(zzz zzzVar, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.zza = i;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return new zzw(status, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeu) ((zzhv) anyClient).getService()).zzp(new zzhb(this), this.zza);
    }
}
