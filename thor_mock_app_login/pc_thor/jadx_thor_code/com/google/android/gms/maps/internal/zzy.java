package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public abstract class zzy extends com.google.android.gms.internal.maps.zzb implements zzx {
    public zzy() {
        super("com.google.android.gms.maps.internal.IOnGroundOverlayClickListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        zza(com.google.android.gms.internal.maps.zzl.zzd(parcel.readStrongBinder()));
        parcel2.writeNoException();
        return true;
    }
}
