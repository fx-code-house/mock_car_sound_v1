package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.CameraPosition;

/* loaded from: classes2.dex */
public abstract class zzm extends com.google.android.gms.internal.maps.zzb implements zzl {
    public zzm() {
        super("com.google.android.gms.maps.internal.IOnCameraChangeListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        onCameraChange((CameraPosition) com.google.android.gms.internal.maps.zzc.zza(parcel, CameraPosition.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
