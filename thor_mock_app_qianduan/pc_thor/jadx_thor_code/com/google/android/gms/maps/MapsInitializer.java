package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

/* loaded from: classes2.dex */
public final class MapsInitializer {
    private static boolean zzbm = false;

    public static synchronized int initialize(Context context) {
        Preconditions.checkNotNull(context, "Context is null");
        if (zzbm) {
            return 0;
        }
        try {
            com.google.android.gms.maps.internal.zze zzeVarZza = zzbz.zza(context);
            try {
                CameraUpdateFactory.zza(zzeVarZza.zze());
                BitmapDescriptorFactory.zza(zzeVarZza.zzf());
                zzbm = true;
                return 0;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } catch (GooglePlayServicesNotAvailableException e2) {
            return e2.errorCode;
        }
    }

    private MapsInitializer() {
    }
}
