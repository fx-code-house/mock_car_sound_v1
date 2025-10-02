package com.google.android.gms.wallet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzak extends TaskApiCall<com.google.android.gms.internal.wallet.zzv, AutoResolvableVoidResult> {
    private final /* synthetic */ CreateWalletObjectsRequest zzeh;

    zzak(WalletObjectsClient walletObjectsClient, CreateWalletObjectsRequest createWalletObjectsRequest) {
        this.zzeh = createWalletObjectsRequest;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<AutoResolvableVoidResult> taskCompletionSource) throws RemoteException {
        ((com.google.android.gms.internal.wallet.zzv) anyClient).zza(this.zzeh, taskCompletionSource);
    }
}
