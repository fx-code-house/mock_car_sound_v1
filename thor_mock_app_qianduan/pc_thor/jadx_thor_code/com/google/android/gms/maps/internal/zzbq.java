package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public abstract class zzbq extends com.google.android.gms.internal.maps.zzb implements zzbp {
    public zzbq() {
        super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaReadyCallback");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        IStreetViewPanoramaDelegate zzbuVar;
        if (i != 1) {
            return false;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        if (strongBinder == null) {
            zzbuVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            if (iInterfaceQueryLocalInterface instanceof IStreetViewPanoramaDelegate) {
                zzbuVar = (IStreetViewPanoramaDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzbuVar = new zzbu(strongBinder);
            }
        }
        zza(zzbuVar);
        parcel2.writeNoException();
        return true;
    }
}
