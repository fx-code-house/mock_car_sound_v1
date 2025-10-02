package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/* loaded from: classes2.dex */
public final class zzm extends zza implements zzk {
    zzm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void remove() throws RemoteException {
        zzb(1, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final String getId() throws RemoteException {
        Parcel parcelZza = zza(2, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setPosition(LatLng latLng) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, latLng);
        zzb(3, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final LatLng getPosition() throws RemoteException {
        Parcel parcelZza = zza(4, zza());
        LatLng latLng = (LatLng) zzc.zza(parcelZza, LatLng.CREATOR);
        parcelZza.recycle();
        return latLng;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setDimensions(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void zza(float f, float f2) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        parcelZza.writeFloat(f2);
        zzb(6, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final float getWidth() throws RemoteException {
        Parcel parcelZza = zza(7, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final float getHeight() throws RemoteException {
        Parcel parcelZza = zza(8, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setPositionFromBounds(LatLngBounds latLngBounds) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, latLngBounds);
        zzb(9, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final LatLngBounds getBounds() throws RemoteException {
        Parcel parcelZza = zza(10, zza());
        LatLngBounds latLngBounds = (LatLngBounds) zzc.zza(parcelZza, LatLngBounds.CREATOR);
        parcelZza.recycle();
        return latLngBounds;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setBearing(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(11, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final float getBearing() throws RemoteException {
        Parcel parcelZza = zza(12, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setZIndex(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(13, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final float getZIndex() throws RemoteException {
        Parcel parcelZza = zza(14, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setVisible(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(15, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final boolean isVisible() throws RemoteException {
        Parcel parcelZza = zza(16, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setTransparency(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(17, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final float getTransparency() throws RemoteException {
        Parcel parcelZza = zza(18, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final boolean zzb(zzk zzkVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, zzkVar);
        Parcel parcelZza2 = zza(19, parcelZza);
        boolean zZza = zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final int zzj() throws RemoteException {
        Parcel parcelZza = zza(20, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void zzf(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, iObjectWrapper);
        zzb(21, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void setClickable(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(22, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final boolean isClickable() throws RemoteException {
        Parcel parcelZza = zza(23, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final void zze(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, iObjectWrapper);
        zzb(24, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzk
    public final IObjectWrapper zzk() throws RemoteException {
        Parcel parcelZza = zza(25, zza());
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return iObjectWrapperAsInterface;
    }
}
