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
public final class zzy extends zza implements zzw {
    zzy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IPolygonDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void remove() throws RemoteException {
        zzb(1, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final String getId() throws RemoteException {
        Parcel parcelZza = zza(2, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setPoints(List<LatLng> list) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeTypedList(list);
        zzb(3, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final List<LatLng> getPoints() throws RemoteException {
        Parcel parcelZza = zza(4, zza());
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(LatLng.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setHoles(List list) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeList(list);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final List getHoles() throws RemoteException {
        Parcel parcelZza = zza(6, zza());
        ArrayList arrayListZzb = zzc.zzb(parcelZza);
        parcelZza.recycle();
        return arrayListZzb;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setStrokeWidth(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(7, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final float getStrokeWidth() throws RemoteException {
        Parcel parcelZza = zza(8, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setStrokeColor(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(9, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final int getStrokeColor() throws RemoteException {
        Parcel parcelZza = zza(10, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setFillColor(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(11, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final int getFillColor() throws RemoteException {
        Parcel parcelZza = zza(12, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setZIndex(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(13, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final float getZIndex() throws RemoteException {
        Parcel parcelZza = zza(14, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setVisible(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(15, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final boolean isVisible() throws RemoteException {
        Parcel parcelZza = zza(16, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setGeodesic(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(17, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final boolean isGeodesic() throws RemoteException {
        Parcel parcelZza = zza(18, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final boolean zzb(zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, zzwVar);
        Parcel parcelZza2 = zza(19, parcelZza);
        boolean zZza = zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final int zzj() throws RemoteException {
        Parcel parcelZza = zza(20, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setClickable(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(21, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final boolean isClickable() throws RemoteException {
        Parcel parcelZza = zza(22, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setStrokeJointType(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(23, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final int getStrokeJointType() throws RemoteException {
        Parcel parcelZza = zza(24, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void setStrokePattern(List<PatternItem> list) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeTypedList(list);
        zzb(25, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final List<PatternItem> getStrokePattern() throws RemoteException {
        Parcel parcelZza = zza(26, zza());
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(PatternItem.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final void zze(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, iObjectWrapper);
        zzb(27, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzw
    public final IObjectWrapper zzk() throws RemoteException {
        Parcel parcelZza = zza(28, zza());
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return iObjectWrapperAsInterface;
    }
}
