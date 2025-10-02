package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public final class zzae extends zza implements zzac {
    zzae(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final void remove() throws RemoteException {
        zzb(1, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final void clearTileCache() throws RemoteException {
        zzb(2, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final String getId() throws RemoteException {
        Parcel parcelZza = zza(3, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final void setZIndex(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(4, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final float getZIndex() throws RemoteException {
        Parcel parcelZza = zza(5, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final void setVisible(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(6, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final boolean isVisible() throws RemoteException {
        Parcel parcelZza = zza(7, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final boolean zza(zzac zzacVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, zzacVar);
        Parcel parcelZza2 = zza(8, parcelZza);
        boolean zZza = zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final int zzj() throws RemoteException {
        Parcel parcelZza = zza(9, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final void setFadeIn(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(10, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final boolean getFadeIn() throws RemoteException {
        Parcel parcelZza = zza(11, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final void setTransparency(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(12, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzac
    public final float getTransparency() throws RemoteException {
        Parcel parcelZza = zza(13, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }
}
