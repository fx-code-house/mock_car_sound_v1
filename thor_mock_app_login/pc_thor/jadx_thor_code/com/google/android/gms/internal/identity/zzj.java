package com.google.android.gms.internal.identity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.identity.intents.UserAddressRequest;

/* loaded from: classes2.dex */
public final class zzj extends zza implements zzi {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.identity.intents.internal.IAddressService");
    }

    @Override // com.google.android.gms.internal.identity.zzi
    public final void zza(zzg zzgVar, UserAddressRequest userAddressRequest, Bundle bundle) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzgVar);
        zzc.zza(parcelObtainAndWriteInterfaceToken, userAddressRequest);
        zzc.zza(parcelObtainAndWriteInterfaceToken, bundle);
        transactAndReadExceptionReturnVoid(2, parcelObtainAndWriteInterfaceToken);
    }
}
