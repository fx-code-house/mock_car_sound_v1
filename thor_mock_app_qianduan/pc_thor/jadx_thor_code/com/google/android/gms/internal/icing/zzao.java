package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.GoogleNowAuthState;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzao extends zza implements zzam {
    public zzao() {
        super("com.google.android.gms.search.internal.ISearchAuthCallbacks");
    }

    @Override // com.google.android.gms.internal.icing.zza
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            zza((Status) zzd.zza(parcel, Status.CREATOR), (GoogleNowAuthState) zzd.zza(parcel, GoogleNowAuthState.CREATOR));
        } else {
            if (i != 2) {
                return false;
            }
            zzb((Status) zzd.zza(parcel, Status.CREATOR));
        }
        return true;
    }
}
