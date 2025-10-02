package com.google.android.gms.internal.vision;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.vision.barcode.Barcode;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzo extends zzb implements zzl {
    zzo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector");
    }

    @Override // com.google.android.gms.internal.vision.zzl
    public final Barcode[] zza(IObjectWrapper iObjectWrapper, zzu zzuVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, iObjectWrapper);
        zzd.zza(parcelZza, zzuVar);
        Parcel parcelZza2 = zza(1, parcelZza);
        Barcode[] barcodeArr = (Barcode[]) parcelZza2.createTypedArray(Barcode.CREATOR);
        parcelZza2.recycle();
        return barcodeArr;
    }

    @Override // com.google.android.gms.internal.vision.zzl
    public final Barcode[] zzb(IObjectWrapper iObjectWrapper, zzu zzuVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, iObjectWrapper);
        zzd.zza(parcelZza, zzuVar);
        Parcel parcelZza2 = zza(2, parcelZza);
        Barcode[] barcodeArr = (Barcode[]) parcelZza2.createTypedArray(Barcode.CREATOR);
        parcelZza2.recycle();
        return barcodeArr;
    }

    @Override // com.google.android.gms.internal.vision.zzl
    public final void zzo() throws RemoteException {
        zzb(3, zza());
    }
}
