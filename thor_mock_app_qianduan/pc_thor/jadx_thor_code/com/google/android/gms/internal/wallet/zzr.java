package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzr extends zzb implements zzo {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IOwService");
    }

    @Override // com.google.android.gms.internal.wallet.zzo
    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, Bundle bundle, zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, createWalletObjectsRequest);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(6, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.wallet.zzo
    public final void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, isReadyToPayRequest);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(14, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.wallet.zzo
    public final void zza(PaymentDataRequest paymentDataRequest, Bundle bundle, zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(parcelObtainAndWriteInterfaceToken, paymentDataRequest);
        zzd.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzd.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        zza(19, parcelObtainAndWriteInterfaceToken);
    }
}
