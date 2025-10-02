package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class zzj extends zza implements zzh {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.ICircleDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void remove() throws RemoteException {
        zzb(1, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final String getId() throws RemoteException {
        Parcel parcelZza = zza(2, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setCenter(LatLng latLng) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, latLng);
        zzb(3, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final LatLng getCenter() throws RemoteException {
        Parcel parcelZza = zza(4, zza());
        LatLng latLng = (LatLng) zzc.zza(parcelZza, LatLng.CREATOR);
        parcelZza.recycle();
        return latLng;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setRadius(double d) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeDouble(d);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final double getRadius() throws RemoteException {
        Parcel parcelZza = zza(6, zza());
        double d = parcelZza.readDouble();
        parcelZza.recycle();
        return d;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setStrokeWidth(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(7, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final float getStrokeWidth() throws RemoteException {
        Parcel parcelZza = zza(8, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setStrokeColor(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(9, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final int getStrokeColor() throws RemoteException {
        Parcel parcelZza = zza(10, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setFillColor(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(11, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final int getFillColor() throws RemoteException {
        Parcel parcelZza = zza(12, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setZIndex(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(13, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final float getZIndex() throws RemoteException {
        Parcel parcelZza = zza(14, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setVisible(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(15, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final boolean isVisible() throws RemoteException {
        Parcel parcelZza = zza(16, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final boolean zzb(zzh zzhVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, zzhVar);
        Parcel parcelZza2 = zza(17, parcelZza);
        boolean zZza = zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final int zzj() throws RemoteException {
        Parcel parcelZza = zza(18, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setClickable(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(19, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final boolean isClickable() throws RemoteException {
        Parcel parcelZza = zza(20, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void setStrokePattern(List<PatternItem> list) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeTypedList(list);
        zzb(21, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final List<PatternItem> getStrokePattern() throws RemoteException {
        Parcel parcelZza = zza(22, zza());
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(PatternItem.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final void zze(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, iObjectWrapper);
        zzb(23, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzh
    public final IObjectWrapper zzk() throws RemoteException {
        Parcel parcelZza = zza(24, zza());
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return iObjectWrapperAsInterface;
    }
}
