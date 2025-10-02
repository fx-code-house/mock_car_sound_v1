package com.google.android.gms.wallet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzad extends TaskApiCall<com.google.android.gms.internal.wallet.zzv, PaymentData> {
    private final /* synthetic */ PaymentDataRequest zzdu;

    zzad(PaymentsClient paymentsClient, PaymentDataRequest paymentDataRequest) {
        this.zzdu = paymentDataRequest;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<PaymentData> taskCompletionSource) throws RemoteException {
        ((com.google.android.gms.internal.wallet.zzv) anyClient).zza(this.zzdu, taskCompletionSource);
    }
}
