package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public interface zzo extends IInterface {
    void zza(CreateWalletObjectsRequest createWalletObjectsRequest, Bundle bundle, zzq zzqVar) throws RemoteException;

    void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzq zzqVar) throws RemoteException;

    void zza(PaymentDataRequest paymentDataRequest, Bundle bundle, zzq zzqVar) throws RemoteException;
}
