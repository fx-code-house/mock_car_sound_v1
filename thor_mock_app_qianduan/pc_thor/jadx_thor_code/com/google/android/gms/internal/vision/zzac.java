package com.google.android.gms.internal.vision;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public final class zzac extends zzb implements zzad {
    zzac(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.text.internal.client.INativeTextRecognizer");
    }

    @Override // com.google.android.gms.internal.vision.zzad
    public final zzah[] zza(IObjectWrapper iObjectWrapper, zzu zzuVar, zzaj zzajVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, iObjectWrapper);
        zzd.zza(parcelZza, zzuVar);
        zzd.zza(parcelZza, zzajVar);
        Parcel parcelZza2 = zza(3, parcelZza);
        zzah[] zzahVarArr = (zzah[]) parcelZza2.createTypedArray(zzah.CREATOR);
        parcelZza2.recycle();
        return zzahVarArr;
    }

    @Override // com.google.android.gms.internal.vision.zzad
    public final void zzs() throws RemoteException {
        zzb(2, zza());
    }
}
