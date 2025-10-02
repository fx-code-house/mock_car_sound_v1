package com.google.android.gms.vision.face.internal.client;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.vision.zzu;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzj extends com.google.android.gms.internal.vision.zzb implements zzh {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.face.internal.client.INativeFaceDetector");
    }

    @Override // com.google.android.gms.vision.face.internal.client.zzh
    public final FaceParcel[] zzc(IObjectWrapper iObjectWrapper, zzu zzuVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, iObjectWrapper);
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, zzuVar);
        Parcel parcelZza2 = zza(1, parcelZza);
        FaceParcel[] faceParcelArr = (FaceParcel[]) parcelZza2.createTypedArray(FaceParcel.CREATOR);
        parcelZza2.recycle();
        return faceParcelArr;
    }

    @Override // com.google.android.gms.vision.face.internal.client.zzh
    public final FaceParcel[] zza(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, int i, int i2, int i3, int i4, int i5, int i6, zzu zzuVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, iObjectWrapper);
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, iObjectWrapper2);
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, iObjectWrapper3);
        parcelZza.writeInt(i);
        parcelZza.writeInt(i2);
        parcelZza.writeInt(i3);
        parcelZza.writeInt(i4);
        parcelZza.writeInt(i5);
        parcelZza.writeInt(i6);
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, zzuVar);
        Parcel parcelZza2 = zza(4, parcelZza);
        FaceParcel[] faceParcelArr = (FaceParcel[]) parcelZza2.createTypedArray(FaceParcel.CREATOR);
        parcelZza2.recycle();
        return faceParcelArr;
    }

    @Override // com.google.android.gms.vision.face.internal.client.zzh
    public final boolean zzd(int i) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeInt(i);
        Parcel parcelZza2 = zza(2, parcelZza);
        boolean zZza = com.google.android.gms.internal.vision.zzd.zza(parcelZza2);
        parcelZza2.recycle();
        return zZza;
    }

    @Override // com.google.android.gms.vision.face.internal.client.zzh
    public final void zzo() throws RemoteException {
        zzb(3, zza());
    }
}
