package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class zzab extends zza implements zzz {
    zzab(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IPolylineDelegate");
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void remove() throws RemoteException {
        zzb(1, zza());
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final String getId() throws RemoteException {
        Parcel parcelZza = zza(2, zza());
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setPoints(List<LatLng> list) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeTypedList(list);
        zzb(3, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final List<LatLng> getPoints() throws RemoteException {
        Parcel parcelZza = zza(4, zza());
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(LatLng.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setWidth(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final float getWidth() throws RemoteException {
        Parcel parcelZza = zza(6, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setColor(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(7, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final int getColor() throws RemoteException {
        Parcel parcelZza = zza(8, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setZIndex(float f) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeFloat(f);
        zzb(9, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final float getZIndex() throws RemoteException {
        Parcel parcelZza = zza(10, zza());
        float f = parcelZza.readFloat();
        parcelZza.recycle();
        return f;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setVisible(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(11, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final boolean isVisible() throws RemoteException {
        Parcel parcelZza = zza(12, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setGeodesic(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(13, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final boolean isGeodesic() throws RemoteException {
        Parcel parcelZza = zza(14, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final boolean zzb(zzz zzzVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, zzzVar);
        Parcel parcelZza2 = zza(15, parcelZza);
        boolean zZza = zzc.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final int zzj() throws RemoteException {
        Parcel parcelZza = zza(16, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setClickable(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.writeBoolean(parcelZza, z);
        zzb(17, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final boolean isClickable() throws RemoteException {
        Parcel parcelZza = zza(18, zza());
        boolean zZza = zzc.zza(parcelZza);
        parcelZza.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setStartCap(Cap cap) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, cap);
        zzb(19, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final Cap getStartCap() throws RemoteException {
        Parcel parcelZza = zza(20, zza());
        Cap cap = (Cap) zzc.zza(parcelZza, Cap.CREATOR);
        parcelZza.recycle();
        return cap;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setEndCap(Cap cap) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, cap);
        zzb(21, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final Cap getEndCap() throws RemoteException {
        Parcel parcelZza = zza(22, zza());
        Cap cap = (Cap) zzc.zza(parcelZza, Cap.CREATOR);
        parcelZza.recycle();
        return cap;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setJointType(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        zzb(23, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final int getJointType() throws RemoteException {
        Parcel parcelZza = zza(24, zza());
        int i = parcelZza.readInt();
        parcelZza.recycle();
        return i;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void setPattern(List<PatternItem> list) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeTypedList(list);
        zzb(25, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final List<PatternItem> getPattern() throws RemoteException {
        Parcel parcelZza = zza(26, zza());
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(PatternItem.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final void zze(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zza(parcelZza, iObjectWrapper);
        zzb(27, parcelZza);
    }

    @Override // com.google.android.gms.internal.maps.zzz
    public final IObjectWrapper zzk() throws RemoteException {
        Parcel parcelZza = zza(28, zza());
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza.readStrongBinder());
        parcelZza.recycle();
        return iObjectWrapperAsInterface;
    }
}
