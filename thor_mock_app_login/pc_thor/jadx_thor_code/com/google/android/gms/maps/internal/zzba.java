package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public abstract class zzba extends com.google.android.gms.internal.maps.zzb implements zzaz {
    public zzba() {
        super("com.google.android.gms.maps.internal.IOnMyLocationClickListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        onMyLocationClick((Location) com.google.android.gms.internal.maps.zzc.zza(parcel, Location.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
