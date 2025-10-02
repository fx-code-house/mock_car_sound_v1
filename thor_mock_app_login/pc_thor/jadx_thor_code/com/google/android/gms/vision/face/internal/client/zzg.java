package com.google.android.gms.vision.face.internal.client;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.vision.zzu;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.1 */
/* loaded from: classes2.dex */
public abstract class zzg extends com.google.android.gms.internal.vision.zza implements zzh {
    public zzg() {
        super("com.google.android.gms.vision.face.internal.client.INativeFaceDetector");
    }

    @Override // com.google.android.gms.internal.vision.zza
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            FaceParcel[] faceParcelArrZzc = zzc(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), (zzu) com.google.android.gms.internal.vision.zzd.zza(parcel, zzu.CREATOR));
            parcel2.writeNoException();
            parcel2.writeTypedArray(faceParcelArrZzc, 1);
        } else if (i == 2) {
            boolean zZzd = zzd(parcel.readInt());
            parcel2.writeNoException();
            com.google.android.gms.internal.vision.zzd.writeBoolean(parcel2, zZzd);
        } else if (i == 3) {
            zzo();
            parcel2.writeNoException();
        } else {
            if (i != 4) {
                return false;
            }
            FaceParcel[] faceParcelArrZza = zza(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (zzu) com.google.android.gms.internal.vision.zzd.zza(parcel, zzu.CREATOR));
            parcel2.writeNoException();
            parcel2.writeTypedArray(faceParcelArrZza, 1);
        }
        return true;
    }
}
