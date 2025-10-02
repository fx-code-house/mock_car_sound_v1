package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ILocationSourceDelegate extends IInterface {
    void activate(zzah zzahVar) throws RemoteException;

    void deactivate() throws RemoteException;

    public static abstract class zza extends com.google.android.gms.internal.maps.zzb implements ILocationSourceDelegate {
        public zza() {
            super("com.google.android.gms.maps.internal.ILocationSourceDelegate");
        }

        @Override // com.google.android.gms.internal.maps.zzb
        protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            zzah zzaiVar;
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    zzaiVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnLocationChangeListener");
                    if (iInterfaceQueryLocalInterface instanceof zzah) {
                        zzaiVar = (zzah) iInterfaceQueryLocalInterface;
                    } else {
                        zzaiVar = new zzai(strongBinder);
                    }
                }
                activate(zzaiVar);
            } else {
                if (i != 2) {
                    return false;
                }
                deactivate();
            }
            parcel2.writeNoException();
            return true;
        }
    }
}
