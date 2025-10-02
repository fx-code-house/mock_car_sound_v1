package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzaf extends zza implements zzac {
    public zzaf() {
        super("com.google.android.gms.appdatasearch.internal.ILightweightAppDataSearchCallbacks");
    }

    @Override // com.google.android.gms.internal.icing.zza
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            zza((Status) zzd.zza(parcel, Status.CREATOR));
        } else if (i == 2) {
            zza((Status) zzd.zza(parcel, Status.CREATOR), (ParcelFileDescriptor) zzd.zza(parcel, ParcelFileDescriptor.CREATOR));
        } else {
            if (i != 4) {
                return false;
            }
            zza((zzo) zzd.zza(parcel, zzo.CREATOR));
        }
        return true;
    }
}
