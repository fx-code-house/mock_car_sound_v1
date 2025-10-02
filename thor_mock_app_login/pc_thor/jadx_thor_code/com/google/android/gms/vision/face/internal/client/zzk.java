package com.google.android.gms.vision.face.internal.client;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzk extends com.google.android.gms.internal.vision.zzb implements zzi {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.face.internal.client.INativeFaceDetectorCreator");
    }

    @Override // com.google.android.gms.vision.face.internal.client.zzi
    public final zzh newFaceDetector(IObjectWrapper iObjectWrapper, zzf zzfVar) throws RemoteException {
        zzh zzjVar;
        Parcel parcelZza = zza();
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, iObjectWrapper);
        com.google.android.gms.internal.vision.zzd.zza(parcelZza, zzfVar);
        Parcel parcelZza2 = zza(1, parcelZza);
        IBinder strongBinder = parcelZza2.readStrongBinder();
        if (strongBinder == null) {
            zzjVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.vision.face.internal.client.INativeFaceDetector");
            if (iInterfaceQueryLocalInterface instanceof zzh) {
                zzjVar = (zzh) iInterfaceQueryLocalInterface;
            } else {
                zzjVar = new zzj(strongBinder);
            }
        }
        parcelZza2.recycle();
        return zzjVar;
    }
}
