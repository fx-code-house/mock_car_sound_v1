package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes2.dex */
public final class zzk extends com.google.android.gms.internal.maps.zza implements IMapViewDelegate {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final IGoogleMapDelegate getMap() throws RemoteException {
        IGoogleMapDelegate zzgVar;
        Parcel parcelZza = zza(1, zza());
        IBinder strongBinder = parcelZza.readStrongBinder();
        if (strongBinder == null) {
            zzgVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            if (iInterfaceQueryLocalInterface instanceof IGoogleMapDelegate) {
                zzgVar = (IGoogleMapDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzgVar = new zzg(strongBinder);
            }
        }
        parcelZza.recycle();
        return zzgVar;
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, bundle);
        zzb(2, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onResume() throws RemoteException {
        zzb(3, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onPause() throws RemoteException {
        zzb(4, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onDestroy() throws RemoteException {
        zzb(5, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onLowMemory() throws RemoteException {
        zzb(6, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, bundle);
        Parcel parcelZza2 = zza(7, parcelZza);
        if (parcelZza2.readInt() != 0) {
            bundle.readFromParcel(parcelZza2);
        }
        parcelZza2.recycle();
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final IObjectWrapper getView() throws RemoteException {
        Parcel parcelZza = zza(8, zza());
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return iObjectWrapperAsInterface;
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void getMapAsync(zzap zzapVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, zzapVar);
        zzb(9, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, bundle);
        zzb(10, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onExitAmbient() throws RemoteException {
        zzb(11, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onStart() throws RemoteException {
        zzb(12, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onStop() throws RemoteException {
        zzb(13, zza());
    }
}
