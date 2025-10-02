package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/* loaded from: classes2.dex */
public abstract class zzbi extends com.google.android.gms.internal.maps.zzb implements zzbh {
    public zzbi() {
        super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaCameraChangeListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        onStreetViewPanoramaCameraChange((StreetViewPanoramaCamera) com.google.android.gms.internal.maps.zzc.zza(parcel, StreetViewPanoramaCamera.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
