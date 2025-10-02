package com.google.android.gms.identity.intents;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.internal.identity.zze;

/* loaded from: classes2.dex */
final class zzb extends Address.zza {
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ UserAddressRequest zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzb(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        super(googleApiClient);
        this.zze = userAddressRequest;
        this.val$requestCode = i;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zze) anyClient).zza(this.zze, this.val$requestCode);
        setResult((zzb) Status.RESULT_SUCCESS);
    }
}
