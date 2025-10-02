package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public final class zzs extends zza implements zzq {
    zzs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzq
    public final String getName() throws RemoteException {
        Parcel parcelZza = zza(1, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzq
    public final String getShortName() throws RemoteException {
        Parcel parcelZza = zza(2, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzq
    public final void activate() throws RemoteException {
        zzb(3, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzq
    public final boolean zzb(zzq zzqVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, zzqVar);
        Parcel parcelZza2 = zza(4, parcelZza);
        boolean zZza = zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzq
    public final int zzj() throws RemoteException {
        Parcel parcelZza = zza(5, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }
}
