package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzf extends zza implements zzd {
    zzf(IBinder iBinder) {
        super(iBinder, "com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
    }

    @Override // com.google.android.gms.internal.measurement.zzd
    public final Bundle zza(Bundle bundle) throws RemoteException {
        Parcel parcelA_ = a_();
        zzb.zza(parcelA_, bundle);
        Parcel parcelZza = zza(1, parcelA_);
        Bundle bundle2 = (Bundle) zzb.zza(parcelZza, Bundle.CREATOR);
        parcelZza.recycle();
        return bundle2;
    }
}
