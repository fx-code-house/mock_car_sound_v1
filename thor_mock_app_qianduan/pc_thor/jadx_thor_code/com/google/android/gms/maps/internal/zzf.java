package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;

/* loaded from: classes2.dex */
public final class zzf extends com.google.android.gms.internal.maps.zza implements zze {
    zzf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICreator");
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final IMapFragmentDelegate zzc(IObjectWrapper iObjectWrapper) throws RemoteException {
        IMapFragmentDelegate zzjVar;
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        Parcel parcelZza2 = zza(2, parcelZza);
        IBinder strongBinder = parcelZza2.readStrongBinder();
        if (strongBinder == null) {
            zzjVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            if (iInterfaceQueryLocalInterface instanceof IMapFragmentDelegate) {
                zzjVar = (IMapFragmentDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzjVar = new zzj(strongBinder);
            }
        }
        parcelZza2.recycle();
        return zzjVar;
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final IMapViewDelegate zza(IObjectWrapper iObjectWrapper, GoogleMapOptions googleMapOptions) throws RemoteException {
        IMapViewDelegate zzkVar;
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, googleMapOptions);
        Parcel parcelZza2 = zza(3, parcelZza);
        IBinder strongBinder = parcelZza2.readStrongBinder();
        if (strongBinder == null) {
            zzkVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            if (iInterfaceQueryLocalInterface instanceof IMapViewDelegate) {
                zzkVar = (IMapViewDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzkVar = new zzk(strongBinder);
            }
        }
        parcelZza2.recycle();
        return zzkVar;
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final ICameraUpdateFactoryDelegate zze() throws RemoteException {
        ICameraUpdateFactoryDelegate zzbVar;
        Parcel parcelZza = zza(4, zza());
        IBinder strongBinder = parcelZza.readStrongBinder();
        if (strongBinder == null) {
            zzbVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            if (iInterfaceQueryLocalInterface instanceof ICameraUpdateFactoryDelegate) {
                zzbVar = (ICameraUpdateFactoryDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzbVar = new zzb(strongBinder);
            }
        }
        parcelZza.recycle();
        return zzbVar;
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final com.google.android.gms.internal.maps.zze zzf() throws RemoteException {
        Parcel parcelZza = zza(5, zza());
        com.google.android.gms.internal.maps.zze zzeVarZzb = com.google.android.gms.internal.maps.zzf.zzb(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return zzeVarZzb;
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final void zza(IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeInt(i);
        zzb(6, parcelZza);
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final IStreetViewPanoramaViewDelegate zza(IObjectWrapper iObjectWrapper, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException {
        IStreetViewPanoramaViewDelegate zzbwVar;
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, streetViewPanoramaOptions);
        Parcel parcelZza2 = zza(7, parcelZza);
        IBinder strongBinder = parcelZza2.readStrongBinder();
        if (strongBinder == null) {
            zzbwVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
            if (iInterfaceQueryLocalInterface instanceof IStreetViewPanoramaViewDelegate) {
                zzbwVar = (IStreetViewPanoramaViewDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzbwVar = new zzbw(strongBinder);
            }
        }
        parcelZza2.recycle();
        return zzbwVar;
    }

    @Override // com.google.android.gms.maps.internal.zze
    public final IStreetViewPanoramaFragmentDelegate zzd(IObjectWrapper iObjectWrapper) throws RemoteException {
        IStreetViewPanoramaFragmentDelegate zzbvVar;
        Parcel parcelZza = zza();
        com.google.android.gms.internal.maps.zzc.zza(parcelZza, iObjectWrapper);
        Parcel parcelZza2 = zza(8, parcelZza);
        IBinder strongBinder = parcelZza2.readStrongBinder();
        if (strongBinder == null) {
            zzbvVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
            if (iInterfaceQueryLocalInterface instanceof IStreetViewPanoramaFragmentDelegate) {
                zzbvVar = (IStreetViewPanoramaFragmentDelegate) iInterfaceQueryLocalInterface;
            } else {
                zzbvVar = new zzbv(strongBinder);
            }
        }
        parcelZza2.recycle();
        return zzbvVar;
    }
}
