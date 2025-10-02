package com.google.android.gms.internal.wallet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.Wallet;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzac extends Wallet.zzb {
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ CreateWalletObjectsRequest zzeh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(zzaa zzaaVar, GoogleApiClient googleApiClient, CreateWalletObjectsRequest createWalletObjectsRequest, int i) {
        super(googleApiClient);
        this.zzeh = createWalletObjectsRequest;
        this.val$requestCode = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.wallet.Wallet.zza
    /* renamed from: zza */
    public final void doExecute(zzv zzvVar) {
        zzvVar.zza(this.zzeh, this.val$requestCode);
        setResult((zzac) Status.RESULT_SUCCESS);
    }

    @Override // com.google.android.gms.wallet.Wallet.zza, com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        doExecute((zzv) anyClient);
    }
}
