package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public abstract class zzau extends com.google.android.gms.internal.maps.zzb implements zzat {
    public zzau() {
        super("com.google.android.gms.maps.internal.IOnMarkerDragListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            zzb(com.google.android.gms.internal.maps.zzu.zzg(parcel.readStrongBinder()));
        } else if (i == 2) {
            zzd(com.google.android.gms.internal.maps.zzu.zzg(parcel.readStrongBinder()));
        } else {
            if (i != 3) {
                return false;
            }
            zzc(com.google.android.gms.internal.maps.zzu.zzg(parcel.readStrongBinder()));
        }
        parcel2.writeNoException();
        return true;
    }
}
