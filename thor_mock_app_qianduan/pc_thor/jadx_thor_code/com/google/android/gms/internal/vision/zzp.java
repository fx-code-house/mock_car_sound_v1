package com.google.android.gms.internal.vision;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzp extends zzb implements zzn {
    zzp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
    }

    @Override // com.google.android.gms.internal.vision.zzn
    public final zzl zza(IObjectWrapper iObjectWrapper, zzk zzkVar) throws RemoteException {
        zzl zzoVar;
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, iObjectWrapper);
        zzd.zza(parcelZza, zzkVar);
        Parcel parcelZza2 = zza(1, parcelZza);
        IBinder strongBinder = parcelZza2.readStrongBinder();
        if (strongBinder == null) {
            zzoVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector");
            if (iInterfaceQueryLocalInterface instanceof zzl) {
                zzoVar = (zzl) iInterfaceQueryLocalInterface;
            } else {
                zzoVar = new zzo(strongBinder);
            }
        }
        parcelZza2.recycle();
        return zzoVar;
    }
}
