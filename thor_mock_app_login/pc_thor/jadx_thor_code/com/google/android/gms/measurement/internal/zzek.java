package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzek extends com.google.android.gms.internal.measurement.zza implements zzei {
    zzek(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzaq zzaqVar, zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zzaqVar);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(1, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzku zzkuVar, zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zzkuVar);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(2, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(4, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzaq zzaqVar, String str, String str2) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zzaqVar);
        parcelA_.writeString(str);
        parcelA_.writeString(str2);
        zzb(5, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zzb(zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(6, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzku> zza(zzn zznVar, boolean z) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, z);
        Parcel parcelZza = zza(7, parcelA_);
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(zzku.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final byte[] zza(zzaq zzaqVar, String str) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zzaqVar);
        parcelA_.writeString(str);
        Parcel parcelZza = zza(9, parcelA_);
        byte[] bArrCreateByteArray = parcelZza.createByteArray();
        parcelZza.recycle();
        return bArrCreateByteArray;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(long j, String str, String str2, String str3) throws RemoteException {
        Parcel parcelA_ = a_();
        parcelA_.writeLong(j);
        parcelA_.writeString(str);
        parcelA_.writeString(str2);
        parcelA_.writeString(str3);
        zzb(10, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final String zzc(zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        Parcel parcelZza = zza(11, parcelA_);
        String string = parcelZza.readString();
        parcelZza.recycle();
        return string;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzz zzzVar, zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zzzVar);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(12, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(zzz zzzVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zzzVar);
        zzb(13, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzku> zza(String str, String str2, boolean z, zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        parcelA_.writeString(str);
        parcelA_.writeString(str2);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, z);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        Parcel parcelZza = zza(14, parcelA_);
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(zzku.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzku> zza(String str, String str2, String str3, boolean z) throws RemoteException {
        Parcel parcelA_ = a_();
        parcelA_.writeString(str);
        parcelA_.writeString(str2);
        parcelA_.writeString(str3);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, z);
        Parcel parcelZza = zza(15, parcelA_);
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(zzku.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzz> zza(String str, String str2, zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        parcelA_.writeString(str);
        parcelA_.writeString(str2);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        Parcel parcelZza = zza(16, parcelA_);
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(zzz.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final List<zzz> zza(String str, String str2, String str3) throws RemoteException {
        Parcel parcelA_ = a_();
        parcelA_.writeString(str);
        parcelA_.writeString(str2);
        parcelA_.writeString(str3);
        Parcel parcelZza = zza(17, parcelA_);
        ArrayList arrayListCreateTypedArrayList = parcelZza.createTypedArrayList(zzz.CREATOR);
        parcelZza.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zzd(zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(18, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zza(Bundle bundle, zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, bundle);
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(19, parcelA_);
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final void zze(zzn zznVar) throws RemoteException {
        Parcel parcelA_ = a_();
        com.google.android.gms.internal.measurement.zzb.zza(parcelA_, zznVar);
        zzb(20, parcelA_);
    }
}
