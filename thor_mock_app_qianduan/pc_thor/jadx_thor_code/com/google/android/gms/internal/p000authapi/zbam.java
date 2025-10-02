package com.google.android.gms.internal.p000authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* compiled from: com.google.android.gms:play-services-auth@@20.7.0 */
/* loaded from: classes2.dex */
public final class zbam extends zba implements IInterface {
    zbam(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ISignInService");
    }

    public final void zbc(zbac zbacVar, BeginSignInRequest beginSignInRequest) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbacVar);
        zbc.zbc(parcelZba, beginSignInRequest);
        zbb(1, parcelZba);
    }

    public final void zbd(zbaf zbafVar, GetPhoneNumberHintIntentRequest getPhoneNumberHintIntentRequest, String str) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbafVar);
        zbc.zbc(parcelZba, getPhoneNumberHintIntentRequest);
        parcelZba.writeString(str);
        zbb(4, parcelZba);
    }

    public final void zbe(zbah zbahVar, GetSignInIntentRequest getSignInIntentRequest) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbahVar);
        zbc.zbc(parcelZba, getSignInIntentRequest);
        zbb(3, parcelZba);
    }

    public final void zbf(IStatusCallback iStatusCallback, String str) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, iStatusCallback);
        parcelZba.writeString(str);
        zbb(2, parcelZba);
    }
}
