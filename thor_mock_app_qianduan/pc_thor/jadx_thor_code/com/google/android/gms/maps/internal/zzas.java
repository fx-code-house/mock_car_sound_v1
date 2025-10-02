package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public abstract class zzas extends com.google.android.gms.internal.maps.zzb implements zzar {
    public zzas() {
        super("com.google.android.gms.maps.internal.IOnMarkerClickListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        boolean zZza = zza(com.google.android.gms.internal.maps.zzu.zzg(parcel.readStrongBinder()));
        parcel2.writeNoException();
        com.google.android.gms.internal.maps.zzc.writeBoolean(parcel2, zZza);
        return true;
    }
}
